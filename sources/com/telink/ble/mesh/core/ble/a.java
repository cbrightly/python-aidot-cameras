package com.telink.ble.mesh.core.ble;

import com.telink.ble.mesh.core.ble.GattConnection;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ GattConnection c;
    public final /* synthetic */ GattConnection.GattRequestItemWrapItem d;

    public /* synthetic */ a(GattConnection gattConnection, GattConnection.GattRequestItemWrapItem gattRequestItemWrapItem) {
        this.c = gattConnection;
        this.d = gattRequestItemWrapItem;
    }

    public final void run() {
        this.c.R(this.d);
    }
}
