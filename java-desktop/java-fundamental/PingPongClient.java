
import java.io.*;
import java.net.*;

public class PingPongClient {

    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 4444);
            System.out.println("client konek to server localhost:4444");
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            do {
                out.write("Ping\n");
                System.out.println("Send ping to server");
                out.flush();
                String inputLine = in.readLine();
                System.out.println("Recieved " + inputLine + " from server");
                //pause selama 1 detik
                Thread.sleep(1000);
            } while (true); //lakukan terus menerus
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
