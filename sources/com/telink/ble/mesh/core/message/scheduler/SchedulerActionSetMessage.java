package com.telink.ble.mesh.core.message.scheduler;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;
import com.telink.ble.mesh.entity.Scheduler;

public class SchedulerActionSetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Scheduler t;
    private boolean u = false;

    public static SchedulerActionSetMessage I(int address, int appKeyIndex, Scheduler scheduler, boolean ack, int rspMax) {
        Object[] objArr = {new Integer(address), new Integer(appKeyIndex), scheduler, new Byte(ack ? (byte) 1 : 0), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12778, new Class[]{cls, cls, Scheduler.class, Boolean.TYPE, cls}, SchedulerActionSetMessage.class);
        if (proxy.isSupported) {
            return (SchedulerActionSetMessage) proxy.result;
        }
        SchedulerActionSetMessage message = new SchedulerActionSetMessage(address, appKeyIndex);
        message.t = scheduler;
        message.u = ack;
        message.C(rspMax);
        return message;
    }

    public SchedulerActionSetMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    public int o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12779, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return this.u ? Opcode.SCHD_ACTION_STATUS.value : super.o();
    }

    public int k() {
        return (this.u ? Opcode.SCHD_ACTION_SET : Opcode.SCHD_ACTION_SET_NOACK).value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12780, new Class[0], byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : this.t.toBytes();
    }
}
