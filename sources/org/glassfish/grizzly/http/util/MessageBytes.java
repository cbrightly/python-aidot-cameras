package org.glassfish.grizzly.http.util;

import java.io.Serializable;
import java.nio.charset.Charset;

public final class MessageBytes implements Cloneable, Serializable {
    public static final int T_BYTES = 2;
    public static final int T_CHARS = 3;
    public static final int T_NULL = 0;
    public static final int T_STR = 1;
    private static MessageBytesFactory factory = new MessageBytesFactory();
    private final ByteChunk byteC = new ByteChunk();
    private boolean caseSensitive = true;
    private final CharChunk charC = new CharChunk();
    private boolean hasHashCode = false;
    private boolean hasIntValue = false;
    private boolean hasLongValue = false;
    private boolean hasStrValue = false;
    private int hashCode = 0;
    private int intValue;
    private long longValue;
    private String strValue;
    private int type = 0;

    public static MessageBytes newInstance() {
        return factory.newInstance();
    }

    public void setCaseSenitive(boolean b) {
        this.caseSensitive = b;
    }

    public MessageBytes getClone() {
        try {
            return (MessageBytes) clone();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isNull() {
        return this.byteC.isNull() && this.charC.isNull() && !this.hasStrValue;
    }

    public void recycle() {
        this.type = 0;
        this.byteC.recycle();
        this.charC.recycle();
        this.strValue = null;
        this.caseSensitive = true;
        this.hasStrValue = false;
        this.hasHashCode = false;
        this.hasIntValue = false;
        this.hasLongValue = false;
    }

    public void setBytes(byte[] b, int off, int len) {
        this.byteC.setBytes(b, off, len);
        this.type = 2;
        this.hasStrValue = false;
        this.hasHashCode = false;
        this.hasIntValue = false;
        this.hasLongValue = false;
    }

    public void setCharset(Charset enc) {
        if (!this.byteC.isNull()) {
            this.charC.recycle();
            this.hasStrValue = false;
        }
        this.byteC.setCharset(enc);
    }

    public void setChars(char[] c, int off, int len) {
        this.charC.setChars(c, off, len);
        this.type = 3;
        this.hasStrValue = false;
        this.hasHashCode = false;
        this.hasIntValue = false;
        this.hasLongValue = false;
    }

    public void resetStringValue() {
        if (this.type != 1) {
            this.hasStrValue = false;
            this.strValue = null;
        }
    }

    public void setString(String s) {
        this.strValue = s;
        this.hasHashCode = false;
        this.hasIntValue = false;
        this.hasLongValue = false;
        if (s == null) {
            this.hasStrValue = false;
            this.type = 0;
            return;
        }
        this.hasStrValue = true;
        this.type = 1;
    }

    public String toString() {
        if (this.hasStrValue) {
            return this.strValue;
        }
        this.hasStrValue = true;
        switch (this.type) {
            case 2:
                String byteChunk = this.byteC.toString();
                this.strValue = byteChunk;
                return byteChunk;
            case 3:
                String charChunk = this.charC.toString();
                this.strValue = charChunk;
                return charChunk;
            default:
                return "";
        }
    }

    public int getType() {
        return this.type;
    }

    public ByteChunk getByteChunk() {
        return this.byteC;
    }

    public CharChunk getCharChunk() {
        return this.charC;
    }

    public String getString() {
        return this.strValue;
    }

    public void toBytes() {
        if (!this.byteC.isNull()) {
            this.type = 2;
            return;
        }
        toString();
        this.type = 2;
        byte[] bb = this.strValue.getBytes(this.byteC.getCharset());
        this.byteC.setBytes(bb, 0, bb.length);
    }

    public void toChars() {
        if (!this.charC.isNull()) {
            this.type = 3;
            return;
        }
        toString();
        this.type = 3;
        char[] cc = this.strValue.toCharArray();
        this.charC.setChars(cc, 0, cc.length);
    }

    public int getLength() {
        int i = this.type;
        if (i == 2) {
            return this.byteC.getLength();
        }
        if (i == 3) {
            return this.charC.getLength();
        }
        if (i == 1) {
            return this.strValue.length();
        }
        toString();
        String str = this.strValue;
        if (str == null) {
            return 0;
        }
        return str.length();
    }

    public boolean equals(String s) {
        if (s == null) {
            return false;
        }
        if (!this.caseSensitive) {
            return equalsIgnoreCase(s);
        }
        switch (this.type) {
            case 1:
                String str = this.strValue;
                if (str == null || !str.equals(s)) {
                    return false;
                }
                return true;
            case 2:
                return this.byteC.equals(s);
            case 3:
                return this.charC.equals((CharSequence) s);
            default:
                return false;
        }
    }

    public boolean equalsIgnoreCase(String s) {
        if (s == null) {
            return false;
        }
        switch (this.type) {
            case 1:
                String str = this.strValue;
                if (str == null || !str.equalsIgnoreCase(s)) {
                    return false;
                }
                return true;
            case 2:
                return this.byteC.equalsIgnoreCase(s);
            case 3:
                return this.charC.equalsIgnoreCase((CharSequence) s);
            default:
                return false;
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof MessageBytes)) {
            return false;
        }
        MessageBytes mb = (MessageBytes) obj;
        int i = this.type;
        switch (i) {
            case 1:
                return mb.equals(this.strValue);
            default:
                int i2 = mb.type;
                if (i2 != 3 && i2 != 2) {
                    return equals(mb.toString());
                }
                if (i2 == 3 && i == 3) {
                    return this.charC.equals(mb.charC);
                }
                if (i2 == 2 && i == 2) {
                    return this.byteC.equals(mb.byteC);
                }
                if (i2 == 3 && i == 2) {
                    return this.byteC.equals(mb.charC);
                }
                if (i2 == 2 && i == 3) {
                    return mb.byteC.equals(this.charC);
                }
                return true;
        }
    }

    public boolean startsWith(String s) {
        switch (this.type) {
            case 1:
                return this.strValue.startsWith(s);
            case 2:
                return this.byteC.startsWith(s);
            case 3:
                return this.charC.startsWith(s);
            default:
                return false;
        }
    }

    public boolean startsWithIgnoreCase(String s, int pos) {
        switch (this.type) {
            case 1:
                String str = this.strValue;
                if (str == null || str.length() < s.length() + pos) {
                    return false;
                }
                for (int i = 0; i < s.length(); i++) {
                    if (Ascii.toLower((int) s.charAt(i)) != Ascii.toLower((int) this.strValue.charAt(pos + i))) {
                        return false;
                    }
                }
                return true;
            case 2:
                return this.byteC.startsWithIgnoreCase(s, pos);
            case 3:
                return this.charC.startsWithIgnoreCase(s, pos);
            default:
                return false;
        }
    }

    public int hashCode() {
        int code;
        if (this.hasHashCode) {
            return this.hashCode;
        }
        if (this.caseSensitive) {
            code = hash();
        } else {
            code = hashIgnoreCase();
        }
        this.hashCode = code;
        this.hasHashCode = true;
        return code;
    }

    private int hash() {
        int code = 0;
        switch (this.type) {
            case 1:
                for (int i = 0; i < this.strValue.length(); i++) {
                    code = (code * 37) + this.strValue.charAt(i);
                }
                return code;
            case 2:
                return this.byteC.hash();
            case 3:
                return this.charC.hash();
            default:
                return 0;
        }
    }

    private int hashIgnoreCase() {
        int code = 0;
        switch (this.type) {
            case 1:
                for (int i = 0; i < this.strValue.length(); i++) {
                    code = (code * 37) + Ascii.toLower((int) this.strValue.charAt(i));
                }
                return code;
            case 2:
                return this.byteC.hashIgnoreCase();
            case 3:
                return this.charC.hashIgnoreCase();
            default:
                return 0;
        }
    }

    public int indexOf(char c) {
        return indexOf(c, 0);
    }

    public int indexOf(String s, int starting) {
        toString();
        return this.strValue.indexOf(s, starting);
    }

    public int indexOf(String s) {
        return indexOf(s, 0);
    }

    public int indexOfIgnoreCase(String s, int starting) {
        toString();
        return this.strValue.toUpperCase().indexOf(s.toUpperCase(), starting);
    }

    public int indexOf(char c, int starting) {
        switch (this.type) {
            case 1:
                return this.strValue.indexOf(c, starting);
            case 2:
                return this.byteC.indexOf(c, starting);
            case 3:
                return this.charC.indexOf(c, starting);
            default:
                return -1;
        }
    }

    public void duplicate(MessageBytes src) {
        recycle();
        switch (src.getType()) {
            case 1:
                this.type = 1;
                setString(src.getString());
                return;
            case 2:
                this.type = 2;
                ByteChunk bc = src.getByteChunk();
                this.byteC.allocate(bc.getLength() * 2, -1);
                this.byteC.append(bc);
                return;
            case 3:
                this.type = 3;
                CharChunk cc = src.getCharChunk();
                this.charC.allocate(cc.getLength() * 2, -1);
                this.charC.append(cc);
                return;
            default:
                return;
        }
    }

    public void setInt(int i) {
        this.byteC.allocate(16, 32);
        int current = i;
        byte[] buf = this.byteC.getBuffer();
        int start = 0;
        int end = 0;
        if (i == 0) {
            buf[0] = 48;
            end = 0 + 1;
        }
        if (i < 0) {
            current = -i;
            buf[end] = 45;
            end++;
        }
        while (current > 0) {
            int digit = current % 10;
            current /= 10;
            buf[end] = HexUtils.HEX[digit];
            end++;
        }
        this.byteC.setStart(0);
        this.byteC.setEnd(end);
        if (i < 0) {
            start = 0 + 1;
        }
        for (int end2 = end - 1; end2 > start; end2--) {
            byte temp = buf[start];
            buf[start] = buf[end2];
            buf[end2] = temp;
            start++;
        }
        this.intValue = i;
        this.hasStrValue = false;
        this.hasHashCode = false;
        this.hasIntValue = true;
        this.hasLongValue = false;
        this.type = 2;
    }

    public void setLong(long l) {
        this.byteC.allocate(32, 64);
        long current = l;
        byte[] buf = this.byteC.getBuffer();
        int start = 0;
        int end = 0;
        if (l == 0) {
            buf[0] = 48;
            end = 0 + 1;
        }
        if (l < 0) {
            current = -l;
            buf[end] = 45;
            end++;
        }
        while (current > 0) {
            current /= 10;
            buf[end] = HexUtils.HEX[(int) (current % 10)];
            end++;
        }
        this.byteC.setStart(0);
        this.byteC.setEnd(end);
        if (l < 0) {
            start = 0 + 1;
        }
        for (int end2 = end - 1; end2 > start; end2--) {
            byte temp = buf[start];
            buf[start] = buf[end2];
            buf[end2] = temp;
            start++;
        }
        this.longValue = l;
        this.hasStrValue = false;
        this.hasHashCode = false;
        this.hasIntValue = false;
        this.hasLongValue = true;
        this.type = 2;
    }

    public int getInt() {
        if (this.hasIntValue) {
            return this.intValue;
        }
        switch (this.type) {
            case 2:
                this.intValue = this.byteC.getInt();
                break;
            default:
                this.intValue = Integer.parseInt(toString());
                break;
        }
        this.hasIntValue = true;
        return this.intValue;
    }

    public long getLong() {
        if (this.hasLongValue) {
            return this.longValue;
        }
        switch (this.type) {
            case 2:
                this.longValue = this.byteC.getLong();
                break;
            default:
                this.longValue = Long.parseLong(toString());
                break;
        }
        this.hasLongValue = true;
        return this.longValue;
    }

    public static void setFactory(MessageBytesFactory mbf) {
        factory = mbf;
    }

    public static class MessageBytesFactory {
        protected MessageBytesFactory() {
        }

        public MessageBytes newInstance() {
            return new MessageBytes();
        }
    }
}
