package anne3D;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.Exception;
import anne3D.canvas.EngineCanvas;
import anne3D.configurations.Scene;
import anne3D.configurations.View;
import anne3D.utilities.Logger;

final public class Main {
	
	final public static boolean g_DEBUG 			= true;
	final private static int 	g_EXIT_SUCCESS 		= 0;
	final private static int 	g_EXIT_ERROR 		= -1;
	final private static float  g_VERSION 			= 0.1f;
	final private static String g_EXCEPTION_MESSAGE = "Unhandled exception.";
	final private static String g_TITLE 			= String.format("Anne3D game engine v1.0", g_VERSION);
	
	// TODO: get scene and view file from main args.
	public static void main(final String[] args) throws Exception {
		try {
			Logger.Info("Starting engine.");
			launch(args);
		}
		
		catch (final Exception error) {
			Logger.Error(g_EXCEPTION_MESSAGE);
			error.printStackTrace();
			// Can not continue execution, terminate program.
			System.exit(g_EXIT_ERROR);
		}
	}
	
	private static void launch(final String[] args) throws IOException {
		
		Frame frame = new Frame(g_TITLE);
		EngineCanvas engineCanvas = new EngineCanvas(
				Scene.loadSceneFromFile("c:\\users\\galsh\\desktop\\example.scn"),
				View.loadViewFromFile("c:\\users\\galsh\\desktop\\example.viw"),
				Color.BLACK);
		frame.add(engineCanvas);
		WindowAdapter windowAdapter = new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				Logger.Debug("Window closed.");
				System.exit(g_EXIT_SUCCESS);
			}
		};
		
		frame.addWindowListener(windowAdapter);
		frame.pack();
		frame.setVisible(true);
	}
}
