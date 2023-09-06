public class SetImpl<E extends Ordered> implements Set<E> {
    private CircularlyDblLinkedList<E> list;

    public SetImpl() {
        list = new CircularlyDblLinkedList<E>();
    }

    public SetImpl(SetImpl<E> set) {
        list = set.copyList();
        dedupe();
    }

    public SetImpl(E[] arr) {
        list = new CircularlyDblLinkedList<E>();
        for (int i = 0; i < arr.length; i++)
            list.add(i, arr[i]);
        dedupe();
    }

    //TODO: implement interface Set

    public boolean has(E e) {
        for (int i = 0; i < list.size(); i++) {
            return list.get(i).eq(e);
        }
        return false;
    }


    public boolean isEqual(Set<E> set) {
        SetImpl<E> ss = ((SetImpl<E>) set);
        if (list.size != ss.list.size) {
            return false;
        }
        for (int m = 0; m < list.size; m++) {
            for (int n = m + 1; n < list.size; n++) {
                if (list.get(n).lt(list.get(m))) {
                    E temp = list.get(m);
                    list.set(m, list.get(n));
                    list.set(n, temp);
                }
            }
        }
        for (int m = 0; m < ss.list.size; m++) {
            for (int n = m + 1; n < ss.list.size; n++) {
                if (ss.list.get(n).lt(ss.list.get(m))) {
                    E temp = ss.list.get(m);
                    ss.list.set(m, ss.list.get(n));
                    ss.list.set(n, temp);
                }
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i).ne(ss.list.get(i)))) {
                return false;
            }
        }
        return true;
    }


    public Set<E> union(Set<E> set) {
        SetImpl<E> s = (SetImpl<E>) set;
        SetImpl<E> empty = new SetImpl<E>(this);

        for (E j : s.list) {
            empty.list.add(j);
        }
        for (int k = 0; k < empty.list.size; k++) {
            for (int j = k + 1; j < empty.list.size; j++) {
                if (empty.list.get(j).lt(empty.list.get(k))) {
                    E temp = empty.list.get(k);
                    empty.list.set(k, empty.list.get(j));
                    empty.list.set(j, temp);
                }
            }
        }
        return new SetImpl<E>(empty);
    }


    public Set<E> intersection(Set<E> set) {
        SetImpl<E> ss = (SetImpl<E>) set;
        SetImpl<E> newList = new SetImpl<E>();
        int k = 0;
        for (int i = 0; i < list.size; i++) {
            for (int j = 0; j < ss.list.size; j++) {
                if (list.get(i).eq(ss.list.get(j))) {
                    newList.list.add(k++, ss.list.get(j));
                }
            }
        }
        for (int m = 0; m < newList.list.size; m++) {
            for (int n = m + 1; n < newList.list.size; n++) {
                if (newList.list.get(n).lt(newList.list.get(m))) {
                    E temp = newList.list.get(m);
                    newList.list.set(m, newList.list.get(n));
                    newList.list.set(n, temp);
                }
            }
        }

        return new SetImpl<E>(newList);
    }

    public Set<E> difference(Set<E> set) {
        SetImpl<E> lalo = (SetImpl<E>) set;
        SetImpl<E> newList = new SetImpl<E>(this);
        for (int t = 0; t < newList.list.size; t++) {
            for (int k = 0; k < lalo.list.size; k++) {
                if (newList.list.get(t).eq(lalo.list.get(k))) {
                    newList.list.remove(t);
                }
            }
        }
        for (int m = 0; m < newList.list.size; m++) {
            for (int n = m + 1; n < newList.list.size; n++) {
                if (newList.list.get(n).lt(newList.list.get(m))) {
                    E temp = newList.list.get(m);
                    newList.list.set(m, newList.list.get(n));
                    newList.list.set(n, temp);
                }
            }
        }

        return new SetImpl<E>(newList);
    }

    //helper methods
    private CircularlyDblLinkedList<E> copyList() {
        CircularlyDblLinkedList<E> dst = new CircularlyDblLinkedList<E>();
        int i = 0;
        for (E e : list)
            dst.add(i++, e);
        return dst;
    }


    //TODO: remove duplicated elements (do not sort the list)
    private void dedupe() {
        for (int i = 0; i < list.size; i++) {
            for (int j = i + 1; j < list.size; j++) {
                if (list.get(i).eq(list.get(j))) {
                    list.remove(j);
                    j--;
                }
            }
        }
    }


    private static void onFalseThrow(boolean b) {
        if (!b)
            throw new RuntimeException("Error: unexpected");
    }

    public static void main(String[] args) {
        Int _1 = new Int(1);
        Int _2 = new Int(2);
        Int _3 = new Int(3);
        SetImpl<Int> a = new SetImpl<Int>(new Int[]{_1});
        SetImpl<Int> b = new SetImpl<Int>(new Int[]{_1, _2});
        SetImpl<Int> c = new SetImpl<Int>(new Int[]{_2, _3});
        SetImpl<Int> e = new SetImpl<Int>();
        SetImpl<Int> x = new SetImpl<Int>(new Int[]{_1, _1, _1});
        SetImpl<Int> y = new SetImpl<Int>(new Int[]{_1, _2, _1, _2});
        SetImpl<Int> z = new SetImpl<Int>(new Int[]{_3, _2, _3, _3});
        SetImpl<Int> u = new SetImpl<Int>(new Int[]{_1, _2, _3});

        onFalseThrow(x.isEqual(a));
        onFalseThrow(y.isEqual(b));
        onFalseThrow(z.isEqual(c));
        onFalseThrow(y.union(z).isEqual(u));
        onFalseThrow(x.intersection(y).isEqual(x));
        onFalseThrow(x.intersection(z).isEqual(e));
        onFalseThrow(u.difference(z).isEqual(x));
        System.out.println("Success!");
    }
}