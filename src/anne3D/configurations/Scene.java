package anne3D.configurations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import anne3D.math.Point;
import anne3D.utilities.File;
import anne3D.math.Edge;

final public class Scene {
	
	final private static int g_NUMBER_OF_VERTICES_INDEX = 0;
	private static int g_NUMBER_OF_EDGES_INDEX 	= 0;
	
	final private int m_NumberOfVertices;
	final private ArrayList<Point> m_Coordinates;
	final private int m_NumberOfEdges;
	final private ArrayList<Edge> m_Edges;
	
	private Scene(
			final int numberOfVertices,
			final ArrayList<Point> coordinates,
			final int numberOfEdges,
			final ArrayList<Edge> edges) {
		m_NumberOfVertices = numberOfVertices;
		m_Coordinates = coordinates;
		m_NumberOfEdges = numberOfEdges;
		m_Edges = edges;
	}
	
	/*
	 * Scene file format:
	 * 		NUMBER - NUMBER_OF_VERTICES
	 * 		LIST   - VERTICES[NUMBER_OF_VERTICES]
	 *		NUMBER - NUMBER_OF_EDGES
	 *		LIST   - EDGES[NUMBER_OF_EDGES]
	 */
	public static Scene loadSceneFromFile(final String filePath) throws IOException {
		Objects.requireNonNull(filePath, "filePath argument can not be null.");
		final File sceneFile = new File(filePath);
		final List<String> content = sceneFile.read();
		final int numberOfVertices = Integer.parseInt(content.get(g_NUMBER_OF_VERTICES_INDEX));
		g_NUMBER_OF_EDGES_INDEX = g_NUMBER_OF_VERTICES_INDEX + 1 + numberOfVertices;
		final ArrayList<Point> coordinates = new ArrayList<Point>();
		
		for (int i = g_NUMBER_OF_VERTICES_INDEX + 1; i <= numberOfVertices; ++i) {
			String coordinateString = content.get(i);
			String[] coordinateStringArray = coordinateString.split(" ");
			coordinates.add(new Point
					(Double.parseDouble(coordinateStringArray[0]),
					Double.parseDouble(coordinateStringArray[1])));
		}
		
		final ArrayList<Edge> edges = new ArrayList<Edge>();
		final int numberOfEdges = Integer.parseInt(
				content.get(numberOfVertices + g_NUMBER_OF_VERTICES_INDEX + 1));
		for (int i = g_NUMBER_OF_EDGES_INDEX + 1; i <= g_NUMBER_OF_EDGES_INDEX + numberOfEdges; ++i) {
			String edgeString = content.get(i);
			String[] edgeStringArray = edgeString.split(" ");
			edges.add(new Edge(
					new Point(coordinates.get(Integer.parseInt(edgeStringArray[0]))),
					new Point(coordinates.get(Integer.parseInt(edgeStringArray[1])))));
		}
		
		return new Scene(
				numberOfVertices,
				coordinates,
				numberOfEdges,
				edges);
	}
}
