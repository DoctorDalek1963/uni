/*
 * PROJECT III: GeneralMatrix.java
 *
 * This file contains a template for the class GeneralMatrix. Not all methods
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

import java.util.Arrays;
import java.util.Random;

public class GeneralMatrix extends Matrix {
	/**
	 * This instance variable stores the elements of the matrix.
	 */
	private double[][] values;

	/**
	 * Constructor function: should initialise iDim and jDim through the Matrix
	 * constructor and set up the data array.
	 *
	 * @param firstDim   The first dimension of the array.
	 * @param secondDim  The second dimension of the array.
	 */
	public GeneralMatrix(int firstDim, int secondDim) {
		super(firstDim, secondDim);
		this.values = new double[firstDim][secondDim];
	}

	/**
	 * Constructor function. This is a copy constructor; it should create a
	 * copy of the second matrix.
	 *
	 * @param other  The matrix to create a copy of.
	 */
	public GeneralMatrix(Matrix other) {
		this(other.iDim, other.jDim);

		for (int i = 0; i < other.iDim; i++)
			for (int j = 0; j < other.jDim; j++)
				this.setIJ(i, j, other.getIJ(i, j));
	}

	/**
	 * Getter function: return the (i,j)'th entry of the matrix.
	 *
	 * @param i  The location in the first co-ordinate.
	 * @param j  The location in the second co-ordinate.
	 * @return   The (i,j)'th entry of the matrix.
	 */
	public double getIJ(int i, int j) throws MatrixException {
		this.validateIndex(i, j);
		return this.values[i][j];
	}

	/**
	 * Setter function: set the (i,j)'th entry of the values array.
	 *
	 * @param i      The location in the first co-ordinate.
	 * @param j      The location in the second co-ordinate.
	 * @param value  The value to set the (i,j)'th entry to.
	 */
	public void setIJ(int i, int j, double value) throws MatrixException {
		this.validateIndex(i, j);
		this.values[i][j] = value;
	}

	/**
	 * Return the determinant of this matrix.
	 *
	 * @return The determinant of the matrix.
	 */
	public double determinant() throws MatrixException {
		if (this.iDim != this.jDim)
			throw new MatrixException("Can only take determinants of square matrices");

		// We want to decompose the matrix into L and U, lower and upper triangular
		// matrices. Then the determinant of this matrix is the product of the
		// determinants of L and U. The LUdecomp method provides gives a version of L
		// with ones all down the leading diagonal, packed with U. So the $\det L = 1$
		// and $\det U$ is the product of the elements on the leading diagonal of LUdecomp.
		double[] sign = {1.0};
		GeneralMatrix decomp = this.LUdecomp(sign);

		double det = sign[0];

		for (int i = 0; i < this.iDim; i++)
			det *= decomp.getIJ(i, i);

		return det;
	}

	/**
	 * Add the matrix to another second matrix.
	 *
	 * @param other  The Matrix to add to this matrix.
	 * @return       The sum of this matrix with the second matrix.
	 */
	public Matrix add(Matrix other) throws MatrixException {
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
		this.validateMultiplyDimensions(A);

		GeneralMatrix m = new GeneralMatrix(this.iDim, A.jDim);

		double midLen = this.jDim;

		for (int i = 0; i < m.iDim; i++) {
			for (int j = 0; j < m.jDim; j++) {
				double x = 0.0;

				for (int k = 0; k < midLen; k++)
					x += this.getIJ(i, k) * A.getIJ(k, j);

				m.setIJ(i, j, x);
			}
		}

		return m;
	}

	/**
	 * Multiply the matrix by a scalar.
	 *
	 * @param scalar  The scalar to multiply the matrix by.
	 * @return        The product of this matrix with the scalar.
	 */
	public Matrix multiply(double scalar) {
		Matrix m = new GeneralMatrix(this.iDim, this.jDim);

		for (int i = 0; i < this.iDim; i++)
			for (int j = 0; j < this.jDim; j++)
				m.setIJ(i, j, this.getIJ(i, j) * scalar);

		return m;
	}

	/**
	 * Populates the matrix with random numbers which are uniformly
	 * distributed between 0 and 1.
	 */
	public void random() {
		Random rand = new Random();

		for (int i = 0; i < this.iDim; i++)
			for (int j = 0; j < this.jDim; j++)
				this.setIJ(i, j, rand.nextDouble());
	}

	/**
	 * Returns the LU decomposition of this matrix; i.e. two matrices L and U
	 * so that A = LU, where L is lower-diagonal and U is upper-diagonal.
	 *
	 * On exit, decomp returns the two matrices in a single matrix by packing
	 * both matrices as follows:
	 *
	 * [ u_11 u_12 u_13 u_14 ]
	 * [ l_21 u_22 u_23 u_24 ]
	 * [ l_31 l_32 u_33 u_34 ]
	 * [ l_41 l_42 l_43 u_44 ]
	 *
	 * where u_ij are the elements of U and l_ij are the elements of l. When
	 * calculating the determinant you will need to multiply by the value of
	 * sign[0] calculated by the function.
	 *
	 * If the matrix is singular, then the routine throws a MatrixException.
	 * In this case the string from the exception's getMessage() will contain
	 * "singular"
	 *
	 * This method is an adaptation of the one found in the book "Numerical
	 * Recipies in C" (see online for more details).
	 *
	 * @param sign  An array of length 1. On exit, the value contained in here
	 *              will either be 1 or -1, which you can use to calculate the
	 *              correct sign on the determinant.
	 * @return      The LU decomposition of the matrix.
	 */
	public GeneralMatrix LUdecomp(double[] sign) {
		// This method is complete. You should not even attempt to change it!!
		if (jDim != iDim)
			throw new MatrixException("Matrix is not square");

		if (sign.length != 1)
			throw new MatrixException("d should be of length 1");

		int i, j, k;
		int imax = -10;
		double big, dum, sum, temp;
		double[] vv = new double[jDim];
		GeneralMatrix a = new GeneralMatrix(this);

		sign[0] = 1.0;

		for (i = 1; i <= jDim; i++) {
			big = 0.0;

			for (j = 1; j <= jDim; j++)
				if ((temp = Math.abs(a.values[i-1][j-1])) > big)
					big = temp;

			if (big == 0.0)
				throw new MatrixException("Matrix is singular");

			vv[i-1] = 1.0/big;
		}

		for (j = 1; j <= jDim; j++) {
			for (i = 1; i < j; i++) {
				sum = a.values[i-1][j-1];

				for (k = 1; k < i; k++)
					sum -= a.values[i-1][k-1]*a.values[k-1][j-1];

				a.values[i-1][j-1] = sum;
			}

			big = 0.0;

			for (i = j; i <= jDim; i++) {
				sum = a.values[i-1][j-1];

				for (k = 1; k < j; k++)
					sum -= a.values[i-1][k-1]*a.values[k-1][j-1];

				a.values[i-1][j-1] = sum;

				if ((dum = vv[i-1]*Math.abs(sum)) >= big) {
					big = dum;
					imax = i;
				}
			}

			if (j != imax) {
				for (k = 1; k <= jDim; k++) {
					dum = a.values[imax-1][k-1];
					a.values[imax-1][k-1] = a.values[j-1][k-1];
					a.values[j-1][k-1] = dum;
				}

				sign[0] = -sign[0];
				vv[imax-1] = vv[j-1];
			}

			if (a.values[j-1][j-1] == 0.0)
				a.values[j-1][j-1] = 1.0e-20;

			if (j != jDim) {
				dum = 1.0/a.values[j-1][j-1];

				for (i = j+1; i <= jDim; i++)
					a.values[i-1][j-1] *= dum;
			}
		}

		return a;
	}

	/*
	 * Your tester function should go here.
	 */
	public static void main(String[] args) {
		GeneralMatrix m = new GeneralMatrix(20, 10);
		m.random();
		System.out.println(m.toString());
	}
}
