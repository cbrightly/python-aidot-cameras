package zendesk.android.settings.internal;

import com.squareup.moshi.r;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SettingsRestClient.kt */
public final class g {
    @NotNull
    private final a a;
    @NotNull
    private final r b;
    @NotNull
    private final zendesk.android.internal.g c;

    @f(c = "zendesk.android.settings.internal.SettingsRestClient", f = "SettingsRestClient.kt", l = {31}, m = "getSettings")
    /* compiled from: SettingsRestClient.kt */
    public static final class a extends d {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(g gVar, kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
            this.this$0 = gVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a(this);
        }
    }

    public g(@NotNull a settingsApi, @NotNull r moshi, @NotNull zendesk.android.internal.g zendeskComponentConfig) {
        k.e(settingsApi, "settingsApi");
        k.e(moshi, "moshi");
        k.e(zendeskComponentConfig, "zendeskComponentConfig");
        this.a = settingsApi;
        this.b = moshi;
        this.c = zendeskComponentConfig;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.android.settings.internal.model.SettingsDto> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof zendesk.android.settings.internal.g.a
            if (r0 == 0) goto L_0x0013
            r0 = r8
            zendesk.android.settings.internal.g$a r0 = (zendesk.android.settings.internal.g.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.android.settings.internal.g$a r0 = new zendesk.android.settings.internal.g$a
            r0.<init>(r7, r8)
        L_0x0018:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x0031;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x002c:
            kotlin.p.b(r0)
            r2 = r0
            goto L_0x0054
        L_0x0031:
            kotlin.p.b(r0)
            r2 = r7
            zendesk.android.internal.g r3 = r2.c
            zendesk.android.d r3 = r3.b()
            com.squareup.moshi.r r4 = r2.b
            zendesk.android.internal.ChannelKeyFields r4 = zendesk.android.e.a(r3, r4)
            if (r4 == 0) goto L_0x005b
            r3 = r4
            zendesk.android.settings.internal.a r4 = r2.a
            java.lang.String r5 = r3.a()
            r6 = 1
            r8.label = r6
            java.lang.Object r2 = r4.a(r5, r8)
            if (r2 != r1) goto L_0x0054
            return r1
        L_0x0054:
            zendesk.android.settings.internal.model.SettingsResponseDto r2 = (zendesk.android.settings.internal.model.SettingsResponseDto) r2
            zendesk.android.settings.internal.model.SettingsDto r1 = r2.a()
            return r1
        L_0x005b:
            zendesk.android.internal.i$c r1 = zendesk.android.internal.i.c.INSTANCE
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.android.settings.internal.g.a(kotlin.coroutines.d):java.lang.Object");
    }
}
