package kotlin.reflect.jvm.internal.impl.storage;

import kotlin.jvm.functions.a;
import kotlin.jvm.functions.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StorageManager.kt */
public interface j {
    @NotNull
    <K, V> a<K, V> a();

    @NotNull
    <T> f<T> b(@NotNull a<? extends T> aVar, @NotNull T t);

    @NotNull
    <T> f<T> c(@NotNull a<? extends T> aVar);

    <T> T d(@NotNull a<? extends T> aVar);

    @NotNull
    <T> g<T> e(@NotNull a<? extends T> aVar);

    @NotNull
    <T> f<T> f(@NotNull a<? extends T> aVar, @Nullable l<? super Boolean, ? extends T> lVar, @NotNull l<? super T, x> lVar2);

    @NotNull
    <K, V> d<K, V> g(@NotNull l<? super K, ? extends V> lVar);

    @NotNull
    <K, V> c<K, V> h(@NotNull l<? super K, ? extends V> lVar);
}
