package anne3D.math;

public class Matrix4 extends Matrix {

	public Matrix4() {
		super(4, 4);
	}
	
	public Matrix4(final double[] data) {
		this();
		if (16 != data.length) {
			throw new RuntimeException("Matrix2D must accept 4 double's");
		}
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				m_Data[i][j] = data[i * 3 + j];
			}
		}
	}
}
