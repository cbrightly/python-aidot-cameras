package com.leedarson.skiprope.ctrl;

import com.leedarson.skiprope.bean.VoiceEnum;
import com.leedarson.skiprope.bean.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.LinkedList;

/* compiled from: EnglishStrategy */
public class a implements b {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void a(int num, LinkedList<b> list) {
        Object[] objArr = {new Integer(num), list};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9511, new Class[]{Integer.TYPE, LinkedList.class}, Void.TYPE).isSupported) {
            if (num < 100) {
                list.addAll(b(num));
            } else if (num < 1000) {
                list.addAll(c(num));
            } else if (num < 10000) {
                list.addAll(d(num));
            } else {
                String str = String.valueOf(num);
                for (int i = 0; i < str.length(); i++) {
                    b item = b.findItem(String.valueOf(String.valueOf(str.charAt(i))));
                    if (item != null) {
                        list.add(item);
                    }
                }
            }
        }
    }

    private ArrayList<b> b(int num) {
        Object[] objArr = {new Integer(num)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 9512, new Class[]{Integer.TYPE}, ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        ArrayList<VoiceEnum> list = new ArrayList<>();
        if (num <= 20 || (num < 100 && num % 10 == 0)) {
            b item = b.findItem(String.valueOf(num));
            if (item != null) {
                list.add(item);
            }
        } else if (num < 100) {
            int k = num % 10;
            b item2 = b.findItem((num / 10) + "0");
            if (item2 != null) {
                list.add(item2);
            }
            b item22 = b.findItem(String.valueOf(k));
            if (item22 != null) {
                list.add(item22);
            }
        }
        return list;
    }

    private ArrayList<b> c(int num) {
        Object[] objArr = {new Integer(num)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 9513, new Class[]{Integer.TYPE}, ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        ArrayList<VoiceEnum> list = new ArrayList<>();
        list.add(b.findItem(String.valueOf(num / 100)));
        list.add(b.findItem(b.hundred.name()));
        if (num % 100 != 0) {
            list.add(b.findItem(b.and.name()));
            list.addAll(b(num % 100));
        }
        return list;
    }

    private ArrayList<b> d(int num) {
        Object[] objArr = {new Integer(num)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 9514, new Class[]{Integer.TYPE}, ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        ArrayList<VoiceEnum> list = new ArrayList<>();
        int hundred = (num % 1000) / 100;
        list.add(b.findItem((num / 1000) + ""));
        list.add(b.findItem(b.thousand.name()));
        if (num % 1000 != 0) {
            if (hundred > 0) {
                list.addAll(c(num % 1000));
            } else {
                list.add(b.findItem(b.and.name()));
                list.addAll(b(num % 100));
            }
        }
        return list;
    }
}
