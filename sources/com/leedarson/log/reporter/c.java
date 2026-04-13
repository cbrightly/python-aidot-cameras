package com.leedarson.log.reporter;

import android.content.Context;
import com.leedarson.log.mgr.q;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: FileReporter */
public class c implements d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private String b;

    public c(Context context) {
        this.a = context;
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1335, new Class[0], Void.TYPE).isSupported) {
            q.r().g(this.b);
        }
    }

    public void a(String content) {
        if (!PatchProxy.proxy(new Object[]{content}, this, changeQuickRedirect, false, 1337, new Class[]{String.class}, Void.TYPE).isSupported) {
            String con = content.replace("\n", "");
            this.b = con + "\n";
        }
    }
}
