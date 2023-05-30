package ws.spring.web.util;

import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author WindShadow
 * @version 2023-05-23.
 */

public class AssertionUtils extends Assertions {

    public static void assertJsonEquals(String actualContent, Object... expectedObjs) {

        assertJson(true, actualContent, expectedObjs);
    }

    public static void assertJsonNotEquals(String actualContent, Object... expectedObjs) {

        assertJson(false, actualContent, expectedObjs);
    }

    private static void assertJson(boolean eq, String actualContent, Object... expectedObjs) {

        Object obj = expectedObjs.length == 1 ? expectedObjs[0] : expectedObjs;

        Object expected = JacksonUtils.pasre(JacksonUtils.toJson(obj), Object.class);
        Object actual = JacksonUtils.pasre(actualContent, Object.class);
        actual = JacksonUtils.pasre(JacksonUtils.toJson(actual), Object.class);

        if (eq) {

            if (ObjectUtils.isArray(expected) && ObjectUtils.isArray(actual)) {

                assertArrayEquals((Object[]) expected, (Object[]) actual);
            } else if (expected instanceof Collection && actual instanceof Collection) {
                assertEquals(new HashSet<>((Collection) expected), new HashSet<>((Collection) actual));
            } else {
                assertEquals(expected, actual);
            }
        } else {

            if (ObjectUtils.isArray(expected) && ObjectUtils.isArray(actual)) {

                assertFalse(Arrays.deepEquals((Object[]) expected, (Object[]) actual));
            } else if (expected instanceof Collection && actual instanceof Collection) {
                assertNotEquals(new HashSet<>((Collection) expected), new HashSet<>((Collection) actual));
            } else {
                assertNotEquals(expected, actual);
            }

        }
    }
}
