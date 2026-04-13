package okhttp3.internal.cache;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import meshsdk.BaseResp;
import okhttp3.b0;
import okhttp3.d;
import okhttp3.d0;
import okhttp3.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

/* compiled from: CacheStrategy.kt */
public final class c {
    public static final a a = new a((DefaultConstructorMarker) null);
    @Nullable
    private final b0 b;
    @Nullable
    private final d0 c;

    public c(@Nullable b0 networkRequest, @Nullable d0 cacheResponse) {
        this.b = networkRequest;
        this.c = cacheResponse;
    }

    @Nullable
    public final b0 b() {
        return this.b;
    }

    @Nullable
    public final d0 a() {
        return this.c;
    }

    /* compiled from: CacheStrategy.kt */
    public static final class b {
        private Date a;
        private String b;
        private Date c;
        private String d;
        private Date e;
        private long f;
        private long g;
        private String h;
        private int i = -1;
        private final long j;
        @NotNull
        private final b0 k;
        private final d0 l;

        public b(long nowMillis, @NotNull b0 request, @Nullable d0 cacheResponse) {
            k.f(request, Progress.REQUEST);
            this.j = nowMillis;
            this.k = request;
            this.l = cacheResponse;
            if (cacheResponse != null) {
                this.f = cacheResponse.P();
                this.g = cacheResponse.I();
                u headers = cacheResponse.s();
                int size = headers.size();
                for (int i2 = 0; i2 < size; i2++) {
                    String fieldName = headers.b(i2);
                    String value = headers.h(i2);
                    if (w.y(fieldName, "Date", true)) {
                        this.a = okhttp3.internal.http.c.a(value);
                        this.b = value;
                    } else if (w.y(fieldName, HttpHeaders.HEAD_KEY_EXPIRES, true)) {
                        this.e = okhttp3.internal.http.c.a(value);
                    } else if (w.y(fieldName, HttpHeaders.HEAD_KEY_LAST_MODIFIED, true)) {
                        this.c = okhttp3.internal.http.c.a(value);
                        this.d = value;
                    } else if (w.y(fieldName, HttpHeaders.HEAD_KEY_E_TAG, true)) {
                        this.h = value;
                    } else if (w.y(fieldName, "Age", true)) {
                        this.i = okhttp3.internal.b.U(value, -1);
                    }
                }
            }
        }

        private final boolean f() {
            d0 d0Var = this.l;
            if (d0Var == null) {
                k.n();
            }
            return d0Var.c().c() == -1 && this.e == null;
        }

        @NotNull
        public final c b() {
            c candidate = c();
            if (candidate.b() == null || !this.k.b().i()) {
                return candidate;
            }
            return new c((b0) null, (d0) null);
        }

        private final c c() {
            String conditionName;
            String conditionName2;
            if (this.l == null) {
                return new c(this.k, (d0) null);
            }
            if (this.k.g() && this.l.m() == null) {
                return new c(this.k, (d0) null);
            }
            if (!c.a.a(this.l, this.k)) {
                return new c(this.k, (d0) null);
            }
            d requestCaching = this.k.b();
            if (requestCaching.g()) {
            } else if (e(this.k)) {
                d dVar = requestCaching;
            } else {
                d responseCaching = this.l.c();
                long ageMillis = a();
                long freshMillis = d();
                if (requestCaching.c() != -1) {
                    freshMillis = Math.min(freshMillis, TimeUnit.SECONDS.toMillis((long) requestCaching.c()));
                }
                long minFreshMillis = 0;
                if (requestCaching.e() != -1) {
                    minFreshMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.e());
                }
                long maxStaleMillis = 0;
                if (!responseCaching.f() && requestCaching.d() != -1) {
                    maxStaleMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.d());
                }
                if (responseCaching.g() || ageMillis + minFreshMillis >= freshMillis + maxStaleMillis) {
                    if (this.h != null) {
                        conditionName2 = HttpHeaders.HEAD_KEY_IF_NONE_MATCH;
                        conditionName = this.h;
                    } else if (this.c != null) {
                        conditionName2 = HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE;
                        conditionName = this.d;
                    } else if (this.a != null) {
                        conditionName2 = HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE;
                        conditionName = this.b;
                    } else {
                        return new c(this.k, (d0) null);
                    }
                    u.a conditionalRequestHeaders = this.k.f().f();
                    if (conditionName == null) {
                        k.n();
                    }
                    conditionalRequestHeaders.d(conditionName2, conditionName);
                    d dVar2 = requestCaching;
                    return new c(this.k.i().h(conditionalRequestHeaders.f()).b(), this.l);
                }
                d0.a builder = this.l.v();
                if (ageMillis + minFreshMillis >= freshMillis) {
                    builder.a("Warning", "110 HttpURLConnection \"Response is stale\"");
                }
                if (ageMillis > CostTimeUtil.DAY && f()) {
                    builder.a("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                }
                return new c((b0) null, builder.c());
            }
            return new c(this.k, (d0) null);
        }

