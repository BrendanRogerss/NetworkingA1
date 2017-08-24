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

        String menu = ("Enter: 'q' to Query the servers database    's' to Store a new tax range " +
                "    'b' to close the connection    'e' to terminate the server    " +
                "or any number to calculate its tax if known"); //string used to display the menu
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        Socket socket;
        PrintWriter out;
        BufferedReader serverIn;


        try {
            //let the user enter a custom hostname
            System.out.println("Enter in a custom hostname, else '0' to use default (local host)");
            hostName  = stdIn.readLine();
            hostName = hostName.equals("0")?"localhost":hostName;
            //let the user enter a custom port number
            System.out.println("Enter in a custom port number, else '0' to use default (54321)");
            String customPort = stdIn.readLine();
            portNumber = customPort.equals("0")?54321:Integer.parseInt(customPort);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            System.out.println("Enter 'TAX' to connect to server");
            if((userInput = stdIn.readLine()).equals("TAX")) { //wait for the user to enter TAX before starting
                //connect to the server
                socket = new Socket(hostName, portNumber);
                out = new PrintWriter(socket.getOutputStream(), true);
                serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println(userInput);//send TAX to the server
                System.out.println(serverIn.readLine()); //print the servers return message
                System.out.println(menu); //display the mnu
                while ((userInput = stdIn.readLine()) != null) { //continue to read until the connection terminates
                    switch (userInput.toUpperCase()) {
                        case "S":
                            System.out.println("Enter in the 4 numbers, pressing Enter after each");
                            out.println("STORE"); //send STORE to the server
                            for (int i = 0; i < 4; i++) { //loop over the 4 inupts
                                out.println(stdIn.readLine()); //send store to the server
                            }
                            System.out.println(serverIn.readLine()); //display the servers message
                            break;
                        case "Q":
                            out.println("QUERY"); //send QUERY to the server
                            System.out.println(serverIn.readLine()); //print out the first line from the server
                            while (serverIn.ready()) { //check if the is more from the server
                                System.out.println(serverIn.readLine()); //print it out
                            }
                            break;
                        case "B":
                            out.println("BYE"); //send BYE to the server
                            System.out.println(serverIn.readLine()); //print the servers response
                            return; //close down the client
                        case "E":
                            out.println("END"); //send END to the server
                            System.out.println(serverIn.readLine()); //print message from the server
                            return;
                        default:
                            out.println(userInput); //send user input to the server
                            System.out.println(serverIn.readLine()); //display the response message
                    }
                    System.out.println(menu); //display the menu
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }

    }
}
