package com.leedarson.base.webservice.server;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.yanzhenjie.andserver.framework.config.b;
import com.yanzhenjie.andserver.framework.config.c;
import java.io.File;

/* compiled from: AppConfig */
public class a implements c {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void a(Context context, c.a delegate) {
        Class[] clsArr = {Context.class, c.a.class};
        if (!PatchProxy.proxy(new Object[]{context, delegate}, this, changeQuickRedirect, false, 955, clsArr, Void.TYPE).isSupported) {
            delegate.b(new d(context.getFilesDir().getAbsolutePath() + "/web"));
            delegate.a(b.e().e(31457280).g(5242880).h(20480).i(new File(context.getCacheDir(), "_server_upload_cache_")).f());
        }
    }
}
