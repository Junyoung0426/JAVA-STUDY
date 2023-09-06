@SuppressWarnings("unchecked")
public class HeapSort<E extends Comparable<E>> {
    protected E[] arr;
    protected int size;

    public HeapSort() {
        arr = (E[]) new Comparable[16];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E min() {
        if (isEmpty())
            throw new IndexOutOfBoundsException("Empty heap");
        return arr[0];
    }

    public void add(E e) {
        //dynamic array
        if (size + 1 == arr.length)
            resize(arr.length * 2);

        //TODO: add e at arr[size] and increase size
        arr[size++] = e;
        upheap(size - 1);

        //TODO: call upheap

    }

    public E remove() {
        if (isEmpty())
            throw new IndexOutOfBoundsException("Empty heap");

        E ret = arr[0];
        //TODO: copy the last element to the root and reduce size
        arr[0] = arr[--size];
        //TODO: call downheap
        downheap(0);
        return ret;
    }

    protected int parent(int i) {
        return (i - 1) / 2;
    }

    protected int left(int i) {
        return 2 * i + 1;
    }

    protected int right(int i) {
        return 2 * i + 2;
    }

    protected boolean hasLeft(int i) {
        return left(i) < size;
    }

    protected boolean hasRight(int i) {
        return right(i) < size;
    }

    protected void swap(int i, int j) {
        E tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    protected void resize(int cap) {
        E[] tmp = (E[]) new Comparable[cap];
        for (int i = 0; i < arr.length; i++)
            tmp[i] = arr[i];
        arr = tmp;
    }


    protected void upheap(int i) {
        if (i == 0) {
            return;
        }
        //TODO: if i is the root, return
        int p = parent(i);
        if (arr[p].compareTo(arr[i]) <= 0)
            return;
        swap(i, p);
        upheap(p);
    }
    //TODO: if parent is less than or equal to arr[i], return

    //TODO: swap

    //TODO: recursivelyu call upheap


    protected void downheap(int i) {
        //Find the smaller child
        //TODO: if i does not have left child, return
        if (!hasLeft(i))
            return;
        int c = left(i);
        if (hasRight(i)) {
            int r = right(i);
            if (arr[r].compareTo(arr[c]) <= 0)
                c = r;
        }
        //TODO: if i has right child and it is smaller than arr[c], c = right child
        if (arr[c].compareTo(arr[i]) >= 0)
            return;
        //TODO: if arr[c] is larger than or equal to arr[i], return
        swap(i, c);
        //TODO: swap i and c
        downheap(c);
        //TODO: recursively call downheap

    }

    public static <E extends Comparable<E>> void sort(E[] arr) {
        HeapSort<E> heap = new HeapSort<E>();

        //TODO: add arr elements to heap
        for (E e : arr)
            heap.add(e);

        //TODO: remove elements from heap and add them to arr
        for (int i = 0; !heap.isEmpty(); i++)
            arr[i] = heap.remove();
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{3, 5, 2, 4, 1, 8, 7, 6, 0, 9};

        System.out.println("Before sorting");
        for (int i : arr)
            System.out.print(i + ", ");
        System.out.println();

        sort(arr);

        System.out.println("After sorting");
        for (int i : arr)
            System.out.print(i + ", ");
        System.out.println();
    }
}