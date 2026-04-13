package org.webrtc;

public class FramerateBitrateAdjuster extends BaseBitrateAdjuster {
    private static final int DEFAULT_FRAMERATE_FPS = 30;

    FramerateBitrateAdjuster() {
    }

    public void setTargets(int targetBitrateBps, double targetFramerateFps) {
        this.targetFramerateFps = 30.0d;
        this.targetBitrateBps = (int) (((double) (targetBitrateBps * 30)) / targetFramerateFps);
    }
}
