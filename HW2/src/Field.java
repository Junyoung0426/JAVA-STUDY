//Field is an algebra that supports + - * / operations
public interface Field extends Ring {
    public Ring mulIdentity();
    public Ring mulInverse() throws ArithmeticException;

    //default methods
    public default Field div(Field a) {
        return (Field)this.mul(a.mulInverse());
    }
}
