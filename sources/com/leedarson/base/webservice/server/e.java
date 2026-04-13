package com.leedarson.base.webservice.server;

import com.alibaba.android.arouter.launcher.a;
import com.leedarson.base.utils.l;
import com.leedarson.serviceinterface.BleMeshService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.yanzhenjie.andserver.http.multipart.b;
import java.io.File;
import java.io.IOException;
import org.json.JSONObject;

/* compiled from: FileController */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;

    /* access modifiers changed from: package-private */
    public String a(b file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 969, new Class[]{b.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        b("httpServer收到文件上传请求:" + file.getFilename());
        File dir = new File(com.leedarson.base.webservice.utils.b.b().a().getCacheDir() + File.separator + "ipcLog");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File localFile = new File(dir, file.getFilename());
        if (!localFile.exists()) {
            try {
                localFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            file.transferTo(localFile);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        BleMeshService bleMeshService = (BleMeshService) a.c().g(BleMeshService.class);
        if (bleMeshService != null) {
            JSONObject buildBody = bleMeshService.getELKMessageBody(this, l.d(localFile), "deviceLogUpLoad", BleMeshService.ACTION_ADD_DEVICES, "info", "LdsIpc");
            String name = localFile.getName();
            l.h(dir, name, buildBody.toString() + "\n", true);
        }
        b("返回文件路径:" + file.getFilename());
        return localFile.getAbsolutePath();
    }

    private void b(String s) {
        if (!PatchProxy.proxy(new Object[]{s}, this, changeQuickRedirect, false, 970, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("LdsCore").a(s, new Object[0]);
        }
    }
}
