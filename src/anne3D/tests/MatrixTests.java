package anne3D.tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anne3D.math.Matrix;

public class MatrixTests {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void matrixAdd_add2IdentityMatrices() {
		assertEquals(new Matrix(new double[][] {{2, 0},
												{0, 2}}),
				Matrix.identity(2).plus(Matrix.identity(2)));
	}
	
	@Test
	void matrixAdd_add2DifferentDimensionsMatrices_shouldFail() {
		assertThrows(RuntimeException.class, () -> {
			Matrix.identity(2).plus(Matrix.identity(3));
		});
	}
	
	@Test
	void identityMatrix_get1DIdentityMatrix_shouldFail() {
		assertThrows(RuntimeException.class, () -> {
			Matrix.identity(1);
		});
	}

	@Test
	void identityMatrix_get2DIdentityMatrix() {
		assertEquals(
				new Matrix(new double[][] {{1, 0},
										   {0, 1}}),
				Matrix.identity(2));
	}
	
	@Test
	void identityMatrix_get3DIdentityMatrix() {
		assertEquals(
				new Matrix(new double[][] {{1, 0, 0},
										   {0, 1, 0},
										   {0, 0, 1}}),
				Matrix.identity(3));
	}
	
	@Test
	void identityMatrix_getWrong2DIdentityMatrix_shouldFail() {
		assertNotEquals(
				new Matrix(new double[][] {{1, 2},
										   {3, 4}}),
		Matrix.identity(2));
	}
	
	@Test
	void scaleMatrix_get2DScaleMatrix() {
		assertEquals(
				new Matrix(new double[][] {{1, 0},
										   {0, 3}}),
				Matrix.scale(1, 3));
	}
	
	@Test
	void scaleMatrix_get3DScaleMatrix() {
		assertEquals(
				new Matrix(new double[][] {{1, 0,  0},
										   {0, 3,  0},
										   {0, 0, -6}}),
				Matrix.scale(1, 3, -6));
	}
	
	@Test
	void scaleMatrix_get2DNegativeScaleMatrix() {
		assertEquals(
				new Matrix(new double[][] {{-10, 0},
										   {  0, 3}}),
				Matrix.scale(-10, 3));
	}
	
	@Test
	void scaleMatrix_getScaleMatrixWithNoDeltas_shouldThrow() {
		assertThrows(RuntimeException.class, () -> {
			Matrix.scale();
		});
	}
	
	@Test
	void translateMatrix_get2DTranslateMatrix() {
		assertEquals(
				new Matrix(new double[][] {{1, 0, 1},
										   {0, 1, 2},
										   {0, 0, 1}}),
				Matrix.translate(1, 2));
	}
	
	@Test
	void translateMatrix_get3DTranslateMatrix() {
		assertEquals(
				new Matrix(new double[][] {{1, 0, 0, -4},
										   {0, 1, 0,  7},
										   {0, 0, 1, -5},
										   {0, 0, 0,  1}}),
				Matrix.translate(-4, 7, -5));
	}
	
	@Test
	void translateMatrix_get1DTranslateMatrix_shouldThrow() {
		assertThrows(RuntimeException.class, () -> {
			Matrix.translate();
		});
	}
}