import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    private static String HOST = "localhost";
    private static int PORT = 9000;

    private static ObjectOutputStream outToServer = null;
    private static ObjectInputStream inFromServer = null;
    private static Message toSend = null;
    private static Socket sock;


    public static void main(String[] args) {
        if (args.length == 2) {
            HOST = args[0];
            PORT = Integer.parseInt(args[1]);
        }
        startConnexion();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Enter calc");
            try {
                String expression = br.readLine();
                continueLoop = processExpression(expression);
                System.out.println("Processed user input");
                System.out.println("Sending to server");
                outToServer.writeObject(toSend);
                Number result = (Number) inFromServer.readObject();
                System.out.println("Result from server : " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Exiting");
    }

    private static void startConnexion() {
        try {
            System.out.println("Connecting to " + HOST + ":" + PORT + "...");
            sock = new Socket(HOST, PORT);
            outToServer = new ObjectOutputStream(sock.getOutputStream());
            inFromServer = new ObjectInputStream(sock.getInputStream());
            System.out.println("Connected to " + HOST + ":" + PORT + ".");
        } catch (Exception e) {
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
        }
    }


    private static boolean processExpression(String input) throws Exception {
        System.out.println("Processing expression");
        String exit_pattern = "(?i)\\s*exit\\s*";
        Pattern operation_pattern = Pattern.compile("(?i)\\s*(\\d+[.,]?\\d*)\\s*([+\\-*/])\\s*(\\d+[.,]?\\d*)\\s*");
        if (input.matches(exit_pattern))
            return false;
        else {
            Matcher matcher = operation_pattern.matcher(input);
            if (matcher.find()) {
                Operation op = new Operation(matcher.group(2), matcher.group(1), matcher.group(3));
                toSend = op.createMsg();
            }
            return true;
        }
    }
}
