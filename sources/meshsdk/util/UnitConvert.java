package meshsdk.util;

import com.telink.ble.mesh.util.MeshLogger;
import java.util.Calendar;
import meshsdk.MeshLog;

public final class UnitConvert {
    public static int lum2level(int lum) {
        if (lum > 100) {
            lum = 100;
        }
        return ((65535 * lum) / 100) - 32768;
    }

    public static int level2lum(short level) {
        int lightness = 32768 + level;
        int fix = 0;
        if (lightness != 0) {
            if (lightness < 329) {
                lightness = 654;
            }
            fix = 327;
        }
        int re = ((lightness + fix) * 100) / 65535;
        MeshLogger.d("level2lum: " + level + " re: " + re);
        return re;
    }

    public static int lum2lightness(int lum) {
        return (int) Math.ceil((((double) lum) * 65535.0d) / 100.0d);
    }

    public static int lightness2lum(int lightness) {
        return (lightness * 100) / 65535;
    }

    public static int temp100ToTemp(int temp100) {
        if (temp100 > 100) {
            temp100 = 100;
        }
        return ((temp100 * 19200) / 100) + 800;
    }

    public static int tempToTemp100(int temp) {
        if (temp < 800) {
            return 0;
        }
        if (temp > 20000) {
            return 100;
        }
        return ((temp - 800) * 100) / 19200;
    }

    public static int getZoneOffset() {
        Calendar cal = Calendar.getInstance();
        return ((((cal.get(15) + cal.get(16)) / 60) / 1000) / 15) + 64;
    }

    public static byte fading2Transition(int fadingMill) {
        int value;
        byte step;
        if (fadingMill > 630000) {
            value = fadingMill / 600000;
            step = 3;
        } else if (fadingMill > 63000) {
            value = fadingMill / 10000;
            step = 2;
        } else if (fadingMill > 6300) {
            value = fadingMill / 1000;
            step = 1;
        } else {
            value = fadingMill / 100;
            step = 0;
        }
        byte val = (byte) (value & 63);
        byte res = (byte) ((step << 6) | val);
        MeshLog.d("fading step:" + step + ",val:" + val + ",result:" + res + "==>" + getByteStr(res));
        return res;
    }

    public static String getByteStr(byte b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 7; i++) {
            sb.append((byte) (b & 1));
            b = (byte) (b >> 1);
        }
        return "0b" + sb.reverse().toString();
    }
}
