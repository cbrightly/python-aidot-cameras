package org.apache.commons.lang3.builder;

import java.util.Comparator;

/* compiled from: CompareToBuilder */
public class a {
    private int a = 0;

    public a g(Object lhs, Object rhs) {
        return h(lhs, rhs, (Comparator<?>) null);
    }

    public a h(Object lhs, Object rhs, Comparator<?> comparator) {
        if (this.a != 0 || lhs == rhs) {
            return this;
        }
        if (lhs == null) {
            this.a = -1;
            return this;
        } else if (rhs == null) {
            this.a = 1;
            return this;
        } else {
            if (lhs.getClass().isArray()) {
                t(lhs, rhs, comparator);
            } else if (comparator == null) {
                this.a = ((Comparable) lhs).compareTo(rhs);
            } else {
                this.a = comparator.compare(lhs, rhs);
            }
            return this;
        }
    }

    private void t(Object lhs, Object rhs, Comparator<?> comparator) {
        if (lhs instanceof long[]) {
            p((long[]) lhs, (long[]) rhs);
        } else if (lhs instanceof int[]) {
            o((int[]) lhs, (int[]) rhs);
        } else if (lhs instanceof short[]) {
            r((short[]) lhs, (short[]) rhs);
        } else if (lhs instanceof char[]) {
            l((char[]) lhs, (char[]) rhs);
        } else if (lhs instanceof byte[]) {
            k((byte[]) lhs, (byte[]) rhs);
        } else if (lhs instanceof double[]) {
            m((double[]) lhs, (double[]) rhs);
        } else if (lhs instanceof float[]) {
            n((float[]) lhs, (float[]) rhs);
        } else if (lhs instanceof boolean[]) {
            s((boolean[]) lhs, (boolean[]) rhs);
        } else {
            q((Object[]) lhs, (Object[]) rhs, comparator);
        }
    }

    public a f(long lhs, long rhs) {
        if (this.a != 0) {
            return this;
        }
        this.a = Long.compare(lhs, rhs);
        return this;
    }

    public a e(int lhs, int rhs) {
        if (this.a != 0) {
            return this;
        }
        this.a = Integer.compare(lhs, rhs);
        return this;
    }

    public a i(short lhs, short rhs) {
        if (this.a != 0) {
            return this;
        }
        this.a = Short.compare(lhs, rhs);
        return this;
    }

    public a b(char lhs, char rhs) {
        if (this.a != 0) {
            return this;
        }
        this.a = Character.compare(lhs, rhs);
        return this;
    }

    public a a(byte lhs, byte rhs) {
        if (this.a != 0) {
            return this;
        }
        this.a = Byte.compare(lhs, rhs);
        return this;
    }

    public a c(double lhs, double rhs) {
        if (this.a != 0) {
            return this;
        }
        this.a = Double.compare(lhs, rhs);
        return this;
    }

    public a d(float lhs, float rhs) {
        if (this.a != 0) {
            return this;
        }
        this.a = Float.compare(lhs, rhs);
        return this;
    }

    public a j(boolean lhs, boolean rhs) {
        if (this.a != 0 || lhs == rhs) {
            return this;
        }
        if (lhs) {
            this.a = 1;
        } else {
            this.a = -1;
        }
        return this;
    }

    public a q(Object[] lhs, Object[] rhs, Comparator<?> comparator) {
        if (this.a != 0 || lhs == rhs) {
            return this;
        }
        int i = -1;
        if (lhs == null) {
            this.a = -1;
            return this;
        } else if (rhs == null) {
            this.a = 1;
            return this;
        } else if (lhs.length != rhs.length) {
            if (lhs.length >= rhs.length) {
                i = 1;
            }
            this.a = i;
            return this;
        } else {
            for (int i2 = 0; i2 < lhs.length && this.a == 0; i2++) {
                h(lhs[i2], rhs[i2], comparator);
            }
            return this;
        }
    }

    public a p(long[] lhs, long[] rhs) {
        if (this.a != 0 || lhs == rhs) {
            return this;
        }
        int i = -1;
        if (lhs == null) {
            this.a = -1;
            return this;
        } else if (rhs == null) {
            this.a = 1;
            return this;
        } else if (lhs.length != rhs.length) {
            if (lhs.length >= rhs.length) {
                i = 1;
            }
            this.a = i;
            return this;
        } else {
            for (int i2 = 0; i2 < lhs.length && this.a == 0; i2++) {
                f(lhs[i2], rhs[i2]);
            }
            return this;
        }
    }

