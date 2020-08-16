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
	private static Texture texture2;
	private static Texture texture3;
	
	static {
        try {
    		String textureFilePath = "resources/Wall/anne.jpeg";
    		texture = TextureIO.newTexture(new File(textureFilePath), true);
    		String textureFilePath2 = "resources/Wall/anne2.jpg";
    		texture2 = TextureIO.newTexture(new File(textureFilePath2), true);
    		String textureFilePath3 = "resources/Wall/anne3.jpg";
    		texture3 = TextureIO.newTexture(new File(textureFilePath3), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
	
	public static void draw(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		float material[] = {0.8f,0.8f,0.8f,1.0f};
    	float position[] = {0.0f,0.0f,-9.0f,1.0f};
    	gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0);
    	gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, material, 0);
    	
		gl.glPushAttrib(GL2.GL_CURRENT_BIT);
		// Floor.
		gl.glEnable(GL2.GL_TEXTURE_2D); /**/
		texture.bind(gl);
		gl.glNormal3f(0.0f, 1.0f, 0.0f);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glBegin(GL2.GL_TRIANGLES);
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
		gl.glEnable(GL2.GL_TEXTURE_2D); /**/
		texture.bind(gl);
		gl.glNormal3f(0.0f, -1.0f, 0.0f);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(10, g_CeilingHeight, 10);
		gl.glTexCoord2f(2f, 0.0f);
		gl.glVertex3f(10, g_CeilingHeight, -10);
		gl.glTexCoord2f(2f, 1.0f);
		gl.glVertex3f(-10, g_CeilingHeight, 10);
		gl.glEnd();
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-10, g_CeilingHeight, 10);
		gl.glTexCoord2f(2f, 0.0f);
		gl.glVertex3f(-10, g_CeilingHeight, -10);
		gl.glTexCoord2f(2f, 1.0f);
		gl.glVertex3f(10, g_CeilingHeight, -10);
		gl.glEnd();
		texture.disable(gl);
		
		// Right wall
		gl.glEnable(GL2.GL_TEXTURE_2D); /**/
		texture2.bind(gl);
		gl.glNormal3f(-1.0f, 0.0f, 0.0f);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(10, g_FloorHeight, 10);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(10, g_FloorHeight, -10);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(10, g_CeilingHeight, -10);
		gl.glEnd();
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(10, g_FloorHeight, 10);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(10, g_CeilingHeight, -10);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(10, g_CeilingHeight, 10);
		gl.glEnd();
		texture2.disable(gl);
		
		// Left wall.
		gl.glEnable(GL2.GL_TEXTURE_2D); /**/
		texture3.bind(gl);
		gl.glNormal3f(1.0f, 0.0f, 0.0f);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-10, g_FloorHeight, 10);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-10, g_FloorHeight, -10);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-10, g_CeilingHeight, -10);
		gl.glEnd();
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-10, g_FloorHeight, 10);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-10, g_CeilingHeight, -10);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-10, g_CeilingHeight, 10);
		gl.glEnd();
		texture3.disable(gl);
		
		// Front wall.
		gl.glEnable(GL2.GL_TEXTURE_2D); /**/
		texture.bind(gl);
		gl.glNormal3f(0.0f, 0.0f, 1.0f);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-10, g_FloorHeight, -10);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(10, g_FloorHeight, -10);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-10, g_CeilingHeight, -10);
		gl.glEnd();
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(10, g_FloorHeight, -10);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(10, g_CeilingHeight, -10);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-10, g_CeilingHeight, -10);
		gl.glEnd();
		texture.disable(gl);
		
		gl.glPopAttrib();
	}
}
