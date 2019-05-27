package com.learn.oauth.common;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/9/16 0016.
 */
public class JsonUtils {

    private static Gson gson = new Gson();

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

}
