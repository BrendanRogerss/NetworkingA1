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
                BufferedReader serverIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                switch(userInput){
                    case "STORE":
                        out.println(userInput);
                        for (int i = 0; i < 4; i++) {
                            out.println(stdIn.readLine());
                        }
                        break;
                    case"QUERY":
                        out.println(userInput);
                        while(serverIn.ready()){
                            System.out.println(serverIn.readLine());
                        }
                    default:
                        out.println(userInput);
                }
                    System.out.println(serverIn.readLine());


            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    }
}
