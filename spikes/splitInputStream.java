

import java.io.*;
import java.io.BufferedInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.String;
import java.lang.System;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class splitInputStream {

    public static final int CR = 13;
    public static final int LF = 10;
    public static final int[] END_OF_HTTP_HEADER = { CR, LF, CR, LF };
    /**
     * Take a tip from Apache.
     * Reference:
     * http://stackoverflow.com/questions/686217/maximum-on-http-header-values
     * http://httpd.apache.org/docs/2.2/mod/core.html#limitrequestfieldsize
     *
     * But when we do the math, we get 1024 * 8 = 8192, not 8190 bytes, so we use that here.
     */
    public static final int LIMIT_REQUEST_FIELD_SIZE = 8192;
    public static final int LIMIT_BODY_FIELD_SIZE = 8192;


    public static void main(String[] args) throws IOException {
        System.out.println("I will be an http server one day.");
        ServerSocket myServerSocket = new ServerSocket(80);
        Socket mySocket = myServerSocket.accept();

        BufferedInputStream myBufferedInputStream = new BufferedInputStream(mySocket.getInputStream());
        /* Get the Header */
        // Probably not a good idea, do this a different way.
        // If we do this, then if we exceed LIMIT_REQUEST_FIELD_SIZE, we'd just silently fail to get the right bytes
        //     and High Chaos would ensue.
        myBufferedInputStream.mark(LIMIT_REQUEST_FIELD_SIZE);
        int i = 0;
        int current_byte = 0;
        int bytes_read = 0;

        while(i < 4 && current_byte != -1) {
            current_byte = myBufferedInputStream.read();
            bytes_read++;
            if(current_byte == END_OF_HTTP_HEADER[i])
                i++;
            else
                i = 0;
        }

        byte[] headerBytes = new byte[bytes_read];
        myBufferedInputStream.reset();
        myBufferedInputStream.read(headerBytes, 0, bytes_read);

        /* Get the Body */
        myBufferedInputStream.mark(LIMIT_BODY_FIELD_SIZE);
        bytes_read = 0;

        while(myBufferedInputStream.read() != -1)
            bytes_read++;

        byte[] bodyBytes = new byte[bytes_read];
        myBufferedInputStream.reset();
        myBufferedInputStream.read(bodyBytes, 0, bytes_read);



        String myHeader = new String(headerBytes);
        String myBody = new String(bodyBytes);

        System.out.println("Header: ");
        System.out.println(myHeader);
        System.out.println(("Body: "));
        System.out.println(myBody);

        mySocket.close();
    }

}
