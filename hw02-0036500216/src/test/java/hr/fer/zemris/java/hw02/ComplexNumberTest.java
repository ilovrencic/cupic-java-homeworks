package hr.fer.zemris.java.hw02;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComplexNumberTest {
	
	private static final double DELTA = 1e-06;
	
	@Test
	public void testComplexNumber() {
		ComplexNumber c1 = new ComplexNumber(1,2);
		assertEquals(c1.getReal(),1,DELTA);
		assertEquals(c1.getImaginary(),2,DELTA);
	}

	@Test
	public void testGetReal() {
		ComplexNumber c1 = new ComplexNumber(1,2);
		assertEquals(c1.getReal(),1,DELTA);
	}

	@Test
	public void testGetImaginary() {
		ComplexNumber c2 = new ComplexNumber(-3,4);
		assertEquals(c2.getImaginary(),4,DELTA);
	}

	@Test
	public void testGetMagnitude() {
		ComplexNumber c3 = new ComplexNumber(3,4);
		assertEquals(c3.getMagnitude(),5,DELTA);
	}

	@Test
	public void testGetAngle() {
		ComplexNumber c1 = new ComplexNumber(1,1);
		assertEquals(c1.getAngle(),Math.PI/4,DELTA);
		
		ComplexNumber c2 = new ComplexNumber(-1,1);
		assertEquals(c2.getAngle(),(3*Math.PI)/4,DELTA);
		
		ComplexNumber c3 = new ComplexNumber(-1,-1);
		assertEquals(c3.getAngle(),(5*Math.PI)/4,DELTA);
		
		ComplexNumber c4 = new ComplexNumber(1,-1);
		assertEquals(c4.getAngle(),(7*Math.PI)/4,DELTA);
	}

	@Test
	public void testAdd() {
		ComplexNumber c1 = new ComplexNumber(1,2);
		ComplexNumber c2 = new ComplexNumber(2,3);
		ComplexNumber c3 = c1.add(c2);
		assertEquals(c3.getReal(),3,DELTA);
		assertEquals(c3.getImaginary(),5,DELTA);
	}

	@Test
	public void testSub() {
		ComplexNumber c1 = new ComplexNumber(5,2);
		ComplexNumber c2 = new ComplexNumber(2,3);
		ComplexNumber c3 = c1.sub(c2);
		assertEquals(c3.getReal(),3,DELTA);
		assertEquals(c3.getImaginary(),-1,DELTA);
	}

	@Test
	public void testMul() {
		ComplexNumber c1 = new ComplexNumber(1,1);
		ComplexNumber c2 = new ComplexNumber(1,1);
		ComplexNumber c3 = c1.mul(c2);
		assertEquals(c3.getReal(),0,DELTA);
		assertEquals(c3.getImaginary(),2,DELTA);
	}

	@Test
	public void testDiv() {
		ComplexNumber c1 = new ComplexNumber(2,1);
		ComplexNumber c2 = new ComplexNumber(1,1);
		ComplexNumber c3 = c1.div(c2);
		assertEquals(c3.getReal(),1.5,DELTA);
		assertEquals(c3.getImaginary(),-0.5,DELTA);
	}

	@Test
	public void testPower() {
		ComplexNumber c1 = new ComplexNumber(2,1);
		ComplexNumber c2 = c1.power(3);
		assertEquals(c2.getReal(),2,DELTA);
		assertEquals(c2.getImaginary(),11,DELTA);
	}

	@Test
	public void testRoot() {
		ComplexNumber c1 = new ComplexNumber(0,2);
		ComplexNumber[] c2 = c1.root(2);
		assertEquals(c2[0].getReal(),1,DELTA);
		assertEquals(c2[0].getImaginary(),1,DELTA);
	}

	@Test
	public void testFromReal() {
		ComplexNumber c1 = ComplexNumber.fromReal(10);
		assertEquals(c1.getReal(),10,DELTA);
		assertEquals(c1.getImaginary(),0,DELTA);
	}

	@Test
	public void testFromImaginary() {
		ComplexNumber c1 = ComplexNumber.fromImaginary(10);
		assertEquals(c1.getReal(),0,DELTA);
		assertEquals(c1.getImaginary(),10,DELTA);
	}

	@Test
	public void testFromMagnitudeAndAngle() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(2,0);
		assertEquals(c1.getReal(),2,DELTA);
		assertEquals(c1.getImaginary(),0,DELTA);
	}

	@Test
	public void testParse() {
		ComplexNumber c1 = ComplexNumber.parse("1+i");
		assertEquals(c1.getReal(),1,DELTA);
		assertEquals(c1.getImaginary(),1,DELTA);
		
		ComplexNumber c2 = ComplexNumber.parse("12");
		assertEquals(c2.getReal(),12,DELTA);
		assertEquals(c2.getImaginary(),0,DELTA);
		
		ComplexNumber c3 = ComplexNumber.parse("    -i    ");
		assertEquals(c3.getReal(),0,DELTA);
		assertEquals(c3.getImaginary(),-1,DELTA);
	}

}
