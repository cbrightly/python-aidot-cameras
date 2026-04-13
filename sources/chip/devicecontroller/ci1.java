package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ci1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ci1 a = new ci1();

    private /* synthetic */ ci1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TimeFormatLocalizationCluster) baseChipCluster).writeActiveCalendarTypeAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
