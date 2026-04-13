package com.leedarson.serviceimpl.business;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.android.arouter.launcher.a;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.e;
import com.leedarson.base.utils.w;
import com.leedarson.serviceimpl.blec075.callback.d;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceimpl.business.bean.BleBusinessConnectBean;
import com.leedarson.serviceimpl.business.bean.CompatNotifyAndWriteInfoBean;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import com.leedarson.serviceimpl.business.reporters.BleOTAReporter;
import com.leedarson.serviceimpl.business.reporters.OTAStepBean;
import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.Observable;
import io.reactivex.disposables.b;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import meshsdk.UpdateRetryStrategy;
import meshsdk.ctrl.OtaCtrl;
import meshsdk.util.MeshConstants;
import okhttp3.ResponseBody;
import okhttp3.e0;

public class TRVOta {
    private static final int ERR_CODE_CRC_ERROR = 2;
    private static final int ERR_CODE_DISALLOW = 3;
    private static final int ERR_CODE_OTHERS = 255;
    private static final int ERR_CODE_PKG_LOSS = 4;
    private static final int ERR_CODE_SUCCESS = 0;
    private static final int ERR_CODE_TOTAL_LEN_ERROR = 1;
    private static final int MAX_PKG_LIST_SIZE = 15;
    public static final int STAGE_DOWNLOADING = 1;
    public static final int STAGE_DOWNLOAD_COMPLETED = 2;
    public static final int STAGE_FAIL = 6;
    public static final int STAGE_INSTALLING = 4;
    public static final int STAGE_SUCCESS = 5;
    public static final int STAGE_WAIT_UPGRADE = 3;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public volatile int FRAME_INDEX = 0;
    private int PKG_SIZE = 0;
    /* access modifiers changed from: private */
    public volatile int SUB_PKG_INDEX = 0;
    private final String TAG = "BleTRVOta";
    boolean _bleConnectState = true;
    String _bleDisconnectDesc = "";
    OTAStepBean _checkMd5Step = new OTAStepBean(OTAStepBean.CHECK_OTA_MD5_STEP, OTAStepBean.CHECK_OTA_MD5_STEP_FAIL);
    BleBusinessConnectBean _connectBean;
    int _currentMtu = 256;
    b _diposOfDownload;
    /* access modifiers changed from: private */
    public boolean _foreToEndOTA = false;
    OTAStepBean _getFireVersionStep = new OTAStepBean(OTAStepBean.GET_WARE_VERSION_STEP, OTAStepBean.GET_WARE_VERSION_FAIL);
    private final int _maxRetryTimesOfEveryPack = 4;
    CompatNotifyAndWriteInfoBean _otaWriteAndNotifyBean;
    OTAStepBean _prePareToSendOTAStep = new OTAStepBean(OTAStepBean.SEND_OTA_PREPARE_CMD_STEP, OTAStepBean.SEND_OTA_PREPARE_CMD_STEP_FAIL);
    BleOTAReporter _reporter;
    private HashMap<String, Integer> _retryTimesOfEveryOtaPackage = new HashMap<>();
    /* access modifiers changed from: private */
    public boolean allowSendNext = false;
    private int alreadySendDataLength = 0;
    private BleC075Service bleC075Service;
    private BleBusinessClient businessClient;
    private Context context;
    private int countdown_s = 0;
    /* access modifiers changed from: private */
    public boolean flagIsOtaDataTransformedToDevice = false;
    private boolean flagSupportPointTransport = false;
    private FrameFinishTimeoutTask frameFinishTimeoutTask = new FrameFinishTimeoutTask();
    /* access modifiers changed from: private */
    public ArrayList<byte[]>[] frameList;
    /* access modifiers changed from: private */
    public Handler handler;
    private boolean isWorking = false;
    int lastCorrectIndex = 0;
    int lastTotalSub = 0;
    private byte[] mFirmware;
    private String mac;
    String newOTAFileLocalPath = "";
    private String newVersion;
    private PkgLossDelayTask pkgLossDelayTask = new PkgLossDelayTask();
    private SendPacketTask sendPacketTask = new SendPacketTask();
    private TRVOTACallback trvotaCallback;
    private long txFrameNum;
    private byte[] wareVersion = new byte[3];
    byte xieyiVersion = 1;

    public interface TRVOTACallback {
        void onFail(int i, String str, String str2);

        void onProgress(int i, String str, int i2);

        void onStage(int i);

        void onSuccess(String str);
    }

