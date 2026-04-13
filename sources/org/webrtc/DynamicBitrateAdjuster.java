package org.webrtc;

public class DynamicBitrateAdjuster extends BaseBitrateAdjuster {
    private static final double BITRATE_ADJUSTMENT_MAX_SCALE = 4.0d;
    private static final double BITRATE_ADJUSTMENT_SEC = 3.0d;
    private static final int BITRATE_ADJUSTMENT_STEPS = 20;
    private static final double BITS_PER_BYTE = 8.0d;
    private int bitrateAdjustmentScaleExp;
    private double deviationBytes;
    private double timeSinceLastAdjustmentMs;

    DynamicBitrateAdjuster() {
    }

    public void setTargets(int targetBitrateBps, double targetFramerateFps) {
        int i = this.targetBitrateBps;
        if (i > 0 && targetBitrateBps < i) {
            this.deviationBytes = (this.deviationBytes * ((double) targetBitrateBps)) / ((double) i);
        }
        super.setTargets(targetBitrateBps, targetFramerateFps);
    }

    public void reportEncodedFrame(int size) {
        double d = this.targetFramerateFps;
        if (d != 0.0d) {
            int i = this.targetBitrateBps;
            double d2 = this.deviationBytes + (((double) size) - ((((double) i) / BITS_PER_BYTE) / d));
            this.deviationBytes = d2;
            this.timeSinceLastAdjustmentMs += 1000.0d / d;
            double deviationThresholdBytes = ((double) i) / BITS_PER_BYTE;
            double deviationCap = BITRATE_ADJUSTMENT_SEC * deviationThresholdBytes;
            double min = Math.min(d2, deviationCap);
            this.deviationBytes = min;
            double max = Math.max(min, -deviationCap);
            this.deviationBytes = max;
            if (this.timeSinceLastAdjustmentMs > 3000.0d) {
                if (max > deviationThresholdBytes) {
                    int i2 = this.bitrateAdjustmentScaleExp - ((int) ((max / deviationThresholdBytes) + 0.5d));
                    this.bitrateAdjustmentScaleExp = i2;
                    this.bitrateAdjustmentScaleExp = Math.max(i2, -20);
                    this.deviationBytes = deviationThresholdBytes;
                } else if (max < (-deviationThresholdBytes)) {
                    int i3 = this.bitrateAdjustmentScaleExp + ((int) (((-max) / deviationThresholdBytes) + 0.5d));
                    this.bitrateAdjustmentScaleExp = i3;
                    this.bitrateAdjustmentScaleExp = Math.min(i3, 20);
                    this.deviationBytes = -deviationThresholdBytes;
                }
                this.timeSinceLastAdjustmentMs = 0.0d;
            }
        }
    }

    private double getBitrateAdjustmentScale() {
        return Math.pow(BITRATE_ADJUSTMENT_MAX_SCALE, ((double) this.bitrateAdjustmentScaleExp) / 20.0d);
    }

    public int getAdjustedBitrateBps() {
        return (int) (((double) this.targetBitrateBps) * getBitrateAdjustmentScale());
    }
}
