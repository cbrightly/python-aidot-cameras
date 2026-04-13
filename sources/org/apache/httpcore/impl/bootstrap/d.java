package org.apache.httpcore.impl.bootstrap;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;
import org.apache.httpcore.HttpConnectionFactory;
import org.apache.httpcore.config.a;
import org.apache.httpcore.config.c;
import org.apache.httpcore.i;
import org.apache.httpcore.impl.DefaultBHttpServerConnection;
import org.apache.httpcore.impl.e;
import org.apache.httpcore.o;
import org.apache.httpcore.protocol.HttpRequestHandler;
import org.apache.httpcore.protocol.g;
import org.apache.httpcore.protocol.h;
import org.apache.httpcore.protocol.j;
import org.apache.httpcore.protocol.k;
import org.apache.httpcore.protocol.l;
import org.apache.httpcore.protocol.n;
import org.apache.httpcore.protocol.p;
import org.apache.httpcore.q;
import org.apache.httpcore.r;

/* compiled from: ServerBootstrap */
public class d {
    private int a;
    private InetAddress b;
    private c c;
    private a d;
    private LinkedList<o> e;
    private LinkedList<o> f;
    private LinkedList<r> g;
    private LinkedList<r> h;
    private String i;
    private h j;
    private org.apache.httpcore.a k;
    private q l;
    private k m;
    private Map<String, j> n;
    private g o;
    private ServerSocketFactory p;
    private SSLContext q;
    private c r;
    private i<? extends org.apache.httpcore.impl.c> s;
    private org.apache.httpcore.c t;

    private d() {
    }

    public static d a() {
        return new d();
    }

    public final d e(int listenerPort) {
        this.a = listenerPort;
        return this;
    }

    public final d f(InetAddress localAddress) {
        this.b = localAddress;
        return this;
    }

    public final d i(c socketConfig) {
        this.c = socketConfig;
        return this;
    }

    public final d g(String serverInfo) {
        this.i = serverInfo;
        return this;
    }

    public final d c(String pattern, j handler) {
        if (pattern == null || handler == null) {
            return this;
        }
        if (this.n == null) {
            this.n = new HashMap();
        }
        this.n.put(pattern, handler);
        return this;
    }

    public final d k(c sslSetupHandler) {
        this.r = sslSetupHandler;
        return this;
    }

    public final d h(ServerSocketFactory serverSocketFactory) {
        this.p = serverSocketFactory;
        return this;
    }

    public final d j(SSLContext sslContext) {
        this.q = sslContext;
        return this;
    }

    public final d d(org.apache.httpcore.c exceptionLogger) {
        this.t = exceptionLogger;
        return this;
    }

    public a b() {
        q responseFactoryCopy;
        h httpProcessorCopy = this.j;
        if (httpProcessorCopy == null) {
            org.apache.httpcore.protocol.i b2 = org.apache.httpcore.protocol.i.h();
            LinkedList<o> linkedList = this.e;
            if (linkedList != null) {
                Iterator it = linkedList.iterator();
                while (it.hasNext()) {
                    b2.c((o) it.next());
                }
            }
            LinkedList<r> linkedList2 = this.g;
            if (linkedList2 != null) {
                Iterator it2 = linkedList2.iterator();
                while (it2.hasNext()) {
                    b2.d((r) it2.next());
                }
            }
            String serverInfoCopy = this.i;
            if (serverInfoCopy == null) {
                serverInfoCopy = "Apache-HttpCore/1.1";
            }
            b2.a(new p(), new org.apache.httpcore.protocol.q(serverInfoCopy), new org.apache.httpcore.protocol.o(), new n());
            LinkedList<o> linkedList3 = this.f;
            if (linkedList3 != null) {
                Iterator it3 = linkedList3.iterator();
                while (it3.hasNext()) {
                    b2.e((o) it3.next());
                }
            }
            LinkedList<r> linkedList4 = this.h;
            if (linkedList4 != null) {
                Iterator it4 = linkedList4.iterator();
                while (it4.hasNext()) {
                    b2.f((r) it4.next());
                }
            }
            httpProcessorCopy = b2.g();
        }
        k handlerMapperCopy = this.m;
        if (handlerMapperCopy == null) {
            org.apache.httpcore.protocol.r reqistry = new org.apache.httpcore.protocol.r();
            Map<String, j> map = this.n;
            if (map != null) {
                for (Map.Entry<String, HttpRequestHandler> entry : map.entrySet()) {
                    reqistry.c(entry.getKey(), (j) entry.getValue());
                }
            }
            handlerMapperCopy = reqistry;
        }
        org.apache.httpcore.a connStrategyCopy = this.k;
        if (connStrategyCopy == null) {
            connStrategyCopy = e.a;
        }
        q responseFactoryCopy2 = this.l;
        if (responseFactoryCopy2 == null) {
            responseFactoryCopy = org.apache.httpcore.impl.g.a;
        } else {
            responseFactoryCopy = responseFactoryCopy2;
        }
        l lVar = new l(httpProcessorCopy, connStrategyCopy, responseFactoryCopy, handlerMapperCopy, this.o);
        ServerSocketFactory serverSocketFactoryCopy = this.p;
        if (serverSocketFactoryCopy == null) {
            SSLContext sSLContext = this.q;
            if (sSLContext != null) {
                serverSocketFactoryCopy = sSLContext.getServerSocketFactory();
            } else {
                serverSocketFactoryCopy = ServerSocketFactory.getDefault();
            }
        }
        HttpConnectionFactory<? extends DefaultBHttpServerConnection> connectionFactoryCopy = this.s;
        if (connectionFactoryCopy == null) {
            if (this.d != null) {
                connectionFactoryCopy = new org.apache.httpcore.impl.d(this.d);
            } else {
                connectionFactoryCopy = org.apache.httpcore.impl.d.a;
            }
        }
        org.apache.httpcore.c exceptionLoggerCopy = this.t;
        if (exceptionLoggerCopy == null) {
            exceptionLoggerCopy = org.apache.httpcore.c.a;
        }
        int i2 = this.a;
        int i3 = i2 > 0 ? i2 : 0;
        InetAddress inetAddress = this.b;
        c cVar = this.c;
        if (cVar == null) {
            cVar = c.c;
        }
        return new a(i3, inetAddress, cVar, serverSocketFactoryCopy, lVar, connectionFactoryCopy, this.r, exceptionLoggerCopy);
    }
}
