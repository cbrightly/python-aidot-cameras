package org.apache.http.impl.execchain;

import org.apache.http.HttpException;
import org.apache.http.q;

public class TunnelRefusedException extends HttpException {
    private static final long serialVersionUID = -8646722842745617323L;
    private final q response;

    public TunnelRefusedException(String message, q response2) {
        super(message);
        this.response = response2;
    }

    public q getResponse() {
        return this.response;
    }
}
