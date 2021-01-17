package business.data;

public abstract class AbstractSite {
	private String name;
	private int price;
	private Coordinates coordinates;
	protected SiteEnum type;
	private String description;
	
	public AbstractSite() {
		
	}

	public SiteEnum getType() {
		return type;
	}

	public void setType(SiteEnum type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "AbstractSite [name: " + name + ", price: " + price + ", description: " + getDescription()
				+ ", type: " + type + ", coordinates: " + coordinates + "]";
	}
}
