public class BSTRestructure<E extends Comparable<E>> {
    protected static class Node<E extends Comparable<E>> {
        public E e;
        public Node<E> parent, left, right;
        public Node(E e, Node<E> parent, Node<E> left, Node<E> right) {
            this.e = e; this.parent = parent; this.left = left; this.right = right;
        }
    }

    private Node<E> root;

    public BSTRestructure() {}

    protected static final boolean AS_LEFT_CHILD = true;
    protected static final boolean AS_RIGHT_CHILD = false;
    protected void setParent(Node<E> node, Node<E> parent, boolean asLeftChild) {
        if(node != null)
            node.parent = parent;
        if(parent != null) {
            if(asLeftChild)  parent.left  = node;
            else             parent.right = node;
        }
    }
    protected void rotate(Node<E> node) {
        Node<E> x = node;
        Node<E> y = x.parent;   //assumed to be exist
        Node<E> z = y.parent;   //may be null

        //TODO: implement this method
        // - make z the parent of x or make x the root if z is null
        // - make y the parent of x.left or x.right
        // - make x the parent of y
        if(z != null) {
            setParent(x, z, y == z.left/*asLeftChild*/);
        }
        else {
            root = x;
            x.parent = null;
        }

        if(x == y.left) {
            setParent(x.right, y, AS_LEFT_CHILD);
            setParent(y,       x, AS_RIGHT_CHILD);
        }
        else {
            setParent(x.left, y, AS_RIGHT_CHILD);
            setParent(y,      x, AS_LEFT_CHILD);
        }
    }

    protected Node<E> restructure(Node<E> node) {
        Node<E> x = node;
        Node<E> y = x.parent;
        Node<E> z = y.parent;

        //TODO: implement this method
        // - case 1: single rotation on y (x, y, z are on the same side)
        // - case 2: double rotation on x (x, y, z are on different sides)
        // - return the new middle node
        if( (x == y.left  && y == z.left) ||
                (x == y.right && y == z.right) ) {
            rotate(y);  //case 1: single rotation
            return y;   //return the middle node
        }
        else {
            rotate(x);  //case 2: double rotation
            rotate(x);
            return x;   //return the middle node
        }
    }

    public void add(E e) {
        if(root != null)
            add(e, root);
        else
            root = new Node<E>(e, null, null, null);
    }
    private void add(E e, Node<E> node) {
        if(e.compareTo(node.e) < 0) {
            if(node.left != null)
                add(e, node.left);
            else
                node.left = new Node<E>(e, node, null, null);
        }
        else {
            if(node.right != null)
                add(e, node.right);
            else
                node.right = new Node<E>(e, node, null, null);
        }
    }
    public void preorder() {
        preorder(root);
    }
    private void preorder(Node<E> node) {
        if(node == null)
            return;
        System.out.print(node.e + ", ");
        preorder(node.left);
        preorder(node.right);
    }

    public static <E extends Comparable<E>> void test(E[] arr) {
        BSTRestructure<E> tree = new BSTRestructure<E>();
        for(E e : arr)
            tree.add(e);
        tree.preorder();
        System.out.println();

        tree.restructure(tree.root.left.left.left);
        tree.preorder();
        System.out.println();

        tree.restructure(tree.root.right.right.left);
        tree.preorder();
        System.out.println();
    }
    public static void main(String[] args) {
        Integer[] arr = {5, 3, 6, 2, 8, 1, 9, 4, 7};
        test(arr);
    }
}