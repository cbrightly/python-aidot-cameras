package com.leedarson.bean;

public class AmpDotBean {
    public double amplitude;
    public float frequency;
    public long timestamp;

    public AmpDotBean(float frequency2, double amplitude2, long timestamp2) {
        this.frequency = frequency2;
        this.amplitude = amplitude2;
        this.timestamp = timestamp2;
    }
}
