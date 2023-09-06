public interface List<E> {
    //get the size of the list
    public int size();

    //is the list empty?
    public boolean isEmpty();

    //get element at index i
    public E get(int i) throws IndexOutOfBoundsException;

    //set e at index i
    public E set(int i, E e) throws IndexOutOfBoundsException;

    //add e at index i
    public void add(int i, E e) throws IndexOutOfBoundsException;

    //add e at the last index
    public void add(E e) throws IndexOutOfBoundsException;

    //remove node a index i and return its element
    public E remove(int i) throws IndexOutOfBoundsException;
}
