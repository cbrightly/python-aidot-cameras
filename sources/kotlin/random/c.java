package kotlin.random;

/* compiled from: PlatformRandom.kt */
public final class c {
    public static final double a(int hi26, int low27) {
        return ((double) ((((long) hi26) << 27) + ((long) low27))) / ((double) 9007199254740992L);
    }
}
