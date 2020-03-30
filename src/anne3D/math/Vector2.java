package anne3D.math;

public class Vector2 extends ColumnVector {

	public Vector2() {
		super(2);
	}
	
	public Vector2(final double x, final double y) {
		super(new double[] {x, y});
	}
	
	@Override
	public Vector2 minus(final Matrix other) {
	Matrix result = super.minus(other);
	return new Vector2(
			result.getValue(0, 0),
			result.getValue(1, 0));
	}
	
	@Override
	public Vector2 plus(final Matrix other) {
		Matrix result = super.plus(other);
		return new Vector2(
				result.getValue(0, 0),
				result.getValue(1, 0));
	}
}
