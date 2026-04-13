package zendesk.messaging.android.internal.extension;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.fragment.app.FragmentActivity;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ActivityKtx.kt */
public final class a {
    public static final void a(@NotNull FragmentActivity $this$hideKeyboard) {
        View currentFocus;
        k.e($this$hideKeyboard, "<this>");
        Object systemService = $this$hideKeyboard.getSystemService("input_method");
        InputMethodManager inputMethodManager = systemService instanceof InputMethodManager ? (InputMethodManager) systemService : null;
        if (inputMethodManager != null && (currentFocus = $this$hideKeyboard.getCurrentFocus()) != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }
}
