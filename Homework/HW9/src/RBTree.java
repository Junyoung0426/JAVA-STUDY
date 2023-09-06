import java.util.ArrayList;

public class RBTree<E extends Comparable<E>> {
    public enum NodeColor {
        BLACK, RED
    }

    public static class Node<E extends Comparable<E>> {
        protected E e;
        protected Node<E> parent, left, right;
        protected NodeColor color;

        public Node() {
            this(null, NodeColor.BLACK, null, null, null);
        }

        public Node(E e, NodeColor color, Node<E> p, Node<E> l, Node<E> r) {
            this.e = e;
            this.color = color;
            parent = p;
            left = l;
            right = r;
        }

        E getElement() {
            return e;
        }

        Node<E> getParent() {
            return parent;
        }

        Node<E> getLeft() {
            return left;
        }

        Node<E> getRight() {
            return right;
        }

        NodeColor getColor() {
            return color;
        }

        void setElement(E e) {
            this.e = e;
        }

        void setParent(Node<E> node) {
            this.parent = node;
        }

        void setLeft(Node<E> node) {
            this.left = node;
        }

        void setRight(Node<E> node) {
            this.right = node;
        }

        void setColor(NodeColor color) {
            this.color = color;
        }
    }

    protected Node<E> root;
    protected int size;

    public RBTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Node<E> root() {
        return root;
    }

    public E min() {
        return minNode(root).getElement();
    }

    public E max() {
        return maxNode(root).getElement();
    }

    public E insert(E e) {
        if (isEmpty()) {
            root = new Node<E>(e, NodeColor.BLACK, null, null, null);
            size++;
            return e;
        }

        Node<E> node = findNodeOrFutureParent(root, e);
        int cmp = e.compareTo(node.getElement());
        if (cmp == 0) {      //key exists; replace
            E ret = node.getElement();
            node.setElement(e);
            return ret;
        } else {              //not found; add e
            Node<E> fresh = new Node<E>(e, NodeColor.BLACK, null, null, null);
            if (cmp < 0)
                setParentAsLeftChild(fresh, node);
            else
                setParentAsRightChild(fresh, node);
            size++;
            rebalanceInsert(fresh);
            return e;
        }
    }

    public E delete(E e) {
        Node<E> node = findNodeOrFutureParent(root, e);
        if (e.compareTo(node.getElement()) != 0)    //not found
            return null;

        //return value in node.e
        E ret = node.getElement();

        //if node has two children, swap it with its predecessor
        if (node.getLeft() != null && node.getRight() != null) {
            Node<E> predecessor = maxNode(node.getLeft());
            node.setElement(predecessor.getElement());
            node = predecessor;
        }

        //now, node has at most 1 child
        if (node.getLeft() != null || node.getRight() != null) {
            //promoted child
            Node<E> promote = node.getLeft() != null ? node.getLeft() : node.getRight();
            deleteNode(node);
            if (isBlack(node))
                rebalanceDelete(promote);
        } else {
            if (isBlack(node))
                rebalanceDelete(node);
            deleteNode(node);
        }

        return ret;
    }

    protected boolean isInternal(Node<E> node) {
        return node.getLeft() != null || node.getRight() != null;
    }

    protected boolean isRed(Node<E> node) {
        return node != null && node.getColor() == NodeColor.RED;
    }

    protected boolean isBlack(Node<E> node) {
        return !isRed(node);
    }

    protected Node<E> minNode(Node<E> root) {
        if (root.getLeft() == null)
            return root;
        return minNode(root.getLeft());
    }

    protected Node<E> maxNode(Node<E> root) {
        if (root.getRight() == null)
            return root;
        return maxNode(root.getRight());
    }

    protected Node<E> sibling(Node<E> node) {
        Node<E> parent = node.getParent();
        if (parent == null)
            return null;
        else if (parent.getLeft() == node)
            return parent.getRight();
        else
            return parent.getLeft();
    }

    protected Node<E> findNodeOrFutureParent(Node<E> root, E e) {
        int cmp = e.compareTo(root.getElement());
        if (cmp == 0)
            return root;
        else if (cmp < 0) {
            if (root.getLeft() != null)
                return findNodeOrFutureParent(root.getLeft(), e);
            else
                return root;
        } else {
            if (root.getRight() != null)
                return findNodeOrFutureParent(root.getRight(), e);
            else
                return root;
        }
    }

    protected void setParentAsLeftChild(Node<E> node, Node<E> parent) {
        if (node != null)
            node.setParent(parent);
        if (parent != null) {
            parent.setLeft(node);
        }
    }

    protected void setParentAsRightChild(Node<E> node, Node<E> parent) {
        if (node != null)
            node.setParent(parent);
        if (parent != null) {
            parent.setRight(node);
        }
    }

