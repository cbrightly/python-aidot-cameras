package zendesk.conversationkit.android.internal.rest;

import java.io.File;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.k;
import okhttp3.c0;
import okhttp3.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.rest.model.ActivityDataRequestDto;
import zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto;
import zendesk.conversationkit.android.internal.rest.model.ConversationResponseDto;
import zendesk.conversationkit.android.internal.rest.model.CreateConversationRequestDto;
import zendesk.conversationkit.android.internal.rest.model.SendMessageRequestDto;
import zendesk.conversationkit.android.internal.rest.model.SendMessageResponseDto;
import zendesk.conversationkit.android.internal.rest.model.UpdateAppUserLocaleDto;
import zendesk.conversationkit.android.internal.rest.model.UpdatePushTokenDto;
import zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody;
import zendesk.conversationkit.android.internal.rest.user.model.LogoutRequestBody;

/* compiled from: UserRestClient.kt */
public final class g {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final f c;
    @NotNull
    private final e d;

    @f(c = "zendesk.conversationkit.android.internal.rest.UserRestClient", f = "UserRestClient.kt", l = {146}, m = "uploadFile")
    /* compiled from: UserRestClient.kt */
    public static final class a extends d {
        Object L$0;
        Object L$1;
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
            return this.this$0.j((String) null, (String) null, (zendesk.conversationkit.android.internal.rest.model.d) null, this);
        }
    }

    public g(@NotNull String appId, @NotNull String appUserId, @NotNull f sunshineConversationsApi, @NotNull e restClientFiles) {
        k.e(appId, "appId");
        k.e(appUserId, "appUserId");
        k.e(sunshineConversationsApi, "sunshineConversationsApi");
        k.e(restClientFiles, "restClientFiles");
        this.a = appId;
        this.b = appUserId;
        this.c = sunshineConversationsApi;
        this.d = restClientFiles;
    }

    @Nullable
    public final Object b(@NotNull String authorization, @NotNull kotlin.coroutines.d<? super AppUserResponseDto> $completion) {
        return this.c.c(authorization, this.a, this.b, $completion);
    }

    @Nullable
    public final Object a(@NotNull String authorization, @NotNull CreateConversationRequestDto createConversationRequestDto, @NotNull kotlin.coroutines.d<? super ConversationResponseDto> $completion) {
        return this.c.d(authorization, this.a, this.b, createConversationRequestDto, $completion);
    }

    @Nullable
    public final Object c(@NotNull String authorization, @NotNull String conversationId, @NotNull kotlin.coroutines.d<? super ConversationResponseDto> $completion) {
        return this.c.k(authorization, this.a, conversationId, $completion);
    }

    @Nullable
    public final Object g(@NotNull String authorization, @NotNull String conversationId, @NotNull SendMessageRequestDto sendMessageRequestDto, @NotNull kotlin.coroutines.d<? super SendMessageResponseDto> $completion) {
        return this.c.j(authorization, this.a, conversationId, sendMessageRequestDto, $completion);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object j(@org.jetbrains.annotations.NotNull java.lang.String r12, @org.jetbrains.annotations.NotNull java.lang.String r13, @org.jetbrains.annotations.NotNull zendesk.conversationkit.android.internal.rest.model.d r14, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.rest.model.UploadFileResponseDto> r15) {
        /*
            r11 = this;
            boolean r0 = r15 instanceof zendesk.conversationkit.android.internal.rest.g.a
            if (r0 == 0) goto L_0x0013
            r0 = r15
            zendesk.conversationkit.android.internal.rest.g$a r0 = (zendesk.conversationkit.android.internal.rest.g.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.rest.g$a r0 = new zendesk.conversationkit.android.internal.rest.g$a
            r0.<init>(r11, r15)
        L_0x0018:
            r15 = r0
            java.lang.Object r8 = r15.result
            java.lang.Object r9 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r15.label
            switch(r0) {
                case 0: goto L_0x003a;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002c:
            java.lang.Object r12 = r15.L$1
            zendesk.conversationkit.android.internal.rest.model.d r12 = (zendesk.conversationkit.android.internal.rest.model.d) r12
            java.lang.Object r13 = r15.L$0
            zendesk.conversationkit.android.internal.rest.g r13 = (zendesk.conversationkit.android.internal.rest.g) r13
            kotlin.p.b(r8)
            r10 = r13
            r13 = r8
            goto L_0x009b
        L_0x003a:
            kotlin.p.b(r8)
            r10 = r11
            r3 = r13
            r1 = r12
            r12 = r14
            zendesk.conversationkit.android.internal.rest.e r13 = r10.d
            zendesk.conversationkit.android.internal.rest.model.c r14 = r12.c()
            java.lang.String r14 = r14.c()
            zendesk.conversationkit.android.internal.rest.model.c r0 = r12.c()
            java.lang.String r0 = r0.b()
            java.io.File r13 = r13.b(r14, r0)
            okhttp3.x$a r14 = okhttp3.x.c
            zendesk.conversationkit.android.internal.rest.model.c r0 = r12.c()
            java.lang.String r0 = r0.a()
            okhttp3.x r14 = r14.b(r0)
            zendesk.conversationkit.android.internal.rest.g$b r0 = new zendesk.conversationkit.android.internal.rest.g$b
            r0.<init>(r13, r14)
            r13 = r0
            zendesk.conversationkit.android.internal.rest.f r0 = r10.c
            java.lang.String r2 = r10.a
            zendesk.conversationkit.android.internal.rest.model.AuthorDto r4 = r12.a()
            zendesk.conversationkit.android.internal.rest.model.MetadataDto r5 = r12.b()
            okhttp3.y$c$a r14 = okhttp3.y.c.a
            zendesk.conversationkit.android.internal.rest.model.c r6 = r12.c()
            java.lang.String r6 = r6.b()
            java.lang.String r7 = "source"
            okhttp3.y$c r6 = r14.c(r7, r6, r13)
            r15.L$0 = r10
            r15.L$1 = r12
            r13 = 1
            r15.label = r13
            r7 = r15
            java.lang.Object r13 = r0.b(r1, r2, r3, r4, r5, r6, r7)
            if (r13 != r9) goto L_0x009b
            return r9
        L_0x009b:
            zendesk.conversationkit.android.internal.rest.model.UploadFileResponseDto r13 = (zendesk.conversationkit.android.internal.rest.model.UploadFileResponseDto) r13
            zendesk.conversationkit.android.internal.rest.e r14 = r10.d
            zendesk.conversationkit.android.internal.rest.model.c r0 = r12.c()
            java.lang.String r0 = r0.b()
            r14.a(r0)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.rest.g.j(java.lang.String, java.lang.String, zendesk.conversationkit.android.internal.rest.model.d, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: UserRestClient.kt */
    public static final class b extends c0 {
        private final long a;
        @NotNull
        private final byte[] b;
        final /* synthetic */ File c;
        final /* synthetic */ x d;

        b(File $sourceFile, x $mediaType) {
            this.c = $sourceFile;
            this.d = $mediaType;
            this.a = $sourceFile.length();
            byte[] bArr = new byte[1];
            for (int i = 0; i < 1; i++) {
                bArr[i] = 0;
            }
            this.b = bArr;
        }

        @Nullable
        public x contentType() {
            return this.d;
        }

        public long contentLength() {
            long j = this.a;
            if (j > 0) {
                return j;
            }
            return (long) this.b.length;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0020, code lost:
            kotlin.io.b.a(r0, r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0023, code lost:
            throw r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x001f, code lost:
            r2 = move-exception;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void writeTo(@org.jetbrains.annotations.NotNull okio.d r5) {
            /*
                r4 = this;
                java.lang.String r0 = "sink"
                kotlin.jvm.internal.k.e(r5, r0)
                long r0 = r4.a
                r2 = 0
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 <= 0) goto L_0x0024
                java.io.File r0 = r4.c
                okio.e0 r0 = okio.p.k(r0)
                r1 = 0
                r2 = r0
                r3 = 0
                r5.writeAll(r2)     // Catch:{ all -> 0x001d }
                kotlin.io.b.a(r0, r1)
                goto L_0x0029
            L_0x001d:
                r1 = move-exception
                throw r1     // Catch:{ all -> 0x001f }
            L_0x001f:
                r2 = move-exception
                kotlin.io.b.a(r0, r1)
                throw r2
            L_0x0024:
                byte[] r0 = r4.b
                r5.write((byte[]) r0)
            L_0x0029:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.rest.g.b.writeTo(okio.d):void");
        }
    }

    @Nullable
    public final Object i(@NotNull String authorization, @NotNull String clientId, @NotNull UpdatePushTokenDto updatePushTokenDto, @NotNull kotlin.coroutines.d<? super kotlin.x> $completion) {
        Object a2 = this.c.a(authorization, this.a, this.b, clientId, updatePushTokenDto, $completion);
        return a2 == c.d() ? a2 : kotlin.x.a;
    }

    @Nullable
    public final Object f(@NotNull String authorization, @NotNull String conversationId, @NotNull ActivityDataRequestDto activityDataDto, @NotNull kotlin.coroutines.d<? super kotlin.x> $completion) {
        Object f = this.c.f(authorization, this.a, conversationId, activityDataDto, $completion);
        return f == c.d() ? f : kotlin.x.a;
    }

    @Nullable
    public final Object h(@NotNull String authorization, @NotNull UpdateAppUserLocaleDto updateAppUserLocaleDto, @NotNull kotlin.coroutines.d<? super kotlin.x> $completion) {
        Object g = this.c.g(authorization, this.a, this.b, updateAppUserLocaleDto, $completion);
        return g == c.d() ? g : kotlin.x.a;
    }

    @Nullable
    public final Object d(@NotNull String jwt, @NotNull LoginRequestBody loginRequestBody, @NotNull kotlin.coroutines.d<? super AppUserResponseDto> $completion) {
        return this.c.h(this.a, k.l("Bearer ", jwt), loginRequestBody, $completion);
    }

    @Nullable
    public final Object e(@NotNull String jwt, @NotNull String appUserId, @NotNull LogoutRequestBody logoutRequestBody, @NotNull kotlin.coroutines.d<? super kotlin.x> $completion) {
        Object e = this.c.e(this.a, appUserId, k.l("Bearer ", jwt), logoutRequestBody, $completion);
        return e == c.d() ? e : kotlin.x.a;
    }
}
