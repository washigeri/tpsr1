import java.io.Serializable;

@SuppressWarnings("ALL")
/**
 * Classe qui est appelée pour effectuer les opérations arithmétiques
 * de base (addition, soustraction, multiplication et division).
 */
public class Calc implements Serializable {


    public static int add(int a, int b) {
        return a + b;
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static int divide(int a, int b) {
        return a / b;
    }

    public static double divide(double a, double b) {
        return a / b;
    }

    public static int sub(int a, int b) {
        return a - b;
    }

    public static double sub(double a, double b) {
        return a - b;
    }

}
