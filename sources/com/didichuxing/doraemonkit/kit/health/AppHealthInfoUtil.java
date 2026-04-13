package com.didichuxing.doraemonkit.kit.health;

import com.blankj.utilcode.util.d;
import com.blankj.utilcode.util.d0;
import com.blankj.utilcode.util.e0;
import com.blankj.utilcode.util.g;
import com.blankj.utilcode.util.k;
import com.didichuxing.doraemonkit.config.CrashCaptureConfig;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.blockmonitor.core.BlockMonitorManager;
import com.didichuxing.doraemonkit.kit.crash.CrashCaptureManager;
import com.didichuxing.doraemonkit.kit.health.model.AppHealthInfo;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.kit.performance.PerformanceDataManager;
import com.didichuxing.doraemonkit.okgo.DokitOkGo;
import com.didichuxing.doraemonkit.okgo.callback.StringCallback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.PostRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppHealthInfoUtil {
    private static String TAG = "AppHealthInfoUtil";
    private AppHealthInfo mAppHealthInfo = new AppHealthInfo();

    public static class Holder {
        /* access modifiers changed from: private */
        public static AppHealthInfoUtil INSTANCE = new AppHealthInfoUtil();

        private Holder() {
        }
    }

    public static AppHealthInfoUtil getInstance() {
        return Holder.INSTANCE;
    }

    /* access modifiers changed from: package-private */
    public void setBaseInfo(String caseName, String testPerson) {
        AppHealthInfo.BaseInfoBean baseInfoBean = new AppHealthInfo.BaseInfoBean();
        baseInfoBean.setTestPerson(testPerson);
        baseInfoBean.setCaseName(caseName);
        baseInfoBean.setAppName(d.a());
        baseInfoBean.setAppVersion(d.n());
        baseInfoBean.setDokitVersion("3.2.0");
        baseInfoBean.setPlatform("Android");
        baseInfoBean.setPhoneMode(g.j());
        baseInfoBean.setTime(d0.c());
        baseInfoBean.setSystemVersion(g.k());
        baseInfoBean.setpId("" + DokitConstant.PRODUCT_ID);
        this.mAppHealthInfo.setBaseInfo(baseInfoBean);
    }

    public void setAppStartInfo(long costTime, String costDetail, List<AppHealthInfo.DataBean.AppStartBean.LoadFuncBean> loadFunc) {
        AppHealthInfo.DataBean.AppStartBean appStartBean = new AppHealthInfo.DataBean.AppStartBean();
        appStartBean.setCostTime(costTime);
        appStartBean.setCostDetail(costDetail);
        appStartBean.setLoadFunc(loadFunc);
        getData().setAppStart(appStartBean);
    }

    public void addCPUInfo(AppHealthInfo.DataBean.PerformanceBean cpuBean) {
        List<AppHealthInfo.DataBean.PerformanceBean> cpus = getData().getCpu();
        if (cpus == null) {
            cpus = new ArrayList<>();
            getData().setCpu(cpus);
        }
        cpus.add(cpuBean);
    }

    public void addMemoryInfo(AppHealthInfo.DataBean.PerformanceBean memoryBean) {
        List<AppHealthInfo.DataBean.PerformanceBean> memories = getData().getMemory();
        if (memories == null) {
            memories = new ArrayList<>();
            getData().setMemory(memories);
        }
        memories.add(memoryBean);
    }

    public void addFPSInfo(AppHealthInfo.DataBean.PerformanceBean fpsBean) {
        List<AppHealthInfo.DataBean.PerformanceBean> fpsBeans = getData().getFps();
        if (fpsBeans == null) {
            fpsBeans = new ArrayList<>();
            getData().setFps(fpsBeans);
        }
        fpsBeans.add(fpsBean);
    }

    public AppHealthInfo.DataBean.PerformanceBean getLastPerformanceInfo(int performanceType) {
        List<AppHealthInfo.DataBean.PerformanceBean> performanceBeans = null;
        if (performanceType == 1) {
            performanceBeans = getData().getCpu();
        } else if (performanceType == 2) {
            performanceBeans = getData().getMemory();
        } else if (performanceType == 3) {
            performanceBeans = getData().getFps();
        }
        if (performanceBeans == null || performanceBeans.size() == 0) {
            return null;
        }
        return performanceBeans.get(performanceBeans.size() - 1);
    }

    public void removeLastPerformanceInfo(int performanceType) {
        List<AppHealthInfo.DataBean.PerformanceBean> performanceBeans = null;
        if (performanceType == 1) {
            performanceBeans = getData().getCpu();
        } else if (performanceType == 2) {
            performanceBeans = getData().getMemory();
        } else if (performanceType == 3) {
            performanceBeans = getData().getFps();
        }
        if (performanceBeans != null && performanceBeans.size() > 0) {
            performanceBeans.remove(performanceBeans.size() - 1);
        }
    }

    public void addNetWorkInfo(AppHealthInfo.DataBean.NetworkBean networkBean) {
        List<AppHealthInfo.DataBean.NetworkBean> networks = getData().getNetwork();
        if (networks == null) {
            networks = new ArrayList<>();
            getData().setNetwork(networks);
        }
        networks.add(networkBean);
    }

    public AppHealthInfo.DataBean.NetworkBean getNetWorkInfo(String activityName) {
        List<AppHealthInfo.DataBean.NetworkBean> networks = getData().getNetwork();
        if (networks == null || networks.size() == 0) {
            return null;
        }
        for (AppHealthInfo.DataBean.NetworkBean traverseNetworkBean : networks) {
            if (traverseNetworkBean.getPage().equals(activityName)) {
                return traverseNetworkBean;
            }
        }
        return null;
    }

    public void addBlockInfo(AppHealthInfo.DataBean.BlockBean blockBean) {
        List<AppHealthInfo.DataBean.BlockBean> blocks = getData().getBlock();
        if (blocks == null) {
            blocks = new ArrayList<>();
            getData().setBlock(blocks);
        }
        blocks.add(blockBean);
    }

    public void addUiLevelInfo(AppHealthInfo.DataBean.UiLevelBean uiLevelBean) {
        List<AppHealthInfo.DataBean.UiLevelBean> uiLevels = getData().getUiLevel();
        if (uiLevels == null) {
            uiLevels = new ArrayList<>();
            getData().setUiLevel(uiLevels);
        }
        uiLevels.add(uiLevelBean);
    }

    public void addLeakInfo(AppHealthInfo.DataBean.LeakBean leakBean) {
        List<AppHealthInfo.DataBean.LeakBean> leaks = getData().getLeak();
        if (leaks == null) {
            leaks = new ArrayList<>();
            getData().setLeak(leaks);
        }
        leaks.add(leakBean);
    }

    public void addPageLoadInfo(AppHealthInfo.DataBean.PageLoadBean pageLoadBean) {
        List<AppHealthInfo.DataBean.PageLoadBean> pageloads = getData().getPageLoad();
        if (pageloads == null) {
            pageloads = new ArrayList<>();
            getData().setPageLoad(pageloads);
        }
        pageloads.add(pageLoadBean);
    }

    public void addBigFilrInfo(AppHealthInfo.DataBean.BigFileBean bigFileBean) {
        List<AppHealthInfo.DataBean.BigFileBean> bigFiles = getData().getBigFile();
        if (bigFiles == null) {
            bigFiles = new ArrayList<>();
            getData().setBigFile(bigFiles);
        }
        bigFiles.add(bigFileBean);
    }

    public void post(final UploadAppHealthCallback uploadAppHealthCallBack) {
        if (this.mAppHealthInfo != null) {
            ((PostRequest) DokitOkGo.post(NetworkManager.APP_HEALTH_URL).upJson(k.j(this.mAppHealthInfo))).execute(new StringCallback() {
                public void onSuccess(Response<String> response) {
                    UploadAppHealthCallback uploadAppHealthCallback = uploadAppHealthCallBack;
                    if (uploadAppHealthCallback != null) {
                        uploadAppHealthCallback.onSuccess(response);
                    }
                }

                public void onError(Response<String> response) {
                    super.onError(response);
                    UploadAppHealthCallback uploadAppHealthCallback = uploadAppHealthCallBack;
                    if (uploadAppHealthCallback != null) {
                        uploadAppHealthCallback.onError(response);
                    }
                }
            });
        }
    }

    private AppHealthInfo.DataBean getData() {
        if (this.mAppHealthInfo.getData() == null) {
            AppHealthInfo.DataBean dataBean = new AppHealthInfo.DataBean();
            dataBean.setCpu(new ArrayList());
            dataBean.setMemory(new ArrayList());
            dataBean.setFps(new ArrayList());
            dataBean.setNetwork(new ArrayList());
            dataBean.setBlock(new ArrayList());
            dataBean.setUiLevel(new ArrayList());
            dataBean.setLeak(new ArrayList());
            dataBean.setPageLoad(new ArrayList());
            dataBean.setBigFile(new ArrayList());
            dataBean.setSubThreadUI(new ArrayList());
            this.mAppHealthInfo.setData(dataBean);
        }
        return this.mAppHealthInfo.getData();
    }

    public boolean isAppHealthRunning() {
        boolean isRunning = DokitConstant.APP_HEALTH_RUNNING;
        if (isRunning) {
            e0.n("App当前处于健康体检状态,无法进行此操作");
        }
        return isRunning;
    }

    public void start() {
        PerformanceDataManager.getInstance().init();
        PerformanceDataManager.getInstance().startMonitorFrameInfo();
        PerformanceDataManager.getInstance().startMonitorCPUInfo();
        PerformanceDataManager.getInstance().startMonitorMemoryInfo();
        PerformanceDataManager.getInstance().startMonitorNetFlowInfo();
        BlockMonitorManager.getInstance().start();
        CrashCaptureConfig.setCrashCaptureOpen(true);
        CrashCaptureManager.getInstance().start();
    }

    public void stop() {
        PerformanceDataManager.getInstance().stopMonitorFrameInfo();
        PerformanceDataManager.getInstance().stopMonitorCPUInfo();
        PerformanceDataManager.getInstance().stopMonitorMemoryInfo();
        PerformanceDataManager.getInstance().stopMonitorNetFlowInfo();
        BlockMonitorManager.getInstance().stop();
        CrashCaptureConfig.setCrashCaptureOpen(false);
        CrashCaptureManager.getInstance().stop();
    }

    private List<AppHealthInfo.DataBean.PerformanceBean.ValuesBean> sortValue(List<AppHealthInfo.DataBean.PerformanceBean.ValuesBean> valuesBeans) {
        List<AppHealthInfo.DataBean.PerformanceBean.ValuesBean> newValuesBeans = new ArrayList<>(valuesBeans);
        Collections.sort(newValuesBeans, new Comparator<AppHealthInfo.DataBean.PerformanceBean.ValuesBean>() {
            public int compare(AppHealthInfo.DataBean.PerformanceBean.ValuesBean pre, AppHealthInfo.DataBean.PerformanceBean.ValuesBean next) {
                if (Float.parseFloat(pre.getValue()) < Float.parseFloat(next.getValue())) {
                    return -1;
                }
                return 1;
            }
        });
        newValuesBeans.remove(0);
        newValuesBeans.remove(newValuesBeans.size() - 1);
        Collections.sort(newValuesBeans, new Comparator<AppHealthInfo.DataBean.PerformanceBean.ValuesBean>() {
            public int compare(AppHealthInfo.DataBean.PerformanceBean.ValuesBean pre, AppHealthInfo.DataBean.PerformanceBean.ValuesBean next) {
                if (Long.parseLong(pre.getTime()) < Long.parseLong(next.getTime())) {
                    return -1;
                }
                return 1;
            }
        });
        return newValuesBeans;
    }

    public void release() {
        if (this.mAppHealthInfo != null) {
            this.mAppHealthInfo = null;
        }
    }
}
