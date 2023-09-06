public class Heap<E extends Comparable<E>> {
    protected E[] arr;
    protected int size;

    public Heap() {
        arr = null;
        size = 0;
    }

    public E min() {
        if (size <= 0)
            throw new IndexOutOfBoundsException("Empty heap");
        return arr[0];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public void add(E e) {
        if (arr == null) {
            arr = (E[]) new Comparable[16];
        }
        //dynamic array
        if (size + 1 == arr.length) {
            E[] tmp = (E[]) new Comparable[arr.length * 2];
            for (int i = 0; i < arr.length; i++)
                tmp[i] = arr[i];
            arr = tmp;
        }
        arr[size] = e;
        size++;
        upheap(size - 1);


        //TODO: append e to arr and call upheap
    }

    public E remove() {
        if (size <= 0)
            throw new IndexOutOfBoundsException("Empty heap");
        swap(0, size - 1);
        E ret = arr[size - 1];
        arr[size - 1] = null;
        size --;
        downheap(0);
        return ret;
        //TODO: remove arr[0] and call downheap
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

    protected void swap(int i, int j) {
        E tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    protected void upheap(int i) {
        //TODO: implement upheap
        while (i > 0) {
            int p = parent(i);
            if (get(i).compareTo(get(p)) >= 0)
                break;
            swap(i, p);
            i = p;
        }
    }

    protected void downheap(int i) {
        //TODO: implement downheap
        while (left(i) < size) {
            int l = left(i);
            int c = l;
            if (right(i) < size) {
                int r = right(i);
                if (get(l).compareTo(get(r)) > 0)
                    c = r;
            }
            if (get(c).compareTo(get(i)) >= 0)
                break;
            swap(i, c);
            i = c;
        }
    }

    //for the test code
    protected E get(int i) {
        return arr[i];
    }

    protected static void onFalseThrow(boolean b) {
        if (!b)
            throw new RuntimeException("Error: unexpected");
    }

    public static void main(String[] args) {
        Heap<Integer> heap = new Heap<Integer>();
        int[] arr = new int[]{3, 5, 2, 4, 1, 8, 7, 6, 0, 9};

        //test add
        //
        for (int i = 0; i < 10; i++)
            heap.add(arr[i]);
        int[] ord = new int[]{0, 1, 3, 2, 4, 8, 7, 6, 5, 9};
        for (int i = 0; i < 10; i++)
            onFalseThrow(heap.get(i) == ord[i]);
        System.out.println("Success: add");

        //test remove
        //
        for (int i = 0; i < 5; i++)
            onFalseThrow(heap.remove() == i);

        ord = new int[]{5, 7, 6, 8, 9};
        for (int i = 0; i < 5; i++)
            onFalseThrow(heap.get(i) == ord[i]);

        for (int i = 0; i < 5; i++)
            onFalseThrow(heap.remove() == i + 5);
        System.out.println("Success: remove");
    }
}
