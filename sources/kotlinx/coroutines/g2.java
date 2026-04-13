package kotlinx.coroutines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.sequences.j;
import kotlin.x;
import kotlinx.coroutines.internal.a0;
import kotlinx.coroutines.internal.e0;
import kotlinx.coroutines.internal.q;
import kotlinx.coroutines.internal.r;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000è\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0001\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0017\u0018\u00002\u00020X2\u00020\u00172\u000202\u00030Ã\u0001:\u0006Ò\u0001Ó\u0001Ô\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J'\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0002¢\u0006\u0004\b\u000b\u0010\fJ%\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002¢\u0006\u0004\b\u0012\u0010\u0013J\u0019\u0010\u0015\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0014¢\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u0017¢\u0006\u0004\b\u001a\u0010\u001bJ\u0015\u0010\u001e\u001a\u0004\u0018\u00010\u0005H@ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010\u001f\u001a\u0004\u0018\u00010\u0005H@ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010\u001dJ\u0019\u0010!\u001a\u00020\u00012\b\u0010 \u001a\u0004\u0018\u00010\rH\u0017¢\u0006\u0004\b!\u0010\"J\u001f\u0010!\u001a\u00020\u00112\u000e\u0010 \u001a\n\u0018\u00010#j\u0004\u0018\u0001`$H\u0016¢\u0006\u0004\b!\u0010%J\u0017\u0010&\u001a\u00020\u00012\b\u0010 \u001a\u0004\u0018\u00010\r¢\u0006\u0004\b&\u0010\"J\u0019\u0010)\u001a\u00020\u00012\b\u0010 \u001a\u0004\u0018\u00010\u0005H\u0000¢\u0006\u0004\b'\u0010(J\u0017\u0010*\u001a\u00020\u00112\u0006\u0010 \u001a\u00020\rH\u0016¢\u0006\u0004\b*\u0010+J\u001b\u0010,\u001a\u0004\u0018\u00010\u00052\b\u0010 \u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\b,\u0010-J\u0017\u0010.\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\rH\u0002¢\u0006\u0004\b.\u0010\"J\u000f\u00100\u001a\u00020/H\u0014¢\u0006\u0004\b0\u00101J\u0017\u00102\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\rH\u0016¢\u0006\u0004\b2\u0010\"J!\u00105\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u0002032\b\u00104\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\b5\u00106J)\u0010;\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u0002072\u0006\u00109\u001a\u0002082\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\b;\u0010<J\u0019\u0010=\u001a\u00020\r2\b\u0010 \u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\b=\u0010>J(\u0010C\u001a\u00020@2\n\b\u0002\u0010?\u001a\u0004\u0018\u00010/2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\rH\b¢\u0006\u0004\bA\u0010BJ#\u0010D\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u0002072\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\bD\u0010EJ\u0019\u0010F\u001a\u0004\u0018\u0001082\u0006\u0010\u0014\u001a\u000203H\u0002¢\u0006\u0004\bF\u0010GJ\u0011\u0010H\u001a\u00060#j\u0002`$¢\u0006\u0004\bH\u0010IJ\u0013\u0010J\u001a\u00060#j\u0002`$H\u0016¢\u0006\u0004\bJ\u0010IJ\u0011\u0010M\u001a\u0004\u0018\u00010\u0005H\u0000¢\u0006\u0004\bK\u0010LJ\u000f\u0010N\u001a\u0004\u0018\u00010\r¢\u0006\u0004\bN\u0010OJ'\u0010P\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0014\u001a\u0002072\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002¢\u0006\u0004\bP\u0010QJ\u0019\u0010R\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0014\u001a\u000203H\u0002¢\u0006\u0004\bR\u0010SJ\u0017\u0010U\u001a\u00020\u00012\u0006\u0010T\u001a\u00020\rH\u0014¢\u0006\u0004\bU\u0010\"J\u0017\u0010W\u001a\u00020\u00112\u0006\u0010T\u001a\u00020\rH\u0010¢\u0006\u0004\bV\u0010+J\u0019\u0010Z\u001a\u00020\u00112\b\u0010Y\u001a\u0004\u0018\u00010XH\u0004¢\u0006\u0004\bZ\u0010[JF\u0010d\u001a\u00020c2\u0006\u0010\\\u001a\u00020\u00012\u0006\u0010]\u001a\u00020\u00012'\u0010b\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\r¢\u0006\f\b_\u0012\b\b`\u0012\u0004\b\b( \u0012\u0004\u0012\u00020\u00110^j\u0002`a¢\u0006\u0004\bd\u0010eJ6\u0010d\u001a\u00020c2'\u0010b\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\r¢\u0006\f\b_\u0012\b\b`\u0012\u0004\b\b( \u0012\u0004\u0012\u00020\u00110^j\u0002`a¢\u0006\u0004\bd\u0010fJ\u0013\u0010g\u001a\u00020\u0011H@ø\u0001\u0000¢\u0006\u0004\bg\u0010\u001dJ\u000f\u0010h\u001a\u00020\u0001H\u0002¢\u0006\u0004\bh\u0010iJ\u0013\u0010j\u001a\u00020\u0011H@ø\u0001\u0000¢\u0006\u0004\bj\u0010\u001dJ&\u0010m\u001a\u00020l2\u0014\u0010k\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00110^H\b¢\u0006\u0004\bm\u0010nJ\u001b\u0010o\u001a\u0004\u0018\u00010\u00052\b\u0010 \u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\bo\u0010-J\u0019\u0010q\u001a\u00020\u00012\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0000¢\u0006\u0004\bp\u0010(J\u001b\u0010s\u001a\u0004\u0018\u00010\u00052\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0000¢\u0006\u0004\br\u0010-J@\u0010t\u001a\u00020\t2'\u0010b\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\r¢\u0006\f\b_\u0012\b\b`\u0012\u0004\b\b( \u0012\u0004\u0012\u00020\u00110^j\u0002`a2\u0006\u0010\\\u001a\u00020\u0001H\u0002¢\u0006\u0004\bt\u0010uJ\u000f\u0010w\u001a\u00020/H\u0010¢\u0006\u0004\bv\u00101J\u001f\u0010x\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\rH\u0002¢\u0006\u0004\bx\u0010yJ.\u0010{\u001a\u00020\u0011\"\n\b\u0000\u0010z\u0018\u0001*\u00020\t2\u0006\u0010\b\u001a\u00020\u00072\b\u0010 \u001a\u0004\u0018\u00010\rH\b¢\u0006\u0004\b{\u0010yJ\u0019\u0010\\\u001a\u00020\u00112\b\u0010 \u001a\u0004\u0018\u00010\rH\u0014¢\u0006\u0004\b\\\u0010+J\u0019\u0010|\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0014¢\u0006\u0004\b|\u0010\u0016J\u000f\u0010}\u001a\u00020\u0011H\u0014¢\u0006\u0004\b}\u0010~J\u0019\u0010\u0001\u001a\u00020\u00112\u0007\u0010\u0001\u001a\u00020¢\u0006\u0006\b\u0001\u0010\u0001J\u001b\u0010\u0001\u001a\u00020\u00112\u0007\u0010\u0014\u001a\u00030\u0001H\u0002¢\u0006\u0006\b\u0001\u0010\u0001J\u001a\u0010\u0001\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\tH\u0002¢\u0006\u0006\b\u0001\u0010\u0001JI\u0010\u0001\u001a\u00020\u0011\"\u0005\b\u0000\u0010\u00012\u000e\u0010\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000\u00012\u001d\u0010k\u001a\u0019\b\u0001\u0012\u000b\u0012\t\u0012\u0004\u0012\u00028\u00000\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00050^ø\u0001\u0000¢\u0006\u0006\b\u0001\u0010\u0001JX\u0010\u0001\u001a\u00020\u0011\"\u0004\b\u0000\u0010z\"\u0005\b\u0001\u0010\u00012\u000e\u0010\u0001\u001a\t\u0012\u0004\u0012\u00028\u00010\u00012$\u0010k\u001a \b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u000b\u0012\t\u0012\u0004\u0012\u00028\u00010\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0001H\u0000ø\u0001\u0000¢\u0006\u0006\b\u0001\u0010\u0001J\u001a\u0010\u0001\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\tH\u0000¢\u0006\u0006\b\u0001\u0010\u0001JX\u0010\u0001\u001a\u00020\u0011\"\u0004\b\u0000\u0010z\"\u0005\b\u0001\u0010\u00012\u000e\u0010\u0001\u001a\t\u0012\u0004\u0012\u00028\u00010\u00012$\u0010k\u001a \b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u000b\u0012\t\u0012\u0004\u0012\u00028\u00010\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0001H\u0000ø\u0001\u0000¢\u0006\u0006\b\u0001\u0010\u0001J\u000f\u0010\u0001\u001a\u00020\u0001¢\u0006\u0005\b\u0001\u0010iJ\u001d\u0010\u0001\u001a\u00030\u00012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0006\b\u0001\u0010\u0001J\u001c\u0010\u0001\u001a\u00020/2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0006\b\u0001\u0010\u0001J\u0011\u0010\u0001\u001a\u00020/H\u0007¢\u0006\u0005\b\u0001\u00101J\u0011\u0010\u0001\u001a\u00020/H\u0016¢\u0006\u0005\b\u0001\u00101J$\u0010\u0001\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u0002032\b\u00104\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0006\b\u0001\u0010\u0001J\"\u0010 \u0001\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u0002032\u0006\u0010\u000e\u001a\u00020\rH\u0002¢\u0006\u0006\b \u0001\u0010¡\u0001J(\u0010¢\u0001\u001a\u0004\u0018\u00010\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u00052\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0006\b¢\u0001\u0010£\u0001J&\u0010¤\u0001\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u0002032\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0006\b¤\u0001\u0010¥\u0001J-\u0010¦\u0001\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u0002072\u0006\u0010\u0018\u001a\u0002082\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0010¢\u0006\u0006\b¦\u0001\u0010§\u0001J\u0019\u0010©\u0001\u001a\u0004\u0018\u000108*\u00030¨\u0001H\u0002¢\u0006\u0006\b©\u0001\u0010ª\u0001J\u001f\u0010«\u0001\u001a\u00020\u0011*\u00020\u00072\b\u0010 \u001a\u0004\u0018\u00010\rH\u0002¢\u0006\u0005\b«\u0001\u0010yJ&\u0010¬\u0001\u001a\u00060#j\u0002`$*\u00020\r2\n\b\u0002\u0010?\u001a\u0004\u0018\u00010/H\u0004¢\u0006\u0006\b¬\u0001\u0010­\u0001R\u001b\u0010±\u0001\u001a\t\u0012\u0004\u0012\u00020X0®\u00018F¢\u0006\b\u001a\u0006\b¯\u0001\u0010°\u0001R\u0018\u0010³\u0001\u001a\u0004\u0018\u00010\r8DX\u0004¢\u0006\u0007\u001a\u0005\b²\u0001\u0010OR\u0016\u0010µ\u0001\u001a\u00020\u00018DX\u0004¢\u0006\u0007\u001a\u0005\b´\u0001\u0010iR\u0016\u0010·\u0001\u001a\u00020\u00018PX\u0004¢\u0006\u0007\u001a\u0005\b¶\u0001\u0010iR\u0016\u0010¸\u0001\u001a\u00020\u00018VX\u0004¢\u0006\u0007\u001a\u0005\b¸\u0001\u0010iR\u0013\u0010¹\u0001\u001a\u00020\u00018F¢\u0006\u0007\u001a\u0005\b¹\u0001\u0010iR\u0013\u0010º\u0001\u001a\u00020\u00018F¢\u0006\u0007\u001a\u0005\bº\u0001\u0010iR\u0013\u0010»\u0001\u001a\u00020\u00018F¢\u0006\u0007\u001a\u0005\b»\u0001\u0010iR\u0016\u0010¼\u0001\u001a\u00020\u00018TX\u0004¢\u0006\u0007\u001a\u0005\b¼\u0001\u0010iR\u0019\u0010À\u0001\u001a\u0007\u0012\u0002\b\u00030½\u00018F¢\u0006\b\u001a\u0006\b¾\u0001\u0010¿\u0001R\u0016\u0010Â\u0001\u001a\u00020\u00018PX\u0004¢\u0006\u0007\u001a\u0005\bÁ\u0001\u0010iR\u0015\u0010Æ\u0001\u001a\u00030Ã\u00018F¢\u0006\b\u001a\u0006\bÄ\u0001\u0010Å\u0001R.\u0010Ì\u0001\u001a\u0004\u0018\u00010\u00192\t\u0010Ç\u0001\u001a\u0004\u0018\u00010\u00198@@@X\u000e¢\u0006\u0010\u001a\u0006\bÈ\u0001\u0010É\u0001\"\u0006\bÊ\u0001\u0010Ë\u0001R\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u00058@X\u0004¢\u0006\u0007\u001a\u0005\bÍ\u0001\u0010LR\u001e\u0010Ï\u0001\u001a\u0004\u0018\u00010\r*\u0004\u0018\u00010\u00058BX\u0004¢\u0006\u0007\u001a\u0005\bÎ\u0001\u0010>R\u001b\u0010Ð\u0001\u001a\u00020\u0001*\u0002038BX\u0004¢\u0006\b\u001a\u0006\bÐ\u0001\u0010Ñ\u0001\u0002\u0004\n\u0002\b\u0019¨\u0006Õ\u0001"}, d2 = {"Lkotlinx/coroutines/JobSupport;", "", "active", "<init>", "(Z)V", "", "expect", "Lkotlinx/coroutines/NodeList;", "list", "Lkotlinx/coroutines/JobNode;", "node", "addLastAtomic", "(Ljava/lang/Object;Lkotlinx/coroutines/NodeList;Lkotlinx/coroutines/JobNode;)Z", "", "rootCause", "", "exceptions", "", "addSuppressedExceptions", "(Ljava/lang/Throwable;Ljava/util/List;)V", "state", "afterCompletion", "(Ljava/lang/Object;)V", "Lkotlinx/coroutines/ChildJob;", "child", "Lkotlinx/coroutines/ChildHandle;", "attachChild", "(Lkotlinx/coroutines/ChildJob;)Lkotlinx/coroutines/ChildHandle;", "awaitInternal$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitInternal", "awaitSuspend", "cause", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "(Ljava/util/concurrent/CancellationException;)V", "cancelCoroutine", "cancelImpl$kotlinx_coroutines_core", "(Ljava/lang/Object;)Z", "cancelImpl", "cancelInternal", "(Ljava/lang/Throwable;)V", "cancelMakeCompleting", "(Ljava/lang/Object;)Ljava/lang/Object;", "cancelParent", "", "cancellationExceptionMessage", "()Ljava/lang/String;", "childCancelled", "Lkotlinx/coroutines/Incomplete;", "update", "completeStateFinalization", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;)V", "Lkotlinx/coroutines/JobSupport$Finishing;", "Lkotlinx/coroutines/ChildHandleNode;", "lastChild", "proposedUpdate", "continueCompleting", "(Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)V", "createCauseException", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "message", "Lkotlinx/coroutines/JobCancellationException;", "defaultCancellationException$kotlinx_coroutines_core", "(Ljava/lang/String;Ljava/lang/Throwable;)Lkotlinx/coroutines/JobCancellationException;", "defaultCancellationException", "finalizeFinishingState", "(Lkotlinx/coroutines/JobSupport$Finishing;Ljava/lang/Object;)Ljava/lang/Object;", "firstChild", "(Lkotlinx/coroutines/Incomplete;)Lkotlinx/coroutines/ChildHandleNode;", "getCancellationException", "()Ljava/util/concurrent/CancellationException;", "getChildJobCancellationCause", "getCompletedInternal$kotlinx_coroutines_core", "()Ljava/lang/Object;", "getCompletedInternal", "getCompletionExceptionOrNull", "()Ljava/lang/Throwable;", "getFinalRootCause", "(Lkotlinx/coroutines/JobSupport$Finishing;Ljava/util/List;)Ljava/lang/Throwable;", "getOrPromoteCancellingList", "(Lkotlinx/coroutines/Incomplete;)Lkotlinx/coroutines/NodeList;", "exception", "handleJobException", "handleOnCompletionException$kotlinx_coroutines_core", "handleOnCompletionException", "Lkotlinx/coroutines/Job;", "parent", "initParentJob", "(Lkotlinx/coroutines/Job;)V", "onCancelling", "invokeImmediately", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "handler", "Lkotlinx/coroutines/DisposableHandle;", "invokeOnCompletion", "(ZZLkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/DisposableHandle;", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/DisposableHandle;", "join", "joinInternal", "()Z", "joinSuspend", "block", "", "loopOnState", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Void;", "makeCancelling", "makeCompleting$kotlinx_coroutines_core", "makeCompleting", "makeCompletingOnce$kotlinx_coroutines_core", "makeCompletingOnce", "makeNode", "(Lkotlin/jvm/functions/Function1;Z)Lkotlinx/coroutines/JobNode;", "nameString$kotlinx_coroutines_core", "nameString", "notifyCancelling", "(Lkotlinx/coroutines/NodeList;Ljava/lang/Throwable;)V", "T", "notifyHandlers", "onCompletionInternal", "onStart", "()V", "Lkotlinx/coroutines/ParentJob;", "parentJob", "parentCancelled", "(Lkotlinx/coroutines/ParentJob;)V", "Lkotlinx/coroutines/Empty;", "promoteEmptyToNodeList", "(Lkotlinx/coroutines/Empty;)V", "promoteSingleToNodeList", "(Lkotlinx/coroutines/JobNode;)V", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/coroutines/Continuation;", "registerSelectClause0", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function1;)V", "Lkotlin/Function2;", "registerSelectClause1Internal$kotlinx_coroutines_core", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "registerSelectClause1Internal", "removeNode$kotlinx_coroutines_core", "removeNode", "selectAwaitCompletion$kotlinx_coroutines_core", "selectAwaitCompletion", "start", "", "startInternal", "(Ljava/lang/Object;)I", "stateString", "(Ljava/lang/Object;)Ljava/lang/String;", "toDebugString", "toString", "tryFinalizeSimpleState", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;)Z", "tryMakeCancelling", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Throwable;)Z", "tryMakeCompleting", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "tryMakeCompletingSlowPath", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;)Ljava/lang/Object;", "tryWaitForChild", "(Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)Z", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "nextChild", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Lkotlinx/coroutines/ChildHandleNode;", "notifyCompletion", "toCancellationException", "(Ljava/lang/Throwable;Ljava/lang/String;)Ljava/util/concurrent/CancellationException;", "Lkotlin/sequences/Sequence;", "getChildren", "()Lkotlin/sequences/Sequence;", "children", "getCompletionCause", "completionCause", "getCompletionCauseHandled", "completionCauseHandled", "getHandlesException$kotlinx_coroutines_core", "handlesException", "isActive", "isCancelled", "isCompleted", "isCompletedExceptionally", "isScopedCoroutine", "Lkotlin/coroutines/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "key", "getOnCancelComplete$kotlinx_coroutines_core", "onCancelComplete", "Lkotlinx/coroutines/selects/SelectClause0;", "getOnJoin", "()Lkotlinx/coroutines/selects/SelectClause0;", "onJoin", "value", "getParentHandle$kotlinx_coroutines_core", "()Lkotlinx/coroutines/ChildHandle;", "setParentHandle$kotlinx_coroutines_core", "(Lkotlinx/coroutines/ChildHandle;)V", "parentHandle", "getState$kotlinx_coroutines_core", "getExceptionOrNull", "exceptionOrNull", "isCancelling", "(Lkotlinx/coroutines/Incomplete;)Z", "AwaitContinuation", "ChildCompletion", "Finishing", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: JobSupport.kt */
public class g2 implements z1, v, o2 {
    private static final /* synthetic */ AtomicReferenceFieldUpdater c = AtomicReferenceFieldUpdater.newUpdater(g2.class, Object.class, "_state");
    @NotNull
    private volatile /* synthetic */ Object _parentHandle;
    @NotNull
    private volatile /* synthetic */ Object _state;

