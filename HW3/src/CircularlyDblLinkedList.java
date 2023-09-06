import java.util.Iterator;

public class CircularlyDblLinkedList<E> implements List<E>, Iterable<E> {
    protected static class Node<E> {
        public E e;
        public Node<E> prev, next;

        public Node() {
            this.e = null;
            this.prev = this;
            this.next = this;
        }

        public Node(E e, Node<E> prev, Node<E> next) {
            this.e = e;
            this.prev = prev;
            this.next = next;
        }
    }

    public static class NodeIterator<E> implements Iterator<E> {
        private Node<E> head, curr;

        public NodeIterator(Node<E> head) {
            this.head = head;
            this.curr = head.next;
        }
        //TODO: implement Iterator<E>

        public boolean hasNext() {
            return curr.next != head.next;
        }

        public E next() {
            E elem = curr.e;
            curr = curr.next;
            return elem;
        }
    }


    protected Node<E> head;
    protected int size;

    //constructor
    public CircularlyDblLinkedList() {
        head = new Node<E>();
        size = 0;
    }

    //TODO: implement interface List (use findNode)
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E set(int i, E e) throws IndexOutOfBoundsException {
        Node<E> oldNode = findNode(i);
        E oNode = oldNode.e;
        oldNode.e = e;
        return oNode;
    }

    public void add(int i, E e) throws IndexOutOfBoundsException {
        size++;
        Node<E> oldNode = findNode(i);
        Node<E> newest = new Node(e, oldNode.prev, oldNode);
        newest.prev = oldNode.prev;
        newest.next = oldNode;
        oldNode.prev = newest;
        newest.prev.next = newest;

    }

    public E get(int i) {
        return findNode(i).e;
    }

    public void add(E e) throws IndexOutOfBoundsException {
        add(size, e);
    }

    public E remove(int i) throws IndexOutOfBoundsException {
        Node<E> oldNode = findNode(i);
        oldNode.prev.next = oldNode.next;
        oldNode.next.prev = oldNode.prev;
        size--;
        return oldNode.e;
    }


    //TODO: implement interface Iterable

    public Iterator<E> iterator() {
        return new NodeIterator(head);
    }

    //helper methods
    protected Node<E> findNode(int i) {
        if (i < 0 || i >= size)
            throw new IndexOutOfBoundsException("invalid index: " + i + " is not in [ 0, " + size + ")");
        Node<E> nextNode = head.next;
        for (int j = 0; j < i; j++)
            nextNode = nextNode.next;
        return nextNode;
        //TODO: find the node at index i and return it
    }

    private static void onFalseThrow(boolean b) {
        if (!b)
            throw new RuntimeException("Error: unexpected");
    }

    public static void main(String[] args) {
        CircularlyDblLinkedList<Integer> list = new CircularlyDblLinkedList<Integer>();
        list.add(list.size(), 2);
        list.add(list.size(), 3);
        list.add(list.size(), 4);
        list.add(0, 1);
        onFalseThrow(list.remove(list.size() - 1) == 4);
        onFalseThrow(list.remove(list.size() - 1) == 3);
        onFalseThrow(list.remove(0) == 1);
        onFalseThrow(list.remove(list.size() - 1) == 2);
        onFalseThrow(list.isEmpty());
        System.out.println("Success!");
    }
}