package anne3D.math;

final public class Edge {

	private final Point m_FirstPoint;
	private final Point m_SecondPoint;
	
	public Edge(final Point firstPoint, final Point secondPoint) {
		m_FirstPoint = firstPoint;
		m_SecondPoint = secondPoint;
	}
	
	public Point GetFirstPoint() {
		return m_FirstPoint;
	}
	
	public Point GetSecondPoint() {
		return m_SecondPoint;
	}
}
