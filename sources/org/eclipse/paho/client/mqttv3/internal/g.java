package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.b;
import org.eclipse.paho.client.mqttv3.f;
import org.eclipse.paho.client.mqttv3.h;
import org.eclipse.paho.client.mqttv3.i;
import org.eclipse.paho.client.mqttv3.internal.wire.u;
import org.eclipse.paho.client.mqttv3.j;
import org.eclipse.paho.client.mqttv3.o;

/* compiled from: ConnectActionListener */
public class g implements b {
    private i a;
    private MqttAsyncClient b;
    private a c;
    private j d;
    private o e;
    private Object f;
    private b g;
    private int h;
    private h i;
    private boolean j;

    public g(MqttAsyncClient client, i persistence, a comms, j options, o userToken, Object userContext, b userCallback, boolean reconnect) {
        this.a = persistence;
        this.b = client;
        this.c = comms;
        this.d = options;
        this.e = userToken;
        this.f = userContext;
        this.g = userCallback;
        this.h = options.g();
        this.j = reconnect;
    }

    public void onSuccess(f token) {
        if (this.h == 0) {
            this.d.x(0);
        }
        this.e.a.n(token.b(), (MqttException) null);
        this.e.a.o();
        this.e.a.r(this.b);
        this.c.F();
        if (this.g != null) {
            this.e.j(this.f);
            this.g.onSuccess(this.e);
        }
        if (this.i != null) {
            this.i.connectComplete(this.j, this.c.w()[this.c.v()].a());
        }
    }

    public void onFailure(f token, Throwable exception) {
        MqttException ex;
        int numberOfURIs = this.c.w().length;
        int index = this.c.v();
        if (index + 1 < numberOfURIs || (this.h == 0 && this.d.g() == 4)) {
            if (this.h != 0) {
                this.c.J(index + 1);
            } else if (this.d.g() == 4) {
                this.d.x(3);
            } else {
                this.d.x(4);
                this.c.J(index + 1);
            }
            try {
                a();
            } catch (MqttPersistenceException e2) {
                onFailure(token, e2);
            }
        } else {
            if (this.h == 0) {
                this.d.x(0);
            }
            if (exception instanceof MqttException) {
                ex = (MqttException) exception;
            } else {
                ex = new MqttException(exception);
            }
            this.e.a.n((u) null, ex);
            this.e.a.o();
            this.e.a.r(this.b);
            if (this.g != null) {
                this.e.j(this.f);
                this.g.onFailure(this.e, exception);
            }
        }
    }

    public void a() {
        o token = new o(this.b.f0());
        token.i(this);
        token.j(this);
        this.a.H0(this.b.f0(), this.b.T());
        if (this.d.q()) {
            this.a.clear();
        }
        if (this.d.g() == 0) {
            this.d.x(4);
        }
        try {
            this.c.p(this.d, token);
        } catch (MqttException e2) {
            onFailure(token, e2);
        }
    }

    public void b(h mqttCallbackExtended) {
        this.i = mqttCallbackExtended;
    }
}
