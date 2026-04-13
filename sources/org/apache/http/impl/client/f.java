package org.apache.http.impl.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import org.apache.http.cookie.c;
import org.apache.http.cookie.e;

/* compiled from: BasicCookieStore */
public class f implements org.apache.http.client.f, Serializable {
    private static final long serialVersionUID = -7581093305228232025L;
    private final TreeSet<c> cookies = new TreeSet<>(new e());

    public synchronized void addCookie(c cookie) {
        if (cookie != null) {
            this.cookies.remove(cookie);
            if (!cookie.isExpired(new Date())) {
                this.cookies.add(cookie);
            }
        }
    }

    public synchronized void addCookies(c[] cookies2) {
        if (cookies2 != null) {
            for (c cooky : cookies2) {
                addCookie(cooky);
            }
        }
    }

    public synchronized List<c> getCookies() {
        return new ArrayList(this.cookies);
    }

    public synchronized boolean clearExpired(Date date) {
        if (date == null) {
            return false;
        }
        boolean removed = false;
        Iterator<c> it = this.cookies.iterator();
        while (it.hasNext()) {
            if (it.next().isExpired(date)) {
                it.remove();
                removed = true;
            }
        }
        return removed;
    }

    public synchronized void clear() {
        this.cookies.clear();
    }

    public synchronized String toString() {
        return this.cookies.toString();
    }
}
