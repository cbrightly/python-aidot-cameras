package org.apache.http.impl.auth;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.auth.j;
import org.apache.http.auth.l;
import org.apache.http.b;
import org.apache.http.d;
import org.apache.http.e;
import org.apache.http.message.g;
import org.apache.http.message.v;

/* compiled from: RFC2617Scheme */
public abstract class o extends a implements Serializable {
    private static final long serialVersionUID = -2845454858205884623L;
    private transient Charset c;
    private final Map<String, String> params;

    @Deprecated
    public abstract /* synthetic */ d authenticate(l lVar, org.apache.http.o oVar);

    public abstract /* synthetic */ String getSchemeName();

    public abstract /* synthetic */ boolean isComplete();

    public abstract /* synthetic */ boolean isConnectionBased();

    @Deprecated
    public o(j challengeState) {
        super(challengeState);
        this.params = new HashMap();
        this.c = b.b;
    }

    public o(Charset credentialsCharset) {
        this.params = new HashMap();
        this.c = credentialsCharset != null ? credentialsCharset : b.b;
    }

    public o() {
        this(b.b);
    }

    public Charset getCredentialsCharset() {
        Charset charset = this.c;
        return charset != null ? charset : b.b;
    }

    /* access modifiers changed from: package-private */
    public String getCredentialsCharset(org.apache.http.o request) {
        String charset = (String) request.getParams().getParameter("http.auth.credential-charset");
        if (charset == null) {
            return getCredentialsCharset().name();
        }
        return charset;
    }

    /* access modifiers changed from: protected */
    public void parseChallenge(org.apache.http.util.d buffer, int pos, int len) {
        e[] elements = g.b.b(buffer, new v(pos, buffer.length()));
        this.params.clear();
        for (e element : elements) {
            this.params.put(element.getName().toLowerCase(Locale.ROOT), element.getValue());
        }
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getParameters() {
        return this.params;
    }

    public String getParameter(String name) {
        if (name == null) {
            return null;
        }
        return this.params.get(name.toLowerCase(Locale.ROOT));
    }

    public String getRealm() {
        return getParameter("realm");
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeUTF(this.c.name());
        out.writeObject(this.challengeState);
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        Charset a = org.apache.http.util.e.a(in.readUTF());
        this.c = a;
        if (a == null) {
            this.c = b.b;
        }
        this.challengeState = (j) in.readObject();
    }
}
