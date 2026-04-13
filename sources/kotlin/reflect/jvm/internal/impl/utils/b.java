package kotlin.reflect.jvm.internal.impl.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import kotlin.jvm.functions.l;
import org.jetbrains.annotations.NotNull;

/* compiled from: DFS */
public class b {

    /* compiled from: DFS */
    public interface c<N> {
        @NotNull
        Iterable<? extends N> a(N n);
    }

    /* compiled from: DFS */
    public interface d<N, R> {
        R a();

        void b(N n);

        boolean c(N n);
    }

    /* compiled from: DFS */
    public interface e<N> {
        boolean a(N n);
    }

    private static /* synthetic */ void a(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 5:
            case 8:
            case 11:
            case 15:
            case 18:
            case 21:
            case 23:
                objArr[0] = "neighbors";
                break;
            case 2:
            case 12:
            case 16:
            case 19:
            case 24:
                objArr[0] = "visited";
                break;
            case 3:
            case 6:
            case 13:
            case 25:
                objArr[0] = "handler";
                break;
            case 9:
                objArr[0] = "predicate";
                break;
            case 10:
            case 14:
                objArr[0] = "node";
                break;
            case 22:
                objArr[0] = "current";
                break;
            default:
                objArr[0] = "nodes";
                break;
        }
        objArr[1] = "kotlin/reflect/jvm/internal/impl/utils/DFS";
        switch (i) {
            case 7:
            case 8:
            case 9:
                objArr[2] = "ifAny";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "dfsFromNode";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                objArr[2] = "topologicalOrder";
                break;
            case 22:
            case 23:
            case 24:
            case 25:
                objArr[2] = "doDfs";
                break;
            default:
                objArr[2] = "dfs";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static <N, R> R c(@NotNull Collection<N> nodes, @NotNull c<N> neighbors, @NotNull e<N> visited, @NotNull d<N, R> handler) {
        if (nodes == null) {
            a(0);
        }
        if (neighbors == null) {
            a(1);
        }
        if (visited == null) {
            a(2);
        }
        if (handler == null) {
            a(3);
        }
        for (N node : nodes) {
            d(node, neighbors, visited, handler);
        }
        return handler.a();
    }

    public static <N, R> R b(@NotNull Collection<N> nodes, @NotNull c<N> neighbors, @NotNull d<N, R> handler) {
        if (nodes == null) {
            a(4);
        }
        if (neighbors == null) {
            a(5);
        }
        if (handler == null) {
            a(6);
        }
        return c(nodes, neighbors, new f(), handler);
    }

    public static <N> Boolean e(@NotNull Collection<N> nodes, @NotNull c<N> neighbors, @NotNull l<N, Boolean> predicate) {
        if (nodes == null) {
            a(7);
        }
        if (neighbors == null) {
            a(8);
        }
        if (predicate == null) {
            a(9);
        }
        return (Boolean) b(nodes, neighbors, new a(predicate, new boolean[1]));
    }

    /* compiled from: DFS */
    public static final class a extends C0433b<N, Boolean> {
        final /* synthetic */ l a;
        final /* synthetic */ boolean[] b;

        a(l lVar, boolean[] zArr) {
            this.a = lVar;
            this.b = zArr;
        }

        public boolean c(N current) {
            if (((Boolean) this.a.invoke(current)).booleanValue()) {
                this.b[0] = true;
            }
            return !this.b[0];
        }

        /* renamed from: d */
        public Boolean a() {
            return Boolean.valueOf(this.b[0]);
        }
    }

    public static <N> void d(@NotNull N current, @NotNull c<N> neighbors, @NotNull e<N> visited, @NotNull d<N, ?> handler) {
        if (current == null) {
            a(22);
        }
        if (neighbors == null) {
            a(23);
        }
        if (visited == null) {
            a(24);
        }
        if (handler == null) {
            a(25);
        }
        if (visited.a(current) && handler.c(current)) {
            for (N neighbor : neighbors.a(current)) {
                d(neighbor, neighbors, visited, handler);
            }
            handler.b(current);
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.utils.b$b  reason: collision with other inner class name */
    /* compiled from: DFS */
    public static abstract class C0433b<N, R> implements d<N, R> {
        public void b(N n) {
        }
    }

    /* compiled from: DFS */
    public static class f<N> implements e<N> {
        private final Set<N> a;

        private static /* synthetic */ void b(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"visited", "kotlin/reflect/jvm/internal/impl/utils/DFS$VisitedWithSet", "<init>"}));
        }

        public f() {
            this(new HashSet());
        }

        public f(@NotNull Set<N> visited) {
            if (visited == null) {
                b(0);
            }
            this.a = visited;
        }

        public boolean a(N current) {
            return this.a.add(current);
        }
    }
}
