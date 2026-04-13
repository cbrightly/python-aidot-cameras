package org.webrtc;

public class BaseBitrateAdjuster implements BitrateAdjuster {
    protected int targetBitrateBps;
    protected double targetFramerateFps;

    BaseBitrateAdjuster() {
    }

    public void setTargets(int targetBitrateBps2, double targetFramerateFps2) {
        this.targetBitrateBps = targetBitrateBps2;
        this.targetFramerateFps = targetFramerateFps2;
    }

    public void reportEncodedFrame(int size) {
    }

    public int getAdjustedBitrateBps() {
        return this.targetBitrateBps;
    }

    public double getAdjustedFramerateFps() {
        return this.targetFramerateFps;
    }
}
