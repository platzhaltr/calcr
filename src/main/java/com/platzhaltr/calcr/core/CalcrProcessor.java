package com.platzhaltr.calcr.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import com.googlecode.lingwah.AbstractProcessor;
import com.googlecode.lingwah.Match;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.annotations.Processes;

@Processes(CalcrGrammar.class)
public class CalcrProcessor extends AbstractProcessor {

	static final CalcrGrammar grammar = CalcrGrammar.INSTANCE;

	private final Map<String, BigDecimal> variables;

	public CalcrProcessor(final Map<String, BigDecimal> variables) {
		this.variables = variables;
	}

	public void completeAddition(final Match expr) {
		final List<Match> children = expr.getChildrenByType(grammar.expr);
		final BigDecimal left = (BigDecimal) getResult(children.get(0));
		final BigDecimal right = (BigDecimal) getResult(children.get(1));
		putResult(left.add(right));
	}

	public void completeSubtraction(final Match expr) {
		final List<Match> children = expr.getChildrenByType(grammar.expr);
		final BigDecimal left = (BigDecimal) getResult(children.get(0));
		final BigDecimal right = (BigDecimal) getResult(children.get(1));
		putResult(left.subtract(right));
	}

	public void completeMultiplication(final Match expr) {
		final List<Match> children = expr.getChildrenByType(grammar.expr);
		final BigDecimal left = (BigDecimal) getResult(children.get(0));
		final BigDecimal right = (BigDecimal) getResult(children.get(1));
		putResult(left.multiply(right));
	}

	public void completeDivision(final Match expr) {
		final List<Match> children = expr.getChildrenByType(grammar.expr);
		final BigDecimal left = (BigDecimal) getResult(children.get(0));
		final BigDecimal right = (BigDecimal) getResult(children.get(1));
		putResult(left.divide(right, 28, RoundingMode.HALF_UP));
	}

	public void completeOperator(final Match op) {
		putResult(getResult(op.getChildren().get(0)));
	}

	public void completeGroup(final Match expr) {
		putResult(getResult(expr.getChildByType(grammar.expr)));
	}

	public void completeDecimal(final Match expr) {
		putResult(new BigDecimal(expr.getText()));
	}

	public void completeVariable(final Match expr) {
		final String key = expr.getText();
		final BigDecimal bigDecimal = variables.get(key);

		putResult(bigDecimal);
	}

	public void completeExpr(final Match expr) {
		putResult(getResult(expr.getChildren().get(0)));
	}

	public static BigDecimal process(final Map<String, BigDecimal> variables,
			final ParseResults results) {
		return (BigDecimal) new CalcrProcessor(variables).getResult(results
				.getLongestMatch());
	}

}