    static /* synthetic */ void access$1100(TRVOta x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 7085, new Class[]{TRVOta.class}, Void.TYPE).isSupported) {
            x0.sendOtaNextCommand();
        }
    }

    static /* synthetic */ int access$1408(TRVOta x0) {
        int i = x0.SUB_PKG_INDEX;
        x0.SUB_PKG_INDEX = i + 1;
        return i;
    }

    static /* synthetic */ void access$1600(TRVOta x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 7086, new Class[]{TRVOta.class}, Void.TYPE).isSupported) {
            x0.disallowSendNext();
        }
    }

    static /* synthetic */ void access$1700(TRVOta x0, String x1) {
        Class[] clsArr = {TRVOta.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 7087, clsArr, Void.TYPE).isSupported) {
            x0.allowSendNext(x1);
        }
    }

    static /* synthetic */ void access$1800(TRVOta x0, int x1, int x2) {
        Object[] objArr = {x0, new Integer(x1), new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7088, new Class[]{TRVOta.class, cls, cls}, Void.TYPE).isSupported) {
            x0.processPkgLoss(x1, x2);
        }
    }

    static /* synthetic */ boolean access$1900(TRVOta x0, String x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 7089, new Class[]{TRVOta.class, String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.readFirmware(x1);
    }

    static /* synthetic */ void access$300(TRVOta x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {TRVOta.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 7080, clsArr, Void.TYPE).isSupported) {
            x0.log(x1, x2);
        }
    }

    static /* synthetic */ void access$400(TRVOta x0, int x1, String x2) {
        Object[] objArr = {x0, new Integer(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7081, new Class[]{TRVOta.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            x0.onFail(x1, x2);
        }
    }

    static /* synthetic */ void access$500(TRVOta x0, String x1) {
        Class[] clsArr = {TRVOta.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 7082, clsArr, Void.TYPE).isSupported) {
            x0.log(x1);
        }
    }

    static /* synthetic */ void access$600(TRVOta x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 7083, new Class[]{TRVOta.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.onProgress(x1);
        }
    }

    static /* synthetic */ void access$700(TRVOta x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 7084, new Class[]{TRVOta.class}, Void.TYPE).isSupported) {
            x0.getWareVersion();
        }
    }

    public TRVOta(String mac2, BleBusinessClient businessClient2, CompatNotifyAndWriteInfoBean _writeAndNotifyBean, BleBusinessConnectBean connectBean) {
        this.mac = mac2;
        this.businessClient = businessClient2;
        this.bleC075Service = (BleC075Service) a.c().g(BleC075Service.class);
        this._otaWriteAndNotifyBean = _writeAndNotifyBean;
        this._connectBean = connectBean;
        this._reporter = BleOTAReporter.getInstance(connectBean);
    }

    public synchronized void startUpgrade(Context context2, String str, TRVOTACallback tRVOTACallback, HandlerThread handlerThread, String str2) {
        Class<String> cls = String.class;
        synchronized (this) {
            if (!PatchProxy.proxy(new Object[]{context2, str, tRVOTACallback, handlerThread, str2}, this, changeQuickRedirect, false, 7053, new Class[]{Context.class, cls, TRVOTACallback.class, HandlerThread.class, cls}, Void.TYPE).isSupported) {
                String url = str;
                HandlerThread handlerThread2 = handlerThread;
                Context context3 = context2;
                TRVOTACallback trvotaCallback2 = tRVOTACallback;
                String newVersion2 = str2;
                log("newVersion is :" + newVersion2);
                this.countdown_s = 0;
                BleOTAReporter instance = BleOTAReporter.getInstance(this._connectBean);
                this._reporter = instance;
                instance.url = url;
                instance.targetFrimwareVersion = newVersion2;
                this.newVersion = newVersion2;
                this.flagIsOtaDataTransformedToDevice = false;
                this._retryTimesOfEveryOtaPackage.clear();
                this._retryTimesOfEveryOtaPackage = null;
                this._retryTimesOfEveryOtaPackage = new HashMap<>();
                this._foreToEndOTA = false;
                this._bleConnectState = true;
                this._bleDisconnectDesc = "";
                OTAStepBean _checkOtaPatams = new OTAStepBean(OTAStepBean.OTA_START_CHECK_PARAMS, 401);
                this._reporter.putStep(_checkOtaPatams);
                if (TextUtils.isEmpty(url)) {
                    _checkOtaPatams.endTrace("OTA参数不合法(缺失URL)", 401);
                    onFail(401, "下载的url为空：url=" + url);
                    return;
                }
                if (this.handler == null) {
                    this.handler = new Handler(handlerThread2.getLooper());
                }
                _checkOtaPatams.endTrace("参数校验成功", 200);
                this.context = context3;
                this.trvotaCallback = trvotaCallback2;
                init();
                this.isWorking = true;
                OTAStepBean _setMtuStep = new OTAStepBean(OTAStepBean.OTA_SET_MTU_STEP, OTAStepBean.OTA_SET_MTU_FAIL_CODE);
                this._reporter.putStep(_setMtuStep);
                this.businessClient.setMtu().J(l.d).Y(new q(this, _setMtuStep, url, trvotaCallback2), new s(this, _setMtuStep));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$startUpgrade$0 */
    public /* synthetic */ void c(OTAStepBean _setMtuStep, final String url, final TRVOTACallback trvotaCallback2, Integer mtu) {
        if (!PatchProxy.proxy(new Object[]{_setMtuStep, url, trvotaCallback2, mtu}, this, changeQuickRedirect, false, 7079, new Class[]{OTAStepBean.class, String.class, TRVOTACallback.class, Integer.class}, Void.TYPE).isSupported) {
            this._currentMtu = mtu.intValue();
            if (mtu.intValue() > 0) {
                _setMtuStep.endTrace("MTU协商成功", 200);
                final OTAStepBean _downloadStep = new OTAStepBean(OTAStepBean.OTA_DOWN_LOAD_OTA_STEP, OTAStepBean.OTA_DOWN_LOAD_FAIL);
                _downloadStep.desc = "下载OTA失败";
                this._reporter.putStep(_downloadStep);
                downloadPkg(url, new com.leedarson.base.http.listener.b() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void onStart() {
                        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7090, new Class[0], Void.TYPE).isSupported) {
                            trvotaCallback2.onStage(1);
                        }
                    }

                    public void onProgress(int currentLength) {
                    }

                    public void onFinish(String localPath) {
                        if (!PatchProxy.proxy(new Object[]{localPath}, this, changeQuickRedirect, false, 7091, new Class[]{String.class}, Void.TYPE).isSupported) {
                            TRVOta tRVOta = TRVOta.this;
                            TRVOta.access$500(tRVOta, "固件包下载成功... url=" + url + "  localPath=" + localPath);
                            _downloadStep.endTrace("下载OTA成功", 200);
                            TRVOta.this.newOTAFileLocalPath = localPath;
                            trvotaCallback2.onStage(2);
                            TRVOta.access$600(TRVOta.this, 50);
                            if (TRVOta.access$1900(TRVOta.this, localPath)) {
                                TRVOta.this.openOTANotify();
                            }
                        }
                    }

                    public void onFailure(String e) {
                        if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7092, new Class[]{String.class}, Void.TYPE).isSupported) {
                            OTAStepBean oTAStepBean = _downloadStep;
                            oTAStepBean.endTrace("下载OTA失败 e=" + e, OTAStepBean.OTA_DOWN_LOAD_FAIL);
                            TRVOta tRVOta = TRVOta.this;
                            TRVOta.access$400(tRVOta, OTAStepBean.OTA_DOWN_LOAD_FAIL, "下载OTA失败 download or writeFile2Disk upgrade package err:" + e);
                        }
                    }
                });
                return;
            }
            _setMtuStep.endTrace("MTU协商失败", OTAStepBean.OTA_SET_MTU_FAIL_CODE);
            onFail(OTAStepBean.OTA_SET_MTU_FAIL_CODE, "MTU协商失败 ble trv set mtu fail  mtu=" + mtu);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$startUpgrade$1 */
    public /* synthetic */ void d(OTAStepBean _setMtuStep, Throwable throwable) {
        Class[] clsArr = {OTAStepBean.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{_setMtuStep, throwable}, this, changeQuickRedirect, false, 7078, clsArr, Void.TYPE).isSupported) {
            _setMtuStep.endTrace("MTU协商失败" + throwable.toString(), OTAStepBean.OTA_SET_MTU_FAIL_CODE);
            onFail(OTAStepBean.OTA_SET_MTU_FAIL_CODE, "MTU协商失败 ble trv set mtu err:" + throwable.toString());
        }
    }

    private void downloadPkg(String url, com.leedarson.base.http.listener.b downloadListener) {
        if (!PatchProxy.proxy(new Object[]{url, downloadListener}, this, changeQuickRedirect, false, 7054, new Class[]{String.class, com.leedarson.base.http.listener.b.class}, Void.TYPE).isSupported) {
            new UpdateRetryStrategy().setRetryDelayMillis(500);
            Observable<ResponseBody> observable = ((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).h(url);
            String localOtaName = url.split("/")[url.split("/").length - 1] + "_" + this.mac + "_trvimg.lib";
            File localOtaFile = new File(this.context.getFilesDir(), localOtaName);
            if (localOtaFile.exists()) {
                log("本地文件存在，直接使用缓存文件... localOtaFile=" + localOtaFile.getAbsolutePath());
                downloadListener.onFinish(localOtaFile.getAbsolutePath());
                traceOtaByELK("TRV 固件包下载成功(使用本地缓存) " + localOtaName, "info", "downloadPkg");
                return;
            }
            b bVar = this._diposOfDownload;
            if (bVar != null && !bVar.isDisposed()) {
                this._diposOfDownload.dispose();
            }
            this._diposOfDownload = observable.b0(l.d).J(l.d).Y(new u(this, localOtaName, downloadListener), new t(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$downloadPkg$2 */
    public /* synthetic */ void a(String localOtaName, com.leedarson.base.http.listener.b downloadListener, e0 response) {
        Class[] clsArr = {String.class, com.leedarson.base.http.listener.b.class, e0.class};
        if (!PatchProxy.proxy(new Object[]{localOtaName, downloadListener, response}, this, changeQuickRedirect, false, 7077, clsArr, Void.TYPE).isSupported) {
            File file = new File(this.context.getFilesDir(), localOtaName);
            log("固件包正在下载...");
            traceOtaByELK("TRV 固件包下载成功,写入文件", "info", "downloadPkg");
            com.leedarson.base.utils.l.g(response, file, downloadListener);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$downloadPkg$3 */
    public /* synthetic */ void b(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 7076, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            onFail(OTAStepBean.OTA_DOWN_LOAD_FAIL, "TRV 固件包下载失败 " + throwable.toString());
        }
    }

    private void onFail(int code, String msg) {
        Object[] objArr = {new Integer(code), msg};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7055, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            OTAStepBean _successStep = new OTAStepBean(OTAStepBean.OTA_EXCEPTION_STEP, code);
            _successStep.desc = "OTA失败：" + msg;
            BleOTAReporter bleOTAReporter = this._reporter;
            bleOTAReporter.descExtro = msg;
            bleOTAReporter.putStep(_successStep);
            this._reporter.report();
            otaComplete();
            TRVOTACallback tRVOTACallback = this.trvotaCallback;
            if (tRVOTACallback != null) {
                tRVOTACallback.onFail(code, msg, this.mac);
            }
            log("trv ota失败:" + this.mac + ",msg:" + msg + ",code" + code, "e");
            StringBuilder sb = new StringBuilder();
            sb.append("trv ota失败:");
            sb.append(this.mac);
            sb.append(",reason:");
            sb.append(msg);
            traceOtaByELK(sb.toString(), "failure", "onFail");
        }
    }

    private void onSuccess() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7056, new Class[0], Void.TYPE).isSupported) {
            OTAStepBean _successStep = new OTAStepBean(OTAStepBean.OTA_SUCCESS_STEP, 200);
            _successStep.desc = "OTA成功";
            BleOTAReporter bleOTAReporter = this._reporter;
            bleOTAReporter.descExtro = "OTA成功";
            bleOTAReporter.putStep(_successStep);
            this._reporter.report();
            otaComplete();
            TRVOTACallback tRVOTACallback = this.trvotaCallback;
            if (tRVOTACallback != null) {
                tRVOTACallback.onSuccess(this.mac);
            }
            traceOtaByELK("trv ota成功:" + this.mac, FirebaseAnalytics.Param.SUCCESS, "onSuccess");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onProgress(int r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r9)
            r3 = 0
            r1[r3] = r2
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 7057(0x1b91, float:9.889E-42)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            com.leedarson.serviceimpl.business.TRVOta$TRVOTACallback r1 = r0.trvotaCallback
            if (r1 == 0) goto L_0x0030
            java.lang.String r2 = r0.mac
            int r3 = r0.countdown_s
            r1.onProgress(r9, r2, r3)
        L_0x0030:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.TRVOta.onProgress(int):void");
    }

    private void otaComplete() {
        this.isWorking = false;
    }

    private void traceOtaByELK(String message, String level, String method) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{message, level, method}, this, changeQuickRedirect, false, 7058, clsArr, Void.TYPE).isSupported) {
            com.leedarson.log.elk.a s = com.leedarson.log.elk.a.y(this).x(MeshConstants.TRACE_ID_OTA).c(OtaCtrl.class.getSimpleName()).o(level).t("LdsBle").p(message).s(method);
            s.u("mac", "" + this.mac).a().b();
        }
    }

    private boolean readFirmware(String fileName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{fileName}, this, changeQuickRedirect, false, 7059, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            InputStream stream = new FileInputStream(fileName);
            byte[] bArr = new byte[stream.available()];
            this.mFirmware = bArr;
            stream.read(bArr);
            stream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            this.mFirmware = null;
            onFail(422, "读取本地固件包文件失败 e=" + e.toString());
            return false;
        }
    }

    private void getWareVersion() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7060, new Class[0], Void.TYPE).isSupported) {
            OTAStepBean oTAStepBean = new OTAStepBean(OTAStepBean.GET_WARE_VERSION_STEP, OTAStepBean.GET_WARE_VERSION_FAIL);
            this._getFireVersionStep = oTAStepBean;
            this._reporter.putStep(oTAStepBean);
            OTACommand otaCommand = new OTACommand(this.mac, this.businessClient.getSequenceNum());
            otaCommand.packetFlag = 17;
            otaCommand.commandId = 0;
            byte[] dataFrame = otaCommand.toDataFrame();
            log("请求固件版本:" + w.a(dataFrame));
            send(dataFrame);
        }
    }

    private void sendOTAMD5CheckInfo() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7061, new Class[0], Void.TYPE).isSupported) {
            OTACommand otaCommand = new OTACommand(this.mac, this.businessClient.getSequenceNum());
            otaCommand.packetFlag = 17;
            otaCommand.commandId = 4;
            OTAStepBean oTAStepBean = new OTAStepBean(OTAStepBean.CHECK_OTA_MD5_STEP, OTAStepBean.CHECK_OTA_MD5_STEP_FAIL);
            this._checkMd5Step = oTAStepBean;
            oTAStepBean.desc = "发起校验";
            this._reporter.putStep(oTAStepBean);
            String newOTAFileMd5 = getFileMd5(new File(this.newOTAFileLocalPath));
            log("新固件OTA文件MD5值是：" + newOTAFileMd5 + "   localPath=" + this.newOTAFileLocalPath);
            otaCommand.payload = newOTAFileMd5.getBytes(StandardCharsets.UTF_8);
            byte[] dataFrame = otaCommand.toDataFrame();
            log("发起固件OTA文件Md5较验:" + w.a(dataFrame));
            send(dataFrame);
        }
    }

    public static String getFileMd5(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 7062, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            if (!file.isFile()) {
                return "";
            }
            byte[] buffer = new byte[1024];
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                FileInputStream in = new FileInputStream(file);
                while (true) {
                    int read = in.read(buffer, 0, 1024);
                    int len = read;
                    if (read != -1) {
                        digest.update(buffer, 0, len);
                    } else {
                        in.close();
                        return e.a(digest.digest());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e2) {
            Log.e("PatchConfigRepos", "getFileMd5  exception   e=" + e2.toString());
            return "";
        }
    }

    private void sendOTAPrepareCommand() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7063, new Class[0], Void.TYPE).isSupported) {
            OTAStepBean oTAStepBean = new OTAStepBean(OTAStepBean.SEND_OTA_PREPARE_CMD_STEP, OTAStepBean.SEND_OTA_PREPARE_CMD_STEP_FAIL);
            this._prePareToSendOTAStep = oTAStepBean;
            this._reporter.putStep(oTAStepBean);
            if (!isNewTransportVersion()) {
                OTACommand otaCommand = new OTACommand(this.mac, this.businessClient.getSequenceNum());
                otaCommand.packetFlag = 17;
                this._prePareToSendOTAStep.desc = "老协议OTA..ing";
                otaCommand.commandId = 1;
                byte[] wareVersion2 = TRVUtils.getVersionBytes(this.newVersion);
                byte[] crc16 = TRVUtils.getCRC16(this.mFirmware);
                byte[] lenArr = TRVUtils.int2ByteArr((long) this.mFirmware.length, 4);
                byte[] bArr = new byte[11];
                otaCommand.payload = bArr;
                bArr[0] = 0;
                bArr[1] = 1;
                System.arraycopy(wareVersion2, 0, bArr, 2, wareVersion2.length);
                System.arraycopy(lenArr, 0, otaCommand.payload, 5, lenArr.length);
                System.arraycopy(crc16, 0, otaCommand.payload, 9, crc16.length);
                byte[] dataFrame = otaCommand.toDataFrame();
                log("请求OTA开始(老协议):" + w.a(dataFrame) + ",version:" + this.newVersion);
                this._reporter.currentFirmwareVersion = this.newVersion;
                send(dataFrame);
                return;
            }
            this._prePareToSendOTAStep.desc = "新协议OTA..ing";
            OTACommand otaCommand2 = new OTACommand(this.mac, this.businessClient.getSequenceNum());
            otaCommand2.packetFlag = 17;
            otaCommand2.commandId = 1;
            byte payloadXieyiVersion = this.xieyiVersion;
            byte[] wareVersion3 = this.newVersion.getBytes(StandardCharsets.UTF_8);
            byte[] lenArr2 = TRVUtils.int2ByteArr((long) this.mFirmware.length, 4);
            byte[] crc162 = TRVUtils.getCRC16(this.mFirmware);
            byte[] payload = new byte[(wareVersion3.length + 3 + lenArr2.length + crc162.length)];
            payload[0] = 0;
            payload[1] = payloadXieyiVersion;
            payload[2] = (byte) wareVersion3.length;
            System.arraycopy(wareVersion3, 0, payload, 3, wareVersion3.length);
            System.arraycopy(lenArr2, 0, payload, wareVersion3.length + 3, lenArr2.length);
            System.arraycopy(crc162, 0, payload, wareVersion3.length + 3 + lenArr2.length, crc162.length);
            otaCommand2.payload = payload;
            byte[] dataFrame2 = otaCommand2.toDataFrame();
            log("请求OTA开始:（新协议）" + w.a(dataFrame2) + ",version:" + this.newVersion);
            this._reporter.currentFirmwareVersion = this.newVersion;
            send(dataFrame2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0161, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void sendOtaNextCommand() {
        /*
            r11 = this;
            monitor-enter(r11)
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x01af }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x01af }
            r4 = 0
            r5 = 7064(0x1b98, float:9.899E-42)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x01af }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x01af }
            r2 = r11
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x01af }
            boolean r1 = r1.isSupported     // Catch:{ all -> 0x01af }
            if (r1 == 0) goto L_0x0018
            monitor-exit(r11)
            return
        L_0x0018:
            r1 = r11
            int r2 = r1.FRAME_INDEX     // Catch:{ all -> 0x01af }
            java.util.ArrayList<byte[]>[] r3 = r1.frameList     // Catch:{ all -> 0x01af }
            int r4 = r3.length     // Catch:{ all -> 0x01af }
            if (r2 < r4) goto L_0x0022
            monitor-exit(r11)
            return
        L_0x0022:
            int r2 = r1.FRAME_INDEX     // Catch:{ all -> 0x01af }
            r2 = r3[r2]     // Catch:{ all -> 0x01af }
            int r3 = r1.SUB_PKG_INDEX     // Catch:{ all -> 0x01af }
            int r4 = r2.size()     // Catch:{ all -> 0x01af }
            if (r3 < r4) goto L_0x0030
            monitor-exit(r11)
            return
        L_0x0030:
            boolean r3 = r1._foreToEndOTA     // Catch:{ all -> 0x01af }
            if (r3 == 0) goto L_0x003b
            java.lang.String r0 = "已经被强制终止OTA了，你只能重新尝试"
            r1.log(r0)     // Catch:{ all -> 0x01af }
            monitor-exit(r11)
            return
        L_0x003b:
            int r3 = r1.SUB_PKG_INDEX     // Catch:{ all -> 0x01af }
            java.lang.Object r3 = r2.get(r3)     // Catch:{ all -> 0x01af }
            byte[] r3 = (byte[]) r3     // Catch:{ all -> 0x01af }
            com.leedarson.serviceimpl.business.BleBusinessClient r4 = r1.businessClient     // Catch:{ all -> 0x01af }
            int r4 = r4.getSequenceNum()     // Catch:{ all -> 0x01af }
            com.leedarson.serviceimpl.business.bean.OTACommand r5 = new com.leedarson.serviceimpl.business.bean.OTACommand     // Catch:{ all -> 0x01af }
            java.lang.String r6 = r1.mac     // Catch:{ all -> 0x01af }
            r5.<init>(r6, r4)     // Catch:{ all -> 0x01af }
            int r6 = r2.size()     // Catch:{ all -> 0x01af }
            int r7 = r1.SUB_PKG_INDEX     // Catch:{ all -> 0x01af }
            r8 = 1
            int r7 = r7 + r8
            byte r6 = com.leedarson.serviceimpl.business.TRVUtils.createPkgFlag(r6, r7)     // Catch:{ all -> 0x01af }
            r5.packetFlag = r6     // Catch:{ all -> 0x01af }
            boolean r6 = r1.flagSupportPointTransport     // Catch:{ all -> 0x01af }
            if (r6 == 0) goto L_0x0064
            r6 = 5
            goto L_0x0065
        L_0x0064:
            r6 = 2
        L_0x0065:
            r5.commandId = r6     // Catch:{ all -> 0x01af }
            r5.payload = r3     // Catch:{ all -> 0x01af }
            byte[] r6 = r5.toDataFrame()     // Catch:{ all -> 0x01af }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x01af }
            r7.<init>()     // Catch:{ all -> 0x01af }
            r7.append(r4)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = ",请求写OTA分包数据:FRAME_INDEX/totalFrame:"
            r7.append(r9)     // Catch:{ all -> 0x01af }
            int r9 = r1.FRAME_INDEX     // Catch:{ all -> 0x01af }
            r7.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = "/"
            r7.append(r9)     // Catch:{ all -> 0x01af }
            java.util.ArrayList<byte[]>[] r9 = r1.frameList     // Catch:{ all -> 0x01af }
            int r9 = r9.length     // Catch:{ all -> 0x01af }
            r7.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = ",SUB_PKG_INDEX/subPkgTotal:"
            r7.append(r9)     // Catch:{ all -> 0x01af }
            int r9 = r1.SUB_PKG_INDEX     // Catch:{ all -> 0x01af }
            r7.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = "/"
            r7.append(r9)     // Catch:{ all -> 0x01af }
            int r9 = r2.size()     // Catch:{ all -> 0x01af }
            r7.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = ",数据大小："
            r7.append(r9)     // Catch:{ all -> 0x01af }
            int r9 = r6.length     // Catch:{ all -> 0x01af }
            r7.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = ",listSize="
            r7.append(r9)     // Catch:{ all -> 0x01af }
            int r9 = r2.size()     // Catch:{ all -> 0x01af }
            r7.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = ",payload="
            r7.append(r9)     // Catch:{ all -> 0x01af }
            int r9 = r3.length     // Catch:{ all -> 0x01af }
            r7.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = ",fwLength="
            r7.append(r9)     // Catch:{ all -> 0x01af }
            byte[] r9 = r1.mFirmware     // Catch:{ all -> 0x01af }
            int r9 = r9.length     // Catch:{ all -> 0x01af }
            r7.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x01af }
            java.lang.String r9 = "v"
            r1.log(r7, r9)     // Catch:{ all -> 0x01af }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x01af }
            r7.<init>()     // Catch:{ all -> 0x01af }
            int r9 = r1.FRAME_INDEX     // Catch:{ all -> 0x01af }
            r7.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = "_"
            r7.append(r9)     // Catch:{ all -> 0x01af }
            int r9 = r1.SUB_PKG_INDEX     // Catch:{ all -> 0x01af }
            r7.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x01af }
            java.util.HashMap<java.lang.String, java.lang.Integer> r9 = r1._retryTimesOfEveryOtaPackage     // Catch:{ all -> 0x01af }
            boolean r9 = r9.containsKey(r7)     // Catch:{ all -> 0x01af }
            if (r9 != 0) goto L_0x00fb
            java.util.HashMap<java.lang.String, java.lang.Integer> r9 = r1._retryTimesOfEveryOtaPackage     // Catch:{ all -> 0x01af }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x01af }
            r9.put(r7, r0)     // Catch:{ all -> 0x01af }
        L_0x00fb:
            java.util.HashMap<java.lang.String, java.lang.Integer> r0 = r1._retryTimesOfEveryOtaPackage     // Catch:{ all -> 0x01af }
            java.lang.Object r0 = r0.get(r7)     // Catch:{ all -> 0x01af }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ all -> 0x01af }
            int r0 = r0.intValue()     // Catch:{ all -> 0x01af }
            r9 = 4
            if (r0 >= r9) goto L_0x0162
            boolean r0 = r1._bleConnectState     // Catch:{ all -> 0x01af }
            if (r0 != 0) goto L_0x010f
            goto L_0x0162
        L_0x010f:
            java.util.HashMap<java.lang.String, java.lang.Integer> r0 = r1._retryTimesOfEveryOtaPackage     // Catch:{ all -> 0x01af }
            java.lang.Object r9 = r0.get(r7)     // Catch:{ all -> 0x01af }
            java.lang.Integer r9 = (java.lang.Integer) r9     // Catch:{ all -> 0x01af }
            int r9 = r9.intValue()     // Catch:{ all -> 0x01af }
            int r9 = r9 + r8
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x01af }
            r0.put(r7, r9)     // Catch:{ all -> 0x01af }
            boolean r0 = r1.isNewTransportVersion()     // Catch:{ all -> 0x01af }
            if (r0 == 0) goto L_0x014b
            int r0 = r1.FRAME_INDEX     // Catch:{ all -> 0x01af }
            if (r0 != 0) goto L_0x014b
            int r0 = r1.SUB_PKG_INDEX     // Catch:{ all -> 0x01af }
            int r9 = r2.size()     // Catch:{ all -> 0x01af }
            int r9 = r9 - r8
            if (r0 != r9) goto L_0x014b
            r1.send(r6)     // Catch:{ all -> 0x01af }
            java.lang.String r0 = "OTA分包==>已经发送第一组的最后一条数据，不要急着继续往后发，等固件回复后重启数据发送"
            r1.log(r0)     // Catch:{ all -> 0x01af }
            r1.disallowSendNext()     // Catch:{ all -> 0x01af }
            android.os.Handler r0 = r1.handler     // Catch:{ all -> 0x01af }
            com.leedarson.serviceimpl.business.TRVOta$FrameFinishTimeoutTask r8 = r1.frameFinishTimeoutTask     // Catch:{ all -> 0x01af }
            r9 = 25000(0x61a8, double:1.23516E-319)
            r0.postDelayed(r8, r9)     // Catch:{ all -> 0x01af }
            goto L_0x0160
        L_0x014b:
            r1.send(r6)     // Catch:{ all -> 0x01af }
            int r0 = r1.SUB_PKG_INDEX     // Catch:{ all -> 0x01af }
            int r9 = r2.size()     // Catch:{ all -> 0x01af }
            int r9 = r9 - r8
            if (r0 != r9) goto L_0x0160
            android.os.Handler r0 = r1.handler     // Catch:{ all -> 0x01af }
            com.leedarson.serviceimpl.business.TRVOta$FrameFinishTimeoutTask r8 = r1.frameFinishTimeoutTask     // Catch:{ all -> 0x01af }
            r9 = 3000(0xbb8, double:1.482E-320)
            r0.postDelayed(r8, r9)     // Catch:{ all -> 0x01af }
        L_0x0160:
            monitor-exit(r11)
            return
        L_0x0162:
            r1.disallowSendNext()     // Catch:{ all -> 0x01af }
            android.os.Handler r0 = r1.handler     // Catch:{ all -> 0x01af }
            com.leedarson.serviceimpl.business.TRVOta$FrameFinishTimeoutTask r10 = r1.frameFinishTimeoutTask     // Catch:{ all -> 0x01af }
            r0.removeCallbacks(r10)     // Catch:{ all -> 0x01af }
            r1._foreToEndOTA = r8     // Catch:{ all -> 0x01af }
            r0 = -400008(0xfffffffffff9e578, float:NaN)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x01af }
            r8.<init>()     // Catch:{ all -> 0x01af }
            java.lang.String r10 = "在进行OTA阶段，出现第"
            r8.append(r10)     // Catch:{ all -> 0x01af }
            int r10 = r1.FRAME_INDEX     // Catch:{ all -> 0x01af }
            r8.append(r10)     // Catch:{ all -> 0x01af }
            java.lang.String r10 = "组，第"
            r8.append(r10)     // Catch:{ all -> 0x01af }
            int r10 = r1.SUB_PKG_INDEX     // Catch:{ all -> 0x01af }
            r8.append(r10)     // Catch:{ all -> 0x01af }
            java.lang.String r10 = "包数据重试次数达到了"
            r8.append(r10)     // Catch:{ all -> 0x01af }
            r8.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = "次还未成功(无收到设备的响应)，被判定为ota失败,原因：1、App与Ble断开连接 2、设备发送的OTA Notify在App中未收到,而当前的蓝牙连接状态：bleConnectStatue="
            r8.append(r9)     // Catch:{ all -> 0x01af }
            boolean r9 = r1._bleConnectState     // Catch:{ all -> 0x01af }
            r8.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = ",bleDesc="
            r8.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r9 = r1._bleDisconnectDesc     // Catch:{ all -> 0x01af }
            r8.append(r9)     // Catch:{ all -> 0x01af }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x01af }
            r1.onFail(r0, r8)     // Catch:{ all -> 0x01af }
            monitor-exit(r11)
            return
        L_0x01af:
            r0 = move-exception
            monitor-exit(r11)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.TRVOta.sendOtaNextCommand():void");
    }

    private void sendOtaEndCommand() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7065, new Class[0], Void.TYPE).isSupported) {
            OTACommand otaCommand = new OTACommand(this.mac, this.businessClient.getSequenceNum());
            otaCommand.packetFlag = 17;
            otaCommand.commandId = 3;
            byte[] dataFrame = otaCommand.toDataFrame();
            log("OTA分包 请求OTA结束:" + w.a(dataFrame));
            send(dataFrame);
        }
    }

    private boolean isNewTransportVersion() {
        return (this.xieyiVersion & 255) != 1;
    }

    public static int byteToInt2(byte[] byteArray) {
        int result = 0;
        for (byte b : byteArray) {
            result = (result << 8) | (b & 255);
        }
        return result;
    }

    public void onDeviceNofity(byte[] bArr) {
        if (!PatchProxy.proxy(new Object[]{bArr}, this, changeQuickRedirect, false, 7066, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            byte[] rxData = bArr;
            if (rxData == null || rxData.length < 4) {
                onFail(OTAStepBean.ON_RECEIVE_DEVICE_INVALID_DATA, "收到来自设备的异常数据：ota notify receive err data=" + e.a(rxData));
                return;
            }
            int i = rxData[0] & 255;
            byte[] flags = TRVUtils.parsePkgFlag(rxData[1]);
            byte subFrameTotalSize = flags[0];
            byte subPkgCurrentIndex = flags[1];
            byte cmdId = rxData[2];
            byte len = rxData[3];
            byte[] payLoad = new byte[len];
            System.arraycopy(rxData, 4, payLoad, 0, len);
            switch (cmdId) {
                case Byte.MIN_VALUE:
                    int seqNum = i;
                    log("收到固件版本响应:" + w.a(payLoad));
                    this.xieyiVersion = payLoad[0];
                    this._getFireVersionStep.endTrace("获取版本号成功", 200);
                    if ((this.xieyiVersion & 255) == 32) {
                        int versionDesPackSizeLength = payLoad[1] & 255;
                        System.arraycopy(payLoad, 2, new byte[versionDesPackSizeLength], 0, versionDesPackSizeLength);
                        sendOTAMD5CheckInfo();
                        return;
                    }
                    byte[] bArr2 = this.wareVersion;
                    bArr2[0] = payLoad[1];
                    bArr2[1] = payLoad[2];
                    bArr2[2] = payLoad[3];
                    sendOTAPrepareCommand();
                    return;
                case -127:
                    int seqNum2 = i;
                    log(" 应答OTA升级开始 收到开始OTA响应 payload:" + w.a(payLoad));
                    byte status = payLoad[0];
                    onProgress(52);
                    if (status != 0) {
                        this._prePareToSendOTAStep.endTrace("OTA协商失败 " + w.a(payLoad), OTAStepBean.SEND_OTA_PREPARE_CMD_STEP_FAIL);
                        this._reporter.report();
                        return;
                    }
                    int i2 = this._currentMtu;
                    if (i2 < (payLoad[1] & 255)) {
                        this.PKG_SIZE = i2;
                    } else {
                        this.PKG_SIZE = payLoad[1] & 255;
                    }
                    int i3 = this.PKG_SIZE;
                    if (i3 == i2) {
                        this.PKG_SIZE = i3 - 4;
                    }
                    int alreadyReceiveByteCount = 0;
                    if (len >= 6) {
                        byte[] receiveByteCount = new byte[4];
                        System.arraycopy(payLoad, 2, receiveByteCount, 0, 4);
                        alreadyReceiveByteCount = byteToInt2(receiveByteCount);
                        this.flagSupportPointTransport = true;
                        log("判定是否支持断点续传协议：支持断点结续传 " + len);
                    } else {
                        this.flagSupportPointTransport = false;
                        log("判定是否支持断点续传协议：不支持断点结续传 " + len);
                    }
                    analyseWareData(alreadyReceiveByteCount);
                    log("PKG_SIZE=" + this.PKG_SIZE + ",fileSize=" + this.mFirmware.length + ",TOTAL FRAME:" + this.frameList.length);
                    disallowSendNext();
                    allowSendNext("收到固件的准许发送ota包回执");
                    OTAStepBean oTAStepBean = this._prePareToSendOTAStep;
                    StringBuilder sb = new StringBuilder();
                    sb.append("OTA协商成功");
                    sb.append(w.a(payLoad));
                    oTAStepBean.endTrace(sb.toString(), 200);
                    return;
                case -126:
                case -123:
                    byte res_status = payLoad[0];
                    log("收到OTA分包发送响应: seqNumber:" + i + ",状态码:" + res_status + ",数据内容:" + w.a(payLoad) + ",subFrameTotalSize=" + subFrameTotalSize + ",subPkgCurrentIndex=" + subPkgCurrentIndex + ",currentFrameIndex=" + this.FRAME_INDEX);
                    if (this.flagIsOtaDataTransformedToDevice) {
                        log("OTA分包，数据已经传输完成，不用再care固件数据回执....已在走ota结束请求");
                        this.handler.removeCallbacks(this.frameFinishTimeoutTask);
                        disallowSendNext();
                        return;
                    }
                    this.handler.removeCallbacks(this.frameFinishTimeoutTask);
                    disallowSendNext();
                    if (res_status == 0) {
                        int seqNum3 = i;
                        int receiveOtaDataLength = Integer.parseInt(w.a(payLoad), 16);
                        if (subPkgCurrentIndex == subFrameTotalSize) {
                            byte[] bArr3 = this.mFirmware;
                            if (receiveOtaDataLength == bArr3.length) {
                                log("OTA分包==>已经全部传输完毕--->正在与固件协商OTA结束");
                                this.flagIsOtaDataTransformedToDevice = true;
                                disallowSendNext();
                                sendOtaEndCommand();
                                return;
                            }
                            if (this.countdown_s == 0) {
                                this.countdown_s = ((this.frameList.length * 15) * 35) / 1000;
                            }
                            float lastTransportPercent = (((float) this.alreadySendDataLength) * 1.0f) / ((float) bArr3.length);
                            int i4 = receiveOtaDataLength;
                            onProgress((int) ((((double) ((((float) this.FRAME_INDEX) * 100.0f) / ((float) this.frameList.length))) * (0.48d - (((double) lastTransportPercent) * 0.48d))) + 52.0d + (((double) lastTransportPercent) * 0.48d * 100.0d)));
                            this.FRAME_INDEX++;
                            this.SUB_PKG_INDEX = 0;
                            allowSendNext("开始发送下一组ota包数据");
                            return;
                        }
                        return;
                    } else if (res_status == 4) {
                        disallowSendNext();
                        byte lossInfo = payLoad[1];
                        byte[] info = TRVUtils.parsePkgFlag(lossInfo);
                        this.lastTotalSub = info[0];
                        this.lastCorrectIndex = info[1];
                        log("OTA中途丢包,当前帧序号:" + this.FRAME_INDEX + ",固件最后正确的帧子包总数:" + this.lastTotalSub + ",固件最后正确的子包序号:" + this.lastCorrectIndex);
                        this.handler.removeCallbacks(this.pkgLossDelayTask);
                        byte b = i;
                        byte b2 = lossInfo;
                        this.handler.postDelayed(this.pkgLossDelayTask, 1000);
                        return;
                    } else {
                        int seqNum4 = i;
                        this._reporter.deviceErrorCode = res_status + "";
                        onFail(res_status, "设备OTA中途失败:status code:" + res_status);
                        return;
                    }
                case -125:
                    log("OTA分包 收到结束OTA响应,状态码:" + w.a(payLoad));
                    byte res_status1 = payLoad[0];
                    if (res_status1 != 0) {
                        this._reporter.deviceErrorCode = w.a(payLoad) + "";
                        onFail(res_status1, "设备OTA结束时失败:status code:" + parseErrorCode(res_status1) + "  rawData=" + (payLoad[0] & 255) + "  payload=" + w.a(payLoad) + ",alldata=" + w.a(rxData));
                        return;
                    }
                    onProgress(100);
                    onSuccess();
                    byte b3 = i;
                    return;
                case -124:
                    log("收到固件MD5响应码数据:" + w.a(payLoad));
                    if (payLoad[0] != 0) {
                        this._checkMd5Step.endTrace("M5D校验失败 " + w.a(payLoad), OTAStepBean.CHECK_OTA_MD5_STEP_FAIL);
                        onFail(OTAStepBean.CHECK_OTA_MD5_STEP_FAIL, "设备OTA设置文件Md5码回执失败：" + parseErrorCode(payLoad[0] & 255));
                        return;
                    }
                    this._checkMd5Step.endTrace("M5D校验成功 " + w.a(payLoad), 200);
                    sendOTAPrepareCommand();
                    byte b4 = i;
                    return;
                default:
                    byte b5 = i;
                    return;
            }
        }
    }

    private void processPkgLoss(int subFrameTotalSize, int subPkgCurrentIndex) {
        Object[] objArr = {new Integer(subFrameTotalSize), new Integer(subPkgCurrentIndex)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7067, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if (subPkgCurrentIndex != subFrameTotalSize) {
                this.SUB_PKG_INDEX = subPkgCurrentIndex + 1;
                allowSendNext("丢包处理-本组进行重传");
            } else if (this.FRAME_INDEX == this.frameList.length - 1) {
                sendOtaEndCommand();
            } else {
                this.FRAME_INDEX++;
                this.SUB_PKG_INDEX = 0;
                allowSendNext("丢包处理-本组进行全部重传");
            }
        }
    }

    private void send(byte[] frame) {
        if (!PatchProxy.proxy(new Object[]{frame}, this, changeQuickRedirect, false, 7068, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.bleC075Service.commonWrite(this.mac, (BluetoothDevice) null, UUID.fromString(this._otaWriteAndNotifyBean.serviceUuid), UUID.fromString(this._otaWriteAndNotifyBean.writeCharacterUuid), (String) null, frame, (String) null, new d() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onSimpleWriteSuccess(int i, int i2, byte[] bArr, String str, String str2) {
                    Class<String> cls = String.class;
                    Object[] objArr = {new Integer(i), new Integer(i2), bArr, str, str2};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    Class cls2 = Integer.TYPE;
                    if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7093, new Class[]{cls2, cls2, byte[].class, cls, cls}, Void.TYPE).isSupported) {
                        TRVOta.access$300(TRVOta.this, "send success", "v");
                    }
                }

                public void onSimpleWriteFail(Exception exception, String str, String str2, int i) {
                    Class<String> cls = String.class;
                    Object[] objArr = {exception, str, str2, new Integer(i)};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7094, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
                        TRVOta tRVOta = TRVOta.this;
                        TRVOta.access$300(tRVOta, "send fail:" + exception.getMessage(), "e");
                    }
                }
            }, false, 10);
        }
    }

    public void openOTANotify() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7069, new Class[0], Void.TYPE).isSupported) {
            final OTAStepBean _openNotifyStep = new OTAStepBean(OTAStepBean.OPEN_OTA_NOTIFY_STEP, OTAStepBean.OPEN_OTA_NOTIFY_STEP_FAIL);
            this._reporter.putStep(_openNotifyStep);
            AnonymousClass3 r9 = new com.leedarson.serviceimpl.blec075.callback.b() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onSimpleNotifyError(Exception exception, String str, String str2, int i) {
                    Class<String> cls = String.class;
                    Object[] objArr = {exception, str, str2, new Integer(i)};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7095, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
                        TRVOta tRVOta = TRVOta.this;
                        TRVOta.access$300(tRVOta, "onNotifyFailure" + exception.getMessage(), "e");
                        OTAStepBean oTAStepBean = _openNotifyStep;
                        oTAStepBean.endTrace("Notify打开失败 e=" + exception.toString(), OTAStepBean.OPEN_OTA_NOTIFY_STEP_FAIL);
                        TRVOta tRVOta2 = TRVOta.this;
                        TRVOta.access$400(tRVOta2, OTAStepBean.OPEN_OTA_NOTIFY_STEP_FAIL, "Notify打开失败 err:" + exception.toString());
                    }
                }

                public void onSimpleNotifySuccess(String str, String mac) {
                    Class<String> cls = String.class;
                    Class[] clsArr = {cls, cls};
                    if (!PatchProxy.proxy(new Object[]{str, mac}, this, changeQuickRedirect, false, 7096, clsArr, Void.TYPE).isSupported) {
                        TRVOta tRVOta = TRVOta.this;
                        TRVOta.access$500(tRVOta, "onNotifySuccess:" + mac);
                        _openNotifyStep.endTrace("OTA通道打开成功", 200);
                        TRVOta.access$600(TRVOta.this, 51);
                        TRVOta.access$700(TRVOta.this);
                    }
                }

                public void onSimpleCharacteristicChanged(byte[] data, String str, String str2) {
                    Class<String> cls = String.class;
                    Class[] clsArr = {byte[].class, cls, cls};
                    if (!PatchProxy.proxy(new Object[]{data, str, str2}, this, changeQuickRedirect, false, 7097, clsArr, Void.TYPE).isSupported) {
                        TRVOta tRVOta = TRVOta.this;
                        TRVOta.access$300(tRVOta, "onNotify:" + h.b(data), "d");
                        TRVOta.this.onDeviceNofity(data);
                    }
                }
            };
            CompatNotifyAndWriteInfoBean compatNotifyAndWriteInfoBean = this._otaWriteAndNotifyBean;
            if (compatNotifyAndWriteInfoBean.isNotifyChannel) {
                this.bleC075Service.commonNotify(this.mac, UUID.fromString(compatNotifyAndWriteInfoBean.serviceUuid), UUID.fromString(this._otaWriteAndNotifyBean.notifyCharacterUuid), "", "", r9);
            } else {
                this.bleC075Service.commonIndicate(this.mac, UUID.fromString(compatNotifyAndWriteInfoBean.serviceUuid), UUID.fromString(this._otaWriteAndNotifyBean.notifyCharacterUuid), "", r9);
            }
        }
    }

    private void log(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 7070, new Class[]{String.class}, Void.TYPE).isSupported) {
            log("blebusiness.ota " + msg, "i");
        }
    }

    private void log(String msg, String level) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{msg, level}, this, changeQuickRedirect, false, 7071, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if ("d".equals(level)) {
                timber.log.a.g("BleTRVOta").a(msg, new Object[0]);
            } else if ("i".equals(level)) {
                timber.log.a.g("BleTRVOta").h(msg, new Object[0]);
            } else if ("v".equals(level)) {
                timber.log.a.g("BleTRVOta").m(msg, new Object[0]);
            } else if ("e".equals(level)) {
                timber.log.a.g("BleTRVOta").c(msg, new Object[0]);
            }
        }
    }

    private void analyseWareData(int alreadyReceiveByteCount) {
        int i;
        if (!PatchProxy.proxy(new Object[]{new Integer(alreadyReceiveByteCount)}, this, changeQuickRedirect, false, 7072, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.alreadySendDataLength = alreadyReceiveByteCount;
            log("进行文件拆包之前已传输数据量（字节数）" + alreadyReceiveByteCount);
            byte[] bArr = this.mFirmware;
            byte[] restWaitToSendBytes = new byte[(bArr.length - alreadyReceiveByteCount)];
            System.arraycopy(bArr, alreadyReceiveByteCount, restWaitToSendBytes, 0, restWaitToSendBytes.length);
            int length = restWaitToSendBytes.length;
            int i2 = this.PKG_SIZE;
            int frameNum = length / (i2 * 15);
            if (restWaitToSendBytes.length % (i2 * 15) != 0) {
                frameNum++;
            }
            this.frameList = new ArrayList[frameNum];
            for (int f = 0; f < frameNum; f++) {
                if (f == frameNum - 1) {
                    i = restWaitToSendBytes.length - ((this.PKG_SIZE * f) * 15);
                } else {
                    i = this.PKG_SIZE * 15;
                }
                byte[] subFrame = new byte[i];
                System.arraycopy(restWaitToSendBytes, this.PKG_SIZE * f * 15, subFrame, 0, subFrame.length);
                this.frameList[f] = subFrame2List(subFrame, this.PKG_SIZE);
            }
        }
    }

    private ArrayList<byte[]> subFrame2List(byte[] srcArr, int pkgSize) {
        byte[] subPayload;
        Object[] objArr = {srcArr, new Integer(pkgSize)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 7073, new Class[]{byte[].class, Integer.TYPE}, ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        ArrayList<byte[]> list = new ArrayList<>();
        int num = srcArr.length / pkgSize;
        if (srcArr.length % pkgSize != 0) {
            num++;
        }
        for (int i = 0; i < num; i++) {
            if (i == num - 1) {
                subPayload = new byte[(srcArr.length - (i * pkgSize))];
            } else {
                subPayload = new byte[pkgSize];
            }
            System.arraycopy(srcArr, i * pkgSize, subPayload, 0, subPayload.length);
            list.add(subPayload);
        }
        return list;
    }

    private void init() {
        this.FRAME_INDEX = 0;
        this.SUB_PKG_INDEX = 0;
        this.txFrameNum = 0;
    }

    private void disallowSendNext() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7074, new Class[0], Void.TYPE).isSupported) {
            this.allowSendNext = false;
            this.handler.removeCallbacks(this.sendPacketTask);
        }
    }

    private void allowSendNext(String bzRef) {
        if (!PatchProxy.proxy(new Object[]{bzRef}, this, changeQuickRedirect, false, 7075, new Class[]{String.class}, Void.TYPE).isSupported) {
            log("OTA分包===》allowSendNext.bzRef=" + bzRef);
            this.allowSendNext = true;
            this.handler.post(this.sendPacketTask);
        }
    }

    public String parseErrorCode(int code) {
        switch (code) {
            case 1:
                return "总包长度错误";
            case 2:
                return "CRC校验失败";
            case 3:
                return "设备不允许OTA";
            case 4:
                return "丢包";
            default:
                return "未知原因";
        }
    }

    public boolean isOta() {
        return this.isWorking;
    }

    public final class SendPacketTask implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private SendPacketTask() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7100, new Class[0], Void.TYPE).isSupported) {
                if (!TRVOta.this._foreToEndOTA) {
                    if (TRVOta.this.allowSendNext && !TRVOta.this.flagIsOtaDataTransformedToDevice) {
                        TRVOta.access$1100(TRVOta.this);
                    }
                    if (TRVOta.this.FRAME_INDEX == TRVOta.this.frameList.length) {
                        TRVOta tRVOta = TRVOta.this;
                        TRVOta.access$500(tRVOta, "分包出现异常---> Frame_INDEX=" + TRVOta.this.FRAME_INDEX + "   frameList=" + TRVOta.this.frameList.length);
                        return;
                    }
                    ArrayList<byte[]> list = TRVOta.this.frameList[TRVOta.this.FRAME_INDEX];
                    if (TRVOta.this.FRAME_INDEX == TRVOta.this.frameList.length - 1 && TRVOta.this.SUB_PKG_INDEX == list.size() - 1) {
                        TRVOta.access$500(TRVOta.this, "OTA分包====> 正在投送最后一组的，最后一个角标数据,请不要那着急重试.....");
                        TRVOta.this.handler.removeCallbacks(this);
                        TRVOta.access$1600(TRVOta.this);
                        return;
                    }
                    TRVOta.access$1408(TRVOta.this);
                    TRVOta.this.handler.postDelayed(this, 1);
                }
            }
        }
    }

    public final class FrameFinishTimeoutTask implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private FrameFinishTimeoutTask() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7098, new Class[0], Void.TYPE).isSupported) {
                TRVOta tRVOta = TRVOta.this;
                TRVOta.access$500(tRVOta, "帧最后一个子包发完，等待帧响应超时 FRAME_INDEX:" + TRVOta.this.FRAME_INDEX);
                TRVOta.access$1600(TRVOta.this);
                int unused = TRVOta.this.SUB_PKG_INDEX = TRVOta.this.frameList[TRVOta.this.FRAME_INDEX].size() + -1;
                TRVOta.access$1700(TRVOta.this, "FrameFinishTimeoutTask 超时处理");
            }
        }
    }

    public final class PkgLossDelayTask implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private PkgLossDelayTask() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7099, new Class[0], Void.TYPE).isSupported) {
                TRVOta tRVOta = TRVOta.this;
                TRVOta.access$1800(tRVOta, tRVOta.lastTotalSub, tRVOta.lastCorrectIndex - 1);
            }
        }
    }

    public void onBleDisconnect(String desc) {
        this._bleConnectState = false;
        this._bleDisconnectDesc = desc;
    }
}
