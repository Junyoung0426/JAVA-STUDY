//IntMod represents integer modulo system
//IntMod(n, m) means n modulo m, where m is a prime number.
//E.g. modulo 5 system
// Modulo 5 system has [n] = {x | x % 5 = n} for n = 0, ... , 4
//    i.e., [0] = {0, 5, 10, ...}, [1] = {1, 6, 11, ...}, ... [4] = {4, 9, 14, ...}
//
// IntMod(7, 5) means 7 in modulo 5 system. Hence, IntMod(7, 5) is in [2]
//   hint for Ordered,    [0] <= [1] <= [2] <= [3] <= [4]
//   hint for addition,   IntMod(7, 5) + IntMod(8, 5) = IntMod(15, 5) = IntMod(0, 5)
//   hint for mulInverse, IntMod(7, 5) * IntMod(3, 5) = IntMod(21, 5) = IntMod(1, 5)
//   hint for division,   IntMod(8, 5) / IntMod(7, 5) = IntMod(8, 5) * IntMod(3, 5) = IntMod(4, 5)

public class IntMod implements Field, Ordered {
    private int n, m;

    public IntMod(int n, int m) {
        if (m <= 0)
            throw new IllegalArgumentException("Expecting a positive divisior");
        n = n % m;
        n = n < 0 ? n + m : n;  //if n is negative, make it positive
        this.n = n;
        this.m = m;
    }

    //interface Ring
    //TODO: implement interface Ring
    public Ring add(Ring a) {
        if (((IntMod) a).m != this.m) {
            throw new IllegalArgumentException("We cannot do that there mod is not same");
        }
        return new IntMod(this.n + ((IntMod) a).n, m);
    }

    public Ring addIdentity() {
        return new IntMod(0, m);
    }

    public Ring addInverse() {
        return new IntMod(-n, m);
    }

    public Ring mul(Ring a) {
        if (((IntMod) a).m != this.m) {
            throw new IllegalArgumentException("We cannot do that their mod is not same");
        }
        return new IntMod(this.n * ((IntMod) a).n, m);
    }


    //interface Field
    //TODO: implement interface Field
    public Ring mulIdentity() {
        return new IntMod(1, m);
    }

    public Ring mulInverse() throws ArithmeticException {
        for (int x = m - 1; x >= 0; x--)
            if ((x * n) % m == 1)
                return new IntMod(x, m);
        return null;
    }


    //interface Ordered
    //TODO: implement interface Ordered
    public boolean ge(Ordered a) {
        if (((IntMod) a).m != this.m) {
            throw new IllegalArgumentException("We cannot do that, their mod is not same");
        }
        return n >= ((IntMod) a).n;
    }

    //IntMod specific methods
    public int getInt() {
        return n;
    }

    public int getMod() {
        return m;
    }

    public String toString() {
        return String.format("%d%%%d", n, m);
    }
}
