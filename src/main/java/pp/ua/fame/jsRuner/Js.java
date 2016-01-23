package pp.ua.fame.jsRuner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pp.ua.fame.exception.TimeoutException;

import javax.script.ScriptEngine;
import javax.script.ScriptException;


@Component()
@Scope("prototype")
public class Js {

    private static final int TIMEOUT = 5000;

    @Autowired
    private ScriptEngine engine;

    private Status status = Status.CREATE;

    private String source;

    private ScriptException ex;

    private Result result;


    public void setSource(String source) {
        this.source = source;
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


