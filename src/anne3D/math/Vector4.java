package anne3D.math;

public class Vector4 extends ColumnVector {

	public Vector4() {
		super(4);
	}
	
	public Vector4(final double x, final double y, final double z, final double w) {
		super(new double[] {x, y, z, w});
	}
}
