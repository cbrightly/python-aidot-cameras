package com.leedarson.serviceimpl.tcp.socket;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import timber.log.a;

/* compiled from: LdsSocketServer */
public class c extends Thread {
    private static c c;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static long d = 0;
    private final String f = "LdsSocketServer";
    private ServerSocket q;
    private boolean x = true;
    private b y = new b();
    private d z;

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 9092, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g("LdsSocketServer").a(msg, new Object[0]);
        }
    }

    public void run() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9093, new Class[0], Void.TYPE).isSupported) {
            super.run();
            try {
                this.q = new ServerSocket(16655);
                a("服务端socket server开启:" + com.leedarson.base.webservice.utils.a.a().getHostAddress() + ":" + 16655);
            } catch (IOException e) {
                try {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    a("ServerSocket Exception:" + e2.toString());
                    return;
                }
            }
            while (this.x) {
                a("开始监听客户端...");
                Socket socket = this.q.accept();
                String ip = socket.getInetAddress().getHostName();
                a("远程socket 客户端已连接:" + ip + ",port:" + socket.getPort());
                a client = new a(ip, socket);
                this.y.b(ip, client);
                client.setOnSocketStatusChangeListener(c.z);
                client.r();
            }
        }
    }

    public static c c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9094, new Class[0], c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        if (System.currentTimeMillis() - d <= 800) {
            return null;
        }
        d = System.currentTimeMillis();
        b();
        c cVar = new c();
        c = cVar;
        cVar.start();
        return c;
    }

    public static void b() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9095, new Class[0], Void.TYPE).isSupported) {
            c cVar = c;
            if (cVar != null) {
                for (a client : cVar.y.a().values()) {
                    if (client.m()) {
                        client.s();
                    }
                }
                ServerSocket serverSocket = c.q;
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            c = null;
        }
    }

    public void setOnSocketClientListener(d socketClientListener) {
        this.z = socketClientListener;
    }
}
