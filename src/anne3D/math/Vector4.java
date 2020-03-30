package anne3D.math;

public class Vector4 extends ColumnVector {

	public Vector4() {
		super(4);
	}
	
	public Vector4(final double x, final double y, final double z, final double w) {
		super(new double[] {x, y, z, w});
	}
	
	@Override
	public Vector4 minus(final Matrix other) {
	Matrix result = super.minus(other);
	return new Vector4(
			result.getValue(0, 0),
			result.getValue(1, 0),
			result.getValue(2, 0),
			result.getValue(3, 0));
	}
	
	@Override
	public Vector4 plus(final Matrix other) {
		Matrix result = super.plus(other);
		return new Vector4(
				result.getValue(0, 0),
				result.getValue(1, 0),
				result.getValue(2, 0),
				result.getValue(3, 0));
	}
}
