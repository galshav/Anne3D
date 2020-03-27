package anne3D.configurations;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import anne3D.math.Matrix;
import anne3D.math.Point;
import anne3D.utilities.File;

/*
 * View data type class.
 * All members should be public for easy access.
 */
public class View {
	
	final public static int  g_WINDOW_MARGIN      = 40;
	final private static int g_INDEX_ORIGIN 	  = 0;
	final private static int g_INDEX_ANGLE  	  = 1;
	final private static int g_INDEX_WINDOW_SIZE  = 2;
	final private static int g_INDEX_RESOLUTION   = 3;
	
	final public Point Origin;
	final public double Angle;
	final public int WindowWidth;
	final public int WindowHeight;
	final public int ViewWidth;
	final public int ViewHeight;
	final public Matrix ViewTransformation;
	
	private View(
			final Point origin,
			final double angle,
			final int windowWidth,
			final int windowHeight,
			final int viewWidth,
			final int viewHeight) {
		Origin = origin;
		Angle = angle;
		WindowWidth = windowWidth;
		WindowHeight = windowHeight;
		ViewWidth = viewWidth;
		ViewHeight = viewHeight;
		
		Matrix marginTranslateTransformation = Matrix.translate(
				(ViewWidth  / 2) + (g_WINDOW_MARGIN / 2), 
				(ViewHeight / 2) + (g_WINDOW_MARGIN / 2));
		Matrix scaleTransformation = Matrix.scale(
				(ViewWidth  / WindowWidth),
				(ViewHeight / WindowHeight) * (-1));
		Matrix rotateTransformation = Matrix.rotate(Angle);
		Matrix originTranslateTransformation = Matrix.translate(
				-Origin.X(),
				- Origin.Y());
		
		ViewTransformation = 
				marginTranslateTransformation.times(
				scaleTransformation).times(
				rotateTransformation).times(
				originTranslateTransformation);
	}
	
	public static View loadViewFromFile(final String filePath) throws IOException {		
		Objects.requireNonNull(filePath, "filePath argument can not be null.");
		final File viewFile = new File(filePath);
		final List<String> content = viewFile.read();
		final String originString = content.get(g_INDEX_ORIGIN);
		final String[] originStringArray = originString.split(" ");
		final Point origin = new Point(
				Double.parseDouble(originStringArray[1]),
				Double.parseDouble(originStringArray[2]));
		final String angleString = content.get(g_INDEX_ANGLE);
		final String[] angleStringArray = angleString.split(" ");
		final double angle = Double.parseDouble(angleStringArray[1]);
		final String windowSize = content.get(g_INDEX_WINDOW_SIZE);
		final String[] windowSizeArray = windowSize.split(" ");
		final int windowWidth = Integer.parseInt(windowSizeArray[1]);
		final int windowHeight = Integer.parseInt(windowSizeArray[2]); 
		final String resolutionString = content.get(g_INDEX_RESOLUTION);
		final String[] resolutionStringArray = resolutionString.split(" ");
		final int viewWidth = Integer.parseInt(resolutionStringArray[1]);
		final int viewHeight = Integer.parseInt(resolutionStringArray[2]);
		return new View(
				origin,
				angle, 
				windowWidth,
				windowHeight,
				viewWidth,
				viewHeight);
	}
}
