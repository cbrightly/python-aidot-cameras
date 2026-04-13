package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.Connection;

public final class HttpServerProbeNotifier {
    HttpServerProbeNotifier() {
    }

    static void notifyRequestReceive(HttpServerFilter filter, Connection connection, Request request) {
        HttpServerProbe[] probes = (HttpServerProbe[]) filter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpServerProbe probe : probes) {
                probe.onRequestReceiveEvent(filter, connection, request);
            }
        }
    }

    static void notifyRequestComplete(HttpServerFilter filter, Connection connection, Response response) {
        HttpServerProbe[] probes = (HttpServerProbe[]) filter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpServerProbe probe : probes) {
                probe.onRequestCompleteEvent(filter, connection, response);
            }
        }
    }

    static void notifyRequestSuspend(HttpServerFilter filter, Connection connection, Request request) {
        HttpServerProbe[] probes = (HttpServerProbe[]) filter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpServerProbe probe : probes) {
                probe.onRequestSuspendEvent(filter, connection, request);
            }
        }
    }

    static void notifyRequestResume(HttpServerFilter filter, Connection connection, Request request) {
        HttpServerProbe[] probes = (HttpServerProbe[]) filter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpServerProbe probe : probes) {
                probe.onRequestResumeEvent(filter, connection, request);
            }
        }
    }

    static void notifyRequestTimeout(HttpServerFilter filter, Connection connection, Request request) {
        HttpServerProbe[] probes = (HttpServerProbe[]) filter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpServerProbe probe : probes) {
                probe.onRequestTimeoutEvent(filter, connection, request);
            }
        }
    }

    static void notifyRequestCancel(HttpServerFilter filter, Connection connection, Request request) {
        HttpServerProbe[] probes = (HttpServerProbe[]) filter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpServerProbe probe : probes) {
                probe.onRequestCancelEvent(filter, connection, request);
            }
        }
    }

    static void notifyBeforeService(HttpServerFilter filter, Connection connection, Request request, HttpHandler httpHandler) {
        HttpServerProbe[] probes = (HttpServerProbe[]) filter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpServerProbe probe : probes) {
                probe.onBeforeServiceEvent(filter, connection, request, httpHandler);
            }
        }
    }
}
