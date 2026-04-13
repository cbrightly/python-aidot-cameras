package io.ktor.util.pipeline;

/* compiled from: StackWalkingFailed.kt */
public final class j {
    public static final j a = new j();

    private j() {
    }

    public final void a() {
        throw new IllegalStateException("Failed to capture stack frame. This is usually happens when a coroutine is running so the frame stack is changing quickly and the coroutine debug agent is unable to capture it concurrently. You may retry running your test to see this particular trace.".toString());
    }
}
