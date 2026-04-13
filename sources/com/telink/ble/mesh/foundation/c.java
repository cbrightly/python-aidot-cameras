package com.telink.ble.mesh.foundation;

import com.leedarson.base.beans.a;
import com.telink.ble.mesh.entity.ProvisioningDevice;
import com.telink.ble.mesh.foundation.MeshController;
import io.reactivex.functions.e;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: lambda */
public final /* synthetic */ class c implements e {
    public final /* synthetic */ MeshController.AnonymousClass7 c;
    public final /* synthetic */ ProvisioningDevice d;
    public final /* synthetic */ AtomicBoolean f;

    public /* synthetic */ c(MeshController.AnonymousClass7 r1, ProvisioningDevice provisioningDevice, AtomicBoolean atomicBoolean) {
        this.c = r1;
        this.d = provisioningDevice;
        this.f = atomicBoolean;
    }

    public final void accept(Object obj) {
        this.c.b(this.d, this.f, (a) obj);
    }
}
