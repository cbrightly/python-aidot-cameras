package zendesk.storage.android.internal;

import android.content.SharedPreferences;
import kotlin.jvm.functions.l;
import kotlin.x;

/* compiled from: BasicStorage.kt */
public final class b {
    /* access modifiers changed from: private */
    public static final void b(SharedPreferences $this$edit, l<? super SharedPreferences.Editor, x> block) {
        SharedPreferences.Editor edit = $this$edit.edit();
        block.invoke(edit);
        edit.apply();
    }
}
