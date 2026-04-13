package androidx.core.content;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: SharedPreferences.kt */
public final class SharedPreferencesKt {
    public static /* synthetic */ void edit$default(SharedPreferences $this$edit_u24default, boolean commit, l action, int i, Object obj) {
        if ((i & 1) != 0) {
            commit = false;
        }
        k.e($this$edit_u24default, "<this>");
        k.e(action, "action");
        SharedPreferences.Editor editor = $this$edit_u24default.edit();
        k.d(editor, "editor");
        action.invoke(editor);
        if (commit) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    @SuppressLint({"ApplySharedPref"})
    public static final void edit(@NotNull SharedPreferences $this$edit, boolean commit, @NotNull l<? super SharedPreferences.Editor, x> action) {
        k.e($this$edit, "<this>");
        k.e(action, "action");
        SharedPreferences.Editor editor = $this$edit.edit();
        k.d(editor, "editor");
        action.invoke(editor);
        if (commit) {
            editor.commit();
        } else {
            editor.apply();
        }
    }
}
