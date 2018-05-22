enum OperationType {
    /**
     * Enum représentant les types d'opérations possibles
     */
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    UNKNOWN
}

class Operation {
    /**
     * Classe représentant une opération. On y stocke un type d'opération, les strings réprésentant les deux arguments
     * et un tableau de Number stockant les interprétations de ces arguments (Integer ou Double).
     */
    private OperationType type;
    private String arg1;
    private String arg2;
    private Number[] values = null;

    /**
     * Constructeur d'une opération.
     *
     * @param op   opérande (+,-,*,/)
     * @param val1 premier argument de l'opérande
     * @param val2 deuxième argument de l'opérande
     */
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
        setValues(parseValues());
    }

    /**
     * Méthode qui analyse les arguments pour les convertir de String à un type numérique
     * @return Un tableau de number représentant les deux arguments interprétes
     */
    private Number[] parseValues() {
        Number[] res = new Number[2];
        if (getArg1() != null && getArg2() != null) {
            if (getArg1().matches(".*[.,].*")) { //On vérifie si on trouve une virgule ou un point => Double sinon Int
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

    /**
     * Méthode qui permet de créer un message à partir des données de l'opérations. On ajuste le nom de la méthode à appeler
     * en fonction du type d'opération analysé
     * @return Message a envoyer au serveur
     * @throws Exception Exception levée si on n'arrive pas à determiner la nature de l'opération
     */
    Message createMsg() throws Exception {
        String methodName = "";
        switch (getType()) {
            case PLUS:
                methodName = "add";
                break;
            case MINUS:
                methodName = "sub";
                break;
            case DIVIDE:
                methodName = "divide";
                break;
            case MULTIPLY:
                methodName = "multiply";
                break;
            case UNKNOWN:
                throw new Exception("UNKNOWN OPERATION TYPE");
        }
        return new Message(getValues()[0], getValues()[1], methodName);
    }

    private OperationType getType() {
        return type;
    }

    private void setType(OperationType type) {
        this.type = type;
    }

    private String getArg1() {
        return arg1;
    }

    private void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    private String getArg2() {
        return arg2;
    }

    private void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    private Number[] getValues() {
        return values;
    }

    private void setValues(Number[] values) {
        this.values = values;
    }
}
