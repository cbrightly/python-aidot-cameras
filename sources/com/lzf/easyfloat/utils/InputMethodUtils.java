package com.lzf.easyfloat.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.lzf.easyfloat.core.FloatingWindowHelper;
import com.lzf.easyfloat.core.FloatingWindowManager;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: InputMethodUtils.kt */
public final class InputMethodUtils {
    @NotNull
    public static final InputMethodUtils INSTANCE = new InputMethodUtils();

    @Nullable
    public static final x closedInputMethod() {
        return closedInputMethod$default((String) null, 1, (Object) null);
    }

    public static final void openInputMethod(@NotNull EditText editText) {
        k.e(editText, "editText");
        openInputMethod$default(editText, (String) null, 2, (Object) null);
    }

    private InputMethodUtils() {
    }

    public static /* synthetic */ void initInputMethod$easyfloat_release$default(InputMethodUtils inputMethodUtils, EditText editText, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        inputMethodUtils.initInputMethod$easyfloat_release(editText, str);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public final void initInputMethod$easyfloat_release(@NotNull EditText editText, @Nullable String tag) {
        k.e(editText, "editText");
        editText.setOnTouchListener(new b(editText, tag));
    }

    /* access modifiers changed from: private */
    /* renamed from: initInputMethod$lambda-0  reason: not valid java name */
    public static final boolean m14initInputMethod$lambda0(EditText $editText, String $tag, View $noName_0, MotionEvent event) {
        k.e($editText, "$editText");
        if (event.getAction() != 0) {
            return false;
        }
        openInputMethod($editText, $tag);
        return false;
    }

    public static /* synthetic */ void openInputMethod$default(EditText editText, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        openInputMethod(editText, str);
    }

    public static final void openInputMethod(@NotNull EditText editText, @Nullable String tag) {
        k.e(editText, "editText");
        FloatingWindowHelper $this$openInputMethod_u24lambda_u2d1 = FloatingWindowManager.INSTANCE.getHelper(tag);
        if ($this$openInputMethod_u24lambda_u2d1 != null) {
            $this$openInputMethod_u24lambda_u2d1.getParams().flags = 32;
            $this$openInputMethod_u24lambda_u2d1.getWindowManager().updateViewLayout($this$openInputMethod_u24lambda_u2d1.getFrameLayout(), $this$openInputMethod_u24lambda_u2d1.getParams());
        }
        new Handler(Looper.getMainLooper()).postDelayed(new a(editText), 100);
    }

    /* access modifiers changed from: private */
    /* renamed from: openInputMethod$lambda-2  reason: not valid java name */
    public static final void m15openInputMethod$lambda2(EditText $editText) {
        k.e($editText, "$editText");
        InputMethodManager inputManager = (InputMethodManager) $editText.getContext().getSystemService("input_method");
        if (inputManager != null) {
            inputManager.showSoftInput($editText, 0);
        }
    }

    public static /* synthetic */ x closedInputMethod$default(String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return closedInputMethod(str);
    }

    @Nullable
    public static final x closedInputMethod(@Nullable String tag) {
        FloatingWindowHelper $this$closedInputMethod_u24lambda_u2d3 = FloatingWindowManager.INSTANCE.getHelper(tag);
        if ($this$closedInputMethod_u24lambda_u2d3 == null) {
            return null;
        }
        $this$closedInputMethod_u24lambda_u2d3.getParams().flags = 40;
        $this$closedInputMethod_u24lambda_u2d3.getWindowManager().updateViewLayout($this$closedInputMethod_u24lambda_u2d3.getFrameLayout(), $this$closedInputMethod_u24lambda_u2d3.getParams());
        return x.a;
    }
}
