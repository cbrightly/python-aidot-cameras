package com.bumptech.glide.load.resource.bitmap;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.view.InputDeviceCompat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.util.i;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public final class DefaultImageHeaderParser implements ImageHeaderParser {
    static final byte[] a = "Exif\u0000\u0000".getBytes(Charset.forName("UTF-8"));
    private static final int[] b = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};

    @NonNull
    public ImageHeaderParser.ImageType b(@NonNull InputStream is) {
        return f(new c((InputStream) i.d(is)));
    }

    @NonNull
    public ImageHeaderParser.ImageType a(@NonNull ByteBuffer byteBuffer) {
        return f(new a((ByteBuffer) i.d(byteBuffer)));
    }

    public int c(@NonNull InputStream is, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b byteArrayPool) {
        return e(new c((InputStream) i.d(is)), (com.bumptech.glide.load.engine.bitmap_recycle.b) i.d(byteArrayPool));
    }

    @NonNull
    private ImageHeaderParser.ImageType f(Reader reader) {
        try {
            int firstTwoBytes = reader.getUInt16();
            if (firstTwoBytes == 65496) {
                return ImageHeaderParser.ImageType.JPEG;
            }
            int firstThreeBytes = (firstTwoBytes << 8) | reader.getUInt8();
            if (firstThreeBytes == 4671814) {
                return ImageHeaderParser.ImageType.GIF;
            }
            int firstFourBytes = (firstThreeBytes << 8) | reader.getUInt8();
            if (firstFourBytes == -1991225785) {
                reader.skip(21);
                try {
                    return reader.getUInt8() >= 3 ? ImageHeaderParser.ImageType.PNG_A : ImageHeaderParser.ImageType.PNG;
                } catch (Reader.EndOfFileException e) {
                    return ImageHeaderParser.ImageType.PNG;
                }
            } else if (firstFourBytes != 1380533830) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            } else {
                reader.skip(4);
                if (((reader.getUInt16() << 16) | reader.getUInt16()) != 1464156752) {
                    return ImageHeaderParser.ImageType.UNKNOWN;
                }
                int fourthFourBytes = (reader.getUInt16() << 16) | reader.getUInt16();
                if ((fourthFourBytes & InputDeviceCompat.SOURCE_ANY) != 1448097792) {
                    return ImageHeaderParser.ImageType.UNKNOWN;
                }
                if ((fourthFourBytes & 255) == 88) {
                    reader.skip(4);
                    return (reader.getUInt8() & 16) != 0 ? ImageHeaderParser.ImageType.WEBP_A : ImageHeaderParser.ImageType.WEBP;
                } else if ((fourthFourBytes & 255) != 76) {
                    return ImageHeaderParser.ImageType.WEBP;
                } else {
                    reader.skip(4);
                    return (reader.getUInt8() & 8) != 0 ? ImageHeaderParser.ImageType.WEBP_A : ImageHeaderParser.ImageType.WEBP;
                }
            }
        } catch (Reader.EndOfFileException e2) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
    }

    private int e(Reader reader, com.bumptech.glide.load.engine.bitmap_recycle.b byteArrayPool) {
        byte[] exifData;
        try {
            int magicNumber = reader.getUInt16();
            if (!g(magicNumber)) {
                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                    Log.d("DfltImageHeaderParser", "Parser doesn't handle magic number: " + magicNumber);
                }
                return -1;
            }
            int exifSegmentLength = i(reader);
            if (exifSegmentLength == -1) {
                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                    Log.d("DfltImageHeaderParser", "Failed to parse exif segment length, or exif segment not found");
                }
                return -1;
            }
            exifData = (byte[]) byteArrayPool.e(exifSegmentLength, byte[].class);
            int k = k(reader, exifData, exifSegmentLength);
            byteArrayPool.put(exifData);
            return k;
        } catch (Reader.EndOfFileException e) {
            return -1;
        } catch (Throwable th) {
            byteArrayPool.put(exifData);
            throw th;
        }
    }

    private int k(Reader reader, byte[] tempArray, int exifSegmentLength) {
        int read = reader.read(tempArray, exifSegmentLength);
        if (read != exifSegmentLength) {
            if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                Log.d("DfltImageHeaderParser", "Unable to read exif segment data, length: " + exifSegmentLength + ", actually read: " + read);
            }
            return -1;
        } else if (h(tempArray, exifSegmentLength)) {
            return j(new b(tempArray, exifSegmentLength));
        } else {
            if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                Log.d("DfltImageHeaderParser", "Missing jpeg exif preamble");
            }
            return -1;
        }
    }

    private boolean h(byte[] exifData, int exifSegmentLength) {
        boolean result = exifData != null && exifSegmentLength > a.length;
        if (!result) {
            return result;
        }
        int i = 0;
        while (true) {
            byte[] bArr = a;
            if (i >= bArr.length) {
                return result;
            }
            if (exifData[i] != bArr[i]) {
                return false;
            }
            i++;
        }
    }

    private int i(Reader reader) {
        short segmentType;
        int segmentContentsLength;
        long skipped;
        do {
            short segmentId = reader.getUInt8();
            if (segmentId != 255) {
                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                    Log.d("DfltImageHeaderParser", "Unknown segmentId=" + segmentId);
                }
                return -1;
            }
            segmentType = reader.getUInt8();
            if (segmentType == 218) {
                return -1;
            }
            if (segmentType == 217) {
                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                    Log.d("DfltImageHeaderParser", "Found MARKER_EOI in exif segment");
                }
                return -1;
            }
            segmentContentsLength = reader.getUInt16() - 2;
            if (segmentType == 225) {
                return segmentContentsLength;
            }
            skipped = reader.skip((long) segmentContentsLength);
        } while (skipped == ((long) segmentContentsLength));
        if (Log.isLoggable("DfltImageHeaderParser", 3)) {
            Log.d("DfltImageHeaderParser", "Unable to skip enough data, type: " + segmentType + ", wanted to skip: " + segmentContentsLength + ", but actually skipped: " + skipped);
        }
        return -1;
    }

    private static int j(b segmentData) {
        ByteOrder byteOrder;
        int i;
        b bVar = segmentData;
        int headerOffsetSize = "Exif\u0000\u0000".length();
        short byteOrderIdentifier = bVar.a(headerOffsetSize);
        int i2 = 3;
        switch (byteOrderIdentifier) {
            case 18761:
                byteOrder = ByteOrder.LITTLE_ENDIAN;
                break;
            case 19789:
                byteOrder = ByteOrder.BIG_ENDIAN;
                break;
            default:
                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                    Log.d("DfltImageHeaderParser", "Unknown endianness = " + byteOrderIdentifier);
                }
                byteOrder = ByteOrder.BIG_ENDIAN;
                break;
        }
        bVar.e(byteOrder);
        int firstIfdOffset = bVar.b(headerOffsetSize + 4) + headerOffsetSize;
        int tagCount = bVar.a(firstIfdOffset);
        int i3 = 0;
        while (i3 < tagCount) {
            int tagOffset = d(firstIfdOffset, i3);
            int tagType = bVar.a(tagOffset);
            if (tagType != 274) {
                i = i2;
            } else {
                int formatCode = bVar.a(tagOffset + 2);
                if (formatCode < 1 || formatCode > 12) {
                    i = 3;
                    if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                        Log.d("DfltImageHeaderParser", "Got invalid format code = " + formatCode);
                    }
                } else {
                    int componentCount = bVar.b(tagOffset + 4);
                    if (componentCount >= 0) {
                        if (Log.isLoggable("DfltImageHeaderParser", i2)) {
                            Log.d("DfltImageHeaderParser", "Got tagIndex=" + i3 + " tagType=" + tagType + " formatCode=" + formatCode + " componentCount=" + componentCount);
                        }
                        int byteCount = b[formatCode] + componentCount;
                        if (byteCount <= 4) {
                            int tagValueOffset = tagOffset + 8;
                            if (tagValueOffset < 0 || tagValueOffset > segmentData.d()) {
                                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                                    Log.d("DfltImageHeaderParser", "Illegal tagValueOffset=" + tagValueOffset + " tagType=" + tagType);
                                    i = 3;
                                } else {
                                    i = 3;
                                }
                            } else if (byteCount >= 0 && tagValueOffset + byteCount <= segmentData.d()) {
                                return bVar.a(tagValueOffset);
                            } else {
                                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                                    Log.d("DfltImageHeaderParser", "Illegal number of bytes for TI tag data tagType=" + tagType);
                                    i = 3;
                                } else {
                                    i = 3;
                                }
                            }
                        } else if (Log.isLoggable("DfltImageHeaderParser", i2)) {
                            Log.d("DfltImageHeaderParser", "Got byte count > 4, not orientation, continuing, formatCode=" + formatCode);
                            i = i2;
                        } else {
                            i = i2;
                        }
                    } else if (Log.isLoggable("DfltImageHeaderParser", i2)) {
                        Log.d("DfltImageHeaderParser", "Negative tiff component count");
                        i = i2;
                    } else {
                        i = i2;
                    }
                }
            }
            i3++;
            i2 = i;
            bVar = segmentData;
        }
        return -1;
    }

    private static int d(int ifdOffset, int tagIndex) {
        return ifdOffset + 2 + (tagIndex * 12);
    }

    private static boolean g(int imageMagicNumber) {
        return (imageMagicNumber & 65496) == 65496 || imageMagicNumber == 19789 || imageMagicNumber == 18761;
    }

    public static final class b {
        private final ByteBuffer a;

        b(byte[] data, int length) {
            this.a = (ByteBuffer) ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).limit(length);
        }

        /* access modifiers changed from: package-private */
        public void e(ByteOrder byteOrder) {
            this.a.order(byteOrder);
        }

        /* access modifiers changed from: package-private */
        public int d() {
            return this.a.remaining();
        }

        /* access modifiers changed from: package-private */
        public int b(int offset) {
            if (c(offset, 4)) {
                return this.a.getInt(offset);
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public short a(int offset) {
            if (c(offset, 2)) {
                return this.a.getShort(offset);
            }
            return -1;
        }

        private boolean c(int offset, int byteSize) {
            return this.a.remaining() - offset >= byteSize;
        }
    }

    public interface Reader {
        int getUInt16();

        short getUInt8();

        int read(byte[] bArr, int i);

        long skip(long j);

        public static final class EndOfFileException extends IOException {
            private static final long serialVersionUID = 1;

            EndOfFileException() {
                super("Unexpectedly reached end of a file");
            }
        }
    }

    public static final class a implements Reader {
        private final ByteBuffer a;

        a(ByteBuffer byteBuffer) {
            this.a = byteBuffer;
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        }

        public short getUInt8() {
            if (this.a.remaining() >= 1) {
                return (short) (this.a.get() & 255);
            }
            throw new Reader.EndOfFileException();
        }

        public int getUInt16() {
            return (getUInt8() << 8) | getUInt8();
        }

        public int read(byte[] buffer, int byteCount) {
            int toRead = Math.min(byteCount, this.a.remaining());
            if (toRead == 0) {
                return -1;
            }
            this.a.get(buffer, 0, toRead);
            return toRead;
        }

        public long skip(long total) {
            int toSkip = (int) Math.min((long) this.a.remaining(), total);
            ByteBuffer byteBuffer = this.a;
            byteBuffer.position(byteBuffer.position() + toSkip);
            return (long) toSkip;
        }
    }

    public static final class c implements Reader {
        private final InputStream a;

        c(InputStream is) {
            this.a = is;
        }

        public short getUInt8() {
            int readResult = this.a.read();
            if (readResult != -1) {
                return (short) readResult;
            }
            throw new Reader.EndOfFileException();
        }

        public int getUInt16() {
            return (getUInt8() << 8) | getUInt8();
        }

        public int read(byte[] buffer, int byteCount) {
            int numBytesRead = 0;
            int lastReadResult = 0;
            while (numBytesRead < byteCount) {
                int read = this.a.read(buffer, numBytesRead, byteCount - numBytesRead);
                lastReadResult = read;
                if (read == -1) {
                    break;
                }
                numBytesRead += lastReadResult;
            }
            if (numBytesRead != 0 || lastReadResult != -1) {
                return numBytesRead;
            }
            throw new Reader.EndOfFileException();
        }

        public long skip(long total) {
            if (total < 0) {
                return 0;
            }
            long toSkip = total;
            while (toSkip > 0) {
                long skipped = this.a.skip(toSkip);
                if (skipped > 0) {
                    toSkip -= skipped;
                } else if (this.a.read() == -1) {
                    break;
                } else {
                    toSkip--;
                }
            }
            return total - toSkip;
        }
    }
}
