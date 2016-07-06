package Algorithm;

import Calculator.CalculatorPrevention;

public class FractionQuestion extends MyAlgorithm {

	@Override
	public String generateQuestion(int max) {
		setQuestionState(QuestionState.FRACTION);
		String question;
		int x[] = new int[3];
		int y[] = new int[3];
		int xPlus = (Math.random() < 0.4) ? 1 : -1; 
		int yPlus = (Math.random() < 0.4) ? 1 : -1; 
		
		// x[0]^^x[1]//x[2]
		x[1] = (int)(Math.random() * max + 1);
		x[2] = (int)(Math.random() * max + x[1]);
		x[0] = (int)(Math.random() * max);

		// y[0]^^y[1]//y[2]
		y[1] = (int)(Math.random() * max + 1);
		y[2] = (int)(Math.random() * max + y[1]);
		y[0] = (int)(Math.random() * max);
		
		double xValue = xPlus * ((double)(x[2] * x[0] + x[1]) / x[2]);
		double yValue = yPlus * ((double)(y[2] * y[0] + y[1]) / y[2]);
		x[0] = (xPlus == 1) ? x[0] : -x[0]; 
		y[0] = (yPlus == 1) ? y[0] : -y[0];
		
		int operation = (int)(Math.random() * 4);
		double answer;
		switch(operation) {
			case 0:
				// +
				answer = xValue + yValue;
				question = getQuestionString(x, y, "+");
				break;
			case 1:
				// -
				answer = xValue - yValue;
				question = getQuestionString(x, y, "-");
				break;
			case 2:
				// *
				answer = xValue * yValue;
				question = getQuestionString(x, y, "x");
				break;
			default:
				// /
				answer = xValue / yValue;
				question = getQuestionString(x, y, "\u00F7");
				break;	
		}
		super.setParameter(question, answer);
        CalculatorPrevention.closeCalculator();

		System.out.println(xValue + "  " + yValue);
		System.out.println(answer);
		return question;
	}
	
	private String getQuestionString(int x[], int y[], String op) {
		String q = "";
		q += "{"; 
		q += x[0] + "^^" + x[1] + "//" + x[2];
		q += "}";
		q +=  " " + op + " "; 
		q += "{"; 
		q += y[0] + "^^" + y[1] + "//" + y[2];
		q += "}";
		return q;
	}

}
