package com.platzhaltr.calcr.core;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;

public class Calcr {
	static final Parser PARSER = CalcrGrammar.INSTANCE.expr;

	public static float parseInt(String expression) {
		return parseInt(expression, Collections.<String, Integer> emptyMap());
	}

	public static float parseFloat(String expression) {
		return parseFloat(expression, Collections.<String, Float> emptyMap());
	}

	public static double parseDouble(String expression) {
		return parseDouble(expression, Collections.<String, Double> emptyMap());
	}

	public static float parseInt(String expression, String key, int value) {
		Map<String, Integer> variables = new HashMap<String, Integer>(1);
		variables.put(key, value);

		return parseInt(expression, variables);
	}

	public static float parseFloat(String expression, String key, float value) {
		Map<String, Float> variables = new HashMap<String, Float>(1);
		variables.put(key, value);

		return parseFloat(expression, variables);
	}

	public static int parseInt(String expression, Map<String, Integer> variables) {
		Map<String, BigDecimal> bigDecimals = new HashMap<String, BigDecimal>(1);

		for (Map.Entry<String, Integer> variable : variables.entrySet()) {
			bigDecimals.put(variable.getKey(),
					new BigDecimal(variable.getValue()));
		}
		return parseBigDecimal(expression, bigDecimals).intValue();
	}

	public static float parseFloat(String expression,
			Map<String, Float> variables) {
		Map<String, BigDecimal> bigDecimals = new HashMap<String, BigDecimal>(1);

		for (Map.Entry<String, Float> variable : variables.entrySet()) {
			bigDecimals.put(variable.getKey(),
					new BigDecimal(variable.getValue()));
		}
		return parseBigDecimal(expression, bigDecimals).floatValue();
	}

	public static double parseDouble(String expression,
			Map<String, Double> variables) {
		Map<String, BigDecimal> bigDecimals = new HashMap<String, BigDecimal>(1);

		for (Map.Entry<String, Double> variable : variables.entrySet()) {
			bigDecimals.put(variable.getKey(),
					new BigDecimal(variable.getValue()));
		}
		return parseBigDecimal(expression, bigDecimals).doubleValue();
	}

	public static BigDecimal parseBigDecimal(String expression,
			Map<String, BigDecimal> variables) {
		ParseResults parseResults = ParseContext.parse(PARSER, expression);
		if (!parseResults.success())
			throw parseResults.getError();
		return CalcrProcessor.process(variables, parseResults);
	}
}