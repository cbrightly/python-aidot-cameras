package org.glassfish.grizzly.http;

public final class ProcessingState {
    boolean error;
    HttpContext httpContext;
    boolean keepAlive = false;

    public boolean isError() {
        return this.error;
    }

    public void setError(boolean error2) {
        this.error = error2;
    }

    public boolean isStayAlive() {
        return this.keepAlive && !this.error;
    }

    public boolean isKeepAlive() {
        return this.keepAlive;
    }

    public void setKeepAlive(boolean keepAlive2) {
        this.keepAlive = keepAlive2;
    }

    public HttpContext getHttpContext() {
        return this.httpContext;
    }

    public void setHttpContext(HttpContext httpContext2) {
        this.httpContext = httpContext2;
    }

    public void recycle() {
        this.keepAlive = false;
        this.error = false;
        this.httpContext = null;
    }
}
