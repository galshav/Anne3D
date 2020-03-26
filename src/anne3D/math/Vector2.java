package anne3D.math;

public class Vector2 extends ColumnVector {

	public Vector2() {
		super(2);
	}
	
	public Vector2(final double x, final double y) {
		super(new double[] {x, y});
	}
}
