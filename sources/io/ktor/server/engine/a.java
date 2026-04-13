package io.ktor.server.engine;

import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationEngine.kt */
public interface a {
    @NotNull
    a a(boolean z);

    @NotNull
    b b();

    /* renamed from: io.ktor.server.engine.a$a  reason: collision with other inner class name */
    /* compiled from: ApplicationEngine.kt */
    public static class C0270a {
        private final int a;
        private int b;
        private int c;
        private int d;

        public C0270a() {
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            this.a = availableProcessors;
            this.b = (availableProcessors / 2) + 1;
            this.c = (availableProcessors / 2) + 1;
            this.d = availableProcessors;
        }

        public final int b() {
            return this.b;
        }

        public final int c() {
            return this.c;
        }

        public final int a() {
            return this.d;
        }
    }

    /* compiled from: ApplicationEngine.kt */
    public static final class b {
        @NotNull
        public static io.ktor.application.a a(a $this) {
            return $this.b().a();
        }

        public static /* synthetic */ a b(a aVar, boolean z, int i, Object obj) {
            if (obj == null) {
                if ((i & 1) != 0) {
                    z = false;
                }
                return aVar.a(z);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: start");
        }
    }
}
