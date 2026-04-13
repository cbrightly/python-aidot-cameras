package com.alibaba.android.arouter.thread;

import java.util.concurrent.CountDownLatch;

/* compiled from: CancelableCountDownLatch */
public class a extends CountDownLatch {
    public a(int count) {
        super(count);
    }

    public void a() {
        while (getCount() > 0) {
            countDown();
        }
    }
}
