import java.util.Iterator;

@SuppressWarnings("unchecked")
public class DynamicArrayDeque<E> implements Deque<E> {
    private E[] data;
    private int f;
    private int size;
    private static final int capacity = 16;

    public DynamicArrayDeque() {
        this(capacity);
    }

    public DynamicArrayDeque(int capacity) {
        data = (E[]) new Object[capacity];
    }

    //TODO: implement DynamicArrayDeque
    //      in addFirst and addLast, resize when the array is full

    private void resize(int capacity) {
        E[] tmp = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            tmp[i] = data[((f + i ) + data.length) % data.length];
        data = tmp;
        f=0;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        return data[f];
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        return data[((f + size - 1) + data.length) % data.length];
    }

    @Override
    public void addFirst(E e) {
        if (size == data.length)
            resize(2 * data.length);
        f = ((f - 1) + data.length) % data.length;
        data[f] = e;
        size++;
    }

    @Override
    public void addLast(E e) {
        if (size == data.length)
            resize(2 * data.length);
        int i = (((((f + size - 1) + data.length) % data.length) + 1) + data.length) % data.length;
        data[i] = e;
        size++;
    }

    @Override
    public E removeFirst() {
        if (isEmpty())
            throw new IllegalMonitorStateException("Deque is empty");
        E element = data[f];
        data[f] = null;
        f = ((f + 1) + data.length) % data.length;
        size--;
        return element;
    }

    public E removeLast() {
        if (isEmpty())
            throw new IllegalMonitorStateException("Deque is empty");
        int i = ((f + size - 1) + data.length) % data.length;
        E element = data[i];
        data[i] = null;
        size--;
        return element;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<E> {
        int i;
        public ArrayIterator() {
            i = f;
        }

        public boolean hasNext() {
            return (i-1 + data.length) % data.length != ((f + size - 1) + data.length) % data.length;
        }

        public E next() {
            return data[((i++) + data.length) % data.length];
        }
        //TODO: implement ArrayIterator
    }

    private static void onFalseThrow(boolean b) {
        if (!b)
            throw new RuntimeException("Error: unexpected");
    }

    public static void main(String[] args) {
        DynamicArrayDeque<Integer> dq = new DynamicArrayDeque<>(1);

        for (int i : dq)
            onFalseThrow(false);
        onFalseThrow(dq.size() == 0);
        onFalseThrow(dq.isEmpty() == true);

        dq.addFirst(3);
        dq.addFirst(2);
        dq.addFirst(1);
        dq.addLast(4);
        dq.addLast(5);

        int j = 1;
        for (int i : dq)
            onFalseThrow(i == j++);
        onFalseThrow(dq.size() == 5);
        onFalseThrow(dq.isEmpty() == false);

        for (int i = 1; i <= 3; i++)
            onFalseThrow(i == dq.removeFirst());
        onFalseThrow(dq.removeLast() == 5);
        onFalseThrow(dq.removeLast() == 4);

        onFalseThrow(dq.size() == 0);
        onFalseThrow(dq.isEmpty() == true);

        System.out.println("Success!");
    }
}