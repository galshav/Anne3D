package anne3D.math;

public class Vector3 extends ColumnVector {
	
	public Vector3() {
		super(3);
	}
	
	public Vector3(final double x, final double y, final double z) {
		super(new double[] {x, y, z});
	}
	
	@Override
	public Vector3 minus(final Matrix other) {
		Matrix result = super.minus(other);
		return new Vector3(
				result.getValue(0, 0),
				result.getValue(1, 0),
				result.getValue(2, 0));
	}
	
	@Override
	public Vector3 plus(final Matrix other) {
		Matrix result = super.plus(other);
		return new Vector3(
				result.getValue(0, 0),
				result.getValue(1, 0),
				result.getValue(2, 0));
	}
}
