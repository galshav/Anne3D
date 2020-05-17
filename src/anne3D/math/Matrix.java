package anne3D.math;

import java.lang.RuntimeException;
import java.util.Arrays;

public class Matrix {
	
	protected final int m_NumberOfRows;
	protected final int m_NumberOfColumns;
	protected final double[][] m_Data;
	
	public Matrix(final int numberOfRows, final int numberOfColumns) {
		if (numberOfRows    <= 0 ||
			numberOfColumns <= 0) {
			throw new RuntimeException("Invalid matrix dimensions.");
		}
		
		m_NumberOfRows = numberOfRows;
		m_NumberOfColumns = numberOfColumns;
		m_Data = new double[m_NumberOfRows][m_NumberOfColumns];
	}
	
	public Matrix(final double[][] data) {
		this(data.length, data[0].length);
		for (int i = 0; i < m_NumberOfRows; ++i) {
			for (int j = 0; j < m_NumberOfColumns; ++j) {
				m_Data[i][j] = data[i][j];
			}
		}
	}
	
	public double getValue(final int row, final int column) {
		if (row < 0 || column < 0) {
			throw new RuntimeException("Can not get matrix value with negative dimensions as arguments.");
		}
		
		if (row > m_NumberOfRows || column > m_NumberOfColumns) {
			throw new RuntimeException("Can not get matrix value, out of matrix dimension.");
		}
		
		return m_Data[row][column];
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		
		if (!(other instanceof Matrix)) {
			return false;
		}
		
		return Arrays.deepEquals(((Matrix)other).m_Data , this.m_Data);
	}
	
	public Matrix plus(final Matrix other) {
		if ((m_NumberOfRows    != other.m_NumberOfRows) ||
			(m_NumberOfColumns != other.m_NumberOfColumns)) {
			throw new RuntimeException("Can not add matrix with different dimensions.");
		}
		
		//T result = this.createSpecificMatrixType(other);
		Matrix result = new Matrix(m_NumberOfRows, m_NumberOfColumns);
		for (int i = 0; i < m_NumberOfRows; ++i) {
			for (int j = 0; j < m_NumberOfColumns; ++j) {
				result.m_Data[i][j] = m_Data[i][j] + other.m_Data[i][j];
			}
		}
		
		return result;
	}
	
	public Matrix minus(final Matrix other) {
		if ((m_NumberOfRows	   != other.m_NumberOfRows) ||
			(m_NumberOfColumns != other.m_NumberOfColumns)) {
				throw new RuntimeException("Can not add matrix with different dimensions.");
		}
		
		Matrix result = new Matrix(m_NumberOfRows, m_NumberOfColumns);
		for (int i = 0; i < m_NumberOfRows; ++i) {
			for (int j = 0; j < m_NumberOfColumns; ++j) {
				result.m_Data[i][j] = m_Data[i][j] - other.m_Data[i][j];
			}
		}
		
		return result;
	}
	
	public Matrix divide(final double factor) {
		final Matrix result = new Matrix(m_NumberOfRows, m_NumberOfColumns);
		for (int i = 0; i < result.m_NumberOfRows; ++i) {
			for (int j = 0; j < result.m_NumberOfColumns; ++j) {
				result.m_Data[i][j] = this.m_Data[i][j] / factor;
			}
		}
		
		return result;
	}
	
	public Matrix times(final Matrix other) {
		if (m_NumberOfColumns != other.m_NumberOfRows) {
			throw new RuntimeException("Can not multiply matrices with illegal dimensions.");
		}
		
		final Matrix result = new Matrix(m_NumberOfRows, other.m_NumberOfColumns);
		for (int i = 0; i < result.m_NumberOfRows; ++i) {
			for (int j = 0; j < result.m_NumberOfColumns; ++j) {
				for (int k = 0; k < m_NumberOfRows; ++k) {
					result.m_Data[i][j] += (m_Data[i][k] * other.m_Data[k][j]);
				}
			}
		}

		return result;
	}
	
	public Point times(final Point point) {
		Matrix newPointAsMatrix = this.times(new Matrix(new double[][] {
			{point.X()},
			{point.Y()},
			{point.Z()},
			{point.W()}}));
		
		return new Point(
				newPointAsMatrix.getValue(0, 0),
				newPointAsMatrix.getValue(1, 0),
				newPointAsMatrix.getValue(2, 0),
				newPointAsMatrix.getValue(3, 0));
	}
	
	public Matrix transpose() {
		Matrix transposeMatrix = new Matrix(m_NumberOfColumns, m_NumberOfRows);
		for (int i = 0; i < transposeMatrix.m_NumberOfRows; ++i) {
			for (int j = 0; j < transposeMatrix.m_NumberOfColumns; ++j) {
				transposeMatrix.m_Data[i][j] = m_Data[j][i];
			}
		}
		
		return transposeMatrix;
	}
	
	public static Matrix identity(final int dimension) {
		final Matrix identity = new Matrix(dimension, dimension);
		for (int i = 0; i < dimension; ++i) {
			identity.m_Data[i][i] = 1;
		}
		
		return identity;
	}
	
	public static Matrix scale(final double ...deltas) {
		if (deltas.length <= 1) {
			throw new RuntimeException("Scale matrix must be at least 2D.");
		}
		
		final Matrix scaleMatrix = new Matrix(deltas.length + 1, deltas.length + 1);
		for (int i = 0; i < deltas.length; ++i) {
			scaleMatrix.m_Data[i][i] = deltas[i];
		}
		
		scaleMatrix.m_Data[deltas.length][deltas.length] = 1;
		return scaleMatrix;
	}
	
	public static Matrix translate(final double ...deltas) {
		if (deltas.length <= 1) {
			throw new RuntimeException("Translate matrix must be at least 2D.");
		}
		
		final Matrix translateMatrix = Matrix.identity(deltas.length + 1);
		for (int i = 0; i < deltas.length; ++i) {
			translateMatrix.m_Data[i][deltas.length] = deltas[i];
		}
		
		return translateMatrix;
	}
	
	public static Matrix rotate(final double angleInDeg) {
		final Matrix3 rotationMatrix = new Matrix3(new double[] {
				Math.cos(Math.round(Math.toRadians(angleInDeg))), Math.sin(Math.round(Math.toRadians(angleInDeg))), 0,
			   -Math.sin(Math.round(Math.toRadians(angleInDeg))), Math.cos(Math.round(Math.toRadians(angleInDeg))), 0,
				0									, 0				 					  , 1
			});
		
		return rotationMatrix;
	}
	
	public static Matrix changeCoordinates(final ColumnVector ...coordinates) {
		final Matrix result = Matrix.identity(coordinates.length + 1); 
		for (int i = 0; i < coordinates.length; ++i) {
			for (int j = 0; j < coordinates.length; ++j) {
				result.m_Data[i][j] = coordinates[i].getValue(j, 0);
			}
		}
		
		return result;
	}
}
