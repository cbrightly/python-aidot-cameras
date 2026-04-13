package com.didichuxing.doraemonkit.util;

import com.blankj.utilcode.util.f0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CacheUtils {
    private static final String TAG = "CacheUtils";

    public static boolean saveObject(String key, Serializable ser) {
        File file = new File(f0.a().getCacheDir() + "/" + key);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return saveObject(ser, file);
    }

    public static Serializable readObject(String key) {
        return readObject(new File(f0.a().getCacheDir() + "/" + key));
    }

    public static boolean saveObject(Serializable ser, File file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return true;
        } catch (IOException e3) {
            e3.printStackTrace();
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static Serializable readObject(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            Serializable serializable = (Serializable) ois.readObject();
            try {
                fis.close();
            } catch (IOException e) {
                LogHelper.e(TAG, e.toString());
            }
            try {
                ois.close();
            } catch (IOException e2) {
                LogHelper.e(TAG, e2.toString());
            }
            return serializable;
        } catch (IOException e3) {
            if (e3 instanceof InvalidClassException) {
                file.delete();
            }
            e3.printStackTrace();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e4) {
                    LogHelper.e(TAG, e4.toString());
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e5) {
                    LogHelper.e(TAG, e5.toString());
                }
            }
            return null;
        } catch (ClassNotFoundException e6) {
            e6.printStackTrace();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e7) {
                    LogHelper.e(TAG, e7.toString());
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e8) {
                    LogHelper.e(TAG, e8.toString());
                }
            }
            return null;
        } catch (Throwable th) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e9) {
                    LogHelper.e(TAG, e9.toString());
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e10) {
                    LogHelper.e(TAG, e10.toString());
                }
            }
            throw th;
        }
    }
}
