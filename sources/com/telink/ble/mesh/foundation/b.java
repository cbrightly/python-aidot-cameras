package com.telink.ble.mesh.foundation;

import com.telink.ble.mesh.entity.ProvisioningDevice;
import com.telink.ble.mesh.foundation.MeshController;
import io.reactivex.functions.e;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public final /* synthetic */ MeshController.AnonymousClass7 c;
    public final /* synthetic */ AtomicBoolean d;
    public final /* synthetic */ ProvisioningDevice f;

    public /* synthetic */ b(MeshController.AnonymousClass7 r1, AtomicBoolean atomicBoolean, ProvisioningDevice provisioningDevice) {
        this.c = r1;
        this.d = atomicBoolean;
        this.f = provisioningDevice;
    }

    public final void accept(Object obj) {
        this.c.d(this.d, this.f, (Throwable) obj);
    }
}
