package zendesk.messaging.android.internal.conversationscreen.attachments;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.luck.picture.lib.compress.Checker;
import com.luck.picture.lib.config.PictureMimeType;
import com.yanzhenjie.andserver.util.h;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.jvm.internal.k;
import kotlin.t;
import org.jetbrains.annotations.NotNull;

/* compiled from: Attachment.kt */
public final class c {
    @NotNull
    private static final Map<String, String> a = l0.h(t.a("3g2", "video/3gpp2"), t.a("3gp", "video/3gpp"), t.a("7z", "application/x-7z-compressed"), t.a("aac", "audio/aac"), t.a("amr", PictureMimeType.MIME_TYPE_AUDIO_AMR), t.a("avi", "video/x-msvideo"), t.a("bmp", "image/bmp"), t.a("csv", "text/csv"), t.a("doc", "application/msword"), t.a("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"), t.a("eml", h.APPLICATION_OCTET_STREAM_VALUE), t.a("gif", h.IMAGE_GIF_VALUE), t.a("heic", Checker.MIME_TYPE_HEIC), t.a("heif", "image/heif"), t.a("ics", "text/calendar"), t.a("jfif", "image/jpeg"), t.a("jpeg", "image/jpeg"), t.a(FileUtil.JPG, "image/jpeg"), t.a(CacheEntity.KEY, "application/vnd.apple.keynote"), t.a("log", h.TEXT_PLAIN_VALUE), t.a("m4a", "audio/m4a"), t.a("m4v", "video/mp4"), t.a("mov", "video/quicktime"), t.a("mp3", PictureMimeType.MIME_TYPE_AUDIO), t.a("mp4", "video/mp4"), t.a("mp4a", "application/mp4"), t.a("mpeg", "video/mpeg"), t.a("mpg", "video/mpeg"), t.a("mpga", PictureMimeType.MIME_TYPE_AUDIO), t.a("numbers", "application/vnd.apple.numbers"), t.a(".odp", "application/vnd.oasis.opendocument.presentation"), t.a(".ods", "application/vnd.oasis.opendocument.spreadsheet"), t.a("odt", "application/vnd.oasis.opendocument.text"), t.a("oga", "audio/ogg"), t.a("ogg", "application/ogg"), t.a("ogv", "video/ogg"), t.a(".otf", "font/otf"), t.a("pages", "application/vnd.apple.pages"), t.a("pdf", h.APPLICATION_PDF_VALUE), t.a("png", "image/png"), t.a("pps", "application/vnd.ms-powerpoint"), t.a("ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow"), t.a("ppt", "application/vnd.ms-powerpoint"), t.a("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"), t.a("qt", "video/quicktime"), t.a("svg", "image/svg+xml"), t.a("tif", "image/tiff"), t.a("tiff", "image/tiff"), t.a(FileUtil.TXT, h.TEXT_PLAIN_VALUE), t.a("vcf", "text/vcard"), t.a("wav", "audio/wav"), t.a("webm", "video/webm"), t.a("webp", "image/webp"), t.a("wmv", "video/x-ms-wmv"), t.a("xls", "application/vnd.ms-excel"), t.a("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), t.a("xml", h.APPLICATION_XML_VALUE), t.a("yaml", "text/yaml"), t.a("yml", "text/yml"));

    @NotNull
    public static final String a(@NotNull String extension) {
        k.e(extension, "extension");
        String str = a.get(extension);
        return str == null ? "" : str;
    }
}
