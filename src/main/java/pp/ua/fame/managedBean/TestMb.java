package pp.ua.fame.managedBean;

import pp.ua.fame.exception.TimeoutException;
import pp.ua.fame.exception.TypeMismatchException;
import pp.ua.fame.jsRuner.Js;
import pp.ua.fame.model.Task;
import pp.ua.fame.model.Test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ManagedBean(name="test")
@javax.faces.bean.ViewScoped
public class TestMb {

    private String source;
    private Object result;

    private String resultColor;

    private Task task;

    @ManagedProperty(value="#{js}")
    private Js js;      //TODO js is a singleton!!!!

    public void setJs(Js js) {
        this.js = js;
    }


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
