package com.didichuxing.doraemonkit.kit.largepicture;

import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.config.PerformanceSpInfoConfig;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class LargePictureManager {
    public static float FILE_DEFAULT_THRESHOLD = 150.0f;
    public static Map<String, LargeImageInfo> LARGE_IMAGE_INFO_MAP = new HashMap();
    public static float MEMORY_DEFAULT_THRESHOLD = 1.0f;
    private static final String TAG = "LargePictureManager";
    private double fileThreshold = PerformanceSpInfoConfig.getLargeImgFileThreshold(FILE_DEFAULT_THRESHOLD);
    private DecimalFormat mDecimalFormat = new DecimalFormat("0.00");
    private double memoryThreshold = PerformanceSpInfoConfig.getLargeImgMemoryThreshold(MEMORY_DEFAULT_THRESHOLD);

    public void setFileThreshold(double fileThreshold2) {
        this.fileThreshold = fileThreshold2;
    }

    public void setMemoryThreshold(double memoryThreshold2) {
        this.memoryThreshold = memoryThreshold2;
    }

    public static LargePictureManager getInstance() {
        return Holder.INSTANCE;
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static LargePictureManager INSTANCE = new LargePictureManager();

        private Holder() {
        }
    }

    public void process(String url, int size) {
        if (!(a.b() instanceof UniversalActivity) && PerformanceSpInfoConfig.isLargeImgOpen()) {
            saveImageInfo(url, ((double) size) / 1024.0d);
        }
    }

    private void saveImageInfo(String url, double fileSize) {
        LargeImageInfo largeImageInfo;
        if (!(a.b() instanceof UniversalActivity)) {
            if (LARGE_IMAGE_INFO_MAP.containsKey(url)) {
                largeImageInfo = LARGE_IMAGE_INFO_MAP.get(url);
            } else {
                largeImageInfo = new LargeImageInfo();
                LARGE_IMAGE_INFO_MAP.put(url, largeImageInfo);
                largeImageInfo.setUrl(url);
            }
            largeImageInfo.setFileSize(fileSize);
        }
    }

    public void saveImageInfo(String url, double memorySize, int width, int height, String framework) {
        LargeImageInfo largeImageInfo;
        if (!(a.b() instanceof UniversalActivity)) {
            if (LARGE_IMAGE_INFO_MAP.containsKey(url)) {
                largeImageInfo = LARGE_IMAGE_INFO_MAP.get(url);
            } else {
                largeImageInfo = new LargeImageInfo();
                LARGE_IMAGE_INFO_MAP.put(url, largeImageInfo);
                largeImageInfo.setUrl(url);
            }
            largeImageInfo.setMemorySize(memorySize);
            largeImageInfo.setWidth(width);
            largeImageInfo.setHeight(height);
            largeImageInfo.setFramework(framework);
        }
    }
}
