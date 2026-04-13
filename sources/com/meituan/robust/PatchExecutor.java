package com.meituan.robust;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

public class PatchExecutor extends Thread {
    private static final String ROBUST_PATCH_CACHE_DIR = "patch_cache";
    protected Context context;
    protected PatchManipulate patchManipulate;
    protected RobustCallBack robustCallBack;

    public PatchExecutor(Context context2, PatchManipulate patchManipulate2, RobustCallBack robustCallBack2) {
        this.context = context2.getApplicationContext();
        this.patchManipulate = patchManipulate2;
        this.robustCallBack = robustCallBack2;
    }

    public void run() {
        try {
            applyPatchList(fetchPatchList());
        } catch (Throwable t) {
            Log.e("robust", "PatchExecutor run", t);
            this.robustCallBack.exceptionNotify(t, "class:PatchExecutor,method:run,line:36");
        }
    }

    /* access modifiers changed from: protected */
    public List<Patch> fetchPatchList() {
        return this.patchManipulate.fetchPatchList(this.context);
    }

    /* access modifiers changed from: protected */
    public void applyPatchList(List<Patch> patches) {
        if (patches != null && !patches.isEmpty()) {
            Log.d("robust", " patchManipulate list size is " + patches.size());
            for (Patch p : patches) {
                if (p.isAppliedSuccess()) {
                    Log.d("robust", "p.isAppliedSuccess() skip " + p.getLocalPath());
                } else if (this.patchManipulate.ensurePatchExist(p)) {
                    boolean currentPatchResult = false;
                    try {
                        currentPatchResult = patch(this.context, p);
                    } catch (Throwable t) {
                        this.robustCallBack.exceptionNotify(t, "class:PatchExecutor method:applyPatchList line:69");
                    }
                    if (currentPatchResult) {
                        p.setAppliedSuccess(true);
                        this.robustCallBack.onPatchApplied(true, p);
                    } else {
                        this.robustCallBack.onPatchApplied(false, p);
                    }
                    Log.d("robust", "patch LocalPath:" + p.getLocalPath() + ",apply result " + currentPatchResult);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean patch(Context context2, Patch patch) {
        PatchesInfo patchesInfo;
        PatchesInfo patchesInfo2;
        Field changeQuickRedirectField;
        Context context3 = context2;
        if (!this.patchManipulate.verifyPatch(context3, patch)) {
            this.robustCallBack.logNotify("verifyPatch failure, patch info:id = " + patch.getName() + ",md5 = " + patch.getMd5(), "class:PatchExecutor method:patch line:107");
            return false;
        }
        ClassLoader classLoader = null;
        try {
            classLoader = new DexClassLoader(patch.getTempPath(), getPatchCacheDirPath(context3, patch.getName() + patch.getMd5()).getAbsolutePath(), (String) null, PatchExecutor.class.getClassLoader());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (classLoader == null) {
            return false;
        }
        PatchesInfo patchesInfo3 = null;
        try {
            Log.d("robust", "patch patch_info_name:" + patch.getPatchesInfoImplClassFullName());
            patchesInfo3 = (PatchesInfo) classLoader.loadClass(patch.getPatchesInfoImplClassFullName()).newInstance();
        } catch (Throwable t) {
            Log.e("robust", "patch failed 188 ", t);
        }
        if (patchesInfo == null) {
            this.robustCallBack.logNotify("patchesInfo is null, patch info:id = " + patch.getName() + ",md5 = " + patch.getMd5(), "class:PatchExecutor method:patch line:114");
            return false;
        }
        List<PatchedClassInfo> patchedClasses = patchesInfo.getPatchedClassesInfo();
        if (patchedClasses == null) {
            return true;
        } else if (patchedClasses.isEmpty()) {
            PatchesInfo patchesInfo4 = patchesInfo;
            return true;
        } else {
            boolean isClassNotFoundException = false;
            for (PatchedClassInfo patchedClassInfo : patchedClasses) {
                String patchedClassName = patchedClassInfo.patchedClassName;
                String patchClassName = patchedClassInfo.patchClassName;
                if (TextUtils.isEmpty(patchedClassName)) {
                    patchesInfo2 = patchesInfo;
                } else if (TextUtils.isEmpty(patchClassName)) {
                    patchesInfo2 = patchesInfo;
                } else {
                    Log.d("robust", "current path:" + patchedClassName);
                    try {
                        Class loadClass = classLoader.loadClass(patchedClassName.trim());
                        try {
                            Field[] fields = loadClass.getDeclaredFields();
                            StringBuilder sb = new StringBuilder();
                            sb.append("oldClass :");
                            Class cls = loadClass;
                            sb.append(cls);
                            sb.append("     fields ");
                            patchesInfo2 = patchesInfo;
                            Field[] fields2 = fields;
                            try {
                                sb.append(fields2.length);
                                Log.d("robust", sb.toString());
                                int length = fields2.length;
                                int i = 0;
                                while (true) {
                                    if (i >= length) {
                                        changeQuickRedirectField = null;
                                        break;
                                    }
                                    Field field = fields2[i];
                                    Field[] fields3 = fields2;
                                    int i2 = length;
                                    if (TextUtils.equals(field.getType().getCanonicalName(), ChangeQuickRedirect.class.getCanonicalName()) && TextUtils.equals(field.getDeclaringClass().getCanonicalName(), cls.getCanonicalName())) {
                                        changeQuickRedirectField = field;
                                        break;
                                    }
                                    i++;
                                    fields2 = fields3;
                                    length = i2;
                                }
                                if (changeQuickRedirectField == null) {
                                    RobustCallBack robustCallBack2 = this.robustCallBack;
                                    StringBuilder sb2 = new StringBuilder();
                                    Class<?> cls2 = cls;
                                    sb2.append("changeQuickRedirectField  is null, patch info:id = ");
                                    sb2.append(patch.getName());
                                    sb2.append(",md5 = ");
                                    sb2.append(patch.getMd5());
                                    robustCallBack2.logNotify(sb2.toString(), "class:PatchExecutor method:patch line:147");
                                    Log.d("robust", "current path:" + patchedClassName + " something wrong !! can  not find:ChangeQuickRedirect in" + patchClassName);
                                } else {
                                    Class sourceClass = cls;
                                    Log.d("robust", "current path:" + patchedClassName + " find:ChangeQuickRedirect " + patchClassName);
                                    Object patchObject = classLoader.loadClass(patchClassName).newInstance();
                                    changeQuickRedirectField.setAccessible(true);
                                    changeQuickRedirectField.set((Object) null, patchObject);
                                    Log.d("robust", "changeQuickRedirectField set success " + patchClassName);
                                }
                            } catch (Throwable th) {
                            }
                        } catch (Throwable th2) {
                            patchesInfo2 = patchesInfo;
                            Log.e("robust", "patch failed! ");
                            Context context4 = context2;
                            Patch patch2 = patch;
                            patchesInfo = patchesInfo2;
                        }
                        Context context42 = context2;
                        Patch patch22 = patch;
                        patchesInfo = patchesInfo2;
                    } catch (ClassNotFoundException e) {
                        isClassNotFoundException = true;
                        Context context5 = context2;
                        Patch patch3 = patch;
                        patchesInfo = patchesInfo;
                    }
                }
                this.robustCallBack.logNotify("patchedClasses or patchClassName is empty, patch info:id = " + patch.getName() + ",md5 = " + patch.getMd5(), "class:PatchExecutor method:patch line:131");
                Context context422 = context2;
                Patch patch222 = patch;
                patchesInfo = patchesInfo2;
            }
            Log.d("robust", "patch finished ");
            if (isClassNotFoundException) {
                return false;
            }
            return true;
        }
    }

    private static File getPatchCacheDirPath(Context c, String key) {
        File patchTempDir = c.getDir(ROBUST_PATCH_CACHE_DIR + key, 0);
        if (!patchTempDir.exists()) {
            patchTempDir.mkdir();
        }
        return patchTempDir;
    }
}
