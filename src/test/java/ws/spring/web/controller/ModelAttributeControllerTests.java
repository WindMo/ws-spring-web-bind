package ws.spring.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ws.spring.web.SpringWebApplicationTests;
import ws.spring.web.pojo.Employee;
import ws.spring.web.util.AssertionUtils;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author WindShadow
 * @version 2023-05-23.
 * @see ModelAttributeController
 */

public class ModelAttributeControllerTests extends SpringWebApplicationTests {

    @Autowired
    ApplicationContext context;

    @Test
    void modelAttributeContentTest() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "tom");
        map.put("age", 18);
        map.put("long", 100L);

        String content = request(get("/bind/model-attribute/model-map"));

        AssertionUtils.assertJsonEquals(content, map);
    }

    @Test
    void modelAttributeParamsTest() {

        String content = request(get("/bind/model-attribute/params"));

        AssertionUtils.assertJsonEquals(content, "tom", 18, 100L);
    }

    @Test
    void modelAttributeAutoBindTest() {

        Employee employee = new Employee();
        employee.setId(-1);
        employee.setName("tom");
        employee.setAge(18);
        employee.setAddress("fake-address");

        String content = request(get("/bind/model-attribute/auto-bind/{id}", employee.getId())
                .param("address", employee.getAddress()));

        AssertionUtils.assertJsonNotEquals(content, employee);
        AssertionUtils.assertJsonEquals(content, new Employee(employee.getId(), null, null, employee.getAddress()));

        // 触发校验
        employee.setAddress(null);
        request(get("/bind/model-attribute/auto-bind/{id}", employee.getId())
                        .param("address", employee.getAddress()),
                status().isBadRequest());

    }

    @Test
    void modelAttributeUnableBindTest() {

        Employee employee = new Employee();
        employee.setId(-1);
        employee.setName("tom");
        employee.setAge(18);
        employee.setAddress("fake-address");

        String content = request(get("/bind/model-attribute/unable-bind/{id}", employee.getId())
                .param("address", employee.getAddress()));

        AssertionUtils.assertJsonNotEquals(content, employee);
        AssertionUtils.assertJsonEquals(content, new Employee());
    }

    @Test
    void modelAttributeOverrideTest() {

        Employee employee = new Employee();
        employee.setId(-1);
        employee.setName("tom");

        String content = request(get("/bind/model-attribute/override/{id}", employee.getId())
                .param("address", employee.getAddress()));

        AssertionUtils.assertJsonEquals(content, employee);
    }

    @Test
    void modelAttributeNullBeanTest() {

        request(get("/bind/model-attribute/bean-null"));
    }
}
