import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    /**
     * Classe du serveur. Le serveur écoute par défaut sur le port 9000
     */
    private static int PORT = 9000;

    /**
     * Fonction principale d'exécution du serveur. On écoute sur le port, puis lorsqu'une connexion se fait
     * on traite les demandes du client puis on envoie la réponse.
     *
     * @param args Paramètres de lancement du programme. Utilisé pour changer le port d'écoute si besoin
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            PORT = Integer.parseInt(args[0]);
        }
        try {
            System.out.println("Starting server");
            ServerSocket welcomeSock = new ServerSocket(PORT); // On écoute sur le PORT
            //noinspection InfiniteLoopStatement
            while (true) {
                Socket clientSock = welcomeSock.accept(); //Création d'un nouveau socket
                System.out.println("Client connected from " + clientSock.getInetAddress().toString());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSock.getInputStream()); //Création des Stream de communication avec le client
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSock.getOutputStream());
                while (true) { //tant que la connexion est active
                    try {

                        Message inMsg = (Message) inFromClient.readObject(); //réception du message du client
                        Number result = processMsg(inMsg); //analyse
                        if (result != null) {
                            outToClient.writeObject(result); //envoi du résultat
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

    /**
     * Fonction du serveur qui analyse un message reçu du client.
     * On va utiliser la reflexion pour invoquer la méthode correspondante de la classe Calc
     * @param msg Message reçu par le socket du client
     * @return Renvoie un Number (résultat : Int ou Double)
     */
    private static Number processMsg(Message msg) {
        Number param1 = msg.getParam1();
        Number param2 = msg.getParam2();
        boolean hasDouble = param1 instanceof Double || param2 instanceof Double; //On vérifie l'existence d'un double
        Number res = null;
        try {
            Method m;
            if (hasDouble) //Si double on cherche la méthode de Calc prenant comme type de paramètres les doubles
                m = Calc.class.getMethod(msg.getMethod(), double.class, double.class);
            else //Sinon on cherche la fonction sur les entiers
                m = Calc.class.getMethod(msg.getMethod(), int.class, int.class);

            System.out.println("Processing " + m.getName() + "(" + param1 + "," + param2 + ")");
            res = (Number) m.invoke(null, param1, param2); //Invocation de la méthode trouvée avec les paramètres
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return res; //Renvoi du résultat
    }
}
