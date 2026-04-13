package com.yalantis.ucrop.util;

import android.text.TextUtils;
import android.util.Log;
import androidx.core.view.MotionEventCompat;
import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class ImageHeaderParser {
    private static final int[] BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};
    private static final int EXIF_MAGIC_NUMBER = 65496;
    private static final int EXIF_SEGMENT_TYPE = 225;
    private static final int INTEL_TIFF_MAGIC_NUMBER = 18761;
    private static final String JPEG_EXIF_SEGMENT_PREAMBLE = "Exif\u0000\u0000";
    private static final byte[] JPEG_EXIF_SEGMENT_PREAMBLE_BYTES = JPEG_EXIF_SEGMENT_PREAMBLE.getBytes(StandardCharsets.UTF_8);
    private static final int MARKER_EOI = 217;
    private static final int MOTOROLA_TIFF_MAGIC_NUMBER = 19789;
    private static final int ORIENTATION_TAG_TYPE = 274;
    private static final int SEGMENT_SOS = 218;
    private static final int SEGMENT_START_ID = 255;
    private static final String TAG = "ImageHeaderParser";
    public static final int UNKNOWN_ORIENTATION = -1;
    private final Reader reader;

    public interface Reader {
        int getUInt16();

        short getUInt8();

        int read(byte[] bArr, int i);

        long skip(long j);
    }

    public ImageHeaderParser(InputStream is) {
        this.reader = new StreamReader(is);
    }

    public int getOrientation() {
        int magicNumber = this.reader.getUInt16();
        if (!handles(magicNumber)) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Parser doesn't handle magic number: " + magicNumber);
            }
            return -1;
        }
        int exifSegmentLength = moveToExifSegmentAndGetLength();
        if (exifSegmentLength != -1) {
            return parseExifSegment(new byte[exifSegmentLength], exifSegmentLength);
        }
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Failed to parse exif segment length, or exif segment not found");
        }
        return -1;
    }

    private int parseExifSegment(byte[] tempArray, int exifSegmentLength) {
        int read = this.reader.read(tempArray, exifSegmentLength);
        if (read != exifSegmentLength) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Unable to read exif segment data, length: " + exifSegmentLength + ", actually read: " + read);
            }
            return -1;
        } else if (hasJpegExifPreamble(tempArray, exifSegmentLength)) {
            return parseExifSegment(new RandomAccessReader(tempArray, exifSegmentLength));
        } else {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Missing jpeg exif preamble");
            }
            return -1;
        }
    }

    private boolean hasJpegExifPreamble(byte[] exifData, int exifSegmentLength) {
        boolean result = exifData != null && exifSegmentLength > JPEG_EXIF_SEGMENT_PREAMBLE_BYTES.length;
        if (!result) {
            return result;
        }
        int i = 0;
        while (true) {
            byte[] bArr = JPEG_EXIF_SEGMENT_PREAMBLE_BYTES;
            if (i >= bArr.length) {
                return result;
            }
            if (exifData[i] != bArr[i]) {
                return false;
            }
            i++;
        }
    }

    private int moveToExifSegmentAndGetLength() {
        short segmentType;
        int segmentLength;
        long skipped;
        do {
            short segmentId = this.reader.getUInt8();
            if (segmentId != 255) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Unknown segmentId=" + segmentId);
                }
                return -1;
            }
            segmentType = this.reader.getUInt8();
            if (segmentType == SEGMENT_SOS) {
                return -1;
            }
            if (segmentType == MARKER_EOI) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Found MARKER_EOI in exif segment");
                }
                return -1;
            }
            segmentLength = this.reader.getUInt16() - 2;
            if (segmentType == EXIF_SEGMENT_TYPE) {
                return segmentLength;
            }
            skipped = this.reader.skip((long) segmentLength);
        } while (skipped == ((long) segmentLength));
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Unable to skip enough data, type: " + segmentType + ", wanted to skip: " + segmentLength + ", but actually skipped: " + skipped);
        }
        return -1;
    }

    private static int parseExifSegment(RandomAccessReader segmentData) {
        ByteOrder byteOrder;
        int i;
        RandomAccessReader randomAccessReader = segmentData;
        int headerOffsetSize = JPEG_EXIF_SEGMENT_PREAMBLE.length();
        short byteOrderIdentifier = randomAccessReader.getInt16(headerOffsetSize);
        int i2 = 3;
        if (byteOrderIdentifier == MOTOROLA_TIFF_MAGIC_NUMBER) {
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else if (byteOrderIdentifier == INTEL_TIFF_MAGIC_NUMBER) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Unknown endianness = " + byteOrderIdentifier);
            }
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        randomAccessReader.order(byteOrder);
        int firstIfdOffset = randomAccessReader.getInt32(headerOffsetSize + 4) + headerOffsetSize;
        int tagCount = randomAccessReader.getInt16(firstIfdOffset);
        int i3 = 0;
        while (i3 < tagCount) {
            int tagOffset = calcTagOffset(firstIfdOffset, i3);
            int tagType = randomAccessReader.getInt16(tagOffset);
            if (tagType != ORIENTATION_TAG_TYPE) {
                i = i2;
            } else {
                int formatCode = randomAccessReader.getInt16(tagOffset + 2);
                if (formatCode < 1 || formatCode > 12) {
                    i = 3;
                    if (Log.isLoggable(TAG, 3)) {
                        Log.d(TAG, "Got invalid format code = " + formatCode);
                    }
                } else {
                    int componentCount = randomAccessReader.getInt32(tagOffset + 4);
                    if (componentCount >= 0) {
                        if (Log.isLoggable(TAG, i2)) {
                            Log.d(TAG, "Got tagIndex=" + i3 + " tagType=" + tagType + " formatCode=" + formatCode + " componentCount=" + componentCount);
                        }
                        int byteCount = BYTES_PER_FORMAT[formatCode] + componentCount;
                        if (byteCount <= 4) {
                            int tagValueOffset = tagOffset + 8;
                            if (tagValueOffset < 0 || tagValueOffset > segmentData.length()) {
                                if (Log.isLoggable(TAG, 3)) {
                                    Log.d(TAG, "Illegal tagValueOffset=" + tagValueOffset + " tagType=" + tagType);
                                    i = 3;
                                } else {
                                    i = 3;
                                }
                            } else if (byteCount >= 0 && tagValueOffset + byteCount <= segmentData.length()) {
                                return randomAccessReader.getInt16(tagValueOffset);
                            } else {
                                if (Log.isLoggable(TAG, 3)) {
                                    Log.d(TAG, "Illegal number of bytes for TI tag data tagType=" + tagType);
                                    i = 3;
                                } else {
                                    i = 3;
                                }
                            }
                        } else if (Log.isLoggable(TAG, i2)) {
                            Log.d(TAG, "Got byte count > 4, not orientation, continuing, formatCode=" + formatCode);
                            i = i2;
                        } else {
                            i = i2;
                        }
                    } else if (Log.isLoggable(TAG, i2)) {
                        Log.d(TAG, "Negative tiff component count");
                        i = i2;
                    } else {
                        i = i2;
                    }
                }
            }
            i3++;
            i2 = i;
            randomAccessReader = segmentData;
        }
        return -1;
    }

    private static int calcTagOffset(int ifdOffset, int tagIndex) {
        return ifdOffset + 2 + (tagIndex * 12);
    }

    private static boolean handles(int imageMagicNumber) {
        return (imageMagicNumber & EXIF_MAGIC_NUMBER) == EXIF_MAGIC_NUMBER || imageMagicNumber == MOTOROLA_TIFF_MAGIC_NUMBER || imageMagicNumber == INTEL_TIFF_MAGIC_NUMBER;
    }

    public static class RandomAccessReader {
        private final ByteBuffer data;

        public RandomAccessReader(byte[] data2, int length) {
            this.data = (ByteBuffer) ByteBuffer.wrap(data2).order(ByteOrder.BIG_ENDIAN).limit(length);
        }

        public void order(ByteOrder byteOrder) {
            this.data.order(byteOrder);
        }

        public int length() {
            return this.data.remaining();
        }

        public int getInt32(int offset) {
            return this.data.getInt(offset);
        }

        public short getInt16(int offset) {
            return this.data.getShort(offset);
        }
    }

    public static class StreamReader implements Reader {
        private final InputStream is;

        public StreamReader(InputStream is2) {
            this.is = is2;
        }

        public int getUInt16() {
            return ((this.is.read() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (this.is.read() & 255);
        }

        public short getUInt8() {
            return (short) (this.is.read() & 255);
        }

        public long skip(long total) {
            if (total < 0) {
                return 0;
            }
            long toSkip = total;
            while (toSkip > 0) {
                long skipped = this.is.skip(toSkip);
                if (skipped > 0) {
                    toSkip -= skipped;
                } else if (this.is.read() == -1) {
                    break;
                } else {
                    toSkip--;
                }
            }
            return total - toSkip;
        }

        public int read(byte[] buffer, int byteCount) {
            int toRead = byteCount;
            while (toRead > 0) {
                int read = this.is.read(buffer, byteCount - toRead, toRead);
                int read2 = read;
                if (read == -1) {
                    break;
                }
                toRead -= read2;
            }
            return byteCount - toRead;
        }
    }

    public static void copyExif(ExifInterface originalExif, int width, int height, String imageOutputPath) {
        String[] attributes = {ExifInterface.TAG_F_NUMBER, ExifInterface.TAG_DATETIME, ExifInterface.TAG_DATETIME_DIGITIZED, ExifInterface.TAG_EXPOSURE_TIME, ExifInterface.TAG_FLASH, ExifInterface.TAG_FOCAL_LENGTH, ExifInterface.TAG_GPS_ALTITUDE, ExifInterface.TAG_GPS_ALTITUDE_REF, ExifInterface.TAG_GPS_DATESTAMP, ExifInterface.TAG_GPS_LATITUDE, ExifInterface.TAG_GPS_LATITUDE_REF, ExifInterface.TAG_GPS_LONGITUDE, ExifInterface.TAG_GPS_LONGITUDE_REF, ExifInterface.TAG_GPS_PROCESSING_METHOD, ExifInterface.TAG_GPS_TIMESTAMP, ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY, ExifInterface.TAG_MAKE, ExifInterface.TAG_MODEL, ExifInterface.TAG_SUBSEC_TIME, ExifInterface.TAG_SUBSEC_TIME_DIGITIZED, ExifInterface.TAG_SUBSEC_TIME_ORIGINAL, ExifInterface.TAG_WHITE_BALANCE};
        try {
            try {
                ExifInterface newExif = new ExifInterface(imageOutputPath);
                int length = attributes.length;
                int i = 0;
                while (i < length) {
                    String attribute = attributes[i];
                    try {
                        String value = originalExif.getAttribute(attribute);
                        if (!TextUtils.isEmpty(value)) {
                            newExif.setAttribute(attribute, value);
                        }
                        i++;
                    } catch (IOException e) {
                        e = e;
                        Log.d(TAG, e.getMessage());
                    }
                }
                ExifInterface exifInterface = originalExif;
                newExif.setAttribute(ExifInterface.TAG_IMAGE_WIDTH, String.valueOf(width));
                newExif.setAttribute(ExifInterface.TAG_IMAGE_LENGTH, String.valueOf(height));
                newExif.setAttribute(ExifInterface.TAG_ORIENTATION, "0");
                newExif.saveAttributes();
            } catch (IOException e2) {
                e = e2;
                ExifInterface exifInterface2 = originalExif;
                Log.d(TAG, e.getMessage());
            }
        } catch (IOException e3) {
            e = e3;
            ExifInterface exifInterface3 = originalExif;
            String str = imageOutputPath;
            Log.d(TAG, e.getMessage());
        }
    }
}
