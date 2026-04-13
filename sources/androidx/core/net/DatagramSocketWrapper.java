package androidx.core.net;

import java.io.FileDescriptor;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketImpl;

public class DatagramSocketWrapper extends Socket {
    DatagramSocketWrapper(DatagramSocket socket, FileDescriptor fd) {
        super(new DatagramSocketImplWrapper(socket, fd));
    }

    public static class DatagramSocketImplWrapper extends SocketImpl {
        DatagramSocketImplWrapper(DatagramSocket socket, FileDescriptor fd) {
            this.localport = socket.getLocalPort();
            this.fd = fd;
        }

        /* access modifiers changed from: protected */
        public void accept(SocketImpl newSocket) {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public int available() {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void bind(InetAddress address, int port) {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void close() {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void connect(String host, int port) {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void connect(InetAddress address, int port) {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void create(boolean isStreaming) {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public InputStream getInputStream() {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public OutputStream getOutputStream() {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void listen(int backlog) {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void connect(SocketAddress remoteAddr, int timeout) {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void sendUrgentData(int value) {
            throw new UnsupportedOperationException();
        }

        public Object getOption(int optID) {
            throw new UnsupportedOperationException();
        }

        public void setOption(int optID, Object val) {
            throw new UnsupportedOperationException();
        }
    }
}
