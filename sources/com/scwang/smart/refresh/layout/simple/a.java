package com.scwang.smart.refresh.layout.simple;

import android.graphics.PointF;
import android.view.View;
import com.scwang.smart.refresh.layout.listener.i;
import com.scwang.smart.refresh.layout.util.b;

/* compiled from: SimpleBoundaryDecider */
public class a implements i {
    public PointF a;
    public i b;
    public boolean c = true;

    public boolean a(View content) {
        i iVar = this.b;
        if (iVar != null) {
            return iVar.a(content);
        }
        return b.b(content, this.a);
    }

    public boolean b(View content) {
        i iVar = this.b;
        if (iVar != null) {
            return iVar.b(content);
        }
        return b.a(content, this.a, this.c);
    }
}
