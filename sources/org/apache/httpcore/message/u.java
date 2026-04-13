package org.apache.httpcore.message;

/* compiled from: ParserCursor */
public class u {
    private final int a;
    private final int b;
    private int c;

    public u(int lowerBound, int upperBound) {
        if (lowerBound < 0) {
            throw new IndexOutOfBoundsException("Lower bound cannot be negative");
        } else if (lowerBound <= upperBound) {
            this.a = lowerBound;
            this.b = upperBound;
            this.c = lowerBound;
        } else {
            throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
        }
    }

    public int c() {
        return this.b;
    }

    public int b() {
        return this.c;
    }

    public void d(int pos) {
        if (pos < this.a) {
            throw new IndexOutOfBoundsException("pos: " + pos + " < lowerBound: " + this.a);
        } else if (pos <= this.b) {
            this.c = pos;
        } else {
            throw new IndexOutOfBoundsException("pos: " + pos + " > upperBound: " + this.b);
        }
    }

    public boolean a() {
        return this.c >= this.b;
    }

    public String toString() {
        return '[' + Integer.toString(this.a) + '>' + Integer.toString(this.c) + '>' + Integer.toString(this.b) + ']';
    }
}
