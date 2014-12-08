

import java.io.*;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.String;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class outputStreamRequestServer {

    public static void main(String[] args) throws IOException {
        System.out.println("I will be an http server one day.");
        ServerSocket myServerSocket = new ServerSocket(80);
        Socket mySocket = myServerSocket.accept();

        InputStream myInputStream = mySocket.getInputStream();
        OutputStream myOutputStream = mySocket.getOutputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(myInputStream));
        //PrintWriter out = new PrintWriter(myOutputStream);
        ObjectOutputStream out = new ObjectOutputStream(myOutputStream);

        Vector<String> myStrings = new Vector<String>();
        String myLine;
        while((myLine = in.readLine()).length() > 0)
            myStrings.add(myLine);

        for(String myString : myStrings) {
            out.writeObject(myString);
        }
        mySocket.close();
    }

}
