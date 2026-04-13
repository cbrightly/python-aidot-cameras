package com.telink.ble.mesh.core.message.firmwareupdate;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;

public class FirmwareUpdateCancelMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static FirmwareUpdateCancelMessage I(int destinationAddress, int appKeyIndex) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 12599, new Class[]{cls, cls}, FirmwareUpdateCancelMessage.class);
        if (proxy.isSupported) {
            return (FirmwareUpdateCancelMessage) proxy.result;
        }
        FirmwareUpdateCancelMessage message = new FirmwareUpdateCancelMessage(destinationAddress, appKeyIndex);
        message.C(1);
        return message;
    }

    public FirmwareUpdateCancelMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    public int k() {
        return Opcode.FIRMWARE_UPDATE_CANCEL.value;
    }

    public int o() {
        return Opcode.FIRMWARE_UPDATE_STATUS.value;
    }
}
