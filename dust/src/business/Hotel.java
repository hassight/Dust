package business;

public class Hotel extends Place {
	private String beachName;
		
	public Hotel() {
		
	}

	public String getBeachName() {
		return beachName;
	}

	public void setBeachName(String beachName) {
		this.beachName = beachName;
	}

	@Override
	public String toString() {
		return "Hotel [name: " + super.getName() + ", price: " + super.getPrice() + ", beachName: " + beachName
				+ ", coordinates: " + super.getCoordinates() + "]";
	}
}
