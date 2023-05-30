package ws.spring.web.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ws.spring.web.SpringWebApplicationTests;
import ws.spring.web.pojo.Company;
import ws.spring.web.pojo.User;
import ws.spring.web.util.AssertionUtils;
import ws.spring.web.util.ObjectUtils;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static ws.spring.web.util.ObjectUtils.asMatrixParams;

/**
 * @author WindShadow
 * @version 2023-05-28.
 * @see MatrixController
 */

public class MatrixControllerTests extends SpringWebApplicationTests {

    @Disabled
    @Test
    void bind2PojoTest() {

        User user = new User("tom", 18, "tom-cat", "123@qq.com");
        Company company = new Company(-1L, "al", "hz", Arrays.asList("dev", "test"));
        String userStr = asMatrixParams("user-tom", user);
        String comStr = asMatrixParams("com-alibaba", company);
        String content = request(get("/bind/matrix/users/{user}/coms/{com}", userStr, comStr));
        AssertionUtils.assertJsonEquals(content, user, company);
    }

    @Test
    void bindSingle2ParamTest() {

        User user = new User("tom", 18, "tom-cat", "123@qq.com");
        String userStr = asMatrixParams("user-tom", user);
        String content = request(get("/bind/matrix/users/{user}", userStr));

        AssertionUtils.assertJsonEquals(content, ObjectUtils.fetchObjectPropertyMap(user).values().toArray());
    }

    @Test
    void bind2MapTest() {

        User user = new User("tom", 18, "tom-cat", "123@qq.com");
        Company company = new Company(-1L, "al", "hz", Arrays.asList("dev", "test"));
        String userStr = asMatrixParams("user-tom", user);
        String comStr = asMatrixParams("com-alibaba", company);
        String content = request(post("/bind/matrix/users/{user}/coms/{com}", userStr, comStr));
    }
}
