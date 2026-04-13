package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.Connection;

public interface HttpServerProbe {
    void onBeforeServiceEvent(HttpServerFilter httpServerFilter, Connection connection, Request request, HttpHandler httpHandler);

    void onRequestCancelEvent(HttpServerFilter httpServerFilter, Connection connection, Request request);

    void onRequestCompleteEvent(HttpServerFilter httpServerFilter, Connection connection, Response response);

    void onRequestReceiveEvent(HttpServerFilter httpServerFilter, Connection connection, Request request);

    void onRequestResumeEvent(HttpServerFilter httpServerFilter, Connection connection, Request request);

    void onRequestSuspendEvent(HttpServerFilter httpServerFilter, Connection connection, Request request);

    void onRequestTimeoutEvent(HttpServerFilter httpServerFilter, Connection connection, Request request);

    public static class Adapter implements HttpServerProbe {
        public void onRequestReceiveEvent(HttpServerFilter filter, Connection connection, Request request) {
        }

        public void onRequestCompleteEvent(HttpServerFilter filter, Connection connection, Response response) {
        }

        public void onRequestSuspendEvent(HttpServerFilter filter, Connection connection, Request request) {
        }

        public void onRequestResumeEvent(HttpServerFilter filter, Connection connection, Request request) {
        }

        public void onRequestTimeoutEvent(HttpServerFilter filter, Connection connection, Request request) {
        }

        public void onRequestCancelEvent(HttpServerFilter filter, Connection connection, Request request) {
        }

        public void onBeforeServiceEvent(HttpServerFilter filter, Connection connection, Request request, HttpHandler httpHandler) {
        }
    }
}
