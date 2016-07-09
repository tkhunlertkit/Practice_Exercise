package Algorithm;

/**
 * Created by MSDK on 7/8/16.
 */
public class StraightLine {

//    y  = mx + c
//    ay = bx + z
//    m = b / a
//    c = z / a

    private Fraction a, b, z;

    public StraightLine(Fraction a, Fraction b, Fraction z) {
        this.a = new Fraction(a);
        this.b = new Fraction(b);
        this.z = new Fraction(z);
    }

    public StraightLine(Fraction m, Fraction c) {
        this.a = new Fraction(1);
        this.b = new Fraction(m);
        this.z = new Fraction(c);
    }

    public static StraightLine generateRandomLine(int min, int max) {
        Fraction a = Fraction.generateRandomFraction(min, max);
        Fraction b = Fraction.generateRandomFraction(min, max);
        Fraction z = Fraction.generateRandomFraction(min, max);
        return new StraightLine(a, b, z);
    }

    public Fraction getSlope() {
        return b.divide(a);
    }

    public Fraction getYIntercept() {
        return z.divide(a);
    }

    public StraightLine scaleLine(Fraction scale) {
        return new StraightLine(this.a.multiply(scale), this.b.multiply(scale), this.z.multiply(scale));
    }

    public StraightLine getDuplicateLineEquation() {
        Fraction scale = Fraction.generateRandomFraction(1, 5);
        scale = Math.random() < 0.5 ? scale.negate() : scale;
        return this.scaleLine(scale);
    }

}
