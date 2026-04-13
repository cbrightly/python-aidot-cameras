package org.glassfish.grizzly.nio.transport;

import org.glassfish.grizzly.NIOTransportBuilder;
import org.glassfish.grizzly.nio.NIOTransport;

public class TCPNIOTransportBuilder extends NIOTransportBuilder<TCPNIOTransportBuilder> {
    protected boolean keepAlive = true;
    protected int linger = -1;
    protected int serverConnectionBackLog = 4096;
    protected int serverSocketSoTimeout = 0;
    protected boolean tcpNoDelay = true;

    protected TCPNIOTransportBuilder(Class<? extends TCPNIOTransport> transportClass) {
        super(transportClass);
    }

    public static TCPNIOTransportBuilder newInstance() {
        return new TCPNIOTransportBuilder(TCPNIOTransport.class);
    }

    public boolean isKeepAlive() {
        return this.keepAlive;
    }

    public TCPNIOTransportBuilder setKeepAlive(boolean keepAlive2) {
        this.keepAlive = keepAlive2;
        return getThis();
    }

    public int getLinger() {
        return this.linger;
    }

    public TCPNIOTransportBuilder setLinger(int linger2) {
        this.linger = linger2;
        return getThis();
    }

    public int getServerConnectionBackLog() {
        return this.serverConnectionBackLog;
    }

    public TCPNIOTransportBuilder setServerConnectionBackLog(int serverConnectionBackLog2) {
        this.serverConnectionBackLog = serverConnectionBackLog2;
        return getThis();
    }

    public int getServerSocketSoTimeout() {
        return this.serverSocketSoTimeout;
    }

    public TCPNIOTransportBuilder setServerSocketSoTimeout(int serverSocketSoTimeout2) {
        this.serverSocketSoTimeout = serverSocketSoTimeout2;
        return getThis();
    }

    public boolean isTcpNoDelay() {
        return this.tcpNoDelay;
    }

    public TCPNIOTransportBuilder setTcpNoDelay(boolean tcpNoDelay2) {
        this.tcpNoDelay = tcpNoDelay2;
        return getThis();
    }

    public TCPNIOTransport build() {
        TCPNIOTransport transport = (TCPNIOTransport) super.build();
        transport.setKeepAlive(this.keepAlive);
        transport.setLinger(this.linger);
        transport.setServerConnectionBackLog(this.serverConnectionBackLog);
        transport.setTcpNoDelay(this.tcpNoDelay);
        transport.setServerSocketSoTimeout(this.serverSocketSoTimeout);
        return transport;
    }

    /* access modifiers changed from: protected */
    public TCPNIOTransportBuilder getThis() {
        return this;
    }

    /* access modifiers changed from: protected */
    public NIOTransport create(String name) {
        return new TCPNIOTransport(name);
    }
}
