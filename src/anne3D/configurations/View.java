package anne3D.configurations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import anne3D.math.Matrix;
import anne3D.math.Point;
import anne3D.utilities.File;

public class View {
	
	final private static int g_INDEX_ORIGIN 	  = 0;
	final private static int g_INDEX_ANGLE  	  = 1;
	final private static int g_INDEX_WINDOW_SIZE  = 2;
	final private static int g_INDEX_RESOLUTION   = 3;
	
	final private Point m_Origin;
	final private double m_Angle;
	final private int m_WindowWidth;
	final private int m_WindowHeight;
	final private int m_ViewWidth;
	final private int m_ViewHeight;
	final private Matrix m_ViewTransformation;
	
	public View(
			final Point origin,
			final double angle,
			final int windowWidth,
			final int windowHeight,
			final int viewWidth,
			final int viewHeight) {
		m_Origin = origin;
		m_Angle = angle;
		m_WindowWidth = windowWidth;
		m_WindowHeight = windowHeight;
		m_ViewWidth = viewWidth;
		m_ViewHeight = viewHeight;
		m_ViewTransformation = null;
		// TODO: Calculate view transformation matrix here.
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
