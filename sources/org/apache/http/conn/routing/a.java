package org.apache.http.conn.routing;

/* compiled from: BasicRouteDirector */
public class a implements c {
    public int a(e plan, e fact) {
        org.apache.http.util.a.i(plan, "Planned route");
        if (fact == null || fact.a() < 1) {
            return c(plan);
        }
        if (plan.a() > 1) {
            return d(plan, fact);
        }
        return b(plan, fact);
    }

    /* access modifiers changed from: protected */
    public int c(e plan) {
        return plan.a() > 1 ? 2 : 1;
    }

    /* access modifiers changed from: protected */
    public int b(e plan, e fact) {
        if (fact.a() > 1 || !plan.e().equals(fact.e()) || plan.isSecure() != fact.isSecure()) {
            return -1;
        }
        if (plan.getLocalAddress() == null || plan.getLocalAddress().equals(fact.getLocalAddress())) {
            return 0;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public int d(e plan, e fact) {
        int phc;
        int fhc;
        if (fact.a() <= 1 || !plan.e().equals(fact.e()) || (phc = plan.a()) < (fhc = fact.a())) {
            return -1;
        }
        for (int i = 0; i < fhc - 1; i++) {
            if (!plan.d(i).equals(fact.d(i))) {
                return -1;
            }
        }
        if (phc > fhc) {
            return 4;
        }
        if ((fact.b() && !plan.b()) || (fact.f() && !plan.f())) {
            return -1;
        }
        if (plan.b() && !fact.b()) {
            return 3;
        }
        if (plan.f() && !fact.f()) {
            return 5;
        }
        if (plan.isSecure() != fact.isSecure()) {
            return -1;
        }
        return 0;
    }
}
