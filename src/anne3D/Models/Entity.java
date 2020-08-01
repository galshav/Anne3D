package anne3D.Models;
import com.jogamp.opengl.GLAutoDrawable;

import anne3D.math.Vector3;

public abstract class Entity {
	/* Operation: Draw the entity model to the canvas
	 * Input: drawable - Used to draw entity.
	 * Output: None.
	 */ 
	public abstract void draw(final GLAutoDrawable drawable);
	public Vector3 TranslationSettings = null;
	public double AngleOfRotation = 0;
	public Vector3 RotationSettings = null;
	public boolean IsActive = true;
}
