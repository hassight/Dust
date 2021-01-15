package business;

public abstract class Place {
	private String name;
	private String description;
	private int price;
	private Coordinates coordinates;
	private boolean busDisponibility;
	private boolean boatDisponibility;
	
	public Place() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	public boolean isBusDisponibility() {
		return busDisponibility;
	}
	
	public void setBusDisponibility(boolean busDisponibility) {
		this.busDisponibility = busDisponibility;
	}
	
	public boolean isBoatDisponibility() {
		return boatDisponibility;
	}
	
	public void setBoatDisponibility(boolean boatDisponibility) {
		this.boatDisponibility = boatDisponibility;
	}
}
