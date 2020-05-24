package anne3D.Camera;

import anne3D.math.Matrix;
import anne3D.math.Vector3;
import anne3D.math.Vector4;

/*
 * Singleton camera class.
 * Holds information about current position of camera
 * and the player coordinates system.
 */
final public class Camera {
	private static Camera m_instance = null;
	
	public Vector4 Position;
	public Vector3 X;
	public Vector3 Y;
	public Vector3 Z;
	
	public enum AXIS {
		X,
		Y,
		Z,
	}
	
	private Camera() {
		Position = new Vector4(0, 0, 0, 1);
		X = new Vector3(1, 0 ,0);
		Y = new Vector3(0, 1, 0);
		Z = new Vector3(0, 0, 1);
	}
	
	public static Camera getInstance() {
		if (null == m_instance) {
			m_instance = new Camera();
		}
		
		return m_instance;
	}
	
	public void rotate() {
		
	}
	
	public void move(final AXIS axis, final double moveFactor) {
		if (axis == AXIS.X) { 
			Position.X = Position.X + moveFactor;
		}
		
		else if (axis == AXIS.Y) {
			Position.Y = Position.Y + moveFactor;
		}
		
		else if (axis == AXIS.Z) {
			Position.Z = Position.Z + moveFactor;
		}
		
		else {
			throw new RuntimeException("Incorrect camera dimension.");
		}
	}
}