    protected void rotate(Node<E> node) {
        Node<E> x = node;
        Node<E> y = x.getParent();   //assumed to be exist
        Node<E> z = y.getParent();   //may be null

        if (z == null) {
            root = x;
            x.setParent(null);
        } else if (y == z.getLeft()) {
            setParentAsLeftChild(x, z);
        } else
            setParentAsRightChild(x, z);
        x.parent = z;
        if (x == y.getLeft()) {
            setParentAsLeftChild(x.getRight(), y);
            setParentAsRightChild(y, x);
        } else {
            setParentAsRightChild(x.getLeft(), y);
            setParentAsLeftChild(y, x);
        }


        //TODO: implement this method
        // - make z the parent of x or make x the root
        // - make y the parent of x.left or x.right
        // - make x the parent of y
    }

    protected Node<E> restructure(Node<E> node) {
        Node<E> x = node;
        Node<E> y = x.getParent();
        Node<E> z = y.getParent();

        //TODO: implement this method
        // - case 1: single rotation
        // - case 2: double rotation
        // - return the new middle node
        if ((x == y.getLeft() && y == z.getLeft()) ||
                (x == y.getRight() && y == z.getRight())) {
            rotate(y);
            return y;
            //case 1: single rotation
        } else {
            rotate(x);
            rotate(x);
            return x;
            //case 2: double rotation
        }
    }

    protected void fixDoubleRed(Node<E> node) {
        if (!(isRed(node) && isRed(node.getParent())))
            return;
        Node<E> parent = node.getParent(); //parent is red
        Node<E> uncle = sibling(parent);

        //TODO: implement this method
        //  case 1: malformed 4 node (uncle is black)
        //  - after restructure,
        //  - make middle black and its two children red
        //  case 2: overflow (uncle is red)
        //  - make parent and uncle black
        //  - make grandparent red if it exists
        //    and fix double-red again

        if (isBlack(uncle)) {
            Node<E> mal = restructure(node);
            mal.setColor(NodeColor.BLACK);
            mal.left.setColor(NodeColor.RED);
            mal.right.setColor(NodeColor.RED);

            //case 1: malformed 4 node
        } else {
            //case 2: overflow
            parent.setColor(NodeColor.BLACK);
            uncle.setColor(NodeColor.BLACK);
            Node<E> grand = parent.parent;
            if (grand != null) {
                if (grand == root)
                    return;
                else {
                    grand.setColor(NodeColor.RED);
                    fixDoubleRed(grand);
                }
            }
        }
    }

    protected void rebalanceInsert(Node<E> node) {
        if (node != root) {
            node.setColor(NodeColor.RED);
            fixDoubleRed(node);
        }
    }

    protected void fixDoubleBlack(Node<E> node) {
        Node<E> z = node.getParent();
        Node<E> y = sibling(node);

        //TODO: implement this method
        // case 1: transfer (y is black and y.left or y.right is red)
        // - after restructure color middle with z's color
        // - make middle's left and right black
        // case 2: fusion (y is black and y.left and y.rigth is black)
        // - make y red
        // - if z is red, make it black; otherwise, fix double-black again
        // case 3: re-orientation
        // - rotate y, make y black and z red
        // - fix double black again
        if (isBlack(y)) {
            if (isRed(y.getLeft()) || isRed(y.getRight())) {
                //case 1: transfer
                Node<E> x = (isRed(y.right)) ? y.right : y.left;
                Node<E> mal = restructure(x);
                mal.setColor(z.color);
                mal.left.setColor(NodeColor.BLACK);
                mal.right.setColor(NodeColor.BLACK);


            } else {
                //case 2: fusion
                y.setColor(NodeColor.RED);
                if (isRed(z)) {
                    z.setColor(NodeColor.BLACK);
                } else if (z != root) {
                    fixDoubleBlack(z);
                }
            }
        } else {
            //case 3: re-orientation
            rotate(y);
            y.setColor(NodeColor.BLACK);
            z.setColor(NodeColor.RED);
            fixDoubleBlack(node);
        }
    }

    // node is a promoted child of a deleted node or
    // a to be deleted node if it is external
    protected void rebalanceDelete(Node<E> node) {
        if (isRed(node))                     //deleted node was black
            node.setColor(NodeColor.BLACK); //regain the black depth
        else if (node != root)
            fixDoubleBlack(node);           //regain the black depth
    }

    protected E deleteNode(Node<E> node) {
        if (node.getLeft() != null && node.getRight() != null)
            throw new IllegalArgumentException("has 2 children");

        Node<E> child = node.getLeft() != null ? node.getLeft() : node.getRight();
        if (child != null)
            child.setParent(node.getParent());
        if (node == root)
            root = child;
        else if (node == node.getParent().getLeft())
            setParentAsLeftChild(child, node.getParent());
        else
            setParentAsRightChild(child, node.getParent());
        size--;
        return node.getElement();
    }

    protected void preorderRecur(Node<E> node, ArrayList<Node<E>> snapshot) {
        snapshot.add(node);
        if (node.getLeft() != null)
            preorderRecur(node.getLeft(), snapshot);
        if (node.getRight() != null)
            preorderRecur(node.getRight(), snapshot);
    }

