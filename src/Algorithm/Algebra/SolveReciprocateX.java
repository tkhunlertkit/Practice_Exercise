package Algorithm.Algebra;

import Algorithm.Fraction;
import Algorithm.MyAlgorithm;

/**
 * Created by MSDK on 7/8/16.
 */
public class SolveReciprocateX extends MyAlgorithm {

    private String question;
    private int answer;
    private Fraction a;
    private Fraction b;
    private Fraction c;


    @Override
    public String generateQuestion(int max) {
        return generateSolveXNoParen(max);
    }

    //    a(x^-1) = b(x^-1) + c
    private String generateSolveXNoParen(int max) {
        int x;
        do {
            x = (int)(Math.random() * max * 2 + 1 - max);
        } while(x == 0);

        question = "";


        a = Fraction.generateRandomFraction(3, 10);
        do {
            b = Fraction.generateRandomFraction(3, 10);
        } while (b.equals(a));

        a = Math.random() <= 0.5 ? a.negate() : a;
        b = Math.random() <= 0.5 ? b.negate() : b;

        Fraction fracXRec = new Fraction(x).reciprocate();

        c = a.multiply(fracXRec).subtract(b.multiply(fracXRec));

        question += a + " x ^ -1 = " + b + " x ^ -1" + c;

        answer = x;
        super.setParameter(question, answer);
        return question;
    }

    public String toLatexFormat() {
        String res = a.toLatexFormat() + "x^{-1} = " + b.toLatexFormat() + "x^{-1} + " + c.toLatexFormat();
        return res;
    }

    @Override
    public String getAnswerLatex() {
        return "x = " + answer;
    }

    public static void main(String[] args) {
        SolveReciprocateX prob = new SolveReciprocateX();
        for (int i=0; i<10; i++) {
            prob.generateQuestion(i+10);
            System.out.println(prob.toLatexFormat());
            System.out.println(prob.getAnswer());
            System.out.println();
        }
    }
}
