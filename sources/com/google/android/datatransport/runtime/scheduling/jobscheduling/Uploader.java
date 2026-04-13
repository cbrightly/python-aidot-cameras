package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.runtime.EncodedPayload;
import com.google.android.datatransport.runtime.EventInternal;
import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.backends.BackendRegistry;
import com.google.android.datatransport.runtime.backends.BackendRequest;
import com.google.android.datatransport.runtime.backends.BackendResponse;
import com.google.android.datatransport.runtime.backends.TransportBackend;
import com.google.android.datatransport.runtime.firebase.transport.ClientMetrics;
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped;
import com.google.android.datatransport.runtime.logging.Logging;
import com.google.android.datatransport.runtime.scheduling.persistence.ClientHealthMetricsStore;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStore;
import com.google.android.datatransport.runtime.scheduling.persistence.PersistedEvent;
import com.google.android.datatransport.runtime.synchronization.SynchronizationException;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;
import com.google.android.datatransport.runtime.time.Clock;
import com.google.android.datatransport.runtime.time.Monotonic;
import com.google.android.datatransport.runtime.time.WallTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

public class Uploader {
    private static final String CLIENT_HEALTH_METRICS_LOG_SOURCE = "GDT_CLIENT_METRICS";
    private static final String LOG_TAG = "Uploader";
    private final BackendRegistry backendRegistry;
    private final ClientHealthMetricsStore clientHealthMetricsStore;
    private final Clock clock;
    private final Context context;
    private final EventStore eventStore;
    private final Executor executor;
    private final SynchronizationGuard guard;
    private final Clock uptimeClock;
    private final WorkScheduler workScheduler;

    public /* synthetic */ Object c(Iterable iterable, TransportContext transportContext, long j) {
        lambda$logAndUpdateState$4(iterable, transportContext, j);
        return null;
    }

    public /* synthetic */ Object d(Iterable iterable) {
        lambda$logAndUpdateState$5(iterable);
        return null;
    }

    public /* synthetic */ Object e() {
        lambda$logAndUpdateState$6();
        return null;
    }

    public /* synthetic */ Object f(Map map) {
        lambda$logAndUpdateState$7(map);
        return null;
    }

    public /* synthetic */ Object g(TransportContext transportContext, long j) {
        lambda$logAndUpdateState$8(transportContext, j);
        return null;
    }

    public /* synthetic */ Object h(TransportContext transportContext, int i) {
        lambda$upload$0(transportContext, i);
        return null;
    }

    public Uploader(Context context2, BackendRegistry backendRegistry2, EventStore eventStore2, WorkScheduler workScheduler2, Executor executor2, SynchronizationGuard guard2, @WallTime Clock clock2, @Monotonic Clock uptimeClock2, ClientHealthMetricsStore clientHealthMetricsStore2) {
        this.context = context2;
        this.backendRegistry = backendRegistry2;
        this.eventStore = eventStore2;
        this.workScheduler = workScheduler2;
        this.executor = executor2;
        this.guard = guard2;
        this.clock = clock2;
        this.uptimeClock = uptimeClock2;
        this.clientHealthMetricsStore = clientHealthMetricsStore2;
    }

