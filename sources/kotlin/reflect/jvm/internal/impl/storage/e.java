package kotlin.reflect.jvm.internal.impl.storage;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.jetbrains.annotations.NotNull;

/* compiled from: NoLock */
public class e implements Lock {
    public static final Lock c = new e();

    private static /* synthetic */ void a(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"unit", "kotlin/reflect/jvm/internal/impl/storage/NoLock", "tryLock"}));
    }

    private e() {
    }

    public void lock() {
    }

    public void unlock() {
    }

    public void lockInterruptibly() {
        throw new UnsupportedOperationException("Should not be called");
    }

    public boolean tryLock() {
        throw new UnsupportedOperationException("Should not be called");
    }

    public boolean tryLock(long time, @NotNull TimeUnit timeUnit) {
        if (timeUnit == null) {
            a(0);
        }
        throw new UnsupportedOperationException("Should not be called");
    }

    @NotNull
    public Condition newCondition() {
        throw new UnsupportedOperationException("Should not be called");
    }
}
