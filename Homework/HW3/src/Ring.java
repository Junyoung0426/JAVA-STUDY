//Ring is an algebra that supports + - * operations
public interface Ring {
    public Ring add(Ring a);
    public Ring addIdentity();
    public Ring addInverse();
    public Ring mul(Ring a);

    //default methods
    public default Ring sub(Ring a) {
        return this.add(a.addInverse());
    }
}

