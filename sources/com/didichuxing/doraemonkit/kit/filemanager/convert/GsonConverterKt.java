package com.didichuxing.doraemonkit.kit.filemanager.convert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ktor.features.g;
import io.ktor.http.c;
import kotlin.jvm.a;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: GsonConverter.kt */
public final class GsonConverterKt {
    public static /* synthetic */ void gson$default(g.a aVar, c cVar, l lVar, int i, Object obj) {
        if ((i & 1) != 0) {
            cVar = c.a.t.b();
        }
        if ((i & 2) != 0) {
            lVar = GsonConverterKt$gson$1.INSTANCE;
        }
        gson(aVar, cVar, lVar);
    }

    public static final void gson(@NotNull g.a $this$gson, @NotNull c contentType, @NotNull l<? super GsonBuilder, x> block) {
        k.f($this$gson, "$this$gson");
        k.f(contentType, "contentType");
        k.f(block, "block");
        GsonBuilder builder = new GsonBuilder();
        block.invoke(builder);
        Gson create = builder.create();
        k.b(create, "builder.create()");
        g.a.d($this$gson, contentType, new GsonConverter(create), (l) null, 4, (Object) null);
    }

    /* access modifiers changed from: private */
    public static final boolean isExcluded(@NotNull Gson $this$isExcluded, kotlin.reflect.c<?> type) {
        return $this$isExcluded.excluder().excludeClass(a.b(type), false);
    }
}
