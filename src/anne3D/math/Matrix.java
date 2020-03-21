package anne3D.math;
import java.lang.RuntimeException;
import java.util.Arrays;

final public class Matrix {
	private final int m_NumberOfRows;
	private final int m_NumberOfColumns;
	private final double[][] m_Data;
	
	public Matrix(final int numberOfRows, final int numberOfColumns) {
		if (numberOfRows    <= 0 ||
			numberOfColumns <= 0 || 
			(1 == numberOfRows && 1 == numberOfColumns)) {
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
