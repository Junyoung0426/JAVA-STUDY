public interface Modulo {
    public Ring quo(Ring d);            //quotient

    //default methods
    public default Ring mod(Ring d) {   //remainder
        Ring q = this.quo(d);
        return ((Ring)this).sub(d.mul(q));
    }
}
