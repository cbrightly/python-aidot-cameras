package org.json;

import io.netty.util.internal.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class JSONTokener {
    private long character;
    private long characterPreviousLine;
    private boolean eof;
    private long index;
    private long line;
    private char previous;
    private final Reader reader;
    private boolean usePrevious;

    public JSONTokener(Reader reader2) {
        this.reader = reader2.markSupported() ? reader2 : new BufferedReader(reader2);
        this.eof = false;
        this.usePrevious = false;
        this.previous = 0;
        this.index = 0;
        this.character = 1;
        this.characterPreviousLine = 0;
        this.line = 1;
    }

    public JSONTokener(InputStream inputStream) {
        this((Reader) new InputStreamReader(inputStream));
    }

    public JSONTokener(String s) {
        this((Reader) new StringReader(s));
    }

    public void back() {
        if (this.usePrevious || this.index <= 0) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        decrementIndexes();
        this.usePrevious = true;
        this.eof = false;
    }

    private void decrementIndexes() {
        this.index--;
        char c = this.previous;
        if (c == 13 || c == 10) {
            this.line--;
            this.character = this.characterPreviousLine;
            return;
        }
        long j = this.character;
        if (j > 0) {
            this.character = j - 1;
        }
    }

    public static int dehexchar(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - '7';
        }
        if (c < 'a' || c > 'f') {
            return -1;
        }
        return c - 'W';
    }

    public boolean end() {
        return this.eof && !this.usePrevious;
    }

    public boolean more() {
        if (this.usePrevious) {
            return true;
        }
        try {
            this.reader.mark(1);
            try {
                if (this.reader.read() <= 0) {
                    this.eof = true;
                    return false;
                }
                this.reader.reset();
                return true;
            } catch (IOException e) {
                throw new JSONException("Unable to read the next character from the stream", e);
            }
        } catch (IOException e2) {
            throw new JSONException("Unable to preserve stream position", e2);
        }
    }

    public char next() {
        int c;
        if (this.usePrevious) {
            this.usePrevious = false;
            c = this.previous;
        } else {
            try {
                c = this.reader.read();
            } catch (IOException exception) {
                throw new JSONException((Throwable) exception);
            }
        }
        if (c <= 0) {
            this.eof = true;
            return 0;
        }
        incrementIndexes(c);
        char c2 = (char) c;
        this.previous = c2;
        return c2;
    }

    private void incrementIndexes(int c) {
        if (c > 0) {
            this.index++;
            if (c == 13) {
                this.line++;
                this.characterPreviousLine = this.character;
                this.character = 0;
            } else if (c == 10) {
                if (this.previous != 13) {
                    this.line++;
                    this.characterPreviousLine = this.character;
                }
                this.character = 0;
            } else {
                this.character++;
            }
        }
    }

    public char next(char c) {
        char n = next();
        if (n == c) {
            return n;
        }
        if (n > 0) {
            throw syntaxError("Expected '" + c + "' and instead saw '" + n + "'");
        }
        throw syntaxError("Expected '" + c + "' and instead saw ''");
    }

    public String next(int n) {
        if (n == 0) {
            return "";
        }
        char[] chars = new char[n];
        int pos = 0;
        while (pos < n) {
            chars[pos] = next();
            if (!end()) {
                pos++;
            } else {
                throw syntaxError("Substring bounds error");
            }
        }
        return new String(chars);
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public char nextClean() {
        /*
            r2 = this;
        L_0x0000:
            char r0 = r2.next()
            if (r0 == 0) goto L_0x000c
            r1 = 32
            if (r0 <= r1) goto L_0x000b
            goto L_0x000c
        L_0x000b:
            goto L_0x0000
        L_0x000c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONTokener.nextClean():char");
    }

    public String nextString(char quote) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            char c = next();
            switch (c) {
                case 0:
                case 10:
                case 13:
                    throw syntaxError("Unterminated string");
                case '\\':
                    char c2 = next();
                    switch (c2) {
                        case '\"':
                        case '\'':
                        case '/':
                        case '\\':
                            sb.append(c2);
                            break;
                        case 'b':
                            sb.append(8);
                            break;
                        case 'f':
                            sb.append(12);
                            break;
                        case 'n':
                            sb.append(10);
                            break;
                        case 'r':
                            sb.append(StringUtil.CARRIAGE_RETURN);
                            break;
                        case 't':
                            sb.append(9);
                            break;
                        case 'u':
                            try {
                                sb.append((char) Integer.parseInt(next(4), 16));
                                break;
                            } catch (NumberFormatException e) {
                                throw syntaxError("Illegal escape.", e);
                            }
                        default:
                            throw syntaxError("Illegal escape.");
                    }
                default:
                    if (c != quote) {
                        sb.append(c);
                        break;
                    } else {
                        return sb.toString();
                    }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String nextTo(char r4) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
        L_0x0005:
            char r1 = r3.next()
            if (r1 == r4) goto L_0x001a
            if (r1 == 0) goto L_0x001a
            r2 = 10
            if (r1 == r2) goto L_0x001a
            r2 = 13
            if (r1 != r2) goto L_0x0016
            goto L_0x001a
        L_0x0016:
            r0.append(r1)
            goto L_0x0005
        L_0x001a:
            if (r1 == 0) goto L_0x001f
            r3.back()
        L_0x001f:
            java.lang.String r2 = r0.toString()
            java.lang.String r2 = r2.trim()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONTokener.nextTo(char):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0020  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String nextTo(java.lang.String r4) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
        L_0x0005:
            char r1 = r3.next()
            int r2 = r4.indexOf(r1)
            if (r2 >= 0) goto L_0x001e
            if (r1 == 0) goto L_0x001e
            r2 = 10
            if (r1 == r2) goto L_0x001e
            r2 = 13
            if (r1 != r2) goto L_0x001a
            goto L_0x001e
        L_0x001a:
            r0.append(r1)
            goto L_0x0005
        L_0x001e:
            if (r1 == 0) goto L_0x0023
            r3.back()
        L_0x0023:
            java.lang.String r2 = r0.toString()
            java.lang.String r2 = r2.trim()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONTokener.nextTo(java.lang.String):java.lang.String");
    }

    public Object nextValue() {
        char c = nextClean();
        switch (c) {
            case '\"':
            case '\'':
                return nextString(c);
            case '[':
                back();
                return new JSONArray(this);
            case '{':
                back();
                return new JSONObject(this);
            default:
                StringBuilder sb = new StringBuilder();
                while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
                    sb.append(c);
                    c = next();
                }
                if (!this.eof) {
                    back();
                }
                String string = sb.toString().trim();
                if (!"".equals(string)) {
                    return JSONObject.stringToValue(string);
                }
                throw syntaxError("Missing value");
        }
    }

    public char skipTo(char to) {
        char c;
        try {
            long startIndex = this.index;
            long startCharacter = this.character;
            long startLine = this.line;
            this.reader.mark(1000000);
            do {
                c = next();
                if (c == 0) {
                    this.reader.reset();
                    this.index = startIndex;
                    this.character = startCharacter;
                    this.line = startLine;
                    return 0;
                }
            } while (c != to);
            this.reader.mark(1);
            back();
            return c;
        } catch (IOException exception) {
            throw new JSONException((Throwable) exception);
        }
    }

    public JSONException syntaxError(String message) {
        return new JSONException(message + toString());
    }

    public JSONException syntaxError(String message, Throwable causedBy) {
        return new JSONException(message + toString(), causedBy);
    }

    public String toString() {
        return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
    }
}
