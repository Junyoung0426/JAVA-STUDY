/* CSE214 Data Structures
  Final  Exam
  Date: 6/18/2020 12:30pm ~ 3:00pm
  Total: 174 pt
*/
import java.util.*;

public class Finalpractice2 {
    // PROBLEM1. [24 pt] This problem is about Stack
    //
    //
    public static class Stack<E> {
        public static class Node<E> {
            public E e; // element
            public Node<E> next; // next

            public Node(E e, Node<E> next) {
                this.e = e;
                this.next = next;
            }
        }

        // Fields
        int size;
        Node<E> head;

        // Constructor
        public Stack() {
            size = 0;
            // number of elements
            // sentinel
            head = new Node<E>(null, null);
        }

        // public methods
        public void push(E e) {
            // Q1 [4 pt] implement push
            // - make the new node close to the head
           Node<E> a=new Node<E>(e,head);
           head=a;
           size++;
        }

        public E pop() {
            // Q2 [4 pt] implement pop.
            // - skip checking the emptiness
           E a=head.e;
           head=head.next;
           size--;
           return a;
        }

        public E top() {
            // Q3 [4 pt] implement top (peek)
            return head.e;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public static void test() {
            // [6 x 2pt]
            Stack<Integer> stack = new Stack<>();
            onFalseThrow(stack.isEmpty() == true /* Q4 */);
            stack.push( 1 /* Q5 */);
            stack.push( 2 /* Q6 */);
            stack.push(3);
            onFalseThrow(stack.size() == 3 /* Q7 */);
            onFalseThrow(stack.isEmpty() == false/* Q8 */);
            onFalseThrow(stack.pop() ==3 /* Q9 */);
            onFalseThrow(stack.pop() == 2);
            onFalseThrow(stack.pop() == 1);
            onFalseThrow(stack.size() == 0);
            System.out.println("Stack test: Success");
        }
    }

    // PROBLEM2. [56 pt] This problem is about Heap
    //
    //
    public static class Heap<E extends Comparable<E>> {
        static class Node<E extends Comparable<E>> {
            public E e; // element
            public Node<E> p, l, r; // parent, left, right

            public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
                this.e = e;
                this.p = p;
                this.l = l;
                this.r = r;
            }
        }

        // number of elements
        // the root node
        // Fields
        int size;
        Node<E> root;

        // Constructor
        public Heap() {
            size = 0;
            root = null;
        }

        // public methods
        public void add(E x) {
            size++;
            int i = size - 1;
            if (i == 0) {
                // position of the node to add
                root = newNode(x, null, null, null);
            }
            else {
                // Q1 [8 pt] implement this block (use nodeAt)
                // - get the parent node
                // - create a node (use newNode) and link it to the parent
                // - do the remaining tasks
                Node<E> parent=nodeAt((i-1)/2);
                Node<E> a=new Node<E>(x,parent,null,null);
                if(i%2==1){
                   parent.l=a;
                }
                else {
                   parent.r=a;
                }
                upHeap(a);
            }
        }

