import java.util.ArrayList;

public class BinarySearchTree<E extends Comparable<E>> {
    private static class Node<E extends Comparable<E>> {
        public E e;
        public Node<E> left, right;

        public Node(E e, Node<E> left, Node<E> right) {
            this.e = e;
            this.left = left;
            this.right = right;
        }
    }

    private Node<E> root;

    public BinarySearchTree() {
    }

    //TODO: implement find method
    public Node<E> find(E e) {
        if (root == null)
            return null;
        return find(root, e);
    }

    private Node<E> find(Node<E> node, E e) {
        if (e.compareTo(node.e) == 0) {
            return node;
        } else if (e.compareTo(node.e) < 0) {
            if (node.left == null)
                return null;
            return find(node.left, e);
        } else {
            if (node.right == null)
                return null;
            return find(node.right, e);
        }
    }

    //TODO: implement add method
    public void add(E e) {
        if (root == null)
            root = new Node<>(e, null, null);
        add(root, e);
    }

    private void add(Node<E> node, E e) {
        if (e.compareTo(node.e) < 0) {
            if (node.left == null) {
                node.left = new Node<>(e, null, null);
            } else {
                add(node.left, e);
            }
        } else {
            if (node.right == null) {
                node.right = new Node<>(e, null, null);
            } else {
                add(node.right, e);
            }
        }

    }

    //TODO: implement visitInorder method
    public Iterable<E> visitInorder() {
        java.util.List<E> snapshot = new ArrayList<E>(5);
        visitInorder(root, snapshot);
        return snapshot;
    }

    private void visitInorder(Node<E> node, java.util.List<E> snapshot) {
        if (node.left != null) {
            visitInorder(node.left, snapshot);
        }
        snapshot.add(node.e);
        if (node.right != null) {
            visitInorder(node.right, snapshot);
        }
    }

    public static void find(Integer[] arr) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (Integer e : arr)
            tree.add(e);
        for (int i = 0; i < 10; i++)
            System.out.print((tree.find(i) != null) + ", ");
        System.out.println("");
    }

    //TODO: implement sort by BinarySearchTree
    public static void sort(Integer[] arr) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (Integer e : arr) {
            tree.add(e);
        }
        for (Integer e : tree.visitInorder())
            System.out.println(e + " ");

        //TODO: 1. add elements of arr to tree

        //TODO: 2. print the inorder traversal result

        System.out.println("");
    }

    public static void main(String[] args) {
        Integer[] arr = {5, 3, 6, 2, 8, 1, 9, 4, 7};
        find(arr);
        sort(arr);
    }
}