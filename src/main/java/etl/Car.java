package etl;

public class Car {

	private double mpg;
	private int cylinders;
	private double displacement;
	private double horsepower;
	private int weight;
	private double acceleration;
	private int modelYear;
	private String origin;
	private String name;

    
    /**Metodo construtor 
     * @param acceleration - aceleracao do carro
	 * @param horsepower - potencia em cavalos
	 * @param mpg - miles per gallon (US)
	 * @param origin - origem do carro
	 * @param name - name do carro
	 * @param weight - peso do carro
	 * @param displacement - deslocamento
	 * @param modelYear - ano do modelo
	 * @param cylinders - quantidade de cilindros
    */
    public Car() {}
    public Car(double mpg, int cylinders, double displacement, double horsepower, int weight, double acceleration, int modelYear, String origin, String name){
        super();
        this.mpg = mpg;
        this.cylinders = cylinders;
        this.displacement = displacement;
        this.horsepower = horsepower;
        this.weight = weight;
        this.acceleration = acceleration;
		this.modelYear = modelYear;
		this.origin = origin;
		this.name = name;
    }


    public double getMpg() {
		return mpg;
	}


	public void setMpg(double mpg) {
		this.mpg = mpg;
	}


	public int getCylinders() {
		return cylinders;
	}


	public void setCylinders(int cylinders) {
		this.cylinders = cylinders;
	}


	public double getDisplacement() {
		return displacement;
	}


	public void setDisplacement(double displacement) {
		this.displacement = displacement;
	}


	public double getHorsepower() {
		return horsepower;
	}


	public void setHorsepower(double horsepower) {
		this.horsepower = horsepower;
	}


	public int getWeight() {
		return weight;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}


	public double getAcceleration() {
		return acceleration;
	}


	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}


	public int getModelYear() {
		return modelYear;
	}


	public void setModelYear(int modelYear) {
		this.modelYear = modelYear;
	}


	public String getOrigin() {
		return origin;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


    /**Método para validar e converter a linha
     * @param line - linha do arquivo
     * @return Car - objeto do tipo Car
     */
    public static Car String2Car(String line) {

        Car car = null;

        String[] fields = line.split(",");

        // validando a quantidade de colunas
        if (fields.length != Car.class.getDeclaredFields().length) {
            System.err.println("Linha nao possui todos os atributos necessarios: " + line);
        } else {
            try {
                // recuperando os dados
                double mpg = Double.valueOf(fields[0]);
				int cylinders = Integer.valueOf(fields[1]);
				double displacement = Double.valueOf(fields[2]);
				double horsepower = Double.valueOf(fields[3]);
                int weight = Integer.valueOf(fields[4]);
				double acceleration = Double.valueOf(fields[5]);
				int modelYear = Integer.valueOf(fields[6]);
				String origin = fields[7].trim();
				String name = fields[8].trim();

                // criando o objeto
                car = new Car(mpg, cylinders, displacement, horsepower, weight, acceleration, modelYear, origin, name);
            } catch (NumberFormatException ex) {
                System.err.println("Nao foi possivel recuperar os dados da linha: " + line);
            }
        }
        
        return car;
    }


    /**Metodo personalizado que retorna uma string com os atributos da classe Car
     * @return String - nome @ hash [variavel=valor, ..., variavel=valor]
     */
    @Override
	public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(Car.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        
        sb.append("mpg").append('=').append(this.mpg).append(',');
        sb.append("cylinders").append('=').append(this.cylinders).append(',');
        sb.append("displacement").append('=').append(this.displacement).append(',');
        sb.append("horsepower").append('=').append(this.horsepower).append(',');
        sb.append("weight").append('=').append(this.weight).append(',');
        sb.append("acceleration").append('=').append(this.acceleration).append(',');
        sb.append("modelYear").append('=').append(this.modelYear).append(',');
        sb.append("origin").append('=').append(((this.origin == null) ? "<null>" : this.origin)).append(',');
        sb.append("name").append('=').append(((this.name == null) ? "<null>" : this.name)).append(',');
        
        // validando se o ultimo caractere adicionado foi a ',', coloca ']' no lugar
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
			sb.append(']');
		}

        return sb.toString();
    }
}
