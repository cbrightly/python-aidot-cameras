package com.leedarson.log.mgr;

import android.content.Context;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.serviceimpl.tcp.socket.c;
import com.leedarson.serviceimpl.tcp.socket.d;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import io.reactivex.l;
import io.reactivex.o;
import java.io.File;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: IPCLogManager */
public class r {
    private static r a;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public String b;
    private boolean c;
    private t d;
    private d e = new a();

    static /* synthetic */ void b(r x0, File x1) {
        Class[] clsArr = {r.class, File.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 1292, clsArr, Void.TYPE).isSupported) {
            x0.q(x1);
        }
    }

    public static r e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_SAVE_DROPBOX_REQ, new Class[0], r.class);
        if (proxy.isSupported) {
            return (r) proxy.result;
        }
        if (a == null) {
            synchronized (r.class) {
                if (a == null) {
                    a = new r();
                }
            }
        }
        return a;
    }

    public void d(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_SAVE_DROPBOX_RESP, new Class[]{Context.class}, Void.TYPE).isSupported) {
            o("IPCLogManager#execUpload");
            if (this.d == null) {
                this.d = new t(context);
            }
            if (!this.c) {
                this.b = context.getCacheDir() + File.separator + "ipcLog";
                File dirs = new File(this.b);
                if (dirs.exists()) {
                    File[] listFiles = dirs.listFiles();
                    if (listFiles == null || listFiles.length == 0) {
                        o("ipc设备日志为空，无须上传");
                    } else if (SharePreferenceUtils.hasKey(context, "logUploadUrl")) {
                        this.c = true;
                        o("ipc设备日志开始上传 日志数量:" + listFiles.length);
                        l.y(listFiles).u(new m(this)).b0(com.leedarson.base.http.observer.l.a).J(com.leedarson.base.http.observer.l.a).Y(new n(this), new l(this));
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public /* synthetic */ o h(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 1291, new Class[]{File.class}, o.class);
        if (proxy.isSupported) {
            return (o) proxy.result;
        }
        return this.d.f(file);
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public /* synthetic */ void j(File file) {
        if (!PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 1290, new Class[]{File.class}, Void.TYPE).isSupported) {
            o("ipc设备日志上传成功:" + file.getAbsolutePath());
            o("删除缓存文件:" + file.getName());
            file.delete();
            c();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public /* synthetic */ void l(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 1289, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            if (throwable instanceof ApiException) {
                ApiException e2 = (ApiException) throwable;
                o("ipc设备日志上传出错:" + e2.getMsg() + ",code:" + e2.getCode());
            } else {
                o("ipc设备日志上传出错:" + throwable.getMessage());
            }
            c();
        }
    }

    private void o(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 1284, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("LdsCore").a(msg, new Object[0]);
        }
    }

    private void c() {
        this.c = false;
    }

    public String f(int port) {
        Object[] objArr = {new Integer(port)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 1285, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String ip = com.leedarson.base.webservice.utils.a.a().getHostAddress();
        return NetworkManager.MOCK_SCHEME_HTTP + ip + ":" + port + "/deviceLogUpLoad/545687";
    }

    public void p(String origin) {
        if (!PatchProxy.proxy(new Object[]{origin}, this, changeQuickRedirect, false, 1286, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("IPCLogManager");
            g.a("IPCLogManager startSocketMonitor ," + origin, new Object[0]);
            c server = c.c();
            if (server != null) {
                server.setOnSocketClientListener(this.e);
            }
        }
    }

    /* compiled from: IPCLogManager */
    public class a implements d {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void b(String ip, int port) {
            if (!PatchProxy.proxy(new Object[]{ip, new Integer(port)}, this, changeQuickRedirect, false, 1293, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
                r.b(r.this, new File(r.this.b, ip + "-" + port));
            }
        }

        public void a(String ip, int port, byte[] data) {
            if (!PatchProxy.proxy(new Object[]{ip, new Integer(port), data}, this, changeQuickRedirect, false, 1294, new Class[]{String.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
                String str = new String(data, StandardCharsets.UTF_8);
                File file = new File(r.this.b);
                com.leedarson.base.utils.l.a(file, ip + "-" + port, str + "\n");
            }
        }
    }

    private void q(File localFile) {
        if (!PatchProxy.proxy(new Object[]{localFile}, this, changeQuickRedirect, false, 1287, new Class[]{File.class}, Void.TYPE).isSupported) {
            BleMeshService bleMeshService = (BleMeshService) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class);
            if (bleMeshService != null) {
                if (!localFile.exists() || !localFile.isFile() || localFile.length() / 1048576 <= 20) {
                    JSONObject buildBody = bleMeshService.getELKMessageBody(this, com.leedarson.base.utils.l.d(localFile), "deviceLogUpLoad", BleMeshService.ACTION_ADD_DEVICES, "info", "DeviceLog");
                    File file = new File(this.b);
                    String name = localFile.getName();
                    com.leedarson.base.utils.l.h(file, name, buildBody.toString() + "\n", false);
                } else {
                    com.leedarson.base.http.observer.l.a.b(new o(this, localFile));
                    o("设备传输过来的日志太大了，大于20M，传的什么鬼日志 ：");
                    return;
                }
            }
            o("IPC日志文件写入路径:" + localFile.getAbsolutePath());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: m */
    public /* synthetic */ void n(File localFile) {
        if (!PatchProxy.proxy(new Object[]{localFile}, this, changeQuickRedirect, false, 1288, new Class[]{File.class}, Void.TYPE).isSupported) {
            try {
                localFile.delete();
            } catch (Exception e2) {
                o("设备传输过来的日志太大了，大于20M，传的什么鬼日志 ：e=" + e2.toString());
            }
        }
    }
}
