package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.RyuDouble;
import com.alibaba.fastjson.util.RyuFloat;
import com.google.maps.android.BuildConfig;
import com.tencent.bugly.Bugly;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import org.glassfish.grizzly.http.server.util.MappingData;

public final class SerializeWriter extends Writer {
    private static int BUFFER_THRESHOLD;
    private static final char[] VALUE_FALSE = ":false".toCharArray();
    private static final char[] VALUE_TRUE = ":true".toCharArray();
    private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
    private static final ThreadLocal<byte[]> bytesBufLocal = new ThreadLocal<>();
    static final int nonDirectFeatures = (((((((((SerializerFeature.UseSingleQuotes.mask | 0) | SerializerFeature.BrowserCompatible.mask) | SerializerFeature.PrettyFormat.mask) | SerializerFeature.WriteEnumUsingToString.mask) | SerializerFeature.WriteNonStringValueAsString.mask) | SerializerFeature.WriteSlashAsSpecial.mask) | SerializerFeature.IgnoreErrorGetter.mask) | SerializerFeature.WriteClassName.mask) | SerializerFeature.NotWriteDefaultValue.mask);
    protected boolean beanToArray;
    protected boolean browserSecure;
    protected char[] buf;
    protected int count;
    protected boolean disableCircularReferenceDetect;
    protected int features;
    protected char keySeperator;
    protected int maxBufSize;
    protected boolean notWriteDefaultValue;
    protected boolean quoteFieldNames;
    protected long sepcialBits;
    protected boolean sortField;
    protected boolean useSingleQuotes;
    protected boolean writeDirect;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected boolean writeNonStringValueAsString;
    private final Writer writer;

    static {
        int serializer_buffer_threshold;
        BUFFER_THRESHOLD = 131072;
        try {
            String prop = IOUtils.getStringProperty("fastjson.serializer_buffer_threshold");
            if (prop != null && prop.length() > 0 && (serializer_buffer_threshold = Integer.parseInt(prop)) >= 64 && serializer_buffer_threshold <= 65536) {
                BUFFER_THRESHOLD = serializer_buffer_threshold * 1024;
            }
        } catch (Throwable th) {
        }
    }

    public SerializeWriter() {
        this((Writer) null);
    }

    public SerializeWriter(Writer writer2) {
        this(writer2, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);
    }

    public SerializeWriter(SerializerFeature... features2) {
        this((Writer) null, features2);
    }

    public SerializeWriter(Writer writer2, SerializerFeature... features2) {
        this(writer2, 0, features2);
    }

    public SerializeWriter(Writer writer2, int defaultFeatures, SerializerFeature... features2) {
        this.maxBufSize = -1;
        this.writer = writer2;
        ThreadLocal<char[]> threadLocal = bufLocal;
        char[] cArr = threadLocal.get();
        this.buf = cArr;
        if (cArr != null) {
            threadLocal.set((Object) null);
        } else {
            this.buf = new char[2048];
        }
        int featuresValue = defaultFeatures;
        for (SerializerFeature feature : features2) {
            featuresValue |= feature.getMask();
        }
        this.features = featuresValue;
        computeFeatures();
    }

    public int getMaxBufSize() {
        return this.maxBufSize;
    }

    public void setMaxBufSize(int maxBufSize2) {
        if (maxBufSize2 >= this.buf.length) {
            this.maxBufSize = maxBufSize2;
            return;
        }
        throw new JSONException("must > " + this.buf.length);
    }

    public int getBufferLength() {
        return this.buf.length;
    }

    public SerializeWriter(int initialSize) {
        this((Writer) null, initialSize);
    }

    public SerializeWriter(Writer writer2, int initialSize) {
        this.maxBufSize = -1;
        this.writer = writer2;
        if (initialSize > 0) {
            this.buf = new char[initialSize];
            computeFeatures();
            return;
        }
        throw new IllegalArgumentException("Negative initial size: " + initialSize);
    }

    public void config(SerializerFeature feature, boolean state) {
        if (state) {
            int mask = this.features | feature.getMask();
            this.features = mask;
            SerializerFeature serializerFeature = SerializerFeature.WriteEnumUsingToString;
            if (feature == serializerFeature) {
                this.features = mask & (~SerializerFeature.WriteEnumUsingName.getMask());
            } else if (feature == SerializerFeature.WriteEnumUsingName) {
                this.features = mask & (~serializerFeature.getMask());
            }
        } else {
            this.features &= ~feature.getMask();
        }
        computeFeatures();
    }

    /* access modifiers changed from: protected */
    public void computeFeatures() {
        int i = this.features;
        boolean z = true;
        boolean z2 = (SerializerFeature.QuoteFieldNames.mask & i) != 0;
        this.quoteFieldNames = z2;
        boolean z3 = (SerializerFeature.UseSingleQuotes.mask & i) != 0;
        this.useSingleQuotes = z3;
        this.sortField = (SerializerFeature.SortField.mask & i) != 0;
        this.disableCircularReferenceDetect = (SerializerFeature.DisableCircularReferenceDetect.mask & i) != 0;
        boolean z4 = (SerializerFeature.BeanToArray.mask & i) != 0;
        this.beanToArray = z4;
        this.writeNonStringValueAsString = (SerializerFeature.WriteNonStringValueAsString.mask & i) != 0;
        this.notWriteDefaultValue = (SerializerFeature.NotWriteDefaultValue.mask & i) != 0;
        boolean z5 = (SerializerFeature.WriteEnumUsingName.mask & i) != 0;
        this.writeEnumUsingName = z5;
        this.writeEnumUsingToString = (SerializerFeature.WriteEnumUsingToString.mask & i) != 0;
        this.writeDirect = z2 && (nonDirectFeatures & i) == 0 && (z4 || z5);
        this.keySeperator = z3 ? '\'' : StringUtil.DOUBLE_QUOTE;
        if ((SerializerFeature.BrowserSecure.mask & i) == 0) {
            z = false;
        }
        this.browserSecure = z;
        this.sepcialBits = z ? 5764610843043954687L : (i & SerializerFeature.WriteSlashAsSpecial.mask) != 0 ? 140758963191807L : 21474836479L;
    }

    public boolean isSortField() {
        return this.sortField;
    }

    public boolean isNotWriteDefaultValue() {
        return this.notWriteDefaultValue;
    }

    public boolean isEnabled(SerializerFeature feature) {
        return (this.features & feature.mask) != 0;
    }

    public boolean isEnabled(int feature) {
        return (this.features & feature) != 0;
    }

