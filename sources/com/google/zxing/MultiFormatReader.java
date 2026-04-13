package com.google.zxing;

import com.google.zxing.oned.MultiFormatOneDReader;
import com.google.zxing.qrcode.QRCodeReader;
import com.king.zxing.CaptureActivity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class MultiFormatReader implements Reader {
    private CaptureActivity activity;
    private Map<DecodeHintType, ?> hints;
    private Reader[] readers;

    public Result decode(BinaryBitmap image) {
        setHints((Map<DecodeHintType, ?>) null);
        return decodeInternal(image);
    }

    public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints2) {
        setHints(hints2);
        return decodeInternal(image);
    }

    public Result decodeWithState(BinaryBitmap image) {
        if (this.readers == null) {
            setHints((Map<DecodeHintType, ?>) null);
        }
        return decodeInternal(image);
    }

    public void setHints(Map<DecodeHintType, ?> hints2) {
        this.hints = hints2;
        boolean addOneDReader = true;
        boolean tryHarder = hints2 != null && hints2.containsKey(DecodeHintType.TRY_HARDER);
        Collection<BarcodeFormat> formats = hints2 == null ? null : (Collection) hints2.get(DecodeHintType.POSSIBLE_FORMATS);
        Collection<Reader> readers2 = new ArrayList<>();
        if (formats != null) {
            if (!formats.contains(BarcodeFormat.UPC_A) && !formats.contains(BarcodeFormat.UPC_E) && !formats.contains(BarcodeFormat.EAN_13) && !formats.contains(BarcodeFormat.EAN_8) && !formats.contains(BarcodeFormat.CODABAR) && !formats.contains(BarcodeFormat.CODE_39) && !formats.contains(BarcodeFormat.CODE_93) && !formats.contains(BarcodeFormat.CODE_128) && !formats.contains(BarcodeFormat.ITF) && !formats.contains(BarcodeFormat.RSS_14) && !formats.contains(BarcodeFormat.RSS_EXPANDED)) {
                addOneDReader = false;
            }
            if (addOneDReader && !tryHarder) {
                readers2.add(new MultiFormatOneDReader(hints2));
            }
            if (formats.contains(BarcodeFormat.QR_CODE)) {
                readers2.add(new QRCodeReader(this.activity));
            }
            if (addOneDReader && tryHarder) {
                readers2.add(new MultiFormatOneDReader(hints2));
            }
        }
        if (readers2.isEmpty()) {
            if (!tryHarder) {
                readers2.add(new MultiFormatOneDReader(hints2));
            }
            readers2.add(new QRCodeReader(this.activity));
            if (tryHarder) {
                readers2.add(new MultiFormatOneDReader(hints2));
            }
        }
        this.readers = (Reader[]) readers2.toArray(new Reader[readers2.size()]);
    }

    public void reset() {
        Reader[] readerArr = this.readers;
        if (readerArr != null) {
            for (Reader reader : readerArr) {
                reader.reset();
            }
        }
    }

    private Result decodeInternal(BinaryBitmap image) {
        Reader[] readerArr = this.readers;
        if (readerArr != null) {
            int length = readerArr.length;
            int i = 0;
            while (i < length) {
                try {
                    return readerArr[i].decode(image, this.hints);
                } catch (ReaderException e) {
                    i++;
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public void setActivity(CaptureActivity context) {
        this.activity = context;
    }
}
