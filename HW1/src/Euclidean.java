public class Euclidean {
    protected static Ring GCDImpl(Ring a, Ring b) {
        //TODO: implement GCDImpl by remainder
        // if a is equal to its add identity return b;
        // if b is equal to its add identity return a;
        // if a >= b then return gcd of a % b and b
        // if b >= a then return gcd of b % a and a
        if (Comp.eq(a, a.addIdentity())) {
            return b;
        } else if (Comp.eq(b, b.addIdentity())) {
            return a;
        } else {
            if (Comp.ge(a,b)) {
                return GCDImpl(b, ((Modulo) a).mod(b));
            } else {
                return GCDImpl(a, ((Modulo) b).mod(a));
            }
        }
    }


    protected static Ring abs(Ring a) {
        if (Comp.le(a, a.addIdentity()))
            return a.addInverse();
        else
            return a;
    }

    //Greatest Common Denominator
    public static Ring GCD(Ring a, Ring b) {
        return GCDImpl(abs(a), abs(b));
    }

    //Least Common Multiplier
    public static Ring LCM(Ring a, Ring b) {
        Ring gcd = GCD(a, b);
        Ring q = ((Modulo) b).quo(gcd);
        return a.mul(q);
    }
}

