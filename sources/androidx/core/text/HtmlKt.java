package androidx.core.text;

import android.text.Html;
import android.text.Spanned;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Html.kt */
public final class HtmlKt {
    public static /* synthetic */ Spanned parseAsHtml$default(String $this$parseAsHtml_u24default, int flags, Html.ImageGetter imageGetter, Html.TagHandler tagHandler, int i, Object obj) {
        if ((i & 1) != 0) {
            flags = 0;
        }
        if ((i & 2) != 0) {
            imageGetter = null;
        }
        if ((i & 4) != 0) {
            tagHandler = null;
        }
        k.e($this$parseAsHtml_u24default, "<this>");
        Spanned fromHtml = HtmlCompat.fromHtml($this$parseAsHtml_u24default, flags, imageGetter, tagHandler);
        k.d(fromHtml, "fromHtml(this, flags, imageGetter, tagHandler)");
        return fromHtml;
    }

    @NotNull
    public static final Spanned parseAsHtml(@NotNull String $this$parseAsHtml, int flags, @Nullable Html.ImageGetter imageGetter, @Nullable Html.TagHandler tagHandler) {
        k.e($this$parseAsHtml, "<this>");
        Spanned fromHtml = HtmlCompat.fromHtml($this$parseAsHtml, flags, imageGetter, tagHandler);
        k.d(fromHtml, "fromHtml(this, flags, imageGetter, tagHandler)");
        return fromHtml;
    }

    public static /* synthetic */ String toHtml$default(Spanned $this$toHtml_u24default, int option, int i, Object obj) {
        if ((i & 1) != 0) {
            option = 0;
        }
        k.e($this$toHtml_u24default, "<this>");
        String html = HtmlCompat.toHtml($this$toHtml_u24default, option);
        k.d(html, "toHtml(this, option)");
        return html;
    }

    @NotNull
    public static final String toHtml(@NotNull Spanned $this$toHtml, int option) {
        k.e($this$toHtml, "<this>");
        String html = HtmlCompat.toHtml($this$toHtml, option);
        k.d(html, "toHtml(this, option)");
        return html;
    }
}
