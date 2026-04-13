package io.ktor.http;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import meshsdk.BaseResp;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

/* compiled from: HttpStatusCode.kt */
public final class v {
    /* access modifiers changed from: private */
    @NotNull
    public static final v A = new v(BaseResp.ERR_WAIT_RESPONSE, "Not Acceptable");
    /* access modifiers changed from: private */
    @NotNull
    public static final v B = new v(407, "Proxy Authentication Required");
    /* access modifiers changed from: private */
    @NotNull
    public static final v C = new v(BaseResp.ERR_INVAILD_MODEL_ID, "Request Timeout");
    /* access modifiers changed from: private */
    @NotNull
    public static final v D = new v(BaseResp.ERR_DISCONNECT_FAIL, "Conflict");
    /* access modifiers changed from: private */
    @NotNull
    public static final v E = new v(BaseResp.ERR_UPLOAD_EXPORT_FAIL, "Gone");
    /* access modifiers changed from: private */
    @NotNull
    public static final v F = new v(411, "Length Required");
    /* access modifiers changed from: private */
    @NotNull
    public static final v G = new v(BaseResp.ERR_DOWNLOAD_MESH_FAIL, "Precondition Failed");
    /* access modifiers changed from: private */
    @NotNull
    public static final v H = new v(BaseResp.ERR_DOWNLOAD_IMPORT_FAIL, "Payload Too Large");
    /* access modifiers changed from: private */
    @NotNull
    public static final v I = new v(BaseResp.ERR_OP_FAIL, "Request-URI Too Long");
    /* access modifiers changed from: private */
    @NotNull
    public static final v J = new v(BaseResp.ERR_PARAM_ERROR, "Unsupported Media Type");
    /* access modifiers changed from: private */
    @NotNull
    public static final v K = new v(416, "Requested Range Not Satisfiable");
    /* access modifiers changed from: private */
    @NotNull
    public static final v L = new v(BaseResp.ERR_GROUP_NOT_EMPTY, "Expectation Failed");
    /* access modifiers changed from: private */
    @NotNull
    public static final v M = new v(422, "Unprocessable Entity");
    /* access modifiers changed from: private */
    @NotNull
    public static final v N = new v(423, "Locked");
    /* access modifiers changed from: private */
    @NotNull
    public static final v O = new v(424, "Failed Dependency");
    /* access modifiers changed from: private */
    @NotNull
    public static final v P = new v(BaseResp.ERR_GROUP_LIMIT, "Upgrade Required");
    /* access modifiers changed from: private */
    @NotNull
    public static final v Q = new v(BaseResp.ERR_SMART_LIMIT, "Too Many Requests");
    /* access modifiers changed from: private */
    @NotNull
    public static final v R = new v(431, "Request Header Fields Too Large");
    /* access modifiers changed from: private */
    @NotNull
    public static final v S = new v(500, "Internal Server Error");
    /* access modifiers changed from: private */
    @NotNull
    public static final v T = new v(TypedValues.PositionType.TYPE_TRANSITION_EASING, "Not Implemented");
    /* access modifiers changed from: private */
    @NotNull
    public static final v U = new v(TypedValues.PositionType.TYPE_DRAWPATH, "Bad Gateway");
    /* access modifiers changed from: private */
    @NotNull
    public static final v V = new v(TypedValues.PositionType.TYPE_PERCENT_WIDTH, "Service Unavailable");
    /* access modifiers changed from: private */
    @NotNull
    public static final v W = new v(TypedValues.PositionType.TYPE_PERCENT_HEIGHT, "Gateway Timeout");
    /* access modifiers changed from: private */
    @NotNull
    public static final v X = new v(TypedValues.PositionType.TYPE_SIZE_PERCENT, "HTTP Version Not Supported");
    /* access modifiers changed from: private */
    @NotNull
    public static final v Y = new v(TypedValues.PositionType.TYPE_PERCENT_X, "Variant Also Negotiates");
    /* access modifiers changed from: private */
    @NotNull
    public static final v Z = new v(TypedValues.PositionType.TYPE_PERCENT_Y, "Insufficient Storage");
    /* access modifiers changed from: private */
    @NotNull
    public static final v a = new v(100, "Continue");
    @NotNull
    private static final List<v> a0 = w.a();
    /* access modifiers changed from: private */
    @NotNull
    public static final v b = new v(101, UpgradeRequest.RESPONSE_CODE_MESSAGE);
    private static final v[] b0;
    /* access modifiers changed from: private */
    @NotNull
    public static final v c = new v(102, "Processing");
    public static final a c0 = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static final v d = new v(200, "OK");
    /* access modifiers changed from: private */
    @NotNull
    public static final v e = new v(201, "Created");
    /* access modifiers changed from: private */
    @NotNull
    public static final v f = new v(KitWrapItem.TYPE_MODE, "Accepted");
    /* access modifiers changed from: private */
    @NotNull
    public static final v g = new v(KitWrapItem.TYPE_EXIT, "Non-Authoritative Information");
    /* access modifiers changed from: private */
    @NotNull
    public static final v h = new v(KitWrapItem.TYPE_VERSION, "No Content");
    /* access modifiers changed from: private */
    @NotNull
    public static final v i = new v(205, "Reset Content");
    /* access modifiers changed from: private */
    @NotNull
    public static final v j = new v(206, "Partial Content");
    /* access modifiers changed from: private */
    @NotNull
    public static final v k = new v(207, "Multi-Status");
    /* access modifiers changed from: private */
    @NotNull
    public static final v l = new v(IjkMediaCodecInfo.RANK_SECURE, "Multiple Choices");
    /* access modifiers changed from: private */
    @NotNull
    public static final v m = new v(301, "Moved Permanently");
    /* access modifiers changed from: private */
    @NotNull
    public static final v n = new v(302, "Found");
    /* access modifiers changed from: private */
    @NotNull
    public static final v o = new v(303, "See Other");
    /* access modifiers changed from: private */
    @NotNull
    public static final v p = new v(304, "Not Modified");
    /* access modifiers changed from: private */
    @NotNull
    public static final v q = new v(305, "Use Proxy");
    /* access modifiers changed from: private */
    @NotNull
    public static final v r = new v(306, "Switch Proxy");
    /* access modifiers changed from: private */
    @NotNull
    public static final v s = new v(307, "Temporary Redirect");
    /* access modifiers changed from: private */
    @NotNull
    public static final v t = new v(308, "Permanent Redirect");
    /* access modifiers changed from: private */
    @NotNull
    public static final v u = new v(BaseResp.ERR_MSG_SEND_FAIL, "Bad Request");
    /* access modifiers changed from: private */
    @NotNull
    public static final v v = new v(401, "Unauthorized");
    /* access modifiers changed from: private */
    @NotNull
    public static final v w = new v(402, "Payment Required");
    /* access modifiers changed from: private */
    @NotNull
    public static final v x = new v(403, "Forbidden");
    /* access modifiers changed from: private */
    @NotNull
    public static final v y = new v(404, "Not Found");
    /* access modifiers changed from: private */
    @NotNull
    public static final v z = new v(BaseResp.ERR_CONNECT_FAIL, "Method Not Allowed");
    private final int d0;
    @NotNull
    private final String e0;

