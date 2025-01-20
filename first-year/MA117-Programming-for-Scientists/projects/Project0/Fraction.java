/*
 * FRACTION
 *
 * This file is a SKELETON file. That means that a number of functions have
 * not been implemented. Your task is to complete the class by filling in the
 * missing methods.
 *
 * First an important note. This file contains names of public methods
 * which you should NOT change. Each method is already "preprogrammed" so
 * the interface (the functions, their parameters and what should be
 * returned) is fixed and you should NOT change it. Every function has a
 * description, which outlines:
 *
 * - The purpose of the function, and intended use;
 * - The parameters it accepts (if any) indicated by @param;
 * - What it returns (if anything) indicated by @return.
 *
 * Tasks:
 *
 * 5) The BOSS system will perform specific tests with your class. It will
 *    test: constructors, operators and simplification to irreducible form. So
 *    even if you do not complete all of the methods you will obtain some
 *    credit if others are completed satisfactorily.
 *
 * NAME: Dyson Dyson
 * UNIVERSITY ID: 5503449
 * DEPARTMENT: Mathematics
 */

/**
 * Classname: Fraction
 * Description: This class implements a new type for fractions
 *              and corresponding arithmetic.
 *
 * @author : Original: K.N. King, modified by D. Moxey and P. Plechac
 *           for use in the course MA117
 * @version: history: v1.1
 */

public class Fraction {
	// ============================================================
	// Instance variables
	// ============================================================

	/**
	 * The numerator of the fraction.
	 */
	private int numerator;
	/**
	 * The denominator of the fraction.
	 */
	private int denominator;

	// ============================================================
	// Constructors
	// ============================================================

	/**
	 * Constructor which takes coefficients explicity.
	 *
	 * Behaviour: Constructs a fraction with the specified numerator and
	 *            denominator. Remember that your fraction should *always* be
	 *            stored in irreducible form.
	 *
	 * @param num   The numerator
	 * @param denom The denominator
	 */
	public Fraction(int num, int denom) {
		this.numerator = num;
		this.denominator = denom;
		this.reduce();
	}

	/**
	 * Constructor which takes coefficients explicity.
	 *
	 * Behaviour: Constructs a fraction which represents an integer: set the
	 *            specified numerator and set denominator to 1.
	 *
	 * @param num The numerator
	 */
	public Fraction(int num) {
		this(num, 1);
	}

	/**
	 * Default constructor.
	 *
	 * Behaviour: Constructs a fraction and set the default value to 0;
	 *            i.e. numerator = 0 and denominator = 1
	 */
	public Fraction() {
		this(0, 1);
	}


	// ==============================================================
	// Convertors
	//
	// These functions will convert the Fraction to other data types.
	// ==============================================================

	/**
	 * Convert the fraction to the floating point representation using double
	 * precision.
	 *
	 * Behaviour: Converts this fraction into a double.
	 *
	 * @return    A double value obtained by dividing the fraction's numerator
	 *            by its denominator.
	 */
	public double toDouble() {
		return (double) numerator / (double) denominator;
	}


	/**
	 * Convert the fraction to the floating point representation using the
	 * single precision.
	 *
	 * Behaviour: Converts this fraction into a float value.
	 *
	 * @return    A float value obtained by dividing the fraction's numerator by
	 *            its denominator
	 */
	public float toFloat() {
		return (float) numerator / (float) denominator;
	}

	/**
	 * Convert the fraction to a string.
	 *
	 * Behaviour: Converts this fraction into a string
	 *
	 * @return    A string of the form "num/denom". If the denominator is 1,
	 *            returns a string containing *only* the numerator.
	 */
	public String toString() {
		if (denominator == 1) {
			return String.format("%d", numerator);
		} else {
			return String.format("%d/%d", numerator, denominator);
		}
	}


	// ============================================================
	// Accessors and mutator methods (getters and setters)
	// ============================================================

	/**
	 * Get denominator.
	 *
	 * Behaviour: Returns the denominator of this fraction.
	 *
	 * @return    The denominator of this fraction.
	 */
	public int getDenominator() {
		return denominator;
	}

	/**
	 * Get numerator.
	 *
	 * Behaviour: Returns the numerator of this fraction.
	 *
	 * @return    The numerator of this fraction.
	 */
	public int getNumerator() {
		return numerator;
	}

	//============================================================
	// Operations with fractions - Core methods
	//============================================================

	/**
	 * Addition operation.
	 *
	 * Behaviour: Adds this fraction to a supplied fraction.
	 *
	 * @param f  The fraction to be added.
	 * @return   The sum of this fraction and f.
	 */
	public Fraction add(Fraction f) {
		int num   = numerator * f.denominator + f.numerator * denominator;
		int denom = denominator * f.denominator;

		return new Fraction(num, denom);
	}

	/**
	 * Subtraction operation.
	 *
	 * Behaviour: Subtracts a fraction from this fraction.
	 *
	 * @param f   The fraction to be subtracted.
	 * @return    The difference between this fraction and f.
	 *
	 */
	public Fraction subtract(Fraction f) {
		int num   = numerator * f.denominator - f.numerator * denominator;
		int denom = denominator * f.denominator;

		return new Fraction(num, denom);
	}

	/**
	 * Division.
	 *
	 * Behaviour: Divides this fraction by another fraction.
	 *
	 * @param f   The fraction to be used as a divisor.
	 * @return    The quotient of this fraction and f.
	 */
	public Fraction divide(Fraction f) {
		return this.multiply(f.reciprocal());
	}

	/**
	 * Multiplication.
	 *
	 * Behaviour: Multiplies this fraction and another fraction.
	 *
	 * @param f   The fraction to be multiplied.
	 * @return    The product of this fraction and f.
	 */
	public Fraction multiply(Fraction f) {
		return new Fraction(numerator * f.numerator, denominator * f.denominator);
	}

	/**
	 * Find the GCD of this fraction's numerator and denominator.
	 *
	 * @return The GCD of this fraction's numerator and denominator.
	 */
	private int gcd() {
		// This feels hacky, but Java doesn't allow functions outside of classes
		class GcdCalc {
			public static int gcd(int a, int b) {
				if (b == 0) {
					return a;
				} else {
					return GcdCalc.gcd(b, a % b);
				}
			}
		}

		return GcdCalc.gcd(numerator, denominator);
	}

	/**
	 * Reduce the fraction to its simplest form.
	 */
	private void reduce() {
		int gcd = this.gcd();
		this.numerator /= gcd;
		this.denominator /= gcd;

		if (this.denominator < 0) {
			this.numerator *= -1;
			this.denominator *= -1;
		}
	}

	private Fraction reciprocal() {
		return new Fraction(this.denominator, this.numerator);
	}

	// ======================================================================
	// END OF USER MODIFIABLE CODE
	//
	// DO NOT CHANGE ANY FUNCTIONS IN THE SECTION BELOW; THEY ARE NEEDED FOR
	// THE AUTOMATED MARKING SYSTEM. YOUR CODE CANNOT BE MARKED WITHOUT IT!
	// ======================================================================

	/**
	 * Compare two fractions.
	 *
	 * @param  q  The fraction to compare with.
	 * @return    true if q is Fraction equal to this fraction .
	 */
	public boolean sameAs(Fraction q) {
		return (numerator   == q.getNumerator() &&
				denominator == q.getDenominator());
	}
}

