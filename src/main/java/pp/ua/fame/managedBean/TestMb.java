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
        taskDao = (TaskDao)appContext.getBean("taskDaoImp");
    }

    public Task getTask() throws IOException {
        if (task == null){
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            int id = Integer.valueOf(externalContext.getRequestParameterMap().get("id"));
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
        resultColor = "red";
        tryBlock:try {
            for (Test test: task.getTests()) {
//                Object answer = new Js(source + test.getTest()).eval().getNumber();
                Js js = (Js) appContext.getBean("js");
                js.setSource(source + test.getTest());  //TODO ask to Dima how set default constructor
                Double answer = js.eval().getNumber();
                if (!test.getAnswer().equals(answer.toString())){
                    result = "Fail on " + test.getTest();       //TODO on fail throw exception
                    resultColor = "red";
                    break tryBlock;
                }
            }
            resultColor = "green";
        } catch (ScriptException e) {
            result = "Error: "+ e.getMessage();
        } catch (TimeoutException e) {
            result = "Error: Timeout";
        } catch (TypeMismatchException e) {
            result = "Wrong returned type";
        }
    }
}
