/*
 * PROJECT II: Polynomial.java
 *
 * This file contains a template for the class Polynomial. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * This class is designed to use Complex in order to represent polynomials
 * with complex co-efficients. It provides very basic functionality and there
 * are very few methods to implement! The project formulation contains a
 * complete description.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file! You should also test this class using the main()
 * function.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 *
 * Tasks:
 *
 * 1) Complete this class with the indicated methods and instance variables.
 *
 * 2) Fill in the following fields:
 *
 * NAME: Dyson Dyson
 * UNIVERSITY ID: 5503449
 * DEPARTMENT: Mathematics
 */

import java.util.Arrays;

public class Polynomial {
	/**
	 * An array storing the complex co-efficients of the polynomial.
	 */
	Complex[] coeff;

	// ========================================================
	// Constructor functions.
	// ========================================================

	/**
	 * General constructor: assigns this polynomial a given set of
	 * co-efficients.
	 *
	 * @param coeff  The co-efficients to use for this polynomial.
	 */
	public Polynomial(Complex[] coeff) {
		int finalIndex = coeff.length - 1;
		while (finalIndex >= 0 && coeff[finalIndex].equals(new Complex()))
			finalIndex--;

		this.coeff = Arrays.copyOf(coeff, finalIndex + 1);
	}

	/**
	 * Default constructor: sets the Polynomial to the zero polynomial.
	 */
	public Polynomial() {
		this(new Complex[] {});
	}

	// ========================================================
	// Operations and functions with polynomials.
	// ========================================================

	/**
	 * Return the coefficients array.
	 *
	 * @return  The coefficients array.
	 */
	public Complex[] getCoeff() {
		return this.coeff;
	}

	/**
	 * Create a string representation of the polynomial.
	 * Use z to represent the variable.  Include terms
	 * with zero co-efficients up to the degree of the
	 * polynomial.
	 *
	 * For example: (-5.000+5.000i) + (2.000-2.000i)z + (-1.000+0.000i)z^2
	 */
	public String toString() {
		// TODO
		return "(-5.000+5.000i) + (2.000-2.000i)z + (-1.000+0.000i)z^2";
	}

	/**
	 * Returns the degree of this polynomial.
	 */
	public int degree() {
		// If coeff == [] then we want degree 0, not -1
		return Math.max(0, this.coeff.length - 1);
	}

	/**
	 * Evaluates the polynomial at a given point z.
	 *
	 * @param z  The point at which to evaluate the polynomial
	 * @return   The complex number P(z).
	 */
	public Complex evaluate(Complex z) {
		// TODO
		return new Complex();
	}


	// ========================================================
	// Tester function.
	// ========================================================

	public static void main(String[] args) {
		System.out.println("Empty array: " + Arrays.toString(new Polynomial(new Complex[] {}).getCoeff()));
		System.out.println("No args: " + Arrays.toString(new Polynomial().getCoeff()));
		System.out.println("x^3 - 1: " + Arrays.toString(new Polynomial(
			new Complex[] {new Complex(-1.0), new Complex(), new Complex(), new Complex(1.0)}
		).getCoeff()));
		System.out.println("x^2 - (2+i) with extra zeroes: " + Arrays.toString(new Polynomial(
			new Complex[] {new Complex(-2.0, -1.0), new Complex(), new Complex(1.0), new Complex(), new Complex()}
		).getCoeff()));

		System.out.println("Degree of 0: " + new Polynomial().degree());
		System.out.println("Degree of 1-3i: " + new Polynomial(new Complex[] {new Complex(1.0, -3.0)}).degree());
		System.out.println("Degree of x^2 + (-2+i) x + 1-3i: " + new Polynomial(
			new Complex[] {new Complex(1.0, -3.0), new Complex(-2.0, 1.0), new Complex(1.0)}
		).degree());
	}
}
