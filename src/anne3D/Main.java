package anne3D;
import anne3D.math.Matrix;
import anne3D.math.Matrix2;
import anne3D.utilities.Logger;
import anne3D.view.EngineCanvas;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.Exception;

final public class Main {
	
	final private static String g_TITLE = "";
	
	public static void main(final String[] args) throws Exception {
		try {
			Matrix2 mat = new Matrix2(new double[] {1,2,3,4});
			Logger.Info("Starting engine.");
			Frame frame = new Frame(g_TITLE);
			EngineCanvas engineCanvas = new EngineCanvas(600, 800);
			frame.add(engineCanvas);
			WindowAdapter windowAdapter = new WindowAdapter() {
				public void windowClosing(WindowEvent windowEvent) {
					Logger.Debug("Window closed.");
					System.exit(0);
				}
			};
			
			frame.addWindowListener(windowAdapter);
			frame.pack();
			frame.setVisible(true);
			Logger.Debug("debugbreak.");
		}
		
		catch (final Exception error) {
			Logger.Error("Unhandled exception.");
			error.printStackTrace();
			// Can not continue execution, terminate program.
			throw error;
		}
	}
}

class a{
	
}
