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
		// You need to fill in this function.
	}

	/**
	 * Default constructor: sets the Polynomial to the zero polynomial.
	 */
	public Polynomial() {
		// You need to fill in this function.
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
		// You need to fill in this method with the correct code.
		return new Complex[] {};
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
		// You need to fill in this method with the correct code.
		return "(-5.000+5.000i) + (2.000-2.000i)z + (-1.000+0.000i)z^2";
	}

	/**
	 * Returns the degree of this polynomial.
	 */
	public int degree() {
		// You need to fill in this method with the correct code.
		return 0;
	}

	/**
	 * Evaluates the polynomial at a given point z.
	 *
	 * @param z  The point at which to evaluate the polynomial
	 * @return   The complex number P(z).
	 */
	public Complex evaluate(Complex z) {
		// You need to fill in this method with the correct code.
		return new Complex();
	}


	// ========================================================
	// Tester function.
	// ========================================================

	public static void main(String[] args) {
		// You can fill in this function with your own testing code.
	}
}
