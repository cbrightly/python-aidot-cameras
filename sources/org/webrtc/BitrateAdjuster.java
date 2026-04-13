package org.webrtc;

public interface BitrateAdjuster {
    int getAdjustedBitrateBps();

    double getAdjustedFramerateFps();

    void reportEncodedFrame(int i);

    void setTargets(int i, double d);
}
