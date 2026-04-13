package com.blankj.utilcode.util;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UtilsTransActivity extends AppCompatActivity {
    private static final Map<UtilsTransActivity, a> c = new HashMap();

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        Serializable extra = getIntent().getSerializableExtra("extra_delegate");
        if (!(extra instanceof a)) {
            super.onCreate(savedInstanceState);
            finish();
            return;
        }
        a delegate = (a) extra;
        c.put(this, delegate);
        delegate.onCreateBefore(this, savedInstanceState);
        super.onCreate(savedInstanceState);
        delegate.onCreated(this, savedInstanceState);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        a callback = c.get(this);
        if (callback != null) {
            callback.onStarted(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        a callback = c.get(this);
        if (callback != null) {
            callback.onResumed(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
        a callback = c.get(this);
        if (callback != null) {
            callback.onPaused(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        a callback = c.get(this);
        if (callback != null) {
            callback.onStopped(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        a callback = c.get(this);
        if (callback != null) {
            callback.onSaveInstanceState(this, outState);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        Map<UtilsTransActivity, a> map = c;
        a callback = map.get(this);
        if (callback != null) {
            callback.onDestroy(this);
            map.remove(this);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        a callback = c.get(this);
        if (callback != null) {
            callback.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        a callback = c.get(this);
        if (callback != null) {
            callback.onActivityResult(this, requestCode, resultCode, data);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        a callback = c.get(this);
        if (callback == null) {
            return super.dispatchTouchEvent(ev);
        }
        if (callback.dispatchTouchEvent(this, ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public static abstract class a implements Serializable {
        public void onCreateBefore(@NonNull UtilsTransActivity activity, @Nullable Bundle savedInstanceState) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onCreated(@NonNull UtilsTransActivity activity, @Nullable Bundle savedInstanceState) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onStarted(@NonNull UtilsTransActivity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onDestroy(@NonNull UtilsTransActivity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onResumed(@NonNull UtilsTransActivity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onPaused(@NonNull UtilsTransActivity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onStopped(@NonNull UtilsTransActivity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onSaveInstanceState(@NonNull UtilsTransActivity activity, Bundle outState) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onRequestPermissionsResult(@NonNull UtilsTransActivity activity, int requestCode, String[] permissions, int[] grantResults) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onActivityResult(@NonNull UtilsTransActivity activity, int requestCode, int resultCode, Intent data) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public boolean dispatchTouchEvent(@NonNull UtilsTransActivity activity, MotionEvent ev) {
            if (activity != null) {
                return false;
            }
            throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }
}
