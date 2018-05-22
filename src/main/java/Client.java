import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe du client. C'est ici que l'on trouve le code principal d'execution du client
 */
public class Client {

    private static String HOST = "localhost";
    private static int PORT = 9000;

    private static ObjectOutputStream outToServer = null;
    private static ObjectInputStream inFromServer = null;
    private static Message toSend = null;
    private static Socket sock;

    /**
     * Fonction main du client. D'abord on se connecte au serveur, puis on lit
     * l'entrée système pour ensuite analyser l'expression entrée pour l'envoyer
     * au serveur, qui va nous renvoyer le résultat
     *
     * @param args Paramètres d'appel du programme. Param 1 : adresse du serveur Param 2 : port du serveur
     */

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
                String expression = br.readLine(); //Lecture de l'entrée
                continueLoop = processExpression(expression); //Analyse de l'entrée => évaluation de l'expression ou arret du programe
                System.out.println("Processed user input");
                System.out.println("Sending to server");
                outToServer.writeObject(toSend); //Envoi au serveur
                Number result = (Number) inFromServer.readObject(); //Réponse du serveur
                System.out.println("Result from server : " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            sock.close(); //Fin de la connexion
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Exiting");
    }

    /**
     * Fonction qui initalise la connexion au serveur en créant les ObjectIOStream avec le socket.
     */
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

    /**
     * Fonction qui permet d'analyser la saisie de l'utilisateur dans la console (à l'aide de regex)
     * @param input Entrée de l'utilisateur
     * @return Booléen indiquant si on continue l'execution de la boucle principale
     * @throws Exception En cas d'erreur lors de l'analyse de l'entrée
     */
    private static boolean processExpression(String input) throws Exception {
        System.out.println("Processing expression");
        String exit_pattern = "(?i)\\s*exit\\s*"; //regex pour détecter si on tape "exit"
        Pattern operation_pattern = Pattern.compile("(?i)\\s*([+\\-]?\\d+[.,]?\\d*)\\s*([+\\-*/])\\s*([+\\-]?\\d+[.,]?\\d*)\\s*"); //regex pour analyser l'expression arithmétique, l'expression ne peut être que de la forme a(+-/*)b (deux valeurs seulement)
        if (input.matches(exit_pattern))
            return false; //si exit, on renvoie false pour arreter la boucle principale
        else {
            Matcher matcher = operation_pattern.matcher(input);
            if (matcher.find()) {
                Operation op = new Operation(matcher.group(2), matcher.group(1), matcher.group(3)); //on instancie un objet opération avec les valeurs repérées. par exemple, pour 1+3 les params seront (1,3,+)
                toSend = op.createMsg(); //Création du message à partir de l'opération retrouvée
            }
            return true; //Continue la boucle si tout se passe bien
        }
    }
}
