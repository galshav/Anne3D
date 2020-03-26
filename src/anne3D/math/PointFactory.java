package anne3D.math;

final public class PointFactory {
	
	public Point create2DPoint(final double x, final double y) {
		return new Point(x, y, 0);
	}
	
	public Point create3DPoint(final double x, final double y, final double z) {
		return new Point(x, y, z);
	}
}
