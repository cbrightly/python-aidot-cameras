package zendesk.messaging.android.push.internal;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.c;
import zendesk.android.messaging.b;
import zendesk.messaging.android.internal.d;

/* compiled from: NotificationBuilder.kt */
public final class a {
    @NotNull
    public static final C0551a a = new C0551a((DefaultConstructorMarker) null);
    @NotNull
    private final NotificationCompat.Builder b;
    @NotNull
    private final Context c;

    public a(@NotNull NotificationCompat.Builder compatBuilder, @NotNull Context context) {
        k.e(compatBuilder, "compatBuilder");
        k.e(context, "context");
        this.b = compatBuilder;
        this.c = context;
    }

    @NotNull
    public final a f(int smallIconId) {
        this.b.setSmallIcon(smallIconId);
        return this;
    }

    @NotNull
    public final a g(@Nullable NotificationCompat.Style style) {
        this.b.setStyle(style);
        return this;
    }

    @NotNull
    public final a d(@NotNull String text, long received, @NotNull Person person) {
        k.e(text, "text");
        k.e(person, "person");
        g(new NotificationCompat.MessagingStyle(person).addMessage(new NotificationCompat.MessagingStyle.Message((CharSequence) text, received, person)));
        return this;
    }

    @NotNull
    public final a c(@NotNull String category) {
        k.e(category, "category");
        this.b.setCategory(category);
        return this;
    }

    @NotNull
    public final a b(boolean autoCancel) {
        this.b.setAutoCancel(autoCancel);
        return this;
    }

    @NotNull
    public final a e() {
        int flags;
        b h = c.a.a().h();
        Intent intent = null;
        d messaging = h instanceof d ? (d) h : null;
        if (messaging != null) {
            intent = messaging.q(this.c, 805306368);
        }
        if (intent == null) {
            intent = this.c.getPackageManager().getLaunchIntentForPackage(this.c.getPackageName());
        }
        Intent intentToLaunch = intent;
        if (Build.VERSION.SDK_INT > 30) {
            flags = 1140850688;
        } else {
            flags = 1073741824;
        }
        if (intentToLaunch != null) {
            Context context = this.c;
            PushAutoTrackHelper.hookIntentGetActivity(context, 0, intentToLaunch, flags);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentToLaunch, flags);
            PushAutoTrackHelper.hookPendingIntentGetActivity(pendingIntent, context, 0, intentToLaunch, flags);
            this.b.setContentIntent(pendingIntent);
        }
        return this;
    }

    @NotNull
    public final Notification a() {
        Notification build = this.b.build();
        k.d(build, "compatBuilder.build()");
        return build;
    }

    /* renamed from: zendesk.messaging.android.push.internal.a$a  reason: collision with other inner class name */
    /* compiled from: NotificationBuilder.kt */
    public static final class C0551a {
        public /* synthetic */ C0551a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0551a() {
        }
    }
}
