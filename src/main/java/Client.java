import java.lang.reflect.Method;

public class Client {

    public static void main(String[] args) throws NoSuchMethodException {
        Calc c = new Calc();
        Method m = c.getClass().getDeclaredMethod("add", float.class, float.class);
        System.out.print("ok");
    }
}
