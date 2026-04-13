package org.glassfish.tyrus.core.uri.internal;

import java.util.NoSuchElementException;

public final class CharacterIterator {
    private int pos = -1;
    private String s;

    public CharacterIterator(String s2) {
        this.s = s2;
    }

    public boolean hasNext() {
        return this.pos < this.s.length() - 1;
    }

    public char next() {
        if (hasNext()) {
            String str = this.s;
            int i = this.pos + 1;
            this.pos = i;
            return str.charAt(i);
        }
        throw new NoSuchElementException();
    }

    public char peek() {
        if (hasNext()) {
            return this.s.charAt(this.pos + 1);
        }
        throw new NoSuchElementException();
    }

    public int pos() {
        return this.pos;
    }

    public String getInput() {
        return this.s;
    }

    public void setPosition(int newPosition) {
        if (newPosition <= this.s.length() - 1) {
            this.pos = newPosition;
            return;
        }
        throw new IndexOutOfBoundsException("Given position " + newPosition + " is outside the input string range.");
    }

    public char current() {
        int i = this.pos;
        if (i != -1) {
            return this.s.charAt(i);
        }
        throw new IllegalStateException("Iterator not used yet.");
    }
}
