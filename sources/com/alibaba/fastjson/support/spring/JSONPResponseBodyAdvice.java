package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Order(Integer.MIN_VALUE)
public class JSONPResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    public final a logger = h.n(getClass());

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return FastJsonHttpMessageConverter.class.isAssignableFrom(converterType) && (returnType.getContainingClass().isAnnotationPresent(ResponseJSONP.class) || returnType.hasMethodAnnotation(ResponseJSONP.class));
    }

    /* JADX WARNING: type inference failed for: r1v6, types: [java.lang.annotation.Annotation] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object beforeBodyWrite(java.lang.Object r14, org.springframework.core.MethodParameter r15, org.springframework.http.MediaType r16, java.lang.Class<? extends org.springframework.http.converter.HttpMessageConverter<?>> r17, org.springframework.http.server.ServerHttpRequest r18, org.springframework.http.server.ServerHttpResponse r19) {
        /*
            r13 = this;
            r6 = r13
            java.lang.Class<com.alibaba.fastjson.support.spring.annotation.ResponseJSONP> r0 = com.alibaba.fastjson.support.spring.annotation.ResponseJSONP.class
            r7 = r15
            java.lang.annotation.Annotation r0 = r15.getMethodAnnotation(r0)
            com.alibaba.fastjson.support.spring.annotation.ResponseJSONP r0 = (com.alibaba.fastjson.support.spring.annotation.ResponseJSONP) r0
            if (r0 != 0) goto L_0x001b
            java.lang.Class r1 = r15.getContainingClass()
            java.lang.Class<com.alibaba.fastjson.support.spring.annotation.ResponseJSONP> r2 = com.alibaba.fastjson.support.spring.annotation.ResponseJSONP.class
            java.lang.annotation.Annotation r1 = r1.getAnnotation(r2)
            r0 = r1
            com.alibaba.fastjson.support.spring.annotation.ResponseJSONP r0 = (com.alibaba.fastjson.support.spring.annotation.ResponseJSONP) r0
            r8 = r0
            goto L_0x001c
        L_0x001b:
            r8 = r0
        L_0x001c:
            r0 = r18
            org.springframework.http.server.ServletServerHttpRequest r0 = (org.springframework.http.server.ServletServerHttpRequest) r0
            javax.servlet.http.HttpServletRequest r9 = r0.getServletRequest()
            java.lang.String r0 = r8.callback()
            java.lang.String r0 = r9.getParameter(r0)
            boolean r1 = com.alibaba.fastjson.util.IOUtils.isValidJsonpQueryParam(r0)
            if (r1 != 0) goto L_0x0053
            org.apache.commons.logging.a r1 = r6.logger
            boolean r1 = r1.isDebugEnabled()
            if (r1 == 0) goto L_0x0050
            org.apache.commons.logging.a r1 = r6.logger
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Invalid jsonp parameter value:"
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            r1.debug(r2)
        L_0x0050:
            r0 = 0
            r10 = r0
            goto L_0x0054
        L_0x0053:
            r10 = r0
        L_0x0054:
            com.alibaba.fastjson.JSONPObject r0 = new com.alibaba.fastjson.JSONPObject
            r0.<init>(r10)
            r11 = r0
            r12 = r14
            r11.addParameter(r14)
            r0 = r13
            r1 = r11
            r2 = r16
            r3 = r15
            r4 = r18
            r5 = r19
            r0.beforeBodyWriteInternal(r1, r2, r3, r4, r5)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.support.spring.JSONPResponseBodyAdvice.beforeBodyWrite(java.lang.Object, org.springframework.core.MethodParameter, org.springframework.http.MediaType, java.lang.Class, org.springframework.http.server.ServerHttpRequest, org.springframework.http.server.ServerHttpResponse):java.lang.Object");
    }

    public void beforeBodyWriteInternal(JSONPObject jsonpObject, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
    }

    /* access modifiers changed from: protected */
    public MediaType getContentType(MediaType contentType, ServerHttpRequest request, ServerHttpResponse response) {
        return FastJsonHttpMessageConverter.APPLICATION_JAVASCRIPT;
    }
}
