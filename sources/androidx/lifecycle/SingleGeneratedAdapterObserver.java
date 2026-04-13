package androidx.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

public class SingleGeneratedAdapterObserver implements LifecycleEventObserver {
    private final GeneratedAdapter mGeneratedAdapter;

    SingleGeneratedAdapterObserver(GeneratedAdapter generatedAdapter) {
        this.mGeneratedAdapter = generatedAdapter;
    }

    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        this.mGeneratedAdapter.callMethods(source, event, false, (MethodCallsLogger) null);
        this.mGeneratedAdapter.callMethods(source, event, true, (MethodCallsLogger) null);
    }
}
