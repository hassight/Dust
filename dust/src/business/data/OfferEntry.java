package business.data;

public class OfferEntry {
	private String keywords = "";
	private String siteType = "";
	private int minPrice = 0;
	private int maxPrice = 3000;
	private int minVisitDuration = 2;
	private int maxVisitDuration = 6;
	private int pace = 2;
	
	public OfferEntry() {
		
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public int getMinVisitDuration() {
		return minVisitDuration;
	}

	public void setMinVisitDuration(int minVisitDuration) {
		this.minVisitDuration = minVisitDuration;
	}

	public int getMaxVisitDuration() {
		return maxVisitDuration;
	}

	public void setMaxVisitDuration(int maxVisitDuration) {
		this.maxVisitDuration = maxVisitDuration;
	}

	public int getPace() {
		return pace;
	}

	public void setPace(int pace) {
		this.pace = pace;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
}
