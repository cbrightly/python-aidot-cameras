package org.glassfish.grizzly.http.server;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.server.util.Globals;

public class DefaultSessionManager implements SessionManager {
    private final Random rnd;
    private String sessionCookieName;
    private final ScheduledThreadPoolExecutor sessionExpirer;
    /* access modifiers changed from: private */
    public final ConcurrentMap<String, Session> sessions;

    public static SessionManager instance() {
        return LazyHolder.INSTANCE;
    }

    public static class LazyHolder {
        /* access modifiers changed from: private */
        public static final DefaultSessionManager INSTANCE = new DefaultSessionManager();

        private LazyHolder() {
        }
    }

    private DefaultSessionManager() {
        this.sessions = new ConcurrentHashMap();
        this.rnd = new Random();
        this.sessionCookieName = Globals.SESSION_COOKIE_NAME;
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "Grizzly-HttpSession-Expirer");
                t.setDaemon(true);
                return t;
            }
        });
        this.sessionExpirer = scheduledThreadPoolExecutor;
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                long currentTime = System.currentTimeMillis();
                Iterator<Map.Entry<String, Session>> iterator = DefaultSessionManager.this.sessions.entrySet().iterator();
                while (iterator.hasNext()) {
                    Session session = iterator.next().getValue();
                    if (!session.isValid() || (session.getSessionTimeout() > 0 && currentTime - session.getTimestamp() > session.getSessionTimeout())) {
                        session.setValid(false);
                        iterator.remove();
                    }
                }
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    public Session getSession(Request request, String requestedSessionId) {
        Session session;
        if (requestedSessionId == null || (session = (Session) this.sessions.get(requestedSessionId)) == null || !session.isValid()) {
            return null;
        }
        return session;
    }

    public Session createSession(Request request) {
        String requestedSessionId;
        Session session = new Session();
        do {
            requestedSessionId = String.valueOf(generateRandomLong());
            session.setIdInternal(requestedSessionId);
        } while (this.sessions.putIfAbsent(requestedSessionId, session) != null);
        return session;
    }

    public String changeSessionId(Request request, Session session) {
        String oldSessionId = session.getIdInternal();
        String newSessionId = String.valueOf(generateRandomLong());
        session.setIdInternal(newSessionId);
        this.sessions.remove(oldSessionId);
        this.sessions.put(newSessionId, session);
        return oldSessionId;
    }

    public void configureSessionCookie(Request request, Cookie cookie) {
    }

    public void setSessionCookieName(String name) {
        if (name != null && !name.isEmpty()) {
            this.sessionCookieName = name;
        }
    }

    public String getSessionCookieName() {
        return this.sessionCookieName;
    }

    private long generateRandomLong() {
        return this.rnd.nextLong() & DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
    }
}
