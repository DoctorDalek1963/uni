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

import java.util.Random;

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
		this.validateIndex(i, j);

		if (i == j) {
			return this.diagonal[i];
		} else if (i == j + 1) {
			return this.lowerDiagonal[j];
		} else if (j == i + 1) {
			return this.upperDiagonal[i];
		} else {
			return 0.0;
		}
	}

	/**
	 * Setter function: set the (i,j)'th entry of the data array.
	 *
	 * @param i      The location in the first co-ordinate.
	 * @param j      The location in the second co-ordinate.
	 * @param value  The value to set the (i,j)'th entry to.
	 */
	public void setIJ(int i, int j, double value) {
		this.validateIndex(i, j);

		if (i == j) {
			this.diagonal[i] = value;
		} else if (i == j + 1) {
			this.lowerDiagonal[j] = value;
		} else if (j == i + 1) {
			this.upperDiagonal[i] = value;
		}
	}

	/**
	 * Return the determinant of this matrix.
	 *
	 * @return The determinant of the matrix.
	 */
	public double determinant() {
		// See GeneralMatrix.determinant
		TriMatrix decomp = this.LUdecomp();
		double det = 1.0;

		for (int i = 0; i < this.iDim; i++)
			det *= decomp.getIJ(i, i);

		return det;
	}

	/**
	 * Returns the LU decomposition of this matrix. See the formulation for a
	 * more detailed description.
	 *
	 * @return The LU decomposition of this matrix.
	 */
	public TriMatrix LUdecomp() {
		TriMatrix m = new TriMatrix(this.iDim);

		for (int i = 0; i < this.iDim - 1; i++)
			// u_i' = u_i
			m.upperDiagonal[i] = this.upperDiagonal[i];

		m.diagonal[0] = this.diagonal[0];

		for (int i = 1; i < this.iDim; i++) {
			// l_i' = l_i / d_{i-1}' (remember lowerDiagonal is indexed with an offset of 1)
			double liPrime = this.lowerDiagonal[i - 1] / m.diagonal[i - 1];
			m.lowerDiagonal[i - 1] = liPrime;

			// d_i' = d_i - u_{i-1} l_i'
			m.diagonal[i] = this.diagonal[i] - this.upperDiagonal[i - 1] * liPrime;
		}

		return m;
	}

	/**
	 * Add the matrix to another second matrix.
	 *
	 * @param other  The Matrix to add to this matrix.
	 * @return       The sum of this matrix with the second matrix.
	 */
	public Matrix add(Matrix other) {
		if (this.iDim != other.iDim || this.jDim != other.jDim)
			throw new MatrixException("Can only add matrices of the same size");

		GeneralMatrix m = new GeneralMatrix(this.iDim, this.jDim);

		for (int i = 0; i < this.iDim; i++)
			for (int j = 0; j < this.jDim; j++)
				m.setIJ(i, j, this.getIJ(i, j) + other.getIJ(i, j));

		return m;
	}

	/**
	 * Multiply the matrix by another matrix A. This is a _left_ product,
	 * i.e. if this matrix is called B then it calculates the product BA.
	 *
	 * @param A  The Matrix to multiply by.
	 * @return   The product of this matrix with the matrix A.
	 */
	public Matrix multiply(Matrix A) {
		// Multiplying two tri-diagonal matrices doesn't always result in
		// a tri-diagonal matrix, so we have to be more general
		return new GeneralMatrix(this).multiply(new GeneralMatrix(A));
	}

	/**
	 * Multiply the matrix by a scalar.
	 *
	 * @param scalar  The scalar to multiply the matrix by.
	 * @return        The product of this matrix with the scalar.
	 */
	public Matrix multiply(double scalar) {
		Matrix m = new TriMatrix(this.iDim);

		for (int i = 0; i < this.iDim - 1; i++) {
			m.setIJ(i, i, this.getIJ(i, i) * scalar);
			m.setIJ(i + 1, i, this.getIJ(i + 1, i) * scalar);
			m.setIJ(i, i + 1, this.getIJ(i, i + 1) * scalar);
		}

		m.setIJ(this.iDim - 1, this.iDim - 1, this.getIJ(this.iDim, this.iDim) * scalar);

		return m;
	}

	/**
	 * Populates the matrix with random numbers which are uniformly
	 * distributed between 0 and 1.
	 */
	public void random() {
		Random rand = new Random();

		for (int i = 0; i < this.iDim - 1; i++) {
			this.setIJ(i, i, rand.nextDouble());
			this.setIJ(i + 1, i, rand.nextDouble());
			this.setIJ(i, i + 1, rand.nextDouble());
		}

		this.setIJ(this.iDim - 1, this.iDim - 1, rand.nextDouble());
	}

	/*
	 * Your tester function should go here.
	 */
	public static void main(String[] args) {
		TriMatrix m = new TriMatrix(20);
		m.random();
		System.out.println(m.toString());
	}
}
