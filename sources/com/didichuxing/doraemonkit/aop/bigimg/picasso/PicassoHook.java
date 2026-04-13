package com.didichuxing.doraemonkit.aop.bigimg.picasso;

import com.blankj.utilcode.util.ReflectUtils;
import com.squareup.picasso.Request;
import com.squareup.picasso.Transformation;
import java.util.ArrayList;
import java.util.List;

public class PicassoHook {
    public static void proxy(Object request) {
        try {
            if (request instanceof Request) {
                Request requestObj = (Request) request;
                List<Transformation> transformations = requestObj.transformations;
                if (transformations == null) {
                    transformations = new ArrayList<>();
                    transformations.add(new DokitPicassoTransformation(requestObj.uri, requestObj.resourceId));
                } else {
                    transformations.clear();
                    transformations.add(new DokitPicassoTransformation(requestObj.uri, requestObj.resourceId));
                }
                ReflectUtils.g(request).c("transformations", transformations);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
