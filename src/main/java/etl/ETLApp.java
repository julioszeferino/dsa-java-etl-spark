package etl;

import java.io.*; // lib para leitura e escrita de dados
import java.nio.file.*; // lib para operar arquivos e diretorios
import java.util.*; // lib que adiciona metodos uteis para a manipulacao de dados
import java.util.stream.*; // class que permite iterar sobre colecoes
import java.io.FileWriter; // class para gravar dados em arquivos
import java.io.IOException; // class para manipulacao erros de escrita e leitura de dados
import org.apache.commons.lang3.text.WordUtils; // class para a processamento de textos

public class ETLApp {

    /**Metodo para imprimir dados no terminal
     * @param x - texto a ser impresso
    */
    public static <T> void print(T x){
        System.out.println(x);
    }


    /**Metodo para gravar as saidas em um arquivo txt
     * @param message - string a ser gravada no arquivo
     */
    public static void grava(String message) {
        try{
    	    String filename = "resultado.txt";
            FileWriter fw = new FileWriter(filename, true); 
            fw.write(message);
    		fw.close();
        } catch(IOException ioe){
    		System.err.println("Nao foi possivel escrever no arquivo resultados.txt: " + ioe.getMessage());
		}
    }


    /**Metodo principal que executa o processo de ETL*/
    public static void main(String[] args) throws IOException {

        print("\nIniciando o Processamento...\n");
        String fileName = "dados.csv";

        // carregando o arquivo e instancia os objetos Car recuperados a partir da 2 linha
        List<Car> cars = Files.lines(Path.of(fileName)).skip(1).map(Car::String2Car).collect(Collectors.toList());

        // removendo nulos
        cars = cars.stream().filter(car -> car != null).collect(Collectors.toList());

        print("\nIniciando a extração...");
		grava("ETL Realizado no Arquivo dados.csv\n");

        // 1- Extrai o número total de veículos
        long count = cars.stream().count();
        String str1 = String.valueOf(count);
        grava("Número total de veículos: " + str1);
		print("\nExtraído e Gravado o Item 1!");

        // 2- Extrai o número total de veículos fabricados nos EUA
        var uscars = cars.stream().filter(car -> car.getOrigin().equals("usa"));
        String str2 = String.valueOf(uscars.count());
        grava("\nNúmero total de veículos produzidos nos EUA: " + str2);
		print("\nExtraído e Gravado o Item 2!");

        // 3- Extrai o maior ano de fabricação entre todos os anos (ou seja, ano mais recente)
        var maxYear = cars.stream().map(car -> car.getModelYear()).reduce((x,y) -> Math.max(x, y)).get();
        String str3 = String.valueOf(maxYear);
        grava("\nMaior ano de fabricação dos veículos: " + str3);
		print("\nExtraído e Gravado o Item 3!");

        // 4- Extrai a média de horsepower de veículos produzidos no Japão
        var japanHorsepower = cars.stream().filter(car -> car.getOrigin().equals("japan")).mapToDouble(car -> car.getHorsepower()).average().getAsDouble();
        String str4 = String.valueOf(japanHorsepower);
		grava("\nMédia de horsepower dos veículos produzidos no Japão: " + str4);
		print("\nExtraído e Gravado o Item 4!");

        // 5- Extrai a média de horsepower por país de fabricação dos veículos
        var countryHorsePower = cars.stream().collect(Collectors.groupingBy(car -> car.getOrigin(), Collectors.averagingDouble(car -> car.getHorsepower())));
        String str5 = String.valueOf(countryHorsePower);
		grava("\nMédia de horsepower dos veículos produzidos por país/continente: " + str5);
		print("\nExtraído e Gravado o Item 5!");

        // 6- Considerando que a primeira palavra da coluna de nome do veículo é a marca, 
		// extraímos as marcas distintas, convertendo para maiúsculas e minúsculas.
        List<String> brands = cars.stream().map(car -> car.getName().split(" ")[0]).distinct().map(name -> WordUtils.capitalize(name)).collect(Collectors.toList());
        String str6 = String.valueOf(brands);
		grava("\nMarcas dos veículos: " + str6);
		print("\nExtraído e Gravado o Item 6!");	

        // 7- Extrai a marca com o maior número de veículos
        var res = cars.stream().map(car -> car.getName().split(" ")[0]).collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        var brandWithHighestNumber = res.entrySet().stream().max( (x, y) -> x.getValue().compareTo(y.getValue()) ).get().getKey();
        String str7 = String.valueOf(brandWithHighestNumber);
		grava("\nMarca com maior número de veículos: " + str7);
		print("\nExtraído e Gravado o Item 7!");	

        print("\nProcessamento Concluído. Obrigado por usar esta  app! Verifique o arquivo de saída!\n");
    }

}