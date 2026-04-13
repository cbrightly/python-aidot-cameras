package org.glassfish.grizzly;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortRange {
    private static final Pattern FORMAT = Pattern.compile("(\\d+)(?:(?:,|:)(\\d+))?");
    private final int lower;
    private final int upper;

    public PortRange(int low, int high) {
        if (low < 1 || high < low || 65535 < high) {
            throw new IllegalArgumentException("Invalid range");
        }
        this.lower = low;
        this.upper = high;
    }

    public PortRange(int port) {
        this(port, port);
    }

    public static PortRange valueOf(String s) {
        Matcher m = FORMAT.matcher(s);
        if (m.matches()) {
            try {
                int low = Integer.parseInt(m.group(1));
                return new PortRange(low, m.groupCount() == 1 ? low : Integer.parseInt(m.group(2)));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid string format: " + s);
            }
        } else {
            throw new IllegalArgumentException("Invalid string format: " + s);
        }
    }

    public int getLower() {
        return this.lower;
    }

    public int getUpper() {
        return this.upper;
    }

    public String toString() {
        return String.format("%d:%d", new Object[]{Integer.valueOf(this.lower), Integer.valueOf(this.upper)});
    }
}
