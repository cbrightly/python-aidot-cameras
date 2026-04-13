package zendesk.android.internal.extension;

import android.content.Context;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;
import java.util.Locale;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: AndroidExt.kt */
public final class a {
    @NotNull
    public static final String a(@NotNull Context $this$getLanguageTag) {
        k.e($this$getLanguageTag, "<this>");
        LocaleListCompat locales = ConfigurationCompat.getLocales($this$getLanguageTag.getResources().getConfiguration());
        k.d(locales, "getLocales(resources.configuration)");
        if (locales.isEmpty()) {
            String languageTag = Locale.getDefault().toLanguageTag();
            k.d(languageTag, "{\n        Locale.getDefa…t().toLanguageTag()\n    }");
            return languageTag;
        }
        String languageTag2 = locales.get(0).toLanguageTag();
        k.d(languageTag2, "{\n        locales[0].toLanguageTag()\n    }");
        return languageTag2;
    }
}
