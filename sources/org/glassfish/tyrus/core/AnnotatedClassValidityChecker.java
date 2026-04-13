package org.glassfish.tyrus.core;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Decoder;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.d;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class AnnotatedClassValidityChecker {
    private final Class<?> annotatedClass;
    private final ErrorCollector collector;
    private final List<Class<? extends d>> encoders;
    private final MessageHandlerManager handlerManager;

    public AnnotatedClassValidityChecker(Class<?> annotatedClass2, List<Class<? extends d>> encoders2, List<Class<? extends Decoder>> decoders, ErrorCollector collector2) {
        this.annotatedClass = annotatedClass2;
        this.encoders = encoders2;
        this.collector = collector2;
        this.handlerManager = new MessageHandlerManager(decoders);
    }

    public void checkOnMessageParams(Method method, MessageHandler handler) {
        try {
            this.handlerManager.addMessageHandler(handler);
        } catch (IllegalStateException ise) {
            this.collector.addException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_ADD_MESSAGE_HANDLER_ERROR(this.annotatedClass.getCanonicalName(), ise.getMessage()), ise.getCause()));
        }
        checkOnMessageReturnType(method);
    }

    private void checkOnMessageReturnType(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType != Void.TYPE && returnType != String.class && returnType != ByteBuffer.class && returnType != byte[].class && !returnType.isPrimitive() && checkEncoders(returnType) && !PrimitivesToWrappers.isPrimitiveWrapper(returnType)) {
            logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_FORBIDDEN_RETURN_TYPE(this.annotatedClass.getName(), method.getName())));
        }
    }

    public void checkOnOpenParams(Method method, Map<Integer, Class<?>> params) {
        for (Class<?> value : params.values()) {
            if (value != EndpointConfig.class) {
                logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_FORBIDDEN_WEB_SOCKET_OPEN_PARAM(this.annotatedClass.getName(), method.getName(), value)));
            }
        }
    }

    public void checkOnCloseParams(Method method, Map<Integer, Class<?>> params) {
        for (Class<?> value : params.values()) {
            if (value != CloseReason.class) {
                logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_FORBIDDEN_WEB_SOCKET_CLOSE_PARAM(this.annotatedClass.getName(), method.getName())));
            }
        }
    }

    public void checkOnErrorParams(Method method, Map<Integer, Class<?>> params) {
        boolean throwablePresent = false;
        for (Class<?> value : params.values()) {
            if (value != Throwable.class) {
                logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_FORBIDDEN_WEB_SOCKET_ERROR_PARAM(this.annotatedClass.getName(), method.getName(), value)));
            } else {
                if (throwablePresent) {
                    logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_MULTIPLE_IDENTICAL_PARAMS(this.annotatedClass.getName(), method.getName())));
                }
                throwablePresent = true;
            }
        }
        if (!throwablePresent) {
            logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_MANDATORY_PARAM_MISSING(this.annotatedClass.getName(), method.getName())));
        }
    }

    private String getPrefix(String methodName) {
        return String.format("Method:  %s.%s:", new Object[]{this.annotatedClass.getName(), methodName});
    }

    private boolean checkEncoders(Class<?> requiredType) {
        for (Class<? extends d> encoderClass : this.encoders) {
            if (AnnotatedEndpoint.getEncoderClassType(encoderClass).isAssignableFrom(requiredType)) {
                return false;
            }
        }
        return true;
    }

    private void logDeploymentException(DeploymentException de) {
        this.collector.addException(de);
    }
}
