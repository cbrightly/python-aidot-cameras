package org.glassfish.tyrus.core.cluster;

import jakarta.websocket.CloseReason;
import jakarta.websocket.SendHandler;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import org.glassfish.tyrus.core.cluster.RemoteSession;

public abstract class ClusterContext {
    public static final String CLUSTER_CONTEXT = "org.glassfish.tyrus.core.cluster.ClusterContext";

    public abstract void broadcastBinary(String str, byte[] bArr);

    public abstract void broadcastText(String str, String str2);

    public abstract Future<Void> close(String str);

    public abstract Future<Void> close(String str, CloseReason closeReason);

    public abstract String createConnectionId();

    public abstract String createSessionId();

    public abstract void destroyDistributedUserProperties(String str);

    public abstract Map<RemoteSession.DistributedMapKey, Object> getDistributedSessionProperties(String str);

    public abstract Map<String, Object> getDistributedUserProperties(String str);

    public abstract Set<String> getRemoteSessionIds(String str);

    public abstract boolean isSessionOpen(String str, String str2);

    public abstract void registerBroadcastListener(String str, BroadcastListener broadcastListener);

    public abstract void registerSession(String str, String str2, SessionEventListener sessionEventListener);

    public abstract void registerSessionListener(String str, SessionListener sessionListener);

    public abstract void removeSession(String str, String str2);

    public abstract Future<Void> sendBinary(String str, byte[] bArr);

    public abstract Future<Void> sendBinary(String str, byte[] bArr, boolean z);

    public abstract void sendBinary(String str, byte[] bArr, SendHandler sendHandler);

    public abstract Future<Void> sendPing(String str, byte[] bArr);

    public abstract Future<Void> sendPong(String str, byte[] bArr);

    public abstract Future<Void> sendText(String str, String str2);

    public abstract Future<Void> sendText(String str, String str2, boolean z);

    public abstract void sendText(String str, String str2, SendHandler sendHandler);

    public abstract void shutdown();
}
