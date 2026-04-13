package org.apache.httpcore;

/* compiled from: ExceptionLogger */
public interface c {
    public static final c a = new a();
    public static final c b = new b();

    void a(Exception exc);

    /* compiled from: ExceptionLogger */
    public static final class a implements c {
        a() {
        }

        public void a(Exception ex) {
        }
    }

    /* compiled from: ExceptionLogger */
    public static final class b implements c {
        b() {
        }

        public void a(Exception ex) {
            ex.printStackTrace();
        }
    }
}
