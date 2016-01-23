package pp.ua.fame.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SpringController {
    @RequestMapping(value = "/main/{id}", method = RequestMethod.GET)
    public String Task(ModelMap model, @PathVariable("id") Integer id){
        return "/main.xhtml?id="+id;
    }
}
