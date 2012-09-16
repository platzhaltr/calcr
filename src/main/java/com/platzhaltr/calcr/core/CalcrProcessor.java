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

	private Map<String, BigDecimal> variables;

	public CalcrProcessor(Map<String, BigDecimal> variables) {
		this.variables = variables;
	}

	public void completeAddition(Match expr) {
		List<Match> children = expr.getChildrenByType(grammar.expr);
		BigDecimal left = getResult(children.get(0));
		BigDecimal right = getResult(children.get(1));
		putResult(left.add(right));
	}

	public void completeSubtraction(Match expr) {
		List<Match> children = expr.getChildrenByType(grammar.expr);
		BigDecimal left = getResult(children.get(0));
		BigDecimal right = getResult(children.get(1));
		putResult(left.subtract(right));
	}

	public void completeMultiplication(Match expr) {
		List<Match> children = expr.getChildrenByType(grammar.expr);
		BigDecimal left = getResult(children.get(0));
		BigDecimal right = getResult(children.get(1));
		putResult(left.multiply(right));
	}

	public void completeDivision(Match expr) {
		List<Match> children = expr.getChildrenByType(grammar.expr);
		BigDecimal left = getResult(children.get(0));
		BigDecimal right = getResult(children.get(1));
		putResult(left.divide(right, 28, RoundingMode.HALF_UP));
	}

	public void completeOperator(Match op) {
		putResult(getResult(op.getChildren().get(0)));
	}

	public void completeGroup(Match expr) {
		putResult(getResult(expr.getChildByType(grammar.expr)));
	}

	public void completeDecimal(Match expr) {
		putResult(new BigDecimal(expr.getText()));
	}

	public void completeVariable(Match expr) {
		final String key = expr.getText();
		final BigDecimal bigDecimal = variables.get(key);

		putResult(bigDecimal);
	}

	public void completeExpr(Match expr) {
		putResult(getResult(expr.getChildren().get(0)));
	}

	public static BigDecimal process(Map<String, BigDecimal> variables,
			ParseResults results) {
		return new CalcrProcessor(variables).getResult(results
				.getLongestMatch());
	}

}
