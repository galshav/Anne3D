package anne3D.tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anne3D.math.RowVector;
import anne3D.math.ColumnVector;
import anne3D.math.Matrix;

public class VectorTests {
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void rowVector_createRowVectorAsMatrix() {
		assertEquals(
				new Matrix(new double[][] {{1, 2, 3}}),
				new RowVector(new double[] {1, 2, 3}));
	}
	
	@Test
	void columnVector_createColumnVectorAsMatrix() {
		assertEquals(
				new Matrix(new double[][] {
					{1},
					{2},
					{3}}), 
				new ColumnVector(new double[] {1,2,3}));
	}
}