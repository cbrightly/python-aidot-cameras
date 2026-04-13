package com.leedarson.serviceimpl.shake;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.ShakeService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import net.sqlcipher.database.SQLiteDatabase;

public class ShakeServiceImpl implements ShakeService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    /* access modifiers changed from: private */
    public boolean b = false;

    public void handleShake(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 8692, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            try {
                timber.log.a.g(Constants.SERVICE_DEBUG).c("ShakeServiceImplhandleShake: ", new Object[0]);
                SensorManager manager = (SensorManager) this.a.getSystemService("sensor");
                manager.registerListener(new a(activity), manager.getDefaultSensor(1), 3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class a implements SensorEventListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Activity a;

        a(Activity activity) {
            this.a = activity;
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            if (!PatchProxy.proxy(new Object[]{sensorEvent}, this, changeQuickRedirect, false, 8695, new Class[]{SensorEvent.class}, Void.TYPE).isSupported) {
                int sensorType = sensorEvent.sensor.getType();
                float[] values = sensorEvent.values;
                if (sensorType != 1) {
                    return;
                }
                if (Math.abs(values[0]) > 16.0f || Math.abs(values[1]) > 16.0f || Math.abs(values[2]) > 16.0f) {
                    timber.log.a.g(Constants.SERVICE_DEBUG).a("摇一摇功能触发", new Object[0]);
                    boolean b2 = BaseApplication.b().o();
                    if (ShakeServiceImpl.this.b && b2) {
                        this.a.startActivity(new Intent(this.a, ShakeActivity.class));
                    }
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }

    public void init(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 8693, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.a = context;
            timber.log.a.g(Constants.SERVICE_DEBUG).a("init: ", new Object[0]);
        }
    }

    public void setJumpShake(boolean jumpShake) {
        this.b = jumpShake;
    }

    public void jumpShakeActivity(Context act) {
        if (!PatchProxy.proxy(new Object[]{act}, this, changeQuickRedirect, false, 8694, new Class[]{Context.class}, Void.TYPE).isSupported) {
            Intent intent = new Intent(act, ShakeActivity.class);
            if (!(act instanceof Activity)) {
                intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            }
            act.startActivity(intent);
        }
    }
}
