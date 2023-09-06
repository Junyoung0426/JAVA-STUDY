//Rat represents rational number
//  Rat(10, 15) is 10/15 and is equivalent to Rat(2,3)

public class Rat implements Field, Modulo, Ordered {
    private int n, d;   //numerator, denominator

    public Rat(int n, int d) {
        if (d == 0)
            throw new ArithmeticException("Division by zero");
        //TODO: Using Euclidean and Int, reduce numerator/denominator
        //to its lowest terms (divide them by their gcd)
        Ring numerator = new Int(n);
        Ring denominator = new Int(d);
        Ring gcd = Euclidean.GCD(numerator, denominator);
        Int commonDivisor = (Int) gcd;
        this.n = n / commonDivisor.getInt();
        this.d = d / commonDivisor.getInt();
    }

    //interface Ring
    //TODO: implement interface Ring

    public Ring add(Ring a) {
        Rat r = (Rat) a;
        if (d == r.d) {
            return new Rat(n + r.n, d);
        }
        return new Rat(r.n * d + n * r.d, d * r.d);
    }

    public Ring addIdentity() {
        return new Rat(0, 1);
    }

    public Ring addInverse() {
        return new Rat(-n, d);
    }

    public Ring mul(Ring a) {
        Rat r = (Rat) a;
        return new Rat(n * r.n, d * r.d);
    }

    //interface Field
    //TODO: implement interface Field
    public Ring mulIdentity() {
        return new Rat(1, 1);
    }

    public Ring mulInverse() throws ArithmeticException {
        if (n == 0 || d == 0) {
            throw new ArithmeticException("Never Divide by zero");
        }
        return new Rat(d, n);
    }

    //interface Modulo
    public Ring quo(Ring a) throws ArithmeticException {
        Rat r = (Rat) a;
        if (r.n == 0)
            throw new ArithmeticException("Division by zero");
        //Let rem be this mod r, then quotient is (this - rem) / r
        //Of course, - and / should be replaced by the proper operations            
        return new Rat((n * r.d) / (d * r.n), 1);
    }

    //interface Ordered
    //TODO: implement interface Ordered
    public boolean ge(Ordered a) {
        Rat r = (Rat) a;
        return n * r.d >= r.n * d;
    }


    //Rat specific methods
    public int getNum() {
        return n;
    }

    public int getDen() {
        return d;
    }

    public String toString() {
        return String.format("%d/%d", n, d);
    }


}
