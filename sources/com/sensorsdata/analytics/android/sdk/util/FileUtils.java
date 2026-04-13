package com.sensorsdata.analytics.android.sdk.util;

import com.sensorsdata.analytics.android.sdk.SALog;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public static void writeToFile(File outFile, String content) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outFile);
            outputStream.write(content.getBytes());
            try {
                outputStream.close();
            } catch (IOException e) {
                SALog.printStackTrace(e);
            }
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Throwable th) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e3) {
                    SALog.printStackTrace(e3);
                }
            }
            throw th;
        }
    }

    public static String readFileToString(File inFile) {
        InputStream is;
        ByteArrayOutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(inFile));
            try {
                ByteArrayOutputStream os2 = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                while (true) {
                    int read = is.read(b, 0, 1024);
                    int len = read;
                    if (read == -1) {
                        break;
                    }
                    os2.write(b, 0, len);
                }
                String byteArrayOutputStream = os2.toString();
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    os2.close();
                } catch (IOException e2) {
                    SALog.printStackTrace(e2);
                }
                return byteArrayOutputStream;
            } catch (IOException e3) {
                SALog.printStackTrace(e3);
                try {
                    is.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e5) {
                        SALog.printStackTrace(e5);
                    }
                }
                return null;
            }
        } catch (Exception e6) {
            SALog.printStackTrace(e6);
            return null;
        } catch (Throwable th) {
            try {
                is.close();
            } catch (IOException e7) {
                e7.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e8) {
                    SALog.printStackTrace(e8);
                }
            }
            throw th;
        }
    }
}
