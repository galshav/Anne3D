package anne3D.Demo;

import java.io.File;
import java.io.IOException;

import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import anne3D.Camera.Camera;
import anne3D.Models.Axe;
import anne3D.Models.Baby;
import anne3D.Models.Pyramid;
import anne3D.Models.Room;
import anne3D.utilities.Logger;

public class Demo extends KeyAdapter implements GLEventListener, KeyListener{
	
	static private GLU g_glu;
	static private GLUT g_glut;
	
	private Texture m_BabyTexture;
	private Texture m_AxeTexture;
    private WavefrontObjectLoader_DisplayList m_Baby;
    private WavefrontObjectLoader_DisplayList m_Axe;
	
	// Move camera properties.
	private final double m_MovementFactor = 0.02;
	private double m_MoveForwardSpeed = 0;
	private double m_MoveBackwardSpeed = 0;
	private double m_MoveRightSpeed = 0;
	private double m_MoveLeftSpeed = 0;
	private double m_MoveUpSpeed = 0;
	private double m_MoveDownSpeed = 0;
	
	// Rotate camera properties.
	private final double m_RotationFactor = 0.5;
	private double m_LookUpSpeed = 0;
	private double m_LookDownSpeed = 0;
	private double m_LookRightSpeed = 0;
	private double m_LookLeftSpeed = 0;
	private double m_TiltRightSpeed = 0;
	private double m_TiltLeftSpeed = 0;
	
