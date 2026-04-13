package com.google.firebase.heartbeatinfo;

import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
public abstract class HeartBeatResult {
    public abstract List<String> getUsedDates();

    public abstract String getUserAgent();

    public static HeartBeatResult create(String userAgent, List<String> dateList) {
        return new AutoValue_HeartBeatResult(userAgent, dateList);
    }
}
