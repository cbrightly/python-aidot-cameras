package com.amazonaws.http;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.b;

public final class IdleConnectionReaper extends Thread {
    private static final ArrayList<b> CONNECTION_MANAGERS = new ArrayList<>();
    private static final int MINUTE_IN_SECONDS = 60;
    private static final int PERIOD_MILLISECONDS = 60000;
    private static IdleConnectionReaper instance;
    static final Log log = LogFactory.getLog(IdleConnectionReaper.class);
    private volatile boolean shuttingDown;

    private IdleConnectionReaper() {
        super("java-sdk-http-connection-reaper");
        setDaemon(true);
    }

    public static synchronized boolean registerConnectionManager(b connectionManager) {
        boolean add;
        synchronized (IdleConnectionReaper.class) {
            if (instance == null) {
                IdleConnectionReaper idleConnectionReaper = new IdleConnectionReaper();
                instance = idleConnectionReaper;
                idleConnectionReaper.start();
            }
            add = CONNECTION_MANAGERS.add(connectionManager);
        }
        return add;
    }

    public static synchronized boolean removeConnectionManager(b connectionManager) {
        boolean b;
        synchronized (IdleConnectionReaper.class) {
            ArrayList<b> arrayList = CONNECTION_MANAGERS;
            b = arrayList.remove(connectionManager);
            if (arrayList.isEmpty()) {
                shutdown();
            }
        }
        return b;
    }

    private void markShuttingDown() {
        this.shuttingDown = true;
    }

    public void run() {
        List<b> list;
        while (!this.shuttingDown) {
            try {
                Thread.sleep(60000);
                synchronized (IdleConnectionReaper.class) {
                    list = (List) CONNECTION_MANAGERS.clone();
                }
                for (b connectionManager : list) {
                    connectionManager.a(60, TimeUnit.SECONDS);
                }
            } catch (Exception t) {
                log.warn("Unable to close idle connections", t);
            } catch (Throwable t2) {
                log.debug("Reaper thread: ", t2);
            }
        }
        log.debug("Shutting down reaper thread.");
    }

    public static synchronized boolean shutdown() {
        synchronized (IdleConnectionReaper.class) {
            IdleConnectionReaper idleConnectionReaper = instance;
            if (idleConnectionReaper == null) {
                return false;
            }
            idleConnectionReaper.markShuttingDown();
            instance.interrupt();
            CONNECTION_MANAGERS.clear();
            instance = null;
            return true;
        }
    }

    static synchronized int size() {
        int size;
        synchronized (IdleConnectionReaper.class) {
            size = CONNECTION_MANAGERS.size();
        }
        return size;
    }
}
