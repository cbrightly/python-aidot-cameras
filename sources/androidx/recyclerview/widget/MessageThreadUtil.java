package androidx.recyclerview.widget;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.recyclerview.widget.ThreadUtil;
import androidx.recyclerview.widget.TileList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class MessageThreadUtil<T> implements ThreadUtil<T> {
    MessageThreadUtil() {
    }

    public ThreadUtil.MainThreadCallback<T> getMainThreadProxy(final ThreadUtil.MainThreadCallback<T> callback) {
        return new ThreadUtil.MainThreadCallback<T>() {
            static final int ADD_TILE = 2;
            static final int REMOVE_TILE = 3;
            static final int UPDATE_ITEM_COUNT = 1;
            private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
            private Runnable mMainThreadRunnable = new Runnable() {
                public void run() {
                    SyncQueueItem msg = AnonymousClass1.this.mQueue.next();
                    while (msg != null) {
                        switch (msg.what) {
                            case 1:
                                callback.updateItemCount(msg.arg1, msg.arg2);
                                break;
                            case 2:
                                callback.addTile(msg.arg1, (TileList.Tile) msg.data);
                                break;
                            case 3:
                                callback.removeTile(msg.arg1, msg.arg2);
                                break;
                            default:
                                Log.e("ThreadUtil", "Unsupported message, what=" + msg.what);
                                break;
                        }
                        msg = AnonymousClass1.this.mQueue.next();
                    }
                }
            };
            final MessageQueue mQueue = new MessageQueue();

            public void updateItemCount(int generation, int itemCount) {
                sendMessage(SyncQueueItem.obtainMessage(1, generation, itemCount));
            }

            public void addTile(int generation, TileList.Tile<T> tile) {
                sendMessage(SyncQueueItem.obtainMessage(2, generation, (Object) tile));
            }

            public void removeTile(int generation, int position) {
                sendMessage(SyncQueueItem.obtainMessage(3, generation, position));
            }

            private void sendMessage(SyncQueueItem msg) {
                this.mQueue.sendMessage(msg);
                this.mMainThreadHandler.post(this.mMainThreadRunnable);
            }
        };
    }

    public ThreadUtil.BackgroundCallback<T> getBackgroundProxy(final ThreadUtil.BackgroundCallback<T> callback) {
        return new ThreadUtil.BackgroundCallback<T>() {
            static final int LOAD_TILE = 3;
            static final int RECYCLE_TILE = 4;
            static final int REFRESH = 1;
            static final int UPDATE_RANGE = 2;
            private Runnable mBackgroundRunnable = new Runnable() {
                public void run() {
                    while (true) {
                        SyncQueueItem msg = AnonymousClass2.this.mQueue.next();
                        if (msg != null) {
                            switch (msg.what) {
                                case 1:
                                    AnonymousClass2.this.mQueue.removeMessages(1);
                                    callback.refresh(msg.arg1);
                                    break;
                                case 2:
                                    AnonymousClass2.this.mQueue.removeMessages(2);
                                    AnonymousClass2.this.mQueue.removeMessages(3);
                                    callback.updateRange(msg.arg1, msg.arg2, msg.arg3, msg.arg4, msg.arg5);
                                    break;
                                case 3:
                                    callback.loadTile(msg.arg1, msg.arg2);
                                    break;
                                case 4:
                                    callback.recycleTile((TileList.Tile) msg.data);
                                    break;
                                default:
                                    Log.e("ThreadUtil", "Unsupported message, what=" + msg.what);
                                    break;
                            }
                        } else {
                            AnonymousClass2.this.mBackgroundRunning.set(false);
                            return;
                        }
                    }
                }
            };
            AtomicBoolean mBackgroundRunning = new AtomicBoolean(false);
            private final Executor mExecutor = AsyncTask.THREAD_POOL_EXECUTOR;
            final MessageQueue mQueue = new MessageQueue();

            public void refresh(int generation) {
                sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(1, generation, (Object) null));
            }

            public void updateRange(int rangeStart, int rangeEnd, int extRangeStart, int extRangeEnd, int scrollHint) {
                sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(2, rangeStart, rangeEnd, extRangeStart, extRangeEnd, scrollHint, (Object) null));
            }

            public void loadTile(int position, int scrollHint) {
                sendMessage(SyncQueueItem.obtainMessage(3, position, scrollHint));
            }

            public void recycleTile(TileList.Tile<T> tile) {
                sendMessage(SyncQueueItem.obtainMessage(4, 0, (Object) tile));
            }

            private void sendMessage(SyncQueueItem msg) {
                this.mQueue.sendMessage(msg);
                maybeExecuteBackgroundRunnable();
            }

            private void sendMessageAtFrontOfQueue(SyncQueueItem msg) {
                this.mQueue.sendMessageAtFrontOfQueue(msg);
                maybeExecuteBackgroundRunnable();
            }

            private void maybeExecuteBackgroundRunnable() {
                if (this.mBackgroundRunning.compareAndSet(false, true)) {
                    this.mExecutor.execute(this.mBackgroundRunnable);
                }
            }
        };
    }

    public static class SyncQueueItem {
        private static SyncQueueItem sPool;
        private static final Object sPoolLock = new Object();
        public int arg1;
        public int arg2;
        public int arg3;
        public int arg4;
        public int arg5;
        public Object data;
        SyncQueueItem next;
        public int what;

        SyncQueueItem() {
        }

        /* access modifiers changed from: package-private */
        public void recycle() {
            this.next = null;
            this.arg5 = 0;
            this.arg4 = 0;
            this.arg3 = 0;
            this.arg2 = 0;
            this.arg1 = 0;
            this.what = 0;
            this.data = null;
            synchronized (sPoolLock) {
                SyncQueueItem syncQueueItem = sPool;
                if (syncQueueItem != null) {
                    this.next = syncQueueItem;
                }
                sPool = this;
            }
        }

        static SyncQueueItem obtainMessage(int what2, int arg12, int arg22, int arg32, int arg42, int arg52, Object data2) {
            SyncQueueItem item;
            synchronized (sPoolLock) {
                SyncQueueItem item2 = sPool;
                if (item2 == null) {
                    item = new SyncQueueItem();
                } else {
                    SyncQueueItem item3 = item2;
                    sPool = item2.next;
                    item3.next = null;
                    item = item3;
                }
                item.what = what2;
                item.arg1 = arg12;
                item.arg2 = arg22;
                item.arg3 = arg32;
                item.arg4 = arg42;
                item.arg5 = arg52;
                item.data = data2;
            }
            return item;
        }

        static SyncQueueItem obtainMessage(int what2, int arg12, int arg22) {
            return obtainMessage(what2, arg12, arg22, 0, 0, 0, (Object) null);
        }

        static SyncQueueItem obtainMessage(int what2, int arg12, Object data2) {
            return obtainMessage(what2, arg12, 0, 0, 0, 0, data2);
        }
    }

    public static class MessageQueue {
        private SyncQueueItem mRoot;

        MessageQueue() {
        }

        /* access modifiers changed from: package-private */
        public synchronized SyncQueueItem next() {
            SyncQueueItem syncQueueItem = this.mRoot;
            if (syncQueueItem == null) {
                return null;
            }
            SyncQueueItem next = syncQueueItem;
            this.mRoot = syncQueueItem.next;
            return next;
        }

        /* access modifiers changed from: package-private */
        public synchronized void sendMessageAtFrontOfQueue(SyncQueueItem item) {
            item.next = this.mRoot;
            this.mRoot = item;
        }

        /* access modifiers changed from: package-private */
        public synchronized void sendMessage(SyncQueueItem item) {
            SyncQueueItem last = this.mRoot;
            if (last == null) {
                this.mRoot = item;
                return;
            }
            while (true) {
                SyncQueueItem syncQueueItem = last.next;
                if (syncQueueItem != null) {
                    last = syncQueueItem;
                } else {
                    last.next = item;
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0014  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public synchronized void removeMessages(int r5) {
            /*
                r4 = this;
                monitor-enter(r4)
            L_0x0001:
                androidx.recyclerview.widget.MessageThreadUtil$SyncQueueItem r0 = r4.mRoot     // Catch:{ all -> 0x002a }
                if (r0 == 0) goto L_0x0012
                int r1 = r0.what     // Catch:{ all -> 0x002a }
                if (r1 != r5) goto L_0x0012
                r1 = r0
                androidx.recyclerview.widget.MessageThreadUtil$SyncQueueItem r0 = r0.next     // Catch:{ all -> 0x002a }
                r4.mRoot = r0     // Catch:{ all -> 0x002a }
                r1.recycle()     // Catch:{ all -> 0x002a }
                goto L_0x0001
            L_0x0012:
                if (r0 == 0) goto L_0x0028
                androidx.recyclerview.widget.MessageThreadUtil$SyncQueueItem r1 = r0.next     // Catch:{ all -> 0x002a }
            L_0x0017:
                if (r1 == 0) goto L_0x0028
                androidx.recyclerview.widget.MessageThreadUtil$SyncQueueItem r2 = r1.next     // Catch:{ all -> 0x002a }
                int r3 = r1.what     // Catch:{ all -> 0x002a }
                if (r3 != r5) goto L_0x0025
                r0.next = r2     // Catch:{ all -> 0x002a }
                r1.recycle()     // Catch:{ all -> 0x002a }
                goto L_0x0026
            L_0x0025:
                r0 = r1
            L_0x0026:
                r1 = r2
                goto L_0x0017
            L_0x0028:
                monitor-exit(r4)
                return
            L_0x002a:
                r5 = move-exception
                monitor-exit(r4)
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.MessageThreadUtil.MessageQueue.removeMessages(int):void");
        }
    }
}
