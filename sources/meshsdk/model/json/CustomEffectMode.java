package meshsdk.model.json;

import android.util.SparseArray;
import java.util.List;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

public class CustomEffectMode {
    public static SparseArray timeMap;
    public int customizeEffectId;
    public List<Effect> effects;
    public int fading;
    public String groupId;
    public String mac;
    public int type;
    public int unit = 1;

    public static class Effect {
        public int color;
        public int dimming;
        public int type;
    }

    static {
        SparseArray sparseArray = new SparseArray();
        timeMap = sparseArray;
        sparseArray.put(0, 0);
        timeMap.put(3, 1);
        timeMap.put(5, 2);
        timeMap.put(10, 3);
        timeMap.put(15, 4);
        timeMap.put(20, 5);
        timeMap.put(30, 6);
        timeMap.put(60, 7);
        timeMap.put(180, 8);
        timeMap.put(IjkMediaCodecInfo.RANK_SECURE, 9);
        timeMap.put(600, 10);
        timeMap.put(900, 11);
        timeMap.put(1200, 12);
        timeMap.put(1500, 13);
        timeMap.put(1800, 14);
    }

    public int getTime() {
        if (timeMap.get(this.fading) != null) {
            return ((Integer) timeMap.get(this.fading)).intValue();
        }
        return 0;
    }
}
