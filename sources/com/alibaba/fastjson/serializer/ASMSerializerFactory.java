package com.alibaba.fastjson.serializer;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.asm.Type;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.meituan.robust.Constants;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ASMSerializerFactory implements Opcodes {
    static final String JSONSerializer = ASMUtils.type(JSONSerializer.class);
    static final String JavaBeanSerializer;
    static final String JavaBeanSerializer_desc;
    static final String ObjectSerializer;
    static final String ObjectSerializer_desc;
    static final String SerialContext_desc = ASMUtils.desc((Class<?>) SerialContext.class);
    static final String SerializeFilterable_desc = ASMUtils.desc((Class<?>) SerializeFilterable.class);
    static final String SerializeWriter;
    static final String SerializeWriter_desc;
    protected final ASMClassLoader classLoader = new ASMClassLoader();
    private final AtomicLong seed = new AtomicLong();

    static {
        Class<JavaBeanSerializer> cls = JavaBeanSerializer.class;
        String type = ASMUtils.type(ObjectSerializer.class);
        ObjectSerializer = type;
        ObjectSerializer_desc = "L" + type + Constants.PACKNAME_END;
        String type2 = ASMUtils.type(SerializeWriter.class);
        SerializeWriter = type2;
        SerializeWriter_desc = "L" + type2 + Constants.PACKNAME_END;
        JavaBeanSerializer = ASMUtils.type(cls);
        JavaBeanSerializer_desc = "L" + ASMUtils.type(cls) + Constants.PACKNAME_END;
    }

    public static class Context {
        static final int features = 5;
        static int fieldName = 6;
        static final int obj = 2;
        static int original = 7;
        static final int paramFieldName = 3;
        static final int paramFieldType = 4;
        static int processValue = 8;
        static final int serializer = 1;
        /* access modifiers changed from: private */
        public final SerializeBeanInfo beanInfo;
        /* access modifiers changed from: private */
        public final String className;
        private final FieldInfo[] getters;
        /* access modifiers changed from: private */
        public final boolean nonContext;
        /* access modifiers changed from: private */
        public int variantIndex = 9;
        private Map<String, Integer> variants = new HashMap();
        /* access modifiers changed from: private */
        public final boolean writeDirect;

        public Context(FieldInfo[] getters2, SerializeBeanInfo beanInfo2, String className2, boolean writeDirect2, boolean nonContext2) {
            this.getters = getters2;
            this.className = className2;
            this.beanInfo = beanInfo2;
            this.writeDirect = writeDirect2;
            this.nonContext = nonContext2 || beanInfo2.beanType.isEnum();
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

        public int var(String name, int increment) {
            if (this.variants.get(name) == null) {
                this.variants.put(name, Integer.valueOf(this.variantIndex));
                this.variantIndex += increment;
            }
            return this.variants.get(name).intValue();
        }

        public int getFieldOrinal(String name) {
            int size = this.getters.length;
            for (int i = 0; i < size; i++) {
                if (this.getters[i].name.equals(name)) {
                    return i;
                }
            }
            return -1;
        }
    }

    public JavaBeanSerializer createJavaBeanSerializer(SerializeBeanInfo beanInfo) {
        String classNameFull;
        String classNameType;
        boolean DisableCircularReferenceDetect;
        Class<SerializeBeanInfo> cls;
        FieldInfo[] unsortedGetters;
        int i;
        String str;
        String methodName;
        boolean writeDirect;
        boolean nonContext;
        JSONType jsonType;
        String classNameType2;
        boolean nativeSorted;
        boolean DisableCircularReferenceDetect2;
        ClassWriter cw;
        String className;
        String classNameFull2;
        int i2;
        Method method;
        SerializeBeanInfo serializeBeanInfo = beanInfo;
        Class<SerializeBeanInfo> cls2 = SerializeBeanInfo.class;
        Class<?> clazz = serializeBeanInfo.beanType;
        if (!clazz.isPrimitive()) {
            JSONType jsonType2 = (JSONType) TypeUtils.getAnnotation(clazz, JSONType.class);
            FieldInfo[] unsortedGetters2 = serializeBeanInfo.fields;
            for (FieldInfo fieldInfo : unsortedGetters2) {
                if (fieldInfo.field == null && (method = fieldInfo.method) != null && method.getDeclaringClass().isInterface()) {
                    return new JavaBeanSerializer(serializeBeanInfo);
                }
            }
            FieldInfo[] getters = serializeBeanInfo.sortedFields;
            boolean nativeSorted2 = serializeBeanInfo.sortedFields == serializeBeanInfo.fields;
            if (getters.length > 256) {
                return new JavaBeanSerializer(serializeBeanInfo);
            }
            for (FieldInfo getter : getters) {
                if (!ASMUtils.checkName(getter.getMember().getName())) {
                    return new JavaBeanSerializer(serializeBeanInfo);
                }
            }
            String className2 = "ASMSerializer_" + this.seed.incrementAndGet() + "_" + clazz.getSimpleName();
            Package pkg = ASMSerializerFactory.class.getPackage();
            if (pkg != null) {
                String packageName = pkg.getName();
                classNameFull = packageName + "." + className2;
                classNameType = packageName.replace('.', '/') + "/" + className2;
            } else {
                classNameFull = className2;
                classNameType = className2;
            }
            ClassWriter cw2 = new ClassWriter();
            cw2.visit(49, 33, classNameType, JavaBeanSerializer, new String[]{ObjectSerializer});
            int length = getters.length;
            int i3 = 0;
            while (i3 < length) {
                FieldInfo fieldInfo2 = getters[i3];
                if (!fieldInfo2.fieldClass.isPrimitive()) {
                    i2 = length;
                    if (fieldInfo2.fieldClass == String.class) {
                        classNameFull2 = classNameFull;
                        className = className2;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        classNameFull2 = classNameFull;
                        sb.append(fieldInfo2.name);
                        sb.append("_asm_fieldType");
                        className = className2;
                        new FieldWriter(cw2, 1, sb.toString(), "Ljava/lang/reflect/Type;").visitEnd();
                        if (List.class.isAssignableFrom(fieldInfo2.fieldClass)) {
                            new FieldWriter(cw2, 1, fieldInfo2.name + "_asm_list_item_ser_", ObjectSerializer_desc).visitEnd();
                        }
                        new FieldWriter(cw2, 1, fieldInfo2.name + "_asm_ser_", ObjectSerializer_desc).visitEnd();
                    }
                } else {
                    i2 = length;
                    classNameFull2 = classNameFull;
                    className = className2;
                }
                i3++;
                length = i2;
                classNameFull = classNameFull2;
                className2 = className;
            }
            String className3 = classNameFull;
            String className4 = className2;
            MethodWriter methodWriter = new MethodWriter(cw2, 1, "<init>", "(" + ASMUtils.desc((Class<?>) cls2) + ")V", (String) null, (String[]) null);
            methodWriter.visitVarInsn(25, 0);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "<init>", "(" + ASMUtils.desc((Class<?>) cls2) + ")V");
            int i4 = 0;
            while (i4 < getters.length) {
                FieldInfo fieldInfo3 = getters[i4];
                if (fieldInfo3.fieldClass.isPrimitive()) {
                    cw = cw2;
                } else if (fieldInfo3.fieldClass == String.class) {
                    cw = cw2;
                } else {
                    methodWriter.visitVarInsn(25, 0);
                    if (fieldInfo3.method != null) {
                        methodWriter.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo3.declaringClass)));
                        methodWriter.visitLdcInsn(fieldInfo3.method.getName());
                        cw = cw2;
                        methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(ASMUtils.class), "getMethodType", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Type;");
                    } else {
                        cw = cw2;
                        methodWriter.visitVarInsn(25, 0);
                        methodWriter.visitLdcInsn(Integer.valueOf(i4));
                        methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "getFieldType", "(I)Ljava/lang/reflect/Type;");
                    }
                    methodWriter.visitFieldInsn(Opcodes.PUTFIELD, classNameType, fieldInfo3.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                }
                i4++;
                cw2 = cw;
            }
            ClassWriter cw3 = cw2;
            methodWriter.visitInsn(Opcodes.RETURN);
            methodWriter.visitMaxs(4, 4);
            methodWriter.visitEnd();
            if (jsonType2 != null) {
                SerializerFeature[] serialzeFeatures = jsonType2.serialzeFeatures();
                int length2 = serialzeFeatures.length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length2) {
                        break;
                    } else if (serialzeFeatures[i5] == SerializerFeature.DisableCircularReferenceDetect) {
                        DisableCircularReferenceDetect = true;
                        break;
                    } else {
                        i5++;
                    }
                }
            }
            DisableCircularReferenceDetect = false;
            MethodWriter methodWriter2 = methodWriter;
            int i6 = 0;
            while (true) {
                MethodWriter methodWriter3 = methodWriter2;
                cls = cls2;
                unsortedGetters = unsortedGetters2;
                if (i6 >= 3) {
                    break;
                }
                boolean nonContext2 = DisableCircularReferenceDetect;
                if (i6 == 0) {
                    nonContext = nonContext2;
                    writeDirect = true;
                    methodName = "write";
                } else if (i6 == 1) {
                    nonContext = nonContext2;
                    methodName = "writeNormal";
                    writeDirect = false;
                } else {
                    nonContext = true;
                    methodName = "writeDirectNonContext";
                    writeDirect = true;
                }
                String str2 = "entity";
                ClassWriter cw4 = cw3;
                String classNameType3 = classNameType;
                String classNameFull3 = className3;
                String className5 = className4;
                int i7 = i6;
                Context context = new Context(getters, beanInfo, classNameType, writeDirect, nonContext);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("(L");
                String str3 = JSONSerializer;
                sb2.append(str3);
                sb2.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                MethodWriter methodWriter4 = new MethodWriter(cw4, 1, methodName, sb2.toString(), (String) null, new String[]{"java/io/IOException"});
                Label endIf_ = new Label();
                methodWriter4.visitVarInsn(25, 2);
                methodWriter4.visitJumpInsn(199, endIf_);
                methodWriter4.visitVarInsn(25, 1);
                methodWriter4.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str3, "writeNull", "()V");
                methodWriter4.visitInsn(Opcodes.RETURN);
                methodWriter4.visitLabel(endIf_);
                methodWriter4.visitVarInsn(25, 1);
                methodWriter4.visitFieldInsn(180, str3, "out", SerializeWriter_desc);
                methodWriter4.visitVarInsn(58, context.var("out"));
                if (nativeSorted2) {
                    jsonType = jsonType2;
                    classNameType2 = classNameType3;
                } else if (context.writeDirect) {
                    jsonType = jsonType2;
                    classNameType2 = classNameType3;
                } else if (jsonType2 == null || jsonType2.alphabetic()) {
                    Label _else = new Label();
                    methodWriter4.visitVarInsn(25, context.var("out"));
                    jsonType = jsonType2;
                    methodWriter4.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isSortField", "()Z");
                    methodWriter4.visitJumpInsn(Opcodes.IFNE, _else);
                    methodWriter4.visitVarInsn(25, 0);
                    methodWriter4.visitVarInsn(25, 1);
                    methodWriter4.visitVarInsn(25, 2);
                    methodWriter4.visitVarInsn(25, 3);
                    methodWriter4.visitVarInsn(25, 4);
                    methodWriter4.visitVarInsn(21, 5);
                    classNameType2 = classNameType3;
                    methodWriter4.visitMethodInsn(Opcodes.INVOKEVIRTUAL, classNameType2, "writeUnsorted", "(L" + str3 + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodWriter4.visitInsn(Opcodes.RETURN);
                    methodWriter4.visitLabel(_else);
                } else {
                    jsonType = jsonType2;
                    classNameType2 = classNameType3;
                }
                if (!context.writeDirect || nonContext) {
                    DisableCircularReferenceDetect2 = DisableCircularReferenceDetect;
                    nativeSorted = nativeSorted2;
                } else {
                    Label _direct = new Label();
                    Label _directElse = new Label();
                    methodWriter4.visitVarInsn(25, 0);
                    methodWriter4.visitVarInsn(25, 1);
                    String str4 = JavaBeanSerializer;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("(L");
                    sb3.append(str3);
                    DisableCircularReferenceDetect2 = DisableCircularReferenceDetect;
                    sb3.append(";)Z");
                    nativeSorted = nativeSorted2;
                    methodWriter4.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str4, "writeDirect", sb3.toString());
                    methodWriter4.visitJumpInsn(Opcodes.IFNE, _directElse);
                    methodWriter4.visitVarInsn(25, 0);
                    methodWriter4.visitVarInsn(25, 1);
                    methodWriter4.visitVarInsn(25, 2);
                    methodWriter4.visitVarInsn(25, 3);
                    methodWriter4.visitVarInsn(25, 4);
                    methodWriter4.visitVarInsn(21, 5);
                    methodWriter4.visitMethodInsn(Opcodes.INVOKEVIRTUAL, classNameType2, "writeNormal", "(L" + str3 + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodWriter4.visitInsn(Opcodes.RETURN);
                    methodWriter4.visitLabel(_directElse);
                    methodWriter4.visitVarInsn(25, context.var("out"));
                    methodWriter4.visitLdcInsn(Integer.valueOf(SerializerFeature.DisableCircularReferenceDetect.mask));
                    methodWriter4.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
                    methodWriter4.visitJumpInsn(Opcodes.IFEQ, _direct);
                    methodWriter4.visitVarInsn(25, 0);
                    methodWriter4.visitVarInsn(25, 1);
                    methodWriter4.visitVarInsn(25, 2);
                    methodWriter4.visitVarInsn(25, 3);
                    methodWriter4.visitVarInsn(25, 4);
                    methodWriter4.visitVarInsn(21, 5);
                    methodWriter4.visitMethodInsn(Opcodes.INVOKEVIRTUAL, classNameType2, "writeDirectNonContext", "(L" + str3 + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodWriter4.visitInsn(Opcodes.RETURN);
                    methodWriter4.visitLabel(_direct);
                }
                methodWriter4.visitVarInsn(25, 2);
                methodWriter4.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(clazz));
                methodWriter4.visitVarInsn(58, context.var(str2));
                generateWriteMethod(clazz, methodWriter4, getters, context);
                methodWriter4.visitInsn(Opcodes.RETURN);
                methodWriter4.visitMaxs(7, context.variantIndex + 2);
                methodWriter4.visitEnd();
                i6 = i7 + 1;
                SerializeBeanInfo serializeBeanInfo2 = beanInfo;
                methodWriter2 = methodWriter4;
                classNameType = classNameType2;
                className4 = className5;
                cls2 = cls;
                unsortedGetters2 = unsortedGetters;
                jsonType2 = jsonType;
                cw3 = cw4;
                DisableCircularReferenceDetect = DisableCircularReferenceDetect2;
                className3 = classNameFull3;
                nativeSorted2 = nativeSorted;
            }
            boolean DisableCircularReferenceDetect3 = DisableCircularReferenceDetect;
            boolean nativeSorted3 = nativeSorted2;
            ClassWriter cw5 = cw3;
            String classNameFull4 = className3;
            String classNameFull5 = className4;
            String str5 = "entity";
            String classNameType4 = classNameType;
            int i8 = i6;
            if (!nativeSorted3) {
                i = 180;
                Context context2 = new Context(getters, beanInfo, classNameType4, false, DisableCircularReferenceDetect3);
                StringBuilder sb4 = new StringBuilder();
                sb4.append("(L");
                String str6 = JSONSerializer;
                sb4.append(str6);
                sb4.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                MethodWriter methodWriter5 = new MethodWriter(cw5, 1, "writeUnsorted", sb4.toString(), (String) null, new String[]{"java/io/IOException"});
                methodWriter5.visitVarInsn(25, 1);
                methodWriter5.visitFieldInsn(180, str6, "out", SerializeWriter_desc);
                methodWriter5.visitVarInsn(58, context2.var("out"));
                methodWriter5.visitVarInsn(25, 2);
                methodWriter5.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(clazz));
                methodWriter5.visitVarInsn(58, context2.var(str5));
                generateWriteMethod(clazz, methodWriter5, unsortedGetters, context2);
                methodWriter5.visitInsn(Opcodes.RETURN);
                methodWriter5.visitMaxs(7, context2.variantIndex + 2);
                methodWriter5.visitEnd();
                MethodWriter methodWriter6 = methodWriter5;
            } else {
                i = 180;
                FieldInfo[] fieldInfoArr = unsortedGetters;
            }
            int i9 = 0;
            while (i9 < 3) {
                boolean nonContext3 = DisableCircularReferenceDetect3;
                boolean writeDirect2 = false;
                if (i9 == 0) {
                    str = "writeAsArray";
                    writeDirect2 = true;
                } else if (i9 == 1) {
                    str = "writeAsArrayNormal";
                } else {
                    writeDirect2 = true;
                    nonContext3 = true;
                    str = "writeAsArrayNonContext";
                }
                String methodName2 = str;
                Context context3 = new Context(getters, beanInfo, classNameType4, writeDirect2, nonContext3);
                StringBuilder sb5 = new StringBuilder();
                sb5.append("(L");
                String str7 = JSONSerializer;
                sb5.append(str7);
                sb5.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                MethodWriter methodWriter7 = new MethodWriter(cw5, 1, methodName2, sb5.toString(), (String) null, new String[]{"java/io/IOException"});
                methodWriter7.visitVarInsn(25, 1);
                methodWriter7.visitFieldInsn(i, str7, "out", SerializeWriter_desc);
                methodWriter7.visitVarInsn(58, context3.var("out"));
                methodWriter7.visitVarInsn(25, 2);
                methodWriter7.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(clazz));
                methodWriter7.visitVarInsn(58, context3.var(str5));
                generateWriteAsArray(clazz, methodWriter7, getters, context3);
                methodWriter7.visitInsn(Opcodes.RETURN);
                methodWriter7.visitMaxs(7, context3.variantIndex + 2);
                methodWriter7.visitEnd();
                i9++;
                MethodWriter methodWriter8 = methodWriter7;
            }
            int i10 = i9;
            byte[] code = cw5.toByteArray();
            return (JavaBeanSerializer) this.classLoader.defineClassPublic(classNameFull4, code, 0, code.length).getConstructor(new Class[]{cls}).newInstance(new Object[]{beanInfo});
        }
        throw new JSONException("unsupportd class " + clazz.getName());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v25, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v2, resolved type: java.lang.Class} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v28, resolved type: java.lang.Class<java.lang.Object>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void generateWriteAsArray(java.lang.Class<?> r40, com.alibaba.fastjson.asm.MethodVisitor r41, com.alibaba.fastjson.util.FieldInfo[] r42, com.alibaba.fastjson.serializer.ASMSerializerFactory.Context r43) {
        /*
            r39 = this;
            r0 = r39
            r1 = r41
            r2 = r42
            r3 = r43
            com.alibaba.fastjson.asm.Label r4 = new com.alibaba.fastjson.asm.Label
            r4.<init>()
            r5 = 25
            r6 = 1
            r1.visitVarInsn(r5, r6)
            r7 = 0
            r1.visitVarInsn(r5, r7)
            java.lang.String r8 = JSONSerializer
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "("
            r9.append(r10)
            java.lang.String r10 = SerializeFilterable_desc
            r9.append(r10)
            java.lang.String r10 = ")Z"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r10 = 182(0xb6, float:2.55E-43)
            java.lang.String r11 = "hasPropertyFilters"
            r1.visitMethodInsn(r10, r8, r11, r9)
            r9 = 154(0x9a, float:2.16E-43)
            r1.visitJumpInsn(r9, r4)
            r1.visitVarInsn(r5, r7)
            r1.visitVarInsn(r5, r6)
            r9 = 2
            r1.visitVarInsn(r5, r9)
            r9 = 3
            r1.visitVarInsn(r5, r9)
            r9 = 4
            r1.visitVarInsn(r5, r9)
            r9 = 21
            r11 = 5
            r1.visitVarInsn(r9, r11)
            java.lang.String r11 = JavaBeanSerializer
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "(L"
            r12.append(r13)
            r12.append(r8)
            java.lang.String r8 = ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V"
            r12.append(r8)
            java.lang.String r12 = r12.toString()
            r14 = 183(0xb7, float:2.56E-43)
            java.lang.String r15 = "writeNoneASM"
            r1.visitMethodInsn(r14, r11, r15, r12)
            r11 = 177(0xb1, float:2.48E-43)
            r1.visitInsn(r11)
            r1.visitLabel(r4)
            java.lang.String r11 = "out"
            int r12 = r3.var(r11)
            r1.visitVarInsn(r5, r12)
            r12 = 16
            r14 = 91
            r1.visitVarInsn(r12, r14)
            java.lang.String r14 = SerializeWriter
            java.lang.String r15 = "write"
            java.lang.String r6 = "(I)V"
            r1.visitMethodInsn(r10, r14, r15, r6)
            int r9 = r2.length
            if (r9 != 0) goto L_0x00aa
            int r7 = r3.var(r11)
            r1.visitVarInsn(r5, r7)
            r5 = 93
            r1.visitVarInsn(r12, r5)
            r1.visitMethodInsn(r10, r14, r15, r6)
            return
        L_0x00aa:
            r14 = 0
        L_0x00ab:
            if (r14 >= r9) goto L_0x091e
            int r7 = r9 + -1
            if (r14 != r7) goto L_0x00b4
            r7 = 93
            goto L_0x00b6
        L_0x00b4:
            r7 = 44
        L_0x00b6:
            r12 = r2[r14]
            java.lang.Class<?> r10 = r12.fieldClass
            java.lang.String r5 = r12.name
            r1.visitLdcInsn(r5)
            int r5 = com.alibaba.fastjson.serializer.ASMSerializerFactory.Context.fieldName
            r2 = 58
            r1.visitVarInsn(r2, r5)
            java.lang.Class r5 = java.lang.Byte.TYPE
            if (r10 == r5) goto L_0x08dd
            java.lang.Class r5 = java.lang.Short.TYPE
            if (r10 == r5) goto L_0x08dd
            java.lang.Class r5 = java.lang.Integer.TYPE
            if (r10 != r5) goto L_0x00e0
            r19 = r4
            r20 = r9
            r25 = r10
            r4 = r11
            r24 = r14
            r11 = r6
            r6 = r7
            r7 = r13
            goto L_0x08e9
        L_0x00e0:
            java.lang.Class r5 = java.lang.Long.TYPE
            if (r10 != r5) goto L_0x0119
            int r5 = r3.var(r11)
            r2 = 25
            r1.visitVarInsn(r2, r5)
            r2 = 89
            r1.visitInsn(r2)
            r0._get(r1, r3, r12)
            java.lang.String r2 = SerializeWriter
            java.lang.String r5 = "writeLong"
            r19 = r4
            java.lang.String r4 = "(J)V"
            r20 = r9
            r9 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r9, r2, r5, r4)
            r4 = 16
            r1.visitVarInsn(r4, r7)
            r1.visitMethodInsn(r9, r2, r15, r6)
            r10 = r9
            r4 = r11
            r7 = r13
            r24 = r14
            r2 = 25
            r9 = 16
            r11 = r6
            goto L_0x090e
        L_0x0119:
            r19 = r4
            r20 = r9
            java.lang.Class r2 = java.lang.Float.TYPE
            if (r10 != r2) goto L_0x0156
            int r2 = r3.var(r11)
            r4 = 25
            r1.visitVarInsn(r4, r2)
            r2 = 89
            r1.visitInsn(r2)
            r0._get(r1, r3, r12)
            r2 = 4
            r1.visitInsn(r2)
            java.lang.String r2 = SerializeWriter
            java.lang.String r4 = "writeFloat"
            java.lang.String r5 = "(FZ)V"
            r9 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r9, r2, r4, r5)
            r4 = 16
            r1.visitVarInsn(r4, r7)
            r1.visitMethodInsn(r9, r2, r15, r6)
            r10 = r9
            r4 = r11
            r7 = r13
            r24 = r14
            r2 = 25
            r9 = 16
            r11 = r6
            goto L_0x090e
        L_0x0156:
            java.lang.Class r2 = java.lang.Double.TYPE
            if (r10 != r2) goto L_0x018f
            int r2 = r3.var(r11)
            r4 = 25
            r1.visitVarInsn(r4, r2)
            r2 = 89
            r1.visitInsn(r2)
            r0._get(r1, r3, r12)
            r2 = 4
            r1.visitInsn(r2)
            java.lang.String r2 = SerializeWriter
            java.lang.String r4 = "writeDouble"
            java.lang.String r5 = "(DZ)V"
            r9 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r9, r2, r4, r5)
            r4 = 16
            r1.visitVarInsn(r4, r7)
            r1.visitMethodInsn(r9, r2, r15, r6)
            r10 = r9
            r4 = r11
            r7 = r13
            r24 = r14
            r2 = 25
            r9 = 16
            r11 = r6
            goto L_0x090e
        L_0x018f:
            java.lang.Class r2 = java.lang.Boolean.TYPE
            if (r10 != r2) goto L_0x01c1
            int r2 = r3.var(r11)
            r4 = 25
            r1.visitVarInsn(r4, r2)
            r2 = 89
            r1.visitInsn(r2)
            r0._get(r1, r3, r12)
            java.lang.String r2 = SerializeWriter
            java.lang.String r4 = "(Z)V"
            r5 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r5, r2, r15, r4)
            r4 = 16
            r1.visitVarInsn(r4, r7)
            r1.visitMethodInsn(r5, r2, r15, r6)
            r10 = r5
            r4 = r11
            r7 = r13
            r24 = r14
            r2 = 25
            r9 = 16
            r11 = r6
            goto L_0x090e
        L_0x01c1:
            java.lang.Class r2 = java.lang.Character.TYPE
            r4 = 184(0xb8, float:2.58E-43)
            if (r10 != r2) goto L_0x01fa
            int r2 = r3.var(r11)
            r5 = 25
            r1.visitVarInsn(r5, r2)
            r0._get(r1, r3, r12)
            java.lang.String r2 = "java/lang/Character"
            java.lang.String r5 = "toString"
            java.lang.String r9 = "(C)Ljava/lang/String;"
            r1.visitMethodInsn(r4, r2, r5, r9)
            r2 = 16
            r1.visitVarInsn(r2, r7)
            java.lang.String r2 = SerializeWriter
            java.lang.String r4 = "writeString"
            java.lang.String r5 = "(Ljava/lang/String;C)V"
            r9 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r9, r2, r4, r5)
            r10 = r9
            r4 = r11
            r7 = r13
            r24 = r14
            r2 = 25
            r9 = 16
            r11 = r6
            goto L_0x090e
        L_0x01fa:
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            if (r10 != r2) goto L_0x0227
            int r2 = r3.var(r11)
            r4 = 25
            r1.visitVarInsn(r4, r2)
            r0._get(r1, r3, r12)
            r2 = 16
            r1.visitVarInsn(r2, r7)
            java.lang.String r2 = SerializeWriter
            java.lang.String r4 = "writeString"
            java.lang.String r5 = "(Ljava/lang/String;C)V"
            r9 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r9, r2, r4, r5)
            r10 = r9
            r4 = r11
            r7 = r13
            r24 = r14
            r2 = 25
            r9 = 16
            r11 = r6
            goto L_0x090e
        L_0x0227:
            boolean r2 = r10.isEnum()
            if (r2 == 0) goto L_0x025e
            int r2 = r3.var(r11)
            r4 = 25
            r1.visitVarInsn(r4, r2)
            r2 = 89
            r1.visitInsn(r2)
            r0._get(r1, r3, r12)
            java.lang.String r2 = SerializeWriter
            java.lang.String r4 = "writeEnum"
            java.lang.String r5 = "(Ljava/lang/Enum;)V"
            r9 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r9, r2, r4, r5)
            r4 = 16
            r1.visitVarInsn(r4, r7)
            r1.visitMethodInsn(r9, r2, r15, r6)
            r10 = r9
            r4 = r11
            r7 = r13
            r24 = r14
            r2 = 25
            r9 = 16
            r11 = r6
            goto L_0x090e
        L_0x025e:
            java.lang.Class<java.util.List> r2 = java.util.List.class
            boolean r2 = r2.isAssignableFrom(r10)
            java.lang.String r5 = "writeWithFieldName"
            if (r2 == 0) goto L_0x0657
            java.lang.reflect.Type r2 = r12.fieldType
            boolean r9 = r2 instanceof java.lang.Class
            if (r9 == 0) goto L_0x0272
            java.lang.Class<java.lang.Object> r9 = java.lang.Object.class
            goto L_0x027d
        L_0x0272:
            r9 = r2
            java.lang.reflect.ParameterizedType r9 = (java.lang.reflect.ParameterizedType) r9
            java.lang.reflect.Type[] r9 = r9.getActualTypeArguments()
            r16 = 0
            r9 = r9[r16]
        L_0x027d:
            r18 = 0
            boolean r4 = r9 instanceof java.lang.Class
            if (r4 == 0) goto L_0x0291
            r4 = r9
            java.lang.Class r4 = (java.lang.Class) r4
            r23 = r2
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
            if (r4 != r2) goto L_0x0295
            r18 = 0
            r4 = r18
            goto L_0x0295
        L_0x0291:
            r23 = r2
            r4 = r18
        L_0x0295:
            r0._get(r1, r3, r12)
            r2 = 192(0xc0, float:2.69E-43)
            r24 = r14
            java.lang.String r14 = "java/util/List"
            r1.visitTypeInsn(r2, r14)
            java.lang.String r2 = "list"
            int r14 = r3.var(r2)
            r25 = r10
            r10 = 58
            r1.visitVarInsn(r10, r14)
            java.lang.Class<java.lang.String> r10 = java.lang.String.class
            if (r4 != r10) goto L_0x02db
            boolean r10 = r43.writeDirect
            if (r10 == 0) goto L_0x02db
            int r5 = r3.var(r11)
            r10 = 25
            r1.visitVarInsn(r10, r5)
            int r2 = r3.var(r2)
            r1.visitVarInsn(r10, r2)
            java.lang.String r2 = SerializeWriter
            java.lang.String r5 = "(Ljava/util/List;)V"
            r10 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r10, r2, r15, r5)
            r5 = r6
            r26 = r7
            r18 = r9
            r14 = r10
            r7 = r13
            r13 = r11
            goto L_0x0636
        L_0x02db:
            com.alibaba.fastjson.asm.Label r10 = new com.alibaba.fastjson.asm.Label
            r10.<init>()
            com.alibaba.fastjson.asm.Label r14 = new com.alibaba.fastjson.asm.Label
            r14.<init>()
            r26 = r7
            int r7 = r3.var(r2)
            r27 = r5
            r5 = 25
            r1.visitVarInsn(r5, r7)
            r7 = 199(0xc7, float:2.79E-43)
            r1.visitJumpInsn(r7, r14)
            int r7 = r3.var(r11)
            r1.visitVarInsn(r5, r7)
            java.lang.String r5 = SerializeWriter
            java.lang.String r7 = "writeNull"
            r18 = r9
            java.lang.String r9 = "()V"
            r28 = r8
            r8 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r8, r5, r7, r9)
            r7 = 167(0xa7, float:2.34E-43)
            r1.visitJumpInsn(r7, r10)
            r1.visitLabel(r14)
            int r7 = r3.var(r2)
            r8 = 25
            r1.visitVarInsn(r8, r7)
            java.lang.String r7 = "java/util/List"
            java.lang.String r8 = "size"
            java.lang.String r9 = "()I"
            r29 = r14
            r14 = 185(0xb9, float:2.59E-43)
            r1.visitMethodInsn(r14, r7, r8, r9)
            r7 = 54
            java.lang.String r8 = "size"
            int r8 = r3.var(r8)
            r1.visitVarInsn(r7, r8)
            int r7 = r3.var(r11)
            r8 = 25
            r1.visitVarInsn(r8, r7)
            r7 = 91
            r8 = 16
            r1.visitVarInsn(r8, r7)
            r7 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r7, r5, r15, r6)
            com.alibaba.fastjson.asm.Label r7 = new com.alibaba.fastjson.asm.Label
            r7.<init>()
            com.alibaba.fastjson.asm.Label r8 = new com.alibaba.fastjson.asm.Label
            r8.<init>()
            com.alibaba.fastjson.asm.Label r9 = new com.alibaba.fastjson.asm.Label
            r9.<init>()
            r14 = 3
            r1.visitInsn(r14)
            r14 = 54
            r30 = r10
            java.lang.String r10 = "i"
            r31 = r13
            int r13 = r3.var(r10)
            r1.visitVarInsn(r14, r13)
            r1.visitLabel(r7)
            int r13 = r3.var(r10)
            r14 = 21
            r1.visitVarInsn(r14, r13)
            java.lang.String r13 = "size"
            int r13 = r3.var(r13)
            r1.visitVarInsn(r14, r13)
            r13 = 162(0xa2, float:2.27E-43)
            r1.visitJumpInsn(r13, r9)
            int r13 = r3.var(r10)
            r1.visitVarInsn(r14, r13)
            r13 = 153(0x99, float:2.14E-43)
            r1.visitJumpInsn(r13, r8)
            int r13 = r3.var(r11)
            r14 = 25
            r1.visitVarInsn(r14, r13)
            r13 = 44
            r14 = 16
            r1.visitVarInsn(r14, r13)
            r13 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r13, r5, r15, r6)
            r1.visitLabel(r8)
            int r2 = r3.var(r2)
            r13 = 25
            r1.visitVarInsn(r13, r2)
            int r2 = r3.var(r10)
            r13 = 21
            r1.visitVarInsn(r13, r2)
            java.lang.String r2 = "java/util/List"
            java.lang.String r13 = "get"
            java.lang.String r14 = "(I)Ljava/lang/Object;"
            r32 = r8
            r8 = 185(0xb9, float:2.59E-43)
            r1.visitMethodInsn(r8, r2, r13, r14)
            java.lang.String r2 = "list_item"
            int r8 = r3.var(r2)
            r13 = 58
            r1.visitVarInsn(r13, r8)
            com.alibaba.fastjson.asm.Label r8 = new com.alibaba.fastjson.asm.Label
            r8.<init>()
            com.alibaba.fastjson.asm.Label r13 = new com.alibaba.fastjson.asm.Label
            r13.<init>()
            int r14 = r3.var(r2)
            r33 = r6
            r6 = 25
            r1.visitVarInsn(r6, r14)
            r14 = 199(0xc7, float:2.79E-43)
            r1.visitJumpInsn(r14, r13)
            int r14 = r3.var(r11)
            r1.visitVarInsn(r6, r14)
            java.lang.String r6 = "writeNull"
            java.lang.String r14 = "()V"
            r34 = r11
            r11 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r11, r5, r6, r14)
            r6 = 167(0xa7, float:2.34E-43)
            r1.visitJumpInsn(r6, r8)
            r1.visitLabel(r13)
            com.alibaba.fastjson.asm.Label r6 = new com.alibaba.fastjson.asm.Label
            r6.<init>()
            com.alibaba.fastjson.asm.Label r11 = new com.alibaba.fastjson.asm.Label
            r11.<init>()
            if (r4 == 0) goto L_0x057c
            int r14 = r4.getModifiers()
            boolean r14 = java.lang.reflect.Modifier.isPublic(r14)
            if (r14 == 0) goto L_0x057c
            int r14 = r3.var(r2)
            r35 = r13
            r13 = 25
            r1.visitVarInsn(r13, r14)
            java.lang.String r13 = "java/lang/Object"
            java.lang.String r14 = "getClass"
            r36 = r5
            java.lang.String r5 = "()Ljava/lang/Class;"
            r37 = r9
            r9 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r9, r13, r14, r5)
            java.lang.String r5 = com.alibaba.fastjson.util.ASMUtils.desc((java.lang.Class<?>) r4)
            com.alibaba.fastjson.asm.Type r5 = com.alibaba.fastjson.asm.Type.getType(r5)
            r1.visitLdcInsn(r5)
            r5 = 166(0xa6, float:2.33E-43)
            r1.visitJumpInsn(r5, r11)
            r0._getListFieldItemSer(r3, r1, r12, r4)
            java.lang.String r5 = "list_item_desc"
            int r5 = r3.var(r5)
            r9 = 58
            r1.visitVarInsn(r9, r5)
            com.alibaba.fastjson.asm.Label r5 = new com.alibaba.fastjson.asm.Label
            r5.<init>()
            com.alibaba.fastjson.asm.Label r9 = new com.alibaba.fastjson.asm.Label
            r9.<init>()
            boolean r13 = r43.writeDirect
            if (r13 == 0) goto L_0x0505
            java.lang.String r13 = "list_item_desc"
            int r13 = r3.var(r13)
            r14 = 25
            r1.visitVarInsn(r14, r13)
            r13 = 193(0xc1, float:2.7E-43)
            java.lang.String r14 = JavaBeanSerializer
            r1.visitTypeInsn(r13, r14)
            r13 = 153(0x99, float:2.14E-43)
            r1.visitJumpInsn(r13, r5)
            java.lang.String r13 = "list_item_desc"
            int r13 = r3.var(r13)
            r0 = 25
            r1.visitVarInsn(r0, r13)
            r13 = 192(0xc0, float:2.69E-43)
            r1.visitTypeInsn(r13, r14)
            r13 = 1
            r1.visitVarInsn(r0, r13)
            int r13 = r3.var(r2)
            r1.visitVarInsn(r0, r13)
            boolean r0 = r43.nonContext
            if (r0 == 0) goto L_0x04ad
            r0 = 1
            r1.visitInsn(r0)
            r17 = r7
            r38 = r8
            goto L_0x04c6
        L_0x04ad:
            int r0 = r3.var(r10)
            r13 = 21
            r1.visitVarInsn(r13, r0)
            java.lang.String r0 = "java/lang/Integer"
            java.lang.String r13 = "valueOf"
            r17 = r7
            java.lang.String r7 = "(I)Ljava/lang/Integer;"
            r38 = r8
            r8 = 184(0xb8, float:2.58E-43)
            r1.visitMethodInsn(r8, r0, r13, r7)
        L_0x04c6:
            java.lang.String r0 = com.alibaba.fastjson.util.ASMUtils.desc((java.lang.Class<?>) r4)
            com.alibaba.fastjson.asm.Type r0 = com.alibaba.fastjson.asm.Type.getType(r0)
            r1.visitLdcInsn(r0)
            int r0 = r12.serialzeFeatures
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1.visitLdcInsn(r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r7 = r31
            r0.append(r7)
            java.lang.String r8 = JSONSerializer
            r0.append(r8)
            r8 = r28
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            java.lang.String r13 = "writeAsArrayNonContext"
            r28 = r11
            r11 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r11, r14, r13, r0)
            r0 = 167(0xa7, float:2.34E-43)
            r1.visitJumpInsn(r0, r9)
            r1.visitLabel(r5)
            goto L_0x050f
        L_0x0505:
            r17 = r7
            r38 = r8
            r8 = r28
            r7 = r31
            r28 = r11
        L_0x050f:
            java.lang.String r0 = "list_item_desc"
            int r0 = r3.var(r0)
            r11 = 25
            r1.visitVarInsn(r11, r0)
            r0 = 1
            r1.visitVarInsn(r11, r0)
            int r13 = r3.var(r2)
            r1.visitVarInsn(r11, r13)
            boolean r11 = r43.nonContext
            if (r11 == 0) goto L_0x052f
            r1.visitInsn(r0)
            goto L_0x0544
        L_0x052f:
            int r0 = r3.var(r10)
            r11 = 21
            r1.visitVarInsn(r11, r0)
            java.lang.String r0 = "java/lang/Integer"
            java.lang.String r11 = "valueOf"
            java.lang.String r13 = "(I)Ljava/lang/Integer;"
            r14 = 184(0xb8, float:2.58E-43)
            r1.visitMethodInsn(r14, r0, r11, r13)
        L_0x0544:
            java.lang.String r0 = com.alibaba.fastjson.util.ASMUtils.desc((java.lang.Class<?>) r4)
            com.alibaba.fastjson.asm.Type r0 = com.alibaba.fastjson.asm.Type.getType(r0)
            r1.visitLdcInsn(r0)
            int r0 = r12.serialzeFeatures
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1.visitLdcInsn(r0)
            java.lang.String r0 = ObjectSerializer
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r7)
            java.lang.String r13 = JSONSerializer
            r11.append(r13)
            r11.append(r8)
            java.lang.String r11 = r11.toString()
            r13 = 185(0xb9, float:2.59E-43)
            r1.visitMethodInsn(r13, r0, r15, r11)
            r1.visitLabel(r9)
            r0 = 167(0xa7, float:2.34E-43)
            r1.visitJumpInsn(r0, r6)
            goto L_0x058c
        L_0x057c:
            r36 = r5
            r17 = r7
            r38 = r8
            r37 = r9
            r35 = r13
            r8 = r28
            r7 = r31
            r28 = r11
        L_0x058c:
            r0 = r28
            r1.visitLabel(r0)
            r5 = 1
            r9 = 25
            r1.visitVarInsn(r9, r5)
            int r2 = r3.var(r2)
            r1.visitVarInsn(r9, r2)
            boolean r2 = r43.nonContext
            if (r2 == 0) goto L_0x05aa
            r1.visitInsn(r5)
            r5 = 21
            goto L_0x05bf
        L_0x05aa:
            int r2 = r3.var(r10)
            r5 = 21
            r1.visitVarInsn(r5, r2)
            java.lang.String r2 = "java/lang/Integer"
            java.lang.String r9 = "valueOf"
            java.lang.String r11 = "(I)Ljava/lang/Integer;"
            r13 = 184(0xb8, float:2.58E-43)
            r1.visitMethodInsn(r13, r2, r9, r11)
        L_0x05bf:
            if (r4 == 0) goto L_0x05ef
            int r2 = r4.getModifiers()
            boolean r2 = java.lang.reflect.Modifier.isPublic(r2)
            if (r2 == 0) goto L_0x05ef
            r2 = r18
            java.lang.Class r2 = (java.lang.Class) r2
            java.lang.String r2 = com.alibaba.fastjson.util.ASMUtils.desc((java.lang.Class<?>) r2)
            com.alibaba.fastjson.asm.Type r2 = com.alibaba.fastjson.asm.Type.getType(r2)
            r1.visitLdcInsn(r2)
            int r2 = r12.serialzeFeatures
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r1.visitLdcInsn(r2)
            java.lang.String r2 = JSONSerializer
            java.lang.String r9 = "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V"
            r11 = r27
            r13 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r13, r2, r11, r9)
            goto L_0x05fa
        L_0x05ef:
            r11 = r27
            r13 = 182(0xb6, float:2.55E-43)
            java.lang.String r2 = JSONSerializer
            java.lang.String r9 = "(Ljava/lang/Object;Ljava/lang/Object;)V"
            r1.visitMethodInsn(r13, r2, r11, r9)
        L_0x05fa:
            r1.visitLabel(r6)
            r2 = r38
            r1.visitLabel(r2)
            int r9 = r3.var(r10)
            r10 = 1
            r1.visitIincInsn(r9, r10)
            r9 = r17
            r10 = 167(0xa7, float:2.34E-43)
            r1.visitJumpInsn(r10, r9)
            r10 = r37
            r1.visitLabel(r10)
            r13 = r34
            int r11 = r3.var(r13)
            r14 = 25
            r1.visitVarInsn(r14, r11)
            r11 = 93
            r5 = 16
            r1.visitVarInsn(r5, r11)
            r5 = r33
            r11 = r36
            r14 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r14, r11, r15, r5)
            r11 = r30
            r1.visitLabel(r11)
        L_0x0636:
            int r0 = r3.var(r13)
            r2 = 25
            r1.visitVarInsn(r2, r0)
            r0 = r26
            r2 = 16
            r1.visitVarInsn(r2, r0)
            java.lang.String r2 = SerializeWriter
            r1.visitMethodInsn(r14, r2, r15, r5)
            r2 = 25
            r9 = 16
            r10 = 182(0xb6, float:2.55E-43)
            r0 = r39
            r11 = r5
            r4 = r13
            goto L_0x090e
        L_0x0657:
            r0 = r7
            r25 = r10
            r7 = r13
            r24 = r14
            r13 = r11
            r11 = r5
            r5 = r6
            com.alibaba.fastjson.asm.Label r2 = new com.alibaba.fastjson.asm.Label
            r2.<init>()
            com.alibaba.fastjson.asm.Label r4 = new com.alibaba.fastjson.asm.Label
            r4.<init>()
            r6 = r39
            r6._get(r1, r3, r12)
            r9 = 89
            r1.visitInsn(r9)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "field_"
            r9.append(r10)
            java.lang.Class<?> r14 = r12.fieldClass
            java.lang.String r14 = r14.getName()
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            int r9 = r3.var(r9)
            r14 = 58
            r1.visitVarInsn(r14, r9)
            r9 = 199(0xc7, float:2.79E-43)
            r1.visitJumpInsn(r9, r4)
            int r9 = r3.var(r13)
            r14 = 25
            r1.visitVarInsn(r14, r9)
            java.lang.String r9 = SerializeWriter
            java.lang.String r14 = "writeNull"
            r33 = r5
            java.lang.String r5 = "()V"
            r26 = r0
            r0 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r0, r9, r14, r5)
            r0 = 167(0xa7, float:2.34E-43)
            r1.visitJumpInsn(r0, r2)
            r1.visitLabel(r4)
            com.alibaba.fastjson.asm.Label r0 = new com.alibaba.fastjson.asm.Label
            r0.<init>()
            com.alibaba.fastjson.asm.Label r5 = new com.alibaba.fastjson.asm.Label
            r5.<init>()
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r10)
            r18 = r4
            java.lang.Class<?> r4 = r12.fieldClass
            java.lang.String r4 = r4.getName()
            r14.append(r4)
            java.lang.String r4 = r14.toString()
            int r4 = r3.var(r4)
            r14 = 25
            r1.visitVarInsn(r14, r4)
            java.lang.String r4 = "java/lang/Object"
            java.lang.String r14 = "getClass"
            r21 = r9
            java.lang.String r9 = "()Ljava/lang/Class;"
            r34 = r13
            r13 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r13, r4, r14, r9)
            java.lang.String r4 = com.alibaba.fastjson.util.ASMUtils.desc((java.lang.Class<?>) r25)
            com.alibaba.fastjson.asm.Type r4 = com.alibaba.fastjson.asm.Type.getType(r4)
            r1.visitLdcInsn(r4)
            r4 = 166(0xa6, float:2.33E-43)
            r1.visitJumpInsn(r4, r5)
            r6._getFieldSer(r3, r1, r12)
            java.lang.String r4 = "fied_ser"
            int r9 = r3.var(r4)
            r13 = 58
            r1.visitVarInsn(r13, r9)
            com.alibaba.fastjson.asm.Label r9 = new com.alibaba.fastjson.asm.Label
            r9.<init>()
            com.alibaba.fastjson.asm.Label r13 = new com.alibaba.fastjson.asm.Label
            r13.<init>()
            boolean r14 = r43.writeDirect
            if (r14 == 0) goto L_0x07b2
            int r14 = r25.getModifiers()
            boolean r14 = java.lang.reflect.Modifier.isPublic(r14)
            if (r14 == 0) goto L_0x07b2
            int r14 = r3.var(r4)
            r6 = 25
            r1.visitVarInsn(r6, r14)
            r14 = 193(0xc1, float:2.7E-43)
            java.lang.String r6 = JavaBeanSerializer
            r1.visitTypeInsn(r14, r6)
            r14 = 153(0x99, float:2.14E-43)
            r1.visitJumpInsn(r14, r9)
            int r14 = r3.var(r4)
            r17 = r2
            r2 = 25
            r1.visitVarInsn(r2, r14)
            r14 = 192(0xc0, float:2.69E-43)
            r1.visitTypeInsn(r14, r6)
            r14 = 1
            r1.visitVarInsn(r2, r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r10)
            java.lang.Class<?> r2 = r12.fieldClass
            java.lang.String r2 = r2.getName()
            r14.append(r2)
            java.lang.String r2 = r14.toString()
            int r2 = r3.var(r2)
            r14 = 25
            r1.visitVarInsn(r14, r2)
            int r2 = com.alibaba.fastjson.serializer.ASMSerializerFactory.Context.fieldName
            r1.visitVarInsn(r14, r2)
            java.lang.String r2 = com.alibaba.fastjson.util.ASMUtils.desc((java.lang.Class<?>) r25)
            com.alibaba.fastjson.asm.Type r2 = com.alibaba.fastjson.asm.Type.getType(r2)
            r1.visitLdcInsn(r2)
            int r2 = r12.serialzeFeatures
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r1.visitLdcInsn(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r7)
            java.lang.String r14 = JSONSerializer
            r2.append(r14)
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            java.lang.String r14 = "writeAsArrayNonContext"
            r27 = r11
            r11 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r11, r6, r14, r2)
            r2 = 167(0xa7, float:2.34E-43)
            r1.visitJumpInsn(r2, r13)
            r1.visitLabel(r9)
            goto L_0x07b6
        L_0x07b2:
            r17 = r2
            r27 = r11
        L_0x07b6:
            int r2 = r3.var(r4)
            r4 = 25
            r1.visitVarInsn(r4, r2)
            r2 = 1
            r1.visitVarInsn(r4, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r10)
            java.lang.Class<?> r6 = r12.fieldClass
            java.lang.String r6 = r6.getName()
            r2.append(r6)
            java.lang.String r2 = r2.toString()
            int r2 = r3.var(r2)
            r1.visitVarInsn(r4, r2)
            int r2 = com.alibaba.fastjson.serializer.ASMSerializerFactory.Context.fieldName
            r1.visitVarInsn(r4, r2)
            java.lang.String r2 = com.alibaba.fastjson.util.ASMUtils.desc((java.lang.Class<?>) r25)
            com.alibaba.fastjson.asm.Type r2 = com.alibaba.fastjson.asm.Type.getType(r2)
            r1.visitLdcInsn(r2)
            int r2 = r12.serialzeFeatures
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r1.visitLdcInsn(r2)
            java.lang.String r2 = ObjectSerializer
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r7)
            java.lang.String r6 = JSONSerializer
            r4.append(r6)
            r4.append(r8)
            java.lang.String r4 = r4.toString()
            r11 = 185(0xb9, float:2.59E-43)
            r1.visitMethodInsn(r11, r2, r15, r4)
            r1.visitLabel(r13)
            r2 = 167(0xa7, float:2.34E-43)
            r1.visitJumpInsn(r2, r0)
            r1.visitLabel(r5)
            java.lang.String r2 = r12.getFormat()
            r4 = 1
            r11 = 25
            r1.visitVarInsn(r11, r4)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r10)
            java.lang.Class<?> r10 = r12.fieldClass
            java.lang.String r10 = r10.getName()
            r14.append(r10)
            java.lang.String r10 = r14.toString()
            int r10 = r3.var(r10)
            r1.visitVarInsn(r11, r10)
            if (r2 == 0) goto L_0x0856
            r1.visitLdcInsn(r2)
            java.lang.String r10 = "writeWithFormat"
            java.lang.String r11 = "(Ljava/lang/Object;Ljava/lang/String;)V"
            r14 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r14, r6, r10, r11)
            r22 = r2
            goto L_0x08b2
        L_0x0856:
            int r10 = com.alibaba.fastjson.serializer.ASMSerializerFactory.Context.fieldName
            r11 = 25
            r1.visitVarInsn(r11, r10)
            java.lang.reflect.Type r10 = r12.fieldType
            boolean r11 = r10 instanceof java.lang.Class
            if (r11 == 0) goto L_0x087a
            java.lang.Class r10 = (java.lang.Class) r10
            boolean r10 = r10.isPrimitive()
            if (r10 == 0) goto L_0x0877
            java.lang.String r10 = "(Ljava/lang/Object;Ljava/lang/Object;)V"
            r11 = r27
            r14 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r14, r6, r11, r10)
            r22 = r2
            goto L_0x08b2
        L_0x0877:
            r11 = r27
            goto L_0x087c
        L_0x087a:
            r11 = r27
        L_0x087c:
            r10 = 0
            r14 = 25
            r1.visitVarInsn(r14, r10)
            java.lang.String r4 = r43.className
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r14 = r12.name
            r10.append(r14)
            java.lang.String r14 = "_asm_fieldType"
            r10.append(r14)
            java.lang.String r10 = r10.toString()
            java.lang.String r14 = "Ljava/lang/reflect/Type;"
            r22 = r2
            r2 = 180(0xb4, float:2.52E-43)
            r1.visitFieldInsn(r2, r4, r10, r14)
            int r2 = r12.serialzeFeatures
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r1.visitLdcInsn(r2)
            java.lang.String r2 = "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V"
            r4 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r4, r6, r11, r2)
        L_0x08b2:
            r1.visitLabel(r0)
            r2 = r17
            r1.visitLabel(r2)
            r4 = r34
            int r6 = r3.var(r4)
            r10 = 25
            r1.visitVarInsn(r10, r6)
            r6 = r26
            r10 = 16
            r1.visitVarInsn(r10, r6)
            r10 = r21
            r11 = r33
            r14 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r14, r10, r15, r11)
            r2 = 25
            r9 = 16
            r0 = r39
            r10 = r14
            goto L_0x090e
        L_0x08dd:
            r19 = r4
            r20 = r9
            r25 = r10
            r4 = r11
            r24 = r14
            r11 = r6
            r6 = r7
            r7 = r13
        L_0x08e9:
            int r0 = r3.var(r4)
            r2 = 25
            r1.visitVarInsn(r2, r0)
            r0 = 89
            r1.visitInsn(r0)
            r0 = r39
            r0._get(r1, r3, r12)
            java.lang.String r5 = SerializeWriter
            java.lang.String r9 = "writeInt"
            r10 = 182(0xb6, float:2.55E-43)
            r1.visitMethodInsn(r10, r5, r9, r11)
            r9 = 16
            r1.visitVarInsn(r9, r6)
            r1.visitMethodInsn(r10, r5, r15, r11)
        L_0x090e:
            int r14 = r24 + 1
            r5 = r2
            r13 = r7
            r12 = r9
            r6 = r11
            r9 = r20
            r7 = 0
            r2 = r42
            r11 = r4
            r4 = r19
            goto L_0x00ab
        L_0x091e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.ASMSerializerFactory.generateWriteAsArray(java.lang.Class, com.alibaba.fastjson.asm.MethodVisitor, com.alibaba.fastjson.util.FieldInfo[], com.alibaba.fastjson.serializer.ASMSerializerFactory$Context):void");
    }

    private void generateWriteMethod(Class<?> clazz, MethodVisitor mw, FieldInfo[] getters, Context context) {
        Label end;
        String writeAsArrayMethodName;
        String str;
        int i;
        String str2;
        int i2;
        String str3;
        int i3;
        Class<?> propertyClass;
        FieldInfo property;
        boolean hasMethod;
        Class<?> cls = clazz;
        MethodVisitor methodVisitor = mw;
        FieldInfo[] fieldInfoArr = getters;
        Context context2 = context;
        Label end2 = new Label();
        int size = fieldInfoArr.length;
        String str4 = "out";
        if (!context.writeDirect) {
            Label endSupper_ = new Label();
            methodVisitor.visitVarInsn(25, context2.var(str4));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.PrettyFormat.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            Label supper_ = new Label();
            methodVisitor.visitJumpInsn(Opcodes.IFNE, supper_);
            int length = fieldInfoArr.length;
            end = end2;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    hasMethod = false;
                    break;
                }
                int i5 = length;
                if (fieldInfoArr[i4].method != null) {
                    hasMethod = true;
                    break;
                }
                i4++;
                Class<?> cls2 = clazz;
                length = i5;
            }
            if (hasMethod) {
                methodVisitor.visitVarInsn(25, context2.var(str4));
                methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreErrorGetter.mask));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, endSupper_);
            } else {
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endSupper_);
            }
            methodVisitor.visitLabel(supper_);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            String str5 = JavaBeanSerializer;
            StringBuilder sb = new StringBuilder();
            sb.append("(L");
            boolean z = hasMethod;
            sb.append(JSONSerializer);
            sb.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, str5, "write", sb.toString());
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitLabel(endSupper_);
        } else {
            end = end2;
        }
        if (!context.nonContext) {
            Label endRef_ = new Label();
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeReference", "(L" + JSONSerializer + ";Ljava/lang/Object;I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, endRef_);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitLabel(endRef_);
        }
        if (!context.writeDirect) {
            writeAsArrayMethodName = "writeAsArrayNormal";
        } else if (context.nonContext) {
            writeAsArrayMethodName = "writeAsArrayNonContext";
        } else {
            writeAsArrayMethodName = "writeAsArray";
        }
        int i6 = context.beanInfo.features;
        SerializerFeature serializerFeature = SerializerFeature.BeanToArray;
        if ((i6 & serializerFeature.mask) == 0) {
            Label endWriteAsArray_ = new Label();
            methodVisitor.visitVarInsn(25, context2.var(str4));
            methodVisitor.visitLdcInsn(Integer.valueOf(serializerFeature.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, endWriteAsArray_);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, context.className, writeAsArrayMethodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitLabel(endWriteAsArray_);
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, context.className, writeAsArrayMethodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitInsn(Opcodes.RETURN);
        }
        String str6 = "(";
        if (!context.nonContext) {
            methodVisitor.visitVarInsn(25, 1);
            String str7 = JSONSerializer;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("()");
            String str8 = SerialContext_desc;
            sb2.append(str8);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str7, "getContext", sb2.toString());
            methodVisitor.visitVarInsn(58, context2.var("parent"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context2.var("parent"));
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitLdcInsn(Integer.valueOf(context.beanInfo.features));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str7, "setContext", str6 + str8 + "Ljava/lang/Object;Ljava/lang/Object;I)V");
        }
        boolean writeClasName = (context.beanInfo.features & SerializerFeature.WriteClassName.mask) != 0;
        if (writeClasName || !context.writeDirect) {
            Label end_ = new Label();
            Label else_ = new Label();
            Label writeClass_ = new Label();
            if (!writeClasName) {
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, 4);
                methodVisitor.visitVarInsn(25, 2);
                String str9 = writeAsArrayMethodName;
                str = "parent";
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, else_);
            } else {
                str = "parent";
            }
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor.visitJumpInsn(Opcodes.IF_ACMPEQ, else_);
            methodVisitor.visitLabel(writeClass_);
            methodVisitor.visitVarInsn(25, context2.var(str4));
            methodVisitor.visitVarInsn(16, 123);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            if (context.beanInfo.typeKey != null) {
                methodVisitor.visitLdcInsn(context.beanInfo.typeKey);
            } else {
                methodVisitor.visitInsn(1);
            }
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeClassName", "(L" + JSONSerializer + ";Ljava/lang/String;Ljava/lang/Object;)V");
            methodVisitor.visitVarInsn(16, 44);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, end_);
            methodVisitor.visitLabel(else_);
            i = 123;
            methodVisitor.visitVarInsn(16, 123);
            methodVisitor.visitLabel(end_);
        } else {
            methodVisitor.visitVarInsn(16, 123);
            String str10 = writeAsArrayMethodName;
            str = "parent";
            i = 123;
        }
        methodVisitor.visitVarInsn(54, context2.var("seperator"));
        if (!context.writeDirect) {
            _before(methodVisitor, context2);
        }
        if (!context.writeDirect) {
            methodVisitor.visitVarInsn(25, context2.var(str4));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isNotWriteDefaultValue", "()Z");
            methodVisitor.visitVarInsn(54, context2.var("notWriteDefaultValue"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            String str11 = JSONSerializer;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str6);
            String str12 = SerializeFilterable_desc;
            sb3.append(str12);
            sb3.append(")Z");
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str11, "checkValue", sb3.toString());
            methodVisitor.visitVarInsn(54, context2.var("checkValue"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str11, "hasNameFilters", str6 + str12 + ")Z");
            methodVisitor.visitVarInsn(54, context2.var("hasNameFilters"));
        }
        int i7 = 0;
        while (i7 < size) {
            FieldInfo property2 = fieldInfoArr[i7];
            Class<?> propertyClass2 = property2.fieldClass;
            methodVisitor.visitLdcInsn(property2.name);
            methodVisitor.visitVarInsn(58, Context.fieldName);
            if (propertyClass2 == Byte.TYPE || propertyClass2 == Short.TYPE) {
                Class<?> cls3 = clazz;
                property = property2;
                i2 = i7;
                str3 = str6;
                str2 = str4;
                i3 = i;
                propertyClass = propertyClass2;
            } else if (propertyClass2 == Integer.TYPE) {
                Class<?> cls4 = clazz;
                property = property2;
                i2 = i7;
                str3 = str6;
                str2 = str4;
                i3 = i;
                propertyClass = propertyClass2;
            } else {
                if (propertyClass2 == Long.TYPE) {
                    Class<?> cls5 = clazz;
                    _long(cls5, methodVisitor, property2, context2);
                    i2 = i7;
                    str3 = str6;
                    str2 = str4;
                    Class<?> cls6 = cls5;
                    i3 = i;
                } else {
                    Class<?> cls7 = clazz;
                    if (propertyClass2 == Float.TYPE) {
                        _float(cls7, methodVisitor, property2, context2);
                        i2 = i7;
                        str3 = str6;
                        str2 = str4;
                        Class<?> cls8 = cls7;
                        i3 = i;
                    } else if (propertyClass2 == Double.TYPE) {
                        _double(cls7, methodVisitor, property2, context2);
                        i2 = i7;
                        str3 = str6;
                        str2 = str4;
                        Class<?> cls9 = cls7;
                        i3 = i;
                    } else if (propertyClass2 == Boolean.TYPE) {
                        Class<?> cls10 = propertyClass2;
                        i2 = i7;
                        str3 = str6;
                        str2 = str4;
                        Class<?> cls11 = cls7;
                        i3 = 123;
                        _int(clazz, mw, property2, context, context2.var("boolean"), 'Z');
                    } else {
                        FieldInfo property3 = property2;
                        i2 = i7;
                        str3 = str6;
                        str2 = str4;
                        Class<?> cls12 = cls7;
                        i3 = i;
                        Class<?> propertyClass3 = propertyClass2;
                        if (propertyClass3 == Character.TYPE) {
                            _int(clazz, mw, property3, context, context2.var(Constants.CHAR), 'C');
                        } else if (propertyClass3 == String.class) {
                            _string(cls12, methodVisitor, property3, context2);
                        } else {
                            FieldInfo property4 = property3;
                            if (propertyClass3 == BigDecimal.class) {
                                _decimal(cls12, methodVisitor, property4, context2);
                            } else if (List.class.isAssignableFrom(propertyClass3)) {
                                _list(cls12, methodVisitor, property4, context2);
                            } else if (propertyClass3.isEnum()) {
                                _enum(cls12, methodVisitor, property4, context2);
                            } else {
                                _object(cls12, methodVisitor, property4, context2);
                            }
                        }
                    }
                }
                i7 = i2 + 1;
                i = i3;
                str6 = str3;
                str4 = str2;
                fieldInfoArr = getters;
            }
            FieldInfo fieldInfo = property;
            _int(clazz, mw, property, context, context2.var(propertyClass.getName()), 'I');
            i7 = i2 + 1;
            i = i3;
            str6 = str3;
            str4 = str2;
            fieldInfoArr = getters;
        }
        Class<?> cls13 = clazz;
        int i8 = i7;
        String str13 = str6;
        String str14 = str4;
        int i9 = i;
        if (!context.writeDirect) {
            _after(methodVisitor, context2);
        }
        Label _else = new Label();
        Label _end_if = new Label();
        methodVisitor.visitVarInsn(21, context2.var("seperator"));
        methodVisitor.visitIntInsn(16, i9);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, _else);
        String str15 = str14;
        methodVisitor.visitVarInsn(25, context2.var(str15));
        methodVisitor.visitVarInsn(16, i9);
        String str16 = SerializeWriter;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str16, "write", "(I)V");
        methodVisitor.visitLabel(_else);
        methodVisitor.visitVarInsn(25, context2.var(str15));
        methodVisitor.visitVarInsn(16, 125);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str16, "write", "(I)V");
        methodVisitor.visitLabel(_end_if);
        methodVisitor.visitLabel(end);
        if (!context.nonContext) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context2.var(str));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", str13 + SerialContext_desc + ")V");
        }
    }

    private void _object(Class<?> cls, MethodVisitor mw, FieldInfo property, Context context) {
        Label _end = new Label();
        _nameApply(mw, property, context, _end);
        _get(mw, context, property);
        mw.visitVarInsn(58, context.var("object"));
        _filters(mw, property, context, _end);
        _writeObject(mw, property, context, _end);
        mw.visitLabel(_end);
    }

    private void _enum(Class<?> cls, MethodVisitor mw, FieldInfo fieldInfo, Context context) {
        Label _not_null = new Label();
        Label _end_if = new Label();
        Label _end = new Label();
        _nameApply(mw, fieldInfo, context, _end);
        _get(mw, context, fieldInfo);
        mw.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Enum");
        mw.visitVarInsn(58, context.var("enum"));
        _filters(mw, fieldInfo, context, _end);
        mw.visitVarInsn(25, context.var("enum"));
        mw.visitJumpInsn(199, _not_null);
        _if_write_null(mw, fieldInfo, context);
        mw.visitJumpInsn(Opcodes.GOTO, _end_if);
        mw.visitLabel(_not_null);
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, context.var("enum"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Enum", "name", "()Ljava/lang/String;");
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValueStringWithDoubleQuote", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            String str = SerializeWriter;
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "write", "(I)V");
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitInsn(3);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "writeFieldName", "(Ljava/lang/String;Z)V");
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("enum"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
            mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        }
        _seperator(mw, context);
        mw.visitLabel(_end_if);
        mw.visitLabel(_end);
    }

    private void _int(Class<?> cls, MethodVisitor mw, FieldInfo property, Context context, int var, char type) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(54, var);
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(21, var);
        String str = SerializeWriter;
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "writeFieldValue", "(CLjava/lang/String;" + type + ")V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _long(Class<?> cls, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(55, context.var(Constants.LONG, 2));
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(22, context.var(Constants.LONG, 2));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;J)V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _float(Class<?> cls, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(56, context.var("float"));
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(23, context.var("float"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;F)V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _double(Class<?> cls, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(57, context.var(Constants.DOUBLE, 2));
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(24, context.var(Constants.DOUBLE, 2));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;D)V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _get(MethodVisitor mw, Context context, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method != null) {
            mw.visitVarInsn(25, context.var("entity"));
            Class<?> declaringClass = method.getDeclaringClass();
            mw.visitMethodInsn(declaringClass.isInterface() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL, ASMUtils.type(declaringClass), method.getName(), ASMUtils.desc(method));
            if (!method.getReturnType().equals(fieldInfo.fieldClass)) {
                mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldInfo.fieldClass));
                return;
            }
            return;
        }
        mw.visitVarInsn(25, context.var("entity"));
        Field field = fieldInfo.field;
        mw.visitFieldInsn(180, ASMUtils.type(fieldInfo.declaringClass), field.getName(), ASMUtils.desc(field.getType()));
        if (!field.getType().equals(fieldInfo.fieldClass)) {
            mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldInfo.fieldClass));
        }
    }

    private void _decimal(Class<?> cls, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(58, context.var("decimal"));
        _filters(mw, property, context, end_);
        Label if_ = new Label();
        Label else_ = new Label();
        Label endIf_ = new Label();
        mw.visitLabel(if_);
        mw.visitVarInsn(25, context.var("decimal"));
        mw.visitJumpInsn(199, else_);
        _if_write_null(mw, property, context);
        mw.visitJumpInsn(Opcodes.GOTO, endIf_);
        mw.visitLabel(else_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(25, context.var("decimal"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/math/BigDecimal;)V");
        _seperator(mw, context);
        mw.visitJumpInsn(Opcodes.GOTO, endIf_);
        mw.visitLabel(endIf_);
        mw.visitLabel(end_);
    }

    private void _string(Class<?> cls, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        if (property.name.equals(context.beanInfo.typeKey)) {
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 4);
            mw.visitVarInsn(25, 2);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
            mw.visitJumpInsn(Opcodes.IFNE, end_);
        }
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(58, context.var(TypedValues.Custom.S_STRING));
        _filters(mw, property, context, end_);
        Label else_ = new Label();
        Label endIf_ = new Label();
        mw.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
        mw.visitJumpInsn(199, else_);
        _if_write_null(mw, property, context);
        mw.visitJumpInsn(Opcodes.GOTO, endIf_);
        mw.visitLabel(else_);
        if ("trim".equals(property.format)) {
            mw.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "trim", "()Ljava/lang/String;");
            mw.visitVarInsn(58, context.var(TypedValues.Custom.S_STRING));
        }
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValueStringWithDoubleQuoteCheck", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/lang/String;)V");
        }
        _seperator(mw, context);
        mw.visitLabel(endIf_);
        mw.visitLabel(end_);
    }

    private void _list(Class<?> cls, MethodVisitor mw, FieldInfo fieldInfo, Context context) {
        Label _end_if_3;
        int i;
        int i2;
        int i3;
        Label forItemClassIfElse_;
        Label for_;
        Label forEnd_;
        String str;
        Label forItemNullEnd_;
        String str2;
        FieldInfo fieldInfo2;
        Label forItemClassIfEnd_;
        Label forItemClassIfEnd_2;
        String str3;
        String str4;
        MethodVisitor methodVisitor = mw;
        FieldInfo fieldInfo3 = fieldInfo;
        Context context2 = context;
        java.lang.reflect.Type propertyType = fieldInfo3.fieldType;
        java.lang.reflect.Type elementType = TypeUtils.getCollectionItemType(propertyType);
        Class<?> elementClass = null;
        if (elementType instanceof Class) {
            elementClass = (Class) elementType;
        }
        if (elementClass == Object.class || elementClass == Serializable.class) {
            elementClass = null;
        }
        Label end_ = new Label();
        Label else_ = new Label();
        Label endIf_ = new Label();
        _nameApply(methodVisitor, fieldInfo3, context2, end_);
        _get(methodVisitor, context2, fieldInfo3);
        methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, "java/util/List");
        methodVisitor.visitVarInsn(58, context2.var("list"));
        _filters(methodVisitor, fieldInfo3, context2, end_);
        methodVisitor.visitVarInsn(25, context2.var("list"));
        methodVisitor.visitJumpInsn(199, else_);
        _if_write_null(methodVisitor, fieldInfo3, context2);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endIf_);
        methodVisitor.visitLabel(else_);
        methodVisitor.visitVarInsn(25, context2.var("out"));
        methodVisitor.visitVarInsn(21, context2.var("seperator"));
        String str5 = SerializeWriter;
        java.lang.reflect.Type type = propertyType;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "write", "(I)V");
        _writeFieldName(methodVisitor, context2);
        Label label = else_;
        methodVisitor.visitVarInsn(25, context2.var("list"));
        Label end_2 = end_;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I");
        methodVisitor.visitVarInsn(54, context2.var("size"));
        Label _else_3 = new Label();
        Label _end_if_32 = new Label();
        Label endIf_2 = endIf_;
        methodVisitor.visitVarInsn(21, context2.var("size"));
        methodVisitor.visitInsn(3);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, _else_3);
        methodVisitor.visitVarInsn(25, context2.var("out"));
        methodVisitor.visitLdcInsn("[]");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "write", "(Ljava/lang/String;)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, _end_if_32);
        methodVisitor.visitLabel(_else_3);
        if (!context.nonContext) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context2.var("list"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            Label label2 = _else_3;
            _end_if_3 = _end_if_32;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)V");
        } else {
            _end_if_3 = _end_if_32;
        }
        if (elementType != String.class) {
            i3 = 25;
        } else if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context2.var("out"));
            methodVisitor.visitVarInsn(25, context2.var("list"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "write", "(Ljava/util/List;)V");
            FieldInfo fieldInfo4 = fieldInfo3;
            i = 182;
            java.lang.reflect.Type type2 = elementType;
            i2 = 25;
            methodVisitor.visitVarInsn(i2, 1);
            methodVisitor.visitMethodInsn(i, JSONSerializer, "popContext", "()V");
            methodVisitor.visitLabel(_end_if_3);
            _seperator(methodVisitor, context2);
            methodVisitor.visitLabel(endIf_2);
            methodVisitor.visitLabel(end_2);
        } else {
            i3 = 25;
        }
        methodVisitor.visitVarInsn(i3, context2.var("out"));
        methodVisitor.visitVarInsn(16, 91);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "write", "(I)V");
        Label for_2 = new Label();
        Label forFirst_ = new Label();
        Label forEnd_2 = new Label();
        methodVisitor.visitInsn(3);
        java.lang.reflect.Type elementType2 = elementType;
        methodVisitor.visitVarInsn(54, context2.var("i"));
        methodVisitor.visitLabel(for_2);
        methodVisitor.visitVarInsn(21, context2.var("i"));
        methodVisitor.visitVarInsn(21, context2.var("size"));
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, forEnd_2);
        methodVisitor.visitVarInsn(21, context2.var("i"));
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, forFirst_);
        methodVisitor.visitVarInsn(25, context2.var("out"));
        methodVisitor.visitVarInsn(16, 44);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "write", "(I)V");
        methodVisitor.visitLabel(forFirst_);
        methodVisitor.visitVarInsn(25, context2.var("list"));
        methodVisitor.visitVarInsn(21, context2.var("i"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
        methodVisitor.visitVarInsn(58, context2.var("list_item"));
        Label forItemNullEnd_2 = new Label();
        Label forItemNullElse_ = new Label();
        methodVisitor.visitVarInsn(25, context2.var("list_item"));
        methodVisitor.visitJumpInsn(199, forItemNullElse_);
        methodVisitor.visitVarInsn(25, context2.var("out"));
        Label label3 = forFirst_;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "writeNull", "()V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, forItemNullEnd_2);
        methodVisitor.visitLabel(forItemNullElse_);
        Label forItemClassIfEnd_3 = new Label();
        Label forItemClassIfElse_2 = new Label();
        Label label4 = forItemNullElse_;
        String str6 = "(I)V";
        String str7 = str5;
        if (elementClass == null || !Modifier.isPublic(elementClass.getModifiers())) {
            for_ = for_2;
            forItemNullEnd_ = forItemNullEnd_2;
            forItemClassIfEnd_ = forItemClassIfEnd_3;
            forEnd_ = forEnd_2;
            str = "out";
            forItemClassIfElse_ = forItemClassIfElse_2;
            str2 = "write";
            fieldInfo2 = fieldInfo;
        } else {
            str = "out";
            methodVisitor.visitVarInsn(25, context2.var("list_item"));
            forEnd_ = forEnd_2;
            for_ = for_2;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
            methodVisitor.visitJumpInsn(166, forItemClassIfElse_2);
            fieldInfo2 = fieldInfo;
            _getListFieldItemSer(context2, methodVisitor, fieldInfo2, elementClass);
            methodVisitor.visitVarInsn(58, context2.var("list_item_desc"));
            Label instanceOfElse_ = new Label();
            Label instanceOfEnd_ = new Label();
            if (context.writeDirect) {
                if (!context.nonContext || !context.writeDirect) {
                    str4 = "write";
                } else {
                    str4 = "writeDirectNonContext";
                }
                String writeMethodName = str4;
                forItemNullEnd_ = forItemNullEnd_2;
                forItemClassIfElse_ = forItemClassIfElse_2;
                methodVisitor.visitVarInsn(25, context2.var("list_item_desc"));
                String str8 = JavaBeanSerializer;
                methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, str8);
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, instanceOfElse_);
                forItemClassIfEnd_2 = forItemClassIfEnd_3;
                methodVisitor.visitVarInsn(25, context2.var("list_item_desc"));
                methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, str8);
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, context2.var("list_item"));
                if (context.nonContext) {
                    methodVisitor.visitInsn(1);
                } else {
                    methodVisitor.visitVarInsn(21, context2.var("i"));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                }
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
                str3 = "write";
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str8, writeMethodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodVisitor.visitJumpInsn(Opcodes.GOTO, instanceOfEnd_);
                methodVisitor.visitLabel(instanceOfElse_);
            } else {
                forItemNullEnd_ = forItemNullEnd_2;
                forItemClassIfEnd_2 = forItemClassIfEnd_3;
                forItemClassIfElse_ = forItemClassIfElse_2;
                str3 = "write";
            }
            methodVisitor.visitVarInsn(25, context2.var("list_item_desc"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context2.var("list_item"));
            if (context.nonContext) {
                methodVisitor.visitInsn(1);
            } else {
                methodVisitor.visitVarInsn(21, context2.var("i"));
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            }
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
            str2 = str3;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, str2, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitLabel(instanceOfEnd_);
            forItemClassIfEnd_ = forItemClassIfEnd_2;
            methodVisitor.visitJumpInsn(Opcodes.GOTO, forItemClassIfEnd_);
        }
        methodVisitor.visitLabel(forItemClassIfElse_);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context2.var("list_item"));
        if (context.nonContext) {
            methodVisitor.visitInsn(1);
        } else {
            methodVisitor.visitVarInsn(21, context2.var("i"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        }
        if (elementClass == null || !Modifier.isPublic(elementClass.getModifiers())) {
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
        } else {
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) (Class) elementType2)));
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        }
        methodVisitor.visitLabel(forItemClassIfEnd_);
        methodVisitor.visitLabel(forItemNullEnd_);
        methodVisitor.visitIincInsn(context2.var("i"), 1);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, for_);
        methodVisitor.visitLabel(forEnd_);
        i2 = 25;
        methodVisitor.visitVarInsn(25, context2.var(str));
        methodVisitor.visitVarInsn(16, 93);
        i = Opcodes.INVOKEVIRTUAL;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str7, str2, str6);
        methodVisitor.visitVarInsn(i2, 1);
        methodVisitor.visitMethodInsn(i, JSONSerializer, "popContext", "()V");
        methodVisitor.visitLabel(_end_if_3);
        _seperator(methodVisitor, context2);
        methodVisitor.visitLabel(endIf_2);
        methodVisitor.visitLabel(end_2);
    }

    private void _filters(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        if (property.fieldTransient) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.SkipTransientField.mask));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(Opcodes.IFNE, _end);
        }
        _notWriteDefault(mw, property, context, _end);
        if (!context.writeDirect) {
            _apply(mw, property, context);
            mw.visitJumpInsn(Opcodes.IFEQ, _end);
            _processKey(mw, property, context);
            _processValue(mw, property, context, _end);
        }
    }

    private void _nameApply(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        if (!context.writeDirect) {
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, Context.fieldName);
            String str = JavaBeanSerializer;
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "applyName", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;)Z");
            mw.visitJumpInsn(Opcodes.IFEQ, _end);
            _labelApply(mw, property, context, _end);
        }
        if (property.field == null) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreNonFieldGetter.mask));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(Opcodes.IFNE, _end);
        }
    }

    private void _labelApply(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(property.label);
        String str = JavaBeanSerializer;
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "applyLabel", "(L" + JSONSerializer + ";Ljava/lang/String;)Z");
        mw.visitJumpInsn(Opcodes.IFEQ, _end);
    }

    private void _writeObject(MethodVisitor mw, FieldInfo fieldInfo, Context context, Label _end) {
        Label classIfElse_;
        String format;
        Class<String> cls;
        Label classIfEnd_;
        String writeMethodName;
        MethodVisitor methodVisitor = mw;
        FieldInfo fieldInfo2 = fieldInfo;
        Context context2 = context;
        Class<String> cls2 = String.class;
        String format2 = fieldInfo.getFormat();
        Class<?> fieldClass = fieldInfo2.fieldClass;
        Label notNull_ = new Label();
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context2.var("object"));
        } else {
            methodVisitor.visitVarInsn(25, Context.processValue);
        }
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(58, context2.var("object"));
        methodVisitor.visitJumpInsn(199, notNull_);
        _if_write_null(mw, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, _end);
        methodVisitor.visitLabel(notNull_);
        methodVisitor.visitVarInsn(25, context2.var("out"));
        methodVisitor.visitVarInsn(21, context2.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        _writeFieldName(methodVisitor, context2);
        Label classIfEnd_2 = new Label();
        Label classIfElse_2 = new Label();
        if (!Modifier.isPublic(fieldClass.getModifiers())) {
            cls = cls2;
            format = format2;
            Class<?> cls3 = fieldClass;
            Label label = notNull_;
            classIfEnd_ = classIfEnd_2;
            classIfElse_ = classIfElse_2;
        } else if (!ParserConfig.isPrimitive2(fieldClass)) {
            Label label2 = notNull_;
            methodVisitor.visitVarInsn(25, context2.var("object"));
            cls = cls2;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldClass)));
            methodVisitor.visitJumpInsn(166, classIfElse_2);
            _getFieldSer(context2, methodVisitor, fieldInfo2);
            methodVisitor.visitVarInsn(58, context2.var("fied_ser"));
            Label instanceOfElse_ = new Label();
            Label instanceOfEnd_ = new Label();
            Class<?> cls4 = fieldClass;
            methodVisitor.visitVarInsn(25, context2.var("fied_ser"));
            String str = JavaBeanSerializer;
            methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, str);
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, instanceOfElse_);
            int i = fieldInfo2.serialzeFeatures;
            boolean disableCircularReferenceDetect = (SerializerFeature.DisableCircularReferenceDetect.mask & i) != 0;
            format = format2;
            boolean fieldBeanToArray = (SerializerFeature.BeanToArray.mask & i) != 0;
            if (disableCircularReferenceDetect || (context.nonContext && context.writeDirect)) {
                writeMethodName = fieldBeanToArray ? "writeAsArrayNonContext" : "writeDirectNonContext";
            } else if (fieldBeanToArray) {
                writeMethodName = "writeAsArray";
            } else {
                writeMethodName = "write";
            }
            boolean z = disableCircularReferenceDetect;
            boolean z2 = fieldBeanToArray;
            methodVisitor.visitVarInsn(25, context2.var("fied_ser"));
            methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, str);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context2.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            String access$300 = context.className;
            StringBuilder sb = new StringBuilder();
            classIfElse_ = classIfElse_2;
            sb.append(fieldInfo2.name);
            sb.append("_asm_fieldType");
            methodVisitor.visitFieldInsn(180, access$300, sb.toString(), "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
            StringBuilder sb2 = new StringBuilder();
            sb2.append("(L");
            String str2 = JSONSerializer;
            sb2.append(str2);
            sb2.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, writeMethodName, sb2.toString());
            methodVisitor.visitJumpInsn(Opcodes.GOTO, instanceOfEnd_);
            methodVisitor.visitLabel(instanceOfElse_);
            methodVisitor.visitVarInsn(25, context2.var("fied_ser"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context2.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            String access$3002 = context.className;
            methodVisitor.visitFieldInsn(180, access$3002, fieldInfo2.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
            String str3 = ObjectSerializer;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, str3, "write", "(L" + str2 + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitLabel(instanceOfEnd_);
            classIfEnd_ = classIfEnd_2;
            methodVisitor.visitJumpInsn(Opcodes.GOTO, classIfEnd_);
        } else {
            cls = cls2;
            format = format2;
            Class<?> cls5 = fieldClass;
            Label label3 = notNull_;
            classIfEnd_ = classIfEnd_2;
            classIfElse_ = classIfElse_2;
        }
        methodVisitor.visitLabel(classIfElse_);
        methodVisitor.visitVarInsn(25, 1);
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context2.var("object"));
        } else {
            methodVisitor.visitVarInsn(25, Context.processValue);
        }
        if (format != null) {
            methodVisitor.visitLdcInsn(format);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, Context.fieldName);
            java.lang.reflect.Type type = fieldInfo2.fieldType;
            if (!(type instanceof Class) || !((Class) type).isPrimitive()) {
                Class<String> cls6 = cls;
                if (fieldInfo2.fieldClass == cls6) {
                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) cls6)));
                } else {
                    methodVisitor.visitVarInsn(25, 0);
                    String access$3003 = context.className;
                    methodVisitor.visitFieldInsn(180, access$3003, fieldInfo2.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                }
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            } else {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            }
        }
        methodVisitor.visitLabel(classIfEnd_);
        _seperator(methodVisitor, context2);
    }

    private void _before(MethodVisitor mw, Context context) {
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(21, context.var("seperator"));
        String str = JavaBeanSerializer;
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "writeBefore", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        mw.visitVarInsn(54, context.var("seperator"));
    }

    private void _after(MethodVisitor mw, Context context) {
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(21, context.var("seperator"));
        String str = JavaBeanSerializer;
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "writeAfter", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        mw.visitVarInsn(54, context.var("seperator"));
    }

    private void _notWriteDefault(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        if (!context.writeDirect) {
            Label elseLabel = new Label();
            mw.visitVarInsn(21, context.var("notWriteDefaultValue"));
            mw.visitJumpInsn(Opcodes.IFEQ, elseLabel);
            Class<?> propertyClass = property.fieldClass;
            if (propertyClass == Boolean.TYPE) {
                mw.visitVarInsn(21, context.var("boolean"));
                mw.visitJumpInsn(Opcodes.IFEQ, _end);
            } else if (propertyClass == Byte.TYPE) {
                mw.visitVarInsn(21, context.var(Constants.BYTE));
                mw.visitJumpInsn(Opcodes.IFEQ, _end);
            } else if (propertyClass == Short.TYPE) {
                mw.visitVarInsn(21, context.var(Constants.SHORT));
                mw.visitJumpInsn(Opcodes.IFEQ, _end);
            } else if (propertyClass == Integer.TYPE) {
                mw.visitVarInsn(21, context.var(Constants.INT));
                mw.visitJumpInsn(Opcodes.IFEQ, _end);
            } else if (propertyClass == Long.TYPE) {
                mw.visitVarInsn(22, context.var(Constants.LONG));
                mw.visitInsn(9);
                mw.visitInsn(Opcodes.LCMP);
                mw.visitJumpInsn(Opcodes.IFEQ, _end);
            } else if (propertyClass == Float.TYPE) {
                mw.visitVarInsn(23, context.var("float"));
                mw.visitInsn(11);
                mw.visitInsn(Opcodes.FCMPL);
                mw.visitJumpInsn(Opcodes.IFEQ, _end);
            } else if (propertyClass == Double.TYPE) {
                mw.visitVarInsn(24, context.var(Constants.DOUBLE));
                mw.visitInsn(14);
                mw.visitInsn(Opcodes.DCMPL);
                mw.visitJumpInsn(Opcodes.IFEQ, _end);
            }
            mw.visitLabel(elseLabel);
        }
    }

    private void _apply(MethodVisitor mw, FieldInfo property, Context context) {
        Class<?> propertyClass = property.fieldClass;
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, Context.fieldName);
        if (propertyClass == Byte.TYPE) {
            mw.visitVarInsn(21, context.var(Constants.BYTE));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (propertyClass == Short.TYPE) {
            mw.visitVarInsn(21, context.var(Constants.SHORT));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (propertyClass == Integer.TYPE) {
            mw.visitVarInsn(21, context.var(Constants.INT));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (propertyClass == Character.TYPE) {
            mw.visitVarInsn(21, context.var(Constants.CHAR));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (propertyClass == Long.TYPE) {
            mw.visitVarInsn(22, context.var(Constants.LONG, 2));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (propertyClass == Float.TYPE) {
            mw.visitVarInsn(23, context.var("float"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (propertyClass == Double.TYPE) {
            mw.visitVarInsn(24, context.var(Constants.DOUBLE, 2));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (propertyClass == Boolean.TYPE) {
            mw.visitVarInsn(21, context.var("boolean"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (propertyClass == BigDecimal.class) {
            mw.visitVarInsn(25, context.var("decimal"));
        } else if (propertyClass == String.class) {
            mw.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
        } else if (propertyClass.isEnum()) {
            mw.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(propertyClass)) {
            mw.visitVarInsn(25, context.var("list"));
        } else {
            mw.visitVarInsn(25, context.var("object"));
        }
        String str = JavaBeanSerializer;
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "apply", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
    }

    private void _processValue(MethodVisitor mw, FieldInfo fieldInfo, Context context, Label _end) {
        MethodVisitor methodVisitor = mw;
        FieldInfo fieldInfo2 = fieldInfo;
        Context context2 = context;
        Class<BeanContext> cls = BeanContext.class;
        Label processKeyElse_ = new Label();
        Class<?> fieldClass = fieldInfo2.fieldClass;
        if (fieldClass.isPrimitive()) {
            Label checkValueEnd_ = new Label();
            methodVisitor.visitVarInsn(21, context2.var("checkValue"));
            methodVisitor.visitJumpInsn(Opcodes.IFNE, checkValueEnd_);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(58, Context.processValue);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, processKeyElse_);
            methodVisitor.visitLabel(checkValueEnd_);
        }
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitLdcInsn(Integer.valueOf(context2.getFieldOrinal(fieldInfo2.name)));
        String str = JavaBeanSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "getBeanContext", "(I)" + ASMUtils.desc((Class<?>) cls));
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (fieldClass == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context2.var(Constants.BYTE));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (fieldClass == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context2.var(Constants.SHORT));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (fieldClass == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context2.var(Constants.INT));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (fieldClass == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context2.var(Constants.CHAR));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (fieldClass == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context2.var(Constants.LONG, 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (fieldClass == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context2.var("float"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (fieldClass == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context2.var(Constants.DOUBLE, 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (fieldClass == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context2.var("boolean"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (fieldClass == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context2.var("decimal"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (fieldClass == String.class) {
            methodVisitor.visitVarInsn(25, context2.var(TypedValues.Custom.S_STRING));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (fieldClass.isEnum()) {
            methodVisitor.visitVarInsn(25, context2.var("enum"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (List.class.isAssignableFrom(fieldClass)) {
            methodVisitor.visitVarInsn(25, context2.var("list"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else {
            methodVisitor.visitVarInsn(25, context2.var("object"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "processValue", "(L" + JSONSerializer + Constants.PACKNAME_END + ASMUtils.desc((Class<?>) cls) + "Ljava/lang/Object;Ljava/lang/String;" + "Ljava/lang/Object;" + ")Ljava/lang/Object;");
        methodVisitor.visitVarInsn(58, Context.processValue);
        methodVisitor.visitVarInsn(25, Context.original);
        methodVisitor.visitVarInsn(25, Context.processValue);
        methodVisitor.visitJumpInsn(Opcodes.IF_ACMPEQ, processKeyElse_);
        _writeObject(mw, fieldInfo, context, _end);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, _end);
        methodVisitor.visitLabel(processKeyElse_);
    }

    private void _processKey(MethodVisitor mw, FieldInfo property, Context context) {
        Label _else_processKey = new Label();
        mw.visitVarInsn(21, context.var("hasNameFilters"));
        mw.visitJumpInsn(Opcodes.IFEQ, _else_processKey);
        Class<?> propertyClass = property.fieldClass;
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, Context.fieldName);
        if (propertyClass == Byte.TYPE) {
            mw.visitVarInsn(21, context.var(Constants.BYTE));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (propertyClass == Short.TYPE) {
            mw.visitVarInsn(21, context.var(Constants.SHORT));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (propertyClass == Integer.TYPE) {
            mw.visitVarInsn(21, context.var(Constants.INT));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (propertyClass == Character.TYPE) {
            mw.visitVarInsn(21, context.var(Constants.CHAR));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (propertyClass == Long.TYPE) {
            mw.visitVarInsn(22, context.var(Constants.LONG, 2));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (propertyClass == Float.TYPE) {
            mw.visitVarInsn(23, context.var("float"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (propertyClass == Double.TYPE) {
            mw.visitVarInsn(24, context.var(Constants.DOUBLE, 2));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (propertyClass == Boolean.TYPE) {
            mw.visitVarInsn(21, context.var("boolean"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (propertyClass == BigDecimal.class) {
            mw.visitVarInsn(25, context.var("decimal"));
        } else if (propertyClass == String.class) {
            mw.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
        } else if (propertyClass.isEnum()) {
            mw.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(propertyClass)) {
            mw.visitVarInsn(25, context.var("list"));
        } else {
            mw.visitVarInsn(25, context.var("object"));
        }
        String str = JavaBeanSerializer;
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "processKey", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
        mw.visitVarInsn(58, Context.fieldName);
        mw.visitLabel(_else_processKey);
    }

    private void _if_write_null(MethodVisitor mw, FieldInfo fieldInfo, Context context) {
        int writeNullFeatures;
        MethodVisitor methodVisitor = mw;
        Context context2 = context;
        Class<Boolean> cls = Boolean.class;
        Class<String> cls2 = String.class;
        Class<?> propertyClass = fieldInfo.fieldClass;
        Label _if = new Label();
        Label _else = new Label();
        Label _write_null = new Label();
        Label _end_if = new Label();
        methodVisitor.visitLabel(_if);
        JSONField annotation = fieldInfo.getAnnotation();
        int features = 0;
        if (annotation != null) {
            features = SerializerFeature.of(annotation.serialzeFeatures());
        }
        JSONType jsonType = context.beanInfo.jsonType;
        if (jsonType != null) {
            features |= SerializerFeature.of(jsonType.serialzeFeatures());
        }
        if (propertyClass == cls2) {
            writeNullFeatures = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullStringAsEmpty.getMask();
        } else if (Number.class.isAssignableFrom(propertyClass)) {
            writeNullFeatures = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullNumberAsZero.getMask();
        } else if (Collection.class.isAssignableFrom(propertyClass)) {
            writeNullFeatures = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullListAsEmpty.getMask();
        } else if (cls == propertyClass) {
            writeNullFeatures = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullBooleanAsFalse.getMask();
        } else {
            writeNullFeatures = SerializerFeature.WRITE_MAP_NULL_FEATURES;
        }
        Label label = _if;
        if ((features & writeNullFeatures) == 0) {
            methodVisitor.visitVarInsn(25, context2.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(writeNullFeatures));
            JSONField jSONField = annotation;
            JSONType jSONType = jsonType;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, _else);
        } else {
            JSONType jSONType2 = jsonType;
        }
        methodVisitor.visitLabel(_write_null);
        methodVisitor.visitVarInsn(25, context2.var("out"));
        methodVisitor.visitVarInsn(21, context2.var("seperator"));
        String str = SerializeWriter;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "write", "(I)V");
        _writeFieldName(methodVisitor, context2);
        methodVisitor.visitVarInsn(25, context2.var("out"));
        methodVisitor.visitLdcInsn(Integer.valueOf(features));
        if (propertyClass == cls2 || propertyClass == Character.class) {
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullStringAsEmpty.mask));
        } else if (Number.class.isAssignableFrom(propertyClass)) {
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullNumberAsZero.mask));
        } else if (propertyClass == cls) {
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullBooleanAsFalse.mask));
        } else if (Collection.class.isAssignableFrom(propertyClass) || propertyClass.isArray()) {
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullListAsEmpty.mask));
        } else {
            methodVisitor.visitLdcInsn(0);
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "writeNull", "(II)V");
        _seperator(methodVisitor, context2);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, _end_if);
        methodVisitor.visitLabel(_else);
        methodVisitor.visitLabel(_end_if);
    }

    private void _writeFieldName(MethodVisitor mw, Context context) {
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldNameDirect", "(Ljava/lang/String;)V");
            return;
        }
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitInsn(3);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldName", "(Ljava/lang/String;Z)V");
    }

    private void _seperator(MethodVisitor mw, Context context) {
        mw.visitVarInsn(16, 44);
        mw.visitVarInsn(54, context.var("seperator"));
    }

    private void _getListFieldItemSer(Context context, MethodVisitor mw, FieldInfo fieldInfo, Class<?> itemType) {
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        String str = ObjectSerializer_desc;
        mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_ser_", str);
        mw.visitJumpInsn(199, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(Type.getType(ASMUtils.desc(itemType)));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + str);
        mw.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo.name + "_asm_list_item_ser_", str);
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_ser_", str);
    }

    private void _getFieldSer(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        String str = ObjectSerializer_desc;
        mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_ser_", str);
        mw.visitJumpInsn(199, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + str);
        mw.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo.name + "_asm_ser_", str);
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_ser_", str);
    }
}
