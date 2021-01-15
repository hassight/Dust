package business;

public class Transport {
	// each km costs 10cts more
	public static double BUS_FACTOR = 0.1;
	
	private TransportEnum type;
	private int price;
	
	public Transport(TransportEnum type) {
		this.setType(type);
		if (type == TransportEnum.BUS) {
			price = 2;
		} else {
			price = 15;
		}
	}
	
	public TransportEnum getType() {
		return type;
	}
	
	public void setType(TransportEnum type) {
		this.type = type;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Transport [type: " + type + ", price: " + price;
	}
}