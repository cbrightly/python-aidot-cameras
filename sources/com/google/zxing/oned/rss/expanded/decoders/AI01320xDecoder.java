package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;
import com.tutk.IOTC.IOTCAPIs;

public final class AI01320xDecoder extends AI013x0xDecoder {
    AI01320xDecoder(BitArray information) {
        super(information);
    }

    /* access modifiers changed from: protected */
    public void addWeightCode(StringBuilder buf, int weight) {
        if (weight < 10000) {
            buf.append("(3202)");
        } else {
            buf.append("(3203)");
        }
    }

    /* access modifiers changed from: protected */
    public int checkWeight(int weight) {
        if (weight < 10000) {
            return weight;
        }
        return weight + IOTCAPIs.API_ER_ANDROID_NULL;
    }
}
