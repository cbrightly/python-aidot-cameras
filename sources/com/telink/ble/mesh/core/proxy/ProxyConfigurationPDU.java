package com.telink.ble.mesh.core.proxy;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.networking.NetworkLayerPDU;
import com.telink.ble.mesh.core.networking.NonceGenerator;
import java.nio.ByteOrder;

public class ProxyConfigurationPDU extends NetworkLayerPDU {
    public static ChangeQuickRedirect changeQuickRedirect;

    public ProxyConfigurationPDU(NetworkLayerPDU.NetworkEncryptionSuite encryptionSuite) {
        super(encryptionSuite);
    }

    public byte[] f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12979, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return NonceGenerator.c(MeshUtils.l(l(), 3, ByteOrder.BIG_ENDIAN), m(), this.k.a);
    }
}
