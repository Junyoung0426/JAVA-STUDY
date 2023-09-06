//PolyDbl represents polynomial with double type coefficients

public class PolyDbl implements Ring, Modulo, Ordered {
    //x^2 + 2*x + 3 is stored in coef array as {3, 2, 1}
    private double[] coef;

    public PolyDbl(double[] coef) {
        //TODO: implement the constructor
        //unnecessary zero terms should be trimmed off: {3, 2, 1, 0, 0} should be {3, 2, 1}
        int length = coef.length;

        for (int i = coef.length - 1; i >= 0; i--) {
            if (coef[i] == 0 && length >= 2) {
                length--;
            } else {
                break;
            }
        }
        this.coef = new double[length];
        for (int i = 0; i < length; i++) {
            this.coef[i] = coef[i];
        }
    }

    //interface Ring
    //TODO: implement interface Ring
    public Ring add(Ring a) {
        double[] sum = new double[Math.max(this.coef.length, ((PolyDbl) a).coef.length)];
        for (int i = 0; i < this.coef.length; i++) {
            sum[i] = this.coef[i];
        }
        for (int i = 0; i < ((PolyDbl) a).coef.length; i++) {
            sum[i] += ((PolyDbl) a).coef[i];
        }
        return new PolyDbl(sum);
    }

    public Ring addIdentity() {
        double[] set = new double[1];
        set[0] = 0;
        return new PolyDbl(set);
    }

    public Ring addInverse() {
        double[] set = new double[this.coef.length];
        for (int i = 0; i < this.coef.length; i++) {
            set[i] = -this.coef[i];
        }
        return new PolyDbl(set);
    }


    public Ring mul(Ring a) {
        double[] set = new double[this.coef.length + ((PolyDbl) a).coef.length - 1];
        for (int i = 0; i < this.coef.length; i++)
            for (int j = 0; j < ((PolyDbl) a).coef.length; j++)
                set[i + j] += this.coef[i] * ((PolyDbl) a).coef[j];
        return new PolyDbl(set);

    }

    private Ring[] longdiv(Ring a) {
        //TODO: implement the long division algorithm
        double[] quo = new double[this.coef.length - ((PolyDbl) a).coef.length + 1];
        double[] num = new double[this.coef.length];
        double[] den = ((PolyDbl) a).coef;
        int dd = den.length - 1;

        //copy this.coef to num because num will be modified
        for (int i = 0; i < this.coef.length; i++)
            num[i] = this.coef[i];

        for (int qi = quo.length - 1; qi >= 0; qi--) {
            quo[qi] = num[qi + dd] / den[dd];
            for (int i = 0; i < dd; i++)
                num[qi + i] = num[qi + i] - quo[qi] * den[i];
            num[qi + dd] = 0;
        }
        return new PolyDbl[]{new PolyDbl(quo), new PolyDbl(num)};
    }

    //interface Modulo
    //TODO: implement interface Modulo
    public Ring quo(Ring a) {
        PolyDbl p = (PolyDbl) a;
        PolyDbl[] temp = (PolyDbl[]) longdiv(p);
        return temp[0];
    }

    public Ring mod(Ring a) {
        PolyDbl p = (PolyDbl) a;
        PolyDbl[] temp = (PolyDbl[]) longdiv(p);
        return temp[1];
    }

    //interface Ordered
    //TODO: implement interface Ordered
    public boolean ge(Ordered a) {
        if (this.coef.length == ((PolyDbl) a).coef.length) {
            for (int i = 0; i < this.coef.length; i++) {

                return this.coef[i] >= ((PolyDbl) a).coef[i];
            }
        }
        return coef.length >= ((PolyDbl) a).coef.length;
    }


    //PolyDbl specific methods
    public double[] getCoef() {
        return coef;
    }

    public String toString() {
        String res = "[";
        for (int i = 0; i < coef.length; i++)
            res += String.format("%f, ", coef[i]);
        return res + "]";
    }


}
