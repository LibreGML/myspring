package com.jbj.jbjapi.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {
    public static String replacePropertyValues(Object object) {
        if (object == null) {
            return null;
        }
        Gson gson = new GsonBuilder().create();
        JsonElement jsonElement = gson.toJsonTree(object);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Map<String, String> fieldValueMap = new HashMap<>();

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
            if (apiModelProperty != null) {
                fieldValueMap.put(field.getName(), apiModelProperty.value());
            }
        }
        String json = jsonObject.toString();
        for (Map.Entry<String, String> entry : fieldValueMap.entrySet()) {
            json = json.replaceAll(entry.getKey(), entry.getValue());
        }

        return json;
    }
}