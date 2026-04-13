package org.apache.http.impl.conn.tsccm;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import org.apache.http.util.a;

@Deprecated
/* compiled from: WaitingThread */
public class h {
    private final Condition a;
    private final f b;
    private Thread c;
    private boolean d;

    public h(Condition cond, f pool) {
        a.i(cond, JsonDocumentFields.CONDITION);
        this.a = cond;
        this.b = pool;
    }

    public boolean a(Date deadline) {
        boolean success;
        if (this.c != null) {
            throw new IllegalStateException("A thread is already waiting on this object.\ncaller: " + Thread.currentThread() + "\nwaiter: " + this.c);
        } else if (!this.d) {
            this.c = Thread.currentThread();
            if (deadline != null) {
                try {
                    success = this.a.awaitUntil(deadline);
                } catch (Throwable th) {
                    this.c = null;
                    throw th;
                }
            } else {
                this.a.await();
                success = true;
            }
            if (!this.d) {
                this.c = null;
                return success;
            }
            throw new InterruptedException("Operation interrupted");
        } else {
            throw new InterruptedException("Operation interrupted");
        }
    }

    public void c() {
        if (this.c != null) {
            this.a.signalAll();
            return;
        }
        throw new IllegalStateException("Nobody waiting on this object.");
    }

    public void b() {
        this.d = true;
        this.a.signalAll();
    }
}
