package anne3D.math;

final public class Vector extends Matrix{

	public Vector(final int numberOfRows) {
		super(numberOfRows, 1);		
	}
	
	public Vector(final double[] data) {
		this(data.length);
		for (int i = 0; i < data.length; ++i) {
			m_Data[i] = new double[] { data[i] };
		}
	}
}