package pp.ua.fame.managedBean;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;
import pp.ua.fame.exception.TimeoutException;
import pp.ua.fame.exception.TypeMismatchException;
import pp.ua.fame.jsRuner.Js;
import pp.ua.fame.jsRuner.Result;
import pp.ua.fame.model.Task;
import pp.ua.fame.model.Test;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ManagedBean
@javax.faces.bean.ViewScoped
public class TestMb {

    private String source;
    private Object result;

    private String resultColor;
    private String console ="";
    private Task task;

    public Task getTask() throws IOException {
        if (task == null){
            HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            task = (Task) req.getAttribute("task");
        }
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getSource() throws IOException {
        if (source == null){
            source = getTask().getSkeleton();
        }
        return source;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
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
                ApplicationContext appContext = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
                Js js = (Js) appContext.getBean("js", source + test.getTest());
                Result result = js.eval();      //TODO change variables names;
                console += result.getConsole();
                Double answer = result.getNumber();
                System.out.println(console);
                if (!test.getAnswer().equals(answer.toString())){
                    this.result = "Fail on " + test.getTest();       //TODO on fail throw exception
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
