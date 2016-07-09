package Algorithm.Algebra;

import Algorithm.Fraction;
import Algorithm.MyAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSDK on 7/8/16.
 */
public class SolveXSimple extends MyAlgorithm {

    private String question;
    private int answer;
    private Fraction[] a;
    private Fraction[] b;
    private Fraction c;

    private final int MAX_TERMS = 3;

    @Override
    public String generateQuestion(int max) {
        return generateSolveXNoParen(max);
    }

    //    a[i]x + b[i] = c
    private String generateSolveXNoParen(int max) {
        int numTerms = (int)(Math.random() * MAX_TERMS + 1);
        int x;
        do {
            x = (int)(Math.random() * max * 2 + 1 - max);
        } while(x == 0);

        a = new Fraction[numTerms];
        b = new Fraction[numTerms];
        c = new Fraction(0);
        question = "";

        for (int i=0; i<numTerms; i++) {
            if (i > 0) {
                question += " + ";
            }
            a[i] = Fraction.generateRandomFraction(3, 10);
            b[i] = Fraction.generateRandomFraction(3, 10);

            a[i] = Math.random() <= 0.5 ? a[i].negate() : a[i];
            b[i] = Math.random() <= 0.5 ? b[i].negate() : b[i];

            c = a[i].multiply(x).add(b[i]).add(c);
            question += "(" + a[i].toString() + " * x + " + b[i].toString() + ")";
        }

        question += " = " + c.toString();
        answer = x;
        super.setParameter(question, answer);
        return question;
    }

    public String toLatexFormat() {
        String res = "";
        for (int i=0; i<a.length; i++) {
            if (i > 0) {
                res += " + ";
            }
            res += "(" + a[i].toLatexFormat()+ "x + " + b[i].toLatexFormat() + ")";
        }
        res += " = " + c.toLatexFormat();
        return res;
    }

    @Override
    public String getAnswerLatex() {
        return "x = " + answer;
    }

    public List<Fraction> getA() {
        ArrayList<Fraction> res = new ArrayList<>();
        for (Fraction r : a) {
            res.add(new Fraction(r));
        }
        return res;
    }

    public List<Fraction> getB() {
        ArrayList<Fraction> res = new ArrayList<>();
        for (Fraction r : b) {
            res.add(new Fraction(r));
        }
        return res;
    }

    public Fraction getC() {
        return new Fraction(c);
    }

    public static void main(String[] args) {
        SolveXSimple prob = new SolveXSimple();
        for (int i=0; i<10; i++) {
            prob.generateQuestion(i+10);
            System.out.println(prob.toLatexFormat());
            System.out.println(prob.getAnswer());
            System.out.println();
        }
    }
}
