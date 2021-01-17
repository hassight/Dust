package business;

public class Ride {
	private AbstractSite departureSite;
	private AbstractSite arrivalSite;
	private Transport transport;
	
	public Ride(AbstractSite departureSite, AbstractSite arrivalSite, Transport transport) {
		this.setDepartureSite(departureSite);
		this.setArrivalSite(arrivalSite);
		this.setTransport(transport);
	}
	
	public Ride() {
		
	}
	
	public AbstractSite getDepartureSite() {
		return departureSite;
	}

	public void setDepartureSite(AbstractSite departureSite) {
		this.departureSite = departureSite;
	}

	public AbstractSite getArrivalSite() {
		return arrivalSite;
	}

	public void setArrivalSite(AbstractSite arrivalSite) {
		this.arrivalSite = arrivalSite;
	}

	public Transport getTransport() {
		return transport;
	}
	
	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	@Override
	public String toString() {
		return "Ride [departure: " + departureSite + ", arrival: " + arrivalSite + ", transport: " + transport + "]";
	}
}