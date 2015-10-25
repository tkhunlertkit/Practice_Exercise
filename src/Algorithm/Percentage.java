package Algorithm;

import GUI.CalculatorPrevention;

public class Percentage extends MyAlgorithm {

	private String finalString = "Final";
	private String originalString = "Original";

	@Override
	public String generateQuestion(int max) {
		clearPercentState();
		String question;
		double answer, original, finalValue;
		int randPercent;
		do {
			original = (int) (Math.random() * max);
		} while (original - 0 < 0.1);
		randPercent = (int) (Math.random() * 100) + 1;
		int percentChange = (Math.random() > 0.5) ? randPercent : -randPercent;
		finalValue = original * (1 + percentChange / 100.0);
		int questionRandomProb = (int) (Math.random() * 3);
		switch (questionRandomProb) {
		// find original
		case 0:
			question = finalString + ": " + String.format("%.2f", finalValue)
					+ "\nFrom " + randPercent
					+ (percentChange <= 0 ? "% decrease" : "% increase")
					+ ",\n" + originalString;
			answer = original;
			break;
		// Find final
		case 1:
			question = originalString + ": " + String.format("%.2f", original)
					+ "\nwith " + randPercent
					+ (percentChange <= 0 ? "% decrease" : "% increase")
					+ ",\n" + finalString;
			answer = finalValue;
			break;
		// Find percent increase or decrease
		default:
			question = originalString + ": " + String.format("%.2f", original)
					+ "\nwith " + finalString + ": "
					+ String.format("%.2f", finalValue)
					+ ",\nPercentage Change";
			answer = percentChange;
			setQuestionState(QuestionState.PERCENT_INC_DEC);
			break;
		}
		super.setParameter(question, answer);
		CalculatorPrevention.openCalculator();
		return question;
	}

}
