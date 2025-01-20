/*
 * Volume.java
 *
 * Instructions:
 *
 * The code below should compute volume of a sphere.  However it contains
 * ERRORS. Your task is to fix these errors (syntax errors first, but there are
 * more than syntax errors in this code!) and to check the functionality.
 *
 * Make sure that at the end the volume is computed correctly and displayed in
 * cubic meters and it is computed accurately with the tolerance in cubic mm.
 *
 * Also structure the code properly so it is legible and enter comments wherever
 * appropriate.
 *
 * Author: D. Moxey
 * Date:   3/1/10
 * Updated: A. Hague
 * Date:   4/1/22
 */

import java.util.Scanner;

public class Volume {
	public static void main(String args[]) {
		System.out.print("Radius of sphere in centimetres: ");

		// Read input
		Scanner s = new Scanner(System.in);
		double r = s.nextDouble();
		s.close();

		System.out.format("You entered %.3f centimetres, which is %.3f metres%n", r, r / 100.0);

		// Convert radius to metres
		r = r / 100.0;

		double v = 4.0 * Math.PI * r * r * r / 3.0;
		System.out.format("Volume of the sphere is %.4f cubic metres%n", v);
	}

}
