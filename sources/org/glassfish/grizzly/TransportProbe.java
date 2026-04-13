package org.glassfish.grizzly;

public interface TransportProbe {
    void onBeforePauseEvent(Transport transport);

    void onBeforeResumeEvent(Transport transport);

    void onBeforeStartEvent(Transport transport);

    void onBeforeStopEvent(Transport transport);

    void onConfigChangeEvent(Transport transport);

    void onErrorEvent(Transport transport, Throwable th);

    void onPauseEvent(Transport transport);

    void onResumeEvent(Transport transport);

    void onStartEvent(Transport transport);

    void onStopEvent(Transport transport);

    public static class Adapter implements TransportProbe {
        public void onBeforeStartEvent(Transport transport) {
        }

        public void onStartEvent(Transport transport) {
        }

        public void onBeforeStopEvent(Transport transport) {
        }

        public void onStopEvent(Transport transport) {
        }

        public void onBeforePauseEvent(Transport transport) {
        }

        public void onPauseEvent(Transport transport) {
        }

        public void onBeforeResumeEvent(Transport transport) {
        }

        public void onResumeEvent(Transport transport) {
        }

        public void onConfigChangeEvent(Transport transport) {
        }

        public void onErrorEvent(Transport transport, Throwable error) {
        }
    }
}
