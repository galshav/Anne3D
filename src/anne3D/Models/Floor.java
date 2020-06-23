package anne3D.Models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import anne3D.Camera.Camera;

final public class Floor {
	
	public static float g_FloorHeight = -1f;
	
	public static void draw(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glBegin(GL2.GL_QUADS);
		Camera camera = Camera.getInstance();
		gl.glColor3f((float) camera.U.X, (float) camera.U.Y, (float) camera.U.Z);
		gl.glVertex3f(-10, g_FloorHeight, -10);
		gl.glVertex3f(10, g_FloorHeight, -10);
		gl.glVertex3f(10, g_FloorHeight, 10);
		gl.glVertex3f(-10, g_FloorHeight, 10);
		gl.glEnd();
	}
}
