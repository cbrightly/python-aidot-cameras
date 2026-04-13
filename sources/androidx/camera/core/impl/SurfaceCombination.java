package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SurfaceCombination {
    private final List<SurfaceConfig> mSurfaceConfigList = new ArrayList();

    private static void generateArrangements(List<int[]> arrangementsResultList, int n, int[] result, int index) {
        if (index >= result.length) {
            arrangementsResultList.add((int[]) result.clone());
            return;
        }
        for (int i = 0; i < n; i++) {
            boolean included = false;
            int j = 0;
            while (true) {
                if (j >= index) {
                    break;
                } else if (i == result[j]) {
                    included = true;
                    break;
                } else {
                    j++;
                }
            }
            if (!included) {
                result[index] = i;
                generateArrangements(arrangementsResultList, n, result, index + 1);
            }
        }
    }

    public boolean addSurfaceConfig(@NonNull SurfaceConfig surfaceConfig) {
        return this.mSurfaceConfigList.add(surfaceConfig);
    }

    public boolean removeSurfaceConfig(@NonNull SurfaceConfig surfaceConfig) {
        return this.mSurfaceConfigList.remove(surfaceConfig);
    }

    @NonNull
    public List<SurfaceConfig> getSurfaceConfigList() {
        return this.mSurfaceConfigList;
    }

    public boolean isSupported(@NonNull List<SurfaceConfig> configList) {
        if (configList.isEmpty()) {
            return true;
        }
        if (configList.size() > this.mSurfaceConfigList.size()) {
            return false;
        }
        Iterator<int[]> it = getElementsArrangements(this.mSurfaceConfigList.size()).iterator();
        while (it.hasNext()) {
            int[] elementsArrangement = it.next();
            boolean checkResult = true;
            int index = 0;
            while (index < this.mSurfaceConfigList.size() && (elementsArrangement[index] >= configList.size() || ((checkResult = checkResult & this.mSurfaceConfigList.get(index).isSupported(configList.get(elementsArrangement[index])))))) {
                index++;
            }
            if (checkResult) {
                return true;
            }
        }
        return false;
    }

    private List<int[]> getElementsArrangements(int n) {
        List<int[]> arrangementsResultList = new ArrayList<>();
        generateArrangements(arrangementsResultList, n, new int[n], 0);
        return arrangementsResultList;
    }
}
