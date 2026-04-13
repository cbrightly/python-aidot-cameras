package meshsdk.ctrl;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import androidx.annotation.NonNull;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.base.http.api.a;
import com.leedarson.base.http.listener.b;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.e;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.entity.ConnectionFilter;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.foundation.parameter.GattOtaParameters;
import com.telink.ble.mesh.util.FileSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.UpdateRetryStrategy;
import meshsdk.callback.MeshOTACallback;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.NodeInfo;
import meshsdk.util.MeshConstants;
import okhttp3.e0;

public class OtaCtrl extends CtrlLifecycle implements EventListener<String> {
    public static final int STAGE_BUSY = 7;
    public static final int STAGE_DOWNLOADING = 1;
    public static final int STAGE_DOWNLOAD_COMPLETED = 2;
    public static final int STAGE_FAIL = 6;
    public static final int STAGE_INSTALLING = 4;
    public static final int STAGE_SUCCESS = 5;
    public static final int STAGE_WAIT_UPGRADE = 3;
    private int binPid;
    /* access modifiers changed from: private */
    public Context context;
    private int countdown_s = 0;
    private int countdown_s_every_pkg_spend_time_ms = 10;
    private Handler handler;
    public volatile boolean isUpdateing = false;
    private byte[] mFirmware;
    private NodeInfo mNodeInfo;
    private String mac;
    private MeshOTACallback meshOTACallback;

