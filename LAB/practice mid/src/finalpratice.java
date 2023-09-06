/* CSE214 Data Structures Final Exam
   Date: 6/17/2021, 12:30pm ~ 3:00pm
   

   - Total 165 pt

   - In the programming problems, you can ignore the user errors like
     removing from an empty collection or adding to a full collection.
*/

import java.util.*;

@SuppressWarnings("unchecked")
public class finalpratice {
/*  P1. [13 x 2 pt] What are the terms the explanations below are about?
    Choose your answer from the choices below:

    abstraction, composite, big-oh, big-omega, big-theta, connected graph,
    compression function, depth, dynamic array, edge, encapsulation, equivalence,
    hash code, heap, height, linked list, partial order, path, 
    queue, spanning tree, subtree, stack, traversal,

    1. Expose only the key information and hide non-essential details: abstraction

    2. In Map, it converts an integer for a key to the index of an array: compression function

    3. The maximum depth of all positions of a tree: height
    
    4. An asymptotic lower bound of a function disregarding a constant factor: big-omega

    5. A tree of a graph that has all vertices of the graph: spanning tree

    6. In Map, it converts a general key to an integer: hash code

    7. A collection of data that maintains the First-In, First-Out principle: queue
    
    8. A collection of data that maintains the Last-In, First-Out principle: stack

    9. Hiding implementation details from other components: encapsulation
    
    10. What is the relation if it satisfies reflexivity, symmetry, and transitivity?: equivalence

    11. An asymptotic upper bound of a function disregarding a constant factor: big-oh

    12. What is the relation if it satisfies reflexivity, antisymmetry, and transitivity?: partial order

    13. A graph that has paths between any two vertices: connected graph

     1. An instance of a class: object

    2. A type of an object: class

    3. An object is created in this area of a java virtual machine:  heap

    4. Parameters, local variables, and return address are stored in this area of a java virtual machine:stack

    5. Distill a complicated system down to its most fundamental parts: abstraction

    6. Hide implementation details from other components: encapsulation

    7. Ability of a reference variable to take different forms such as Liskov substitution and dynamic dispatch: polymorphism

    8. An operator that returns true if and only if the "is a" relation is true: instanceof

    9. A class that can take formal type parameters: generic class

    10. A class that has unimplemented methods: abstract class

    11. A design pattern that uses existing objects as components for different purpose: adapter

    12. A design pattern where superclass objects invoke subclass object's methods: template

    13. A design pattern where superclass objects create subclass objects: factory

    14. An analysis technique that distributes a single burst of operations over entire operations: amortization

    15. A design pattern for scanning element of a collection:iterator

    16. A data structure that doubles its array size when it is full: dynamic array

    17. Nodes without data at the front and back of linked lists for a uniform handling of the list:  sentinels

    18. An asymptotic lower bound of a function disregarding a constant factor: big-omega

    19. An asymptotic upper bound of a function disregarding a constant factor:  big-oh,

    20. Subtypes of RuntimeException that are typically caused by programming logic errors:  unchecked exception

    1.  this method adds element to a stack:  push

    2.  this method removes element from a stack: pop

    3.  a data structure that has the LIFO principle: stack

    4.  a data structure that has the FIFO principle: queue

    5.  a tree such that left subtree of a node has elements smaller than the node and the right subtree has elements larger than the node:
     binary search tree

    6.  a tree such that each node has a smaller element than both of its children:
heap

    7.  a relation that satisfies reflexivity, symmetry, and transitivity:
    equivalence

    8.  a relation that satisfies reflexivity, anti-symmetry, and transitivity:
    partial order

    9.  the number of ancestors of a node:
     depth

    10. the maximum depth of a tree:
     height

*/


/*  P2. [5 x 5 pt] This problem is about Red-Black tree. 
    - Write the pre-order tree traversal result after the operation.
    - Suffix * if the node is a red node.
    For example, the result after adding 2, 1, 3 to the empty tree is: 2 1* 3*

    1. Add 9, 8, 7, 6 to the empty Red-Black tree: 8 7 6* 9

    2. Add 5, 4, 3 to the Red-Black tree of Problem 1: 8 6* 4 3* 5* 7 9

    3. Add 2, 1, 0 to the Red-Black tree of Problem 2: 6 4 2* 1 0* 3 5 8 7 9

    4. Remove 9, 8, 7 from the Red-Black tree of Problem 3: 4 2* 1 0* 3 6 5*

    5. Remove 9, 8, 7 from the Red-Black tree of Problem 2: 4 3 6 5*
*/

