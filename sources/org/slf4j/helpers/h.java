package org.slf4j.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.a;
import org.slf4j.b;
import org.slf4j.event.d;

/* compiled from: SubstituteLoggerFactory */
public class h implements a {
    boolean a = false;
    final Map<String, g> b = new HashMap();
    final LinkedBlockingQueue<d> c = new LinkedBlockingQueue<>();

    public synchronized b a(String name) {
        g logger;
        logger = this.b.get(name);
        if (logger == null) {
            logger = new g(name, this.c, this.a);
            this.b.put(name, logger);
        }
        return logger;
    }

    public List<g> d() {
        return new ArrayList(this.b.values());
    }

    public LinkedBlockingQueue<d> c() {
        return this.c;
    }

    public void e() {
        this.a = true;
    }

    public void b() {
        this.b.clear();
        this.c.clear();
    }
}
