package com.google.zxing.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import java.util.Map;

public final class QRCodeWriter implements Writer {
    private static final int QUIET_ZONE_SIZE = 4;

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) {
        return encode(contents, format, width, height, (Map<EncodeHintType, ?>) null);
    }

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        if (contents.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        } else if (format != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + format);
        } else if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' + height);
        } else {
            ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
            int quietZone = 4;
            if (hints != null) {
                EncodeHintType encodeHintType = EncodeHintType.ERROR_CORRECTION;
                if (hints.containsKey(encodeHintType)) {
                    errorCorrectionLevel = ErrorCorrectionLevel.valueOf(hints.get(encodeHintType).toString());
                }
                EncodeHintType encodeHintType2 = EncodeHintType.MARGIN;
                if (hints.containsKey(encodeHintType2)) {
                    quietZone = Integer.parseInt(hints.get(encodeHintType2).toString());
                }
            }
            return renderResult(Encoder.encode(contents, errorCorrectionLevel, hints), width, height, quietZone);
        }
    }

    private static BitMatrix renderResult(QRCode code, int width, int height, int quietZone) {
        ByteMatrix input = code.getMatrix();
        if (input != null) {
            int inputWidth = input.getWidth();
            int inputHeight = input.getHeight();
            int qrWidth = (quietZone * 2) + inputWidth;
            int qrHeight = (quietZone * 2) + inputHeight;
            int outputWidth = Math.max(width, qrWidth);
            int outputHeight = Math.max(height, qrHeight);
            int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
            int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
            BitMatrix output = new BitMatrix(outputWidth, outputHeight);
            int inputY = 0;
            int outputY = (outputHeight - (inputHeight * multiple)) / 2;
            while (inputY < inputHeight) {
                int inputX = 0;
                int inputHeight2 = inputHeight;
                int outputX = leftPadding;
                while (inputX < inputWidth) {
                    int inputWidth2 = inputWidth;
                    ByteMatrix input2 = input;
                    if (input.get(inputX, inputY) == 1) {
                        output.setRegion(outputX, outputY, multiple, multiple);
                    }
                    inputX++;
                    outputX += multiple;
                    inputWidth = inputWidth2;
                    input = input2;
                }
                int i = inputWidth;
                inputY++;
                outputY += multiple;
                inputHeight = inputHeight2;
            }
            return output;
        }
        ByteMatrix byteMatrix = input;
        throw new IllegalStateException();
    }
}
