//Poly represents polynomial with Field type coefficients


public class Poly implements Ring, Modulo, Ordered {
    // x^2 + 2*x + 3 is stored in coef array as {3, 2, 1}
    private Field[] coef;

    public Poly(Field[] coef) {
        //TODO: implement the constructor
        //unnecessary zero terms should be trimmed off: {3, 2, 1, 0, 0} should be {3, 2, 1}
        Ring zero = coef[0].addIdentity();
        int length = coef.length;
        for (int i = coef.length - 1; i >= 0; i--) {
            if (Comp.eq(coef[i], zero) && length >= 2) {
                length--;
            } else {
                break;
            }
        }

        this.coef = new Field[length];
        for (int i = 0; i < length; i++) {
            this.coef[i] = coef[i];
        }
    }

    //interface Ring
    //TODO: implement interface Ring

    public Ring add(Ring a) {
        Field[] sum = new Field[Math.max(this.coef.length, ((Poly) a).coef.length)];
        for(int i = 0; i < sum.length; i++) {
            sum[i] = (Field) this.coef[0].addIdentity();
        }
        for (int i = 0; i < this.coef.length; i++) {
            sum[i] = this.coef[i];
        }
        for (int i = 0; i < ((Poly) a).coef.length; i++) {
            sum[i] = (Field) sum[i].add(((Poly) a).coef[i]);
        }
        return new Poly(sum);
    }

    public Ring addIdentity() {
        Field[] set = new Field[1];
        set[0] = (Field) this.coef[0].addIdentity();
        return new Poly(set);
    }

    public Ring addInverse() {
        Field[] set = new Field[coef.length];
        for (int i = 0; i < coef.length; i++) {
            set[i] = (Field) coef[i].addInverse();
        }
        return new Poly(set);
    }

    public Ring mul(Ring a) {
        Field[] set = new Field[this.coef.length + ((Poly) a).coef.length - 1];
        for (int i = 0; i < set.length; i++) {
            set[i] = (Field) this.coef[0].addIdentity();
        }
        for (int i = 0; i < this.coef.length; i++) {
            for (int j = 0; j < ((Poly) a).coef.length; j++)
                set[i + j] = (Field) set[i + j].add(this.coef[i].mul(((Poly) a).coef[j]));
        }
        return new Poly(set);
    }

    private Ring[] longdiv(Ring a) {
        //TODO: implement the long division algorithm
        //return value: longdiv(...)[0]: quotient,
        //              longdiv(...)[1]: remainder
        Field[] quo = new Field[this.coef.length - ((Poly) a).coef.length + 1];
        Field[] num = new Field[this.coef.length];
        Field[] den = ((Poly) a).coef;
        int dd = den.length - 1;

        //copy this.coef to num because num will be modified
        for (int i = 0; i < this.coef.length; i++)
            num[i] = this.coef[i];

        for (int qi = quo.length - 1; qi >= 0; qi--) {
            quo[qi] = (Field) (num[qi + dd]).mul(den[dd].mulInverse());
            for (int i = 0; i < dd; i++)
                num[qi + i] = (Field) (num[qi + i]).add((quo[qi].mul(den[i])).addInverse());
            num[qi + dd] = (Field) num[qi + dd].addIdentity();
        }
        return new Poly[]{new Poly(quo), new Poly(num)};
    }
    //interface Modulo
    //TODO: implement interface Modulo

    public Ring quo(Ring a) {
        Poly p = (Poly) a;
        Poly[] temp = (Poly[]) longdiv(p);
        return temp[0];
    }

    public Ring mod(Ring a) {
        Poly p = (Poly) a;
        Poly[] temp = (Poly[]) longdiv(p);
        return temp[1];
    }

    //interface Ordered
    //TODO: implement interface Ordered
    public boolean ge(Ordered a) {
        if (this.coef.length == ((Poly) a).coef.length) {
            for (int i = 0; i < this.coef.length; i++) {
                return Comp.ge(this.coef[i], ((Poly) a).coef[i]);
            }
        }
        return coef.length >= ((Poly) a).coef.length;
    }

    //Poly specific methods
    public Field[] getCoef() {
        return coef;
    }

    public String toString() {
        String res = "[";
        for (int i = 0; i < coef.length; i++)
            res += String.format("%s, ", coef[i]);
        return res + "]";
    }


}
