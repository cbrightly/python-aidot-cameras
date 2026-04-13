package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class GenericMultipleBarcodeReader implements MultipleBarcodeReader {
    private static final int MAX_DEPTH = 4;
    private static final int MIN_DIMENSION_TO_RECUR = 100;
    private final Reader delegate;

    public GenericMultipleBarcodeReader(Reader delegate2) {
        this.delegate = delegate2;
    }

    public Result[] decodeMultiple(BinaryBitmap image) {
        return decodeMultiple(image, (Map<DecodeHintType, ?>) null);
    }

    public Result[] decodeMultiple(BinaryBitmap image, Map<DecodeHintType, ?> hints) {
        ArrayList arrayList = new ArrayList();
        doDecodeMultiple(image, hints, arrayList, 0, 0, 0);
        if (!arrayList.isEmpty()) {
            return (Result[]) arrayList.toArray(new Result[arrayList.size()]);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void doDecodeMultiple(BinaryBitmap image, Map<DecodeHintType, ?> hints, List<Result> results, int xOffset, int yOffset, int currentDepth) {
        boolean alreadyFound;
        int height;
        float maxX;
        float maxY;
        float minY;
        int width;
        BinaryBitmap binaryBitmap = image;
        int i = xOffset;
        int i2 = yOffset;
        int i3 = currentDepth;
        if (i3 <= 4) {
            try {
                Result result = this.delegate.decode(binaryBitmap, hints);
                Iterator<Result> it = results.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().getText().equals(result.getText())) {
                            alreadyFound = true;
                            break;
                        }
                    } else {
                        alreadyFound = false;
                        break;
                    }
                }
                if (!alreadyFound) {
                    results.add(translateResultPoints(result, i, i2));
                } else {
                    List<Result> list = results;
                }
                ResultPoint[] resultPoints = result.getResultPoints();
                if (resultPoints == null) {
                    ResultPoint[] resultPointArr = resultPoints;
                    boolean z = alreadyFound;
                } else if (resultPoints.length == 0) {
                    Result result2 = result;
                    ResultPoint[] resultPointArr2 = resultPoints;
                    boolean z2 = alreadyFound;
                } else {
                    int width2 = image.getWidth();
                    int height2 = image.getHeight();
                    Result result3 = result;
                    float minX = (float) width2;
                    float maxY2 = 0.0f;
                    float minY2 = (float) height2;
                    float maxX2 = 0.0f;
                    for (ResultPoint point : resultPoints) {
                        if (point != null) {
                            float x = point.getX();
                            float y = point.getY();
                            if (x < minX) {
                                minX = x;
                            }
                            if (y < minY2) {
                                minY2 = y;
                            }
                            if (x > maxX2) {
                                maxX2 = x;
                            }
                            if (y > maxY2) {
                                maxY2 = y;
                            }
                        }
                    }
                    if (minX > 100.0f) {
                        maxY = maxY2;
                        boolean z3 = alreadyFound;
                        maxX = maxX2;
                        minY = minY2;
                        float f = minX;
                        height = height2;
                        width = width2;
                        ResultPoint[] resultPointArr3 = resultPoints;
                        doDecodeMultiple(binaryBitmap.crop(0, 0, (int) minX, height2), hints, results, xOffset, yOffset, i3 + 1);
                    } else {
                        maxY = maxY2;
                        minY = minY2;
                        float f2 = minX;
                        height = height2;
                        width = width2;
                        ResultPoint[] resultPointArr4 = resultPoints;
                        boolean z4 = alreadyFound;
                        maxX = maxX2;
                    }
                    if (minY > 100.0f) {
                        doDecodeMultiple(binaryBitmap.crop(0, 0, width, (int) minY), hints, results, xOffset, yOffset, i3 + 1);
                    }
                    if (maxX < ((float) (width - 100))) {
                        doDecodeMultiple(binaryBitmap.crop((int) maxX, 0, width - ((int) maxX), height), hints, results, i + ((int) maxX), yOffset, i3 + 1);
                    }
                    if (maxY < ((float) (height - 100))) {
                        doDecodeMultiple(binaryBitmap.crop(0, (int) maxY, width, height - ((int) maxY)), hints, results, xOffset, yOffset + ((int) maxY), i3 + 1);
                    }
                }
            } catch (ReaderException e) {
            }
        }
    }

    private static Result translateResultPoints(Result result, int xOffset, int yOffset) {
        ResultPoint[] oldResultPoints = result.getResultPoints();
        if (oldResultPoints == null) {
            return result;
        }
        ResultPoint[] newResultPoints = new ResultPoint[oldResultPoints.length];
        for (int i = 0; i < oldResultPoints.length; i++) {
            ResultPoint oldPoint = oldResultPoints[i];
            if (oldPoint != null) {
                newResultPoints[i] = new ResultPoint(oldPoint.getX() + ((float) xOffset), oldPoint.getY() + ((float) yOffset));
            }
        }
        Result newResult = new Result(result.getText(), result.getRawBytes(), result.getNumBits(), newResultPoints, result.getBarcodeFormat(), result.getTimestamp());
        newResult.putAllMetadata(result.getResultMetadata());
        return newResult;
    }
}