    /* access modifiers changed from: package-private */
    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void upload(TransportContext transportContext, int attemptNumber, Runnable callback) {
        this.executor.execute(new d(this, transportContext, attemptNumber, callback));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$upload$1 */
    public /* synthetic */ void i(TransportContext transportContext, int attemptNumber, Runnable callback) {
        try {
            SynchronizationGuard synchronizationGuard = this.guard;
            EventStore eventStore2 = this.eventStore;
            Objects.requireNonNull(eventStore2);
            synchronizationGuard.runCriticalSection(new b(eventStore2));
            if (!isNetworkAvailable()) {
                this.guard.runCriticalSection(new k(this, transportContext, attemptNumber));
            } else {
                logAndUpdateState(transportContext, attemptNumber);
            }
        } catch (SynchronizationException e) {
            this.workScheduler.schedule(transportContext, attemptNumber + 1);
        } catch (Throwable th) {
            callback.run();
            throw th;
        }
        callback.run();
    }

    private /* synthetic */ Object lambda$upload$0(TransportContext transportContext, int attemptNumber) {
        this.workScheduler.schedule(transportContext, attemptNumber + 1);
        return null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public BackendResponse logAndUpdateState(TransportContext transportContext, int attemptNumber) {
        BackendResponse response;
        TransportContext transportContext2 = transportContext;
        TransportBackend backend = this.backendRegistry.get(transportContext.getBackendName());
        BackendResponse response2 = BackendResponse.ok(0);
        long maxNextRequestWaitMillis = 0;
        while (((Boolean) this.guard.runCriticalSection(new e(this, transportContext2))).booleanValue()) {
            Iterable<PersistedEvent> persistedEvents = (Iterable) this.guard.runCriticalSection(new g(this, transportContext2));
            if (!persistedEvents.iterator().hasNext()) {
                return response2;
            }
            if (backend == null) {
                Logging.d(LOG_TAG, "Unknown backend for %s, deleting event batch for it...", (Object) transportContext2);
                response = BackendResponse.fatalError();
            } else {
                List<EventInternal> eventInternals = new ArrayList<>();
                for (PersistedEvent persistedEvent : persistedEvents) {
                    eventInternals.add(persistedEvent.getEvent());
                }
                if (transportContext.shouldUploadClientHealthMetrics()) {
                    eventInternals.add(createMetricsEvent(backend));
                }
                response = backend.send(BackendRequest.builder().setEvents(eventInternals).setExtras(transportContext.getExtras()).build());
            }
            if (response.getStatus() == BackendResponse.Status.TRANSIENT_ERROR) {
                this.guard.runCriticalSection(new h(this, persistedEvents, transportContext, maxNextRequestWaitMillis));
                this.workScheduler.schedule(transportContext2, attemptNumber + 1, true);
                return response;
            }
            this.guard.runCriticalSection(new j(this, persistedEvents));
            if (response.getStatus() == BackendResponse.Status.OK) {
                long maxNextRequestWaitMillis2 = Math.max(maxNextRequestWaitMillis, response.getNextRequestWaitMillis());
                if (transportContext.shouldUploadClientHealthMetrics()) {
                    this.guard.runCriticalSection(new l(this));
                }
                maxNextRequestWaitMillis = maxNextRequestWaitMillis2;
            } else if (response.getStatus() == BackendResponse.Status.INVALID_PAYLOAD) {
                Map<String, Integer> countMap = new HashMap<>();
                for (PersistedEvent persistedEvent2 : persistedEvents) {
                    String logSource = persistedEvent2.getEvent().getTransportName();
                    if (!countMap.containsKey(logSource)) {
                        countMap.put(logSource, 1);
                    } else {
                        countMap.put(logSource, Integer.valueOf(countMap.get(logSource).intValue() + 1));
                    }
                }
                this.guard.runCriticalSection(new f(this, countMap));
            }
            response2 = response;
        }
        this.guard.runCriticalSection(new i(this, transportContext2, maxNextRequestWaitMillis));
        return response2;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$logAndUpdateState$2 */
    public /* synthetic */ Boolean a(TransportContext transportContext) {
        return Boolean.valueOf(this.eventStore.hasPendingEventsFor(transportContext));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$logAndUpdateState$3 */
    public /* synthetic */ Iterable b(TransportContext transportContext) {
        return this.eventStore.loadBatch(transportContext);
    }

    private /* synthetic */ Object lambda$logAndUpdateState$4(Iterable persistedEvents, TransportContext transportContext, long finalMaxNextRequestWaitMillis1) {
        this.eventStore.recordFailure(persistedEvents);
        this.eventStore.recordNextCallTime(transportContext, this.clock.getTime() + finalMaxNextRequestWaitMillis1);
        return null;
    }

    private /* synthetic */ Object lambda$logAndUpdateState$5(Iterable persistedEvents) {
        this.eventStore.recordSuccess(persistedEvents);
        return null;
    }

    private /* synthetic */ Object lambda$logAndUpdateState$6() {
        this.clientHealthMetricsStore.resetClientMetrics();
        return null;
    }

    private /* synthetic */ Object lambda$logAndUpdateState$7(Map countMap) {
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            this.clientHealthMetricsStore.recordLogEventDropped((long) entry.getValue().intValue(), LogEventDropped.Reason.INVALID_PAYLOD, entry.getKey());
        }
        return null;
    }

    private /* synthetic */ Object lambda$logAndUpdateState$8(TransportContext transportContext, long finalMaxNextRequestWaitMillis) {
        this.eventStore.recordNextCallTime(transportContext, this.clock.getTime() + finalMaxNextRequestWaitMillis);
        return null;
    }

    @VisibleForTesting
    public EventInternal createMetricsEvent(TransportBackend backend) {
        SynchronizationGuard synchronizationGuard = this.guard;
        ClientHealthMetricsStore clientHealthMetricsStore2 = this.clientHealthMetricsStore;
        Objects.requireNonNull(clientHealthMetricsStore2);
        return backend.decorate(EventInternal.builder().setEventMillis(this.clock.getTime()).setUptimeMillis(this.uptimeClock.getTime()).setTransportName(CLIENT_HEALTH_METRICS_LOG_SOURCE).setEncodedPayload(new EncodedPayload(Encoding.of("proto"), ((ClientMetrics) synchronizationGuard.runCriticalSection(new o(clientHealthMetricsStore2))).toByteArray())).build());
    }
}
