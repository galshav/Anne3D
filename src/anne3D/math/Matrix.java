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
	

	@SuppressWarnings("unchecked")
	private <T extends Matrix> T createSpecificMatrixType(final T other) {
		if (other instanceof ColumnVector) {
			return (T) new ColumnVector(other.m_NumberOfRows);
		}
		
		else if (other instanceof RowVector) {
			return (T) new RowVector(other.m_NumberOfColumns);
		}
		
		return (T) new Matrix(m_NumberOfRows, m_NumberOfColumns);
	}
	
	public <T extends Matrix> T plus(final T other) {
		if ((m_NumberOfRows    != other.m_NumberOfRows) ||
			(m_NumberOfColumns != other.m_NumberOfColumns)) {
			throw new RuntimeException("Can not add matrix with different dimensions.");
		}
		
		T result = this.createSpecificMatrixType(other);
		for (int i = 0; i < m_NumberOfRows; ++i) {
			for (int j = 0; j < m_NumberOfColumns; ++j) {
				result.m_Data[i][j] = m_Data[i][j] + other.m_Data[i][j];
			}
		}
		
		return result;
	}
	
	public <T extends Matrix> T minus(final T other) {
		if ((m_NumberOfRows	   != other.m_NumberOfRows) ||
			(m_NumberOfColumns != other.m_NumberOfColumns)) {
				throw new RuntimeException("Can not add matrix with different dimensions.");
		}
		
		T result = this.createSpecificMatrixType(other);
		for (int i = 0; i < m_NumberOfRows; ++i) {
			for (int j = 0; j < m_NumberOfColumns; ++j) {
				result.m_Data[i][j] = m_Data[i][j] - other.m_Data[i][j];
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
				for (int k = 0; k < result.m_NumberOfColumns; ++k) {
					result.m_Data[i][j] += (m_Data[i][k] * other.m_Data[k][j]);
				}
			}
		}

		return result;
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
	
	final public static Matrix scale(final double ...deltas) {
		if (deltas.length <= 1) {
			throw new RuntimeException("Scale matrix must be at least 2D.");
		}
		
		final Matrix scaleMatrix = new Matrix(deltas.length, deltas.length);
		for (int i = 0; i < deltas.length; ++i) {
			scaleMatrix.m_Data[i][i] = deltas[i];
		}
		
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
}
