package com.telink.ble.mesh.core.provisioning;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ProvisioningParams {
    private static final int DATA_PDU_LEN = 25;
    public static ChangeQuickRedirect changeQuickRedirect;
    private int ivIndex;
    private byte ivUpdateFlag;
    private byte keyRefreshFlag;
    private byte[] networkKey;
    private int networkKeyIndex;
    private int unicastAddress;

    public static ProvisioningParams getDefault(byte[] networkKey2, int networkKeyIndex2, int ivIndex2, int unicastAddress2) {
        Object[] objArr = {networkKey2, new Integer(networkKeyIndex2), new Integer(ivIndex2), new Integer(unicastAddress2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12965, new Class[]{byte[].class, cls, cls, cls}, ProvisioningParams.class);
        if (proxy.isSupported) {
            return (ProvisioningParams) proxy.result;
        }
        ProvisioningParams params = new ProvisioningParams();
        params.networkKey = networkKey2;
        params.networkKeyIndex = networkKeyIndex2;
        params.keyRefreshFlag = 0;
        params.ivUpdateFlag = 0;
        params.ivIndex = ivIndex2;
        params.unicastAddress = unicastAddress2;
        return params;
    }

    public static ProvisioningParams getSimple(byte[] networkKey2, int unicastAddress2) {
        Object[] objArr = {networkKey2, new Integer(unicastAddress2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 12966, new Class[]{byte[].class, Integer.TYPE}, ProvisioningParams.class);
        if (proxy.isSupported) {
            return (ProvisioningParams) proxy.result;
        }
        ProvisioningParams params = new ProvisioningParams();
        params.networkKey = networkKey2;
        params.networkKeyIndex = 0;
        params.keyRefreshFlag = 0;
        params.ivUpdateFlag = 0;
        params.ivIndex = 0;
        params.unicastAddress = unicastAddress2;
        return params;
    }

    public byte[] toProvisioningData() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12967, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer buffer = ByteBuffer.allocate(25).order(ByteOrder.BIG_ENDIAN);
        buffer.put(this.networkKey).putShort((short) this.networkKeyIndex).put((byte) ((this.keyRefreshFlag & 1) | (this.ivUpdateFlag & 2))).putInt(this.ivIndex).putShort((short) this.unicastAddress);
        return buffer.array();
    }

    public byte[] getNetworkKey() {
        return this.networkKey;
    }

    public void setNetworkKey(byte[] networkKey2) {
        this.networkKey = networkKey2;
    }

    public int getNetworkKeyIndex() {
        return this.networkKeyIndex;
    }

    public void setNetworkKeyIndex(int networkKeyIndex2) {
        this.networkKeyIndex = networkKeyIndex2;
    }

    public byte getKeyRefreshFlag() {
        return this.keyRefreshFlag;
    }

    public void setKeyRefreshFlag(byte keyRefreshFlag2) {
        this.keyRefreshFlag = keyRefreshFlag2;
    }

    public byte getIvUpdateFlag() {
        return this.ivUpdateFlag;
    }

    public void setIvUpdateFlag(byte ivUpdateFlag2) {
        this.ivUpdateFlag = ivUpdateFlag2;
    }

    public int getIvIndex() {
        return this.ivIndex;
    }

    public void setIvIndex(int ivIndex2) {
        this.ivIndex = ivIndex2;
    }

    public int getUnicastAddress() {
        return this.unicastAddress;
    }

    public void setUnicastAddress(int unicastAddress2) {
        this.unicastAddress = unicastAddress2;
    }
}
