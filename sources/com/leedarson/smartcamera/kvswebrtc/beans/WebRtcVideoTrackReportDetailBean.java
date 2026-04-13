package com.leedarson.smartcamera.kvswebrtc.beans;

public class WebRtcVideoTrackReportDetailBean {
    public String decoderImplementation = "";
    public long framesDecoded = 0;
    public long framesDropped = 0;
    public long framesReceived = 0;
    public long headerBytesReceived = 0;
    public long infoUpdateTime = 0;
    public float jitter = 0.0f;
    public float jitterBufferDelay = 0.0f;
    public int jitterBufferEmittedCount = 0;
    public long keyFramesDecoded = 0;
    public long packetsLost = 0;
    public long packetsReceived = 0;
    public long timestampUs = 0;
    public float totalDecodeTime = 0.0f;
    public float totalInterFrameDelay = 0.0f;
    public float totalSquaredInterFrameDelay = 0.0f;
    public String trackId = "";
}
