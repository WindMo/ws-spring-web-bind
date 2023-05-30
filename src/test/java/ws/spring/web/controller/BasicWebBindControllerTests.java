package ws.spring.web.controller;

import org.junit.jupiter.api.Test;
import ws.spring.web.SpringWebApplicationTests;
import ws.spring.web.pojo.Employee;
import ws.spring.web.util.AssertionUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static ws.spring.web.util.ObjectUtils.asParams;

/**
 * @author WindShadow
 * @version 2023-05-23.
 * @see BasicWebBindController
 */

public class BasicWebBindControllerTests extends SpringWebApplicationTests {

    @Test
    void basicDefaultBindTest() {

        Integer id = -1;
        String desc = "fake-desc";
        Employee employee = new Employee(null, "fake-name", 0, "fake-address");
        String content = request(get("/bind/basic/default-bind/{id}/{desc}", id, desc)
                .params(asParams(employee)));

        AssertionUtils.assertJsonNotEquals(content, employee, desc);

        employee.setId(id);
        AssertionUtils.assertJsonEquals(content, employee, null);
    }
}
