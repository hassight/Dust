package tests;

import java.util.ArrayList;

public class TestHotelCreation {

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		int nbHotels = 4;
		
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		
		for (int j = 0; j < list.size(); j++) {
			if (j%nbHotels == 0) {
				// Hotel creation here
				System.out.println(j);
			}
		}
	}

}
