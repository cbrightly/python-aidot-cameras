package com.chad.library.adapter.base.module;

import com.chad.library.adapter.base.listener.i;
import org.jetbrains.annotations.Nullable;

/* compiled from: UpFetchModule.kt */
public class c {
    private i a;
    private boolean b;
    private boolean c;
    private int d;

    public final void a(int position) {
        i iVar;
        if (this.b && !this.c && position <= this.d && (iVar = this.a) != null) {
            iVar.onUpFetch();
        }
    }

    public void setOnUpFetchListener(@Nullable i listener) {
        this.a = listener;
    }
}
