package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ASMDeserializerFactory implements Opcodes {
    static final String DefaultJSONParser = ASMUtils.type(DefaultJSONParser.class);
    static final String JSONLexerBase = ASMUtils.type(JSONLexerBase.class);
    public final ASMClassLoader classLoader;
    protected final AtomicLong seed = new AtomicLong();

    public ASMDeserializerFactory(ClassLoader parentClassLoader) {
        this.classLoader = parentClassLoader instanceof ASMClassLoader ? (ASMClassLoader) parentClassLoader : new ASMClassLoader(parentClassLoader);
    }

    public ObjectDeserializer createJavaBeanDeserializer(ParserConfig config, JavaBeanInfo beanInfo) {
        String classNameType;
        String packageName;
        Class<?> clazz = beanInfo.clazz;
        if (!clazz.isPrimitive()) {
            String className = "FastjsonASMDeserializer_" + this.seed.incrementAndGet() + "_" + clazz.getSimpleName();
            Package pkg = ASMDeserializerFactory.class.getPackage();
            if (pkg != null) {
                String packageName2 = pkg.getName();
                String classNameType2 = packageName2.replace('.', '/') + "/" + className;
                packageName = packageName2 + "." + className;
                classNameType = classNameType2;
            } else {
                packageName = className;
                classNameType = className;
            }
            ClassWriter cw = new ClassWriter();
            cw.visit(49, 33, classNameType, ASMUtils.type(JavaBeanDeserializer.class), (String[]) null);
            _init(cw, new Context(classNameType, config, beanInfo, 3));
            _createInstance(cw, new Context(classNameType, config, beanInfo, 3));
            _deserialze(cw, new Context(classNameType, config, beanInfo, 5));
            _deserialzeArrayMapping(cw, new Context(classNameType, config, beanInfo, 4));
            byte[] code = cw.toByteArray();
            return (ObjectDeserializer) this.classLoader.defineClassPublic(packageName, code, 0, code.length).getConstructor(new Class[]{ParserConfig.class, JavaBeanInfo.class}).newInstance(new Object[]{config, beanInfo});
        }
        throw new IllegalArgumentException("not support type :" + clazz.getName());
    }

    private void _setFlag(MethodVisitor mw, Context context, int i) {
        String varName = "_asm_flag_" + (i / 32);
        mw.visitVarInsn(21, context.var(varName));
        mw.visitLdcInsn(Integer.valueOf(1 << i));
        mw.visitInsn(128);
        mw.visitVarInsn(54, context.var(varName));
    }

    private void _isFlag(MethodVisitor mw, Context context, int i, Label label) {
        mw.visitVarInsn(21, context.var("_asm_flag_" + (i / 32)));
        mw.visitLdcInsn(Integer.valueOf(1 << i));
        mw.visitInsn(126);
        mw.visitJumpInsn(Opcodes.IFEQ, label);
    }

    private void _deserialzeArrayMapping(ClassWriter cw, Context context) {
        Class<JavaBeanDeserializer> cls;
        Label typeNameNotNull_;
        char c;
        int fieldListSize;
        char c2;
        int i;
        FieldInfo[] sortedFieldInfoList;
        char seperator;
        String str;
        char seperator2;
        char c3;
        int i2;
        Context context2 = context;
        Class<JavaBeanDeserializer> cls2 = JavaBeanDeserializer.class;
        StringBuilder sb = new StringBuilder();
        sb.append("(L");
        String str2 = DefaultJSONParser;
        sb.append(str2);
        sb.append(";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        MethodWriter methodWriter = new MethodWriter(cw, 1, "deserialzeArrayMapping", sb.toString(), (String) null, (String[]) null);
        defineVarLexer(context2, methodWriter);
        methodWriter.visitVarInsn(25, context2.var("lexer"));
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "getSymbolTable", "()" + ASMUtils.desc((Class<?>) SymbolTable.class));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanTypeName", "(" + ASMUtils.desc((Class<?>) SymbolTable.class) + ")Ljava/lang/String;");
        methodWriter.visitVarInsn(58, context2.var("typeName"));
        Label typeNameNotNull_2 = new Label();
        methodWriter.visitVarInsn(25, context2.var("typeName"));
        methodWriter.visitJumpInsn(Opcodes.IFNULL, typeNameNotNull_2);
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "getConfig", "()" + ASMUtils.desc((Class<?>) ParserConfig.class));
        methodWriter.visitVarInsn(25, 0);
        methodWriter.visitFieldInsn(180, ASMUtils.type(cls2), "beanInfo", ASMUtils.desc((Class<?>) JavaBeanInfo.class));
        methodWriter.visitVarInsn(25, context2.var("typeName"));
        methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(cls2), "getSeeAlso", "(" + ASMUtils.desc((Class<?>) ParserConfig.class) + ASMUtils.desc((Class<?>) JavaBeanInfo.class) + "Ljava/lang/String;)" + ASMUtils.desc((Class<?>) cls2));
        methodWriter.visitVarInsn(58, context2.var("userTypeDeser"));
        methodWriter.visitVarInsn(25, context2.var("userTypeDeser"));
        methodWriter.visitTypeInsn(Opcodes.INSTANCEOF, ASMUtils.type(cls2));
        methodWriter.visitJumpInsn(Opcodes.IFEQ, typeNameNotNull_2);
        methodWriter.visitVarInsn(25, context2.var("userTypeDeser"));
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitVarInsn(25, 2);
        methodWriter.visitVarInsn(25, 3);
        methodWriter.visitVarInsn(25, 4);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(cls2), "deserialzeArrayMapping", "(L" + str2 + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        methodWriter.visitInsn(Opcodes.ARETURN);
        methodWriter.visitLabel(typeNameNotNull_2);
        _createInstance(context2, (MethodVisitor) methodWriter);
        FieldInfo[] sortedFieldInfoList2 = context.beanInfo.sortedFields;
        int fieldListSize2 = sortedFieldInfoList2.length;
        int i3 = 0;
        while (i3 < fieldListSize2) {
            boolean last = i3 == fieldListSize2 + -1;
            char seperator3 = last ? ']' : StringUtil.COMMA;
            FieldInfo fieldInfo = sortedFieldInfoList2[i3];
            Class<?> fieldClass = fieldInfo.fieldClass;
            Type fieldType = fieldInfo.fieldType;
            int fieldListSize3 = fieldListSize2;
            FieldInfo[] sortedFieldInfoList3 = sortedFieldInfoList2;
            if (fieldClass == Byte.TYPE || fieldClass == Short.TYPE) {
                i = i3;
                str = "_asm";
                cls = cls2;
                Type type = fieldType;
                typeNameNotNull_ = typeNameNotNull_2;
                fieldListSize = fieldListSize3;
                sortedFieldInfoList = sortedFieldInfoList3;
                c2 = 180;
                c = 184;
                seperator = seperator3;
            } else if (fieldClass == Integer.TYPE) {
                i = i3;
                str = "_asm";
                cls = cls2;
                Type type2 = fieldType;
                typeNameNotNull_ = typeNameNotNull_2;
                fieldListSize = fieldListSize3;
                sortedFieldInfoList = sortedFieldInfoList3;
                c2 = 180;
                c = 184;
                seperator = seperator3;
            } else {
                typeNameNotNull_ = typeNameNotNull_2;
                String str3 = "(I)V";
                int i4 = i3;
                if (fieldClass == Byte.class) {
                    methodWriter.visitVarInsn(25, context2.var("lexer"));
                    methodWriter.visitVarInsn(16, seperator3);
                    String str4 = JSONLexerBase;
                    Type type3 = fieldType;
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str4, "scanInt", "(C)I");
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                    methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                    Label valueNullEnd_ = new Label();
                    methodWriter.visitVarInsn(25, context2.var("lexer"));
                    methodWriter.visitFieldInsn(180, str4, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                    methodWriter.visitLabel(valueNullEnd_);
                    cls = cls2;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                    i = i4;
                    c2 = 180;
                    c = 184;
                } else {
                    Type fieldType2 = fieldType;
                    if (fieldClass == Short.class) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        String str5 = JSONLexerBase;
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "scanInt", "(C)I");
                        methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        Label valueNullEnd_2 = new Label();
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitFieldInsn(180, str5, "matchStat", "I");
                        methodWriter.visitLdcInsn(5);
                        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_2);
                        methodWriter.visitInsn(1);
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        methodWriter.visitLabel(valueNullEnd_2);
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == Integer.class) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        String str6 = JSONLexerBase;
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "scanInt", "(C)I");
                        methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        Label valueNullEnd_3 = new Label();
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitFieldInsn(180, str6, "matchStat", "I");
                        methodWriter.visitLdcInsn(5);
                        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_3);
                        methodWriter.visitInsn(1);
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        methodWriter.visitLabel(valueNullEnd_3);
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == Long.TYPE) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanLong", "(C)J");
                        methodWriter.visitVarInsn(55, context2.var(fieldInfo.name + "_asm", 2));
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == Long.class) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        String str7 = JSONLexerBase;
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str7, "scanLong", "(C)J");
                        methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        Label valueNullEnd_4 = new Label();
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitFieldInsn(180, str7, "matchStat", "I");
                        methodWriter.visitLdcInsn(5);
                        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_4);
                        methodWriter.visitInsn(1);
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        methodWriter.visitLabel(valueNullEnd_4);
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == Boolean.TYPE) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanBoolean", "(C)Z");
                        methodWriter.visitVarInsn(54, context2.var(fieldInfo.name + "_asm"));
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == Float.TYPE) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFloat", "(C)F");
                        methodWriter.visitVarInsn(56, context2.var(fieldInfo.name + "_asm"));
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == Float.class) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        String str8 = JSONLexerBase;
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str8, "scanFloat", "(C)F");
                        methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        Label valueNullEnd_5 = new Label();
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitFieldInsn(180, str8, "matchStat", "I");
                        methodWriter.visitLdcInsn(5);
                        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_5);
                        methodWriter.visitInsn(1);
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        methodWriter.visitLabel(valueNullEnd_5);
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == Double.TYPE) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDouble", "(C)D");
                        methodWriter.visitVarInsn(57, context2.var(fieldInfo.name + "_asm", 2));
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == Double.class) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        String str9 = JSONLexerBase;
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanDouble", "(C)D");
                        methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        Label valueNullEnd_6 = new Label();
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitFieldInsn(180, str9, "matchStat", "I");
                        methodWriter.visitLdcInsn(5);
                        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_6);
                        methodWriter.visitInsn(1);
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        methodWriter.visitLabel(valueNullEnd_6);
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == Character.TYPE) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                        methodWriter.visitInsn(3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C");
                        methodWriter.visitVarInsn(54, context2.var(fieldInfo.name + "_asm"));
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == String.class) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == BigDecimal.class) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDecimal", "(C)Ljava/math/BigDecimal;");
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == Date.class) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDate", "(C)Ljava/util/Date;");
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass == UUID.class) {
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanUUID", "(C)Ljava/util/UUID;");
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        cls = cls2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else if (fieldClass.isEnum()) {
                        Label enumNumIf_ = new Label();
                        Label enumNumErr_ = new Label();
                        Label enumStore_ = new Label();
                        Label enumQuote_ = new Label();
                        cls = cls2;
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        String str10 = JSONLexerBase;
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str10, "getCurrent", "()C");
                        methodWriter.visitInsn(89);
                        methodWriter.visitVarInsn(54, context2.var("ch"));
                        methodWriter.visitLdcInsn(110);
                        methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, enumQuote_);
                        methodWriter.visitVarInsn(21, context2.var("ch"));
                        methodWriter.visitLdcInsn(34);
                        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, enumNumIf_);
                        methodWriter.visitLabel(enumQuote_);
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldClass)));
                        methodWriter.visitVarInsn(25, 1);
                        Label label = enumQuote_;
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getSymbolTable", "()" + ASMUtils.desc((Class<?>) SymbolTable.class));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str10, "scanEnum", "(Ljava/lang/Class;" + ASMUtils.desc((Class<?>) SymbolTable.class) + "C)Ljava/lang/Enum;");
                        methodWriter.visitJumpInsn(Opcodes.GOTO, enumStore_);
                        methodWriter.visitLabel(enumNumIf_);
                        methodWriter.visitVarInsn(21, context2.var("ch"));
                        methodWriter.visitLdcInsn(48);
                        methodWriter.visitJumpInsn(Opcodes.IF_ICMPLT, enumNumErr_);
                        methodWriter.visitVarInsn(21, context2.var("ch"));
                        methodWriter.visitLdcInsn(57);
                        methodWriter.visitJumpInsn(Opcodes.IF_ICMPGT, enumNumErr_);
                        _getFieldDeser(context2, methodWriter, fieldInfo);
                        methodWriter.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(EnumDeserializer.class));
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str10, "scanInt", "(C)I");
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(EnumDeserializer.class), "valueOf", "(I)Ljava/lang/Enum;");
                        methodWriter.visitJumpInsn(Opcodes.GOTO, enumStore_);
                        methodWriter.visitLabel(enumNumErr_);
                        methodWriter.visitVarInsn(25, 0);
                        methodWriter.visitVarInsn(25, context2.var("lexer"));
                        methodWriter.visitVarInsn(16, seperator3);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(cls), "scanEnum", "(L" + str10 + ";C)Ljava/lang/Enum;");
                        methodWriter.visitLabel(enumStore_);
                        methodWriter.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
                        methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + "_asm"));
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        i = i4;
                        c2 = 180;
                        c = 184;
                    } else {
                        String str11 = "_asm";
                        cls = cls2;
                        if (Collection.class.isAssignableFrom(fieldClass)) {
                            Class<?> itemClass = TypeUtils.getCollectionItemClass(fieldType2);
                            if (itemClass == String.class) {
                                if (fieldClass == List.class || fieldClass == Collections.class || fieldClass == ArrayList.class) {
                                    methodWriter.visitTypeInsn(Opcodes.NEW, ASMUtils.type(ArrayList.class));
                                    methodWriter.visitInsn(89);
                                    methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(ArrayList.class), "<init>", "()V");
                                } else {
                                    methodWriter.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldClass)));
                                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/Class;)Ljava/util/Collection;");
                                }
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + str11));
                                methodWriter.visitVarInsn(25, context2.var("lexer"));
                                methodWriter.visitVarInsn(25, context2.var(fieldInfo.name + str11));
                                methodWriter.visitVarInsn(16, seperator3);
                                String str12 = JSONLexerBase;
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str12, "scanStringArray", "(Ljava/util/Collection;C)V");
                                Label valueNullEnd_7 = new Label();
                                methodWriter.visitVarInsn(25, context2.var("lexer"));
                                methodWriter.visitFieldInsn(180, str12, "matchStat", "I");
                                methodWriter.visitLdcInsn(5);
                                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_7);
                                methodWriter.visitInsn(1);
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + str11));
                                methodWriter.visitLabel(valueNullEnd_7);
                                char c4 = seperator3;
                                i2 = i4;
                                c3 = 184;
                            } else {
                                Label notError_ = new Label();
                                methodWriter.visitVarInsn(25, context2.var("lexer"));
                                String str13 = JSONLexerBase;
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str13, "token", "()I");
                                methodWriter.visitVarInsn(54, context2.var("token"));
                                methodWriter.visitVarInsn(21, context2.var("token"));
                                int token = i4 == 0 ? 14 : 16;
                                methodWriter.visitLdcInsn(Integer.valueOf(token));
                                methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, notError_);
                                methodWriter.visitVarInsn(25, 1);
                                methodWriter.visitLdcInsn(Integer.valueOf(token));
                                String str14 = DefaultJSONParser;
                                int i5 = token;
                                String str15 = str3;
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str14, "throwException", str15);
                                methodWriter.visitLabel(notError_);
                                Label quickElse_ = new Label();
                                Label quickEnd_ = new Label();
                                Label label2 = notError_;
                                char c5 = seperator3;
                                methodWriter.visitVarInsn(25, context2.var("lexer"));
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str13, "getCurrent", "()C");
                                methodWriter.visitVarInsn(16, 91);
                                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, quickElse_);
                                methodWriter.visitVarInsn(25, context2.var("lexer"));
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str13, "next", "()C");
                                methodWriter.visitInsn(87);
                                methodWriter.visitVarInsn(25, context2.var("lexer"));
                                methodWriter.visitLdcInsn(14);
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str13, "setToken", str15);
                                methodWriter.visitJumpInsn(Opcodes.GOTO, quickEnd_);
                                methodWriter.visitLabel(quickElse_);
                                methodWriter.visitVarInsn(25, context2.var("lexer"));
                                methodWriter.visitLdcInsn(14);
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str13, "nextToken", str15);
                                methodWriter.visitLabel(quickEnd_);
                                i2 = i4;
                                _newCollection(methodWriter, fieldClass, i2, false);
                                methodWriter.visitInsn(89);
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + str11));
                                _getCollectionFieldItemDeser(context2, methodWriter, fieldInfo, itemClass);
                                methodWriter.visitVarInsn(25, 1);
                                methodWriter.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(itemClass)));
                                methodWriter.visitVarInsn(25, 3);
                                c3 = 184;
                                methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(cls), "parseArray", "(Ljava/util/Collection;" + ASMUtils.desc((Class<?>) ObjectDeserializer.class) + "L" + str14 + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)V");
                            }
                            i = i2;
                            c = c3;
                            fieldListSize = fieldListSize3;
                            sortedFieldInfoList = sortedFieldInfoList3;
                            c2 = 180;
                        } else {
                            char seperator4 = seperator3;
                            String str16 = str3;
                            int i6 = i4;
                            if (fieldClass.isArray()) {
                                methodWriter.visitVarInsn(25, context2.var("lexer"));
                                methodWriter.visitLdcInsn(14);
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", str16);
                                methodWriter.visitVarInsn(25, 1);
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitLdcInsn(Integer.valueOf(i6));
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(cls), "getFieldType", "(I)Ljava/lang/reflect/Type;");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "parseObject", "(Ljava/lang/reflect/Type;)Ljava/lang/Object;");
                                methodWriter.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + str11));
                                i = i6;
                                c = 184;
                                fieldListSize = fieldListSize3;
                                sortedFieldInfoList = sortedFieldInfoList3;
                                c2 = 180;
                            } else {
                                Label objElseIf_ = new Label();
                                Label objEndIf_ = new Label();
                                if (fieldClass == Date.class) {
                                    methodWriter.visitVarInsn(25, context2.var("lexer"));
                                    String str17 = JSONLexerBase;
                                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str17, "getCurrent", "()C");
                                    methodWriter.visitLdcInsn(49);
                                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, objElseIf_);
                                    methodWriter.visitTypeInsn(Opcodes.NEW, ASMUtils.type(Date.class));
                                    methodWriter.visitInsn(89);
                                    methodWriter.visitVarInsn(25, context2.var("lexer"));
                                    seperator2 = seperator4;
                                    methodWriter.visitVarInsn(16, seperator2);
                                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str17, "scanLong", "(C)J");
                                    methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(Date.class), "<init>", "(J)V");
                                    methodWriter.visitVarInsn(58, context2.var(fieldInfo.name + str11));
                                    methodWriter.visitJumpInsn(Opcodes.GOTO, objEndIf_);
                                } else {
                                    seperator2 = seperator4;
                                }
                                methodWriter.visitLabel(objElseIf_);
                                _quickNextToken(context2, methodWriter, 14);
                                i = i6;
                                fieldListSize = fieldListSize3;
                                c = 184;
                                c2 = 180;
                                char c6 = seperator2;
                                sortedFieldInfoList = sortedFieldInfoList3;
                                _deserObject(context, methodWriter, fieldInfo, fieldClass, i);
                                methodWriter.visitVarInsn(25, context2.var("lexer"));
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
                                methodWriter.visitLdcInsn(15);
                                methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, objEndIf_);
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitVarInsn(25, context2.var("lexer"));
                                if (!last) {
                                    methodWriter.visitLdcInsn(16);
                                } else {
                                    methodWriter.visitLdcInsn(15);
                                }
                                methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(cls), "check", "(" + ASMUtils.desc((Class<?>) JSONLexer.class) + "I)V");
                                methodWriter.visitLabel(objEndIf_);
                            }
                        }
                    }
                }
                i3 = i + 1;
                sortedFieldInfoList2 = sortedFieldInfoList;
                char c7 = c2;
                fieldListSize2 = fieldListSize;
                char c8 = c;
                typeNameNotNull_2 = typeNameNotNull_;
                cls2 = cls;
            }
            methodWriter.visitVarInsn(25, context2.var("lexer"));
            methodWriter.visitVarInsn(16, seperator);
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
            methodWriter.visitVarInsn(54, context2.var(fieldInfo.name + str));
            i3 = i + 1;
            sortedFieldInfoList2 = sortedFieldInfoList;
            char c72 = c2;
            fieldListSize2 = fieldListSize;
            char c82 = c;
            typeNameNotNull_2 = typeNameNotNull_;
            cls2 = cls;
        }
        int i7 = i3;
        int i8 = fieldListSize2;
        FieldInfo[] fieldInfoArr = sortedFieldInfoList2;
        String str18 = "(I)V";
        _batchSet(context2, methodWriter, false);
        Label quickElse_2 = new Label();
        Label quickElseIf_ = new Label();
        Label quickElseIfEOI_ = new Label();
        Label quickEnd_2 = new Label();
        methodWriter.visitVarInsn(25, context2.var("lexer"));
        String str19 = JSONLexerBase;
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str19, "getCurrent", "()C");
        methodWriter.visitInsn(89);
        methodWriter.visitVarInsn(54, context2.var("ch"));
        methodWriter.visitVarInsn(16, 44);
        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, quickElseIf_);
        methodWriter.visitVarInsn(25, context2.var("lexer"));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str19, "next", "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context2.var("lexer"));
        methodWriter.visitLdcInsn(16);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str19, "setToken", str18);
        methodWriter.visitJumpInsn(Opcodes.GOTO, quickEnd_2);
        methodWriter.visitLabel(quickElseIf_);
        methodWriter.visitVarInsn(21, context2.var("ch"));
        methodWriter.visitVarInsn(16, 93);
        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, quickElseIfEOI_);
        methodWriter.visitVarInsn(25, context2.var("lexer"));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str19, "next", "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context2.var("lexer"));
        methodWriter.visitLdcInsn(15);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str19, "setToken", str18);
        methodWriter.visitJumpInsn(Opcodes.GOTO, quickEnd_2);
        methodWriter.visitLabel(quickElseIfEOI_);
        methodWriter.visitVarInsn(21, context2.var("ch"));
        methodWriter.visitVarInsn(16, 26);
        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, quickElse_2);
        methodWriter.visitVarInsn(25, context2.var("lexer"));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str19, "next", "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context2.var("lexer"));
        methodWriter.visitLdcInsn(20);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str19, "setToken", str18);
        methodWriter.visitJumpInsn(Opcodes.GOTO, quickEnd_2);
        methodWriter.visitLabel(quickElse_2);
        methodWriter.visitVarInsn(25, context2.var("lexer"));
        methodWriter.visitLdcInsn(16);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str19, "nextToken", str18);
        methodWriter.visitLabel(quickEnd_2);
        methodWriter.visitVarInsn(25, context2.var("instance"));
        methodWriter.visitInsn(Opcodes.ARETURN);
        methodWriter.visitMaxs(5, context.variantIndex);
        methodWriter.visitEnd();
    }

    private void _deserialze(ClassWriter cw, Context context) {
        String str;
        String str2;
        String str3;
        Label continue_2;
        String str4;
        int fieldListSize;
        String str5;
        int i;
        Label end_;
        Label return_;
        Label reset_;
        int i2;
        Label notMatch_;
        int fieldListSize2;
        ASMDeserializerFactory aSMDeserializerFactory;
        Label reset_2;
        Label end_2;
        JavaBeanInfo beanInfo;
        Label super_;
        Label end_3;
        Label reset_3;
        Label super_2;
        ASMDeserializerFactory aSMDeserializerFactory2 = this;
        Context context2 = context;
        if (context.fieldInfoList.length != 0) {
            FieldInfo[] access$200 = context.fieldInfoList;
            int length = access$200.length;
            int i3 = 0;
            while (i3 < length) {
                FieldInfo fieldInfo = access$200[i3];
                Class<?> fieldClass = fieldInfo.fieldClass;
                Type fieldType = fieldInfo.fieldType;
                if (fieldClass != Character.TYPE) {
                    if (!Collection.class.isAssignableFrom(fieldClass) || ((fieldType instanceof ParameterizedType) && (((ParameterizedType) fieldType).getActualTypeArguments()[0] instanceof Class))) {
                        i3++;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            JavaBeanInfo beanInfo2 = context.beanInfo;
            FieldInfo[] unused = context2.fieldInfoList = beanInfo2.sortedFields;
            StringBuilder sb = new StringBuilder();
            sb.append("(L");
            String str6 = DefaultJSONParser;
            sb.append(str6);
            sb.append(";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;");
            MethodWriter methodWriter = new MethodWriter(cw, 1, "deserialze", sb.toString(), (String) null, (String[]) null);
            Label reset_4 = new Label();
            Label super_3 = new Label();
            Label return_2 = new Label();
            Label end_4 = new Label();
            aSMDeserializerFactory2.defineVarLexer(context2, methodWriter);
            Label next_ = new Label();
            String str7 = "lexer";
            methodWriter.visitVarInsn(25, context2.var(str7));
            String str8 = JSONLexerBase;
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str8, "token", "()I");
            methodWriter.visitLdcInsn(14);
            methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, next_);
            int i4 = beanInfo2.parserFeatures;
            Feature feature = Feature.SupportArrayToBean;
            if ((i4 & feature.mask) == 0) {
                methodWriter.visitVarInsn(25, context2.var(str7));
                methodWriter.visitVarInsn(21, 4);
                methodWriter.visitLdcInsn(Integer.valueOf(feature.mask));
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str8, "isEnabled", "(II)Z");
                methodWriter.visitJumpInsn(Opcodes.IFEQ, next_);
            }
            methodWriter.visitVarInsn(25, 0);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitVarInsn(25, 2);
            methodWriter.visitVarInsn(25, 3);
            methodWriter.visitInsn(1);
            methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, context.className, "deserialzeArrayMapping", "(L" + str6 + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
            methodWriter.visitInsn(Opcodes.ARETURN);
            methodWriter.visitLabel(next_);
            methodWriter.visitVarInsn(25, context2.var(str7));
            methodWriter.visitLdcInsn(Integer.valueOf(Feature.SortFeidFastMatch.mask));
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str8, "isEnabled", "(I)Z");
            Label continue_ = new Label();
            methodWriter.visitJumpInsn(Opcodes.IFNE, continue_);
            methodWriter.visitJumpInsn(200, super_3);
            methodWriter.visitLabel(continue_);
            methodWriter.visitVarInsn(25, context2.var(str7));
            methodWriter.visitLdcInsn(context.clazz.getName());
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str8, "scanType", "(Ljava/lang/String;)I");
            methodWriter.visitLdcInsn(-1);
            Label continue_22 = new Label();
            methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, continue_22);
            methodWriter.visitJumpInsn(200, super_3);
            methodWriter.visitLabel(continue_22);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
            methodWriter.visitVarInsn(58, context2.var("mark_context"));
            methodWriter.visitInsn(3);
            methodWriter.visitVarInsn(54, context2.var("matchedCount"));
            aSMDeserializerFactory2._createInstance(context2, (MethodVisitor) methodWriter);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
            methodWriter.visitVarInsn(58, context2.var("context"));
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitVarInsn(25, context2.var("context"));
            methodWriter.visitVarInsn(25, context2.var("instance"));
            methodWriter.visitVarInsn(25, 3);
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "setContext", "(" + ASMUtils.desc((Class<?>) ParseContext.class) + "Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.desc((Class<?>) ParseContext.class));
            methodWriter.visitVarInsn(58, context2.var("childContext"));
            methodWriter.visitVarInsn(25, context2.var(str7));
            String str9 = "matchStat";
            String str10 = "I";
            methodWriter.visitFieldInsn(180, str8, str9, str10);
            methodWriter.visitLdcInsn(4);
            methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, return_2);
            methodWriter.visitInsn(3);
            methodWriter.visitIntInsn(54, context2.var(str9));
            int fieldListSize3 = context.fieldInfoList.length;
            int i5 = 0;
            while (i5 < fieldListSize3) {
                methodWriter.visitInsn(3);
                methodWriter.visitVarInsn(54, context2.var("_asm_flag_" + (i5 / 32)));
                i5 += 32;
                continue_22 = continue_22;
            }
            Label continue_23 = continue_22;
            methodWriter.visitVarInsn(25, context2.var(str7));
            methodWriter.visitLdcInsn(Integer.valueOf(Feature.InitStringFieldAsEmpty.mask));
            Label return_3 = return_2;
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "isEnabled", "(I)Z");
            methodWriter.visitIntInsn(54, context2.var("initStringFieldAsEmpty"));
            int i6 = 0;
            while (true) {
                str = "_asm";
                if (i6 >= fieldListSize3) {
                    break;
                }
                FieldInfo fieldInfo2 = context.fieldInfoList[i6];
                Class<?> fieldClass2 = fieldInfo2.fieldClass;
                Label continue_3 = continue_;
                if (fieldClass2 == Boolean.TYPE || fieldClass2 == Byte.TYPE || fieldClass2 == Short.TYPE) {
                    end_3 = end_4;
                    beanInfo = beanInfo2;
                    reset_3 = reset_4;
                    super_2 = super_3;
                } else if (fieldClass2 == Integer.TYPE) {
                    end_3 = end_4;
                    beanInfo = beanInfo2;
                    reset_3 = reset_4;
                    super_2 = super_3;
                } else {
                    if (fieldClass2 == Long.TYPE) {
                        methodWriter.visitInsn(9);
                        StringBuilder sb2 = new StringBuilder();
                        beanInfo = beanInfo2;
                        sb2.append(fieldInfo2.name);
                        sb2.append(str);
                        methodWriter.visitVarInsn(55, context2.var(sb2.toString(), 2));
                        end_2 = end_4;
                        reset_2 = reset_4;
                        super_ = super_3;
                    } else {
                        beanInfo = beanInfo2;
                        if (fieldClass2 == Float.TYPE) {
                            methodWriter.visitInsn(11);
                            methodWriter.visitVarInsn(56, context2.var(fieldInfo2.name + str));
                            end_2 = end_4;
                            reset_2 = reset_4;
                            super_ = super_3;
                        } else if (fieldClass2 == Double.TYPE) {
                            methodWriter.visitInsn(14);
                            methodWriter.visitVarInsn(57, context2.var(fieldInfo2.name + str, 2));
                            end_2 = end_4;
                            reset_2 = reset_4;
                            super_ = super_3;
                        } else {
                            if (fieldClass2 == String.class) {
                                Label flagEnd_ = new Label();
                                Label flagElse_ = new Label();
                                super_ = super_3;
                                end_2 = end_4;
                                methodWriter.visitVarInsn(21, context2.var("initStringFieldAsEmpty"));
                                methodWriter.visitJumpInsn(Opcodes.IFEQ, flagElse_);
                                aSMDeserializerFactory2._setFlag(methodWriter, context2, i6);
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                reset_2 = reset_4;
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "stringDefaultValue", "()Ljava/lang/String;");
                                methodWriter.visitJumpInsn(Opcodes.GOTO, flagEnd_);
                                methodWriter.visitLabel(flagElse_);
                                methodWriter.visitInsn(1);
                                methodWriter.visitLabel(flagEnd_);
                            } else {
                                end_2 = end_4;
                                reset_2 = reset_4;
                                super_ = super_3;
                                methodWriter.visitInsn(1);
                            }
                            methodWriter.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass2));
                            methodWriter.visitVarInsn(58, context2.var(fieldInfo2.name + str));
                        }
                    }
                    i6++;
                    aSMDeserializerFactory2 = this;
                    continue_ = continue_3;
                    super_3 = super_;
                    beanInfo2 = beanInfo;
                    end_4 = end_2;
                    reset_4 = reset_2;
                }
                methodWriter.visitInsn(3);
                methodWriter.visitVarInsn(54, context2.var(fieldInfo2.name + str));
                i6++;
                aSMDeserializerFactory2 = this;
                continue_ = continue_3;
                super_3 = super_;
                beanInfo2 = beanInfo;
                end_4 = end_2;
                reset_4 = reset_2;
            }
            Label end_5 = end_4;
            Label label = continue_;
            JavaBeanInfo javaBeanInfo = beanInfo2;
            Label reset_5 = reset_4;
            Label super_4 = super_3;
            int i7 = 0;
            while (i7 < fieldListSize3) {
                FieldInfo fieldInfo3 = context.fieldInfoList[i7];
                Class<?> fieldClass3 = fieldInfo3.fieldClass;
                Type fieldType2 = fieldInfo3.fieldType;
                Label notMatch_2 = new Label();
                if (fieldClass3 == Boolean.TYPE) {
                    methodWriter.visitVarInsn(25, context2.var(str7));
                    methodWriter.visitVarInsn(25, 0);
                    String access$300 = context.className;
                    StringBuilder sb3 = new StringBuilder();
                    fieldListSize2 = fieldListSize3;
                    sb3.append(fieldInfo3.name);
                    sb3.append("_asm_prefix__");
                    methodWriter.visitFieldInsn(180, access$300, sb3.toString(), "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldBoolean", "([C)Z");
                    methodWriter.visitVarInsn(54, context2.var(fieldInfo3.name + str));
                    i2 = i7;
                    Type type = fieldType2;
                    notMatch_ = notMatch_2;
                    aSMDeserializerFactory = this;
                } else {
                    fieldListSize2 = fieldListSize3;
                    if (fieldClass3 == Byte.TYPE) {
                        notMatch_ = notMatch_2;
                        methodWriter.visitVarInsn(25, context2.var(str7));
                        methodWriter.visitVarInsn(25, 0);
                        String access$3002 = context.className;
                        StringBuilder sb4 = new StringBuilder();
                        i2 = i7;
                        sb4.append(fieldInfo3.name);
                        sb4.append("_asm_prefix__");
                        methodWriter.visitFieldInsn(180, access$3002, sb4.toString(), "[C");
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                        methodWriter.visitVarInsn(54, context2.var(fieldInfo3.name + str));
                        Type type2 = fieldType2;
                        aSMDeserializerFactory = this;
                    } else {
                        i2 = i7;
                        notMatch_ = notMatch_2;
                        if (fieldClass3 == Byte.class) {
                            methodWriter.visitVarInsn(25, context2.var(str7));
                            methodWriter.visitVarInsn(25, 0);
                            String access$3003 = context.className;
                            StringBuilder sb5 = new StringBuilder();
                            Type type3 = fieldType2;
                            sb5.append(fieldInfo3.name);
                            sb5.append("_asm_prefix__");
                            methodWriter.visitFieldInsn(180, access$3003, sb5.toString(), "[C");
                            String str11 = JSONLexerBase;
                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str11, "scanFieldInt", "([C)I");
                            methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                            methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                            Label valueNullEnd_ = new Label();
                            methodWriter.visitVarInsn(25, context2.var(str7));
                            methodWriter.visitFieldInsn(180, str11, str9, str10);
                            methodWriter.visitLdcInsn(5);
                            methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_);
                            methodWriter.visitInsn(1);
                            methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                            methodWriter.visitLabel(valueNullEnd_);
                            aSMDeserializerFactory = this;
                        } else {
                            Type fieldType3 = fieldType2;
                            if (fieldClass3 == Short.TYPE) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                                methodWriter.visitVarInsn(54, context2.var(fieldInfo3.name + str));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == Short.class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                String str12 = JSONLexerBase;
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str12, "scanFieldInt", "([C)I");
                                methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                Label valueNullEnd_2 = new Label();
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitFieldInsn(180, str12, str9, str10);
                                methodWriter.visitLdcInsn(5);
                                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_2);
                                methodWriter.visitInsn(1);
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                methodWriter.visitLabel(valueNullEnd_2);
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == Integer.TYPE) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                                methodWriter.visitVarInsn(54, context2.var(fieldInfo3.name + str));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == Integer.class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                String str13 = JSONLexerBase;
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str13, "scanFieldInt", "([C)I");
                                methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                Label valueNullEnd_3 = new Label();
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitFieldInsn(180, str13, str9, str10);
                                methodWriter.visitLdcInsn(5);
                                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_3);
                                methodWriter.visitInsn(1);
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                methodWriter.visitLabel(valueNullEnd_3);
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == Long.TYPE) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldLong", "([C)J");
                                methodWriter.visitVarInsn(55, context2.var(fieldInfo3.name + str, 2));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == Long.class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                String str14 = JSONLexerBase;
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str14, "scanFieldLong", "([C)J");
                                methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                Label valueNullEnd_4 = new Label();
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitFieldInsn(180, str14, str9, str10);
                                methodWriter.visitLdcInsn(5);
                                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_4);
                                methodWriter.visitInsn(1);
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                methodWriter.visitLabel(valueNullEnd_4);
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == Float.TYPE) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloat", "([C)F");
                                methodWriter.visitVarInsn(56, context2.var(fieldInfo3.name + str));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == Float.class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                String str15 = JSONLexerBase;
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str15, "scanFieldFloat", "([C)F");
                                methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                Label valueNullEnd_5 = new Label();
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitFieldInsn(180, str15, str9, str10);
                                methodWriter.visitLdcInsn(5);
                                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_5);
                                methodWriter.visitInsn(1);
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                methodWriter.visitLabel(valueNullEnd_5);
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == Double.TYPE) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDouble", "([C)D");
                                methodWriter.visitVarInsn(57, context2.var(fieldInfo3.name + str, 2));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == Double.class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                String str16 = JSONLexerBase;
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str16, "scanFieldDouble", "([C)D");
                                methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                Label valueNullEnd_6 = new Label();
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitFieldInsn(180, str16, str9, str10);
                                methodWriter.visitLdcInsn(5);
                                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_6);
                                methodWriter.visitInsn(1);
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                methodWriter.visitLabel(valueNullEnd_6);
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == String.class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldString", "([C)Ljava/lang/String;");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == Date.class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDate", "([C)Ljava/util/Date;");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == UUID.class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldUUID", "([C)Ljava/util/UUID;");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == BigDecimal.class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDecimal", "([C)Ljava/math/BigDecimal;");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == BigInteger.class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldBigInteger", "([C)Ljava/math/BigInteger;");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == int[].class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldIntArray", "([C)[I");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == float[].class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloatArray", "([C)[F");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3 == float[][].class) {
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloatArray2", "([C)[[F");
                                methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                aSMDeserializerFactory = this;
                            } else if (fieldClass3.isEnum()) {
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitVarInsn(25, context2.var(str7));
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                aSMDeserializerFactory = this;
                                aSMDeserializerFactory._getFieldDeser(context2, methodWriter, fieldInfo3);
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "scanEnum", "(L" + JSONLexerBase + ";[C" + ASMUtils.desc((Class<?>) ObjectDeserializer.class) + ")Ljava/lang/Enum;");
                                methodWriter.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass3));
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append(fieldInfo3.name);
                                sb6.append(str);
                                methodWriter.visitVarInsn(58, context2.var(sb6.toString()));
                            } else {
                                aSMDeserializerFactory = this;
                                if (Collection.class.isAssignableFrom(fieldClass3)) {
                                    methodWriter.visitVarInsn(25, context2.var(str7));
                                    methodWriter.visitVarInsn(25, 0);
                                    methodWriter.visitFieldInsn(180, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                    Class<?> itemClass = TypeUtils.getCollectionItemClass(fieldType3);
                                    if (itemClass == String.class) {
                                        methodWriter.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldClass3)));
                                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldStringArray", "([CLjava/lang/Class;)" + ASMUtils.desc((Class<?>) Collection.class));
                                        methodWriter.visitVarInsn(58, context2.var(fieldInfo3.name + str));
                                    } else {
                                        Label end_6 = end_5;
                                        Label label2 = reset_5;
                                        Label label3 = notMatch_;
                                        i = i2;
                                        str2 = str7;
                                        fieldListSize = fieldListSize2;
                                        str4 = str10;
                                        str3 = str;
                                        str5 = str9;
                                        continue_2 = continue_23;
                                        Class<?> cls = itemClass;
                                        return_ = return_3;
                                        Class<?> cls2 = itemClass;
                                        end_ = end_6;
                                        _deserialze_list_obj(context, methodWriter, reset_5, fieldInfo3, fieldClass3, cls, i);
                                        if (i == fieldListSize - 1) {
                                            reset_ = reset_5;
                                            aSMDeserializerFactory._deserialize_endCheck(context2, methodWriter, reset_);
                                        } else {
                                            reset_ = reset_5;
                                        }
                                    }
                                } else {
                                    str4 = str10;
                                    str5 = str9;
                                    str3 = str;
                                    continue_2 = continue_23;
                                    return_ = return_3;
                                    end_ = end_5;
                                    reset_ = reset_5;
                                    fieldListSize = fieldListSize2;
                                    i = i2;
                                    str2 = str7;
                                    _deserialze_obj(context, methodWriter, reset_, fieldInfo3, fieldClass3, i);
                                    if (i == fieldListSize - 1) {
                                        aSMDeserializerFactory._deserialize_endCheck(context2, methodWriter, reset_);
                                    }
                                }
                                i7 = i + 1;
                                reset_5 = reset_;
                                return_3 = return_;
                                str9 = str5;
                                fieldListSize3 = fieldListSize;
                                str10 = str4;
                                continue_23 = continue_2;
                                str = str3;
                                str7 = str2;
                                end_5 = end_;
                            }
                        }
                    }
                }
                methodWriter.visitVarInsn(25, context2.var(str7));
                String str17 = JSONLexerBase;
                methodWriter.visitFieldInsn(180, str17, str9, str10);
                Label flag_ = new Label();
                methodWriter.visitJumpInsn(Opcodes.IFLE, flag_);
                i = i2;
                aSMDeserializerFactory._setFlag(methodWriter, context2, i);
                methodWriter.visitLabel(flag_);
                methodWriter.visitVarInsn(25, context2.var(str7));
                methodWriter.visitFieldInsn(180, str17, str9, str10);
                methodWriter.visitInsn(89);
                methodWriter.visitVarInsn(54, context2.var(str9));
                methodWriter.visitLdcInsn(-1);
                Label reset_6 = reset_5;
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, reset_6);
                methodWriter.visitVarInsn(25, context2.var(str7));
                methodWriter.visitFieldInsn(180, str17, str9, str10);
                Label notMatch_3 = notMatch_;
                methodWriter.visitJumpInsn(Opcodes.IFLE, notMatch_3);
                methodWriter.visitVarInsn(21, context2.var("matchedCount"));
                methodWriter.visitInsn(4);
                methodWriter.visitInsn(96);
                methodWriter.visitVarInsn(54, context2.var("matchedCount"));
                methodWriter.visitVarInsn(25, context2.var(str7));
                methodWriter.visitFieldInsn(180, str17, str9, str10);
                methodWriter.visitLdcInsn(4);
                Label end_7 = end_5;
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, end_7);
                methodWriter.visitLabel(notMatch_3);
                if (i == fieldListSize2 - 1) {
                    str2 = str7;
                    methodWriter.visitVarInsn(25, context2.var(str7));
                    methodWriter.visitFieldInsn(180, str17, str9, str10);
                    methodWriter.visitLdcInsn(4);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, reset_6);
                    str4 = str10;
                    str5 = str9;
                    str3 = str;
                    continue_2 = continue_23;
                    return_ = return_3;
                    fieldListSize = fieldListSize2;
                    Label label4 = reset_6;
                    end_ = end_7;
                    reset_ = label4;
                } else {
                    str2 = str7;
                    str4 = str10;
                    str5 = str9;
                    str3 = str;
                    continue_2 = continue_23;
                    return_ = return_3;
                    fieldListSize = fieldListSize2;
                    Label label5 = reset_6;
                    end_ = end_7;
                    reset_ = label5;
                }
                i7 = i + 1;
                reset_5 = reset_;
                return_3 = return_;
                str9 = str5;
                fieldListSize3 = fieldListSize;
                str10 = str4;
                continue_23 = continue_2;
                str = str3;
                str7 = str2;
                end_5 = end_;
            }
            int fieldListSize4 = fieldListSize3;
            int i8 = i7;
            Label label6 = continue_23;
            Label return_4 = return_3;
            Label reset_7 = reset_5;
            methodWriter.visitLabel(end_5);
            if (!context.clazz.isInterface() && !Modifier.isAbstract(context.clazz.getModifiers())) {
                _batchSet(context2, methodWriter);
            }
            methodWriter.visitLabel(return_4);
            _setContext(context2, methodWriter);
            methodWriter.visitVarInsn(25, context2.var("instance"));
            Method buildMethod = context.beanInfo.buildMethod;
            if (buildMethod != null) {
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(context.getInstClass()), buildMethod.getName(), "()" + ASMUtils.desc(buildMethod.getReturnType()));
            }
            methodWriter.visitInsn(Opcodes.ARETURN);
            methodWriter.visitLabel(reset_7);
            _batchSet(context2, methodWriter);
            methodWriter.visitVarInsn(25, 0);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitVarInsn(25, 2);
            methodWriter.visitVarInsn(25, 3);
            methodWriter.visitVarInsn(25, context2.var("instance"));
            methodWriter.visitVarInsn(21, 4);
            int flagSize = fieldListSize4 / 32;
            if (!(fieldListSize4 == 0 || fieldListSize4 % 32 == 0)) {
                flagSize++;
            }
            if (flagSize == 1) {
                methodWriter.visitInsn(4);
            } else {
                methodWriter.visitIntInsn(16, flagSize);
            }
            methodWriter.visitIntInsn(188, 10);
            for (int i9 = 0; i9 < flagSize; i9++) {
                methodWriter.visitInsn(89);
                if (i9 == 0) {
                    methodWriter.visitInsn(3);
                } else if (i9 == 1) {
                    methodWriter.visitInsn(4);
                } else {
                    methodWriter.visitIntInsn(16, i9);
                }
                methodWriter.visitVarInsn(21, context2.var("_asm_flag_" + i9));
                methodWriter.visitInsn(79);
            }
            String type4 = ASMUtils.type(JavaBeanDeserializer.class);
            StringBuilder sb7 = new StringBuilder();
            sb7.append("(L");
            String str18 = DefaultJSONParser;
            sb7.append(str18);
            sb7.append(";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;I[I)Ljava/lang/Object;");
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, type4, "parseRest", sb7.toString());
            methodWriter.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(context.clazz));
            methodWriter.visitInsn(Opcodes.ARETURN);
            methodWriter.visitLabel(super_4);
            methodWriter.visitVarInsn(25, 0);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitVarInsn(25, 2);
            methodWriter.visitVarInsn(25, 3);
            methodWriter.visitVarInsn(21, 4);
            methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "deserialze", "(L" + str18 + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;");
            methodWriter.visitInsn(Opcodes.ARETURN);
            methodWriter.visitMaxs(10, context.variantIndex);
            methodWriter.visitEnd();
        }
    }

    private void defineVarLexer(Context context, MethodVisitor mw) {
        mw.visitVarInsn(25, 1);
        mw.visitFieldInsn(180, DefaultJSONParser, "lexer", ASMUtils.desc((Class<?>) JSONLexer.class));
        mw.visitTypeInsn(Opcodes.CHECKCAST, JSONLexerBase);
        mw.visitVarInsn(58, context.var("lexer"));
    }

    private void _createInstance(Context context, MethodVisitor mw) {
        Class<JavaBeanDeserializer> cls = JavaBeanDeserializer.class;
        Constructor<?> defaultConstructor = context.beanInfo.defaultConstructor;
        if (Modifier.isPublic(defaultConstructor.getModifiers())) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(context.getInstClass()));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(defaultConstructor.getDeclaringClass()), "<init>", "()V");
            mw.visitVarInsn(58, context.var("instance"));
            return;
        }
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(180, ASMUtils.type(cls), "clazz", "Ljava/lang/Class;");
        String type = ASMUtils.type(cls);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, type, "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;");
        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(context.getInstClass()));
        mw.visitVarInsn(58, context.var("instance"));
    }

    private void _batchSet(Context context, MethodVisitor mw) {
        _batchSet(context, mw, true);
    }

    private void _batchSet(Context context, MethodVisitor mw, boolean flag) {
        int size = context.fieldInfoList.length;
        for (int i = 0; i < size; i++) {
            Label notSet_ = new Label();
            if (flag) {
                _isFlag(mw, context, i, notSet_);
            }
            _loadAndSet(context, mw, context.fieldInfoList[i]);
            if (flag) {
                mw.visitLabel(notSet_);
            }
        }
    }

    private void _loadAndSet(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        Type type = String.class;
        Class<?> fieldClass = fieldInfo.fieldClass;
        Type fieldType = fieldInfo.fieldType;
        if (fieldClass == Boolean.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(21, context.var(fieldInfo.name + "_asm"));
            _set(context, mw, fieldInfo);
        } else if (fieldClass == Byte.TYPE || fieldClass == Short.TYPE || fieldClass == Integer.TYPE || fieldClass == Character.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(21, context.var(fieldInfo.name + "_asm"));
            _set(context, mw, fieldInfo);
        } else if (fieldClass == Long.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(22, context.var(fieldInfo.name + "_asm", 2));
            if (fieldInfo.method != null) {
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(context.getInstClass()), fieldInfo.method.getName(), ASMUtils.desc(fieldInfo.method));
                if (!fieldInfo.method.getReturnType().equals(Void.TYPE)) {
                    mw.visitInsn(87);
                    return;
                }
                return;
            }
            mw.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
        } else if (fieldClass == Float.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(23, context.var(fieldInfo.name + "_asm"));
            _set(context, mw, fieldInfo);
        } else if (fieldClass == Double.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(24, context.var(fieldInfo.name + "_asm", 2));
            _set(context, mw, fieldInfo);
        } else if (fieldClass == type) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            _set(context, mw, fieldInfo);
        } else if (fieldClass.isEnum()) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            _set(context, mw, fieldInfo);
        } else if (Collection.class.isAssignableFrom(fieldClass)) {
            mw.visitVarInsn(25, context.var("instance"));
            if (TypeUtils.getCollectionItemClass(fieldType) == type) {
                mw.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
                mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
            } else {
                mw.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            }
            _set(context, mw, fieldInfo);
        } else {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            _set(context, mw, fieldInfo);
        }
    }

    private void _set(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method != null) {
            mw.visitMethodInsn(method.getDeclaringClass().isInterface() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL, ASMUtils.type(fieldInfo.declaringClass), method.getName(), ASMUtils.desc(method));
            if (!fieldInfo.method.getReturnType().equals(Void.TYPE)) {
                mw.visitInsn(87);
                return;
            }
            return;
        }
        mw.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
    }

    private void _setContext(Context context, MethodVisitor mw) {
        Class<ParseContext> cls = ParseContext.class;
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, context.var("context"));
        String str = DefaultJSONParser;
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "setContext", "(" + ASMUtils.desc((Class<?>) cls) + ")V");
        Label endIf_ = new Label();
        mw.visitVarInsn(25, context.var("childContext"));
        mw.visitJumpInsn(Opcodes.IFNULL, endIf_);
        mw.visitVarInsn(25, context.var("childContext"));
        mw.visitVarInsn(25, context.var("instance"));
        mw.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(cls), "object", "Ljava/lang/Object;");
        mw.visitLabel(endIf_);
    }

    private void _deserialize_endCheck(Context context, MethodVisitor mw, Label reset_) {
        mw.visitIntInsn(21, context.var("matchedCount"));
        mw.visitJumpInsn(Opcodes.IFLE, reset_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        mw.visitLdcInsn(13);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, reset_);
        _quickNextTokenComma(context, mw);
    }

    private void _deserialze_list_obj(Context context, MethodVisitor mw, Label reset_, FieldInfo fieldInfo, Class<?> fieldClass, Class<?> itemType, int i) {
        String str;
        Label _end_if;
        String str2;
        String str3;
        String str4;
        String str5;
        Context context2 = context;
        MethodVisitor methodVisitor = mw;
        FieldInfo fieldInfo2 = fieldInfo;
        Class<?> cls = fieldClass;
        Class<?> cls2 = itemType;
        int i2 = i;
        Label _end_if2 = new Label();
        String str6 = JSONLexerBase;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "matchField", "([C)Z");
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, _end_if2);
        _setFlag(methodVisitor, context2, i2);
        Label valueNotNull_ = new Label();
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "token", "()I");
        methodVisitor.visitLdcInsn(8);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, valueNotNull_);
        Class<ObjectDeserializer> cls3 = ObjectDeserializer.class;
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "nextToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, _end_if2);
        methodVisitor.visitLabel(valueNotNull_);
        Label storeCollection_ = new Label();
        Label endSet_ = new Label();
        Label label = valueNotNull_;
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "token", "()I");
        methodVisitor.visitLdcInsn(21);
        Label endSet_2 = endSet_;
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, endSet_2);
        Label _end_if3 = _end_if2;
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitLdcInsn(14);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "nextToken", "(I)V");
        _newCollection(methodVisitor, cls, i2, true);
        Label storeCollection_2 = storeCollection_;
        methodVisitor.visitJumpInsn(Opcodes.GOTO, storeCollection_2);
        methodVisitor.visitLabel(endSet_2);
        Label label2 = endSet_2;
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "token", "()I");
        methodVisitor.visitLdcInsn(14);
        Label lbacketNormal_ = new Label();
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPEQ, lbacketNormal_);
        String str7 = "nextToken";
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "token", "()I");
        methodVisitor.visitLdcInsn(12);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, reset_);
        _newCollection(methodVisitor, cls, i2, false);
        String str8 = "token";
        methodVisitor.visitVarInsn(58, context2.var(fieldInfo2.name + "_asm"));
        Class<?> cls4 = itemType;
        _getCollectionFieldItemDeser(context2, methodVisitor, fieldInfo2, cls4);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(itemType)));
        methodVisitor.visitInsn(3);
        String str9 = str6;
        String str10 = "(I)V";
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        String type = ASMUtils.type(cls3);
        StringBuilder sb = new StringBuilder();
        sb.append("(L");
        String str11 = DefaultJSONParser;
        sb.append(str11);
        sb.append(";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        String str12 = str11;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, type, "deserialze", sb.toString());
        methodVisitor.visitVarInsn(58, context2.var("list_item_value"));
        methodVisitor.visitVarInsn(25, context2.var(fieldInfo2.name + "_asm"));
        methodVisitor.visitVarInsn(25, context2.var("list_item_value"));
        if (fieldClass.isInterface()) {
            str = "list_item_value";
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(fieldClass), "add", "(Ljava/lang/Object;)Z");
        } else {
            str = "list_item_value";
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(fieldClass), "add", "(Ljava/lang/Object;)Z");
        }
        methodVisitor.visitInsn(87);
        Label _end_if4 = _end_if3;
        methodVisitor.visitJumpInsn(Opcodes.GOTO, _end_if4);
        methodVisitor.visitLabel(lbacketNormal_);
        _newCollection(methodVisitor, cls, i2, false);
        methodVisitor.visitLabel(storeCollection_2);
        methodVisitor.visitVarInsn(58, context2.var(fieldInfo2.name + "_asm"));
        boolean isPrimitive = ParserConfig.isPrimitive2(fieldInfo2.fieldClass);
        _getCollectionFieldItemDeser(context2, methodVisitor, fieldInfo2, cls4);
        if (isPrimitive) {
            Label label3 = storeCollection_2;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(cls3), "getFastMatchToken", "()I");
            methodVisitor.visitVarInsn(54, context2.var("fastMatchToken"));
            methodVisitor.visitVarInsn(25, context2.var("lexer"));
            methodVisitor.visitVarInsn(21, context2.var("fastMatchToken"));
            str2 = str7;
            str4 = str9;
            str3 = str10;
            Label label4 = lbacketNormal_;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str4, str2, str3);
            _end_if = _end_if4;
        } else {
            str2 = str7;
            str4 = str9;
            str3 = str10;
            Label label5 = lbacketNormal_;
            methodVisitor.visitInsn(87);
            methodVisitor.visitLdcInsn(12);
            _end_if = _end_if4;
            methodVisitor.visitVarInsn(54, context2.var("fastMatchToken"));
            _quickNextToken(context2, methodVisitor, 12);
        }
        methodVisitor.visitVarInsn(25, 1);
        String str13 = str3;
        String str14 = str12;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str14, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
        methodVisitor.visitVarInsn(58, context2.var("listContext"));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context2.var(fieldInfo2.name + "_asm"));
        methodVisitor.visitLdcInsn(fieldInfo2.name);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str14, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.desc((Class<?>) ParseContext.class));
        methodVisitor.visitInsn(87);
        Label loop_ = new Label();
        Label loop_end_ = new Label();
        methodVisitor.visitInsn(3);
        String str15 = str2;
        methodVisitor.visitVarInsn(54, context2.var("i"));
        methodVisitor.visitLabel(loop_);
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        String str16 = str8;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str4, str16, "()I");
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPEQ, loop_end_);
        Label loop_end_2 = loop_end_;
        methodVisitor.visitVarInsn(25, 0);
        String access$300 = context.className;
        StringBuilder sb2 = new StringBuilder();
        String str17 = "fastMatchToken";
        sb2.append(fieldInfo2.name);
        sb2.append("_asm_list_item_deser__");
        boolean isPrimitive2 = isPrimitive;
        methodVisitor.visitFieldInsn(180, access$300, sb2.toString(), ASMUtils.desc((Class<?>) cls3));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(itemType)));
        methodVisitor.visitVarInsn(21, context2.var("i"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        String type2 = ASMUtils.type(cls3);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, type2, "deserialze", "(L" + str14 + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        String str18 = str;
        methodVisitor.visitVarInsn(58, context2.var(str18));
        methodVisitor.visitIincInsn(context2.var("i"), 1);
        methodVisitor.visitVarInsn(25, context2.var(fieldInfo2.name + "_asm"));
        methodVisitor.visitVarInsn(25, context2.var(str18));
        if (fieldClass.isInterface()) {
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(fieldClass), "add", "(Ljava/lang/Object;)Z");
        } else {
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(fieldClass), "add", "(Ljava/lang/Object;)Z");
        }
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context2.var(fieldInfo2.name + "_asm"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str14, "checkListResolve", "(Ljava/util/Collection;)V");
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str4, str16, "()I");
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, loop_);
        if (isPrimitive2) {
            methodVisitor.visitVarInsn(25, context2.var("lexer"));
            methodVisitor.visitVarInsn(21, context2.var(str17));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str4, str15, str13);
            str5 = str14;
        } else {
            str5 = str14;
            _quickNextToken(context2, methodVisitor, 12);
        }
        methodVisitor.visitJumpInsn(Opcodes.GOTO, loop_);
        methodVisitor.visitLabel(loop_end_2);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context2.var("listContext"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "setContext", "(" + ASMUtils.desc((Class<?>) ParseContext.class) + ")V");
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str4, str16, "()I");
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, reset_);
        _quickNextTokenComma(context, mw);
        methodVisitor.visitLabel(_end_if);
    }

    private void _quickNextToken(Context context, MethodVisitor mw, int token) {
        Label quickElse_ = new Label();
        Label quickEnd_ = new Label();
        mw.visitVarInsn(25, context.var("lexer"));
        String str = JSONLexerBase;
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "getCurrent", "()C");
        if (token == 12) {
            mw.visitVarInsn(16, 123);
        } else if (token == 14) {
            mw.visitVarInsn(16, 91);
        } else {
            throw new IllegalStateException();
        }
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, quickElse_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "next", "()C");
        mw.visitInsn(87);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(Integer.valueOf(token));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "setToken", "(I)V");
        mw.visitJumpInsn(Opcodes.GOTO, quickEnd_);
        mw.visitLabel(quickElse_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(Integer.valueOf(token));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "nextToken", "(I)V");
        mw.visitLabel(quickEnd_);
    }

    private void _quickNextTokenComma(Context context, MethodVisitor mw) {
        Context context2 = context;
        MethodVisitor methodVisitor = mw;
        Label quickElse_ = new Label();
        Label quickElseIf0_ = new Label();
        Label quickElseIf1_ = new Label();
        Label quickElseIf2_ = new Label();
        Label quickEnd_ = new Label();
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        String str = JSONLexerBase;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "getCurrent", "()C");
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(54, context2.var("ch"));
        methodVisitor.visitVarInsn(16, 44);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, quickElseIf0_);
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, quickEnd_);
        methodVisitor.visitLabel(quickElseIf0_);
        methodVisitor.visitVarInsn(21, context2.var("ch"));
        methodVisitor.visitVarInsn(16, 125);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, quickElseIf1_);
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitLdcInsn(13);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, quickEnd_);
        methodVisitor.visitLabel(quickElseIf1_);
        methodVisitor.visitVarInsn(21, context2.var("ch"));
        methodVisitor.visitVarInsn(16, 93);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, quickElseIf2_);
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, quickEnd_);
        methodVisitor.visitLabel(quickElseIf2_);
        methodVisitor.visitVarInsn(21, context2.var("ch"));
        methodVisitor.visitVarInsn(16, 26);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, quickElse_);
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitLdcInsn(20);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, quickEnd_);
        methodVisitor.visitLabel(quickElse_);
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "nextToken", "()V");
        methodVisitor.visitLabel(quickEnd_);
    }

    private void _getCollectionFieldItemDeser(Context context, MethodVisitor mw, FieldInfo fieldInfo, Class<?> itemType) {
        Class<ParserConfig> cls = ParserConfig.class;
        Class<ObjectDeserializer> cls2 = ObjectDeserializer.class;
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        String access$300 = context.className;
        mw.visitFieldInsn(180, access$300, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) cls2));
        mw.visitJumpInsn(199, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        String str = DefaultJSONParser;
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "getConfig", "()" + ASMUtils.desc((Class<?>) cls));
        mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(itemType)));
        String type = ASMUtils.type(cls);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, type, "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc((Class<?>) cls2));
        String access$3002 = context.className;
        mw.visitFieldInsn(Opcodes.PUTFIELD, access$3002, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) cls2));
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        String access$3003 = context.className;
        mw.visitFieldInsn(180, access$3003, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) cls2));
    }

    private void _newCollection(MethodVisitor mw, Class<?> fieldClass, int i, boolean set) {
        if (fieldClass.isAssignableFrom(ArrayList.class) && !set) {
            mw.visitTypeInsn(Opcodes.NEW, "java/util/ArrayList");
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V");
        } else if (fieldClass.isAssignableFrom(LinkedList.class) && !set) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(LinkedList.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(LinkedList.class), "<init>", "()V");
        } else if (fieldClass.isAssignableFrom(HashSet.class)) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(HashSet.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(HashSet.class), "<init>", "()V");
        } else if (fieldClass.isAssignableFrom(TreeSet.class)) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(TreeSet.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(TreeSet.class), "<init>", "()V");
        } else if (fieldClass.isAssignableFrom(LinkedHashSet.class)) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(LinkedHashSet.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(LinkedHashSet.class), "<init>", "()V");
        } else if (set) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(HashSet.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(HashSet.class), "<init>", "()V");
        } else {
            mw.visitVarInsn(25, 0);
            mw.visitLdcInsn(Integer.valueOf(i));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/reflect/Type;)Ljava/util/Collection;");
        }
        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
    }

    private void _deserialze_obj(Context context, MethodVisitor mw, Label reset_, FieldInfo fieldInfo, Class<?> fieldClass, int i) {
        Context context2 = context;
        MethodVisitor methodVisitor = mw;
        FieldInfo fieldInfo2 = fieldInfo;
        Class<FieldDeserializer> cls = FieldDeserializer.class;
        Class<ParseContext> cls2 = ParseContext.class;
        Class<DefaultJSONParser.ResolveTask> cls3 = DefaultJSONParser.ResolveTask.class;
        Label matched_ = new Label();
        Label _end_if = new Label();
        methodVisitor.visitVarInsn(25, context2.var("lexer"));
        methodVisitor.visitVarInsn(25, 0);
        String access$300 = context.className;
        methodVisitor.visitFieldInsn(180, access$300, fieldInfo2.name + "_asm_prefix__", "[C");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "matchField", "([C)Z");
        methodVisitor.visitJumpInsn(Opcodes.IFNE, matched_);
        methodVisitor.visitInsn(1);
        methodVisitor.visitVarInsn(58, context2.var(fieldInfo2.name + "_asm"));
        methodVisitor.visitJumpInsn(Opcodes.GOTO, _end_if);
        methodVisitor.visitLabel(matched_);
        _setFlag(methodVisitor, context2, i);
        methodVisitor.visitVarInsn(21, context2.var("matchedCount"));
        methodVisitor.visitInsn(4);
        methodVisitor.visitInsn(96);
        methodVisitor.visitVarInsn(54, context2.var("matchedCount"));
        _deserObject(context, mw, fieldInfo, fieldClass, i);
        methodVisitor.visitVarInsn(25, 1);
        String str = DefaultJSONParser;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "getResolveStatus", "()I");
        methodVisitor.visitLdcInsn(1);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, _end_if);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "getLastResolveTask", "()" + ASMUtils.desc((Class<?>) cls3));
        methodVisitor.visitVarInsn(58, context2.var("resolveTask"));
        methodVisitor.visitVarInsn(25, context2.var("resolveTask"));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "getContext", "()" + ASMUtils.desc((Class<?>) cls2));
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(cls3), "ownerContext", ASMUtils.desc((Class<?>) cls2));
        methodVisitor.visitVarInsn(25, context2.var("resolveTask"));
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitLdcInsn(fieldInfo2.name);
        String type = ASMUtils.type(JavaBeanDeserializer.class);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, type, "getFieldDeserializer", "(Ljava/lang/String;)" + ASMUtils.desc((Class<?>) cls));
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(cls3), "fieldDeserializer", ASMUtils.desc((Class<?>) cls));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(0);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "setResolveStatus", "(I)V");
        methodVisitor.visitLabel(_end_if);
    }

    private void _deserObject(Context context, MethodVisitor mw, FieldInfo fieldInfo, Class<?> fieldClass, int i) {
        Context context2 = context;
        MethodVisitor methodVisitor = mw;
        FieldInfo fieldInfo2 = fieldInfo;
        Class<JavaBeanDeserializer> cls = JavaBeanDeserializer.class;
        _getFieldDeser(context, mw, fieldInfo);
        Label instanceOfElse_ = new Label();
        Label instanceOfEnd_ = new Label();
        if ((fieldInfo2.parserFeatures & Feature.SupportArrayToBean.mask) != 0) {
            methodVisitor.visitInsn(89);
            methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, ASMUtils.type(cls));
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, instanceOfElse_);
            methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(cls));
            methodVisitor.visitVarInsn(25, 1);
            if (fieldInfo2.fieldType instanceof Class) {
                methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo2.fieldClass)));
            } else {
                methodVisitor.visitVarInsn(25, 0);
                methodVisitor.visitLdcInsn(Integer.valueOf(i));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(cls), "getFieldType", "(I)Ljava/lang/reflect/Type;");
            }
            methodVisitor.visitLdcInsn(fieldInfo2.name);
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.parserFeatures));
            String type = ASMUtils.type(cls);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, type, "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;");
            methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
            methodVisitor.visitVarInsn(58, context2.var(fieldInfo2.name + "_asm"));
            methodVisitor.visitJumpInsn(Opcodes.GOTO, instanceOfEnd_);
            methodVisitor.visitLabel(instanceOfElse_);
        }
        methodVisitor.visitVarInsn(25, 1);
        if (fieldInfo2.fieldType instanceof Class) {
            methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo2.fieldClass)));
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitLdcInsn(Integer.valueOf(i));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(cls), "getFieldType", "(I)Ljava/lang/reflect/Type;");
        }
        methodVisitor.visitLdcInsn(fieldInfo2.name);
        String type2 = ASMUtils.type(ObjectDeserializer.class);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, type2, "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
        methodVisitor.visitVarInsn(58, context2.var(fieldInfo2.name + "_asm"));
        methodVisitor.visitLabel(instanceOfEnd_);
    }

    private void _getFieldDeser(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        Class<ParserConfig> cls = ParserConfig.class;
        Class<ObjectDeserializer> cls2 = ObjectDeserializer.class;
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        String access$300 = context.className;
        mw.visitFieldInsn(180, access$300, fieldInfo.name + "_asm_deser__", ASMUtils.desc((Class<?>) cls2));
        mw.visitJumpInsn(199, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        String str = DefaultJSONParser;
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "getConfig", "()" + ASMUtils.desc((Class<?>) cls));
        mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        String type = ASMUtils.type(cls);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, type, "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc((Class<?>) cls2));
        String access$3002 = context.className;
        mw.visitFieldInsn(Opcodes.PUTFIELD, access$3002, fieldInfo.name + "_asm_deser__", ASMUtils.desc((Class<?>) cls2));
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        String access$3003 = context.className;
        mw.visitFieldInsn(180, access$3003, fieldInfo.name + "_asm_deser__", ASMUtils.desc((Class<?>) cls2));
    }

    public static class Context {
        static final int fieldName = 3;
        static final int parser = 1;
        static final int type = 2;
        /* access modifiers changed from: private */
        public final JavaBeanInfo beanInfo;
        /* access modifiers changed from: private */
        public final String className;
        /* access modifiers changed from: private */
        public final Class<?> clazz;
        /* access modifiers changed from: private */
        public FieldInfo[] fieldInfoList;
        /* access modifiers changed from: private */
        public int variantIndex = -1;
        private final Map<String, Integer> variants = new HashMap();

        public Context(String className2, ParserConfig config, JavaBeanInfo beanInfo2, int initVariantIndex) {
            this.className = className2;
            this.clazz = beanInfo2.clazz;
            this.variantIndex = initVariantIndex;
            this.beanInfo = beanInfo2;
            this.fieldInfoList = beanInfo2.fields;
        }

        public Class<?> getInstClass() {
            Class<?> instClass = this.beanInfo.builderClass;
            if (instClass == null) {
                return this.clazz;
            }
            return instClass;
        }

        public int var(String name, int increment) {
            if (this.variants.get(name) == null) {
                this.variants.put(name, Integer.valueOf(this.variantIndex));
                this.variantIndex += increment;
            }
            return this.variants.get(name).intValue();
        }

        public int var(String name) {
            if (this.variants.get(name) == null) {
                Map<String, Integer> map = this.variants;
                int i = this.variantIndex;
                this.variantIndex = i + 1;
                map.put(name, Integer.valueOf(i));
            }
            return this.variants.get(name).intValue();
        }
    }

    private void _init(ClassWriter cw, Context context) {
        ClassWriter classWriter = cw;
        Class<ObjectDeserializer> cls = ObjectDeserializer.class;
        Class<JavaBeanInfo> cls2 = JavaBeanInfo.class;
        Class<ParserConfig> cls3 = ParserConfig.class;
        for (FieldInfo fieldInfo : context.fieldInfoList) {
            new FieldWriter(classWriter, 1, fieldInfo.name + "_asm_prefix__", "[C").visitEnd();
        }
        for (FieldInfo fieldInfo2 : context.fieldInfoList) {
            Class<?> fieldClass = fieldInfo2.fieldClass;
            if (!fieldClass.isPrimitive()) {
                if (Collection.class.isAssignableFrom(fieldClass)) {
                    new FieldWriter(classWriter, 1, fieldInfo2.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) cls)).visitEnd();
                } else {
                    new FieldWriter(classWriter, 1, fieldInfo2.name + "_asm_deser__", ASMUtils.desc((Class<?>) cls)).visitEnd();
                }
            }
        }
        MethodVisitor mw = new MethodWriter(cw, 1, "<init>", "(" + ASMUtils.desc((Class<?>) cls3) + ASMUtils.desc((Class<?>) cls2) + ")V", (String) null, (String[]) null);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "<init>", "(" + ASMUtils.desc((Class<?>) cls3) + ASMUtils.desc((Class<?>) cls2) + ")V");
        for (FieldInfo fieldInfo3 : context.fieldInfoList) {
            mw.visitVarInsn(25, 0);
            mw.visitLdcInsn("\"" + fieldInfo3.name + "\":");
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "toCharArray", "()[C");
            mw.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
        }
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(4, 4);
        mw.visitEnd();
    }

    private void _createInstance(ClassWriter cw, Context context) {
        if (Modifier.isPublic(context.beanInfo.defaultConstructor.getModifiers())) {
            MethodWriter methodWriter = new MethodWriter(cw, 1, "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;", (String) null, (String[]) null);
            methodWriter.visitTypeInsn(Opcodes.NEW, ASMUtils.type(context.getInstClass()));
            methodWriter.visitInsn(89);
            methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(context.getInstClass()), "<init>", "()V");
            methodWriter.visitInsn(Opcodes.ARETURN);
            methodWriter.visitMaxs(3, 3);
            methodWriter.visitEnd();
        }
    }
}