        public E remove() {
            E r = root.e;
            size--;
            if (size > 0) {
                // Q2 [8 pt] implement this block (use nodeAt)
                // - let n be the last node
                // - disconnect n from its parent
                // - disconnect links from n
                // - do the remaining tasks
                Node<E> n=nodeAt(size);
                if(n==n.p.l)
                   n.p.l=null;
                else {
                   n.p.r=null;
                }
                n.p=null;
                swap(root,n);
                downHeap(root);
            }
            return r;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // internal methods
        void upHeap(Node<E> n) {
            // Q3 [8 pt] implement upHeap recursively
            // - if n is the root, return
            // - if n's parent has smaller element than n, return
            // - do the remaining tasks
            if(n==root) {
               return;
            }
            if(n.p.e.compareTo(n.e)<0) {
               return;
            }
            else {
               Node<E> curr=n;
               swap(curr,n.p);
               curr=n.p;
               upHeap(curr);
            }
        }

        void downHeap(Node<E> n) {
            // Q4 [10 pt] implement downHeap recursively
            // - if n does not have a left child, return
            // - if n has a right child, choose between l and r
            // - if n's smaller child has larger element than n, return
            // - do the remaining tasks
            if(n.l==null) {
               return;
            }
            Node<E> a = n.l;
            if(n.r!=null) {
               if(n.r.e.compareTo(n.l.e)<0) {
                  a =n.r;
               }
            }
            if(n.e.compareTo(a.e)<0)
               return;
            
            swap(n,a);
            downHeap(a);
            
        }

        void swap(Node<E> a, Node<E> b) {
            E t = a.e;
            a.e = b.e;
            b.e = t;
        }

        // node at the i-th position of array based heap
        Node<E> nodeAt(int i) {
            if (i == 0)
                return root;
            if (i % 2 == 1)
                return nodeAt((i - 1) / 2).l;
            else
                return nodeAt((i - 1) / 2).r;
        }

        // factory method
        Node<E> newNode(E e, Node<E> p, Node<E> l, Node<E> r) {
            return new Node<E>(e, p, l, r);
        }

        public static void test() {
            // [11 x 2 pt]
            Heap<Integer> heap = new Heap<>();
            heap.add(5);
            heap.add(4);
            heap.add(3);
            heap.add(2);
            heap.add(1);
            onFalseThrow(heap.nodeAt(0).e == 1/* Q5 */ );
            onFalseThrow(heap.nodeAt(1).e == 2/* Q6 */ );
            onFalseThrow(heap.nodeAt(2).e == 4/* Q7 */ );
            onFalseThrow(heap.nodeAt(3).e == 5/* Q8 */ );
            onFalseThrow(heap.nodeAt(4).e == 3/* Q9 */ );
            onFalseThrow(heap.remove() ==  1/* Q10 */ );
            onFalseThrow(heap.remove() ==  2/* Q11 */ );
            onFalseThrow(heap.nodeAt(0).e == 3/* Q12 */ );
            onFalseThrow(heap.nodeAt(1).e == 5/* Q13 */ );
            onFalseThrow(heap.nodeAt(2).e == 4/* Q14 */ );
            onFalseThrow(heap.remove() == 3);
            onFalseThrow(heap.remove() == 4);
            onFalseThrow(heap.remove() == 5);
            onFalseThrow(heap.isEmpty() == true/* Q15 */);
            System.out.println("Heap test: Success");
        }
    }

    // A heap that can adjust itself to the order change of the elements
    public static class AdjustableHeap<E extends Comparable<E>> extends Heap<E> {
        // Fields
        Map<E, Node<E>> map; // key to node map
        // Constructor

        public AdjustableHeap() {
            super();
            map = new HashMap<E, Node<E>>();
        }

        // public methods
        public E remove() {
            E r = super.remove();
            map.remove(r);
            return r;
        }

        // Adjust the position of e after changing its order (for Dijkstra's algorithm)
        public void adjust(E e) {
            Node<E> n = map.get(e);
            upHeap(n);
            downHeap(n);
        }

        // internal methods
        void swap(Node<E> a, Node<E> b) {

            super.swap(a, b);
            map.put(a.e, a);
            map.put(b.e, b);
        }

        // factory method
        Node<E> newNode(E e, Node<E> p, Node<E> l, Node<E> r) {
            Node<E> n = super.newNode(e, p, l, r);
            map.put(e, n);
            return n;
        }
    }

    //
    // Graph
    //
    public static class Graph {
        public static class Node /* Vertex */ implements Comparable<Node> {
            int id;
            int mark; // mark for DFS, distance for Dijkstra
            Map<Node, Edge> edges; // adjacency map
            // Constructors

            public Node(int id) {
                this(id, 0);
            }

            public Node(int id, int mark) {
                this.id = id;
                this.mark = mark;
                edges = new HashMap<Node, Edge>();
            }

            // Comparable
            public int compareTo(Node n) {
                return mark - n.mark;
            }

            public String toString() {
                return String.format("%d", id);
            }
        }

        public static class Edge implements Comparable<Edge> {
            int id;
            int w; // weight
            Node a, b; // vertexes
            // Constructor

            public Edge(int id, int w, Node a, Node b) {
                this.id = id;
                this.w = w;
                this.a = a;
                this.b = b;
                a.edges.put(b, this); // update adjacency map
                b.edges.put(a, this);
                if (a.id > b.id) { // sort a, b based on their ids
                    this.a = b;
                    this.b = a;
                }
            }

            // Comparable
            public int compareTo(Edge e) {
                return w - e.w;
            }

            public String toString() {
                return String.format("(%s,%s;%d)", a, b, w);
            }
        }

        // Fields
        List<Node> nodes;
        List<Edge> edges;

        // Constructor
        // nodes of the graph
        // edges of the graph
        public Graph() {
            nodes = new ArrayList<Node>();
            edges = new ArrayList<Edge>();
        }

