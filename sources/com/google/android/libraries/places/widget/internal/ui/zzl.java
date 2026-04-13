package com.google.android.libraries.places.widget.internal.ui;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.core.content.ContextCompat;
import com.google.android.libraries.places.internal.zzgt;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzl implements View.OnFocusChangeListener {
    private zzl() {
    }

    /* synthetic */ zzl(zzk zzk) {
    }

    public final void onFocusChange(View view, boolean z) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) ContextCompat.getSystemService(view.getContext(), InputMethodManager.class);
            if (inputMethodManager != null) {
                if (z) {
                    inputMethodManager.showSoftInput(view, 1);
                } else {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }
}