    public g2(boolean active) {
        this._state = active ? h2.g : h2.f;
        this._parentHandle = null;
    }

    public <R> R fold(R initial, @NotNull p<? super R, ? super g.b, ? extends R> operation) {
        return z1.a.b(this, initial, operation);
    }

    @Nullable
    public <E extends g.b> E get(@NotNull g.c<E> key) {
        return z1.a.c(this, key);
    }

    @NotNull
    public g minusKey(@NotNull g.c<?> key) {
        return z1.a.e(this, key);
    }

    @NotNull
    public g plus(@NotNull g context) {
        return z1.a.f(this, context);
    }

    @NotNull
    public final g.c<?> getKey() {
        return z1.g;
    }

    @l(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006H\u0016¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/internal/LockFreeLinkedListNode$makeCondAddOp$1", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "prepare", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: LockFreeLinkedList.kt */
    public static final class d extends s.b {
        final /* synthetic */ s d;
        final /* synthetic */ g2 e;
        final /* synthetic */ Object f;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(s $node, g2 g2Var, Object obj) {
            super($node);
            this.d = $node;
            this.e = g2Var;
            this.f = obj;
        }

        @Nullable
        /* renamed from: i */
        public Object g(@NotNull s affected) {
            if (this.e.l0() == this.f) {
                return null;
            }
            return r.a();
        }
    }

    @Nullable
    public final t k0() {
        return (t) this._parentHandle;
    }

    public final void G0(@Nullable t value) {
        this._parentHandle = value;
    }

    /* access modifiers changed from: protected */
    public final void o0(@Nullable z1 parent) {
        if (s0.a()) {
            if (!(k0() == null)) {
                throw new AssertionError();
            }
        }
        if (parent == null) {
            G0(m2.c);
            return;
        }
        parent.start();
        t handle = parent.T(this);
        G0(handle);
        if (I()) {
            handle.dispose();
            G0(m2.c);
        }
    }

    @Nullable
    public final Object l0() {
        while (true) {
            Object state = this._state;
            if (!(state instanceof a0)) {
                return state;
            }
            ((a0) state).c(this);
        }
    }

    public boolean isActive() {
        Object state = l0();
        return (state instanceof u1) && ((u1) state).isActive();
    }

    public final boolean I() {
        return !(l0() instanceof u1);
    }

    public final boolean isCancelled() {
        Object state = l0();
        return (state instanceof b0) || ((state instanceof c) && ((c) state).f());
    }

    private final Object c0(c state, Object proposedUpdate) {
        boolean wasCancelling;
        Throwable finalCause;
        boolean handled = true;
        if (s0.a()) {
            if ((l0() == state ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (s0.a() && (state.h() ^ 1) == 0) {
            throw new AssertionError();
        } else if (!s0.a() || state.g() != 0) {
            b0 b0Var = proposedUpdate instanceof b0 ? (b0) proposedUpdate : null;
            Throwable proposedException = b0Var == null ? null : b0Var.b;
            synchronized (state) {
                wasCancelling = state.f();
                List exceptions = state.i(proposedException);
                finalCause = g0(state, exceptions);
                if (finalCause != null) {
                    M(finalCause, exceptions);
                }
            }
            Throwable finalException = finalCause;
            Object finalState = (finalException == null || finalException == proposedException) ? proposedUpdate : new b0(finalException, false, 2, (DefaultConstructorMarker) null);
            if (finalException != null) {
                if (!W(finalException) && !m0(finalException)) {
                    handled = false;
                }
                if (handled) {
                    if (finalState != null) {
                        ((b0) finalState).b();
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.CompletedExceptionally");
                    }
                }
            }
            if (!wasCancelling) {
                A0(finalException);
            }
            B0(finalState);
            boolean casSuccess = c.compareAndSet(this, state, h2.g(finalState));
            if (!s0.a() || casSuccess) {
                Z(state, finalState);
                return finalState;
            }
            throw new AssertionError();
        } else {
            throw new AssertionError();
        }
    }

    private final Throwable g0(c state, List<? extends Throwable> exceptions) {
        Iterable element$iv;
        Throwable it;
        T t = null;
        if (!exceptions.isEmpty()) {
            Iterator<T> it2 = exceptions.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    element$iv = null;
                    break;
                }
                element$iv = it2.next();
                if (!(((Throwable) element$iv) instanceof CancellationException)) {
                    break;
                }
            }
            Throwable firstNonCancellation = (Throwable) element$iv;
            if (firstNonCancellation != null) {
                return firstNonCancellation;
            }
            Throwable first = (Throwable) exceptions.get(0);
            if (first instanceof TimeoutCancellationException) {
                Iterator<T> it3 = exceptions.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        break;
                    }
                    T next = it3.next();
                    Throwable it4 = (Throwable) next;
                    if (it4 == first || !(it4 instanceof TimeoutCancellationException)) {
                        it = null;
                        continue;
                    } else {
                        it = 1;
                        continue;
                    }
                    if (it != null) {
                        t = next;
                        break;
                    }
                }
                Throwable detailedTimeoutException = (Throwable) t;
                if (detailedTimeoutException != null) {
                    return detailedTimeoutException;
                }
            }
            return first;
        } else if (state.f()) {
            return new JobCancellationException(X(), (Throwable) null, this);
        } else {
            return null;
        }
    }

