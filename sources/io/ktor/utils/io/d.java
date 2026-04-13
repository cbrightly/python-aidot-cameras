package io.ktor.utils.io;

import com.leedarson.bean.Constants;
import io.ktor.utils.io.internal.f;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.n;
import kotlin.reflect.e;
import kotlin.reflect.i;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteBufferChannel.kt */
public final /* synthetic */ class d extends n {
    public static final i INSTANCE = new d();

    d() {
    }

    public String getName() {
        return Constants.ACTION_STATE;
    }

    public e getOwner() {
        return a0.b(a.class);
    }

    public String getSignature() {
        return "getState()Lio/ktor/utils/io/internal/ReadWriteBufferState;";
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return ((a) receiver).state;
    }

    public void set(@Nullable Object receiver, @Nullable Object value) {
        ((a) receiver).state = (f) value;
    }
}
