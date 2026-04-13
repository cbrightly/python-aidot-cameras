package com.google.firebase.heartbeatinfo;

import androidx.annotation.NonNull;

public interface HeartBeatInfo {
    @NonNull
    HeartBeat getHeartBeatCode(@NonNull String str);

    public enum HeartBeat {
        NONE(0),
        SDK(1),
        GLOBAL(2),
        COMBINED(3);
        
        private final int code;

        private HeartBeat(int code2) {
            this.code = code2;
        }

        public int getCode() {
            return this.code;
        }
    }
}
