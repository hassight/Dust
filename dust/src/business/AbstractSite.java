package business;

public abstract class AbstractSite extends Place {
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
	
	@Override
	public String toString() {
		return "AbstractSite [name: " + super.getName() + ", price: " + super.getPrice() + ", description: " + getDescription()
				+ ", type: " + type + ", coordinates: " + super.getCoordinates() + "]";
	}
}
