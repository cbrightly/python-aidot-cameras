package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class cr implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ cr a = new cr();

    private /* synthetic */ cr() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BridgedDeviceBasicCluster) baseChipCluster).readHardwareVersionAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
