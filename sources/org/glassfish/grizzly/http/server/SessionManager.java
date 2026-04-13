package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.http.Cookie;

public interface SessionManager {
    String changeSessionId(Request request, Session session);

    void configureSessionCookie(Request request, Cookie cookie);

    Session createSession(Request request);

    Session getSession(Request request, String str);

    String getSessionCookieName();

    void setSessionCookieName(String str);
}
