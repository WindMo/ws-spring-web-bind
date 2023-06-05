package ws.spring.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;
import ws.spring.web.SpringWebApplicationTests;
import ws.spring.web.pojo.City;
import ws.spring.web.pojo.User;
import ws.spring.web.util.AssertionUtils;

import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static ws.spring.web.util.ObjectUtils.*;

/**
 * @author WindShadow
 * @version 2023-06-01.
 * @see FormModelController
 */

public class FormModelControllerTests extends SpringWebApplicationTests {

    @Test
    void formModelBindTest() {

        User user = new User("tom", 18, "tom-cat", "123@qq.com");
        City city = new City("北京", "中国首都");

        MultiValueMap<String, String> userParams = converterMultiValueMap(asParams(user), k -> "user." + k, Function.identity());
        MultiValueMap<String, String> cityParams = converterMultiValueMap(asParams(city), k -> "city." + k, Function.identity());
        userParams.addAll(cityParams);
        String content = request(get("/bind/extend/form-model").params(userParams));

        AssertionUtils.assertJsonEquals(content, user, city);

    }
}
