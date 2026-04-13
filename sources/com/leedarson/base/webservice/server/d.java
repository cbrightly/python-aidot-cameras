package com.leedarson.base.webservice.server;

import androidx.annotation.NonNull;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.yanzhenjie.andserver.error.NotFoundException;
import com.yanzhenjie.andserver.framework.body.a;
import com.yanzhenjie.andserver.framework.website.b;
import com.yanzhenjie.andserver.http.c;
import com.yanzhenjie.andserver.http.i;
import java.io.File;

/* compiled from: CusStorageWebsite */
public class d extends b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String j;

    public d(String rootPath) {
        super(rootPath);
        this.j = rootPath;
    }

    public i g(c request, com.yanzhenjie.andserver.http.d dVar) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{request, dVar}, this, changeQuickRedirect2, false, 967, new Class[]{c.class, com.yanzhenjie.andserver.http.d.class}, i.class);
        if (proxy.isSupported) {
            return (i) proxy.result;
        }
        String httpPath = request.getPath();
        File resource = j(httpPath);
        if (resource != null) {
            return new a(resource);
        }
        throw new NotFoundException(httpPath);
    }

    private File j(@NonNull String httpPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{httpPath}, this, changeQuickRedirect, false, 968, new Class[]{String.class}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        if ("/".equals(httpPath)) {
            File indexFile = new File(this.j, h());
            if (!indexFile.exists() || !indexFile.isFile()) {
                return null;
            }
            return indexFile;
        }
        File sourceFile = new File(this.j, httpPath);
        if (!sourceFile.exists()) {
            return null;
        }
        if (sourceFile.isFile()) {
            return sourceFile;
        }
        File indexFile2 = new File(this.j, h());
        if (!indexFile2.exists() || !indexFile2.isFile()) {
            return null;
        }
        return indexFile2;
    }
}
