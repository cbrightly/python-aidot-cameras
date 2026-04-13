package zendesk.messaging.android.internal;

import android.app.Activity;
import android.content.Intent;
import com.yanzhenjie.andserver.util.h;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: AttachmentIntents.kt */
public final class a implements b {
    @NotNull
    private final Activity a;

    public a(@NotNull Activity activity) {
        k.e(activity, "activity");
        this.a = activity;
    }

    public boolean b() {
        List resolveInfo = this.a.getPackageManager().queryIntentActivities(c(), 0);
        k.d(resolveInfo, "activity.packageManager.…tentActivities(intent, 0)");
        return !resolveInfo.isEmpty();
    }

    public boolean a() {
        List resolveInfo = this.a.getPackageManager().queryIntentActivities(d(), 0);
        k.d(resolveInfo, "activity.packageManager.…tentActivities(intent, 0)");
        return !resolveInfo.isEmpty();
    }

    @NotNull
    public Intent c() {
        return new Intent("android.media.action.IMAGE_CAPTURE");
    }

    @NotNull
    public Intent d() {
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.setType(h.ALL_VALUE);
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setFlags(65);
        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        return intent;
    }
}
