package androidx.camera.core.impl.utils;

import androidx.annotation.NonNull;
import androidx.camera.core.impl.utils.ExifData;
import androidx.core.util.Preconditions;
import java.io.BufferedOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

public final class ExifOutputStream extends FilterOutputStream {
    private static final short BYTE_ALIGN_II = 18761;
    private static final short BYTE_ALIGN_MM = 19789;
    private static final boolean DEBUG = false;
    private static final byte[] IDENTIFIER_EXIF_APP1 = "Exif\u0000\u0000".getBytes(ExifAttribute.ASCII);
    private static final int IFD_OFFSET = 8;
    private static final byte START_CODE = 42;
    private static final int STATE_FRAME_HEADER = 1;
    private static final int STATE_JPEG_DATA = 2;
    private static final int STATE_SOI = 0;
    private static final int STREAMBUFFER_SIZE = 65536;
    private static final String TAG = "ExifOutputStream";
    private final ByteBuffer mBuffer = ByteBuffer.allocate(4);
    private int mByteToCopy;
    private int mByteToSkip;
    private final ExifData mExifData;
    private final byte[] mSingleByteArray = new byte[1];
    private int mState = 0;

    public ExifOutputStream(@NonNull OutputStream ou, @NonNull ExifData exifData) {
        super(new BufferedOutputStream(ou, 65536));
        this.mExifData = exifData;
    }

    private int requestByteToBuffer(int requestByteCount, byte[] buffer, int offset, int length) {
        int byteToRead = Math.min(length, requestByteCount - this.mBuffer.position());
        this.mBuffer.put(buffer, offset, byteToRead);
        return byteToRead;
    }

    public void write(@NonNull byte[] buffer, int offset, int length) {
        while (true) {
            int i = this.mByteToSkip;
            if ((i > 0 || this.mByteToCopy > 0 || this.mState != 2) && length > 0) {
                if (i > 0) {
                    int byteToProcess = Math.min(length, i);
                    length -= byteToProcess;
                    this.mByteToSkip -= byteToProcess;
                    offset += byteToProcess;
                }
                int byteToProcess2 = this.mByteToCopy;
                if (byteToProcess2 > 0) {
                    int byteToProcess3 = Math.min(length, byteToProcess2);
                    this.out.write(buffer, offset, byteToProcess3);
                    length -= byteToProcess3;
                    this.mByteToCopy -= byteToProcess3;
                    offset += byteToProcess3;
                }
                if (length != 0) {
                    switch (this.mState) {
                        case 0:
                            int byteRead = requestByteToBuffer(2, buffer, offset, length);
                            offset += byteRead;
                            length -= byteRead;
                            if (this.mBuffer.position() >= 2) {
                                this.mBuffer.rewind();
                                if (this.mBuffer.getShort() == -40) {
                                    this.out.write(this.mBuffer.array(), 0, 2);
                                    this.mState = 1;
                                    this.mBuffer.rewind();
                                    ByteOrderedDataOutputStream dataOutputStream = new ByteOrderedDataOutputStream(this.out, ByteOrder.BIG_ENDIAN);
                                    dataOutputStream.writeShort(-31);
                                    writeExifSegment(dataOutputStream);
                                    break;
                                } else {
                                    throw new IOException("Not a valid jpeg image, cannot write exif");
                                }
                            } else {
                                return;
                            }
                        case 1:
                            int byteRead2 = requestByteToBuffer(4, buffer, offset, length);
                            offset += byteRead2;
                            length -= byteRead2;
                            if (this.mBuffer.position() == 2 && this.mBuffer.getShort() == -39) {
                                this.out.write(this.mBuffer.array(), 0, 2);
                                this.mBuffer.rewind();
                            }
                            if (this.mBuffer.position() >= 4) {
                                this.mBuffer.rewind();
                                short marker = this.mBuffer.getShort();
                                if (marker == -31) {
                                    this.mByteToSkip = (this.mBuffer.getShort() & 65535) - 2;
                                    this.mState = 2;
                                } else if (!JpegHeader.isSofMarker(marker)) {
                                    this.out.write(this.mBuffer.array(), 0, 4);
                                    this.mByteToCopy = (this.mBuffer.getShort() & 65535) - 2;
                                } else {
                                    this.out.write(this.mBuffer.array(), 0, 4);
                                    this.mState = 2;
                                }
                                this.mBuffer.rewind();
                                break;
                            } else {
                                return;
                            }
                    }
                } else {
                    return;
                }
            }
        }
        if (length > 0) {
            this.out.write(buffer, offset, length);
        }
    }

    public void write(int oneByte) {
        byte[] bArr = this.mSingleByteArray;
        bArr[0] = (byte) (oneByte & 255);
        write(bArr);
    }

    public void write(@NonNull byte[] buffer) {
        write(buffer, 0, buffer.length);
    }

