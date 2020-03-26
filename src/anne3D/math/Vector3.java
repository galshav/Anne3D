package anne3D.math;

public class Vector3 extends ColumnVector {
	
	public Vector3() {
		super(3);
	}
	
	public Vector3(final double x, final double y, final double z) {
		super(new double[] {x, y, z});
	}
}
