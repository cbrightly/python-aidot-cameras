package com.didichuxing.doraemonkit.kit.filemanager.convert;

import com.google.gson.Gson;
import io.ktor.application.b;
import io.ktor.features.f;
import io.ktor.features.h;
import io.ktor.http.c;
import io.ktor.http.content.l;
import io.ktor.http.v;
import io.ktor.request.e;
import io.ktor.util.pipeline.d;
import io.ktor.utils.io.i;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import kotlin.jvm.a;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: GsonConverter.kt */
public final class GsonConverter implements f {
    private final Gson gson;

    public GsonConverter() {
        this((Gson) null, 1, (DefaultConstructorMarker) null);
    }

    public GsonConverter(@NotNull Gson gson2) {
        k.f(gson2, "gson");
        this.gson = gson2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ GsonConverter(Gson gson2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Gson() : gson2);
    }

    @Nullable
    public Object convertForSend(@NotNull d<Object, b> context, @NotNull c contentType, @NotNull Object value, @NotNull kotlin.coroutines.d<Object> $completion) {
        String json = this.gson.toJson(value);
        k.b(json, "gson.toJson(value)");
        return new l(json, io.ktor.http.d.b(contentType, h.c(context.getContext(), (Charset) null, 1, (Object) null)), (v) null, 4, (DefaultConstructorMarker) null);
    }

    @Nullable
    public Object convertForReceive(@NotNull d<io.ktor.request.c, b> context, @NotNull kotlin.coroutines.d<Object> $completion) {
        io.ktor.request.c request = context.l();
        Object c = request.c();
        if (!(c instanceof i)) {
            c = null;
        }
        i channel = (i) c;
        if (channel == null) {
            return null;
        }
        InputStream b = io.ktor.utils.io.jvm.javaio.b.b(channel, (z1) null, 1, (Object) null);
        Charset b2 = e.b(context.getContext().getRequest());
        if (b2 == null) {
            b2 = kotlin.text.c.a;
        }
        InputStreamReader reader = new InputStreamReader(b, b2);
        kotlin.reflect.c type = request.a();
        if (!GsonConverterKt.isExcluded(this.gson, type)) {
            Object fromJson = this.gson.fromJson((Reader) reader, a.c(type));
            if (fromJson != null) {
                return fromJson;
            }
            throw new UnsupportedNullValuesException();
        }
        throw new ExcludedTypeGsonException(type);
    }
}
