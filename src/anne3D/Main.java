package anne3D;

import anne3D.canvas.EngineCanvas;
import anne3D.configurations.Scene;
import anne3D.configurations.View;
import anne3D.utilities.Logger;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.Exception;

final public class Main {
	
	final private static float g_VERSION = 0.1f;
	final private static String g_TITLE = String.format("Anne3D game engine v1.0", g_VERSION);
	
	// TODO: get scene and view file from main args.
	public static void main(final String[] args) throws Exception {
		try {
			Logger.Info("Starting engine.");
			Frame frame = new Frame(g_TITLE);
			EngineCanvas engineCanvas = new EngineCanvas(
					Scene.loadSceneFromFile("c:\\users\\galsh\\desktop\\rec.scn"),
					View.loadViewFromFile("c:\\users\\galsh\\desktop\\example.viw"));
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
