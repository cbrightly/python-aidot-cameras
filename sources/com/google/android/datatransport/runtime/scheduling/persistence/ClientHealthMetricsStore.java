package com.google.android.datatransport.runtime.scheduling.persistence;

import com.google.android.datatransport.runtime.firebase.transport.ClientMetrics;
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped;

public interface ClientHealthMetricsStore {
    ClientMetrics loadClientMetrics();

    void recordLogEventDropped(long j, LogEventDropped.Reason reason, String str);

    void resetClientMetrics();
}
