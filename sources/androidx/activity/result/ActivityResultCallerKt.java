package androidx.activity.result;

import androidx.activity.result.contract.ActivityResultContract;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: ActivityResultCaller.kt */
public final class ActivityResultCallerKt {
    @NotNull
    public static final <I, O> ActivityResultLauncher<x> registerForActivityResult(@NotNull ActivityResultCaller $this$registerForActivityResult, @NotNull ActivityResultContract<I, O> contract, I input, @NotNull ActivityResultRegistry registry, @NotNull l<? super O, x> callback) {
        k.e($this$registerForActivityResult, "<this>");
        k.e(contract, "contract");
        k.e(registry, "registry");
        k.e(callback, "callback");
        ActivityResultLauncher resultLauncher = $this$registerForActivityResult.registerForActivityResult(contract, registry, new ActivityResultCallerKt$registerForActivityResult$resultLauncher$1(callback));
        k.d(resultLauncher, "callback: (O) -> Unit\n): ActivityResultLauncher<Unit> {\n    val resultLauncher = registerForActivityResult(contract, registry) { callback(it) }");
        return new ActivityResultCallerLauncher(resultLauncher, contract, input);
    }

    @NotNull
    public static final <I, O> ActivityResultLauncher<x> registerForActivityResult(@NotNull ActivityResultCaller $this$registerForActivityResult, @NotNull ActivityResultContract<I, O> contract, I input, @NotNull l<? super O, x> callback) {
        k.e($this$registerForActivityResult, "<this>");
        k.e(contract, "contract");
        k.e(callback, "callback");
        ActivityResultLauncher resultLauncher = $this$registerForActivityResult.registerForActivityResult(contract, new ActivityResultCallerKt$registerForActivityResult$resultLauncher$2(callback));
        k.d(resultLauncher, "callback: (O) -> Unit\n): ActivityResultLauncher<Unit> {\n    val resultLauncher = registerForActivityResult(contract) { callback(it) }");
        return new ActivityResultCallerLauncher(resultLauncher, contract, input);
    }
}
