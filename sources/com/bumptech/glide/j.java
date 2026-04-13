package com.bumptech.glide;

import com.bumptech.glide.j;
import com.bumptech.glide.request.transition.a;
import com.bumptech.glide.request.transition.c;

/* compiled from: TransitionOptions */
public abstract class j<CHILD extends j<CHILD, TranscodeType>, TranscodeType> implements Cloneable {
    private c<? super TranscodeType> c = a.b();

    /* renamed from: a */
    public final CHILD clone() {
        try {
            return (j) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: package-private */
    public final c<? super TranscodeType> b() {
        return this.c;
    }
}
