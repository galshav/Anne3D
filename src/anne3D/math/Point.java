package anne3D.math;

public final class Point {
	
	final private double m_X;
	final private double m_Y;
	final private double m_Z;
	
	public Point(final double x, final double y, final double z) {
		m_X = x;
		m_Y = y;
		m_Z = z;
	}
	
	public Point(final double x, final double y) {
		this(x, y, 0);
	}
	
	public Point() {
		this(0, 0, 0);
	}
	
	public Point(final Point other) {
		this(other.m_X, other.m_Y, other.m_Z);
	}
	
	public double X() {
		return m_X;
	}
	
	public double Y() {
		return m_Y;
	}
	
	public double Z() {
		return m_Z;
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		
		if (!(other instanceof Point)) {
			return false;
		}
		
		return ((m_X == ((Point)(other)).m_X) &&
				 m_Y == ((Point)(other)).m_Y);
	}
}
