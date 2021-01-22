package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import business.data.Offer;
import business.data.OfferEntry;
import business.data.Ride;
import business.engine.OfferCalculator;
import persistence.jdbc.Queries;

public class Main {

	public static void main(String[] args) {
		try {
			// ONLY UNCOMMENT THESE LINES IF YOU EXECUTE IT FOR THE FIRST TIME, TO FILL RIDES TABLE.
			/*Queries queries = new Queries();
			queries.fillRides();*/
			
			String[] inputs = new String[7];

			Scanner scan = new Scanner(new File("src/inputs.txt"));
			
			while (scan.hasNextLine()) {
				inputs[0] = scan.nextLine().split("=", -1)[1];
				inputs[1] = scan.nextLine().split("=", -1)[1];
				inputs[2] = scan.nextLine().split("=", -1)[1];
				inputs[3] = scan.nextLine().split("=", -1)[1];
				inputs[4] = scan.nextLine().split("=", -1)[1];
				inputs[5] = scan.nextLine().split("=", -1)[1];
				inputs[6] = scan.nextLine().split("=", -1)[1];
			}

			OfferEntry offerEntry = new OfferEntry();

			if (!inputs[0].trim().isEmpty()) {
				offerEntry.setKeywords(inputs[0]);
			}
			if (!inputs[1].trim().isEmpty()) {
				offerEntry.setSiteType(inputs[1]);
			}
			if (!inputs[2].trim().isEmpty()) {
				offerEntry.setMinPrice(Integer.valueOf(inputs[2]));
			}
			if (!inputs[3].trim().isEmpty()) {
				offerEntry.setMaxPrice(Integer.valueOf(inputs[3]));
			}
			if (!inputs[4].trim().isEmpty()) {
				offerEntry.setMinVisitDuration(Integer.valueOf(inputs[4]));
			}
			if (!inputs[5].trim().isEmpty()) {
				offerEntry.setMaxVisitDuration(Integer.valueOf(inputs[5]));
			}
			if (!inputs[6].trim().isEmpty()) {
				offerEntry.setPace(Integer.valueOf(inputs[6]));
			}			
			
			OfferCalculator offerCalculator = new OfferCalculator(offerEntry);
			ArrayList<Offer> results = offerCalculator.getOffers();
			
			for (Offer offer : results) {
				System.out.println("\n*********************************************");
				System.out.println("*                                           *");
				System.out.println("*" + "         " + offer.getName() + ", PRICE: " + offer.getPrice() + "         " + "*");
				System.out.println("*                                           *");
				System.out.println("*********************************************");
				System.out.println("HOTEL: " + offer.getHotel().getName() + ", price: " + offer.getHotel().getPrice() + "\n");
				for (int j = 0; j < offer.getExcursions().size(); j++) {
					System.out.println(offer.getExcursions().get(j).getName() + ": \t" + offer.getExcursions().get(j).getDescription());
					for (Ride ride : offer.getExcursions().get(j).getRides()) {
						System.out.println("\t\t\t\t" + ride.getTransport().getType());
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Check your file path;");
		}
		
	}

}
