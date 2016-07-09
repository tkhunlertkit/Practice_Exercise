package UnitTest;

import Algorithm.Fraction;
import Algorithm.Algebra.SolveXSimple;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MSDK on 7/6/16.
 */
public class FractionTest {

    private int MAX_ITERATION = 10000;

    Fraction f1;
    Fraction f2;
    Fraction f3;
    Fraction f4;
    Fraction f5;
    Fraction f6;
    Fraction f7;

    @Before
    public void setupTest() {
        f1 = new Fraction( 7, 3);
        f2 = new Fraction(-7, 3);
        f3 = new Fraction( 2, 3);
        f4 = new Fraction( 1, 4);
        f5 = new Fraction( 2, 3, 4);
        f6 = new Fraction(-2, 3, 4);
        f7 = new Fraction( -1, 4);
    }

    @Test
    public void testSimplify() {
        Fraction g = f1.simplify();
        Assert.assertEquals(g.getWhole(), 2);
        Assert.assertEquals(g.getNumer(), 1);
        Assert.assertEquals(g.getDenom(), 3);

        g = f2.simplify();
        Assert.assertEquals(g.getWhole(), -2);
        Assert.assertEquals(g.getNumer(), 1);
        Assert.assertEquals(g.getDenom(), 3);

        g = f7.simplify();
        Assert.assertEquals(g.getWhole(),  0);
        Assert.assertEquals(g.getNumer(), -1);
        Assert.assertEquals(g.getDenom(),  4);
    }

    @Test
    public void testAdd() {
        Fraction g = f1.add(f2);
        Assert.assertEquals(0, g.getWhole());
        Assert.assertEquals(0, g.getNumer());
        Assert.assertEquals(1, g.getDenom());

        g = f5.add(f4);
        Assert.assertEquals(3, g.getWhole());
        Assert.assertEquals(0, g.getNumer());
        Assert.assertEquals(1, g.getDenom());

        g = f5.add(f3);
        Assert.assertEquals(3, g.getWhole());
        Assert.assertEquals(5, g.getNumer());
        Assert.assertEquals(12, g.getDenom());

//        11 / 4 =  33 / 12
//        -7 / 3 = -28 / 12
//               =   5 / 12
        g = f5.add(f2);
        Assert.assertEquals(0, g.getWhole());
        Assert.assertEquals(5, g.getNumer());
        Assert.assertEquals(12, g.getDenom());


    }

    @Test
    public void testGetFullFraction() {
        Fraction g = f5.getFullFraction();
        Assert.assertEquals(g.getWhole(),  0);
        Assert.assertEquals(g.getNumer(), 11);
        Assert.assertEquals(g.getDenom(),  4);

        g = f6.getFullFraction();
        Assert.assertEquals(g.getWhole(),   0);
        Assert.assertEquals(g.getNumer(), -11);
        Assert.assertEquals(g.getDenom(),   4);

        g = f2.getFullFraction();
        Assert.assertEquals(g.getWhole(),   0);
        Assert.assertEquals(g.getNumer(),  -7);
        Assert.assertEquals(g.getDenom(),   3);
    }

    @Test
    public void testSubtract() {
        Fraction g = f1.subtract(f2);
        Assert.assertEquals(g.getWhole(),  4);
        Assert.assertEquals(g.getNumer(),  2);
        Assert.assertEquals(g.getDenom(),  3);

        g = f3.subtract(f4);
        Assert.assertEquals(g.getWhole(),  0);
        Assert.assertEquals(g.getNumer(),  5);
        Assert.assertEquals(g.getDenom(), 12);

        g = f4.subtract(f7);
        Assert.assertEquals(g.getWhole(),  0);
        Assert.assertEquals(g.getNumer(),  1);
        Assert.assertEquals(g.getDenom(),  2);

        g = f7.subtract(f2);
        Assert.assertEquals(g.getWhole(),  2);
        Assert.assertEquals(g.getNumer(),  1);
        Assert.assertEquals(g.getDenom(), 12);
    }

    @Test
    public void testToValue() {
        Assert.assertEquals(f1.toValue(),  2.33, 0.01);
        Assert.assertEquals(f2.toValue(), -2.33, 0.01);
        Assert.assertEquals(f5.toValue(),  2.75, 0.01);
        Assert.assertEquals(f6.toValue(), -2.75, 0.01);
        Assert.assertEquals(f7.toValue(), -0.25, 0.01);
        Assert.assertEquals(f4.toValue(),  0.25, 0.01);
    }

