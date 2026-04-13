package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzho {
    private static volatile zzii zza = null;

    private zzho() {
    }

    public static zzii zza(Context context) {
        zzii zzii;
        zzii zzii2;
        zzii zzii3;
        BufferedReader bufferedReader;
        synchronized (zzho.class) {
            zzii = zza;
            if (zzii == null) {
                String str = Build.TYPE;
                String str2 = Build.TAGS;
                if (str.equals("eng") || str.equals("userdebug")) {
                    if (!str2.contains("dev-keys")) {
                        if (str2.contains("test-keys")) {
                        }
                    }
                    if (zzhb.zzb() && !context.isDeviceProtectedStorage()) {
                        context = context.createDeviceProtectedStorageContext();
                    }
                    StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
                    try {
                        StrictMode.allowThreadDiskWrites();
                        File file = new File(context.getDir("phenotype_hermetic", 0), "overrides.txt");
                        zzii2 = file.exists() ? zzii.zzd(file) : zzii.zzc();
                    } catch (RuntimeException e) {
                        Log.e("HermeticFileOverrides", "no data dir", e);
                        zzii2 = zzii.zzc();
                    } catch (Throwable th) {
                        StrictMode.setThreadPolicy(allowThreadDiskReads);
                        throw th;
                    }
                    if (zzii2.zzb()) {
                        File file2 = (File) zzii2.zza();
                        try {
                            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
                            SimpleArrayMap simpleArrayMap = new SimpleArrayMap();
                            HashMap hashMap = new HashMap();
                            while (true) {
                                String readLine = bufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                String[] split = readLine.split(" ", 3);
                                if (split.length != 3) {
                                    Log.e("HermeticFileOverrides", "Invalid: " + readLine);
                                } else {
                                    String str3 = new String(split[0]);
                                    String decode = Uri.decode(new String(split[1]));
                                    String str4 = (String) hashMap.get(split[2]);
                                    if (str4 == null) {
                                        String str5 = new String(split[2]);
                                        str4 = Uri.decode(str5);
                                        if (str4.length() < 1024 || str4 == str5) {
                                            hashMap.put(str5, str4);
                                        }
                                    }
                                    if (!simpleArrayMap.containsKey(str3)) {
                                        simpleArrayMap.put(str3, new SimpleArrayMap());
                                    }
                                    ((SimpleArrayMap) simpleArrayMap.get(str3)).put(decode, str4);
                                }
                            }
                            String obj = file2.toString();
                            String packageName = context.getPackageName();
                            Log.w("HermeticFileOverrides", "Parsed " + obj + " for Android package " + packageName);
                            zzhh zzhh = new zzhh(simpleArrayMap);
                            bufferedReader.close();
                            zzii3 = zzii.zzd(zzhh);
                        } catch (IOException e2) {
                            throw new RuntimeException(e2);
                        } catch (Throwable th2) {
                            try {
                                Throwable.class.getDeclaredMethod("addSuppressed", new Class[]{Throwable.class}).invoke(th, new Object[]{th2});
                            } catch (Exception e3) {
                            }
                        }
                    } else {
                        zzii3 = zzii.zzc();
                    }
                    StrictMode.setThreadPolicy(allowThreadDiskReads);
                    zzii = zzii3;
                    zza = zzii;
                }
                zzii = zzii.zzc();
                zza = zzii;
            }
        }
        return zzii;
        throw th;
    }
}
