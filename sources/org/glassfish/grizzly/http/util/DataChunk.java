package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.nio.charset.Charset;
import org.glassfish.grizzly.Buffer;

public class DataChunk implements Chunk {
    final BufferChunk bufferChunk;
    final ByteChunk byteChunk;
    final CharChunk charChunk;
    String stringValue;
    Type type;

    public enum Type {
        None,
        Bytes,
        Buffer,
        Chars,
        String
    }

    public static DataChunk newInstance() {
        return newInstance(new ByteChunk(), new BufferChunk(), new CharChunk(), (String) null);
    }

    public static DataChunk newInstance(ByteChunk byteChunk2, BufferChunk bufferChunk2, CharChunk charChunk2, String stringValue2) {
        return new DataChunk(byteChunk2, bufferChunk2, charChunk2, stringValue2);
    }

    protected DataChunk() {
        this(new ByteChunk(), new BufferChunk(), new CharChunk(), (String) null);
    }

    protected DataChunk(ByteChunk byteChunk2, BufferChunk bufferChunk2, CharChunk charChunk2, String stringValue2) {
        this.type = Type.None;
        this.byteChunk = byteChunk2;
        this.bufferChunk = bufferChunk2;
        this.charChunk = charChunk2;
        this.stringValue = stringValue2;
    }

    public DataChunk toImmutable() {
        return new Immutable(this);
    }

    public Type getType() {
        return this.type;
    }

