package com.king.zxing;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import com.king.zxing.camera.d;
import com.king.zxing.camera.e;

/* compiled from: AmbientLightManager */
public final class f implements SensorEventListener {
    private float a = 45.0f;
    private float b = 100.0f;
    private final Context c;
    private d d;
    private Sensor e;

    f(Context context) {
        this.c = context;
    }

    /* access modifiers changed from: package-private */
    public void c(d cameraManager) {
        try {
            this.d = cameraManager;
            if (e.readPref(PreferenceManager.getDefaultSharedPreferences(this.c)) == e.AUTO) {
                SensorManager sensorManager = (SensorManager) this.c.getSystemService("sensor");
                Sensor defaultSensor = sensorManager.getDefaultSensor(5);
                this.e = defaultSensor;
                if (defaultSensor != null) {
                    sensorManager.registerListener(this, defaultSensor, 3);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void d() {
        if (this.e != null) {
            try {
                ((SensorManager) this.c.getSystemService("sensor")).unregisterListener(this);
                this.d = null;
                this.e = null;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        float ambientLightLux = sensorEvent.values[0];
        d dVar = this.d;
        if (dVar == null) {
            return;
        }
        if (ambientLightLux <= this.a) {
            dVar.k(true, ambientLightLux);
        } else if (ambientLightLux >= this.b) {
            dVar.k(false, ambientLightLux);
        }
    }

    public void b(float tooDarkLux) {
        this.a = tooDarkLux;
    }

    public void a(float brightEnoughLux) {
        this.b = brightEnoughLux;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
