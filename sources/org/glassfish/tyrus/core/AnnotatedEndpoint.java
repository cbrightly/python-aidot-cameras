package org.glassfish.tyrus.core;

import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Decoder;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Encoder;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Extension;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import jakarta.websocket.a;
import jakarta.websocket.d;
import jakarta.websocket.server.ServerEndpointConfig;
import jakarta.websocket.server.b;
import jakarta.websocket.server.c;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.TyrusServerEndpointConfig;
import org.glassfish.tyrus.core.coder.PrimitiveDecoders;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.core.monitoring.EndpointEventListener;

public class AnnotatedEndpoint extends Endpoint {
    private static final Logger LOGGER = Logger.getLogger(AnnotatedEndpoint.class.getName());
    private final Class<?> annotatedClass;
    private final Object annotatedInstance;
    private final ComponentProviderService componentProvider;
    /* access modifiers changed from: private */
    public final EndpointConfig configuration;
    private final EndpointEventListener endpointEventListener;
    private final Set<MessageHandlerFactory> messageHandlerFactories = new HashSet();
    private final Method onCloseMethod;
    private final ParameterExtractor[] onCloseParameters;
    private final Method onErrorMethod;
    private final ParameterExtractor[] onErrorParameters;
    private final Method onOpenMethod;
    private final ParameterExtractor[] onOpenParameters;

    public interface ParameterExtractor {
        Object value(Session session, Object... objArr);
    }

    public static AnnotatedEndpoint fromClass(Class<?> annotatedClass2, ComponentProviderService componentProvider2, boolean isServerEndpoint, int incomingBufferSize, ErrorCollector collector, EndpointEventListener endpointEventListener2) {
        return fromClass(annotatedClass2, componentProvider2, isServerEndpoint, incomingBufferSize, collector, endpointEventListener2, Collections.emptySet());
    }

    public static AnnotatedEndpoint fromClass(Class<?> annotatedClass2, ComponentProviderService componentProvider2, boolean isServerEndpoint, int incomingBufferSize, ErrorCollector collector, EndpointEventListener endpointEventListener2, Set<Extension> extensions) {
        return new AnnotatedEndpoint(annotatedClass2, (Object) null, componentProvider2, Boolean.valueOf(isServerEndpoint), incomingBufferSize, collector, endpointEventListener2, extensions);
    }

    public static AnnotatedEndpoint fromInstance(Object annotatedInstance2, ComponentProviderService componentProvider2, boolean isServerEndpoint, int incomingBufferSize, ErrorCollector collector) {
        return fromInstance(annotatedInstance2, componentProvider2, isServerEndpoint, incomingBufferSize, collector, Collections.emptySet());
    }