    private final void M(Throwable rootCause, List<? extends Throwable> exceptions) {
        if (exceptions.size() > 1) {
            Set seenExceptions = Collections.newSetFromMap(new IdentityHashMap(exceptions.size()));
            Throwable unwrappedCause = !s0.d() ? rootCause : e0.n(rootCause);
            for (Throwable exception : exceptions) {
                Throwable unwrapped = !s0.d() ? exception : e0.n(exception);
                if (unwrapped != rootCause && unwrapped != unwrappedCause && !(unwrapped instanceof CancellationException) && seenExceptions.add(unwrapped)) {
                    kotlin.b.a(rootCause, unwrapped);
                }
            }
        }
    }

    private final boolean M0(u1 state, Object update) {
        if (s0.a()) {
            if ((((state instanceof i1) || (state instanceof f2)) ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (s0.a() && ((update instanceof b0) ^ 1) == 0) {
            throw new AssertionError();
        } else if (!c.compareAndSet(this, state, h2.g(update))) {
            return false;
        } else {
            A0((Throwable) null);
            B0(update);
            Z(state, update);
            return true;
        }
    }

    private final void Z(u1 state, Object update) {
        t it = k0();
        if (it != null) {
            it.dispose();
            G0(m2.c);
        }
        Throwable th = null;
        b0 b0Var = update instanceof b0 ? (b0) update : null;
        if (b0Var != null) {
            th = b0Var.b;
        }
        Throwable cause = th;
        if (state instanceof f2) {
            try {
                ((f2) state).y(cause);
            } catch (Throwable ex) {
                n0(new CompletionHandlerException("Exception in completion handler " + state + " for " + this, ex));
            }
        } else {
            l2 b2 = state.b();
            if (b2 != null) {
                z0(b2, cause);
            }
        }
    }

    private final void y0(l2 list, Throwable cause) {
        Throwable $this$notifyHandlers_u24lambda_u2d14_u24lambda_u2d12$iv;
        Throwable th = cause;
        A0(th);
        q this_$iv$iv = list;
        Throwable it$iv = null;
        for (s cur$iv$iv = (s) this_$iv$iv.m(); !k.a(cur$iv$iv, this_$iv$iv); cur$iv$iv = cur$iv$iv.n()) {
            if (cur$iv$iv instanceof a2) {
                f2 node$iv = (f2) cur$iv$iv;
                try {
                    node$iv.y(th);
                } catch (Throwable th2) {
                    Throwable ex$iv = th2;
                    if (it$iv == null) {
                        $this$notifyHandlers_u24lambda_u2d14_u24lambda_u2d12$iv = null;
                    } else {
                        $this$notifyHandlers_u24lambda_u2d14_u24lambda_u2d12$iv = it$iv;
                        kotlin.b.a($this$notifyHandlers_u24lambda_u2d14_u24lambda_u2d12$iv, ex$iv);
                    }
                    if ($this$notifyHandlers_u24lambda_u2d14_u24lambda_u2d12$iv == null) {
                        it$iv = new CompletionHandlerException("Exception in completion handler " + node$iv + " for " + this, ex$iv);
                    }
                }
            }
        }
        if (it$iv != null) {
            n0(it$iv);
        }
        W(th);
    }

    private final boolean W(Throwable cause) {
        if (p0()) {
            return true;
        }
        boolean isCancellation = cause instanceof CancellationException;
        t parent = k0();
        if (parent == null || parent == m2.c) {
            return isCancellation;
        }
        if (parent.c(cause) || isCancellation) {
            return true;
        }
        return false;
    }

    private final void z0(l2 $this$notifyCompletion, Throwable cause) {
        Throwable $this$notifyHandlers_u24lambda_u2d14_u24lambda_u2d12$iv;
        q this_$iv$iv = $this$notifyCompletion;
        Throwable it$iv = null;
        for (s cur$iv$iv = (s) this_$iv$iv.m(); !k.a(cur$iv$iv, this_$iv$iv); cur$iv$iv = cur$iv$iv.n()) {
            if (cur$iv$iv instanceof f2) {
                f2 node$iv = (f2) cur$iv$iv;
                try {
                    node$iv.y(cause);
                } catch (Throwable th) {
                    Throwable ex$iv = th;
                    if (it$iv == null) {
                        $this$notifyHandlers_u24lambda_u2d14_u24lambda_u2d12$iv = null;
                    } else {
                        $this$notifyHandlers_u24lambda_u2d14_u24lambda_u2d12$iv = it$iv;
                        kotlin.b.a($this$notifyHandlers_u24lambda_u2d14_u24lambda_u2d12$iv, ex$iv);
                    }
                    if ($this$notifyHandlers_u24lambda_u2d14_u24lambda_u2d12$iv == null) {
                        it$iv = new CompletionHandlerException("Exception in completion handler " + node$iv + " for " + this, ex$iv);
                    }
                }
            } else {
                Throwable th2 = cause;
            }
        }
        Throwable th3 = cause;
        if (it$iv != null) {
            n0(it$iv);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:1:0x0002 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean start() {
        /*
            r5 = this;
            r0 = r5
            r1 = 0
        L_0x0002:
            java.lang.Object r2 = r0.l0()
            r3 = 0
            int r4 = r5.H0(r2)
            switch(r4) {
                case 0: goto L_0x0013;
                case 1: goto L_0x0011;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x0002
        L_0x0011:
            r4 = 1
            return r4
        L_0x0013:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.g2.start():boolean");
    }

    private final int H0(Object state) {
        if (state instanceof i1) {
            if (((i1) state).isActive()) {
                return 0;
            }
            if (!c.compareAndSet(this, state, h2.g)) {
                return -1;
            }
            C0();
            return 1;
        } else if (!(state instanceof t1)) {
            return 0;
        } else {
            if (!c.compareAndSet(this, state, ((t1) state).b())) {
                return -1;
            }
            C0();
            return 1;
        }
    }

    /* access modifiers changed from: protected */
    public void C0() {
    }

    @NotNull
    public final CancellationException n() {
        Object state = l0();
        CancellationException cancellationException = null;
        if (state instanceof c) {
            Throwable e2 = ((c) state).e();
            if (e2 != null) {
                cancellationException = J0(e2, k.l(t0.a(this), " is cancelling"));
            }
            if (cancellationException != null) {
                return cancellationException;
            }
            throw new IllegalStateException(k.l("Job is still new or active: ", this).toString());
        } else if (state instanceof u1) {
            throw new IllegalStateException(k.l("Job is still new or active: ", this).toString());
        } else if (state instanceof b0) {
            return K0(this, ((b0) state).b, (String) null, 1, (Object) null);
        } else {
            return new JobCancellationException(k.l(t0.a(this), " has completed normally"), (Throwable) null, this);
        }
    }

    public static /* synthetic */ CancellationException K0(g2 g2Var, Throwable th, String str, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                str = null;
            }
            return g2Var.J0(th, str);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toCancellationException");
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final CancellationException J0(@NotNull Throwable $this$toCancellationException, @Nullable String message) {
        CancellationException cancellationException = $this$toCancellationException instanceof CancellationException ? (CancellationException) $this$toCancellationException : null;
        if (cancellationException != null) {
            return cancellationException;
        }
        return new JobCancellationException(message == null ? X() : message, $this$toCancellationException, this);
    }

    @NotNull
    public final f1 t(@NotNull kotlin.jvm.functions.l<? super Throwable, x> handler) {
        return m(false, true, handler);
    }

    @NotNull
    public final f1 m(boolean onCancelling, boolean invokeImmediately, @NotNull kotlin.jvm.functions.l<? super Throwable, x> handler) {
        boolean z = onCancelling;
        f2 node = v0(handler, z);
        while (true) {
            Object state = l0();
            if (state instanceof i1) {
                if (!((i1) state).isActive()) {
                    D0((i1) state);
                } else if (c.compareAndSet(this, state, node)) {
                    return node;
                }
            } else if (state instanceof u1) {
                l2 list = ((u1) state).b();
                if (list != null) {
                    Object rootCause = null;
                    f1 f1Var = m2.c;
                    if (z && (state instanceof c)) {
                        synchronized (state) {
                            rootCause = ((c) state).e();
                            if (rootCause == null || ((handler instanceof u) && !((c) state).g())) {
                                if (L(state, list, node)) {
                                    if (rootCause == null) {
                                        return node;
                                    }
                                    f1Var = node;
                                }
                            }
                            x xVar = x.a;
                        }
                    }
                    if (rootCause != null) {
                        if (invokeImmediately) {
                            handler.invoke(rootCause);
                        }
                        return f1Var;
                    } else if (L(state, list, node)) {
                        return node;
                    }
                } else if (state != null) {
                    E0((f2) state);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.JobNode");
                }
            } else {
                if (invokeImmediately) {
                    Throwable cause$iv = null;
                    b0 b0Var = state instanceof b0 ? (b0) state : null;
                    if (b0Var != null) {
                        cause$iv = b0Var.b;
                    }
                    handler.invoke(cause$iv);
                }
                return m2.c;
            }
        }
    }

    private final f2 v0(kotlin.jvm.functions.l<? super Throwable, x> handler, boolean onCancelling) {
        f2 it = null;
        if (onCancelling) {
            if (handler instanceof a2) {
                it = (a2) handler;
            }
            if (it == null) {
                it = new x1(handler);
            }
        } else {
            f2 f2Var = handler instanceof f2 ? (f2) handler : null;
            if (f2Var != null) {
                f2 it2 = f2Var;
                if (!s0.a() || ((it2 instanceof a2) ^ 1) != 0) {
                    it = f2Var;
                } else {
                    throw new AssertionError();
                }
            }
            if (it == null) {
                it = new y1(handler);
            }
        }
        it.A(this);
        return it;
    }

    /* JADX WARNING: Removed duplicated region for block: B:1:0x000b A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean L(java.lang.Object r6, kotlinx.coroutines.l2 r7, kotlinx.coroutines.f2 r8) {
        /*
            r5 = this;
            r0 = r7
            r1 = 0
            r2 = r0
            r3 = 0
            kotlinx.coroutines.g2$d r4 = new kotlinx.coroutines.g2$d
            r4.<init>(r8, r5, r6)
            r2 = r4
        L_0x000b:
            kotlinx.coroutines.internal.s r3 = r0.p()
            int r4 = r3.x(r8, r0, r2)
            switch(r4) {
                case 1: goto L_0x001a;
                case 2: goto L_0x0018;
                default: goto L_0x0017;
            }
        L_0x0017:
            goto L_0x000b
        L_0x0018:
            r4 = 0
            goto L_0x001b
        L_0x001a:
            r4 = 1
        L_0x001b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.g2.L(java.lang.Object, kotlinx.coroutines.l2, kotlinx.coroutines.f2):boolean");
    }

    private final void D0(i1 state) {
        l2 list = new l2();
        c.compareAndSet(this, state, state.isActive() ? list : new t1(list));
    }

    private final void E0(f2 state) {
        state.i(new l2());
        c.compareAndSet(this, state, state.n());
    }

    @Nullable
    public final Object J(@NotNull kotlin.coroutines.d<? super x> $completion) {
        if (!q0()) {
            c2.i($completion.getContext());
            return x.a;
        }
        Object r0 = r0($completion);
        return r0 == kotlin.coroutines.intrinsics.c.d() ? r0 : x.a;
    }

    private final boolean q0() {
        Object state;
        do {
            state = l0();
            if (!(state instanceof u1)) {
                return false;
            }
        } while (H0(state) < 0);
        return true;
    }

    private final Object r0(kotlin.coroutines.d<? super x> $completion) {
        o cancellable$iv = new o(kotlin.coroutines.intrinsics.b.c($completion), 1);
        cancellable$iv.w();
        n cont = cancellable$iv;
        q.a(cont, t(new r2(cont)));
        Object t = cancellable$iv.t();
        if (t == kotlin.coroutines.intrinsics.c.d()) {
            h.c($completion);
        }
        return t == kotlin.coroutines.intrinsics.c.d() ? t : x.a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x001f A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x000d A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void F0(@org.jetbrains.annotations.NotNull kotlinx.coroutines.f2 r7) {
        /*
            r6 = this;
            r0 = r6
            r1 = 0
        L_0x0002:
            java.lang.Object r2 = r0.l0()
            r3 = 0
            boolean r4 = r2 instanceof kotlinx.coroutines.f2
            if (r4 == 0) goto L_0x001f
            if (r2 == r7) goto L_0x0010
            return
        L_0x0010:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = c
            kotlinx.coroutines.i1 r5 = kotlinx.coroutines.h2.g
            boolean r4 = r4.compareAndSet(r6, r2, r5)
            if (r4 == 0) goto L_0x001d
            return
        L_0x001d:
            goto L_0x0002
        L_0x001f:
            boolean r4 = r2 instanceof kotlinx.coroutines.u1
            if (r4 == 0) goto L_0x0030
            r4 = r2
            kotlinx.coroutines.u1 r4 = (kotlinx.coroutines.u1) r4
            kotlinx.coroutines.l2 r4 = r4.b()
            if (r4 == 0) goto L_0x002f
            r7.t()
        L_0x002f:
            return
        L_0x0030:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.g2.F0(kotlinx.coroutines.f2):void");
    }

    public boolean i0() {
        return false;
    }

    public void c(@Nullable CancellationException cause) {
        CancellationException cancellationException;
        if (cause == null) {
            cancellationException = new JobCancellationException(X(), (Throwable) null, this);
        } else {
            cancellationException = cause;
        }
        U(cancellationException);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public String X() {
        return "Job was cancelled";
    }

    public void U(@NotNull Throwable cause) {
        S(cause);
    }

    public final void s(@NotNull o2 parentJob) {
        S(parentJob);
    }

    public boolean Y(@NotNull Throwable cause) {
        if (cause instanceof CancellationException) {
            return true;
        }
        if (!S(cause) || !h0()) {
            return false;
        }
        return true;
    }

    public final boolean R(@Nullable Throwable cause) {
        return S(cause);
    }

    public final boolean S(@Nullable Object cause) {
        Object finalState = h2.a;
        if (i0() && (finalState = V(cause)) == h2.b) {
            return true;
        }
        if (finalState == h2.a) {
            finalState = s0(cause);
        }
        if (finalState == h2.a || finalState == h2.b) {
            return true;
        }
        if (finalState == h2.d) {
            return false;
        }
        N(finalState);
        return true;
    }

    private final Object V(Object cause) {
        Object finalState;
        do {
            Object state = l0();
            if (!(state instanceof u1) || ((state instanceof c) && ((c) state).g())) {
                return h2.a;
            }
            finalState = O0(state, new b0(b0(cause), false, 2, (DefaultConstructorMarker) null));
        } while (finalState == h2.c);
        return finalState;
    }

    @NotNull
    public CancellationException z() {
        Throwable rootCause;
        Object state = l0();
        CancellationException cancellationException = null;
        if (state instanceof c) {
            rootCause = ((c) state).e();
        } else if (state instanceof b0) {
            rootCause = ((b0) state).b;
        } else if (!(state instanceof u1)) {
            rootCause = null;
        } else {
            throw new IllegalStateException(k.l("Cannot be cancelling child in this state: ", state).toString());
        }
        if (rootCause instanceof CancellationException) {
            cancellationException = (CancellationException) rootCause;
        }
        return cancellationException == null ? new JobCancellationException(k.l("Parent job is ", I0(state)), rootCause, this) : cancellationException;
    }

    private final Throwable b0(Object cause) {
        if (cause == null ? true : cause instanceof Throwable) {
            Throwable th = (Throwable) cause;
            if (th == null) {
                return new JobCancellationException(X(), (Throwable) null, this);
            }
            return th;
        } else if (cause != null) {
            return ((o2) cause).z();
        } else {
            throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.ParentJob");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0052, code lost:
        r5 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0054, code lost:
        if (r5 != null) goto L_0x0057;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0057, code lost:
        y0(((kotlinx.coroutines.g2.c) r3).b(), r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0067, code lost:
        return kotlinx.coroutines.h2.a();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object s0(java.lang.Object r15) {
        /*
            r14 = this;
            r0 = 0
            r1 = r14
            r2 = 0
        L_0x0003:
            java.lang.Object r3 = r1.l0()
            r4 = 0
            boolean r5 = r3 instanceof kotlinx.coroutines.g2.c
            r6 = 0
            r7 = 0
            if (r5 == 0) goto L_0x006e
            r5 = 0
            monitor-enter(r3)
            r8 = 0
            r9 = r3
            kotlinx.coroutines.g2$c r9 = (kotlinx.coroutines.g2.c) r9     // Catch:{ all -> 0x006b }
            boolean r9 = r9.h()     // Catch:{ all -> 0x006b }
            if (r9 == 0) goto L_0x0022
            kotlinx.coroutines.internal.f0 r6 = kotlinx.coroutines.h2.d     // Catch:{ all -> 0x006b }
            monitor-exit(r3)
            return r6
        L_0x0022:
            r9 = r3
            kotlinx.coroutines.g2$c r9 = (kotlinx.coroutines.g2.c) r9     // Catch:{ all -> 0x006b }
            boolean r9 = r9.f()     // Catch:{ all -> 0x006b }
            if (r15 != 0) goto L_0x002d
            if (r9 != 0) goto L_0x0042
        L_0x002d:
            if (r0 != 0) goto L_0x003a
            java.lang.Throwable r10 = r14.b0(r15)     // Catch:{ all -> 0x006b }
            r11 = r10
            r12 = 0
            r0 = r11
            r13 = r10
            r10 = r0
            r0 = r13
            goto L_0x003b
        L_0x003a:
            r10 = r0
        L_0x003b:
            r11 = r3
            kotlinx.coroutines.g2$c r11 = (kotlinx.coroutines.g2.c) r11     // Catch:{ all -> 0x0068 }
            r11.a(r0)     // Catch:{ all -> 0x0068 }
            r0 = r10
        L_0x0042:
            r10 = r3
            kotlinx.coroutines.g2$c r10 = (kotlinx.coroutines.g2.c) r10     // Catch:{ all -> 0x006b }
            java.lang.Throwable r10 = r10.e()     // Catch:{ all -> 0x006b }
            r11 = r10
            r12 = 0
            if (r9 != 0) goto L_0x004e
            r7 = 1
        L_0x004e:
            if (r7 == 0) goto L_0x0051
            r6 = r10
        L_0x0051:
            monitor-exit(r3)
            r5 = r6
            if (r5 != 0) goto L_0x0057
            goto L_0x0063
        L_0x0057:
            r6 = r5
            r7 = 0
            r8 = r3
            kotlinx.coroutines.g2$c r8 = (kotlinx.coroutines.g2.c) r8
            kotlinx.coroutines.l2 r8 = r8.b()
            r14.y0(r8, r6)
        L_0x0063:
            kotlinx.coroutines.internal.f0 r6 = kotlinx.coroutines.h2.a
            return r6
        L_0x0068:
            r6 = move-exception
            r0 = r10
            goto L_0x006c
        L_0x006b:
            r6 = move-exception
        L_0x006c:
            monitor-exit(r3)
            throw r6
        L_0x006e:
            boolean r5 = r3 instanceof kotlinx.coroutines.u1
            if (r5 == 0) goto L_0x00c4
            if (r0 != 0) goto L_0x007f
            java.lang.Throwable r5 = r14.b0(r15)
            r8 = r5
            r9 = 0
            r0 = r8
            r13 = r5
            r5 = r0
            r0 = r13
            goto L_0x0080
        L_0x007f:
            r5 = r0
        L_0x0080:
            r8 = r3
            kotlinx.coroutines.u1 r8 = (kotlinx.coroutines.u1) r8
            boolean r8 = r8.isActive()
            if (r8 == 0) goto L_0x0099
            r6 = r3
            kotlinx.coroutines.u1 r6 = (kotlinx.coroutines.u1) r6
            boolean r6 = r14.N0(r6, r0)
            if (r6 == 0) goto L_0x0097
            kotlinx.coroutines.internal.f0 r6 = kotlinx.coroutines.h2.a
            return r6
        L_0x0097:
            goto L_0x00b0
        L_0x0099:
            kotlinx.coroutines.b0 r8 = new kotlinx.coroutines.b0
            r9 = 2
            r8.<init>(r0, r7, r9, r6)
            java.lang.Object r6 = r14.O0(r3, r8)
            kotlinx.coroutines.internal.f0 r7 = kotlinx.coroutines.h2.a
            if (r6 == r7) goto L_0x00b4
            kotlinx.coroutines.internal.f0 r7 = kotlinx.coroutines.h2.c
            if (r6 != r7) goto L_0x00b3
        L_0x00b0:
            r0 = r5
            goto L_0x0003
        L_0x00b3:
            return r6
        L_0x00b4:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "Cannot happen in "
            java.lang.String r8 = kotlin.jvm.internal.k.l(r8, r3)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x00c4:
            kotlinx.coroutines.internal.f0 r5 = kotlinx.coroutines.h2.d
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.g2.s0(java.lang.Object):java.lang.Object");
    }

    private final l2 j0(u1 state) {
        l2 b2 = state.b();
        if (b2 != null) {
            return b2;
        }
        if (state instanceof i1) {
            return new l2();
        }
        if (state instanceof f2) {
            E0((f2) state);
            return null;
        }
        throw new IllegalStateException(k.l("State should have list: ", state).toString());
    }

    private final boolean N0(u1 state, Throwable rootCause) {
        if (s0.a() && ((state instanceof c) ^ 1) == 0) {
            throw new AssertionError();
        } else if (!s0.a() || state.isActive() != 0) {
            l2 list = j0(state);
            if (list == null) {
                return false;
            }
            if (!c.compareAndSet(this, state, new c(list, false, rootCause))) {
                return false;
            }
            y0(list, rootCause);
            return true;
        } else {
            throw new AssertionError();
        }
    }

    public final boolean t0(@Nullable Object proposedUpdate) {
        Object finalState;
        do {
            finalState = O0(l0(), proposedUpdate);
            if (finalState == h2.a) {
                return false;
            }
            if (finalState == h2.b) {
                return true;
            }
        } while (finalState == h2.c);
        N(finalState);
        return true;
    }

    @Nullable
    public final Object u0(@Nullable Object proposedUpdate) {
        Object finalState;
        do {
            finalState = O0(l0(), proposedUpdate);
            if (finalState == h2.a) {
                throw new IllegalStateException("Job " + this + " is already complete or completing, but is being completed with " + proposedUpdate, f0(proposedUpdate));
            }
        } while (finalState == h2.c);
        return finalState;
    }

    private final Object O0(Object state, Object proposedUpdate) {
        if (!(state instanceof u1)) {
            return h2.a;
        }
        if ((!(state instanceof i1) && !(state instanceof f2)) || (state instanceof u) || (proposedUpdate instanceof b0)) {
            return P0((u1) state, proposedUpdate);
        }
        if (M0((u1) state, proposedUpdate)) {
            return proposedUpdate;
        }
        return h2.c;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x007f, code lost:
        if (r4 != null) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0082, code lost:
        y0(r0, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0087, code lost:
        r2 = d0(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x008b, code lost:
        if (r2 == null) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0091, code lost:
        if (Q0(r1, r2, r14) == false) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0095, code lost:
        return kotlinx.coroutines.h2.b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x009a, code lost:
        return c0(r1, r14);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object P0(kotlinx.coroutines.u1 r13, java.lang.Object r14) {
        /*
            r12 = this;
            kotlinx.coroutines.l2 r0 = r12.j0(r13)
            if (r0 != 0) goto L_0x000b
            kotlinx.coroutines.internal.f0 r0 = kotlinx.coroutines.h2.c
            return r0
        L_0x000b:
            boolean r1 = r13 instanceof kotlinx.coroutines.g2.c
            r2 = 0
            if (r1 == 0) goto L_0x0014
            r1 = r13
            kotlinx.coroutines.g2$c r1 = (kotlinx.coroutines.g2.c) r1
            goto L_0x0015
        L_0x0014:
            r1 = r2
        L_0x0015:
            r3 = 0
            if (r1 != 0) goto L_0x001d
            kotlinx.coroutines.g2$c r1 = new kotlinx.coroutines.g2$c
            r1.<init>(r0, r3, r2)
        L_0x001d:
            r4 = 0
            r5 = 0
            monitor-enter(r1)
            r6 = 0
            boolean r7 = r1.g()     // Catch:{ all -> 0x009b }
            if (r7 == 0) goto L_0x002d
            kotlinx.coroutines.internal.f0 r2 = kotlinx.coroutines.h2.a     // Catch:{ all -> 0x009b }
            monitor-exit(r1)
            return r2
        L_0x002d:
            r7 = 1
            r1.j(r7)     // Catch:{ all -> 0x009b }
            if (r1 == r13) goto L_0x0041
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r8 = c     // Catch:{ all -> 0x009b }
            boolean r8 = r8.compareAndSet(r12, r13, r1)     // Catch:{ all -> 0x009b }
            if (r8 != 0) goto L_0x0041
            kotlinx.coroutines.internal.f0 r2 = kotlinx.coroutines.h2.c     // Catch:{ all -> 0x009b }
            monitor-exit(r1)
            return r2
        L_0x0041:
            boolean r8 = kotlinx.coroutines.s0.a()     // Catch:{ all -> 0x009b }
            if (r8 == 0) goto L_0x0057
            r8 = 0
            boolean r9 = r1.h()     // Catch:{ all -> 0x009b }
            r8 = r9 ^ 1
            if (r8 == 0) goto L_0x0051
            goto L_0x0057
        L_0x0051:
            java.lang.AssertionError r2 = new java.lang.AssertionError     // Catch:{ all -> 0x009b }
            r2.<init>()     // Catch:{ all -> 0x009b }
            throw r2     // Catch:{ all -> 0x009b }
        L_0x0057:
            boolean r8 = r1.f()     // Catch:{ all -> 0x009b }
            boolean r9 = r14 instanceof kotlinx.coroutines.b0     // Catch:{ all -> 0x009b }
            if (r9 == 0) goto L_0x0063
            r9 = r14
            kotlinx.coroutines.b0 r9 = (kotlinx.coroutines.b0) r9     // Catch:{ all -> 0x009b }
            goto L_0x0064
        L_0x0063:
            r9 = r2
        L_0x0064:
            if (r9 != 0) goto L_0x0067
            goto L_0x006d
        L_0x0067:
            r10 = 0
            java.lang.Throwable r11 = r9.b     // Catch:{ all -> 0x009b }
            r1.a(r11)     // Catch:{ all -> 0x009b }
        L_0x006d:
            java.lang.Throwable r9 = r1.e()     // Catch:{ all -> 0x009b }
            r10 = r9
            r11 = 0
            if (r8 != 0) goto L_0x0076
            r3 = r7
        L_0x0076:
            if (r3 == 0) goto L_0x0079
            r2 = r9
        L_0x0079:
            r4 = r2
            kotlin.x r2 = kotlin.x.a     // Catch:{ all -> 0x009b }
            monitor-exit(r1)
            if (r4 != 0) goto L_0x0082
            goto L_0x0087
        L_0x0082:
            r2 = r4
            r3 = 0
            r12.y0(r0, r2)
        L_0x0087:
            kotlinx.coroutines.u r2 = r12.d0(r13)
            if (r2 == 0) goto L_0x0096
            boolean r3 = r12.Q0(r1, r2, r14)
            if (r3 == 0) goto L_0x0096
            kotlinx.coroutines.internal.f0 r3 = kotlinx.coroutines.h2.b
            return r3
        L_0x0096:
            java.lang.Object r3 = r12.c0(r1, r14)
            return r3
        L_0x009b:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.g2.P0(kotlinx.coroutines.u1, java.lang.Object):java.lang.Object");
    }

    private final Throwable f0(Object $this$exceptionOrNull) {
        b0 b0Var = $this$exceptionOrNull instanceof b0 ? (b0) $this$exceptionOrNull : null;
        if (b0Var == null) {
            return null;
        }
        return b0Var.b;
    }

    private final u d0(u1 state) {
        u uVar = state instanceof u ? (u) state : null;
        if (uVar != null) {
            return uVar;
        }
        l2 b2 = state.b();
        if (b2 == null) {
            return null;
        }
        return x0(b2);
    }

    private final boolean Q0(c state, u child, Object proposedUpdate) {
        u uVar = child;
        while (z1.a.d(uVar.x, false, false, new b(this, state, uVar, proposedUpdate), 1, (Object) null) == m2.c) {
            uVar = x0(uVar);
            if (uVar == null) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final void a0(c state, u lastChild, Object proposedUpdate) {
        if (s0.a()) {
            if (!(l0() == state)) {
                throw new AssertionError();
            }
        }
        u waitChild = x0(lastChild);
        if (waitChild == null || !Q0(state, waitChild, proposedUpdate)) {
            N(c0(state, proposedUpdate));
        }
    }

    private final u x0(s $this$nextChild) {
        s cur = $this$nextChild;
        while (cur.s()) {
            cur = cur.p();
        }
        while (true) {
            cur = cur.n();
            if (!cur.s()) {
                if (cur instanceof u) {
                    return (u) cur;
                }
                if (cur instanceof l2) {
                    return null;
                }
            }
        }
    }

    @l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H@"}, d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", "Lkotlinx/coroutines/Job;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.JobSupport$children$1", f = "JobSupport.kt", l = {952, 954}, m = "invokeSuspend")
    /* compiled from: JobSupport.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.k implements p<j<? super z1>, kotlin.coroutines.d<? super x>, Object> {
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ g2 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(g2 g2Var, kotlin.coroutines.d<? super e> dVar) {
            super(2, dVar);
            this.this$0 = g2Var;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            e eVar = new e(this.this$0, dVar);
            eVar.L$0 = obj;
            return eVar;
        }

        @Nullable
        public final Object invoke(@NotNull j<? super z1> jVar, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((e) create(jVar, dVar)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x004f, code lost:
            r2 = r1;
            r1 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0073, code lost:
            if (kotlin.jvm.internal.k.a(r5, r6) != 0) goto L_0x0096;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0077, code lost:
            if ((r5 instanceof kotlinx.coroutines.u) == false) goto L_0x0091;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0079, code lost:
            r9 = ((kotlinx.coroutines.u) r5).x;
            r1.L$0 = r7;
            r1.L$1 = r6;
            r1.L$2 = r5;
            r1.label = 2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x008c, code lost:
            if (r7.g(r9, r1) != r0) goto L_0x008f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x008e, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0091, code lost:
            r5 = r5.n();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0096, code lost:
            r2 = r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x009b, code lost:
            return kotlin.x.a;
         */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r13) {
            /*
                r12 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r12.label
                switch(r1) {
                    case 0: goto L_0x002c;
                    case 1: goto L_0x0026;
                    case 2: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r13.<init>(r0)
                throw r13
            L_0x0011:
                r1 = r12
                r2 = 0
                r3 = 0
                r4 = 0
                java.lang.Object r5 = r1.L$2
                kotlinx.coroutines.internal.s r5 = (kotlinx.coroutines.internal.s) r5
                java.lang.Object r6 = r1.L$1
                kotlinx.coroutines.internal.q r6 = (kotlinx.coroutines.internal.q) r6
                java.lang.Object r7 = r1.L$0
                kotlin.sequences.j r7 = (kotlin.sequences.j) r7
                kotlin.p.b(r13)
                goto L_0x0090
            L_0x0026:
                r0 = r12
                kotlin.p.b(r13)
                r1 = 0
                goto L_0x004f
            L_0x002c:
                kotlin.p.b(r13)
                r1 = r12
                java.lang.Object r2 = r1.L$0
                kotlin.sequences.j r2 = (kotlin.sequences.j) r2
                kotlinx.coroutines.g2 r3 = r1.this$0
                java.lang.Object r3 = r3.l0()
                boolean r4 = r3 instanceof kotlinx.coroutines.u
                if (r4 == 0) goto L_0x0052
                r4 = r3
                kotlinx.coroutines.u r4 = (kotlinx.coroutines.u) r4
                kotlinx.coroutines.v r4 = r4.x
                r5 = 1
                r1.label = r5
                java.lang.Object r3 = r2.g(r4, r1)
                if (r3 != r0) goto L_0x004d
                return r0
            L_0x004d:
                r0 = r1
                r1 = r2
            L_0x004f:
                r2 = r1
                r1 = r0
                goto L_0x0099
            L_0x0052:
                boolean r4 = r3 instanceof kotlinx.coroutines.u1
                if (r4 == 0) goto L_0x0099
                r4 = r3
                kotlinx.coroutines.u1 r4 = (kotlinx.coroutines.u1) r4
                kotlinx.coroutines.l2 r3 = r4.b()
                if (r3 != 0) goto L_0x0060
                goto L_0x0099
            L_0x0060:
                r4 = 0
                r5 = 0
                java.lang.Object r6 = r3.m()
                kotlinx.coroutines.internal.s r6 = (kotlinx.coroutines.internal.s) r6
                r7 = r2
                r2 = r4
                r11 = r6
                r6 = r3
                r3 = r5
                r5 = r11
            L_0x006f:
                boolean r4 = kotlin.jvm.internal.k.a(r5, r6)
                if (r4 != 0) goto L_0x0096
                boolean r4 = r5 instanceof kotlinx.coroutines.u
                if (r4 == 0) goto L_0x0091
                r4 = r5
                kotlinx.coroutines.u r4 = (kotlinx.coroutines.u) r4
                r8 = 0
                kotlinx.coroutines.v r9 = r4.x
                r1.L$0 = r7
                r1.L$1 = r6
                r1.L$2 = r5
                r10 = 2
                r1.label = r10
                java.lang.Object r4 = r7.g(r9, r1)
                if (r4 != r0) goto L_0x008f
                return r0
            L_0x008f:
                r4 = r8
            L_0x0090:
            L_0x0091:
                kotlinx.coroutines.internal.s r5 = r5.n()
                goto L_0x006f
            L_0x0096:
                r2 = r7
            L_0x0099:
                kotlin.x r0 = kotlin.x.a
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.g2.e.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @NotNull
    public final kotlin.sequences.h<z1> getChildren() {
        return kotlin.sequences.k.b(new e(this, (kotlin.coroutines.d<? super e>) null));
    }

    @NotNull
    public final t T(@NotNull v child) {
        return (t) z1.a.d(this, true, false, new u(child), 2, (Object) null);
    }

    public void n0(@NotNull Throwable exception) {
        throw exception;
    }

    /* access modifiers changed from: protected */
    public void A0(@Nullable Throwable cause) {
    }

    /* access modifiers changed from: protected */
    public boolean p0() {
        return false;
    }

    public boolean h0() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean m0(@NotNull Throwable exception) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void B0(@Nullable Object state) {
    }

    /* access modifiers changed from: protected */
    public void N(@Nullable Object state) {
    }

    @NotNull
    public String toString() {
        return L0() + '@' + t0.b(this);
    }

    @NotNull
    public final String L0() {
        return w0() + '{' + I0(l0()) + '}';
    }

    @NotNull
    public String w0() {
        return t0.a(this);
    }

    private final String I0(Object state) {
        if (state instanceof c) {
            if (((c) state).f()) {
                return "Cancelling";
            }
            if (((c) state).g()) {
                return "Completing";
            }
            return "Active";
        } else if (state instanceof u1) {
            if (((u1) state).isActive()) {
                return "Active";
            }
            return "New";
        } else if (state instanceof b0) {
            return "Cancelled";
        } else {
            return "Completed";
        }
    }

    @l(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0002\u0018\u00002\u00060\u0018j\u0002`,2\u00020-B!\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\u0005¢\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u00050\rj\b\u0012\u0004\u0012\u00020\u0005`\u000eH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0016\u001a\u00020\u0015H\u0016¢\u0006\u0004\b\u0016\u0010\u0017R(\u0010\u001e\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u00188B@BX\u000e¢\u0006\f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001f\u001a\u00020\u00038VX\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b!\u0010 R$\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00038F@FX\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010 \"\u0004\b\"\u0010#R\u0011\u0010$\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b$\u0010 R\u001a\u0010\u0002\u001a\u00020\u00018\u0016X\u0004¢\u0006\f\n\u0004\b\u0002\u0010%\u001a\u0004\b&\u0010'R(\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u00058F@FX\u000e¢\u0006\f\u001a\u0004\b(\u0010)\"\u0004\b*\u0010\f¨\u0006+"}, d2 = {"Lkotlinx/coroutines/JobSupport$Finishing;", "Lkotlinx/coroutines/NodeList;", "list", "", "isCompleting", "", "rootCause", "<init>", "(Lkotlinx/coroutines/NodeList;ZLjava/lang/Throwable;)V", "exception", "", "addExceptionLocked", "(Ljava/lang/Throwable;)V", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "allocateList", "()Ljava/util/ArrayList;", "proposedException", "", "sealLocked", "(Ljava/lang/Throwable;)Ljava/util/List;", "", "toString", "()Ljava/lang/String;", "", "value", "getExceptionsHolder", "()Ljava/lang/Object;", "setExceptionsHolder", "(Ljava/lang/Object;)V", "exceptionsHolder", "isActive", "()Z", "isCancelling", "setCompleting", "(Z)V", "isSealed", "Lkotlinx/coroutines/NodeList;", "getList", "()Lkotlinx/coroutines/NodeList;", "getRootCause", "()Ljava/lang/Throwable;", "setRootCause", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/SynchronizedObject;", "Lkotlinx/coroutines/Incomplete;"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: JobSupport.kt */
    public static final class c implements u1 {
        @NotNull
        private volatile /* synthetic */ Object _exceptionsHolder = null;
        @NotNull
        private volatile /* synthetic */ int _isCompleting;
        @NotNull
        private volatile /* synthetic */ Object _rootCause;
        @NotNull
        private final l2 c;

        @NotNull
        public l2 b() {
            return this.c;
        }

        public c(@NotNull l2 list, boolean isCompleting, @Nullable Throwable rootCause) {
            this.c = list;
            this._isCompleting = isCompleting;
            this._rootCause = rootCause;
        }

        /* JADX WARNING: type inference failed for: r0v0, types: [int, boolean] */
        public final boolean g() {
            return this._isCompleting;
        }

        public final void j(boolean value) {
            this._isCompleting = value;
        }

        @Nullable
        public final Throwable e() {
            return (Throwable) this._rootCause;
        }

        public final void l(@Nullable Throwable value) {
            this._rootCause = value;
        }

        private final Object d() {
            return this._exceptionsHolder;
        }

        private final void k(Object value) {
            this._exceptionsHolder = value;
        }

        public final boolean h() {
            return d() == h2.e;
        }

        public final boolean f() {
            return e() != null;
        }

        public boolean isActive() {
            return e() == null;
        }

        @NotNull
        public final List<Throwable> i(@Nullable Throwable proposedException) {
            ArrayList it;
            Object eh = d();
            if (eh == null) {
                it = c();
            } else if (eh instanceof Throwable) {
                it = c();
                it.add(eh);
            } else if (eh instanceof ArrayList) {
                it = (ArrayList) eh;
            } else {
                throw new IllegalStateException(k.l("State is ", eh).toString());
            }
            ArrayList list = it;
            Throwable rootCause = e();
            if (rootCause != null) {
                list.add(0, rootCause);
            }
            if (proposedException != null && !k.a(proposedException, rootCause)) {
                list.add(proposedException);
            }
            k(h2.e);
            return list;
        }

        public final void a(@NotNull Throwable exception) {
            Throwable rootCause = e();
            if (rootCause == null) {
                l(exception);
            } else if (exception != rootCause) {
                Object eh = d();
                if (eh == null) {
                    k(exception);
                } else if (eh instanceof Throwable) {
                    if (exception != eh) {
                        ArrayList c2 = c();
                        ArrayList $this$addExceptionLocked_u24lambda_u2d2 = c2;
                        $this$addExceptionLocked_u24lambda_u2d2.add(eh);
                        $this$addExceptionLocked_u24lambda_u2d2.add(exception);
                        k(c2);
                    }
                } else if (eh instanceof ArrayList) {
                    ((ArrayList) eh).add(exception);
                } else {
                    throw new IllegalStateException(k.l("State is ", eh).toString());
                }
            }
        }

        private final ArrayList<Throwable> c() {
            return new ArrayList<>(4);
        }

        @NotNull
        public String toString() {
            return "Finishing[cancelling=" + f() + ", completing=" + g() + ", rootCause=" + e() + ", exceptions=" + d() + ", list=" + b() + ']';
        }
    }

    @l(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lkotlinx/coroutines/JobSupport$ChildCompletion;", "Lkotlinx/coroutines/JobNode;", "parent", "Lkotlinx/coroutines/JobSupport;", "state", "Lkotlinx/coroutines/JobSupport$Finishing;", "child", "Lkotlinx/coroutines/ChildHandleNode;", "proposedUpdate", "", "(Lkotlinx/coroutines/JobSupport;Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: JobSupport.kt */
    public static final class b extends f2 {
        @Nullable
        private final Object p0;
        @NotNull
        private final g2 x;
        @NotNull
        private final c y;
        @NotNull
        private final u z;

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            y((Throwable) p1);
            return x.a;
        }

        public b(@NotNull g2 parent, @NotNull c state, @NotNull u child, @Nullable Object proposedUpdate) {
            this.x = parent;
            this.y = state;
            this.z = child;
            this.p0 = proposedUpdate;
        }

        public void y(@Nullable Throwable cause) {
            this.x.a0(this.y, this.z, this.p0);
        }
    }

    @l(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/JobSupport$AwaitContinuation;", "T", "Lkotlinx/coroutines/CancellableContinuationImpl;", "delegate", "Lkotlin/coroutines/Continuation;", "job", "Lkotlinx/coroutines/JobSupport;", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/JobSupport;)V", "getContinuationCancellationCause", "", "parent", "Lkotlinx/coroutines/Job;", "nameString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: JobSupport.kt */
    public static final class a<T> extends o<T> {
        @NotNull
        private final g2 a1;

        public a(@NotNull kotlin.coroutines.d<? super T> delegate, @NotNull g2 job) {
            super(delegate, 1);
            this.a1 = job;
        }

        @NotNull
        public Throwable s(@NotNull z1 parent) {
            Throwable it;
            Object state = this.a1.l0();
            if ((state instanceof c) && (it = ((c) state).e()) != null) {
                return it;
            }
            if (state instanceof b0) {
                return ((b0) state).b;
            }
            return parent.n();
        }

        /* access modifiers changed from: protected */
        @NotNull
        public String E() {
            return "AwaitContinuation";
        }
    }

    @Nullable
    public final Object e0() {
        Object state = l0();
        if (!(!(state instanceof u1))) {
            throw new IllegalStateException("This job has not completed yet".toString());
        } else if (!(state instanceof b0)) {
            return h2.h(state);
        } else {
            throw ((b0) state).b;
        }
    }

    @Nullable
    public final Object O(@NotNull kotlin.coroutines.d<Object> $completion) {
        Object state;
        do {
            state = l0();
            if (!(state instanceof u1)) {
                if (!(state instanceof b0)) {
                    return h2.h(state);
                }
                Throwable exception$iv = ((b0) state).b;
                if (s0.d()) {
                    kotlin.coroutines.d<Object> dVar = $completion;
                    if (!(dVar instanceof kotlin.coroutines.jvm.internal.e)) {
                        throw exception$iv;
                    }
                    throw e0.j(exception$iv, (kotlin.coroutines.jvm.internal.e) dVar);
                }
                throw exception$iv;
            }
        } while (H0(state) < 0);
        return Q($completion);
    }

    private final Object Q(kotlin.coroutines.d<Object> $completion) {
        a cont = new a(kotlin.coroutines.intrinsics.b.c($completion), this);
        cont.w();
        q.a(cont, t(new q2(cont)));
        Object t = cont.t();
        if (t == kotlin.coroutines.intrinsics.c.d()) {
            h.c($completion);
        }
        return t;
    }
}
