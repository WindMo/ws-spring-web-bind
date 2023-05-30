package ws.spring.web.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WindShadow
 * @version 2023-05-29.
 */

public class HttpServletRequestUtils {


    public static String getPath(HttpServletRequest request) {
        String path = request.getServletPath();
        if (!StringUtils.hasText(path)) {
            path = request.getPathInfo();
        }
        return path;
    }
}
