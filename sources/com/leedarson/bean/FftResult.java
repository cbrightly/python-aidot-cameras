package com.leedarson.bean;

public class FftResult {
    public double maxAmp;
    public float resultFreq;
    public int[] simpleAmpArr;

    public FftResult(float resultFreq2, int[] ampArr, double maxAmp2) {
        this.resultFreq = resultFreq2;
        this.simpleAmpArr = ampArr;
        this.maxAmp = maxAmp2;
    }
}
