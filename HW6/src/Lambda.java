public class Lambda {
    //EPS is a small number
    public static final double EPS = 1e-10;

    //Interfaces

    //function
    public interface Fun<T, R> {
        //Single Abstract Method
        public R apply(T a);
    }

    //recursive function
    public interface Rec<T, R> {
        //Single Abstract Method
        public R fun(Rec<T, R> self, T a);

        public default R apply(T a) {
            return fun(this, a);
        }
    }

    //recursive function with 2 parameters
    public interface Rec2<T1, T2, R> {
        //Single Abstract Method
        public R fun(Rec2<T1, T2, R> self, T1 a, T2 b);

        public default R apply(T1 a, T2 b) {
            return fun(this, a, b);
        }
    }

    //Factorial
    public static Rec<Integer, Integer> fact =
            (self, a) -> a <= 1 ? a
                    : a * self.apply(a - 1);

    //GCD
    public static Rec2<Integer, Integer, Integer> gcd =
            (self, a, b) -> a > b ? self.apply(a - b, b)
                    : b > a ? self.apply(b - a, a)
                    : a;

    //Bisection method
    public interface Bisection {
        //Single Abstract Method
        public double fun(double x);

        //Solve: finds x such that fun(x) = 0
        public default double solve(double xl, double xr) {
            /*TODO: implement this function recursively
                    repeat until xr - xl < EPS*/
            double m = (xl + xr) / 2;
            if(Math.abs(xl - xr) <EPS) {
                return m;
            }
            else if (fun(xl) * fun(m) <= 0)
                return solve(xl, m);
            else if (fun(xr) * fun(m) <= 0)
                return solve(m, xr);
            else
                throw new IllegalArgumentException("Exception error");
        }
    }

    //Newton's method
    public interface Newton {
        //Single Abstract Method
        public double fun(double x);

        //Solve: finds x such that fun(x) = 0
        public default double solve(double x0) {
            //f: the function to solve
            //g: next guess of Newton's method
            //   e.g. g(x0) -> x1, g(x1) -> x2, ...
            Fun<Double, Double> fx = x -> fun(x);
            Fun<Double, Double> gx = next.apply(fx);
            return fixedPoint.apply(gx, x0);
        }
    }

    //Derivative
    public static Fun<Fun<Double, Double>, Fun<Double, Double>> derivative =
            f -> x -> (f.apply(x + EPS) - f.apply(x)) / EPS;

    //next: it returns the next guess of Netwon's method
    //next(f) = g, g(x) = x - f(x) / f'(x)
    //i.e., let g = next(f), g(x0) = x1, g(x1) = x2, g(x2) = x3,...
    public static Fun<Fun<Double, Double>, Fun<Double, Double>>
            next = f -> x -> x - f.apply(x) / derivative.apply(f).apply(x);/*TODO: implement this function*/

    //fixedPoint: returns the fixed point of f
    //fixed point of f is x such that |f(x) - x| < EPS
    public static Rec2<Fun<Double, Double>, Double, Double>
            fixedPoint = (self, f, x) -> Math.abs(f.apply(x) - x) < EPS ? f.apply(x) : self.apply(f, f.apply(x));/*TODO: implement this function*/

    //fixedPoint2: curried version of fixedPoint
    public static Rec<Fun<Double, Double>, Fun<Double, Double>>
            fixedPoint2 = (self, f) -> x -> Math.abs(f.apply(x) - x) < EPS ? f.apply(x) : self.apply(f).apply(f.apply(x));/*TODO: implement this function*/

    //Square root function
    public static Fun<Double, Double>
            sqrt = x -> ((Newton) y -> y * y - x).solve(1.0);

    public static void main(String[] args) {
        //Recursion
        System.out.println("fact(5) :     " + fact.apply(5));
        System.out.println("gcd(12, 30) : " + gcd.apply(12, 30));

        //Bisection
        Bisection b = x -> x * x + 4 * x - 8;
        System.out.println("bisection: " + b.solve(-10, 10));

        //Newton's method
        Newton n = x -> x * x + 4 * x - 8;
        System.out.println("netwon:    " + n.solve(-10));
        System.out.println("sqrt(2):   " + sqrt.apply(2.0));

        //Fixed point
        Fun<Double, Double> cx = x -> Math.cos(x);
        System.out.println("fixedPoint:  " + fixedPoint.apply(cx, 1.0));
        System.out.println("fixedPoint2: " + fixedPoint2.apply(cx).apply(1.0));
    }
}
