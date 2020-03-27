package anne3D.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Objects;
import anne3D.configurations.Scene;
import anne3D.configurations.View;
import anne3D.math.Edge;
import anne3D.math.Matrix;
import anne3D.math.Transformation;
import anne3D.utilities.Logger;

final public class EngineCanvas extends Canvas implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	private Point m_StartPoint;
	private Point m_EndPoint;
	
	final private int m_Width;
	final private int m_Height;
	final private Scene m_Scene;
	final private View m_View;
	
	int x[] = {100, 200, 200, 100};
	int y[] = {100, 100, 200, 200};
	
	public EngineCanvas(final int width, final int height) {
		if (width <= 0 || height <= 0) {
			throw new RuntimeException("Canvas dimensions must be positive.");
		}
		
		m_Width = width;
		m_Height = height;
		setSize(m_Width, m_Height);
		setBackground(Color.black);
		addMouseListener(this);
		addMouseMotionListener(this);
		m_Scene = null;
		m_View = null;
	}
	
	public EngineCanvas(final Scene scene, final View view) {
		Objects.requireNonNull(scene, "scene argument can not be null.");
		Objects.requireNonNull(view, "view argument can not be null.");
		m_Width = view.ViewWidth + View.g_WINDOW_MARGIN;
		m_Height = view.ViewHeight + View.g_WINDOW_MARGIN;
		m_View = view;
		m_Scene = scene;
		setSize(m_Width, m_Height);
		setBackground(Color.black);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void paint (Graphics graphics) {
		graphics.setColor(Color.GREEN);
		for (Edge edge : m_Scene.Edges) {
			graphics.drawLine(
					(int)edge.GetFirstPoint().X(),
					(int)edge.GetFirstPoint().Y(),
					(int)edge.GetSecondPoint().X(),
					(int)edge.GetSecondPoint().Y());
		}
		
		//Polygon	p = new Polygon();

		//for (int i=0; i < x.length; i++) {
		//	p.addPoint(x[i],y[i]);
		//}
		//graphics.drawRect(20,20, m_Width - 40, m_Height - 40);
		//graphics.setColor(Color.red);
		//graphics.drawPolygon(p);
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse clicked",
				mouseEvent.getX(),
				mouseEvent.getY()));
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse entered",
				mouseEvent.getX(),
				mouseEvent.getY()));
		
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse exited",
				mouseEvent.getX(),
				mouseEvent.getY()));
		
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse pressed",
				mouseEvent.getX(),
				mouseEvent.getY()));
		// Translate.
		/*for (int i = 0; i < x.length; ++i) {
			Matrix result = Matrix.translate(10,10).times(new Matrix(new double[][] {
				{x[i]},
				{y[i]},
				{1}
				
			x[i] = (int)result.getValue(0, 0);
			y[i] = (int)result.getValue(1, 0);

		}*/
		
		/* Scale.
		for (int i = 0; i < x.length; ++i) {
			Matrix result = Matrix.scale(2,2).times(new Matrix(new double[][] {
				{x[i]},
				{y[i]},
				{1}
			}));
			
			x[i] = (int)result.getValue(0, 0);
			y[i] = (int)result.getValue(1, 0);
		}*/
		
		/* Scale + Transle.
		for (int i = 0; i < x.length; ++i) {
			Matrix transformationMatrix = 
					Matrix.translate(100, 100).times
					(Matrix.scale(2,2).times
					(Matrix.translate(-100, -100)));
			
			
			Matrix result = transformationMatrix.times(new Matrix(new double[][] {
				{x[i]},
				{y[i]},
				{1}
			}));
			
			x[i] = (int)result.getValue(0, 0);
			y[i] = (int)result.getValue(1, 0);
		}
		*/
		
		for (int i = 0; i < x.length; ++i) {
			Matrix transformationMatrix = 
					Transformation.translate(150, 150).times
					(Transformation.rotate2D(45).times
					(Transformation.translate(-150, -150)));
			
			Matrix result = transformationMatrix.times(new Matrix(new double[][] {
				{x[i]},
				{y[i]},
				{1}
			}));

			x[i] = (int)result.getValue(0, 0);
			y[i] = (int)result.getValue(1, 0);
		}
		
		this.repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse released",
				mouseEvent.getX(),
				mouseEvent.getY()));
		
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse dragged",
				mouseEvent.getX(),
				mouseEvent.getY()));
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse moved",
				mouseEvent.getX(),
				mouseEvent.getY()));
		
	}
}
