package androidx.room;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.room.IMultiInstanceInvalidationService;
import java.util.HashMap;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class MultiInstanceInvalidationService extends Service {
    private final IMultiInstanceInvalidationService.Stub mBinder = new IMultiInstanceInvalidationService.Stub() {
        public int registerCallback(IMultiInstanceInvalidationCallback callback, String name) {
            if (name == null) {
                return 0;
            }
            synchronized (MultiInstanceInvalidationService.this.mCallbackList) {
                MultiInstanceInvalidationService multiInstanceInvalidationService = MultiInstanceInvalidationService.this;
                int clientId = multiInstanceInvalidationService.mMaxClientId + 1;
                multiInstanceInvalidationService.mMaxClientId = clientId;
                if (multiInstanceInvalidationService.mCallbackList.register(callback, Integer.valueOf(clientId))) {
                    MultiInstanceInvalidationService.this.mClientNames.put(Integer.valueOf(clientId), name);
                    return clientId;
                }
                MultiInstanceInvalidationService multiInstanceInvalidationService2 = MultiInstanceInvalidationService.this;
                multiInstanceInvalidationService2.mMaxClientId--;
                return 0;
            }
        }

        public void unregisterCallback(IMultiInstanceInvalidationCallback callback, int clientId) {
            synchronized (MultiInstanceInvalidationService.this.mCallbackList) {
                MultiInstanceInvalidationService.this.mCallbackList.unregister(callback);
                MultiInstanceInvalidationService.this.mClientNames.remove(Integer.valueOf(clientId));
            }
        }

        public void broadcastInvalidation(int clientId, String[] tables) {
            synchronized (MultiInstanceInvalidationService.this.mCallbackList) {
                String name = MultiInstanceInvalidationService.this.mClientNames.get(Integer.valueOf(clientId));
                if (name == null) {
                    Log.w("ROOM", "Remote invalidation client ID not registered");
                    return;
                }
                int count = MultiInstanceInvalidationService.this.mCallbackList.beginBroadcast();
                for (int i = 0; i < count; i++) {
                    try {
                        int targetClientId = ((Integer) MultiInstanceInvalidationService.this.mCallbackList.getBroadcastCookie(i)).intValue();
                        String targetName = MultiInstanceInvalidationService.this.mClientNames.get(Integer.valueOf(targetClientId));
                        if (clientId != targetClientId && name.equals(targetName)) {
                            MultiInstanceInvalidationService.this.mCallbackList.getBroadcastItem(i).onInvalidation(tables);
                        }
                    } catch (RemoteException e) {
                        Log.w("ROOM", "Error invoking a remote callback", e);
                    } catch (Throwable th) {
                        MultiInstanceInvalidationService.this.mCallbackList.finishBroadcast();
                        throw th;
                    }
                }
                MultiInstanceInvalidationService.this.mCallbackList.finishBroadcast();
            }
        }
    };
    final RemoteCallbackList<IMultiInstanceInvalidationCallback> mCallbackList = new RemoteCallbackList<IMultiInstanceInvalidationCallback>() {
        public void onCallbackDied(IMultiInstanceInvalidationCallback callback, Object cookie) {
            MultiInstanceInvalidationService.this.mClientNames.remove(Integer.valueOf(((Integer) cookie).intValue()));
        }
    };
    final HashMap<Integer, String> mClientNames = new HashMap<>();
    int mMaxClientId = 0;

    @Nullable
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
