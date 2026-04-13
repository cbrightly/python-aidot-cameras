package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.locks.Lock;
import org.glassfish.grizzly.AbstractBindingHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.utils.Exceptions;

public class UDPNIOBindingHandler extends AbstractBindingHandler {
    private final UDPNIOTransport udpTransport;

    public UDPNIOBindingHandler(UDPNIOTransport udpTransport2) {
        super(udpTransport2);
        this.udpTransport = udpTransport2;
    }

    public UDPNIOServerConnection bind(SocketAddress socketAddress) {
        return bind(socketAddress, -1);
    }

    public UDPNIOServerConnection bind(SocketAddress socketAddress, int backlog) {
        return bindToChannel(this.udpTransport.getSelectorProvider().openDatagramChannel(), socketAddress);
    }

    public UDPNIOServerConnection bindToInherited() {
        return bindToChannel((DatagramChannel) getSystemInheritedChannel(DatagramChannel.class), (SocketAddress) null);
    }

    public void unbind(Connection connection) {
        this.udpTransport.unbind(connection);
    }

    public static Builder builder(UDPNIOTransport transport) {
        return new Builder().transport(transport);
    }

    private UDPNIOServerConnection bindToChannel(DatagramChannel serverDatagramChannel, SocketAddress socketAddress) {
        UDPNIOServerConnection serverConnection = null;
        Lock lock = this.udpTransport.getState().getStateLocker().writeLock();
        lock.lock();
        try {
            this.udpTransport.getChannelConfigurator().preConfigure(this.transport, serverDatagramChannel);
            if (socketAddress != null) {
                serverDatagramChannel.socket().bind(socketAddress);
            }
            this.udpTransport.getChannelConfigurator().postConfigure(this.transport, serverDatagramChannel);
            UDPNIOServerConnection serverConnection2 = this.udpTransport.obtainServerNIOConnection(serverDatagramChannel);
            serverConnection2.setProcessor(getProcessor());
            serverConnection2.setProcessorSelector(getProcessorSelector());
            this.udpTransport.serverConnections.add(serverConnection2);
            if (!this.udpTransport.isStopped()) {
                serverConnection2.register();
            }
            lock.unlock();
            return serverConnection2;
        } catch (Exception e) {
            if (serverConnection != null) {
                this.udpTransport.serverConnections.remove(serverConnection);
                serverConnection.closeSilently();
            } else {
                try {
                    serverDatagramChannel.close();
                } catch (IOException e2) {
                }
            }
            throw Exceptions.makeIOException(e);
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    public static class Builder extends AbstractBindingHandler.Builder<Builder> {
        private UDPNIOTransport transport;

        public UDPNIOBindingHandler build() {
            return (UDPNIOBindingHandler) super.build();
        }

        public Builder transport(UDPNIOTransport transport2) {
            this.transport = transport2;
            return this;
        }

        /* access modifiers changed from: protected */
        public AbstractBindingHandler create() {
            if (this.transport != null) {
                return new UDPNIOBindingHandler(this.transport);
            }
            throw new IllegalStateException("Unable to create TCPNIOBindingHandler - transport is null");
        }
    }
}
