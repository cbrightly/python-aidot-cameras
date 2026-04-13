package androidx.work;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import kotlin.jvm.a;
import kotlin.jvm.internal.k;
import kotlin.reflect.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: OneTimeWorkRequest.kt */
public final class OneTimeWorkRequestKt {
    @NotNull
    public static final OneTimeWorkRequest.Builder setInputMerger(@NotNull OneTimeWorkRequest.Builder $this$setInputMerger, @NotNull @NonNull c<? extends InputMerger> inputMerger) {
        k.e($this$setInputMerger, "<this>");
        k.e(inputMerger, "inputMerger");
        OneTimeWorkRequest.Builder inputMerger2 = $this$setInputMerger.setInputMerger(a.b(inputMerger));
        k.d(inputMerger2, "setInputMerger(inputMerger.java)");
        return inputMerger2;
    }
}
