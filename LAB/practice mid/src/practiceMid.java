/* CSE214 Data Structures Midterm Exam 2
   Date: 5/13/2021, 2:00pm ~ 3:20pm
   Name: 
   Student Id:

   - Total 100 pt

   - In the programming problems, you can ignore the user errors like
     removing from an empty collection or adding to a full collection.
*/

import javax.swing.text.Position;
import java.util.*;

@SuppressWarnings("unchecked")
public class practiceMid {
/********************************************************************
 P1. [14 x 2 pt] What are the terms the explanations below are about?
 Choose your answer from the choices below:

 ancestor, , descendant, , deque, dequeue, dynamic array,
 edge, enqueue, , fractal,, , linked list,
 , , ,    recursion,
 subtree, , stack frame,

 1. A sequence of nodes of a tree such that any two consecutive nodes form an edge: path

 2. THe maxium depth of all positions of a tree:  height

 3. A collection of data that removes the minimum data first: priority queue

 4. Remove operation from a stack: pop

 5. A place that holds the parameters, local variables, return address, etc for a function invocation: heap or priority que

 6. Add operation to a stack: push,

 7. A collection of data that maintains the First-In, First-Out principle: queue,

 8. A collection of data that is a complete binary tree and each position has a smaller key than its children's key:
 heap

 9. What is the relation if it satisfies reflexivity, antisymmetry, and transitivity?:  partial order

 10. A collection of data that maintains the Last-In, First-Out principle: stack

 11. Two nodes of a tree that are children of the same parent: sibling

 12. What is the relation if it satisfies reflexivity, symmetry, and transitivity?:  equivalence

 13. The number of ancestors of a position (excluding itself) of a tree: depth

 14. A systematic way of visiting all the possible positions of a tree: traversal,

 */

/******************************************************************** 
 P2. [14 x 2 pt] Write the answers for the following questions.

 1. If 2, 3, 1, 4 were added to a stack in this order, what is the sequence of their removals?:
 4.1,3,2

 2. If 2, 3, 1, 4 were added to a queue in this order, what is the sequence of their removals?:
 2,3,1,4
 3. If 2, 3, 1, 4 were added to a priority queue in this order, what is the sequence of their removals?:
 1,2,3,4
 ** In problem 4 to 7, suppose that 4, 1, 3, 0, 2, 6, 5, 7 were added to a Binary Search Tree in this order.
 4. If the tree is traversed in preorder,  what is the sequence of the visit?:
 4->1->0->3->->2->6->5->7
 5. If the tree is traversed in postorder, what is the sequence of the visit?:
 0->2->3->1->5->7->->6->4
 6. If the tree is traversed in inorder,   what is the sequence of the visit?:
 0->1->->2->3->4->5->6->7->
 7. If the tree is traversed in breadth first order, what is the sequence of the visit?:
 4->1->6->0->3->5->7->2
 8. Suppose that 4, 1, 3, 0, 2, 6, 5, 7 were added to a heap in this order.
 If all the elements of the heap are removed, what is the sequence of the remvals?:
 0-> 1->2->3->4->5->6->7

 9. What is the time complexity (Big-Oh) for adding an element to a heap with n elements?:
 O(log(n))


 10. What is the time complexity (Big-Oh) for removing an element from a heap with n elements?:
 O(log(n))
 11. What is the time complexity (Big-Oh) for sorting an array with n elements using a heap?:
 O(nlog(n))
 12. What is the worst case time complexity (Big-Oh) for finding an element from a binary search tree with n elements?:
 o(n)
 13. Write the postfix expression for the infix expression (1 + 2) * 3 - 4:
 1 2 + 3 * 4-
 14. Write the infix expression for the postfix expression 1 2 3 4 - * +:
 1 + 2 * (3 - 4)
 */

    /********************************************************************
     P3. [5 x 2 pt] Implement an array based stack
     */
    public static class Stack<E> {
        protected E[] arr;
        protected int sp;

        //Constructors
        public Stack() {
            this(256);
        }

        public Stack(int cap) {
            arr = (E[]) new Object[cap];
            sp = 0;
        }

        //1. TODO: implement isEmpty
        public boolean isEmpty() {
            return sp == 0;
        }

        //2. TODO: implement size
        public int size() {
            return sp;
        }

        //3. TODO: implement top
        public E top() {
            if (isEmpty())
                throw new IndexOutOfBoundsException("empty");
            return arr[sp - 1];
        }

