public class UnitTest {
    //Ring
    public static boolean testAddCommutativity(Ring a, Ring b) {
        Ring x = a.add(b);
        Ring y = b.add(a);
        return Comp.eq(x, y);
    }
    public static boolean testAddAssociativity(Ring a, Ring b, Ring c) {
        Ring x = a.add(b.add(c));
        Ring y = a.add(b).add(c);
        return Comp.eq(x, y);
    }
    public static boolean testAddIdentity(Ring a) {
        Ring x = a.add(a.addIdentity());
        Ring y = a.addIdentity().add(a);
        return Comp.eq(x, a) &&
                Comp.eq(y, a);
    }
    public static boolean testAddInverse(Ring a) {
        Ring b = a.addInverse();
        Ring x = a.add(b);
        Ring y = b.add(a);
        return Comp.eq(x, a.addIdentity()) &&
                Comp.eq(y, a.addIdentity());
    }
    public static boolean testMulAssociativity(Ring a, Ring b, Ring c) {
        Ring x = a.mul(b.mul(c));
        Ring y = a.mul(b).mul(c);
        return Comp.eq(x, y);
    }
    public static boolean testDistributivity(Ring a, Ring b, Ring c) {
        Ring w = a.mul(b.add(c));
        Ring x = a.mul(b).add(a.mul(c));
        Ring y = b.add(c).mul(a);
        Ring z = b.mul(a).add(c.mul(a));
        return Comp.eq(w, x) &&
                Comp.eq(y, z);
    }
    //Field
    public static boolean testMulIdentity(Field a) {
        Ring x = a.mul(a.mulIdentity());
        Ring y = a.mulIdentity().mul(a);
        return Comp.eq(x, a) &&
                Comp.eq(y, a);
    }
    public static boolean testMulInverse(Field a) {
        Ring b = a.mulInverse();
        Ring x = a.mul(b);
        Ring y = b.mul(a);
        return Comp.eq(x, a.mulIdentity()) &&
                Comp.eq(y, a.mulIdentity());
    }
    //Ordered (partial order)
    public static boolean testReflexivity(Ordered a) {
        return a.ge(a);
    }
    public static boolean testAntisymmetry(Ordered a, Ordered b) {
        if(a.ge(b))
            return !b.ge(a);
        else
            return b.ge(a);
    }
    public static boolean testTransitivity(Ordered a, Ordered b, Ordered c) {
        if(a.ge(b) && b.ge(c))  return a.ge(c);
        if(a.ge(c) && c.ge(b))  return a.ge(b);
        if(b.ge(a) && a.ge(c))  return b.ge(c);
        if(b.ge(c) && c.ge(a))  return b.ge(a);
        if(c.ge(a) && a.ge(b))  return c.ge(b);
        if(c.ge(b) && b.ge(a))  return c.ge(a);
        throw new RuntimeException("Error: unexpected");
    }
    //Euclidean Algorithm
    public static boolean testGCD(Ring a, Ring b) {
        Ring gcd = Euclidean.GCD(a, b);
        Ring c = ((Modulo)a).quo(gcd);
        Ring d = ((Modulo)b).quo(gcd);
        return Comp.eq(gcd.mul(c), a) &&
                Comp.eq(gcd.mul(d), b);
    }
    public static boolean testLCM(Ring a, Ring b) {
        Ring lcm = Euclidean.LCM(a, b);
        Ring gcd = Euclidean.GCD(a, b);
        Ring c = ((Modulo)lcm).quo(a);
        Ring d = ((Modulo)lcm).quo(b);
        return Comp.eq(c.mul(gcd), b) &&
                Comp.eq(d.mul(gcd), a);
    }
    //test properties
    protected static void onFalseThrow(boolean b) {
        if(!b)
            throw new RuntimeException("Error: unexpected");
    }
    public static void testOrdered(Ordered a, Ordered b, Ordered c) {
        onFalseThrow(testReflexivity(a));
        onFalseThrow(testAntisymmetry(a, b));
        onFalseThrow(testTransitivity(a, b, c));
        System.out.println("testOrdered: Success.");
    }
    public static void testRing(Ring a, Ring b, Ring c) {
        onFalseThrow(testAddCommutativity(a, b));
        onFalseThrow(testAddAssociativity(a, b, c));
        onFalseThrow(testAddIdentity(a));
        onFalseThrow(testAddInverse(a));
        onFalseThrow(testMulAssociativity(a, b, c));
        onFalseThrow(testDistributivity(a, b, c));
        System.out.println("testRing: Success.");
    }
    public static void testField(Field a, Field b, Field c) {
        onFalseThrow(testMulIdentity(a));
        onFalseThrow(testMulInverse(a));
        System.out.println("testField: Success.");
    }
    public static void testEuclidean(Ring a, Ring b, Ring c) {
        onFalseThrow(testGCD(a, b));
        onFalseThrow(testLCM(a, b));
        System.out.println("testEuclidean: Success.");
    }
    //test for each type
    public static void testInt() {
        System.out.println("testInt...");
        Int a = new Int(12);
        Int b = new Int(30);
        Int c = new Int(7);
        testOrdered(a, b, c);
        testRing(a, b, c);
        testEuclidean(a, b, c);
        System.out.println("testInt done");
    }
    public static void testIntMod() {
        System.out.println("testIntMod...");
        IntMod a = new IntMod(12, 11);
        IntMod b = new IntMod(30, 11);
        IntMod c = new IntMod(7, 11);
        testOrdered(a, b, c);
        testRing(a, b, c);
        testField(a, b, c);
        System.out.println("testIntMod done");
    }
    public static void testRat() {
        System.out.println("testRat...");
        Rat a = new Rat(12, 5);
        Rat b = new Rat(30, 7);
        Rat c = new Rat(7, 2);
        testOrdered(a, b, c);
        testRing(a, b, c);
        testField(a, b, c);
        testEuclidean(a, b, c);
        System.out.println("testRat done");
    }
    public static void testPolyDbl() {
        System.out.println("testPolyDbl...");
        PolyDbl a = new PolyDbl(new double[] { 1, 2, 1});
        PolyDbl b = new PolyDbl(new double[] {-1, 0, 1});
        PolyDbl c = new PolyDbl(new double[] { 1, 1, 1});
        testOrdered(a, b, c);
        testRing(a, b, c);
        testEuclidean(a, b, c);
        System.out.println("testPolyDbl done");
    }
    public static void testPolyRat() {
        System.out.println("testPolyRat...");
        Poly a = new Poly(new Rat[] {new Rat( 1,1), new Rat(2,1), new Rat(1,1)});
        Poly b = new Poly(new Rat[] {new Rat(-1,1), new Rat(0,1), new Rat(1,1)});
        Poly c = new Poly(new Rat[] {new Rat( 1,1), new Rat(1,1), new Rat(1,1)});
        testOrdered(a, b, c);
        testRing(a, b, c);
        testEuclidean(a, b, c);
        System.out.println("testPolyRat done");
    }
    public static void testPolyIntMod() {
        System.out.println("testPolyIntMod...");
        Poly a = new Poly(new IntMod[] {new IntMod( 1,3), new IntMod(2,3), new IntMod(1,3)});
        Poly b = new Poly(new IntMod[] {new IntMod(-1,3), new IntMod(0,3), new IntMod(1,3)});
        Poly c = new Poly(new IntMod[] {new IntMod( 1,3), new IntMod(1,3), new IntMod(1,3)});
        testOrdered(a, b, c);
        testRing(a, b, c);
        CRC.testCRC();
        System.out.println("testPolyIntMod done");
    }
}
