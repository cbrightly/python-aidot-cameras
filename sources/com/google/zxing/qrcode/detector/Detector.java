package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Map;

public class Detector {
    private final BitMatrix image;
    private ResultPointCallback resultPointCallback;

    public Detector(BitMatrix image2) {
        this.image = image2;
    }

    /* access modifiers changed from: protected */
    public final BitMatrix getImage() {
        return this.image;
    }

    /* access modifiers changed from: protected */
    public final ResultPointCallback getResultPointCallback() {
        return this.resultPointCallback;
    }

    public DetectorResult detect() {
        return detect((Map<DecodeHintType, ?>) null);
    }

    public final DetectorResult detect(Map<DecodeHintType, ?> hints) {
        ResultPointCallback resultPointCallback2;
        if (hints == null) {
            resultPointCallback2 = null;
        } else {
            resultPointCallback2 = (ResultPointCallback) hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        }
        this.resultPointCallback = resultPointCallback2;
        return processFinderPatternInfo(new FinderPatternFinder(this.image, resultPointCallback2).find(hints));
    }

    /* access modifiers changed from: protected */
    public final DetectorResult processFinderPatternInfo(FinderPatternInfo info) {
        ResultPoint[] points;
        FinderPattern topLeft = info.getTopLeft();
        FinderPattern topRight = info.getTopRight();
        FinderPattern bottomLeft = info.getBottomLeft();
        float moduleSize = calculateModuleSize(topLeft, topRight, bottomLeft);
        if (moduleSize >= 1.0f) {
            int dimension = computeDimension(topLeft, topRight, bottomLeft, moduleSize);
            Version provisionalVersion = Version.getProvisionalVersionForDimension(dimension);
            int modulesBetweenFPCenters = provisionalVersion.getDimensionForVersion() - 7;
            AlignmentPattern alignmentPattern = null;
            if (provisionalVersion.getAlignmentPatternCenters().length > 0) {
                float bottomRightX = (topRight.getX() - topLeft.getX()) + bottomLeft.getX();
                float bottomRightY = (topRight.getY() - topLeft.getY()) + bottomLeft.getY();
                float correctionToTopLeft = 1.0f - (3.0f / ((float) modulesBetweenFPCenters));
                int estAlignmentX = (int) (topLeft.getX() + ((bottomRightX - topLeft.getX()) * correctionToTopLeft));
                int estAlignmentY = (int) (topLeft.getY() + ((bottomRightY - topLeft.getY()) * correctionToTopLeft));
                int i = 4;
                while (true) {
                    if (i > 16) {
                        break;
                    }
                    try {
                        alignmentPattern = findAlignmentInRegion(moduleSize, estAlignmentX, estAlignmentY, (float) i);
                        break;
                    } catch (NotFoundException e) {
                        i <<= 1;
                    }
                }
            }
            BitMatrix bits = sampleGrid(this.image, createTransform(topLeft, topRight, bottomLeft, alignmentPattern, dimension), dimension);
            if (alignmentPattern == null) {
                points = new ResultPoint[]{bottomLeft, topLeft, topRight};
            } else {
                points = new ResultPoint[]{bottomLeft, topLeft, topRight, alignmentPattern};
            }
            return new DetectorResult(bits, points);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static PerspectiveTransform createTransform(ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomLeft, ResultPoint alignmentPattern, int dimension) {
        float sourceBottomRightY;
        float sourceBottomRightX;
        float bottomRightY;
        float bottomRightX;
        float dimMinusThree = ((float) dimension) - 3.5f;
        if (alignmentPattern != null) {
            bottomRightX = alignmentPattern.getX();
            float sourceBottomRightX2 = dimMinusThree - 3.0f;
            bottomRightY = alignmentPattern.getY();
            sourceBottomRightX = sourceBottomRightX2;
            sourceBottomRightY = sourceBottomRightX2;
        } else {
            bottomRightX = (topRight.getX() - topLeft.getX()) + bottomLeft.getX();
            bottomRightY = (topRight.getY() - topLeft.getY()) + bottomLeft.getY();
            sourceBottomRightX = dimMinusThree;
            sourceBottomRightY = dimMinusThree;
        }
        return PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, dimMinusThree, 3.5f, sourceBottomRightX, sourceBottomRightY, 3.5f, dimMinusThree, topLeft.getX(), topLeft.getY(), topRight.getX(), topRight.getY(), bottomRightX, bottomRightY, bottomLeft.getX(), bottomLeft.getY());
    }

    private static BitMatrix sampleGrid(BitMatrix image2, PerspectiveTransform transform, int dimension) {
        return GridSampler.getInstance().sampleGrid(image2, dimension, dimension, transform);
    }

    private static int computeDimension(ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomLeft, float moduleSize) {
        int dimension = ((MathUtils.round(ResultPoint.distance(topLeft, topRight) / moduleSize) + MathUtils.round(ResultPoint.distance(topLeft, bottomLeft) / moduleSize)) / 2) + 7;
        switch (dimension & 3) {
            case 0:
                return dimension + 1;
            case 2:
                return dimension - 1;
            case 3:
                throw NotFoundException.getNotFoundInstance();
            default:
                return dimension;
        }
    }

    /* access modifiers changed from: protected */
    public final float calculateModuleSize(ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomLeft) {
        return (calculateModuleSizeOneWay(topLeft, topRight) + calculateModuleSizeOneWay(topLeft, bottomLeft)) / 2.0f;
    }

    private float calculateModuleSizeOneWay(ResultPoint pattern, ResultPoint otherPattern) {
        float moduleSizeEst1 = sizeOfBlackWhiteBlackRunBothWays((int) pattern.getX(), (int) pattern.getY(), (int) otherPattern.getX(), (int) otherPattern.getY());
        float moduleSizeEst2 = sizeOfBlackWhiteBlackRunBothWays((int) otherPattern.getX(), (int) otherPattern.getY(), (int) pattern.getX(), (int) pattern.getY());
        if (Float.isNaN(moduleSizeEst1)) {
            return moduleSizeEst2 / 7.0f;
        }
        if (Float.isNaN(moduleSizeEst2)) {
            return moduleSizeEst1 / 7.0f;
        }
        return (moduleSizeEst1 + moduleSizeEst2) / 14.0f;
    }

    private float sizeOfBlackWhiteBlackRunBothWays(int fromX, int fromY, int toX, int toY) {
        float result = sizeOfBlackWhiteBlackRun(fromX, fromY, toX, toY);
        float scale = 1.0f;
        int otherToX = fromX - (toX - fromX);
        if (otherToX < 0) {
            scale = ((float) fromX) / ((float) (fromX - otherToX));
            otherToX = 0;
        } else if (otherToX >= this.image.getWidth()) {
            scale = ((float) ((this.image.getWidth() - 1) - fromX)) / ((float) (otherToX - fromX));
            otherToX = this.image.getWidth() - 1;
        }
        int otherToY = (int) (((float) fromY) - (((float) (toY - fromY)) * scale));
        float scale2 = 1.0f;
        if (otherToY < 0) {
            scale2 = ((float) fromY) / ((float) (fromY - otherToY));
            otherToY = 0;
        } else if (otherToY >= this.image.getHeight()) {
            scale2 = ((float) ((this.image.getHeight() - 1) - fromY)) / ((float) (otherToY - fromY));
            otherToY = this.image.getHeight() - 1;
        }
        return (result + sizeOfBlackWhiteBlackRun(fromX, fromY, (int) (((float) fromX) + (((float) (otherToX - fromX)) * scale2)), otherToY)) - 1.0f;
    }

    private float sizeOfBlackWhiteBlackRun(int fromX, int fromY, int toX, int toY) {
        int toY2;
        int toX2;
        int fromY2;
        int fromX2;
        boolean z = true;
        boolean steep = Math.abs(toY - fromY) > Math.abs(toX - fromX);
        if (steep) {
            fromX2 = fromY;
            fromY2 = fromX;
            toX2 = toY;
            toY2 = toX;
        } else {
            fromX2 = fromX;
            fromY2 = fromY;
            toX2 = toX;
            toY2 = toY;
        }
        int dx = Math.abs(toX2 - fromX2);
        int dy = Math.abs(toY2 - fromY2);
        int error = (-dx) / 2;
        int ystep = -1;
        int xstep = fromX2 < toX2 ? 1 : -1;
        if (fromY2 < toY2) {
            ystep = 1;
        }
        int state = 0;
        int xLimit = toX2 + xstep;
        int x = fromX2;
        int y = fromY2;
        while (true) {
            if (x == xLimit) {
                int i = xLimit;
                break;
            }
            int realX = steep ? y : x;
            int realY = steep ? x : y;
            if (state != z) {
                z = false;
            }
            boolean steep2 = steep;
            int realX2 = realX;
            int xLimit2 = xLimit;
            if (z == this.image.get(realX2, realY)) {
                if (state == 2) {
                    return MathUtils.distance(x, y, fromX2, fromY2);
                }
                state++;
            }
            error += dy;
            if (error > 0) {
                if (y == toY2) {
                    break;
                }
                y += ystep;
                error -= dx;
            }
            x += xstep;
            xLimit = xLimit2;
            steep = steep2;
            z = true;
        }
        if (state == 2) {
            return MathUtils.distance(toX2 + xstep, toY2, fromX2, fromY2);
        }
        return Float.NaN;
    }

    /* access modifiers changed from: protected */
    public final AlignmentPattern findAlignmentInRegion(float overallEstModuleSize, int estAlignmentX, int estAlignmentY, float allowanceFactor) {
        int allowance = (int) (allowanceFactor * overallEstModuleSize);
        int alignmentAreaLeftX = Math.max(0, estAlignmentX - allowance);
        int alignmentAreaRightX = Math.min(this.image.getWidth() - 1, estAlignmentX + allowance);
        if (((float) (alignmentAreaRightX - alignmentAreaLeftX)) >= overallEstModuleSize * 3.0f) {
            int alignmentAreaTopY = Math.max(0, estAlignmentY - allowance);
            int alignmentAreaBottomY = Math.min(this.image.getHeight() - 1, estAlignmentY + allowance);
            if (((float) (alignmentAreaBottomY - alignmentAreaTopY)) >= overallEstModuleSize * 3.0f) {
                return new AlignmentPatternFinder(this.image, alignmentAreaLeftX, alignmentAreaTopY, alignmentAreaRightX - alignmentAreaLeftX, alignmentAreaBottomY - alignmentAreaTopY, overallEstModuleSize, this.resultPointCallback).find();
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