    public static AnnotatedEndpoint fromInstance(Object annotatedInstance2, ComponentProviderService componentProvider2, boolean isServerEndpoint, int incomingBufferSize, ErrorCollector collector, Set<Extension> extensions) {
        return new AnnotatedEndpoint(annotatedInstance2.getClass(), annotatedInstance2, componentProvider2, Boolean.valueOf(isServerEndpoint), incomingBufferSize, collector, EndpointEventListener.NO_OP, extensions);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v39, resolved type: java.util.Map$Entry} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private AnnotatedEndpoint(java.lang.Class<?> r38, java.lang.Object r39, org.glassfish.tyrus.core.ComponentProviderService r40, java.lang.Boolean r41, int r42, org.glassfish.tyrus.core.ErrorCollector r43, org.glassfish.tyrus.core.monitoring.EndpointEventListener r44, java.util.Set<jakarta.websocket.Extension> r45) {
        /*
            r37 = this;
            r7 = r37
            r8 = r38
            r9 = r40
            r10 = r43
            java.lang.Class<java.lang.Boolean> r11 = java.lang.Boolean.class
            r37.<init>()
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            r7.messageHandlerFactories = r0
            boolean r0 = r41.booleanValue()
            r12 = r45
            jakarta.websocket.EndpointConfig r0 = r7.createEndpointConfig(r8, r0, r10, r12)
            r7.configuration = r0
            r13 = r39
            r7.annotatedInstance = r13
            r7.annotatedClass = r8
            r14 = r44
            r7.endpointEventListener = r14
            boolean r1 = r41.booleanValue()
            if (r1 == 0) goto L_0x004e
            java.lang.Class<org.glassfish.tyrus.core.TyrusServerEndpointConfigurator> r1 = org.glassfish.tyrus.core.TyrusServerEndpointConfigurator.class
            r2 = r0
            jakarta.websocket.server.ServerEndpointConfig r2 = (jakarta.websocket.server.ServerEndpointConfig) r2
            jakarta.websocket.server.ServerEndpointConfig$Configurator r2 = r2.getConfigurator()
            java.lang.Class r2 = r2.getClass()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0046
            r7.componentProvider = r9
            goto L_0x0050
        L_0x0046:
            org.glassfish.tyrus.core.AnnotatedEndpoint$1 r1 = new org.glassfish.tyrus.core.AnnotatedEndpoint$1
            r1.<init>(r9)
            r7.componentProvider = r1
            goto L_0x0050
        L_0x004e:
            r7.componentProvider = r9
        L_0x0050:
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            java.util.HashMap r15 = new java.util.HashMap
            r15.<init>()
            r16 = r1
            org.glassfish.tyrus.core.AnnotatedClassValidityChecker r1 = new org.glassfish.tyrus.core.AnnotatedClassValidityChecker
            r17 = r2
            java.util.List r2 = r0.getEncoders()
            java.util.List r0 = r0.getDecoders()
            r1.<init>(r8, r2, r0, r10)
            r2 = r1
            java.lang.reflect.Method[] r1 = r38.getMethods()
            int r0 = r1.length
            r18 = r5
            r19 = r6
            r8 = 0
            r5 = r3
            r6 = r4
            r3 = r16
            r4 = r17
        L_0x007d:
            if (r8 >= r0) goto L_0x038e
            r12 = r1[r8]
            boolean r20 = r12.isBridge()
            if (r20 == 0) goto L_0x0090
            r24 = r0
            r25 = r1
            r13 = r2
            r29 = 0
            goto L_0x037f
        L_0x0090:
            java.lang.annotation.Annotation[] r13 = r12.getAnnotations()
            int r14 = r13.length
            r20 = r5
            r21 = r6
            r22 = r18
            r23 = r19
            r5 = 0
            r18 = r3
            r19 = r4
        L_0x00a2:
            if (r5 >= r14) goto L_0x036c
            r6 = r13[r5]
            boolean r3 = r6 instanceof jakarta.websocket.h
            if (r3 == 0) goto L_0x00f2
            if (r18 != 0) goto L_0x00c6
            r3 = r12
            org.glassfish.tyrus.core.AnnotatedEndpoint$ParameterExtractor[] r4 = r7.getParameterExtractors(r12, r15, r10)
            r2.checkOnOpenParams(r12, r15)
            r24 = r0
            r25 = r1
            r18 = r3
            r21 = r4
            r26 = r5
            r30 = r13
            r1 = 0
            r29 = 0
            r13 = r2
            goto L_0x0361
        L_0x00c6:
            jakarta.websocket.DeploymentException r3 = new jakarta.websocket.DeploymentException
            java.lang.Class<jakarta.websocket.h> r4 = jakarta.websocket.h.class
            java.lang.String r4 = r4.getSimpleName()
            r24 = r0
            java.lang.String r0 = r38.getName()
            r25 = r1
            java.lang.String r1 = r18.getName()
            r26 = r5
            java.lang.String r5 = r12.getName()
            java.lang.String r0 = org.glassfish.tyrus.core.l10n.LocalizationMessages.ENDPOINT_MULTIPLE_METHODS(r4, r0, r1, r5)
            r3.<init>(r0)
            r10.addException(r3)
            r30 = r13
            r1 = 0
            r29 = 0
            r13 = r2
            goto L_0x0361
        L_0x00f2:
            r24 = r0
            r25 = r1
            r26 = r5
            boolean r0 = r6 instanceof jakarta.websocket.e
            r1 = 1
            if (r0 == 0) goto L_0x0169
            if (r19 != 0) goto L_0x0143
            r0 = r12
            org.glassfish.tyrus.core.AnnotatedEndpoint$ParameterExtractor[] r3 = r7.getOnCloseParameterExtractors(r12, r15, r10)
            r2.checkOnCloseParams(r12, r15)
            int r4 = r15.size()
            if (r4 != r1) goto L_0x0137
            java.util.Collection r1 = r15.values()
            java.util.Iterator r1 = r1.iterator()
            java.lang.Object r1 = r1.next()
            java.lang.Class<jakarta.websocket.CloseReason> r4 = jakarta.websocket.CloseReason.class
            if (r1 == r4) goto L_0x0137
            java.util.Set r1 = r15.keySet()
            java.util.Iterator r1 = r1.iterator()
            java.lang.Object r1 = r1.next()
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            org.glassfish.tyrus.core.AnnotatedEndpoint$ParamValue r4 = new org.glassfish.tyrus.core.AnnotatedEndpoint$ParamValue
            r5 = 0
            r4.<init>(r5)
            r3[r1] = r4
        L_0x0137:
            r19 = r0
            r22 = r3
            r30 = r13
            r1 = 0
            r29 = 0
            r13 = r2
            goto L_0x0361
        L_0x0143:
            jakarta.websocket.DeploymentException r0 = new jakarta.websocket.DeploymentException
            java.lang.Class<jakarta.websocket.e> r1 = jakarta.websocket.e.class
            java.lang.String r1 = r1.getSimpleName()
            java.lang.String r3 = r38.getName()
            java.lang.String r4 = r19.getName()
            java.lang.String r5 = r12.getName()
            java.lang.String r1 = org.glassfish.tyrus.core.l10n.LocalizationMessages.ENDPOINT_MULTIPLE_METHODS(r1, r3, r4, r5)
            r0.<init>(r1)
            r10.addException(r0)
            r30 = r13
            r1 = 0
            r29 = 0
            r13 = r2
            goto L_0x0361
        L_0x0169:
            boolean r0 = r6 instanceof jakarta.websocket.f
            if (r0 == 0) goto L_0x01ff
            if (r20 != 0) goto L_0x01d9
            r0 = r12
            org.glassfish.tyrus.core.AnnotatedEndpoint$ParameterExtractor[] r3 = r7.getParameterExtractors(r12, r15, r10)
            r2.checkOnErrorParams(r12, r15)
            int r4 = r15.size()
            if (r4 != r1) goto L_0x01a8
            java.lang.Class<java.lang.Throwable> r1 = java.lang.Throwable.class
            java.util.Collection r4 = r15.values()
            java.util.Iterator r4 = r4.iterator()
            java.lang.Object r4 = r4.next()
            if (r1 != r4) goto L_0x01a8
            java.util.Set r1 = r15.keySet()
            java.util.Iterator r1 = r1.iterator()
            java.lang.Object r1 = r1.next()
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            org.glassfish.tyrus.core.AnnotatedEndpoint$ParamValue r4 = new org.glassfish.tyrus.core.AnnotatedEndpoint$ParamValue
            r5 = 0
            r4.<init>(r5)
            r3[r1] = r4
            goto L_0x01cd
        L_0x01a8:
            boolean r1 = r15.isEmpty()
            if (r1 != 0) goto L_0x01cd
            java.util.logging.Logger r1 = LOGGER
            java.lang.String r4 = r38.getName()
            java.lang.String r5 = r12.getName()
            java.lang.String r4 = org.glassfish.tyrus.core.l10n.LocalizationMessages.ENDPOINT_UNKNOWN_PARAMS(r4, r5, r15)
            r1.warning(r4)
            r0 = 0
            r1 = 0
            r20 = r0
            r23 = r1
            r30 = r13
            r1 = 0
            r29 = 0
            r13 = r2
            goto L_0x0361
        L_0x01cd:
            r20 = r0
            r23 = r3
            r30 = r13
            r1 = 0
            r29 = 0
            r13 = r2
            goto L_0x0361
        L_0x01d9:
            jakarta.websocket.DeploymentException r0 = new jakarta.websocket.DeploymentException
            java.lang.Class<jakarta.websocket.f> r1 = jakarta.websocket.f.class
            java.lang.String r1 = r1.getSimpleName()
            java.lang.String r3 = r38.getName()
            java.lang.String r4 = r20.getName()
            java.lang.String r5 = r12.getName()
            java.lang.String r1 = org.glassfish.tyrus.core.l10n.LocalizationMessages.ENDPOINT_MULTIPLE_METHODS(r1, r3, r4, r5)
            r0.<init>(r1)
            r10.addException(r0)
            r30 = r13
            r1 = 0
            r29 = 0
            r13 = r2
            goto L_0x0361
        L_0x01ff:
            boolean r0 = r6 instanceof jakarta.websocket.g
            if (r0 == 0) goto L_0x0359
            r0 = r6
            jakarta.websocket.g r0 = (jakarta.websocket.g) r0
            long r27 = r0.maxMessageSize()
            r5 = r42
            long r3 = (long) r5
            int r0 = (r27 > r3 ? 1 : (r27 == r3 ? 0 : -1))
            if (r0 <= 0) goto L_0x022d
            java.util.logging.Logger r0 = LOGGER
            java.lang.Long r3 = java.lang.Long.valueOf(r27)
            java.lang.String r4 = r12.getName()
            java.lang.String r1 = r38.getName()
            r30 = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r42)
            java.lang.String r1 = org.glassfish.tyrus.core.l10n.LocalizationMessages.ENDPOINT_MAX_MESSAGE_SIZE_TOO_LONG(r3, r4, r1, r2)
            r0.config(r1)
            goto L_0x022f
        L_0x022d:
            r30 = r2
        L_0x022f:
            org.glassfish.tyrus.core.AnnotatedEndpoint$ParameterExtractor[] r31 = r7.getParameterExtractors(r12, r15, r10)
            int r0 = r15.size()
            r1 = 1
            if (r0 != r1) goto L_0x028d
            java.util.Set r0 = r15.entrySet()
            java.util.Iterator r0 = r0.iterator()
            java.lang.Object r0 = r0.next()
            r29 = r0
            java.util.Map$Entry r29 = (java.util.Map.Entry) r29
            java.lang.Object r0 = r29.getKey()
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            org.glassfish.tyrus.core.AnnotatedEndpoint$ParamValue r1 = new org.glassfish.tyrus.core.AnnotatedEndpoint$ParamValue
            r2 = 0
            r1.<init>(r2)
            r31[r0] = r1
            org.glassfish.tyrus.core.AnnotatedEndpoint$WholeHandler r32 = new org.glassfish.tyrus.core.AnnotatedEndpoint$WholeHandler
            java.lang.reflect.Method r2 = r9.getInvocableMethod(r12)
            java.lang.Object r0 = r29.getValue()
            r4 = r0
            java.lang.Class r4 = (java.lang.Class) r4
            r0 = r32
            r1 = r37
            r3 = r30
            r30 = r13
            r13 = r3
            r3 = r31
            r33 = r6
            r5 = r27
            r0.<init>(r2, r3, r4, r5)
            java.util.Set<org.glassfish.tyrus.core.AnnotatedEndpoint$MessageHandlerFactory> r1 = r7.messageHandlerFactories
            r1.add(r0)
            r1 = 0
            jakarta.websocket.MessageHandler r2 = r0.create(r1)
            r13.checkOnMessageParams(r12, r2)
            r1 = 0
            r29 = 0
            goto L_0x0361
        L_0x028d:
            r33 = r6
            r36 = r30
            r30 = r13
            r13 = r36
            int r0 = r15.size()
            r1 = 2
            if (r0 != r1) goto L_0x0341
            java.util.Set r0 = r15.entrySet()
            java.util.Iterator r32 = r0.iterator()
            java.lang.Object r0 = r32.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r1 = r0.getValue()
            java.lang.Class r2 = java.lang.Boolean.TYPE
            if (r1 == r2) goto L_0x02c4
            java.lang.Object r1 = r0.getValue()
            if (r1 != r11) goto L_0x02b9
            goto L_0x02c4
        L_0x02b9:
            java.lang.Object r1 = r32.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            r34 = r0
            r35 = r1
            goto L_0x02d0
        L_0x02c4:
            r1 = r0
            java.lang.Object r3 = r32.next()
            r0 = r3
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            r34 = r0
            r35 = r1
        L_0x02d0:
            java.lang.Object r0 = r34.getKey()
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            org.glassfish.tyrus.core.AnnotatedEndpoint$ParamValue r1 = new org.glassfish.tyrus.core.AnnotatedEndpoint$ParamValue
            r5 = 0
            r1.<init>(r5)
            r31[r0] = r1
            java.lang.Object r0 = r35.getKey()
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            org.glassfish.tyrus.core.AnnotatedEndpoint$ParamValue r1 = new org.glassfish.tyrus.core.AnnotatedEndpoint$ParamValue
            r3 = 1
            r1.<init>(r3)
            r31[r0] = r1
            java.lang.Object r0 = r35.getValue()
            if (r0 == r2) goto L_0x0319
            java.lang.Object r0 = r35.getValue()
            if (r0 != r11) goto L_0x0301
            goto L_0x0319
        L_0x0301:
            jakarta.websocket.DeploymentException r0 = new jakarta.websocket.DeploymentException
            java.lang.String r1 = r38.getName()
            java.lang.String r2 = r12.getName()
            java.lang.String r1 = org.glassfish.tyrus.core.l10n.LocalizationMessages.ENDPOINT_WRONG_PARAMS(r1, r2)
            r0.<init>(r1)
            r10.addException(r0)
            r29 = r5
            r1 = 0
            goto L_0x0340
        L_0x0319:
            org.glassfish.tyrus.core.AnnotatedEndpoint$PartialHandler r16 = new org.glassfish.tyrus.core.AnnotatedEndpoint$PartialHandler
            java.lang.reflect.Method r2 = r9.getInvocableMethod(r12)
            java.lang.Object r0 = r34.getValue()
            r4 = r0
            java.lang.Class r4 = (java.lang.Class) r4
            r0 = r16
            r1 = r37
            r3 = r31
            r29 = r5
            r5 = r27
            r0.<init>(r2, r3, r4, r5)
            java.util.Set<org.glassfish.tyrus.core.AnnotatedEndpoint$MessageHandlerFactory> r1 = r7.messageHandlerFactories
            r1.add(r0)
            r1 = 0
            jakarta.websocket.MessageHandler r2 = r0.create(r1)
            r13.checkOnMessageParams(r12, r2)
        L_0x0340:
            goto L_0x0361
        L_0x0341:
            r1 = 0
            r29 = 0
            jakarta.websocket.DeploymentException r0 = new jakarta.websocket.DeploymentException
            java.lang.String r2 = r38.getName()
            java.lang.String r3 = r12.getName()
            java.lang.String r2 = org.glassfish.tyrus.core.l10n.LocalizationMessages.ENDPOINT_WRONG_PARAMS(r2, r3)
            r0.<init>(r2)
            r10.addException(r0)
            goto L_0x0361
        L_0x0359:
            r33 = r6
            r30 = r13
            r1 = 0
            r29 = 0
            r13 = r2
        L_0x0361:
            int r5 = r26 + 1
            r2 = r13
            r0 = r24
            r1 = r25
            r13 = r30
            goto L_0x00a2
        L_0x036c:
            r24 = r0
            r25 = r1
            r13 = r2
            r29 = 0
            r3 = r18
            r4 = r19
            r5 = r20
            r6 = r21
            r18 = r22
            r19 = r23
        L_0x037f:
            int r8 = r8 + 1
            r14 = r44
            r12 = r45
            r2 = r13
            r0 = r24
            r1 = r25
            r13 = r39
            goto L_0x007d
        L_0x038e:
            r13 = r2
            r1 = 0
            if (r3 != 0) goto L_0x0394
            r0 = r1
            goto L_0x0398
        L_0x0394:
            java.lang.reflect.Method r0 = r9.getInvocableMethod(r3)
        L_0x0398:
            r7.onOpenMethod = r0
            if (r5 != 0) goto L_0x039e
            r0 = r1
            goto L_0x03a2
        L_0x039e:
            java.lang.reflect.Method r0 = r9.getInvocableMethod(r5)
        L_0x03a2:
            r7.onErrorMethod = r0
            if (r4 != 0) goto L_0x03a8
            r12 = r1
            goto L_0x03ac
        L_0x03a8:
            java.lang.reflect.Method r12 = r9.getInvocableMethod(r4)
        L_0x03ac:
            r7.onCloseMethod = r12
            r7.onOpenParameters = r6
            r0 = r19
            r7.onErrorParameters = r0
            r1 = r18
            r7.onCloseParameters = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.core.AnnotatedEndpoint.<init>(java.lang.Class, java.lang.Object, org.glassfish.tyrus.core.ComponentProviderService, java.lang.Boolean, int, org.glassfish.tyrus.core.ErrorCollector, org.glassfish.tyrus.core.monitoring.EndpointEventListener, java.util.Set):void");
    }

    private EndpointConfig createEndpointConfig(Class<?> annotatedClass2, boolean isServerEndpoint, ErrorCollector collector, Set<Extension> extensions) {
        Class<ServerEndpointConfig.Configurator> cls = ServerEndpointConfig.Configurator.class;
        if (isServerEndpoint) {
            c wseAnnotation = (c) annotatedClass2.getAnnotation(c.class);
            if (wseAnnotation == null) {
                collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_ANNOTATION_NOT_FOUND(c.class.getSimpleName(), annotatedClass2.getName())));
                return null;
            }
            List<Class<? extends Encoder>> encoderClasses = new ArrayList<>();
            List<Class<? extends Decoder>> decoderClasses = new ArrayList<>();
            encoderClasses.addAll(Arrays.asList(wseAnnotation.encoders()));
            decoderClasses.addAll(Arrays.asList(wseAnnotation.decoders()));
            String[] subProtocols = wseAnnotation.subprotocols();
            decoderClasses.addAll(TyrusEndpointWrapper.getDefaultDecoders());
            MaxSessions wseMaxSessionsAnnotation = (MaxSessions) annotatedClass2.getAnnotation(MaxSessions.class);
            if (wseMaxSessionsAnnotation != null) {
                TyrusServerEndpointConfig.Builder builder = TyrusServerEndpointConfig.Builder.create(annotatedClass2, wseAnnotation.value()).encoders(encoderClasses).decoders(decoderClasses).extensions(new ArrayList(extensions)).subprotocols(Arrays.asList(subProtocols));
                if (!wseAnnotation.configurator().equals(cls)) {
                    builder = builder.configurator((ServerEndpointConfig.Configurator) ReflectionHelper.getInstance(wseAnnotation.configurator(), collector));
                }
                builder.maxSessions(wseMaxSessionsAnnotation.value());
                return builder.build();
            }
            ServerEndpointConfig.a builder2 = ServerEndpointConfig.a.c(annotatedClass2, wseAnnotation.value()).e(encoderClasses).d(decoderClasses).f(new ArrayList(extensions)).g(Arrays.asList(subProtocols));
            if (!wseAnnotation.configurator().equals(cls)) {
                builder2 = builder2.b((ServerEndpointConfig.Configurator) ReflectionHelper.getInstance(wseAnnotation.configurator(), collector));
            }
            return builder2.a();
        }
        a wscAnnotation = (a) annotatedClass2.getAnnotation(a.class);
        if (wscAnnotation == null) {
            collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_ANNOTATION_NOT_FOUND(a.class.getSimpleName(), annotatedClass2.getName())));
            return null;
        }
        List<Class<? extends Encoder>> encoderClasses2 = new ArrayList<>();
        List<Class<? extends Decoder>> decoderClasses2 = new ArrayList<>();
        encoderClasses2.addAll(Arrays.asList(wscAnnotation.encoders()));
        decoderClasses2.addAll(Arrays.asList(wscAnnotation.decoders()));
        String[] subProtocols2 = wscAnnotation.subprotocols();
        decoderClasses2.addAll(TyrusEndpointWrapper.getDefaultDecoders());
        return ClientEndpointConfig.a.c().e(encoderClasses2).d(decoderClasses2).f(Arrays.asList(subProtocols2)).b((ClientEndpointConfig.b) ReflectionHelper.getInstance(wscAnnotation.configurator(), collector)).a();
    }

    static Class<?> getDecoderClassType(Class<? extends Decoder> decoder) {
        Class<Object> cls = Object.class;
        Class cls2 = null;
        if (Decoder.Text.class.isAssignableFrom(decoder)) {
            cls2 = Decoder.Text.class;
        } else if (Decoder.a.class.isAssignableFrom(decoder)) {
            cls2 = Decoder.a.class;
        } else if (Decoder.c.class.isAssignableFrom(decoder)) {
            cls2 = Decoder.c.class;
        } else if (Decoder.b.class.isAssignableFrom(decoder)) {
            cls2 = Decoder.b.class;
        }
        Class[] as = ReflectionHelper.getParameterizedClassArguments(ReflectionHelper.getClass(decoder, cls2));
        return (as == null || as[0] == null) ? cls : as[0];
    }

    static Class<?> getEncoderClassType(Class<? extends d> encoder) {
        Class<Object> cls = Object.class;
        Class cls2 = null;
        if (d.c.class.isAssignableFrom(encoder)) {
            cls2 = d.c.class;
        } else if (d.a.class.isAssignableFrom(encoder)) {
            cls2 = d.a.class;
        } else if (d.C0315d.class.isAssignableFrom(encoder)) {
            cls2 = d.C0315d.class;
        } else if (d.b.class.isAssignableFrom(encoder)) {
            cls2 = d.b.class;
        }
        Class[] as = ReflectionHelper.getParameterizedClassArguments(ReflectionHelper.getClass(encoder, cls2));
        return (as == null || as[0] == null) ? cls : as[0];
    }

    private ParameterExtractor[] getOnCloseParameterExtractors(Method method, Map<Integer, Class<?>> unknownParams, ErrorCollector collector) {
        return getParameterExtractors(method, unknownParams, new HashSet(Arrays.asList(new Class[]{CloseReason.class})), collector);
    }

    private ParameterExtractor[] getParameterExtractors(Method method, Map<Integer, Class<?>> unknownParams, ErrorCollector collector) {
        return getParameterExtractors(method, unknownParams, Collections.emptySet(), collector);
    }

    private ParameterExtractor[] getParameterExtractors(Method method, Map<Integer, Class<?>> unknownParams, Set<Class<?>> params, ErrorCollector collector) {
        ParameterExtractor[] result = new ParameterExtractor[method.getParameterTypes().length];
        boolean sessionPresent = false;
        unknownParams.clear();
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            final Class<?> type = method.getParameterTypes()[i];
            String pathParamName = getPathParamName(method.getParameterAnnotations()[i]);
            if (pathParamName != null) {
                if (!PrimitivesToWrappers.isPrimitiveWrapper(type) && !type.isPrimitive() && !type.equals(String.class)) {
                    collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_WRONG_PATH_PARAM(method.getName(), type.getName())));
                }
                result[i] = new ParameterExtractor(type, pathParamName) {
                    final Decoder.Text<?> decoder;
                    final /* synthetic */ String val$pathParamName;
                    final /* synthetic */ Class val$type;

                    {
                        this.val$type = r2;
                        this.val$pathParamName = r3;
                        this.decoder = PrimitiveDecoders.ALL_INSTANCES.get(PrimitivesToWrappers.getPrimitiveWrapper(r2));
                    }

                    public Object value(Session session, Object... values) {
                        Decoder.Text<?> text = this.decoder;
                        if (text != null) {
                            return text.decode(session.getPathParameters().get(this.val$pathParamName));
                        }
                        if (this.val$type.equals(String.class)) {
                            return session.getPathParameters().get(this.val$pathParamName);
                        }
                        return null;
                    }
                };
            } else if (type == Session.class) {
                if (sessionPresent) {
                    collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_MULTIPLE_SESSION_PARAM(method.getName())));
                } else {
                    sessionPresent = true;
                }
                result[i] = new ParameterExtractor() {
                    public Object value(Session session, Object... values) {
                        return session;
                    }
                };
            } else if (type == EndpointConfig.class) {
                result[i] = new ParameterExtractor() {
                    public Object value(Session session, Object... values) {
                        return AnnotatedEndpoint.this.getEndpointConfig();
                    }
                };
            } else if (params.contains(type)) {
                result[i] = new ParameterExtractor() {
                    public Object value(Session session, Object... values) {
                        for (Object value : values) {
                            if (value != null && type.isAssignableFrom(value.getClass())) {
                                return value;
                            }
                        }
                        return null;
                    }
                };
            } else {
                unknownParams.put(Integer.valueOf(i), type);
            }
        }
        return result;
    }

    private String getPathParamName(Annotation[] annotations) {
        for (b bVar : annotations) {
            if (bVar instanceof b) {
                return bVar.value();
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public Object callMethod(Method method, ParameterExtractor[] extractors, Session session, boolean callOnError, Object... params) {
        ErrorCollector collector = new ErrorCollector();
        Object[] paramValues = new Object[extractors.length];
        try {
            Object endpoint = this.annotatedInstance;
            if (endpoint == null) {
                endpoint = this.componentProvider.getInstance(this.annotatedClass, session, collector);
            }
            if (callOnError && endpoint == null) {
                if (!collector.isEmpty()) {
                    Throwable t = collector.composeComprehensiveException();
                    LOGGER.log(Level.FINE, t.getMessage(), t);
                }
                try {
                    session.close(CloseReasons.UNEXPECTED_CONDITION.getCloseReason());
                } catch (Exception e) {
                    LOGGER.log(Level.FINEST, e.getMessage(), e);
                }
                return null;
            } else if (collector.isEmpty()) {
                for (int i = 0; i < paramValues.length; i++) {
                    paramValues[i] = extractors[i].value(session, params);
                }
                return method.invoke(endpoint, paramValues);
            } else {
                throw collector.composeComprehensiveException();
            }
        } catch (Exception e2) {
            if (callOnError) {
                onError(session, e2 instanceof InvocationTargetException ? e2.getCause() : e2);
            } else {
                LOGGER.log(Level.INFO, LocalizationMessages.ENDPOINT_EXCEPTION_FROM_ON_ERROR(method), e2);
            }
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void onClose(CloseReason closeReason, Session session) {
        try {
            Method method = this.onCloseMethod;
            if (method != null) {
                callMethod(method, this.onCloseParameters, session, true, closeReason);
            }
        } finally {
            this.componentProvider.removeSession(session);
        }
    }

    public void onClose(Session session, CloseReason closeReason) {
        onClose(closeReason, session);
    }

    public void onError(Session session, Throwable thr) {
        Method method = this.onErrorMethod;
        if (method != null) {
            callMethod(method, this.onErrorParameters, session, false, thr);
        } else {
            LOGGER.log(Level.INFO, LocalizationMessages.ENDPOINT_UNHANDLED_EXCEPTION(this.annotatedClass.getCanonicalName()), thr);
        }
        this.endpointEventListener.onError(session.getId(), thr);
    }

    public EndpointConfig getEndpointConfig() {
        return this.configuration;
    }

    public void onOpen(Session session, EndpointConfig configuration2) {
        for (MessageHandlerFactory f : this.messageHandlerFactories) {
            session.addMessageHandler(f.create(session));
        }
        Method method = this.onOpenMethod;
        if (method != null) {
            callMethod(method, this.onOpenParameters, session, true, new Object[0]);
        }
    }

    public static class ParamValue implements ParameterExtractor {
        private final int index;

        ParamValue(int index2) {
            this.index = index2;
        }

        public Object value(Session session, Object... paramValues) {
            return paramValues[this.index];
        }
    }

    public abstract class MessageHandlerFactory {
        final ParameterExtractor[] extractors;
        final long maxMessageSize;
        final Method method;
        final Class<?> type;

        /* access modifiers changed from: package-private */
        public abstract MessageHandler create(Session session);

        MessageHandlerFactory(Method method2, ParameterExtractor[] extractors2, Class<?> type2, long maxMessageSize2) {
            Class<?> cls;
            this.method = method2;
            this.extractors = extractors2;
            if (PrimitivesToWrappers.getPrimitiveWrapper(type2) == null) {
                cls = type2;
            } else {
                cls = PrimitivesToWrappers.getPrimitiveWrapper(type2);
            }
            this.type = cls;
            this.maxMessageSize = maxMessageSize2;
        }
    }

    public class WholeHandler extends MessageHandlerFactory {
        WholeHandler(Method method, ParameterExtractor[] extractors, Class<?> type, long maxMessageSize) {
            super(method, extractors, type, maxMessageSize);
        }

        public MessageHandler create(final Session session) {
            return new BasicMessageHandler() {
                public void onMessage(Object message) {
                    WholeHandler wholeHandler = WholeHandler.this;
                    Object result = AnnotatedEndpoint.this.callMethod(wholeHandler.method, wholeHandler.extractors, session, true, message);
                    if (result != null) {
                        try {
                            session.getBasicRemote().sendObject(result);
                        } catch (Exception e) {
                            AnnotatedEndpoint.this.onError(session, e);
                        }
                    }
                }

                public Class<?> getType() {
                    return WholeHandler.this.type;
                }

                public long getMaxMessageSize() {
                    return WholeHandler.this.maxMessageSize;
                }
            };
        }
    }

    public class PartialHandler extends MessageHandlerFactory {
        PartialHandler(Method method, ParameterExtractor[] extractors, Class<?> type, long maxMessageSize) {
            super(method, extractors, type, maxMessageSize);
        }

        public MessageHandler create(final Session session) {
            return new AsyncMessageHandler() {
                public void onMessage(Object partialMessage, boolean last) {
                    PartialHandler partialHandler = PartialHandler.this;
                    Object result = AnnotatedEndpoint.this.callMethod(partialHandler.method, partialHandler.extractors, session, true, partialMessage, Boolean.valueOf(last));
                    if (result != null) {
                        try {
                            session.getBasicRemote().sendObject(result);
                        } catch (Exception e) {
                            AnnotatedEndpoint.this.onError(session, e);
                        }
                    }
                }

                public Class<?> getType() {
                    return PartialHandler.this.type;
                }

                public long getMaxMessageSize() {
                    return PartialHandler.this.maxMessageSize;
                }
            };
        }
    }
}
