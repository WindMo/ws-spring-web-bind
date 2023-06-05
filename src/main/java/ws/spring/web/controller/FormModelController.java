package ws.spring.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ws.spring.web.annotation.FormModel;
import ws.spring.web.pojo.City;
import ws.spring.web.pojo.User;
import ws.spring.web.util.ObjectUtils;

/**
 * @author WindShadow
 * @version 2023-06-01.
 */

@RestController
@RequestMapping("/bind/extend/form-model")
public class FormModelController {

    @GetMapping("")
    public Object formModelBind(@FormModel User user, @FormModel City city) {

        return ObjectUtils.wrap(user, city);
    }
}
