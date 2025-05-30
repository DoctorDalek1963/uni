/*
 * PROJECT II: Complex.java
 *
 * This file contains a template for the class Complex. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * This class implements a new type for complex numbers and corresponding
 * arithmetic operations. You should already have done something very similar
 * in the labs (see CmplxNum in the labs), however, they are not identical.
 *
 * At the bottom of this file, I have included a simple main function which
 * tests the basic functionality.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
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

public class Complex {
	/**
	 * Real part x of the complex number x+iy.
	 */
	private double x;

	/**
	 * Imaginary part y of the complex number x+iy.
	 */
	private double y;

	// ========================================================
	// Constructor functions.
	// ========================================================

	/**
	 * Constructor: Initializes x, y.
	 *
	 * @param x The initial value of the real component.
	 * @param y The initial value of the imaginary component.
	 */
	public Complex(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Real constructor - initialises with a real number.
	 *
	 * @param x The initial real number to initialise to.
	 */
	public Complex(double x) {
		this(x, 0.0);
	}

	/**
	 * Default constructor; initialise x and y to zero.
	 */
	public Complex() {
		this(0.0);
	}

	// ========================================================
	// Accessor and mutator methods.
	// ========================================================

	/**
	 * Accessor Method: get real part of the complex number.
	 *
	 * @return The real part of the complex number.
	 */
	public double getReal() {
		return this.x;
	}

	/**
	 * Accessor Method: get imaginary part of the complex number.
	 *
	 * @return The imaginary part of the complex number
	 */
	public double getImag() {
		return this.y;
	}

	/**
	 * Mutator method: set the real part of the complex number.
	 *
	 * @param x The replacement real part of z.
	 */
	public void setReal(double x) {
		this.x = x;
	}

	/**
	 * Mutator method: set the imaginary part of the complex number.
	 *
	 * @param y The replacement imaginary part of z.
	 */
	public void setImag(double y) {
		this.y = y;
	}

	// ========================================================
	// Operations and functions with complex numbers.
	// ========================================================

	/**
	 * Converts the complex number to a string. This is an important method as
	 * it allows us to print complex numbers using System.out.println.
	 *
	 * @return A string describing the complex number.
	 */
	public String toString() {
		return String.format("%.3f%s%.3fi", x, (y < 0.0 ? "-" : "+"), Math.abs(y));
	}

	/**
	 * Computes square of the absolute value (magnitude) of the complex number
	 * (i.e. |z|^2).
	 *
	 * @return The square of the absolute value of this complex number.
	 */
	public double abs2() {
		return this.x * this.x + this.y * this.y;
	}

	/**
	 * Computes absolute value (magnitude) of the complex number.
	 *
	 * @return The absolute value of the complex number.
	 */
	public double abs() {
		return Math.sqrt(this.abs2());
	}

	/**
	 * Calculates the conjugate of this complex number.
	 *
	 * @return A Complex containing the conjugate.
	 */
	public Complex conjugate() {
		return new Complex(this.x, -this.y);
	}

	/**
	 * Adds a complex number to this one.
	 *
	 * @param b The complex number to add to this one.
	 * @return The sum of this complex number with b.
	 */
	public Complex add(Complex b) {
		return new Complex(this.getReal() + b.getReal(), this.getImag() + b.getImag());
	}

	/**
	 * Calculates -z.
	 *
	 * @return The complex number -z = -x-iy
	 */
	public Complex negate() {
		return new Complex(-this.x, -this.y);
	}

	/**
	 * Subtracts a complex number from this one.
	 *
	 * @param b The complex number to subtract from this one.
	 * @return The difference of this complex number and b.
	 */
	public Complex sub(Complex b) {
		return this.add(b.negate());
	}

	/**
	 * Multiplies this complex number by a constant.
	 *
	 * @param alpha The constant to multiply by.
	 * @return The product of alpha with z.
	 */
	public Complex multiply(double alpha) {
		return new Complex(alpha * this.x, alpha * this.y);
	}

	/**
	 * Multiplies this complex number by another complex number.
	 *
	 * @param b The complex number to multiply by.
	 * @return The product of b with z.
	 */
	public Complex multiply(Complex b) {
		double x1 = this.getReal();
		double y1 = this.getImag();
		double x2 = b.getReal();
		double y2 = b.getImag();

		return new Complex(x1 * x2 - y1 * y2, x2 * y1 + x1 * y2);
	}

	/**
	 * Divide this complex number by another.
	 *
	 * @param b The complex number to divide by.
	 * @return The division z/a.
	 */
	public Complex divide(Complex b) {
		return this.multiply(b.conjugate()).multiply(1.0 / b.abs2());
	}

	public boolean equals(Object obj) {
		if (obj instanceof Complex) {
			Complex other = (Complex) obj;
			return this.getReal() == other.getReal() && this.getImag() == other.getImag();
		} else {
			return false;
		}
	}

	// ========================================================
	// Tester function.
	// ========================================================

	public static void main(String[] args) {
		// Test all of the constructor functions. You can add anything you
		// want to this if you think it's necessary!
		Complex A = new Complex(2.0, 2.0);
		Complex B = new Complex(1.0);
		Complex C = new Complex();

		// Test the converters by printing out A, B and C.
		System.out.println("Constructor test:");
		System.out.println("A = " + A.toString());
		System.out.println("B = " + B.toString());
		System.out.println("C = " + C.toString());

		// Test accessor/mutators.
		System.out.println();
		System.out.println("Setting imag(C) = real(C) + 1:");
		C.setImag(C.getReal() + 1.0);
		System.out.println("C = " + C.toString());

		// Now test operations for both complex and real numbers, just to make
		// sure something isn't wrong.
		System.out.println();
		System.out.println("Testing operators:");
		System.out.println("abs(A)   = " + A.abs());
		System.out.println("abs2(A)  = " + A.abs2());
		System.out.println("abs(B)   = " + B.abs());
		System.out.println("abs2(B)  = " + B.abs2());
		System.out.println("conj(A)  = " + A.conjugate());
		System.out.println("conj(B)  = " + B.conjugate());
		System.out.println("neg(A)   = " + A.negate());
		System.out.println("neg(B)   = " + B.negate());
		System.out.println("A+B      = " + A.add(B));
		System.out.println("A+C      = " + A.add(C));
		System.out.println("A-B      = " + A.sub(B));
		System.out.println("A-C      = " + A.sub(C));
		System.out.println("B-C      = " + B.sub(C));
		System.out.println("2*A      = " + A.multiply(2.0));
		System.out.println("A*C      = " + A.multiply(C));
		System.out.println("B*C      = " + B.multiply(C));
		System.out.println("A*A      = " + A.multiply(A));
		System.out.println("A/B      = " + A.divide(B));
		System.out.println("A/C      = " + A.divide(C));
		System.out.println("A/A      = " + A.divide(A));

		// And to finish off, a couple of examples of how you can chain the
		// different operations together.
		System.out.println();
		System.out.println("Chained operators:");
		System.out.println("B*(A+C)    = " + B.multiply(A.add(C)));
		System.out.println("-A+B*C     = " + A.negate().add(B.multiply(C)));
	}
}
