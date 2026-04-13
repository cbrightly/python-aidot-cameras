package com.yanzhenjie.andserver.framework.mapping;

import androidx.annotation.NonNull;
import com.yanzhenjie.andserver.http.b;
import java.util.LinkedList;
import java.util.List;

/* compiled from: Method */
public class c {
    private List<b> a = new LinkedList();

    @NonNull
    public List<b> b() {
        return this.a;
    }

    public void a(@NonNull String ruleText) {
        this.a.add(b.reverse(ruleText));
    }
}
