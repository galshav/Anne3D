package anne3D;
import anne3D.utilities.Logger;
import java.lang.Exception;

final public class Main {
	
	public static void main(final String[] args) {
		try {
			Logger.Info("Starting engine.");
			Logger.Debug("debugbreak.");
		}
		
		catch (Exception error) {
			Logger.Error(error.toString());
			// Can not continue execution, terminate program.
			throw error;
		}
	}
}
