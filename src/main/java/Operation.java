enum OperationType {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    UNKNOWN
}

public class Operation {
    private OperationType type;
    private String arg1;
    private String arg2;

    Operation(String op, String val1, String val2) {
        setArg1(val1);
        setArg2(val2);
        switch (op) {
            case "+":
                setType(OperationType.PLUS);
                break;
            case "-":
                setType(OperationType.MINUS);
                break;
            case "/":
                setType(OperationType.DIVIDE);
                break;
            case "*":
                setType(OperationType.MULTIPLY);
                break;
            default:
                setType(OperationType.UNKNOWN);
                break;
        }
    }

    public Number[] parseValues() {
        Number[] res = new Number[2];
        if (getArg1() != null && getArg2() != null) {
            if (getArg1().matches(".*[.,].*")) {
                res[0] = Double.parseDouble(getArg1());
            } else {
                res[0] = Integer.parseInt(getArg1());
            }
            if (getArg2().matches(".*[.,].*")) {
                res[1] = Double.parseDouble(getArg2());
            } else {
                res[1] = Integer.parseInt(getArg2());
            }
        }
        return res;
    }

    public OperationType getType() {
        return type;
    }

    private void setType(OperationType type) {
        this.type = type;
    }

    public String getArg1() {
        return arg1;
    }

    private void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    private void setArg2(String arg2) {
        this.arg2 = arg2;
    }
}
