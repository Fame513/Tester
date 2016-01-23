package pp.ua.fame.jsRuner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pp.ua.fame.exception.TimeoutException;

import javax.faces.context.FacesContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;



public class Js {

    private static final int TIMEOUT = 5000;

    private Status status;

    private ScriptEngine engine;

    private String source;

    private ScriptException ex;

    private Result result;

    private static ApplicationContext appContext;

    public Js(String source) {
        if (appContext == null) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            String configLocation =
                    ctx.getExternalContext().getInitParameter("contextConfigLocation");
            appContext = new ClassPathXmlApplicationContext(configLocation);
        }
        engine = (ScriptEngine)appContext.getBean("nashorn");
//        engine.getContext().setWriter(new OutputStreamWriter(System.err));
        this.source = source;
        status = Status.CREATE;
    }

    public Result eval() throws ScriptException, TimeoutException {
        status = Status.RUN;
        Thread calculatingThread = new Thread(() ->{
            try {
                result = new Result(engine.eval(source));
                status = Status.FINISH;
            } catch (ScriptException e) {
                ex = e;
                status = Status.ERROR;
            }
        });
        calculatingThread.start();
        try {
            calculatingThread.join(TIMEOUT);
        } catch (InterruptedException e) {
            /* no-op */
        }
        switch (status){
            case FINISH:
                return result;
            case ERROR:
                throw ex;
            default:
                calculatingThread.stop();
                status = Status.TIMEOUT;
                throw new TimeoutException();
        }
    }

    public Status getStatus() {
        return status;
    }

}


