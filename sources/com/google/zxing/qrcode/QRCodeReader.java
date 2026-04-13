package com.google.zxing.qrcode;

import android.graphics.Rect;
import android.hardware.Camera;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.Decoder;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import com.google.zxing.qrcode.detector.Detector;
import com.king.zxing.CaptureActivity;
import com.king.zxing.camera.d;
import com.king.zxing.util.b;
import java.util.List;
import java.util.Map;

public class QRCodeReader implements Reader {
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    private static final String TAG = "QRCodeReader ";
    private static final int maxScanNumCheckZoom = 5;
    private static int scanNum;
    private CaptureActivity activity;
    private final Decoder decoder = new Decoder();

    public QRCodeReader(CaptureActivity context) {
        this.activity = context;
    }

    /* access modifiers changed from: protected */
    public final Decoder getDecoder() {
        return this.decoder;
    }

    public Result decode(BinaryBitmap image) {
        return decode(image, (Map<DecodeHintType, ?>) null);
    }

    public final Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) {
        DecoderResult decoderResult;
        ResultPoint[] points;
        if (image.getBinarizer() instanceof HybridBinarizer) {
            logD("HybridBinarizer decode解析");
        } else if (image.getBinarizer() instanceof GlobalHistogramBinarizer) {
            logD("GlobalHistogramBinarizer decode解析");
        }
        if (hints == null || !hints.containsKey(DecodeHintType.PURE_BARCODE)) {
            if (scanNum <= 5) {
                logD("scanNum:" + scanNum + ",this:" + this);
                scanNum = scanNum + 1;
            } else {
                logD("scanNum>5");
            }
            try {
                DetectorResult detectorResult = new Detector(image.getBlackMatrix()).detect(hints);
                if (scanNum > 5) {
                    logD("scanNum>5 进入缩放检测");
                }
                autoZoomCheck(detectorResult);
                decoderResult = this.decoder.decode(detectorResult.getBits(), hints);
                points = detectorResult.getPoints();
            } catch (Exception e) {
                if (0 != 0) {
                    autoZoomCheck((DetectorResult) null);
                } else {
                    logD("detectorResult == null");
                }
                throw e;
            }
        } else {
            decoderResult = this.decoder.decode(extractPureBits(image.getBlackMatrix()), hints);
            points = NO_POINTS;
        }
        if (decoderResult.getOther() instanceof QRCodeDecoderMetaData) {
            ((QRCodeDecoderMetaData) decoderResult.getOther()).applyMirroredCorrection(points);
        }
        Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), points, BarcodeFormat.QR_CODE);
        List<byte[]> byteSegments = decoderResult.getByteSegments();
        if (byteSegments != null) {
            result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
        }
        String ecLevel = decoderResult.getECLevel();
        if (ecLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
        }
        if (decoderResult.hasStructuredAppend()) {
            result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, Integer.valueOf(decoderResult.getStructuredAppendSequenceNumber()));
            result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, Integer.valueOf(decoderResult.getStructuredAppendParity()));
        }
        return result;
    }

    private void autoZoomCheck(DetectorResult detectorResult) {
        int zoom;
        logD("autoZoomCheck: scanNum:" + scanNum);
        if (scanNum > 5) {
            CaptureActivity captureActivity = this.activity;
            if (!(captureActivity == null || detectorResult == null)) {
                d cameraManager = captureActivity.X();
                ResultPoint[] p = detectorResult.getPoints();
                if (p == null || p.length < 3) {
                    ResultPoint[] resultPointArr = p;
                    logD("不满足points > 3 条件,不处理放大");
                } else {
                    float point1X = p[0].getX();
                    float point1Y = p[0].getY();
                    float point2X = p[1].getX();
                    float point2Y = p[1].getY();
                    float point3X = p[2].getX();
                    float point3Y = p[2].getY();
                    int len = (int) Math.sqrt((double) ((Math.abs(point1X - point2X) * Math.abs(point1X - point2X)) + (Math.abs(point1Y - point2Y) * Math.abs(point1Y - point2Y))));
                    if (((float) (Math.abs(len - ((int) Math.sqrt((double) ((Math.abs(point3X - point2X) * Math.abs(point3X - point2X)) + (Math.abs(point3Y - point2Y) * Math.abs(point3Y - point2Y)))))) / len)) <= 0.1f) {
                        Rect frameRect = cameraManager.d();
                        if (frameRect != null) {
                            int frameWidth = frameRect.right - frameRect.left;
                            Camera camera = cameraManager.f().a();
                            Camera.Parameters parameters = camera.getParameters();
                            int maxZoom = parameters.getMaxZoom();
                            d dVar = cameraManager;
                            int zoom2 = parameters.getZoom();
                            ResultPoint[] resultPointArr2 = p;
                            StringBuilder sb = new StringBuilder();
                            float f = point1X;
                            sb.append("最大放大比例：");
                            sb.append(maxZoom);
                            sb.append(",当前放大比例 zoom:");
                            sb.append(zoom2);
                            logD(sb.toString());
                            if (parameters.isZoomSupported() && len <= frameWidth / 4 && zoom2 < maxZoom) {
                                if (zoom2 == 0) {
                                    zoom = maxZoom / 2;
                                    logD("zoom 放大最大：" + maxZoom + " 的一半:" + zoom);
                                } else if (zoom2 <= maxZoom) {
                                    zoom = zoom2 + 8;
                                    logD("zoom 递增置:" + zoom);
                                } else {
                                    zoom = maxZoom;
                                    logD("zomm 设置最大:" + zoom);
                                }
                                parameters.setZoom(zoom >= maxZoom ? maxZoom : zoom);
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("setZoom:");
                                sb2.append(zoom >= maxZoom ? maxZoom : zoom);
                                logD(sb2.toString());
                                camera.setParameters(parameters);
                            }
                        } else {
                            ResultPoint[] resultPointArr3 = p;
                            float f2 = point1X;
                        }
                    } else {
                        ResultPoint[] resultPointArr4 = p;
                        float f3 = point1X;
                        logD("不满足 < 0.1f 条件,不处理放大");
                    }
                }
            }
            scanNum = 0;
        }
    }

    private void autoZoomCheckNew(DetectorResult detectorResult) {
        double relate;
        if (scanNum > 5) {
            if (this.activity != null) {
                logD("scanNum--- 5帧还没扫描出来,scanNum > 5，处理放大逻辑");
                d cameraManager = this.activity.X();
                ResultPoint[] p = detectorResult.getPoints();
                double len12 = calLen(p[0], p[1]);
                double len13 = calLen(p[0], p[2]);
                double len23 = calLen(p[1], p[2]);
                double len = Math.max(Math.max(len12, len13), len23);
                Rect frameRect = cameraManager.d();
                if (frameRect != null) {
                    int frameWidth = frameRect.right - frameRect.left;
                    int frameHeight = frameRect.bottom - frameRect.top;
                    ResultPoint[] resultPointArr = p;
                    double frameCross = Math.sqrt((double) ((frameWidth * frameWidth) + (frameHeight * frameHeight)));
                    Camera camera = cameraManager.f().a();
                    d dVar = cameraManager;
                    Camera.Parameters parameters = camera.getParameters();
                    double d = len23;
                    int zoom = parameters.getZoom();
                    if (parameters.isZoomSupported()) {
                        if (zoom == 0) {
                            zoom++;
                            relate = len / frameCross;
                            double d2 = len12;
                        } else {
                            double relate2 = len12;
                            relate = (Math.sqrt((double) zoom) * len) / frameCross;
                        }
                        if (relate < 0.8d) {
                            if (relate < 0.3d) {
                                zoom += 4;
                                logD("scanNum--- QRCodeReader 二维码在扫码框的占比比较低的时候 zoom + 4");
                                double d3 = relate;
                            } else if (relate < 0.45d) {
                                zoom += 3;
                                logD("scanNum--- QRCodeReader  zoom + 3 ");
                                double d4 = relate;
                            } else {
                                double d5 = relate;
                                if (Math.sqrt((double) (zoom + 1)) * len < 0.8d * frameCross) {
                                    logD("scanNum--- QRCodeReader 慢慢变大 zoom+1 ");
                                    zoom++;
                                }
                            }
                            parameters.setZoom(zoom);
                            camera.setParameters(parameters);
                            logD("scanNum--- QRCodeReader setZoom New:" + zoom);
                        }
                    }
                } else {
                    ResultPoint[] resultPointArr2 = p;
                    double d6 = len23;
                    double d7 = len12;
                }
            }
            scanNum = 0;
            logD("scanNum--- 5帧过后，scanNum重置为0");
        }
    }

    private double calLen(ResultPoint point1, ResultPoint point2) {
        return Math.sqrt(Math.pow((double) (point1.getX() - point2.getX()), 2.0d) + Math.pow((double) (point1.getY() - point2.getY()), 2.0d));
    }

    public void reset() {
    }

    private static BitMatrix extractPureBits(BitMatrix image) {
        BitMatrix bitMatrix = image;
        int[] leftTopBlack = image.getTopLeftOnBit();
        int[] rightBottomBlack = image.getBottomRightOnBit();
        if (leftTopBlack == null || rightBottomBlack == null) {
            int[] iArr = rightBottomBlack;
            throw NotFoundException.getNotFoundInstance();
        }
        float moduleSize = moduleSize(leftTopBlack, bitMatrix);
        int top = leftTopBlack[1];
        int bottom = rightBottomBlack[1];
        int left = leftTopBlack[0];
        int right = rightBottomBlack[0];
        if (left >= right || top >= bottom) {
            int[] iArr2 = rightBottomBlack;
            throw NotFoundException.getNotFoundInstance();
        } else if (bottom - top == right - left || (right = left + (bottom - top)) < image.getWidth()) {
            int matrixWidth = Math.round(((float) ((right - left) + 1)) / moduleSize);
            int matrixHeight = Math.round(((float) ((bottom - top) + 1)) / moduleSize);
            if (matrixWidth <= 0 || matrixHeight <= 0) {
                int[] iArr3 = rightBottomBlack;
                throw NotFoundException.getNotFoundInstance();
            } else if (matrixHeight == matrixWidth) {
                int nudge = (int) (moduleSize / 2.0f);
                int top2 = top + nudge;
                int left2 = left + nudge;
                int nudgedTooFarRight = (((int) (((float) (matrixWidth - 1)) * moduleSize)) + left2) - right;
                if (nudgedTooFarRight > 0) {
                    if (nudgedTooFarRight <= nudge) {
                        left2 -= nudgedTooFarRight;
                    } else {
                        throw NotFoundException.getNotFoundInstance();
                    }
                }
                int nudgedTooFarDown = (((int) (((float) (matrixHeight - 1)) * moduleSize)) + top2) - bottom;
                if (nudgedTooFarDown > 0) {
                    if (nudgedTooFarDown <= nudge) {
                        top2 -= nudgedTooFarDown;
                    } else {
                        throw NotFoundException.getNotFoundInstance();
                    }
                }
                BitMatrix bits = new BitMatrix(matrixWidth, matrixHeight);
                int y = 0;
                while (y < matrixHeight) {
                    int iOffset = ((int) (((float) y) * moduleSize)) + top2;
                    int[] leftTopBlack2 = leftTopBlack;
                    int x = 0;
                    while (x < matrixWidth) {
                        int[] rightBottomBlack2 = rightBottomBlack;
                        if (bitMatrix.get(((int) (((float) x) * moduleSize)) + left2, iOffset)) {
                            bits.set(x, y);
                        }
                        x++;
                        rightBottomBlack = rightBottomBlack2;
                    }
                    y++;
                    leftTopBlack = leftTopBlack2;
                }
                return bits;
            } else {
                int[] iArr4 = leftTopBlack;
                throw NotFoundException.getNotFoundInstance();
            }
        } else {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    private static float moduleSize(int[] leftTopBlack, BitMatrix image) {
        int height = image.getHeight();
        int width = image.getWidth();
        int x = leftTopBlack[0];
        int y = leftTopBlack[1];
        boolean inBlack = true;
        int transitions = 0;
        while (x < width && y < height) {
            if (inBlack != image.get(x, y)) {
                transitions++;
                if (transitions == 5) {
                    break;
                }
                inBlack = !inBlack;
            }
            x++;
            y++;
        }
        if (x != width && y != height) {
            return ((float) (x - leftTopBlack[0])) / 7.0f;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void logD(String msg) {
        b.h(TAG + msg);
    }
}
