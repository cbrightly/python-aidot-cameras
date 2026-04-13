package androidx.activity.result;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityOptionsCompat;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ActivityResultCaller.kt */
public final class ActivityResultCallerLauncher<I, O> extends ActivityResultLauncher<x> {
    @NotNull
    private final ActivityResultContract<I, O> callerContract;
    private final I input;
    @NotNull
    private final ActivityResultLauncher<I> launcher;
    @NotNull
    private final g resultContract$delegate = i.b(new ActivityResultCallerLauncher$resultContract$2(this));

    @NotNull
    public final ActivityResultLauncher<I> getLauncher() {
        return this.launcher;
    }

    @NotNull
    public final ActivityResultContract<I, O> getCallerContract() {
        return this.callerContract;
    }

    public final I getInput() {
        return this.input;
    }

    public ActivityResultCallerLauncher(@NotNull ActivityResultLauncher<I> launcher2, @NotNull ActivityResultContract<I, O> callerContract2, I input2) {
        k.e(launcher2, "launcher");
        k.e(callerContract2, "callerContract");
        this.launcher = launcher2;
        this.callerContract = callerContract2;
        this.input = input2;
    }

    @NotNull
    public final ActivityResultContract<x, O> getResultContract() {
        return (ActivityResultContract) this.resultContract$delegate.getValue();
    }

    public void launch(@Nullable x xVar, @Nullable ActivityOptionsCompat options) {
        this.launcher.launch(this.input, options);
    }

    public void unregister() {
        this.launcher.unregister();
    }

    @NotNull
    public ActivityResultContract<x, O> getContract() {
        return getResultContract();
    }
}
