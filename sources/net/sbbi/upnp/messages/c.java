package net.sbbi.upnp.messages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sbbi.upnp.services.b;

/* compiled from: ActionResponse */
public class c {
    private Map a = new HashMap();
    private Map b = new HashMap();

    protected c() {
    }

    public String b(String actionArgumentName) {
        return (String) this.b.get(actionArgumentName);
    }

    /* access modifiers changed from: protected */
    public void a(b arg, String value) {
        this.a.put(arg.b(), arg);
        this.b.put(arg.b(), value);
    }

    public String toString() {
        StringBuffer rtrVal = new StringBuffer();
        Iterator i = this.a.keySet().iterator();
        while (i.hasNext()) {
            String name = (String) i.next();
            rtrVal.append(name);
            rtrVal.append("=");
            rtrVal.append((String) this.b.get(name));
            if (i.hasNext()) {
                rtrVal.append("\n");
            }
        }
        return rtrVal.toString();
    }
}
