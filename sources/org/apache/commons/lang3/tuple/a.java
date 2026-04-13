package org.apache.commons.lang3.tuple;

import java.util.Map;

/* compiled from: ImmutablePair */
public final class a<L, R> extends b<L, R> {
    public static final a<?, ?>[] EMPTY_ARRAY = new a[0];
    private static final a c = of((Object) null, (Object) null);
    private static final long serialVersionUID = 4954918890077093841L;
    public final L left;
    public final R right;

    public static <L, R> a<L, R>[] emptyArray() {
        return EMPTY_ARRAY;
    }

    public static <L, R> b<L, R> left(L left2) {
        return of(left2, (Object) null);
    }

    public static <L, R> a<L, R> nullPair() {
        return c;
    }

    public static <L, R> a<L, R> of(L left2, R right2) {
        return new a<>(left2, right2);
    }

    public static <L, R> a<L, R> of(Map.Entry<L, R> pair) {
        R right2;
        L left2;
        if (pair != null) {
            left2 = pair.getKey();
            right2 = pair.getValue();
        } else {
            left2 = null;
            right2 = null;
        }
        return new a<>(left2, right2);
    }

    public static <L, R> b<L, R> right(R right2) {
        return of((Object) null, right2);
    }

    public a(L left2, R right2) {
        this.left = left2;
        this.right = right2;
    }

    public L getLeft() {
        return this.left;
    }

    public R getRight() {
        return this.right;
    }

    public R setValue(R r) {
        throw new UnsupportedOperationException();
    }
}
