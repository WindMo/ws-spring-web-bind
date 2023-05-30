package ws.spring.web.controller;

import ws.spring.web.util.AntPathMatcherUtils;
import ws.spring.web.util.HttpServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WindShadow
 * @version 2023-05-22.
 */

public interface BindAssistant {

    default void matchRun(HttpServletRequest request, String expectServletPath, Runnable r) {

        if (AntPathMatcherUtils.match(expectServletPath, HttpServletRequestUtils.getPath(request))) {
            r.run();
        }
    }
}
