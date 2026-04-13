package org.glassfish.grizzly.http.server;

public interface ErrorPageGenerator {
    String generate(Request request, int i, String str, String str2, Throwable th);
}
