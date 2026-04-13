package com.amazonaws.mobileconnectors.kinesisvideo.client;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import androidx.annotation.NonNull;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.kinesisvideo.client.KinesisVideoClient;
import com.amazonaws.kinesisvideo.client.KinesisVideoClientConfiguration;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.logging.LogLevel;
import com.amazonaws.kinesisvideo.common.logging.OutputChannel;
import com.amazonaws.kinesisvideo.producer.DeviceInfo;
import com.amazonaws.kinesisvideo.producer.StorageInfo;
import com.amazonaws.kinesisvideo.producer.Tag;
import com.amazonaws.kinesisvideo.storage.DefaultStorageCallbacks;
import com.amazonaws.mobileconnectors.kinesisvideo.auth.KinesisVideoCredentialsProviderImpl;
import com.amazonaws.mobileconnectors.kinesisvideo.service.KinesisVideoAndroidServiceClient;
import com.amazonaws.mobileconnectors.kinesisvideo.util.AndroidLogOutputChannel;
import com.amazonaws.regions.Regions;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class KinesisVideoAndroidClientFactory {
    private static final String DEVICE_NAME = "android-client-library";
    private static final int DEVICE_VERSION = 0;
    private static KinesisVideoClient KINESIS_VIDEO_CLIENT_INSTANCE = null;
    private static final String LOG_TAG = "KinesisVideoAndroidClient";
    private static final long MAX_STORAGE_SIZE_384_MEGS = 402653184;
    private static final long MIN_STORAGE_SIZE_64_MEGS = 67108864;
    private static final int NUMBER_OF_THREADS_IN_POOL = 2;
    private static final int SPILL_RATIO_90_PERCENT = 90;
    private static final String STORAGE_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final int TEN_STREAMS = 10;
    private static final double TOTAL_MEMORY_RATIO = 0.9d;

    private KinesisVideoAndroidClientFactory() {
        throw new UnsupportedOperationException();
    }

    public static KinesisVideoClient createKinesisVideoClient(@NonNull Context context, @NonNull AWSCredentialsProvider credentialsProvider) {
        return createKinesisVideoClient(context, Regions.DEFAULT_REGION, credentialsProvider);
    }

    public static KinesisVideoClient createKinesisVideoClient(@NonNull Context context, @NonNull Regions regions, @NonNull AWSCredentialsProvider awsCredentialsProvider) {
        OutputChannel outputChannel = new AndroidLogOutputChannel();
        Log log = new Log(outputChannel, LogLevel.VERBOSE, LOG_TAG);
        return createKinesisVideoClient(context, KinesisVideoClientConfiguration.builder().withRegion(regions.getName()).withCredentialsProvider(new KinesisVideoCredentialsProviderImpl(awsCredentialsProvider, log)).withLogChannel(outputChannel).withStorageCallbacks(new DefaultStorageCallbacks()).build(), defaultDeviceInfo(context), log, Executors.newScheduledThreadPool(2));
    }

    public static KinesisVideoClient createKinesisVideoClient(@NonNull Context context, @NonNull KinesisVideoClientConfiguration configuration, @NonNull DeviceInfo deviceInfo, @NonNull Log log, @NonNull ScheduledExecutorService executor) {
        if (KINESIS_VIDEO_CLIENT_INSTANCE == null) {
            AndroidKinesisVideoClient androidKinesisVideoClient = new AndroidKinesisVideoClient(log, context, configuration, new KinesisVideoAndroidServiceClient(log), executor);
            androidKinesisVideoClient.initialize(deviceInfo);
            KINESIS_VIDEO_CLIENT_INSTANCE = androidKinesisVideoClient;
        }
        return KINESIS_VIDEO_CLIENT_INSTANCE;
    }

    public static void freeKinesisVideoClient() {
        KINESIS_VIDEO_CLIENT_INSTANCE.free();
        KINESIS_VIDEO_CLIENT_INSTANCE = null;
    }

    private static DeviceInfo defaultDeviceInfo(@NonNull Context context) {
        return new DeviceInfo(0, DEVICE_NAME, defaultStorageInfo(context), 10, defaultDeviceTags());
    }

    private static StorageInfo defaultStorageInfo(@NonNull Context context) {
        return new StorageInfo(0, StorageInfo.DeviceStorageType.DEVICE_STORAGE_TYPE_IN_MEM, defaultMemorySize(context), 90, STORAGE_PATH);
    }

    private static long defaultMemorySize(@NonNull Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager == null) {
            return MIN_STORAGE_SIZE_64_MEGS;
        }
        activityManager.getMemoryInfo(memoryInfo);
        return Math.min(MAX_STORAGE_SIZE_384_MEGS, (long) (((double) memoryInfo.availMem) * TOTAL_MEMORY_RATIO));
    }

    private static Tag[] defaultDeviceTags() {
        return null;
    }
}
