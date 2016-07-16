package Algorithm.Algebra;

import Algorithm.Fraction;
import Algorithm.MyAlgorithm;

/**
 * Created by MSDK on 7/8/16.
 */
public class SolveXIntermediate extends MyAlgorithm {

    private String question;
    private int answer;
    private Fraction[] a;
    private Fraction[] b;
    private Fraction[] c;
    private Fraction d;

    private final int MAX_TERMS = 2;

    @Override
    public String generateQuestion(int max) {
        return generateSolveX(max);
    }

    //    a[i](b[i]x + c[i]) = d
    private String generateSolveX(int max) {
        int numTerms = (int)(Math.random() * MAX_TERMS + 1);
        int x;
        do {
            x = (int)(Math.random() * max * 2 + 1 - max);
        } while(x == 0);

        a = new Fraction[numTerms];
        b = new Fraction[numTerms];
        c = new Fraction[numTerms];
        d = new Fraction(0);
        question = "";

        for (int i=0; i<numTerms; i++) {
            if (i > 0) {
                question += " + ";
            }
            a[i] = Fraction.generateRandomFraction(3, 10);
            b[i] = Fraction.generateRandomFraction(3, 10);
            c[i] = Fraction.generateRandomFraction(3, 10);

            a[i] = Math.random() <= 0.5 ? a[i].negate() : a[i];
            b[i] = Math.random() <= 0.5 ? b[i].negate() : b[i];
            c[i] = Math.random() <= 0.5 ? c[i].negate() : c[i];

            d = a[i].multiply(b[i].multiply(x).add(c[i])).add(d);
            question += a[i]+ "(" + b[i] + " * x + " + c[i] + ")";
        }

        question += " = " + d;
        answer = x;
        super.setParameter(question, answer);
        return question;
    }

    public String toLatexFormat() {
        String res = "$";
        for (int i=0; i<a.length; i++) {
            if (i > 0) {
                res += " + ";
            }
            res += a[i].toLatexFormat()+ "(" + b[i].toLatexFormat() + "x + " + c[i].toLatexFormat() + ")";
        }
        res += " = " + d.toLatexFormat() + "$";
        return res;
    }

    @Override
    public String getAnswerLatex() {
        return "$x = " + answer + "$";
    }

    public static void main(String[] args) {
        MyAlgorithm prob = new SolveXIntermediate();
        for (int i=0; i<10; i++) {
            prob.generateQuestion(i+10);
            System.out.println(prob.getQuestion());
            System.out.println(prob.getAnswer());
            System.out.println();
        }
    }
}
