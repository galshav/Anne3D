package anne3D.Demo;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

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
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import anne3D.Camera.Camera;
import anne3D.Models.Axe;
import anne3D.Models.Baby;
import anne3D.Models.Entity;
import anne3D.Models.Pig;
import anne3D.Models.Pyramid;
import anne3D.Models.Room;
import anne3D.math.Vector2;
import anne3D.math.Vector3;
import anne3D.utilities.Logger;

public class Demo extends KeyAdapter implements GLEventListener, KeyListener{
	
	static private GLU g_glu;
	static private GLUT g_glut;
    
    // Dynamic lists of entities to be drawn.
    private ArrayList<Entity> m_Diamonds;
    private ArrayList<Entity> m_Babies;
	
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
	
	// Diamonds settings.
	private float m_DiamondRotationFactor = 0.2f;
	
	// Game settings.
	private boolean m_Running = true;
	private Random rand = null;
	private long m_TimeStep = 0;
	private TextRenderer m_TextRenderer = null;
	private int m_Score = 0;
	private int m_Lives = 3;
	private int m_NumberOfBabies = 1;
	private static int g_INITIAL_NUMBER_OF_DIAMONDS = 5;
	private int m_NumberOfDiamonds = g_INITIAL_NUMBER_OF_DIAMONDS;
	
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
		gl.glEnable(GL2.GL_TEXTURE_2D);
		// Really Nice Perspective Calculations
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

		rand = new Random();
		m_TimeStep = System.nanoTime();
		m_TextRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
		m_Score = 0;
		m_Lives = 3;
		m_Running = true;
		updateLevel();
		
