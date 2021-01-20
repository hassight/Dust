package tests;

public class TestRandom {

	public static void main(String[] args) {
		int min = 0, max = 3;
		System.out.println((int)(Math.random()*((max-min)+1))+min);
	}
}