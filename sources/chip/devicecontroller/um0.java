package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class um0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ um0 a = new um0();

    private /* synthetic */ um0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readStartUpColorTemperatureMiredsAttribute((ChipClusters.ColorControlCluster.StartUpColorTemperatureMiredsAttributeCallback) obj);
    }
}
