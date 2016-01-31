
import java.io.*;
import java.net.*;
public class PingPongServer {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(4444);
            System.out.println("Server ready. Listening in port 4444");
            Socket clientSocket = server.accept();
            System.out.println("Connection from client is excepted");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine = null;
            while((inputLine = in.readLine())!=null){
                System.out.println("Recieved " + inputLine + " from client");
                out.write("Pong\n");
                System.out.println("Send Pong to client");
                out.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
