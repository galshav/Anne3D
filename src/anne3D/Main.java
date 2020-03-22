package anne3D;
import anne3D.math.Matrix;
import anne3D.math.ColumnVector;
import anne3D.utilities.Logger;
import java.lang.Exception;

final public class Main {
	
	public static void main(final String[] args) {
		try {
			Logger.Info("Starting engine.");
			ColumnVector vector = new ColumnVector(new double[] {-7, 10, 6});
			Logger.Debug("debugbreak.");
		}
		
		catch (Exception error) {
			Logger.Error(error.toString());
			// Can not continue execution, terminate program.
			throw error;
		}
	}
}