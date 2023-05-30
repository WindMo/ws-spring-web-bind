package ws.spring.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author WindShadow
 * @version 2020/9/20.
 */

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Company {

    private Long id;
    private String name;
    @NotNull
    private String address;
    private List<String> depts;
}