    public void write(int c) {
        int newcount = this.count + 1;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                flush();
                newcount = 1;
            }
        }
        this.buf[this.count] = (char) c;
        this.count = newcount;
    }

    public void write(char[] c, int off, int len) {
        if (off < 0 || off > c.length || len < 0 || off + len > c.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        } else if (len != 0) {
            int newcount = this.count + len;
            if (newcount > this.buf.length) {
                if (this.writer == null) {
                    expandCapacity(newcount);
                } else {
                    do {
                        char[] cArr = this.buf;
                        int length = cArr.length;
                        int i = this.count;
                        int rest = length - i;
                        System.arraycopy(c, off, cArr, i, rest);
                        this.count = this.buf.length;
                        flush();
                        len -= rest;
                        off += rest;
                    } while (len > this.buf.length);
                    newcount = len;
                }
            }
            System.arraycopy(c, off, this.buf, this.count, len);
            this.count = newcount;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0045, code lost:
        r0 = bufLocal;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void expandCapacity(int r7) {
        /*
            r6 = this;
            int r0 = r6.maxBufSize
            r1 = -1
            if (r0 == r1) goto L_0x002a
            if (r7 >= r0) goto L_0x0008
            goto L_0x002a
        L_0x0008:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "serialize exceeded MAX_OUTPUT_LENGTH="
            r1.append(r2)
            int r2 = r6.maxBufSize
            r1.append(r2)
            java.lang.String r2 = ", minimumCapacity="
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x002a:
            char[] r0 = r6.buf
            int r1 = r0.length
            int r2 = r0.length
            int r2 = r2 >> 1
            int r1 = r1 + r2
            int r1 = r1 + 1
            if (r1 >= r7) goto L_0x0036
            r1 = r7
        L_0x0036:
            char[] r2 = new char[r1]
            int r3 = r6.count
            r4 = 0
            java.lang.System.arraycopy(r0, r4, r2, r4, r3)
            char[] r0 = r6.buf
            int r0 = r0.length
            int r3 = BUFFER_THRESHOLD
            if (r0 >= r3) goto L_0x005a
            java.lang.ThreadLocal<char[]> r0 = bufLocal
            java.lang.Object r3 = r0.get()
            char[] r3 = (char[]) r3
            if (r3 == 0) goto L_0x0055
            int r4 = r3.length
            char[] r5 = r6.buf
            int r5 = r5.length
            if (r4 >= r5) goto L_0x005a
        L_0x0055:
            char[] r4 = r6.buf
            r0.set(r4)
        L_0x005a:
            r6.buf = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeWriter.expandCapacity(int):void");
    }

    public SerializeWriter append(CharSequence csq) {
        String s = csq == null ? BuildConfig.TRAVIS : csq.toString();
        write(s, 0, s.length());
        return this;
    }

    public SerializeWriter append(CharSequence csq, int start, int end) {
        String s = (csq == null ? BuildConfig.TRAVIS : csq).subSequence(start, end).toString();
        write(s, 0, s.length());
        return this;
    }

    public SerializeWriter append(char c) {
        write((int) c);
        return this;
    }

    public void write(String str, int off, int len) {
        int newcount = this.count + len;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                do {
                    char[] cArr = this.buf;
                    int length = cArr.length;
                    int i = this.count;
                    int rest = length - i;
                    str.getChars(off, off + rest, cArr, i);
                    this.count = this.buf.length;
                    flush();
                    len -= rest;
                    off += rest;
                } while (len > this.buf.length);
                newcount = len;
            }
        }
        str.getChars(off, off + len, this.buf, this.count);
        this.count = newcount;
    }

    public void writeTo(Writer out) {
        if (this.writer == null) {
            out.write(this.buf, 0, this.count);
            return;
        }
        throw new UnsupportedOperationException("writer not null");
    }

    public void writeTo(OutputStream out, String charsetName) {
        writeTo(out, Charset.forName(charsetName));
    }

    public void writeTo(OutputStream out, Charset charset) {
        writeToEx(out, charset);
    }

    public int writeToEx(OutputStream out, Charset charset) {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        } else if (charset == IOUtils.UTF8) {
            return encodeToUTF8(out);
        } else {
            byte[] bytes = new String(this.buf, 0, this.count).getBytes(charset);
            out.write(bytes);
            return bytes.length;
        }
    }

    public char[] toCharArray() {
        if (this.writer == null) {
            int i = this.count;
            char[] newValue = new char[i];
            System.arraycopy(this.buf, 0, newValue, 0, i);
            return newValue;
        }
        throw new UnsupportedOperationException("writer not null");
    }

    public char[] toCharArrayForSpringWebSocket() {
        if (this.writer == null) {
            int i = this.count;
            char[] newValue = new char[(i - 2)];
            System.arraycopy(this.buf, 1, newValue, 0, i - 2);
            return newValue;
        }
        throw new UnsupportedOperationException("writer not null");
    }

    public byte[] toBytes(String charsetName) {
        Charset charset;
        if (charsetName == null || "UTF-8".equals(charsetName)) {
            charset = IOUtils.UTF8;
        } else {
            charset = Charset.forName(charsetName);
        }
        return toBytes(charset);
    }

    public byte[] toBytes(Charset charset) {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        } else if (charset == IOUtils.UTF8) {
            return encodeToUTF8Bytes();
        } else {
            return new String(this.buf, 0, this.count).getBytes(charset);
        }
    }

    private int encodeToUTF8(OutputStream out) {
        int bytesLength = (int) (((double) this.count) * 3.0d);
        ThreadLocal<byte[]> threadLocal = bytesBufLocal;
        byte[] bytes = threadLocal.get();
        if (bytes == null) {
            bytes = new byte[8192];
            threadLocal.set(bytes);
        }
        byte[] bytesLocal = bytes;
        if (bytes.length < bytesLength) {
            bytes = new byte[bytesLength];
        }
        int position = IOUtils.encodeUTF8(this.buf, 0, this.count, bytes);
        out.write(bytes, 0, position);
        if (bytes != bytesLocal && bytes.length <= BUFFER_THRESHOLD) {
            threadLocal.set(bytes);
        }
        return position;
    }

    private byte[] encodeToUTF8Bytes() {
        int bytesLength = (int) (((double) this.count) * 3.0d);
        ThreadLocal<byte[]> threadLocal = bytesBufLocal;
        byte[] bytes = threadLocal.get();
        if (bytes == null) {
            bytes = new byte[8192];
            threadLocal.set(bytes);
        }
        byte[] bytesLocal = bytes;
        if (bytes.length < bytesLength) {
            bytes = new byte[bytesLength];
        }
        int position = IOUtils.encodeUTF8(this.buf, 0, this.count, bytes);
        byte[] copy = new byte[position];
        System.arraycopy(bytes, 0, copy, 0, position);
        if (bytes != bytesLocal && bytes.length <= BUFFER_THRESHOLD) {
            threadLocal.set(bytes);
        }
        return copy;
    }

    public int size() {
        return this.count;
    }

    public String toString() {
        return new String(this.buf, 0, this.count);
    }

    public void close() {
        if (this.writer != null && this.count > 0) {
            flush();
        }
        char[] cArr = this.buf;
        if (cArr.length <= BUFFER_THRESHOLD) {
            bufLocal.set(cArr);
        }
        this.buf = null;
    }

    public void write(String text) {
        if (text == null) {
            writeNull();
        } else {
            write(text, 0, text.length());
        }
    }

    public void writeInt(int i) {
        if (i == Integer.MIN_VALUE) {
            write("-2147483648");
            return;
        }
        int size = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int newcount = this.count + size;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                char[] chars = new char[size];
                IOUtils.getChars(i, size, chars);
                write(chars, 0, chars.length);
                return;
            }
        }
        IOUtils.getChars(i, newcount, this.buf);
        this.count = newcount;
    }

    public void writeByteArray(byte[] bytes) {
        byte[] bArr = bytes;
        if (isEnabled(SerializerFeature.WriteClassName.mask)) {
            writeHex(bytes);
            return;
        }
        int bytesLen = bArr.length;
        boolean z = this.useSingleQuotes;
        char quote = z ? '\'' : StringUtil.DOUBLE_QUOTE;
        if (bytesLen == 0) {
            write(z ? "''" : "\"\"");
            return;
        }
        char[] CA = IOUtils.CA;
        int eLen = (bytesLen / 3) * 3;
        int offset = this.count;
        int newcount = this.count + ((((bytesLen - 1) / 3) + 1) << 2) + 2;
        int i = 0;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write((int) quote);
                int i2 = 0;
                while (i2 < eLen) {
                    int s = i2 + 1;
                    int s2 = s + 1;
                    int i3 = ((bArr[i2] & 255) << MappingData.PATH) | ((bArr[s] & 255) << 8) | (bArr[s2] & 255);
                    write((int) CA[(i3 >>> 18) & 63]);
                    write((int) CA[(i3 >>> 12) & 63]);
                    write((int) CA[(i3 >>> 6) & 63]);
                    write((int) CA[i3 & 63]);
                    i2 = s2 + 1;
                }
                int left = bytesLen - eLen;
                if (left > 0) {
                    int i4 = (bArr[eLen] & 255) << 10;
                    if (left == 2) {
                        i = (bArr[bytesLen - 1] & 255) << 2;
                    }
                    int i5 = i | i4;
                    write((int) CA[i5 >> 12]);
                    write((int) CA[(i5 >>> 6) & 63]);
                    write((int) left == 2 ? CA[i5 & 63] : '=');
                    write(61);
                }
                write((int) quote);
                return;
            }
            expandCapacity(newcount);
        }
        this.count = newcount;
        int offset2 = offset + 1;
        this.buf[offset] = quote;
        int i6 = 0;
        int d = offset2;
        while (i6 < eLen) {
            int s3 = i6 + 1;
            int s4 = s3 + 1;
            int i7 = ((bArr[i6] & 255) << MappingData.PATH) | ((bArr[s3] & 255) << 8);
            int s5 = s4 + 1;
            int i8 = i7 | (bArr[s4] & 255);
            char[] cArr = this.buf;
            int d2 = d + 1;
            cArr[d] = CA[(i8 >>> 18) & 63];
            int d3 = d2 + 1;
            cArr[d2] = CA[(i8 >>> 12) & 63];
            int d4 = d3 + 1;
            cArr[d3] = CA[(i8 >>> 6) & 63];
            d = d4 + 1;
            cArr[d4] = CA[i8 & 63];
            i6 = s5;
        }
        int left2 = bytesLen - eLen;
        if (left2 > 0) {
            int i9 = (bArr[eLen] & 255) << 10;
            if (left2 == 2) {
                i = (bArr[bytesLen - 1] & 255) << 2;
            }
            int i10 = i9 | i;
            char[] cArr2 = this.buf;
            cArr2[newcount - 5] = CA[i10 >> 12];
            cArr2[newcount - 4] = CA[(i10 >>> 6) & 63];
            cArr2[newcount - 3] = left2 == 2 ? CA[i10 & 63] : '=';
            cArr2[newcount - 2] = '=';
        }
        this.buf[newcount - 1] = quote;
    }

    public void writeHex(byte[] bytes) {
        int newcount = this.count + (bytes.length * 2) + 3;
        if (newcount > this.buf.length) {
            expandCapacity(newcount);
        }
        char[] cArr = this.buf;
        int i = this.count;
        int i2 = i + 1;
        this.count = i2;
        cArr[i] = 'x';
        this.count = i2 + 1;
        cArr[i2] = '\'';
        for (byte b : bytes) {
            int a = b & 255;
            int b0 = a >> 4;
            int b1 = a & 15;
            char[] cArr2 = this.buf;
            int i3 = this.count;
            int i4 = i3 + 1;
            this.count = i4;
            int i5 = 48;
            cArr2[i3] = (char) ((b0 < 10 ? 48 : 55) + b0);
            this.count = i4 + 1;
            if (b1 >= 10) {
                i5 = 55;
            }
            cArr2[i4] = (char) (i5 + b1);
        }
        char[] cArr3 = this.buf;
        int i6 = this.count;
        this.count = i6 + 1;
        cArr3[i6] = '\'';
    }

    public void writeFloat(float value, boolean checkWriteClassName) {
        if (value != value || value == Float.POSITIVE_INFINITY || value == Float.NEGATIVE_INFINITY) {
            writeNull();
            return;
        }
        int newcount = this.count + 15;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                String str = RyuFloat.toString(value);
                write(str, 0, str.length());
                if (checkWriteClassName && isEnabled(SerializerFeature.WriteClassName)) {
                    write(70);
                    return;
                }
                return;
            }
        }
        this.count += RyuFloat.toString(value, this.buf, this.count);
        if (checkWriteClassName && isEnabled(SerializerFeature.WriteClassName)) {
            write(70);
        }
    }

    public void writeDouble(double value, boolean checkWriteClassName) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            writeNull();
            return;
        }
        int newcount = this.count + 24;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                String str = RyuDouble.toString(value);
                write(str, 0, str.length());
                if (checkWriteClassName && isEnabled(SerializerFeature.WriteClassName)) {
                    write(68);
                    return;
                }
                return;
            }
        }
        this.count += RyuDouble.toString(value, this.buf, this.count);
        if (checkWriteClassName && isEnabled(SerializerFeature.WriteClassName)) {
            write(68);
        }
    }

    public void writeEnum(Enum<?> value) {
        if (value == null) {
            writeNull();
            return;
        }
        String strVal = null;
        if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            strVal = value.name();
        } else if (this.writeEnumUsingToString) {
            strVal = value.toString();
        }
        if (strVal != null) {
            char quote = isEnabled(SerializerFeature.UseSingleQuotes) ? '\'' : StringUtil.DOUBLE_QUOTE;
            write((int) quote);
            write(strVal);
            write((int) quote);
            return;
        }
        writeInt(value.ordinal());
    }

    public void writeLongAndChar(long i, char c) {
        writeLong(i);
        write((int) c);
    }

    public void writeLong(long i) {
        boolean needQuotationMark = isEnabled(SerializerFeature.BrowserCompatible) && !isEnabled(SerializerFeature.WriteClassName) && (i > 9007199254740991L || i < -9007199254740991L);
        if (i != Long.MIN_VALUE) {
            int size = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
            int newcount = this.count + size;
            if (needQuotationMark) {
                newcount += 2;
            }
            if (newcount > this.buf.length) {
                if (this.writer == null) {
                    expandCapacity(newcount);
                } else {
                    char[] chars = new char[size];
                    IOUtils.getChars(i, size, chars);
                    if (needQuotationMark) {
                        write(34);
                        write(chars, 0, chars.length);
                        write(34);
                        return;
                    }
                    write(chars, 0, chars.length);
                    return;
                }
            }
            if (needQuotationMark) {
                char[] cArr = this.buf;
                cArr[this.count] = StringUtil.DOUBLE_QUOTE;
                IOUtils.getChars(i, newcount - 1, cArr);
                this.buf[newcount - 1] = StringUtil.DOUBLE_QUOTE;
            } else {
                IOUtils.getChars(i, newcount, this.buf);
            }
            this.count = newcount;
        } else if (needQuotationMark) {
            write("\"-9223372036854775808\"");
        } else {
            write("-9223372036854775808");
        }
    }

    public void writeNull() {
        write(BuildConfig.TRAVIS);
    }

    public void writeNull(SerializerFeature feature) {
        writeNull(0, feature.mask);
    }

    public void writeNull(int beanFeatures, int feature) {
        if ((beanFeatures & feature) == 0 && (this.features & feature) == 0) {
            writeNull();
            return;
        }
        int i = SerializerFeature.WriteMapNullValue.mask;
        if ((beanFeatures & i) != 0 && ((~i) & beanFeatures & SerializerFeature.WRITE_MAP_NULL_FEATURES) == 0) {
            writeNull();
        } else if (feature == SerializerFeature.WriteNullListAsEmpty.mask) {
            write("[]");
        } else if (feature == SerializerFeature.WriteNullStringAsEmpty.mask) {
            writeString("");
        } else if (feature == SerializerFeature.WriteNullBooleanAsFalse.mask) {
            write(Bugly.SDK_IS_DEV);
        } else if (feature == SerializerFeature.WriteNullNumberAsZero.mask) {
            write(48);
        } else {
            writeNull();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0315, code lost:
        if ((r0.sepcialBits & (1 << r9)) == 0) goto L_0x031a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x033f, code lost:
        if (r15[r9] == 4) goto L_0x0341;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:206:0x04c7, code lost:
        if (r12 == '>') goto L_0x04cc;
     */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x0323  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x0346  */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x034b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeStringWithDoubleQuote(java.lang.String r28, char r29) {
        /*
            r27 = this;
            r0 = r27
            r1 = r28
            r2 = r29
            if (r1 != 0) goto L_0x0011
            r27.writeNull()
            if (r2 == 0) goto L_0x0010
            r0.write((int) r2)
        L_0x0010:
            return
        L_0x0011:
            int r3 = r28.length()
            int r4 = r0.count
            int r4 = r4 + r3
            int r4 = r4 + 2
            if (r2 == 0) goto L_0x001e
            int r4 = r4 + 1
        L_0x001e:
            char[] r5 = r0.buf
            int r5 = r5.length
            r6 = 62
            r7 = 60
            r8 = 41
            r9 = 40
            r13 = 34
            r14 = 12
            r15 = 8
            r10 = 117(0x75, float:1.64E-43)
            r12 = 92
            if (r4 <= r5) goto L_0x016e
            java.io.Writer r5 = r0.writer
            if (r5 == 0) goto L_0x016b
            r0.write((int) r13)
            r5 = 0
        L_0x003d:
            int r11 = r28.length()
            if (r5 >= r11) goto L_0x0160
            char r11 = r1.charAt(r5)
            com.alibaba.fastjson.serializer.SerializerFeature r13 = com.alibaba.fastjson.serializer.SerializerFeature.BrowserSecure
            boolean r13 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r13)
            if (r13 == 0) goto L_0x0083
            if (r11 == r9) goto L_0x0057
            if (r11 == r8) goto L_0x0057
            if (r11 == r7) goto L_0x0057
            if (r11 != r6) goto L_0x0083
        L_0x0057:
            r0.write((int) r12)
            r0.write((int) r10)
            char[] r13 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r21 = r11 >>> 12
            r21 = r21 & 15
            char r6 = r13[r21]
            r0.write((int) r6)
            int r6 = r11 >>> 8
            r6 = r6 & 15
            char r6 = r13[r6]
            r0.write((int) r6)
            int r6 = r11 >>> 4
            r6 = r6 & 15
            char r6 = r13[r6]
            r0.write((int) r6)
            r6 = r11 & 15
            char r6 = r13[r6]
            r0.write((int) r6)
            goto L_0x0158
        L_0x0083:
            com.alibaba.fastjson.serializer.SerializerFeature r6 = com.alibaba.fastjson.serializer.SerializerFeature.BrowserCompatible
            boolean r6 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r6)
            if (r6 == 0) goto L_0x0108
            if (r11 == r15) goto L_0x00fd
            if (r11 == r14) goto L_0x00fd
            r6 = 10
            if (r11 == r6) goto L_0x00fd
            r6 = 13
            if (r11 == r6) goto L_0x00fd
            r6 = 9
            if (r11 == r6) goto L_0x00fd
            r6 = 34
            if (r11 == r6) goto L_0x00fd
            r6 = 47
            if (r11 == r6) goto L_0x00fd
            if (r11 != r12) goto L_0x00a6
            goto L_0x00fd
        L_0x00a6:
            r6 = 32
            if (r11 >= r6) goto L_0x00ce
            r0.write((int) r12)
            r0.write((int) r10)
            r6 = 48
            r0.write((int) r6)
            r0.write((int) r6)
            char[] r6 = com.alibaba.fastjson.util.IOUtils.ASCII_CHARS
            int r13 = r11 * 2
            char r13 = r6[r13]
            r0.write((int) r13)
            int r13 = r11 * 2
            r19 = 1
            int r13 = r13 + 1
            char r6 = r6[r13]
            r0.write((int) r6)
            goto L_0x0158
        L_0x00ce:
            r6 = 127(0x7f, float:1.78E-43)
            if (r11 < r6) goto L_0x0155
            r0.write((int) r12)
            r0.write((int) r10)
            char[] r6 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r13 = r11 >>> 12
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            int r13 = r11 >>> 8
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            int r13 = r11 >>> 4
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            r13 = r11 & 15
            char r6 = r6[r13]
            r0.write((int) r6)
            goto L_0x0158
        L_0x00fd:
            r0.write((int) r12)
            char[] r6 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r6 = r6[r11]
            r0.write((int) r6)
            goto L_0x0158
        L_0x0108:
            byte[] r6 = com.alibaba.fastjson.util.IOUtils.specicalFlags_doubleQuotes
            int r13 = r6.length
            if (r11 >= r13) goto L_0x0111
            byte r13 = r6[r11]
            if (r13 != 0) goto L_0x011d
        L_0x0111:
            r13 = 47
            if (r11 != r13) goto L_0x0155
            com.alibaba.fastjson.serializer.SerializerFeature r13 = com.alibaba.fastjson.serializer.SerializerFeature.WriteSlashAsSpecial
            boolean r13 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r13)
            if (r13 == 0) goto L_0x0155
        L_0x011d:
            r0.write((int) r12)
            byte r6 = r6[r11]
            r13 = 4
            if (r6 != r13) goto L_0x014d
            r0.write((int) r10)
            char[] r6 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r13 = r11 >>> 12
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            int r13 = r11 >>> 8
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            int r13 = r11 >>> 4
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            r13 = r11 & 15
            char r6 = r6[r13]
            r0.write((int) r6)
            goto L_0x0158
        L_0x014d:
            char[] r6 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r6 = r6[r11]
            r0.write((int) r6)
            goto L_0x0158
        L_0x0155:
            r0.write((int) r11)
        L_0x0158:
            int r5 = r5 + 1
            r6 = 62
            r13 = 34
            goto L_0x003d
        L_0x0160:
            r5 = 34
            r0.write((int) r5)
            if (r2 == 0) goto L_0x016a
            r0.write((int) r2)
        L_0x016a:
            return
        L_0x016b:
            r0.expandCapacity(r4)
        L_0x016e:
            int r5 = r0.count
            int r6 = r5 + 1
            int r11 = r6 + r3
            char[] r13 = r0.buf
            r20 = 34
            r13[r5] = r20
            r5 = 0
            r1.getChars(r5, r3, r13, r6)
            r0.count = r4
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.BrowserCompatible
            boolean r5 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r5)
            if (r5 == 0) goto L_0x02cf
            r5 = -1
            r7 = r6
        L_0x018a:
            if (r7 >= r11) goto L_0x01c7
            char[] r8 = r0.buf
            char r8 = r8[r7]
            r9 = 34
            if (r8 == r9) goto L_0x01c0
            r9 = 47
            if (r8 == r9) goto L_0x01c0
            if (r8 != r12) goto L_0x019b
            goto L_0x01c0
        L_0x019b:
            if (r8 == r15) goto L_0x01bc
            if (r8 == r14) goto L_0x01bc
            r9 = 10
            if (r8 == r9) goto L_0x01bc
            r9 = 13
            if (r8 == r9) goto L_0x01bc
            r9 = 9
            if (r8 != r9) goto L_0x01ac
            goto L_0x01bc
        L_0x01ac:
            r9 = 32
            if (r8 >= r9) goto L_0x01b4
            r5 = r7
            int r4 = r4 + 5
            goto L_0x01c4
        L_0x01b4:
            r9 = 127(0x7f, float:1.78E-43)
            if (r8 < r9) goto L_0x01c4
            r5 = r7
            int r4 = r4 + 5
            goto L_0x01c4
        L_0x01bc:
            r5 = r7
            int r4 = r4 + 1
            goto L_0x01c4
        L_0x01c0:
            r5 = r7
            int r4 = r4 + 1
        L_0x01c4:
            int r7 = r7 + 1
            goto L_0x018a
        L_0x01c7:
            char[] r7 = r0.buf
            int r7 = r7.length
            if (r4 <= r7) goto L_0x01cf
            r0.expandCapacity(r4)
        L_0x01cf:
            r0.count = r4
            r7 = r5
        L_0x01d2:
            if (r7 < r6) goto L_0x02b3
            char[] r8 = r0.buf
            char r9 = r8[r7]
            if (r9 == r15) goto L_0x028f
            if (r9 == r14) goto L_0x028f
            r13 = 10
            if (r9 == r13) goto L_0x028f
            r13 = 13
            if (r9 == r13) goto L_0x028f
            r13 = 9
            if (r9 != r13) goto L_0x01ea
            goto L_0x028f
        L_0x01ea:
            r13 = 34
            if (r9 == r13) goto L_0x0277
            r13 = 47
            if (r9 == r13) goto L_0x0277
            if (r9 != r12) goto L_0x01f6
            goto L_0x0277
        L_0x01f6:
            r13 = 32
            if (r9 >= r13) goto L_0x0233
            int r13 = r7 + 1
            int r14 = r7 + 6
            int r18 = r11 - r7
            r19 = 1
            int r15 = r18 + -1
            java.lang.System.arraycopy(r8, r13, r8, r14, r15)
            char[] r8 = r0.buf
            r8[r7] = r12
            int r13 = r7 + 1
            r8[r13] = r10
            int r13 = r7 + 2
            r14 = 48
            r8[r13] = r14
            int r13 = r7 + 3
            r8[r13] = r14
            int r13 = r7 + 4
            char[] r14 = com.alibaba.fastjson.util.IOUtils.ASCII_CHARS
            int r15 = r9 * 2
            char r15 = r14[r15]
            r8[r13] = r15
            int r13 = r7 + 5
            int r15 = r9 * 2
            r18 = 1
            int r15 = r15 + 1
            char r14 = r14[r15]
            r8[r13] = r14
            int r11 = r11 + 5
            goto L_0x02ab
        L_0x0233:
            r13 = 127(0x7f, float:1.78E-43)
            if (r9 < r13) goto L_0x02ab
            int r13 = r7 + 1
            int r14 = r7 + 6
            int r15 = r11 - r7
            r18 = 1
            int r15 = r15 + -1
            java.lang.System.arraycopy(r8, r13, r8, r14, r15)
            char[] r8 = r0.buf
            r8[r7] = r12
            int r13 = r7 + 1
            r8[r13] = r10
            int r13 = r7 + 2
            char[] r14 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r15 = r9 >>> 12
            r15 = r15 & 15
            char r15 = r14[r15]
            r8[r13] = r15
            int r13 = r7 + 3
            int r15 = r9 >>> 8
            r15 = r15 & 15
            char r15 = r14[r15]
            r8[r13] = r15
            int r13 = r7 + 4
            int r15 = r9 >>> 4
            r15 = r15 & 15
            char r15 = r14[r15]
            r8[r13] = r15
            int r13 = r7 + 5
            r15 = r9 & 15
            char r14 = r14[r15]
            r8[r13] = r14
            int r11 = r11 + 5
            goto L_0x02ab
        L_0x0277:
            int r13 = r7 + 1
            int r14 = r7 + 2
            int r15 = r11 - r7
            r18 = 1
            int r15 = r15 + -1
            java.lang.System.arraycopy(r8, r13, r8, r14, r15)
            char[] r8 = r0.buf
            r8[r7] = r12
            int r13 = r7 + 1
            r8[r13] = r9
            int r11 = r11 + 1
            goto L_0x02ab
        L_0x028f:
            int r13 = r7 + 1
            int r14 = r7 + 2
            int r15 = r11 - r7
            r18 = 1
            int r15 = r15 + -1
            java.lang.System.arraycopy(r8, r13, r8, r14, r15)
            char[] r8 = r0.buf
            r8[r7] = r12
            int r13 = r7 + 1
            char[] r14 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r14 = r14[r9]
            r8[r13] = r14
            int r11 = r11 + 1
        L_0x02ab:
            int r7 = r7 + -1
            r14 = 12
            r15 = 8
            goto L_0x01d2
        L_0x02b3:
            if (r2 == 0) goto L_0x02c4
            char[] r7 = r0.buf
            int r8 = r0.count
            int r9 = r8 + -2
            r10 = 34
            r7[r9] = r10
            r9 = 1
            int r8 = r8 - r9
            r7[r8] = r2
            goto L_0x02ce
        L_0x02c4:
            r9 = 1
            r10 = 34
            char[] r7 = r0.buf
            int r8 = r0.count
            int r8 = r8 - r9
            r7[r8] = r10
        L_0x02ce:
            return
        L_0x02cf:
            r5 = 0
            r13 = -1
            r14 = -1
            r15 = 0
            r21 = r6
            r10 = r21
        L_0x02d7:
            r8 = 8232(0x2028, float:1.1535E-41)
            if (r10 >= r11) goto L_0x035c
            char[] r9 = r0.buf
            char r9 = r9[r10]
            r12 = 93
            r7 = -1
            if (r9 < r12) goto L_0x0301
            r12 = 127(0x7f, float:1.78E-43)
            if (r9 < r12) goto L_0x02fe
            if (r9 == r8) goto L_0x02f2
            r8 = 8233(0x2029, float:1.1537E-41)
            if (r9 == r8) goto L_0x02f2
            r8 = 160(0xa0, float:2.24E-43)
            if (r9 >= r8) goto L_0x02fe
        L_0x02f2:
            if (r14 != r7) goto L_0x02f5
            r14 = r10
        L_0x02f5:
            int r5 = r5 + 1
            r7 = r10
            r8 = r9
            int r4 = r4 + 4
            r13 = r7
            r15 = r8
            goto L_0x0350
        L_0x02fe:
            r23 = r13
            goto L_0x034e
        L_0x0301:
            r12 = 127(0x7f, float:1.78E-43)
            r8 = 64
            if (r9 >= r8) goto L_0x0318
            r23 = r13
            long r12 = r0.sepcialBits
            r24 = 1
            long r24 = r24 << r9
            long r12 = r12 & r24
            r24 = 0
            int r8 = (r12 > r24 ? 1 : (r12 == r24 ? 0 : -1))
            if (r8 != 0) goto L_0x031e
            goto L_0x031a
        L_0x0318:
            r23 = r13
        L_0x031a:
            r8 = 92
            if (r9 != r8) goto L_0x0320
        L_0x031e:
            r8 = 1
            goto L_0x0321
        L_0x0320:
            r8 = 0
        L_0x0321:
            if (r8 == 0) goto L_0x034e
            int r5 = r5 + 1
            r12 = r10
            r13 = r9
            r15 = 40
            if (r9 == r15) goto L_0x0341
            r15 = 41
            if (r9 == r15) goto L_0x0341
            r15 = 60
            if (r9 == r15) goto L_0x0341
            r15 = 62
            if (r9 == r15) goto L_0x0341
            byte[] r15 = com.alibaba.fastjson.util.IOUtils.specicalFlags_doubleQuotes
            int r7 = r15.length
            if (r9 >= r7) goto L_0x0343
            byte r7 = r15[r9]
            r15 = 4
            if (r7 != r15) goto L_0x0343
        L_0x0341:
            int r4 = r4 + 4
        L_0x0343:
            r7 = -1
            if (r14 != r7) goto L_0x034b
            r7 = r10
            r14 = r7
            r15 = r13
            r13 = r12
            goto L_0x0350
        L_0x034b:
            r15 = r13
            r13 = r12
            goto L_0x0350
        L_0x034e:
            r13 = r23
        L_0x0350:
            int r10 = r10 + 1
            r7 = 60
            r8 = 41
            r9 = 40
            r12 = 92
            goto L_0x02d7
        L_0x035c:
            r23 = r13
            if (r5 <= 0) goto L_0x05ce
            int r4 = r4 + r5
            char[] r7 = r0.buf
            int r7 = r7.length
            if (r4 <= r7) goto L_0x0369
            r0.expandCapacity(r4)
        L_0x0369:
            r0.count = r4
            r7 = 1
            if (r5 != r7) goto L_0x04a0
            r7 = 50
            if (r15 != r8) goto L_0x03a4
            int r13 = r23 + 1
            int r8 = r23 + 6
            int r9 = r11 - r23
            r10 = 1
            int r9 = r9 - r10
            char[] r10 = r0.buf
            java.lang.System.arraycopy(r10, r13, r10, r8, r9)
            char[] r10 = r0.buf
            r12 = 92
            r10[r23] = r12
            int r12 = r23 + 1
            r16 = 117(0x75, float:1.64E-43)
            r10[r12] = r16
            r16 = 1
            int r12 = r12 + 1
            r10[r12] = r7
            int r12 = r12 + 1
            r17 = 48
            r10[r12] = r17
            int r12 = r12 + 1
            r10[r12] = r7
            int r7 = r12 + 1
            r12 = 56
            r10[r7] = r12
            r13 = r7
            goto L_0x05d0
        L_0x03a4:
            r8 = 8233(0x2029, float:1.1537E-41)
            if (r15 != r8) goto L_0x03da
            int r13 = r23 + 1
            int r8 = r23 + 6
            int r9 = r11 - r23
            r10 = 1
            int r9 = r9 - r10
            char[] r10 = r0.buf
            java.lang.System.arraycopy(r10, r13, r10, r8, r9)
            char[] r10 = r0.buf
            r12 = 92
            r10[r23] = r12
            int r12 = r23 + 1
            r16 = 117(0x75, float:1.64E-43)
            r10[r12] = r16
            r16 = 1
            int r12 = r12 + 1
            r10[r12] = r7
            int r12 = r12 + 1
            r17 = 48
            r10[r12] = r17
            int r12 = r12 + 1
            r10[r12] = r7
            int r7 = r12 + 1
            r12 = 57
            r10[r7] = r12
            r13 = r7
            goto L_0x05d0
        L_0x03da:
            r7 = 40
            if (r15 == r7) goto L_0x045c
            r7 = 41
            if (r15 == r7) goto L_0x045c
            r7 = 60
            if (r15 == r7) goto L_0x045c
            r7 = 62
            if (r15 != r7) goto L_0x03eb
            goto L_0x045c
        L_0x03eb:
            r7 = r15
            byte[] r8 = com.alibaba.fastjson.util.IOUtils.specicalFlags_doubleQuotes
            int r9 = r8.length
            if (r7 >= r9) goto L_0x043e
            byte r8 = r8[r7]
            r9 = 4
            if (r8 != r9) goto L_0x043e
            int r13 = r23 + 1
            int r8 = r23 + 6
            int r9 = r11 - r23
            r10 = 1
            int r9 = r9 - r10
            char[] r10 = r0.buf
            java.lang.System.arraycopy(r10, r13, r10, r8, r9)
            r10 = r23
            char[] r12 = r0.buf
            int r16 = r10 + 1
            r17 = 92
            r12[r10] = r17
            int r10 = r16 + 1
            r17 = 117(0x75, float:1.64E-43)
            r12[r16] = r17
            int r16 = r10 + 1
            char[] r17 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r18 = r7 >>> 12
            r18 = r18 & 15
            char r18 = r17[r18]
            r12[r10] = r18
            int r10 = r16 + 1
            int r18 = r7 >>> 8
            r18 = r18 & 15
            char r18 = r17[r18]
            r12[r16] = r18
            int r16 = r10 + 1
            int r18 = r7 >>> 4
            r18 = r18 & 15
            char r18 = r17[r18]
            r12[r10] = r18
            int r10 = r16 + 1
            r18 = r7 & 15
            char r17 = r17[r18]
            r12[r16] = r17
            r13 = r23
            goto L_0x045a
        L_0x043e:
            int r13 = r23 + 1
            int r8 = r23 + 2
            int r9 = r11 - r23
            r10 = 1
            int r9 = r9 - r10
            char[] r10 = r0.buf
            java.lang.System.arraycopy(r10, r13, r10, r8, r9)
            char[] r10 = r0.buf
            r12 = 92
            r10[r23] = r12
            int r12 = r23 + 1
            char[] r16 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r16 = r16[r7]
            r10[r12] = r16
            r13 = r12
        L_0x045a:
            goto L_0x05d0
        L_0x045c:
            int r13 = r23 + 1
            int r7 = r23 + 6
            int r8 = r11 - r23
            r9 = 1
            int r8 = r8 - r9
            char[] r9 = r0.buf
            java.lang.System.arraycopy(r9, r13, r9, r7, r8)
            char[] r9 = r0.buf
            r10 = 92
            r9[r23] = r10
            int r10 = r23 + 1
            r12 = 117(0x75, float:1.64E-43)
            r9[r10] = r12
            r12 = r15
            r16 = r8
            r8 = 1
            int r10 = r10 + r8
            char[] r17 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r18 = r12 >>> 12
            r18 = r18 & 15
            char r18 = r17[r18]
            r9[r10] = r18
            int r10 = r10 + r8
            int r18 = r12 >>> 8
            r18 = r18 & 15
            char r18 = r17[r18]
            r9[r10] = r18
            int r10 = r10 + r8
            int r18 = r12 >>> 4
            r18 = r18 & 15
            char r18 = r17[r18]
            r9[r10] = r18
            int r10 = r10 + r8
            r18 = r12 & 15
            char r17 = r17[r18]
            r9[r10] = r17
            r13 = r10
            goto L_0x05d0
        L_0x04a0:
            r26 = r8
            r8 = r7
            r7 = r26
            if (r5 <= r8) goto L_0x05cb
            int r8 = r14 - r6
            r9 = r14
            r10 = r8
        L_0x04ab:
            int r12 = r28.length()
            if (r10 >= r12) goto L_0x05c8
            char r12 = r1.charAt(r10)
            boolean r13 = r0.browserSecure
            if (r13 == 0) goto L_0x050b
            r13 = 40
            if (r12 == r13) goto L_0x04ca
            r13 = 41
            if (r12 == r13) goto L_0x04ca
            r13 = 60
            if (r12 == r13) goto L_0x04ca
            r13 = 62
            if (r12 != r13) goto L_0x050b
            goto L_0x04cc
        L_0x04ca:
            r13 = 62
        L_0x04cc:
            char[] r13 = r0.buf
            int r16 = r9 + 1
            r17 = 92
            r13[r9] = r17
            int r9 = r16 + 1
            r17 = 117(0x75, float:1.64E-43)
            r13[r16] = r17
            int r16 = r9 + 1
            char[] r17 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r24 = r12 >>> 12
            r24 = r24 & 15
            char r24 = r17[r24]
            r13[r9] = r24
            int r9 = r16 + 1
            int r24 = r12 >>> 8
            r24 = r24 & 15
            char r24 = r17[r24]
            r13[r16] = r24
            int r16 = r9 + 1
            int r24 = r12 >>> 4
            r24 = r24 & 15
            char r24 = r17[r24]
            r13[r9] = r24
            int r9 = r16 + 1
            r24 = r12 & 15
            char r17 = r17[r24]
            r13[r16] = r17
            int r11 = r11 + 5
            r13 = 4
            r18 = 92
            r21 = 117(0x75, float:1.64E-43)
            goto L_0x05c2
        L_0x050b:
            byte[] r13 = com.alibaba.fastjson.util.IOUtils.specicalFlags_doubleQuotes
            int r7 = r13.length
            if (r12 >= r7) goto L_0x0514
            byte r7 = r13[r12]
            if (r7 != 0) goto L_0x0520
        L_0x0514:
            r7 = 47
            if (r12 != r7) goto L_0x0573
            com.alibaba.fastjson.serializer.SerializerFeature r7 = com.alibaba.fastjson.serializer.SerializerFeature.WriteSlashAsSpecial
            boolean r7 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r7)
            if (r7 == 0) goto L_0x0571
        L_0x0520:
            char[] r7 = r0.buf
            int r17 = r9 + 1
            r22 = 92
            r7[r9] = r22
            byte r9 = r13[r12]
            r13 = 4
            if (r9 != r13) goto L_0x0562
            int r9 = r17 + 1
            r18 = 117(0x75, float:1.64E-43)
            r7[r17] = r18
            int r17 = r9 + 1
            char[] r18 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r24 = r12 >>> 12
            r24 = r24 & 15
            char r24 = r18[r24]
            r7[r9] = r24
            int r9 = r17 + 1
            int r24 = r12 >>> 8
            r24 = r24 & 15
            char r24 = r18[r24]
            r7[r17] = r24
            int r17 = r9 + 1
            int r24 = r12 >>> 4
            r24 = r24 & 15
            char r24 = r18[r24]
            r7[r9] = r24
            int r9 = r17 + 1
            r24 = r12 & 15
            char r18 = r18[r24]
            r7[r17] = r18
            int r11 = r11 + 5
            r18 = 92
            r21 = 117(0x75, float:1.64E-43)
            goto L_0x05c2
        L_0x0562:
            int r9 = r17 + 1
            char[] r18 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r18 = r18[r12]
            r7[r17] = r18
            int r11 = r11 + 1
            r18 = 92
            r21 = 117(0x75, float:1.64E-43)
            goto L_0x05c2
        L_0x0571:
            r13 = 4
            goto L_0x0574
        L_0x0573:
            r13 = 4
        L_0x0574:
            r7 = 8232(0x2028, float:1.1535E-41)
            if (r12 == r7) goto L_0x058a
            r7 = 8233(0x2029, float:1.1537E-41)
            if (r12 != r7) goto L_0x057d
            goto L_0x058a
        L_0x057d:
            char[] r7 = r0.buf
            int r17 = r9 + 1
            r7[r9] = r12
            r9 = r17
            r18 = 92
            r21 = 117(0x75, float:1.64E-43)
            goto L_0x05c2
        L_0x058a:
            char[] r7 = r0.buf
            int r17 = r9 + 1
            r18 = 92
            r7[r9] = r18
            int r9 = r17 + 1
            r21 = 117(0x75, float:1.64E-43)
            r7[r17] = r21
            int r17 = r9 + 1
            char[] r22 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r24 = r12 >>> 12
            r24 = r24 & 15
            char r24 = r22[r24]
            r7[r9] = r24
            int r9 = r17 + 1
            int r24 = r12 >>> 8
            r24 = r24 & 15
            char r24 = r22[r24]
            r7[r17] = r24
            int r17 = r9 + 1
            int r24 = r12 >>> 4
            r24 = r24 & 15
            char r24 = r22[r24]
            r7[r9] = r24
            int r9 = r17 + 1
            r24 = r12 & 15
            char r22 = r22[r24]
            r7[r17] = r22
            int r11 = r11 + 5
        L_0x05c2:
            int r10 = r10 + 1
            r7 = 8232(0x2028, float:1.1535E-41)
            goto L_0x04ab
        L_0x05c8:
            r13 = r23
            goto L_0x05d0
        L_0x05cb:
            r13 = r23
            goto L_0x05d0
        L_0x05ce:
            r13 = r23
        L_0x05d0:
            if (r2 == 0) goto L_0x05e1
            char[] r7 = r0.buf
            int r8 = r0.count
            int r9 = r8 + -2
            r10 = 34
            r7[r9] = r10
            r9 = 1
            int r8 = r8 - r9
            r7[r8] = r2
            goto L_0x05eb
        L_0x05e1:
            r9 = 1
            r10 = 34
            char[] r7 = r0.buf
            int r8 = r0.count
            int r8 = r8 - r9
            r7[r8] = r10
        L_0x05eb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeWriter.writeStringWithDoubleQuote(java.lang.String, char):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:138:0x030c, code lost:
        if ((r0.sepcialBits & (1 << r9)) == 0) goto L_0x0311;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0336, code lost:
        if (r11[r9] == 4) goto L_0x0338;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:206:0x04a7, code lost:
        if (r12 == '>') goto L_0x04ac;
     */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x031a  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x033d  */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x0341  */
    /* JADX WARNING: Removed duplicated region for block: B:260:0x0342 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeStringWithDoubleQuote(char[] r28, char r29) {
        /*
            r27 = this;
            r0 = r27
            r1 = r28
            r2 = r29
            if (r1 != 0) goto L_0x0011
            r27.writeNull()
            if (r2 == 0) goto L_0x0010
            r0.write((int) r2)
        L_0x0010:
            return
        L_0x0011:
            int r3 = r1.length
            int r4 = r0.count
            int r4 = r4 + r3
            int r4 = r4 + 2
            if (r2 == 0) goto L_0x001b
            int r4 = r4 + 1
        L_0x001b:
            char[] r5 = r0.buf
            int r5 = r5.length
            r6 = 62
            r7 = 60
            r8 = 41
            r9 = 40
            r13 = 34
            r14 = 12
            r15 = 8
            r10 = 117(0x75, float:1.64E-43)
            r12 = 92
            if (r4 <= r5) goto L_0x0166
            java.io.Writer r5 = r0.writer
            if (r5 == 0) goto L_0x0163
            r0.write((int) r13)
            r5 = 0
        L_0x003a:
            int r11 = r1.length
            if (r5 >= r11) goto L_0x0158
            char r11 = r1[r5]
            com.alibaba.fastjson.serializer.SerializerFeature r13 = com.alibaba.fastjson.serializer.SerializerFeature.BrowserSecure
            boolean r13 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r13)
            if (r13 == 0) goto L_0x007b
            if (r11 == r9) goto L_0x004f
            if (r11 == r8) goto L_0x004f
            if (r11 == r7) goto L_0x004f
            if (r11 != r6) goto L_0x007b
        L_0x004f:
            r0.write((int) r12)
            r0.write((int) r10)
            char[] r13 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r22 = r11 >>> 12
            r22 = r22 & 15
            char r6 = r13[r22]
            r0.write((int) r6)
            int r6 = r11 >>> 8
            r6 = r6 & 15
            char r6 = r13[r6]
            r0.write((int) r6)
            int r6 = r11 >>> 4
            r6 = r6 & 15
            char r6 = r13[r6]
            r0.write((int) r6)
            r6 = r11 & 15
            char r6 = r13[r6]
            r0.write((int) r6)
            goto L_0x0150
        L_0x007b:
            com.alibaba.fastjson.serializer.SerializerFeature r6 = com.alibaba.fastjson.serializer.SerializerFeature.BrowserCompatible
            boolean r6 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r6)
            if (r6 == 0) goto L_0x0100
            if (r11 == r15) goto L_0x00f5
            if (r11 == r14) goto L_0x00f5
            r6 = 10
            if (r11 == r6) goto L_0x00f5
            r6 = 13
            if (r11 == r6) goto L_0x00f5
            r6 = 9
            if (r11 == r6) goto L_0x00f5
            r6 = 34
            if (r11 == r6) goto L_0x00f5
            r6 = 47
            if (r11 == r6) goto L_0x00f5
            if (r11 != r12) goto L_0x009e
            goto L_0x00f5
        L_0x009e:
            r6 = 32
            if (r11 >= r6) goto L_0x00c6
            r0.write((int) r12)
            r0.write((int) r10)
            r6 = 48
            r0.write((int) r6)
            r0.write((int) r6)
            char[] r6 = com.alibaba.fastjson.util.IOUtils.ASCII_CHARS
            int r13 = r11 * 2
            char r13 = r6[r13]
            r0.write((int) r13)
            int r13 = r11 * 2
            r20 = 1
            int r13 = r13 + 1
            char r6 = r6[r13]
            r0.write((int) r6)
            goto L_0x0150
        L_0x00c6:
            r6 = 127(0x7f, float:1.78E-43)
            if (r11 < r6) goto L_0x014d
            r0.write((int) r12)
            r0.write((int) r10)
            char[] r6 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r13 = r11 >>> 12
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            int r13 = r11 >>> 8
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            int r13 = r11 >>> 4
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            r13 = r11 & 15
            char r6 = r6[r13]
            r0.write((int) r6)
            goto L_0x0150
        L_0x00f5:
            r0.write((int) r12)
            char[] r6 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r6 = r6[r11]
            r0.write((int) r6)
            goto L_0x0150
        L_0x0100:
            byte[] r6 = com.alibaba.fastjson.util.IOUtils.specicalFlags_doubleQuotes
            int r13 = r6.length
            if (r11 >= r13) goto L_0x0109
            byte r13 = r6[r11]
            if (r13 != 0) goto L_0x0115
        L_0x0109:
            r13 = 47
            if (r11 != r13) goto L_0x014d
            com.alibaba.fastjson.serializer.SerializerFeature r13 = com.alibaba.fastjson.serializer.SerializerFeature.WriteSlashAsSpecial
            boolean r13 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r13)
            if (r13 == 0) goto L_0x014d
        L_0x0115:
            r0.write((int) r12)
            byte r6 = r6[r11]
            r13 = 4
            if (r6 != r13) goto L_0x0145
            r0.write((int) r10)
            char[] r6 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r13 = r11 >>> 12
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            int r13 = r11 >>> 8
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            int r13 = r11 >>> 4
            r13 = r13 & 15
            char r13 = r6[r13]
            r0.write((int) r13)
            r13 = r11 & 15
            char r6 = r6[r13]
            r0.write((int) r6)
            goto L_0x0150
        L_0x0145:
            char[] r6 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r6 = r6[r11]
            r0.write((int) r6)
            goto L_0x0150
        L_0x014d:
            r0.write((int) r11)
        L_0x0150:
            int r5 = r5 + 1
            r6 = 62
            r13 = 34
            goto L_0x003a
        L_0x0158:
            r5 = 34
            r0.write((int) r5)
            if (r2 == 0) goto L_0x0162
            r0.write((int) r2)
        L_0x0162:
            return
        L_0x0163:
            r0.expandCapacity(r4)
        L_0x0166:
            int r5 = r0.count
            int r6 = r5 + 1
            int r11 = r6 + r3
            char[] r13 = r0.buf
            r21 = 34
            r13[r5] = r21
            r5 = 0
            int r7 = r1.length
            java.lang.System.arraycopy(r1, r5, r13, r6, r7)
            r0.count = r4
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.BrowserCompatible
            boolean r5 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r5)
            if (r5 == 0) goto L_0x02c8
            r5 = -1
            r7 = r6
        L_0x0183:
            if (r7 >= r11) goto L_0x01c0
            char[] r8 = r0.buf
            char r8 = r8[r7]
            r9 = 34
            if (r8 == r9) goto L_0x01b9
            r9 = 47
            if (r8 == r9) goto L_0x01b9
            if (r8 != r12) goto L_0x0194
            goto L_0x01b9
        L_0x0194:
            if (r8 == r15) goto L_0x01b5
            if (r8 == r14) goto L_0x01b5
            r9 = 10
            if (r8 == r9) goto L_0x01b5
            r9 = 13
            if (r8 == r9) goto L_0x01b5
            r9 = 9
            if (r8 != r9) goto L_0x01a5
            goto L_0x01b5
        L_0x01a5:
            r9 = 32
            if (r8 >= r9) goto L_0x01ad
            r5 = r7
            int r4 = r4 + 5
            goto L_0x01bd
        L_0x01ad:
            r9 = 127(0x7f, float:1.78E-43)
            if (r8 < r9) goto L_0x01bd
            r5 = r7
            int r4 = r4 + 5
            goto L_0x01bd
        L_0x01b5:
            r5 = r7
            int r4 = r4 + 1
            goto L_0x01bd
        L_0x01b9:
            r5 = r7
            int r4 = r4 + 1
        L_0x01bd:
            int r7 = r7 + 1
            goto L_0x0183
        L_0x01c0:
            char[] r7 = r0.buf
            int r7 = r7.length
            if (r4 <= r7) goto L_0x01c8
            r0.expandCapacity(r4)
        L_0x01c8:
            r0.count = r4
            r7 = r5
        L_0x01cb:
            if (r7 < r6) goto L_0x02ac
            char[] r8 = r0.buf
            char r9 = r8[r7]
            if (r9 == r15) goto L_0x0288
            if (r9 == r14) goto L_0x0288
            r13 = 10
            if (r9 == r13) goto L_0x0288
            r13 = 13
            if (r9 == r13) goto L_0x0288
            r13 = 9
            if (r9 != r13) goto L_0x01e3
            goto L_0x0288
        L_0x01e3:
            r13 = 34
            if (r9 == r13) goto L_0x0270
            r13 = 47
            if (r9 == r13) goto L_0x0270
            if (r9 != r12) goto L_0x01ef
            goto L_0x0270
        L_0x01ef:
            r13 = 32
            if (r9 >= r13) goto L_0x022c
            int r13 = r7 + 1
            int r14 = r7 + 6
            int r18 = r11 - r7
            r20 = 1
            int r15 = r18 + -1
            java.lang.System.arraycopy(r8, r13, r8, r14, r15)
            char[] r8 = r0.buf
            r8[r7] = r12
            int r13 = r7 + 1
            r8[r13] = r10
            int r13 = r7 + 2
            r14 = 48
            r8[r13] = r14
            int r13 = r7 + 3
            r8[r13] = r14
            int r13 = r7 + 4
            char[] r14 = com.alibaba.fastjson.util.IOUtils.ASCII_CHARS
            int r15 = r9 * 2
            char r15 = r14[r15]
            r8[r13] = r15
            int r13 = r7 + 5
            int r15 = r9 * 2
            r18 = 1
            int r15 = r15 + 1
            char r14 = r14[r15]
            r8[r13] = r14
            int r11 = r11 + 5
            goto L_0x02a4
        L_0x022c:
            r13 = 127(0x7f, float:1.78E-43)
            if (r9 < r13) goto L_0x02a4
            int r13 = r7 + 1
            int r14 = r7 + 6
            int r15 = r11 - r7
            r18 = 1
            int r15 = r15 + -1
            java.lang.System.arraycopy(r8, r13, r8, r14, r15)
            char[] r8 = r0.buf
            r8[r7] = r12
            int r13 = r7 + 1
            r8[r13] = r10
            int r13 = r7 + 2
            char[] r14 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r15 = r9 >>> 12
            r15 = r15 & 15
            char r15 = r14[r15]
            r8[r13] = r15
            int r13 = r7 + 3
            int r15 = r9 >>> 8
            r15 = r15 & 15
            char r15 = r14[r15]
            r8[r13] = r15
            int r13 = r7 + 4
            int r15 = r9 >>> 4
            r15 = r15 & 15
            char r15 = r14[r15]
            r8[r13] = r15
            int r13 = r7 + 5
            r15 = r9 & 15
            char r14 = r14[r15]
            r8[r13] = r14
            int r11 = r11 + 5
            goto L_0x02a4
        L_0x0270:
            int r13 = r7 + 1
            int r14 = r7 + 2
            int r15 = r11 - r7
            r18 = 1
            int r15 = r15 + -1
            java.lang.System.arraycopy(r8, r13, r8, r14, r15)
            char[] r8 = r0.buf
            r8[r7] = r12
            int r13 = r7 + 1
            r8[r13] = r9
            int r11 = r11 + 1
            goto L_0x02a4
        L_0x0288:
            int r13 = r7 + 1
            int r14 = r7 + 2
            int r15 = r11 - r7
            r18 = 1
            int r15 = r15 + -1
            java.lang.System.arraycopy(r8, r13, r8, r14, r15)
            char[] r8 = r0.buf
            r8[r7] = r12
            int r13 = r7 + 1
            char[] r14 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r14 = r14[r9]
            r8[r13] = r14
            int r11 = r11 + 1
        L_0x02a4:
            int r7 = r7 + -1
            r14 = 12
            r15 = 8
            goto L_0x01cb
        L_0x02ac:
            if (r2 == 0) goto L_0x02bd
            char[] r7 = r0.buf
            int r8 = r0.count
            int r9 = r8 + -2
            r10 = 34
            r7[r9] = r10
            r9 = 1
            int r8 = r8 - r9
            r7[r8] = r2
            goto L_0x02c7
        L_0x02bd:
            r9 = 1
            r10 = 34
            char[] r7 = r0.buf
            int r8 = r0.count
            int r8 = r8 - r9
            r7[r8] = r10
        L_0x02c7:
            return
        L_0x02c8:
            r5 = 0
            r7 = -1
            r13 = -1
            r14 = 0
            r15 = r6
        L_0x02cd:
            r8 = 8232(0x2028, float:1.1535E-41)
            if (r15 >= r11) goto L_0x0350
            char[] r9 = r0.buf
            char r9 = r9[r15]
            r12 = 93
            r10 = -1
            if (r9 < r12) goto L_0x02f8
            r12 = 127(0x7f, float:1.78E-43)
            if (r9 < r12) goto L_0x02f5
            if (r9 == r8) goto L_0x02e8
            r8 = 8233(0x2029, float:1.1537E-41)
            if (r9 == r8) goto L_0x02e8
            r8 = 160(0xa0, float:2.24E-43)
            if (r9 >= r8) goto L_0x02f5
        L_0x02e8:
            if (r13 != r10) goto L_0x02eb
            r13 = r15
        L_0x02eb:
            int r5 = r5 + 1
            r7 = r15
            r8 = r9
            int r4 = r4 + 4
            r14 = r8
            r16 = r11
            goto L_0x0342
        L_0x02f5:
            r16 = r11
            goto L_0x0342
        L_0x02f8:
            r12 = 127(0x7f, float:1.78E-43)
            r8 = 64
            if (r9 >= r8) goto L_0x030f
            r16 = r11
            long r10 = r0.sepcialBits
            r25 = 1
            long r25 = r25 << r9
            long r10 = r10 & r25
            r25 = 0
            int r8 = (r10 > r25 ? 1 : (r10 == r25 ? 0 : -1))
            if (r8 != 0) goto L_0x0315
            goto L_0x0311
        L_0x030f:
            r16 = r11
        L_0x0311:
            r8 = 92
            if (r9 != r8) goto L_0x0317
        L_0x0315:
            r8 = 1
            goto L_0x0318
        L_0x0317:
            r8 = 0
        L_0x0318:
            if (r8 == 0) goto L_0x0342
            int r5 = r5 + 1
            r7 = r15
            r10 = r9
            r11 = 40
            if (r9 == r11) goto L_0x0338
            r11 = 41
            if (r9 == r11) goto L_0x0338
            r11 = 60
            if (r9 == r11) goto L_0x0338
            r11 = 62
            if (r9 == r11) goto L_0x0338
            byte[] r11 = com.alibaba.fastjson.util.IOUtils.specicalFlags_doubleQuotes
            int r14 = r11.length
            if (r9 >= r14) goto L_0x033a
            byte r11 = r11[r9]
            r14 = 4
            if (r11 != r14) goto L_0x033a
        L_0x0338:
            int r4 = r4 + 4
        L_0x033a:
            r11 = -1
            if (r13 != r11) goto L_0x0341
            r11 = r15
            r14 = r10
            r13 = r11
            goto L_0x0342
        L_0x0341:
            r14 = r10
        L_0x0342:
            int r15 = r15 + 1
            r11 = r16
            r8 = 41
            r9 = 40
            r10 = 117(0x75, float:1.64E-43)
            r12 = 92
            goto L_0x02cd
        L_0x0350:
            r16 = r11
            if (r5 <= 0) goto L_0x05ae
            int r4 = r4 + r5
            char[] r9 = r0.buf
            int r9 = r9.length
            if (r4 <= r9) goto L_0x035d
            r0.expandCapacity(r4)
        L_0x035d:
            r0.count = r4
            r9 = 1
            if (r5 != r9) goto L_0x0489
            r9 = 50
            if (r14 != r8) goto L_0x0394
            int r8 = r7 + 1
            int r10 = r7 + 6
            int r11 = r16 - r7
            r12 = 1
            int r11 = r11 - r12
            char[] r12 = r0.buf
            java.lang.System.arraycopy(r12, r8, r12, r10, r11)
            char[] r12 = r0.buf
            r15 = 92
            r12[r7] = r15
            int r7 = r7 + 1
            r15 = 117(0x75, float:1.64E-43)
            r12[r7] = r15
            r15 = 1
            int r7 = r7 + r15
            r12[r7] = r9
            int r7 = r7 + r15
            r17 = 48
            r12[r7] = r17
            int r7 = r7 + r15
            r12[r7] = r9
            int r7 = r7 + r15
            r9 = 56
            r12[r7] = r9
            r11 = r16
            goto L_0x05b0
        L_0x0394:
            r8 = 8233(0x2029, float:1.1537E-41)
            if (r14 != r8) goto L_0x03c6
            int r8 = r7 + 1
            int r10 = r7 + 6
            int r11 = r16 - r7
            r12 = 1
            int r11 = r11 - r12
            char[] r12 = r0.buf
            java.lang.System.arraycopy(r12, r8, r12, r10, r11)
            char[] r12 = r0.buf
            r15 = 92
            r12[r7] = r15
            int r7 = r7 + 1
            r15 = 117(0x75, float:1.64E-43)
            r12[r7] = r15
            r15 = 1
            int r7 = r7 + r15
            r12[r7] = r9
            int r7 = r7 + r15
            r17 = 48
            r12[r7] = r17
            int r7 = r7 + r15
            r12[r7] = r9
            int r7 = r7 + r15
            r9 = 57
            r12[r7] = r9
            r11 = r16
            goto L_0x05b0
        L_0x03c6:
            r8 = 40
            if (r14 == r8) goto L_0x0446
            r8 = 41
            if (r14 == r8) goto L_0x0446
            r8 = 60
            if (r14 == r8) goto L_0x0446
            r8 = 62
            if (r14 != r8) goto L_0x03d7
            goto L_0x0446
        L_0x03d7:
            r8 = r14
            byte[] r9 = com.alibaba.fastjson.util.IOUtils.specicalFlags_doubleQuotes
            int r10 = r9.length
            if (r8 >= r10) goto L_0x0427
            byte r9 = r9[r8]
            r10 = 4
            if (r9 != r10) goto L_0x0427
            int r9 = r7 + 1
            int r10 = r7 + 6
            int r11 = r16 - r7
            r12 = 1
            int r11 = r11 - r12
            char[] r12 = r0.buf
            java.lang.System.arraycopy(r12, r9, r12, r10, r11)
            r12 = r7
            char[] r15 = r0.buf
            int r17 = r12 + 1
            r18 = 92
            r15[r12] = r18
            int r12 = r17 + 1
            r18 = 117(0x75, float:1.64E-43)
            r15[r17] = r18
            int r17 = r12 + 1
            char[] r18 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r19 = r8 >>> 12
            r19 = r19 & 15
            char r19 = r18[r19]
            r15[r12] = r19
            int r12 = r17 + 1
            int r19 = r8 >>> 8
            r19 = r19 & 15
            char r19 = r18[r19]
            r15[r17] = r19
            int r17 = r12 + 1
            int r19 = r8 >>> 4
            r19 = r19 & 15
            char r19 = r18[r19]
            r15[r12] = r19
            int r12 = r17 + 1
            r19 = r8 & 15
            char r18 = r18[r19]
            r15[r17] = r18
            goto L_0x0442
        L_0x0427:
            int r9 = r7 + 1
            int r10 = r7 + 2
            int r11 = r16 - r7
            r12 = 1
            int r11 = r11 - r12
            char[] r12 = r0.buf
            java.lang.System.arraycopy(r12, r9, r12, r10, r11)
            char[] r12 = r0.buf
            r15 = 92
            r12[r7] = r15
            int r7 = r7 + 1
            char[] r15 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r15 = r15[r8]
            r12[r7] = r15
        L_0x0442:
            r11 = r16
            goto L_0x05b0
        L_0x0446:
            int r8 = r7 + 1
            int r9 = r7 + 6
            int r11 = r16 - r7
            r10 = 1
            int r11 = r11 - r10
            char[] r10 = r0.buf
            java.lang.System.arraycopy(r10, r8, r10, r9, r11)
            char[] r10 = r0.buf
            r12 = 92
            r10[r7] = r12
            int r7 = r7 + 1
            r12 = 117(0x75, float:1.64E-43)
            r10[r7] = r12
            r12 = r14
            r15 = 1
            int r7 = r7 + r15
            char[] r17 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r18 = r12 >>> 12
            r18 = r18 & 15
            char r18 = r17[r18]
            r10[r7] = r18
            int r7 = r7 + r15
            int r18 = r12 >>> 8
            r18 = r18 & 15
            char r18 = r17[r18]
            r10[r7] = r18
            int r7 = r7 + r15
            int r18 = r12 >>> 4
            r18 = r18 & 15
            char r18 = r17[r18]
            r10[r7] = r18
            int r7 = r7 + r15
            r18 = r12 & 15
            char r17 = r17[r18]
            r10[r7] = r17
            r11 = r16
            goto L_0x05b0
        L_0x0489:
            r15 = r9
            if (r5 <= r15) goto L_0x05ab
            int r9 = r13 - r6
            r10 = r13
            r11 = r9
        L_0x0490:
            int r12 = r1.length
            if (r11 >= r12) goto L_0x05a8
            char r12 = r1[r11]
            boolean r15 = r0.browserSecure
            if (r15 == 0) goto L_0x04eb
            r15 = 40
            if (r12 == r15) goto L_0x04aa
            r15 = 41
            if (r12 == r15) goto L_0x04aa
            r15 = 60
            if (r12 == r15) goto L_0x04aa
            r15 = 62
            if (r12 != r15) goto L_0x04eb
            goto L_0x04ac
        L_0x04aa:
            r15 = 62
        L_0x04ac:
            char[] r15 = r0.buf
            int r17 = r10 + 1
            r24 = 92
            r15[r10] = r24
            int r10 = r17 + 1
            r23 = 117(0x75, float:1.64E-43)
            r15[r17] = r23
            int r17 = r10 + 1
            char[] r25 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r26 = r12 >>> 12
            r26 = r26 & 15
            char r26 = r25[r26]
            r15[r10] = r26
            int r10 = r17 + 1
            int r26 = r12 >>> 8
            r26 = r26 & 15
            char r26 = r25[r26]
            r15[r17] = r26
            int r17 = r10 + 1
            int r26 = r12 >>> 4
            r26 = r26 & 15
            char r26 = r25[r26]
            r15[r10] = r26
            int r10 = r17 + 1
            r26 = r12 & 15
            char r25 = r25[r26]
            r15[r17] = r25
            int r16 = r16 + 5
            r15 = 4
            r23 = 117(0x75, float:1.64E-43)
            r24 = 92
            goto L_0x05a2
        L_0x04eb:
            byte[] r15 = com.alibaba.fastjson.util.IOUtils.specicalFlags_doubleQuotes
            int r8 = r15.length
            if (r12 >= r8) goto L_0x04f4
            byte r8 = r15[r12]
            if (r8 != 0) goto L_0x0500
        L_0x04f4:
            r8 = 47
            if (r12 != r8) goto L_0x0553
            com.alibaba.fastjson.serializer.SerializerFeature r8 = com.alibaba.fastjson.serializer.SerializerFeature.WriteSlashAsSpecial
            boolean r8 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r8)
            if (r8 == 0) goto L_0x0551
        L_0x0500:
            char[] r8 = r0.buf
            int r25 = r10 + 1
            r24 = 92
            r8[r10] = r24
            byte r10 = r15[r12]
            r15 = 4
            if (r10 != r15) goto L_0x0542
            int r10 = r25 + 1
            r18 = 117(0x75, float:1.64E-43)
            r8[r25] = r18
            int r18 = r10 + 1
            char[] r25 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r26 = r12 >>> 12
            r26 = r26 & 15
            char r26 = r25[r26]
            r8[r10] = r26
            int r10 = r18 + 1
            int r26 = r12 >>> 8
            r26 = r26 & 15
            char r26 = r25[r26]
            r8[r18] = r26
            int r18 = r10 + 1
            int r26 = r12 >>> 4
            r26 = r26 & 15
            char r26 = r25[r26]
            r8[r10] = r26
            int r10 = r18 + 1
            r26 = r12 & 15
            char r25 = r25[r26]
            r8[r18] = r25
            int r16 = r16 + 5
            r23 = 117(0x75, float:1.64E-43)
            r24 = 92
            goto L_0x05a2
        L_0x0542:
            int r10 = r25 + 1
            char[] r18 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r18 = r18[r12]
            r8[r25] = r18
            int r16 = r16 + 1
            r23 = 117(0x75, float:1.64E-43)
            r24 = 92
            goto L_0x05a2
        L_0x0551:
            r15 = 4
            goto L_0x0554
        L_0x0553:
            r15 = 4
        L_0x0554:
            r8 = 8232(0x2028, float:1.1535E-41)
            if (r12 == r8) goto L_0x056a
            r8 = 8233(0x2029, float:1.1537E-41)
            if (r12 != r8) goto L_0x055d
            goto L_0x056a
        L_0x055d:
            char[] r8 = r0.buf
            int r18 = r10 + 1
            r8[r10] = r12
            r10 = r18
            r23 = 117(0x75, float:1.64E-43)
            r24 = 92
            goto L_0x05a2
        L_0x056a:
            char[] r8 = r0.buf
            int r18 = r10 + 1
            r24 = 92
            r8[r10] = r24
            int r10 = r18 + 1
            r23 = 117(0x75, float:1.64E-43)
            r8[r18] = r23
            int r18 = r10 + 1
            char[] r25 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r26 = r12 >>> 12
            r26 = r26 & 15
            char r26 = r25[r26]
            r8[r10] = r26
            int r10 = r18 + 1
            int r26 = r12 >>> 8
            r26 = r26 & 15
            char r26 = r25[r26]
            r8[r18] = r26
            int r18 = r10 + 1
            int r26 = r12 >>> 4
            r26 = r26 & 15
            char r26 = r25[r26]
            r8[r10] = r26
            int r10 = r18 + 1
            r26 = r12 & 15
            char r25 = r25[r26]
            r8[r18] = r25
            int r16 = r16 + 5
        L_0x05a2:
            int r11 = r11 + 1
            r8 = 8232(0x2028, float:1.1535E-41)
            goto L_0x0490
        L_0x05a8:
            r11 = r16
            goto L_0x05b0
        L_0x05ab:
            r11 = r16
            goto L_0x05b0
        L_0x05ae:
            r11 = r16
        L_0x05b0:
            if (r2 == 0) goto L_0x05c1
            char[] r8 = r0.buf
            int r9 = r0.count
            int r10 = r9 + -2
            r12 = 34
            r8[r10] = r12
            r10 = 1
            int r9 = r9 - r10
            r8[r9] = r2
            goto L_0x05cb
        L_0x05c1:
            r10 = 1
            r12 = 34
            char[] r8 = r0.buf
            int r9 = r0.count
            int r9 = r9 - r10
            r8[r9] = r12
        L_0x05cb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeWriter.writeStringWithDoubleQuote(char[], char):void");
    }

    public void writeFieldNameDirect(String text) {
        int len = text.length();
        int newcount = this.count + len + 3;
        if (newcount > this.buf.length) {
            expandCapacity(newcount);
        }
        int i = this.count;
        char[] cArr = this.buf;
        cArr[i] = StringUtil.DOUBLE_QUOTE;
        text.getChars(0, len, cArr, i + 1);
        this.count = newcount;
        char[] cArr2 = this.buf;
        cArr2[newcount - 2] = StringUtil.DOUBLE_QUOTE;
        cArr2[newcount - 1] = ':';
    }

    public void write(List<String> list) {
        int offset;
        if (list.isEmpty()) {
            write("[]");
            return;
        }
        int offset2 = this.count;
        int initOffset = offset2;
        int i = 0;
        int list_size = list.size();
        while (i < list_size) {
            String text = list.get(i);
            boolean hasSpecial = false;
            if (text == null) {
                hasSpecial = true;
            } else {
                int len = text.length();
                for (int j = 0; j < len; j++) {
                    char ch = text.charAt(j);
                    boolean z = ch < ' ' || ch > '~' || ch == '\"' || ch == '\\';
                    hasSpecial = z;
                    if (z) {
                        break;
                    }
                }
            }
            if (hasSpecial) {
                this.count = initOffset;
                write(91);
                for (int j2 = 0; j2 < list.size(); j2++) {
                    String text2 = list.get(j2);
                    if (j2 != 0) {
                        write(44);
                    }
                    if (text2 == null) {
                        write(BuildConfig.TRAVIS);
                    } else {
                        writeStringWithDoubleQuote(text2, 0);
                    }
                }
                write(93);
                return;
            }
            int newcount = text.length() + offset2 + 3;
            if (i == list.size() - 1) {
                newcount++;
            }
            if (newcount > this.buf.length) {
                this.count = offset2;
                expandCapacity(newcount);
            }
            if (i == 0) {
                offset = offset2 + 1;
                this.buf[offset2] = '[';
            } else {
                this.buf[offset2] = StringUtil.COMMA;
                offset = offset2 + 1;
            }
            int offset3 = offset + 1;
            this.buf[offset] = StringUtil.DOUBLE_QUOTE;
            text.getChars(0, text.length(), this.buf, offset3);
            int offset4 = offset3 + text.length();
            this.buf[offset4] = StringUtil.DOUBLE_QUOTE;
            i++;
            offset2 = offset4 + 1;
        }
        this.buf[offset2] = ']';
        this.count = offset2 + 1;
    }

    public void writeFieldValue(char seperator, String name, char value) {
        write((int) seperator);
        writeFieldName(name);
        if (value == 0) {
            writeString("\u0000");
        } else {
            writeString(Character.toString(value));
        }
    }

    public void writeFieldValue(char seperator, String name, boolean value) {
        if (!this.quoteFieldNames) {
            write((int) seperator);
            writeFieldName(name);
            write(value);
            return;
        }
        int intSize = value ? 4 : 5;
        int nameLen = name.length();
        int newcount = this.count + nameLen + 4 + intSize;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write((int) seperator);
                writeString(name);
                write(58);
                write(value);
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count;
        this.count = newcount;
        char[] cArr = this.buf;
        cArr[start] = seperator;
        int nameEnd = start + nameLen + 1;
        cArr[start + 1] = this.keySeperator;
        name.getChars(0, nameLen, cArr, start + 2);
        char[] cArr2 = this.buf;
        cArr2[nameEnd + 1] = this.keySeperator;
        if (value) {
            System.arraycopy(VALUE_TRUE, 0, cArr2, nameEnd + 2, 5);
        } else {
            System.arraycopy(VALUE_FALSE, 0, cArr2, nameEnd + 2, 6);
        }
    }

    public void write(boolean value) {
        if (value) {
            write("true");
        } else {
            write(Bugly.SDK_IS_DEV);
        }
    }

    public void writeFieldValue(char seperator, String name, int value) {
        if (value == Integer.MIN_VALUE || !this.quoteFieldNames) {
            write((int) seperator);
            writeFieldName(name);
            writeInt(value);
            return;
        }
        int intSize = value < 0 ? IOUtils.stringSize(-value) + 1 : IOUtils.stringSize(value);
        int nameLen = name.length();
        int newcount = this.count + nameLen + 4 + intSize;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write((int) seperator);
                writeFieldName(name);
                writeInt(value);
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count;
        this.count = newcount;
        char[] cArr = this.buf;
        cArr[start] = seperator;
        int nameEnd = start + nameLen + 1;
        cArr[start + 1] = this.keySeperator;
        name.getChars(0, nameLen, cArr, start + 2);
        char[] cArr2 = this.buf;
        cArr2[nameEnd + 1] = this.keySeperator;
        cArr2[nameEnd + 2] = ':';
        IOUtils.getChars(value, this.count, cArr2);
    }

    public void writeFieldValue(char seperator, String name, long value) {
        if (value == Long.MIN_VALUE || !this.quoteFieldNames || isEnabled(SerializerFeature.BrowserCompatible.mask)) {
            write((int) seperator);
            writeFieldName(name);
            writeLong(value);
            return;
        }
        int intSize = value < 0 ? IOUtils.stringSize(-value) + 1 : IOUtils.stringSize(value);
        int nameLen = name.length();
        int newcount = this.count + nameLen + 4 + intSize;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write((int) seperator);
                writeFieldName(name);
                writeLong(value);
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count;
        this.count = newcount;
        char[] cArr = this.buf;
        cArr[start] = seperator;
        int nameEnd = start + nameLen + 1;
        cArr[start + 1] = this.keySeperator;
        name.getChars(0, nameLen, cArr, start + 2);
        char[] cArr2 = this.buf;
        cArr2[nameEnd + 1] = this.keySeperator;
        cArr2[nameEnd + 2] = ':';
        IOUtils.getChars(value, this.count, cArr2);
    }

    public void writeFieldValue(char seperator, String name, float value) {
        write((int) seperator);
        writeFieldName(name);
        writeFloat(value, false);
    }

    public void writeFieldValue(char seperator, String name, double value) {
        write((int) seperator);
        writeFieldName(name);
        writeDouble(value, false);
    }

    public void writeFieldValue(char seperator, String name, String value) {
        if (!this.quoteFieldNames) {
            write((int) seperator);
            writeFieldName(name);
            if (value == null) {
                writeNull();
            } else {
                writeString(value);
            }
        } else if (this.useSingleQuotes) {
            write((int) seperator);
            writeFieldName(name);
            if (value == null) {
                writeNull();
            } else {
                writeString(value);
            }
        } else if (isEnabled(SerializerFeature.BrowserCompatible)) {
            write((int) seperator);
            writeStringWithDoubleQuote(name, ':');
            writeStringWithDoubleQuote(value, 0);
        } else {
            writeFieldValueStringWithDoubleQuoteCheck(seperator, name, value);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:107:0x029a, code lost:
        if (r7 == '>') goto L_0x029f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c7, code lost:
        if ((r0.sepcialBits & (1 << r12)) == 0) goto L_0x00cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00f3, code lost:
        if (r1[r12] == 4) goto L_0x00f8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00d5  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ff  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0106  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x010b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeFieldValueStringWithDoubleQuoteCheck(char r33, java.lang.String r34, java.lang.String r35) {
        /*
            r32 = this;
            r0 = r32
            r1 = r34
            r2 = r35
            int r3 = r34.length()
            int r4 = r0.count
            if (r2 != 0) goto L_0x0013
            r5 = 4
            int r6 = r3 + 8
            int r4 = r4 + r6
            goto L_0x001c
        L_0x0013:
            int r5 = r35.length()
            int r6 = r3 + r5
            int r6 = r6 + 6
            int r4 = r4 + r6
        L_0x001c:
            char[] r6 = r0.buf
            int r6 = r6.length
            r7 = 58
            r8 = 0
            if (r4 <= r6) goto L_0x0035
            java.io.Writer r6 = r0.writer
            if (r6 == 0) goto L_0x0032
            r32.write((int) r33)
            r0.writeStringWithDoubleQuote((java.lang.String) r1, (char) r7)
            r0.writeStringWithDoubleQuote((java.lang.String) r2, (char) r8)
            return
        L_0x0032:
            r0.expandCapacity(r4)
        L_0x0035:
            char[] r6 = r0.buf
            int r9 = r0.count
            r6[r9] = r33
            int r10 = r9 + 2
            int r11 = r10 + r3
            r12 = 1
            int r9 = r9 + r12
            r13 = 34
            r6[r9] = r13
            r1.getChars(r8, r3, r6, r10)
            r0.count = r4
            char[] r6 = r0.buf
            r6[r11] = r13
            int r9 = r11 + 1
            int r14 = r9 + 1
            r6[r9] = r7
            r7 = 117(0x75, float:1.64E-43)
            if (r2 != 0) goto L_0x006d
            int r8 = r14 + 1
            r9 = 110(0x6e, float:1.54E-43)
            r6[r14] = r9
            int r9 = r8 + 1
            r6[r8] = r7
            int r7 = r9 + 1
            r8 = 108(0x6c, float:1.51E-43)
            r6[r9] = r8
            int r9 = r7 + 1
            r6[r7] = r8
            return
        L_0x006d:
            int r9 = r14 + 1
            r6[r14] = r13
            r14 = r9
            int r15 = r14 + r5
            r2.getChars(r8, r5, r6, r14)
            r6 = 0
            r16 = -1
            r17 = -1
            r18 = 0
            r19 = r14
            r8 = r17
            r13 = r18
            r7 = r19
        L_0x0086:
            if (r7 >= r15) goto L_0x0118
            char[] r12 = r0.buf
            char r12 = r12[r7]
            r1 = 93
            r28 = r3
            r3 = -1
            if (r12 < r1) goto L_0x00b5
            r1 = 127(0x7f, float:1.78E-43)
            if (r12 < r1) goto L_0x00b1
            r1 = 8232(0x2028, float:1.1535E-41)
            if (r12 == r1) goto L_0x00a3
            r1 = 8233(0x2029, float:1.1537E-41)
            if (r12 == r1) goto L_0x00a3
            r1 = 160(0xa0, float:2.24E-43)
            if (r12 >= r1) goto L_0x00b1
        L_0x00a3:
            if (r8 != r3) goto L_0x00a6
            r8 = r7
        L_0x00a6:
            int r6 = r6 + 1
            r1 = r7
            r3 = r12
            int r4 = r4 + 4
            r16 = r1
            r13 = r3
            goto L_0x010f
        L_0x00b1:
            r29 = r4
            goto L_0x010d
        L_0x00b5:
            r1 = 64
            if (r12 >= r1) goto L_0x00ca
            r29 = r4
            long r3 = r0.sepcialBits
            r24 = 1
            long r24 = r24 << r12
            long r3 = r3 & r24
            r24 = 0
            int r3 = (r3 > r24 ? 1 : (r3 == r24 ? 0 : -1))
            if (r3 != 0) goto L_0x00d0
            goto L_0x00cc
        L_0x00ca:
            r29 = r4
        L_0x00cc:
            r3 = 92
            if (r12 != r3) goto L_0x00d2
        L_0x00d0:
            r3 = 1
            goto L_0x00d3
        L_0x00d2:
            r3 = 0
        L_0x00d3:
            if (r3 == 0) goto L_0x010b
            int r6 = r6 + 1
            r4 = r7
            r13 = r12
            r1 = 40
            if (r12 == r1) goto L_0x00f6
            r1 = 41
            if (r12 == r1) goto L_0x00f6
            r1 = 60
            if (r12 == r1) goto L_0x00f6
            r1 = 62
            if (r12 == r1) goto L_0x00f6
            byte[] r1 = com.alibaba.fastjson.util.IOUtils.specicalFlags_doubleQuotes
            r21 = r3
            int r3 = r1.length
            if (r12 >= r3) goto L_0x00fc
            byte r1 = r1[r12]
            r3 = 4
            if (r1 != r3) goto L_0x00fc
            goto L_0x00f8
        L_0x00f6:
            r21 = r3
        L_0x00f8:
            int r1 = r29 + 4
            r29 = r1
        L_0x00fc:
            r1 = -1
            if (r8 != r1) goto L_0x0106
            r1 = r7
            r8 = r1
            r16 = r4
            r4 = r29
            goto L_0x010f
        L_0x0106:
            r16 = r4
            r4 = r29
            goto L_0x010f
        L_0x010b:
            r21 = r3
        L_0x010d:
            r4 = r29
        L_0x010f:
            int r7 = r7 + 1
            r1 = r34
            r3 = r28
            r12 = 1
            goto L_0x0086
        L_0x0118:
            r28 = r3
            r29 = r4
            if (r6 <= 0) goto L_0x03a7
            int r4 = r29 + r6
            char[] r1 = r0.buf
            int r1 = r1.length
            if (r4 <= r1) goto L_0x0128
            r0.expandCapacity(r4)
        L_0x0128:
            r0.count = r4
            r1 = 1
            if (r6 != r1) goto L_0x0275
            r3 = 8232(0x2028, float:1.1535E-41)
            if (r13 != r3) goto L_0x0166
            int r3 = r16 + 1
            int r7 = r16 + 6
            int r12 = r15 - r16
            r17 = 1
            int r12 = r12 + -1
            char[] r1 = r0.buf
            java.lang.System.arraycopy(r1, r3, r1, r7, r12)
            char[] r1 = r0.buf
            r21 = 92
            r1[r16] = r21
            int r16 = r16 + 1
            r19 = 117(0x75, float:1.64E-43)
            r1[r16] = r19
            r19 = 1
            int r16 = r16 + 1
            r17 = 50
            r1[r16] = r17
            int r16 = r16 + 1
            r20 = 48
            r1[r16] = r20
            int r16 = r16 + 1
            r1[r16] = r17
            int r16 = r16 + 1
            r17 = 56
            r1[r16] = r17
            goto L_0x03a9
        L_0x0166:
            r1 = 8233(0x2029, float:1.1537E-41)
            if (r13 != r1) goto L_0x019d
            int r1 = r16 + 1
            int r3 = r16 + 6
            int r7 = r15 - r16
            r12 = 1
            int r7 = r7 - r12
            char[] r12 = r0.buf
            java.lang.System.arraycopy(r12, r1, r12, r3, r7)
            char[] r12 = r0.buf
            r21 = 92
            r12[r16] = r21
            int r16 = r16 + 1
            r19 = 117(0x75, float:1.64E-43)
            r12[r16] = r19
            r19 = 1
            int r16 = r16 + 1
            r17 = 50
            r12[r16] = r17
            int r16 = r16 + 1
            r20 = 48
            r12[r16] = r20
            int r16 = r16 + 1
            r12[r16] = r17
            int r16 = r16 + 1
            r17 = 57
            r12[r16] = r17
            goto L_0x03a9
        L_0x019d:
            r1 = 40
            if (r13 == r1) goto L_0x0229
            r1 = 41
            if (r13 == r1) goto L_0x0229
            r1 = 60
            if (r13 == r1) goto L_0x0229
            r1 = 62
            if (r13 != r1) goto L_0x01b1
            r17 = r4
            goto L_0x022b
        L_0x01b1:
            r1 = r13
            byte[] r3 = com.alibaba.fastjson.util.IOUtils.specicalFlags_doubleQuotes
            int r7 = r3.length
            if (r1 >= r7) goto L_0x0208
            byte r3 = r3[r1]
            r7 = 4
            if (r3 != r7) goto L_0x0208
            int r3 = r16 + 1
            int r7 = r16 + 6
            int r12 = r15 - r16
            r17 = 1
            int r12 = r12 + -1
            r17 = r4
            char[] r4 = r0.buf
            java.lang.System.arraycopy(r4, r3, r4, r7, r12)
            r4 = r16
            r21 = r3
            char[] r3 = r0.buf
            int r22 = r4 + 1
            r23 = 92
            r3[r4] = r23
            int r4 = r22 + 1
            r19 = 117(0x75, float:1.64E-43)
            r3[r22] = r19
            int r19 = r4 + 1
            char[] r22 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r23 = r1 >>> 12
            r23 = r23 & 15
            char r23 = r22[r23]
            r3[r4] = r23
            int r4 = r19 + 1
            int r23 = r1 >>> 8
            r23 = r23 & 15
            char r23 = r22[r23]
            r3[r19] = r23
            int r19 = r4 + 1
            int r23 = r1 >>> 4
            r23 = r23 & 15
            char r23 = r22[r23]
            r3[r4] = r23
            int r4 = r19 + 1
            r23 = r1 & 15
            char r22 = r22[r23]
            r3[r19] = r22
            goto L_0x0225
        L_0x0208:
            r17 = r4
            int r3 = r16 + 1
            int r4 = r16 + 2
            int r7 = r15 - r16
            r12 = 1
            int r7 = r7 - r12
            char[] r12 = r0.buf
            java.lang.System.arraycopy(r12, r3, r12, r4, r7)
            char[] r12 = r0.buf
            r19 = 92
            r12[r16] = r19
            int r16 = r16 + 1
            char[] r19 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r19 = r19[r1]
            r12[r16] = r19
        L_0x0225:
            r4 = r17
            goto L_0x03a9
        L_0x0229:
            r17 = r4
        L_0x022b:
            r1 = r13
            int r3 = r16 + 1
            int r4 = r16 + 6
            int r7 = r15 - r16
            r12 = 1
            int r7 = r7 - r12
            char[] r12 = r0.buf
            java.lang.System.arraycopy(r12, r3, r12, r4, r7)
            r12 = r16
            r21 = r3
            char[] r3 = r0.buf
            int r22 = r12 + 1
            r23 = 92
            r3[r12] = r23
            int r12 = r22 + 1
            r19 = 117(0x75, float:1.64E-43)
            r3[r22] = r19
            int r19 = r12 + 1
            char[] r22 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r23 = r1 >>> 12
            r23 = r23 & 15
            char r23 = r22[r23]
            r3[r12] = r23
            int r12 = r19 + 1
            int r23 = r1 >>> 8
            r23 = r23 & 15
            char r23 = r22[r23]
            r3[r19] = r23
            int r19 = r12 + 1
            int r23 = r1 >>> 4
            r23 = r23 & 15
            char r23 = r22[r23]
            r3[r12] = r23
            int r12 = r19 + 1
            r23 = r1 & 15
            char r22 = r22[r23]
            r3[r19] = r22
            goto L_0x03a4
        L_0x0275:
            r17 = r4
            r1 = 1
            if (r6 <= r1) goto L_0x03a4
            int r1 = r8 - r14
            r3 = r8
            r4 = r1
        L_0x027e:
            int r7 = r35.length()
            if (r4 >= r7) goto L_0x039f
            char r7 = r2.charAt(r4)
            boolean r12 = r0.browserSecure
            if (r12 == 0) goto L_0x02e0
            r12 = 40
            if (r7 == r12) goto L_0x029d
            r12 = 41
            if (r7 == r12) goto L_0x029d
            r12 = 60
            if (r7 == r12) goto L_0x029d
            r12 = 62
            if (r7 != r12) goto L_0x02e0
            goto L_0x029f
        L_0x029d:
            r12 = 62
        L_0x029f:
            char[] r12 = r0.buf
            int r29 = r3 + 1
            r26 = 92
            r12[r3] = r26
            int r3 = r29 + 1
            r19 = 117(0x75, float:1.64E-43)
            r12[r29] = r19
            int r29 = r3 + 1
            char[] r30 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r31 = r7 >>> 12
            r31 = r31 & 15
            char r31 = r30[r31]
            r12[r3] = r31
            int r3 = r29 + 1
            int r31 = r7 >>> 8
            r31 = r31 & 15
            char r31 = r30[r31]
            r12[r29] = r31
            int r29 = r3 + 1
            int r31 = r7 >>> 4
            r31 = r31 & 15
            char r31 = r30[r31]
            r12[r3] = r31
            int r3 = r29 + 1
            r31 = r7 & 15
            char r30 = r30[r31]
            r12[r29] = r30
            int r15 = r15 + 5
            r29 = r1
            r12 = 4
            r19 = 117(0x75, float:1.64E-43)
            r26 = 92
            goto L_0x0399
        L_0x02e0:
            byte[] r12 = com.alibaba.fastjson.util.IOUtils.specicalFlags_doubleQuotes
            r29 = r1
            int r1 = r12.length
            if (r7 >= r1) goto L_0x02eb
            byte r1 = r12[r7]
            if (r1 != 0) goto L_0x02f7
        L_0x02eb:
            r1 = 47
            if (r7 != r1) goto L_0x034a
            com.alibaba.fastjson.serializer.SerializerFeature r1 = com.alibaba.fastjson.serializer.SerializerFeature.WriteSlashAsSpecial
            boolean r1 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r1)
            if (r1 == 0) goto L_0x0348
        L_0x02f7:
            char[] r1 = r0.buf
            int r30 = r3 + 1
            r26 = 92
            r1[r3] = r26
            byte r3 = r12[r7]
            r12 = 4
            if (r3 != r12) goto L_0x0339
            int r3 = r30 + 1
            r19 = 117(0x75, float:1.64E-43)
            r1[r30] = r19
            int r27 = r3 + 1
            char[] r30 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r31 = r7 >>> 12
            r31 = r31 & 15
            char r31 = r30[r31]
            r1[r3] = r31
            int r3 = r27 + 1
            int r31 = r7 >>> 8
            r31 = r31 & 15
            char r31 = r30[r31]
            r1[r27] = r31
            int r27 = r3 + 1
            int r31 = r7 >>> 4
            r31 = r31 & 15
            char r31 = r30[r31]
            r1[r3] = r31
            int r3 = r27 + 1
            r31 = r7 & 15
            char r30 = r30[r31]
            r1[r27] = r30
            int r15 = r15 + 5
            r19 = 117(0x75, float:1.64E-43)
            r26 = 92
            goto L_0x0399
        L_0x0339:
            int r3 = r30 + 1
            char[] r27 = com.alibaba.fastjson.util.IOUtils.replaceChars
            char r27 = r27[r7]
            r1[r30] = r27
            int r15 = r15 + 1
            r19 = 117(0x75, float:1.64E-43)
            r26 = 92
            goto L_0x0399
        L_0x0348:
            r12 = 4
            goto L_0x034b
        L_0x034a:
            r12 = 4
        L_0x034b:
            r1 = 8232(0x2028, float:1.1535E-41)
            if (r7 == r1) goto L_0x0361
            r1 = 8233(0x2029, float:1.1537E-41)
            if (r7 != r1) goto L_0x0354
            goto L_0x0361
        L_0x0354:
            char[] r1 = r0.buf
            int r27 = r3 + 1
            r1[r3] = r7
            r3 = r27
            r19 = 117(0x75, float:1.64E-43)
            r26 = 92
            goto L_0x0399
        L_0x0361:
            char[] r1 = r0.buf
            int r27 = r3 + 1
            r26 = 92
            r1[r3] = r26
            int r3 = r27 + 1
            r19 = 117(0x75, float:1.64E-43)
            r1[r27] = r19
            int r27 = r3 + 1
            char[] r30 = com.alibaba.fastjson.util.IOUtils.DIGITS
            int r31 = r7 >>> 12
            r31 = r31 & 15
            char r31 = r30[r31]
            r1[r3] = r31
            int r3 = r27 + 1
            int r31 = r7 >>> 8
            r31 = r31 & 15
            char r31 = r30[r31]
            r1[r27] = r31
            int r27 = r3 + 1
            int r31 = r7 >>> 4
            r31 = r31 & 15
            char r31 = r30[r31]
            r1[r3] = r31
            int r3 = r27 + 1
            r31 = r7 & 15
            char r30 = r30[r31]
            r1[r27] = r30
            int r15 = r15 + 5
        L_0x0399:
            int r4 = r4 + 1
            r1 = r29
            goto L_0x027e
        L_0x039f:
            r29 = r1
            r4 = r17
            goto L_0x03a9
        L_0x03a4:
            r4 = r17
            goto L_0x03a9
        L_0x03a7:
            r4 = r29
        L_0x03a9:
            char[] r1 = r0.buf
            int r3 = r0.count
            r7 = 1
            int r3 = r3 - r7
            r7 = 34
            r1[r3] = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeWriter.writeFieldValueStringWithDoubleQuoteCheck(char, java.lang.String, java.lang.String):void");
    }

    public void writeFieldValueStringWithDoubleQuote(char seperator, String name, String value) {
        int nameLen = name.length();
        int newcount = this.count;
        int valueLen = value.length();
        int newcount2 = newcount + nameLen + valueLen + 6;
        if (newcount2 > this.buf.length) {
            if (this.writer != null) {
                write((int) seperator);
                writeStringWithDoubleQuote(name, ':');
                writeStringWithDoubleQuote(value, 0);
                return;
            }
            expandCapacity(newcount2);
        }
        char[] cArr = this.buf;
        int i = this.count;
        cArr[i] = seperator;
        int nameStart = i + 2;
        int nameEnd = nameStart + nameLen;
        cArr[i + 1] = StringUtil.DOUBLE_QUOTE;
        name.getChars(0, nameLen, cArr, nameStart);
        this.count = newcount2;
        char[] cArr2 = this.buf;
        cArr2[nameEnd] = StringUtil.DOUBLE_QUOTE;
        int index = nameEnd + 1;
        int index2 = index + 1;
        cArr2[index] = ':';
        cArr2[index2] = StringUtil.DOUBLE_QUOTE;
        value.getChars(0, valueLen, cArr2, index2 + 1);
        this.buf[this.count - 1] = StringUtil.DOUBLE_QUOTE;
    }

    public void writeFieldValue(char seperator, String name, Enum<?> value) {
        if (value == null) {
            write((int) seperator);
            writeFieldName(name);
            writeNull();
        } else if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            writeEnumFieldValue(seperator, name, value.name());
        } else if (this.writeEnumUsingToString) {
            writeEnumFieldValue(seperator, name, value.toString());
        } else {
            writeFieldValue(seperator, name, value.ordinal());
        }
    }

    private void writeEnumFieldValue(char seperator, String name, String value) {
        if (this.useSingleQuotes) {
            writeFieldValue(seperator, name, value);
        } else {
            writeFieldValueStringWithDoubleQuote(seperator, name, value);
        }
    }

    public void writeFieldValue(char seperator, String name, BigDecimal value) {
        String str;
        write((int) seperator);
        writeFieldName(name);
        if (value == null) {
            writeNull();
            return;
        }
        int scale = value.scale();
        if (!isEnabled(SerializerFeature.WriteBigDecimalAsPlain) || scale < -100 || scale >= 100) {
            str = value.toString();
        } else {
            str = value.toPlainString();
        }
        write(str);
    }

    public void writeString(String text, char seperator) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(text);
            write((int) seperator);
            return;
        }
        writeStringWithDoubleQuote(text, seperator);
    }

    public void writeString(String text) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(text);
        } else {
            writeStringWithDoubleQuote(text, 0);
        }
    }

    public void writeString(char[] chars) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(chars);
        } else {
            writeStringWithDoubleQuote(new String(chars), 0);
        }
    }

    /* access modifiers changed from: protected */
    public void writeStringWithSingleQuote(String text) {
        String str = text;
        if (str == null) {
            int newcount = this.count + 4;
            if (newcount > this.buf.length) {
                expandCapacity(newcount);
            }
            BuildConfig.TRAVIS.getChars(0, 4, this.buf, this.count);
            this.count = newcount;
            return;
        }
        int len = text.length();
        int newcount2 = this.count + len + 2;
        int length = this.buf.length;
        char c = StringUtil.CARRIAGE_RETURN;
        char c2 = '\\';
        if (newcount2 > length) {
            if (this.writer != null) {
                write(39);
                for (int i = 0; i < text.length(); i++) {
                    char ch = str.charAt(i);
                    if (ch <= 13 || ch == '\\' || ch == '\'' || (ch == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write((int) IOUtils.replaceChars[ch]);
                    } else {
                        write((int) ch);
                    }
                }
                write(39);
                return;
            }
            expandCapacity(newcount2);
        }
        int i2 = this.count;
        int start = i2 + 1;
        int end = start + len;
        char[] cArr = this.buf;
        cArr[i2] = '\'';
        str.getChars(0, len, cArr, start);
        this.count = newcount2;
        int specialCount = 0;
        int lastSpecialIndex = -1;
        char lastSpecial = 0;
        for (int i3 = start; i3 < end; i3++) {
            char ch2 = this.buf[i3];
            if (ch2 <= 13 || ch2 == '\\' || ch2 == '\'' || (ch2 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                specialCount++;
                lastSpecialIndex = i3;
                lastSpecial = ch2;
            }
        }
        int newcount3 = newcount2 + specialCount;
        if (newcount3 > this.buf.length) {
            expandCapacity(newcount3);
        }
        this.count = newcount3;
        if (specialCount == 1) {
            char[] cArr2 = this.buf;
            System.arraycopy(cArr2, lastSpecialIndex + 1, cArr2, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            char[] cArr3 = this.buf;
            cArr3[lastSpecialIndex] = '\\';
            cArr3[lastSpecialIndex + 1] = IOUtils.replaceChars[lastSpecial];
        } else if (specialCount > 1) {
            char[] cArr4 = this.buf;
            System.arraycopy(cArr4, lastSpecialIndex + 1, cArr4, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            char[] cArr5 = this.buf;
            cArr5[lastSpecialIndex] = '\\';
            int lastSpecialIndex2 = lastSpecialIndex + 1;
            cArr5[lastSpecialIndex2] = IOUtils.replaceChars[lastSpecial];
            int end2 = end + 1;
            int i4 = lastSpecialIndex2 - 2;
            while (i4 >= start) {
                char ch3 = this.buf[i4];
                if (ch3 > c && ch3 != c2 && ch3 != '\'') {
                    if (ch3 == '/') {
                        if (!isEnabled(SerializerFeature.WriteSlashAsSpecial)) {
                        }
                    }
                    i4--;
                    c = StringUtil.CARRIAGE_RETURN;
                }
                char[] cArr6 = this.buf;
                System.arraycopy(cArr6, i4 + 1, cArr6, i4 + 2, (end2 - i4) - 1);
                char[] cArr7 = this.buf;
                c2 = '\\';
                cArr7[i4] = '\\';
                cArr7[i4 + 1] = IOUtils.replaceChars[ch3];
                end2++;
                i4--;
                c = StringUtil.CARRIAGE_RETURN;
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    /* access modifiers changed from: protected */
    public void writeStringWithSingleQuote(char[] chars) {
        char[] cArr = chars;
        if (cArr == null) {
            int newcount = this.count + 4;
            if (newcount > this.buf.length) {
                expandCapacity(newcount);
            }
            BuildConfig.TRAVIS.getChars(0, 4, this.buf, this.count);
            this.count = newcount;
            return;
        }
        int len = cArr.length;
        int newcount2 = this.count + len + 2;
        int length = this.buf.length;
        char c = StringUtil.CARRIAGE_RETURN;
        char c2 = '\\';
        if (newcount2 > length) {
            if (this.writer != null) {
                write(39);
                for (char ch : cArr) {
                    if (ch <= 13 || ch == '\\' || ch == '\'' || (ch == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write((int) IOUtils.replaceChars[ch]);
                    } else {
                        write((int) ch);
                    }
                }
                write(39);
                return;
            }
            expandCapacity(newcount2);
        }
        int i = this.count;
        int start = i + 1;
        int end = start + len;
        char[] cArr2 = this.buf;
        cArr2[i] = '\'';
        System.arraycopy(cArr, 0, cArr2, start, cArr.length);
        this.count = newcount2;
        int specialCount = 0;
        int lastSpecialIndex = -1;
        char lastSpecial = 0;
        for (int i2 = start; i2 < end; i2++) {
            char ch2 = this.buf[i2];
            if (ch2 <= 13 || ch2 == '\\' || ch2 == '\'' || (ch2 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                specialCount++;
                lastSpecialIndex = i2;
                lastSpecial = ch2;
            }
        }
        int newcount3 = newcount2 + specialCount;
        if (newcount3 > this.buf.length) {
            expandCapacity(newcount3);
        }
        this.count = newcount3;
        if (specialCount == 1) {
            char[] cArr3 = this.buf;
            System.arraycopy(cArr3, lastSpecialIndex + 1, cArr3, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            char[] cArr4 = this.buf;
            cArr4[lastSpecialIndex] = '\\';
            cArr4[lastSpecialIndex + 1] = IOUtils.replaceChars[lastSpecial];
        } else if (specialCount > 1) {
            char[] cArr5 = this.buf;
            System.arraycopy(cArr5, lastSpecialIndex + 1, cArr5, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            char[] cArr6 = this.buf;
            cArr6[lastSpecialIndex] = '\\';
            int lastSpecialIndex2 = lastSpecialIndex + 1;
            cArr6[lastSpecialIndex2] = IOUtils.replaceChars[lastSpecial];
            int end2 = end + 1;
            int i3 = lastSpecialIndex2 - 2;
            while (i3 >= start) {
                char ch3 = this.buf[i3];
                if (ch3 > c && ch3 != c2 && ch3 != '\'') {
                    if (ch3 == '/') {
                        if (!isEnabled(SerializerFeature.WriteSlashAsSpecial)) {
                        }
                    }
                    i3--;
                    c = StringUtil.CARRIAGE_RETURN;
                }
                char[] cArr7 = this.buf;
                System.arraycopy(cArr7, i3 + 1, cArr7, i3 + 2, (end2 - i3) - 1);
                char[] cArr8 = this.buf;
                c2 = '\\';
                cArr8[i3] = '\\';
                cArr8[i3 + 1] = IOUtils.replaceChars[ch3];
                end2++;
                i3--;
                c = StringUtil.CARRIAGE_RETURN;
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    public void writeFieldName(String key) {
        writeFieldName(key, false);
    }

    public void writeFieldName(String key, boolean checkSpecial) {
        if (key == null) {
            write("null:");
        } else if (this.useSingleQuotes) {
            if (this.quoteFieldNames) {
                writeStringWithSingleQuote(key);
                write(58);
                return;
            }
            writeKeyWithSingleQuoteIfHasSpecial(key);
        } else if (this.quoteFieldNames) {
            writeStringWithDoubleQuote(key, ':');
        } else {
            boolean hashSpecial = key.length() == 0;
            int i = 0;
            while (true) {
                if (i >= key.length()) {
                    break;
                }
                char ch = key.charAt(i);
                if ((ch < '@' && (this.sepcialBits & (1 << ch)) != 0) || ch == '\\') {
                    hashSpecial = true;
                    break;
                }
                i++;
            }
            if (hashSpecial) {
                writeStringWithDoubleQuote(key, ':');
                return;
            }
            write(key);
            write(58);
        }
    }

    private void writeKeyWithSingleQuoteIfHasSpecial(String text) {
        char c;
        String str = text;
        byte[] specicalFlags_singleQuotes = IOUtils.specicalFlags_singleQuotes;
        int len = text.length();
        int newcount = this.count + len + 1;
        char c2 = '\\';
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else if (len == 0) {
                write(39);
                write(39);
                write(58);
                return;
            } else {
                boolean hasSpecial = false;
                int i = 0;
                while (true) {
                    if (i < len) {
                        char ch = str.charAt(i);
                        if (ch < specicalFlags_singleQuotes.length && specicalFlags_singleQuotes[ch] != 0) {
                            hasSpecial = true;
                            break;
                        }
                        i++;
                    } else {
                        break;
                    }
                }
                if (hasSpecial) {
                    write(39);
                }
                for (int i2 = 0; i2 < len; i2++) {
                    char ch2 = str.charAt(i2);
                    if (ch2 >= specicalFlags_singleQuotes.length || specicalFlags_singleQuotes[ch2] == 0) {
                        write((int) ch2);
                    } else {
                        write(92);
                        write((int) IOUtils.replaceChars[ch2]);
                    }
                }
                if (hasSpecial) {
                    write(39);
                }
                write(58);
                return;
            }
        }
        if (len == 0) {
            int i3 = this.count;
            if (i3 + 3 > this.buf.length) {
                expandCapacity(i3 + 3);
            }
            char[] cArr = this.buf;
            int i4 = this.count;
            int i5 = i4 + 1;
            this.count = i5;
            cArr[i4] = '\'';
            int i6 = i5 + 1;
            this.count = i6;
            cArr[i5] = '\'';
            this.count = i6 + 1;
            cArr[i6] = ':';
            return;
        }
        int newCount = this.count;
        int end = newCount + len;
        str.getChars(0, len, this.buf, newCount);
        this.count = newcount;
        boolean hasSpecial2 = false;
        int i7 = newCount;
        while (i7 < end) {
            char[] cArr2 = this.buf;
            char ch3 = cArr2[i7];
            if (ch3 >= specicalFlags_singleQuotes.length || specicalFlags_singleQuotes[ch3] == 0) {
                c = c2;
            } else if (!hasSpecial2) {
                newcount += 3;
                if (newcount > cArr2.length) {
                    expandCapacity(newcount);
                }
                this.count = newcount;
                char[] cArr3 = this.buf;
                System.arraycopy(cArr3, i7 + 1, cArr3, i7 + 3, (end - i7) - 1);
                char[] cArr4 = this.buf;
                System.arraycopy(cArr4, 0, cArr4, 1, i7);
                char[] cArr5 = this.buf;
                cArr5[newCount] = '\'';
                int i8 = i7 + 1;
                cArr5[i8] = '\\';
                i7 = i8 + 1;
                cArr5[i7] = IOUtils.replaceChars[ch3];
                end += 2;
                cArr5[this.count - 2] = '\'';
                hasSpecial2 = true;
                c = '\\';
            } else {
                newcount++;
                if (newcount > cArr2.length) {
                    expandCapacity(newcount);
                }
                this.count = newcount;
                char[] cArr6 = this.buf;
                System.arraycopy(cArr6, i7 + 1, cArr6, i7 + 2, end - i7);
                char[] cArr7 = this.buf;
                c = '\\';
                cArr7[i7] = '\\';
                i7++;
                cArr7[i7] = IOUtils.replaceChars[ch3];
                end++;
            }
            i7++;
            c2 = c;
        }
        this.buf[newcount - 1] = ':';
    }

    public void flush() {
        Writer writer2 = this.writer;
        if (writer2 != null) {
            try {
                writer2.write(this.buf, 0, this.count);
                this.writer.flush();
                this.count = 0;
            } catch (IOException e) {
                throw new JSONException(e.getMessage(), e);
            }
        }
    }

    public void reset() {
        this.count = 0;
    }
}
