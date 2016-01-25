package pp.ua.fame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pp.ua.fame.dao.impl.TaskDaoImp;
import pp.ua.fame.model.Task;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SpringController {

    @Autowired
    private TaskDaoImp taskDao;

    @RequestMapping(value = "/main/{id}", method = RequestMethod.GET)
    public String Task(@PathVariable("id") Integer id, HttpServletRequest req){
        Task task = taskDao.getTask(id);
        if (task == null) {
            return ("404page.xhtml");
        }
        req.setAttribute("task", task);
        return "/main.xhtml";
    }
}
