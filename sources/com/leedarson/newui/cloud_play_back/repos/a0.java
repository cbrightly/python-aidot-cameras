package com.leedarson.newui.cloud_play_back.repos;

import com.downloader.c;
import com.downloader.f;
import com.downloader.g;
import com.leedarson.newui.repos.beans.VideoUrlItemBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.util.HashSet;

/* compiled from: PreLoadVideoWrap */
public class a0 {
    /* access modifiers changed from: private */
    public static HashSet<String> a = new HashSet<>();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void c(VideoUrlItemBean data, String eventUuid, String deviceId) {
        Class<String> cls = String.class;
        Class[] clsArr = {VideoUrlItemBean.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{data, eventUuid, deviceId}, (Object) null, changeQuickRedirect, true, 3764, clsArr, Void.TYPE).isSupported && data.type != 2) {
            String dirPath = c0.e(deviceId, eventUuid);
            String fileName = c0.f(data.begin);
            File file = new File(dirPath + fileName);
            if (file.exists()) {
                data.local_path_url = file.getAbsolutePath();
            } else if (!a.contains(data.url)) {
                a.add(data.url);
                g.c(data.url, dirPath, fileName).a().G(new b()).L(new a(data, file));
            }
        }
    }

    /* compiled from: PreLoadVideoWrap */
    public class b implements f {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void a() {
        }
    }

    /* compiled from: PreLoadVideoWrap */
    public class a implements c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ VideoUrlItemBean a;
        final /* synthetic */ File b;

        a(VideoUrlItemBean videoUrlItemBean, File file) {
            this.a = videoUrlItemBean;
            this.b = file;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3766, new Class[0], Void.TYPE).isSupported) {
                this.a.local_path_url = this.b.getAbsolutePath();
                a0.a.remove(this.a.url);
            }
        }

        public void b(com.downloader.a error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 3767, new Class[]{com.downloader.a.class}, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("downloader", "downloader onError error=" + error.toString());
                a0.a.remove(this.a.url);
            }
        }
    }

    public static boolean b(VideoUrlItemBean data, String deviceId, String eventUuid) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data, deviceId, eventUuid}, (Object) null, changeQuickRedirect2, true, 3765, new Class[]{VideoUrlItemBean.class, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String dirPath = c0.e(deviceId, eventUuid);
        String fileName = c0.f(data.begin);
        return new File(dirPath + fileName).exists();
    }
}
