package androidx.camera.core.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class ExifAttribute {
    static final Charset ASCII = StandardCharsets.US_ASCII;
    public static final long BYTES_OFFSET_UNKNOWN = -1;
    static final byte[] EXIF_ASCII_PREFIX = {65, 83, 67, 73, 73, 0, 0, 0};
    static final int IFD_FORMAT_BYTE = 1;
    static final int[] IFD_FORMAT_BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    static final int IFD_FORMAT_DOUBLE = 12;
    static final String[] IFD_FORMAT_NAMES = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};
    static final int IFD_FORMAT_SBYTE = 6;
    static final int IFD_FORMAT_SINGLE = 11;
    static final int IFD_FORMAT_SLONG = 9;
    static final int IFD_FORMAT_SRATIONAL = 10;
    static final int IFD_FORMAT_SSHORT = 8;
    static final int IFD_FORMAT_STRING = 2;
    static final int IFD_FORMAT_ULONG = 4;
    static final int IFD_FORMAT_UNDEFINED = 7;
    static final int IFD_FORMAT_URATIONAL = 5;
    static final int IFD_FORMAT_USHORT = 3;
    private static final String TAG = "ExifAttribute";
    public final byte[] bytes;
    public final long bytesOffset;
    public final int format;
    public final int numberOfComponents;

    ExifAttribute(int format2, int numberOfComponents2, byte[] bytes2) {
        this(format2, numberOfComponents2, -1, bytes2);
    }

    ExifAttribute(int format2, int numberOfComponents2, long bytesOffset2, byte[] bytes2) {
        this.format = format2;
        this.numberOfComponents = numberOfComponents2;
        this.bytesOffset = bytesOffset2;
        this.bytes = bytes2;
    }

    @NonNull
    public static ExifAttribute createUShort(@NonNull int[] values, @NonNull ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[(IFD_FORMAT_BYTES_PER_FORMAT[3] * values.length)]);
        buffer.order(byteOrder);
        for (int value : values) {
            buffer.putShort((short) value);
        }
        return new ExifAttribute(3, values.length, buffer.array());
    }

    @NonNull
    public static ExifAttribute createUShort(int value, @NonNull ByteOrder byteOrder) {
        return createUShort(new int[]{value}, byteOrder);
    }

    @NonNull
    public static ExifAttribute createULong(@NonNull long[] values, @NonNull ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[(IFD_FORMAT_BYTES_PER_FORMAT[4] * values.length)]);
        buffer.order(byteOrder);
        for (long value : values) {
            buffer.putInt((int) value);
        }
        return new ExifAttribute(4, values.length, buffer.array());
    }

    @NonNull
    public static ExifAttribute createULong(long value, @NonNull ByteOrder byteOrder) {
        return createULong(new long[]{value}, byteOrder);
    }

    @NonNull
    public static ExifAttribute createSLong(@NonNull int[] values, @NonNull ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[(IFD_FORMAT_BYTES_PER_FORMAT[9] * values.length)]);
        buffer.order(byteOrder);
        for (int value : values) {
            buffer.putInt(value);
        }
        return new ExifAttribute(9, values.length, buffer.array());
    }

    @NonNull
    public static ExifAttribute createSLong(int value, @NonNull ByteOrder byteOrder) {
        return createSLong(new int[]{value}, byteOrder);
    }

    @NonNull
    public static ExifAttribute createByte(@NonNull String value) {
        if (value.length() != 1 || value.charAt(0) < '0' || value.charAt(0) > '1') {
            byte[] ascii = value.getBytes(ASCII);
            return new ExifAttribute(1, ascii.length, ascii);
        }
        byte[] bytes2 = {(byte) (value.charAt(0) - '0')};
        return new ExifAttribute(1, bytes2.length, bytes2);
    }

    @NonNull
    public static ExifAttribute createString(@NonNull String value) {
        byte[] ascii = (value + 0).getBytes(ASCII);
        return new ExifAttribute(2, ascii.length, ascii);
    }

    @NonNull
    public static ExifAttribute createURational(@NonNull LongRational[] values, @NonNull ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[(IFD_FORMAT_BYTES_PER_FORMAT[5] * values.length)]);
        buffer.order(byteOrder);
        for (LongRational value : values) {
            buffer.putInt((int) value.getNumerator());
            buffer.putInt((int) value.getDenominator());
        }
        return new ExifAttribute(5, values.length, buffer.array());
    }

    @NonNull
    public static ExifAttribute createURational(@NonNull LongRational value, @NonNull ByteOrder byteOrder) {
        return createURational(new LongRational[]{value}, byteOrder);
    }

    @NonNull
    public static ExifAttribute createSRational(@NonNull LongRational[] values, @NonNull ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[(IFD_FORMAT_BYTES_PER_FORMAT[10] * values.length)]);
        buffer.order(byteOrder);
        for (LongRational value : values) {
            buffer.putInt((int) value.getNumerator());
            buffer.putInt((int) value.getDenominator());
        }
        return new ExifAttribute(10, values.length, buffer.array());
    }

    @NonNull
    public static ExifAttribute createSRational(@NonNull LongRational value, @NonNull ByteOrder byteOrder) {
        return createSRational(new LongRational[]{value}, byteOrder);
    }

    @NonNull
    public static ExifAttribute createDouble(@NonNull double[] values, @NonNull ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[(IFD_FORMAT_BYTES_PER_FORMAT[12] * values.length)]);
        buffer.order(byteOrder);
        for (double value : values) {
            buffer.putDouble(value);
        }
        return new ExifAttribute(12, values.length, buffer.array());
    }

    @NonNull
    public static ExifAttribute createDouble(double value, @NonNull ByteOrder byteOrder) {
        return createDouble(new double[]{value}, byteOrder);
    }

    public String toString() {
        return "(" + IFD_FORMAT_NAMES[this.format] + ", data length:" + this.bytes.length + ")";
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x01b6 A[SYNTHETIC, Splitter:B:153:0x01b6] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object getValue(java.nio.ByteOrder r12) {
        /*
            r11 = this;
            java.lang.String r0 = "IOException occurred while closing InputStream"
            java.lang.String r1 = "ExifAttribute"
            r2 = 0
            r3 = 0
            androidx.camera.core.impl.utils.ByteOrderedDataInputStream r4 = new androidx.camera.core.impl.utils.ByteOrderedDataInputStream     // Catch:{ IOException -> 0x01ad }
            byte[] r5 = r11.bytes     // Catch:{ IOException -> 0x01ad }
            r4.<init>((byte[]) r5)     // Catch:{ IOException -> 0x01ad }
            r2 = r4
            r2.setByteOrder(r12)     // Catch:{ IOException -> 0x01ad }
            int r4 = r11.format     // Catch:{ IOException -> 0x01ad }
            switch(r4) {
                case 1: goto L_0x0166;
                case 2: goto L_0x0116;
                case 3: goto L_0x00f9;
                case 4: goto L_0x00dc;
                case 5: goto L_0x00b6;
                case 6: goto L_0x0166;
                case 7: goto L_0x0116;
                case 8: goto L_0x0099;
                case 9: goto L_0x007c;
                case 10: goto L_0x0054;
                case 11: goto L_0x0036;
                case 12: goto L_0x0019;
                default: goto L_0x0016;
            }     // Catch:{ IOException -> 0x01ad }
        L_0x0016:
            goto L_0x01a2
        L_0x0019:
            int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            double[] r4 = new double[r4]     // Catch:{ IOException -> 0x01ad }
            r5 = 0
        L_0x001e:
            int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            if (r5 >= r6) goto L_0x002b
            double r6 = r2.readDouble()     // Catch:{ IOException -> 0x01ad }
            r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
            int r5 = r5 + 1
            goto L_0x001e
        L_0x002b:
            r2.close()     // Catch:{ IOException -> 0x0031 }
            goto L_0x0035
        L_0x0031:
            r3 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r3)
        L_0x0035:
            return r4
        L_0x0036:
            int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            double[] r4 = new double[r4]     // Catch:{ IOException -> 0x01ad }
            r5 = 0
        L_0x003b:
            int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            if (r5 >= r6) goto L_0x0049
            float r6 = r2.readFloat()     // Catch:{ IOException -> 0x01ad }
            double r6 = (double) r6     // Catch:{ IOException -> 0x01ad }
            r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
            int r5 = r5 + 1
            goto L_0x003b
        L_0x0049:
            r2.close()     // Catch:{ IOException -> 0x004f }
            goto L_0x0053
        L_0x004f:
            r3 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r3)
        L_0x0053:
            return r4
        L_0x0054:
            int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            androidx.camera.core.impl.utils.LongRational[] r4 = new androidx.camera.core.impl.utils.LongRational[r4]     // Catch:{ IOException -> 0x01ad }
            r5 = 0
        L_0x0059:
            int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            if (r5 >= r6) goto L_0x0071
            int r6 = r2.readInt()     // Catch:{ IOException -> 0x01ad }
            long r6 = (long) r6     // Catch:{ IOException -> 0x01ad }
            int r8 = r2.readInt()     // Catch:{ IOException -> 0x01ad }
            long r8 = (long) r8     // Catch:{ IOException -> 0x01ad }
            androidx.camera.core.impl.utils.LongRational r10 = new androidx.camera.core.impl.utils.LongRational     // Catch:{ IOException -> 0x01ad }
            r10.<init>(r6, r8)     // Catch:{ IOException -> 0x01ad }
            r4[r5] = r10     // Catch:{ IOException -> 0x01ad }
            int r5 = r5 + 1
            goto L_0x0059
        L_0x0071:
            r2.close()     // Catch:{ IOException -> 0x0077 }
            goto L_0x007b
        L_0x0077:
            r3 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r3)
        L_0x007b:
            return r4
        L_0x007c:
            int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            int[] r4 = new int[r4]     // Catch:{ IOException -> 0x01ad }
            r5 = 0
        L_0x0081:
            int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            if (r5 >= r6) goto L_0x008e
            int r6 = r2.readInt()     // Catch:{ IOException -> 0x01ad }
            r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
            int r5 = r5 + 1
            goto L_0x0081
        L_0x008e:
            r2.close()     // Catch:{ IOException -> 0x0094 }
            goto L_0x0098
        L_0x0094:
            r3 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r3)
        L_0x0098:
            return r4
        L_0x0099:
            int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            int[] r4 = new int[r4]     // Catch:{ IOException -> 0x01ad }
            r5 = 0
        L_0x009e:
            int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            if (r5 >= r6) goto L_0x00ab
            short r6 = r2.readShort()     // Catch:{ IOException -> 0x01ad }
            r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
            int r5 = r5 + 1
            goto L_0x009e
        L_0x00ab:
            r2.close()     // Catch:{ IOException -> 0x00b1 }
            goto L_0x00b5
        L_0x00b1:
            r3 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r3)
        L_0x00b5:
            return r4
        L_0x00b6:
            int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            androidx.camera.core.impl.utils.LongRational[] r4 = new androidx.camera.core.impl.utils.LongRational[r4]     // Catch:{ IOException -> 0x01ad }
            r5 = 0
        L_0x00bb:
            int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            if (r5 >= r6) goto L_0x00d1
            long r6 = r2.readUnsignedInt()     // Catch:{ IOException -> 0x01ad }
            long r8 = r2.readUnsignedInt()     // Catch:{ IOException -> 0x01ad }
            androidx.camera.core.impl.utils.LongRational r10 = new androidx.camera.core.impl.utils.LongRational     // Catch:{ IOException -> 0x01ad }
            r10.<init>(r6, r8)     // Catch:{ IOException -> 0x01ad }
            r4[r5] = r10     // Catch:{ IOException -> 0x01ad }
            int r5 = r5 + 1
            goto L_0x00bb
        L_0x00d1:
            r2.close()     // Catch:{ IOException -> 0x00d7 }
            goto L_0x00db
        L_0x00d7:
            r3 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r3)
        L_0x00db:
            return r4
        L_0x00dc:
            int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            long[] r4 = new long[r4]     // Catch:{ IOException -> 0x01ad }
            r5 = 0
        L_0x00e1:
            int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            if (r5 >= r6) goto L_0x00ee
            long r6 = r2.readUnsignedInt()     // Catch:{ IOException -> 0x01ad }
            r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
            int r5 = r5 + 1
            goto L_0x00e1
        L_0x00ee:
            r2.close()     // Catch:{ IOException -> 0x00f4 }
            goto L_0x00f8
        L_0x00f4:
            r3 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r3)
        L_0x00f8:
            return r4
        L_0x00f9:
            int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            int[] r4 = new int[r4]     // Catch:{ IOException -> 0x01ad }
            r5 = 0
        L_0x00fe:
            int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            if (r5 >= r6) goto L_0x010b
            int r6 = r2.readUnsignedShort()     // Catch:{ IOException -> 0x01ad }
            r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
            int r5 = r5 + 1
            goto L_0x00fe
        L_0x010b:
            r2.close()     // Catch:{ IOException -> 0x0111 }
            goto L_0x0115
        L_0x0111:
            r3 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r3)
        L_0x0115:
            return r4
        L_0x0116:
            r4 = 0
            int r5 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            byte[] r6 = EXIF_ASCII_PREFIX     // Catch:{ IOException -> 0x01ad }
            int r6 = r6.length     // Catch:{ IOException -> 0x01ad }
            if (r5 < r6) goto L_0x0136
            r5 = 1
            r6 = 0
        L_0x0120:
            byte[] r7 = EXIF_ASCII_PREFIX     // Catch:{ IOException -> 0x01ad }
            int r8 = r7.length     // Catch:{ IOException -> 0x01ad }
            if (r6 >= r8) goto L_0x0132
            byte[] r8 = r11.bytes     // Catch:{ IOException -> 0x01ad }
            byte r8 = r8[r6]     // Catch:{ IOException -> 0x01ad }
            byte r9 = r7[r6]     // Catch:{ IOException -> 0x01ad }
            if (r8 == r9) goto L_0x012f
            r5 = 0
            goto L_0x0132
        L_0x012f:
            int r6 = r6 + 1
            goto L_0x0120
        L_0x0132:
            if (r5 == 0) goto L_0x0136
            int r6 = r7.length     // Catch:{ IOException -> 0x01ad }
            r4 = r6
        L_0x0136:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01ad }
            r5.<init>()     // Catch:{ IOException -> 0x01ad }
        L_0x013b:
            int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
            if (r4 >= r6) goto L_0x0158
            byte[] r6 = r11.bytes     // Catch:{ IOException -> 0x01ad }
            byte r6 = r6[r4]     // Catch:{ IOException -> 0x01ad }
            if (r6 != 0) goto L_0x0146
            goto L_0x0158
        L_0x0146:
            r7 = 32
            if (r6 < r7) goto L_0x014f
            char r7 = (char) r6     // Catch:{ IOException -> 0x01ad }
            r5.append(r7)     // Catch:{ IOException -> 0x01ad }
            goto L_0x0154
        L_0x014f:
            r7 = 63
            r5.append(r7)     // Catch:{ IOException -> 0x01ad }
        L_0x0154:
            int r4 = r4 + 1
            goto L_0x013b
        L_0x0158:
            java.lang.String r3 = r5.toString()     // Catch:{ IOException -> 0x01ad }
            r2.close()     // Catch:{ IOException -> 0x0161 }
            goto L_0x0165
        L_0x0161:
            r6 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r6)
        L_0x0165:
            return r3
        L_0x0166:
            byte[] r4 = r11.bytes     // Catch:{ IOException -> 0x01ad }
            int r5 = r4.length     // Catch:{ IOException -> 0x01ad }
            r6 = 1
            if (r5 != r6) goto L_0x018f
            r5 = 0
            byte r7 = r4[r5]     // Catch:{ IOException -> 0x01ad }
            if (r7 < 0) goto L_0x018f
            byte r4 = r4[r5]     // Catch:{ IOException -> 0x01ad }
            if (r4 > r6) goto L_0x018f
            java.lang.String r4 = new java.lang.String     // Catch:{ IOException -> 0x01ad }
            char[] r6 = new char[r6]     // Catch:{ IOException -> 0x01ad }
            byte[] r7 = r11.bytes     // Catch:{ IOException -> 0x01ad }
            byte r7 = r7[r5]     // Catch:{ IOException -> 0x01ad }
            int r7 = r7 + 48
            char r7 = (char) r7     // Catch:{ IOException -> 0x01ad }
            r6[r5] = r7     // Catch:{ IOException -> 0x01ad }
            r4.<init>(r6)     // Catch:{ IOException -> 0x01ad }
            r2.close()     // Catch:{ IOException -> 0x018a }
            goto L_0x018e
        L_0x018a:
            r3 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r3)
        L_0x018e:
            return r4
        L_0x018f:
            java.lang.String r4 = new java.lang.String     // Catch:{ IOException -> 0x01ad }
            byte[] r5 = r11.bytes     // Catch:{ IOException -> 0x01ad }
            java.nio.charset.Charset r6 = ASCII     // Catch:{ IOException -> 0x01ad }
            r4.<init>(r5, r6)     // Catch:{ IOException -> 0x01ad }
            r2.close()     // Catch:{ IOException -> 0x019d }
            goto L_0x01a1
        L_0x019d:
            r3 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r3)
        L_0x01a1:
            return r4
        L_0x01a2:
            r2.close()     // Catch:{ IOException -> 0x01a6 }
            goto L_0x01aa
        L_0x01a6:
            r4 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r4)
        L_0x01aa:
            return r3
        L_0x01ab:
            r3 = move-exception
            goto L_0x01bf
        L_0x01ad:
            r4 = move-exception
            java.lang.String r5 = "IOException occurred during reading a value"
            androidx.camera.core.Logger.w(r1, r5, r4)     // Catch:{ all -> 0x01ab }
            if (r2 == 0) goto L_0x01be
            r2.close()     // Catch:{ IOException -> 0x01ba }
            goto L_0x01be
        L_0x01ba:
            r5 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r5)
        L_0x01be:
            return r3
        L_0x01bf:
            if (r2 == 0) goto L_0x01c9
            r2.close()     // Catch:{ IOException -> 0x01c5 }
            goto L_0x01c9
        L_0x01c5:
            r4 = move-exception
            androidx.camera.core.Logger.e(r1, r0, r4)
        L_0x01c9:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.utils.ExifAttribute.getValue(java.nio.ByteOrder):java.lang.Object");
    }

    public double getDoubleValue(@NonNull ByteOrder byteOrder) {
        Object value = getValue(byteOrder);
        if (value == null) {
            throw new NumberFormatException("NULL can't be converted to a double value");
        } else if (value instanceof String) {
            return Double.parseDouble((String) value);
        } else {
            if (value instanceof long[]) {
                long[] array = (long[]) value;
                if (array.length == 1) {
                    return (double) array[0];
                }
                throw new NumberFormatException("There are more than one component");
            } else if (value instanceof int[]) {
                int[] array2 = (int[]) value;
                if (array2.length == 1) {
                    return (double) array2[0];
                }
                throw new NumberFormatException("There are more than one component");
            } else if (value instanceof double[]) {
                double[] array3 = (double[]) value;
                if (array3.length == 1) {
                    return array3[0];
                }
                throw new NumberFormatException("There are more than one component");
            } else if (value instanceof LongRational[]) {
                LongRational[] array4 = (LongRational[]) value;
                if (array4.length == 1) {
                    return array4[0].toDouble();
                }
                throw new NumberFormatException("There are more than one component");
            } else {
                throw new NumberFormatException("Couldn't find a double value");
            }
        }
    }

    public int getIntValue(@NonNull ByteOrder byteOrder) {
        Object value = getValue(byteOrder);
        if (value == null) {
            throw new NumberFormatException("NULL can't be converted to a integer value");
        } else if (value instanceof String) {
            return Integer.parseInt((String) value);
        } else {
            if (value instanceof long[]) {
                long[] array = (long[]) value;
                if (array.length == 1) {
                    return (int) array[0];
                }
                throw new NumberFormatException("There are more than one component");
            } else if (value instanceof int[]) {
                int[] array2 = (int[]) value;
                if (array2.length == 1) {
                    return array2[0];
                }
                throw new NumberFormatException("There are more than one component");
            } else {
                throw new NumberFormatException("Couldn't find a integer value");
            }
        }
    }

    @Nullable
    public String getStringValue(@NonNull ByteOrder byteOrder) {
        Object value = getValue(byteOrder);
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (value instanceof long[]) {
            long[] array = (long[]) value;
            for (int i = 0; i < array.length; i++) {
                stringBuilder.append(array[i]);
                if (i + 1 != array.length) {
                    stringBuilder.append(",");
                }
            }
            return stringBuilder.toString();
        } else if (value instanceof int[]) {
            int[] array2 = (int[]) value;
            for (int i2 = 0; i2 < array2.length; i2++) {
                stringBuilder.append(array2[i2]);
                if (i2 + 1 != array2.length) {
                    stringBuilder.append(",");
                }
            }
            return stringBuilder.toString();
        } else if (value instanceof double[]) {
            double[] array3 = (double[]) value;
            for (int i3 = 0; i3 < array3.length; i3++) {
                stringBuilder.append(array3[i3]);
                if (i3 + 1 != array3.length) {
                    stringBuilder.append(",");
                }
            }
            return stringBuilder.toString();
        } else if (!(value instanceof LongRational[])) {
            return null;
        } else {
            LongRational[] array4 = (LongRational[]) value;
            for (int i4 = 0; i4 < array4.length; i4++) {
                stringBuilder.append(array4[i4].getNumerator());
                stringBuilder.append('/');
                stringBuilder.append(array4[i4].getDenominator());
                if (i4 + 1 != array4.length) {
                    stringBuilder.append(",");
                }
            }
            return stringBuilder.toString();
        }
    }

    public int size() {
        return IFD_FORMAT_BYTES_PER_FORMAT[this.format] * this.numberOfComponents;
    }
}
