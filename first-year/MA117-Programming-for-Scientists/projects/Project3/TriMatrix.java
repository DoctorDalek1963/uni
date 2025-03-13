/*
 * PROJECT III: TriMatrix.java
 *
 * This file contains a template for the class TriMatrix. Not all methods are
 * implemented and they do not have placeholder return statements. Make sure
 * you have carefully read the project formulation before starting to work
 * on this file. You will also need to have completed the Matrix class.
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

public class TriMatrix extends Matrix {
	/**
	 * An array holding the diagonal elements of the matrix.
	 */
	private double[] diagonal;

	/**
	 * An array holding the upper-diagonal elements of the matrix.
	 */
	private double[] upperDiagonal;

	/**
	 * An array holding the lower-diagonal elements of the matrix.
	 */
	private double[] lowerDiagonal;

	/**
	 * Constructor function: should initialise iDim and jDim through the Matrix
	 * constructor and set up the values array.
	 *
	 * @param dimension  The dimension of the array.
	 */
	public TriMatrix(int dimension) {
		super(dimension, dimension);
		this.diagonal = new double[dimension];
		this.upperDiagonal = new double[dimension - 1];
		this.lowerDiagonal = new double[dimension - 1];
	}

	/**
	 * Getter function: return the (i,j)'th entry of the matrix.
	 *
	 * @param i  The location in the first co-ordinate.
	 * @param j  The location in the second co-ordinate.
	 * @return   The (i,j)'th entry of the matrix.
	 */
	public double getIJ(int i, int j) {
		// TODO
		return 0.0;
	}

	/**
	 * Setter function: set the (i,j)'th entry of the data array.
	 *
	 * @param i      The location in the first co-ordinate.
	 * @param j      The location in the second co-ordinate.
	 * @param value  The value to set the (i,j)'th entry to.
	 */
	public void setIJ(int i, int j, double value) {
		// TODO
	}

	/**
	 * Return the determinant of this matrix.
	 *
	 * @return The determinant of the matrix.
	 */
	public double determinant() {
		// TODO
		return 0.0;
	}

	/**
	 * Returns the LU decomposition of this matrix. See the formulation for a
	 * more detailed description.
	 *
	 * @return The LU decomposition of this matrix.
	 */
	public TriMatrix LUdecomp() {
		// TODO
		return this;
	}

	/**
	 * Add the matrix to another second matrix.
	 *
	 * @param other  The Matrix to add to this matrix.
	 * @return       The sum of this matrix with the second matrix.
	 */
	public Matrix add(Matrix other) {
		// TODO
		return this;
	}

	/**
	 * Multiply the matrix by another matrix A. This is a _left_ product,
	 * i.e. if this matrix is called B then it calculates the product BA.
	 *
	 * @param A  The Matrix to multiply by.
	 * @return   The product of this matrix with the matrix A.
	 */
	public Matrix multiply(Matrix A) {
		// TODO
		return this;
	}

	/**
	 * Multiply the matrix by a scalar.
	 *
	 * @param scalar  The scalar to multiply the matrix by.
	 * @return        The product of this matrix with the scalar.
	 */
	public Matrix multiply(double scalar) {
		// TODO
		return this;
	}

	/**
	 * Populates the matrix with random numbers which are uniformly
	 * distributed between 0 and 1.
	 */
	public void random() {
		// TODO
	}

	/*
	 * Your tester function should go here.
	 */
	public static void main(String[] args) {
		// Test your class implementation using this method.
	}
}
