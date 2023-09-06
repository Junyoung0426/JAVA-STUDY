//A helper class for comparison
public class Comp {
    public static boolean ge(Object a, Object b) {    //greater than or equal to
        return ((Ordered) a).ge((Ordered) b);
    }
    public static boolean gt(Object a, Object b) {    //greater than
        return ((Ordered) a).gt((Ordered) b);
    }
    public static boolean le(Object a, Object b) {    //less than or equal to
        return ((Ordered) a).le((Ordered) b);
    }
    public static boolean lt(Object a, Object b) {    //less than
        return ((Ordered) a).lt((Ordered) b);
    }
    public static boolean eq(Object a, Object b) {    //equal
        return ((Ordered) a).eq((Ordered) b);
    }
    public static boolean ne(Object a, Object b) {    //not equal
        return ((Ordered) a).ne((Ordered) b);
    }
}