        private final long d() {
            d0 d0Var = this.l;
            if (d0Var == null) {
                k.n();
            }
            d responseCaching = d0Var.c();
            if (responseCaching.c() != -1) {
                return TimeUnit.SECONDS.toMillis((long) responseCaching.c());
            }
            Date expires = this.e;
            if (expires != null) {
                Date date = this.a;
                long delta = expires.getTime() - (date != null ? date.getTime() : this.g);
                if (delta > 0) {
                    return delta;
                }
                return 0;
            } else if (this.c == null || this.l.J().l().q() != null) {
                return 0;
            } else {
                Date date2 = this.a;
                long servedMillis = date2 != null ? date2.getTime() : this.f;
                Date date3 = this.c;
                if (date3 == null) {
                    k.n();
                }
                long delta2 = servedMillis - date3.getTime();
                if (delta2 > 0) {
                    return delta2 / ((long) 10);
                }
                return 0;
            }
        }

        private final long a() {
            long receivedAge;
            Date servedDate = this.a;
            long apparentReceivedAge = 0;
            if (servedDate != null) {
                apparentReceivedAge = Math.max(0, this.g - servedDate.getTime());
            }
            int i2 = this.i;
            if (i2 != -1) {
                receivedAge = Math.max(apparentReceivedAge, TimeUnit.SECONDS.toMillis((long) i2));
            } else {
                receivedAge = apparentReceivedAge;
            }
            long j2 = this.g;
            return receivedAge + (j2 - this.f) + (this.j - j2);
        }

        private final boolean e(b0 request) {
            return (request.d(HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE) == null && request.d(HttpHeaders.HEAD_KEY_IF_NONE_MATCH) == null) ? false : true;
        }
    }

    /* compiled from: CacheStrategy.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean a(@NotNull d0 response, @NotNull b0 request) {
            k.f(response, "response");
            k.f(request, Progress.REQUEST);
            switch (response.j()) {
                case 200:
                case KitWrapItem.TYPE_EXIT:
                case KitWrapItem.TYPE_VERSION:
                case IjkMediaCodecInfo.RANK_SECURE /*300*/:
                case 301:
                case 308:
                case 404:
                case BaseResp.ERR_CONNECT_FAIL:
                case BaseResp.ERR_UPLOAD_EXPORT_FAIL:
                case BaseResp.ERR_OP_FAIL:
                case TypedValues.PositionType.TYPE_TRANSITION_EASING:
                    break;
                case 302:
                case 307:
                    if (d0.r(response, HttpHeaders.HEAD_KEY_EXPIRES, (String) null, 2, (Object) null) == null && response.c().c() == -1 && !response.c().b() && !response.c().a()) {
                        return false;
                    }
                default:
                    return false;
            }
            if (response.c().h() || request.b().h()) {
                return false;
            }
            return true;
        }
    }
}
