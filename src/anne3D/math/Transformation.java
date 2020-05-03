package anne3D.math;

final public class Transformation {
	
	final public Matrix TransformationMatrix;
	
	public Transformation(final Matrix transformationMatrix) {
		TransformationMatrix = transformationMatrix;
	}
	
	public Point applyTransformation(final Point point) {
		return TransformationMatrix.times(point);
	}
	
	public Transformation compose(final Transformation transformation) {
		return new Transformation(TransformationMatrix.times(transformation.TransformationMatrix));
	}
	
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
	
	public static Matrix rotate3DByX(final double angleInDeg) {
		final Matrix4 rotationMatrix = new Matrix4(new double[] {
				1									   , 0									  , 0									 , 0,
			    0, Math.cos(Math.toRadians(angleInDeg)), -Math.sin(Math.toRadians(angleInDeg)), 0  									 ,
				0									   , Math.sin(Math.toRadians(angleInDeg)) , Math.cos(Math.toRadians(angleInDeg)) , 0,
				0									   , 0									  , 0                                    , 1
			});
		
		return rotationMatrix;
	}
	
	public static Matrix rotate3DByY(final double angleInDeg) {
		final Matrix4 rotationMatrix = new Matrix4(new double[] {
				Math.cos(Math.toRadians(angleInDeg)), 0									  , -Math.sin(Math.toRadians(angleInDeg)), 0,
			    0									, 1									  , 0, 0,
			    Math.sin(Math.toRadians(angleInDeg)), 0				 					  , Math.cos(Math.toRadians(angleInDeg)) , 0,
				0									, 0									  , 0									 , 1
			});
		
		return rotationMatrix;
	}
	
	public static Matrix rotate3DByZ(final double angleInDeg) {
		final Matrix4 rotationMatrix = new Matrix4(new double[] {
				Math.cos(Math.toRadians(angleInDeg)), Math.sin(Math.toRadians(angleInDeg)), 0, 0,
			   -Math.sin(Math.toRadians(angleInDeg)), Math.cos(Math.toRadians(angleInDeg)), 0, 0,
				0									, 0				 					  , 1, 0,
				0									, 0									  , 0, 1
			});
		
		return rotationMatrix;
	}
	
	public static Transformation orthographicProjection() {
		final Matrix result = Matrix.identity(4);
		result.m_Data[2][2] = 0;
		return new Transformation(result);
	}
}
