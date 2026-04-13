package zendesk.conversationkit.android.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: HostAppInfo.kt */
public final class x {
    @NotNull
    private final PackageManager a;
    @NotNull
    private final String b;
    @NotNull
    private final String c;
    @NotNull
    private final String d;
    @NotNull
    private final String e;
    @NotNull
    private final String f;
    @NotNull
    private final String g;
    @NotNull
    private final String h;
    @NotNull
    private final String i;
    @NotNull
    private final String j;

    public x(@NotNull Context context) {
        String str;
        String networkOperatorName;
        String obj;
        k.e(context, "context");
        PackageManager packageManager = context.getPackageManager();
        k.d(packageManager, "context.packageManager");
        this.a = packageManager;
        String packageName = context.getPackageName();
        String str2 = "";
        packageName = packageName == null ? str2 : packageName;
        this.b = packageName;
        String installerPackageName = packageManager.getInstallerPackageName(packageName);
        this.c = installerPackageName == null ? str2 : installerPackageName;
        CharSequence applicationLabel = packageManager.getApplicationLabel(context.getApplicationInfo());
        this.d = (applicationLabel == null || (obj = applicationLabel.toString()) == null) ? str2 : obj;
        try {
            str = packageManager.getPackageInfo(packageName, 0).versionName;
            if (str == null) {
                str = str2;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            str = str2;
        }
        this.e = str;
        f fVar = f.a;
        this.f = fVar.a();
        this.g = fVar.b();
        this.h = "Android";
        this.i = fVar.c();
        Object systemService = context.getSystemService("phone");
        TelephonyManager telephonyManager = systemService instanceof TelephonyManager ? (TelephonyManager) systemService : null;
        if (!(telephonyManager == null || (networkOperatorName = telephonyManager.getNetworkOperatorName()) == null)) {
            str2 = networkOperatorName;
        }
        this.j = str2;
    }

    @NotNull
    public final String c() {
        return this.b;
    }

    @NotNull
    public final String a() {
        return this.c;
    }

    @NotNull
    public final String b() {
        return this.d;
    }

    @NotNull
    public final String d() {
        return this.e;
    }

    @NotNull
    public final String f() {
        return this.f;
    }

    @NotNull
    public final String g() {
        return this.g;
    }

    @NotNull
    public final String h() {
        return this.h;
    }

    @NotNull
    public final String i() {
        return this.i;
    }

    @NotNull
    public final String e() {
        return this.j;
    }
}
