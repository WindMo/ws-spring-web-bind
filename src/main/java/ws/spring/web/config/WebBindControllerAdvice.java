package ws.spring.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.Formatter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ws.spring.web.controller.BasicWebBindController;
import ws.spring.web.controller.InitWebBindController;
import ws.spring.web.controller.MatrixController;
import ws.spring.web.controller.ModelAttributeController;
import ws.spring.web.util.HttpServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制层参数绑定增强写法
 *
 * @author WindShadow
 * @version 2021-12-19.
 */

@Slf4j
@RestControllerAdvice(assignableTypes = {BasicWebBindController.class, ModelAttributeController.class, InitWebBindController.class, MatrixController.class})
public class WebBindControllerAdvice extends ResponseEntityExceptionHandler {

    //---------------------------------
    // 初始化参数绑定器 WebDataBinder
    //---------------------------------

    /**
     * 初始化参数绑定器，在请求到达真正的处理器之前调用
     * <p>一般传参为{@link WebDataBinder}，可以设置的入参类型和普通MVC处理器（Controller的方法）差不多，同样支持无参，非要拿来当做请求前置处理也不是不行，但不是很优雅
     * <p>{@link InitBinder#value()}代表当参数名称为指定值时，才执行此初始化参数绑定器的方法，不指定则默认匹配全部
     * <p> 见名知意的方法：
     * <ul>
     *   <li>{@link WebDataBinder#setValidator(Validator)} 为当前参数设置Spring的校验器</li>
     *   <li>{@link WebDataBinder#addValidators(Validator...)} 为当前参数添加一个Spring的校验器</li>
     *   <li>{@link WebDataBinder#addCustomFormatter(Formatter)} 为当前参数设置一个格式化器</li>
     *   <li>{@link WebDataBinder#setConversionService(ConversionService)} 为当前参数设置一个数据转换服务</li>
     * </ul>
     *
     * @param binder 参数绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public void handlerException(Exception e) {

        log.error("Error", e);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        HttpServletRequest httpRequest = null;
        if (request instanceof HttpServletRequest) {

            httpRequest = (HttpServletRequest) request;

        } else if (request instanceof ServletWebRequest) {

            httpRequest = ((ServletWebRequest) request).getNativeRequest(HttpServletRequest.class);
        }

        if (httpRequest != null) {
            log.warn("Bad Request: {}", HttpServletRequestUtils.getPath(httpRequest), ex);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
