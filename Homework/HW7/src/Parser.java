public class Parser {
    private static enum Action {Push, Pop}

    private static final Action[][] ACT;

    static {
        // ACT: an action table. based on the input token and the top symbol
        //      of the stack, what action needs to be taken.
        // Push: push the current input token to the stack
        // Pop: pop the operator from the stack and perform the operation
        // Undefined elements (null) mean an error

        ACT = new Action[128][128]; //[stack top][input token]

        //TODO: add table entries for unary minus '~' operator

        //stack top is '#'
        for (char c : "(+-*/~".toCharArray()) ACT['#'][c] = Action.Push;
        for (char c : "$".toCharArray()) ACT['#'][c] = Action.Pop;

        //stack top is '('
        for (char c : "(+-*/~".toCharArray()) ACT['('][c] = Action.Push;
        for (char c : ")".toCharArray()) ACT['('][c] = Action.Pop;

        //stack top is '+'
        for (char c : "(*/~".toCharArray()) ACT['+'][c] = Action.Push;
        for (char c : "+-)$".toCharArray()) ACT['+'][c] = Action.Pop;

        //stack top is '-'
        for (char c : "(*/~".toCharArray()) ACT['-'][c] = Action.Push;
        for (char c : "+-)$".toCharArray()) ACT['-'][c] = Action.Pop;

        //stack top is '*'
        for (char c : "(~".toCharArray()) ACT['*'][c] = Action.Push;
        for (char c : "+-*/)$".toCharArray()) ACT['*'][c] = Action.Pop;

        //stack top is '/'
        for (char c : "(~".toCharArray()) ACT['/'][c] = Action.Push;
        for (char c : "+-*/)$".toCharArray()) ACT['/'][c] = Action.Pop;

        //stack top is '~'
        for (char c : "(~".toCharArray()) ACT['~'][c] = Action.Push;
        for (char c : ")+-*/$".toCharArray()) ACT['~'][c] = Action.Pop;
    }

    private static class Node {
        protected Integer num;
        protected Character opr;

        public Node(Integer num) {
            this.num = num;
        }

        public Node(Character opr) {
            this.opr = opr;
        }

        public String toString() {
            return num != null ? num.toString()
                    : opr != null ? opr.toString()
                    : ""
                    ;
        }
    }
    //TODO: - parseExpr will be similar to evalExpr function that evaluates
    //        infix expressions.
    //      - Here, instead of using the operand stack, we push/pop subtrees of
    //        the parse tree to/from the tree stack.
    //      - When popping an operator, pop one or two parse-trees from the tree stack;
    //        build a parse-tree rooted at the operator; and push the resulting tree
    //        onto the tree stack

    public static BinaryTree<Node> parseExpr(String expr) {
        Scanner scan = new Scanner(expr + "$");
        Stack<LinkedBinaryTree<Node>> stack_tree = new StackByArray<>();
        Stack<Character> stack_oper = new StackByArray<>();

        stack_oper.push('#');
        for (String tok : scan) {
            char cur = tok.charAt(0);
            if (Scanner.isAlpha(cur))
                throw new UnsupportedOperationException("There is Alphabet Error:" + cur);
            else if (Scanner.isWhiteSpace(cur))
                continue;
            else if (Scanner.isDigit(cur)) {
                LinkedBinaryTree<Node> newTree = new LinkedBinaryTree<Node>();
                newTree.addRoot(new Node(cur));
                stack_tree.push(newTree);
            } else {
                Action act;
                while ((act = ACT[stack_oper.top()][cur]) == Action.Pop) {
                    LinkedBinaryTree<Node> newTree = new LinkedBinaryTree<>();
                    char top = stack_oper.pop();
                    if (top == '~') {
                        newTree.addRoot(new Node(top));
                        LinkedBinaryTree<Node> right = stack_tree.pop();
                        newTree.attach(newTree.root(), null, right);
                        stack_tree.push(newTree);
                    } else if (top == '+' || top == '-' || top == '*' || top == '/') {
                        newTree.addRoot(new Node(top));
                        LinkedBinaryTree<Node> right = stack_tree.pop();
                        newTree.attach(newTree.root(), stack_tree.pop(), right);
                        stack_tree.push(newTree);

                    } else if (top == '(' || top == '#') break;
                    else throw new UnsupportedOperationException("Error: " + tok);
                }
                if (act == Action.Push)
                    stack_oper.push(cur);
                else if (cur == '$')
                    break;
            }
        }

        return stack_tree.pop();
    }


    public static double evalExpr(BinaryTree<Node> tree) {
        Stack<Double> num = new StackByArray<Double>();

        //TODO: - evalExpr will be similar to evalPostfixExpr function that evaluates
        //        postfix expressions.
        //      - While traversing the nodes of the parseTree in the post-order,
        //        evaluate the expression by pushing/popping operands to/from the stack num 
        LinkedBinaryTree<Node> parseTree = (LinkedBinaryTree<Node>) tree;
        for (Position<Node> n : parseTree.postorder()) {
            char a = n.getElement().toString().charAt(0);
            if (Scanner.isDigit(a)) {
                num.push((double) (a - '0'));
            } else if (Scanner.isAlpha(a))
                throw new UnsupportedOperationException();

            else if (Scanner.isWhiteSpace(a))
                continue;

            else if (a == '~') {
                double n1 = num.pop();
                num.push(-n1);
            } else {
                if (a == '+' || a == '-' || a == '*' || a == '/') {
                    double n2 = num.pop();
                    double n1 = num.pop();

                    if (a == '+')
                        num.push(n1 + n2);
                    else if (a == '-')
                        num.push(n1 - n2);
                    else if (a == '*')
                        num.push(n1 * n2);
                    else if (a == '/')
                        num.push(n1 / n2);
                } else
                    throw new UnsupportedOperationException("Error at tok~1+~~2*~3en " + a);
            }
        }
        return num.pop();
    }


    public static String infixToPrefix(String expr) {
        String strExp = "";
        BinaryTree<Node> parseTree = parseExpr(expr);
        for (Position<Node> p : parseTree.preorder())
            strExp += p.getElement() + " ";
        return strExp;
    }

    public static String infixToPostfix(String expr) {
        String strExp = "";
        BinaryTree<Node> parseTree = parseExpr(expr);
        for (Position<Node> p : parseTree.postorder())
            strExp += p.getElement() + " ";
        return strExp;
    }

    public static String infixToInfix(String expr) {
        String strExp = "";
        BinaryTree<Node> parseTree = parseExpr(expr);
        for (Position<Node> p : parseTree.inorder())
            strExp += p.getElement() + " ";
        return strExp;
    }
}