    public v(int value, @NotNull String description) {
        k.f(description, "description");
        this.d0 = value;
        this.e0 = description;
    }

    @NotNull
    public final String a0() {
        return this.e0;
    }

    public final int b0() {
        return this.d0;
    }

    @NotNull
    public String toString() {
        return this.d0 + ' ' + this.e0;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof v) && ((v) other).d0 == this.d0;
    }

    public int hashCode() {
        return Integer.valueOf(this.d0).hashCode();
    }

    /* compiled from: HttpStatusCode.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final v e() {
            return v.a;
        }

        @NotNull
        public final v Q() {
            return v.b;
        }

        @NotNull
        public final v G() {
            return v.c;
        }

        @NotNull
        public final v A() {
            return v.d;
        }

        @NotNull
        public final v f() {
            return v.e;
        }

        @NotNull
        public final v a() {
            return v.f;
        }

        @NotNull
        public final v v() {
            return v.g;
        }

        @NotNull
        public final v u() {
            return v.h;
        }

        @NotNull
        public final v M() {
            return v.i;
        }

        @NotNull
        public final v B() {
            return v.j;
        }

        @NotNull
        public final v s() {
            return v.k;
        }

        @NotNull
        public final v t() {
            return v.l;
        }

        @NotNull
        public final v r() {
            return v.m;
        }

        @NotNull
        public final v j() {
            return v.n;
        }

        @NotNull
        public final v N() {
            return v.o;
        }

        @NotNull
        public final v z() {
            return v.p;
        }

        @NotNull
        public final v X() {
            return v.q;
        }

        @NotNull
        public final v P() {
            return v.r;
        }

        @NotNull
        public final v R() {
            return v.s;
        }

        @NotNull
        public final v E() {
            return v.t;
        }

        @NotNull
        public final v c() {
            return v.u;
        }

        @NotNull
        public final v T() {
            return v.v;
        }

        @NotNull
        public final v D() {
            return v.w;
        }

        @NotNull
        public final v i() {
            return v.x;
        }

        @NotNull
        public final v x() {
            return v.y;
        }

        @NotNull
        public final v q() {
            return v.z;
        }

        @NotNull
        public final v w() {
            return v.A;
        }

        @NotNull
        public final v H() {
            return v.B;
        }

        @NotNull
        public final v J() {
            return v.C;
        }

        @NotNull
        public final v d() {
            return v.D;
        }

        @NotNull
        public final v l() {
            return v.E;
        }

        @NotNull
        public final v o() {
            return v.F;
        }

        @NotNull
        public final v F() {
            return v.G;
        }

        @NotNull
        public final v C() {
            return v.H;
        }

        @NotNull
        public final v K() {
            return v.I;
        }

        @NotNull
        public final v V() {
            return v.J;
        }

        @NotNull
        public final v L() {
            return v.K;
        }

        @NotNull
        public final v g() {
            return v.L;
        }

        @NotNull
        public final v U() {
            return v.M;
        }

        @NotNull
        public final v p() {
            return v.N;
        }

        @NotNull
        public final v h() {
            return v.O;
        }

        @NotNull
        public final v W() {
            return v.P;
        }

        @NotNull
        public final v S() {
            return v.Q;
        }

        @NotNull
        public final v I() {
            return v.R;
        }

        @NotNull
        public final v n() {
            return v.S;
        }

        @NotNull
        public final v y() {
            return v.T;
        }

        @NotNull
        public final v b() {
            return v.U;
        }

        @NotNull
        public final v O() {
            return v.V;
        }

        @NotNull
        public final v k() {
            return v.W;
        }

        @NotNull
        public final v Z() {
            return v.X;
        }

        @NotNull
        public final v Y() {
            return v.Y;
        }

        @NotNull
        public final v m() {
            return v.Z;
        }
    }

    static {
        Object element$iv;
        boolean z2;
        v[] vVarArr = new v[1000];
        for (int i2 = 0; i2 < 1000; i2++) {
            int idx = i2;
            Iterator<T> it = a0.iterator();
            while (true) {
                if (!it.hasNext()) {
                    element$iv = null;
                    break;
                }
                element$iv = it.next();
                if (((v) element$iv).d0 == idx) {
                    z2 = true;
                    continue;
                } else {
                    z2 = false;
                    continue;
                }
                if (z2) {
                    break;
                }
            }
            vVarArr[i2] = (v) element$iv;
        }
        b0 = vVarArr;
    }
}
