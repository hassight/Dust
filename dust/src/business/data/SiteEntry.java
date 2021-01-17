package business.data;

public class SiteEntry {
	private String keywords = "";
	private int minPrice = 0;
	private int maxPrice = 150;
	private int numberOfSites;
	
	public SiteEntry() {
		
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

	public int getNumberOfSites() {
		return numberOfSites;
	}

	public void setNumberOfSites(int numberOfSites) {
		this.numberOfSites = numberOfSites;
	}
}
