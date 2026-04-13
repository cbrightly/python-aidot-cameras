package com.alibaba.fastjson.support.hsf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class HSFJSONUtils {
    static final char[] fieldName_argsObjs = "\"argsObjs\"".toCharArray();
    static final char[] fieldName_argsTypes = "\"argsTypes\"".toCharArray();
    static final char[] fieldName_type = "\"@type\":".toCharArray();
    static final SymbolTable typeSymbolTable = new SymbolTable(1024);

    public static Object[] parseInvocationArguments(String json, MethodLocator methodLocator) {
        Object[] values;
        DefaultJSONParser parser = new DefaultJSONParser(json);
        JSONLexerBase lexer = (JSONLexerBase) parser.getLexer();
        ParseContext rootContext = parser.setContext((Object) null, (Object) null);
        int token = lexer.token();
        if (token == 12) {
            char[] cArr = fieldName_argsTypes;
            SymbolTable symbolTable = typeSymbolTable;
            String[] typeNames = lexer.scanFieldStringArray(cArr, -1, symbolTable);
            if (typeNames == null && lexer.matchStat == -2 && "com.alibaba.fastjson.JSONObject".equals(lexer.scanFieldString(fieldName_type))) {
                typeNames = lexer.scanFieldStringArray(cArr, -1, symbolTable);
            }
            Method method = methodLocator.findMethod(typeNames);
            if (method == null) {
                lexer.close();
                JSONObject jsonObject = JSON.parseObject(json);
                Method method2 = methodLocator.findMethod((String[]) jsonObject.getObject("argsTypes", String[].class));
                JSONArray argsObjs = jsonObject.getJSONArray("argsObjs");
                if (argsObjs == null) {
                    return null;
                }
                Type[] argTypes = method2.getGenericParameterTypes();
                Object[] values2 = new Object[argTypes.length];
                for (int i = 0; i < argTypes.length; i++) {
                    values2[i] = argsObjs.getObject(i, argTypes[i]);
                }
                return values2;
            }
            Type[] argTypes2 = method.getGenericParameterTypes();
            lexer.skipWhitespace();
            if (lexer.getCurrent() == ',') {
                lexer.next();
            }
            if (lexer.matchField2(fieldName_argsObjs)) {
                lexer.nextToken();
                ParseContext context = parser.setContext(rootContext, (Object) null, "argsObjs");
                Object[] values3 = parser.parseArray(argTypes2);
                context.object = values3;
                parser.accept(13);
                parser.handleResovleTask((Object) null);
                values = values3;
            } else {
                values = null;
            }
            parser.close();
            return values;
        } else if (token != 14) {
            return null;
        } else {
            String[] typeNames2 = lexer.scanFieldStringArray((char[]) null, -1, typeSymbolTable);
            lexer.skipWhitespace();
            char ch = lexer.getCurrent();
            if (ch == ']') {
                Type[] argTypes3 = methodLocator.findMethod((String[]) null).getGenericParameterTypes();
                Object[] values4 = new Object[typeNames2.length];
                for (int i2 = 0; i2 < typeNames2.length; i2++) {
                    Type argType = argTypes3[i2];
                    String typeName = typeNames2[i2];
                    if (argType != String.class) {
                        values4[i2] = TypeUtils.cast((Object) typeName, argType, parser.getConfig());
                    } else {
                        values4[i2] = typeName;
                    }
                }
                return values4;
            }
            if (ch == ',') {
                lexer.next();
                lexer.skipWhitespace();
            }
            lexer.nextToken(14);
            Object[] values5 = parser.parseArray(methodLocator.findMethod(typeNames2).getGenericParameterTypes());
            lexer.close();
            return values5;
        }
    }
}
