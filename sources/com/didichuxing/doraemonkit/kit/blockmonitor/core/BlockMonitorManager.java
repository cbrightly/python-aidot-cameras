package com.didichuxing.doraemonkit.kit.blockmonitor.core;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Printer;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.blockmonitor.BlockMonitorFragment;
import com.didichuxing.doraemonkit.kit.blockmonitor.bean.BlockInfo;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.didichuxing.doraemonkit.kit.health.AppHealthInfoUtil;
import com.didichuxing.doraemonkit.kit.health.model.AppHealthInfo;
import com.didichuxing.doraemonkit.kit.timecounter.TimeCounterManager;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.util.NotificationUtils;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockMonitorManager {
    private static final int MAX_SIZE = 50;
    private static final String TAG = "BlockMonitorManager";
    private List<BlockInfo> mBlockInfoList;
    private Context mContext;
    private boolean mIsRunning;
    private MonitorCore mMonitorCore;
    private OnBlockInfoUpdateListener mOnBlockInfoUpdateListener;

    public static class Holder {
        /* access modifiers changed from: private */
        public static BlockMonitorManager INSTANCE = new BlockMonitorManager();

        private Holder() {
        }
    }

    public static BlockMonitorManager getInstance() {
        return Holder.INSTANCE;
    }

    private BlockMonitorManager() {
        this.mBlockInfoList = Collections.synchronizedList(new ArrayList());
    }

    public void start() {
        if (this.mIsRunning) {
            LogHelper.i(TAG, "start when manager is running");
        } else if (DoraemonKit.APPLICATION == null) {
            LogHelper.e(TAG, "start fail, context is null");
        } else {
            TimeCounterManager.get().stop();
            this.mContext = DoraemonKit.APPLICATION.getApplicationContext();
            if (this.mMonitorCore == null) {
                this.mMonitorCore = new MonitorCore();
            }
            this.mIsRunning = true;
            Looper.getMainLooper().setMessageLogging(this.mMonitorCore);
        }
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public void stop() {
        if (!this.mIsRunning) {
            LogHelper.i(TAG, "stop when manager is not running");
            return;
        }
        Looper.getMainLooper().setMessageLogging((Printer) null);
        MonitorCore monitorCore = this.mMonitorCore;
        if (monitorCore != null) {
            monitorCore.shutDown();
            this.mMonitorCore = null;
        }
        NotificationUtils.cancelNotification(this.mContext, 1001);
        this.mIsRunning = false;
        this.mContext = null;
    }

    public void setOnBlockInfoUpdateListener(OnBlockInfoUpdateListener onBlockInfoUpdateListener) {
        this.mOnBlockInfoUpdateListener = onBlockInfoUpdateListener;
    }

    private void addBlockInfoInAppHealth(@NonNull BlockInfo blockInfo) {
        try {
            String activityName = a.b().getClass().getCanonicalName();
            AppHealthInfo.DataBean.BlockBean blockBean = new AppHealthInfo.DataBean.BlockBean();
            blockBean.setPage(activityName);
            blockBean.setBlockTime(blockInfo.timeCost);
            blockBean.setDetail(blockInfo.toString());
            AppHealthInfoUtil.getInstance().addBlockInfo(blockBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyBlockEvent(BlockInfo blockInfo) {
        blockInfo.concernStackString = BlockCanaryUtils.concernStackString(this.mContext, blockInfo);
        blockInfo.time = System.currentTimeMillis();
        if (!TextUtils.isEmpty(blockInfo.concernStackString)) {
            if (DokitConstant.APP_HEALTH_RUNNING && !Debug.isDebuggerConnected()) {
                addBlockInfoInAppHealth(blockInfo);
            }
            showNotification(blockInfo);
            if (this.mBlockInfoList.size() > 50) {
                this.mBlockInfoList.remove(0);
            }
            this.mBlockInfoList.add(blockInfo);
            OnBlockInfoUpdateListener onBlockInfoUpdateListener = this.mOnBlockInfoUpdateListener;
            if (onBlockInfoUpdateListener != null) {
                onBlockInfoUpdateListener.onBlockInfoUpdate(blockInfo);
            }
        }
    }

    private void showNotification(BlockInfo info) {
        String contentTitle = this.mContext.getString(R.string.dk_block_class_has_blocked, new Object[]{info.timeStart});
        String contentText = this.mContext.getString(R.string.dk_block_notification_message);
        Intent intent = new Intent(this.mContext, UniversalActivity.class);
        intent.setFlags(335544320);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, 8);
        intent.putExtra(BlockMonitorFragment.KEY_JUMP_TO_LIST, true);
        Context context = this.mContext;
        PushAutoTrackHelper.hookIntentGetActivity(context, 1, intent, 134217728);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 134217728);
        PushAutoTrackHelper.hookPendingIntentGetActivity(pendingIntent, context, 1, intent, 134217728);
        NotificationUtils.setInfoNotification(this.mContext, 1001, contentTitle, contentText, contentText, pendingIntent);
    }

    public List<BlockInfo> getBlockInfoList() {
        return this.mBlockInfoList;
    }
}
