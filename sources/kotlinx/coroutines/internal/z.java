package kotlinx.coroutines.internal;

import kotlin.b;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.l0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u001aI\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b\u0000\u0010\u0004*\u0018\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0004`\u00052\u0006\u0010\u0006\u001a\u0002H\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\u0010\t\u001a=\u0010\n\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0004*\u0018\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0004`\u00052\u0006\u0010\u0006\u001a\u0002H\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\u0010\u000b\u001aC\u0010\f\u001a\u0004\u0018\u00010\r\"\u0004\b\u0000\u0010\u0004*\u0018\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0004`\u00052\u0006\u0010\u0006\u001a\u0002H\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0000¢\u0006\u0002\u0010\u000f**\b\u0000\u0010\u0010\u001a\u0004\b\u0000\u0010\u0004\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00030\u00012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\u0011"}, d2 = {"bindCancellationFun", "Lkotlin/Function1;", "", "", "E", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "element", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)Lkotlin/jvm/functions/Function1;", "callUndeliveredElement", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)V", "callUndeliveredElementCatchingException", "Lkotlinx/coroutines/internal/UndeliveredElementException;", "undeliveredElementException", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlinx/coroutines/internal/UndeliveredElementException;)Lkotlinx/coroutines/internal/UndeliveredElementException;", "OnUndeliveredElement", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: OnUndeliveredElement.kt */
public final class z {
    public static /* synthetic */ UndeliveredElementException d(kotlin.jvm.functions.l lVar, Object obj, UndeliveredElementException undeliveredElementException, int i, Object obj2) {
        if ((i & 2) != 0) {
            undeliveredElementException = null;
        }
        return c(lVar, obj, undeliveredElementException);
    }

    @Nullable
    public static final <E> UndeliveredElementException c(@NotNull kotlin.jvm.functions.l<? super E, x> $this$callUndeliveredElementCatchingException, E element, @Nullable UndeliveredElementException undeliveredElementException) {
        try {
            $this$callUndeliveredElementCatchingException.invoke(element);
        } catch (Throwable ex) {
            if (undeliveredElementException == null || undeliveredElementException.getCause() == ex) {
                return new UndeliveredElementException(k.l("Exception in undelivered element handler for ", element), ex);
            }
            b.a(undeliveredElementException, ex);
        }
        return undeliveredElementException;
    }

    public static final <E> void b(@NotNull kotlin.jvm.functions.l<? super E, x> $this$callUndeliveredElement, E element, @NotNull g context) {
        UndeliveredElementException ex = c($this$callUndeliveredElement, element, (UndeliveredElementException) null);
        if (ex != null) {
            l0.a(context, ex);
        }
    }

    @l(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "E", "<anonymous parameter 0>", "", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: OnUndeliveredElement.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ g $context;
        final /* synthetic */ E $element;
        final /* synthetic */ kotlin.jvm.functions.l<E, x> $this_bindCancellationFun;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(kotlin.jvm.functions.l<? super E, x> lVar, E e, g gVar) {
            super(1);
            this.$this_bindCancellationFun = lVar;
            this.$element = e;
            this.$context = gVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((Throwable) p1);
            return x.a;
        }

        public final void invoke(@NotNull Throwable $noName_0) {
            z.b(this.$this_bindCancellationFun, this.$element, this.$context);
        }
    }

    @NotNull
    public static final <E> kotlin.jvm.functions.l<Throwable, x> a(@NotNull kotlin.jvm.functions.l<? super E, x> $this$bindCancellationFun, E element, @NotNull g context) {
        return new a($this$bindCancellationFun, element, context);
    }
}
