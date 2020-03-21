package anne3D;
import anne3D.math.Matrix;
import anne3D.utilities.Logger;
import java.lang.Exception;

final public class Main {
	public static void main(final String[] args) {
		try {
			Logger.Info("Starting engine.");
			final double[][] array = 
				{{1, 2},
				 {3, 4}};
			Matrix matrix = new Matrix(array);
		}
		
		catch (Exception error) {
			Logger.Error(error.toString());
			// Can not continue execution, terminate program.
			throw error;
		}
	}
}