        // PROBLEM4. [30 pt] This problem is about DFS algorithms
        //
        //
        public void dfs(Node n, List<Edge> tree) {
            // implement dfs recursively
            // n: the node to start
            // tree: the edges to move from n
            // on visiting a node, set its mark to 1

            // Q1 [2 pt] mark that the node is visited
           
           n.mark=1;
           
            for (Map.Entry<Node, Edge> ent : n.edges.entrySet()) {
                Node m = ent.getKey();
                Edge e = ent.getValue();
                // Q2 [8 pt] if m is an unvisited node,
                // add it to the tree and visit it now
                if(m.mark!=1) {
                   tree.add(e);
                   dfs(m,tree);
                }
            }
        }

        public static void testDfs() {
            // [2 x 2 pt]
            Graph g = buildTestGraph();
            List<Edge> tree = new ArrayList<>();
            g.dfs(g.nodes.get(0), tree);
            onFalseThrow(g.nodes.size() == 9);
            onFalseThrow(tree.size() ==8  /* Q3 */);
            int numMarked = 0;
            for (Node n : g.nodes)
                if (n.mark == 1)
                    numMarked++;
            onFalseThrow(numMarked ==  9/* Q4 */);
            System.out.println("DFS test: Success");
        }

        // DFS algorithm by stack
        public void dfsStack(Node n, List<Edge> tree) {
            // implement dfs iteratively using stack
            // n: the node to start
            // tree: the edges to move
            // on visiting a node, set its mark to 1
            Stack<Node> stack = new Stack<>();
            // Q5 [4 pt] initialize n and the stack
            stack.push(n);

            while (!stack.isEmpty()) {
                Node k = stack.pop();
                for (Map.Entry<Node, Edge> ent : k.edges.entrySet()) {
                    Node m = ent.getKey();
                    Edge e = ent.getValue();
                    // Q6 [8 pt] if m is an unvisited node,
                    // add it to the tree, mark it and visit it later
                    if(m.mark!=1) {
                       tree.add(e);
                       stack.push(m);
                       
                    }
                }
                k.mark=1;
            }
        }

        public static void testDfsStack() {
            //[2 x 2 pt]
            Graph g = buildTestGraph();
            List<Edge> tree = new ArrayList<>();
            g.dfsStack(g.nodes.get(0), tree);
            onFalseThrow(g.nodes.size() == 9);
            onFalseThrow(tree.size() == 14 /* Q7 */ );
            int numMarked = 0;
            for(Node n : g.nodes)
                if(n.mark == 1)
                    numMarked++;
            onFalseThrow(numMarked == 9/* Q8 */ );
            System.out.println("DFS-Stack test: Success");
        }
        
        //PROBLEM5. [34 pt] This problem is about Dijkstra's shortest path algorithm

        public void dijkstra(Node s, List<Node> tree) {
            AdjustableHeap<Node> pq = new AdjustableHeap<>();
            // initialize pq
            setMarks(999999);
            for (Node n : nodes)
                pq.add(n);
            // Q1 [4 pt] update s's distance (mark) and
            // adjust its position in pq
            s.mark=0;
            pq.adjust(s);
            while(!pq.isEmpty()){
                // Q2 [4 pt] remove a node from pq and add it to tree
               Node a=pq.remove();
               tree.add(a);
                for (Map.Entry<Node, Edge> ent : a.edges.entrySet()) {
                    Node m = ent.getKey();
                    Edge e = ent.getValue();
                    // Q3 [6 pt] if applicable, update m's distance and
                    // adjust its position in pq
                    if(a.mark+e.w<m.mark) {
                       m.mark=a.mark+e.w;
                       pq.adjust(m);
                    }
                }
            }
        }

        public static void testDijkstra() {
            Graph g = buildTestGraph();
            List<Node> path = new ArrayList<>();
            g.dijkstra(g.nodes.get(0), path);
            //[10 x 2 pt]
            onFalseThrow(path.size() == 9/*Q4*/);//노드의갯수
            onFalseThrow(path.get(0).id == 0/*Q5*/ );
            onFalseThrow(path.get(1).id == 1/*Q6*/ );
            onFalseThrow(path.get(2).id == 7/*Q7*/ );
            onFalseThrow(path.get(3).id == 6/*Q8*/ );
            onFalseThrow(path.get(4).id == 5/*Q9*/ );
            onFalseThrow(path.get(5).id == 2/*Q10*/);
            onFalseThrow(path.get(6).id == 8/*Q11*/);
            onFalseThrow(path.get(7).id == 3/*Q12*/);
            onFalseThrow(path.get(8).id == 4/*Q13*/);
            System.out.println("Dijkstra test: Success");
        }

