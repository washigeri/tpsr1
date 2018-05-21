import java.io.Serializable;

class Message implements Serializable {

    private Number param1;
    private Number param2;
    private String methodName;

    Message(Number val1, Number val2, String met) {
        setParam1(val1);
        setParam2(val2);
        setMethod(met);
    }

    Number getParam1() {
        return param1;
    }

    private void setParam1(Number param1) {
        this.param1 = param1;
    }

    Number getParam2() {
        return param2;
    }

    private void setParam2(Number param2) {
        this.param2 = param2;
    }

    String getMethod() {
        return methodName;
    }

    private void setMethod(String method) {
        this.methodName = method;
    }
}
