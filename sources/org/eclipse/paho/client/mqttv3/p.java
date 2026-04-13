package org.eclipse.paho.client.mqttv3;

import java.io.UnsupportedEncodingException;
import org.eclipse.paho.client.mqttv3.util.b;
import org.slf4j.e;

/* compiled from: MqttTopic */
public class p {
    public static void b(String topicString, boolean wildcardAllowed) {
        try {
            int topicLen = topicString.getBytes("UTF-8").length;
            if (topicLen < 1 || topicLen > 65535) {
                throw new IllegalArgumentException(String.format("Invalid topic length, should be in range[%d, %d]!", new Object[]{1, 65535}));
            } else if (wildcardAllowed) {
                if (!b.d(topicString, new String[]{"#", e.ANY_NON_NULL_MARKER})) {
                    if (b.c(topicString, "#") > 1 || (topicString.contains("#") && !topicString.endsWith("/#"))) {
                        throw new IllegalArgumentException("Invalid usage of multi-level wildcard in topic string: " + topicString);
                    }
                    c(topicString);
                }
            } else if (b.a(topicString, "#+")) {
                throw new IllegalArgumentException("The topic name MUST NOT contain any wildcard characters (#+)");
            }
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private static void c(String topicString) {
        char singleLevelWildcardChar = e.ANY_NON_NULL_MARKER.charAt(0);
        char topicLevelSeparatorChar = "/".charAt(0);
        char[] chars = topicString.toCharArray();
        int length = chars.length;
        int i = 0;
        while (i < length) {
            char prev = i + -1 >= 0 ? chars[i - 1] : 0;
            char next = i + 1 < length ? chars[i + 1] : 0;
            if (chars[i] != singleLevelWildcardChar || ((prev == topicLevelSeparatorChar || prev == 0) && (next == topicLevelSeparatorChar || next == 0))) {
                i++;
            } else {
                throw new IllegalArgumentException(String.format("Invalid usage of single-level wildcard in topic string '%s'!", new Object[]{topicString}));
            }
        }
    }

    public static boolean a(String topicFilter, String topicName) {
        int topicPos = 0;
        int filterPos = 0;
        int topicLen = topicName.length();
        int filterLen = topicFilter.length();
        b(topicFilter, true);
        b(topicName, false);
        if (topicFilter.equals(topicName)) {
            return true;
        }
        while (true) {
            if (filterPos < filterLen && topicPos < topicLen) {
                if (topicFilter.charAt(filterPos) != '#') {
                    if ((topicName.charAt(topicPos) == '/' && topicFilter.charAt(filterPos) != '/') || (topicFilter.charAt(filterPos) != '+' && topicFilter.charAt(filterPos) != '#' && topicFilter.charAt(filterPos) != topicName.charAt(topicPos))) {
                        break;
                    }
                    if (topicFilter.charAt(filterPos) == '+') {
                        int nextpos = topicPos + 1;
                        while (nextpos < topicLen && topicName.charAt(nextpos) != '/') {
                            topicPos++;
                            nextpos = topicPos + 1;
                        }
                    }
                    filterPos++;
                    topicPos++;
                } else {
                    topicPos = topicLen;
                    filterPos = filterLen;
                    break;
                }
            } else {
                break;
            }
        }
        if (topicPos == topicLen && filterPos == filterLen) {
            return true;
        }
        if (topicFilter.length() - filterPos > 0 && topicPos == topicLen) {
            if (topicName.charAt(topicPos - 1) == '/' && topicFilter.charAt(filterPos) == '#') {
                return true;
            }
            if (topicFilter.length() - filterPos <= 1 || !topicFilter.substring(filterPos, filterPos + 2).equals("/#")) {
                return false;
            }
            return true;
        }
        return false;
    }
}
