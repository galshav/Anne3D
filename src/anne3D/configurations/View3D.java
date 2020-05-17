package anne3D.configurations;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import anne3D.math.Matrix;
import anne3D.math.Transformation;
import anne3D.math.Vector3;
import anne3D.utilities.File;

/*
 * View data type class.
 * All members should be public for easy access.
 */
public class View3D {
	
	final public static int  g_WINDOW_MARGIN      		= 40;
	final private static int g_INDEX_POSITION 	 		= 0;
	final private static int g_INDEX_LOOKAT		  		= 1;
	final private static int g_INDEX_UPVECTOR	  		= 2;
	final private static int g_INDEX_WINDOW_BOUNDARIES  = 3;
	final private static int g_INDEX_VIEW  				= 4;
	
	final public Vector3 Position;
	final public Vector3 LookAt;
	final public Vector3 UpVector;
	final public double WindowLeft;
	final public double WindowRight;
	final public double WindowTop;
	final public double WindowBottom;
	final public int ViewWidth;
	final public int ViewHeight;
	final public Transformation CameraTransformation;
	final public Transformation ProjectionTransformation;
	final public Transformation DeviceTransformation;
	
	private View3D(
			final Vector3 position,
			final Vector3 lookAt,
			final Vector3 upVector,
			final double windowLeft,
			final double windowRight,
			final double windowTop,
			final double windowBottom,
			final int viewWidth,
			final int viewHeight) {
		Position = position;
		LookAt = lookAt;
		UpVector = upVector;
		WindowLeft = windowLeft;
		WindowRight = windowRight;
		WindowTop = windowTop;
		WindowBottom = windowBottom;
		ViewWidth = viewWidth;
		ViewHeight = viewHeight;

		Matrix translationToWorldOrigin = Matrix.translate(
				-Position.X,
				-Position.Y,
				-Position.Z);
		Vector3 cameraZ = Position.minus(LookAt);
		cameraZ = cameraZ.divide(cameraZ.size());
		Vector3 cameraX = UpVector.cross(cameraZ);
		cameraX = cameraX.divide(cameraX.size());
		Vector3 cameraY = cameraZ.cross(cameraX);
		Matrix coordinateSystemMatrix = Matrix.changeCoordinates(
				cameraX, cameraY, cameraZ);
		CameraTransformation = new Transformation(
				coordinateSystemMatrix.times(translationToWorldOrigin));
		ProjectionTransformation = Transformation.orthographicProjection();
		Matrix originTranslateTransformation = Matrix.translate(-WindowLeft, -WindowBottom, 0);
		final double scaleWidth = ViewWidth / (WindowRight - WindowLeft);
		final double scaleHeight = ViewHeight / (WindowTop - WindowBottom);
		final Matrix scaleTransformation = Matrix.scale(scaleWidth, scaleHeight * (-1), 0);
		final Matrix marginTranslateTransformation = Matrix.translate(
				(g_WINDOW_MARGIN / 2), 
				(ViewHeight) + (g_WINDOW_MARGIN / 2),
				0);
		DeviceTransformation = new Transformation(
				marginTranslateTransformation.times(
				scaleTransformation.times(
				originTranslateTransformation)));
	}
	
	public static View3D loadViewFromFile(final String filePath) throws IOException {		
		Objects.requireNonNull(filePath, "filePath argument can not be null.");
		final File viewFile = new File(filePath);
		final List<String> content = viewFile.read();
		final String positionString = content.get(g_INDEX_POSITION);
		final String[] positionStringArray = positionString.split(" ");
		final Vector3 position = new Vector3(
				Double.parseDouble(positionStringArray[1]),
				Double.parseDouble(positionStringArray[2]),
				Double.parseDouble(positionStringArray[3]));
		final String lookAtString = content.get(g_INDEX_LOOKAT);
		final String[] lookAtStringArray = lookAtString.split(" ");
		final Vector3 lookAt = new Vector3(
				Double.parseDouble(lookAtStringArray[1]),
				Double.parseDouble(lookAtStringArray[2]),
				Double.parseDouble(lookAtStringArray[3]));
		final String upVectorString = content.get(g_INDEX_UPVECTOR);
		final String[] upVectorStringArray = upVectorString.split(" ");
		final Vector3 upVector = new Vector3(
				Double.parseDouble(upVectorStringArray[1]),
				Double.parseDouble(upVectorStringArray[2]),
				Double.parseDouble(upVectorStringArray[3]));
		final String windowString = content.get(g_INDEX_WINDOW_BOUNDARIES);
		final String[] windowStringArray = windowString.split(" ");
		final int windowLeft = Integer.parseInt(windowStringArray[1]);
		final int windowRight = Integer.parseInt(windowStringArray[2]);
		final int windowBottom = Integer.parseInt(windowStringArray[3]);
		final int windowTop = Integer.parseInt(windowStringArray[4]);
		final String viewString = content.get(g_INDEX_VIEW);
		final String[] viewStringArray = viewString.split(" ");
		final int viewWidth = Integer.parseInt(viewStringArray[1]);
		final int viewHeight = Integer.parseInt(viewStringArray[2]);
		return new View3D(
				position,
				lookAt,
				upVector,
				windowLeft,
				windowRight,
				windowTop,
				windowBottom,
				viewWidth,
				viewHeight);
	}
}
