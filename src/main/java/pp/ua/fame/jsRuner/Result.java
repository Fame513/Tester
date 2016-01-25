package pp.ua.fame.jsRuner;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import pp.ua.fame.exception.TypeMismatchException;

import java.util.Map;

public class Result {
    private Object result;

    private String console;

    public Result(Object result) {
        this.result = result;
    }

    public Result(Object result, String console) {
        this.result = result;
        this.console = console;
    }

    public String getConsole() {
        return console;
    }

    public boolean isNull(){
        return result == null;
    }

    public boolean isScriptObject(){
        return result instanceof ScriptObjectMirror;
    }

    public boolean isNumber(){
        return result instanceof Number;
    }

    public boolean isString(){
        return result instanceof String;
    }

    public boolean isBoolean(){
        return result instanceof Boolean;
    }

    public boolean isArray(){
        if (isScriptObject()){
            ScriptObjectMirror scriptObject = (ScriptObjectMirror) result;
            return scriptObject.isArray();
        } else {
            return false;
        }
    }

    public boolean isFunction(){
        if (isScriptObject()){
            ScriptObjectMirror scriptObject = (ScriptObjectMirror) result;
            return scriptObject.isFunction();
        } else {
            return false;
        }
    }

    public ScriptObjectMirror getScriptObject() throws TypeMismatchException {
        if (isScriptObject()){
            return (ScriptObjectMirror) result;
        } else {
            throw new TypeMismatchException("Result is not ScriptObject");
        }
    }

    public Double getNumber() throws TypeMismatchException {
        if (isNumber()){
            return (Double.valueOf(result.toString()));
        } else {
            throw new TypeMismatchException("Result is not Number");
        }
    }

    public String getString() throws TypeMismatchException {
        if (isString()){
            return (String) result;
        } else {
            throw new TypeMismatchException("Result is not String");
        }
    }

    public Boolean getBoolean() throws TypeMismatchException {
        if (isBoolean()){
            return (Boolean) result;
        } else {
            throw new TypeMismatchException("Result is not Boolean");
        }
    }

    public Map getArray() throws TypeMismatchException {
        if (isArray()){
            return (Map)result;
        } else {
            throw new TypeMismatchException("Result is not Array");
        }
    }

    public Object getObject(){
        return result;
    }
}
