package sockets;

import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;

/**
 * Created by serhii on 21.01.18.
 */
public class ServerSocket {

    private int port;

    public ServerSocket(int port) {
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

        try (java.net.ServerSocket serverSocket = new java.net.ServerSocket(port)){
            System.out.println("Server was started.");
            while (true) {
                Socket client = serverSocket.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter pw = new PrintWriter(client.getOutputStream());
                String command = br.readLine();
                if (command != null) {
                    if (command.equals("shutdown-server")){
                        System.out.println("Server was shuted down.");
                        client.close();
                        serverSocket.close();
                        break;
                    }
                    if (command.equals("os")) {
                        pw.println("WIN");
                        pw.flush();
                    } else {
                        Runtime rt = Runtime.getRuntime();
                        Process pr = rt.exec(command);
                        BufferedReader responceReader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                        String response = responceReader.lines().collect(Collectors.joining());
                        pw.println(response);
                        pw.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
