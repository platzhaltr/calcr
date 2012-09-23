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

	public static float parseInt(final String expression) {
		return parseInt(expression, Collections.<String, Integer> emptyMap());
	}

	public static float parseFloat(final String expression) {
		return parseFloat(expression, Collections.<String, Float> emptyMap());
	}

	public static double parseDouble(final String expression) {
		return parseDouble(expression, Collections.<String, Double> emptyMap());
	}

	public static float parseInt(final String expression, final String key,
			final int value) {
		final Map<String, Integer> variables = new HashMap<String, Integer>(1);
		variables.put(key, value);

		return parseInt(expression, variables);
	}

	public static float parseFloat(final String expression, final String key,
			final float value) {
		final Map<String, Float> variables = new HashMap<String, Float>(1);
		variables.put(key, value);

		return parseFloat(expression, variables);
	}

	public static int parseInt(final String expression,
			final Map<String, Integer> variables) {
		final Map<String, BigDecimal> bigDecimals = new HashMap<String, BigDecimal>(
				1);

		for (final Map.Entry<String, Integer> variable : variables.entrySet()) {
			bigDecimals.put(variable.getKey(),
					new BigDecimal(variable.getValue()));
		}
		return parseBigDecimal(expression, bigDecimals).intValue();
	}

	public static float parse(final String expression,
			final Map<String, ? extends Number> variables) {
		final Map<String, BigDecimal> bigDecimals = new HashMap<String, BigDecimal>(
				1);

		for (final Map.Entry<String, ? extends Number> entry : variables
				.entrySet()) {
			final double value = (Integer) entry.getValue();

			bigDecimals.put(entry.getKey(), new BigDecimal(value));
		}
		return parseBigDecimal(expression, bigDecimals).floatValue();
	}

	public static float parseFloat(final String expression,
			final Map<String, Float> variables) {
		final Map<String, BigDecimal> bigDecimals = new HashMap<String, BigDecimal>(
				1);

		for (final Map.Entry<String, Float> variable : variables.entrySet()) {
			bigDecimals.put(variable.getKey(),
					new BigDecimal(variable.getValue()));
		}
		return parseBigDecimal(expression, bigDecimals).floatValue();
	}

	public static double parseDouble(final String expression,
			final Map<String, Double> variables) {
		final Map<String, BigDecimal> bigDecimals = new HashMap<String, BigDecimal>(
				1);

		for (final Map.Entry<String, Double> variable : variables.entrySet()) {
			bigDecimals.put(variable.getKey(),
					new BigDecimal(variable.getValue()));
		}
		return parseBigDecimal(expression, bigDecimals).doubleValue();
	}

	public static BigDecimal parseBigDecimal(final String expression,
			final Map<String, BigDecimal> variables) {
		final ParseResults parseResults = ParseContext
				.parse(PARSER, expression);
		if (!parseResults.success()) {
			throw parseResults.getError();
		}
		return CalcrProcessor.process(variables, parseResults);
	}
}