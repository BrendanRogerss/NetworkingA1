import java.io.*;
import java.net.Socket;

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
                switch(userInput){
                    case "STORE":
                        for (int i = 0; i < 4; i++) {
                            userInput+="\n"+stdIn.readLine();
                        }
                        out.print(userInput);
                    default:
                        out.println(userInput);
                }

                System.out.println(in.readLine());

            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    }
}
