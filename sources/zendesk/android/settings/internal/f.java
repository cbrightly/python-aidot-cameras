package zendesk.android.settings.internal;

import kotlin.coroutines.jvm.internal.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SettingsRepository.kt */
public final class f {
    @NotNull
    private final g a;

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.settings.internal.SettingsRepository", f = "SettingsRepository.kt", l = {24}, m = "fetchSettings$zendesk_zendesk_android")
    /* compiled from: SettingsRepository.kt */
    public static final class a extends d {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(f fVar, kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
            this.this$0 = fVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a(this);
        }
    }

    public f(@NotNull g settingsRestClient) {
        k.e(settingsRestClient, "settingsRestClient");
        this.a = settingsRestClient;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002f A[SYNTHETIC, Splitter:B:10:0x002f] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0027  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.android.f<zendesk.android.settings.internal.model.SettingsDto, ? extends java.lang.Throwable>> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof zendesk.android.settings.internal.f.a
            if (r0 == 0) goto L_0x0013
            r0 = r8
            zendesk.android.settings.internal.f$a r0 = (zendesk.android.settings.internal.f.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.android.settings.internal.f$a r0 = new zendesk.android.settings.internal.f$a
            r0.<init>(r7, r8)
        L_0x0018:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r8.label
            r3 = 0
            java.lang.String r4 = "Zendesk"
            switch(r2) {
                case 0: goto L_0x0038;
                case 1: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x002f:
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x0036, all -> 0x0034 }
            r5 = r0
            goto L_0x0049
        L_0x0034:
            r1 = move-exception
            goto L_0x0052
        L_0x0036:
            r1 = move-exception
            goto L_0x007b
        L_0x0038:
            kotlin.p.b(r0)
            r2 = r7
            zendesk.android.settings.internal.g r5 = r2.a     // Catch:{ JsonDataException -> 0x0036, all -> 0x0034 }
            r6 = 1
            r8.label = r6     // Catch:{ JsonDataException -> 0x0036, all -> 0x0034 }
            java.lang.Object r5 = r5.a(r8)     // Catch:{ JsonDataException -> 0x0036, all -> 0x0034 }
            if (r5 != r1) goto L_0x0049
            return r1
        L_0x0049:
            zendesk.android.settings.internal.model.SettingsDto r5 = (zendesk.android.settings.internal.model.SettingsDto) r5     // Catch:{ JsonDataException -> 0x0036, all -> 0x0034 }
            r1 = r5
            zendesk.android.f$b r2 = new zendesk.android.f$b     // Catch:{ JsonDataException -> 0x0036, all -> 0x0034 }
            r2.<init>(r1)     // Catch:{ JsonDataException -> 0x0036, all -> 0x0034 }
            goto L_0x008e
        L_0x0052:
            java.lang.Object[] r2 = new java.lang.Object[r3]
            java.lang.String r3 = "Zendesk failed to initialize."
            zendesk.logger.a.c(r4, r3, r1, r2)
            boolean r2 = r1 instanceof retrofit2.HttpException
            if (r2 == 0) goto L_0x0070
            r2 = r1
            retrofit2.HttpException r2 = (retrofit2.HttpException) r2
            int r2 = r2.code()
            r3 = 404(0x194, float:5.66E-43)
            if (r2 != r3) goto L_0x0070
            zendesk.android.f$a r2 = new zendesk.android.f$a
            zendesk.android.internal.i$a r3 = zendesk.android.internal.i.a.INSTANCE
            r2.<init>(r3)
            goto L_0x007a
        L_0x0070:
            zendesk.android.f$a r2 = new zendesk.android.f$a
            zendesk.android.internal.i$b r3 = new zendesk.android.internal.i$b
            r3.<init>(r1)
            r2.<init>(r3)
        L_0x007a:
            goto L_0x008e
        L_0x007b:
            java.lang.Object[] r2 = new java.lang.Object[r3]
            java.lang.String r3 = "GET request for Settings failed to decode malformed JSON response."
            zendesk.logger.a.c(r4, r3, r1, r2)
            zendesk.android.f$a r2 = new zendesk.android.f$a
            zendesk.android.internal.i$b r3 = new zendesk.android.internal.i$b
            r3.<init>(r1)
            r2.<init>(r3)
        L_0x008e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.android.settings.internal.f.a(kotlin.coroutines.d):java.lang.Object");
    }
}
