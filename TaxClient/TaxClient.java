import java.io.*;
import java.net.Socket;

/**
 * Created by Brendan on 20/08/2017.
 */
public class TaxClient {

    String hostName = "localhost";
    int portNumber = 54321;

    public static void main(String[] args) {
        TaxClient client = new TaxClient();
        client.start();
    }

    public void start(){

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        Socket socket;
        PrintWriter out;
        BufferedReader serverIn;


        try {
            System.out.println("Enter in a custom hostname, else '0' to use default (local host)");
            hostName  = stdIn.readLine();
            hostName = hostName.equals("0")?"localhost":hostName;
            System.out.println("Enter in a custom port number, else '0' to use default (54321)");
            String customPort = "";
            if(!(customPort=stdIn.readLine()).equals("0")){
                portNumber = Integer.parseInt(customPort);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            System.out.println("Enter 'TAX' to connect to server");
            if((userInput = stdIn.readLine()).equals("TAX")) {
                socket = new Socket(hostName, portNumber);
                out = new PrintWriter(socket.getOutputStream(), true);
                serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println(userInput);
                System.out.println(serverIn.readLine());

                while ((userInput = stdIn.readLine()) != null) {
                    switch (userInput) {
                        case "STORE":
                            out.println(userInput);
                            for (int i = 0; i < 4; i++) {
                                out.println(stdIn.readLine());
                            }
                            System.out.println(serverIn.readLine());
                            break;
                        case "QUERY":
                            out.println(userInput);
                            System.out.println(serverIn.readLine());
                            while (serverIn.ready()) {
                                System.out.println(serverIn.readLine());
                            }
                            break;
                        case "BYE":
                            out.println(userInput);
                            System.out.println(serverIn.readLine());
                            break;
                        case "END":
                            out.println(userInput);
                            System.out.println(serverIn.readLine());
                            break;
                        default:
                            out.println(userInput);
                            System.out.println(serverIn.readLine());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }

    }
}
