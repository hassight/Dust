package business;

public abstract class AbstractSite extends Place {
	protected SiteEnum type;
	
	public AbstractSite() {
		
	}

	public SiteEnum getType() {
		return type;
	}

	public void setType(SiteEnum type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "AbstractSite [name: " + super.getName() + ", price: " + super.getPrice() + ", description: " + super.getDescription()
				+ ", type: " + type + ", coordinates: " + super.getCoordinates() + "]";
	}
}
