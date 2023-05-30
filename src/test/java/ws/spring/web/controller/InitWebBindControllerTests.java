package ws.spring.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;
import ws.spring.web.SpringWebApplicationTests;
import ws.spring.web.pojo.City;
import ws.spring.web.pojo.User;
import ws.spring.web.util.AssertionUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static ws.spring.web.util.ObjectUtils.*;

/**
 * @author WindShadow
 * @version 2023-05-25.
 * @see InitWebBindController
 */

public class InitWebBindControllerTests extends SpringWebApplicationTests {

    @Test
    void bindByParamPrefixTest() {

        User user = new User("tom", 18, "tom-cat", "123@qq.com");
        City city = new City("北京", "中国首都");
        MultiValueMap<String, String> params = asQueryParams(
                "user.name", user.getName(),
                "user.age", user.getAge(),
                "user.desc", user.getDesc(),
                "user.email", user.getEmail(),
                "city.name", city.getName(),
                "city.desc", city.getDesc());
        String content = request(get("/bind/init-binder/param-prefix").queryParams(params));

        AssertionUtils.assertJsonEquals(content, wrap(user, city));
    }

    @Test
    void allowedFieldsTest() {

        User user = new User("tom", 18, "tom-cat", "123@qq.com");
        MultiValueMap<String, String> params = asParams(user);
        String content = request(get("/bind/init-binder/allowed-fields").queryParams(params));

        AssertionUtils.assertJsonNotEquals(content, wrap(user));
        AssertionUtils.assertJsonEquals(content, wrap(new User("tom", null, null, null)));
    }

    @Test
    void setDisallowedFieldsTest() {

        User user = new User("tom", 18, "tom-cat", "123@qq.com");
        MultiValueMap<String, String> params = asParams(user);
        String content = request(get("/bind/init-binder/disallowed-fields").queryParams(params));

        AssertionUtils.assertJsonNotEquals(content, wrap(user));
        user.setName(null);
        AssertionUtils.assertJsonEquals(content, wrap(user));
    }
}
