import org.junit.Test;
import pp.ua.fame.jsRuner.Js;
import pp.ua.fame.jsRuner.Result;
import pp.ua.fame.exception.TimeoutException;
import pp.ua.fame.exception.TypeMismatchException;

import javax.script.ScriptException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Tests {
    @Test
    public void test() throws ScriptException, TypeMismatchException, TimeoutException {
        Js test = new Js();
        test.setSource("x=3; x = 3+3+x; x*= 4; x;");
        Result result = test.eval();

        assertEquals(result.getNumber(),(Double) 36.0);


        System.out.println(result);
    }

    @Test
    public void treadSafeTest() throws InterruptedException {
        class MyTread implements Runnable{
            private Js test;
            private Double answer;

            public MyTread(Js test, Double answer) {
                this.test = test;
                this.answer = answer;
            }

            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++){
                    try {
                        Result result = test.eval();
                        assertEquals (result.getNumber(), answer);
                    } catch (ScriptException | TypeMismatchException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Js test1 = new Js();
        test1.setSource("x=2; x = 2+2+x; x*= 3; x;");
        Js test2 = new Js();
        test2.setSource("x=3; x = 3+3+x; x*= 4; x;");
        Thread thread1 = new Thread(new MyTread(test1, 18.0));
        Thread thread2 = new Thread(new MyTread(test2, 36.0));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }


    @Test
    public void isTest() throws ScriptException, TimeoutException {
        Js string = new Js();
        string.setSource("'String'");
        Js boolTrue = new Js();
        boolTrue.setSource("true");
        Js boolfalse = new Js();
        boolfalse.setSource("false");
        Js Null = new Js();
        Null.setSource("null");
        Js undefined = new Js();
        undefined.setSource("undefined");
        Js numberInt = new Js();
        numberInt.setSource("2");
        Js numberDouble = new Js();
        numberDouble.setSource("2.3");
        Js array = new Js();
        array.setSource("[2, 3, 4]");
        Js object = new Js();
        object.setSource("function x(){this.x = this}; new x");
        Result stringResult = string.eval();
        Result boolTrueResult = boolTrue.eval();
        Result boolfalseResult = boolfalse.eval();
        Result NullResult = Null.eval();
        Result undefinedResult = undefined.eval();
        Result numberIntResult = numberInt.eval();
        Result numberDoubleResult = numberDouble.eval();
        Result arrayResult = array.eval();

        assertTrue(stringResult.isString());
        assertTrue(boolTrueResult.isBoolean());
        assertTrue(boolfalseResult.isBoolean());
        assertTrue(NullResult.isNull());
        assertTrue(undefinedResult.isNull());
        assertTrue(numberIntResult.isNumber());
        assertTrue(numberDoubleResult.isNumber());
        assertTrue(arrayResult.isArray());
    }

    @Test
    public void  taskTest(){

//        Task taskDAO = new TestMock();
//        String source = "function sum(array, from){\n" +
//                "\tif (isNaN(from)) from = 0;\n" +
//                "\tif (from >= array.length) return 0;\n" +
//                "\treturn array[from] + sum(array, from + 1);\n" +
//                "}";
//
//        for (pp.ua.fame.model.Test test: taskDAO.getTests()) {
//
//            try {
//                Object answer = new Js( + test.getTest()).eval().getNumber();
//        Object.setSource(source);
//                assertEquals(test.getAnswer(),answer);
//            } catch (ScriptException | TimeoutException e) {
//                System.err.println(e.getMessage());
//            } catch (TypeMismatchException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
