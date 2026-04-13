package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.sensorsdata.analytics.android.sdk.util.ThreadUtils;
import java.util.concurrent.LinkedBlockingQueue;

public class OAIDService implements ServiceConnection {
    public static final LinkedBlockingQueue<IBinder> BINDER_QUEUE = new LinkedBlockingQueue<>(1);

    OAIDService() {
    }

    public class Task implements Runnable {
        final IBinder binder;

        Task(IBinder iBinder) {
            this.binder = iBinder;
        }

        public void run() {
            try {
                LinkedBlockingQueue<IBinder> linkedBlockingQueue = OAIDService.BINDER_QUEUE;
                if (linkedBlockingQueue.size() > 0) {
                    linkedBlockingQueue.clear();
                }
                linkedBlockingQueue.put(this.binder);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        ThreadUtils.getSinglePool().execute(new Task(service));
    }

    public void onServiceDisconnected(ComponentName name) {
    }
}
