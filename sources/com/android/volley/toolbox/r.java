package com.android.volley.toolbox;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import androidx.annotation.NonNull;
import com.android.volley.f;
import com.android.volley.j;
import com.android.volley.toolbox.e;
import java.io.File;

/* compiled from: Volley */
public class r {
    @NonNull
    public static j c(Context context, b stack) {
        c network;
        if (stack != null) {
            network = new c(stack);
        } else if (Build.VERSION.SDK_INT >= 9) {
            network = new c((b) new j());
        } else {
            String userAgent = "volley/0";
            try {
                String packageName = context.getPackageName();
                PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
                userAgent = packageName + "/" + info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
            }
            network = new c((i) new f(AndroidHttpClient.newInstance(userAgent)));
        }
        return b(context, network);
    }

    @NonNull
    private static j b(Context context, f network) {
        j queue = new j(new e(new a(context.getApplicationContext())), network);
        queue.g();
        return queue;
    }

    /* compiled from: Volley */
    public class a implements e.c {
        private File a = null;
        final /* synthetic */ Context b;

        a(Context context) {
            this.b = context;
        }

        public File get() {
            if (this.a == null) {
                this.a = new File(this.b.getCacheDir(), "volley");
            }
            return this.a;
        }
    }

    @NonNull
    public static j a(Context context) {
        return c(context, (b) null);
    }
}