        //4. TODO: implement push
        public void push(E e) {
            if (isEmpty())
                throw new IndexOutOfBoundsException("empty");

            arr[sp++] = e;

        }

        //5. TODO: implement pop
        public E pop() {
            if (isEmpty())
                throw new IndexOutOfBoundsException("empty");
            E x = arr[--sp];
            arr[sp] = null;
            return x;
        }
    }

    /********************************************************************
     P4. [5 x 2 pt] Implement an array based queue
     */
    public static class Queue<E> {
        protected E[] arr;
        protected int f, size;

        public Queue() {
            this(256);
        }

        public Queue(int cap) {
            arr = (E[]) new Object[cap];
            f = 0;
            size = 0;
        }

        //1. TODO: implement isEmpty
        public boolean isEmpty() {
            return size == 0;
        }

        //2. TODO: implement size
        public int size() {
            return size;
        }

        //3. TODO: implement first
        public E first() {
            if (isEmpty())
                return null;
            return arr[f];
        }

        //4. TODO: implement enqueue
        public void enqueue(E e) {
            if (size == arr.length)
                throw new IndexOutOfBoundsException("empty");
            int a = (f + size) % arr.length;
            arr[a] = e;
            size++;
        }

        //5. TODO: implement dequeue
        public E dequeue() {
            if (isEmpty())
                throw new IndexOutOfBoundsException("empty");
            E x = arr[f];
            arr[f] = null;
            f = (f + 1) % arr.length;
            size--;
            return x;
        }
    }

    /********************************************************************
     P5. [6 x 3 pt] Implement a binary search tree
     */
    public static class BinarySearchTree<E extends Comparable<E>> {
        public static class Node<E extends Comparable<E>> {
            public E e;
            public Node<E> left;
            public Node<E> right;

            public Node(E e) {
                this.e = e;
            }
        }

        protected Node<E> root;
        protected Comparator<E> comp;

        //constructor
        public BinarySearchTree() {
            this((a, b) -> a.compareTo(b) /*default comparator*/);
        }

        public BinarySearchTree(Comparator<E> comp) {
            this.comp = comp;
        }

        //find the node that has e in the element
        public Node<E> find(E e) {
            return find(root, e);
        }

        protected Node<E> find(Node<E> node, E e) {
            if (node == null)
                return null;
            //1. TODO: implement find method
            //      using comp, compare e and node.e
            //      if e = node.e, return n
            //      if e < node.e, find e in the left  subtree
            //      otherwise      find e in the right subtree
            int comp = e.compareTo(node.e);
            if (comp == 0)
                return node;
            if (comp < 0)
                return find(node.left, e);
            else {
                return find(node.right, e);
            }

        }

        //add e to the tree
        public void add(E e) {
            if (root == null)
                root = new Node<E>(e);
            else
                add(root, e);
        }

        protected void add(Node<E> node, E e) {
            //2. TODO: implement add method
            //      using comp, decide wiehter e <= node.e
            //      if e <= node.e, add e to the left  subtree
            //      otherwise       add e to the right subtree
            int comp = e.compareTo(node.e);
            Node<E> el = new Node(e);
            if (comp <= 0)
                if (node.left != null)
                    add(node.left, e);
                else
                    node.left = el;
            else {
                if (node.right != null)
                    add(node.right, e);
                else
                    node.right = el;
            }
        }

        //preorder traversal
        public Iterable<E> preorder() {
            List<E> snapshot = new ArrayList<E>();
            preorder(root, snapshot);
            return snapshot;
        }

        protected void preorder(Node<E> node, List<E> snapshot) {
            //3. TODO: implement preorder recursively
            snapshot.add(node.e);
            if (node.left != null)
                preorder(node.left, snapshot);

            if (node.right != null)
                preorder(node.right, snapshot);
        }

        //inorder traversal
        public Iterable<E> inorder() {
            List<E> snapshot = new ArrayList<E>();
            inorder(root, snapshot);
            return snapshot;
        }

        protected void inorder(Node<E> node, List<E> snapshot) {
            //4. TODO: implement inorder recursively
            if (node.left != null)
                inorder(node.left, snapshot);
            snapshot.add(node.e);
            if (node.right != null)
                inorder(node.right, snapshot);

        }


        public static class Pair<F, S> {
            public F first;
            public S second;

            public Pair(F f, S s) {
                first = f;
                second = s;
            }
        }

        protected static enum Action {VisitNode, ToSnapshot}

        ;

