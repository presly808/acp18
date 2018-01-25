package sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by serhii on 21.01.18.
 */
public class Server {

    private int port;

    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
    }

    // start server socket and wait for new connection
    // if we establish new connection, firstly read from client read until meet '\n' as the last character,
    // execute that command through a command line on the  host,
    // then return the response(must be in one line with '\n' in the end) of execution
    // if client send "shutdown-server" command,  we kill the serverSocket
    // if we send "os", the server must reply with either "LINUX" or "WIN"
    public void start() {
        //todo
        try {
            serverSocket = new ServerSocket(port);
            Socket soket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(soket.getInputStream()));

            PrintWriter writer = new PrintWriter(soket.getOutputStream(),true);

            String request = reader.readLine();

            System.out.println(request);

            if (request.equals("os")) {
               String response = "WIN";
               writer.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}