package com.kabunx.core.web.resource;

import com.kabunx.core.util.JsonUtils;

public class ObjectResource {
    public static <T> T toView(Object obj, Class<T> clazz) {
        return JsonUtils.toBean(obj, clazz);
    }
}
