package com.platzhaltr.calcr.core;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class CalcrTest {

	private static Map<String, Float> variables;

	@BeforeClass
	public static void setup() {
		variables = new HashMap<String, Float>();
		variables.put("x", 42f);
		variables.put("y", 7f);
	}

	@Test
	public void testBasics() {
		assertEquals(2, Calcr.parseFloat("1+1"), 0.0);
		assertEquals(2, Calcr.parseFloat("1 + 1"), 0.0);
		assertEquals(3, Calcr.parseFloat("1 + 1*2"), 0.0);
		assertEquals(3, Calcr.parseFloat("1 + 1 * 2"), 0.0);
		assertEquals(3, Calcr.parseFloat("1 + 2*1"), 0.0);
		assertEquals(3, Calcr.parseFloat("1 + 2 * 1"), 0.0);
		assertEquals(2, Calcr.parseFloat("1 + 2 * 1 / 2"), 0.0);
	}

	@Test
	public void testUnusedVariables() {
		assertEquals(3, Calcr.parseFloat("1 + 2*1", variables), 0.0);
	}

	@Test
	public void testSingleVariable() {
		assertEquals(43, Calcr.parseFloat("1 + x", variables), 0.0);
		assertEquals(7, Calcr.parseFloat("x / 6", variables), 0.0);
	}

	@Test
	public void testMultipleVariable() {
		assertEquals(6, Calcr.parseFloat("x / y", variables), 0.0);
	}

	@Test
	public void testMultipleFloatVariable() {
		assertEquals(6, Calcr.parseFloat("x / y", variables), 0.0);
		assertEquals(7, Calcr.parseFloat("1 + x / y", variables), 0.0);
	}
}