    public void set(DataChunk value) {
        if (value != null) {
            switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[value.getType().ordinal()]) {
                case 1:
                    ByteChunk anotherByteChunk = value.byteChunk;
                    setBytesInternal(anotherByteChunk.getBytes(), anotherByteChunk.getStart(), anotherByteChunk.getEnd());
                    return;
                case 2:
                    BufferChunk anotherBufferChunk = value.bufferChunk;
                    setBufferInternal(anotherBufferChunk.getBuffer(), anotherBufferChunk.getStart(), anotherBufferChunk.getEnd());
                    return;
                case 3:
                    setStringInternal(value.stringValue);
                    return;
                case 4:
                    CharChunk anotherCharChunk = value.charChunk;
                    setCharsInternal(anotherCharChunk.getChars(), anotherCharChunk.getStart(), anotherCharChunk.getLimit());
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: org.glassfish.grizzly.http.util.DataChunk$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type = iArr;
            try {
                iArr[Type.Bytes.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[Type.Buffer.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[Type.String.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[Type.Chars.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void set(DataChunk value, int start, int end) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[value.getType().ordinal()]) {
            case 1:
                setBytesInternal(value.byteChunk.getBytes(), start, end);
                return;
            case 2:
                setBufferInternal(value.bufferChunk.getBuffer(), start, end);
                return;
            case 3:
                setStringInternal(value.stringValue.substring(start, end));
                return;
            case 4:
                setCharsInternal(value.charChunk.getChars(), start, end);
                return;
            default:
                return;
        }
    }

    public void notifyDirectUpdate() {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                this.byteChunk.notifyDirectUpdate();
                return;
            case 2:
                this.bufferChunk.notifyDirectUpdate();
                return;
            case 4:
                this.charChunk.notifyDirectUpdate();
                return;
            default:
                return;
        }
    }

    public BufferChunk getBufferChunk() {
        return this.bufferChunk;
    }

    public void setBuffer(Buffer buffer, int position, int limit) {
        setBufferInternal(buffer, position, limit);
    }

    public void setBuffer(Buffer buffer) {
        setBufferInternal(buffer, buffer.position(), buffer.limit());
    }

    public CharChunk getCharChunk() {
        return this.charChunk;
    }

    public void setChars(char[] chars, int position, int limit) {
        setCharsInternal(chars, position, limit);
    }

    public ByteChunk getByteChunk() {
        return this.byteChunk;
    }

    public void setBytes(byte[] bytes) {
        setBytesInternal(bytes, 0, bytes.length);
    }

    public void setBytes(byte[] bytes, int position, int limit) {
        setBytesInternal(bytes, position, limit);
    }

    public void setString(String string) {
        setStringInternal(string);
    }

    public void trimLeft() {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[getType().ordinal()]) {
            case 1:
                getByteChunk().trimLeft();
                return;
            case 2:
                getBufferChunk().trimLeft();
                return;
            case 4:
                getCharChunk().trimLeft();
                return;
            default:
                return;
        }
    }

    public void duplicate(DataChunk src) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[src.getType().ordinal()]) {
            case 1:
                ByteChunk bc = src.getByteChunk();
                this.byteChunk.allocate(bc.getLength() * 2, -1);
                try {
                    this.byteChunk.append(bc);
                } catch (IOException e) {
                }
                switchToByteChunk();
                return;
            case 2:
                BufferChunk bc2 = src.getBufferChunk();
                this.bufferChunk.allocate(bc2.getLength() * 2);
                this.bufferChunk.append(bc2);
                switchToBufferChunk();
                return;
            case 3:
                setString(src.toString());
                return;
            case 4:
                CharChunk cc = src.getCharChunk();
                this.charChunk.allocate(cc.getLength() * 2, -1);
                try {
                    this.charChunk.append(cc);
                } catch (IOException e2) {
                }
                switchToCharChunk();
                return;
            default:
                recycle();
                return;
        }
    }

    public void toChars(Charset charset) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                this.charChunk.set(this.byteChunk, charset);
                setChars(this.charChunk.getChars(), this.charChunk.getStart(), this.charChunk.getEnd());
                return;
            case 2:
                this.charChunk.set(this.bufferChunk, charset);
                setChars(this.charChunk.getChars(), this.charChunk.getStart(), this.charChunk.getEnd());
                return;
            case 3:
                this.charChunk.recycle();
                try {
                    this.charChunk.append(this.stringValue);
                    setChars(this.charChunk.getChars(), this.charChunk.getStart(), this.charChunk.getEnd());
                    return;
                } catch (IOException e) {
                    throw new IllegalStateException("Unexpected exception");
                }
            case 4:
                return;
            default:
                this.charChunk.recycle();
                return;
        }
    }

    public String toString() {
        return toString((Charset) null);
    }

    public String toString(Charset charset) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.toString(charset);
            case 2:
                return this.bufferChunk.toString(charset);
            case 3:
                return this.stringValue;
            case 4:
                return this.charChunk.toString();
            default:
                return null;
        }
    }

    public int getLength() {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.getLength();
            case 2:
                return this.bufferChunk.getLength();
            case 3:
                return this.stringValue.length();
            case 4:
                return this.charChunk.getLength();
            default:
                return 0;
        }
    }

    public int getStart() {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.getStart();
            case 2:
                return this.bufferChunk.getStart();
            case 4:
                return this.charChunk.getStart();
            default:
                return 0;
        }
    }

    public void setStart(int start) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                this.byteChunk.setStart(start);
                return;
            case 2:
                this.bufferChunk.setStart(start);
                return;
            case 4:
                this.charChunk.setStart(start);
                return;
            default:
                return;
        }
    }

    public int getEnd() {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.getEnd();
            case 2:
                return this.bufferChunk.getEnd();
            case 4:
                return this.charChunk.getEnd();
            default:
                return this.stringValue.length();
        }
    }

    public void setEnd(int end) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                this.byteChunk.setEnd(end);
                return;
            case 2:
                this.bufferChunk.setEnd(end);
                return;
            case 4:
                this.charChunk.setEnd(end);
                return;
            default:
                return;
        }
    }

    public final int indexOf(char c, int fromIndex) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.indexOf(c, fromIndex);
            case 2:
                return this.bufferChunk.indexOf(c, fromIndex);
            case 3:
                return this.stringValue.indexOf(c, fromIndex);
            case 4:
                return this.charChunk.indexOf(c, fromIndex);
            default:
                return -1;
        }
    }

    public final int indexOf(String s, int fromIndex) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.indexOf(s, fromIndex);
            case 2:
                return this.bufferChunk.indexOf(s, fromIndex);
            case 3:
                return this.stringValue.indexOf(s, fromIndex);
            case 4:
                return this.charChunk.indexOf(s, fromIndex);
            default:
                return -1;
        }
    }

    public final void delete(int from, int to) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                this.byteChunk.delete(from, to);
                return;
            case 2:
                this.bufferChunk.delete(from, to);
                return;
            case 3:
                StringBuilder sb = new StringBuilder();
                sb.append(this.stringValue.substring(0, from));
                String str = this.stringValue;
                sb.append(str.substring(to, str.length()));
                this.stringValue = sb.toString();
                return;
            case 4:
                this.charChunk.delete(from, to);
                return;
            default:
                return;
        }
    }

    public String toString(int start, int end) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.toString(start, end);
            case 2:
                return this.bufferChunk.toString(start, end);
            case 3:
                return (start == 0 && end == this.stringValue.length()) ? this.stringValue : this.stringValue.substring(start, end);
            case 4:
                return this.charChunk.toString(start, end);
            default:
                return null;
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof DataChunk)) {
            return false;
        }
        DataChunk anotherChunk = (DataChunk) object;
        if (!isNull() && !anotherChunk.isNull()) {
            switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
                case 1:
                    return anotherChunk.equals(this.byteChunk);
                case 2:
                    return anotherChunk.equals(this.bufferChunk);
                case 3:
                    return anotherChunk.equals(this.stringValue);
                case 4:
                    return anotherChunk.equals(this.charChunk);
                default:
                    return false;
            }
        } else if (isNull() == anotherChunk.isNull()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(String s) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.equals(s);
            case 2:
                return this.bufferChunk.equals((CharSequence) s);
            case 3:
                return this.stringValue.equals(s);
            case 4:
                return this.charChunk.equals((CharSequence) s);
            default:
                return false;
        }
    }

    public boolean equals(ByteChunk byteChunkToCheck) {
        return equals(byteChunkToCheck.getBuffer(), byteChunkToCheck.getStart(), byteChunkToCheck.getLength());
    }

    public boolean equals(BufferChunk bufferChunkToCheck) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return bufferChunkToCheck.equals(this.byteChunk.getBuffer(), this.byteChunk.getStart(), this.byteChunk.getLength());
            case 2:
                return bufferChunkToCheck.equals((Object) this.bufferChunk);
            case 3:
                return bufferChunkToCheck.equals((CharSequence) this.stringValue);
            case 4:
                return bufferChunkToCheck.equals(this.charChunk.getBuffer(), this.charChunk.getStart(), this.charChunk.getLength());
            default:
                return false;
        }
    }

    public boolean equals(CharChunk charChunkToCheck) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return charChunkToCheck.equals(this.byteChunk.getBuffer(), this.byteChunk.getStart(), this.byteChunk.getLength());
            case 2:
                return this.bufferChunk.equals(charChunkToCheck.getBuffer(), charChunkToCheck.getStart(), charChunkToCheck.getLength());
            case 3:
                return charChunkToCheck.equals((CharSequence) this.stringValue);
            case 4:
                return this.charChunk.equals(charChunkToCheck.getBuffer(), charChunkToCheck.getStart(), charChunkToCheck.getLength());
            default:
                return false;
        }
    }

    public boolean equals(byte[] bytes) {
        return equals(bytes, 0, bytes.length);
    }

    public boolean equals(byte[] bytes, int start, int len) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.equals(bytes, start, len);
            case 2:
                return this.bufferChunk.equals(bytes, start, len);
            case 3:
                return ByteChunk.equals(bytes, start, len, this.stringValue);
            case 4:
                return this.charChunk.equals(bytes, start, len);
            default:
                return false;
        }
    }

    public boolean equalsIgnoreCase(Object object) {
        if (!(object instanceof DataChunk)) {
            return false;
        }
        DataChunk anotherChunk = (DataChunk) object;
        if (!isNull() && !anotherChunk.isNull()) {
            switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
                case 1:
                    return anotherChunk.equalsIgnoreCase(this.byteChunk);
                case 2:
                    return anotherChunk.equalsIgnoreCase(this.bufferChunk);
                case 3:
                    return anotherChunk.equalsIgnoreCase(this.stringValue);
                case 4:
                    return anotherChunk.equalsIgnoreCase(this.charChunk);
                default:
                    return false;
            }
        } else if (isNull() == anotherChunk.isNull()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equalsIgnoreCase(String s) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.equalsIgnoreCase(s);
            case 2:
                return this.bufferChunk.equalsIgnoreCase((CharSequence) s);
            case 3:
                return this.stringValue.equalsIgnoreCase(s);
            case 4:
                return this.charChunk.equalsIgnoreCase((CharSequence) s);
            default:
                return false;
        }
    }

    public boolean equalsIgnoreCase(ByteChunk byteChunkToCheck) {
        return equalsIgnoreCase(byteChunkToCheck.getBuffer(), byteChunkToCheck.getStart(), byteChunkToCheck.getLength());
    }

    public boolean equalsIgnoreCase(BufferChunk bufferChunkToCheck) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return bufferChunkToCheck.equalsIgnoreCase(this.byteChunk.getBuffer(), this.byteChunk.getStart(), this.byteChunk.getLength());
            case 2:
                return bufferChunkToCheck.equalsIgnoreCase((Object) this.bufferChunk);
            case 3:
                return bufferChunkToCheck.equalsIgnoreCase((CharSequence) this.stringValue);
            case 4:
                return bufferChunkToCheck.equalsIgnoreCase(this.charChunk.getBuffer(), this.charChunk.getStart(), this.charChunk.getLength());
            default:
                return false;
        }
    }

    public boolean equalsIgnoreCase(CharChunk charChunkToCheck) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return charChunkToCheck.equalsIgnoreCase(this.byteChunk.getBuffer(), this.byteChunk.getStart(), this.byteChunk.getLength());
            case 2:
                return this.bufferChunk.equalsIgnoreCase(charChunkToCheck.getBuffer(), charChunkToCheck.getStart(), charChunkToCheck.getLength());
            case 3:
                return charChunkToCheck.equalsIgnoreCase((CharSequence) this.stringValue);
            case 4:
                return this.charChunk.equalsIgnoreCase(charChunkToCheck.getBuffer(), charChunkToCheck.getStart(), charChunkToCheck.getLength());
            default:
                return false;
        }
    }

    public boolean equalsIgnoreCase(byte[] bytes) {
        return equalsIgnoreCase(bytes, 0, bytes.length);
    }

    public boolean equalsIgnoreCase(byte[] bytes, int start, int len) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.equalsIgnoreCase(bytes, start, len);
            case 2:
                return this.bufferChunk.equalsIgnoreCase(bytes, start, len);
            case 3:
                return ByteChunk.equalsIgnoreCase(bytes, start, len, this.stringValue);
            case 4:
                return this.charChunk.equalsIgnoreCase(bytes, start, len);
            default:
                return false;
        }
    }

    public int hashCode() {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.hash();
            case 2:
                return this.bufferChunk.hash();
            case 3:
                return this.stringValue.hashCode();
            case 4:
                return this.charChunk.hash();
            default:
                return 0;
        }
    }

    public final boolean equalsIgnoreCaseLowerCase(byte[] b) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.equalsIgnoreCaseLowerCase(b);
            case 2:
                return this.bufferChunk.equalsIgnoreCaseLowerCase(b);
            case 3:
                return equalsIgnoreCaseLowerCase(this.stringValue, b);
            case 4:
                return this.charChunk.equalsIgnoreCaseLowerCase(b);
            default:
                return false;
        }
    }

    public final boolean startsWith(String s, int pos) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.startsWith(s, pos);
            case 2:
                return this.bufferChunk.startsWith(s, pos);
            case 3:
                if (this.stringValue.length() < s.length() + pos) {
                    return false;
                }
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) != this.stringValue.charAt(pos + i)) {
                        return false;
                    }
                }
                return true;
            case 4:
                return this.charChunk.startsWith(s, pos);
            default:
                return false;
        }
    }

    public final boolean startsWithIgnoreCase(String s, int pos) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[this.type.ordinal()]) {
            case 1:
                return this.byteChunk.startsWithIgnoreCase(s, pos);
            case 2:
                return this.bufferChunk.startsWithIgnoreCase(s, pos);
            case 3:
                if (this.stringValue.length() < s.length() + pos) {
                    return false;
                }
                for (int i = 0; i < s.length(); i++) {
                    if (Ascii.toLower((int) s.charAt(i)) != Ascii.toLower((int) this.stringValue.charAt(pos + i))) {
                        return false;
                    }
                }
                return true;
            case 4:
                return this.charChunk.startsWithIgnoreCase(s, pos);
            default:
                return false;
        }
    }

    public final boolean isNull() {
        return this.type == Type.None || (this.byteChunk.isNull() && this.bufferChunk.isNull() && this.stringValue == null && this.charChunk.isNull());
    }

    /* access modifiers changed from: protected */
    public void resetBuffer() {
        this.bufferChunk.recycle();
    }

    /* access modifiers changed from: protected */
    public void resetCharChunk() {
        this.charChunk.recycle();
    }

    /* access modifiers changed from: protected */
    public void resetByteChunk() {
        this.byteChunk.recycleAndReset();
    }

    /* access modifiers changed from: protected */
    public void resetString() {
        this.stringValue = null;
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.stringValue = null;
        Type type2 = this.type;
        if (type2 == Type.Bytes) {
            this.byteChunk.recycleAndReset();
        } else if (type2 == Type.Buffer) {
            this.bufferChunk.recycle();
        } else if (type2 == Type.Chars) {
            this.charChunk.recycle();
        }
        this.type = Type.None;
    }

    public void recycle() {
        reset();
    }

    private static boolean equalsIgnoreCase(String s, byte[] b) {
        int len = b.length;
        if (s.length() != len) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (Ascii.toLower((int) s.charAt(i)) != Ascii.toLower((int) b[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean equalsIgnoreCaseLowerCase(String s, byte[] b) {
        int len = b.length;
        if (s.length() != len) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (Ascii.toLower((int) s.charAt(i)) != b[i]) {
                return false;
            }
        }
        return true;
    }

    private void setBytesInternal(byte[] array, int position, int limit) {
        this.byteChunk.setBytes(array, position, limit - position);
        switchToByteChunk();
    }

    private void setBufferInternal(Buffer buffer, int position, int limit) {
        this.bufferChunk.setBufferChunk(buffer, position, limit, limit);
        switchToBufferChunk();
    }

    private void setCharsInternal(char[] chars, int position, int limit) {
        this.charChunk.setChars(chars, position, limit - position);
        switchToCharChunk();
    }

    private void setStringInternal(String string) {
        this.stringValue = string;
        switchToString();
    }

    private void switchToByteChunk() {
        Type type2 = this.type;
        if (type2 == Type.Buffer) {
            resetBuffer();
        } else if (type2 == Type.Chars) {
            resetCharChunk();
        }
        resetString();
        this.type = Type.Bytes;
    }

    private void switchToBufferChunk() {
        Type type2 = this.type;
        if (type2 == Type.Bytes) {
            resetByteChunk();
        } else if (type2 == Type.Chars) {
            resetCharChunk();
        }
        resetString();
        this.type = Type.Buffer;
    }

    private void switchToCharChunk() {
        Type type2 = this.type;
        if (type2 == Type.Bytes) {
            resetByteChunk();
        } else if (type2 == Type.Buffer) {
            resetBuffer();
        }
        resetString();
        this.type = Type.Chars;
    }

    private void switchToString() {
        Type type2 = this.type;
        if (type2 == Type.Bytes) {
            resetByteChunk();
        } else if (type2 == Type.Chars) {
            resetCharChunk();
        } else if (type2 == Type.Buffer) {
            resetBuffer();
        }
        this.type = Type.String;
    }

    public static final class Immutable extends DataChunk {
        public Immutable(DataChunk original) {
            DataChunk.super.set(original);
        }

        public DataChunk toImmutable() {
            return this;
        }

        public void set(DataChunk value) {
        }

        public void setBuffer(Buffer buffer, int start, int end) {
        }

        public void setString(String string) {
        }

        public void setChars(char[] chars, int position, int limit) {
        }

        /* access modifiers changed from: protected */
        public void resetBuffer() {
        }

        /* access modifiers changed from: protected */
        public void resetString() {
        }

        /* access modifiers changed from: protected */
        public void resetCharChunk() {
        }

        /* access modifiers changed from: protected */
        public void reset() {
        }

        public void recycle() {
        }
    }
}
