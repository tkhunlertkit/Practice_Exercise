package Algorithm.Algebra;

import Algorithm.MyAlgorithm;

import java.util.Random;

/**
 * Created by MSDK on 7/15/16.
 */
public class SumConsecutive extends MyAlgorithm{

    private enum Type {
        STRAIGHT, ODD, EVEN
    }

    private int beginNumber, numberofConsec, total = 0, delta;
    private Type type;
    private int MAX_CAP = 50;

    @Override
    public String generateQuestion(int max) {
        max            = max > MAX_CAP ? MAX_CAP : max;
        beginNumber    = (int)(Math.random() * max + (max / 2));
        numberofConsec = (int)(Math.random() * 3   + 3); //[3, 5]
        type           = randomType();
        delta          = 2;

        String question = "";
        switch(type) {
            // straight consecutive numbers
            case STRAIGHT:
                delta = 1;
                break;

            // odd consecutive numbers
            case ODD:
                if (beginNumber % 2 == 0) beginNumber++;
                break;

            // even consecutive numbers
            default:
                if (beginNumber % 2 == 1) beginNumber++;
                break;
        }

        for (int i=beginNumber; i<beginNumber+ (numberofConsec * delta); i += delta) {
            total += i;
        }


        return question;
    }

    @Override
    public String toLatexFormat() {
        String question = "Find all the numbers when the sum of $" + numberofConsec + "$ consecutive ";
        question += type != Type.STRAIGHT ? type : "";
        question += " numbers is $" + total + "$.";
        return question;
    }

    @Override
    public String getAnswerLatex() {
        String ans = "Answer: ";
        for (int i=0; i<numberofConsec; i++) {
            ans += (beginNumber + (delta * i));
            ans += i < numberofConsec - 1 ? ", " : "";
        }
        return ans;
    }

    private Type randomType() {
        int pick = new Random().nextInt(Type.values().length);
        return Type.values()[pick];
    }

    public static void main(String[] args) {
        SumConsecutive s = new SumConsecutive();
        s.generateQuestion(30);
        System.out.println(s.toLatexFormat());
        System.out.println(s.getAnswerLatex());
    }
}
