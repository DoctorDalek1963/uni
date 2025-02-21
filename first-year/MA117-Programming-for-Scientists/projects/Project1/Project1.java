/*
 * PROJECT I: Project1.java
 *
 * As in project 0, this file - and the others you downloaded - form a
 * template which should be modified to be fully functional.
 *
 * This file is the *last* file you should implement, as it depends on both
 * Point and Circle. Thus your tasks are to:
 *
 * 1) Make sure you have carefully read the project formulation. It contains
 *    the descriptions of all of the functions and variables below.
 * 2) Write the class Point.
 * 3) Write the class Circle
 * 4) Write this class, Project1. The results() method will perform the tasks
 *    laid out in the project formulation.
 */

import java.io.*;
import java.util.*;

public class Project1 {
	// -----------------------------------------------------------------------
	// Do not modify the names or types of the instance variables below! This
	// is where you will store the results generated in the Results() function.
	// -----------------------------------------------------------------------
	public int circleCounter; // Number of non-singular circles in the file.
	public double[] aabb; // The bounding rectangle for the 10th and
	// 20th circles
	public double Smax; // Area of the largest circle (by area).
	public double Smin; // Area of the smallest circle (by area).
	public double areaAverage; // Average area of the circles.
	public double areaSD; // Standard deviation of area of the circles.
	public double areaMedian; // Median of the area.
	public int stamp = 220209;

	// -----------------------------------------------------------------------
	// You should implement - but *not* change the types, names or parameters of
	// the variables and functions below.
	// -----------------------------------------------------------------------

	/** Default constructor for Project1. You should leave it empty. */
	public Project1() {
		// This method is complete.
	}

	/**
	 * Results function. It should open the file called fileName (using Scanner), and from it generate
	 * the statistics outlined in the project formulation. These are then placed in the instance
	 * variables above.
	 *
	 * @param fileName The name of the file containing the circle data.
	 */
	public void results(String fileName) throws FileNotFoundException {
		ArrayList<Circle> circleList = new ArrayList<Circle>();

		// This try block will make sure the scanner closes properly
		try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
			while (scanner.hasNext()) {
				double x = scanner.nextDouble();
				double y = scanner.nextDouble();
				double radius = scanner.nextDouble();

				Circle circle = new Circle(x, y, radius);
				if (!circle.isSingular()) {
					circleList.add(new Circle(x, y, radius));
				}
			}
		}

		Circle[] circleArray = circleList.toArray(new Circle[circleList.size()]);

		this.circleCounter = circleArray.length;
		this.aabb = this.calculateAABB(new Circle[] {circleArray[9], circleArray[19]});

		// Min and max area
		try {
			this.Smax = circleList.stream()
			                      .map(c -> c.area())
			                      .max(Double::compare)
			                      .get();

			this.Smin = circleList.stream()
			                      .map(c -> c.area())
			                      .min(Double::compare)
			                      .get();
		} catch (NoSuchElementException e) {
			System.err.println("We should always have maximum and minimum areas");
		}

		this.areaAverage = this.averageCircleArea(circleArray);
		this.areaSD = this.areaStandardDeviation(circleArray);

		// Median
		double[] sortedAreas = Arrays.stream(circleArray)
		                             .mapToDouble(c -> c.area())
		                             .sorted()
		                             .toArray();

		if (sortedAreas.length % 2 == 0) {
			double a = sortedAreas[sortedAreas.length / 2 - 1];
			double b = sortedAreas[sortedAreas.length / 2];
			this.areaMedian = (a + b) / 2;
		} else {
			this.areaMedian = sortedAreas[sortedAreas.length / 2];
		}
	}

	/**
	 * A function to calculate the avarage area of circles in the array provided. This array may
	 * contain 0 or more circles.
	 *
	 * @param circles An array of Circles
	 */
	public double averageCircleArea(Circle[] circles) {
		double totalArea = Arrays.stream(circles)
		                         .filter(c -> !c.isSingular())
		                         .mapToDouble(c -> c.area())
		                         .sum();

		return totalArea / circles.length;
	}

	/**
	 * A function to calculate the standard deviation of areas in the circles in the array provided.
	 * This array may contain 0 or more circles.
	 *
	 * @param circles An array of Circles
	 */
	public double areaStandardDeviation(Circle[] circles) {
		double mu = this.averageCircleArea(circles);

		double sumOfSquaredAreas = Arrays.stream(circles)
		                                 .filter(c -> !c.isSingular())
		                                 .mapToDouble(c -> c.area() * c.area())
		                                 .sum();

		return Math.sqrt(sumOfSquaredAreas / circles.length - mu * mu);
	}

	/**
	 * Returns 4 values in an array [X1,Y1,X2,Y2] that define the rectangle that surrounds the array
	 * of circles given, as set out in the project formulation.
	 * ****************************************************** IMPORTANT REMARK
	 * ******************************************************* This method can take any number of
	 * circles, and not just 2 circles. As indicated below, it must return an array of doubles that
	 * define the bottom left and top right of a rectangle that surrounds ALL the given circles. In
	 * case no circle is provided to calculateAABB() or in case all the circles are singular, then the
	 * (default) bouding rectangle should be the largest possible we can define using Double.MAX_VALUE
	 * and Double.MIN_VALUE
	 *
	 * @param circles An array of Circles
	 * @return An array of doubles [X1,Y1,X2,Y2] that define the bounding rectangle with the origin
	 *     (bottom left) at [X1,Y1] and opposite corner (top right) at [X2,Y2]
	 */
	public double[] calculateAABB(Circle[] circles) {
		try {
			double x1 = Arrays.stream(circles)
			                  .filter(c -> !c.isSingular())
			                  .map(c -> c.getCentre().getX() - c.getRadius())
			                  .min(Double::compare)
			                  .get();
			double y1 = Arrays.stream(circles)
			                  .filter(c -> !c.isSingular())
			                  .map(c -> c.getCentre().getY() - c.getRadius())
			                  .min(Double::compare)
			                  .get();

			double x2 = Arrays.stream(circles)
			                  .filter(c -> !c.isSingular())
			                  .map(c -> c.getCentre().getX() + c.getRadius())
			                  .max(Double::compare)
			                  .get();
			double y2 = Arrays.stream(circles)
			                  .filter(c -> !c.isSingular())
			                  .map(c -> c.getCentre().getY() + c.getRadius())
			                  .max(Double::compare)
			                  .get();

			return new double[] {x1, y1, x2, y2};
		} catch (NoSuchElementException e) {
			// If we don't have maxima or minima, return the largest box
			return new double[] {Double.MIN_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Double.MAX_VALUE};
		}
	}

	// =======================================================
	// Tester - tests methods defined in this class
	// =======================================================

	/**
	 * Your tester function should go here (see week 14 lecture notes if you're confused). It is not
	 * tested by BOSS, but you should still implement it in a sensible fashion.
	 */
	public static void main(String args[]) throws FileNotFoundException {
		Project1 p = new Project1();
		p.results("student.data");
		System.out.println(p.circleCounter);
		System.out.println(Arrays.toString(p.aabb));
		System.out.println(p.Smin);
		System.out.println(p.Smax);
		System.out.println(p.areaAverage);
		System.out.println(p.areaSD);
		System.out.println(p.areaMedian);
	}
}
