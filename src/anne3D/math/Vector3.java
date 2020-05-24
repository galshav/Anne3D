package anne3D.math;

public class Vector3 extends ColumnVector {
	
	public double X;
	public double Y;
	public double Z;
	
	public Vector3() {
		super(3);
		X = 0;
		Y = 0;
		Z = 0;
	}
	
	public Vector3(final double x, final double y, final double z) {
		super(new double[] {x, y, z});
		X = x;
		Y = y;
		Z = z;
	}
	
	@Override
	public String toString() {
		return String.format("x=%f, y=%f, z=%f", X, Y, Z);
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
	
	public Vector3 cross(final Vector3 other) {
		return new Vector3(
				this.Y * other.Z - this.Z * other.Y,
				this.Z * other.X - this.X * other.Z,
				this.X * other.Y - this.Y * other.X);
	}
	
	@Override
	public Vector3 divide(final double factor) {
		Matrix result = super.divide(factor);
		return new Vector3(
				result.getValue(0, 0),
				result.getValue(1, 0),
				result.getValue(2, 0));
	}
}
