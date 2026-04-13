package okhttp3.internal.connection;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import kotlin.jvm.internal.k;
import okhttp3.l;
import org.jetbrains.annotations.NotNull;

/* compiled from: ConnectionSpecSelector.kt */
public final class b {
    private int a;
    private boolean b;
    private boolean c;
    private final List<l> d;

    public b(@NotNull List<l> connectionSpecs) {
        k.f(connectionSpecs, "connectionSpecs");
        this.d = connectionSpecs;
    }

    @NotNull
    public final l a(@NotNull SSLSocket sslSocket) {
        k.f(sslSocket, "sslSocket");
        l tlsConfiguration = null;
        int i = this.a;
        int size = this.d.size();
        while (true) {
            if (i >= size) {
                break;
            }
            l connectionSpec = this.d.get(i);
            if (connectionSpec.e(sslSocket)) {
                tlsConfiguration = connectionSpec;
                this.a = i + 1;
                break;
            }
            i++;
        }
        if (tlsConfiguration == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to find acceptable protocols. isFallback=");
            sb.append(this.c);
            sb.append(StringUtil.COMMA);
            sb.append(" modes=");
            sb.append(this.d);
            sb.append(StringUtil.COMMA);
            sb.append(" supported protocols=");
            String[] enabledProtocols = sslSocket.getEnabledProtocols();
            if (enabledProtocols == null) {
                k.n();
            }
            String arrays = Arrays.toString(enabledProtocols);
            k.b(arrays, "java.util.Arrays.toString(this)");
            sb.append(arrays);
            throw new UnknownServiceException(sb.toString());
        }
        this.b = c(sslSocket);
        tlsConfiguration.c(sslSocket, this.c);
        return tlsConfiguration;
    }

    public final boolean b(@NotNull IOException e) {
        k.f(e, "e");
        this.c = true;
        if (!this.b) {
            return false;
        }
        if (e instanceof ProtocolException) {
            return false;
        }
        if (e instanceof InterruptedIOException) {
            return false;
        }
        if ((e instanceof SSLHandshakeException) && (e.getCause() instanceof CertificateException)) {
            return false;
        }
        if (e instanceof SSLPeerUnverifiedException) {
            return false;
        }
        if (e instanceof SSLException) {
            return true;
        }
        return false;
    }

    private final boolean c(SSLSocket socket) {
        int size = this.d.size();
        for (int i = this.a; i < size; i++) {
            if (this.d.get(i).e(socket)) {
                return true;
            }
        }
        return false;
    }
}