    /*  P3. [12 x 2pt] This problem is about a linked list (circularly doubly linked list)
        - implement the TODOs.
    */
    public static class List<E> implements Iterable<E> {
        public static class Node<E> {
            E e;
            Node<E> prev, next;

            Node(E e, Node<E> prev, Node<E> next) {
                this.e = e;
                this.prev = prev;
                this.next = next;
            }
        }

        //sentinel head
        private Node<E> head;
        private int size;

        public List() {
            head = new Node<E>(null, null, null);
            head.next = head.prev = head;
            size = 0;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return head.next == head;
        }

        public E first() {
            return head.next.e;
        }

        public E last() {
            return head.prev.e;
        }

        public boolean has(E e) {
            return findNode(e) != null;
        }

        public Iterator<E> iterator() {
            return new ElementIterator();
        }

        public Iterable<Node<E>> nodeIterator() {
            return () -> new NodeIterator();
        }


        //TODO: 1. add e to the first position (use addBetween)
        public void addFirst(E e) {
            addBetween(e, head, head.next);
        }

        //TODO: 2. add e to the last position (use addBetween)
        public void addLast(E e) {
            addBetween(e, head.prev, head);
        }

        //TODO: 3. remove the first node and returns its element (use removeNode)
        public E removeFirst() {
            if (head.next == head) return null;
            E ret = head.next.e;
            removeNode(head.next);
            return ret;
        }

        //TODO: 4. remove the last node and returns its element (use removeNode)
        public E removeLast() {
            E ret = head.prev.e;
            removeNode(head.prev);
            return ret;
        }

        //TODO: 5. remove the node with e and returns its element (use findNode and removeNode)
        public E remove(E e) {
            Node<E> ret = findNode(e);
            removeNode(ret);
            return ret.e;
        }

        //NodeIterator iterates over the Nodes of the list
        public class NodeIterator implements Iterator<Node<E>> {
            Node<E> pos;

            public NodeIterator() {
                pos = head;
            }

            //TODO: 6. implement hasNext
            public boolean hasNext() {
                return pos != null;
            }

            //TODO: 7. implement next
            public Node<E> next() {
                if (hasNext()) {
                    Node<E> ret = pos;
                    pos = pos.next;
                    return ret;
                }
                return null;
            }
        }

        //ElementIterator iterates over the elements of the list
        //  - implement ElementIterator using NodeIterator 
        public class ElementIterator implements Iterator<E> {
            NodeIterator iter;

            public ElementIterator() {
                iter = new NodeIterator();
            }

            //TODO: 8. implement hasNext
            public boolean hasNext() {
                return iter.hasNext();
            }

            //TODO: 9. implement next
            public E next() {
                if (iter.hasNext())
                    return iter.next().e;
                return null;
            }
        }

        //TODO: 10. return the node with e; return null if such a node does not exist
        //- use e.equals to compare
        private Node<E> findNode(E e) {
            Node<E> ret = head.next;
            while (size > 0) {
                if (ret.e.equals(e)) return ret;
                else {
                    ret = ret.next;
                    size--;
                }
            }
            return null;
        }

        //TODO: 11. add a node with e in between pred and succ
        private void addBetween(E e, Node<E> pred, Node<E> succ) {
            Node<E> newNode = new Node(e, pred, succ);
            pred.next = newNode;
            succ.prev = newNode;
            size++;
        }

        //TODO: 12. remove node from the list and return node
        private Node<E> removeNode(Node<E> node) {
            Node<E> prev = node.prev;
            Node<E> succ = node.next;
            prev.next = succ;
            succ.prev = prev;
            size--;
            return node;
        }
    }

    /*  P4. [10 x 2pt] This problem is about a stack and queue
        - implement the TODOs
    */
    public static class Stack<E> {
        List<E> list;

        public Stack() {
            list = new List<E>();
        }

        //TODO: 1. implement size
        public int size() {
            return list.size;
        }

        //TODO: 2. implement isEmpty
        public boolean isEmpty() {
            return list.size == 0;
        }

        //TODO: 3. implement push (use addFirst)
        public void push(E e) {
            list.addFirst(e);
        }

        //TODO: 4. implement pop
        public E pop() {
            return list.removeFirst();
        }

        //TODO: 5. implement top
        public E top() {
            return list.first();
        }
    }

    public static class Queue<E> {
        List<E> list;

        public Queue() {
            list = new List<E>();
        }

        //TODO: 6. implement size
        public int size() {
            return list.size;
        }

