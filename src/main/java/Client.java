import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Enter calc");
            try {
                String expression = br.readLine();
                continueLoop = processExpression(expression);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Exiting");
    }


    private static boolean processExpression(String input) {
        System.out.println("Processing expression");
        String exit_pattern = "(?i)\\s*exit\\s*";
        Pattern operation_pattern = Pattern.compile("(?i)\\s*([0-9]+)\\s*([+\\-*/])\\s*([0-9]+)\\s*");
        if (input.matches(exit_pattern))
            return false;
        else {
            Matcher matcher = operation_pattern.matcher(input);
            if (matcher.find()) {
                Operation op = new Operation(matcher.group(2), matcher.group(1), matcher.group(3));
            }
            return true;
        }
    }
}
