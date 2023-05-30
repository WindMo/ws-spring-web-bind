package ws.spring.web.controller;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.util.UrlPathHelper;
import ws.spring.web.config.WebMvcConfig;
import ws.spring.web.pojo.Company;
import ws.spring.web.pojo.User;
import ws.spring.web.util.ObjectUtils;

import java.util.Map;

/**
 * SpringWeb参数绑定之{@linkplain  MatrixVariable 矩阵变量}使用示例
 * <p> 矩阵变量常用在url重写时提取参数
 * <p><b>url重写：</b>客户端禁用cookie时，可通过url重写将cookie键值对写到uri中，以分号隔开
 * <p> MatrixVariable 绑定功能需要 UrlPathHelper 的支持
 *
 * @author WindShadow
 * @version 2020/9/25.
 * @see WebMvcConfig#configurePathMatch(PathMatchConfigurer)
 * @see UrlPathHelper
 */

@RestController
@RequestMapping("/bind/matrix")
public class MatrixController {

    /**
     * example:
     * <pre>
     *     GET /bind/matrix/users/user-tom;name=tom;email=123@qq.com;age=18;desc=tom-cat/coms/com-alibaba;id=1001;name=alibaba;address=hz;depts=dev,test
     * </pre>
     *
     * @param user
     * @param company
     * @return
     * @deprecated 不支持绑定到java bean
     */
    @Deprecated
    @GetMapping("/users/{user}/coms/{com}")
    public Object bind2Pojo(
            @MatrixVariable(pathVar = "user") User user,
            @MatrixVariable(pathVar = "com") Company company) {

        return ObjectUtils.wrap(user, company);
    }

    /**
     * example:
     * <pre>
     *     GET /bind/matrix/users/user-tom;name=tom;email=123@qq.com;age=18;desc=tom-cat
     * </pre>
     *
     * @param name
     * @param age
     * @param desc
     * @param email
     * @return
     */
    @GetMapping("/users/{user}")
    public Object bind2SingleParam(
            @MatrixVariable(pathVar = "user", name = "name") String name,
            @MatrixVariable(pathVar = "user", name = "age") Integer age,
            @MatrixVariable(pathVar = "user", name = "desc") String desc,
            @MatrixVariable(pathVar = "user", name = "email") String email) {

        return ObjectUtils.wrap(name, age, desc, email);
    }

    /**
     * example:
     * <pre>
     *     GET /bind/matrix/users/user-tom;name=tom;email=123@qq.com;age=18;desc=tom-cat/coms/com-alibaba;id=1001;name=alibaba;address=hz;depts=dev,test
     * </pre>
     *
     * @return
     */
    @PostMapping("/users/{user}/coms/{com}")
    public Object bind2Map(
            @PathVariable("user") String userStr,
            @PathVariable("com") String comStr,
            @MatrixVariable(pathVar = "user") Map<String, String> userAttrs,
            @MatrixVariable(pathVar = "com") MultiValueMap<String, String> comAttrs) {

        return ObjectUtils.wrap(userStr, comStr, userAttrs, comAttrs);
    }


}
