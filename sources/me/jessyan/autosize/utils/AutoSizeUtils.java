package me.jessyan.autosize.utils;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;
import java.lang.reflect.InvocationTargetException;

public class AutoSizeUtils {
    private AutoSizeUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static int dp2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(1, value, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int sp2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(2, value, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int pt2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(3, value, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int in2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(4, value, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int mm2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(5, value, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static Application getApplicationByReflect() {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object app = activityThread.getMethod("getApplication", new Class[0]).invoke(activityThread.getMethod("currentActivityThread", new Class[0]).invoke((Object) null, new Object[0]), new Object[0]);
            if (app != null) {
                return (Application) app;
            }
            throw new NullPointerException("you should init first");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new NullPointerException("you should init first");
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            throw new NullPointerException("you should init first");
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            throw new NullPointerException("you should init first");
        } catch (ClassNotFoundException e4) {
            e4.printStackTrace();
            throw new NullPointerException("you should init first");
        }
    }
}
