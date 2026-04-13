package com.leedarson.newui.view;

/* compiled from: lambda */
public final /* synthetic */ class e implements io.reactivex.functions.e {
    public final /* synthetic */ BaseKVSCameraView c;
    public final /* synthetic */ String d;

    public /* synthetic */ e(BaseKVSCameraView baseKVSCameraView, String str) {
        this.c = baseKVSCameraView;
        this.d = str;
    }

    public final void accept(Object obj) {
        this.c.H(this.d, (Throwable) obj);
    }
}
