package androidx.activity;

import android.content.Context;
import androidx.activity.contextaware.OnContextAvailableListener;

/* compiled from: lambda */
public final /* synthetic */ class a implements OnContextAvailableListener {
    public final /* synthetic */ ComponentActivity a;

    public /* synthetic */ a(ComponentActivity componentActivity) {
        this.a = componentActivity;
    }

    public final void onContextAvailable(Context context) {
        this.a.J(context);
    }
}
