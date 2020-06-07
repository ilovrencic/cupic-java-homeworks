package hr.fer.zemris.java.hw07.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw06.crypto.Util;

public class UtilTest {

	@Test
	public void testHextobyte() {
		byte array[] = Util.hextobyte("01ff22");
		assertEquals(1, array[0]);
		assertEquals(-1, array[1]);
		assertEquals(34, array[2]);
		
		array = Util.hextobyte("ffffffaa3456e3");
		assertEquals(-1, array[0]);
		assertEquals(-1, array[1]);
		assertEquals(-86, array[3]);
		assertEquals(52, array[4]);
		assertEquals(-29, array[6]);
		
		byte array1[] = Util.hextobyte("");
		assertEquals(0, array1.length);
	}

	@Test
	public void testBytetohex() {
		String s = Util.bytetohex(new byte[] {-1, 68, 40});
		assertEquals("ff4428", s);
		
		s = Util.bytetohex(new byte[] {123, 10}); 
		assertEquals("7b0a", s);
		
		s = Util.bytetohex(new byte[] {-4, 40, 127});
		assertEquals("fc287f", s);
		
		s = Util.bytetohex(new byte[] {-4, 40, -128});
		assertEquals("fc2880", s);
		
		s = Util.bytetohex(new byte[] {0, 0, 0, 0});
		assertEquals("00000000", s);
		
		s = Util.bytetohex(new byte[] {-128, 126, 0, 0});
		assertEquals("807e0000", s);
	}

}
