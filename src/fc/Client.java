package fc;

import java.io.Console;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        //java -cp fortunecookie.jar fc.Client localhost:12345
        if (args.length < 1) {
            System.err.println("Please input host and port number");
            System.exit(-1);
        }
        String [] arg = args[0].split(":");
        String host = arg[0];
        int port = Integer.parseInt(arg[1]);
        System.out.println(host + String.valueOf(port));
        
        Socket clientConn = new Socket(host, port);

        Console console = System.console();
        String command = console.readLine(">>");
        Cookie cookie = new Cookie();
        while (command.equalsIgnoreCase("get cookie")) {            
            cookie.sendCommand(clientConn, command);
            System.out.println("Command sent");

            String cookieText = cookie.receiveCookie(clientConn);
            System.out.println(cookieText);
            
            command = console.readLine(">>");
        }
        System.out.println("Bye Bye.... Disconnecting from server...");
        cookie.sendCommand(clientConn, command);        
       


        
    }
    
}
