//Cyclic Redundancy Check
@SuppressWarnings("unchecked")
public class CRC {
    static final IntMod I = new IntMod(1, 2);
    static final IntMod O = new IntMod(0, 2);

    public static Poly sendMessage(Poly msg, Poly gen) {
        checkPoly(gen);
        checkPoly(msg);

        Poly shift = shiftPoly(gen); /*TODO: compute x^r, when gen = x^r + ... + 1*/
        Poly shiftMsg = (Poly) shift.mul(msg);/*TODO: compute msg * shift*/
        Poly checksum = (Poly) shiftMsg.mod(gen);/*TODO: compute shiftMsg mod gen*/
        Poly txMsg = (Poly) shiftMsg.add(checksum.addInverse());/*TODO: compute shiftMsg - checksum*/

        printPoly("msg:      ", msg);
        printPoly("gen:      ", gen);
        printPoly("shift:    ", shift);
        printPoly("shiftMsg: ", shiftMsg);
        printPoly("checksum: ", checksum);
        printPoly("txMsg:    ", txMsg);
        return txMsg;
    }

    public static boolean checkMessage(Poly rxMsg, Poly gen) {
        checkPoly(gen);
        checkPoly(rxMsg);

        Ring rem = rxMsg.mod(gen);/*TODO: compute msg mod gen*/
        printPoly("rem:      ", (Poly) rem);

        return Comp.eq(rem, rem.addIdentity());
    }

    public static Poly receiveMessage(Poly rxMsg, Poly gen) {
        checkPoly(gen);
        checkPoly(rxMsg);
        Poly shift = shiftPoly(gen);/*TODO: compute x^r, when gen = x^r + ... + 1*/
        Poly msg = (Poly)rxMsg.quo(shift);/*TODO: compute rxMsg quo shift*/

        printPoly("shift:    ", shift);
        printPoly("msg:      ", msg);
        return msg;
    }

    protected static Poly shiftPoly(Poly gen) {
        int shiftLen = ((Field[]) gen.getCoef()).length;
        IntMod[] shiftCoef = new IntMod[shiftLen];
        for (int i = 0; i < shiftLen - 1; i++) {
            shiftCoef[i] = O;
        }
        shiftCoef[shiftLen -1] = I;
        return new Poly(shiftCoef);
    }

    protected static void checkPoly(Poly poly) {
        //check whether all coefficients are integer modulo 2
        Field[] coefs = poly.getCoef();
        for (Field coef : coefs)
            if (((IntMod) coef).getMod() != 2)
                throw new IllegalArgumentException("Not a modulo 2 polynomial");
    }

    protected static void printPoly(String tag, Poly poly) {
        System.out.format("%s %s\n", tag, poly);
    }

    public static void testCRC() {
        /* expected output
            msg:       [1%2, 1%2, 0%2, 1%2, 1%2, 0%2, 1%2, 0%2, 1%2, 1%2, ]
            gen:       [1%2, 1%2, 0%2, 0%2, 1%2, ]
            shift:     [0%2, 0%2, 0%2, 0%2, 1%2, ]
            shiftMsg:  [0%2, 0%2, 0%2, 0%2, 1%2, 1%2, 0%2, 1%2, 1%2, 0%2, 1%2, 0%2, 1%2, 1%2, ]
            checksum:  [0%2, 1%2, 1%2, 1%2, ]
            txMsg:     [0%2, 1%2, 1%2, 1%2, 1%2, 1%2, 0%2, 1%2, 1%2, 0%2, 1%2, 0%2, 1%2, 1%2, ]
            rem:       [0%2, ]
            shift:     [0%2, 0%2, 0%2, 0%2, 1%2, ]
            msg:       [1%2, 1%2, 0%2, 1%2, 1%2, 0%2, 1%2, 0%2, 1%2, 1%2, ]
            testCRC Success!
         */
        Poly message = new Poly(new IntMod[]{I, I, O, I, I, O, I, O, I, I});
        Poly generator = new Poly(new IntMod[]{I, I, O, O, I});
        Poly answer = new Poly(new IntMod[]{O, I, I, I, I, I, O, I, I, O, I, O, I, I});

        Poly txMsg = sendMessage(message, generator);
        if (!Comp.eq(answer, txMsg))
            throw new RuntimeException("Error: unexpected");

        Poly rxMsg = txMsg;
        if (!checkMessage(rxMsg, generator))
            throw new RuntimeException("Error: unexpected");

        Poly msg = receiveMessage(rxMsg, generator);
        if (!Comp.eq(message, msg))
            throw new RuntimeException("Error: unexpected");

        System.out.println("testCRC Success!");
    }

    public static void main(String[] args) {
        testCRC();
    }
}

