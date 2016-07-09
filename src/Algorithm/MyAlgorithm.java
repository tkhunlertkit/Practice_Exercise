package Algorithm;

import Algorithm.Arithemic.FractionQuestion;
import Algorithm.Arithemic.Percentage;
import Algorithm.Arithemic.Subtraction;
import Algorithm.Algebra.SolveReciprocateX;
import Algorithm.Algebra.SolveXIntermediate;
import Algorithm.Algebra.SolveXSimple;

public abstract class MyAlgorithm {

	private static final int SUBTRACTION_WEIGHT   = 0;
	private static final int PERCENTAGE_WEIGHT    = 0;
	private static final int FRACTION_WEIGHT      = 0;
	private static final int SOLVEXSIMPLE_WEIGHT  = 2;
	private static final int SOLVE_X_INTER_WEIGHT = 3;
	private static final int SOLVE_RECP_X_WEIGHT  = 1;

	private static final double TOTAL_WEIGHT = SUBTRACTION_WEIGHT  + PERCENTAGE_WEIGHT    + FRACTION_WEIGHT +
			                                   SOLVEXSIMPLE_WEIGHT + SOLVE_X_INTER_WEIGHT + SOLVE_RECP_X_WEIGHT;

	private static double subtractRatio     = SUBTRACTION_WEIGHT   / TOTAL_WEIGHT;
	private static double percentRatio      = PERCENTAGE_WEIGHT    / TOTAL_WEIGHT + subtractRatio;
	private static double fractionRatio     = FRACTION_WEIGHT      / TOTAL_WEIGHT + percentRatio;
	private static double solveXSimpleRatio = SOLVEXSIMPLE_WEIGHT  / TOTAL_WEIGHT + fractionRatio;
	private static double solveXInterRatio  = SOLVE_X_INTER_WEIGHT / TOTAL_WEIGHT + solveXSimpleRatio;
	private static double solveXRecpRatio   = SOLVE_RECP_X_WEIGHT  / TOTAL_WEIGHT + solveXInterRatio;

	private String question = "";
	private double ans = 0;

	private QuestionState qState = QuestionState.NORMAL;

	static {
		System.out.println("subtractRatio: " + subtractRatio);
		System.out.println("percentRatio: " + percentRatio);
		System.out.println("fractionRatio: " + fractionRatio);
		System.out.println("solveXSimpleRatio: " + solveXSimpleRatio);
		System.out.println("solveXInterRatio: " + solveXInterRatio);
		System.out.println("solveXRecpRatio: " + solveXRecpRatio);
	}

	public MyAlgorithm() {
	}

	protected void setParameter(String question, double answer) {
		this.question = question;
		this.ans = answer;
	}

	protected void setQuestionState(QuestionState state) {
		qState = state;
	}

	public QuestionState getState() {
		return qState;
	}

	public void clearPercentState() {
		qState = QuestionState.NORMAL;
	}

	public double getAnswer() {
		return ans;
	}

	public String getQuestion() {
		return question;
	}

	public boolean checkAnswer(double inputAnswer) {
		return Math.abs(ans - inputAnswer) < 0.05;
	}

	public static MyAlgorithm instantiateQuestion() {
		double randQuestion = Math.random();
		if      (randQuestion < subtractRatio)     { return new Subtraction();}
		else if (randQuestion < percentRatio)      { return new Percentage(); }
		else if (randQuestion < fractionRatio)     { return new FractionQuestion(); }
		else if (randQuestion < solveXSimpleRatio) { return new SolveXSimple(); }
		else if (randQuestion < solveXInterRatio)  { return new SolveXIntermediate(); }
		else if (randQuestion < solveXRecpRatio)   { return new SolveReciprocateX(); }
		else                                       { return new Percentage(); }
	}

	public abstract String generateQuestion(int max);
	public abstract String toLatexFormat();

	public abstract String getAnswerLatex();
}