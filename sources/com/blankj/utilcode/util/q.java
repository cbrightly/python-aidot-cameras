package com.blankj.utilcode.util;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;
import androidx.collection.SimpleArrayMap;
import androidx.exifinterface.media.ExifInterface;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.google.maps.android.BuildConfig;
import com.meituan.robust.Constants;
import io.netty.util.internal.StringUtil;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/* compiled from: LogUtils */
public final class q {
    /* access modifiers changed from: private */
    public static final char[] a = {'V', 'D', 'I', 'W', 'E', 'A'};
    /* access modifiers changed from: private */
    public static final String b = System.getProperty("file.separator");
    /* access modifiers changed from: private */
    public static final String c = System.getProperty("line.separator");
    private static final d d = new d((a) null);
    private static SimpleDateFormat e;
    private static final ExecutorService f = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public static final SimpleArrayMap<Class, f> g = new SimpleArrayMap<>();

    /* compiled from: LogUtils */
    public interface e {
        void a(String str, String str2);
    }

    /* compiled from: LogUtils */
    public static abstract class f<T> {
        public abstract String a(T t);
    }

    public static d n() {
        return d;
    }

    public static void F(Object... contents) {
        t(2, d.j(), contents);
    }

    public static void s(Object content) {
        t(35, d.j(), content);
    }

    public static void t(int type, String tag, Object... contents) {
        d dVar = d;
        if (dVar.s()) {
            int type_low = type & 15;
            int type_high = type & 240;
            if (!dVar.o() && !dVar.p() && type_high != 16) {
                return;
            }
            if (type_low >= dVar.m || type_low >= dVar.n) {
                h tagHead = E(tag);
                String body = C(type_high, contents);
                if (dVar.o() && type_high != 16 && type_low >= dVar.m) {
                    u(type_low, tagHead.a, tagHead.b, body);
                }
                if ((dVar.p() || type_high == 16) && type_low >= dVar.n) {
                    f.execute(new a(type_low, tagHead, body));
                }
            }
        }
    }

    /* compiled from: LogUtils */
    public static final class a implements Runnable {
        final /* synthetic */ int c;
        final /* synthetic */ h d;
        final /* synthetic */ String f;

        a(int i, h hVar, String str) {
            this.c = i;
            this.d = hVar;
            this.f = str;
        }

        public void run() {
            int i = this.c;
            String str = this.d.a;
            q.v(i, str, this.d.c + this.f);
        }
    }

    private static h E(String tag) {
        String tag2;
        String tag3;
        String tag4;
        d dVar = d;
        if (dVar.h || dVar.r()) {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            int stackIndex = dVar.n() + 3;
            if (stackIndex >= stackTrace.length) {
                String fileName = o(stackTrace[3]);
                if (!dVar.h || !h0.E(tag)) {
                    tag4 = tag;
                } else {
                    int index = fileName.indexOf(46);
                    tag4 = index == -1 ? fileName : fileName.substring(0, index);
                }
                return new h(tag4, (String[]) null, ": ");
            }
            StackTraceElement targetElement = stackTrace[stackIndex];
            String fileName2 = o(targetElement);
            if (!dVar.h || !h0.E(tag)) {
                tag3 = tag;
            } else {
                int index2 = fileName2.indexOf(46);
                tag3 = index2 == -1 ? fileName2 : fileName2.substring(0, index2);
            }
            if (dVar.r()) {
                String tName = Thread.currentThread().getName();
                int i = 5;
                String head = new Formatter().format("%s, %s.%s(%s:%d)", new Object[]{tName, targetElement.getClassName(), targetElement.getMethodName(), fileName2, Integer.valueOf(targetElement.getLineNumber())}).toString();
                String fileHead = " [" + head + "]: ";
                if (dVar.m() <= 1) {
                    return new h(tag3, new String[]{head}, fileHead);
                }
                String[] consoleHead = new String[Math.min(dVar.m(), stackTrace.length - stackIndex)];
                consoleHead[0] = head;
                String space = new Formatter().format("%" + (tName.length() + 2) + "s", new Object[]{""}).toString();
                int i2 = 1;
                int len = consoleHead.length;
                while (i2 < len) {
                    StackTraceElement targetElement2 = stackTrace[i2 + stackIndex];
                    Formatter formatter = new Formatter();
                    StackTraceElement[] stackTrace2 = stackTrace;
                    Object[] objArr = new Object[i];
                    objArr[0] = space;
                    objArr[1] = targetElement2.getClassName();
                    objArr[2] = targetElement2.getMethodName();
                    objArr[3] = o(targetElement2);
                    objArr[4] = Integer.valueOf(targetElement2.getLineNumber());
                    consoleHead[i2] = formatter.format("%s%s.%s(%s:%d)", objArr).toString();
                    i2++;
                    stackTrace = stackTrace2;
                    i = 5;
                }
                return new h(tag3, consoleHead, fileHead);
            }
            tag2 = tag3;
        } else {
            tag2 = dVar.j();
        }
        return new h(tag2, (String[]) null, ": ");
    }

