package com.squareup.okhttp.internal.framed;

import java.util.List;
import okio.e;

/* compiled from: PushObserver */
public interface m {
    public static final m a = new a();

    boolean a(int i, List<f> list);

    boolean b(int i, List<f> list, boolean z);

    boolean c(int i, e eVar, int i2, boolean z);

    void d(int i, a aVar);

    /* compiled from: PushObserver */
    public static final class a implements m {
        a() {
        }

        public boolean a(int streamId, List<f> list) {
            return true;
        }

        public boolean b(int streamId, List<f> list, boolean last) {
            return true;
        }

        public boolean c(int streamId, e source, int byteCount, boolean last) {
            source.skip((long) byteCount);
            return true;
        }

        public void d(int streamId, a errorCode) {
        }
    }
}
