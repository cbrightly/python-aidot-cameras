package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.n;
import org.apache.http.util.a;

/* compiled from: LaxExpiresHandler */
public class v extends a implements b {
    static final TimeZone a = TimeZone.getTimeZone("UTC");
    private static final BitSet b;
    private static final Map<String, Integer> c;
    private static final Pattern d = Pattern.compile("^([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})([^0-9].*)?$");
    private static final Pattern e = Pattern.compile("^([0-9]{1,2})([^0-9].*)?$");
    private static final Pattern f = Pattern.compile("^(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)(.*)?$", 2);
    private static final Pattern g = Pattern.compile("^([0-9]{2,4})([^0-9].*)?$");

    static {
        BitSet bitSet = new BitSet();
        bitSet.set(9);
        for (int b2 = 32; b2 <= 47; b2++) {
            bitSet.set(b2);
        }
        for (int b3 = 59; b3 <= 64; b3++) {
            bitSet.set(b3);
        }
        for (int b4 = 91; b4 <= 96; b4++) {
            bitSet.set(b4);
        }
        for (int b5 = 123; b5 <= 126; b5++) {
            bitSet.set(b5);
        }
        b = bitSet;
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(12);
        map.put("jan", 0);
        map.put("feb", 1);
        map.put("mar", 2);
        map.put("apr", 3);
        map.put("may", 4);
        map.put("jun", 5);
        map.put("jul", 6);
        map.put("aug", 7);
        map.put("sep", 8);
        map.put("oct", 9);
        map.put("nov", 10);
        map.put("dec", 11);
        c = map;
    }

    public void d(n cookie, String value) {
        v vVar = this;
        n nVar = cookie;
        String str = value;
        a.i(nVar, HttpHeaders.HEAD_KEY_COOKIE);
        org.apache.http.message.v cursor = new org.apache.http.message.v(0, value.length());
        StringBuilder content = new StringBuilder();
        boolean foundYear = false;
        boolean foundMonth = false;
        boolean foundDayOfMonth = false;
        boolean foundTime = false;
        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        while (true) {
            try {
                if (cursor.a() != 0) {
                    break;
                }
                try {
                    vVar.f(str, cursor);
                    content.setLength(0);
                    vVar.e(str, cursor, content);
                    if (content.length() == 0) {
                        break;
                    }
                    if (!foundTime) {
                        Matcher matcher = d.matcher(content);
                        if (matcher.matches()) {
                            foundTime = true;
                            hour = Integer.parseInt(matcher.group(1));
                            minute = Integer.parseInt(matcher.group(2));
                            second = Integer.parseInt(matcher.group(3));
                        }
                    }
                    if (!foundDayOfMonth) {
                        Matcher matcher2 = e.matcher(content);
                        if (matcher2.matches()) {
                            foundDayOfMonth = true;
                            day = Integer.parseInt(matcher2.group(1));
                        }
                    }
                    if (!foundMonth) {
                        Matcher matcher3 = f.matcher(content);
                        if (matcher3.matches()) {
                            foundMonth = true;
                            Matcher matcher4 = matcher3;
                            month = c.get(matcher3.group(1).toLowerCase(Locale.ROOT)).intValue();
                            vVar = this;
                        }
                    }
                    if (!foundYear) {
                        Matcher matcher5 = g.matcher(content);
                        if (matcher5.matches()) {
                            foundYear = true;
                            year = Integer.parseInt(matcher5.group(1));
                            vVar = this;
                        }
                    }
                    vVar = this;
                } catch (NumberFormatException e2) {
                    org.apache.http.message.v vVar2 = cursor;
                    throw new MalformedCookieException("Invalid 'expires' attribute: " + str);
                }
            } catch (NumberFormatException e3) {
                org.apache.http.message.v vVar3 = cursor;
                throw new MalformedCookieException("Invalid 'expires' attribute: " + str);
            }
        }
        if (!foundTime || !foundDayOfMonth || !foundMonth || !foundYear) {
            throw new MalformedCookieException("Invalid 'expires' attribute: " + str);
        }
        if (year >= 70 && year <= 99) {
            year += 1900;
        }
        if (year >= 0 && year <= 69) {
            year += WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS;
        }
        if (day < 1 || day > 31 || year < 1601 || hour > 23 || minute > 59 || second > 59) {
            throw new MalformedCookieException("Invalid 'expires' attribute: " + str);
        }
        Calendar c2 = Calendar.getInstance();
        c2.setTimeZone(a);
        org.apache.http.message.v vVar4 = cursor;
        c2.setTimeInMillis(0);
        c2.set(13, second);
        c2.set(12, minute);
        c2.set(11, hour);
        c2.set(5, day);
        c2.set(2, month);
        c2.set(1, year);
        nVar.setExpiryDate(c2.getTime());
    }

    private void f(CharSequence buf, org.apache.http.message.v cursor) {
        int pos = cursor.b();
        int indexFrom = cursor.b();
        int indexTo = cursor.c();
        for (int i = indexFrom; i < indexTo; i++) {
            if (!b.get(buf.charAt(i))) {
                break;
            }
            pos++;
        }
        cursor.d(pos);
    }

    private void e(CharSequence buf, org.apache.http.message.v cursor, StringBuilder dst) {
        int pos = cursor.b();
        int indexFrom = cursor.b();
        int indexTo = cursor.c();
        for (int i = indexFrom; i < indexTo; i++) {
            char current = buf.charAt(i);
            if (b.get(current)) {
                break;
            }
            pos++;
            dst.append(current);
        }
        cursor.d(pos);
    }

    public String c() {
        return "expires";
    }
}
