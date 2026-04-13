package org.glassfish.grizzly.http.util;

import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import org.glassfish.grizzly.utils.Charsets;

public class ContentType {
    private static final byte[] CHARSET_BYTES = CHARSET_STRING.getBytes(Charsets.ASCII_CHARSET);
    private static final String CHARSET_STRING = ";charset=";
    private byte[] array = new byte[32];
    private String characterEncoding;
    private String compiledContentType;
    private byte[] compiledContentTypeArray;
    private boolean isCharsetSet;
    private boolean isParsed = true;
    private int len = -1;
    private String mimeType;
    private String quotedCharsetValue;
    private String unparsedContentType;

    public static SettableContentType newSettableContentType() {
        return new SettableContentType();
    }

    public static ContentType newContentType(String contentType) {
        return new ContentType(contentType);
    }

    public static ContentType newContentType(String mimeType2, String characterEncoding2) {
        ContentType ct = new ContentType();
        ct.setMimeType(mimeType2);
        ct.setCharacterEncoding(characterEncoding2);
        return ct;
    }

    ContentType() {
    }

    ContentType(String contentType) {
        set(contentType);
    }

    public ContentType prepare() {
        getByteArray();
        return this;
    }

    public boolean isSet() {
        return isMimeTypeSet() || this.quotedCharsetValue != null;
    }

    public boolean isMimeTypeSet() {
        return !this.isParsed || this.mimeType != null;
    }

    public String getMimeType() {
        if (!this.isParsed) {
            parse();
        }
        return this.mimeType;
    }

    /* access modifiers changed from: protected */
    public void setMimeType(String mimeType2) {
        if (!this.isParsed) {
            parse();
        }
        this.mimeType = mimeType2;
        this.compiledContentType = !this.isCharsetSet ? mimeType2 : null;
        this.compiledContentTypeArray = null;
    }

    public String getCharacterEncoding() {
        if (!this.isParsed) {
            parse();
        }
        return this.characterEncoding;
    }

    /* access modifiers changed from: protected */
    public void setCharacterEncoding(String charset) {
        if (!this.isParsed) {
            parse();
        }
        this.quotedCharsetValue = charset;
        boolean z = charset != null;
        this.isCharsetSet = z;
        if (z) {
            this.compiledContentType = null;
            this.characterEncoding = charset.replace(StringUtil.DOUBLE_QUOTE, ' ').trim();
        } else {
            this.compiledContentType = this.mimeType;
            this.characterEncoding = null;
        }
        this.compiledContentTypeArray = null;
    }

    public int getArrayLen() {
        return this.len;
    }

    public byte[] getByteArray() {
        byte[] bArr = this.compiledContentTypeArray;
        if (bArr != null) {
            return bArr;
        }
        String str = this.compiledContentType;
        if (str != null) {
            checkArray(str.length());
            this.compiledContentTypeArray = HttpCodecUtils.toCheckedByteArray(this.compiledContentType, this.array, 0);
            this.len = this.compiledContentType.length();
            return this.compiledContentTypeArray;
        }
        if (!this.isParsed) {
            if (this.quotedCharsetValue == null) {
                checkArray(this.unparsedContentType.length());
                byte[] checkedByteArray = HttpCodecUtils.toCheckedByteArray(this.unparsedContentType, this.array, 0);
                this.compiledContentTypeArray = checkedByteArray;
                return checkedByteArray;
            }
            parse();
        }
        String str2 = this.mimeType;
        if (str2 == null) {
            return HttpCodecUtils.EMPTY_ARRAY;
        }
        int mtsz = str2.length();
        if (this.isCharsetSet) {
            byte[] bArr2 = CHARSET_BYTES;
            int len2 = mtsz + this.quotedCharsetValue.length() + bArr2.length;
            checkArray(len2);
            HttpCodecUtils.toCheckedByteArray(this.mimeType, this.array, 0);
            System.arraycopy(bArr2, 0, this.array, mtsz, bArr2.length);
            HttpCodecUtils.toCheckedByteArray(this.quotedCharsetValue, this.array, bArr2.length + mtsz);
            this.len = len2;
        } else {
            checkArray(mtsz);
            HttpCodecUtils.toCheckedByteArray(this.mimeType, this.array, 0);
            this.len = mtsz;
        }
        byte[] bArr3 = this.array;
        this.compiledContentTypeArray = bArr3;
        return bArr3;
    }

    public String get() {
        String str = this.compiledContentType;
        if (str != null) {
            return str;
        }
        if (!this.isParsed) {
            parse();
        }
        String ret = this.mimeType;
        if (ret != null && this.isCharsetSet) {
            StringBuilder sb = new StringBuilder(ret.length() + this.quotedCharsetValue.length() + 9);
            sb.append(ret);
            sb.append(CHARSET_STRING);
            sb.append(this.quotedCharsetValue);
            ret = sb.toString();
        }
        this.compiledContentType = ret;
        return ret;
    }

    /* access modifiers changed from: protected */
    public void set(String contentType) {
        if (this.unparsedContentType != null) {
            parse();
        }
        this.unparsedContentType = contentType;
        this.isParsed = contentType == null;
        this.mimeType = null;
        this.compiledContentType = !this.isCharsetSet ? contentType : null;
        this.compiledContentTypeArray = null;
    }

    /* access modifiers changed from: protected */
    public void set(ContentType contentType) {
        if (contentType == null) {
            set((String) null);
            return;
        }
        this.unparsedContentType = contentType.unparsedContentType;
        this.isParsed = contentType.isParsed;
        this.mimeType = contentType.mimeType;
        this.characterEncoding = contentType.characterEncoding;
        this.quotedCharsetValue = contentType.quotedCharsetValue;
        this.isCharsetSet = contentType.isCharsetSet;
        this.compiledContentType = contentType.compiledContentType;
        this.compiledContentTypeArray = contentType.compiledContentTypeArray;
        this.array = contentType.array;
        this.len = contentType.len;
    }

