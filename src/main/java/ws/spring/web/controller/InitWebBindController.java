package ws.spring.web.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ws.spring.web.pojo.City;
import ws.spring.web.pojo.User;
import ws.spring.web.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * pringWeb参数绑定之初始化参数绑定器使用示例
 *
 * @author WindShadow
 * @version 2023-05-19.
 */

@RestController
@RequestMapping("/bind/init-binder")
public class InitWebBindController implements BindAssistant {


    // ~ 根据参数的名称前缀绑定
    // =====================================================================================

    /**
     * 设置寻找的参数的名称前缀
     *
     * @param binder binder
     */
    @InitBinder({"user", "city"})
    public void setFieldDefaultPrefix(WebDataBinder binder, HttpServletRequest request) {

        matchRun(request, "/bind/init-binder/param-prefix", () -> {

            String objectName = binder.getObjectName();
            if ("user".equals(objectName)) {
                binder.setFieldDefaultPrefix("user.");
            } else if ("city".equals(objectName)) {
                binder.setFieldDefaultPrefix("city.");
            }
        });
    }

    /**
     * example:
     * <pre>
     *      GET /bind/init-binder/param-prefix?user.name=tom&user.desc=tom-cat&user.email=123@qq.com&city.name=北京&city.desc=中国首都
     * </pre>
     *
     * @param user user
     * @param city city
     * @return String
     * @see #setFieldDefaultPrefix(WebDataBinder, HttpServletRequest)
     */
    @GetMapping("/param-prefix")
    public Object bindByParamPrefix(User user, City city) {

        return ObjectUtils.wrap(user, city);
    }

    // ~ 指定可绑定的字段
    // =====================================================================================

    /**
     * {@link WebDataBinder#setAllowedFields(String...)}设置允许被绑定的字段，支持通配符匹配
     *
     * @param binder
     */
    @InitBinder({"user"})
    public void setAllowedFields(WebDataBinder binder, HttpServletRequest request) {

        matchRun(request, "/bind/init-binder/allowed-fields", () -> {
            Object target = binder.getTarget();
            if (target instanceof User) {

                binder.setAllowedFields("name");
            }
        });
    }

    /**
     * 设置允许被绑定的字段，
     * 只允许绑定{@linkplain User#setName(String) name字段}，忽略其它字段的绑定
     *
     * @param user
     * @return
     * @see #setAllowedFields(WebDataBinder, HttpServletRequest)
     */
    @GetMapping("/allowed-fields")
    public Object allowedFields(User user) {

        return ObjectUtils.wrap(user);
    }

    // ~ 忽略绑定字段
    // =====================================================================================

    /**
     * {@link WebDataBinder#setDisallowedFields(String...)}设置不允许被绑定的字段，支持通配符匹配
     *
     * @param binder
     */
    @InitBinder({"user"})
    public void setDisallowedFields(WebDataBinder binder, HttpServletRequest request) {

        matchRun(request, "/bind/init-binder/disallowed-fields", () -> {
            Object target = binder.getTarget();
            if (target instanceof User) {

                binder.setDisallowedFields("name");
            }
        });
    }

    /**
     * 设置忽略绑定的字段，
     * 忽略{@linkplain User#setName(String) name字段}的绑定
     *
     * @param user
     * @return
     * @see #setDisallowedFields(WebDataBinder, HttpServletRequest)
     */
    @GetMapping("/disallowed-fields")
    public Object disallowedFields(User user) {

        return ObjectUtils.wrap(user);
    }
}
