package zendesk.messaging.android.internal;

/* compiled from: UnreadMessageCounter.kt */
public final class l {
    private int a;

    public final int c() {
        this.a = 0;
        return 0;
    }

    public final int a() {
        return this.a;
    }

    public final int d(int unreadCount) {
        this.a = unreadCount;
        return unreadCount;
    }

    public final int b() {
        int i = this.a + 1;
        this.a = i;
        return i;
    }
}
