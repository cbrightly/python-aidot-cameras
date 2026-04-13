package androidx.activity.result;

import android.content.Context;
import android.content.Intent;
import androidx.activity.result.contract.ActivityResultContract;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u000b\n\u0002\b\u0002\n\u0002\b\u0003*\u0001\u0002\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0002\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"I", "O", "androidx/activity/result/ActivityResultCallerLauncher$resultContract$2$1", "<anonymous>", "()Landroidx/activity/result/ActivityResultCallerLauncher$resultContract$2$1;"}, k = 3, mv = {1, 5, 1})
/* compiled from: ActivityResultCaller.kt */
public final class ActivityResultCallerLauncher$resultContract$2 extends kotlin.jvm.internal.l implements a<AnonymousClass1> {
    final /* synthetic */ ActivityResultCallerLauncher<I, O> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ActivityResultCallerLauncher$resultContract$2(ActivityResultCallerLauncher<I, O> activityResultCallerLauncher) {
        super(0);
        this.this$0 = activityResultCallerLauncher;
    }

    @NotNull
    public final AnonymousClass1 invoke() {
        final ActivityResultCallerLauncher<I, O> activityResultCallerLauncher = this.this$0;
        return new ActivityResultContract<x, O>() {
            @NotNull
            public Intent createIntent(@NotNull Context context, @Nullable x xVar) {
                k.e(context, "context");
                Intent createIntent = activityResultCallerLauncher.getCallerContract().createIntent(context, activityResultCallerLauncher.getInput());
                k.d(createIntent, "callerContract.createIntent(context, input)");
                return createIntent;
            }

            public O parseResult(int resultCode, @Nullable Intent intent) {
                return activityResultCallerLauncher.getCallerContract().parseResult(resultCode, intent);
            }
        };
    }
}
