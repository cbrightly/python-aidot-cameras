package com.leedarson.tcp;

import android.content.Context;
import android.text.TextUtils;
import com.leedarson.secret.JNIUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;

/* compiled from: NettyClientInitializer */
public class f extends ChannelInitializer<SocketChannel> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private g c;
    private Context d;
    private int f;
    private String q;
    private int x = 28;
    private String y = null;

    public /* bridge */ /* synthetic */ void initChannel(Channel channel) {
        if (!PatchProxy.proxy(new Object[]{channel}, this, changeQuickRedirect, false, 10768, new Class[]{Channel.class}, Void.TYPE).isSupported) {
            b((SocketChannel) channel);
        }
    }

    public f(g listener, Context context, int tls, String aesKey, int heartBeatTick) {
        if (listener == null) {
            throw new IllegalArgumentException("listener == null ");
        } else if (context != null) {
            this.c = listener;
            this.d = context;
            this.f = tls;
            this.q = aesKey;
            if (heartBeatTick > 0) {
                this.x = heartBeatTick;
            }
        } else {
            throw new IllegalArgumentException("context == null ");
        }
    }

    public void b(SocketChannel ch) {
        if (!PatchProxy.proxy(new Object[]{ch}, this, changeQuickRedirect, false, 10766, new Class[]{SocketChannel.class}, Void.TYPE).isSupported) {
            ChannelPipeline pipeline = ch.pipeline();
            if (this.f == 1) {
                SSLEngine sslEngine = a(this.d).createSSLEngine();
                sslEngine.setUseClientMode(true);
                pipeline.addFirst("ssl", (ChannelHandler) new SslHandler(sslEngine));
            }
            pipeline.addLast("IdleStateHandler", (ChannelHandler) new IdleStateHandler(0, this.x, 0));
            pipeline.addLast("decoder", (ChannelHandler) new i(1048576, 4, 4, 0, 0, this.q));
            pipeline.addLast("encoder", (ChannelHandler) new j(this.q));
            pipeline.addLast(new e(this.c));
        }
    }

    private SSLContext a(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 10767, new Class[]{Context.class}, SSLContext.class);
        if (proxy.isSupported) {
            return (SSLContext) proxy.result;
        }
        CertificateFactory cAf = CertificateFactory.getInstance("X.509");
        if (TextUtils.isEmpty(this.y)) {
            this.y = JNIUtil.getInstance().getStr8();
        }
        InputStream inputStream = new ByteArrayInputStream(this.y.getBytes());
        KeyStore caks = KeyStore.getInstance(KeyStore.getDefaultType());
        caks.load((InputStream) null, (char[]) null);
        caks.setCertificateEntry("ca-certificate", (X509Certificate) cAf.generateCertificate(inputStream));
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(caks);
        inputStream.close();
        SSLContext sslcontext = SSLContext.getInstance("TLSv1.2");
        sslcontext.init((KeyManager[]) null, tmf.getTrustManagers(), (SecureRandom) null);
        return sslcontext;
    }
}
