package com.didichuxing.doraemonkit.kit.loginfo.reader;

public abstract class AbsLogcatReader implements LogcatReader {
    protected boolean recordingMode;

    public AbsLogcatReader(boolean recordingMode2) {
        this.recordingMode = recordingMode2;
    }

    public boolean isRecordingMode() {
        return this.recordingMode;
    }
}
