package ws.spring.web.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WindShadow
 * @version 2022-06-25.
 */

public class ObjectUtils extends org.springframework.util.ObjectUtils {

    private static final TypeReference<Map<String, Object>> STRING_STRING_MAP_TRF = new TypeReference<Map<String, Object>>() {
    };

    public static String toString(Object... os) {

        return Arrays.toString(os);
    }

    public static <T> Map<String, Object> fetchObjectPropertyMap(T obj) {

        return JacksonUtils.pasre(JacksonUtils.toJson(obj), STRING_STRING_MAP_TRF)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {

                    Object value = e.getValue();
                    Collection<Object> c;
                    if (isArray(value)) {
                        c = Arrays.asList(toObjectArray(value));
                    } else if (value instanceof Collection) {
                        c = (Collection) value;
                    } else {
                        return value;
                    }
                    return c.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                }));
    }

    public static Object wrap(Object... os) {

        return os.length == 0 ? "" : os.length == 1 ? os[0] : os;
    }

    public static MultiValueMap<String, String> asParams(Object... os) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for (Object o : os) {
            Map<String, Object> objPropertyMap = fetchObjectPropertyMap(o);
            objPropertyMap.forEach((k, v) -> map.add(k, v.toString()));
        }
        return map;
    }

    public static MultiValueMap<String, String> asQueryParams(Object... params) {

        Assert.isTrue(params.length % 2 == 0, "The number of parameters must be even");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for (int i = 0; i < params.length; i += 2) {
            map.add(String.valueOf(params[i]), String.valueOf(params[i + 1]));
        }
        return map;
    }

    public static <T> String asMatrixParams(String paramName, T obj) {

        return fetchObjectPropertyMap(obj)
                .entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(";", paramName + ";", ""));
    }

    public static Map<String, Object> clearUp(Map<String, Object> map) {

        return map.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {

                    Object value = e.getValue();
                    if (ObjectUtils.isArray(value)) {

                        Object[] o = (Object[]) value;
                        if (o.length == 1) {
                            return o[0];
                        }
                    } else if (value instanceof Collection) {

                        if (((Collection) value).size() == 1) {
                            return ((Collection) value).iterator().next();
                        }
                    } else if (value instanceof Map) {
                        value = clearUp((Map) value);
                    }
                    return value;
                }));
    }
}
