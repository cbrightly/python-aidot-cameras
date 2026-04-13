package com.google.android.datatransport.runtime;

import androidx.annotation.WorkerThread;
import com.google.android.datatransport.Priority;
import com.google.android.datatransport.Transport;

public final class ForcedSender {
    @WorkerThread
    public static void sendBlocking(Transport<?> transport, Priority priority) {
        TransportRuntime.getInstance().getUploader().logAndUpdateState(getTransportContextOrThrow(transport).withPriority(priority), 1);
    }

    private static TransportContext getTransportContextOrThrow(Transport<?> transport) {
        if (transport instanceof TransportImpl) {
            return ((TransportImpl) transport).getTransportContext();
        }
        throw new IllegalArgumentException("Expected instance of TransportImpl.");
    }

    private ForcedSender() {
    }
}
