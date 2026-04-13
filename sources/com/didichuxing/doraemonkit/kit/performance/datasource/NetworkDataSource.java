package com.didichuxing.doraemonkit.kit.performance.datasource;

import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.kit.network.utils.ByteUtil;
import com.didichuxing.doraemonkit.kit.performance.widget.LineData;

public class NetworkDataSource implements IDataSource {
    private static final String TAG = "NetworkDataSource";
    private long latestTotalLength = -1;

    public LineData createData() {
        long diff = 0;
        long totalSize = NetworkManager.get().getTotalSize();
        long j = this.latestTotalLength;
        if (j >= 0) {
            diff = totalSize - j;
            if (diff < 0) {
                diff = 0;
            }
        }
        this.latestTotalLength = totalSize;
        if (diff == 0) {
            return LineData.obtain((float) Math.ceil((double) (((float) diff) / 1024.0f)), (String) null);
        }
        return LineData.obtain((float) Math.ceil((double) (((float) diff) / 1024.0f)), ByteUtil.getPrintSize(diff));
    }
}
