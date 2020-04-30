package anne3D.math;

public class Vector2 extends ColumnVector {

	final public double X;
	final public double Y;
	
	public Vector2() {
		super(2);
		X = 0;
		Y = 0;
	}
	
	public Vector2(final double x, final double y) {
		super(new double[] {x, y});
		X = x;
		Y = y;
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
	
	public double angle() {
		double angle = java.lang.Math.atan((Y / X)) * 180 / java.lang.Math.PI;
		
		if (X < 0) {
			angle += 180;
		}
		
		else if (Y < 0) {
			angle += 360;
		}
		
		return angle;
	}
}
