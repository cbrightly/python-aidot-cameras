package androidx.activity.result.contract;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class ActivityResultContract<I, O> {
    @NonNull
    public abstract Intent createIntent(@NonNull Context context, @SuppressLint({"UnknownNullness"}) I i);

    @SuppressLint({"UnknownNullness"})
    public abstract O parseResult(int i, @Nullable Intent intent);

    @Nullable
    public SynchronousResult<O> getSynchronousResult(@NonNull Context context, @SuppressLint({"UnknownNullness"}) I i) {
        return null;
    }

    public static final class SynchronousResult<T> {
        @SuppressLint({"UnknownNullness"})
        private final T mValue;

        public SynchronousResult(@SuppressLint({"UnknownNullness"}) T value) {
            this.mValue = value;
        }

        @SuppressLint({"UnknownNullness"})
        public T getValue() {
            return this.mValue;
        }
    }
}
