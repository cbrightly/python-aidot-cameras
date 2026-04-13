package org.apache.httpcore;

/* compiled from: HttpVersion */
public final class t extends v {
    public static final String HTTP = "HTTP";
    public static final t HTTP_0_9 = new t(0, 9);
    public static final t HTTP_1_0 = new t(1, 0);
    public static final t HTTP_1_1 = new t(1, 1);
    private static final long serialVersionUID = -5856653513894415344L;

    public t(int major, int minor) {
        super("HTTP", major, minor);
    }

    public v forVersion(int major, int minor) {
        if (major == this.major && minor == this.minor) {
            return this;
        }
        if (major == 1) {
            if (minor == 0) {
                return HTTP_1_0;
            }
            if (minor == 1) {
                return HTTP_1_1;
            }
        }
        if (major == 0 && minor == 9) {
            return HTTP_0_9;
        }
        return new t(major, minor);
    }
}
