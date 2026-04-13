package okhttp3.internal.http;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.net.Proxy;
import kotlin.jvm.internal.k;
import okhttp3.b0;
import okhttp3.v;
import org.jetbrains.annotations.NotNull;

/* compiled from: RequestLine.kt */
public final class i {
    public static final i a = new i();

    private i() {
    }

    @NotNull
    public final String a(@NotNull b0 request, @NotNull Proxy.Type proxyType) {
        k.f(request, Progress.REQUEST);
        k.f(proxyType, "proxyType");
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        $this$buildString.append(request.h());
        $this$buildString.append(' ');
        i iVar = a;
        if (iVar.b(request, proxyType)) {
            $this$buildString.append(request.l());
        } else {
            $this$buildString.append(iVar.c(request.l()));
        }
        $this$buildString.append(" HTTP/1.1");
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    private final boolean b(b0 request, Proxy.Type proxyType) {
        return !request.g() && proxyType == Proxy.Type.HTTP;
    }

    @NotNull
    public final String c(@NotNull v url) {
        k.f(url, "url");
        String path = url.e();
        String query = url.g();
        if (query == null) {
            return path;
        }
        return path + '?' + query;
    }
}
