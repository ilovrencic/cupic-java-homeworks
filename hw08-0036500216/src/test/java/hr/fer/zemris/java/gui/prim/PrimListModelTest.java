package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PrimListModelTest {

	@Test
	public void testSize() {
		PrimListModel model = new PrimListModel();
		assertEquals(model.getSize(), 1);

		model.next();
		model.next();
		model.next();

		assertEquals(model.getSize(), 4);

		model.next();
		model.next();

		assertEquals(model.getSize(), 6);
	}
	
	@Test
	public void testNext() {
		PrimListModel model = new PrimListModel();
		assertEquals(model.getElementAt(0), 1);
		
		model.next();
		
		assertEquals(model.getElementAt(1), 2);
		
		model.next();
		
		assertEquals(model.getElementAt(2), 3);
		
		model.next();
		
		assertEquals(model.getElementAt(3), 5);
		
		model.next();
		
		assertEquals(model.getElementAt(4), 7);
	}

}
