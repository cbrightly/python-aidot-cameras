package com.lzf.easyfloat.utils;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/* compiled from: lambda */
public final /* synthetic */ class b implements View.OnTouchListener {
    public final /* synthetic */ EditText c;
    public final /* synthetic */ String d;

    public /* synthetic */ b(EditText editText, String str) {
        this.c = editText;
        this.d = str;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return InputMethodUtils.m14initInputMethod$lambda0(this.c, this.d, view, motionEvent);
    }
}
