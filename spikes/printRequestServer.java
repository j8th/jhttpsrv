

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class printRequestServer {

    public static void main(String[] args) throws IOException {
        System.out.println("I will be an http server one day.");
        ServerSocket myServerSocket = new ServerSocket(80);
        Socket mySocket = myServerSocket.accept();
        InputStream myInputStream = mySocket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(myInputStream));
        String myLine;
        //while((myLine = in.readLine()) != "")
        while((myLine = in.readLine()).length() > 0)
            System.out.println(myLine);
        mySocket.close();
    }

}
