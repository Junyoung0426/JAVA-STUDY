public class Expression {
    private enum Action {Push, Pop}

    private static final Action[][] ACT;

    static {
        // ACT: an action table. based on the input token and the top symbol
        //      of the stack, what action needs to be taken.
        // Push: push the current input token to the stack
        // Pop: pop the operator from the stack and perform the operation
        // Undefined elements (null) mean an error
        //TODO: add table entries for unary minus '~' operator

        ACT = new Action[128][128]; //[stack top][input token]
        ACT['#']['+'] = ACT['(']['+'] = Action.Push;
        ACT['+']['+'] = ACT['-']['+'] = ACT['*']['+'] = ACT['/']['+'] = ACT['~']['+'] = Action.Pop;

        ACT['#']['-'] = ACT['(']['-'] = Action.Push;
        ACT['+']['-'] = ACT['-']['-'] = ACT['*']['-'] = ACT['/']['-'] = ACT['~']['-'] = Action.Pop;

        ACT['#']['*'] = ACT['(']['*'] = ACT['+']['*'] = ACT['-']['*'] = Action.Push;
        ACT['*']['*'] = ACT['/']['*'] = ACT['~']['*'] = Action.Pop;

        ACT['#']['/'] = ACT['(']['/'] = ACT['+']['/'] = ACT['-']['/'] = Action.Push;
        ACT['*']['/'] = ACT['/']['/'] = ACT['~']['/'] = Action.Pop;

        ACT['#']['('] = ACT['(']['('] = ACT['+']['('] = ACT['-']['('] = ACT['*']['('] = ACT['/']['('] = ACT['~']['('] = Action.Push;

        ACT['('][')'] = ACT['+'][')'] = ACT['-'][')'] = ACT['*'][')'] = ACT['/'][')'] = ACT['~'][')'] = Action.Pop;

        ACT['~']['~'] = ACT['#']['~'] = ACT['(']['~'] = ACT['+']['~'] = ACT['-']['~'] = ACT['*']['~'] = ACT['/']['~'] = Action.Push;

        ACT['#']['$'] = ACT['(']['$'] = ACT['+']['$'] = ACT['-']['$'] = ACT['*']['$'] = ACT['/']['$'] = ACT['~']['$'] = Action.Pop;


//stack top is '#'
        for (char c : "(+-*/".toCharArray()) ACT['#'][c] = Action.Push;
        for (char c : "$".toCharArray()) ACT['#'][c] = Action.Pop;

        //stack top is '('
        for (char c : "(+-*/".toCharArray()) ACT['('][c] = Action.Push;
        for (char c : ")".toCharArray()) ACT['('][c] = Action.Pop;

        //stack top is '+'
        for (char c : "(*/".toCharArray()) ACT['+'][c] = Action.Push;
        for (char c : "+-)$".toCharArray()) ACT['+'][c] = Action.Pop;

        //stack top is '-'
        for (char c : "(*/".toCharArray()) ACT['-'][c] = Action.Push;
        for (char c : "+-)$".toCharArray()) ACT['-'][c] = Action.Pop;

        //stack top is '*'
        for (char c : "(".toCharArray()) ACT['*'][c] = Action.Push;
        for (char c : "+-*/)$".toCharArray()) ACT['*'][c] = Action.Pop;

        //stack top is '/'
        for (char c : "(".toCharArray()) ACT['/'][c] = Action.Push;
        for (char c : "+-*/)$".toCharArray()) ACT['/'][c] = Action.Pop;
    }


    public static double evalExpr(String expr) {
        //TODO: handle unary minus operator
        Scanner scan = new Scanner(expr);
        Stack<Double> num = new StackByArray<Double>();
        Stack<Character> opr = new StackByArray<Character>();
        opr.push('#');
        for (String tok : scan) {
            char cur = tok.charAt(0);
            double n1, n2;  //operands

            if (Scanner.isDigit(cur)) {
                num.push(Double.parseDouble(tok));
            } else {
                Action act;
                loop:
                while ((act = ACT[opr.top()][cur]) == Action.Pop) {
                    char op = opr.pop();
                    switch (op) {
                        case '+':
                            n2 = num.pop();
                            n1 = num.pop();
                            num.push(n1 + n2);
                            break;
                        case '-':
                            n2 = num.pop();
                            n1 = num.pop();
                            num.push(n1 - n2);
                            break;
                        case '*':
                            n2 = num.pop();
                            n1 = num.pop();
                            num.push(n1 * n2);
                            break;
                        case '/':
                            n2 = num.pop();
                            n1 = num.pop();
                            num.push(n1 / n2);
                            break;
                        case '~':
                            n2 = num.pop();
                            num.push(-n2);
                            break;
                        case '#':
                        case '(':
                            break loop; //cur cancels op
                        default:
                            throw new UnsupportedOperationException("Error: " + tok);
                    }
                }
                if (act == Action.Push)
                    opr.push(cur);
                else if (act == null)
                    throw new IllegalStateException("Syntax error: " + tok);
            }
        }
        if (opr.size() != 0 || num.size() != 1)
            throw new IllegalStateException("Syntax error");
        return num.pop();
    }

    public static String infixToPostfix(String expr) {
        //TODO: implement this method
        Scanner scan = new Scanner(expr);
        Stack<Character> opr = new StackByArray<Character>();
        String result = "";
        opr.push('#');
        for (String tok : scan) {
            char cur = tok.charAt(0);
            if (Scanner.isAlpha(cur))
                throw new UnsupportedOperationException("There is Alphabet Error:" + tok);
            else if (Scanner.isDigit(cur)) {
                result += " " + tok;
            } else {
                Action act;
                while ((act = ACT[opr.top()][cur]) == Action.Pop) {
                    char op = opr.pop();
                    if (op == '+' || op == '-' || op == '*' || op == '/' | op == '~') {
                        result += " " + op;
                    } else if (op == '(' || op == '#') break;
                    else throw new UnsupportedOperationException("Error: " + tok);
                }
                if (act == Action.Push)
                    opr.push(cur);
                else if (act == null)
                    throw new IllegalStateException("Syntax error:" + tok);
            }
        }
        if (opr.size() != 0)
            throw new IllegalStateException("Syntax error");
        return result;
    }

    public static double evalPostfixExpr(String expr) {
        //TODO: implement this method
        Stack<Double> num = new StackByArray<Double>();
        Scanner scan = new Scanner(expr);
        for (String sc : scan) {
            char cur = sc.charAt(0);
            if (Scanner.isAlpha(cur)) {
                throw new UnsupportedOperationException("There is Alphabet Error:" + cur);
            } else if (Scanner.isDigit(cur)) {
                num.push(Double.parseDouble(sc));

            } else if (cur == '~') {
                double n1 = num.pop();
                num.push(-n1);
            } else {
                if (cur != '$') {
                    double n2 = num.pop();
                    double n3 = num.pop();
                    if (cur == '+') num.push(n3 + n2);
                    else if (cur == '-') num.push(n3 - n2);
                    else if (cur == '/') num.push(n3 / n2);
                    else if (cur == '*') num.push(n3 * n2);
                    else {
                        throw new UnsupportedOperationException("Error: " + cur);
                    }
                }
            }
        }

        return num.pop();
    }
}
