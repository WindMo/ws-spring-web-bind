package ws.spring.web.controller;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ws.spring.web.pojo.Employee;
import ws.spring.web.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * SpringWeb参数绑定之{@link ModelAttribute}使用示例
 * <p> ModelAttribute 与 RequestBody 绑定类似，可直接使用 Validated 进行校验
 * <p> Validated 修饰 controller，使其在被 RequestMappingHandlerMapping 扫描之前被代理
 *
 * @author WindShadow
 * @version 2023-05-19.
 */

@Validated
@RestController
@RequestMapping("/bind")
public class ModelAttributeController implements BindAssistant {

    @Autowired
    private ApplicationContext context;

    private static final String CURRENT_LABORER = "打工人";


    // ~ ModelAttribute 修饰方法
    // 在当前controller执行每个 handler method 逻辑前，先执行 preHandlerMethod
    // 支持参数同 RequestMapping 类似，支持校验
    // =====================================================================================

    /**
     * 方式1：无返回值，手动往 Model 添加数据；
     * 当返回值为void时入参必须有 Model
     *
     * @param model
     */
    @ModelAttribute
    public void presetNameValue(Model model) {

        model.addAttribute("name", "tom");
    }

    /**
     * 方式2：有返回值，指定attribute名称，等价于 model.addAttribute("age", 18)
     *
     * @return
     */
    @ModelAttribute("age")
    public int presetAgeValue(@RequestHeader(value = "X-User-Age", required = false) Integer age) {

        return age == null ? 18 : age;
    }


    /**
     * 方式3：有返回值，不指定attribute名称，等价于 model.addAttribute("long", 100L)
     *
     * @return
     */
    @ModelAttribute
    public long presetLongValue() {

        return 100L;
    }

    /**
     * example:
     * <pre>
     *     GET /bind/model-attribute/model-map
     * </pre>
     * 查看 Model 中的数据
     *
     * @param model
     * @return
     * @see #presetNameValue(Model)
     * @see #presetAgeValue(Integer)
     * @see #presetLongValue()
     */
    @GetMapping("/model-attribute/model-map")
    public Object modelAttributeContent(Model model) {

        // return model.asMap(); // 会被 ModelAndViewMethodReturnValueHandler 处理，从而不写入response.body
        return new HashMap<>(model.asMap());
    }

    /**
     * example:
     * <pre>
     *     GET /bind/model-attribute/params
     * </pre>
     * 获取 Model 中的数据，绑定到参数
     *
     * @param name
     * @return
     * @see #presetNameValue(Model)
     */
    @GetMapping("/model-attribute/params")
    public Object modelAttributeParams(@ModelAttribute("name") String name,
                                       @ModelAttribute("age") Integer age,
                                       @ModelAttribute("long") Long value) {

        return ObjectUtils.wrap(name, age, value);
    }

    /**
     * example:
     * <pre>
     *     GET /bind/model-attribute/auto-bind/1000?address=beijing
     * </pre>
     * <p>
     * ModelAttribute 的自动绑定，自动从请求和路径中获取同名参数绑定，等价于
     * <pre>
     *     dto.setId(getPathVariable("id"));
     *     dto.setAddress(request.getParameter("address"));
     * </pre>
     * 但是不会从模型中获取数据，以下隐式操作不会发生
     * <pre>
     *     dto.setName((String) model.getAttribute("name"));
     *     dto.setAge((Integer) model.getAttribute("age"));
     * </pre>
     * <p>参数来源：
     * <ul>
     *     <li>PathVariable</li>
     *     <li>request.getParameter(String)</li>
     * </ul>
     *
     * @param dto
     * @return
     */
    @GetMapping("/model-attribute/auto-bind/{id}")
    public Object modelAttributeAutoBind(@ModelAttribute @Validated Employee dto) {

        return ObjectUtils.wrap(dto);
    }

    /**
     * {@link ModelAttribute#binding()}开启或关闭绑定
     *
     * @param dto
     * @return
     */
    @GetMapping("/model-attribute/unable-bind/{id}")
    public Object modelAttributeUnableBind(@ModelAttribute(binding = false) Employee dto) {

        return ObjectUtils.wrap(dto);
    }

    // ~ pojo属性值覆盖的情况
    // =====================================================================================

    @ModelAttribute
    public void preModelAttributeOverride(Model model, HttpServletRequest request) {

        matchRun(request, "/bind/model-attribute/override/{id}", () -> {

            Employee e = new Employee();
            e.setId(2000);
            e.setName("tom");
            model.addAttribute(CURRENT_LABORER, e);
        });
    }

    /**
     * example:
     * <pre>
     *     GET /bind/model-attribute/override/1000
     * </pre>
     * <p>
     * 这里的{@linkplain Employee#setId(Integer) ID}在model中初始值为 2000，后在此会被PathVariable重新赋值
     *
     * @param dto
     * @return
     * @see #preModelAttributeOverride(Model, HttpServletRequest)
     */
    @GetMapping("/model-attribute/override/{id}")
    public Object modelAttributeOverride(@ModelAttribute(CURRENT_LABORER) Employee dto) {

        return ObjectUtils.wrap(dto);
    }

    // ~ spring bean空指针情况
    // =====================================================================================

    /**
     * 当 ModelAttribute 修饰的方法非 public 时，可能出现controller bean内依赖注入的对象是 null的情况，
     * 因为spring 通过反射缓存该方法后，后续执行该方法的时候，实际 invoke 的对象可能是aop代理后的bean，
     * 代理bean内的属性默认是null，只有原始bean内的属性才是被spring注入进来的
     * <p> 并不是被aop代理就一定会出现上述情况，取决于代理 controller 时机和 RequestMappingHandlerMapping 扫描时机
     * <p> 此controller 使用Validated 开启了方法参数校验能力（aop增强，在RequestMappingHandlerMapping 扫描时机之前）
     *
     * @param request
     */
    @ModelAttribute
    private void preModelAttributeNullBeanPrivate(HttpServletRequest request) {

        matchRun(request, "/bind/model-attribute/bean-null", () -> {

            if (AopUtils.isAopProxy(this)) {

                // private 如果this是aop，执行原对象方法
                Assert.isNull(context, "The application context is not null");
            }
        });
    }

    @ModelAttribute
    public void preModelAttributeNullBeanPublic(HttpServletRequest request) {

        matchRun(request, "/bind/model-attribute/bean-null", () -> {

            // public 执行原对象方法
            Assert.notNull(context, "The application context is null");
        });
    }

    @GetMapping("/model-attribute/bean-null")
    public Object modelAttributeNullBean() {

        return "OK";
    }

    // ~ 视图渲染相关
    // =====================================================================================

    @Controller
    public static class ModelAttributeViewController {

        /**
         * example:
         * <pre>
         *     GET /bind/model-attribute/view
         * </pre>
         * <p>
         * 此时RequestMapping内的value表示视图名称，
         * 此请求使用名为 /model-attribute/ 下的 view 的视图进行渲染，
         * 且 model 包含键值对 name=tom
         *
         * @return
         */
        @GetMapping("/model-attribute/view")
        @ModelAttribute("name")
        public String view() {

            return "tom";
        }
    }

}
