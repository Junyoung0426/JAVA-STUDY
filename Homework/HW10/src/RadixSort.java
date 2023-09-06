@SuppressWarnings("unchecked")
public class RadixSort {
    public static interface IEntry<V> {
        public int key();

        public V value();
    }

    public static class Entry<V> implements IEntry<V> {
        private int k;
        private V v;

        public Entry() {
        }

        public Entry(int key, V v) {
            this.k = key;
            this.v = v;
        }

        public int key() {
            return k;
        }

        public V value() {
            return v;
        }

        public String toString() {
            return String.format("%d:%s", k, v.toString());
        }
    }

    //TODO
    //Submit your course evaluation at
    //https://stonybrook.campuslabs.com/eval-home/
    //
    protected static <E> void swap(E[] a, int i, int j) {
        E t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    protected static <E> String toString(E[] a) {
        StringBuilder sb = new StringBuilder();
        for (E e : a)
            sb.append(e.toString() + ", ");
        return sb.toString();
    }

    //return the shift-th bit of a
    //  e.g. 5 in binary is 0101. Hence,
    //      checkBit(5, 0) = 1, checkBit(5, 1) = 0,
    //      checkBit(5, 2) = 1, checkBit(5, 3) = 0
    protected static int checkBit(int a, int shift) {
        return (a & (1 << shift)) != 0 ? 1 : 0;
    }

    protected static <V, E extends IEntry<V>> void radixSort1(E[] a, int b, int l, int r) {
        //TODO
        //return if l >= r or b < 0
        //repeat
        //  from l find the entry whose key has 1 in the b-th bit
        //  from r find the entry whose key has 0 in the b-th bit
        //  swap the two entries
        //until l and r meet
        //recursively call radixSort1 with b-1 and new l, r
        int ls = l;
        int rs = r;

        if (l >= r || b < 0) {
            return;
        }
        while (ls < rs) {
            while (checkBit(a[ls].key(), b) != 1 && ls < r) {
                ls++;
            }
            while (checkBit(a[rs].key(), b) != 0 && rs > l) {
                rs--;
            }
            if (ls == rs || ls > rs)
                break;

            swap(a, ls, rs);
        }

        if (ls == rs) {
            radixSort1(a, b - 1, l, r);

        } else {
            radixSort1(a, b - 1, l, ls - 1);
            radixSort1(a, b - 1, ls, r);
        }
    }

    public static <V, E extends IEntry<V>> void radixSort1(E[] a) {
        radixSort1(a, 31, 0, a.length - 1);
    }

    public static <V, E extends IEntry<V>> void radixSort2(E[] a) {
        int n = a.length;
        E[] s = a;
        E[] t = (E[]) (new IEntry[n]);
        //TODO
        //foreach i in [0, 31]
        //  k = 0;
        //  foreach j in [0, n)
        //      copy s[j] to t[k++] if the i-th bit of the key of s[j] is 0
        //  foreach j in [0, n)
        //      copy s[j] to t[k++] if the i-th bit of the key of s[j] is 1
        //  swap s and t: make s reference where t was referencing and vice versa
        //copy from s to a
        for (int i = 0; i < 32; i++) {
            int i0 = 0;
            int i1 = 0;
            for (int j = 0; j < n; j++) {
                if (checkBit(a[j].key(), i) == 0)
                    i1++;
            }
            for (int j = 0; j < a.length; j++) {
                if (checkBit(a[j].key(), i) == 0)
                    t[i0++] = s[j];
                else
                    t[i1++] = s[j];
            }
            for (int k = 0; k < a.length; k++)
                a[k] = t[k];
        }
    }

    public static void main(String[] args) {
        int[] k = new int[]{3, 1, 2, 4, 5, 7, 9, 0, 8, 6};
        IEntry<String>[] a = (IEntry<String>[]) (new IEntry[k.length]);
        for (int i = 0; i < k.length; i++)
            a[i] = new Entry<>(k[i], "" + k[i]);
        radixSort1(a);
        System.out.println("Radix sort 1: " + toString(a));

        for (int i = 0; i < k.length; i++)
            a[i] = new Entry<>(k[i], "" + k[i]);
        radixSort2(a);
        System.out.println("Radix sort 2: " + toString(a));
    }
}