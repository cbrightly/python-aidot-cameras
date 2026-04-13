package com.sensorsdata.analytics.android.sdk.advert.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.oaid.impl.OAIDFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import meshsdk.cache.CacheHandler;
import meshsdk.model.json.MeshStorage;

public class OaidHelper {
    private static final String TAG = "SA.DeviceUtils";
    private static Class<?> jLibrary;
    private static final List<String> mBlackOAIDs = new LinkedList<String>() {
        {
            add("00000000-0000-0000-0000-000000000000");
            add(MeshStorage.Defaults.KEY_INVALID);
        }
    };
    /* access modifiers changed from: private */
    public static CountDownLatch mCountDownLatch;
    /* access modifiers changed from: private */
    public static Class<?> mIdSupplier;
    private static Class<?> mIdentifyListener;
    private static final List<String> mLoadLibrary = new LinkedList<String>() {
        {
            add("msaoaidsec");
            add("nllvm1632808251147706677");
            add("nllvm1630571663641560568");
            add("nllvm1623827671");
        }
    };
    private static Class<?> mMidSDKHelper;
    /* access modifiers changed from: private */
    public static String mOAID = "";
    private static String mOidCertFilePath;

    static {
        initSDKLibrary();
    }

    public static String getOAID(Context context) {
        String OAID = getMSAOAID(context);
        SALog.i(TAG, "MSA OAID is " + OAID);
        if (TextUtils.isEmpty(OAID)) {
            OAID = getROMOAID(context);
            SALog.i(TAG, "Rom OAID is" + OAID);
        }
        if (TextUtils.isEmpty(OAID) || mBlackOAIDs.contains(OAID)) {
            return "";
        }
        return OAID;
    }

    private static String getROMOAID(Context context) {
        return OAIDFactory.create(context).getRomOAID();
    }

    private static String getMSAOAID(Context context) {
        try {
            mCountDownLatch = new CountDownLatch(1);
            initInvokeListener();
            if (!(mMidSDKHelper == null || mIdentifyListener == null)) {
                if (mIdSupplier != null) {
                    if (!TextUtils.isEmpty(mOAID)) {
                        return mOAID;
                    }
                    getOAIDReflect(context, 2);
                    mCountDownLatch.await();
                    SALog.d(TAG, "CountDownLatch await");
                    return mOAID;
                }
            }
            SALog.d(TAG, "OAID 读取类创建失败");
            return "";
        } catch (InterruptedException e) {
            SALog.printStackTrace(e);
        } catch (Throwable ex) {
            SALog.d(TAG, ex.getMessage());
            return "";
        }
    }

