package net.sbbi.upnp.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: ServiceAction */
public class a {
    protected String a;
    protected e b;
    private List c;
    private List d;
    private List e;
    private List f;
    private List g;

    protected a() {
    }

    public b a(String argumentName) {
        List<b> list = this.c;
        if (list == null) {
            return null;
        }
        for (b arg : list) {
            if (arg.b().equals(argumentName)) {
                return arg;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void f(List orderedActionArguments) {
        this.c = orderedActionArguments;
        this.d = c(orderedActionArguments, "in");
        this.e = c(orderedActionArguments, "out");
        this.f = d(orderedActionArguments, "in");
        this.g = d(orderedActionArguments, "out");
    }

    public List b() {
        return this.d;
    }

    public String e() {
        return this.a;
    }

    private List c(List args, String direction) {
        if (args == null) {
            return null;
        }
        List rtrVal = new ArrayList();
        Iterator itr = args.iterator();
        while (itr.hasNext()) {
            b actArg = (b) itr.next();
            if (actArg.a() == direction) {
                rtrVal.add(actArg);
            }
        }
        if (rtrVal.size() == 0) {
            return null;
        }
        return rtrVal;
    }

    private List d(List args, String direction) {
        if (args == null) {
            return null;
        }
        List rtrVal = new ArrayList();
        Iterator itr = args.iterator();
        while (itr.hasNext()) {
            b actArg = (b) itr.next();
            if (actArg.a() == direction) {
                rtrVal.add(actArg.b());
            }
        }
        if (rtrVal.size() == 0) {
            return null;
        }
        return rtrVal;
    }
}
