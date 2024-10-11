package fc;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {
        //java -cp fortunecookie.jar fc.Server 12345 cookie_file.txt
        int port = Integer.parseInt(args[0]);
        File file = new File(args[1]);

        //Creating server
        ServerSocket server = new ServerSocket(port);
        System.out.println("Listening in on port " + port);

        //Getting client connection
        while (true) {
            Socket clientConn = server.accept();
            System.out.println("Got a client connection!");

            Cookie cookie = new Cookie();
            while (!clientConn.isClosed()) {
                String command = cookie.getCommand(clientConn);

                //System.out.println(command);
                if (command.equalsIgnoreCase("get cookie")) {
                    ArrayList<String> fortunes = cookie.readCookiefile(file);
                    //System.out.println(fortunes);
                    String theFortune = cookie.getRandomFortune(fortunes);
                    //System.out.println(theFortune);
                    cookie.sendCookie(clientConn, theFortune);
                } else if (command.equalsIgnoreCase("quit")) {
                    System.out.println("Closing client connection...");
                    clientConn.close();
                    System.exit(0);
                }
            }
        }   
        
    }
}