        //TODO: 7. implement isEmpty
        public boolean isEmpty() {
            return list.size == 0;
        }

        //TODO: 8. implement enqueue (use addLast)
        public void enqueue(E e) {
            list.addLast(e);
        }

        //TODO: 9. implement dequeue
        public E dequeue() {
            return list.removeFirst();
        }

        //TODO: 10. implement first
        public E first() {
            return list.first();
        }
    }

    /*  P5. [45 pt] This problem is about a graph
        - assume that the elements e of all nodes are unique
        - call printNode instead of adding edges to a snapshot
    */
    public static class Graph<N, E extends Comparable<E>> {
        //Vertex of a graph
        public static class Node<N, E extends Comparable<E>> {
            public N e;
            public List<Edge<N, E>> edges;

            public Node(N e) {
                this.e = e;
                edges = new List<Edge<N, E>>();
            }

            public boolean equals(Object o) {
                Node<N, E> that = (Node<N, E>) o;
                return e.equals(that.e);
            }
        }

        //Edge of a graph
        public static class Edge<N, E extends Comparable<E>> {
            public Node<N, E> a, b;
            E e;

            public Edge(E e, Node<N, E> a, Node<N, E> b) {
                this.e = e;
                this.a = a;
                this.b = b;
            }

            public Node<N, E> opposite(Node<N, E> n) {
                return a == n ? b : a;
            }

            public boolean equals(Object o) {
                Edge<N, E> that = (Edge<N, E>) o;
                return a.equals(that.a) && b.equals(that.b) ||
                        a.equals(that.b) && b.equals(that.a);
            }
        }

        //list of nodes
        public List<Node<N, E>> nodes;

        public Graph() {
            nodes = new List<Node<N, E>>();
        }

        public void addNode(N e) {
            nodes.addLast(new Node<N, E>(e));
        }

        public void removeNode(N e) {
            Node<N, E> node = findNode(e);
            nodes.remove(node);
        }

        public void addEdge(E e, Node<N, E> a, Node<N, E> b) {
            Edge<N, E> edge = new Edge<N, E>(e, a, b);
            a.edges.addLast(edge);
            b.edges.addLast(edge);
        }

        public void removeEdge(Node<N, E> a, Node<N, E> b) {
            Edge<N, E> tmp = new Edge<N, E>(null, a, b);
            a.edges.remove(tmp);
            b.edges.remove(tmp);
        }

        public Node<N, E> findNode(N e) {
            Node<N, E> tmp = new Node<N, E>(e);
            return nodes.findNode(tmp).e;
        }

        //recursive DFS
        //
        private void printNode(Node<N, E> n) {
            System.out.print(n.e + ", ");
        }

        public void DFS(Node<N, E> n) {
            List<Node<N, E>> known = new List<>();
            DFSRecur(n, known);
            System.out.println("");
        }

        //TODO: [5 pt] implement DFSRecur
        // - use printNode instead of adding it to a snapshot
        void DFSRecur(Node<N, E> n, List<Node<N, E>> known) {
            known.addLast(n);
            for (Edge<N, E> e : n.edges) {
                Node<N, E> v = e.opposite(n);
                if (known.findNode(v) != null) {
                    printNode(v);
                    DFSRecur(v, known);
                }
            }
        }

        //[15 pt] non-recursive DFS
        //
        public static class Pair<F, S> {
            public F first;
            public S second;

            public Pair(F f, S s) {
                first = f;
                second = s;
            }
        }

        public enum Action {Print, Visit}

        ;

        public void DFSIter(Node<N, E> n) {
            List<Node<N, E>> known = new List<>();
            Stack<Pair<Action, Node<N, E>>> stack = new Stack<>();

            stack.push(new Pair<Action, Node<N, E>>(Action.Visit, n));
            while (!stack.isEmpty()) {
                Pair<Action, Node<N, E>> pair = stack.pop();
                Action action = pair.first;
                Node<N, E> node = pair.second;

                if (known.has(node))
                    continue;

                //TODO: [5 pt] handle this case
                // - use printNode instead of adding it to a snapshot
                if (action == Action.Print) {
                    printNode(known.last());
                }
                //TODO: [10 pt] handle this case
                else if (action == Action.Visit) {
                    //Using revStack, make the order of the operations correct
                    Stack<Pair<Action, Node<N, E>>> revStack = new Stack<>();
                }
            }
            System.out.println("");
        }

