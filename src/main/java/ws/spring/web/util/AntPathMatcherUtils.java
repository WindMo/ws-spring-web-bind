package ws.spring.web.util;

import org.springframework.util.AntPathMatcher;

/**
 * @author WindShadow
 * @version 2023-05-22.
 */

public class AntPathMatcherUtils {

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    public static boolean match(String pattern, String path) {

        return MATCHER.match(pattern, path);
    }
}
