package kotlin.random;

import java.util.Random;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: PlatformRandom.kt */
public abstract class a extends d {
    @NotNull
    public abstract Random getImpl();

    public int nextBits(int bitCount) {
        return e.f(getImpl().nextInt(), bitCount);
    }

    public int nextInt() {
        return getImpl().nextInt();
    }

    public int nextInt(int until) {
        return getImpl().nextInt(until);
    }

    public long nextLong() {
        return getImpl().nextLong();
    }

    public boolean nextBoolean() {
        return getImpl().nextBoolean();
    }

    public double nextDouble() {
        return getImpl().nextDouble();
    }

    public float nextFloat() {
        return getImpl().nextFloat();
    }

    @NotNull
    public byte[] nextBytes(@NotNull byte[] array) {
        k.e(array, "array");
        getImpl().nextBytes(array);
        return array;
    }
}
