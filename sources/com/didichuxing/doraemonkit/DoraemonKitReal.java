package com.didichuxing.doraemonkit;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.a;
import com.blankj.utilcode.util.b0;
import com.blankj.utilcode.util.f;
import com.blankj.utilcode.util.f0;
import com.blankj.utilcode.util.i;
import com.blankj.utilcode.util.j;
import com.blankj.utilcode.util.q;
import com.blankj.utilcode.util.u;
import com.didichuxing.doraemonkit.aop.OkHttpHook;
import com.didichuxing.doraemonkit.config.GlobalConfig;
import com.didichuxing.doraemonkit.config.GpsMockConfig;
import com.didichuxing.doraemonkit.config.PerformanceSpInfoConfig;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.constant.SharedPrefsKey;
import com.didichuxing.doraemonkit.datapick.DataPickManager;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.alignruler.AlignRulerKit;
import com.didichuxing.doraemonkit.kit.blockmonitor.BlockMonitorKit;
import com.didichuxing.doraemonkit.kit.colorpick.ColorPickerKit;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.didichuxing.doraemonkit.kit.crash.CrashCaptureKit;
import com.didichuxing.doraemonkit.kit.dataclean.DataCleanKit;
import com.didichuxing.doraemonkit.kit.dbdebug.DbDebugKit;
import com.didichuxing.doraemonkit.kit.fileexplorer.FileExplorerKit;
import com.didichuxing.doraemonkit.kit.filemanager.FileTransferKit;
import com.didichuxing.doraemonkit.kit.gpsmock.GpsMockKit;
import com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager;
import com.didichuxing.doraemonkit.kit.gpsmock.ServiceHookManager;
import com.didichuxing.doraemonkit.kit.health.AppHealthInfoUtil;
import com.didichuxing.doraemonkit.kit.health.HealthKit;
import com.didichuxing.doraemonkit.kit.health.model.AppHealthInfo;
import com.didichuxing.doraemonkit.kit.largepicture.LargePictureKit;
import com.didichuxing.doraemonkit.kit.layoutborder.LayoutBorderKit;
import com.didichuxing.doraemonkit.kit.loginfo.LogInfoKit;
import com.didichuxing.doraemonkit.kit.methodtrace.MethodCostKit;
import com.didichuxing.doraemonkit.kit.network.MockKit;
import com.didichuxing.doraemonkit.kit.network.NetworkKit;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.kit.parameter.cpu.CpuKit;
import com.didichuxing.doraemonkit.kit.parameter.frameInfo.FrameInfoKit;
import com.didichuxing.doraemonkit.kit.parameter.ram.RamKit;
import com.didichuxing.doraemonkit.kit.sysinfo.DevelopmentPageKit;
import com.didichuxing.doraemonkit.kit.sysinfo.LocalLangKit;
import com.didichuxing.doraemonkit.kit.sysinfo.SysInfoKit;
import com.didichuxing.doraemonkit.kit.timecounter.TimeCounterKit;
import com.didichuxing.doraemonkit.kit.timecounter.instrumentation.HandlerHooker;
import com.didichuxing.doraemonkit.kit.toolpanel.KitBean;
import com.didichuxing.doraemonkit.kit.toolpanel.KitGroupBean;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.didichuxing.doraemonkit.kit.toolpanel.ToolPanelUtil;
import com.didichuxing.doraemonkit.kit.uiperformance.UIPerformanceKit;
import com.didichuxing.doraemonkit.kit.viewcheck.ViewCheckerKit;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkKit;
import com.didichuxing.doraemonkit.kit.webdoor.WebDoorKit;
import com.didichuxing.doraemonkit.kit.webdoor.WebDoorManager;
import com.didichuxing.doraemonkit.model.LatLng;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.util.DoraemonStatisticsUtil;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.util.SharedPrefsUtil;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import meshsdk.model.GroupInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DoraemonKitReal.kt */
public final class DoraemonKitReal {
    /* access modifiers changed from: private */
    public static Application APPLICATION = null;
    private static final long FILE_LENGTH_THRESHOLD = 1048576;
    public static final DoraemonKitReal INSTANCE = new DoraemonKitReal();
    private static final String TAG = "Doraemon";
    private static boolean sEnableUpload = true;

    private DoraemonKitReal() {
    }

    public final void setDebug(boolean debug) {
        LogHelper.setDebug(debug);
    }

