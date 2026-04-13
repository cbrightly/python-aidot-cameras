package androidx.work.impl;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface ExecutionListener {
    void onExecuted(@NonNull String str, boolean z);
}
