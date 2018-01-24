package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by serhii on 21.01.18.
 */
public class MyServerSocket {

    private int port;
    private int id;

    public MyServerSocket(int port) {
        this.port = port;
    }

    // start server socket and wait for new connection
    // if we establish new connection, firstly read from client read until meet '\n' as the last character,
    // execute that command through a command line on the  host,
    // then return the response(must be in one line with '\n' in the end) of execution
    // if client send "shutdown-server" command,  we kill the serverSocket
    // if we send "os", the server must reply with either "LINUX" or "WIN"
    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server was ran");

            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                System.out.printf("new connection № %d\n", ++id);

                BufferedReader socketInputStream = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                PrintWriter pw = new PrintWriter(client.getOutputStream(), true);

                if ((line = socketInputStream.readLine()) != null) {
                    System.out.println("Received a message: " + line);

                    if (line.equals("os")) {
                        pw.println("LINUX");

                    } else if (line.equals("shutdown-server")) {
                        pw.close();
                        serverSocket.close();
                        System.out.println("Server is stopped");

                    } else {
                        Process p = Runtime.getRuntime().exec(line);
                        BufferedReader TerminalInputStream = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));

                        String lineFromTerminal = null;

                        while ((lineFromTerminal = TerminalInputStream.readLine()) != null) {
                            sb.append(lineFromTerminal).append(" ");
                        }
                        pw.println(sb.toString());

                        // clear StringBuilder
                        sb.setLength(0);
                    }
                }
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}