package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

public final class AnyAIDecoder extends AbstractExpandedDecoder {
    private static final int HEADER_SIZE = 5;

    AnyAIDecoder(BitArray information) {
        super(information);
    }

    public String parseInformation() {
        return getGeneralDecoder().decodeAllCodes(new StringBuilder(), 5);
    }
}
