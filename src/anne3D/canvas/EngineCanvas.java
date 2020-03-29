package anne3D.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Objects;
import anne3D.Main;
import anne3D.configurations.Scene;
import anne3D.configurations.View;
import anne3D.math.ColumnVector;
import anne3D.math.Edge;
import anne3D.math.Matrix;
import anne3D.math.Point;
import anne3D.math.Transformation;
import anne3D.utilities.Logger;

final public class EngineCanvas extends Canvas implements MouseListener, MouseMotionListener {
	private static enum e_STATE {
		TRANSLATE,
		SCALE,
		ROTATE,
		NONE
	}
	
	private static final long serialVersionUID = 1L;
	private Point m_StartPoint;
	private View m_View;
	private Scene m_Scene;
	private Transformation m_CurrentTransformation;
	private Transformation m_AccumulatedTransformation;
	private e_STATE m_CurrentTransformationState;
	
	public EngineCanvas(final Scene scene, final View view) {
		Objects.requireNonNull(scene, "scene argument can not be null.");
		Objects.requireNonNull(view, "view argument can not be null.");
		m_CurrentTransformation = new Transformation(Matrix.identity(3));
		m_AccumulatedTransformation = new Transformation(Matrix.identity(3));
		m_View = view;
		m_Scene = scene;
		m_CurrentTransformationState = e_STATE.NONE;
		setSize(view.ViewWidth + View.g_WINDOW_MARGIN,
				view.ViewWidth + View.g_WINDOW_MARGIN);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public EngineCanvas(final Scene scene, final View view, Color canvasColor) {
		this(scene, view);
		setBackground(canvasColor);
	}
	
	private void paintClipBorder(final Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.drawRect(
				View.g_WINDOW_MARGIN / 2,
				View.g_WINDOW_MARGIN / 2,
				m_View.ViewWidth ,
				m_View.ViewHeight);
	}
	
	@SuppressWarnings("unused")
	private void paintGrid(final Graphics graphics) {
		if (false == Main.g_DEBUG) {
			return;
		}
		
		final int viewOrigin = View.g_WINDOW_MARGIN / 2;
		final int widthResolution = m_View.ViewWidth / 3;
		final int heightResolution = m_View.ViewHeight / 3;
		
		graphics.drawLine(
				viewOrigin + widthResolution,
				viewOrigin,
				viewOrigin + widthResolution,
				viewOrigin + m_View.ViewHeight);
		graphics.drawLine(
				viewOrigin + widthResolution * 2,
				viewOrigin, 
				viewOrigin + widthResolution * 2,
				viewOrigin + m_View.ViewHeight);
		graphics.drawLine(
				viewOrigin,
				viewOrigin + heightResolution, 
				viewOrigin + m_View.ViewWidth, 
				viewOrigin + heightResolution);
		graphics.drawLine(
				viewOrigin, 
				viewOrigin + heightResolution * 2, 
				viewOrigin + m_View.ViewWidth, 
				viewOrigin + heightResolution * 2);
	}
	
	@Override
	public void paint (final Graphics graphics) {
		paintClipBorder(graphics);
		paintGrid(graphics);
		graphics.setColor(Color.GREEN);
		final Transformation totalTransformation = 
				m_CurrentTransformation.compose(
				m_AccumulatedTransformation.compose(
				m_View.ViewTransformation));
		
		//m_AccumulatedTransformation = m_AccumulatedTransformation.compose(m_CurrentTransformation);
		for (final Edge origEdge : m_Scene.Edges) {
			Point p1 = totalTransformation.applyTransformation(origEdge.GetFirstPoint());
			Point p2 = totalTransformation.applyTransformation(origEdge.GetSecondPoint());
			graphics.drawLine(
					(int)p1.X(),
					(int)p1.Y(),
					(int)p2.X(),
					(int)p2.Y());
		}	
	}
	
	private void setTransformationTypeByPressedPointCoordinates(final Point point) {
		final double x = point.X();
		final double y = point.Y();
		final int viewOrigin = View.g_WINDOW_MARGIN / 2;
		final int widthResolution = m_View.ViewWidth / 3;
		final int heightResolution = m_View.ViewHeight / 3;
		
		// Center.
		if (x > widthResolution + viewOrigin && (x < widthResolution * 2 + viewOrigin) &&
		   (y > heightResolution + viewOrigin) && (y < heightResolution * 2 + viewOrigin)) {
			m_CurrentTransformationState = e_STATE.TRANSLATE;
		}
		
		// Left center.
		else if (x > viewOrigin && x < widthResolution + viewOrigin &&
				 y > heightResolution + viewOrigin && y < heightResolution * 2 + viewOrigin ||
		// Right center.
				 x > widthResolution * 2 + viewOrigin && x < widthResolution * 3 + viewOrigin &&
				 y > heightResolution + viewOrigin && y < heightResolution * 2 + viewOrigin ||
		// up center.
				 x > widthResolution + viewOrigin && x < widthResolution * 2 + viewOrigin &&
				 y > viewOrigin && y < heightResolution + viewOrigin ||
		// down center.
				 x > widthResolution + viewOrigin && x < widthResolution * 2 + viewOrigin &&
				 y > heightResolution * 2 + viewOrigin && y < heightResolution * 3 + viewOrigin) {
			m_CurrentTransformationState = e_STATE.SCALE;
		}
		
		// top left
		else if (x > viewOrigin && x < widthResolution + viewOrigin &&
				 y > viewOrigin && y < heightResolution + viewOrigin ||
		// top right.
				 x > widthResolution * 2 + viewOrigin && x < widthResolution * 3 + viewOrigin &&
				 y > viewOrigin && y < heightResolution + viewOrigin ||
		// bottom right.
				 x > widthResolution * 2 + viewOrigin && x < widthResolution * 3 + viewOrigin &&
				 y > heightResolution * 2 + viewOrigin && x < heightResolution * 3 + viewOrigin ||
		// bottom left.
				 x > viewOrigin && x < widthResolution + viewOrigin &&
				 y > heightResolution * 2 + viewOrigin && y < heightResolution * 3 + viewOrigin) {
			m_CurrentTransformationState = e_STATE.ROTATE;
		}
		
		else {
			m_CurrentTransformationState = e_STATE.NONE;
		}
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse clicked",
				mouseEvent.getX(),
				mouseEvent.getY()));
	}

	@Override
	public void mouseEntered(final MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse entered",
				mouseEvent.getX(),
				mouseEvent.getY()));
		
	}

	@Override
	public void mouseExited(final MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse exited",
				mouseEvent.getX(),
				mouseEvent.getY()));
	}

	@Override
	public void mousePressed(final MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse pressed",
				mouseEvent.getX(),
				mouseEvent.getY()));
		m_StartPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
		setTransformationTypeByPressedPointCoordinates(m_StartPoint);
		// Find the required transformation according to start point.
		
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
		
		/*
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
		*/
	}

	@Override
	public void mouseReleased(final MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse released",
				mouseEvent.getX(),
				mouseEvent.getY()));
		m_AccumulatedTransformation = m_AccumulatedTransformation.compose(m_CurrentTransformation);
	}

	private void setTranslateTransform(final MouseEvent mouseEvent) {
		m_CurrentTransformation = new Transformation(
				Matrix.translate(
						mouseEvent.getX() - m_StartPoint.X(),
						mouseEvent.getY() - m_StartPoint.Y()));
	}
	
	private void setScaleTransform(final MouseEvent mouseEvent) {
		final double centerX = m_View.ViewWidth / 2 + View.g_WINDOW_MARGIN;
		final double centerY = m_View.ViewHeight / 2 + View.g_WINDOW_MARGIN;
		final ColumnVector centerVector = new ColumnVector(new double[] {centerX, centerY});
		final ColumnVector destinationVector = new ColumnVector(new double[] {mouseEvent.getX(), mouseEvent.getY()});
		final ColumnVector sourceVector = new ColumnVector(new double[] {m_StartPoint.X(), m_StartPoint.Y()});
		final double scaleFactor = destinationVector.minus(centerVector).vectorSize() / 
								   sourceVector.minus(centerVector).vectorSize();
		m_CurrentTransformation = new Transformation(
			 Matrix.translate(centerX, centerY).times
			(Matrix.scale(scaleFactor, scaleFactor).times
			(Matrix.translate(-centerX, -centerY))));
	}
	
	@Override
	public void mouseDragged(final MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse dragged",
				mouseEvent.getX(),
				mouseEvent.getY()));
		Logger.Debug(m_CurrentTransformationState.toString());
		if (e_STATE.TRANSLATE == m_CurrentTransformationState) {
			setTranslateTransform(mouseEvent);
		}
		
		else if (e_STATE.SCALE == m_CurrentTransformationState) {
			setScaleTransform(mouseEvent);
		}
		
		else if (e_STATE.ROTATE == m_CurrentTransformationState) {
			
		}
		
		else {
			return;
		}
		
		this.repaint();
	}

	@Override
	public void mouseMoved(final MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse moved",
				mouseEvent.getX(),
				mouseEvent.getY()));
	}
}
