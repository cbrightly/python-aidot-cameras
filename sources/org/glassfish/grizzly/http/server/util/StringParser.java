package org.glassfish.grizzly.http.server.util;

public final class StringParser {
    private char[] chars;
    private int index;
    private int length;
    private String string;

    public StringParser() {
        this((String) null);
    }

    public StringParser(String string2) {
        this.chars = null;
        this.index = 0;
        this.length = 0;
        this.string = null;
        setString(string2);
    }

    public int getIndex() {
        return this.index;
    }

    public int getLength() {
        return this.length;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String string2) {
        this.string = string2;
        if (string2 != null) {
            this.length = string2.length();
            this.chars = this.string.toCharArray();
        } else {
            this.length = 0;
            this.chars = new char[0];
        }
        reset();
    }

    public void advance() {
        int i = this.index;
        if (i < this.length) {
            this.index = i + 1;
        }
    }

    public String extract(int start) {
        if (start < 0 || start >= this.length) {
            return "";
        }
        return this.string.substring(start);
    }

    public String extract(int start, int end) {
        if (start < 0 || start >= end || end > this.length) {
            return "";
        }
        return this.string.substring(start, end);
    }

    public int findChar(char ch) {
        int i;
        while (true) {
            i = this.index;
            if (i >= this.length || ch == this.chars[i]) {
                return i;
            }
            this.index = i + 1;
        }
        return i;
    }

    public int findText() {
        while (true) {
            int i = this.index;
            if (i < this.length && isWhite(this.chars[i])) {
                this.index++;
            }
        }
        return this.index;
    }

    public int findWhite() {
        while (true) {
            int i = this.index;
            if (i < this.length && !isWhite(this.chars[i])) {
                this.index++;
            }
        }
        return this.index;
    }

    public void reset() {
        this.index = 0;
    }

    public int skipChar(char ch) {
        int i;
        while (true) {
            i = this.index;
            if (i >= this.length || ch != this.chars[i]) {
                return i;
            }
            this.index = i + 1;
        }
        return i;
    }

    public int skipText() {
        while (true) {
            int i = this.index;
            if (i < this.length && !isWhite(this.chars[i])) {
                this.index++;
            }
        }
        return this.index;
    }

    public int skipWhite() {
        while (true) {
            int i = this.index;
            if (i < this.length && isWhite(this.chars[i])) {
                this.index++;
            }
        }
        return this.index;
    }

    /* access modifiers changed from: protected */
    public boolean isWhite(char ch) {
        if (ch == ' ' || ch == 9 || ch == 13 || ch == 10) {
            return true;
        }
        return false;
    }
}
