package com.clj.fastble.data;

/* compiled from: BleScanState */
public enum b {
    STATE_IDLE(-1),
    STATE_SCANNING(1);
    
    private int code;

    private b(int code2) {
        this.code = code2;
    }

    public int getCode() {
        return this.code;
    }
}
