package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

@Deprecated
public final class MonochromeRectangleDetector {
    private static final int MAX_MODULES = 32;
    private final BitMatrix image;

    public MonochromeRectangleDetector(BitMatrix image2) {
        this.image = image2;
    }

    public ResultPoint[] detect() {
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int halfHeight = height / 2;
        int halfWidth = width / 2;
        int deltaY = Math.max(1, height / 256);
        int deltaX = Math.max(1, width / 256);
        int bottom = height;
        int i = halfHeight;
        int top = ((int) findCornerFromCenter(halfWidth, 0, 0, width, i, -deltaY, 0, bottom, halfWidth / 2).getY()) - 1;
        int deltaY2 = -deltaX;
        int deltaX2 = deltaX;
        int deltaX3 = halfWidth;
        int deltaY3 = deltaY;
        int i2 = width;
        int i3 = height;
        int height2 = halfHeight;
        int i4 = top;
        int i5 = bottom;
        ResultPoint pointB = findCornerFromCenter(deltaX3, deltaY2, 0, width, height2, 0, i4, i5, halfHeight / 2);
        int left = ((int) pointB.getX()) - 1;
        int i6 = left;
        ResultPoint pointC = findCornerFromCenter(deltaX3, deltaX2, i6, width, height2, 0, i4, i5, halfHeight / 2);
        int right = ((int) pointC.getX()) + 1;
        ResultPoint pointD = findCornerFromCenter(deltaX3, 0, i6, right, height2, deltaY3, i4, i5, halfWidth / 2);
        int i7 = deltaY3;
        return new ResultPoint[]{findCornerFromCenter(halfWidth, 0, left, right, i, -deltaY3, top, ((int) pointD.getY()) + 1, halfWidth / 4), pointB, pointC, pointD};
    }

    private ResultPoint findCornerFromCenter(int centerX, int deltaX, int left, int right, int centerY, int deltaY, int top, int bottom, int maxWhiteRun) {
        int[] range;
        int i = centerX;
        int i2 = centerY;
        int[] lastRange = null;
        int y = centerY;
        int x = centerX;
        while (true) {
            if (y < bottom) {
                if (y >= top) {
                    if (x < right) {
                        if (x < left) {
                            break;
                        }
                        if (deltaX == 0) {
                            range = blackWhiteRange(y, maxWhiteRun, left, right, true);
                        } else {
                            range = blackWhiteRange(x, maxWhiteRun, top, bottom, false);
                        }
                        if (range != null) {
                            lastRange = range;
                            y += deltaY;
                            x += deltaX;
                        } else if (lastRange != null) {
                            char c = 1;
                            if (deltaX == 0) {
                                int lastY = y - deltaY;
                                if (lastRange[0] >= i) {
                                    return new ResultPoint((float) lastRange[1], (float) lastY);
                                }
                                if (lastRange[1] <= i) {
                                    return new ResultPoint((float) lastRange[0], (float) lastY);
                                }
                                if (deltaY > 0) {
                                    c = 0;
                                }
                                return new ResultPoint((float) lastRange[c], (float) lastY);
                            }
                            int lastX = x - deltaX;
                            if (lastRange[0] >= i2) {
                                return new ResultPoint((float) lastX, (float) lastRange[1]);
                            }
                            if (lastRange[1] <= i2) {
                                return new ResultPoint((float) lastX, (float) lastRange[0]);
                            }
                            float f = (float) lastX;
                            if (deltaX < 0) {
                                c = 0;
                            }
                            return new ResultPoint(f, (float) lastRange[c]);
                        } else {
                            throw NotFoundException.getNotFoundInstance();
                        }
                    } else {
                        int i3 = left;
                        break;
                    }
                } else {
                    int i4 = left;
                    int i5 = right;
                    break;
                }
            } else {
                int i6 = left;
                int i7 = right;
                int i8 = top;
                break;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private int[] blackWhiteRange(int fixedDimension, int maxWhiteRun, int minDim, int maxDim, boolean horizontal) {
        int whiteRunStart;
        int center = (minDim + maxDim) / 2;
        int start = center;
        while (true) {
            if (start < minDim) {
                break;
            }
            BitMatrix bitMatrix = this.image;
            if (!horizontal ? !bitMatrix.get(fixedDimension, start) : !bitMatrix.get(start, fixedDimension)) {
                whiteRunStart = start;
                while (true) {
                    start--;
                    if (start < minDim) {
                        break;
                    }
                    BitMatrix bitMatrix2 = this.image;
                    if (horizontal) {
                        if (bitMatrix2.get(start, fixedDimension)) {
                            break;
                        }
                    } else if (bitMatrix2.get(fixedDimension, start)) {
                        break;
                    }
                }
                int whiteRunSize = whiteRunStart - start;
                if (start < minDim || whiteRunSize > maxWhiteRun) {
                    start = whiteRunStart;
                }
            } else {
                start--;
            }
        }
        start = whiteRunStart;
        int start2 = start + 1;
        int end = center;
        while (true) {
            if (end >= maxDim) {
                break;
            }
            BitMatrix bitMatrix3 = this.image;
            if (!horizontal ? !bitMatrix3.get(fixedDimension, end) : !bitMatrix3.get(end, fixedDimension)) {
                int whiteRunStart2 = end;
                while (true) {
                    end++;
                    if (end >= maxDim) {
                        break;
                    }
                    BitMatrix bitMatrix4 = this.image;
                    if (horizontal) {
                        if (bitMatrix4.get(end, fixedDimension)) {
                            break;
                        }
                    } else if (bitMatrix4.get(fixedDimension, end)) {
                        break;
                    }
                }
                int whiteRunSize2 = end - whiteRunStart2;
                if (end >= maxDim || whiteRunSize2 > maxWhiteRun) {
                    end = whiteRunStart2;
                }
            } else {
                end++;
            }
        }
        int end2 = end - 1;
        if (end2 <= start2) {
            return null;
        }
        return new int[]{start2, end2};
    }
}
