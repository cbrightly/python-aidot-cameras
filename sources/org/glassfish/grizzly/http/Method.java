package org.glassfish.grizzly.http;

import java.io.UnsupportedEncodingException;
import org.glassfish.grizzly.http.server.Constants;
import org.glassfish.grizzly.http.util.DataChunk;

public final class Method {
    public static final Method CONNECT;
    public static final Method DELETE;
    public static final Method GET;
    public static final Method HEAD;
    public static final Method OPTIONS;
    public static final Method PATCH;
    public static final Method POST;
    public static final Method PRI;
    public static final Method PUT;
    public static final Method TRACE;
    private final byte[] methodBytes;
    private final String methodString;
    private final PayloadExpectation payloadExpectation;

    public enum PayloadExpectation {
        ALLOWED,
        NOT_ALLOWED,
        UNDEFINED
    }

    static {
        PayloadExpectation payloadExpectation2 = PayloadExpectation.ALLOWED;
        OPTIONS = new Method("OPTIONS", payloadExpectation2);
        PayloadExpectation payloadExpectation3 = PayloadExpectation.UNDEFINED;
        GET = new Method(Constants.GET, payloadExpectation3);
        HEAD = new Method(Constants.HEAD, payloadExpectation3);
        POST = new Method(Constants.POST, payloadExpectation2);
        PUT = new Method("PUT", payloadExpectation2);
        DELETE = new Method("DELETE", payloadExpectation3);
        PayloadExpectation payloadExpectation4 = PayloadExpectation.NOT_ALLOWED;
        TRACE = new Method("TRACE", payloadExpectation4);
        CONNECT = new Method("CONNECT", payloadExpectation4);
        PATCH = new Method("PATCH", payloadExpectation2);
        PRI = new Method("PRI", payloadExpectation4);
    }

    public static Method CUSTOM(String methodName) {
        return CUSTOM(methodName, PayloadExpectation.ALLOWED);
    }

    public static Method CUSTOM(String methodName, PayloadExpectation payloadExpectation2) {
        return new Method(methodName, payloadExpectation2);
    }

    public static Method valueOf(DataChunk methodC) {
        Method method = GET;
        if (methodC.equals(method.getMethodString())) {
            return method;
        }
        Method method2 = POST;
        if (methodC.equals(method2.getMethodBytes())) {
            return method2;
        }
        Method method3 = HEAD;
        if (methodC.equals(method3.getMethodBytes())) {
            return method3;
        }
        Method method4 = PUT;
        if (methodC.equals(method4.getMethodBytes())) {
            return method4;
        }
        Method method5 = DELETE;
        if (methodC.equals(method5.getMethodBytes())) {
            return method5;
        }
        Method method6 = TRACE;
        if (methodC.equals(method6.getMethodBytes())) {
            return method6;
        }
        Method method7 = CONNECT;
        if (methodC.equals(method7.getMethodBytes())) {
            return method7;
        }
        Method method8 = OPTIONS;
        if (methodC.equals(method8.getMethodBytes())) {
            return method8;
        }
        Method method9 = PATCH;
        if (methodC.equals(method9.getMethodBytes())) {
            return method9;
        }
        Method method10 = PRI;
        if (methodC.equals(method10.getMethodBytes())) {
            return method10;
        }
        return CUSTOM(methodC.toString());
    }

    public static Method valueOf(String method) {
        Method method2 = GET;
        if (method.equals(method2.getMethodString())) {
            return method2;
        }
        Method method3 = POST;
        if (method.equals(method3.getMethodString())) {
            return method3;
        }
        Method method4 = HEAD;
        if (method.equals(method4.getMethodString())) {
            return method4;
        }
        Method method5 = PUT;
        if (method.equals(method5.getMethodString())) {
            return method5;
        }
        Method method6 = DELETE;
        if (method.equals(method6.getMethodString())) {
            return method6;
        }
        Method method7 = TRACE;
        if (method.equals(method7.getMethodString())) {
            return method7;
        }
        Method method8 = CONNECT;
        if (method.equals(method8.getMethodString())) {
            return method8;
        }
        Method method9 = OPTIONS;
        if (method.equals(method9.getMethodString())) {
            return method9;
        }
        Method method10 = PATCH;
        if (method.equals(method10.getMethodString())) {
            return method10;
        }
        Method method11 = PRI;
        if (method.equals(method11.getMethodString())) {
            return method11;
        }
        return CUSTOM(method);
    }

    private Method(String methodString2, PayloadExpectation payloadExpectation2) {
        this.methodString = methodString2;
        try {
            this.methodBytes = methodString2.getBytes("US-ASCII");
            this.payloadExpectation = payloadExpectation2;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getMethodString() {
        return this.methodString;
    }

    public byte[] getMethodBytes() {
        return this.methodBytes;
    }

    public PayloadExpectation getPayloadExpectation() {
        return this.payloadExpectation;
    }

    public String toString() {
        return this.methodString;
    }

    public boolean matchesMethod(String method) {
        return this.methodString.equals(method);
    }
}
