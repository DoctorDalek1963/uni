/*
 * PROJECT III: Project3.java
 *
 * This file contains a template for the class Project3. None of methods are
 * implemented and they do not have placeholder return statements. Make sure
 * you have carefully read the project formulation before starting to work
 * on this file. You will also need to have completed the Matrix class, as
 * well as GeneralMatrix and TriMatrix.
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

public class Project3 {
	/**
	 * Calculates the variance of the distribution defined by the determinant
	 * of a random matrix. See the formulation for a detailed description.
	 *
	 * @param matrix      The matrix object that will be filled with random
	 *                    samples.
	 * @param nSamp       The number of samples to generate when calculating
	 *                    the variance.
	 * @return            The variance of the distribution.
	 */
	public static double matVariance(Matrix matrix, int nSamp) {
		double sumDet = 0.0;
		double sumDetSquared = 0.0;

		for (int i = 0; i < nSamp; i++) {
			matrix.random();
			double det = matrix.determinant();
			sumDet += det;
			sumDetSquared += det * det;
		}

		double sumDetOverNSamp = sumDet / nSamp;

		return (sumDetSquared / nSamp - (sumDetOverNSamp * sumDetOverNSamp));
	}

	/**
	 * This function should calculate the variances of matrices for matrices
	 * of size 2 <= n <= 50 and print the results to the output. See the
	 * formulation for more detail.
	 */
	public static void main(String[] args) {
		for (int n = 2; n <= 50; n++) {
			double varGen = matVariance(new GeneralMatrix(n, n), 20_000);
			double varTri = matVariance(new TriMatrix(n), 200_000);

			System.out.printf("%d %.15e %.15e\n", n, varGen, varTri);
		}
	}
}
