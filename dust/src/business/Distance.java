package business;

public final class Distance {
	public static double getKMDistance(Coordinates coordinates1, Coordinates coordinates2) {
		double distance;
		double earthRadius = 6378.137;
		double lat1 = Math.toRadians(coordinates1.getLatitude());
		double lat2 = Math.toRadians(coordinates2.getLatitude());
		double long1 = Math.toRadians(coordinates1.getLongitude());
		double long2 = Math.toRadians(coordinates2.getLongitude());
		distance = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(long1 - long2));
		
		return earthRadius * distance;
	}
}