    private static String o(StackTraceElement targetElement) {
        String fileName = targetElement.getFileName();
        if (fileName != null) {
            return fileName;
        }
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }
        int index = className.indexOf(36);
        if (index != -1) {
            className = className.substring(0, index);
        }
        return className + ".java";
    }

    private static String C(int type, Object... contents) {
        String body = BuildConfig.TRAVIS;
        if (contents != null) {
            if (contents.length == 1) {
                body = k(type, contents[0]);
            } else {
                StringBuilder sb = new StringBuilder();
                int len = contents.length;
                for (int i = 0; i < len; i++) {
                    Object content = contents[i];
                    sb.append("args");
                    sb.append(Constants.ARRAY_TYPE);
                    sb.append(i);
                    sb.append("]");
                    sb.append(" = ");
                    sb.append(l(content));
                    sb.append(c);
                }
                body = sb.toString();
            }
        }
        return body.length() == 0 ? "log nothing" : body;
    }

    private static String k(int type, Object object) {
        if (object == null) {
            return BuildConfig.TRAVIS;
        }
        if (type == 32) {
            return g.h(object, 32);
        }
        if (type == 48) {
            return g.h(object, 48);
        }
        return l(object);
    }

    /* access modifiers changed from: private */
    public static String l(Object object) {
        f iFormatter;
        if (object == null) {
            return BuildConfig.TRAVIS;
        }
        SimpleArrayMap<Class, f> simpleArrayMap = g;
        if (simpleArrayMap.isEmpty() || (iFormatter = simpleArrayMap.get(m(object))) == null) {
            return g.g(object);
        }
        return iFormatter.a(object);
    }

    private static void u(int type, String tag, String[] head, String msg) {
        if (d.t()) {
            A(type, tag, D(type, tag, head, msg));
            return;
        }
        w(type, tag, true);
        y(type, tag, head);
        z(type, tag, msg);
        w(type, tag, false);
    }

    private static void w(int type, String tag, boolean isTop) {
        if (d.q()) {
            Log.println(type, tag, isTop ? "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────" : "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        }
    }

    private static void y(int type, String tag, String[] head) {
        String str;
        if (head != null) {
            for (String aHead : head) {
                if (d.q()) {
                    str = "│ " + aHead;
                } else {
                    str = aHead;
                }
                Log.println(type, tag, str);
            }
            if (d.q()) {
                Log.println(type, tag, "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
            }
        }
    }

    private static void z(int type, String tag, String msg) {
        int len = msg.length();
        int countOfSub = len / 1100;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                B(type, tag, msg.substring(index, index + 1100));
                index += 1100;
            }
            if (index != len) {
                B(type, tag, msg.substring(index, len));
                return;
            }
            return;
        }
        B(type, tag, msg);
    }

    private static void B(int type, String tag, String msg) {
        if (!d.q()) {
            Log.println(type, tag, msg);
            return;
        }
        new StringBuilder();
        for (String line : msg.split(c)) {
            Log.println(type, tag, "│ " + line);
        }
    }

    private static String D(int type, String tag, String[] head, String msg) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (d.q()) {
            sb.append(" ");
            String str = c;
            sb.append(str);
            sb.append("┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
            sb.append(str);
            if (head != null) {
                for (String aHead : head) {
                    sb.append("│ ");
                    sb.append(aHead);
                    sb.append(c);
                }
                sb.append("├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
                sb.append(c);
            }
            String[] split = msg.split(c);
            int length = split.length;
            while (i < length) {
                String line = split[i];
                sb.append("│ ");
                sb.append(line);
                sb.append(c);
                i++;
            }
            sb.append("└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        } else {
            if (head != null) {
                sb.append(" ");
                sb.append(c);
                int length2 = head.length;
                while (i < length2) {
                    sb.append(head[i]);
                    sb.append(c);
                    i++;
                }
            }
            sb.append(msg);
        }
        return sb.toString();
    }

    private static void A(int type, String tag, String msg) {
        int len = msg.length();
        d dVar = d;
        int countOfSub = dVar.q() ? (len - "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────".length()) / 1100 : len / 1100;
        if (countOfSub <= 0) {
            Log.println(type, tag, msg);
        } else if (dVar.q()) {
            Log.println(type, tag, msg.substring(0, 1100) + c + "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
            int index = 1100;
            for (int i = 1; i < countOfSub; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(" ");
                String str = c;
                sb.append(str);
                sb.append("┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
                sb.append(str);
                sb.append("│ ");
                sb.append(msg.substring(index, index + 1100));
                sb.append(str);
                sb.append("└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
                Log.println(type, tag, sb.toString());
                index += 1100;
            }
            if (index != len - "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────".length()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(" ");
                String str2 = c;
                sb2.append(str2);
                sb2.append("┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
                sb2.append(str2);
                sb2.append("│ ");
                sb2.append(msg.substring(index, len));
                Log.println(type, tag, sb2.toString());
            }
        } else {
            Log.println(type, tag, msg.substring(0, 1100));
            int index2 = 1100;
            for (int i2 = 1; i2 < countOfSub; i2++) {
                Log.println(type, tag, " " + c + msg.substring(index2, index2 + 1100));
                index2 += 1100;
            }
            if (index2 != len) {
                Log.println(type, tag, " " + c + msg.substring(index2, len));
            }
        }
    }

    /* access modifiers changed from: private */
    public static void v(int type, String tag, String msg) {
        String format = p().format(new Date());
        String date = format.substring(0, 10);
        String time = format.substring(11);
        StringBuilder sb = new StringBuilder();
        d dVar = d;
        sb.append(dVar.f());
        sb.append(dVar.i());
        sb.append("_");
        sb.append(date);
        sb.append("_");
        sb.append(dVar.k());
        sb.append(dVar.g());
        String fullPath = sb.toString();
        if (!h(fullPath, date)) {
            Log.e("LogUtils", "create " + fullPath + " failed!");
            return;
        }
        q(fullPath, time + a[type - 2] + "/" + tag + msg + c);
    }

    private static SimpleDateFormat p() {
        if (e == null) {
            e = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss.SSS ", Locale.getDefault());
        }
        return e;
    }

    private static boolean h(String filePath, String date) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.isFile();
        }
        if (!h0.d(file.getParentFile())) {
            return false;
        }
        try {
            i(filePath, date);
            boolean isCreate = file.createNewFile();
            if (isCreate) {
                x(filePath, date);
            }
            return isCreate;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private static void i(String filePath, String date) {
        File[] files;
        d dVar = d;
        if (dVar.l() > 0 && (files = new File(filePath).getParentFile().listFiles(new b())) != null && files.length > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault());
            try {
                long dueMillis = sdf.parse(date).getTime() - (((long) dVar.l()) * CostTimeUtil.DAY);
                for (File aFile : files) {
                    String name = aFile.getName();
                    int length = name.length();
                    if (sdf.parse(j(name)).getTime() <= dueMillis) {
                        f.execute(new c(aFile));
                    }
                }
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: LogUtils */
    public static final class b implements FilenameFilter {
        b() {
        }

        public boolean accept(File dir, String name) {
            return q.r(name);
        }
    }

    /* compiled from: LogUtils */
    public static final class c implements Runnable {
        final /* synthetic */ File c;

        c(File file) {
            this.c = file;
        }

        public void run() {
            if (!this.c.delete()) {
                Log.e("LogUtils", "delete " + this.c + " failed!");
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean r(String name) {
        return name.matches("^" + d.i() + "_[0-9]{4}_[0-9]{2}_[0-9]{2}_.*$");
    }

    private static String j(String str) {
        Matcher matcher = Pattern.compile("[0-9]{4}_[0-9]{2}_[0-9]{2}").matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    private static void x(String filePath, String date) {
        q(filePath, "************* Log Head ****************\nDate of Log        : " + date + "\nDevice Manufacturer: " + Build.MANUFACTURER + "\nDevice Model       : " + Build.MODEL + "\nAndroid Version    : " + Build.VERSION.RELEASE + "\nAndroid SDK        : " + Build.VERSION.SDK_INT + "\nApp VersionName    : " + h0.l() + "\nApp VersionCode    : " + h0.k() + "\n************* Log Head ****************\n\n");
    }

    private static void q(String filePath, String input) {
        d dVar = d;
        if (dVar.s == null) {
            h0.L(filePath, input, true);
        } else {
            dVar.s.a(filePath, input);
        }
    }

    /* compiled from: LogUtils */
    public static final class d {
        private String a;
        private String b;
        private String c;
        private String d;
        private boolean e;
        private boolean f;
        private String g;
        /* access modifiers changed from: private */
        public boolean h;
        private boolean i;
        private boolean j;
        private boolean k;
        private boolean l;
        /* access modifiers changed from: private */
        public int m;
        /* access modifiers changed from: private */
        public int n;
        private int o;
        private int p;
        private int q;
        private String r;
        /* access modifiers changed from: private */
        public e s;

        /* synthetic */ d(a x0) {
            this();
        }

        private d() {
            this.c = "util";
            this.d = ".txt";
            this.e = true;
            this.f = true;
            this.g = "";
            this.h = true;
            this.i = true;
            this.j = false;
            this.k = true;
            this.l = true;
            this.m = 2;
            this.n = 2;
            this.o = 1;
            this.p = 0;
            this.q = -1;
            this.r = h0.n();
            if (!h0.D() || f0.a().getExternalFilesDir((String) null) == null) {
                this.a = f0.a().getFilesDir() + q.b + "log" + q.b;
                return;
            }
            this.a = f0.a().getExternalFilesDir((String) null) + q.b + "log" + q.b;
        }

        public final d D(boolean logSwitch) {
            this.e = logSwitch;
            return this;
        }

        public final d w(boolean consoleSwitch) {
            this.f = consoleSwitch;
            return this;
        }

        public final d A(String tag) {
            if (h0.E(tag)) {
                this.g = "";
                this.h = true;
            } else {
                this.g = tag;
                this.h = false;
            }
            return this;
        }

        public final d C(boolean logHeadSwitch) {
            this.i = logHeadSwitch;
            return this;
        }

        public final d B(boolean log2FileSwitch) {
            this.j = log2FileSwitch;
            return this;
        }

        public final d x(String dir) {
            String str;
            if (h0.E(dir)) {
                this.b = null;
            } else {
                if (dir.endsWith(q.b)) {
                    str = dir;
                } else {
                    str = dir + q.b;
                }
                this.b = str;
            }
            return this;
        }

        public final d z(String filePrefix) {
            if (h0.E(filePrefix)) {
                this.c = "util";
            } else {
                this.c = filePrefix;
            }
            return this;
        }

        public final d u(boolean borderSwitch) {
            this.k = borderSwitch;
            return this;
        }

        public final d E(boolean singleTagSwitch) {
            this.l = singleTagSwitch;
            return this;
        }

        public final d v(int consoleFilter) {
            this.m = consoleFilter;
            return this;
        }

        public final d y(int fileFilter) {
            this.n = fileFilter;
            return this;
        }

        public final d F(@IntRange(from = 1) int stackDeep) {
            this.o = stackDeep;
            return this;
        }

        public final d G(@IntRange(from = 0) int stackOffset) {
            this.p = stackOffset;
            return this;
        }

        public final String k() {
            String str = this.r;
            if (str == null) {
                return "";
            }
            return str.replace(":", "_");
        }

        public final String f() {
            String str = this.b;
            return str == null ? this.a : str;
        }

        public final String i() {
            return this.c;
        }

        public final String g() {
            return this.d;
        }

        public final boolean s() {
            return this.e;
        }

        public final boolean o() {
            return this.f;
        }

        public final String j() {
            if (h0.E(this.g)) {
                return "";
            }
            return this.g;
        }

        public final boolean r() {
            return this.i;
        }

        public final boolean p() {
            return this.j;
        }

        public final boolean q() {
            return this.k;
        }

        public final boolean t() {
            return this.l;
        }

        public final char e() {
            return q.a[this.m - 2];
        }

        public final char h() {
            return q.a[this.n - 2];
        }

        public final int m() {
            return this.o;
        }

        public final int n() {
            return this.p;
        }

        public final int l() {
            return this.q;
        }

        public String toString() {
            return "process: " + k() + q.c + "switch: " + s() + q.c + "console: " + o() + q.c + "tag: " + j() + q.c + "head: " + r() + q.c + "file: " + p() + q.c + "dir: " + f() + q.c + "filePrefix: " + i() + q.c + "border: " + q() + q.c + "singleTag: " + t() + q.c + "consoleFilter: " + e() + q.c + "fileFilter: " + h() + q.c + "stackDeep: " + m() + q.c + "stackOffset: " + n() + q.c + "saveDays: " + l() + q.c + "formatter: " + q.g;
        }
    }

    /* compiled from: LogUtils */
    public static final class h {
        String a;
        String[] b;
        String c;

        h(String tag, String[] consoleHead, String fileHead) {
            this.a = tag;
            this.b = consoleHead;
            this.c = fileHead;
        }
    }

    /* compiled from: LogUtils */
    public static final class g {
        static String g(Object object) {
            return h(object, -1);
        }

        static String h(Object object, int type) {
            if (object.getClass().isArray()) {
                return a(object);
            }
            if (object instanceof Throwable) {
                return h0.p((Throwable) object);
            }
            if (object instanceof Bundle) {
                return b((Bundle) object);
            }
            if (object instanceof Intent) {
                return e((Intent) object);
            }
            if (type == 32) {
                return f(object);
            }
            if (type == 48) {
                return d(object.toString());
            }
            return object.toString();
        }

        private static String b(Bundle bundle) {
            Iterator<String> iterator = bundle.keySet().iterator();
            if (!iterator.hasNext()) {
                return "Bundle {}";
            }
            StringBuilder sb = new StringBuilder(128);
            sb.append("Bundle { ");
            while (true) {
                String key = iterator.next();
                Object value = bundle.get(key);
                sb.append(key);
                sb.append('=');
                if (value instanceof Bundle) {
                    sb.append(value == bundle ? "(this Bundle)" : b((Bundle) value));
                } else {
                    sb.append(q.l(value));
                }
                if (!iterator.hasNext()) {
                    sb.append(" }");
                    return sb.toString();
                }
                sb.append(StringUtil.COMMA);
                sb.append(' ');
            }
        }

        private static String e(Intent intent) {
            Intent mSelector;
            ClipData mClipData;
            StringBuilder sb = new StringBuilder(128);
            sb.append("Intent { ");
            boolean first = true;
            String mAction = intent.getAction();
            if (mAction != null) {
                sb.append("act=");
                sb.append(mAction);
                first = false;
            }
            Set<String> mCategories = intent.getCategories();
            if (mCategories != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("cat=[");
                boolean firstCategory = true;
                for (String c : mCategories) {
                    if (!firstCategory) {
                        sb.append(StringUtil.COMMA);
                    }
                    sb.append(c);
                    firstCategory = false;
                }
                sb.append("]");
            }
            Uri mData = intent.getData();
            if (mData != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("dat=");
                sb.append(mData);
            }
            String mType = intent.getType();
            if (mType != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("typ=");
                sb.append(mType);
            }
            int mFlags = intent.getFlags();
            if (mFlags != 0) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("flg=0x");
                sb.append(Integer.toHexString(mFlags));
            }
            String mPackage = intent.getPackage();
            if (mPackage != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("pkg=");
                sb.append(mPackage);
            }
            ComponentName mComponent = intent.getComponent();
            if (mComponent != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("cmp=");
                sb.append(mComponent.flattenToShortString());
            }
            Rect mSourceBounds = intent.getSourceBounds();
            if (mSourceBounds != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("bnds=");
                sb.append(mSourceBounds.toShortString());
            }
            int i = Build.VERSION.SDK_INT;
            if (i >= 16 && (mClipData = intent.getClipData()) != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                c(mClipData, sb);
            }
            Bundle mExtras = intent.getExtras();
            if (mExtras != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("extras={");
                sb.append(b(mExtras));
                sb.append('}');
            }
            if (i >= 15 && (mSelector = intent.getSelector()) != null) {
                if (!first) {
                    sb.append(' ');
                }
                sb.append("sel={");
                sb.append(mSelector == intent ? "(this Intent)" : e(mSelector));
                sb.append("}");
            }
            sb.append(" }");
            return sb.toString();
        }

        @RequiresApi(api = 16)
        private static void c(ClipData clipData, StringBuilder sb) {
            ClipData.Item item = clipData.getItemAt(0);
            if (item == null) {
                sb.append("ClipData.Item {}");
                return;
            }
            sb.append("ClipData.Item { ");
            String mHtmlText = item.getHtmlText();
            if (mHtmlText != null) {
                sb.append("H:");
                sb.append(mHtmlText);
                sb.append("}");
                return;
            }
            CharSequence mText = item.getText();
            if (mText != null) {
                sb.append("T:");
                sb.append(mText);
                sb.append("}");
                return;
            }
            Uri uri = item.getUri();
            if (uri != null) {
                sb.append("U:");
                sb.append(uri);
                sb.append("}");
                return;
            }
            Intent intent = item.getIntent();
            if (intent != null) {
                sb.append("I:");
                sb.append(e(intent));
                sb.append("}");
                return;
            }
            sb.append("NULL");
            sb.append("}");
        }

        private static String f(Object object) {
            if (object instanceof CharSequence) {
                return h0.j(object.toString());
            }
            try {
                return h0.q().toJson(object);
            } catch (Throwable th) {
                return object.toString();
            }
        }

        private static String d(String xml) {
            try {
                Source xmlInput = new StreamSource(new StringReader(xml));
                StreamResult xmlOutput = new StreamResult(new StringWriter());
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty("indent", "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", ExifInterface.GPS_MEASUREMENT_2D);
                transformer.transform(xmlInput, xmlOutput);
                String obj = xmlOutput.getWriter().toString();
                return obj.replaceFirst(">", ">" + q.c);
            } catch (Exception e) {
                e.printStackTrace();
                return xml;
            }
        }

        private static String a(Object object) {
            if (object instanceof Object[]) {
                return Arrays.deepToString((Object[]) object);
            }
            if (object instanceof boolean[]) {
                return Arrays.toString((boolean[]) object);
            }
            if (object instanceof byte[]) {
                return Arrays.toString((byte[]) object);
            }
            if (object instanceof char[]) {
                return Arrays.toString((char[]) object);
            }
            if (object instanceof double[]) {
                return Arrays.toString((double[]) object);
            }
            if (object instanceof float[]) {
                return Arrays.toString((float[]) object);
            }
            if (object instanceof int[]) {
                return Arrays.toString((int[]) object);
            }
            if (object instanceof long[]) {
                return Arrays.toString((long[]) object);
            }
            if (object instanceof short[]) {
                return Arrays.toString((short[]) object);
            }
            throw new IllegalArgumentException("Array has incompatible type: " + object.getClass());
        }
    }

    private static Class m(Object obj) {
        String className;
        Class objClass = obj.getClass();
        if (objClass.isAnonymousClass() || objClass.isSynthetic()) {
            Type[] genericInterfaces = objClass.getGenericInterfaces();
            if (genericInterfaces.length == 1) {
                Type type = genericInterfaces[0];
                while (type instanceof ParameterizedType) {
                    type = ((ParameterizedType) type).getRawType();
                }
                className = type.toString();
            } else {
                Type type2 = objClass.getGenericSuperclass();
                while (type2 instanceof ParameterizedType) {
                    type2 = ((ParameterizedType) type2).getRawType();
                }
                className = type2.toString();
            }
            if (className.startsWith("class ")) {
                className = className.substring(6);
            } else if (className.startsWith("interface ")) {
                className = className.substring(10);
            }
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        return objClass;
    }
}
