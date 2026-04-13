package org.glassfish.tyrus.core.cluster;

public interface BroadcastListener {
    void onBroadcast(String str);

    void onBroadcast(byte[] bArr);
}
