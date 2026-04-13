package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.locks.Lock;
import org.glassfish.grizzly.AbstractBindingHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.utils.Exceptions;

public class TCPNIOBindingHandler extends AbstractBindingHandler {
    private final TCPNIOTransport tcpTransport;

    TCPNIOBindingHandler(TCPNIOTransport tcpTransport2) {
        super(tcpTransport2);
        this.tcpTransport = tcpTransport2;
    }

    public TCPNIOServerConnection bind(SocketAddress socketAddress) {
        return bind(socketAddress, this.tcpTransport.getServerConnectionBackLog());
    }

    public TCPNIOServerConnection bind(SocketAddress socketAddress, int backlog) {
        return bindToChannelAndAddress(this.tcpTransport.getSelectorProvider().openServerSocketChannel(), socketAddress, backlog);
    }

    public TCPNIOServerConnection bindToInherited() {
        return bindToChannelAndAddress((ServerSocketChannel) getSystemInheritedChannel(ServerSocketChannel.class), (SocketAddress) null, -1);
    }

    public void unbind(Connection connection) {
        this.tcpTransport.unbind(connection);
    }

    public static Builder builder(TCPNIOTransport transport) {
        return new Builder().transport(transport);
    }

    private TCPNIOServerConnection bindToChannelAndAddress(ServerSocketChannel serverSocketChannel, SocketAddress socketAddress, int backlog) {
        TCPNIOServerConnection serverConnection = null;
        Lock lock = this.tcpTransport.getState().getStateLocker().writeLock();
        lock.lock();
        try {
            ServerSocket serverSocket = serverSocketChannel.socket();
            this.tcpTransport.getChannelConfigurator().preConfigure(this.transport, serverSocketChannel);
            if (socketAddress != null) {
                serverSocket.bind(socketAddress, backlog);
            }
            this.tcpTransport.getChannelConfigurator().postConfigure(this.transport, serverSocketChannel);
            TCPNIOServerConnection serverConnection2 = this.tcpTransport.obtainServerNIOConnection(serverSocketChannel);
            serverConnection2.setProcessor(getProcessor());
            serverConnection2.setProcessorSelector(getProcessorSelector());
            this.tcpTransport.serverConnections.add(serverConnection2);
            serverConnection2.resetProperties();
            if (!this.tcpTransport.isStopped()) {
                this.tcpTransport.listenServerConnection(serverConnection2);
            }
            lock.unlock();
            return serverConnection2;
        } catch (Exception e) {
            if (serverConnection != null) {
                this.tcpTransport.serverConnections.remove(serverConnection);
                serverConnection.closeSilently();
            } else {
                try {
                    serverSocketChannel.close();
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
        private TCPNIOTransport transport;

        public Builder transport(TCPNIOTransport transport2) {
            this.transport = transport2;
            return this;
        }

        public TCPNIOBindingHandler build() {
            return (TCPNIOBindingHandler) super.build();
        }

        /* access modifiers changed from: protected */
        public AbstractBindingHandler create() {
            if (this.transport != null) {
                return new TCPNIOBindingHandler(this.transport);
            }
            throw new IllegalStateException("Unable to create TCPNIOBindingHandler - transport is null");
        }
    }
}
