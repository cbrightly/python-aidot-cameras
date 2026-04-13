package org.glassfish.grizzly;

public interface IOEventLifeCycleListener {
    void onComplete(Context context, Object obj);

    void onContextManualIOEventControl(Context context);

    void onContextResume(Context context);

    void onContextSuspend(Context context);

    void onError(Context context, Object obj);

    void onLeave(Context context);

    void onNotRun(Context context);

    void onReregister(Context context);

    void onRerun(Context context, Context context2);

    @Deprecated
    void onTerminate(Context context);

    public static class Adapter implements IOEventLifeCycleListener {
        public void onContextSuspend(Context context) {
        }

        public void onContextResume(Context context) {
        }

        public void onComplete(Context context, Object data) {
        }

        public void onTerminate(Context context) {
        }

        public void onError(Context context, Object description) {
        }

        public void onNotRun(Context context) {
        }

        public void onContextManualIOEventControl(Context context) {
        }

        public void onReregister(Context context) {
        }

        public void onLeave(Context context) {
        }

        public void onRerun(Context context, Context newContext) {
        }
    }
}
