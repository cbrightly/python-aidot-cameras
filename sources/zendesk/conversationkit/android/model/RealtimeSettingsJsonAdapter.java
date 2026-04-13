package zendesk.conversationkit.android.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealtimeSettingsJsonAdapter.kt */
public final class RealtimeSettingsJsonAdapter extends f<RealtimeSettings> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<Boolean> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<Long> d;
    @NotNull
    private final f<Integer> e;
    @NotNull
    private final f<TimeUnit> f;
    @Nullable
    private volatile Constructor<RealtimeSettings> g;

    public RealtimeSettingsJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("enabled", "baseUrl", "retryInterval", "maxConnectionAttempts", "connectionDelay", "timeUnit", "appId", "userId");
        k.d(a2, "of(\"enabled\", \"baseUrl\",…Unit\", \"appId\", \"userId\")");
        this.a = a2;
        f<Boolean> f2 = moshi.f(Boolean.TYPE, o0.d(), "enabled");
        k.d(f2, "moshi.adapter(Boolean::c…tySet(),\n      \"enabled\")");
        this.b = f2;
        f<String> f3 = moshi.f(String.class, o0.d(), "baseUrl");
        k.d(f3, "moshi.adapter(String::cl…tySet(),\n      \"baseUrl\")");
        this.c = f3;
        f<Long> f4 = moshi.f(Long.TYPE, o0.d(), "retryInterval");
        k.d(f4, "moshi.adapter(Long::clas…),\n      \"retryInterval\")");
        this.d = f4;
        f<Integer> f5 = moshi.f(Integer.TYPE, o0.d(), "maxConnectionAttempts");
        k.d(f5, "moshi.adapter(Int::class… \"maxConnectionAttempts\")");
        this.e = f5;
        f<TimeUnit> f6 = moshi.f(TimeUnit.class, o0.d(), "timeUnit");
        k.d(f6, "moshi.adapter(TimeUnit::…  emptySet(), \"timeUnit\")");
        this.f = f6;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(38);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("RealtimeSettings");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public RealtimeSettings b(@NotNull i reader) {
        i iVar = reader;
        Class<String> cls = String.class;
        k.e(iVar, "reader");
        Boolean enabled = null;
        reader.c();
        String baseUrl = null;
        Long retryInterval = null;
        Integer maxConnectionAttempts = null;
        Long connectionDelay = null;
        TimeUnit timeUnit = null;
        String appId = null;
        String userId = null;
        int mask0 = -1;
        while (reader.l()) {
            switch (iVar.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    Boolean b2 = this.b.b(iVar);
                    if (b2 != null) {
                        enabled = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("enabled", "enabled", iVar);
                        k.d(u, "unexpectedNull(\"enabled\"…       \"enabled\", reader)");
                        throw u;
                    }
                case 1:
                    String b3 = this.c.b(iVar);
                    if (b3 != null) {
                        baseUrl = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("baseUrl", "baseUrl", iVar);
                        k.d(u2, "unexpectedNull(\"baseUrl\"…       \"baseUrl\", reader)");
                        throw u2;
                    }
                case 2:
                    Long b4 = this.d.b(iVar);
                    if (b4 != null) {
                        retryInterval = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("retryInterval", "retryInterval", iVar);
                        k.d(u3, "unexpectedNull(\"retryInt… \"retryInterval\", reader)");
                        throw u3;
                    }
                case 3:
                    Integer b5 = this.e.b(iVar);
                    if (b5 != null) {
                        maxConnectionAttempts = b5;
                        break;
                    } else {
                        JsonDataException u4 = b.u("maxConnectionAttempts", "maxConnectionAttempts", iVar);
                        k.d(u4, "unexpectedNull(\"maxConne…nectionAttempts\", reader)");
                        throw u4;
                    }
                case 4:
                    Long b6 = this.d.b(iVar);
                    if (b6 != null) {
                        connectionDelay = b6;
                        break;
                    } else {
                        JsonDataException u5 = b.u("connectionDelay", "connectionDelay", iVar);
                        k.d(u5, "unexpectedNull(\"connecti…connectionDelay\", reader)");
                        throw u5;
                    }
                case 5:
                    TimeUnit b7 = this.f.b(iVar);
                    if (b7 != null) {
                        timeUnit = b7;
                        mask0 &= -33;
                        break;
                    } else {
                        JsonDataException u6 = b.u("timeUnit", "timeUnit", iVar);
                        k.d(u6, "unexpectedNull(\"timeUnit…      \"timeUnit\", reader)");
                        throw u6;
                    }
                case 6:
                    String b8 = this.c.b(iVar);
                    if (b8 != null) {
                        appId = b8;
                        break;
                    } else {
                        JsonDataException u7 = b.u("appId", "appId", iVar);
                        k.d(u7, "unexpectedNull(\"appId\", …pId\",\n            reader)");
                        throw u7;
                    }
                case 7:
                    String b9 = this.c.b(iVar);
                    if (b9 != null) {
                        userId = b9;
                        break;
                    } else {
                        JsonDataException u8 = b.u("userId", "userId", iVar);
                        k.d(u8, "unexpectedNull(\"userId\",…        \"userId\", reader)");
                        throw u8;
                    }
            }
        }
        reader.i();
        if (mask0 != -33) {
            int mask02 = mask0;
            Constructor it = this.g;
            if (it == null) {
                Class cls2 = Long.TYPE;
                Class cls3 = Integer.TYPE;
                it = RealtimeSettings.class.getDeclaredConstructor(new Class[]{Boolean.TYPE, cls, cls2, cls3, cls2, TimeUnit.class, cls, cls, cls3, b.c});
                this.g = it;
                k.d(it, "RealtimeSettings::class.…his.constructorRef = it }");
            }
            Constructor localConstructor = it;
            Object[] objArr = new Object[10];
            if (enabled != null) {
                objArr[0] = Boolean.valueOf(enabled.booleanValue());
                if (baseUrl != null) {
                    objArr[1] = baseUrl;
                    if (retryInterval != null) {
                        objArr[2] = Long.valueOf(retryInterval.longValue());
                        if (maxConnectionAttempts != null) {
                            objArr[3] = Integer.valueOf(maxConnectionAttempts.intValue());
                            if (connectionDelay != null) {
                                objArr[4] = Long.valueOf(connectionDelay.longValue());
                                objArr[5] = timeUnit;
                                if (appId != null) {
                                    objArr[6] = appId;
                                    if (userId != null) {
                                        objArr[7] = userId;
                                        objArr[8] = Integer.valueOf(mask02);
                                        objArr[9] = null;
                                        RealtimeSettings newInstance = localConstructor.newInstance(objArr);
                                        k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
                                        return newInstance;
                                    }
                                    JsonDataException l = b.l("userId", "userId", iVar);
                                    k.d(l, "missingProperty(\"userId\", \"userId\", reader)");
                                    throw l;
                                }
                                JsonDataException l2 = b.l("appId", "appId", iVar);
                                k.d(l2, "missingProperty(\"appId\", \"appId\", reader)");
                                throw l2;
                            }
                            JsonDataException l3 = b.l("connectionDelay", "connectionDelay", iVar);
                            k.d(l3, "missingProperty(\"connect…y\",\n              reader)");
                            throw l3;
                        }
                        JsonDataException l4 = b.l("maxConnectionAttempts", "maxConnectionAttempts", iVar);
                        k.d(l4, "missingProperty(\"maxConn…nectionAttempts\", reader)");
                        throw l4;
                    }
                    JsonDataException l5 = b.l("retryInterval", "retryInterval", iVar);
                    k.d(l5, "missingProperty(\"retryIn… \"retryInterval\", reader)");
                    throw l5;
                }
                JsonDataException l6 = b.l("baseUrl", "baseUrl", iVar);
                k.d(l6, "missingProperty(\"baseUrl\", \"baseUrl\", reader)");
                throw l6;
            }
            JsonDataException l7 = b.l("enabled", "enabled", iVar);
            k.d(l7, "missingProperty(\"enabled\", \"enabled\", reader)");
            throw l7;
        } else if (enabled != null) {
            boolean booleanValue = enabled.booleanValue();
            if (baseUrl == null) {
                JsonDataException l8 = b.l("baseUrl", "baseUrl", iVar);
                k.d(l8, "missingProperty(\"baseUrl\", \"baseUrl\", reader)");
                throw l8;
            } else if (retryInterval != null) {
                long longValue = retryInterval.longValue();
                if (maxConnectionAttempts != null) {
                    int intValue = maxConnectionAttempts.intValue();
                    if (connectionDelay != null) {
                        long longValue2 = connectionDelay.longValue();
                        if (timeUnit == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.util.concurrent.TimeUnit");
                        } else if (appId == null) {
                            JsonDataException l9 = b.l("appId", "appId", iVar);
                            k.d(l9, "missingProperty(\"appId\", \"appId\", reader)");
                            throw l9;
                        } else if (userId != null) {
                            int i = mask0;
                            return new RealtimeSettings(booleanValue, baseUrl, longValue, intValue, longValue2, timeUnit, appId, userId);
                        } else {
                            JsonDataException l10 = b.l("userId", "userId", iVar);
                            k.d(l10, "missingProperty(\"userId\", \"userId\", reader)");
                            throw l10;
                        }
                    } else {
                        JsonDataException l11 = b.l("connectionDelay", "connectionDelay", iVar);
                        k.d(l11, "missingProperty(\"connect…connectionDelay\", reader)");
                        throw l11;
                    }
                } else {
                    JsonDataException l12 = b.l("maxConnectionAttempts", "maxConnectionAttempts", iVar);
                    k.d(l12, "missingProperty(\"maxConn…nectionAttempts\", reader)");
                    throw l12;
                }
            } else {
                JsonDataException l13 = b.l("retryInterval", "retryInterval", iVar);
                k.d(l13, "missingProperty(\"retryIn… \"retryInterval\", reader)");
                throw l13;
            }
        } else {
            JsonDataException l14 = b.l("enabled", "enabled", iVar);
            k.d(l14, "missingProperty(\"enabled\", \"enabled\", reader)");
            throw l14;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable RealtimeSettings value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("enabled");
            this.b.i(writer, Boolean.valueOf(value_.d()));
            writer.r("baseUrl");
            this.c.i(writer, value_.b());
            writer.r("retryInterval");
            this.d.i(writer, Long.valueOf(value_.f()));
            writer.r("maxConnectionAttempts");
            this.e.i(writer, Integer.valueOf(value_.e()));
            writer.r("connectionDelay");
            this.d.i(writer, Long.valueOf(value_.c()));
            writer.r("timeUnit");
            this.f.i(writer, value_.g());
            writer.r("appId");
            this.c.i(writer, value_.a());
            writer.r("userId");
            this.c.i(writer, value_.h());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