    public Iterable<Node<E>> preorder() {
        ArrayList<Node<E>> snapshot = new ArrayList<>();
        preorderRecur(root, snapshot);
        return snapshot;
    }

    protected void print() {
        for (Node<E> n : preorder())
            System.out.print(n.getElement() + (isRed(n) ? "*, " : " , "));
        System.out.println();
    }

    protected static void onFalseThrow(boolean b) {
        if (!b)
            throw new RuntimeException("Error: unexpected");
    }

    public static void main(String[] args) {
        RBTree<Integer> rbtree = new RBTree<>();

        //increasing order
        for (int i = 0; i < 10; i++) {
            rbtree.insert(i);
            rbtree.print();
        }
        /*test*/
        {
            int[] num = new int[]{3, 1, 0, 2, 5, 4, 7, 6, 8, 9};

            NodeColor[] red = new NodeColor[10];
            for (int i = 0; i < red.length; i++)
                red[i] = NodeColor.BLACK;
            red[6] = red[9] = NodeColor.RED;

            int k = 0;
            for (Node<Integer> n : rbtree.preorder()) {
                onFalseThrow(n.getElement() == num[k]);
                onFalseThrow(n.getColor() == red[k++]);
            }
        }

        for (int i = 0; i < 5; i++)
            rbtree.delete(i);
        /*test*/
        {
            int[] num = new int[]{7, 5, 6, 8, 9};
            NodeColor[] red = new NodeColor[10];
            for (int i = 0; i < red.length; i++)
                red[i] = NodeColor.BLACK;
            red[2] = red[4] = NodeColor.RED;

            int k = 0;
            for (Node<Integer> n : rbtree.preorder()) {
                onFalseThrow(n.getElement() == num[k]);
                onFalseThrow(n.getColor() == red[k++]);
            }
        }
        for (int i = 5; i < 10; i++)
            rbtree.delete(i);
        System.out.println("Success: increasing order");


        //decreasing order
        for (int i = 9; i >= 0; i--)
            rbtree.insert(i);
        /*test*/
        {
            int[] num = new int[]{6, 4, 2, 1, 0, 3, 5, 8, 7, 9};
            NodeColor[] red = new NodeColor[10];
            for (int i = 0; i < red.length; i++)
                red[i] = NodeColor.BLACK;
            red[2] = red[4] = NodeColor.RED;
            int k = 0;
            for (Node<Integer> n : rbtree.preorder()) {
                onFalseThrow(n.getElement() == num[k]);
                onFalseThrow(n.getColor() == red[k++]);
            }
        }

        for (int i = 9; i >= 5; i--)
            rbtree.delete(i);
        /*test*/
        {
            int[] num = new int[]{2, 1, 0, 4, 3};
            NodeColor[] red = new NodeColor[10];
            for (int i = 0; i < red.length; i++)
                red[i] = NodeColor.BLACK;
            red[2] = red[4] = NodeColor.RED;
            int k = 0;
            for (Node<Integer> n : rbtree.preorder()) {
                onFalseThrow(n.getElement() == num[k]);
                onFalseThrow(n.getColor() == red[k++]);
            }
        }
        for (int i = 4; i >= 0; i--)
            rbtree.delete(i);
        System.out.println("Success: decreasing order");


        //random order
        int[] arr = new int[]{3, 5, 2, 4, 1, 8, 7, 6, 0, 9};
        for (int i = 0; i < 10; i++)
            rbtree.insert(arr[i]);
        /*test*/
        {
            int[] num = new int[]{5, 3, 1, 0, 2, 4, 7, 6, 8, 9};
            NodeColor[] red = new NodeColor[10];
            for (int i = 0; i < red.length; i++)
                red[i] = NodeColor.BLACK;
            red[1] = red[3] = red[4] = red[6] = red[9] = NodeColor.RED;
            int k = 0;
            for (Node<Integer> n : rbtree.preorder()) {
                onFalseThrow(n.getElement() == num[k]);
                onFalseThrow(n.getColor() == red[k++]);
            }
        }

        arr = new int[]{1, 4, 2, 3, 9, 6, 7, 5, 0, 8};
        for (int i = 0; i < 5; i++)
            rbtree.delete(arr[i]);
        /*test*/
        {
            int[] num = new int[]{5, 0, 7, 6, 8};
            NodeColor[] red = new NodeColor[10];
            for (int i = 0; i < red.length; i++)
                red[i] = NodeColor.BLACK;
            red[2] = NodeColor.RED;
            int k = 0;
            for (Node<Integer> n : rbtree.preorder()) {
                onFalseThrow(n.getElement() == num[k]);
                onFalseThrow(n.getColor() == red[k++]);
            }
        }
        for (int i = 5; i < 10; i++)
            rbtree.delete(arr[i]);
        System.out.println("Success: random order");
    }
}