    private void writeExifSegment(@NonNull ByteOrderedDataOutputStream dataOutputStream) {
        int i;
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = dataOutputStream;
        ExifTag[][] exifTagArr = ExifData.EXIF_TAGS;
        int[] ifdOffsets = new int[exifTagArr.length];
        int[] ifdDataSizes = new int[exifTagArr.length];
        for (ExifTag tag : ExifData.EXIF_POINTER_TAGS) {
            for (int ifdIndex = 0; ifdIndex < ExifData.EXIF_TAGS.length; ifdIndex++) {
                this.mExifData.getAttributes(ifdIndex).remove(tag.name);
            }
        }
        if (!this.mExifData.getAttributes(1).isEmpty()) {
            this.mExifData.getAttributes(0).put(ExifData.EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong(0, this.mExifData.getByteOrder()));
        }
        int i2 = 2;
        if (!this.mExifData.getAttributes(2).isEmpty()) {
            this.mExifData.getAttributes(0).put(ExifData.EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong(0, this.mExifData.getByteOrder()));
        }
        if (!this.mExifData.getAttributes(3).isEmpty()) {
            this.mExifData.getAttributes(1).put(ExifData.EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong(0, this.mExifData.getByteOrder()));
        }
        int i3 = 0;
        while (true) {
            i = 4;
            if (i3 >= ExifData.EXIF_TAGS.length) {
                break;
            }
            int sum = 0;
            for (Map.Entry<String, ExifAttribute> entry : this.mExifData.getAttributes(i3).entrySet()) {
                int size = entry.getValue().size();
                if (size > 4) {
                    sum += size;
                }
            }
            ifdDataSizes[i3] = ifdDataSizes[i3] + sum;
            i3++;
        }
        int position = 8;
        for (int ifdType = 0; ifdType < ExifData.EXIF_TAGS.length; ifdType++) {
            if (!this.mExifData.getAttributes(ifdType).isEmpty()) {
                ifdOffsets[ifdType] = position;
                position += (this.mExifData.getAttributes(ifdType).size() * 12) + 2 + 4 + ifdDataSizes[ifdType];
            }
        }
        int totalSize = position + 8;
        if (!this.mExifData.getAttributes(1).isEmpty()) {
            this.mExifData.getAttributes(0).put(ExifData.EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong((long) ifdOffsets[1], this.mExifData.getByteOrder()));
        }
        if (!this.mExifData.getAttributes(2).isEmpty()) {
            this.mExifData.getAttributes(0).put(ExifData.EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong((long) ifdOffsets[2], this.mExifData.getByteOrder()));
        }
        if (!this.mExifData.getAttributes(3).isEmpty()) {
            this.mExifData.getAttributes(1).put(ExifData.EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong((long) ifdOffsets[3], this.mExifData.getByteOrder()));
        }
        byteOrderedDataOutputStream.writeUnsignedShort(totalSize);
        byteOrderedDataOutputStream.write(IDENTIFIER_EXIF_APP1);
        byteOrderedDataOutputStream.writeShort(this.mExifData.getByteOrder() == ByteOrder.BIG_ENDIAN ? BYTE_ALIGN_MM : BYTE_ALIGN_II);
        byteOrderedDataOutputStream.setByteOrder(this.mExifData.getByteOrder());
        byteOrderedDataOutputStream.writeUnsignedShort(42);
        byteOrderedDataOutputStream.writeUnsignedInt(8);
        int ifdType2 = 0;
        while (ifdType2 < ExifData.EXIF_TAGS.length) {
            if (!this.mExifData.getAttributes(ifdType2).isEmpty()) {
                byteOrderedDataOutputStream.writeUnsignedShort(this.mExifData.getAttributes(ifdType2).size());
                int dataOffset = ifdOffsets[ifdType2] + i2 + (this.mExifData.getAttributes(ifdType2).size() * 12) + i;
                for (Map.Entry<String, ExifAttribute> entry2 : this.mExifData.getAttributes(ifdType2).entrySet()) {
                    ExifTag tag2 = (ExifTag) ExifData.Builder.sExifTagMapsForWriting.get(ifdType2).get(entry2.getKey());
                    int tagNumber = ((ExifTag) Preconditions.checkNotNull(tag2, "Tag not supported: " + entry2.getKey() + ". Tag needs to be ported from ExifInterface to ExifData.")).number;
                    ExifAttribute attribute = entry2.getValue();
                    int size2 = attribute.size();
                    byteOrderedDataOutputStream.writeUnsignedShort(tagNumber);
                    byteOrderedDataOutputStream.writeUnsignedShort(attribute.format);
                    byteOrderedDataOutputStream.writeInt(attribute.numberOfComponents);
                    if (size2 > i) {
                        ExifTag exifTag = tag2;
                        byteOrderedDataOutputStream.writeUnsignedInt((long) dataOffset);
                        dataOffset += size2;
                    } else {
                        byteOrderedDataOutputStream.write(attribute.bytes);
                        if (size2 < 4) {
                            int i4 = size2;
                            for (int i5 = 4; i4 < i5; i5 = 4) {
                                byteOrderedDataOutputStream.writeByte(0);
                                i4++;
                            }
                        }
                    }
                    i = 4;
                }
                byteOrderedDataOutputStream.writeUnsignedInt(0);
                for (Map.Entry<String, ExifAttribute> entry3 : this.mExifData.getAttributes(ifdType2).entrySet()) {
                    byte[] bArr = entry3.getValue().bytes;
                    if (bArr.length > 4) {
                        byteOrderedDataOutputStream.write(bArr, 0, bArr.length);
                    }
                }
            }
            ifdType2++;
            i2 = 2;
            i = 4;
        }
        byteOrderedDataOutputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
    }

    public static final class JpegHeader {
        public static final short APP1 = -31;
        public static final short DAC = -52;
        public static final short DHT = -60;
        public static final short EOI = -39;
        public static final short JPG = -56;
        public static final short SOF0 = -64;
        public static final short SOF15 = -49;
        public static final short SOI = -40;

        public static boolean isSofMarker(short marker) {
            return (marker < -64 || marker > -49 || marker == -60 || marker == -56 || marker == -52) ? false : true;
        }

        private JpegHeader() {
        }
    }
}
