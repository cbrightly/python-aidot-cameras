package com.leedarson.newui.cloud_play_back.repos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.downloader.j;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.w;
import com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView;
import com.leedarson.newui.door_phone.repos.g;
import com.leedarson.newui.repos.beans.EventUrlResponseItemBean;
import com.leedarson.newui.repos.beans.VideoUrlItemBean;
import com.leedarson.newui.repoter.beans.ELKStepRecordBean;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.FileUtils;
import com.leedarson.serviceinterface.utils.IntentUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.microshow.rxffmpeg.RxFFmpegInvoke;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Future;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: ShareRepos */
public class c0 extends g {
    public static ChangeQuickRedirect changeQuickRedirect;
    public io.reactivex.disposables.b b = null;
    public boolean c = false;
    /* access modifiers changed from: private */
    public String d = "ShareRepos";
    private String e = "";
    private String f = "";
    private long g = 0;
    public io.reactivex.processors.b<Boolean> h = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<Integer> i = io.reactivex.processors.b.Y();
    int j = 17;
    int k = 0;
    private boolean l = false;
    private long m = 0;
    /* access modifiers changed from: private */
    public int n = 0;
    /* access modifiers changed from: private */
    public int o = 0;
    com.leedarson.newui.repoter.b p;
    com.leedarson.newui.repoter.g q = new com.leedarson.newui.repoter.g();
    io.reactivex.disposables.b r;
    private SimpleDateFormat s;
    private Future t;

