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
        int gcd = findGCD(Math.abs(f.numer), f.denom);

        int denom = this.denom / gcd;
        int whole = f.numer / this.denom;
        int numer = (Math.abs(f.numer) - (Math.abs(whole) * Math.abs(this.denom))) / gcd;
        return new Fraction(whole, numer, denom);
    }

    public Fraction add(Fraction that) {
        Fraction f1 = this.getFullFraction();
        Fraction f2 = that.getFullFraction();

        int denom = f1.denom * f2.denom;
        int numer = f1.numer * f2.denom + f2.numer * f1.denom;
        return new Fraction(numer, denom).simplify();
    }

    public Fraction subtract(Fraction that) {
        Fraction f = that.getFullFraction();
        f.numer = -f.numer;
        return this.add(f);
    }

    public Fraction getFullFraction() {
        int newNumer = Math.abs(this.whole) * Math.abs(this.denom) + Math.abs(this.numer);
        int sign = this.whole != 0 ? this.whole : 1;
        sign    *= this.numer != 0 ? this.numer : 1;
        sign    *= this.denom != 0 ? this.denom : 1;
        sign     = sign < 0 ? -1 : 1;
        return new Fraction(newNumer * sign, denom);
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
    public String toString() {
        if (numer == 0) {
            return "" + this.whole;
        }
        return "" + this.whole + "^^" + this.numer + "//" + this.denom;
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

}
