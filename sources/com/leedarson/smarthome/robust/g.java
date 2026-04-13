package com.leedarson.smarthome.robust;

import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.Patch;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.RobustCallBack;
import java.util.List;

/* compiled from: RobustCallBackSample */
public class g implements RobustCallBack {
    public static ChangeQuickRedirect changeQuickRedirect;
    private RobustCallBack a;

    public g(RobustCallBack callBack) {
        this.a = callBack;
    }

    public void onPatchListFetched(boolean result, boolean isNet, List<Patch> patches) {
        Object[] objArr = {new Byte(result ? (byte) 1 : 0), new Byte(isNet ? (byte) 1 : 0), patches};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10690, new Class[]{cls, cls, List.class}, Void.TYPE).isSupported) {
            Log.i("robust", "onPatchListFetched result: " + result);
            Log.i("robust", "onPatchListFetched isNet: " + isNet);
            for (Patch patch : patches) {
                Log.d("RobustCallBack", "onPatchListFetched patch: " + patch.getName());
            }
        }
    }

    public void onPatchFetched(boolean result, boolean isNet, Patch patch) {
        Object[] objArr = {new Byte(result ? (byte) 1 : 0), new Byte(isNet ? (byte) 1 : 0), patch};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10691, new Class[]{cls, cls, Patch.class}, Void.TYPE).isSupported) {
            Log.i("robust", "onPatchFetched result: " + result);
            Log.i("robust", "onPatchFetched isNet: " + isNet);
            Log.i("robust", "onPatchFetched patch: " + patch.getName());
        }
    }

    public void onPatchApplied(boolean result, Patch patch) {
        Object[] objArr = {new Byte(result ? (byte) 1 : 0), patch};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10692, new Class[]{Boolean.TYPE, Patch.class}, Void.TYPE).isSupported) {
            Log.i("robust", "onPatchApplied result: " + result);
            Log.i("robust", "onPatchApplied patch: " + patch.getName());
            this.a.onPatchApplied(result, patch);
        }
    }

    public void logNotify(String log, String where) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{log, where}, this, changeQuickRedirect, false, 10693, clsArr, Void.TYPE).isSupported) {
            Log.i("robust", "logNotify log: " + log);
            Log.i("robust", "logNotify where: " + where);
        }
    }

    public void exceptionNotify(Throwable throwable, String where) {
        Class[] clsArr = {Throwable.class, String.class};
        if (!PatchProxy.proxy(new Object[]{throwable, where}, this, changeQuickRedirect, false, 10694, clsArr, Void.TYPE).isSupported) {
            Log.i("robust", "exceptionNotify where: " + where, throwable);
            this.a.exceptionNotify(throwable, where);
        }
    }
}
