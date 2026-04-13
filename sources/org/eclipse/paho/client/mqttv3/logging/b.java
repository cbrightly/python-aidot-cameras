package org.eclipse.paho.client.mqttv3.logging;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/* compiled from: LoggerFactory */
public class b {
    private static final String a = b.class.getName();
    private static String b = null;
    private static String c = JSR47Logger.class.getName();

    public static a a(String messageCatalogName, String loggerID) {
        String loggerClassName = b;
        if (loggerClassName == null) {
            loggerClassName = c;
        }
        a logger = b(loggerClassName, ResourceBundle.getBundle(messageCatalogName), loggerID, (String) null);
        if (logger != null) {
            return logger;
        }
        throw new MissingResourceException("Error locating the logging class", a, loggerID);
    }

    private static a b(String loggerClassName, ResourceBundle messageCatalog, String loggerID, String resourceName) {
        try {
            try {
                a logger = (a) Class.forName(loggerClassName).newInstance();
                logger.initialise(messageCatalog, loggerID, resourceName);
                return logger;
            } catch (IllegalAccessException e) {
                return null;
            } catch (InstantiationException e2) {
                return null;
            } catch (ExceptionInInitializerError e3) {
                return null;
            } catch (SecurityException e4) {
                return null;
            }
        } catch (NoClassDefFoundError e5) {
            return null;
        } catch (ClassNotFoundException e6) {
            return null;
        }
    }
}
