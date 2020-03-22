package anne3D.tests;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.SecondaryLoop;

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
	
	@Test
	void addColumnVector_add2ColumnsVectors() {
		assertEquals(
				new ColumnVector(new double[] {2, 2, 2}),
				(new ColumnVector(new double[] {1, 1, 1}))
					.plus(new ColumnVector(new double[] {1, 1, 1})));
	}
	
	@Test
	void addColumnVector_add2columnsWithDifferentDimensions_shuoldFail() {
		assertThrows(RuntimeException.class, () -> {
			ColumnVector firstVector = new ColumnVector(new double[] {1, 1, 1});
			ColumnVector secondVector = new ColumnVector(new double[] {1, 1, 1, 1});
			firstVector.plus(secondVector);
		});
	}
}