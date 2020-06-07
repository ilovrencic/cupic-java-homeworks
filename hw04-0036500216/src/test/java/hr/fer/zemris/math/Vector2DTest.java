package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Vector2DTest {

	private static final double DELTA = 1e-06;

	@Test
	void testVector2D() {
		Vector2D vector = new Vector2D(1, 2);

		assertEquals(vector.getX(), 1);
		assertEquals(vector.getY(), 2);
	}

	@Test
	void testTranslate() {
		Vector2D vector = new Vector2D(1, 1);
		Vector2D offset = new Vector2D(2, 2);

		assertEquals(vector.getX(), 1);
		assertEquals(vector.getY(), 1);

		vector.translate(offset);

		assertEquals(vector.getX(), 3);
		assertEquals(vector.getY(), 3);
		
		Assertions.assertThrows(NullPointerException.class, ()-> vector.translate(null));
	}

	@Test
	void testTranslated() {
		Vector2D vector = new Vector2D(1, 1);
		Vector2D offset = new Vector2D(2, 2);

		assertEquals(vector.getX(), 1);
		assertEquals(vector.getY(), 1);

		Vector2D translatedVector = vector.translated(offset);

		assertEquals(vector.getX(), 1);
		assertEquals(vector.getY(), 1);

		assertEquals(translatedVector.getX(), 3);
		assertEquals(translatedVector.getY(), 3);
		
		Assertions.assertThrows(NullPointerException.class, ()-> vector.translated(null));
	}

	@Test
	void testRotate() {
		Vector2D vector = new Vector2D(1, 1);

		assertEquals(vector.getX(), 1);
		assertEquals(vector.getY(), 1);

		vector.rotate(Math.PI);

		assertEquals(vector.getX(), -1, DELTA);
		assertEquals(vector.getY(), -1, DELTA);
	}

	@Test
	void testRotated() {
		Vector2D vector = new Vector2D(1, 1);

		assertEquals(vector.getX(), 1);
		assertEquals(vector.getY(), 1);

		Vector2D rotatedVector = vector.rotated(Math.PI);

		assertEquals(vector.getX(), 1);
		assertEquals(vector.getY(), 1);

		assertEquals(rotatedVector.getX(), -1, DELTA);
		assertEquals(rotatedVector.getY(), -1, DELTA);
	}

	@Test
	void testScale() {
		Vector2D vector = new Vector2D(1, 1);

		assertEquals(vector.getX(), 1);
		assertEquals(vector.getY(), 1);

		vector.scale(4);

		assertEquals(vector.getX(), 4, DELTA);
		assertEquals(vector.getY(), 4, DELTA);

	}

	@Test
	void testScaled() {
		Vector2D vector = new Vector2D(2, 2);

		assertEquals(vector.getX(), 2);
		assertEquals(vector.getY(), 2);

		Vector2D scaledVector = vector.scaled(3);

		assertEquals(vector.getX(), 2);
		assertEquals(vector.getY(), 2);

		assertEquals(scaledVector.getX(), 6);
		assertEquals(scaledVector.getY(), 6);
	}

	@Test
	void testCopy() {
		Vector2D vector = new Vector2D(2,2);
	
		Vector2D copiedVector = vector.copy();
		
		assertEquals(copiedVector.getX(), 2);
		assertEquals(copiedVector.getY(), 2);
		
		copiedVector.rotate(Math.PI);
		
		assertEquals(copiedVector.getX(), -2, DELTA);
		assertEquals(copiedVector.getY(), -2, DELTA);
		
		assertEquals(vector.getX(), 2);
		assertEquals(vector.getY(), 2);
		
	}

}
