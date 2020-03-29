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
	
	public double vectorSize() {
		double sum = 0;
		for (int i = 0; i < m_NumberOfRows; ++i) {
			sum += java.lang.Math.pow(m_Data[i][0], 2);
		}
		
		return java.lang.Math.sqrt(sum);
	}
}
