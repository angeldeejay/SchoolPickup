package com.uniajc.schoolpickup.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    public static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static <T> T toInstance(String json, Class<T> k) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, k);
    }

    public static <T> T toInstances(String json, TypeReference<T> k) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, k);
    }
}