    static /* synthetic */ void k(c0 x0, String x1, String x2, Context x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 3801, new Class[]{c0.class, cls, cls, Context.class}, Void.TYPE).isSupported) {
            x0.R(x1, x2, x3);
        }
    }

    static /* synthetic */ void m(c0 x0, String x1) {
        Class[] clsArr = {c0.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 3802, clsArr, Void.TYPE).isSupported) {
            x0.c(x1);
        }
    }

    public void u(String str, String str2, EventUrlResponseItemBean eventUrlResponseItemBean, Context context) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, EventUrlResponseItemBean.class, Context.class};
        if (!PatchProxy.proxy(new Object[]{str, str2, eventUrlResponseItemBean, context}, this, changeQuickRedirect, false, 3772, clsArr, Void.TYPE).isSupported) {
            String _eventUuId = str2;
            Context context2 = context;
            String deviceId = str;
            EventUrlResponseItemBean eventUrlResponseItemBean2 = eventUrlResponseItemBean;
            this.q.d(deviceId);
            this.p = this.q.i();
            this.n = 0;
            this.o = 0;
            String dirPath = e(deviceId, _eventUuId);
            File fileOfList = new File(dirPath + "list.txt");
            if (fileOfList.exists()) {
                fileOfList.delete();
            }
            this.h.onNext(true);
            for (int i2 = 0; i2 < eventUrlResponseItemBean2.videoUrlList.size(); i2++) {
                VideoUrlItemBean itemBean = eventUrlResponseItemBean2.videoUrlList.get(i2);
                String fileName = f(itemBean.begin);
                com.leedarson.utils.g.c("file '" + fileName + "'", dirPath, "list.txt");
                if (new File(dirPath + fileName).exists()) {
                    int i3 = this.o + 1;
                    this.o = i3;
                    if (i3 + this.n == eventUrlResponseItemBean2.videoUrlList.size()) {
                        R(deviceId, _eventUuId, context2);
                    }
                } else {
                    new f(this, deviceId, _eventUuId, eventUrlResponseItemBean2, context2, dirPath, itemBean, fileName);
                }
            }
        }
    }

    public void Q(LDSBasePlayerView lDSBasePlayerView, String str, String str2, EventUrlResponseItemBean eventUrlResponseItemBean, Context context) {
        List<VideoUrlItemBean> list;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{lDSBasePlayerView, str, str2, eventUrlResponseItemBean, context}, this, changeQuickRedirect, false, 3773, new Class[]{LDSBasePlayerView.class, cls, cls, EventUrlResponseItemBean.class, Context.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            EventUrlResponseItemBean eventUrlResponseItemBean2 = eventUrlResponseItemBean;
            LDSBasePlayerView lDSBasePlayerView2 = lDSBasePlayerView;
            String _eventUuId = str2;
            Context context2 = context;
            this.q.d(deviceId);
            this.p = this.q.i();
            this.h.onNext(true);
            if (eventUrlResponseItemBean2 == null || (list = eventUrlResponseItemBean2.videoUrlList) == null || list.size() <= 0 || eventUrlResponseItemBean2.videoUrlList.get(0).type != 2) {
                this.h.onNext(false);
                this.i.onNext(Integer.valueOf(R$string.compose_fail));
                return;
            }
            String remoteUrl = eventUrlResponseItemBean2.videoUrlList.get(0).url;
            String workSpaceDir = e(deviceId, _eventUuId);
            StringBuilder sb = new StringBuilder();
            sb.append(BaseApplication.b().getFilesDir());
            String str3 = File.separator;
            sb.append(str3);
            sb.append("stream_cache");
            File fileStreamCacheDir = new File(sb.toString());
            if (!fileStreamCacheDir.exists()) {
                fileStreamCacheDir.mkdir();
            }
            File diviceDir = new File(BaseApplication.b().getFilesDir() + str3 + "stream_cache" + str3 + deviceId);
            if (!diviceDir.exists()) {
                diviceDir.mkdir();
            }
            File deviceEventDir = new File(BaseApplication.b().getFilesDir() + str3 + "stream_cache" + str3 + deviceId + str3 + _eventUuId);
            if (!deviceEventDir.exists()) {
                deviceEventDir.mkdir();
            }
            String locaPath = workSpaceDir + "ipc_share_video.mp4";
            File preSharePath = new File(locaPath);
            if (preSharePath.exists()) {
                preSharePath.delete();
            }
            s();
            this.r = q(remoteUrl, deviceId, true).c(l.c()).I(new n(this, context2, remoteUrl, locaPath), k.c);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: K */
    public /* synthetic */ void L(Context context, String remoteUrl, String locaPath, Boolean aBoolean) {
        Class<String> cls = String.class;
        Class[] clsArr = {Context.class, cls, cls, Boolean.class};
        if (!PatchProxy.proxy(new Object[]{context, remoteUrl, locaPath, aBoolean}, this, changeQuickRedirect, false, 3798, clsArr, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                n(context, remoteUrl, locaPath);
                return;
            }
            this.h.onNext(false);
            this.i.onNext(Integer.valueOf(R$string.compose_fail));
            this.p.b("事件分享失败");
        }
    }

    static /* synthetic */ void M(Throwable throwable) {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void s() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3774(0xebe, float:5.289E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.r
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.r
            r1.dispose()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.repos.c0.s():void");
    }

    private void n(Context context, String remoteUrl, String str) {
        Class<String> cls = String.class;
        Class[] clsArr = {Context.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{context, remoteUrl, str}, this, changeQuickRedirect, false, 3775, clsArr, Void.TYPE).isSupported) {
            Context context2 = context;
            String locaPath = str;
            StringBuilder apiStrbuild = new StringBuilder();
            apiStrbuild.append("ffmpeg -i ");
            apiStrbuild.append(remoteUrl + " ");
            apiStrbuild.append("-c copy ");
            apiStrbuild.append(locaPath);
            this.h.onNext(true);
            ELKStepRecordBean stepRecordBean = new ELKStepRecordBean();
            stepRecordBean.startRequest(" remoteUrl=" + remoteUrl, "startConvertM3U8ToMp4");
            this.p.a(stepRecordBean);
            new Thread(new r(this, apiStrbuild.toString().split(" "), stepRecordBean, locaPath, context2)).start();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: z */
    public /* synthetic */ void A(String[] cmds, ELKStepRecordBean stepRecordBean, String locaPath, Context context) {
        Class[] clsArr = {String[].class, ELKStepRecordBean.class, String.class, Context.class};
        if (!PatchProxy.proxy(new Object[]{cmds, stepRecordBean, locaPath, context}, this, changeQuickRedirect, false, 3797, clsArr, Void.TYPE).isSupported) {
            try {
                long betweenTime = System.currentTimeMillis() - this.m;
                if (betweenTime > 0 && betweenTime < 4000) {
                    try {
                        Thread.sleep(4000 - betweenTime);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                RxFFmpegInvoke.getInstance().runCommand(cmds, new e(stepRecordBean, locaPath, context));
            } catch (Exception e3) {
                this.h.onNext(false);
                this.i.onNext(Integer.valueOf(R$string.compose_fail));
                stepRecordBean.endRequest("  error=   message=" + e3.toString());
                this.p.b("m3u8视频合成失败");
            }
        }
    }

    /* compiled from: ShareRepos */
    public class e implements RxFFmpegInvoke.IFFmpegListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ELKStepRecordBean c;
        final /* synthetic */ String d;
        final /* synthetic */ Context f;

        e(ELKStepRecordBean eLKStepRecordBean, String str, Context context) {
            this.c = eLKStepRecordBean;
            this.d = str;
            this.f = context;
        }

        public void onFinish() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3832, new Class[0], Void.TYPE).isSupported) {
                c0.this.h.onNext(false);
                ELKStepRecordBean eLKStepRecordBean = this.c;
                eLKStepRecordBean.endRequest("  response=>" + this.d);
                c0.this.p.b("m3u8视频合成成功");
                if (!c0.this.c) {
                    Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.d)));
                    if (com.leedarson.base.utils.c.h().k() != null) {
                        com.leedarson.base.utils.c.h().k().sendBroadcast(localIntent);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    IntentUtils.shareVideo(this.f, new File(this.d), "");
                    c0.this.h.onNext(false);
                }
            }
        }

        public void onProgress(int progress, long progressTime) {
        }

        public void onCancel() {
        }

        public void onError(String message) {
            if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 3833, new Class[]{String.class}, Void.TYPE).isSupported) {
                c0.this.h.onNext(false);
                c0.this.i.onNext(Integer.valueOf(R$string.compose_fail));
                ELKStepRecordBean eLKStepRecordBean = this.c;
                eLKStepRecordBean.endRequest("  error=   message=" + message);
                c0.this.p.b("m3u8视频合成失败");
            }
        }
    }

    private io.reactivex.e<Boolean> q(String remoteUrl, String deviceId, boolean needShow) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{remoteUrl, deviceId, new Byte(needShow ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3776, new Class[]{cls, cls, Boolean.TYPE}, io.reactivex.e.class);
        return proxy.isSupported ? (io.reactivex.e) proxy.result : io.reactivex.e.d(new v(this, deviceId, needShow, remoteUrl), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: B */
    public /* synthetic */ void C(String deviceId, boolean needShow, String remoteUrl, io.reactivex.f emitter) {
        Class<String> cls = String.class;
        Object[] objArr = {deviceId, new Byte(needShow ? (byte) 1 : 0), remoteUrl, emitter};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3796, new Class[]{cls, Boolean.TYPE, cls, io.reactivex.f.class}, Void.TYPE).isSupported) {
            String dirPath = e(deviceId, this.f);
            if (needShow) {
                this.h.onNext(true);
            }
            com.downloader.g.c(remoteUrl, dirPath, System.currentTimeMillis() + ".m3u8").a().L(new a(emitter));
        }
    }

    /* compiled from: ShareRepos */
    public class a implements com.downloader.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ io.reactivex.f a;

        a(io.reactivex.f fVar) {
            this.a = fVar;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3805, new Class[0], Void.TYPE).isSupported) {
                this.a.onNext(true);
                this.a.onComplete();
            }
        }

        public void b(com.downloader.a aVar) {
            if (!PatchProxy.proxy(new Object[]{aVar}, this, changeQuickRedirect, false, 3806, new Class[]{com.downloader.a.class}, Void.TYPE).isSupported) {
                this.a.onNext(false);
                this.a.onComplete();
            }
        }
    }

    /* compiled from: ShareRepos */
    public class f {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public String a = "";
        ELKStepRecordBean b;
        final /* synthetic */ c0 c;

        public f(c0 this$0, String deviceId, String _eventUuId, EventUrlResponseItemBean eventUrlResponseItemBean, Context context, String dirPath, VideoUrlItemBean itemBean, String fileName) {
            c0 c0Var = this$0;
            VideoUrlItemBean videoUrlItemBean = itemBean;
            this.c = c0Var;
            ELKStepRecordBean eLKStepRecordBean = new ELKStepRecordBean();
            this.b = eLKStepRecordBean;
            String str = videoUrlItemBean.url;
            this.a = str;
            eLKStepRecordBean.startRequest(str, "downloadVideoResource");
            c0Var.p.a(this.b);
            com.downloader.g.c(videoUrlItemBean.url, dirPath, fileName).a().G(m.a).L(new a(this$0, eventUrlResponseItemBean, deviceId, _eventUuId, context));
        }

        static /* synthetic */ void b() {
        }

        /* compiled from: ShareRepos */
        public class a implements com.downloader.c {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ c0 a;
            final /* synthetic */ EventUrlResponseItemBean b;
            final /* synthetic */ String c;
            final /* synthetic */ String d;
            final /* synthetic */ Context e;

            a(c0 c0Var, EventUrlResponseItemBean eventUrlResponseItemBean, String str, String str2, Context context) {
                this.a = c0Var;
                this.b = eventUrlResponseItemBean;
                this.c = str;
                this.d = str2;
                this.e = context;
            }

            public void a() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3834, new Class[0], Void.TYPE).isSupported) {
                    c0 c0Var = f.this.c;
                    int unused = c0Var.o = c0Var.o + 1;
                    f fVar = f.this;
                    fVar.b.endRequest(fVar.a);
                    if (f.this.c.o + f.this.c.n == this.b.videoUrlList.size()) {
                        c0.k(f.this.c, this.c, this.d, this.e);
                    }
                }
            }

            public void b(com.downloader.a error) {
                if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 3835, new Class[]{com.downloader.a.class}, Void.TYPE).isSupported) {
                    com.leedarson.base.logger.a.c("downloader", "downloader onError error=" + error.toString());
                    f.this.b.endRequestException(error.toString(), 1200);
                    c0 c0Var = f.this.c;
                    int unused = c0Var.n = c0Var.n + 1;
                    if (f.this.c.o + f.this.c.n == this.b.videoUrlList.size()) {
                        f.this.c.h.onNext(false);
                    }
                }
            }
        }
    }

    private void R(String deviceId, String _eventUuId, Context context) {
        String[] commands;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, _eventUuId, context}, this, changeQuickRedirect, false, 3777, new Class[]{cls, cls, Context.class}, Void.TYPE).isSupported) {
            Context context2 = context;
            String workSpaceDir = e(deviceId, _eventUuId);
            File outPutFile = new File(workSpaceDir + "output_ffmpeg.mp4");
            ELKStepRecordBean stepMxureMp4 = new ELKStepRecordBean();
            stepMxureMp4.startRequest("", "contactVideos");
            if (outPutFile.exists()) {
                this.h.onNext(false);
                if (!this.c) {
                    new Thread(new t(this, outPutFile, context2, stepMxureMp4)).start();
                }
            } else if (!p(workSpaceDir)) {
                this.h.onNext(false);
                this.i.onNext(Integer.valueOf(R$string.compose_fail));
                stepMxureMp4.endRequestException("原视频下载失败", 1200);
                this.p.b("事件分享失败");
            } else {
                String[] commandsH265 = {"ffmpeg", "-f", "concat", "-safe", "0", "-i", workSpaceDir + "list.txt", "-c", "copy", "-y", workSpaceDir + "output_ffmpeg.mp4"};
                String[] commandsH264 = {"ffmpeg", "-f", "concat", "-safe", "0", "-i", workSpaceDir + "list.txt", "-c:v", "libx264", "-c:a", "copy", "-y", workSpaceDir + "output_ffmpeg.mp4"};
                String[] strArr = new String[0];
                if (y(workSpaceDir)) {
                    commands = commandsH264;
                } else {
                    commands = commandsH265;
                }
                RxFFmpegInvoke.getInstance().runCommandAsync(commands, new b(stepMxureMp4, outPutFile, context2));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: N */
    public /* synthetic */ void O(File outPutFile, Context context, ELKStepRecordBean stepMxureMp4) {
        Class[] clsArr = {File.class, Context.class, ELKStepRecordBean.class};
        if (!PatchProxy.proxy(new Object[]{outPutFile, context, stepMxureMp4}, this, changeQuickRedirect, false, 3795, clsArr, Void.TYPE).isSupported) {
            Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(outPutFile));
            if (com.leedarson.base.utils.c.h().k() != null) {
                com.leedarson.base.utils.c.h().k().sendBroadcast(localIntent);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            IntentUtils.shareVideo(context, outPutFile, "");
            stepMxureMp4.endRequest("");
            this.p.b("事件分享成功");
        }
    }

    /* compiled from: ShareRepos */
    public class b implements RxFFmpegInvoke.IFFmpegListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ELKStepRecordBean c;
        final /* synthetic */ File d;
        final /* synthetic */ Context f;

        b(ELKStepRecordBean eLKStepRecordBean, File file, Context context) {
            this.c = eLKStepRecordBean;
            this.d = file;
            this.f = context;
        }

        public void onFinish() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3807, new Class[0], Void.TYPE).isSupported) {
                timber.log.a.g(c0.this.d).h("startMxureWithFfmpeg---->onFinish=", new Object[0]);
                this.c.endRequest("");
                c0.this.p.b("事件分享成功");
                if (!c0.this.c) {
                    new Thread(new l(this.d, this.f)).start();
                    c0.this.h.onNext(false);
                }
            }
        }

        static /* synthetic */ void a(File outPutFile, Context context) {
            Class[] clsArr = {File.class, Context.class};
            if (!PatchProxy.proxy(new Object[]{outPutFile, context}, (Object) null, changeQuickRedirect, true, 3811, clsArr, Void.TYPE).isSupported) {
                Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(outPutFile));
                if (com.leedarson.base.utils.c.h().k() != null) {
                    com.leedarson.base.utils.c.h().k().sendBroadcast(localIntent);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                IntentUtils.shareVideo(context, outPutFile, "");
            }
        }

        public void onProgress(int progress, long progressTime) {
            Object[] objArr = {new Integer(progress), new Long(progressTime)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3808, new Class[]{Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(c0.this.d);
                g.h(" startMxureWithFfmpeg.onProgress  progress=" + progress + "  progressTime=" + progressTime, new Object[0]);
            }
        }

        public void onCancel() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3809, new Class[0], Void.TYPE).isSupported) {
                this.c.endRequestException("事件分享失败", 1200);
                c0.this.p.b("事件转码被取消");
            }
        }

        public void onError(String message) {
            if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 3810, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(c0.this.d);
                g.h("startMxureWithFfmpeg---->onError=" + message, new Object[0]);
                this.c.endRequestException("事件分享失败", 1200);
                c0.this.p.b("事件分享失败");
            }
        }
    }

    private boolean p(String workSpaceDir) {
        File[] listsFile;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{workSpaceDir}, this, changeQuickRedirect, false, 3778, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        File workSpaceDirFile = new File(workSpaceDir);
        if (workSpaceDirFile.exists() && (listsFile = workSpaceDirFile.listFiles()) != null && listsFile.length > 0) {
            for (File name : listsFile) {
                if (name.getName().contains("load")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean y(String workSpaceDir) {
        File[] listsFile;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{workSpaceDir}, this, changeQuickRedirect, false, 3779, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        File workSpaceDirFile = new File(workSpaceDir);
        String testOriginFirstVideoPath = "";
        if (workSpaceDirFile.exists() && (listsFile = workSpaceDirFile.listFiles()) != null && listsFile.length > 0) {
            int i2 = 0;
            while (true) {
                if (i2 >= listsFile.length) {
                    break;
                } else if (listsFile[i2].getName().contains("load")) {
                    testOriginFirstVideoPath = listsFile[i2].getAbsolutePath();
                    break;
                } else {
                    i2++;
                }
            }
        }
        if (!com.alibaba.android.arouter.utils.e.b(testOriginFirstVideoPath) && new File(testOriginFirstVideoPath).exists()) {
            String mediaInfo = RxFFmpegInvoke.getInstance().getMediaInfo(testOriginFirstVideoPath);
            if (!mediaInfo.toUpperCase().contains("H264") && mediaInfo.toUpperCase().contains("HEVC")) {
                return false;
            }
        }
        return true;
    }

    public static String e(String deviceId, String uuid) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId, uuid}, (Object) null, changeQuickRedirect2, true, 3780, new Class[]{cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(BaseApplication.b().getFilesDir());
        String str = File.separator;
        sb.append(str);
        sb.append("stream_cache");
        sb.append(str);
        sb.append(deviceId);
        sb.append(str);
        sb.append(uuid);
        sb.append(str);
        return sb.toString();
    }

    public static String d(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, (Object) null, changeQuickRedirect, true, 3781, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(BaseApplication.b().getFilesDir());
        String str = File.separator;
        sb.append(str);
        sb.append("stream_cache");
        sb.append(str);
        sb.append(deviceId);
        sb.append(str);
        return sb.toString();
    }

    public static String f(long startTime) {
        Object[] objArr = {new Long(startTime)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 3782, new Class[]{Long.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return startTime + "-dowanload.mp4";
    }

    public void P() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3783, new Class[0], Void.TYPE).isSupported) {
            this.c = true;
            o();
            com.leedarson.smartcamera.kvswebrtc.utils.d.a().f();
        }
    }

    private JSONObject x() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3784, new Class[0], JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject headerJson = new JSONObject();
        String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "");
        String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
        String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
        try {
            headerJson.put("owner", (Object) owner);
            headerJson.put("token", (Object) accessToken);
            headerJson.put("terminal", (Object) "app");
            headerJson.put("appId", (Object) appId);
            headerJson.put("appVersion", (Object) w.E(BaseApplication.b()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return headerJson;
    }

    public io.reactivex.disposables.b r(String albumId, String videoId, i<String> iVar) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{albumId, videoId, iVar}, this, changeQuickRedirect, false, 3785, new Class[]{cls, cls, i.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        i<String> iVar2 = iVar;
        Locale locale = Locale.US;
        String reqUrl = String.format(locale, "%s/%s", new Object[]{SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", ""), String.format(locale, "ipc/album/%s/videos/%s", new Object[]{albumId, videoId})});
        c("deleteTimeVideo: " + reqUrl);
        return b0.b().J(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, reqUrl, x().toString(), (String) null, iVar2);
    }

    public void w(String deviceId, String _eventUuId, String str, b0 b0Var) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, _eventUuId, str, b0Var}, this, changeQuickRedirect, false, 3786, new Class[]{cls, cls, cls, b0.class}, Void.TYPE).isSupported) {
            b0 downloadListener = b0Var;
            String remoteUrl = str;
            this.q.d(deviceId);
            this.p = this.q.i();
            String workSpaceDir = e(deviceId, _eventUuId);
            StringBuilder sb = new StringBuilder();
            sb.append(BaseApplication.b().getFilesDir());
            String str2 = File.separator;
            sb.append(str2);
            sb.append("stream_cache");
            File fileStreamCacheDir = new File(sb.toString());
            if (!fileStreamCacheDir.exists()) {
                fileStreamCacheDir.mkdir();
            }
            File diviceDir = new File(BaseApplication.b().getFilesDir() + str2 + "stream_cache" + str2 + deviceId);
            if (!diviceDir.exists()) {
                diviceDir.mkdir();
            }
            File deviceEventDir = new File(BaseApplication.b().getFilesDir() + str2 + "stream_cache" + str2 + deviceId + str2 + _eventUuId);
            if (!deviceEventDir.exists()) {
                deviceEventDir.mkdir();
            }
            String locaPath = workSpaceDir + "ipc_share_video.mp4";
            File preSharePath = new File(locaPath);
            if (preSharePath.exists()) {
                preSharePath.delete();
            }
            t(locaPath, deviceId, remoteUrl, downloadListener, false);
        }
    }

    public void v(String str, long j2, String str2, boolean z, b0 b0Var) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, new Long(j2), str2, new Byte(z ? (byte) 1 : 0), b0Var}, this, changeQuickRedirect, false, 3787, new Class[]{cls, Long.TYPE, cls, Boolean.TYPE, b0.class}, Void.TYPE).isSupported) {
            long date = j2;
            b0 downloadListener = b0Var;
            String remoteUrl = str2;
            String deviceId = str;
            boolean isM3U8 = z;
            this.q.d(deviceId);
            this.p = this.q.i();
            if (this.s == null) {
                this.s = new SimpleDateFormat("yyyyMMddHHmmss");
            }
            String locaPath = com.leedarson.smartcamera.utils.f.b(BaseApplication.b()) + (deviceId + "_" + this.s.format(new Date(date)) + "_" + System.currentTimeMillis() + ".mp4");
            if (isM3U8) {
                t(locaPath, deviceId, remoteUrl, downloadListener, false);
                return;
            }
            String tempDir = d(deviceId);
            String tempFileName = deviceId + "_" + this.s.format(new Date(date)) + ".mp4";
            com.downloader.g.c(remoteUrl, tempDir, tempFileName).a().G(u.a).F(new s(downloadListener)).L(new c(tempDir + tempFileName, locaPath, downloadListener));
        }
    }

    static /* synthetic */ void I() {
    }

    static /* synthetic */ void J(b0 downloadListener, j progress) {
        Class[] clsArr = {b0.class, j.class};
        if (!PatchProxy.proxy(new Object[]{downloadListener, progress}, (Object) null, changeQuickRedirect, true, 3794, clsArr, Void.TYPE).isSupported && downloadListener != null) {
            downloadListener.onProgress((int) (((((float) progress.currentBytes) * 1.0f) / ((float) progress.totalBytes)) * 100.0f), 0);
        }
    }

    /* compiled from: ShareRepos */
    public class c implements com.downloader.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ b0 c;

        c(String str, String str2, b0 b0Var) {
            this.a = str;
            this.b = str2;
            this.c = b0Var;
        }

        /* compiled from: ShareRepos */
        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3814, new Class[0], Void.TYPE).isSupported) {
                    c cVar = c.this;
                    if (!c0.this.c) {
                        boolean copyFile = FileUtils.copyFile(cVar.a, cVar.b);
                        c cVar2 = c.this;
                        b0 b0Var = cVar2.c;
                        if (b0Var != null) {
                            b0Var.onFinish(cVar2.b);
                        }
                    }
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3812, new Class[0], Void.TYPE).isSupported) {
                if (!c0.this.c) {
                    com.leedarson.smartcamera.kvswebrtc.utils.d.a().d().submit(new a());
                }
            }
        }

        public void b(com.downloader.a error) {
            b0 b0Var;
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 3813, new Class[]{com.downloader.a.class}, Void.TYPE).isSupported && (b0Var = this.c) != null) {
                b0Var.onError(error.toString());
            }
        }
    }

    private void t(String locaPath, String deviceId, String remoteUrl, b0 downloadListener, boolean needShow) {
        Class<String> cls = String.class;
        Object[] objArr = {locaPath, deviceId, remoteUrl, downloadListener, new Byte(needShow ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3788, new Class[]{cls, cls, cls, b0.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            s();
            this.r = q(remoteUrl, deviceId, needShow).c(l.c()).I(new o(this, remoteUrl, locaPath, downloadListener), new p(downloadListener));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: F */
    public /* synthetic */ void G(String str, String str2, b0 b0Var, Boolean aBoolean) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, b0Var, aBoolean}, this, changeQuickRedirect, false, 3792, new Class[]{cls, cls, b0.class, Boolean.class}, Void.TYPE).isSupported) {
            String locaPath = str2;
            String remoteUrl = str;
            b0 downloadListener = b0Var;
            if (aBoolean.booleanValue()) {
                StringBuilder apiStrbuild = new StringBuilder();
                apiStrbuild.append("ffmpeg -i ");
                apiStrbuild.append(remoteUrl + " ");
                apiStrbuild.append("-c copy ");
                apiStrbuild.append(locaPath);
                c("下载执行指令 ---> " + apiStrbuild.toString());
                ELKStepRecordBean stepRecordBean = new ELKStepRecordBean();
                stepRecordBean.startRequest(" remoteUrl=" + remoteUrl, "startConvertM3U8ToMp4");
                this.p.a(stepRecordBean);
                String[] cmds = {"ffmpeg", "-i", remoteUrl, "-c", "copy", locaPath};
                try {
                    Future future = this.t;
                    if (future != null && !future.isCancelled()) {
                        this.t.cancel(true);
                    }
                    this.t = com.leedarson.smartcamera.kvswebrtc.utils.d.a().b().submit(new q(this, cmds, downloadListener, locaPath));
                } catch (Exception e2) {
                    stepRecordBean.endRequest("  error=   message=" + e2.toString());
                    this.p.b("m3u8视频合成失败");
                    if (downloadListener != null) {
                        downloadListener.onError("");
                    }
                }
            } else {
                this.p.b("事件分享失败");
                if (downloadListener != null) {
                    downloadListener.onError("");
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: D */
    public /* synthetic */ void E(String[] cmds, b0 downloadListener, String locaPath) {
        Class[] clsArr = {String[].class, b0.class, String.class};
        if (!PatchProxy.proxy(new Object[]{cmds, downloadListener, locaPath}, this, changeQuickRedirect, false, 3793, clsArr, Void.TYPE).isSupported) {
            long betweenTime = System.currentTimeMillis() - this.m;
            if (betweenTime > 0 && betweenTime < 4000) {
                try {
                    Thread.sleep(4000 - betweenTime);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
            RxFFmpegInvoke.getInstance().runCommand(cmds, new d(downloadListener, locaPath));
        }
    }

    /* compiled from: ShareRepos */
    public class d implements RxFFmpegInvoke.IFFmpegListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ b0 c;
        final /* synthetic */ String d;

        d(b0 b0Var, String str) {
            this.c = b0Var;
            this.d = str;
        }

        public void onFinish() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3815, new Class[0], Void.TYPE).isSupported) {
                c0 c0Var = c0.this;
                c0.m(c0Var, "onFinish: " + Thread.currentThread());
                b0 b0Var = this.c;
                if (b0Var != null) {
                    b0Var.onFinish(this.d);
                }
            }
        }

        public void onProgress(int progress, long progressTime) {
            Object[] objArr = {new Integer(progress), new Long(progressTime)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3816, new Class[]{Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
                c0 c0Var = c0.this;
                c0.m(c0Var, "onProgress: " + progress + "==" + progressTime);
                b0 b0Var = this.c;
                if (b0Var != null) {
                    b0Var.onProgress(progress, progressTime);
                }
            }
        }

        public void onCancel() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3817, new Class[0], Void.TYPE).isSupported) {
                c0.m(c0.this, "onCancel: ");
                b0 b0Var = this.c;
                if (b0Var != null) {
                    b0Var.onCancel();
                }
            }
        }

        public void onError(String message) {
            if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 3818, new Class[]{String.class}, Void.TYPE).isSupported) {
                c0 c0Var = c0.this;
                c0.m(c0Var, "onError: " + message);
                b0 b0Var = this.c;
                if (b0Var != null) {
                    b0Var.onError(message);
                }
            }
        }
    }

    static /* synthetic */ void H(b0 downloadListener, Throwable th) {
        Class[] clsArr = {b0.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{downloadListener, th}, (Object) null, changeQuickRedirect, true, 3791, clsArr, Void.TYPE).isSupported && downloadListener != null) {
            downloadListener.onError("");
        }
    }

    private void c(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 3789, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g(this.d).a(msg, new Object[0]);
        }
    }

    public void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3790, new Class[0], Void.TYPE).isSupported) {
            s();
            this.m = System.currentTimeMillis();
            RxFFmpegInvoke.getInstance().exit();
            com.downloader.g.a();
        }
    }
}