    @Test
    public void testGenerateRandomFraction() {
        for (int i=0; i<MAX_ITERATION; i++) {
            int min = (int)(Math.random() * 10 + 1);
            int max = (int)(Math.random() * 100 + 1) + min;
            Fraction g = Fraction.generateRandomFraction(min, max);
//            System.out.println("(" + min + ", " + max + "] = " + g);
            Assert.assertTrue(g.toValue() < max);
            Assert.assertTrue(g.toValue() >= min);
        }
    }

    @Test
    public void testRandomSubtractUsingValue() {
        for (int i=0; i<MAX_ITERATION; i++) {
            int min = (int)(Math.random() * 10 + 1);
            int max = (int)(Math.random() * 100) + min;
            Fraction g = Fraction.generateRandomFraction(min, max);
            Fraction h = Fraction.generateRandomFraction(min, max);
            Fraction a = g.subtract(h);
            double val = g.toValue() - h.toValue();
            Assert.assertEquals(a.toValue(), val, 0.001);
        }
    }

    @Test
    public void testMultiplyComparedtoValues() {
        for (int i=0; i<MAX_ITERATION; i++) {
            int min = (int)(Math.random() * 10 + 1);
            int max = (int)(Math.random() * 100) + min;
            Fraction g = Fraction.generateRandomFraction(min, max);
            Fraction h = Fraction.generateRandomFraction(min, max);
            Fraction a = g.multiply(h);
            double val = g.toValue() * h.toValue();
//            System.out.println(String.format("%10s * %10s = %20s =?= %10.5f", g.getFullFraction(), h.getFullFraction(), a, val));
//            System.out.println(a + " =?= " + val);
            Assert.assertEquals(a.toValue(), val, 0.001);
        }
    }


    @Test
    public void testDivideComparedtoValues() {
        for (int i=0; i<MAX_ITERATION; i++) {
            int min = (int)(Math.random() * 10 + 1);
            int max = (int)(Math.random() * 100) + min;
            Fraction g = Fraction.generateRandomFraction(min, max);
            Fraction h = Fraction.generateRandomFraction(min, max);
            Fraction a = g.divide(h);
            double val = g.toValue() / h.toValue();
//            System.out.println(String.format("%10s * %10s = %20s =?= %10.5f", g.getFullFraction(), h.getFullFraction(), a, val));
//            System.out.println(a + " =?= " + val);
            Assert.assertEquals(a.toValue(), val, 0.001);
        }
    }

    @Test
    public void testMultiplytoInteger() {
        for (int i=0; i<MAX_ITERATION; i++) {
            int min = (int)(Math.random() * 10 + 1);
            int max = (int)(Math.random() * 100) + min;
            Fraction a = Fraction.generateRandomFraction(min, max);
            int x = max;
            Fraction actual = a.multiply(x);
            double expected = a.toValue() * x;
            Assert.assertEquals(expected, actual.toValue(), 0.001);
        }
    }

    @Test
    public void testSolvingProblem() {
        for (int i=0; i<MAX_ITERATION; i++) {
            int min = (int)(Math.random() * 10 + 1);
            int max = (int)(Math.random() * 100) + min;
            SolveXSimple algo = new SolveXSimple();
            algo.generateQuestion(max);

            double x = algo.getAnswer();
            double sum = 0;
            for (int k = 0; k < algo.getA().size(); k++) {
                sum += (algo.getA().get(k).toValue() * x) + algo.getB().get(k).toValue();
            }

            Assert.assertEquals(algo.getC().toValue(), sum, 0.001);
        }
    }

    @Test
    public void testReciprocate() {
        for (int i=0; i<MAX_ITERATION; i++) {
            int numer = -(int)(Math.random() * 10 + 1);
            int denom = -((int)((Math.random() * 100) + numer));

            numer = Math.random() < 0.5 ? -numer : numer;
            denom = Math.random() < 0.5 ? -denom : denom;

            Fraction f = new Fraction(numer, denom);
//            System.out.println(numer + "     " + denom + " : " + f + "    " + f.getFullFraction() + "       " + f.reciprocate());
            Assert.assertEquals(f.reciprocate().toValue(), 1/f.toValue(), 0.001);
        }
    }
}
