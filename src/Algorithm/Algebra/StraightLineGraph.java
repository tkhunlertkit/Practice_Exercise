package Algorithm.Algebra;

import Algorithm.Fraction;
import Algorithm.MyAlgorithm;

/**
 * Created by MSDK on 7/8/16.
 */
public class StraightLineGraph extends MyAlgorithm {

//    ay = bx + z

    private Fraction a, b, z;
    private String question = "";

    @Override
    public String generateQuestion(int max) {

        a = Fraction.generateRandomFraction(3, 10);
        b = Fraction.generateRandomFraction(3, 10);
        z = Fraction.generateRandomFraction(3, 10);

        question = "Find the y-intercept form (y = mx + c), slope, and y-intercept\n";
        question += a + "y = " + b + "x + " + z;

        return question;
    }

    @Override
    public String toLatexFormat() {
        String q = "Find the y-intercept form ($y = mx + c$), slope, and y-intercept of ";
        q += "$" + a.toLatexFormat() + "y = " + b.toLatexFormat() + "x + " + z.toLatexFormat() + "$";
        return q;
    }

    @Override
    public String getAnswerLatex() {
        Fraction m = b.divide(a);
        Fraction c = z.divide(a);
        String ans = "$y = " + m.toLatexFormat() + "x + " + c.toLatexFormat() + "$\\\\";
        ans += "slope = $" + m.toLatexFormat() + "$\\\\";
        ans += "y-intercept = $" + c.toLatexFormat() + "$";
        return ans;
    }
}
