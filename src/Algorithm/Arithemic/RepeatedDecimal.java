package Algorithm.Arithemic;

import Algorithm.Fraction;
import Algorithm.MyAlgorithm;

/**
 * Created by MSDK on 7/15/16.
 */
public class RepeatedDecimal extends MyAlgorithm {

    private final int MAX_NUM_DIGITS = 5;
    private final int MIN_NUM_DIGITS = 3;
    private int numDigits;
    private int numRepeatedDigits;
    private int repeatedDigitValue;
    private int whole;
    private Fraction nonRepeatedDecimal;

    private Fraction answer;

    @Override
    public String generateQuestion(int max) {
        numDigits          = (int)(Math.random() * ((MAX_NUM_DIGITS - MIN_NUM_DIGITS) + 1) + MIN_NUM_DIGITS);
        numRepeatedDigits  = (int)(Math.random() * numDigits + 1);
        repeatedDigitValue = (int)(Math.random() * Math.pow(10, numRepeatedDigits) + 1);
        whole              = (int)(Math.random() * MAX_NUM_DIGITS);
        int numerNonRepeat = (int)(Math.random() * Math.pow(10, numDigits - numRepeatedDigits));
        int denomNonRepeat = (int)(Math.pow(10, numDigits - numRepeatedDigits));
        nonRepeatedDecimal = numDigits == numRepeatedDigits ? new Fraction(0, 1) : new Fraction(numerNonRepeat, denomNonRepeat);

        int denomAns       = (int)(Math.pow(10, numDigits) - Math.pow(10, numDigits - numRepeatedDigits));
        answer = new Fraction(repeatedDigitValue, denomAns);
        answer = answer.add(whole);
        answer = answer.add(nonRepeatedDecimal);

        String nonRepeatedDecimalFormat = "%0" + (numDigits - numRepeatedDigits) + "d";
        String question = "Convert " + whole + ".";
        question += nonRepeatedDecimal.getNumer() == 0 ? "" : String.format(nonRepeatedDecimalFormat, nonRepeatedDecimal.getNumer());
        question += " " + repeatedDigitValue + " to fraction.";

        return question;
    }

    @Override
    public String toLatexFormat() {
        String nonRepeatedDecimalFormat = "%0" + (numDigits - numRepeatedDigits) + "d";
        String question = "Convert $" + whole + ".";
        question += nonRepeatedDecimal.getNumer() == 0 ? "" : String.format(nonRepeatedDecimalFormat, nonRepeatedDecimal.getNumer());
        question += "\\overline{" + repeatedDigitValue + "}$ to fraction.";
        return question;
    }

    @Override
    public String getAnswerLatex() {
        return "Answer: $" + answer.toLatexFormat() + "$";
    }

    public static void main(String[] args) {
        RepeatedDecimal r = new RepeatedDecimal();
        r.generateQuestion(30);
        System.out.println(r.toLatexFormat());
        System.out.println(r.getAnswerLatex());
        System.out.println(r.answer.toValue());
    }
}
