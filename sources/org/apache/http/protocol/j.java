package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.ProtocolException;
import org.apache.http.h;
import org.apache.http.k;
import org.apache.http.o;
import org.apache.http.q;
import org.apache.http.t;
import org.apache.http.util.a;
import org.apache.http.v;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: HttpRequestExecutor */
public class j {
    public static final int DEFAULT_WAIT_FOR_CONTINUE = 3000;
    private final int waitForContinue;

    public j(int waitForContinue2) {
        this.waitForContinue = a.j(waitForContinue2, "Wait for continue time");
    }

    public j() {
        this(3000);
    }

    /* access modifiers changed from: protected */
    public boolean canResponseHaveBody(o request, q response) {
        int status;
        if (Constants.HEAD.equalsIgnoreCase(request.r().getMethod()) || (status = response.j().getStatusCode()) < 200 || status == 204 || status == 304 || status == 205) {
            return false;
        }
        return true;
    }

    public q execute(o request, h conn, f context) {
        a.i(request, "HTTP request");
        a.i(conn, "Client connection");
        a.i(context, "HTTP context");
        try {
            q response = doSendRequest(request, conn, context);
            if (response == null) {
                return doReceiveResponse(request, conn, context);
            }
            return response;
        } catch (IOException ex) {
            closeConnection(conn);
            throw ex;
        } catch (HttpException ex2) {
            closeConnection(conn);
            throw ex2;
        } catch (RuntimeException ex3) {
            closeConnection(conn);
            throw ex3;
        }
    }

    private static void closeConnection(h conn) {
        try {
            conn.close();
        } catch (IOException e) {
        }
    }

    public void preProcess(o request, h processor, f context) {
        a.i(request, "HTTP request");
        a.i(processor, "HTTP processor");
        a.i(context, "HTTP context");
        context.setAttribute("http.request", request);
        processor.b(request, context);
    }

    /* access modifiers changed from: protected */
    public q doSendRequest(o request, h conn, f context) {
        a.i(request, "HTTP request");
        a.i(conn, "Client connection");
        a.i(context, "HTTP context");
        q response = null;
        context.setAttribute("http.connection", conn);
        context.setAttribute("http.request_sent", Boolean.FALSE);
        conn.E0(request);
        if (request instanceof k) {
            boolean sendentity = true;
            v ver = request.r().getProtocolVersion();
            if (((k) request).m() && !ver.lessEquals(t.HTTP_1_0)) {
                conn.flush();
                if (conn.a0(this.waitForContinue)) {
                    response = conn.K0();
                    if (canResponseHaveBody(request, response)) {
                        conn.G0(response);
                    }
                    int status = response.j().getStatusCode();
                    if (status >= 200) {
                        sendentity = false;
                    } else if (status == 100) {
                        response = null;
                    } else {
                        throw new ProtocolException("Unexpected response: " + response.j());
                    }
                }
            }
            if (sendentity) {
                conn.H((k) request);
            }
        }
        conn.flush();
        context.setAttribute("http.request_sent", Boolean.TRUE);
        return response;
    }

    /* access modifiers changed from: protected */
    public q doReceiveResponse(o request, h conn, f context) {
        a.i(request, "HTTP request");
        a.i(conn, "Client connection");
        a.i(context, "HTTP context");
        q response = null;
        int statusCode = 0;
        while (true) {
            if (response != null && statusCode >= 200) {
                return response;
            }
            response = conn.K0();
            if (canResponseHaveBody(request, response)) {
                conn.G0(response);
            }
            statusCode = response.j().getStatusCode();
        }
    }

    public void postProcess(q response, h processor, f context) {
        a.i(response, "HTTP response");
        a.i(processor, "HTTP processor");
        a.i(context, "HTTP context");
        context.setAttribute("http.response", response);
        processor.a(response, context);
    }
}
