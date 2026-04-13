package com.leedarson.view.rangeseekbar;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class SavedState extends View.BaseSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = new a();
    public static ChangeQuickRedirect changeQuickRedirect;
    public float c;
    public float d;
    public float f;
    public int q;
    public float x;
    public float y;

    /* synthetic */ SavedState(Parcel x0, a x1) {
        this(x0);
    }

    public SavedState(Parcelable superState) {
        super(superState);
    }

    private SavedState(Parcel in) {
        super(in);
        this.c = in.readFloat();
        this.d = in.readFloat();
        this.f = in.readFloat();
        this.q = in.readInt();
        this.x = in.readFloat();
        this.y = in.readFloat();
    }

    public void writeToParcel(Parcel out, int flags) {
        if (!PatchProxy.proxy(new Object[]{out, new Integer(flags)}, this, changeQuickRedirect, false, 11949, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.writeToParcel(out, flags);
            out.writeFloat(this.c);
            out.writeFloat(this.d);
            out.writeFloat(this.f);
            out.writeInt(this.q);
            out.writeFloat(this.x);
            out.writeFloat(this.y);
        }
    }

    public class a implements Parcelable.Creator<SavedState> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 11952, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 11951, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public SavedState a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 11950, new Class[]{Parcel.class}, SavedState.class);
            if (proxy.isSupported) {
                return (SavedState) proxy.result;
            }
            return new SavedState(in, (a) null);
        }

        public SavedState[] b(int size) {
            return new SavedState[size];
        }
    }
}
