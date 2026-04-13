package org.glassfish.grizzly.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glassfish.grizzly.Grizzly;

public class JdkVersion implements Comparable<JdkVersion> {
    private static final JdkVersion JDK_VERSION = parseVersion(System.getProperty("java.version"));
    private static final Logger LOGGER = Grizzly.logger(JdkVersion.class);
    private static final JdkVersion UNKNOWN_VERSION = new JdkVersion(-1, -1, -1, -1);
    private static final Pattern VERSION_PATTERN = Pattern.compile("([0-9]+)(\\.([0-9]+))?(\\.([0-9]+))?([_\\.]([0-9]+))?.*");
    private final int maintenance;
    private final int major;
    private final int minor;
    private final int update;

    private JdkVersion(int major2, int minor2, int maintenance2, int update2) {
        this.major = major2;
        this.minor = minor2;
        this.maintenance = maintenance2;
        this.update = update2;
    }

    public static JdkVersion parseVersion(String versionString) {
        try {
            Matcher matcher = VERSION_PATTERN.matcher(versionString);
            if (matcher.matches()) {
                return new JdkVersion(parseInt(matcher.group(1)), parseInt(matcher.group(3)), parseInt(matcher.group(5)), parseInt(matcher.group(7)));
            }
            LOGGER.log(Level.FINE, "Can't parse the JDK version {0}", versionString);
            return UNKNOWN_VERSION;
        } catch (Exception e) {
            Logger logger = LOGGER;
            Level level = Level.FINE;
            logger.log(level, "Error parsing the JDK version " + versionString, e);
        }
    }

    public static JdkVersion getJdkVersion() {
        return JDK_VERSION;
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getMaintenance() {
        return this.maintenance;
    }

    public int getUpdate() {
        return this.update;
    }

    public String toString() {
        return "JdkVersion" + "{major=" + this.major + ", minor=" + this.minor + ", maintenance=" + this.maintenance + ", update=" + this.update + '}';
    }

    public int compareTo(String versionString) {
        return compareTo(parseVersion(versionString));
    }

    public int compareTo(JdkVersion otherVersion) {
        int i = this.major;
        int i2 = otherVersion.major;
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        int i3 = this.minor;
        int i4 = otherVersion.minor;
        if (i3 < i4) {
            return -1;
        }
        if (i3 > i4) {
            return 1;
        }
        int i5 = this.maintenance;
        int i6 = otherVersion.maintenance;
        if (i5 < i6) {
            return -1;
        }
        if (i5 > i6) {
            return 1;
        }
        int i7 = this.update;
        int i8 = otherVersion.update;
        if (i7 < i8) {
            return -1;
        }
        if (i7 > i8) {
            return 1;
        }
        return 0;
    }

    private static int parseInt(String s) {
        if (s != null) {
            return Integer.parseInt(s);
        }
        return 0;
    }
}
