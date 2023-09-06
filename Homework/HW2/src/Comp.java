//A helper class for comparison
public class Comp {
    public static boolean ge(Ring a, Ring b) {    //greater than or equal to
        return ((Ordered) a).ge((Ordered) b);
    }
    public static boolean gt(Ring a, Ring b) {    //greater than
        return ((Ordered) a).gt((Ordered) b);
    }
    public static boolean le(Ring a, Ring b) {    //less than or equal to
        return ((Ordered) a).le((Ordered) b);
    }
    public static boolean lt(Ring a, Ring b) {    //less than
        return ((Ordered) a).lt((Ordered) b);
    }
    public static boolean eq(Ring a, Ring b) {    //equal
        return ((Ordered) a).eq((Ordered) b);
    }
    public static boolean ne(Ring a, Ring b) {    //not equal
        return ((Ordered) a).ne((Ordered) b);
    }
}
