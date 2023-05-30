package ws.spring.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author WindShadow
 * @version 2023-05-22.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    private Integer id;
    private String name;
    private Integer age;
    @NotBlank
    private String address;
}
