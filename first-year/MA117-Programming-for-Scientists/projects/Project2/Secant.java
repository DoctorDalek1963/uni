/*
 * PROJECT II: Secant.java
 *
 * This file contains a template for the class Secant. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * In this class, you will create a basic Java object responsible for
 * performing the Secant root finding method on a given polynomial
 * f(z) with complex co-efficients. The formulation outlines the method, as
 * well as the basic class structure, details of the instance variables and
 * how the class should operate.
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

public class Secant {
	/**
	 * The maximum number of iterations that should be used when applying
	 * Secant. Ensure this is *small* (e.g. at most 50) otherwise your
	 * program may appear to freeze.
	 */
	public static final int MAXITER = 50;

	/**
	 * The tolerance that should be used throughout this project. Note that
	 * you should reference this variable and _not_ explicity write out
	 * 1.0e-10 in your solution code. Other classes can access this tolerance
	 * by using Secant.TOL.
	 */
	public static final double TOL = 1.0e-10;

	/**
	 * The tolerance squared, used for more efficicent comparisons against `abs2()`.
	 */
	public static final double TOL2 = TOL * TOL;

	/**
	 * The polynomial we wish to apply the Secant method to.
	 */
	private Polynomial f;

	/**
	 * A root of the polynomial f corresponding to the root found by the
	 * iterate() function below.
	 */
	private Complex root;

	/**
	 * The number of iterations required to reach within TOL of the root.
	 */
	private int numIterations;

	/**
	 * An enumeration that signifies errors that may occur in the root finding
	 * process.
	 *
	 * Possible values are:
	 *   OK: Nothing went wrong.
	 *   ZERO: Difference went to zero during the algorithm.
	 *   DNF: Reached MAXITER iterations (did not finish)
	 */
	enum Error { OK, ZERO, DNF };
	private Error err = Error.OK;


	// ========================================================
	// Constructor functions.
	// ========================================================

	/**
	 * Basic constructor.
	 *
	 * @param p  The polynomial used for Secant.
	 */
	public Secant(Polynomial p) {
		this.f = p;
		this.numIterations = 0;
	}

	// ========================================================
	// Accessor methods.
	// ========================================================

	/**
	 * Returns the current value of the err instance variable.
	 */
	public Error getError() {
		return this.err;
	}

	/**
	 * Returns the current value of the numIterations instance variable.
	 */
	public int getNumIterations() {
		return this.numIterations;
	}

	/**
	 * Returns the current value of the root instance variable.
	 */
	public Complex getRoot() {
		return this.root;
	}

	/**
	 * Returns the polynomial associated with this object.
	 */
	public Polynomial getF() {
		return this.f;
	}

	// ========================================================
	// Secant method (check the comment)
	// ========================================================

	/**
	 * Given two complex numbers z0 and z1, apply Secant to the polynomial f in
	 * order to find a root within tolerance TOL.
	 *
	 * One of three things may occur:
	 *
	 *   - The root is found, in which case, set root to the end result of the
	 *     algorithm, numIterations to the number of iterations required to
	 *     reach it and err to OK.
	 *   - At some point the absolute difference between f(zn) and f(zn-1) becomes zero.
	 *     In this case, set err to ZERO and return.
	 *   - After MAXITER iterations the algorithm has not converged. In this
	 *     case set err to DNF and return.
	 *
	 * @param z0,z1  The initial starting points for the algorithm.
	 */
	public void iterate(Complex z0, Complex z1) {
		for (int i = 0; i < MAXITER; i++) {
			Complex fz0 = this.f.evaluate(z0);
			Complex fz1 = this.f.evaluate(z1);

			// If the roots have converged
			if (z1.sub(z0).abs2() < TOL2 && fz1.abs2() < TOL2) {
				this.root = z1;
				this.numIterations = i;
				this.err = Error.OK;
				return;
			}

			// If the denominator is zero
			Complex funcDiff = fz1.sub(fz0);
			if (funcDiff.abs2() < TOL2) {
				this.err = Error.ZERO;
				return;
			}

			Complex z2 = z1.sub(fz1.multiply((z1.sub(z0)).divide(funcDiff)));
			z0 = z1;
			z1 = z2;
		}
		this.err = Error.DNF;
	}

	// ========================================================
	// Tester function.
	// ========================================================

	public static void main(String[] args) {
		// Basic tester: find a root of f(z) = z^3-1.
		Complex[] coeff = new Complex[] { new Complex(-1.0, 0.0), new Complex(), new Complex(), new Complex(1.0, 0.0) };
		Polynomial p = new Polynomial(coeff);
		Secant s = new Secant(p);

		s.iterate(new Complex(), new Complex(1.0, 1.0));
		System.out.println(s.getNumIterations() + " (should be 12)");
		System.out.println(s.getError() + " (should be OK)");
	}
}
