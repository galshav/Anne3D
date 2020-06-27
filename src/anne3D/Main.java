package anne3D;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.Exception;

import anne3D.Demo.Demo;
import anne3D.canvas.EngineCanvas;
import anne3D.configurations.Scene3D;
import anne3D.configurations.View3D;
import anne3D.utilities.Logger;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import anne3D.Demo.WavefrontObjectLoader_DisplayList;

final public class Main {
	
	final public static boolean g_DEBUG             = true;
	final private static int 	g_EXIT_SUCCESS      = 0;
	final private static int 	g_EXIT_ERROR        = -1;
	final private static float  g_VERSION           = 0.1f;
	final private static String g_EXCEPTION_MESSAGE = "Unhandled exception.";
	final private static String g_TITLE             = String.format("Anne3D game engine v1.0", g_VERSION);
	
	// TODO: get scene and view file from main args.
	public static void main(final String[] args) throws Exception {
		try {
			WavefrontObjectLoader_DisplayList obj;
			Logger.Info("Starting engine.");
			//launch(args);
			launch_jogl(args);
		}
		
		catch (final Exception error) {
			Logger.Error(g_EXCEPTION_MESSAGE);
			error.printStackTrace();
			// Can not continue execution, terminate program.
			System.exit(g_EXIT_ERROR);
		}
	}
	
	public static void launch_jogl(final String[] args) {
		Frame frame = new Frame(g_TITLE);
		frame.setSize(800, 600);
		final Animator animator = new Animator();
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				new Thread(new Runnable() {
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		
		GLCanvas canvas = new GLCanvas();
		animator.add(canvas);
		canvas.addGLEventListener(new Demo());
		frame.add(canvas, java.awt.BorderLayout.CENTER);
		frame.validate();
		frame.setVisible(true);
		animator.start();
		canvas.requestFocus();
	}
	
	@SuppressWarnings("unused")
	private static void launch(final String[] args) throws IOException {
		
		Frame frame = new Frame(g_TITLE);
		
		FileDialog fd = new FileDialog(frame, "Choose a scene file", FileDialog.LOAD);
		fd.setDirectory("C:\\");
		fd.setFile("*.scn");
		fd.setVisible(true);
		String sceneFilePath = String.format("%s%s", fd.getDirectory(), fd.getFile());
		if (sceneFilePath == null)
		  System.out.println("You cancelled the choice");
		else
		  System.out.println("You chose " + sceneFilePath);
		
		new FileDialog(frame, "Choose a view file", FileDialog.LOAD);
		fd.setDirectory("C:\\");
		fd.setFile("*.viw");
		fd.setVisible(true);
		String viewFilePath = String.format("%s%s", fd.getDirectory(), fd.getFile());

		if (viewFilePath == null)
		  System.out.println("You cancelled the choice");
		else
		  System.out.println("You chose " + viewFilePath);
		
		
		Scene3D scene3D = Scene3D.loadSceneFromFile(sceneFilePath);
		View3D view3D = View3D.loadViewFromFile(viewFilePath);
		EngineCanvas engineCanvas = new EngineCanvas(
				scene3D,
				view3D,
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
