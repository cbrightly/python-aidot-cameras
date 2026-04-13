package org.eclipse.paho.client.mqttv3.util;

import java.util.Enumeration;
import java.util.Properties;

/* compiled from: Debug */
public class a {
    private static final String a = org.eclipse.paho.client.mqttv3.internal.a.class.getName();
    private static final String b = System.getProperty("line.separator", "\n");

    public static String a(Properties props, String name) {
        StringBuffer propStr = new StringBuffer();
        Enumeration propsE = props.propertyNames();
        String str = b;
        propStr.append(String.valueOf(str) + "==============" + " " + name + " " + "==============" + str);
        while (propsE.hasMoreElements()) {
            String key = (String) propsE.nextElement();
            propStr.append(String.valueOf(b(key, 28, ' ')) + ":  " + props.get(key) + b);
        }
        propStr.append("==========================================" + b);
        return propStr.toString();
    }

    public static String b(String s, int width, char fillChar) {
        if (s.length() >= width) {
            return s;
        }
        StringBuffer sb = new StringBuffer(width);
        sb.append(s);
        int i = width - s.length();
        while (true) {
            i--;
            if (i < 0) {
                return sb.toString();
            }
            sb.append(fillChar);
        }
    }
}
