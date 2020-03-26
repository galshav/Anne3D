package anne3D.math;

public class Matrix2 extends Matrix {

	public Matrix2() {
		super(2, 2);
	}
	
	public Matrix2(final double[] data) {
		this();
		if (4 != data.length) {
			throw new RuntimeException("Matrix2D must accept 4 double's");
		}
		
		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 2; ++j) {
				m_Data[i][j] = data[i * 2 + j];
			}
		}
	}
}
