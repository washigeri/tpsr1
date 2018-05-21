import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

    private static int PORT = 9000;

    public static void main(String[] args) {
        if (args.length == 1) {
            PORT = Integer.parseInt(args[0]);
        }
        try {
            System.out.println("Starting server");
            ServerSocket welcomeSock = new ServerSocket(PORT);
            //noinspection InfiniteLoopStatement
            while (true) {
                Socket clientSock = welcomeSock.accept();
                System.out.println("Client connected from " + clientSock.getInetAddress().toString());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSock.getInputStream());
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSock.getOutputStream());
                //boolean clientOn = true;
                while (true) {
                    try {

                        Message inMsg = (Message) inFromClient.readObject();
                        Number result = processMsg(inMsg);
                        if (result != null) {
                            outToClient.writeObject(result);
                        }
                    } catch (Exception e) {
                        System.err.println("Connection closed from " + clientSock.getInetAddress());

                        System.err.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
            System.err.println("To String: " + e.toString());
        }


    }

    private static Number processMsg(Message msg) {
        Number param1 = msg.getParam1();
        Number param2 = msg.getParam2();
        boolean hasDouble = param1 instanceof Double || param2 instanceof Double;
        Number res = null;
        try {
            Method m;
            if (hasDouble)
                m = Calc.class.getMethod(msg.getMethod(), double.class, double.class);
            else
                m = Calc.class.getMethod(msg.getMethod(), int.class, int.class);

            System.out.println("Processing " + m.getName() + "(" + param1 + "," + param2 + ")");
            res = (Number) m.invoke(null, param1, param2);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return res;
    }
}
