package androidx.camera.core.impl.utils.futures;

import androidx.annotation.Nullable;
import com.google.common.util.concurrent.ListenableFuture;

@FunctionalInterface
public interface AsyncFunction<I, O> {
    ListenableFuture<O> apply(@Nullable I i);
}
