package ws.spring.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JacksonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {

        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> String toJson(T obj) {

        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T pasre(String json, Class<T> cla) {

        try {
            return OBJECT_MAPPER.readValue(json, cla);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T pasre(String json, TypeReference<T> trf) {

        try {
            return OBJECT_MAPPER.readValue(json, trf);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
