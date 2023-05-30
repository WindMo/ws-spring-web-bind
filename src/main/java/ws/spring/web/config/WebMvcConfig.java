package ws.spring.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

/**
 * WebMvc配置，官方建议如此配置【实现WebMvcConfigurer 接口】
 * <p>实现WebMvcConfigurer的方法进行mvc的配置
 * <p>与SSM区别：不能添加@EnableWebMvc注解！！！否则会抛异常，spring-boot-starter-web的starter已经开启了@EnableWebMvc
 *
 * @author WindShadow
 * @version 2021-12-10.
 */

@Configuration(proxyBeanMethods = false)
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        // 矩阵变量配置,关键类 UrlPathHelper，开启对 MatrixVariable 的支持
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
