public class Duel implements BooleanAlgebra {
    private BooleanAlgebra b;
    
    //constructors
    public Duel(BooleanAlgebra b) { this.b = b; }

    //interface BooleanAlgebra
    public BooleanAlgebra or(BooleanAlgebra a)  { return new Duel(b.and(castOrThrow(a).b)); } 
    public BooleanAlgebra and(BooleanAlgebra a) { return new Duel(b.or(castOrThrow(a).b)); }
    public BooleanAlgebra not()                 { return new Duel(b.not()); }
    public BooleanAlgebra orIdentity()          { return new Duel(b.andIdentity()); }
    public BooleanAlgebra andIdentity()         { return new Duel(b.orIdentity()); }
    public boolean isEqual(BooleanAlgebra a)    { return b.isEqual(castOrThrow(a).b); }
    
    //private methods
    private Duel castOrThrow(BooleanAlgebra a) {
        return (Duel)a;
    }
}
