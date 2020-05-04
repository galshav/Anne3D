package anne3D.canvas;

import static org.junit.Assert.assertNotNull;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import anne3D.Main;
import anne3D.configurations.Scene3D;
import anne3D.configurations.View;
import anne3D.configurations.View3D;
import anne3D.math.Edge;
import anne3D.math.Matrix;
import anne3D.math.Point;
import anne3D.math.Transformation;
import anne3D.math.Vector2;
import anne3D.utilities.Logger;

final public class EngineCanvas extends Canvas implements MouseListener, MouseMotionListener, KeyListener {
	private static enum e_STATE {
		TRANSLATE,
		SCALE,
		ROTATE,
		NONE
	}
	
	private Boolean g_First = true;
	private char m_RotationAxis = 'z';
	private static final long serialVersionUID = 1L;
	private Point m_StartPoint;
	private View3D m_View;
	private Scene3D m_Scene;
	private Transformation m_CurrentTransformation;
	private Transformation m_AccumulatedTransformation;
	private e_STATE m_CurrentTransformationState;
	
	public EngineCanvas(final Scene3D scene, final View3D view) {
		Objects.requireNonNull(scene, "scene argument can not be null.");
		Objects.requireNonNull(view, "view argument can not be null.");
		m_CurrentTransformation = new Transformation(Matrix.identity(4));
		m_AccumulatedTransformation = new Transformation(Matrix.identity(4));
		m_View = view;
		m_Scene = scene;
		m_CurrentTransformationState = e_STATE.NONE;
		setSize(view.ViewWidth + View.g_WINDOW_MARGIN,
				view.ViewHeight + View.g_WINDOW_MARGIN);
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);
	}
	
	public EngineCanvas(final Scene3D scene, final View3D view, final Color canvasColor) {
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
		graphics.setColor(Color.GREEN);
		paintClipBorder(graphics);
		paintGrid(graphics);
		graphics.setColor(Color.magenta);
		graphics.drawString("Press 'C' for clliping.", 5, 15);
		
		final Transformation modelTransformation = 
				m_CurrentTransformation.compose(
				m_AccumulatedTransformation.compose(
				m_View.CameraTransformation));
		
		final Transformation viewTransformation = 
				m_View.DeviceTransformation.compose(
				m_View.ProjectionTransformation);
		
		final Transformation totalTransformation = 
				viewTransformation.compose(
				modelTransformation);
		
		for (final Edge origEdge : m_Scene.Edges) {
			Point p1 = totalTransformation.applyTransformation(origEdge.GetFirstPoint());
			Point p2 = totalTransformation.applyTransformation(origEdge.GetSecondPoint());
			graphics.drawLine(
					(int)p1.X(),
					(int)p1.Y(),
					(int)p2.X(),
					(int)p2.Y());
		}
		
		if (g_First == true) {
			g_First = false;
			return;
		}
		
		//m_View.ObjectCenter = modelTransformation.applyTransformation(m_View.ObjectCenter);
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
	}

	@Override
	public void mouseReleased(final MouseEvent mouseEvent) {
		Logger.Debug(String.format("[%s] x=%d,y=%d",
				"mouse released",
				mouseEvent.getX(),
				mouseEvent.getY()));
		m_AccumulatedTransformation = m_AccumulatedTransformation.compose(m_CurrentTransformation);
		final Transformation modelTransformation = 
				m_CurrentTransformation.compose(
				m_AccumulatedTransformation.compose(
				m_View.CameraTransformation));
		m_View.ObjectCenter = modelTransformation.applyTransformation(new Point(0, 0, -9.5, 1));
		m_CurrentTransformation = new Transformation(Matrix.identity(4));
		Logger.Debug(String.format("Center:(%f, %f, %f)", m_View.ObjectCenter.X(), m_View.ObjectCenter.Y(), m_View.ObjectCenter.Z()));
	}

	private void setTranslateTransform(final MouseEvent mouseEvent) {
		final double smoothFactor = 400;
		m_CurrentTransformation = new Transformation(
				Matrix.translate(
						(mouseEvent.getX() - m_StartPoint.X()) / smoothFactor,
						(mouseEvent.getY() - m_StartPoint.Y()) / smoothFactor,
						0));
	}
	
	private void setScaleTransform(final MouseEvent mouseEvent) {
		final double centerX = m_View.ViewWidth / 2 + View.g_WINDOW_MARGIN;
		final double centerY = m_View.ViewHeight / 2 + View.g_WINDOW_MARGIN;
		final Vector2 destinationVector = new Vector2(
				mouseEvent.getX() - centerX,
				centerY - mouseEvent.getY());
		final Vector2 sourceVector = new Vector2(
				m_StartPoint.X() - centerX,
				centerY - m_StartPoint.Y());
		final double scaleFactor =
				destinationVector.size() / sourceVector.size();
		Logger.Debug(String.format("%f", scaleFactor));
		final double centerObjectX = m_View.ObjectCenter.X();
		final double centerObjectY = m_View.ObjectCenter.Y();
		final double centerObjectZ = m_View.ObjectCenter.Z();
		m_CurrentTransformation = new Transformation(
			 //Matrix.translate(centerObjectX + 0.5, centerObjectY + 0.5, centerObjectZ + 9.5).times
			(Matrix.scale(scaleFactor, scaleFactor, scaleFactor)));//.times
			//(Matrix.translate(-centerObjectX - 0.5, -centerObjectY - 0.5, centerObjectZ - 9.5))));
	}
	
	private void setRotateTransform(final MouseEvent mouseEvent) {
		final double centerX = m_View.ViewWidth / 2 + View.g_WINDOW_MARGIN;
		final double centerY = m_View.ViewHeight / 2 + View.g_WINDOW_MARGIN;
		final Vector2 destinationVector = new Vector2(
				mouseEvent.getX() - centerX,
				centerY - mouseEvent.getY());
		final Vector2 sourceVector = new Vector2(
				m_StartPoint.X() - centerX,
				centerY - m_StartPoint.Y());
		Logger.Debug(String.format(
				"Dest: %f[deg], Source: %f[deg], delta:%f[deg]",
				destinationVector.angle(),
				sourceVector.angle(),
				destinationVector.angle() - sourceVector.angle()));
		final double angle = 
				destinationVector.angle() - 
				sourceVector.angle();

		Matrix rotationMatrix = null;
		if (m_RotationAxis == 'x') {
			rotationMatrix = Transformation.rotate3DByX(angle);
		}
		else if (m_RotationAxis == 'y') {
			rotationMatrix = Transformation.rotate3DByY(angle);
		}
		
		else {
			rotationMatrix = Transformation.rotate3DByZ(angle);
		}
		
		final double centerObjectX = m_View.ObjectCenter.X();
		final double centerObjectY = m_View.ObjectCenter.Y();
		final double centerObjectZ = m_View.ObjectCenter.Z();
		m_CurrentTransformation = new Transformation(
				Matrix.translate(centerObjectX, centerObjectY, centerObjectZ).times(
				rotationMatrix.times(
				Matrix.translate(-centerObjectX, -centerObjectY, -centerObjectZ))));
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
			setRotateTransform(mouseEvent);
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

	@Override
	public void keyPressed(KeyEvent e) {
		return;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Logger.Debug(String.format("key pressed: %s", e.getKeyChar()));
		m_RotationAxis = e.getKeyChar();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}
}
