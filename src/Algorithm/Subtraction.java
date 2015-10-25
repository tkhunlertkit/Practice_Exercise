package Algorithm;

import GUI.CalculatorPrevention;

public class Subtraction extends MyAlgorithm {

	public Subtraction() {
		super();
	}

	@Override
	public String generateQuestion(int max) {
		setQuestionState(QuestionState.NO_CALC);
		String question;
		double ans;
		int num1, num2;
		boolean plus = (Math.random() > 0.49) ? true:false;
		do {
			num1 = (int) ((Math.random() - 0.5) * (2 * max));
			num2 = (int) ((Math.random() - 0.5) * (2 * max));
		} while (num1 == 0 || num2 == 0);
		question = "" + ((num1 < 0) ? "(" + num1 + ")" : num1); 
		question += ((plus)? "+" : "-");
		question += (num2 < 0) ? "(" + num2 + ")" : num2;
		ans = (plus ? num1 + num2 : num1 - num2);
		super.setParameter(question, ans);
        CalculatorPrevention.closeCalculator();
		return question;
	}

}
