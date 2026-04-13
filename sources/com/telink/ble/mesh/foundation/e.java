package com.telink.ble.mesh.foundation;

import android.bluetooth.BluetoothDevice;
import com.telink.ble.mesh.foundation.MeshController;

/* compiled from: lambda */
public final /* synthetic */ class e implements MeshController.ValidateProxyAdvParseListener {
    public final /* synthetic */ MeshController a;
    public final /* synthetic */ BluetoothDevice b;
    public final /* synthetic */ int c;

    public /* synthetic */ e(MeshController meshController, BluetoothDevice bluetoothDevice, int i) {
        this.a = meshController;
        this.b = bluetoothDevice;
        this.c = i;
    }

    public final void a(int i, int i2) {
        this.a.M0(this.b, this.c, i, i2);
    }
}
