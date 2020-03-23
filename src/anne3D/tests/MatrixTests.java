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
		assertEquals(
				new Matrix(new double[][] {
					{2, 0},
					{0, 2}}),
				Matrix.identity(2).plus(Matrix.identity(2)));
	}
	
	@Test
	void matrixAdd_add2DifferentDimensionsMatrices_shouldThrow() {
		assertThrows(RuntimeException.class, () -> {
			Matrix.identity(2).plus(Matrix.identity(3));
		});
	}
	
	@Test
	void matrixSubtract_subtract2DMatrices() {
		Matrix firstMatrix = new Matrix(new double[][] {
			{10, 6},
			{-1, 5}});
		
		Matrix secondMatrix = new Matrix(new double[][] {
			{ 5,  3},
			{-2, -1}});
		
		assertEquals(
				new Matrix(new double[][] {
					{5, 3},
					{1, 6}}),
				firstMatrix.minus(secondMatrix));
	}
	
	@Test
	void matrixSubtract_subtractMatricesWithDifferentDimensions_shouldThrow() {
		assertThrows(RuntimeException.class, () -> {
			Matrix.identity(6).minus(Matrix.identity(3));
		});
	}
	
	@Test
	void matrixMultiply_multiply2IdentityMatrices() {
		assertEquals(
				Matrix.identity(2),
				Matrix.identity(2).times(Matrix.identity(2)));
	}
	
	@Test
	void matrixMultiply_multiplyMatrixByIdentityMatrix() {
		assertEquals(
				new Matrix(new double[][] {
					{1, 2, 3},
					{4, 5, 6},
					{7, 8, 9}}),
				
				Matrix.identity(3)
				.times(new Matrix(new double[][] {
					{1,2,3},
					{4,5,6},
					{7,8,9}})));
	}
	
	@Test
	void matrixMultiply_multiple3DMatrices() {
		Matrix firstMatrix = new Matrix(new double [][] {
			{ 1, 4 , -9},
			{ 7, 53,142},
			{-3, 2 ,  0}});
		
		Matrix secondMatrix = new Matrix(new double[][] {
			{5 , -90 ,5 },
			{6 ,  13 ,29},
			{42, 67  ,2}});
		
		assertEquals(new Matrix(new double[][] {
			{-349, -641, 103},
			{6317, 9573, 1856},
			{  -3, 296 , 43}}),
			firstMatrix.times(secondMatrix));
	}
	
	@Test
	void matrixMultiply_multiplyMatricesWithDifferentDimensions_shouldThrow() {
		assertThrows(RuntimeException.class, () -> {
			Matrix.identity(6).times(Matrix.identity(3));
		});
	}
	
	@Test
	void matrixTranspose_transpose2DMatrix() {
		assertEquals(
				new Matrix(new double[][]{
					{1, 3},
					{2, 4}}),
				
				new Matrix(new double[][] {
					{1, 2},
					{3, 4}}).transpose());
	}

	@Test
	void identityMatrix_get2DIdentityMatrix() {
		assertEquals(
				new Matrix(new double[][] {
					{1, 0},
					{0, 1}}),
				Matrix.identity(2));
	}
	
	@Test
	void identityMatrix_get3DIdentityMatrix() {
		assertEquals(
				new Matrix(new double[][] {
					{1, 0, 0},
					{0, 1, 0},
					{0, 0, 1}}),
				Matrix.identity(3));
	}
	
	@Test
	void identityMatrix_getWrong2DIdentityMatrix() {
		assertNotEquals(
				new Matrix(new double[][] {
					{1, 2},
					{3, 4}}),
				Matrix.identity(2));
	}
	
	@Test
	void scaleMatrix_get2DScaleMatrix() {
		assertEquals(
				new Matrix(new double[][] {
					{1, 0},
					{0, 3}}),
				Matrix.scale(1, 3));
	}
	
	@Test
	void scaleMatrix_get3DScaleMatrix() {
		assertEquals(
				new Matrix(new double[][] {
					{1, 0,  0},
					{0, 3,  0},
					{0, 0, -6}}),
				Matrix.scale(1, 3, -6));
	}
	
	@Test
	void scaleMatrix_get2DNegativeScaleMatrix() {
		assertEquals(
				new Matrix(new double[][] {
					{-10, 0},
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
				new Matrix(new double[][] {
					{1, 0, 1},
					{0, 1, 2},
					{0, 0, 1}}),
				Matrix.translate(1, 2));
	}
	
	@Test
	void translateMatrix_get3DTranslateMatrix() {
		assertEquals(
				new Matrix(new double[][] {
					{1, 0, 0, -4},
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
