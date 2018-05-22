import java.io.Serializable;

/**
 * Classe représentant un message envoyé au serveur. Classe qui implémente Serializable.
 */
class Message implements Serializable {

    private Number param1;
    private Number param2;
    private String methodName;

    /**
     * Constructeur d'un Message.
     *
     * @param val1 premier paramètre de l'opération (ex: 1, 1.2, 0, etc.)
     * @param val2 second paramètre de l'opération
     * @param met  nom de l'opération à effectuer (cf Calc : add, sub, multiply, divide)
     */
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
