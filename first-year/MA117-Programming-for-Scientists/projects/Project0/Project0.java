/*
 * PROJECT 0
 *
 * This file is a SKELETON file. It has a SMALL set of test to check some features of
 * the FRACTIONS class.  The BOSS system will test ALL parts of your solution.
 * You must create your own tests.
 *
 * NAME: Dyson Dyson
 * UNIVERSITY ID: 5503449
 * DEPARTMENT: Mathematics
 */

/**
 * Classname: Project0
 * Description: This class utlises a new type for fractions
 * and corresponding arithmetic.
 *
 * @author : A.Hague for use in the course MA117
 * @version: history: v1.0
 */

public class Project0 {
	// Simple tester function.
	public static void main(String[] args) {
		try {
			assert false;
			System.out.println("ERROR! You must enable assertions to do the tests");
			System.out.println("Please rerun with `java -enableassertions`");
			return;
		} catch (AssertionError e) {
			// Continue as normal
		}

		Fraction a = new Fraction(-8, 16);
		Fraction b = new Fraction(3, 12);
		Fraction c = new Fraction(2);
		Fraction d = new Fraction(867, 13);

		Fraction z = new Fraction(13524632, 0);

		assert a.toString().equals("-1/2");
		assert b.toString().equals("1/4");
		assert c.toString().equals("2");
		assert d.toString().equals("867/13");
		assert z.toString().equals("1/0");

		assert Float.toString(a.toFloat()).equals("-0.5");
		assert Float.toString(b.toFloat()).equals("0.25");
		assert Float.toString(c.toFloat()).equals("2.0");
		assert Float.toString(d.toFloat()).equals("66.69231");
		assert Float.toString(z.toFloat()).equals("Infinity");

		assert Double.toString(a.toDouble()).equals("-0.5");
		assert Double.toString(b.toDouble()).equals("0.25");
		assert Double.toString(c.toDouble()).equals("2.0");
		assert Double.toString(d.toDouble()).equals("66.6923076923077");
		assert Double.toString(z.toDouble()).equals("Infinity");

		assert a.getNumerator() == -1;
		assert b.getNumerator() == 1;
		assert c.getNumerator() == 2;
		assert d.getNumerator() == 867;
		assert z.getNumerator() == 1;

		assert a.getDenominator() == 2;
		assert b.getDenominator() == 4;
		assert c.getDenominator() == 1;
		assert d.getDenominator() == 13;
		assert z.getDenominator() == 0;

		assert a.add(b).sameAs(new Fraction(-1, 4));
		assert a.add(c).sameAs(new Fraction(3, 2));
		assert a.add(d).sameAs(new Fraction(1721, 26));
		assert a.add(z).sameAs(new Fraction(1, 0));
		assert b.add(c).sameAs(new Fraction(9, 4));
		assert b.add(d).sameAs(new Fraction(3481, 52));
		assert b.add(z).sameAs(new Fraction(1, 0));
		assert c.add(d).sameAs(new Fraction(893, 13));
		assert c.add(z).sameAs(new Fraction(1, 0));
		assert d.add(z).sameAs(new Fraction(1, 0));

		assert a.subtract(b).sameAs(a.add(b.multiply(new Fraction(-1))));
		assert a.subtract(c).sameAs(a.add(c.multiply(new Fraction(-1))));
		assert a.subtract(d).sameAs(a.add(d.multiply(new Fraction(-1))));
		assert a.subtract(z).sameAs(a.add(z.multiply(new Fraction(-1))));
		assert b.subtract(c).sameAs(b.add(c.multiply(new Fraction(-1))));
		assert b.subtract(d).sameAs(b.add(d.multiply(new Fraction(-1))));
		assert b.subtract(z).sameAs(b.add(z.multiply(new Fraction(-1))));
		assert c.subtract(d).sameAs(c.add(d.multiply(new Fraction(-1))));
		assert c.subtract(z).sameAs(c.add(z.multiply(new Fraction(-1))));
		assert d.subtract(z).sameAs(d.add(z.multiply(new Fraction(-1))));

		assert a.toString().equals("-1/2");
		assert b.toString().equals("1/4");
		assert c.toString().equals("2");
		assert d.toString().equals("867/13");
		assert z.toString().equals("1/0");

		assert a.multiply(b).sameAs(new Fraction(-1, 8));
		assert a.multiply(c).sameAs(new Fraction(-1));
		assert a.multiply(d).sameAs(new Fraction(-867, 26));
		assert a.multiply(z).sameAs(new Fraction(1, 0));
		assert b.multiply(c).sameAs(new Fraction(1, 2));
		assert b.multiply(d).sameAs(new Fraction(867, 52));
		assert b.multiply(z).sameAs(new Fraction(1, 0));
		assert c.multiply(d).sameAs(new Fraction(1734, 13));
		assert c.multiply(z).sameAs(new Fraction(1, 0));
		assert d.multiply(z).sameAs(new Fraction(1, 0));

		assert a.divide(b).sameAs(new Fraction(-2));
		assert a.divide(c).sameAs(new Fraction(-1, 4));
		assert a.divide(d).sameAs(new Fraction(-13, 1734));
		assert a.divide(z).sameAs(new Fraction(0));
		assert b.divide(c).sameAs(new Fraction(1, 8));
		assert b.divide(d).sameAs(new Fraction(13, 3468));
		assert b.divide(z).sameAs(new Fraction(0));
		assert c.divide(d).sameAs(new Fraction(26, 867));
		assert c.divide(z).sameAs(new Fraction(0));
		assert d.divide(z).sameAs(new Fraction(0));

		// Really I should do all the divisions the other way as well to
		// be completely thorough, but I'm not going to do that

		System.out.println("All tests passed!");
	}
}
