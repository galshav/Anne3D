package anne3D.Demo;

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
import anne3D.Camera.Camera;
import anne3D.math.Matrix;
import anne3D.utilities.Logger;

public class Demo extends KeyAdapter implements GLEventListener, KeyListener{
	
	static private GLU g_glu;
	static private GLUT g_glut;
	float rotateT = 0.0f;
	
	@Override
	public void init(GLAutoDrawable drawable) {
		Camera camera = Camera.getInstance();
		GL2 gl = drawable.getGL().getGL2();
		g_glu = new GLU();
		g_glut = new GLUT();
		gl.glClearDepth(1.0f); // Depth Buffer Setup
		gl.glEnable(GL2.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL2.GL_LEQUAL); // The Type Of Depth Testing To Do

		// Really Nice Perspective Calculations
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

		// keyboard
	    if (drawable instanceof Window) {
	        Window window = (Window) drawable;
	        window.addKeyListener(this);
	    } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
	        java.awt.Component comp = (java.awt.Component) drawable;
	        new AWTKeyAdapter(this, drawable).addTo(comp);
	    }
		
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		Camera camera = Camera.getInstance();
		//camera.Position = camera.Position.plus(new Matrix(new double[][] {{0.0},{0.0},{-0.02},{0}}));
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g_glu.gluLookAt(
				// Position of camera.
				camera.Position.X,
				camera.Position.Y,
				camera.Position.Z,
				// Where to look at.
				camera.Position.X,
				camera.Position.Y,
				camera.Position.Z - 0.1,
				// Up vector.
				0, 1, 0);
		
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		
		gl.glVertex3f(1.0f, 0.0f, -5.0f);
		//gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 0.0f, -5.0f);
		//gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(0.0f, 1.5f, -5.0f);
		gl.glEnd();
		
		gl.glFlush();
	}
	
	public void displayOrig(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
		
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -10.0f);

		gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
		gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);

		gl.glBegin(GL2.GL_TRIANGLES);

		// Front
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);

		// Right Side Facing Front
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, -1.0f, -1.0f);

		// Left Side Facing Front
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3f(0.0f, -1.0f, -1.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);

		// Bottom
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.1f, 0.1f, 0.1f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.2f, 0.2f, 0.2f);
		gl.glVertex3f(0.0f, -1.0f, -1.0f);

		gl.glEnd();
		
		rotateT += 0.05f;
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();

		float h = (float) height / (float) width;

		gl.glMatrixMode(GL2.GL_PROJECTION);

		gl.glLoadIdentity();

		if (h < 1)
			gl.glFrustum(-1.0f, 1.0f, -h, h, 1.0f, 60.0f);
		else {
			h = 1.0f / h;
			gl.glFrustum(-h, h, -1.0f, 1.0f, 1.0f, 60.0f);
		}

		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}
	
	public void keyPressed(KeyEvent e) {
		Logger.Debug(String.format("key pressed: %s", e.getKeyChar()));
		Camera camera = Camera.getInstance();
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
			
		case KeyEvent.VK_W:
			camera.move(Camera.AXIS.Z, -0.05);
			break;
			
		case KeyEvent.VK_S:
			camera.move(Camera.AXIS.Z, 0.05);
			break;
			
		case KeyEvent.VK_A:
			camera.move(Camera.AXIS.X, -0.05);
			break;
			
		case KeyEvent.VK_D:
			camera.move(Camera.AXIS.X, 0.05);
			break;
			
		case KeyEvent.VK_E:
			camera.move(Camera.AXIS.Y, -0.05);
			break;
			
		case KeyEvent.VK_Q:
			camera.move(Camera.AXIS.Y, 0.05);
			break;
			
		default:
			break;
		}
	}
}
