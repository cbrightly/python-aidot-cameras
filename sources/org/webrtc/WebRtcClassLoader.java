package org.webrtc;

public class WebRtcClassLoader {
    WebRtcClassLoader() {
    }

    @CalledByNative
    static Object getClassLoader() {
        Object loader = WebRtcClassLoader.class.getClassLoader();
        if (loader != null) {
            return loader;
        }
        throw new RuntimeException("Failed to get WebRTC class loader.");
    }
}
