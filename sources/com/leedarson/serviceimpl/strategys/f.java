package com.leedarson.serviceimpl.strategys;

import android.content.Context;
import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.util.MeshLogger;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.model.json.MeshStorage;
import meshsdk.sql.RandomOffsetBean;
import meshsdk.sql.SqlManager;
import meshsdk.util.SharedPreferenceHelper;

/* compiled from: AddrCreater */
public class f {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a = SIGMesh.getInstance().getContext();
    int b = 0;

    public int b(String mac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 8786, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        this.b = 0;
        int offset = a(mac);
        MeshLog.i("11111-最终得到的偏移量:" + mac + "," + offset);
        return SIGMesh.getInstance().getMeshInfo().getNewNodeAddress(mac) + offset;
    }

    private int a(String mac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 8787, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int offset = new Random().nextInt(40) + 1;
        int temp = SqlManager.queryRandomOffset(SIGMesh.getInstance().getMeshInfo().meshUUID, mac, offset);
        int i = this.b + 1;
        this.b = i;
        if (i >= 40) {
            return temp;
        }
        if (offset != temp) {
            RandomOffsetBean offsetBean = new RandomOffsetBean();
            offsetBean.setMeshUUID(SIGMesh.getInstance().getMeshInfo().meshUUID);
            offsetBean.setMac(mac);
            offsetBean.setOffset(offset);
            SqlManager.getDaoSession().getRandomOffsetBeanDao().insertOrReplace(offsetBean);
            MeshLog.d("保存新节点地址随机偏移量 mac:" + mac + ",offset:" + offset);
            return offset;
        }
        MeshLog.e("新节点地址随机偏移量重复，重新获取 mac:" + mac);
        return a(mac);
    }

    public int c(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 8788, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        String mac = str;
        g provisionHighAddrCompat = new g();
        List<MeshStorage.Provisioner> provisionerList = SIGMesh.getInstance().getMeshInfo().provisionerList;
        int maxRangeHigh = -1;
        for (MeshStorage.Provisioner provisioner : provisionerList) {
            if (!provisionHighAddrCompat.c(provisioner)) {
                for (MeshStorage.Provisioner.AddressRange unRange : provisioner.allocatedUnicastRange) {
                    int tmpHigh = MeshUtils.k(unRange.highAddress, ByteOrder.BIG_ENDIAN);
                    if (maxRangeHigh == -1 || maxRangeHigh < tmpHigh) {
                        maxRangeHigh = tmpHigh;
                    }
                }
            }
        }
        MeshStorage.Provisioner hubProvisioner = new MeshStorage.Provisioner();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        hubProvisioner.provisionerName = "AndroidMeshHub-" + mac + "-" + time;
        int low = (maxRangeHigh + 1 + new Random().nextInt(20)) | 16384;
        StringBuilder sb = new StringBuilder();
        sb.append("mesh 网关，新建短地址 low=");
        sb.append(low);
        MeshLog.debugInfo(sb.toString());
        if (low + 255 > 32767) {
            MeshLogger.a("no available unicast range");
            MeshLog.debugInfo("新建短地址无效");
            return -1;
        }
        hubProvisioner.UUID = e.a(MeshUtils.g(16));
        ArrayList arrayList = new ArrayList();
        hubProvisioner.allocatedUnicastRange = arrayList;
        Locale locale = Locale.US;
        String str2 = mac;
        arrayList.add(new MeshStorage.Provisioner.AddressRange(String.format(locale, "%04X", new Object[]{Integer.valueOf(low)}), String.format(locale, "%04X", new Object[]{Integer.valueOf(low + 319)})));
        ArrayList arrayList2 = new ArrayList();
        hubProvisioner.allocatedGroupRange = arrayList2;
        arrayList2.add(new MeshStorage.Provisioner.AddressRange("C000", "C0FF"));
        ArrayList arrayList3 = new ArrayList();
        hubProvisioner.allocatedSceneRange = arrayList3;
        arrayList3.add(new MeshStorage.Provisioner.SceneRange(String.format(locale, "%04X", new Object[]{1}), String.format(locale, "%04X", new Object[]{15})));
        provisionerList.add(hubProvisioner);
        SIGMesh.getInstance().getMeshInfo().saveOrUpdate(this.a, "新建网关节点地址");
        SharedPreferenceHelper.setNeedUpload(this.a, true);
        return low;
    }
}
