package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FinderPatternFinder {
    private static final int CENTER_QUORUM = 2;
    protected static final int MAX_MODULES = 57;
    protected static final int MIN_SKIP = 3;
    private final int[] crossCheckStateCount;
    private boolean hasSkipped;
    private final BitMatrix image;
    private final List<FinderPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;

    public FinderPatternFinder(BitMatrix image2) {
        this(image2, (ResultPointCallback) null);
    }

    public FinderPatternFinder(BitMatrix image2, ResultPointCallback resultPointCallback2) {
        this.image = image2;
        this.possibleCenters = new ArrayList();
        this.crossCheckStateCount = new int[5];
        this.resultPointCallback = resultPointCallback2;
    }

    /* access modifiers changed from: protected */
    public final BitMatrix getImage() {
        return this.image;
    }

    /* access modifiers changed from: protected */
    public final List<FinderPattern> getPossibleCenters() {
        return this.possibleCenters;
    }

    /* access modifiers changed from: package-private */
    public final FinderPatternInfo find(Map<DecodeHintType, ?> hints) {
        int i;
        int i2;
        int currentState;
        Map<DecodeHintType, ?> map = hints;
        int i3 = 1;
        int i4 = 0;
        boolean tryHarder = map != null && map.containsKey(DecodeHintType.TRY_HARDER);
        boolean pureBarcode = map != null && map.containsKey(DecodeHintType.PURE_BARCODE);
        int maxI = this.image.getHeight();
        int maxJ = this.image.getWidth();
        int iSkip = (maxI * 3) / 228;
        char c = 3;
        if (iSkip < 3 || tryHarder) {
            iSkip = 3;
        }
        boolean done = false;
        int[] stateCount = new int[5];
        int i5 = iSkip - 1;
        while (i5 < maxI && !done) {
            stateCount[i4] = i4;
            stateCount[i3] = i4;
            stateCount[2] = i4;
            stateCount[c] = i4;
            int i6 = 4;
            stateCount[4] = i4;
            int currentState2 = 0;
            int j = 0;
            while (j < maxJ) {
                if (this.image.get(j, i5)) {
                    if ((currentState2 & 1) == i3) {
                        currentState2++;
                    }
                    stateCount[currentState2] = stateCount[currentState2] + i3;
                    i2 = i3;
                    currentState = i6;
                } else if ((currentState2 & 1) != 0) {
                    i2 = i3;
                    currentState = i6;
                    stateCount[currentState2] = stateCount[currentState2] + i2;
                } else if (currentState2 != i6) {
                    i2 = i3;
                    currentState = i6;
                    currentState2++;
                    stateCount[currentState2] = stateCount[currentState2] + i2;
                } else if (!foundPatternCross(stateCount)) {
                    stateCount[0] = stateCount[2];
                    i2 = 1;
                    stateCount[1] = stateCount[3];
                    currentState = 4;
                    stateCount[2] = stateCount[4];
                    stateCount[3] = 1;
                    stateCount[4] = 0;
                    currentState2 = 3;
                } else if (handlePossibleCenter(stateCount, i5, j, pureBarcode)) {
                    iSkip = 2;
                    if (this.hasSkipped) {
                        done = haveMultiplyConfirmedCenters();
                    } else {
                        int rowSkip = findRowSkip();
                        if (rowSkip > stateCount[2]) {
                            i5 += (rowSkip - stateCount[2]) - 2;
                            j = maxJ - 1;
                        }
                    }
                    stateCount[0] = 0;
                    stateCount[1] = 0;
                    stateCount[2] = 0;
                    stateCount[3] = 0;
                    stateCount[4] = 0;
                    currentState2 = 0;
                    currentState = 4;
                    i2 = 1;
                } else {
                    int i7 = i6;
                    stateCount[0] = stateCount[2];
                    stateCount[1] = stateCount[3];
                    stateCount[2] = stateCount[i7];
                    stateCount[3] = 1;
                    stateCount[i7] = 0;
                    currentState2 = 3;
                    currentState = 4;
                    i2 = 1;
                }
                j += i2;
                i6 = currentState;
                i3 = i2;
            }
            int i8 = i3;
            if (!foundPatternCross(stateCount)) {
                i = 0;
            } else if (handlePossibleCenter(stateCount, i5, maxJ, pureBarcode)) {
                i = 0;
                iSkip = stateCount[0];
                if (this.hasSkipped) {
                    done = haveMultiplyConfirmedCenters();
                }
            } else {
                i = 0;
            }
            i5 += iSkip;
            i3 = i8;
            i4 = i;
            c = 3;
        }
        FinderPattern[] patternInfo = selectBestPatterns();
        ResultPoint.orderBestPatterns(patternInfo);
        return new FinderPatternInfo(patternInfo);
    }

    private static float centerFromEnd(int[] stateCount, int end) {
        return ((float) ((end - stateCount[4]) - stateCount[3])) - (((float) stateCount[2]) / 2.0f);
    }

    protected static boolean foundPatternCross(int[] stateCount) {
        int totalModuleSize = 0;
        for (int i = 0; i < 5; i++) {
            int count = stateCount[i];
            if (count == 0) {
                return false;
            }
            totalModuleSize += count;
        }
        if (totalModuleSize < 7) {
            return false;
        }
        float moduleSize = ((float) totalModuleSize) / 7.0f;
        float maxVariance = moduleSize / 2.0f;
        if (Math.abs(moduleSize - ((float) stateCount[0])) >= maxVariance || Math.abs(moduleSize - ((float) stateCount[1])) >= maxVariance || Math.abs((moduleSize * 3.0f) - ((float) stateCount[2])) >= 3.0f * maxVariance || Math.abs(moduleSize - ((float) stateCount[3])) >= maxVariance || Math.abs(moduleSize - ((float) stateCount[4])) >= maxVariance) {
            return false;
        }
        return true;
    }

    private int[] getCrossCheckStateCount() {
        int[] iArr = this.crossCheckStateCount;
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
        iArr[4] = 0;
        return iArr;
    }

    private boolean crossCheckDiagonal(int startI, int centerJ, int maxCount, int originalStateCountTotal) {
        int i = startI;
        int i2 = centerJ;
        int i3 = maxCount;
        int[] stateCount = getCrossCheckStateCount();
        int i4 = 0;
        while (i >= i4 && i2 >= i4 && this.image.get(i2 - i4, i - i4)) {
            stateCount[2] = stateCount[2] + 1;
            i4++;
        }
        if (i < i4 || i2 < i4) {
            return false;
        }
        while (i >= i4 && i2 >= i4 && !this.image.get(i2 - i4, i - i4) && stateCount[1] <= i3) {
            stateCount[1] = stateCount[1] + 1;
            i4++;
        }
        if (i < i4 || i2 < i4 || stateCount[1] > i3) {
            return false;
        }
        while (i >= i4 && i2 >= i4 && this.image.get(i2 - i4, i - i4) && stateCount[0] <= i3) {
            stateCount[0] = stateCount[0] + 1;
            i4++;
        }
        if (stateCount[0] > i3) {
            return false;
        }
        int maxI = this.image.getHeight();
        int maxJ = this.image.getWidth();
        int i5 = 1;
        while (i + i5 < maxI && i2 + i5 < maxJ && this.image.get(i2 + i5, i + i5)) {
            stateCount[2] = stateCount[2] + 1;
            i5++;
        }
        if (i + i5 >= maxI || i2 + i5 >= maxJ) {
            return false;
        }
        while (i + i5 < maxI && i2 + i5 < maxJ && !this.image.get(i2 + i5, i + i5) && stateCount[3] < i3) {
            stateCount[3] = stateCount[3] + 1;
            i5++;
        }
        if (i + i5 >= maxI || i2 + i5 >= maxJ || stateCount[3] >= i3) {
            return false;
        }
        while (i + i5 < maxI && i2 + i5 < maxJ && this.image.get(i2 + i5, i + i5) && stateCount[4] < i3) {
            stateCount[4] = stateCount[4] + 1;
            i5++;
        }
        if (stateCount[4] >= i3) {
            return false;
        }
        if (Math.abs(((((stateCount[0] + stateCount[1]) + stateCount[2]) + stateCount[3]) + stateCount[4]) - originalStateCountTotal) >= originalStateCountTotal * 2 || !foundPatternCross(stateCount)) {
            return false;
        }
        return true;
    }

    private float crossCheckVertical(int startI, int centerJ, int maxCount, int originalStateCountTotal) {
        BitMatrix image2 = this.image;
        int maxI = image2.getHeight();
        int[] stateCount = getCrossCheckStateCount();
        int i = startI;
        while (i >= 0 && image2.get(centerJ, i)) {
            stateCount[2] = stateCount[2] + 1;
            i--;
        }
        if (i < 0) {
            return Float.NaN;
        }
        while (i >= 0 && !image2.get(centerJ, i) && stateCount[1] <= maxCount) {
            stateCount[1] = stateCount[1] + 1;
            i--;
        }
        if (i < 0 || stateCount[1] > maxCount) {
            return Float.NaN;
        }
        while (i >= 0 && image2.get(centerJ, i) && stateCount[0] <= maxCount) {
            stateCount[0] = stateCount[0] + 1;
            i--;
        }
        if (stateCount[0] > maxCount) {
            return Float.NaN;
        }
        int i2 = startI + 1;
        while (i2 < maxI && image2.get(centerJ, i2)) {
            stateCount[2] = stateCount[2] + 1;
            i2++;
        }
        if (i2 == maxI) {
            return Float.NaN;
        }
        while (i2 < maxI && !image2.get(centerJ, i2) && stateCount[3] < maxCount) {
            stateCount[3] = stateCount[3] + 1;
            i2++;
        }
        if (i2 == maxI || stateCount[3] >= maxCount) {
            return Float.NaN;
        }
        while (i2 < maxI && image2.get(centerJ, i2) && stateCount[4] < maxCount) {
            stateCount[4] = stateCount[4] + 1;
            i2++;
        }
        if (stateCount[4] < maxCount && Math.abs(((((stateCount[0] + stateCount[1]) + stateCount[2]) + stateCount[3]) + stateCount[4]) - originalStateCountTotal) * 5 < originalStateCountTotal * 2 && foundPatternCross(stateCount)) {
            return centerFromEnd(stateCount, i2);
        }
        return Float.NaN;
    }

    private float crossCheckHorizontal(int startJ, int centerI, int maxCount, int originalStateCountTotal) {
        BitMatrix image2 = this.image;
        int maxJ = image2.getWidth();
        int[] stateCount = getCrossCheckStateCount();
        int j = startJ;
        while (j >= 0 && image2.get(j, centerI)) {
            stateCount[2] = stateCount[2] + 1;
            j--;
        }
        if (j < 0) {
            return Float.NaN;
        }
        while (j >= 0 && !image2.get(j, centerI) && stateCount[1] <= maxCount) {
            stateCount[1] = stateCount[1] + 1;
            j--;
        }
        if (j < 0 || stateCount[1] > maxCount) {
            return Float.NaN;
        }
        while (j >= 0 && image2.get(j, centerI) && stateCount[0] <= maxCount) {
            stateCount[0] = stateCount[0] + 1;
            j--;
        }
        if (stateCount[0] > maxCount) {
            return Float.NaN;
        }
        int j2 = startJ + 1;
        while (j2 < maxJ && image2.get(j2, centerI)) {
            stateCount[2] = stateCount[2] + 1;
            j2++;
        }
        if (j2 == maxJ) {
            return Float.NaN;
        }
        while (j2 < maxJ && !image2.get(j2, centerI) && stateCount[3] < maxCount) {
            stateCount[3] = stateCount[3] + 1;
            j2++;
        }
        if (j2 == maxJ || stateCount[3] >= maxCount) {
            return Float.NaN;
        }
        while (j2 < maxJ && image2.get(j2, centerI) && stateCount[4] < maxCount) {
            stateCount[4] = stateCount[4] + 1;
            j2++;
        }
        if (stateCount[4] < maxCount && Math.abs(((((stateCount[0] + stateCount[1]) + stateCount[2]) + stateCount[3]) + stateCount[4]) - originalStateCountTotal) * 5 < originalStateCountTotal && foundPatternCross(stateCount)) {
            return centerFromEnd(stateCount, j2);
        }
        return Float.NaN;
    }

    /* access modifiers changed from: protected */
    public final boolean handlePossibleCenter(int[] stateCount, int i, int j, boolean pureBarcode) {
        int stateCountTotal = stateCount[0] + stateCount[1] + stateCount[2] + stateCount[3] + stateCount[4];
        float centerJ = centerFromEnd(stateCount, j);
        float centerI = crossCheckVertical(i, (int) centerJ, stateCount[2], stateCountTotal);
        if (!Float.isNaN(centerI)) {
            float centerJ2 = crossCheckHorizontal((int) centerJ, (int) centerI, stateCount[2], stateCountTotal);
            if (!Float.isNaN(centerJ2) && (!pureBarcode || crossCheckDiagonal((int) centerI, (int) centerJ2, stateCount[2], stateCountTotal))) {
                float estimatedModuleSize = ((float) stateCountTotal) / 7.0f;
                boolean found = false;
                int index = 0;
                while (true) {
                    if (index >= this.possibleCenters.size()) {
                        break;
                    }
                    FinderPattern center = this.possibleCenters.get(index);
                    if (center.aboutEquals(estimatedModuleSize, centerI, centerJ2)) {
                        this.possibleCenters.set(index, center.combineEstimate(centerI, centerJ2, estimatedModuleSize));
                        found = true;
                        break;
                    }
                    index++;
                }
                if (!found) {
                    FinderPattern point = new FinderPattern(centerJ2, centerI, estimatedModuleSize);
                    this.possibleCenters.add(point);
                    ResultPointCallback resultPointCallback2 = this.resultPointCallback;
                    if (resultPointCallback2 != null) {
                        resultPointCallback2.foundPossibleResultPoint(point);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private int findRowSkip() {
        if (this.possibleCenters.size() <= 1) {
            return 0;
        }
        ResultPoint firstConfirmedCenter = null;
        for (FinderPattern center : this.possibleCenters) {
            if (center.getCount() >= 2) {
                if (firstConfirmedCenter == null) {
                    firstConfirmedCenter = center;
                } else {
                    this.hasSkipped = true;
                    return ((int) (Math.abs(firstConfirmedCenter.getX() - center.getX()) - Math.abs(firstConfirmedCenter.getY() - center.getY()))) / 2;
                }
            }
        }
        return 0;
    }

    private boolean haveMultiplyConfirmedCenters() {
        int confirmedCount = 0;
        float totalModuleSize = 0.0f;
        int max = this.possibleCenters.size();
        for (FinderPattern pattern : this.possibleCenters) {
            if (pattern.getCount() >= 2) {
                confirmedCount++;
                totalModuleSize += pattern.getEstimatedModuleSize();
            }
        }
        if (confirmedCount < 3) {
            return false;
        }
        float average = totalModuleSize / ((float) max);
        float totalDeviation = 0.0f;
        for (FinderPattern pattern2 : this.possibleCenters) {
            totalDeviation += Math.abs(pattern2.getEstimatedModuleSize() - average);
        }
        if (totalDeviation <= 0.05f * totalModuleSize) {
            return true;
        }
        return false;
    }

    private FinderPattern[] selectBestPatterns() {
        int startSize = this.possibleCenters.size();
        if (startSize >= 3) {
            if (startSize > 3) {
                float totalModuleSize = 0.0f;
                float square = 0.0f;
                for (FinderPattern center : this.possibleCenters) {
                    float size = center.getEstimatedModuleSize();
                    totalModuleSize += size;
                    square += size * size;
                }
                float average = totalModuleSize / ((float) startSize);
                Collections.sort(this.possibleCenters, new FurthestFromAverageComparator(average));
                float limit = Math.max(0.2f * average, (float) Math.sqrt((double) ((square / ((float) startSize)) - (average * average))));
                int i = 0;
                while (i < this.possibleCenters.size() && this.possibleCenters.size() > 3) {
                    if (Math.abs(this.possibleCenters.get(i).getEstimatedModuleSize() - average) > limit) {
                        this.possibleCenters.remove(i);
                        i--;
                    }
                    i++;
                }
            }
            if (this.possibleCenters.size() > 3) {
                float totalModuleSize2 = 0.0f;
                for (FinderPattern possibleCenter : this.possibleCenters) {
                    totalModuleSize2 += possibleCenter.getEstimatedModuleSize();
                }
                Collections.sort(this.possibleCenters, new CenterComparator(totalModuleSize2 / ((float) this.possibleCenters.size())));
                List<FinderPattern> list = this.possibleCenters;
                list.subList(3, list.size()).clear();
            }
            return new FinderPattern[]{this.possibleCenters.get(0), this.possibleCenters.get(1), this.possibleCenters.get(2)};
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public static final class FurthestFromAverageComparator implements Comparator<FinderPattern>, Serializable {
        private final float average;

        private FurthestFromAverageComparator(float f) {
            this.average = f;
        }

        public int compare(FinderPattern center1, FinderPattern center2) {
            float dA = Math.abs(center2.getEstimatedModuleSize() - this.average);
            float dB = Math.abs(center1.getEstimatedModuleSize() - this.average);
            if (dA < dB) {
                return -1;
            }
            return dA > dB ? 1 : 0;
        }
    }

    public static final class CenterComparator implements Comparator<FinderPattern>, Serializable {
        private final float average;

        private CenterComparator(float f) {
            this.average = f;
        }

        public int compare(FinderPattern center1, FinderPattern center2) {
            if (center2.getCount() != center1.getCount()) {
                return center2.getCount() - center1.getCount();
            }
            float dA = Math.abs(center2.getEstimatedModuleSize() - this.average);
            float dB = Math.abs(center1.getEstimatedModuleSize() - this.average);
            if (dA < dB) {
                return 1;
            }
            return dA > dB ? -1 : 0;
        }
    }
}
