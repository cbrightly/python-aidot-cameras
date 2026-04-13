package com.alibaba.fastjson.asm;

import io.netty.util.internal.StringUtil;

public class MethodCollector {
    private int currentParameter;
    protected boolean debugInfoPresent;
    private final int ignoreCount;
    private final int paramCount;
    private final StringBuilder result = new StringBuilder();

    protected MethodCollector(int ignoreCount2, int paramCount2) {
        this.ignoreCount = ignoreCount2;
        this.paramCount = paramCount2;
        boolean z = false;
        this.currentParameter = 0;
        this.debugInfoPresent = paramCount2 == 0 ? true : z;
    }

    /* access modifiers changed from: protected */
    public void visitLocalVariable(String name, int index) {
        int i = this.ignoreCount;
        if (index >= i && index < i + this.paramCount) {
            if (!name.equals("arg" + this.currentParameter)) {
                this.debugInfoPresent = true;
            }
            this.result.append(StringUtil.COMMA);
            this.result.append(name);
            this.currentParameter++;
        }
    }

    /* access modifiers changed from: protected */
    public String getResult() {
        return this.result.length() != 0 ? this.result.substring(1) : "";
    }
}
