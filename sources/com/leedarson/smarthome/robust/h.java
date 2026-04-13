package com.leedarson.smarthome.robust;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.downloader.c;
import com.downloader.g;
import com.downloader.j;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.w;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.smarthome.robust.beans.ELKRobustStepRecordBean;
import com.leedarson.smarthome.robust.beans.PatchResultConfigBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.Patch;
import com.meituan.robust.PatchExecutor;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.meituan.robust.RobustCallBack;
import java.io.File;
import java.util.List;
import meshsdk.BaseResp;
import timber.log.a;

/* compiled from: RobustPatchManager */
public class h {
    public static String a = "SP_KEY_PATCH_INDEX";
    public static String b = "SP_KEY_PATCH_STATUES";
    public static ChangeQuickRedirect changeQuickRedirect;
    PatchExecutor c;
    e d = new e();
    io.reactivex.disposables.b e;
    /* access modifiers changed from: private */
    public String f = "patchProcess";
    com.leedarson.smarthome.robust.reporter.a g = null;
    com.leedarson.smarthome.robust.reporter.b h;
    Gson i = new Gson();

    static /* synthetic */ String a(h x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 10706, new Class[]{h.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : x0.i();
    }

    static /* synthetic */ void c(h x0, Context x1) {
        Class[] clsArr = {h.class, Context.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10707, clsArr, Void.TYPE).isSupported) {
            x0.e(x1);
        }
    }