    public a o(int[] lhs, int[] rhs) {
        if (this.a != 0 || lhs == rhs) {
            return this;
        }
        int i = -1;
        if (lhs == null) {
            this.a = -1;
            return this;
        } else if (rhs == null) {
            this.a = 1;
            return this;
        } else if (lhs.length != rhs.length) {
            if (lhs.length >= rhs.length) {
                i = 1;
            }
            this.a = i;
            return this;
        } else {
            for (int i2 = 0; i2 < lhs.length && this.a == 0; i2++) {
                e(lhs[i2], rhs[i2]);
            }
            return this;
        }
    }

    public a r(short[] lhs, short[] rhs) {
        if (this.a != 0 || lhs == rhs) {
            return this;
        }
        int i = -1;
        if (lhs == null) {
            this.a = -1;
            return this;
        } else if (rhs == null) {
            this.a = 1;
            return this;
        } else if (lhs.length != rhs.length) {
            if (lhs.length >= rhs.length) {
                i = 1;
            }
            this.a = i;
            return this;
        } else {
            for (int i2 = 0; i2 < lhs.length && this.a == 0; i2++) {
                i(lhs[i2], rhs[i2]);
            }
            return this;
        }
    }

    public a l(char[] lhs, char[] rhs) {
        if (this.a != 0 || lhs == rhs) {
            return this;
        }
        int i = -1;
        if (lhs == null) {
            this.a = -1;
            return this;
        } else if (rhs == null) {
            this.a = 1;
            return this;
        } else if (lhs.length != rhs.length) {
            if (lhs.length >= rhs.length) {
                i = 1;
            }
            this.a = i;
            return this;
        } else {
            for (int i2 = 0; i2 < lhs.length && this.a == 0; i2++) {
                b(lhs[i2], rhs[i2]);
            }
            return this;
        }
    }

    public a k(byte[] lhs, byte[] rhs) {
        if (this.a != 0 || lhs == rhs) {
            return this;
        }
        int i = -1;
        if (lhs == null) {
            this.a = -1;
            return this;
        } else if (rhs == null) {
            this.a = 1;
            return this;
        } else if (lhs.length != rhs.length) {
            if (lhs.length >= rhs.length) {
                i = 1;
            }
            this.a = i;
            return this;
        } else {
            for (int i2 = 0; i2 < lhs.length && this.a == 0; i2++) {
                a(lhs[i2], rhs[i2]);
            }
            return this;
        }
    }

    public a m(double[] lhs, double[] rhs) {
        if (this.a != 0 || lhs == rhs) {
            return this;
        }
        int i = -1;
        if (lhs == null) {
            this.a = -1;
            return this;
        } else if (rhs == null) {
            this.a = 1;
            return this;
        } else if (lhs.length != rhs.length) {
            if (lhs.length >= rhs.length) {
                i = 1;
            }
            this.a = i;
            return this;
        } else {
            for (int i2 = 0; i2 < lhs.length && this.a == 0; i2++) {
                c(lhs[i2], rhs[i2]);
            }
            return this;
        }
    }

    public a n(float[] lhs, float[] rhs) {
        if (this.a != 0 || lhs == rhs) {
            return this;
        }
        int i = -1;
        if (lhs == null) {
            this.a = -1;
            return this;
        } else if (rhs == null) {
            this.a = 1;
            return this;
        } else if (lhs.length != rhs.length) {
            if (lhs.length >= rhs.length) {
                i = 1;
            }
            this.a = i;
            return this;
        } else {
            for (int i2 = 0; i2 < lhs.length && this.a == 0; i2++) {
                d(lhs[i2], rhs[i2]);
            }
            return this;
        }
    }

    public a s(boolean[] lhs, boolean[] rhs) {
        if (this.a != 0 || lhs == rhs) {
            return this;
        }
        int i = -1;
        if (lhs == null) {
            this.a = -1;
            return this;
        } else if (rhs == null) {
            this.a = 1;
            return this;
        } else if (lhs.length != rhs.length) {
            if (lhs.length >= rhs.length) {
                i = 1;
            }
            this.a = i;
            return this;
        } else {
            for (int i2 = 0; i2 < lhs.length && this.a == 0; i2++) {
                j(lhs[i2], rhs[i2]);
            }
            return this;
        }
    }

    public int u() {
        return this.a;
    }
}
