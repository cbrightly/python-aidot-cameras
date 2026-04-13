package androidx.room;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.IMultiInstanceInvalidationCallback;
import androidx.room.IMultiInstanceInvalidationService;
import androidx.room.InvalidationTracker;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class MultiInstanceInvalidationClient {
    final Context mAppContext;
    final IMultiInstanceInvalidationCallback mCallback = new IMultiInstanceInvalidationCallback.Stub() {
        public void onInvalidation(final String[] tables) {
            MultiInstanceInvalidationClient.this.mExecutor.execute(new Runnable() {
                public void run() {
                    MultiInstanceInvalidationClient.this.mInvalidationTracker.notifyObserversByTableNames(tables);
                }
            });
        }
    };
    int mClientId;
    final Executor mExecutor;
    final InvalidationTracker mInvalidationTracker;
    final String mName;
    final InvalidationTracker.Observer mObserver;
    final Runnable mRemoveObserverRunnable;
    @Nullable
    IMultiInstanceInvalidationService mService;
    final ServiceConnection mServiceConnection;
    final Runnable mSetUpRunnable;
    final AtomicBoolean mStopped = new AtomicBoolean(false);
    private final Runnable mTearDownRunnable;

    MultiInstanceInvalidationClient(Context context, String name, InvalidationTracker invalidationTracker, Executor executor) {
        AnonymousClass2 r0 = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder service) {
                MultiInstanceInvalidationClient.this.mService = IMultiInstanceInvalidationService.Stub.asInterface(service);
                MultiInstanceInvalidationClient multiInstanceInvalidationClient = MultiInstanceInvalidationClient.this;
                multiInstanceInvalidationClient.mExecutor.execute(multiInstanceInvalidationClient.mSetUpRunnable);
            }

            public void onServiceDisconnected(ComponentName name) {
                MultiInstanceInvalidationClient multiInstanceInvalidationClient = MultiInstanceInvalidationClient.this;
                multiInstanceInvalidationClient.mExecutor.execute(multiInstanceInvalidationClient.mRemoveObserverRunnable);
                MultiInstanceInvalidationClient.this.mService = null;
            }
        };
        this.mServiceConnection = r0;
        this.mSetUpRunnable = new Runnable() {
            public void run() {
                try {
                    MultiInstanceInvalidationClient multiInstanceInvalidationClient = MultiInstanceInvalidationClient.this;
                    IMultiInstanceInvalidationService service = multiInstanceInvalidationClient.mService;
                    if (service != null) {
                        multiInstanceInvalidationClient.mClientId = service.registerCallback(multiInstanceInvalidationClient.mCallback, multiInstanceInvalidationClient.mName);
                        MultiInstanceInvalidationClient multiInstanceInvalidationClient2 = MultiInstanceInvalidationClient.this;
                        multiInstanceInvalidationClient2.mInvalidationTracker.addObserver(multiInstanceInvalidationClient2.mObserver);
                    }
                } catch (RemoteException e) {
                    Log.w("ROOM", "Cannot register multi-instance invalidation callback", e);
                }
            }
        };
        this.mRemoveObserverRunnable = new Runnable() {
            public void run() {
                MultiInstanceInvalidationClient multiInstanceInvalidationClient = MultiInstanceInvalidationClient.this;
                multiInstanceInvalidationClient.mInvalidationTracker.removeObserver(multiInstanceInvalidationClient.mObserver);
            }
        };
        this.mTearDownRunnable = new Runnable() {
            public void run() {
                MultiInstanceInvalidationClient multiInstanceInvalidationClient = MultiInstanceInvalidationClient.this;
                multiInstanceInvalidationClient.mInvalidationTracker.removeObserver(multiInstanceInvalidationClient.mObserver);
                try {
                    MultiInstanceInvalidationClient multiInstanceInvalidationClient2 = MultiInstanceInvalidationClient.this;
                    IMultiInstanceInvalidationService service = multiInstanceInvalidationClient2.mService;
                    if (service != null) {
                        service.unregisterCallback(multiInstanceInvalidationClient2.mCallback, multiInstanceInvalidationClient2.mClientId);
                    }
                } catch (RemoteException e) {
                    Log.w("ROOM", "Cannot unregister multi-instance invalidation callback", e);
                }
                MultiInstanceInvalidationClient multiInstanceInvalidationClient3 = MultiInstanceInvalidationClient.this;
                multiInstanceInvalidationClient3.mAppContext.unbindService(multiInstanceInvalidationClient3.mServiceConnection);
            }
        };
        Context applicationContext = context.getApplicationContext();
        this.mAppContext = applicationContext;
        this.mName = name;
        this.mInvalidationTracker = invalidationTracker;
        this.mExecutor = executor;
        this.mObserver = new InvalidationTracker.Observer((String[]) invalidationTracker.mTableIdLookup.keySet().toArray(new String[0])) {
            public void onInvalidated(@NonNull Set<String> tables) {
                if (!MultiInstanceInvalidationClient.this.mStopped.get()) {
                    try {
                        MultiInstanceInvalidationClient multiInstanceInvalidationClient = MultiInstanceInvalidationClient.this;
                        IMultiInstanceInvalidationService service = multiInstanceInvalidationClient.mService;
                        if (service != null) {
                            service.broadcastInvalidation(multiInstanceInvalidationClient.mClientId, (String[]) tables.toArray(new String[0]));
                        }
                    } catch (RemoteException e) {
                        Log.w("ROOM", "Cannot broadcast invalidation", e);
                    }
                }
            }

            /* access modifiers changed from: package-private */
            public boolean isRemote() {
                return true;
            }
        };
        applicationContext.bindService(new Intent(applicationContext, MultiInstanceInvalidationService.class), r0, 1);
    }

    /* access modifiers changed from: package-private */
    public void stop() {
        if (this.mStopped.compareAndSet(false, true)) {
            this.mExecutor.execute(this.mTearDownRunnable);
        }
    }
}
