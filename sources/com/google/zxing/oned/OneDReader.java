package com.google.zxing.oned;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import java.util.Arrays;
import java.util.Map;

public abstract class OneDReader implements Reader {
    public abstract Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map);

    public Result decode(BinaryBitmap image) {
        return decode(image, (Map<DecodeHintType, ?>) null);
    }

    public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) {
        try {
            return doDecode(image, hints);
        } catch (NotFoundException nfe) {
            if (!(hints != null && hints.containsKey(DecodeHintType.TRY_HARDER)) || !image.isRotateSupported()) {
                throw nfe;
            }
            BinaryBitmap rotatedImage = image.rotateCounterClockwise();
            Result result = doDecode(rotatedImage, hints);
            Map<ResultMetadataType, Object> resultMetadata = result.getResultMetadata();
            int orientation = SubsamplingScaleImageView.ORIENTATION_270;
            if (resultMetadata != null) {
                ResultMetadataType resultMetadataType = ResultMetadataType.ORIENTATION;
                if (resultMetadata.containsKey(resultMetadataType)) {
                    orientation = (((Integer) resultMetadata.get(resultMetadataType)).intValue() + SubsamplingScaleImageView.ORIENTATION_270) % 360;
                }
            }
            result.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(orientation));
            ResultPoint[] points = result.getResultPoints();
            if (points != null) {
                int height = rotatedImage.getHeight();
                for (int i = 0; i < points.length; i++) {
                    points[i] = new ResultPoint((((float) height) - points[i].getY()) - 1.0f, points[i].getX());
                }
            }
            return result;
        }
    }

    public void reset() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00e9, code lost:
        return r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0090 A[Catch:{ ReaderException -> 0x00ea }] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x00e7 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.zxing.Result doDecode(com.google.zxing.BinaryBitmap r24, java.util.Map<com.google.zxing.DecodeHintType, ?> r25) {
        /*
            r23 = this;
            r0 = r25
            int r1 = r24.getWidth()
            int r2 = r24.getHeight()
            com.google.zxing.common.BitArray r3 = new com.google.zxing.common.BitArray
            r3.<init>(r1)
            r5 = 1
            if (r0 == 0) goto L_0x001c
            com.google.zxing.DecodeHintType r6 = com.google.zxing.DecodeHintType.TRY_HARDER
            boolean r6 = r0.containsKey(r6)
            if (r6 == 0) goto L_0x001c
            r6 = r5
            goto L_0x001d
        L_0x001c:
            r6 = 0
        L_0x001d:
            if (r6 == 0) goto L_0x0022
            r7 = 8
            goto L_0x0023
        L_0x0022:
            r7 = 5
        L_0x0023:
            int r7 = r2 >> r7
            int r7 = java.lang.Math.max(r5, r7)
            if (r6 == 0) goto L_0x002d
            r8 = r2
            goto L_0x002f
        L_0x002d:
            r8 = 15
        L_0x002f:
            int r9 = r2 / 2
            r10 = 0
            r11 = r10
            r10 = r3
            r3 = r0
        L_0x0035:
            if (r11 >= r8) goto L_0x0115
            int r0 = r11 + 1
            r12 = 2
            int r13 = r0 / 2
            r0 = r11 & 1
            if (r0 != 0) goto L_0x0042
            r0 = r5
            goto L_0x0043
        L_0x0042:
            r0 = 0
        L_0x0043:
            r14 = r0
            if (r14 == 0) goto L_0x0048
            r0 = r13
            goto L_0x0049
        L_0x0048:
            int r0 = -r13
        L_0x0049:
            int r0 = r0 * r7
            int r15 = r9 + r0
            if (r15 < 0) goto L_0x0112
            if (r15 >= r2) goto L_0x010f
            r4 = r24
            com.google.zxing.common.BitArray r0 = r4.getBlackRow(r15, r10)     // Catch:{ NotFoundException -> 0x0101 }
            r10 = r0
            r0 = 0
            r22 = r3
            r3 = r0
            r0 = r22
        L_0x005e:
            if (r3 >= r12) goto L_0x00fb
            if (r3 != r5) goto L_0x0084
            r10.reverse()
            if (r0 == 0) goto L_0x0081
            com.google.zxing.DecodeHintType r12 = com.google.zxing.DecodeHintType.NEED_RESULT_POINT_CALLBACK
            boolean r17 = r0.containsKey(r12)
            if (r17 == 0) goto L_0x0081
            java.util.EnumMap r5 = new java.util.EnumMap
            r18 = r2
            java.lang.Class<com.google.zxing.DecodeHintType> r2 = com.google.zxing.DecodeHintType.class
            r5.<init>(r2)
            r2 = r5
            r2.putAll(r0)
            r2.remove(r12)
            r0 = r2
            goto L_0x0087
        L_0x0081:
            r18 = r2
            goto L_0x0086
        L_0x0084:
            r18 = r2
        L_0x0086:
            r2 = r0
        L_0x0087:
            r5 = r23
            com.google.zxing.Result r0 = r5.decodeRow(r15, r10, r2)     // Catch:{ ReaderException -> 0x00ea }
            r12 = 1
            if (r3 != r12) goto L_0x00e7
            com.google.zxing.ResultMetadataType r12 = com.google.zxing.ResultMetadataType.ORIENTATION     // Catch:{ ReaderException -> 0x00ea }
            r19 = 180(0xb4, float:2.52E-43)
            r20 = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r19)     // Catch:{ ReaderException -> 0x00e2 }
            r0.putMetadata(r12, r2)     // Catch:{ ReaderException -> 0x00e2 }
            com.google.zxing.ResultPoint[] r2 = r0.getResultPoints()     // Catch:{ ReaderException -> 0x00e2 }
            if (r2 == 0) goto L_0x00e9
            com.google.zxing.ResultPoint r12 = new com.google.zxing.ResultPoint     // Catch:{ ReaderException -> 0x00e2 }
            float r4 = (float) r1
            r16 = 0
            r19 = r2[r16]     // Catch:{ ReaderException -> 0x00df }
            float r19 = r19.getX()     // Catch:{ ReaderException -> 0x00df }
            float r4 = r4 - r19
            r19 = 1065353216(0x3f800000, float:1.0)
            float r4 = r4 - r19
            r21 = r2[r16]     // Catch:{ ReaderException -> 0x00df }
            float r5 = r21.getY()     // Catch:{ ReaderException -> 0x00df }
            r12.<init>(r4, r5)     // Catch:{ ReaderException -> 0x00df }
            r2[r16] = r12     // Catch:{ ReaderException -> 0x00df }
            com.google.zxing.ResultPoint r4 = new com.google.zxing.ResultPoint     // Catch:{ ReaderException -> 0x00df }
            float r5 = (float) r1
            r12 = 1
            r17 = r2[r12]     // Catch:{ ReaderException -> 0x00dc }
            float r17 = r17.getX()     // Catch:{ ReaderException -> 0x00dc }
            float r5 = r5 - r17
            float r5 = r5 - r19
            r17 = r2[r12]     // Catch:{ ReaderException -> 0x00dc }
            float r12 = r17.getY()     // Catch:{ ReaderException -> 0x00df }
            r4.<init>(r5, r12)     // Catch:{ ReaderException -> 0x00df }
            r5 = 1
            r2[r5] = r4     // Catch:{ ReaderException -> 0x00da }
            goto L_0x00e9
        L_0x00da:
            r0 = move-exception
            goto L_0x00f0
        L_0x00dc:
            r0 = move-exception
            r5 = r12
            goto L_0x00f0
        L_0x00df:
            r0 = move-exception
            r5 = 1
            goto L_0x00f0
        L_0x00e2:
            r0 = move-exception
            r5 = 1
            r16 = 0
            goto L_0x00f0
        L_0x00e7:
            r20 = r2
        L_0x00e9:
            return r0
        L_0x00ea:
            r0 = move-exception
            r20 = r2
            r5 = 1
            r16 = 0
        L_0x00f0:
            int r3 = r3 + 1
            r4 = r24
            r2 = r18
            r0 = r20
            r12 = 2
            goto L_0x005e
        L_0x00fb:
            r18 = r2
            r16 = 0
            r3 = r0
            goto L_0x0109
        L_0x0101:
            r0 = move-exception
            r18 = r2
            r16 = 0
            r2 = r0
            r0 = r2
        L_0x0109:
            int r11 = r11 + 1
            r2 = r18
            goto L_0x0035
        L_0x010f:
            r18 = r2
            goto L_0x0117
        L_0x0112:
            r18 = r2
            goto L_0x0117
        L_0x0115:
            r18 = r2
        L_0x0117:
            com.google.zxing.NotFoundException r0 = com.google.zxing.NotFoundException.getNotFoundInstance()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.OneDReader.doDecode(com.google.zxing.BinaryBitmap, java.util.Map):com.google.zxing.Result");
    }

    protected static void recordPattern(BitArray row, int start, int[] counters) {
        int numCounters = counters.length;
        Arrays.fill(counters, 0, numCounters, 0);
        int end = row.getSize();
        if (start < end) {
            boolean isWhite = !row.get(start);
            int counterPosition = 0;
            int i = start;
            while (i < end) {
                if (row.get(i) != isWhite) {
                    counters[counterPosition] = counters[counterPosition] + 1;
                } else {
                    counterPosition++;
                    if (counterPosition == numCounters) {
                        break;
                    }
                    counters[counterPosition] = 1;
                    isWhite = !isWhite;
                }
                i++;
            }
            if (counterPosition == numCounters) {
                return;
            }
            if (counterPosition != numCounters - 1 || i != end) {
                throw NotFoundException.getNotFoundInstance();
            }
            return;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    protected static void recordPatternInReverse(BitArray row, int start, int[] counters) {
        int numTransitionsLeft = counters.length;
        boolean last = row.get(start);
        while (start > 0 && numTransitionsLeft >= 0) {
            start--;
            if (row.get(start) != last) {
                numTransitionsLeft--;
                last = !last;
            }
        }
        if (numTransitionsLeft < 0) {
            recordPattern(row, start + 1, counters);
            return;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    protected static float patternMatchVariance(int[] counters, int[] pattern, float maxIndividualVariance) {
        int numCounters = counters.length;
        int total = 0;
        int patternLength = 0;
        for (int i = 0; i < numCounters; i++) {
            total += counters[i];
            patternLength += pattern[i];
        }
        if (total < patternLength) {
            return Float.POSITIVE_INFINITY;
        }
        float unitBarWidth = ((float) total) / ((float) patternLength);
        float maxIndividualVariance2 = maxIndividualVariance * unitBarWidth;
        float totalVariance = 0.0f;
        for (int x = 0; x < numCounters; x++) {
            int counter = counters[x];
            float scaledPattern = ((float) pattern[x]) * unitBarWidth;
            float variance = ((float) counter) > scaledPattern ? ((float) counter) - scaledPattern : scaledPattern - ((float) counter);
            if (variance > maxIndividualVariance2) {
                return Float.POSITIVE_INFINITY;
            }
            totalVariance += variance;
        }
        return totalVariance / ((float) total);
    }
}
