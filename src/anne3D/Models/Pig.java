package anne3D.Models;
import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import anne3D.Demo.WavefrontObjectLoader_DisplayList;
import anne3D.utilities.Logger;

final public class Pig {
	private static WavefrontObjectLoader_DisplayList Pig;
	
	static {
        Pig = new WavefrontObjectLoader_DisplayList("resources\\pig\\pig.obj");
	}
	
	public static void draw(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glColor3f(1.0f, 1.0f, 1.0f);
        Pig.drawModel(gl);     
        gl.glDisable(GL2.GL_TEXTURE_2D);
	}
}