    public final void install(@NotNull Application app, @NotNull LinkedHashMap<String, List<AbstractKit>> mapKits, @NotNull List<AbstractKit> listKits, @NotNull String productId) {
        Application application = app;
        String str = productId;
        k.f(application, "app");
        k.f(mapKits, "mapKits");
        k.f(listKits, "listKits");
        k.f(str, "productId");
        pluginConfig();
        DokitConstant.PRODUCT_ID = str;
        DokitConstant.APP_HEALTH_RUNNING = GlobalConfig.getAppHealth();
        APPLICATION = application;
        initAndroidUtil(app);
        if (u.e()) {
            DokitConstant.IS_NORMAL_FLOAT_MODE = k.a(SharedPrefsUtil.getString(SharedPrefsKey.FLOAT_START_MODE, GroupInfo.TYPE_NORMAL), GroupInfo.TYPE_NORMAL);
            installLeakCanary(app);
            checkLargeImgIsOpen();
            registerNetworkStatusChangedListener();
            startAppHealth();
            checkGPSMock();
            HandlerHooker.doHook(app);
            ServiceHookManager.getInstance().install(application);
            OkHttpHook.installInterceptor();
            application.registerActivityLifecycleCallbacks(new DokitActivityLifecycleCallbacks());
            DokitConstant.GLOBAL_KITS.clear();
            int i = 10;
            if (!mapKits.isEmpty()) {
                Map $this$forEach$iv = mapKits;
                for (Map.Entry map : $this$forEach$iv.entrySet()) {
                    Iterable<AbstractKit> $this$map$iv = (Iterable) map.getValue();
                    Map $this$forEach$iv2 = $this$forEach$iv;
                    Collection destination$iv$iv = new ArrayList(r.r($this$map$iv, i));
                    for (AbstractKit it : $this$map$iv) {
                        String string = DokitUtil.getString(it.getName());
                        k.b(string, "DokitUtil.getString(it.name)");
                        destination$iv$iv.add(new KitWrapItem(201, string, true, (String) map.getKey(), it));
                        String str2 = productId;
                    }
                    DokitConstant.GLOBAL_KITS.put(map.getKey(), e0.b(destination$iv$iv));
                    String str3 = productId;
                    $this$forEach$iv = $this$forEach$iv2;
                    i = 10;
                }
            } else if (mapKits.isEmpty() && (!listKits.isEmpty())) {
                Iterable<AbstractKit> $this$mapTo$iv$iv = listKits;
                Collection destination$iv$iv2 = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                for (AbstractKit it2 : $this$mapTo$iv$iv) {
                    String string2 = DokitUtil.getString(it2.getName());
                    k.b(string2, "DokitUtil.getString(it.name)");
                    String string3 = DokitUtil.getString(R.string.dk_category_biz);
                    k.b(string3, "DokitUtil.getString(R.string.dk_category_biz)");
                    KitWrapItem kitWrapItem = r13;
                    KitWrapItem kitWrapItem2 = new KitWrapItem(201, string2, true, string3, it2);
                    destination$iv$iv2.add(kitWrapItem);
                }
                List kitWraps = e0.b(destination$iv$iv2);
                LinkedHashMap<String, List<KitWrapItem>> linkedHashMap = DokitConstant.GLOBAL_KITS;
                String string4 = DokitUtil.getString(R.string.dk_category_biz);
                k.b(string4, "DokitUtil.getString(R.string.dk_category_biz)");
                linkedHashMap.put(string4, kitWraps);
            }
            b0.g(new DoraemonKitReal$install$2(application));
            DokitViewManager.getInstance().init(application);
            if (sEnableUpload) {
                try {
                    DoraemonStatisticsUtil.uploadUserInfo(app);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            DataPickManager.getInstance().postData();
        }
    }

    /* access modifiers changed from: private */
    public final void addInnerKit(Application application) {
        String json;
        DokitConstant dokitConstant = DokitConstant.INSTANCE;
        if (j.z(dokitConstant.getSYSTEM_KITS_BAK_PATH())) {
            json = i.d(dokitConstant.getSYSTEM_KITS_BAK_PATH());
            if (TextUtils.isEmpty(json) || k.a(json, "[]")) {
                InputStream open = application.getAssets().open("dokit_system_kits.json");
                k.b(open, "application.assets.open(\"dokit_system_kits.json\")");
                json = f.h(open, "UTF-8");
            }
        } else {
            InputStream open2 = application.getAssets().open("dokit_system_kits.json");
            k.b(open2, "application.assets.open(\"dokit_system_kits.json\")");
            json = f.h(open2, "UTF-8");
        }
        ToolPanelUtil.Companion companion = ToolPanelUtil.Companion;
        k.b(json, "json");
        companion.jsonConfig2InnerKits(json);
        Map $this$forEach$iv = DokitConstant.GLOBAL_KITS;
        String string = DokitUtil.getString(R.string.dk_category_mode);
        k.b(string, "DokitUtil.getString(R.string.dk_category_mode)");
        $this$forEach$iv.put(string, new ArrayList());
        String string2 = DokitUtil.getString(R.string.dk_category_exit);
        k.b(string2, "DokitUtil.getString(R.string.dk_category_exit)");
        $this$forEach$iv.put(string2, new ArrayList());
        String string3 = DokitUtil.getString(R.string.dk_category_version);
        k.b(string3, "DokitUtil.getString(R.string.dk_category_version)");
        $this$forEach$iv.put(string3, new ArrayList());
        for (Map.Entry<String, List<KitWrapItem>> element$iv : $this$forEach$iv.entrySet()) {
            for (KitWrapItem kitWrap : (Iterable) element$iv.getValue()) {
                AbstractKit kit = kitWrap.getKit();
                if (kit != null) {
                    kit.onAppInit(application);
                }
            }
        }
    }

    private final void addSystemKit4Test(Application application) {
        List platformKits = new ArrayList();
        MockKit mockKit = new MockKit();
        String string = DokitUtil.getString(mockKit.getName());
        k.b(string, "DokitUtil.getString(mockKit.name)");
        platformKits.add(new KitWrapItem(201, string, true, DokitConstant.GROUP_ID_PLATFORM, mockKit));
        HealthKit healKit = new HealthKit();
        String string2 = DokitUtil.getString(healKit.getName());
        k.b(string2, "DokitUtil.getString(healKit.name)");
        platformKits.add(new KitWrapItem(201, string2, true, DokitConstant.GROUP_ID_PLATFORM, healKit));
        FileTransferKit fileSyncKit = new FileTransferKit();
        String string3 = DokitUtil.getString(fileSyncKit.getName());
        k.b(string3, "DokitUtil.getString(fileSyncKit.name)");
        platformKits.add(new KitWrapItem(201, string3, true, DokitConstant.GROUP_ID_PLATFORM, fileSyncKit));
        LinkedHashMap<String, List<KitWrapItem>> linkedHashMap = DokitConstant.GLOBAL_KITS;
        linkedHashMap.put(DokitConstant.GROUP_ID_PLATFORM, platformKits);
        List commKits = new ArrayList();
        SysInfoKit sysInfoKit = new SysInfoKit();
        String string4 = DokitUtil.getString(sysInfoKit.getName());
        k.b(string4, "DokitUtil.getString(sysInfoKit.name)");
        commKits.add(new KitWrapItem(201, string4, true, DokitConstant.GROUP_ID_COMM, sysInfoKit));
        DevelopmentPageKit developmentPageKit = new DevelopmentPageKit();
        String string5 = DokitUtil.getString(developmentPageKit.getName());
        k.b(string5, "DokitUtil.getString(developmentPageKit.name)");
        commKits.add(new KitWrapItem(201, string5, true, DokitConstant.GROUP_ID_COMM, developmentPageKit));
        LocalLangKit localLangKit = new LocalLangKit();
        String string6 = DokitUtil.getString(localLangKit.getName());
        k.b(string6, "DokitUtil.getString(localLangKit.name)");
        commKits.add(new KitWrapItem(201, string6, true, DokitConstant.GROUP_ID_COMM, localLangKit));
        FileExplorerKit fileExplorerKit = new FileExplorerKit();
        String string7 = DokitUtil.getString(fileExplorerKit.getName());
        k.b(string7, "DokitUtil.getString(fileExplorerKit.name)");
        commKits.add(new KitWrapItem(201, string7, true, DokitConstant.GROUP_ID_COMM, fileExplorerKit));
        GpsMockKit gpsMockKit = new GpsMockKit();
        String string8 = DokitUtil.getString(gpsMockKit.getName());
        k.b(string8, "DokitUtil.getString(gpsMockKit.name)");
        commKits.add(new KitWrapItem(201, string8, true, DokitConstant.GROUP_ID_COMM, gpsMockKit));
        WebDoorKit webDoorKit = new WebDoorKit();
        String string9 = DokitUtil.getString(webDoorKit.getName());
        k.b(string9, "DokitUtil.getString(webDoorKit.name)");
        commKits.add(new KitWrapItem(201, string9, true, DokitConstant.GROUP_ID_COMM, webDoorKit));
        DataCleanKit dataCleanKit = new DataCleanKit();
        String string10 = DokitUtil.getString(dataCleanKit.getName());
        k.b(string10, "DokitUtil.getString(dataCleanKit.name)");
        commKits.add(new KitWrapItem(201, string10, true, DokitConstant.GROUP_ID_COMM, dataCleanKit));
        LogInfoKit logInfoKit = new LogInfoKit();
        String string11 = DokitUtil.getString(logInfoKit.getName());
        k.b(string11, "DokitUtil.getString(logInfoKit.name)");
        commKits.add(new KitWrapItem(201, string11, true, DokitConstant.GROUP_ID_COMM, logInfoKit));
        DbDebugKit dbDebugKit = new DbDebugKit();
        String string12 = DokitUtil.getString(dbDebugKit.getName());
        k.b(string12, "DokitUtil.getString(dbDebugKit.name)");
        commKits.add(new KitWrapItem(201, string12, true, DokitConstant.GROUP_ID_COMM, dbDebugKit));
        linkedHashMap.put(DokitConstant.GROUP_ID_COMM, commKits);
        List weexKits = new ArrayList();
        try {
            Object newInstance = Class.forName("com.didichuxing.doraemonkit.weex.log.WeexLogKit").newInstance();
            if (newInstance != null) {
                AbstractKit weexLogKit = (AbstractKit) newInstance;
                String string13 = DokitUtil.getString(weexLogKit.getName());
                k.b(string13, "DokitUtil.getString(weexLogKit.name)");
                commKits.add(new KitWrapItem(201, string13, true, DokitConstant.GROUP_ID_WEEX, weexLogKit));
                Object newInstance2 = Class.forName("com.didichuxing.doraemonkit.weex.storage.WeexStorageKit").newInstance();
                if (newInstance2 != null) {
                    AbstractKit storageKit = (AbstractKit) newInstance2;
                    String string14 = DokitUtil.getString(storageKit.getName());
                    k.b(string14, "DokitUtil.getString(storageKit.name)");
                    commKits.add(new KitWrapItem(201, string14, true, DokitConstant.GROUP_ID_WEEX, storageKit));
                    Object newInstance3 = Class.forName("com.didichuxing.doraemonkit.weex.info.WeexInfoKit").newInstance();
                    if (newInstance3 != null) {
                        AbstractKit weexInfoKit = (AbstractKit) newInstance3;
                        String string15 = DokitUtil.getString(weexInfoKit.getName());
                        k.b(string15, "DokitUtil.getString(weexInfoKit.name)");
                        commKits.add(new KitWrapItem(201, string15, true, DokitConstant.GROUP_ID_WEEX, weexInfoKit));
                        Object newInstance4 = Class.forName("com.didichuxing.doraemonkit.weex.devtool.WeexDevToolKit").newInstance();
                        if (newInstance4 != null) {
                            AbstractKit devToolKit = (AbstractKit) newInstance4;
                            String string16 = DokitUtil.getString(devToolKit.getName());
                            k.b(string16, "DokitUtil.getString(devToolKit.name)");
                            commKits.add(new KitWrapItem(201, string16, true, DokitConstant.GROUP_ID_WEEX, devToolKit));
                            linkedHashMap.put(DokitConstant.GROUP_ID_WEEX, weexKits);
                            List performanceKits = new ArrayList();
                            FrameInfoKit frameInfoKit = new FrameInfoKit();
                            String string17 = DokitUtil.getString(frameInfoKit.getName());
                            k.b(string17, "DokitUtil.getString(frameInfoKit.name)");
                            performanceKits.add(new KitWrapItem(201, string17, true, DokitConstant.GROUP_ID_PERFORMANCE, frameInfoKit));
                            CpuKit cpuKit = new CpuKit();
                            String string18 = DokitUtil.getString(cpuKit.getName());
                            k.b(string18, "DokitUtil.getString(cpuKit.name)");
                            performanceKits.add(new KitWrapItem(201, string18, true, DokitConstant.GROUP_ID_PERFORMANCE, cpuKit));
                            RamKit ramKit = new RamKit();
                            String string19 = DokitUtil.getString(ramKit.getName());
                            k.b(string19, "DokitUtil.getString(ramKit.name)");
                            performanceKits.add(new KitWrapItem(201, string19, true, DokitConstant.GROUP_ID_PERFORMANCE, ramKit));
                            NetworkKit networkKit = new NetworkKit();
                            String string20 = DokitUtil.getString(networkKit.getName());
                            k.b(string20, "DokitUtil.getString(networkKit.name)");
                            performanceKits.add(new KitWrapItem(201, string20, true, DokitConstant.GROUP_ID_PERFORMANCE, networkKit));
                            CrashCaptureKit crashCaptureKit = new CrashCaptureKit();
                            String string21 = DokitUtil.getString(crashCaptureKit.getName());
                            k.b(string21, "DokitUtil.getString(crashCaptureKit.name)");
                            performanceKits.add(new KitWrapItem(201, string21, true, DokitConstant.GROUP_ID_PERFORMANCE, crashCaptureKit));
                            BlockMonitorKit blockMonitorKit = new BlockMonitorKit();
                            String string22 = DokitUtil.getString(blockMonitorKit.getName());
                            k.b(string22, "DokitUtil.getString(blockMonitorKit.name)");
                            performanceKits.add(new KitWrapItem(201, string22, true, DokitConstant.GROUP_ID_PERFORMANCE, blockMonitorKit));
                            LargePictureKit largePictureKit = new LargePictureKit();
                            String string23 = DokitUtil.getString(largePictureKit.getName());
                            k.b(string23, "DokitUtil.getString(largePictureKit.name)");
                            performanceKits.add(new KitWrapItem(201, string23, true, DokitConstant.GROUP_ID_PERFORMANCE, largePictureKit));
                            WeakNetworkKit weakNetworkKit = new WeakNetworkKit();
                            String string24 = DokitUtil.getString(weakNetworkKit.getName());
                            k.b(string24, "DokitUtil.getString(weakNetworkKit.name)");
                            performanceKits.add(new KitWrapItem(201, string24, true, DokitConstant.GROUP_ID_PERFORMANCE, weakNetworkKit));
                            TimeCounterKit timeCounterKit = new TimeCounterKit();
                            String string25 = DokitUtil.getString(timeCounterKit.getName());
                            k.b(string25, "DokitUtil.getString(timeCounterKit.name)");
                            performanceKits.add(new KitWrapItem(201, string25, true, DokitConstant.GROUP_ID_PERFORMANCE, timeCounterKit));
                            UIPerformanceKit uiPerformanceKit = new UIPerformanceKit();
                            String string26 = DokitUtil.getString(uiPerformanceKit.getName());
                            k.b(string26, "DokitUtil.getString(uiPerformanceKit.name)");
                            performanceKits.add(new KitWrapItem(201, string26, true, DokitConstant.GROUP_ID_PERFORMANCE, uiPerformanceKit));
                            MethodCostKit methodCostKit = new MethodCostKit();
                            String string27 = DokitUtil.getString(methodCostKit.getName());
                            k.b(string27, "DokitUtil.getString(methodCostKit.name)");
                            performanceKits.add(new KitWrapItem(201, string27, true, DokitConstant.GROUP_ID_PERFORMANCE, methodCostKit));
                            try {
                                Object newInstance5 = Class.forName("com.didichuxing.doraemonkit.kit.leakcanary.LeakCanaryKit").newInstance();
                                if (newInstance5 != null) {
                                    AbstractKit leakCanaryKit = (AbstractKit) newInstance5;
                                    String string28 = DokitUtil.getString(leakCanaryKit.getName());
                                    k.b(string28, "DokitUtil.getString(leakCanaryKit.name)");
                                    performanceKits.add(new KitWrapItem(201, string28, true, DokitConstant.GROUP_ID_PERFORMANCE, leakCanaryKit));
                                    LinkedHashMap<String, List<KitWrapItem>> linkedHashMap2 = DokitConstant.GLOBAL_KITS;
                                    linkedHashMap2.put(DokitConstant.GROUP_ID_PERFORMANCE, performanceKits);
                                    List uiKits = new ArrayList();
                                    if (Build.VERSION.SDK_INT >= 21) {
                                        ColorPickerKit colorPickerKit = new ColorPickerKit();
                                        String string29 = DokitUtil.getString(colorPickerKit.getName());
                                        k.b(string29, "DokitUtil.getString(colorPickerKit.name)");
                                        uiKits.add(new KitWrapItem(201, string29, true, DokitConstant.GROUP_ID_UI, colorPickerKit));
                                    }
                                    AlignRulerKit alignRulerKit = new AlignRulerKit();
                                    String string30 = DokitUtil.getString(alignRulerKit.getName());
                                    k.b(string30, "DokitUtil.getString(alignRulerKit.name)");
                                    uiKits.add(new KitWrapItem(201, string30, true, DokitConstant.GROUP_ID_UI, alignRulerKit));
                                    ViewCheckerKit viewCheckerKit = new ViewCheckerKit();
                                    String string31 = DokitUtil.getString(viewCheckerKit.getName());
                                    k.b(string31, "DokitUtil.getString(viewCheckerKit.name)");
                                    uiKits.add(new KitWrapItem(201, string31, true, DokitConstant.GROUP_ID_UI, viewCheckerKit));
                                    LayoutBorderKit layoutBorderKit = new LayoutBorderKit();
                                    String string32 = DokitUtil.getString(layoutBorderKit.getName());
                                    k.b(string32, "DokitUtil.getString(layoutBorderKit.name)");
                                    uiKits.add(new KitWrapItem(201, string32, true, DokitConstant.GROUP_ID_UI, layoutBorderKit));
                                    linkedHashMap2.put(DokitConstant.GROUP_ID_UI, uiKits);
                                    convert2json();
                                    return;
                                }
                                throw new TypeCastException("null cannot be cast to non-null type com.didichuxing.doraemonkit.kit.AbstractKit");
                            } catch (Exception e) {
                            }
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type com.didichuxing.doraemonkit.kit.AbstractKit");
                        }
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type com.didichuxing.doraemonkit.kit.AbstractKit");
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type com.didichuxing.doraemonkit.kit.AbstractKit");
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type com.didichuxing.doraemonkit.kit.AbstractKit");
            }
        } catch (Exception e2) {
        }
    }

    private final void convert2json() {
        List localKits = new ArrayList();
        Map $this$forEach$iv = DokitConstant.GLOBAL_KITS;
        int $i$f$forEach = false;
        Iterator<Map.Entry<String, List<KitWrapItem>>> it = $this$forEach$iv.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry map = it.next();
            KitGroupBean kitGroupBean = new KitGroupBean((String) map.getKey(), new ArrayList());
            localKits.add(kitGroupBean);
            for (KitWrapItem kitWrap : (Iterable) map.getValue()) {
                List<KitBean> kits = kitGroupBean.getKits();
                AbstractKit kit = kitWrap.getKit();
                if (kit == null) {
                    k.n();
                }
                Map $this$forEach$iv2 = $this$forEach$iv;
                String canonicalName = kit.getClass().getCanonicalName();
                if (canonicalName == null) {
                    k.n();
                }
                k.b(canonicalName, "kitWrap.kit!!::class.java.canonicalName!!");
                kits.add(new KitBean(canonicalName, true, kitWrap.getKit().innerKitId()));
                $i$f$forEach = $i$f$forEach;
                $this$forEach$iv = $this$forEach$iv2;
                it = it;
            }
            int i = $i$f$forEach;
            Iterator<Map.Entry<String, List<KitWrapItem>>> it2 = it;
        }
        int i2 = $i$f$forEach;
        LogHelper.i(TAG, com.blankj.utilcode.util.k.j(localKits));
    }

    private final void pluginConfig() {
    }

    private final void checkGPSMock() {
        if (GpsMockConfig.isGPSMockOpen()) {
            GpsMockManager.getInstance().startMock();
        }
        LatLng latLng = GpsMockConfig.getMockLocation();
        if (latLng != null) {
            GpsMockManager.getInstance().mockLocation(latLng.latitude, latLng.longitude);
        }
    }

    /* access modifiers changed from: private */
    public final void traverseFile(File rootFileDir) {
        File[] files;
        if (rootFileDir != null && (files = rootFileDir.listFiles()) != null) {
            for (File file : files) {
                k.b(file, "file");
                if (file.isDirectory()) {
                    INSTANCE.traverseFile(file);
                }
                if (file.isFile()) {
                    long fileLength = j.t(file);
                    if (fileLength > 1048576) {
                        AppHealthInfo.DataBean.BigFileBean fileBean = new AppHealthInfo.DataBean.BigFileBean();
                        fileBean.setFileName(j.r(file));
                        fileBean.setFilePath(file.getAbsolutePath());
                        fileBean.setFileSize("" + fileLength);
                        AppHealthInfoUtil.getInstance().addBigFilrInfo(fileBean);
                    }
                }
            }
        }
    }

    private final void startBigFileInspect() {
        b0.g(new DoraemonKitReal$startBigFileInspect$1());
    }

    private final void startAppHealth() {
        if (DokitConstant.APP_HEALTH_RUNNING) {
            if (TextUtils.isEmpty(DokitConstant.PRODUCT_ID)) {
                com.blankj.utilcode.util.e0.o("要使用健康体检功能必须先去平台端注册", new Object[0]);
                return;
            }
            AppHealthInfoUtil.getInstance().start();
            startBigFileInspect();
        }
    }

    public final void setWebDoorCallback(@Nullable WebDoorManager.WebDoorCallback callback) {
        WebDoorManager instance = WebDoorManager.getInstance();
        k.b(instance, "WebDoorManager.getInstance()");
        instance.setWebDoorCallback(callback);
    }

    private final void registerNetworkStatusChangedListener() {
        NetworkUtils.registerNetworkStatusChangedListener(new DoraemonKitReal$registerNetworkStatusChangedListener$1());
    }

    private final void checkLargeImgIsOpen() {
        if (PerformanceSpInfoConfig.isLargeImgOpen()) {
            NetworkManager.get().startMonitor();
        }
    }

    private final void installLeakCanary(Application app) {
        try {
            Class leakCanaryManager = Class.forName("com.didichuxing.doraemonkit.LeakCanaryManager");
            k.b(leakCanaryManager, "Class.forName(\"com.didic…onkit.LeakCanaryManager\")");
            Method install = leakCanaryManager.getMethod("install", new Class[]{Application.class});
            k.b(install, "leakCanaryManager.getMet… Application::class.java)");
            install.invoke((Object) null, new Object[]{app});
            Method initAidlBridge = leakCanaryManager.getMethod("initAidlBridge", new Class[]{Application.class});
            k.b(initAidlBridge, "leakCanaryManager.getMet… Application::class.java)");
            initAidlBridge.invoke((Object) null, new Object[]{app});
        } catch (Exception e) {
        }
    }

    private final void initAndroidUtil(Application app) {
        f0.b(app);
        q.d F = q.n().D(true).w(true).A(TAG).C(true).B(true).x("").z("djx-table-log").u(true).E(true).v(2).y(6).F(2);
        k.b(F, "LogUtils.getConfig() // …         .setStackDeep(2)");
        F.G(0);
    }

    private final void showMainIcon() {
        if (!(a.b() instanceof UniversalActivity) && DokitConstant.AWAYS_SHOW_MAIN_ICON) {
            DokitViewManager.getInstance().attachMainIcon();
            DokitConstant.MAIN_ICON_HAS_SHOW = true;
        }
    }

    public final void show() {
        DokitConstant.AWAYS_SHOW_MAIN_ICON = true;
        if (!isShow()) {
            showMainIcon();
        }
    }

    public final void showToolPanel() {
        DokitViewManager.getInstance().attachToolPanel();
    }

    public final void hideToolPanel() {
        DokitViewManager.getInstance().detachToolPanel();
    }

    public final void hide() {
        DokitConstant.MAIN_ICON_HAS_SHOW = false;
        DokitConstant.AWAYS_SHOW_MAIN_ICON = false;
        DokitViewManager.getInstance().detachMainIcon();
    }

    public final void disableUpload() {
        sEnableUpload = false;
    }

    public final boolean isShow() {
        return DokitConstant.MAIN_ICON_HAS_SHOW;
    }

    public final void setDatabasePass(@NotNull Map<String, String> map) {
        k.f(map, "map");
        DokitConstant.INSTANCE.setDATABASE_PASS(map);
    }

    public final void setFileManagerHttpPort(int port) {
        DokitConstant.INSTANCE.setFILE_MANAGER_HTTP_PORT(port);
    }
}
