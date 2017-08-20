import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Brendan on 20/08/2017.
 */
public class TaxClient {

    public static void main(String[] args) {
        TaxClient client = new TaxClient();
        client.start();
    }

    public void start(){
        String hostName = "localhost";
        int portNumber = 12345;

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    }
}
