package anne3D.math;

final public class Transformation {
	
	public static Matrix translate(final double ...deltas) {
		if (deltas.length <= 1) {
			throw new RuntimeException("Translate matrix must be at least 2D.");
		}
		
		final Matrix translateMatrix = Matrix.identity(deltas.length + 1);
		for (int i = 0; i < deltas.length; ++i) {
			translateMatrix.m_Data[i][deltas.length] = deltas[i];
		}
		
		return translateMatrix;
	}
	
	public static Matrix scale(final double ...deltas) {
		if (deltas.length <= 1) {
			throw new RuntimeException("Scale matrix must be at least 2D.");
		}
		
		final Matrix scaleMatrix = new Matrix(deltas.length + 1, deltas.length + 1);
		for (int i = 0; i < deltas.length; ++i) {
			scaleMatrix.m_Data[i][i] = deltas[i];
		}
		
		scaleMatrix.m_Data[deltas.length][deltas.length] = 1;
		return scaleMatrix;
	}
	
	public static Matrix rotate2D(final double angleInDeg) {
		final Matrix3 rotationMatrix = new Matrix3(new double[] {
				Math.cos(Math.toRadians(angleInDeg)), Math.sin(Math.toRadians(angleInDeg)), 0,
			   -Math.sin(Math.toRadians(angleInDeg)), Math.cos(Math.toRadians(angleInDeg)), 0,
				0									, 0				 					  , 1
			});
		
		return rotationMatrix;
	}
}
