package org.eclipse.paho.client.mqttv3.logging;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;

public class JSR47Logger implements a {
    private String catalogID = null;
    private Logger julLogger = null;
    private ResourceBundle logMessageCatalog = null;
    private String loggerName = null;
    private String resourceName = null;
    private ResourceBundle traceMessageCatalog = null;

    public void initialise(ResourceBundle logMsgCatalog, String loggerID, String resourceContext) {
        this.traceMessageCatalog = this.logMessageCatalog;
        this.resourceName = resourceContext;
        this.loggerName = loggerID;
        this.julLogger = Logger.getLogger(loggerID);
        this.logMessageCatalog = logMsgCatalog;
        this.traceMessageCatalog = logMsgCatalog;
        this.catalogID = logMsgCatalog.getString("0");
    }

    public void setResourceName(String logContext) {
        this.resourceName = logContext;
    }

    public boolean isLoggable(int level) {
        return this.julLogger.isLoggable(mapJULLevel(level));
    }

    public void severe(String sourceClass, String sourceMethod, String msg) {
        log(1, sourceClass, sourceMethod, msg, (Object[]) null, (Throwable) null);
    }

    public void severe(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
        log(1, sourceClass, sourceMethod, msg, inserts, (Throwable) null);
    }

    public void severe(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable thrown) {
        log(1, sourceClass, sourceMethod, msg, inserts, thrown);
    }

    public void warning(String sourceClass, String sourceMethod, String msg) {
        log(2, sourceClass, sourceMethod, msg, (Object[]) null, (Throwable) null);
    }

    public void warning(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
        log(2, sourceClass, sourceMethod, msg, inserts, (Throwable) null);
    }

    public void warning(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable thrown) {
        log(2, sourceClass, sourceMethod, msg, inserts, thrown);
    }

    public void info(String sourceClass, String sourceMethod, String msg) {
        log(3, sourceClass, sourceMethod, msg, (Object[]) null, (Throwable) null);
    }

    public void info(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
        log(3, sourceClass, sourceMethod, msg, inserts, (Throwable) null);
    }

    public void info(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable thrown) {
        log(3, sourceClass, sourceMethod, msg, inserts, thrown);
    }

    public void config(String sourceClass, String sourceMethod, String msg) {
        log(4, sourceClass, sourceMethod, msg, (Object[]) null, (Throwable) null);
    }

    public void config(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
        log(4, sourceClass, sourceMethod, msg, inserts, (Throwable) null);
    }

    public void config(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable thrown) {
        log(4, sourceClass, sourceMethod, msg, inserts, thrown);
    }

    public void log(int level, String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable thrown) {
        Level julLevel = mapJULLevel(level);
        if (this.julLogger.isLoggable(julLevel)) {
            logToJsr47(julLevel, sourceClass, sourceMethod, this.catalogID, this.logMessageCatalog, msg, inserts, thrown);
        }
    }

    public void fine(String sourceClass, String sourceMethod, String msg) {
        trace(5, sourceClass, sourceMethod, msg, (Object[]) null, (Throwable) null);
    }

    public void fine(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
        trace(5, sourceClass, sourceMethod, msg, inserts, (Throwable) null);
    }

    public void fine(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable ex) {
        trace(5, sourceClass, sourceMethod, msg, inserts, ex);
    }

    public void finer(String sourceClass, String sourceMethod, String msg) {
        trace(6, sourceClass, sourceMethod, msg, (Object[]) null, (Throwable) null);
    }

    public void finer(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
        trace(6, sourceClass, sourceMethod, msg, inserts, (Throwable) null);
    }

    public void finer(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable ex) {
        trace(6, sourceClass, sourceMethod, msg, inserts, ex);
    }

    public void finest(String sourceClass, String sourceMethod, String msg) {
        trace(7, sourceClass, sourceMethod, msg, (Object[]) null, (Throwable) null);
    }

    public void finest(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
        trace(7, sourceClass, sourceMethod, msg, inserts, (Throwable) null);
    }

    public void finest(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable ex) {
        trace(7, sourceClass, sourceMethod, msg, inserts, ex);
    }

    public void trace(int level, String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable ex) {
        Level julLevel = mapJULLevel(level);
        if (this.julLogger.isLoggable(julLevel)) {
            logToJsr47(julLevel, sourceClass, sourceMethod, this.catalogID, this.traceMessageCatalog, msg, inserts, ex);
        }
    }

    private String getResourceMessage(ResourceBundle messageCatalog, String msg) {
        try {
            return messageCatalog.getString(msg);
        } catch (MissingResourceException e) {
            return msg;
        }
    }

    private void logToJsr47(Level julLevel, String sourceClass, String sourceMethod, String catalogName, ResourceBundle messageCatalog, String msg, Object[] inserts, Throwable thrown) {
        String formattedWithArgs = msg;
        if (!msg.contains("=====")) {
            formattedWithArgs = MessageFormat.format(getResourceMessage(messageCatalog, msg), inserts);
        }
        LogRecord logRecord = new LogRecord(julLevel, String.valueOf(this.resourceName) + ": " + formattedWithArgs);
        logRecord.setSourceClassName(sourceClass);
        logRecord.setSourceMethodName(sourceMethod);
        logRecord.setLoggerName(this.loggerName);
        if (thrown != null) {
            logRecord.setThrown(thrown);
        }
        this.julLogger.log(logRecord);
    }

    private Level mapJULLevel(int level) {
        switch (level) {
            case 1:
                return Level.SEVERE;
            case 2:
                return Level.WARNING;
            case 3:
                return Level.INFO;
            case 4:
                return Level.CONFIG;
            case 5:
                return Level.FINE;
            case 6:
                return Level.FINER;
            case 7:
                return Level.FINEST;
            default:
                return null;
        }
    }

    public String formatMessage(String msg, Object[] inserts) {
        try {
            return this.logMessageCatalog.getString(msg);
        } catch (MissingResourceException e) {
            return msg;
        }
    }

    public void dumpTrace() {
        dumpMemoryTrace47(this.julLogger);
    }

    protected static void dumpMemoryTrace47(Logger logger) {
        if (logger != null) {
            for (Handler handler : logger.getHandlers()) {
                if (handler instanceof MemoryHandler) {
                    synchronized (handler) {
                        ((MemoryHandler) handler).push();
                    }
                    return;
                }
            }
            dumpMemoryTrace47(logger.getParent());
        }
    }
}