        public Iterable<E> postorder() {
            List<E> snapshot = new ArrayList<E>();
            Stack<Pair<Action, Node<E>>> stack = new Stack<Pair<Action, Node<E>>>();
            stack.push(new Pair<>(Action.VisitNode, root));
            while (!stack.isEmpty()) {
                Pair<Action, Node<E>> pair = stack.pop();
                Action act = pair.first;
                Node<E> node = pair.second;

                //if act is ToSnapshot, add node to snapshot
                //if act is VisitNode, push next actions to the stack so that
                //   the while loop will perform the postorder traversal
                if (node == null)
                    continue;
                if (act == Action.ToSnapshot) {
                    snapshot.add(node.e);
                } else if (act == Action.VisitNode) {
                    stack.push(new Pair<>(Action.ToSnapshot, node));
                    stack.push(new Pair<>(Action.VisitNode, node.right));
                    stack.push(new Pair<>(Action.VisitNode, node.left));
                }

                //5. TODO: implement postorder non-recursively using stack


                //if act is ToSnapshot, add node to snapshot
                //if act is VisitNode, push next actions to the stack so that
                //   the while loop will perform the postorder traversal

            }
            return snapshot;
        }

        //breadth first traversal
        public Iterable<E> breadthFirst() {
            List<E> snapshot = new ArrayList<E>();
            Queue<Node<E>> queue = new Queue<>();
            queue.enqueue(root);
            while (!queue.isEmpty()) {
                Node<E> x = queue.dequeue();
                snapshot.add(x.e);
                if (x.left != null)
                    queue.enqueue(x.left);
                if (x.right != null)
                    queue.enqueue(x.right);
            }

            //6. TODO: implement breadthFirst using queue
            return snapshot;
        }
    }


    /********************************************************************
     P6. [2 x 3 pt] Implement a heap
     */
    public static class Heap<E extends Comparable<E>> {
        protected E[] arr;
        protected int size;
        protected Comparator<E> comp;

        //constructors
        public Heap() {
            this(256, (a, b) -> a.compareTo(b) /*default comparator*/);
        }

        public Heap(int cap, Comparator<E> comp) {
            this.comp = comp;
            arr = (E[]) new Comparable[cap];
            size = 0;
        }

        public void add(E e) {
            arr[size++] = e;
            upheap(size - 1);
        }

        public E remove() {
            if (isEmpty())
                return null;
            E ret = arr[0];
            size--;
            arr[0] = arr[size];
            downheap(0);
            return ret;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        //helper methods
        protected int parent(int i) {
            return (i - 1) / 2;
        }

        protected int left(int i) {
            return i * 2 + 1;
        }

        protected int right(int i) {
            return i * 2 + 2;
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

        protected void upheap(int i) {
            //1. TODO: implement upheap non-recursively:
            while (true) {
                if(i==0)
                    return;
                int p = parent(i);
                if (arr[i].compareTo(arr[p]) >= 1)
                    return;
                swap(i, p);
                i = p;
                //hint: instead of the recursive call, update i and loop again
            }
        }

        protected void downheap(int i) {
            //2. TODO: implement downheap non-recursively
            while (true) {
                if(!hasRight(i))
                    return;
                int l = left(i);
                int c = l;
                if (hasRight(i)) {
                    int r = right(i);
                    if (arr[r].compareTo(arr[l]) <= 0)
                        c = r;
                }
                if (arr[i].compareTo(arr[c]) < 0)
                    return;
                swap(i, c);
                i = c;
                //hint: instead of the recursive call, update i and loop again
            }
        }
    }


    protected static void testBst() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        int[] data = new int[]{4, 1, 3, 0, 2, 6, 5, 7};
        for (int i : data)
            bst.add(i);

        System.out.println("preorder");
        for (int i : bst.preorder())
            System.out.print(i + ", ");
        System.out.println();


        System.out.println("inorder");
        for (int i : bst.inorder())
            System.out.print(i + ", ");
        System.out.println();

        System.out.println("postorder");
        for (int i : bst.postorder())
            System.out.print(i + ", ");
        System.out.println();

        System.out.println("breadth first");
        for (int i : bst.breadthFirst())
            System.out.print(i + ", ");
        System.out.println();
    }

    protected static void testHeap() {
        Heap<Integer> heap = new Heap<>();
        int[] data = new int[]{4, 1, 3, 0, 2, 6, 5, 7};
        for (int i : data)
            heap.add(i);

        System.out.println("heap");
        while (!heap.isEmpty())
            System.out.print(heap.remove() + ", ");
        System.out.println();
    }

    public static void main(String[] args) {
        testBst();
        testHeap();
    }
}

