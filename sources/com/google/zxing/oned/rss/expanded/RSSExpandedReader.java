package com.google.zxing.oned.rss.expanded;

import com.alibaba.fastjson.asm.Opcodes;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public final class RSSExpandedReader extends AbstractRSSReader {
    private static final int[] EVEN_TOTAL_SUBSET = {4, 20, 52, 104, KitWrapItem.TYPE_VERSION};
    private static final int[][] FINDER_PATTERNS = {new int[]{1, 8, 4, 1}, new int[]{3, 6, 4, 1}, new int[]{3, 4, 6, 1}, new int[]{3, 2, 8, 1}, new int[]{2, 6, 5, 1}, new int[]{2, 2, 9, 1}};
    private static final int[][] FINDER_PATTERN_SEQUENCES = {new int[]{0, 0}, new int[]{0, 1, 1}, new int[]{0, 2, 1, 3}, new int[]{0, 4, 1, 3, 2}, new int[]{0, 4, 1, 3, 3, 5}, new int[]{0, 4, 1, 3, 4, 5, 5}, new int[]{0, 0, 1, 1, 2, 2, 3, 3}, new int[]{0, 0, 1, 1, 2, 2, 3, 4, 4}, new int[]{0, 0, 1, 1, 2, 2, 3, 4, 5, 5}, new int[]{0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5}};
    private static final int FINDER_PAT_A = 0;
    private static final int FINDER_PAT_B = 1;
    private static final int FINDER_PAT_C = 2;
    private static final int FINDER_PAT_D = 3;
    private static final int FINDER_PAT_E = 4;
    private static final int FINDER_PAT_F = 5;
    private static final int[] GSUM = {0, 348, 1388, 2948, 3988};
    private static final int MAX_PAIRS = 11;
    private static final int[] SYMBOL_WIDEST = {7, 5, 4, 3, 1};
    private static final int[][] WEIGHTS = {new int[]{1, 3, 9, 27, 81, 32, 96, 77}, new int[]{20, 60, 180, 118, 143, 7, 21, 63}, new int[]{189, 145, 13, 39, 117, NeedPermissionEvent.PER_ANDROID_S_BLE, 209, 205}, new int[]{Opcodes.INSTANCEOF, 157, 49, 147, 19, 57, 171, 91}, new int[]{62, 186, 136, 197, Opcodes.RET, 85, 44, 132}, new int[]{Opcodes.INVOKEINTERFACE, 133, 188, 142, 4, 12, 36, 108}, new int[]{113, 128, 173, 97, 80, 29, 87, 50}, new int[]{150, 28, 84, 41, 123, Opcodes.IFLE, 52, 156}, new int[]{46, 138, KitWrapItem.TYPE_EXIT, Opcodes.NEW, NeedPermissionEvent.PER_GET_LOCATION_BLE, 206, 196, 166}, new int[]{76, 17, 51, Opcodes.IFEQ, 37, 111, 122, 155}, new int[]{43, NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM, Opcodes.ARETURN, 106, 107, 110, 119, 146}, new int[]{16, 48, IjkMediaMeta.FF_PROFILE_H264_HIGH_444, 10, 30, 90, 59, Opcodes.RETURN}, new int[]{109, 116, NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV, 200, Opcodes.GETSTATIC, 112, 125, 164}, new int[]{70, 210, 208, KitWrapItem.TYPE_MODE, Opcodes.INVOKESTATIC, NeedPermissionEvent.PER_IPC_ALBUM_PERM, 179, 115}, new int[]{134, 191, Opcodes.DCMPL, 31, 93, 68, KitWrapItem.TYPE_VERSION, 190}, new int[]{Opcodes.LCMP, 22, 66, Opcodes.IFNULL, 172, 94, 71, 2}, new int[]{6, 18, 54, Opcodes.IF_ICMPGE, 64, Opcodes.CHECKCAST, Opcodes.IFNE, 40}, new int[]{120, Opcodes.FCMPL, 25, 75, 14, 42, 126, Opcodes.GOTO}, new int[]{79, 26, 78, 23, 69, 207, 199, 175}, new int[]{103, 98, 83, 38, 114, 131, Opcodes.INVOKEVIRTUAL, 124}, new int[]{Opcodes.IF_ICMPLT, 61, Opcodes.INVOKESPECIAL, NeedPermissionEvent.PER_IPC_SPEAK_PERM, 170, 88, 53, Opcodes.IF_ICMPEQ}, new int[]{55, Opcodes.IF_ACMPEQ, 73, 8, 24, 72, 5, 15}, new int[]{45, 135, 194, Opcodes.IF_ICMPNE, 58, 174, 100, 89}};
    private final List<ExpandedPair> pairs = new ArrayList(11);
    private final List<ExpandedRow> rows = new ArrayList();
    private final int[] startEnd = new int[2];
    private boolean startFromEven;

    public Result decodeRow(int rowNumber, BitArray row, Map<DecodeHintType, ?> map) {
        this.pairs.clear();
        this.startFromEven = false;
        try {
            return constructResult(decodeRow2pairs(rowNumber, row));
        } catch (NotFoundException e) {
            this.pairs.clear();
            this.startFromEven = true;
            return constructResult(decodeRow2pairs(rowNumber, row));
        }
    }

    public void reset() {
        this.pairs.clear();
        this.rows.clear();
    }

    /* access modifiers changed from: package-private */
    public List<ExpandedPair> decodeRow2pairs(int rowNumber, BitArray row) {
        while (true) {
            try {
                this.pairs.add(retrieveNextPair(row, this.pairs, rowNumber));
            } catch (NotFoundException nfe) {
                if (this.pairs.isEmpty()) {
                    throw nfe;
                } else if (checkChecksum()) {
                    return this.pairs;
                } else {
                    boolean tryStackedDecode = !this.rows.isEmpty();
                    storeRow(rowNumber, false);
                    if (tryStackedDecode) {
                        List<ExpandedPair> ps = checkRows(false);
                        if (ps != null) {
                            return ps;
                        }
                        List<ExpandedPair> ps2 = checkRows(true);
                        if (ps2 != null) {
                            return ps2;
                        }
                    }
                    throw NotFoundException.getNotFoundInstance();
                }
            }
        }
    }

    private List<ExpandedPair> checkRows(boolean reverse) {
        if (this.rows.size() > 25) {
            this.rows.clear();
            return null;
        }
        this.pairs.clear();
        if (reverse) {
            Collections.reverse(this.rows);
        }
        List<ExpandedPair> ps = null;
        try {
            ps = checkRows(new ArrayList(), 0);
        } catch (NotFoundException e) {
        }
        if (reverse) {
            Collections.reverse(this.rows);
        }
        return ps;
    }

    private List<ExpandedPair> checkRows(List<ExpandedRow> collectedRows, int currentRow) {
        for (int i = currentRow; i < this.rows.size(); i++) {
            ExpandedRow row = this.rows.get(i);
            this.pairs.clear();
            for (ExpandedRow collectedRow : collectedRows) {
                this.pairs.addAll(collectedRow.getPairs());
            }
            this.pairs.addAll(row.getPairs());
            if (isValidSequence(this.pairs)) {
                if (checkChecksum()) {
                    return this.pairs;
                }
                List<ExpandedRow> rs = new ArrayList<>();
                rs.addAll(collectedRows);
                rs.add(row);
                try {
                    return checkRows(rs, i + 1);
                } catch (NotFoundException e) {
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static boolean isValidSequence(List<ExpandedPair> pairs2) {
        for (int[] sequence : FINDER_PATTERN_SEQUENCES) {
            if (pairs2.size() <= sequence.length) {
                boolean stop = true;
                int j = 0;
                while (true) {
                    if (j >= pairs2.size()) {
                        break;
                    } else if (pairs2.get(j).getFinderPattern().getValue() != sequence[j]) {
                        stop = false;
                        break;
                    } else {
                        j++;
                    }
                }
                if (stop) {
                    return true;
                }
            }
        }
        return false;
    }

    private void storeRow(int rowNumber, boolean wasReversed) {
        int insertPos = 0;
        boolean prevIsSame = false;
        boolean nextIsSame = false;
        while (true) {
            if (insertPos >= this.rows.size()) {
                break;
            }
            ExpandedRow erow = this.rows.get(insertPos);
            if (erow.getRowNumber() > rowNumber) {
                nextIsSame = erow.isEquivalent(this.pairs);
                break;
            } else {
                prevIsSame = erow.isEquivalent(this.pairs);
                insertPos++;
            }
        }
        if (!nextIsSame && !prevIsSame && !isPartialRow(this.pairs, this.rows)) {
            this.rows.add(insertPos, new ExpandedRow(this.pairs, rowNumber, wasReversed));
            removePartialRows(this.pairs, this.rows);
        }
    }

    private static void removePartialRows(List<ExpandedPair> pairs2, List<ExpandedRow> rows2) {
        Iterator<ExpandedRow> iterator = rows2.iterator();
        while (iterator.hasNext()) {
            ExpandedRow r = iterator.next();
            if (r.getPairs().size() != pairs2.size()) {
                boolean allFound = true;
                Iterator<ExpandedPair> it = r.getPairs().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ExpandedPair p = it.next();
                    boolean found = false;
                    Iterator<ExpandedPair> it2 = pairs2.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (p.equals(it2.next())) {
                                found = true;
                                continue;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (!found) {
                        allFound = false;
                        break;
                    }
                }
                if (allFound) {
                    iterator.remove();
                }
            }
        }
    }

    private static boolean isPartialRow(Iterable<ExpandedPair> pairs2, Iterable<ExpandedRow> rows2) {
        for (ExpandedRow r : rows2) {
            boolean allFound = true;
            Iterator<ExpandedPair> it = pairs2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ExpandedPair p = it.next();
                boolean found = false;
                Iterator<ExpandedPair> it2 = r.getPairs().iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (p.equals(it2.next())) {
                            found = true;
                            continue;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (!found) {
                    allFound = false;
                    continue;
                    break;
                }
            }
            if (allFound) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public List<ExpandedRow> getRows() {
        return this.rows;
    }

    static Result constructResult(List<ExpandedPair> pairs2) {
        String resultingString = AbstractExpandedDecoder.createDecoder(BitArrayBuilder.buildBitArray(pairs2)).parseInformation();
        ResultPoint[] firstPoints = pairs2.get(0).getFinderPattern().getResultPoints();
        ResultPoint[] lastPoints = pairs2.get(pairs2.size() - 1).getFinderPattern().getResultPoints();
        return new Result(resultingString, (byte[]) null, new ResultPoint[]{firstPoints[0], firstPoints[1], lastPoints[0], lastPoints[1]}, BarcodeFormat.RSS_EXPANDED);
    }

    private boolean checkChecksum() {
        ExpandedPair firstPair = this.pairs.get(0);
        DataCharacter checkCharacter = firstPair.getLeftChar();
        DataCharacter firstCharacter = firstPair.getRightChar();
        if (firstCharacter == null) {
            return false;
        }
        int checksum = firstCharacter.getChecksumPortion();
        int s = 2;
        for (int i = 1; i < this.pairs.size(); i++) {
            ExpandedPair currentPair = this.pairs.get(i);
            checksum += currentPair.getLeftChar().getChecksumPortion();
            s++;
            DataCharacter currentRightChar = currentPair.getRightChar();
            if (currentRightChar != null) {
                checksum += currentRightChar.getChecksumPortion();
                s++;
            }
        }
        if (((s - 4) * 211) + (checksum % 211) == checkCharacter.getValue()) {
            return true;
        }
        return false;
    }

    private static int getNextSecondBar(BitArray row, int initialPos) {
        if (row.get(initialPos)) {
            return row.getNextSet(row.getNextUnset(initialPos));
        }
        return row.getNextUnset(row.getNextSet(initialPos));
    }

    /* access modifiers changed from: package-private */
    public ExpandedPair retrieveNextPair(BitArray row, List<ExpandedPair> previousPairs, int rowNumber) {
        FinderPattern pattern;
        DataCharacter rightChar;
        boolean isOddPattern = previousPairs.size() % 2 == 0;
        if (this.startFromEven) {
            isOddPattern = !isOddPattern;
        }
        boolean keepFinding = true;
        int forcedOffset = -1;
        do {
            findNextPair(row, previousPairs, forcedOffset);
            pattern = parseFoundFinderPattern(row, rowNumber, isOddPattern);
            if (pattern == null) {
                forcedOffset = getNextSecondBar(row, this.startEnd[0]);
                continue;
            } else {
                keepFinding = false;
                continue;
            }
        } while (keepFinding);
        DataCharacter leftChar = decodeDataCharacter(row, pattern, isOddPattern, true);
        if (previousPairs.isEmpty() || !previousPairs.get(previousPairs.size() - 1).mustBeLast()) {
            try {
                rightChar = decodeDataCharacter(row, pattern, isOddPattern, false);
            } catch (NotFoundException e) {
                rightChar = null;
            }
            return new ExpandedPair(leftChar, rightChar, pattern, true);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void findNextPair(BitArray row, List<ExpandedPair> previousPairs, int forcedOffset) {
        int rowOffset;
        BitArray bitArray = row;
        int[] counters = getDecodeFinderCounters();
        counters[0] = 0;
        counters[1] = 0;
        counters[2] = 0;
        counters[3] = 0;
        int width = row.getSize();
        if (forcedOffset >= 0) {
            rowOffset = forcedOffset;
            List<ExpandedPair> list = previousPairs;
        } else if (previousPairs.isEmpty() != 0) {
            rowOffset = 0;
            List<ExpandedPair> list2 = previousPairs;
        } else {
            rowOffset = previousPairs.get(previousPairs.size() - 1).getFinderPattern().getStartEnd()[1];
        }
        boolean searchingEvenPair = previousPairs.size() % 2 != 0;
        if (this.startFromEven) {
            searchingEvenPair = !searchingEvenPair;
        }
        boolean isWhite = false;
        while (rowOffset < width) {
            isWhite = !bitArray.get(rowOffset);
            if (!isWhite) {
                break;
            }
            rowOffset++;
        }
        int counterPosition = 0;
        int patternStart = rowOffset;
        for (int x = rowOffset; x < width; x++) {
            if (bitArray.get(x) != isWhite) {
                counters[counterPosition] = counters[counterPosition] + 1;
            } else {
                if (counterPosition == 3) {
                    if (searchingEvenPair) {
                        reverseCounters(counters);
                    }
                    if (AbstractRSSReader.isFinderPattern(counters)) {
                        int[] iArr = this.startEnd;
                        iArr[0] = patternStart;
                        iArr[1] = x;
                        return;
                    }
                    if (searchingEvenPair) {
                        reverseCounters(counters);
                    }
                    patternStart += counters[0] + counters[1];
                    counters[0] = counters[2];
                    counters[1] = counters[3];
                    counters[2] = 0;
                    counters[3] = 0;
                    counterPosition--;
                } else {
                    counterPosition++;
                }
                counters[counterPosition] = 1;
                isWhite = !isWhite;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static void reverseCounters(int[] counters) {
        int length = counters.length;
        for (int i = 0; i < length / 2; i++) {
            int tmp = counters[i];
            counters[i] = counters[(length - i) - 1];
            counters[(length - i) - 1] = tmp;
        }
    }

    private FinderPattern parseFoundFinderPattern(BitArray row, int rowNumber, boolean oddPattern) {
        int start;
        int firstCounter;
        int firstElementStart;
        BitArray bitArray = row;
        if (oddPattern) {
            int firstElementStart2 = this.startEnd[0] - 1;
            while (firstElementStart2 >= 0 && !bitArray.get(firstElementStart2)) {
                firstElementStart2--;
            }
            int firstElementStart3 = firstElementStart2 + 1;
            int[] iArr = this.startEnd;
            firstCounter = iArr[0] - firstElementStart3;
            start = firstElementStart3;
            firstElementStart = iArr[1];
        } else {
            int[] iArr2 = this.startEnd;
            start = iArr2[0];
            firstElementStart = bitArray.getNextUnset(iArr2[1] + 1);
            firstCounter = firstElementStart - this.startEnd[1];
        }
        int[] counters = getDecodeFinderCounters();
        System.arraycopy(counters, 0, counters, 1, counters.length - 1);
        counters[0] = firstCounter;
        try {
            return new FinderPattern(AbstractRSSReader.parseFinderValue(counters, FINDER_PATTERNS), new int[]{start, firstElementStart}, start, firstElementStart, rowNumber);
        } catch (NotFoundException e) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public DataCharacter decodeDataCharacter(BitArray row, FinderPattern pattern, boolean isOddPattern, boolean leftChar) {
        BitArray bitArray = row;
        int[] counters = getDataCharacterCounters();
        counters[0] = 0;
        counters[1] = 0;
        counters[2] = 0;
        counters[3] = 0;
        counters[4] = 0;
        counters[5] = 0;
        counters[6] = 0;
        counters[7] = 0;
        if (leftChar) {
            OneDReader.recordPatternInReverse(bitArray, pattern.getStartEnd()[0], counters);
        } else {
            OneDReader.recordPattern(bitArray, pattern.getStartEnd()[1], counters);
            int i = 0;
            for (int j = counters.length - 1; i < j; j--) {
                int temp = counters[i];
                counters[i] = counters[j];
                counters[j] = temp;
                i++;
            }
        }
        float elementWidth = ((float) MathUtils.sum(counters)) / ((float) 17);
        float expectedElementWidth = ((float) (pattern.getStartEnd()[1] - pattern.getStartEnd()[0])) / 15.0f;
        float f = 0.3f;
        if (Math.abs(elementWidth - expectedElementWidth) / expectedElementWidth <= 0.3f) {
            int[] oddCounts = getOddCounts();
            int[] evenCounts = getEvenCounts();
            float[] oddRoundingErrors = getOddRoundingErrors();
            float[] evenRoundingErrors = getEvenRoundingErrors();
            int i2 = 0;
            while (i2 < counters.length) {
                float value = (((float) counters[i2]) * 1.0f) / elementWidth;
                int count = (int) (0.5f + value);
                if (count < 1) {
                    if (value >= f) {
                        count = 1;
                    } else {
                        throw NotFoundException.getNotFoundInstance();
                    }
                } else if (count > 8) {
                    if (value <= 8.7f) {
                        count = 8;
                    } else {
                        throw NotFoundException.getNotFoundInstance();
                    }
                }
                int offset = i2 / 2;
                if ((i2 & 1) == 0) {
                    oddCounts[offset] = count;
                    oddRoundingErrors[offset] = value - ((float) count);
                } else {
                    evenCounts[offset] = count;
                    evenRoundingErrors[offset] = value - ((float) count);
                }
                i2++;
                f = 0.3f;
            }
            adjustOddEvenCounts(17);
            int weightRowNumber = (((pattern.getValue() * 4) + (isOddPattern ? 0 : 2)) + (leftChar ^ true ? 1 : 0)) - 1;
            int oddSum = 0;
            int oddChecksumPortion = 0;
            for (int i3 = oddCounts.length - 1; i3 >= 0; i3--) {
                if (isNotA1left(pattern, isOddPattern, leftChar)) {
                    oddChecksumPortion += oddCounts[i3] * WEIGHTS[weightRowNumber][i3 * 2];
                }
                oddSum += oddCounts[i3];
            }
            int evenChecksumPortion = 0;
            for (int i4 = evenCounts.length - 1; i4 >= 0; i4--) {
                if (isNotA1left(pattern, isOddPattern, leftChar)) {
                    evenChecksumPortion += evenCounts[i4] * WEIGHTS[weightRowNumber][(i4 * 2) + 1];
                }
            }
            int i5 = oddChecksumPortion + evenChecksumPortion;
            if ((oddSum & 1) != 0 || oddSum > 13 || oddSum < 4) {
                throw NotFoundException.getNotFoundInstance();
            }
            int group = (13 - oddSum) / 2;
            int oddWidest = SYMBOL_WIDEST[group];
            int[] iArr = counters;
            int evenWidest = 9 - oddWidest;
            int vOdd = RSSUtils.getRSSvalue(oddCounts, oddWidest, true);
            int i6 = oddWidest;
            int vEven = RSSUtils.getRSSvalue(evenCounts, evenWidest, false);
            int i7 = vEven;
            int i8 = evenWidest;
            return new DataCharacter((vOdd * EVEN_TOTAL_SUBSET[group]) + vEven + GSUM[group], i5);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static boolean isNotA1left(FinderPattern pattern, boolean isOddPattern, boolean leftChar) {
        return pattern.getValue() != 0 || !isOddPattern || !leftChar;
    }

    private void adjustOddEvenCounts(int numModules) {
        int oddSum = MathUtils.sum(getOddCounts());
        int evenSum = MathUtils.sum(getEvenCounts());
        boolean incrementOdd = false;
        boolean decrementOdd = false;
        if (oddSum > 13) {
            decrementOdd = true;
        } else if (oddSum < 4) {
            incrementOdd = true;
        }
        boolean incrementEven = false;
        boolean decrementEven = false;
        if (evenSum > 13) {
            decrementEven = true;
        } else if (evenSum < 4) {
            incrementEven = true;
        }
        int mismatch = (oddSum + evenSum) - numModules;
        boolean evenParityBad = false;
        boolean oddParityBad = (oddSum & 1) == 1;
        if ((evenSum & 1) == 0) {
            evenParityBad = true;
        }
        if (mismatch == 1) {
            if (oddParityBad) {
                if (!evenParityBad) {
                    decrementOdd = true;
                } else {
                    throw NotFoundException.getNotFoundInstance();
                }
            } else if (evenParityBad) {
                decrementEven = true;
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        } else if (mismatch == -1) {
            if (oddParityBad) {
                if (!evenParityBad) {
                    incrementOdd = true;
                } else {
                    throw NotFoundException.getNotFoundInstance();
                }
            } else if (evenParityBad) {
                incrementEven = true;
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        } else if (mismatch != 0) {
            throw NotFoundException.getNotFoundInstance();
        } else if (oddParityBad) {
            if (!evenParityBad) {
                throw NotFoundException.getNotFoundInstance();
            } else if (oddSum < evenSum) {
                incrementOdd = true;
                decrementEven = true;
            } else {
                decrementOdd = true;
                incrementEven = true;
            }
        } else if (evenParityBad) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (incrementOdd) {
            if (!decrementOdd) {
                AbstractRSSReader.increment(getOddCounts(), getOddRoundingErrors());
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        if (decrementOdd) {
            AbstractRSSReader.decrement(getOddCounts(), getOddRoundingErrors());
        }
        if (incrementEven) {
            if (!decrementEven) {
                AbstractRSSReader.increment(getEvenCounts(), getOddRoundingErrors());
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        if (decrementEven) {
            AbstractRSSReader.decrement(getEvenCounts(), getEvenRoundingErrors());
        }
    }
}
