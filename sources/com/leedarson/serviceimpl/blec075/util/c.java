package com.leedarson.serviceimpl.blec075.util;

import android.text.TextUtils;
import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Locale;
import meshsdk.ctrl.CmdCtrl;
import timber.log.a;

/* compiled from: BleCheckDeviceIsBoundedStatue */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;

    public boolean d(String adHexStr, String devMac) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{adHexStr, devMac}, this, changeQuickRedirect, false, 6594, new Class[]{cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!TextUtils.isEmpty(adHexStr) && adHexStr.contains("1115")) {
            byte[] adBytes = e.f(adHexStr);
            if (adBytes.length > 8) {
                String frameControlHightStr = b(adBytes[7]);
                return "1".equals(frameControlHightStr.substring(frameControlHightStr.length() - 1, frameControlHightStr.length()));
            }
        } else if (!TextUtils.isEmpty(adHexStr) && adHexStr.contains("F6FF")) {
            return a(adHexStr, devMac);
        } else {
            if (!TextUtils.isEmpty(adHexStr) && adHexStr.contains("1168")) {
                a.g("ldsConnectDevice").m("1168...", new Object[0]);
                return a(adHexStr, devMac);
            } else if (adHexStr.length() > 48) {
                String adv = adHexStr.substring(10);
                if (adv.contains(devMac.toUpperCase()) && adv.length() > 48 && adv.substring(32, 44).equals(devMac.toUpperCase())) {
                    String displayFlag = adv.substring(46, 48);
                    if ("00".equals(displayFlag)) {
                        a.g("ldsConnectDevice").m("旧广播包解析，未配网", new Object[0]);
                        return false;
                    }
                    a.b g = a.g("ldsConnectDevice");
                    g.m("旧广播包解析，displayflag:" + displayFlag, new Object[0]);
                    return true;
                }
            }
        }
        return true;
    }

    public boolean c(String adHexStr, String devMac) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{adHexStr, devMac}, this, changeQuickRedirect, false, 6595, new Class[]{cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (TextUtils.isEmpty(adHexStr) || !adHexStr.contains("1115")) {
            return false;
        }
        String tempResult = adHexStr.split(devMac.toUpperCase(Locale.US))[1];
        String frameControlHightStr = b(e.f("" + tempResult.toCharArray()[6] + tempResult.toCharArray()[7])[0]);
        return "1".equals("" + frameControlHightStr.toCharArray()[5]);
    }

    private boolean a(String adHexStr, String devMac) {
        Class<String> cls = String.class;
        boolean z = true;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{adHexStr, devMac}, this, changeQuickRedirect, false, 6596, new Class[]{cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String tempResult = adHexStr.split(devMac.toUpperCase(Locale.US))[1];
        String boundStatueHex = "" + tempResult.toCharArray()[6] + tempResult.toCharArray()[7];
        byte[] boundStatueByte = e.f(boundStatueHex);
        int bit0 = CmdCtrl.getValueByBitPosition(boundStatueByte[0], 0, 1);
        int bit2 = CmdCtrl.getValueByBitPosition(boundStatueByte[0], 2, 1);
        boolean result = bit2 == 1 || bit0 == 1;
        if (result) {
            a.b g = a.g("ldsConnectDevice");
            StringBuilder sb = new StringBuilder();
            sb.append("checkBoundedStatue 已入网状态，其中arnoo入网?");
            sb.append(bit0 == 0);
            sb.append(",ble入网?");
            if (bit2 != 1) {
                z = false;
            }
            sb.append(z);
            sb.append(",boundStatueHex:");
            sb.append(boundStatueHex);
            g.m(sb.toString(), new Object[0]);
        } else {
            a.g("ldsConnectDevice").m("checkBoundedStatue 未入网状态,boundStatueHex:" + boundStatueHex, new Object[0]);
        }
        return result;
    }

    public String b(byte by) {
        Object[] objArr = {new Byte(by)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 6597, new Class[]{Byte.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append((by >> 7) & 1);
        sb.append((by >> 6) & 1);
        sb.append((by >> 5) & 1);
        sb.append((by >> 4) & 1);
        sb.append((by >> 3) & 1);
        sb.append((by >> 2) & 1);
        sb.append((by >> 1) & 1);
        sb.append(1 & (by >> 0));
        return sb.toString();
    }
}
