package pp.ua.fame.managedBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pp.ua.fame.dao.TaskDao;
import pp.ua.fame.exception.TimeoutException;
import pp.ua.fame.exception.TypeMismatchException;
import pp.ua.fame.jsRuner.Js;
import pp.ua.fame.model.Task;
import pp.ua.fame.model.Test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.script.ScriptException;
import java.io.IOException;

@ManagedBean(name="test")
@ViewScoped
public class TestMb {
    private static ApplicationContext appContext;

    private String source;
    private Object result;

    private String resultColor;

    private Task task;

    private static TaskDao taskDao;

    public TestMb() {
        if (appContext == null) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            String configLocation =
                    ctx.getExternalContext().getInitParameter("contextConfigLocation");
            appContext = new ClassPathXmlApplicationContext(configLocation);
        }
        taskDao = (TaskDao)appContext.getBean("taskDao");
    }

    public Task getTask() throws IOException {
        if (task == null){
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            int id = Integer.valueOf(externalContext.getRequestParameterMap().get("id"));
//        int id = 1;
            System.out.println(id);
            task = taskDao.getTask(id);
            if (task == null)
                externalContext.dispatch("404page.xhtml");
        }
        return task;
    }

    public void setTask(Task taskDAO) {
        this.task = taskDAO;
    }

    public String getSource() throws IOException {
        if (source == null){
            source = getTask().getSkeleton();
        }
        return source;
    }


    public String getResultColor() {
        return resultColor;
    }

    public void setResultColor(String resultColor) {
        this.resultColor = resultColor;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResult() {
        return "Result: " + result;
    }

    public void calculateResult(){
        result = "OK";
        resultColor = "green";
        try {
            for (Test test: task.getTests()) {
                Object answer = new Js(source + test.getTest()).eval().getNumber();
                if (!test.getAnswer().equals(answer.toString())){
                    result = "Fail on " + test.getTest();
                    resultColor = "red";
                    break;
                }
            }
        } catch (ScriptException e) {
            result = "Error: "+ e.getMessage();
            resultColor = "red";
        } catch (TimeoutException e) {
            result = "Error: Timeout";
            resultColor = "red";
        } catch (TypeMismatchException e) {
            resultColor = "red";
            result = "Wrong returned type";
        }
    }
}
