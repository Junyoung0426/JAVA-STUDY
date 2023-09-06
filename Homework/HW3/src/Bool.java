public class Bool implements BooleanAlgebra {
    public boolean b;
    
    public Bool(boolean b) { this.b = b; }
    
    //interface BooleanAlgebra
    public BooleanAlgebra or(BooleanAlgebra a) {
        return new Bool(b || ((Bool)a).b);
    }
    public BooleanAlgebra and(BooleanAlgebra a) {
        return new Bool(b && ((Bool)a).b);
    }
    public BooleanAlgebra not() {
        return new Bool(!b);
    }
    public BooleanAlgebra orIdentity() {
        return new Bool(false);
    }
    public BooleanAlgebra andIdentity() {
        return new Bool(true);
    }
    public boolean isEqual(BooleanAlgebra a) {
        return b == ((Bool)a).b;
    }
}
