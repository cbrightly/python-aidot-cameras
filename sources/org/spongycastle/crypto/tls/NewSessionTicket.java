package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;

public class NewSessionTicket {
    protected long a;
    protected byte[] b;

    public NewSessionTicket(long ticketLifetimeHint, byte[] ticket) {
        this.a = ticketLifetimeHint;
        this.b = ticket;
    }

    public void a(OutputStream output) {
        TlsUtils.H0(this.a, output);
        TlsUtils.A0(this.b, output);
    }

    public static NewSessionTicket b(InputStream input) {
        return new NewSessionTicket(TlsUtils.p0(input), TlsUtils.g0(input));
    }
}
