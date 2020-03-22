package anne3D.math;

final public class ColumnVector extends Matrix {

	public ColumnVector(final int numberOfRows) {
		super(numberOfRows, 1);		
	}
	
	public ColumnVector(final double[] data) {
		this(data.length);
		for (int i = 0; i < data.length; ++i) {
			m_Data[i] = new double[] { data[i] };
		}
	}
}