import org.junit.Test;
import pp.ua.fame.JSRuner.JS;
import pp.ua.fame.JSRuner.Result;
import pp.ua.fame.exceptions.TimeoutException;
import pp.ua.fame.exceptions.TypeMismatchException;

import javax.script.ScriptException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Tests {
    @Test
    public void test() throws ScriptException, TypeMismatchException, TimeoutException {
        JS test = new JS("x=3; x = 3+3+x; x*= 4; x;");
        Result result = test.eval();

        assertEquals(result.getNumber(),(Double) 36.0);


        System.out.println(result);
    }

    @Test
    public void treadSafeTest() throws InterruptedException {
        class MyTread implements Runnable{
            private JS test;
            private Double answer;

            public MyTread(JS test, Double answer) {
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

        JS test1 = new JS("x=2; x = 2+2+x; x*= 3; x;");
        JS test2 = new JS("x=3; x = 3+3+x; x*= 4; x;");
        Thread thread1 = new Thread(new MyTread(test1, 18.0));
        Thread thread2 = new Thread(new MyTread(test2, 36.0));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }


    @Test
    public void isTest() throws ScriptException, TimeoutException {
        JS string = new JS("'String'");
        JS boolTrue = new JS("true");
        JS boolfalse = new JS("false");
        JS Null = new JS("null");
        JS undefined = new JS("undefined");
        JS numberInt = new JS("2");
        JS numberDouble = new JS("2.3");
        JS array = new JS("[2, 3, 4]");
        JS object = new JS("function x(){this.x = this}; new x");
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
        int x = -2147483648;
//        Task taskDAO = new TestMock();
//        String source = "function sum(array, from){\n" +
//                "\tif (isNaN(from)) from = 0;\n" +
//                "\tif (from >= array.length) return 0;\n" +
//                "\treturn array[from] + sum(array, from + 1);\n" +
//                "}";
//
//        for (pp.ua.fame.persistence.Test test: taskDAO.getTests()) {
//
//            try {
//                Object answer = new JS(source + test.getTest()).eval().getNumber();
//                assertEquals(test.getAnswer(),answer);
//            } catch (ScriptException | TimeoutException e) {
//                System.err.println(e.getMessage());
//            } catch (TypeMismatchException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
