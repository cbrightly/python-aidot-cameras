package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class o81 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ o81 a = new o81();

    private /* synthetic */ o81() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readOptionsAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
