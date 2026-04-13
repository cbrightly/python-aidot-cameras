package com.leedarson.serviceinterface.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

public final class DeviceIdUtils {
    private static final String SP_KEY_DEVICE_ID = "device_id";
    private static final String SP_NAME = "device_info";
    private static final String TAG = DeviceIdUtils.class.getSimpleName();
    private static final String TEMP_DIR = "system_config";
    private static final String TEMP_FILE_NAME = "system_file";
    private static final String TEMP_FILE_NAME_MIME_TYPE = "application/octet-stream";
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String getDeviceId(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9259, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        SharedPreferences sharedPreferences = null;
        String deviceId = null;
        try {
            sharedPreferences = context.getSharedPreferences(SP_NAME, 0);
            String deviceId2 = sharedPreferences.getString(SP_KEY_DEVICE_ID, (String) null);
            if (!TextUtils.isEmpty(deviceId2)) {
                return deviceId2;
            }
            deviceId = getIMEI(context);
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = UUID.randomUUID().toString().replace("-", "");
            }
            try {
                sharedPreferences.edit().putString(SP_KEY_DEVICE_ID, deviceId).apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return deviceId;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static String createUUID(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9260, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Context context2 = context;
        String uuid = UUID.randomUUID().toString().replace("-", "");
        if (Build.VERSION.SDK_INT >= 29) {
            Uri externalContentUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
            ContentResolver contentResolver = context2.getContentResolver();
            Cursor query = contentResolver.query(externalContentUri, new String[]{"_id"}, "title=?", new String[]{TEMP_FILE_NAME}, (String) null);
            if (query == null || !query.moveToFirst()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("title", TEMP_FILE_NAME);
                contentValues.put("mime_type", "application/octet-stream");
                contentValues.put("_display_name", TEMP_FILE_NAME);
                contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS + File.separator + TEMP_DIR);
                Uri insert = contentResolver.insert(externalContentUri, contentValues);
                if (insert != null) {
                    OutputStream outputStream = null;
                    try {
                        outputStream = contentResolver.openOutputStream(insert);
                        if (outputStream == null) {
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            return uuid;
                        }
                        outputStream.write(uuid.getBytes());
                        try {
                            outputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (Throwable th) {
                        Throwable th2 = th;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th2;
                    }
                }
            } else {
                Uri uri = ContentUris.withAppendedId(externalContentUri, query.getLong(0));
                query.close();
                InputStream inputStream = null;
                BufferedReader bufferedReader = null;
                try {
                    InputStream inputStream2 = contentResolver.openInputStream(uri);
                    if (inputStream2 != null) {
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream2));
                        uuid = bufferedReader.readLine();
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                } catch (IOException e7) {
                    e7.printStackTrace();
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e8) {
                            e8.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Throwable th3) {
                    Throwable th4 = th3;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e9) {
                            e9.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e10) {
                            e10.printStackTrace();
                        }
                    }
                    throw th4;
                }
            }
        } else {
            File applicationFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), TEMP_DIR);
            if (!applicationFileDir.exists() && !applicationFileDir.mkdirs()) {
                String str = TAG;
                Log.e(str, "文件夹创建失败: " + applicationFileDir.getPath());
            }
            File file = new File(applicationFileDir, TEMP_FILE_NAME);
            if (!file.exists()) {
                FileWriter fileWriter = null;
                try {
                    if (file.createNewFile()) {
                        fileWriter = new FileWriter(file, false);
                        fileWriter.write(uuid);
                    } else {
                        String str2 = TAG;
                        Log.e(str2, "文件创建失败：" + file.getPath());
                    }
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (IOException e11) {
                            e11.printStackTrace();
                        }
                    }
                } catch (IOException e12) {
                    String str3 = TAG;
                    Log.e(str3, "文件创建失败：" + file.getPath());
                    e12.printStackTrace();
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                } catch (Throwable th5) {
                    Throwable th6 = th5;
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (IOException e13) {
                            e13.printStackTrace();
                        }
                    }
                    throw th6;
                }
            } else {
                FileReader fileReader = null;
                BufferedReader bufferedReader2 = null;
                try {
                    fileReader = new FileReader(file);
                    bufferedReader2 = new BufferedReader(fileReader);
                    uuid = bufferedReader2.readLine();
                    bufferedReader2.close();
                    fileReader.close();
                    try {
                        bufferedReader2.close();
                    } catch (IOException e14) {
                        e14.printStackTrace();
                    }
                    try {
                        fileReader.close();
                    } catch (IOException e15) {
                        e15.printStackTrace();
                    }
                } catch (IOException e16) {
                    e16.printStackTrace();
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e17) {
                            e17.printStackTrace();
                        }
                    }
                    if (fileReader != null) {
                        fileReader.close();
                    }
                } catch (Throwable th7) {
                    Throwable th8 = th7;
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e18) {
                            e18.printStackTrace();
                        }
                    }
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (IOException e19) {
                            e19.printStackTrace();
                        }
                    }
                    throw th8;
                }
            }
        }
        return uuid;
    }

    private static String getIMEI(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9261, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return null;
            }
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getRandomCode() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9262, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String substring = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
        return "0" + substring;
    }
}
