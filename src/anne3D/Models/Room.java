package anne3D.Models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import anne3D.Camera.Camera;

final public class Room {
	
	private static final float g_DiffBetweenFloorAndCeiling = 10;
	public static final float g_FloorHeight = -1f;
	public static final float g_CeilingHeight = g_FloorHeight + g_DiffBetweenFloorAndCeiling;
	
	public static void draw(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		Camera camera = Camera.getInstance();
		gl.glPushAttrib(GL2.GL_CURRENT_BIT);
		// Floor.
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f((float)camera.U.X, (float)camera.U.Y, (float)camera.U.Z);
		gl.glVertex3f(10, g_FloorHeight, 10);
		gl.glVertex3f(10, g_FloorHeight, -10);
		gl.glVertex3f(-10, g_FloorHeight, 10);
		gl.glEnd();
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glVertex3f(-10, g_FloorHeight, -10);
		gl.glVertex3f(-10, g_FloorHeight, 10);
		gl.glVertex3f(10, g_FloorHeight, -10);
		gl.glEnd();
		
		// Ceiling.
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f((float)camera.W.X, (float)camera.W.Y, (float)camera.W.Z);
		gl.glVertex3f(10, g_CeilingHeight, 10);
		gl.glVertex3f(10, g_CeilingHeight, -10);
		gl.glVertex3f(-10, g_CeilingHeight, 10);
		gl.glEnd();
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glVertex3f(-10, g_CeilingHeight, 10);
		gl.glVertex3f(-10, g_CeilingHeight, -10);
		gl.glVertex3f(10, g_CeilingHeight, -10);
		gl.glEnd();
		
		// Wall #1
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f((float)camera.V.X, (float)camera.V.Y, (float)camera.V.Z);
		gl.glVertex3f(10, g_FloorHeight, 10);
		gl.glVertex3f(10, g_FloorHeight, -10);
		gl.glVertex3f(10, g_CeilingHeight, -10);
		gl.glEnd();
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glVertex3f(10, g_FloorHeight, 10);
		gl.glVertex3f(10, g_CeilingHeight, -10);
		gl.glVertex3f(10, g_CeilingHeight, 10);
		gl.glEnd();
		
		// Wall #2
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f((float)camera.V.X, (float)camera.V.Y, (float)camera.V.Z);
		gl.glVertex3f(-10, g_FloorHeight, 10);
		gl.glVertex3f(-10, g_FloorHeight, -10);
		gl.glVertex3f(-10, g_CeilingHeight, -10);
		gl.glEnd();
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glVertex3f(-10, g_FloorHeight, 10);
		gl.glVertex3f(-10, g_CeilingHeight, -10);
		gl.glVertex3f(-10, g_CeilingHeight, 10);
		gl.glEnd();
		
		gl.glPopAttrib();
	}
}
