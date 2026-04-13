package kotlinx.coroutines.internal;

import java.util.ArrayDeque;
import java.util.Iterator;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.n;
import kotlin.o;
import kotlin.p;
import kotlin.t;
import kotlin.text.w;
import kotlinx.coroutines.g0;
import kotlinx.coroutines.s0;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\u001a\u0014\u0010\u0006\u001a\u00060\u0007j\u0002`\b2\u0006\u0010\t\u001a\u00020\u0001H\u0007\u001a9\u0010\n\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\r\u001a\u0002H\u000b2\u0006\u0010\u000e\u001a\u0002H\u000b2\u0010\u0010\u000f\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u0010H\u0002¢\u0006\u0002\u0010\u0011\u001a\u001e\u0010\u0012\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u00102\n\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015H\u0002\u001a1\u0010\u0016\u001a\u00020\u00172\u0010\u0010\u0018\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u00192\u0010\u0010\u000e\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u0010H\u0002¢\u0006\u0002\u0010\u001a\u001a\u0019\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\fHHø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a+\u0010\u001f\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000b2\n\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015H\u0002¢\u0006\u0002\u0010 \u001a\u001f\u0010!\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000bH\u0000¢\u0006\u0002\u0010\"\u001a,\u0010!\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000b2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030#H\b¢\u0006\u0002\u0010$\u001a!\u0010%\u001a\u0004\u0018\u0001H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000bH\u0002¢\u0006\u0002\u0010\"\u001a \u0010&\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000bH\b¢\u0006\u0002\u0010\"\u001a\u001f\u0010'\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000bH\u0000¢\u0006\u0002\u0010\"\u001a1\u0010(\u001a\u0018\u0012\u0004\u0012\u0002H\u000b\u0012\u000e\u0012\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u00190)\"\b\b\u0000\u0010\u000b*\u00020\f*\u0002H\u000bH\u0002¢\u0006\u0002\u0010*\u001a\u001c\u0010+\u001a\u00020,*\u00060\u0007j\u0002`\b2\n\u0010-\u001a\u00060\u0007j\u0002`\bH\u0002\u001a#\u0010.\u001a\u00020/*\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u00192\u0006\u00100\u001a\u00020\u0001H\u0002¢\u0006\u0002\u00101\u001a\u0014\u00102\u001a\u00020\u0017*\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0000\u001a\u0010\u00103\u001a\u00020,*\u00060\u0007j\u0002`\bH\u0000\u001a\u001b\u00104\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f*\u0002H\u000bH\u0002¢\u0006\u0002\u0010\"\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u0016\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u0016\u0010\u0005\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001X\u0004¢\u0006\u0002\n\u0000*\f\b\u0000\u00105\"\u00020\u00142\u00020\u0014*\f\b\u0000\u00106\"\u00020\u00072\u00020\u0007\u0002\u0004\n\u0002\b\u0019¨\u00067"}, d2 = {"baseContinuationImplClass", "", "baseContinuationImplClassName", "kotlin.jvm.PlatformType", "stackTraceRecoveryClass", "stackTraceRecoveryClassName", "artificialFrame", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "message", "createFinalException", "E", "", "cause", "result", "resultStackTrace", "Ljava/util/ArrayDeque;", "(Ljava/lang/Throwable;Ljava/lang/Throwable;Ljava/util/ArrayDeque;)Ljava/lang/Throwable;", "createStackTrace", "continuation", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "mergeRecoveredTraces", "", "recoveredStacktrace", "", "([Ljava/lang/StackTraceElement;Ljava/util/ArrayDeque;)V", "recoverAndThrow", "", "exception", "(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recoverFromStackFrame", "(Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;", "recoverStackTrace", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Throwable;", "tryCopyAndVerify", "unwrap", "unwrapImpl", "causeAndStacktrace", "Lkotlin/Pair;", "(Ljava/lang/Throwable;)Lkotlin/Pair;", "elementWiseEquals", "", "e", "frameIndex", "", "methodName", "([Ljava/lang/StackTraceElement;Ljava/lang/String;)I", "initCause", "isArtificial", "sanitizeStackTrace", "CoroutineStackFrame", "StackTraceElement", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: StackTraceRecovery.kt */
public final class e0 {
    private static final String a;
    private static final String b;

    static {
        Object obj;
        Object obj2;
        try {
            o.a aVar = o.Companion;
            obj = o.m17constructorimpl(Class.forName("kotlin.coroutines.jvm.internal.a").getCanonicalName());
        } catch (Throwable th) {
            o.a aVar2 = o.Companion;
            obj = o.m17constructorimpl(p.a(th));
        }
        Throwable r1 = o.m20exceptionOrNullimpl(obj);
        if (r1 != null) {
            Throwable th2 = r1;
            obj = "kotlin.coroutines.jvm.internal.BaseContinuationImpl";
        }
        a = (String) obj;
        try {
            o.a aVar3 = o.Companion;
            obj2 = o.m17constructorimpl(Class.forName("kotlinx.coroutines.internal.e0").getCanonicalName());
        } catch (Throwable th3) {
            o.a aVar4 = o.Companion;
            obj2 = o.m17constructorimpl(p.a(th3));
        }
        Throwable r12 = o.m20exceptionOrNullimpl(obj2);
        if (r12 != null) {
            Throwable th4 = r12;
            obj2 = "kotlinx.coroutines.internal.StackTraceRecoveryKt";
        }
        b = (String) obj2;
    }

    @NotNull
    public static final <E extends Throwable> E k(@NotNull E exception) {
        Throwable copy;
        if (s0.d() && (copy = m(exception)) != null) {
            return l(copy);
        }
        return exception;
    }

    private static final <E extends Throwable> E l(E $this$sanitizeStackTrace) {
        StackTraceElement stackTraceElement;
        StackTraceElement[] stackTrace = $this$sanitizeStackTrace.getStackTrace();
        int size = stackTrace.length;
        int lastIntrinsic = g(stackTrace, b);
        int startIndex = lastIntrinsic + 1;
        int endIndex = g(stackTrace, a);
        int i = (size - lastIntrinsic) - (endIndex == -1 ? 0 : size - endIndex);
        StackTraceElement[] trace = new StackTraceElement[i];
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 == 0) {
                stackTraceElement = b("Coroutine boundary");
            } else {
                stackTraceElement = stackTrace[(startIndex + i2) - 1];
            }
            trace[i2] = stackTraceElement;
        }
        $this$sanitizeStackTrace.setStackTrace(trace);
        return $this$sanitizeStackTrace;
    }

    /* access modifiers changed from: private */
    public static final <E extends Throwable> E j(E exception, e continuation) {
        n c = c(exception);
        Throwable cause = (Throwable) c.component1();
        StackTraceElement[] recoveredStacktrace = (StackTraceElement[]) c.component2();
        Throwable newException = m(cause);
        if (newException == null) {
            return exception;
        }
        ArrayDeque stacktrace = e(continuation);
        if (stacktrace.isEmpty()) {
            return exception;
        }
        if (cause != exception) {
            i(recoveredStacktrace, stacktrace);
        }
        return d(cause, newException, stacktrace);
    }

    private static final <E extends Throwable> E m(E exception) {
        Throwable newException = k.g(exception);
        if (newException == null) {
            return null;
        }
        if ((exception instanceof g0) || k.a(newException.getMessage(), exception.getMessage())) {
            return newException;
        }
        return null;
    }

    private static final <E extends Throwable> E d(E cause, E result, ArrayDeque<StackTraceElement> resultStackTrace) {
        resultStackTrace.addFirst(b("Coroutine boundary"));
        StackTraceElement[] causeTrace = cause.getStackTrace();
        int size = g(causeTrace, a);
        int i = 0;
        if (size == -1) {
            Object[] array = resultStackTrace.toArray(new StackTraceElement[0]);
            if (array != null) {
                result.setStackTrace((StackTraceElement[]) array);
                return result;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        StackTraceElement[] mergedStackTrace = new StackTraceElement[(resultStackTrace.size() + size)];
        int i2 = 0;
        while (i2 < size) {
            int i3 = i2;
            i2++;
            mergedStackTrace[i3] = causeTrace[i3];
        }
        Iterator<StackTraceElement> it = resultStackTrace.iterator();
        while (it.hasNext()) {
            int index = i;
            i++;
            mergedStackTrace[size + index] = it.next();
        }
        result.setStackTrace(mergedStackTrace);
        return result;
    }

    private static final <E extends Throwable> n<E, StackTraceElement[]> c(E $this$causeAndStacktrace) {
        boolean z;
        Throwable cause = $this$causeAndStacktrace.getCause();
        if (cause == null || !k.a(cause.getClass(), $this$causeAndStacktrace.getClass())) {
            return t.a($this$causeAndStacktrace, new StackTraceElement[0]);
        }
        StackTraceElement[] currentTrace = $this$causeAndStacktrace.getStackTrace();
        StackTraceElement[] stackTraceElementArr = currentTrace;
        int length = stackTraceElementArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            StackTraceElement it = stackTraceElementArr[i];
            i++;
            if (h(it)) {
                z = true;
                break;
            }
        }
        if (z) {
            return t.a(cause, currentTrace);
        }
        return t.a($this$causeAndStacktrace, new StackTraceElement[0]);
    }

    private static final void i(StackTraceElement[] recoveredStacktrace, ArrayDeque<StackTraceElement> result) {
        int index$iv;
        int i;
        StackTraceElement[] stackTraceElementArr = recoveredStacktrace;
        int length = stackTraceElementArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                index$iv = -1;
                break;
            }
            index$iv = i2;
            i2++;
            if (h(stackTraceElementArr[index$iv])) {
                break;
            }
        }
        int startIndex = index$iv + 1;
        int lastFrameIndex = recoveredStacktrace.length - 1;
        if (startIndex <= lastFrameIndex) {
            int i3 = lastFrameIndex;
            do {
                i = i3;
                i3--;
                if (f(recoveredStacktrace[i], result.getLast())) {
                    result.removeLast();
                }
                result.addFirst(recoveredStacktrace[i]);
            } while (i != startIndex);
        }
    }

    @NotNull
    public static final <E extends Throwable> E n(@NotNull E exception) {
        Throwable cause = exception.getCause();
        if (cause == null || !k.a(cause.getClass(), exception.getClass())) {
            return exception;
        }
        StackTraceElement[] stackTrace = exception.getStackTrace();
        int length = stackTrace.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            StackTraceElement it = stackTrace[i];
            i++;
            if (h(it)) {
                z = true;
                break;
            }
        }
        if (z) {
            return cause;
        }
        return exception;
    }

    private static final ArrayDeque<StackTraceElement> e(e continuation) {
        ArrayDeque stack = new ArrayDeque();
        StackTraceElement it = continuation.getStackTraceElement();
        if (it != null) {
            stack.add(it);
        }
        e last = continuation;
        while (true) {
            e callerFrame = last == null ? null : last.getCallerFrame();
            if (callerFrame == null) {
                return stack;
            }
            last = callerFrame;
            StackTraceElement it2 = last.getStackTraceElement();
            if (it2 != null) {
                stack.add(it2);
            }
        }
    }

    @NotNull
    public static final StackTraceElement b(@NotNull String message) {
        return new StackTraceElement(k.l("\b\b\b(", message), "\b", "\b", -1);
    }

    public static final boolean h(@NotNull StackTraceElement $this$isArtificial) {
        return w.N($this$isArtificial.getClassName(), "\b\b\b", false, 2, (Object) null);
    }

    private static final int g(StackTraceElement[] $this$frameIndex, String methodName) {
        StackTraceElement[] stackTraceElementArr = $this$frameIndex;
        int length = stackTraceElementArr.length;
        int i = 0;
        while (i < length) {
            int index$iv = i;
            i++;
            if (k.a(methodName, stackTraceElementArr[index$iv].getClassName())) {
                return index$iv;
            }
        }
        return -1;
    }

    private static final boolean f(StackTraceElement $this$elementWiseEquals, StackTraceElement e) {
        return $this$elementWiseEquals.getLineNumber() == e.getLineNumber() && k.a($this$elementWiseEquals.getMethodName(), e.getMethodName()) && k.a($this$elementWiseEquals.getFileName(), e.getFileName()) && k.a($this$elementWiseEquals.getClassName(), e.getClassName());
    }
}
