package kotlinx.coroutines;

import kotlin.coroutines.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.text.x;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\b\u0018\u0000 \u00182\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001\u0018B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\t\u001a\u00020\u0005HÆ\u0003J\u0013\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0002H\u0016J\b\u0010\u0016\u001a\u00020\u0002H\u0016J\u0010\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0019"}, d2 = {"Lkotlinx/coroutines/CoroutineId;", "Lkotlinx/coroutines/ThreadContextElement;", "", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "id", "", "(J)V", "getId", "()J", "component1", "copy", "equals", "", "other", "", "hashCode", "", "restoreThreadContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "oldState", "toString", "updateThreadContext", "Key", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
@IgnoreJRERequirement
/* compiled from: CoroutineContext.kt */
public final class m0 extends kotlin.coroutines.a implements w2<String> {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);
    private final long d;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof m0) && this.d == ((m0) obj).d;
    }

    public int hashCode() {
        return com.google.chip.chiptool.setuppayloadscanner.a.a(this.d);
    }

    public final long W() {
        return this.d;
    }

    @l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lkotlinx/coroutines/CoroutineId$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlinx/coroutines/CoroutineId;", "()V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: CoroutineContext.kt */
    public static final class a implements g.c<m0> {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }

    public m0(long id) {
        super(c);
        this.d = id;
    }

    @NotNull
    public String toString() {
        return "CoroutineId(" + this.d + ')';
    }

    @NotNull
    /* renamed from: u0 */
    public String P(@NotNull g context) {
        String name;
        n0 n0Var = (n0) context.get(n0.c);
        String str = "coroutine";
        if (!(n0Var == null || (name = n0Var.getName()) == null)) {
            str = name;
        }
        String coroutineName = str;
        Thread currentThread = Thread.currentThread();
        String oldName = currentThread.getName();
        int lastIndex = x.k0(oldName, " @", 0, false, 6, (Object) null);
        if (lastIndex < 0) {
            lastIndex = oldName.length();
        }
        StringBuilder sb = new StringBuilder(coroutineName.length() + lastIndex + 10);
        StringBuilder $this$updateThreadContext_u24lambda_u2d0 = sb;
        String substring = oldName.substring(0, lastIndex);
        k.d(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        $this$updateThreadContext_u24lambda_u2d0.append(substring);
        $this$updateThreadContext_u24lambda_u2d0.append(" @");
        $this$updateThreadContext_u24lambda_u2d0.append(coroutineName);
        $this$updateThreadContext_u24lambda_u2d0.append('#');
        $this$updateThreadContext_u24lambda_u2d0.append(W());
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        currentThread.setName(sb2);
        return oldName;
    }

    /* renamed from: o0 */
    public void v(@NotNull g context, @NotNull String oldState) {
        Thread.currentThread().setName(oldState);
    }
}
