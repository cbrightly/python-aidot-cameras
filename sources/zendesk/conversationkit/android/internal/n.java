package zendesk.conversationkit.android.internal;

import com.squareup.moshi.r;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
import zendesk.storage.android.b;

/* compiled from: StorageFactory.kt */
public final class n implements b {
    @NotNull
    private final r a;

    public n(@NotNull r moshi) {
        k.e(moshi, "moshi");
        this.a = moshi;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ n(com.squareup.moshi.r r3, int r4, kotlin.jvm.internal.DefaultConstructorMarker r5) {
        /*
            r2 = this;
            r4 = r4 & 1
            if (r4 == 0) goto L_0x0116
            com.squareup.moshi.r$b r3 = new com.squareup.moshi.r$b
            r3.<init>()
            java.lang.Class<zendesk.conversationkit.android.model.MessageContent> r4 = zendesk.conversationkit.android.model.MessageContent.class
            java.lang.String r5 = "type"
            com.squareup.moshi.adapters.b r4 = com.squareup.moshi.adapters.b.b(r4, r5)
            java.lang.Class<zendesk.conversationkit.android.model.MessageContent$Unsupported> r0 = zendesk.conversationkit.android.model.MessageContent.Unsupported.class
            zendesk.conversationkit.android.model.v r1 = zendesk.conversationkit.android.model.v.UNSUPPORTED
            java.lang.String r1 = r1.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r0, r1)
            java.lang.Class<zendesk.conversationkit.android.model.MessageContent$Text> r0 = zendesk.conversationkit.android.model.MessageContent.Text.class
            zendesk.conversationkit.android.model.v r1 = zendesk.conversationkit.android.model.v.TEXT
            java.lang.String r1 = r1.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r0, r1)
            java.lang.Class<zendesk.conversationkit.android.model.MessageContent$Form> r0 = zendesk.conversationkit.android.model.MessageContent.Form.class
            zendesk.conversationkit.android.model.v r1 = zendesk.conversationkit.android.model.v.FORM
            java.lang.String r1 = r1.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r0, r1)
            java.lang.Class<zendesk.conversationkit.android.model.MessageContent$FormResponse> r0 = zendesk.conversationkit.android.model.MessageContent.FormResponse.class
            zendesk.conversationkit.android.model.v r1 = zendesk.conversationkit.android.model.v.FORM_RESPONSE
            java.lang.String r1 = r1.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r0, r1)
            java.lang.Class<zendesk.conversationkit.android.model.MessageContent$Carousel> r0 = zendesk.conversationkit.android.model.MessageContent.Carousel.class
            zendesk.conversationkit.android.model.v r1 = zendesk.conversationkit.android.model.v.CAROUSEL
            java.lang.String r1 = r1.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r0, r1)
            java.lang.Class<zendesk.conversationkit.android.model.MessageContent$File> r0 = zendesk.conversationkit.android.model.MessageContent.File.class
            zendesk.conversationkit.android.model.v r1 = zendesk.conversationkit.android.model.v.FILE
            java.lang.String r1 = r1.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r0, r1)
            java.lang.Class<zendesk.conversationkit.android.model.MessageContent$FileUpload> r0 = zendesk.conversationkit.android.model.MessageContent.FileUpload.class
            zendesk.conversationkit.android.model.v r1 = zendesk.conversationkit.android.model.v.FILE_UPLOAD
            java.lang.String r1 = r1.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r0, r1)
            java.lang.Class<zendesk.conversationkit.android.model.MessageContent$Image> r0 = zendesk.conversationkit.android.model.MessageContent.Image.class
            zendesk.conversationkit.android.model.v r1 = zendesk.conversationkit.android.model.v.IMAGE
            java.lang.String r1 = r1.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r0, r1)
            com.squareup.moshi.r$b r3 = r3.a(r4)
            java.lang.Class<zendesk.conversationkit.android.model.Field> r4 = zendesk.conversationkit.android.model.Field.class
            com.squareup.moshi.adapters.b r4 = com.squareup.moshi.adapters.b.b(r4, r5)
            java.lang.Class<zendesk.conversationkit.android.model.Field$Text> r0 = zendesk.conversationkit.android.model.Field.Text.class
            zendesk.conversationkit.android.model.n r1 = zendesk.conversationkit.android.model.n.TEXT
            java.lang.String r1 = r1.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r0, r1)
            java.lang.Class<zendesk.conversationkit.android.model.Field$Email> r0 = zendesk.conversationkit.android.model.Field.Email.class
            zendesk.conversationkit.android.model.n r1 = zendesk.conversationkit.android.model.n.EMAIL
            java.lang.String r1 = r1.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r0, r1)
            java.lang.Class<zendesk.conversationkit.android.model.Field$Select> r0 = zendesk.conversationkit.android.model.Field.Select.class
            zendesk.conversationkit.android.model.n r1 = zendesk.conversationkit.android.model.n.SELECT
            java.lang.String r1 = r1.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r0, r1)
            com.squareup.moshi.r$b r3 = r3.a(r4)
            java.lang.Class<zendesk.conversationkit.android.model.MessageAction> r4 = zendesk.conversationkit.android.model.MessageAction.class
            com.squareup.moshi.adapters.b r4 = com.squareup.moshi.adapters.b.b(r4, r5)
            java.lang.Class<zendesk.conversationkit.android.model.MessageAction$Buy> r5 = zendesk.conversationkit.android.model.MessageAction.Buy.class
            zendesk.conversationkit.android.model.q r0 = zendesk.conversationkit.android.model.q.BUY
            java.lang.String r0 = r0.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r5, r0)
            java.lang.Class<zendesk.conversationkit.android.model.MessageAction$Link> r5 = zendesk.conversationkit.android.model.MessageAction.Link.class
            zendesk.conversationkit.android.model.q r0 = zendesk.conversationkit.android.model.q.LINK
            java.lang.String r0 = r0.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r5, r0)
            java.lang.Class<zendesk.conversationkit.android.model.MessageAction$LocationRequest> r5 = zendesk.conversationkit.android.model.MessageAction.LocationRequest.class
            zendesk.conversationkit.android.model.q r0 = zendesk.conversationkit.android.model.q.LOCATION_REQUEST
            java.lang.String r0 = r0.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r5, r0)
            java.lang.Class<zendesk.conversationkit.android.model.MessageAction$Postback> r5 = zendesk.conversationkit.android.model.MessageAction.Postback.class
            zendesk.conversationkit.android.model.q r0 = zendesk.conversationkit.android.model.q.POSTBACK
            java.lang.String r0 = r0.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r5, r0)
            java.lang.Class<zendesk.conversationkit.android.model.MessageAction$Reply> r5 = zendesk.conversationkit.android.model.MessageAction.Reply.class
            zendesk.conversationkit.android.model.q r0 = zendesk.conversationkit.android.model.q.REPLY
            java.lang.String r0 = r0.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r5, r0)
            java.lang.Class<zendesk.conversationkit.android.model.MessageAction$Share> r5 = zendesk.conversationkit.android.model.MessageAction.Share.class
            zendesk.conversationkit.android.model.q r0 = zendesk.conversationkit.android.model.q.SHARE
            java.lang.String r0 = r0.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r5, r0)
            java.lang.Class<zendesk.conversationkit.android.model.MessageAction$WebView> r5 = zendesk.conversationkit.android.model.MessageAction.WebView.class
            zendesk.conversationkit.android.model.q r0 = zendesk.conversationkit.android.model.q.WEBVIEW
            java.lang.String r0 = r0.name()
            com.squareup.moshi.adapters.b r4 = r4.c(r5, r0)
            com.squareup.moshi.r$b r3 = r3.a(r4)
            java.lang.Class<java.util.Date> r4 = java.util.Date.class
            com.squareup.moshi.adapters.c r5 = new com.squareup.moshi.adapters.c
            r5.<init>()
            com.squareup.moshi.r$b r3 = r3.b(r4, r5)
            com.squareup.moshi.r r3 = r3.c()
            java.lang.String r4 = "Builder()\n        .add(\n…apter())\n        .build()"
            kotlin.jvm.internal.k.d(r3, r4)
        L_0x0116:
            r2.<init>(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.n.<init>(com.squareup.moshi.r, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Nullable
    public <T> T a(@NotNull String source, @NotNull Class<T> type) {
        k.e(source, "source");
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        try {
            return this.a.c(type).c(source);
        } catch (Exception e) {
            return null;
        }
    }

    @NotNull
    public <T> String b(T data, @NotNull Class<T> type) {
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        String h = this.a.c(type).h(data);
        k.d(h, "moshi.adapter(type).toJson(data)");
        return h;
    }
}
