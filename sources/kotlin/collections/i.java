package kotlin.collections;

/* compiled from: ArraysJVM.kt */
public class i {
    public static final void a(int toIndex, int size) {
        if (toIndex > size) {
            throw new IndexOutOfBoundsException("toIndex (" + toIndex + ") is greater than size (" + size + ").");
        }
    }
}
