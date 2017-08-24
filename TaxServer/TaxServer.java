import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Brendan on 20/08/2017.
 */
public class TaxServer {

    int portNumber = 54321;
    private TaxEngine taxEngine = new TaxEngine();

    public static void main(String[] args) {
        TaxServer server = new TaxServer();
        server.start();
    }

    public void start(){

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        //let the user define a custom host number
        System.out.println("Enter in a port number, or '0' to use the default (54321)");
        String customPort = "";
        try {
            if(!(customPort=stdIn.readLine()).equals("0")){
                portNumber = Integer.parseInt(customPort); //set the port number
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server Started on port: "+portNumber);
        while(true) { //while the server hasnt received "END"
            protocol(); //run the main server function
        }

    }

    public void protocol(){
        String[] V = {"", "", "", ""}; //a list to store range values

        try (   //declare resources here so they are returned when function closes
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) { //while the connection with the client exists
                switch (inputLine) {
                    case "TAX": //return the TAX message
                        out.println("TAX: OK");
                        break;
                    case "STORE": //prepare to store a new tax range
                        for (int i = 0; i < 4; i++) { //loop over the 4 values
                            String inputNumber = in.readLine(); //get value from the client
                            if (inputNumber.equals("~")) { //check for undefined number
                                inputNumber = Integer.toString(Integer.MAX_VALUE); //set it to max int
                            }
                            V[i] = inputNumber; //store the values
                        }
                        taxEngine.add(V[0], V[1], V[2], V[3]); //add them to the database
                        out.println("STORE: OK"); //return ok message to the client
                        break;
                    case "QUERY": //return query of all tax ranges from the database
                        out.println(taxEngine.query());
                        break;
                    case "BYE": //return bye message
                        out.println("BYE: OK");
                        return; //exit function, thereby freeing resources
                    case "END":
                        out.println("END: OK"); //return end message
                        System.exit(0); //shut down server
                    default:
                        String output;
                        try {
                            int inputNumber = Integer.parseInt(inputLine); //try parse int from the client
                            output = taxEngine.calculate(inputNumber); //return tax value
                        } catch (NumberFormatException e) {
                            output = "Unknown input"; //prepare error if wrong value was sent
                        }
                        out.println(output); //return message to the client
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }
}
