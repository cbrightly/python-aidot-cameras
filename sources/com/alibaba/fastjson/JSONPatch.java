package com.alibaba.fastjson;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexerBase;

public class JSONPatch {

    @JSONType(orders = {"op", "from", "path", "value"})
    public static class Operation {
        public String from;
        public String path;
        @JSONField(name = "op")
        public OperationType type;
        public Object value;
    }

    public enum OperationType {
        add,
        remove,
        replace,
        move,
        copy,
        test
    }

    public static String apply(String original, String patch) {
        return JSON.toJSONString(apply(JSON.parse(original, Feature.OrderedField), patch));
    }

    public static Object apply(Object object, String patch) {
        boolean z = false;
        for (Operation op : isObject(patch) ? new Operation[]{(Operation) JSON.parseObject(patch, Operation.class)} : (Operation[]) JSON.parseObject(patch, Operation[].class)) {
            JSONPath path = JSONPath.compile(op.path);
            switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPatch$OperationType[op.type.ordinal()]) {
                case 1:
                    path.patchAdd(object, op.value, false);
                    break;
                case 2:
                    path.patchAdd(object, op.value, true);
                    break;
                case 3:
                    path.remove(object);
                    break;
                case 4:
                case 5:
                    JSONPath from = JSONPath.compile(op.from);
                    Object fromValue = from.eval(object);
                    if (op.type != OperationType.move || from.remove(object)) {
                        path.set(object, fromValue);
                        break;
                    } else {
                        throw new JSONException("json patch move error : " + op.from + " -> " + op.path);
                    }
                    break;
                case 6:
                    Object result = path.eval(object);
                    if (result != null) {
                        return Boolean.valueOf(result.equals(op.value));
                    }
                    if (op.value == null) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
            }
        }
        return object;
    }

    /* renamed from: com.alibaba.fastjson.JSONPatch$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$alibaba$fastjson$JSONPatch$OperationType;

        static {
            int[] iArr = new int[OperationType.values().length];
            $SwitchMap$com$alibaba$fastjson$JSONPatch$OperationType = iArr;
            try {
                iArr[OperationType.add.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPatch$OperationType[OperationType.replace.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPatch$OperationType[OperationType.remove.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPatch$OperationType[OperationType.copy.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPatch$OperationType[OperationType.move.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPatch$OperationType[OperationType.test.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    private static boolean isObject(String patch) {
        if (patch == null) {
            return false;
        }
        int i = 0;
        while (i < patch.length()) {
            char ch = patch.charAt(i);
            if (JSONLexerBase.isWhitespace(ch)) {
                i++;
            } else if (ch == '{') {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
