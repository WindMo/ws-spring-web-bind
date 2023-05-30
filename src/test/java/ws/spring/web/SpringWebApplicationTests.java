package ws.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class SpringWebApplicationTests {

    @Autowired
    protected MockMvc mvc;

    protected final String request(RequestBuilder builder) {

        return request(builder, status().isOk());
    }

    protected final String request(RequestBuilder builder, ResultMatcher matcher) {

        try {

            if (builder instanceof MockHttpServletRequestBuilder) {
                ((MockHttpServletRequestBuilder) builder).characterEncoding(StandardCharsets.UTF_8.name());
            }
            return mvc.perform(builder)
                    .andExpect(matcher)
                    .andReturn()
                    .getResponse()
                    .getContentAsString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
