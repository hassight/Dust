package business.data;

public class Hotel {
	private String name;
	private int price;
	private String beachName;
	private Coordinates coordinates;
		
	public Hotel() {
		
	}

	public String getBeachName() {
		return beachName;
	}

	public void setBeachName(String beachName) {
		this.beachName = beachName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "Hotel [name: " + name + ", price: " + price + ", beachName: " + beachName
				+ ", coordinates: " + coordinates + "]";
	}
}
