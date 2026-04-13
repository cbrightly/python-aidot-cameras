package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class xr implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ xr a = new xr();

    private /* synthetic */ xr() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.IlluminanceMeasurementCluster) baseChipCluster).readFeatureMapAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
