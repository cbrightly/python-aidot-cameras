package androidx.activity;

import android.os.Bundle;
import androidx.savedstate.SavedStateRegistry;

/* compiled from: lambda */
public final /* synthetic */ class b implements SavedStateRegistry.SavedStateProvider {
    public final /* synthetic */ ComponentActivity a;

    public /* synthetic */ b(ComponentActivity componentActivity) {
        this.a = componentActivity;
    }

    public final Bundle saveState() {
        return this.a.I();
    }
}
