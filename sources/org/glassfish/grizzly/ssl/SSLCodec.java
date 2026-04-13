package org.glassfish.grizzly.ssl;

import java.util.concurrent.Future;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Codec;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Transformer;

public class SSLCodec implements Codec<Buffer, Buffer> {
    private final SSLEngineConfigurator clientSSLEngineConfig;
    private final Transformer<Buffer, Buffer> decoder;
    private final Transformer<Buffer, Buffer> encoder;
    private final SSLEngineConfigurator serverSSLEngineConfig;

    public SSLCodec(SSLContextConfigurator config) {
        this(config.createSSLContext(true));
    }

    public SSLCodec(SSLContext sslContext) {
        this.decoder = new SSLDecoderTransformer();
        this.encoder = new SSLEncoderTransformer();
        this.serverSSLEngineConfig = new SSLEngineConfigurator(sslContext, false, false, false);
        this.clientSSLEngineConfig = new SSLEngineConfigurator(sslContext, true, false, false);
    }

    public Transformer<Buffer, Buffer> getDecoder() {
        return this.decoder;
    }

    public Transformer<Buffer, Buffer> getEncoder() {
        return this.encoder;
    }

    public SSLEngineConfigurator getClientSSLEngineConfig() {
        return this.clientSSLEngineConfig;
    }

    public SSLEngineConfigurator getServerSSLEngineConfig() {
        return this.serverSSLEngineConfig;
    }

    public Future<SSLEngine> handshake(Connection connection) {
        return handshake(connection, this.clientSSLEngineConfig);
    }

    public Future<SSLEngine> handshake(Connection connection, SSLEngineConfigurator configurator) {
        return null;
    }
}