        // P6. [30 pt]This problem is about Kruskal's MST algorithm
        public void kruskal(List<Edge> mst) {
            //clouds: node -> cloud id map
            Map<Node,Integer> clouds = new HashMap<>();
            for(Node n: nodes)
                clouds.put(n, n.id);
            Heap<Edge> pq = new Heap<>();
            //Q1 [2 pt] initialize pq
            for(Edge e:edges) {
               pq.add(e);
            }
            while(mst.size() < nodes.size()-1) {
                //Q2 [2 pt] get the next Edge e to check
               Edge e=pq.remove();
                //Q3 [8 pt] check the cloud-ids of e's nodes and
                //   update the mst if necessary
                int aId = clouds.get(e.a);
                int bId = clouds.get(e.b);

                if(aId!=bId  /*condition to to update mst*/) {
                    //update mst
                   mst.add(e);
                    //merge the clouds   
                    for(Map.Entry<Node,Integer> ent: clouds.entrySet()) {
                        //Q4 [4 pt] merge the cloud ids
                       if(ent.getValue()==bId)
                           ent.setValue(aId);
                    } 
                }
            } 
        }

        public static void testKruskal() {
            Graph g = buildTestGraph();
            List<Edge> mst = new ArrayList<>();
            g.kruskal(mst);
            //[9 x 2 pt]
            onFalseThrow(mst.size() ==8 /*Q5*/);
            onFalseThrow(mst.get(0).w ==1 /*Q6*/);
            onFalseThrow(mst.get(1).w ==2 /*Q7*/);
            onFalseThrow(mst.get(2).w ==3 /*Q8*/);
            onFalseThrow(mst.get(3).w ==4 /*Q9*/);
            onFalseThrow(mst.get(4).w ==5 /*Q10*/);
            onFalseThrow(mst.get(5).w == 7/*Q11*/);
            onFalseThrow(mst.get(6).w == 8/*Q12*/);
            onFalseThrow(mst.get(7).w == 9/*Q13*/);
            System.out.println("Kruskal test: Success");
        }

        // internal methods
        void setMarks(int m) { // m: mark for dfs, distance for dijkstra
            for (Node n : nodes)
                n.mark = m;
        }

        // build the graph for the test cases
        public static Graph buildTestGraph() {
            Graph g = new Graph();
            // add nodes
            for (int i = 0; i < 9; i++)
                g.nodes.add(new Graph.Node(i));
            // add edges
            // http://www3.cs.stonybrook.edu/~youngkwon/cse214/Exam_Final_Graph.jpg
            // tip: draw an octagon for nodes 0 ~ 7; node 8 is at the center
            g.edges.add(new Graph.Edge(0/* id */, 4/* w */, g.nodes.get(0), g.nodes.get(1)));
            g.edges.add(new Graph.Edge(1/* id */, 8/* w */, g.nodes.get(1), g.nodes.get(2)));
            g.edges.add(new Graph.Edge(2/* id */, 7/* w */, g.nodes.get(2), g.nodes.get(3)));
            g.edges.add(new Graph.Edge(3/* id */, 9/* w */, g.nodes.get(3), g.nodes.get(4)));
            g.edges.add(new Graph.Edge(4/* id */, 10/* w */, g.nodes.get(4), g.nodes.get(5)));
            g.edges.add(new Graph.Edge(5/* id */, 2/* w */, g.nodes.get(5), g.nodes.get(6)));
            g.edges.add(new Graph.Edge(6/* id */, 1/* w */, g.nodes.get(6), g.nodes.get(7)));
            g.edges.add(new Graph.Edge(7/* id */, 8/* w */, g.nodes.get(7), g.nodes.get(0)));
            // tip: draw the remaining 5 edges
            g.edges.add(new Graph.Edge(8/* id */, 11/* w */, g.nodes.get(1), g.nodes.get(7)));
            g.edges.add(new Graph.Edge(9/* id */, 5/* w */, g.nodes.get(2), g.nodes.get(5)));
            g.edges.add(new Graph.Edge(10/* id */, 14/* w */, g.nodes.get(3), g.nodes.get(5)));
            g.edges.add(new Graph.Edge(11/* id */, 3/* w */, g.nodes.get(8), g.nodes.get(2)));
            g.edges.add(new Graph.Edge(12/* id */, 6/* w */, g.nodes.get(8), g.nodes.get(6)));
            g.edges.add(new Graph.Edge(13/* id */, 7/* w */, g.nodes.get(8), g.nodes.get(7)));
            return g;
        }
    }

    public static void onFalseThrow(boolean b) {
        if (!b)
            throw new IllegalStateException();
    }

    public static void main(String[] args) {
        Stack.test();
        Heap.test();
        Graph.testDfs();
        Graph.testDfsStack();
        Graph.testDijkstra();
        Graph.testKruskal();
    }
}