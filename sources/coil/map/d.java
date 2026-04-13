package coil.map;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;

/* compiled from: ResourceUriMapper.kt */
public final class d implements b<Uri, Uri> {
    @NotNull
    private final Context a;

    public d(@NotNull Context context) {
        k.e(context, "context");
        this.a = context;
    }

    /* renamed from: b */
    public boolean a(@NotNull Uri data) {
        k.e(data, "data");
        if (k.a(data.getScheme(), "android.resource")) {
            String authority = data.getAuthority();
            if (!(authority == null || w.A(authority))) {
                List<String> pathSegments = data.getPathSegments();
                k.d(pathSegments, "data.pathSegments");
                if (pathSegments.size() == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    @NotNull
    /* renamed from: c */
    public Uri map(@NotNull Uri data) {
        k.e(data, "data");
        String packageName = data.getAuthority();
        if (packageName == null) {
            packageName = "";
        }
        Resources resources = this.a.getPackageManager().getResourcesForApplication(packageName);
        k.d(resources, "context.packageManager.getResourcesForApplication(packageName)");
        List<String> pathSegments = data.getPathSegments();
        k.d(pathSegments, "pathSegments");
        boolean z = false;
        int id = resources.getIdentifier(pathSegments.get(1), pathSegments.get(0), packageName);
        if (id != 0) {
            z = true;
        }
        if (z) {
            Uri parse = Uri.parse("android.resource://" + packageName + '/' + id);
            k.d(parse, "parse(this)");
            return parse;
        }
        throw new IllegalStateException(k.l("Invalid android.resource URI: ", data).toString());
    }
}
