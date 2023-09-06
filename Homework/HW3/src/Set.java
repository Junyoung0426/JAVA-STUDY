public interface Set<E extends Ordered> {
    //whether this has e
    public boolean has(E e);

    //whether this is equal to set
    public boolean isEqual(Set<E> set);

    //return this union set
    public Set<E> union(Set<E> set);

    //return this intersection with set
    public Set<E> intersection(Set<E> set);

    //return this - set
    public Set<E> difference(Set<E> set);
}
