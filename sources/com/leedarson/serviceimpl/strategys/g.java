package com.leedarson.serviceimpl.strategys;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import meshsdk.MeshLog;
import meshsdk.model.MeshInfo;
import meshsdk.model.json.MeshStorage;

/* compiled from: ProvisionHighAddrCompat */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int a = 1023;

    public boolean a(List<MeshStorage.Provisioner> provisionerList) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{provisionerList}, this, changeQuickRedirect, false, 8789, new Class[]{List.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        for (MeshStorage.Provisioner provisioner : provisionerList) {
            if (b(provisioner)[1] + 255 > 32767) {
                MeshLog.d("provision 存在最大值 即将大于0X7FFF");
                return true;
            }
        }
        return false;
    }

    public boolean c(MeshStorage.Provisioner provisioner) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{provisioner}, this, changeQuickRedirect, false, 8790, new Class[]{MeshStorage.Provisioner.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int[] range = b(provisioner);
        if (range[1] - range[0] > 320 || range[1] - range[0] < 2) {
            return true;
        }
        return false;
    }

    private int[] b(MeshStorage.Provisioner provisioner) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{provisioner}, this, changeQuickRedirect, false, 8791, new Class[]{MeshStorage.Provisioner.class}, int[].class);
        if (proxy.isSupported) {
            return (int[]) proxy.result;
        }
        int low = 0;
        int high = 0;
        List<MeshStorage.Provisioner.AddressRange> allocatedUnicastRange = provisioner.allocatedUnicastRange;
        if (allocatedUnicastRange != null && allocatedUnicastRange.size() > 0) {
            MeshStorage.Provisioner.AddressRange range = allocatedUnicastRange.get(0);
            String str = range.lowAddress;
            ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
            low = MeshUtils.k(str, byteOrder);
            high = MeshUtils.k(range.highAddress, byteOrder);
        }
        return new int[]{low, high};
    }

    public void d(int low, int high, String UUID, MeshInfo meshInfo) {
        Object[] objArr = {new Integer(low), new Integer(high), UUID, meshInfo};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8792, new Class[]{cls, cls, String.class, MeshInfo.class}, Void.TYPE).isSupported) {
            MeshStorage.Provisioner provisioner = new MeshStorage.Provisioner();
            provisioner.UUID = UUID;
            ArrayList arrayList = new ArrayList();
            provisioner.allocatedUnicastRange = arrayList;
            Locale locale = Locale.US;
            arrayList.add(new MeshStorage.Provisioner.AddressRange(String.format(locale, "%04X", new Object[]{Integer.valueOf(low)}), String.format(locale, "%04X", new Object[]{Integer.valueOf(high)})));
            ArrayList arrayList2 = new ArrayList();
            provisioner.allocatedGroupRange = arrayList2;
            arrayList2.add(new MeshStorage.Provisioner.AddressRange("C000", "C0FF"));
            ArrayList arrayList3 = new ArrayList();
            provisioner.allocatedSceneRange = arrayList3;
            arrayList3.add(new MeshStorage.Provisioner.SceneRange(String.format(locale, "%04X", new Object[]{1}), String.format(locale, "%04X", new Object[]{15})));
            meshInfo.provisionerList.add(provisioner);
        }
    }
}
