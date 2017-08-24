import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Brendan on 20/08/2017.
 */
public class TaxServer {

    private TaxCalculator taxCalculator = new TaxCalculator();

    public static void main(String[] args) {
        TaxServer server = new TaxServer();
        server.start();
    }

    public void start(){
        int portNumber = 12345;
        String[] V = {"","","",""};

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                switch (inputLine){
                    case "TAX":
                        out.println("TAX: OK");
                        break;
                    case "STORE":
                        for (int i = 0; i < 4; i++) {
                            V[i] = in.readLine();
                        }
                        taxCalculator.add(V[0],V[1],V[2],V[3]);
                        out.println("STORE: OK");
                        break;
                    case "QUERY":
                        out.println(taxCalculator.query());
                        break;
                    case "BYE":
                        serverSocket.close();
                        break;
                    case "END":
                        return;

                    default:
                        out.println(taxCalculator.calculate(Integer.parseInt(inputLine)));
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