	@Override
	public void init(GLAutoDrawable drawable) {
		// Initialize camera singleton.
		final Camera camera = Camera.getInstance();
		final GL2 gl = drawable.getGL().getGL2();
		g_glu = new GLU();
		g_glut = new GLUT();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearDepth(1.0f); // Depth Buffer Setup
		gl.glEnable(GL2.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL2.GL_LEQUAL); // The Type Of Depth Testing To Do
		//gl.glEnable(GL2.GL_TEXTURE_2D);
		// Really Nice Perspective Calculations
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

		// keyboard
	    if (drawable instanceof Window) {
	        final Window window = (Window) drawable;
	        window.addKeyListener(this);
	    } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
	        final java.awt.Component comp = (java.awt.Component) drawable;
	        new AWTKeyAdapter(this, drawable).addTo(comp);
	    }
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}

	private void moveCamera() {
		final Camera camera = Camera.getInstance();
		camera.move(Camera.AXIS.U, m_MoveRightSpeed);
		camera.move(Camera.AXIS.U, m_MoveLeftSpeed);
		camera.move(Camera.AXIS.V, m_MoveUpSpeed);
		camera.move(Camera.AXIS.V, m_MoveDownSpeed);
		camera.move(Camera.AXIS.W, m_MoveForwardSpeed);
		camera.move(Camera.AXIS.W, m_MoveBackwardSpeed);
	}
	
	private void rotateCamera() {
		final Camera camera = Camera.getInstance();
		camera.rotate(Camera.AXIS.U, m_LookUpSpeed);
		camera.rotate(Camera.AXIS.U, m_LookDownSpeed);
		camera.rotate(Camera.AXIS.V, m_LookRightSpeed);
		camera.rotate(Camera.AXIS.V, m_LookLeftSpeed);
		camera.rotate(Camera.AXIS.W, m_TiltRightSpeed);
		camera.rotate(Camera.AXIS.W, m_TiltLeftSpeed);
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		Camera camera = Camera.getInstance();
		moveCamera();
		rotateCamera();
				
		g_glu.gluLookAt(
			// Position of camera.
			camera.Position.X,
			camera.Position.Y,
			camera.Position.Z,
			// Where to look at.
			camera.Position.X - camera.W.X,
			camera.Position.Y - camera.W.Y,
			camera.Position.Z - camera.W.Z,
			// Up vector.
			camera.V.X, camera.V.Y, camera.V.Z);
		
		// First pyramid.
		gl.glPushMatrix();
		Pyramid.draw(drawable);
		gl.glPopMatrix();
		
		// Second pyramid.
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 2.0f, 0.0f);
		Pyramid.draw(drawable);
		gl.glPopMatrix();
		
		// Third pyramid.
		gl.glPushMatrix();
		gl.glRotated(45.0f, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, 4.0f, 0.0f);
		Pyramid.draw(drawable);
		gl.glPopMatrix();
		
		// Draw room.
		gl.glPushMatrix();
		Room.draw(drawable);
		gl.glPopMatrix();
				
		// Draw baby.
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, -10.0f);
		gl.glScalef(0.1f, 0.1f, 0.1f);
		gl.glRotated(-90.0f, 1.0f, 0.0f, 0.0f);
		Baby.draw(drawable);
		gl.glPopMatrix();
		
		// Draw axe.
		gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, -2.0f);
        gl.glRotatef(0.0f, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glScalef(50, 50, 50);
		Axe.draw(drawable);
		gl.glPopMatrix();
		

		
		gl.glFlush();
	}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		float h = (float) height / (float) width;
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		if (h < 1) {
			gl.glFrustum(-1.0f, 1.0f, -h, h, 1.0f, 60.0f);
		}
		
		else {
			h = 1.0f / h;
			gl.glFrustum(-h, h, -1.0f, 1.0f, 1.0f, 60.0f);
		}

		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}
	
	public void keyPressed(KeyEvent e) {
		Logger.Debug(String.format("key pressed: %s", e.getKeyChar()));
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
			
		case KeyEvent.VK_W:
			m_MoveForwardSpeed = -m_MovementFactor;
			break;
			
		case KeyEvent.VK_S:
			m_MoveBackwardSpeed = m_MovementFactor;
			break;
			
		case KeyEvent.VK_A:
			m_MoveLeftSpeed = -m_MovementFactor;
			break;
			
		case KeyEvent.VK_D:
			m_MoveRightSpeed = m_MovementFactor;
			break;
			
		case KeyEvent.VK_E:
			m_MoveUpSpeed = m_MovementFactor;
			break;
			
		case KeyEvent.VK_Q:
			m_MoveDownSpeed = -m_MovementFactor;
			break;
			
		case KeyEvent.VK_I:
			m_LookUpSpeed = m_RotationFactor;
			break;
			
		case KeyEvent.VK_K:
			m_LookDownSpeed = -m_RotationFactor;
			break;
			
		case KeyEvent.VK_L:
			m_LookRightSpeed = -m_RotationFactor;
			break;
			
		case KeyEvent.VK_J:
			m_LookLeftSpeed = m_RotationFactor;
			break;
			
		case KeyEvent.VK_O:
			m_TiltRightSpeed = m_RotationFactor;
			break;
			
		case KeyEvent.VK_U:
			m_TiltLeftSpeed = -m_RotationFactor;
			break;
			
		default:
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_W:
			m_MoveForwardSpeed = 0;
			break;
			
		case KeyEvent.VK_S:
			m_MoveBackwardSpeed = 0;
			break;
		
		case KeyEvent.VK_D:
			m_MoveRightSpeed = 0;
			break;
	
		case KeyEvent.VK_A:
			m_MoveLeftSpeed = 0;
			break;
		
		case KeyEvent.VK_E:
			m_MoveUpSpeed = 0;
			break;
			
		case KeyEvent.VK_Q:
			m_MoveDownSpeed = 0;
			break;
			
		case KeyEvent.VK_I:
			m_LookUpSpeed = 0;
			break;
			
		case KeyEvent.VK_K:
			m_LookDownSpeed = 0;
			break;
			
		case KeyEvent.VK_L:
			m_LookRightSpeed = 0;
			break;
			
		case KeyEvent.VK_J:
			m_LookLeftSpeed = 0;
			break;
			
		case KeyEvent.VK_O:
			m_TiltRightSpeed = 0;
			break;
			
		case KeyEvent.VK_U:
			m_TiltLeftSpeed = 0;
			break;
		}
	}
}
