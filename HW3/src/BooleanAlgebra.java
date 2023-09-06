public interface BooleanAlgebra {
    //or operation
    public BooleanAlgebra or(BooleanAlgebra a);

    //and operation
    public BooleanAlgebra and(BooleanAlgebra a);

    //not operation
    public BooleanAlgebra not();

    //identity of the or operation
    public BooleanAlgebra orIdentity();

    //identity of the and operation
    public BooleanAlgebra andIdentity();
    
    //Additional methods: whether a is equal to this
    public boolean isEqual(BooleanAlgebra a);
}
