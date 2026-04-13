package com.amazonaws.kinesisvideo.stream.throttling;

public interface BandwidthThrottler {
    int getAllowedBytes(int i);

    void setUpstreamKbps(long j);
}
