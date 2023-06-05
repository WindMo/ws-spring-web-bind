package ws.spring.web.controller;

import org.springframework.web.bind.annotation.*;
import ws.spring.web.pojo.Employee;
import ws.spring.web.util.ObjectUtils;

/**
 * SpringWeb参数绑定之绑定基本使用示例
 *
 * @author WindShadow
 * @version 2021-12-19.
 */

@RestController
@RequestMapping("/bind/basic")
public class BasicWebBindController {

    /**
     * example:
     * <pre>
     *     GET /bind/basic/request-param?name=tom
     *
     *     POST /bind/basic/request-param
     *     body: name=tom
     * </pre>
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/request-param", method = {RequestMethod.GET, RequestMethod.POST})
    public Object basicBindRequestParam(@RequestParam("name") String name) {

        return ObjectUtils.wrap(name);
    }

    /**
     * example:
     * <pre>
     *     GET /bind/basic/request-header
     *          header: X-Custom-Name: tom
     * </pre>
     *
     * @param name
     * @return
     */
    @GetMapping("/request-header")
    public Object basicBindRequestHeader(@RequestHeader("X-Custom-Name") String name) {

        return ObjectUtils.wrap(name);
    }

    /**
     * example:
     * <pre>
     *     GET /basic/path-variable/tom
     * </pre>
     *
     * @param name
     * @return
     */
    @GetMapping("/path-variable/{name}")
    public Object basicBindPathVariable(@PathVariable("name") String name) {

        return ObjectUtils.wrap(name);
    }

    /**
     * example:
     * <pre>
     *     POST /basic/request-body
     *     body: {"id": 100, "name": "tom", "age": 18, "address": "beijing"}
     * </pre>
     *
     * @param employee
     */
    @PostMapping("/request-body")
    public Object basicBindRequestBody(@RequestBody Employee employee) {

        return ObjectUtils.wrap(employee);
    }

    /**
     * example:
     * <pre>
     *     GET /basic/cookie-value
     *     cookie: name: tom
     * </pre>
     *
     * @param name
     * @return
     */
    @GetMapping("/cookie-value")
    public Object basicBindCookieValue(@CookieValue("name") String name) {

        return ObjectUtils.wrap(name);
    }

    /**
     * example:
     * <pre>
     *     GET /basic/request-attribute
     *     server.filter:
     *     request.setAttribute("name", "tom");
     * </pre>
     *
     * @param name
     * @return
     */
    @GetMapping("/request-attribute")
    public Object basicBindRequestAttribute(@RequestAttribute("name") String name) {

        return ObjectUtils.wrap(name);
    }

    /**
     * example:
     * <pre>
     *     GET /basic/request-attribute
     *     server.filter:
     *     session.setAttribute("name", "tom");
     * </pre>
     *
     * @param name
     * @return
     */
    @GetMapping("/session-attribute")
    public Object basicBindSessionAttribute(@SessionAttribute("name") String name) {

        return ObjectUtils.wrap(name);
    }

    /**
     * example:
     * <pre>
     *     GET /bind/basic/default-bind/100?name=tom&age=18&address=beijing
     * </pre>
     * 缺省绑定，自动从请求和路径中获取同名参数绑定到同名java bean属性
     *
     * @param employee
     * @param desc     不会被 URI 参数所绑定
     * @return
     */
    @GetMapping("/default-bind/{id}/{desc}")
    public Object basicDefaultBind(Employee employee, String desc) {

        return ObjectUtils.wrap(employee, desc);
    }
}