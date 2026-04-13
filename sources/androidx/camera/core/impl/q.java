package androidx.camera.core.impl;

import androidx.camera.core.impl.Config;
import java.util.Comparator;

/* compiled from: lambda */
public final /* synthetic */ class q implements Comparator {
    public static final /* synthetic */ q c = new q();

    private /* synthetic */ q() {
    }

    public final int compare(Object obj, Object obj2) {
        return ((Config.Option) obj).getId().compareTo(((Config.Option) obj2).getId());
    }
}
