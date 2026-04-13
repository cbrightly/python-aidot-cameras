package org.apache.http.conn;

import java.net.Socket;
import javax.net.ssl.SSLSession;
import org.apache.http.h;
import org.apache.http.m;

/* compiled from: ManagedHttpClientConnection */
public interface r extends h, m {
    void O0(Socket socket);

    SSLSession S0();

    Socket q();
}
