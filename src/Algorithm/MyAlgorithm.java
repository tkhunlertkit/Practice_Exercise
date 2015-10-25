package Algorithm;

public abstract class MyAlgorithm {

	private final int SUBTRACTION_WEIGHT = 1;
	private final int PERCENTAGE_WEIGHT = 0;
	private final double TOTAL_WEIGHT = SUBTRACTION_WEIGHT + PERCENTAGE_WEIGHT;

	private double subtractRatio = SUBTRACTION_WEIGHT / TOTAL_WEIGHT;
	private double percentRatio = PERCENTAGE_WEIGHT / TOTAL_WEIGHT
			+ subtractRatio;
	private String question = "";
	private double ans = 0;

	private QuestionState qState = QuestionState.NORMAL;

	public MyAlgorithm() {
	}

	protected void setParameter(String question, double answer) {
		this.question = question;
		this.ans = answer;
	}

	protected void setQuestionState(QuestionState state) {
		qState = state;
	}

	public QuestionState getstate() {
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

	public MyAlgorithm instantiateQuestion() {
		double randQuestion = Math.random();
		if (randQuestion < subtractRatio) {
			return new Subtraction();
		} else if (randQuestion < percentRatio) {
			return new Percentage();
		} else {
			// default
			return new Percentage();
		}
	}

	public abstract String generateQuestion(int max);
}