    private void parse() {
        int index;
        String charsetValue;
        this.isParsed = true;
        String type = this.unparsedContentType;
        boolean hasCharset = false;
        int semicolonIndex = -1;
        int index2 = type.indexOf(59);
        while (true) {
            if (index == -1) {
                break;
            }
            int len2 = type.length();
            semicolonIndex = index;
            index++;
            while (index < len2 && type.charAt(index) == ' ') {
                index++;
            }
            if (index + 8 < len2 && type.charAt(index) == 'c' && type.charAt(index + 1) == 'h' && type.charAt(index + 2) == 'a' && type.charAt(index + 3) == 'r' && type.charAt(index + 4) == 's' && type.charAt(index + 5) == 'e' && type.charAt(index + 6) == 't' && type.charAt(index + 7) == '=') {
                hasCharset = true;
                break;
            }
            index2 = type.indexOf(59, index);
        }
        if (!hasCharset) {
            this.mimeType = type;
            return;
        }
        this.mimeType = type.substring(0, semicolonIndex);
        String tail = type.substring(index + 8);
        int nextParam = tail.indexOf(59);
        if (nextParam != -1) {
            this.mimeType += tail.substring(nextParam);
            charsetValue = tail.substring(0, nextParam);
        } else {
            charsetValue = tail;
        }
        if (charsetValue != null && charsetValue.length() > 0) {
            this.isCharsetSet = true;
            this.quotedCharsetValue = charsetValue;
            this.characterEncoding = charsetValue.replace(StringUtil.DOUBLE_QUOTE, ' ').trim();
        }
    }

    public void serializeToDataChunk(DataChunk dc) {
        byte[] bArr = this.compiledContentTypeArray;
        if (bArr != null) {
            dc.setBytes(bArr, 0, this.len);
            return;
        }
        String str = this.compiledContentType;
        if (str != null) {
            dc.setString(str);
        } else {
            dc.setBytes(getByteArray(), 0, this.len);
        }
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.unparsedContentType = null;
        this.compiledContentType = null;
        this.quotedCharsetValue = null;
        this.characterEncoding = null;
        this.compiledContentTypeArray = null;
        this.mimeType = null;
        this.isCharsetSet = false;
        this.isParsed = true;
        this.len = -1;
    }

    public String toString() {
        return get();
    }

    public static String getCharsetFromContentType(String contentType) {
        int start;
        if (contentType == null || (start = contentType.indexOf("charset=")) < 0) {
            return null;
        }
        String encoding = contentType.substring(start + 8);
        int end = encoding.indexOf(59);
        if (end >= 0) {
            encoding = encoding.substring(0, end);
        }
        String encoding2 = encoding.trim();
        if (encoding2.length() > 2 && encoding2.startsWith("\"") && encoding2.endsWith("\"")) {
            encoding2 = encoding2.substring(1, encoding2.length() - 1);
        }
        return encoding2.trim();
    }

    public static byte[] removeCharset(byte[] contentType) {
        int ctLen = contentType.length;
        boolean hasCharset = false;
        int semicolon1Index = -1;
        int semicolon2Index = -1;
        int index = ByteChunk.indexOf(contentType, 0, ctLen, ';');
        while (true) {
            if (index == -1) {
                break;
            }
            semicolon1Index = index;
            int index2 = index + 1;
            while (index2 < ctLen && contentType[index2] == 32) {
                index2++;
            }
            if (index2 + 8 < ctLen && contentType[index2] == 99 && contentType[index2 + 1] == 104 && contentType[index2 + 2] == 97 && contentType[index2 + 3] == 114 && contentType[index2 + 4] == 115 && contentType[index2 + 5] == 101 && contentType[index2 + 6] == 116 && contentType[index2 + 7] == 61) {
                semicolon2Index = ByteChunk.indexOf(contentType, index2 + 8, ctLen, ';');
                hasCharset = true;
                break;
            }
            index = ByteChunk.indexOf(contentType, index2, ctLen, ';');
        }
        if (!hasCharset) {
            return contentType;
        }
        if (semicolon2Index == -1) {
            return Arrays.copyOf(contentType, semicolon1Index);
        }
        byte[] array2 = new byte[(ctLen - (semicolon2Index - semicolon1Index))];
        System.arraycopy(contentType, 0, array2, 0, semicolon1Index);
        System.arraycopy(contentType, semicolon2Index, array2, semicolon1Index, ctLen - semicolon2Index);
        return array2;
    }

    public static byte[] compose(byte[] mimeType2, String charset) {
        int csLen = charset.length();
        byte[] bArr = CHARSET_BYTES;
        byte[] contentType = Arrays.copyOf(mimeType2, mimeType2.length + bArr.length + csLen);
        System.arraycopy(bArr, 0, contentType, mimeType2.length, bArr.length);
        return HttpCodecUtils.toCheckedByteArray(charset, contentType, mimeType2.length + bArr.length);
    }

    private void checkArray(int len2) {
        if (len2 > this.array.length) {
            this.array = new byte[(len2 << 1)];
        }
    }

    public static final class SettableContentType extends ContentType {
        SettableContentType() {
        }

        public void reset() {
            ContentType.super.reset();
        }

        public void set(ContentType contentType) {
            ContentType.super.set(contentType);
        }

        public void set(String type) {
            ContentType.super.set(type);
        }

        public void setCharacterEncoding(String charset) {
            ContentType.super.setCharacterEncoding(charset);
        }

        public void setMimeType(String mimeType) {
            ContentType.super.setMimeType(mimeType);
        }
    }
}
