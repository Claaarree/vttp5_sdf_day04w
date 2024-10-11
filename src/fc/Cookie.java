package fc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.io.InputStreamReader;

public class Cookie {
    //for use in server
    public String getCommand(Socket clientConn) throws IOException {
        InputStream is = clientConn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        //System.out.println("in get command");

        String command = br.readLine();
        //System.out.println("after read line");
        
        return command;
    }

    public ArrayList<String> readCookiefile(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        ArrayList <String> fortunes = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            fortunes.add(line);
        }
        br.close();
        fr.close();
        return fortunes;
    }

    public String getRandomFortune(ArrayList<String> fortunes) {
        Random random = new SecureRandom();
        int randomnumber = random.nextInt(fortunes.size() -1);
        String theFortune = fortunes.get(randomnumber);
        return theFortune;
    }

    public void sendCookie(Socket clientConn, String theFortune) throws IOException {
        OutputStream os = clientConn.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        bw.write("cookie-text " + theFortune);
        bw.newLine();
        bw.flush();
    }

    //for use in client
    public void sendCommand (Socket clientConn, String command) throws IOException {
        OutputStream os = clientConn.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        bw.write(command);
        bw.newLine();
        bw.flush();
    }

    public String receiveCookie(Socket clientConn) throws IOException {
        InputStream is = clientConn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        //System.out.println("in receive cookie");
        
        String cookieText = br.readLine();

        if (cookieText.startsWith("cookie-text ")) {
            //System.out.println("hello");
            cookieText = cookieText.replaceFirst("cookie-text ", "");
        }

        //System.out.println("after read line");
        return cookieText;
    }
}