    public OtaCtrl(SIGMesh sigMesh, HandlerThread thread) {
        super(sigMesh);
        onCreate();
        this.handler = new Handler(thread.getLooper()) {
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    OtaCtrl.this.traceOtaByELK("OTA 300s超时", "info", "handleMessage");
                    OtaCtrl.this.otaComplete();
                }
            }
        };
    }

    public void onCreate() {
        MeshEventHandler.getInstance().addEventListener("com.telink.sig.com.telink.ble.mesh.OTA_SUCCESS", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.sig.com.telink.ble.mesh.OTA_PROGRESS", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.sig.com.telink.ble.mesh.OTA_FAIL", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_AUTO_CONNECT_LOGIN", this);
        this.context = SIGMesh.getInstance().getContext();
    }

    public void onDestroy() {
        MeshEventHandler.getInstance().removeEventListener(this);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void performed(com.telink.ble.mesh.foundation.Event<java.lang.String> r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r5.getType()
            java.lang.String r0 = (java.lang.String) r0
            int r1 = r0.hashCode()
            r2 = 1
            switch(r1) {
                case -1454436148: goto L_0x002d;
                case -223056677: goto L_0x0023;
                case 1114686467: goto L_0x0019;
                case 2037252981: goto L_0x000f;
                default: goto L_0x000e;
            }
        L_0x000e:
            goto L_0x0037
        L_0x000f:
            java.lang.String r1 = "com.telink.sig.com.telink.ble.mesh.OTA_SUCCESS"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000e
            r0 = 0
            goto L_0x0038
        L_0x0019:
            java.lang.String r1 = "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_AUTO_CONNECT_LOGIN"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000e
            r0 = 3
            goto L_0x0038
        L_0x0023:
            java.lang.String r1 = "com.telink.sig.com.telink.ble.mesh.OTA_PROGRESS"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000e
            r0 = 2
            goto L_0x0038
        L_0x002d:
            java.lang.String r1 = "com.telink.sig.com.telink.ble.mesh.OTA_FAIL"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000e
            r0 = r2
            goto L_0x0038
        L_0x0037:
            r0 = -1
        L_0x0038:
            switch(r0) {
                case 0: goto L_0x009c;
                case 1: goto L_0x005d;
                case 2: goto L_0x0052;
                case 3: goto L_0x003d;
                default: goto L_0x003b;
            }
        L_0x003b:
            goto L_0x00b7
        L_0x003d:
            boolean r0 = meshsdk.datamgr.MeshDataManager.flagOTA
            if (r0 == 0) goto L_0x004b
            java.lang.String r0 = "收到mesh网络连接成功，当前正在ota，重新ota"
            meshsdk.MeshLog.d(r0)
            r4.startGattOta()
            goto L_0x00b7
        L_0x004b:
            java.lang.String r0 = "收到mesh网络连接成功，当前没在ota，otaCtrl不用处理"
            meshsdk.MeshLog.d(r0)
            goto L_0x00b7
        L_0x0052:
            r0 = r5
            com.telink.ble.mesh.foundation.event.GattOtaEvent r0 = (com.telink.ble.mesh.foundation.event.GattOtaEvent) r0
            int r0 = r0.b()
            r4.onProgress(r0)
            goto L_0x00b7
        L_0x005d:
            com.telink.ble.mesh.foundation.MeshService r0 = com.telink.ble.mesh.foundation.MeshService.k()
            java.lang.String r1 = "ota fail"
            r0.n(r2, r1)
            java.lang.String r0 = "ota fail, autoConnect request"
            com.leedarson.serviceimpl.reporters.c.f(r0)
            meshsdk.SIGMesh r0 = meshsdk.SIGMesh.getInstance()
            r0.autoConnect()
            java.lang.String r0 = "upgrade fail"
            boolean r1 = r5 instanceof com.telink.ble.mesh.foundation.event.GattOtaEvent
            if (r1 == 0) goto L_0x0096
            r1 = r5
            com.telink.ble.mesh.foundation.event.GattOtaEvent r1 = (com.telink.ble.mesh.foundation.event.GattOtaEvent) r1
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            java.lang.String r3 = ":"
            r2.append(r3)
            java.lang.String r3 = r1.a()
            r2.append(r3)
            java.lang.String r0 = r2.toString()
        L_0x0096:
            r1 = 423(0x1a7, float:5.93E-43)
            r4.onFail(r1, r0)
            goto L_0x00b7
        L_0x009c:
            com.telink.ble.mesh.foundation.MeshService r0 = com.telink.ble.mesh.foundation.MeshService.k()
            java.lang.String r1 = "ota success"
            r0.n(r2, r1)
            java.lang.String r0 = "ota success, autoConnect request"
            com.leedarson.serviceimpl.reporters.c.f(r0)
            meshsdk.SIGMesh r0 = meshsdk.SIGMesh.getInstance()
            r0.autoConnect()
            r4.onSuccess()
        L_0x00b7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: meshsdk.ctrl.OtaCtrl.performed(com.telink.ble.mesh.foundation.Event):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0049, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void startUpgrade(final java.lang.String r5, java.lang.String r6, final meshsdk.callback.MeshOTACallback r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            r4.countdown_s = r0     // Catch:{ all -> 0x0071 }
            boolean r1 = r4.isUpdateing     // Catch:{ all -> 0x0071 }
            if (r1 == 0) goto L_0x000a
            monitor-exit(r4)
            return
        L_0x000a:
            android.os.Handler r1 = r4.handler     // Catch:{ all -> 0x0071 }
            r1.removeMessages(r0)     // Catch:{ all -> 0x0071 }
            android.os.Handler r1 = r4.handler     // Catch:{ all -> 0x0071 }
            r2 = 300000(0x493e0, double:1.482197E-318)
            r1.sendEmptyMessageDelayed(r0, r2)     // Catch:{ all -> 0x0071 }
            r1 = 1
            meshsdk.datamgr.MeshDataManager.flagOTA = r1     // Catch:{ all -> 0x0071 }
            r4.meshOTACallback = r7     // Catch:{ all -> 0x0071 }
            com.telink.ble.mesh.foundation.MeshService r1 = com.telink.ble.mesh.foundation.MeshService.k()     // Catch:{ all -> 0x0071 }
            java.lang.String r2 = "otaCtrl startUpgrade"
            r1.n(r0, r2)     // Catch:{ all -> 0x0071 }
            meshsdk.SIGMesh r0 = meshsdk.SIGMesh.getInstance()     // Catch:{ all -> 0x0071 }
            meshsdk.model.MeshInfo r0 = r0.getMeshInfo()     // Catch:{ all -> 0x0071 }
            java.util.List<meshsdk.model.NodeInfo> r0 = r0.nodes     // Catch:{ all -> 0x0071 }
            meshsdk.model.NodeInfo r0 = meshsdk.util.LDSMeshUtil.findMeshNode((java.util.List<meshsdk.model.NodeInfo>) r0, (java.lang.String) r5)     // Catch:{ all -> 0x0071 }
            r4.mNodeInfo = r0     // Catch:{ all -> 0x0071 }
            r4.mac = r5     // Catch:{ all -> 0x0071 }
            if (r0 != 0) goto L_0x004a
            java.lang.String r0 = "OTA fail nodeinfo == null"
            meshsdk.MeshLog.d(r0)     // Catch:{ all -> 0x0071 }
            if (r7 == 0) goto L_0x0048
            r0 = 418(0x1a2, float:5.86E-43)
            java.lang.String r1 = "device not exist"
            r7.onFail(r0, r1, r5)     // Catch:{ all -> 0x0071 }
        L_0x0048:
            monitor-exit(r4)
            return
        L_0x004a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0071 }
            r0.<init>()     // Catch:{ all -> 0x0071 }
            java.lang.String r1 = "开始下载固件包:"
            r0.append(r1)     // Catch:{ all -> 0x0071 }
            r0.append(r5)     // Catch:{ all -> 0x0071 }
            java.lang.String r1 = ",url:"
            r0.append(r1)     // Catch:{ all -> 0x0071 }
            r0.append(r6)     // Catch:{ all -> 0x0071 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0071 }
            meshsdk.MeshLogNew.ota(r0)     // Catch:{ all -> 0x0071 }
            meshsdk.ctrl.OtaCtrl$2 r0 = new meshsdk.ctrl.OtaCtrl$2     // Catch:{ all -> 0x0071 }
            r0.<init>(r7, r5)     // Catch:{ all -> 0x0071 }
            r4.downloadPkg(r6, r0)     // Catch:{ all -> 0x0071 }
            monitor-exit(r4)
            return
        L_0x0071:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: meshsdk.ctrl.OtaCtrl.startUpgrade(java.lang.String, java.lang.String, meshsdk.callback.MeshOTACallback):void");
    }

    public void startGattOta() {
        NodeInfo nodeInfo;
        if (this.mFirmware == null || (nodeInfo = this.mNodeInfo) == null) {
            MeshLogNew.otaWarn("调用mesh startGattOta mFirmware为null,搞啥呢...?,mNodeInfo?" + this.mNodeInfo);
            return;
        }
        GattOtaParameters parameters = new GattOtaParameters(new ConnectionFilter(0, Integer.valueOf(nodeInfo.meshAddress)), this.mFirmware);
        MeshLogNew.ota("调用mesh startGattOta:" + this.mNodeInfo.macAddress);
        MeshService.k().z(parameters);
    }

    /* access modifiers changed from: private */
    public boolean readFirmware(String fileName) {
        try {
            InputStream stream = new FileInputStream(fileName);
            byte[] bArr = new byte[stream.available()];
            this.mFirmware = bArr;
            stream.read(bArr);
            stream.close();
            byte[] pid = new byte[2];
            byte[] vid = new byte[2];
            System.arraycopy(this.mFirmware, 2, pid, 0, 2);
            this.binPid = MeshUtils.c(pid, ByteOrder.LITTLE_ENDIAN);
            System.arraycopy(this.mFirmware, 4, vid, 0, 2);
            byte[] bArr2 = this.mFirmware;
            if (bArr2 != null && bArr2.length > 0) {
                this.countdown_s = (((bArr2.length / 16) * this.countdown_s_every_pkg_spend_time_ms) / 1000) + 5;
            }
            String str = " pid-" + e.b(pid, ":") + " vid-" + e.b(vid, ":");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            this.mFirmware = null;
            onFail(422, "ota package read error");
            return false;
        }
    }

    private void downloadPkg(String url, final b downloadListener) {
        UpdateRetryStrategy updateRetryStrategy = new UpdateRetryStrategy();
        updateRetryStrategy.setRetryDelayMillis(500);
        ((a) com.leedarson.base.http.b.b().a(a.class)).h(url).b0(l.g).J(l.g).R(updateRetryStrategy).Y(new io.reactivex.functions.e<e0>() {
            public void accept(e0 response) {
                String name = Thread.currentThread().getName();
                File file = new File(OtaCtrl.this.context.getFilesDir(), "img.lib");
                SIGMesh.getInstance().stopScan("OtaCtrl");
                OtaCtrl.this.traceOtaByELK("固件包下载成功,写入文件", "info", "downloadPkg");
                FileSystem.d(response, file, downloadListener);
            }
        }, new io.reactivex.functions.e<Throwable>() {
            public void accept(Throwable throwable) {
                MeshLog.e("ota downloadFile error:" + throwable.toString());
                b bVar = downloadListener;
                bVar.onFailure("ota download upgrade package err:" + throwable.toString());
            }
        });
    }

    public String queryVersion() {
        return "";
    }

    /* access modifiers changed from: private */
    public void onFail(int code, String msg) {
        otaComplete();
        MeshOTACallback meshOTACallback2 = this.meshOTACallback;
        if (meshOTACallback2 != null) {
            meshOTACallback2.onFail(code, msg, this.mac);
        }
        traceOtaByELK("bleMesh ota失败:" + this.mac + ",reason:" + msg, "failure", "onFail");
    }

    private void onSuccess() {
        otaComplete();
        MeshOTACallback meshOTACallback2 = this.meshOTACallback;
        if (meshOTACallback2 != null) {
            meshOTACallback2.onSuccess(this.mac);
        }
        traceOtaByELK("bleMesh ota成功:" + this.mac, FirebaseAnalytics.Param.SUCCESS, "onSuccess");
    }

    private void onProgress(int p) {
        MeshOTACallback meshOTACallback2 = this.meshOTACallback;
        if (meshOTACallback2 != null) {
            meshOTACallback2.onProgress(p, this.mac, this.countdown_s);
        }
    }

    /* access modifiers changed from: private */
    public void otaComplete() {
        MeshLog.i("释放flagOTA，可以继续进行数据更新");
        Handler handler2 = this.handler;
        if (handler2 != null) {
            handler2.removeMessages(0);
        }
        MeshDataManager.flagOTA = false;
    }

    /* access modifiers changed from: private */
    public void traceOtaByELK(String message, String level, String method) {
        MeshLog.i("trackOtaByELK:" + message);
        com.leedarson.log.elk.a.y(this).x(MeshConstants.TRACE_ID_OTA).c(OtaCtrl.class.getSimpleName()).o(level).t("LdsBleMesh").p(message).s(method).a().b();
    }
}
