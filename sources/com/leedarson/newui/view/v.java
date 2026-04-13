package com.leedarson.newui.view;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/* compiled from: MyShaderUtil */
public class v {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String c(Context context, int rawId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Integer(rawId)}, (Object) null, changeQuickRedirect, true, 5203, new Class[]{Context.class, Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(rawId)));
        StringBuffer sb = new StringBuffer();
        while (true) {
            try {
                String readLine = reader.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                sb.append(line);
                sb.append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        reader.close();
        return sb.toString();
    }

    public static int b(int shaderType, String source) {
        Object[] objArr = {new Integer(shaderType), source};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 5204, new Class[]{cls, String.class}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int shader = GLES20.glCreateShader(shaderType);
        if (shader == 0) {
            return shader;
        }
        GLES20.glShaderSource(shader, source);
        GLES20.glCompileShader(shader);
        int[] compile = new int[1];
        GLES20.glGetShaderiv(shader, 35713, compile, 0);
        if (compile[0] == 1) {
            return shader;
        }
        Log.d("qiaopc", "shader compile error");
        GLES20.glDeleteShader(shader);
        return 0;
    }

    public static int a(String vertexSource, String fragmentSource) {
        int fragmentShader;
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{vertexSource, fragmentSource}, (Object) null, changeQuickRedirect, true, 5205, new Class[]{cls, cls}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int vertexShader = b(35633, vertexSource);
        if (vertexShader == 0 || (fragmentShader = b(35632, fragmentSource)) == 0) {
            return 0;
        }
        int program = GLES20.glCreateProgram();
        if (program == 0) {
            return program;
        }
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
        int[] lineStatus = new int[1];
        GLES20.glGetProgramiv(program, 35714, lineStatus, 0);
        if (lineStatus[0] == 1) {
            return program;
        }
        Log.d("qiaopc", "link program error");
        GLES20.glDeleteProgram(program);
        return 0;
    }
}
