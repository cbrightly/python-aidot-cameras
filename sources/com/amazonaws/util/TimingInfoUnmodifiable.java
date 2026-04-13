package com.amazonaws.util;

public final class TimingInfoUnmodifiable extends TimingInfo {
    TimingInfoUnmodifiable(Long startEpochTimeMilli, long startTimeNano, Long endTimeNano) {
        super(startEpochTimeMilli, startTimeNano, endTimeNano);
    }

    public void setEndTime(long endTime) {
        throw new UnsupportedOperationException();
    }

    public void setEndTimeNano(long endTimeNano) {
        throw new UnsupportedOperationException();
    }

    public TimingInfo endTiming() {
        throw new UnsupportedOperationException();
    }
}
