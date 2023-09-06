//Int represents integer
public class Int implements Ring, Modulo, Ordered {
    private int n;

    public Int(int n) {
        this.n = n;
    }

    //interface Ring
    public Ring add(Ring a) {
        return new Int(n + ((Int)a).n);
    }
    public Ring addIdentity() {
        return new Int(0);
    }
    public Ring addInverse() {
        return new Int(-n);
    }
    public Ring mul(Ring a) {
        return new Int(n * ((Int)a).n);
    }

    //interface Modulo
    public Ring quo(Ring a) {
        return new Int(n / ((Int)a).n);
    }

    //interface Ordered
    public boolean ge(Ordered a) {
        return n >= ((Int)a).n;
    }

    //additional methods
    public int getInt() {
        return n;
    }
    public String toString() {
        return String.format("%d", n);
    }
}
