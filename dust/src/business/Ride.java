package business;

public class Ride {
	private Place departurePoint;
	private Place arrivalPoint;
	private Transport transport;
	private double tripCost;
	private double distance;
	
	public Ride(Place departurePoint, Place arrivalPoint, Transport transport) {
		this.setDeparturePoint(departurePoint);
		this.setArrivalPoint(arrivalPoint);
		this.setTransport(transport);
		this.distance = calculateDistance(departurePoint, arrivalPoint);
		this.tripCost = calculateTripCost(transport, distance);
	}
	
	public Ride() {
		
	}
	
	public Place getDeparturePoint() {
		return departurePoint;
	}
	
	public void setDeparturePoint(Place departurePoint) {
		this.departurePoint = departurePoint;
	}
	
	public Place getArrivalPoint() {
		return arrivalPoint;
	}
	
	public void setArrivalPoint(Place arrivalPoint) {
		this.arrivalPoint = arrivalPoint;
	}
	
	public Transport getTransport() {
		return transport;
	}
	
	public void setTransport(Transport transport) {
		this.transport = transport;
	}
	
	public double getTripCost() {
		return tripCost;
	}
	
	public void setTripCost(double tripCost) {
		this.tripCost = tripCost;
	}
	
	public double calculateTripCost(Transport transport, double distance) {
		if (transport.getType() == TransportEnum.BUS) {
			tripCost = transport.getPrice() + (Transport.BUS_FACTOR * distance);
		} else {
			tripCost = transport.getPrice();
		}
		
		return tripCost;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public double calculateDistance(Place departurePoint, Place arrivalPoint) {
		distance = Distance.getKMDistance(departurePoint.getCoordinates(), arrivalPoint.getCoordinates());
		return distance;
	}

	@Override
	public String toString() {
		return "Ride [departure: " + departurePoint + ", arrival: " + arrivalPoint + ", transport: "
				+ transport + "trip cost: " + tripCost + "]";
	}
}