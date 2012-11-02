package com.platzhaltr.calcr.core;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.parser.ParserReference;

/**
 * EBNF of the grammar
 * 
 * <code><pre>
 * blank   = ' ' | '\t' | '\n' | '\r';
 * whitespace      = (blank, {blank});
 * digit = '0'|'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9';
 * char = 'a'|'b' ... |'z'|'A'|'B'| ... |'Z';
 * variable = char, { char };
 * number = digit, { digit };
 * decimal = [ '-' ], (number, ['.', [number]] | '.', number);
 * addition = expr, [whitespace], '+', [whitespace], expr;
 * subtraction = expr, [whitespace], '-', [whitespace], expr;
 * multiplication = (expr, [whitespace], '*', [whitespace], expr) | (expr, [whitespace], expr);
 * division = expr, [whitespace], '\', [whitespace], expr;
 * group = '(', [whitespace], expr, [whitespace], ')';
 * expr = decimal | variable | addition | subtraction | multiplication | division | group;
 * </pre></code>
 * 
 * Based on the original calculator grammar example by Ted Stockwell
 * 
 * @author Oliver Schrenk <oliver.schrenk@gmail.com>
 * 
 */

public class CalcrGrammar extends Grammar {

	public static final CalcrGrammar INSTANCE = new CalcrGrammar();

	// these fields need to be public because lingwah uses reflection to
	// access/invoke these

	public final Parser whitespace = oneOrMore(cho(oneOrMore(regex("[ \t\n\f\r]"))));
	public final Parser digit = regex("[0-9]");
	public final Parser number = oneOrMore(digit);
	public final Parser decimal = seq(opt(str('-')),
			seq(number, opt(seq(str('.'), opt(number)))));

	public final Parser variable = seq(oneOrMore(regex("[a-zA-z]")));
	public final ParserReference expr = ref();

	public final Parser addition = seq(expr, str('+'), expr).separatedBy(
			opt(whitespace));
	public final Parser subtraction = seq(expr, str('-'), expr).separatedBy(
			opt(whitespace));
	public final Parser multiplication = seq(expr, str('*'), expr).separatedBy(
			opt(whitespace));
	public final Parser division = seq(expr, str('/'), expr).separatedBy(
			opt(whitespace));

	public final Parser operation = first(multiplication, division, addition,
			subtraction);

	public final Parser group = seq(str('('), expr, str(')')).separatedBy(
			opt(whitespace));
	{
		expr.define(cho(decimal, variable, operation, group));
	}

	private CalcrGrammar() {
		init();
	}

}
