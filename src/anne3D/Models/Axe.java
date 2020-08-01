package anne3D.Models;
import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import anne3D.Demo.WavefrontObjectLoader_DisplayList;

/*
 * Axe model representation from the class examples.
 */
final public class Axe {
	private static Texture AxeTexture;
	private static WavefrontObjectLoader_DisplayList Axe;
	
	// Initialize static texture and display list.
	static {
        try {
    		String axeTextureFilePath = "resources/axe/axe.jpg";
    		AxeTexture = TextureIO.newTexture(new File(axeTextureFilePath), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        Axe = new WavefrontObjectLoader_DisplayList("resources\\axe\\axe_v1.obj");
	}
	
	public static void draw(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_TEXTURE_2D);
		AxeTexture.bind(gl);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        Axe.drawModel(gl);     
        gl.glDisable(GL2.GL_TEXTURE_2D);
	}
}
