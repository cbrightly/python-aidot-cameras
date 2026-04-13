package com.leedarson.newui.cloud_play_back.repos;

import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.r;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: LdsVideoCacheManager */
public class y {
    public static ChangeQuickRedirect changeQuickRedirect;
    ArrayList<File> a = new ArrayList<>();
    private ExecutorService b = Executors.newFixedThreadPool(2, new r("ldsvideocache"));
    Comparator<File> c = new a();

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3725, new Class[0], Void.TYPE).isSupported) {
            this.b.submit(new c(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public /* synthetic */ void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3728, new Class[0], Void.TYPE).isSupported) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(BaseApplication.b().getFilesDir());
                String str = File.separator;
                sb.append(str);
                sb.append("stream_cache");
                sb.append(str);
                String video_stream_cache_dir = sb.toString();
                this.a.clear();
                d(video_stream_cache_dir);
                ArrayList<File> arrayList = this.a;
                if (arrayList != null) {
                    Collections.sort(arrayList, this.c);
                    ArrayList arrayList2 = new ArrayList();
                    long currentTime = System.currentTimeMillis();
                    long spaceOfTotal = 0;
                    int i = 0;
                    while (true) {
                        if (i >= this.a.size()) {
                            break;
                        }
                        long fileItemSize = 0;
                        if (this.a.get(i).exists()) {
                            try {
                                FileInputStream fis = new FileInputStream(this.a.get(i));
                                fileItemSize = (long) fis.available();
                                fis.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        spaceOfTotal += fileItemSize;
                        if (spaceOfTotal > 314572800) {
                            ArrayList<File> arrayList3 = this.a;
                            arrayList2.addAll(0, arrayList3.subList(i, arrayList3.size() - 1));
                            break;
                        }
                        if (currentTime - this.a.get(i).lastModified() > 259200000) {
                            arrayList2.add(this.a.get(i));
                        }
                        i++;
                    }
                    for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                        if (((File) arrayList2.get(i2)).exists()) {
                            ((File) arrayList2.get(i2)).delete();
                        }
                    }
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    /* compiled from: LdsVideoCacheManager */
    public class a implements Comparator<File> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 3730, new Class[]{cls, cls}, Integer.TYPE);
            return proxy.isSupported ? ((Integer) proxy.result).intValue() : a((File) obj, (File) obj2);
        }

        public int a(File lhs, File rhs) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{lhs, rhs}, this, changeQuickRedirect, false, 3729, new Class[]{File.class, File.class}, Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            if (lhs == null || rhs == null) {
                return 0;
            }
            if (lhs.lastModified() > rhs.lastModified()) {
                return -1;
            }
            return 1;
        }
    }

    public ArrayList<File> d(String strPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{strPath}, this, changeQuickRedirect, false, 3726, new Class[]{String.class}, ArrayList.class);
        return proxy.isSupported ? (ArrayList) proxy.result : e(strPath);
    }

    public ArrayList<File> e(String strPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{strPath}, this, changeQuickRedirect, false, 3727, new Class[]{String.class}, ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        File[] filesArr = new File(strPath).listFiles();
        if (filesArr == null) {
            return null;
        }
        for (int i = 0; i < filesArr.length; i++) {
            if (filesArr[i].isDirectory()) {
                e(filesArr[i].getAbsolutePath());
            } else if (filesArr[i] != null) {
                this.a.add(filesArr[i]);
            }
        }
        return this.a;
    }
}
