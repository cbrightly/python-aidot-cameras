package org.apache.http.impl.conn.tsccm;

@Deprecated
/* compiled from: WaitingThreadAborter */
public class i {
    private h a;
    private boolean b;

    public void a() {
        this.b = true;
        h hVar = this.a;
        if (hVar != null) {
            hVar.b();
        }
    }

    public void b(h waitingThread) {
        this.a = waitingThread;
        if (this.b) {
            waitingThread.b();
        }
    }
}
