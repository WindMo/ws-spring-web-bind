package ws.spring.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author WindShadow
 * @verion 2020/9/20.
 */

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private String name;
    private Integer age;
    private String desc;
    private String email;
}
