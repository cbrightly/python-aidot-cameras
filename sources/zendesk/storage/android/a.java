package zendesk.storage.android;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.google.android.libraries.places.api.model.PlaceTypes;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PersistedProperty.kt */
public final class a<T> {
    @NotNull
    private final c a;
    @NotNull
    private final String b;
    @NotNull
    private final Class<T> c;

    public a(@NotNull c storage, @NotNull String key, @NotNull Class<T> clazz) {
        k.e(storage, PlaceTypes.STORAGE);
        k.e(key, CacheEntity.KEY);
        k.e(clazz, "clazz");
        this.a = storage;
        this.b = key;
        this.c = clazz;
    }

    @Nullable
    public final T a(@NotNull Object thisRef, @NotNull kotlin.reflect.k<?> property) {
        k.e(thisRef, "thisRef");
        k.e(property, "property");
        return this.a.b(this.b, this.c);
    }

    public final void b(@NotNull Object thisRef, @NotNull kotlin.reflect.k<?> property, @Nullable T value) {
        k.e(thisRef, "thisRef");
        k.e(property, "property");
        this.a.a(this.b, value, this.c);
    }
}
