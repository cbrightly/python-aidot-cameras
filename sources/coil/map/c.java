package coil.map;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import androidx.annotation.DrawableRes;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ResourceIntMapper.kt */
public final class c implements b<Integer, Uri> {
    @NotNull
    private final Context a;

    public c(@NotNull Context context) {
        k.e(context, "context");
        this.a = context;
    }

    public /* bridge */ /* synthetic */ boolean a(Object data) {
        return b(((Number) data).intValue());
    }

    public /* bridge */ /* synthetic */ Object map(Object data) {
        return c(((Number) data).intValue());
    }

    public boolean b(@DrawableRes int data) {
        try {
            return this.a.getResources().getResourceEntryName(data) != null;
        } catch (Resources.NotFoundException e) {
            return false;
        }
    }

    @NotNull
    public Uri c(@DrawableRes int data) {
        Uri parse = Uri.parse("android.resource://" + this.a.getPackageName() + '/' + data);
        k.d(parse, "parse(this)");
        return parse;
    }
}
