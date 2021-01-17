package business;

import business.data.Coordinates;
import business.data.Distance;

public class Test {
	public static void main(String[] args) {
		Coordinates c = new Coordinates(-17.539757944124336, -149.56728263802216);
		Coordinates c1 = new Coordinates(-17.532535360396363, -149.54468602215124);
		System.out.println(Distance.getKMDistance(c, c1));
	}
}