        //TODO: [5 pt] implement BFS 
        public void BFS(Node<N, E> n) {
            List<Node<N, E>> known = new List<>();
            Queue<Node<N, E>> queue = new Queue<>();

            known.addFirst(n);
            queue.enqueue(n);
            while (!queue.isEmpty()) {
                Node<N, E> u = queue.dequeue();
                for (Edge<N, E> e : u.edges) {
                    Node<N, E> v = e.opposite(n);
                    if (known.findNode(v) != null) {
                        known.addLast(v);
                        printNode(v);
                        queue.enqueue(v);
                    }
                }
            }
            System.out.println("");
        }

        //[10 x 2pt] Kruskal's algorithm
        //
        private Edge<N, E> removeMinEdge(List<Edge<N, E>> edges) {
            Edge<N, E> min = edges.first();
            //TODO: 1. find the edge with the minimum element
            for (Edge<N, E> e : edges) {
                if (e.e.compareTo(min.e) < 0) {
                    min = e;
                }
            }
            //TODO: 2. remove the minimum edge from edges and return it
            edges.remove(min);
            return min;
        }

        public void kruskal() {
            List<List<Node<N, E>>> clusters = new List<>();
            List<Edge<N, E>> edges = new List<>();
            List<Edge<N, E>> tree = new List<>();

            //initialize clusters
            for (Node<N, E> n : nodes) {
                List<Node<N, E>> cluster = new List<>();
                //TODO: 3. add n to cluster and add cluster to clusters
                cluster.addLast(n);
                clusters.addLast(cluster);
                //TODO: 4. for each edge e in n.edges,
                //      add e to edges if it is not already there
                for(Edge<N, E> e : n.edges){
                    if(edges.findNode(e)==null){
                        edges.addLast(e);
                    }
                }
            }

            //merge clusters
            while (clusters.size() > 1) {
                //TODO: 5. remove the minimum edge in edges and copy it to edge
                Edge<N, E> edge = removeMinEdge(edges);

                List<Node<N, E>> cluster_a = null, cluster_b = null;
                for (List<Node<N, E>> cluster : clusters) {
                    //TODO: 6. find the clusers where edge.a and edge.b belongs

                }

                //TODO: 7. continue if cluster_a and cluster_b are the same cluster
                if(cluster_a.equals(cluster_b))
                    continue;;

                //TODO: 8. add edge to tree
                tree.addLast(edge);

                //Let cluster_src be the smaller of cluster_a and cluster_b,
                //and let cluster_dst be the larger of the two
                List<Node<N, E>> cluster_src = cluster_a, cluster_dst = cluster_b;
                if (cluster_b.size() < cluster_a.size()) {
                    cluster_src = cluster_b;
                    cluster_dst = cluster_a;
                }

                //TODO: 9. move all elements from cluster_src to cluster_dst


                //TODO: 10. remove cluster_src from clusters
                clusters.remove(cluster_src);
            }

            //print out the edges in the tree
            for (Edge<N, E> edge : tree) {
                System.out.println(edge.a.e + " - " + edge.b.e + " : " + edge.e);
            }
        }
    }

    /*  P6. [5 x 5pt] This problem is about sorting (radix sort)
        - implement the TODOs
    */
    public static class Sort {
        //return the shift'th bit of a
        private static int checkBit(int a, int shift) {
            return (a & (1 << shift)) != 0 ? 1 : 0;
        }

        //radix sort from MSB (Most Significant Bit: 2^31) to LSB (Least Significant Bit: 2^0)
        public static void radixSort1(List<Integer> list) {
            radixSort1(list, 31);
        }

        private static void radixSort1(List<Integer> list, int b) {
            if (list.size() == 0 || b < 0)
                return;
            List<Integer> zeros = new List<>();
            List<Integer> ones = new List<>();

            //TODO: 1. move all elements in list to zeros or ones (use addLast)
            for (int i : list) {
                if (checkBit(i, b - 1) == 1)
                    ones.addLast(i);
                else zeros.addLast(i);
            }
            //TODO: 2. recursively sort zeros and ones
            radixSort1(list, b - 1);
            //TODO: 3. copy the elements in zeros and ones to list

        }

        //radix sort from LSB to MSB
        public static void radixSort2(List<Integer> list) {
            List<Integer> zeros = new List<>();
            List<Integer> ones = new List<>();

            for (int b = 0; b < 32; b++) {
                //TODO: 4. move all elements in list to zeros or ones (use addLast)
                for (int i : list) {
                    zeros.addFirst(i);
                }

                //TODO: 5. move the elements in zeros and ones to list
            }
        }
    }
}