		// keyboard
	    if (drawable instanceof Window) {
	        final Window window = (Window) drawable;
	        window.addKeyListener(this);
	    } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
	        final java.awt.Component comp = (java.awt.Component) drawable;
	        new AWTKeyAdapter(this, drawable).addTo(comp);
	    }
	}
	
	private void DrawText(
			GLAutoDrawable drawable, 
			final String text,
			final int x,
			final int y,
			final Color color) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glPushAttrib(gl.GL_QUADS);
		m_TextRenderer.beginRendering(drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
		m_TextRenderer.setColor(color);
		m_TextRenderer.draw(text, x, y);
		m_TextRenderer.endRendering();
		gl.glPopAttrib();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}

	private void moveCamera() {
		final Camera camera = Camera.getInstance();		
		if (camera.Position.X > 9) {
			camera.Position.X = 9;
		}
		
		else if (camera.Position.X < -9) {
			camera.Position.X = -9;
		}
		
		else if (camera.Position.Y > 8) {
			camera.Position.Y = 8;
		}
		
		else if (camera.Position.Y < 0) {
			camera.Position.Y = 0;
		}
		
		else if (camera.Position.Z > 9) {
			camera.Position.Z = 9;
		}
		
		else if (camera.Position.Z < -9) {
			camera.Position.Z = -9;
		}
		
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
	
	private void updateDiamonds() {
		for (final Entity diamond : m_Diamonds) {
			diamond.AngleOfRotation += m_DiamondRotationFactor;
		}
	}
	
	private void updateBabies() {
		for (final Entity baby : m_Babies) {
			rand.nextDouble();
			baby.TranslationSettings.X += rand.nextGaussian() / 8;
			baby.TranslationSettings.Z += rand.nextGaussian() / 8;
			if (baby.TranslationSettings.X > 9) {
				baby.TranslationSettings.X = 9;
			}
			
			else if (baby.TranslationSettings.X < -9) {
				baby.TranslationSettings.X = -9;
			}
			
			else if (baby.TranslationSettings.Z > 9) {
				baby.TranslationSettings.Z = 9;
			}
			
			else if (baby.TranslationSettings.Z < -9) {
				baby.TranslationSettings.Z = -9;
			}
		}
	}
	
	private void updateGameLogic(final long deltaTime) {
		moveCamera();
		rotateCamera();
		updateDiamonds();
		updateBabies();
		CheckCollisionWithDiamonds();
		CheckCollisionWithBabies();
	}
	
	private void updateLevel() {
		Camera camera = Camera.getInstance();
		camera.resetCameraView();
		m_Diamonds = new ArrayList<Entity>();
		
		for (int i = 0; i < m_NumberOfDiamonds; ++i) {
			int x = rand.nextInt(20) - 10;
			int z = rand.nextInt(20) - 10;
			m_Diamonds.add(new Pyramid(
					new Vector3(x, 0.0f, z),
					35.0f,
					new Vector3(0.0f, 1.0f, 0.0f)));
		}
				
		m_Babies = new ArrayList<Entity>();
		for (int i = 0; i < m_NumberOfBabies; ++i) {
			m_Babies.add(new Baby(
					new Vector3(0.0f, -1.0f, -9.0f),
					-90.0f,
					new Vector3(1.0f, 0.0f, 0.0f)));
		}
	}
	
	private void renderGraphics(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		Camera camera = Camera.getInstance();
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
		
		// Draw collection of diamonds.
		for (Entity diamond : m_Diamonds) {
			if (diamond.IsActive) {				
				gl.glPushMatrix();
				gl.glTranslated(diamond.TranslationSettings.X, diamond.TranslationSettings.Y, diamond.TranslationSettings.Z);
				gl.glScalef(-0.5f, -0.5f, -0.5f);
				gl.glRotated(diamond.AngleOfRotation, diamond.RotationSettings.X, diamond.RotationSettings.Y, diamond.RotationSettings.Z);
				diamond.draw(drawable);
				gl.glPopMatrix();
			}
		}
		
		// Draw enemy babies.
		for (Entity baby : m_Babies) {
			gl.glPushMatrix();
			gl.glTranslated(baby.TranslationSettings.X, baby.TranslationSettings.Y, baby.TranslationSettings.Z);
			gl.glScalef(0.1f, 0.1f, 0.1f);
			gl.glRotated(baby.AngleOfRotation, baby.RotationSettings.X, baby.RotationSettings.Y, baby.RotationSettings.Z);
			baby.draw(drawable);
			gl.glPopMatrix();
		}
		
		// Draw room.
		gl.glPushMatrix();
		Room.draw(drawable);
		gl.glPopMatrix();
				
		DrawText(drawable, String.format("Level: %d", m_NumberOfBabies), 20, 20, Color.RED);
		gl.glFlush();
	}
	
	private void CheckCollisionWithDiamonds() {
		Camera camera = Camera.getInstance();
		Vector3 cameraPosition = new Vector3(camera.Position.X, camera.Position.Y, camera.Position.Z);
		for (Entity entity : m_Diamonds) {
			if (entity.IsActive) {
				if (cameraPosition.minus(entity.TranslationSettings).size() < 1.5) {
					m_Score += 1;
					entity.IsActive = false;
					m_NumberOfDiamonds--;
				}
			}
		}
		
		if (0 == m_NumberOfDiamonds) {
			m_NumberOfDiamonds = g_INITIAL_NUMBER_OF_DIAMONDS;
			m_NumberOfBabies++;
			updateLevel();
		}
	}
	
	private void CheckCollisionWithBabies() {
		Camera camera = Camera.getInstance();
		Vector3 cameraPosition = new Vector3(camera.Position.X, camera.Position.Y, camera.Position.Z);
		for (Entity entity : m_Babies) {
			Vector3 babyPosition = new Vector3(
					entity.TranslationSettings.X,
					entity.TranslationSettings.Y,
					entity.TranslationSettings.Z);
			if (cameraPosition.minus(babyPosition).size() < 2.5) {
				m_Running = false;
				entity.IsActive = false;
			}
		}
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		if (!m_Running) {
			DrawText(
				drawable,
				"You are dead!",
				drawable.getSurfaceWidth() / 2,
				drawable.getSurfaceHeight() / 2,
				Color.RED);
			return;
		}
		
		//long currentTime = System.nanoTime();
		//long deltaTime = currentTime - m_TimeStep;
		//m_TimeStep = currentTime;
		
		// Update physics.
		updateGameLogic(0);

		// Render logic.
		renderGraphics(drawable);
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
			
		// Proceed to next level.
		case KeyEvent.VK_F2:
			m_NumberOfBabies++;
			m_NumberOfDiamonds = g_INITIAL_NUMBER_OF_DIAMONDS;
			updateLevel();
			break;
			
		// Reset game back to level 1.
		case KeyEvent.VK_R:
			m_NumberOfDiamonds = g_INITIAL_NUMBER_OF_DIAMONDS;
			m_NumberOfBabies = 1;
			updateLevel();
			m_Running = true;
			
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
