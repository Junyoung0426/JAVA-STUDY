//a = Subset({1,2}, {1,2,3,4}), b = Subset({2,3}, {1,2,3,4});
//a and b = Subset({2},     {1,2,3,4}) : intersection
//a or  b = Subset({1,2,3}, {1,2,3,4}) : union
//not a   = Subset({3,4},   {1,2,3,4}) : complement

public class Subset<E extends Ordered> implements BooleanAlgebra {
    //a subset of univ
    private Set<E> subset;

    //universal set
    private Set<E> univ;

    public Subset(Set<E> subset, Set<E> univ) {
        this.subset = subset.union(new SetImpl<E>());
        this.univ = univ.union(new SetImpl<E>());
    }

    //interface BooleanAlgebra
    public BooleanAlgebra or(BooleanAlgebra a) {
        //TODO: return the union of this and a
        Subset<E> set = castOrThrow(a);
        return new Subset (subset.union(set.subset),univ);
    }

    public BooleanAlgebra and(BooleanAlgebra a) {
        //TODO: return the intersection of this and a
        Subset<E> set = castOrThrow(a);
        return new Subset (subset.intersection(set.subset),univ);
    }

    public BooleanAlgebra not() {
        //TODO: return univ - this
        return new Subset(univ.difference(subset),univ);
    }

    public BooleanAlgebra orIdentity() {
        //TODO: return the or identity
        return new Subset(this.subset.difference(this.subset),univ);
    }

    public BooleanAlgebra andIdentity() {
        //TODO: return the and identity
        return new Subset(univ,univ);
    }

    public boolean isEqual(BooleanAlgebra a) {
        Subset<E> s = castOrThrow(a);
        return subset.isEqual(s.subset) && univ.isEqual(s.univ);
    }

    @SuppressWarnings("unchecked")
    private Subset<E> castOrThrow(BooleanAlgebra a) {
        Subset<E> s = (Subset<E>)a;
        if(!univ.isEqual(s.univ))
            throw new IllegalArgumentException("Unmatched univere");
        return s;
    }
}
