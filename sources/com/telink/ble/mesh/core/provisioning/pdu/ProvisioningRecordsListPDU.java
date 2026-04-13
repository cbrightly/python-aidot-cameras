package com.telink.ble.mesh.core.provisioning.pdu;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class ProvisioningRecordsListPDU implements ProvisioningStatePDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    public byte[] a;
    public int b;
    public List<Integer> c;

    public static ProvisioningRecordsListPDU b(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, (Object) null, changeQuickRedirect, true, 12976, new Class[]{byte[].class}, ProvisioningRecordsListPDU.class);
        if (proxy.isSupported) {
            return (ProvisioningRecordsListPDU) proxy.result;
        }
        ProvisioningRecordsListPDU recordsListPDU = new ProvisioningRecordsListPDU();
        recordsListPDU.a = data;
        recordsListPDU.b = MeshUtils.b(data, 0, 2, ByteOrder.BIG_ENDIAN);
        int index = 0 + 2;
        if (data.length > index) {
            int listSize = (data.length - index) / 2;
            recordsListPDU.c = new ArrayList(listSize);
            for (int i = 0; i < listSize; i++) {
                recordsListPDU.c.add(Integer.valueOf(MeshUtils.b(data, index, 2, ByteOrder.BIG_ENDIAN)));
                index += 2;
            }
        }
        return recordsListPDU;
    }

    public byte getState() {
        return 13;
    }

    public byte[] a() {
        return this.a;
    }
}
