package org.glassfish.grizzly;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glassfish.grizzly.attributes.AttributeBuilder;

public class Grizzly {
    public static final AttributeBuilder DEFAULT_ATTRIBUTE_BUILDER = AttributeBuilder.DEFAULT_ATTRIBUTE_BUILDER;
    private static final String dotedVersion;
    private static boolean isTrackingThreadCache;
    private static final int major;
    private static final int minor;
    private static final Pattern versionPattern = Pattern.compile("((\\d+)\\.(\\d+)(\\.\\d+)*){1}(?:-(.+))?");

    static {
        InputStream is = null;
        Properties prop = new Properties();
        try {
            is = Grizzly.class.getResourceAsStream("version.properties");
            prop.load(is);
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (is != null) {
                is.close();
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e3) {
                }
            }
            throw th;
        }
        Matcher matcher = versionPattern.matcher(prop.getProperty("grizzly.version"));
        if (matcher.matches()) {
            dotedVersion = matcher.group(1);
            major = Integer.parseInt(matcher.group(2));
            minor = Integer.parseInt(matcher.group(3));
            return;
        }
        dotedVersion = "no.version";
        major = -1;
        minor = -1;
    }

    public static Logger logger(Class clazz) {
        return Logger.getLogger(clazz.getName());
    }

    public static void main(String[] args) {
        System.out.println(getDotedVersion());
    }

    public static String getDotedVersion() {
        return dotedVersion;
    }

    public static int getMajorVersion() {
        return major;
    }

    public static int getMinorVersion() {
        return minor;
    }

    public static boolean equalVersion(int major2, int minor2) {
        return minor2 == minor && major2 == major;
    }

    public static boolean isTrackingThreadCache() {
        return isTrackingThreadCache;
    }

    public static void setTrackingThreadCache(boolean isTrackingThreadCache2) {
        isTrackingThreadCache = isTrackingThreadCache2;
    }
}
