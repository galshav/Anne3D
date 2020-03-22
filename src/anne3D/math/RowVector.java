package anne3D.math;

final public class RowVector extends Matrix {
	
	public RowVector(final int numberOfColumns) {
		super(1, numberOfColumns);
	}
	
	public RowVector(final double[] data) {
		this(data.length);
		m_Data[0] = data;
	}
}
