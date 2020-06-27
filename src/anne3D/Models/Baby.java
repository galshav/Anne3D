package anne3D.Models;
import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import anne3D.Demo.WavefrontObjectLoader_DisplayList;
import anne3D.utilities.Logger;

final public class Baby {
	private static Texture BabyTexture;
	private static WavefrontObjectLoader_DisplayList Baby;
	
	static {
        try {
    		String babyTextureFilePath = "resources/baby/StandingBabyDiffuseMap.jpg";
    		BabyTexture = TextureIO.newTexture(new File(babyTextureFilePath), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        Baby = new WavefrontObjectLoader_DisplayList("resources\\baby\\baby.obj");
	}
	
	public static void draw(GLAutoDrawable drawable) {
		try {
			final GL2 gl = drawable.getGL().getGL2();
			gl.glEnable(GL2.GL_TEXTURE_2D);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
	        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
	        gl.glTexParameteri ( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT );
	        gl.glTexParameteri( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT );
	        BabyTexture.bind(gl);
	        Baby.drawModel(gl);     
	        gl.glDisable(GL2.GL_TEXTURE_2D);
		}
		
		catch (Exception e) {
			Logger.Debug("exception");
		}
		
	}
}
