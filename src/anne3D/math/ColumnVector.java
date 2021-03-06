package anne3D.math;

public class ColumnVector extends Matrix {

	public ColumnVector(final int numberOfRows) {
		super(numberOfRows, 1);		
	}
	
	public ColumnVector(final double[] data) {
		this(data.length);
		for (int i = 0; i < data.length; ++i) {
			m_Data[i] = new double[] { data[i] };
		}
	}
	
	public double size() {
		double sum = 0;
		for (int i = 0; i < m_NumberOfRows; ++i) {
			sum += java.lang.Math.pow(m_Data[i][0], 2);
		}
		
		return java.lang.Math.sqrt(sum);
	}
	
	@Override
	public ColumnVector minus(final Matrix other) {
		Matrix resultMatrix = super.minus(other);
		ColumnVector resultVector = new ColumnVector(m_NumberOfRows);
		for (int i = 0; i < m_NumberOfRows; ++i) {
			resultVector.m_Data[i][0] = resultMatrix.getValue(i, 0);
		}
		
		return resultVector;
	}
	
	@Override
	public ColumnVector plus(final Matrix other) {
		Matrix resultMatrix = super.plus(other);
		ColumnVector resultVector = new ColumnVector(m_NumberOfRows);
		for (int i = 0; i < m_NumberOfRows; ++i) {
			resultVector.m_Data[i][0] = resultMatrix.getValue(i, 0);
		}
		
		return resultVector;
	}
}
