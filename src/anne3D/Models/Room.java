package anne3D.Models;

import java.io.File;
import java.io.IOException;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import anne3D.Camera.Camera;

final public class Room {
	
	private static final float g_DiffBetweenFloorAndCeiling = 10;
	public static final float g_FloorHeight = -1f;
	public static final float g_CeilingHeight = g_FloorHeight + g_DiffBetweenFloorAndCeiling;
	private static Texture texture;
	
	static {
        try {
    		String textureFilePath = "resources/Wall/wall.jpg";
    		texture = TextureIO.newTexture(new File(textureFilePath), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
	
	public static void draw(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		Camera camera = Camera.getInstance();
		gl.glPushAttrib(GL2.GL_CURRENT_BIT);
		// Floor.
		gl.glEnable(GL2.GL_TEXTURE_2D); /**/
		texture.bind(gl);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glColor3f((float)camera.W.X, (float)camera.W.Y, (float)camera.W.Z);
        gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(10, g_FloorHeight, 10);
		
		gl.glTexCoord2f(2f, 0.0f);
		gl.glVertex3f(10, g_FloorHeight, -10);
		
		gl.glTexCoord2f(2f, 1.0f);
		gl.glVertex3f(-10, g_FloorHeight, 10);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-10, g_FloorHeight, -10);
		
		gl.glTexCoord2f(2f, 0.0f);
		gl.glVertex3f(-10, g_FloorHeight, 10);
		
		gl.glTexCoord2f(2f, 1.0f);
		gl.glVertex3f(10, g_FloorHeight, -10);
		
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glEnd();
		texture.disable(gl);
		
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
		
		// Wall #3
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f((float)camera.V.Z, (float)camera.V.Z, (float)camera.V.Z);
		gl.glVertex3f(-10, g_FloorHeight, -10);
		gl.glVertex3f(10, g_FloorHeight, -10);
		gl.glVertex3f(-10, g_CeilingHeight, -10);
		gl.glEnd();
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glVertex3f(10, g_FloorHeight, -10);
		gl.glVertex3f(10, g_CeilingHeight, -10);
		gl.glVertex3f(-10, g_CeilingHeight, -10);
		gl.glEnd();
		
		
		
		gl.glPopAttrib();
	}
}
