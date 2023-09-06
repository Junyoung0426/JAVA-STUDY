//Ordered defines a total order
public interface Ordered {
    public boolean ge(Ordered a);   //greater than or equal to

    //default methods
    public default boolean gt(Ordered a) {      //greater than
        return this.ge(a) && this.ne(a);
    }
    public default boolean le(Ordered a) {      //less than or equal to
        return a.ge(this);
    }
    public default boolean lt(Ordered a) {      //less than
        return a.ge(this) && a.ne(this);
    }
    public default boolean eq(Ordered a) {      //equal
        return this.ge(a) && a.ge(this);
    }
    public default boolean ne(Ordered a) {      //not equal
        return !this.eq(a);
    }
}
