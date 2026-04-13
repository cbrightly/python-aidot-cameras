package androidx.camera.view;

import androidx.lifecycle.Observer;

/* compiled from: lambda */
public final /* synthetic */ class a implements Observer {
    public final /* synthetic */ ForwardingLiveData a;

    public /* synthetic */ a(ForwardingLiveData forwardingLiveData) {
        this.a = forwardingLiveData;
    }

    public final void onChanged(Object obj) {
        this.a.setValue(obj);
    }
}
