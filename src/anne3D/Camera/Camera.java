package anne3D.Camera;

import anne3D.math.Vector3;
import anne3D.utilities.Logger;

/*
 * Singleton camera class.
 * Holds information about current position of camera
 * and the player coordinates system.
 */
final public class Camera {
	private static Camera m_instance = null;
	
	public enum AXIS {
		U,
		V,
		W,
	}
	
	public Vector3 Position;
	public Vector3 U; 
	public Vector3 V; 
	public Vector3 W; 

	private double m_RotationAngleRelativeToU = 0;
	private double m_RotationAngleRelativeToV = 0;
	private double m_RotationAngleRelativeToW = 0;
	
	private Camera() {
		Position = new Vector3(0, 0, 3);
		U = new Vector3(1, 0 ,0);
		V = new Vector3(0, 1, 0);
		W = new Vector3(0, 0, 1);
	}
	
	public static Camera getInstance() {
		if (null == m_instance) {
			m_instance = new Camera();
		}
		
		return m_instance;
	}
	
	public void rotate(final AXIS axis, final double angleInDeg) {
		if (0 == angleInDeg) {
			return;
		}
		
		if (axis == AXIS.U) {
			m_RotationAngleRelativeToU += angleInDeg;
			m_RotationAngleRelativeToU = m_RotationAngleRelativeToU % 360;
			Logger.Debug(String.format("Angle relative to X:%f [deg]", m_RotationAngleRelativeToU));
			V = V.multiplyByScalar(Math.cos(Math.toRadians(angleInDeg))).plus(W.multiplyByScalar(Math.sin(Math.toRadians(angleInDeg))));
			V = V.divide(V.size());
			W = V.multiplyByScalar(-Math.sin(Math.toRadians(angleInDeg))).plus(W.multiplyByScalar(Math.cos(Math.toRadians(angleInDeg))));
			W = W.divide(W.size());
		}
		
		else if (axis == AXIS.V) {
			m_RotationAngleRelativeToV += angleInDeg;
			m_RotationAngleRelativeToV = m_RotationAngleRelativeToV % 360;
			Logger.Debug(String.format("Angle relative to Y:%f [deg]", m_RotationAngleRelativeToV));
			U = U.multiplyByScalar(Math.cos(Math.toRadians(angleInDeg))).minus(W.multiplyByScalar(Math.sin(Math.toRadians(angleInDeg))));
			U = U.divide(U.size());
			W = U.multiplyByScalar(Math.sin(Math.toRadians(angleInDeg))).plus(W.multiplyByScalar(Math.cos(Math.toRadians(angleInDeg))));
			W = W.divide(W.size());
		}
		
		else if (axis == AXIS.W) {
			m_RotationAngleRelativeToW += angleInDeg;
			m_RotationAngleRelativeToW = m_RotationAngleRelativeToW % 360;
			Logger.Debug(String.format("Angle relative to Z:%f [deg]", m_RotationAngleRelativeToW));
			U = U.multiplyByScalar(Math.cos(Math.toRadians(angleInDeg))).plus(V.multiplyByScalar(Math.sin(Math.toRadians(angleInDeg))));
			U = U.divide(U.size());
			V = U.multiplyByScalar(-Math.sin(Math.toRadians(angleInDeg))).plus(V.multiplyByScalar(Math.cos(Math.toRadians(angleInDeg))));
			V = V.divide(V.size());
		}
	}
	
	public void move(final AXIS axis, final double moveFactor) {
		if (0 == moveFactor) {
			return;
		}
		
		if (AXIS.U == axis) { 
			Position.X = Position.X + (moveFactor * U.X); 
			Position.Y = Position.Y + (moveFactor * U.Y);
			Position.Z = Position.Z + (moveFactor * U.Z); 
		}
		
		else if (AXIS.V == axis) {
			Position.X = Position.X + (moveFactor * V.X); 
			Position.Y = Position.Y + (moveFactor * V.Y);
			Position.Z = Position.Z + (moveFactor * V.Z); 
		}
		
		else if (AXIS.W == axis) {
			Position.X = Position.X + (moveFactor * W.X); 
			Position.Y = Position.Y + (moveFactor * W.Y);
			Position.Z = Position.Z + (moveFactor * W.Z); 
		}
		
		else {
			throw new RuntimeException("Incorrect camera dimension.");
		}
		
		Logger.Debug(String.format("Camera position: (%f, %f, %f)", Position.X, Position.Y, Position.Z));
	}
}
