package com.google.android.gms.common.moduleinstall.internal;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zaab extends zag {
    private final ListenerHolder zaa;

    public zaab(ListenerHolder listenerHolder) {
        this.zaa = listenerHolder;
    }

    public final void zab(ModuleInstallStatusUpdate moduleInstallStatusUpdate) {
        this.zaa.notifyListener(new zaaa(this, moduleInstallStatusUpdate));
    }
}
