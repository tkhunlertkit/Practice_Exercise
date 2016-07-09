package Algorithm;

/**
 * Created by MSDK on 7/6/16.
 */
public class Fraction {

    private int numer;
    private int denom;
    private int whole;

    public Fraction(int whole) {
        this.whole = whole;
        this.numer = 0;
        this.denom = 1;
    }

    public Fraction(Fraction a) {
        this.whole = a.whole;
        this.denom = a.denom;
        this.numer = a.numer;
    }

    public Fraction(int numer, int denom) {
        this.numer = numer;
        this.denom = denom;
    }

    public Fraction(int whole, int numer, int denom) {
        this.whole = whole;
        this.numer = numer;
        this.denom = denom;
    }

    public static Fraction generateRandomFraction(int min, int max) {
        int denom = (int)(Math.random() * max + 1);
        int numer = (int)(denom * ((Math.random() * (max - min)) + min));

        return new Fraction(numer, denom).simplify();
    }

    public Fraction simplify() {
        Fraction f = this.getFullFraction();
        int gcd = findGCD(Math.abs(f.numer), Math.abs(f.denom));

        int denom = Math.abs(this.denom) / gcd;
        int whole = Math.abs(f.numer) / Math.abs(f.denom);
        int numer = (Math.abs(f.numer) - (Math.abs(whole) * Math.abs(this.denom))) / gcd;
        if (whole == 0) {
            return new Fraction(numer * getSign(), Math.abs(denom));
        }
        return new Fraction(whole * getSign(), Math.abs(numer), Math.abs(denom));
    }

    public Fraction add(Fraction that) {
        Fraction f1 = this.getFullFraction();
        Fraction f2 = that.getFullFraction();

        int denom = f1.denom * f2.denom;
        int n1 = f1.numer * f2.denom;
        int n2 = f2.numer * f1.denom;
        int numer = n1 + n2;
        return new Fraction(numer, denom).simplify();
    }

    public Fraction negate() {
        return new Fraction(-this.whole, this.numer, this.denom);
    }

    public Fraction reciprocate() {
        Fraction f = this.getFullFraction();
        int tmp = f.numer;
        f.numer = f.denom;
        f.denom = tmp;
        return f.simplify();
    }

    public Fraction add(int num) {
        return this.add(new Fraction(num, 1));
    }

    public Fraction subtract(Fraction that) {
        Fraction f = that.getFullFraction();
        f.numer = -f.numer;
        return this.add(f);
    }

    public Fraction multiply(Fraction that) {
        Fraction f1 = this.getFullFraction();
        Fraction f2 = that.getFullFraction();
        return new Fraction(f1.numer * f2.numer, f1.denom * f2.denom).simplify();
    }

    public Fraction multiply(int that) {
        return this.multiply(new Fraction(that, 1));
    }

    public Fraction divide(Fraction that) {
        Fraction f = that.getFullFraction();
        return this.multiply(new Fraction(f.denom, f.numer));
    }

    public Fraction getFullFraction() {
        int newNumer = Math.abs(this.whole) * Math.abs(this.denom) + Math.abs(this.numer);
        return new Fraction(newNumer * getSign(), Math.abs(denom));
    }

    public int getNumer() {
        return this.numer;
    }

    public int getDenom() {
        return this.denom;
    }

    public int getWhole() {
        return this.whole;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Fraction) {
            Fraction that = (Fraction) other;
            Fraction thisFull = this.getFullFraction();
            Fraction thatFull = that.getFullFraction();
            return thisFull.numer == thatFull.numer && thisFull.denom == thatFull.denom;
        }
        return false;
    }

    @Override
    public String toString() {
        if (numer == 0) {
            return "" + this.whole;
        }
        return "" + this.whole + "^^" + this.numer + "//" + this.denom;
    }

    public String toLatexFormat() {
        if (numer == 0) {
            return "" + this.whole;
        }
        if (this.whole == 0) {
            return "\\frac{" + this.numer + "}{" + this.denom + "}";
        }
        return "" + this.whole + "\\frac{" + this.numer + "}{" + this.denom + "}";
    }

    public double toValue() {
        Fraction f = this.getFullFraction();
        return (double)f.numer / f.denom;
    }

    private int findGCD(int number1, int number2) {
        //base case
        if(number2 == 0){
            return number1;
        }
        return findGCD(number2, number1%number2);
    }

    private int getSign() {
        int sign = this.whole != 0 && this.whole < 0 ? -1 : 1;
        sign    *= this.numer != 0 && this.numer < 0 ? -1 : 1;
        sign    *= this.denom != 0 && this.denom < 0 ? -1 : 1;
        return sign < 0 ? -1 : 1;
    }


    public static void main(String[] args) {
        Fraction f = new Fraction(3366, 9999);
        System.out.println(f.simplify());
    }
}
