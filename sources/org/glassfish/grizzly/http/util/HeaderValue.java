package org.glassfish.grizzly.http.util;

public final class HeaderValue {
    public static final HeaderValue IDENTITY = newHeaderValue("identity").prepare();
    private byte[] preparedByteArray;
    private final String value;

    public static HeaderValue newHeaderValue(String value2) {
        return new HeaderValue(value2);
    }

    private HeaderValue(String value2) {
        this.value = value2;
    }

    public HeaderValue prepare() {
        if (this.preparedByteArray == null) {
            getByteArray();
        }
        return this;
    }

    public boolean isSet() {
        return this.value != null;
    }

    public String get() {
        return this.value;
    }

    public byte[] getByteArray() {
        byte[] bArr = this.preparedByteArray;
        if (bArr != null) {
            return bArr;
        }
        String str = this.value;
        if (str == null) {
            return HttpCodecUtils.EMPTY_ARRAY;
        }
        byte[] checkedByteArray = HttpCodecUtils.toCheckedByteArray(str);
        this.preparedByteArray = checkedByteArray;
        return checkedByteArray;
    }

    public String toString() {
        return this.value;
    }

    public void serializeToDataChunk(DataChunk dc) {
        byte[] bArr = this.preparedByteArray;
        if (bArr != null) {
            dc.setBytes(bArr);
        } else {
            dc.setString(this.value);
        }
    }
}