    private static void getOAIDReflect(Context context, int retryCount) {
        if (retryCount != 0) {
            try {
                initPemCert(context);
                Class<?> cls = jLibrary;
                if (cls != null) {
                    cls.getDeclaredMethod("InitEntry", new Class[]{Context.class}).invoke((Object) null, new Object[]{context});
                }
                int errCode = ((Integer) mMidSDKHelper.getDeclaredMethod("InitSdk", new Class[]{Context.class, Boolean.TYPE, mIdentifyListener}).invoke((Object) null, new Object[]{context, true, Proxy.newProxyInstance(context.getClassLoader(), new Class[]{mIdentifyListener}, new IdentifyListenerHandler())})).intValue();
                SALog.d(TAG, "MdidSdkHelper ErrorCode : " + errCode);
                if (!(errCode == 1008614 || errCode == 1008610)) {
                    int retryCount2 = retryCount - 1;
                    getOAIDReflect(context, retryCount2);
                    if (retryCount2 == 0) {
                        mCountDownLatch.countDown();
                    }
                }
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(CacheHandler.delayTime);
                        } catch (InterruptedException e) {
                        }
                        OaidHelper.mCountDownLatch.countDown();
                    }
                }).start();
            } catch (Throwable ex) {
                SALog.d(TAG, ex.getMessage());
                int retryCount3 = retryCount - 1;
                getOAIDReflect(context, retryCount3);
                if (retryCount3 == 0) {
                    mCountDownLatch.countDown();
                }
            }
        }
    }

    public static class IdentifyListenerHandler implements InvocationHandler {
        IdentifyListenerHandler() {
        }

        public Object invoke(Object proxy, Method method, Object[] args) {
            try {
                if (!"OnSupport".equalsIgnoreCase(method.getName())) {
                    return null;
                }
                Method getOAID = OaidHelper.mIdSupplier.getDeclaredMethod("getOAID", new Class[0]);
                if (args.length == 1) {
                    String unused = OaidHelper.mOAID = (String) getOAID.invoke(args[0], new Object[0]);
                } else {
                    String unused2 = OaidHelper.mOAID = (String) getOAID.invoke(args[1], new Object[0]);
                }
                SALog.d(OaidHelper.TAG, "oaid:" + OaidHelper.mOAID);
                OaidHelper.mCountDownLatch.countDown();
                return null;
            } catch (Throwable th) {
                OaidHelper.mCountDownLatch.countDown();
                return null;
            }
        }
    }

    private static void initInvokeListener() {
        try {
            mMidSDKHelper = Class.forName("com.bun.miitmdid.core.MdidSdkHelper");
            try {
                mIdentifyListener = Class.forName("com.bun.miitmdid.interfaces.IIdentifierListener");
                mIdSupplier = Class.forName("com.bun.miitmdid.interfaces.IdSupplier");
            } catch (Exception e) {
                try {
                    mIdentifyListener = Class.forName("com.bun.supplier.IIdentifierListener");
                    mIdSupplier = Class.forName("com.bun.supplier.IdSupplier");
                    jLibrary = Class.forName("com.bun.miitmdid.core.JLibrary");
                } catch (Exception e2) {
                    try {
                        mIdentifyListener = Class.forName("com.bun.miitmdid.core.IIdentifierListener");
                        mIdSupplier = Class.forName("com.bun.miitmdid.supplier.IdSupplier");
                        jLibrary = Class.forName("com.bun.miitmdid.core.JLibrary");
                    } catch (Exception e3) {
                    }
                }
            }
        } catch (ClassNotFoundException e4) {
            SALog.d(TAG, e4.getMessage());
        }
    }

    private static void initSDKLibrary() {
        for (String library : mLoadLibrary) {
            try {
                System.loadLibrary(library);
                return;
            } catch (Throwable th) {
            }
        }
    }

    private static void initPemCert(Context context) {
        try {
            String oaidCert = loadPemFromAssetFile(context);
            if (!TextUtils.isEmpty(oaidCert)) {
                mMidSDKHelper.getDeclaredMethod("InitCert", new Class[]{Context.class, String.class}).invoke((Object) null, new Object[]{context, oaidCert});
            }
        } catch (Throwable e) {
            SALog.d(TAG, e.getMessage());
        }
    }

    private static String loadPemFromAssetFile(Context context) {
        InputStream is;
        try {
            String defaultPemCert = context.getPackageName() + ".cert.pem";
            AssetManager assetManager = context.getAssets();
            if (!TextUtils.isEmpty(mOidCertFilePath)) {
                try {
                    is = assetManager.open(mOidCertFilePath);
                } catch (IOException e) {
                    is = assetManager.open(defaultPemCert);
                }
            } else {
                is = assetManager.open(defaultPemCert);
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            while (true) {
                String readLine = in.readLine();
                String line = readLine;
                if (readLine == null) {
                    return builder.toString();
                }
                builder.append(line);
                builder.append(10);
            }
        } catch (IOException e2) {
            SALog.d(TAG, "loadPemFromAssetFile failed");
            return "";
        }
    }

    public static void setOaidCertFilePath(String filePath) {
        mOidCertFilePath = filePath;
    }
}
