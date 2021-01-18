package tests;

import persistence.jdbc.Queries;

public class TestFillDBRides {

	public static void main(String[] args) {
		Queries q = new Queries();
        q.fillRides();
	}

}
