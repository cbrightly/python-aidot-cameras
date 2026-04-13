package org.apache.commons.lang3.tuple;

import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/* compiled from: Pair */
public abstract class b<L, R> implements Map.Entry<L, R>, Comparable<b<L, R>>, Serializable {
    public static final b<?, ?>[] EMPTY_ARRAY = new a[0];
    private static final long serialVersionUID = 4954918890077093841L;

    public abstract L getLeft();

    public abstract R getRight();

    /* compiled from: Pair */
    public static final class a<L, R> extends b<L, R> {
        private static final long serialVersionUID = 1;

        private a() {
        }

        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return b.super.compareTo((b) obj);
        }

        public L getLeft() {
            return null;
        }

        public R getRight() {
            return null;
        }

        public R setValue(R r) {
            return null;
        }
    }

    public static <L, R> b<L, R>[] emptyArray() {
        return EMPTY_ARRAY;
    }

    public static <L, R> b<L, R> of(L left, R right) {
        return a.of(left, right);
    }

    public static <L, R> b<L, R> of(Map.Entry<L, R> pair) {
        return a.of(pair);
    }

    public int compareTo(b<L, R> other) {
        return new org.apache.commons.lang3.builder.a().g(getLeft(), other.getLeft()).g(getRight(), other.getRight()).u();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry<?, ?> other = (Map.Entry) obj;
        if (!Objects.equals(getKey(), other.getKey()) || !Objects.equals(getValue(), other.getValue())) {
            return false;
        }
        return true;
    }

    public final L getKey() {
        return getLeft();
    }

    public R getValue() {
        return getRight();
    }

    public int hashCode() {
        return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
    }

    public String toString() {
        return "(" + getLeft() + StringUtil.COMMA + getRight() + ')';
    }

    public String toString(String format) {
        return String.format(format, new Object[]{getLeft(), getRight()});
    }
}