    public void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10695, new Class[0], Void.TYPE).isSupported) {
            g("robustConfig ---> start");
            com.leedarson.smarthome.robust.reporter.a aVar = new com.leedarson.smarthome.robust.reporter.a();
            this.g = aVar;
            this.h = aVar.a();
            a = "SP_KEY_PATCH_INDEX_" + w.H(BaseApplication.b()) + "_" + "2979";
            b = "SP_KEY_PATCH_STATUES_" + w.H(BaseApplication.b()) + "_" + "2979";
            String index = SharePreferenceUtils.getPrefString(BaseApplication.b(), a, "");
            String status = SharePreferenceUtils.getPrefString(BaseApplication.b(), b, "0");
            io.reactivex.disposables.b bVar = this.e;
            if (bVar != null && !bVar.isDisposed()) {
                this.e.dispose();
            }
            ELKRobustStepRecordBean stepOfFetchApi = new ELKRobustStepRecordBean();
            stepOfFetchApi.startRequest(" index=" + index + " status=" + status, "fetchApi");
            this.e = this.d.b(index, status).c(l.c()).I(new b(this, stepOfFetchApi), new d(this, stepOfFetchApi));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public /* synthetic */ void l(ELKRobustStepRecordBean stepOfFetchApi, PatchResultConfigBean patchResultConfigBean) {
        Class[] clsArr = {ELKRobustStepRecordBean.class, PatchResultConfigBean.class};
        if (!PatchProxy.proxy(new Object[]{stepOfFetchApi, patchResultConfigBean}, this, changeQuickRedirect, false, 10705, clsArr, Void.TYPE).isSupported) {
            g("robustConfig --->success");
            if (!TextUtils.isEmpty(patchResultConfigBean.id)) {
                Log.e(this.f, " 检测到有配置文件 ");
                stepOfFetchApi.endRequest(this.i.toJson((Object) patchResultConfigBean));
                this.h.a(stepOfFetchApi);
                SharePreferenceUtils.setPrefString(BaseApplication.b(), b, "0");
                f();
                d(patchResultConfigBean);
                return;
            }
            stepOfFetchApi.endRequest(this.i.toJson((Object) patchResultConfigBean));
            this.h.a(stepOfFetchApi);
            p();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: m */
    public /* synthetic */ void n(ELKRobustStepRecordBean stepOfFetchApi, Throwable throwable) {
        Class[] clsArr = {ELKRobustStepRecordBean.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{stepOfFetchApi, throwable}, this, changeQuickRedirect, false, 10704, clsArr, Void.TYPE).isSupported) {
            g("robustConfig： 检测配置文件失败" + throwable.toString());
            stepOfFetchApi.endRequestException("获取失败,尝试本地文件", BaseResp.ERR_MSG_SEND_FAIL);
            this.h.a(stepOfFetchApi);
            p();
        }
    }

    private void g(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10696, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g(this.f);
            g2.c("process-->" + msg, new Object[0]);
        }
    }

    private void p() {
        File[] listFile;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10697, new Class[0], Void.TYPE).isSupported) {
            File localPatchFile = new File(i() + File.separator);
            ELKRobustStepRecordBean stepOfAppLocal = new ELKRobustStepRecordBean();
            stepOfAppLocal.startRequest("try local start", "tryLocal");
            if (localPatchFile.exists() && (listFile = localPatchFile.listFiles()) != null && listFile.length != 0) {
                boolean flagFoundMatchPatch = false;
                int i2 = 0;
                while (true) {
                    if (i2 >= listFile.length) {
                        break;
                    }
                    String name = listFile[i2].getName();
                    if (name.contains(w.H(BaseApplication.b()) + "_" + "2979")) {
                        flagFoundMatchPatch = true;
                        break;
                    }
                    i2++;
                }
                if (flagFoundMatchPatch) {
                    stepOfAppLocal.endRequest("找到了本地匹配数据信息");
                    this.h.a(stepOfAppLocal);
                    e(BaseApplication.b());
                    return;
                }
                this.h.a(stepOfAppLocal);
                stepOfAppLocal.endRequest("并没有找到匹配的数据信息");
                this.h.b("任务结束");
            }
        }
    }

    private void d(PatchResultConfigBean data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 10698, new Class[]{PatchResultConfigBean.class}, Void.TYPE).isSupported) {
            String localFileName = h(data);
            String patchRemoteJarPath = data.image;
            String patchesFolderPath = i();
            File patchesFolder = new File(patchesFolderPath);
            if (!patchesFolder.exists()) {
                patchesFolder.mkdir();
            }
            ELKRobustStepRecordBean stepOfDownloadRes = new ELKRobustStepRecordBean();
            stepOfDownloadRes.startRequest(this.i.toJson((Object) data), "startDownload");
            g.c(patchRemoteJarPath, patchesFolderPath, localFileName + ".jar").a().F(c.a).L(new a(localFileName, data, stepOfDownloadRes));
        }
    }

    /* compiled from: RobustPatchManager */
    public class a implements c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ PatchResultConfigBean b;
        final /* synthetic */ ELKRobustStepRecordBean c;

        a(String str, PatchResultConfigBean patchResultConfigBean, ELKRobustStepRecordBean eLKRobustStepRecordBean) {
            this.a = str;
            this.b = patchResultConfigBean;
            this.c = eLKRobustStepRecordBean;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10708, new Class[0], Void.TYPE).isSupported) {
                Log.i("robust", "robust download complete");
                File newPatchFile = new File(h.a(h.this) + File.separator + this.a + ".jar");
                if (!e.d(newPatchFile).equals(this.b.check)) {
                    try {
                        Log.i("robust", "robust download complete 文件签名不同：目标文件：" + this.b.check + "  下载后本地计算文件:" + e.d(newPatchFile));
                        newPatchFile.delete();
                    } catch (Exception e) {
                        String b2 = h.this.f;
                        Log.e(b2, " robust download complete 文件md5值异常：" + e.toString());
                    }
                    this.c.endRequestException("资源匹配不完整", BaseResp.ERR_MSG_SEND_FAIL);
                    h.this.h.a(this.c);
                    h.this.h.b("失败了");
                    return;
                }
                String b3 = h.this.f;
                Log.i(b3, " robust download complete 准备进行patch：index=" + this.b.index + "   data.img=" + this.b.image + " md5=" + this.b.check);
                this.c.endRequest("资源下载完整");
                h.this.h.a(this.c);
                SharePreferenceUtils.setPrefString(BaseApplication.b(), h.a, this.b.index);
                SharePreferenceUtils.setPrefString(BaseApplication.b(), h.b, "0");
                h.c(h.this, BaseApplication.b());
            }
        }

        public void b(com.downloader.a error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 10709, new Class[]{com.downloader.a.class}, Void.TYPE).isSupported) {
                Log.i("robust", "robust download error=" + error.toString());
                ELKRobustStepRecordBean eLKRobustStepRecordBean = this.c;
                eLKRobustStepRecordBean.endRequestException("资源下载失败: error=" + error.toString(), BaseResp.ERR_MSG_SEND_FAIL);
                h.this.h.a(this.c);
                h.this.h.b("下载失败");
            }
        }
    }

    static /* synthetic */ void j(j progress) {
        if (!PatchProxy.proxy(new Object[]{progress}, (Object) null, changeQuickRedirect, true, 10703, new Class[]{j.class}, Void.TYPE).isSupported) {
            Log.i("robust", "robust download process" + (((float) (progress.currentBytes / progress.totalBytes)) * 1.0f));
        }
    }

    private void f() {
        File[] allPatchFiles;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10699, new Class[0], Void.TYPE).isSupported) {
            File robustPatchFolder = new File(i());
            if (robustPatchFolder.exists() && (allPatchFiles = robustPatchFolder.listFiles()) != null && allPatchFiles.length > 0) {
                int i2 = 0;
                while (i2 < allPatchFiles.length) {
                    try {
                        allPatchFiles[i2].delete();
                        i2++;
                    } catch (Exception e2) {
                        String str = this.f;
                        Log.e(str, "deleteLocalPatch 失败：" + e2.toString());
                        return;
                    }
                }
            }
        }
    }

    private String i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10700, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return BaseApplication.b().getFilesDir() + File.separator + "robust_patches";
    }

    private String h(PatchResultConfigBean data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 10701, new Class[]{PatchResultConfigBean.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return w.H(BaseApplication.b()) + "_" + "2979" + "_" + data.index + "_" + data.check;
    }

    private void e(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 10702, new Class[]{Context.class}, Void.TYPE).isSupported) {
            Log.i("robust", "robust download complete  test start");
            ELKRobustStepRecordBean stepOfMerge = new ELKRobustStepRecordBean();
            stepOfMerge.startRequest("we start to merge ", "mergeProcess");
            PatchExecutor patchExecutor = new PatchExecutor(context, new f(), new g(new b(stepOfMerge)));
            this.c = patchExecutor;
            patchExecutor.start();
        }
    }

    /* compiled from: RobustPatchManager */
    public class b implements RobustCallBack {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ELKRobustStepRecordBean a;

        b(ELKRobustStepRecordBean eLKRobustStepRecordBean) {
            this.a = eLKRobustStepRecordBean;
        }

        public void onPatchListFetched(boolean result, boolean isNet, List<Patch> list) {
        }

        public void onPatchFetched(boolean result, boolean isNet, Patch patch) {
        }

        public void onPatchApplied(boolean result, Patch patch) {
            if (!PatchProxy.proxy(new Object[]{new Byte(result ? (byte) 1 : 0), patch}, this, changeQuickRedirect, false, 10710, new Class[]{Boolean.TYPE, Patch.class}, Void.TYPE).isSupported) {
                try {
                    SharePreferenceUtils.setPrefString(BaseApplication.b(), h.a, patch.getName().split("_")[2]);
                    SharePreferenceUtils.setPrefString(BaseApplication.b(), h.b, result ? "1" : ExifInterface.GPS_MEASUREMENT_2D);
                    if (result) {
                        this.a.endRequest("yes we merge success");
                        h.this.h.a(this.a);
                        h.this.h.b("merge success");
                        return;
                    }
                    this.a.endRequestException("Oh,no ,it seems some error", BaseResp.ERR_MSG_SEND_FAIL);
                    h.this.h.a(this.a);
                    h.this.h.b("merge fail");
                } catch (Exception e) {
                    String b2 = h.this.f;
                    Log.e(b2, "onPatchApplied失败：e=" + e.toString());
                    ELKRobustStepRecordBean eLKRobustStepRecordBean = this.a;
                    eLKRobustStepRecordBean.endRequestException("Oh,no ,it seems some error=" + e.toString(), BaseResp.ERR_MSG_SEND_FAIL);
                    h.this.h.a(this.a);
                    h.this.h.b("merge fail");
                }
            }
        }

        public void logNotify(String log, String where) {
        }

        public void exceptionNotify(Throwable throwable, String where) {
            Class[] clsArr = {Throwable.class, String.class};
            if (!PatchProxy.proxy(new Object[]{throwable, where}, this, changeQuickRedirect, false, 10711, clsArr, Void.TYPE).isSupported) {
                ELKRobustStepRecordBean eLKRobustStepRecordBean = this.a;
                eLKRobustStepRecordBean.endRequestException("merge process occur problems thorwable=" + throwable.toString() + "   where=" + where, BaseResp.ERR_MSG_SEND_FAIL);
                h.this.h.a(this.a);
                h.this.h.b("merge fail");
            }
        }
    }
}
