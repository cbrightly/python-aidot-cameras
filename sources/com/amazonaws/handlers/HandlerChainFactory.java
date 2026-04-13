package com.amazonaws.handlers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.util.ClassLoaderHelper;
import com.amazonaws.util.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HandlerChainFactory {
    public List<RequestHandler2> newRequestHandlerChain(String resource) {
        return createRequestHandlerChain(resource, RequestHandler.class);
    }

    public List<RequestHandler2> newRequestHandler2Chain(String resource) {
        return createRequestHandlerChain(resource, RequestHandler2.class);
    }

    private List<RequestHandler2> createRequestHandlerChain(String resource, Class<?> handlerApiClass) {
        List<RequestHandler2> handlers = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStream input = getClass().getResourceAsStream(resource);
            if (input == null) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
                return handlers;
            }
            reader = new BufferedReader(new InputStreamReader(input, StringUtils.UTF8));
            while (true) {
                String requestHandlerClassName = reader.readLine();
                if (requestHandlerClassName == null) {
                    try {
                        reader.close();
                    } catch (IOException e2) {
                    }
                    return handlers;
                }
                String requestHandlerClassName2 = requestHandlerClassName.trim();
                if (!"".equals(requestHandlerClassName2)) {
                    Object newInstance = ClassLoaderHelper.loadClass(requestHandlerClassName2, handlerApiClass, getClass()).newInstance();
                    if (!handlerApiClass.isInstance(newInstance)) {
                        throw new AmazonClientException("Unable to instantiate request handler chain for client.  Listed request handler ('" + requestHandlerClassName2 + "') does not implement the " + handlerApiClass + " API.");
                    } else if (handlerApiClass == RequestHandler2.class) {
                        handlers.add((RequestHandler2) newInstance);
                    } else if (handlerApiClass == RequestHandler.class) {
                        handlers.add(RequestHandler2.adapt((RequestHandler) newInstance));
                    } else {
                        throw new IllegalStateException();
                    }
                }
            }
        } catch (Exception e3) {
            throw new AmazonClientException("Unable to instantiate request handler chain for client: " + e3.getMessage(), e3);
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e4) {
                }
            }
            throw th;
        }
    }
}
