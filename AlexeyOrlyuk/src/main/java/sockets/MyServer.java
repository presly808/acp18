package sockets;

import sockets.ServerSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex323glo on 26.01.18.
 */
public class MyServer {

    private List<MyHandler> handlerList;

    public MyServer() {
        handlerList = new ArrayList<>();
    }

    public void runServer(int serverPort) throws IOException {
        java.net.ServerSocket serverSocket = new java.net.ServerSocket(serverPort);

        System.out.printf("*** Server started *** \nInfo: %s \n***\n", serverSocket);

        while (true) {
            MyHandler handler = new MyHandler(serverSocket.accept(), serverSocket.toString());
            handlerList.add(handler);
            handler.run();
        }
    }
}

class MyHandler extends Thread {

    private Socket client;
    private String serverInfo;

    public MyHandler(Socket client, String serverInfo) {
        if (client == null) {
            throw new NullPointerException("client is null");
        }

        this.client = client;
        this.serverInfo = serverInfo;
    }

    @Override
    public void run() {
        if (client.isClosed()) {
            return;
        }

        System.out.printf("New connection: %s\n", client);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter writer = new PrintWriter(client.getOutputStream());

            writer.printf("\n *** Congratulations! *** \n\tYou've successfully connected. " +
                            "\n\tServer info: %s \n\tClient info: %s \n ***\n\n",
                    client, serverInfo);
            writer.flush();

            while (true) {
                writer.print(" @ ");
                writer.flush();

                String line = reader.readLine();

                if (line != null) {
                    System.out.printf("Client[addr:%s; port:%s; local_port:%s]: %s\n",
                            client.getInetAddress(), client.getPort(), client.getLocalPort(), line);
                }

                if (line == null || line.trim().toLowerCase().equals("exit")) {
                    System.out.printf("Client closed connection. Client's info: %s\n", client);
                    break;
                }
            }

            reader.close();
            writer.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}

