


import sockets.Server;
import sockets.ServerSocket;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);

        serverSocket.start();

    }
}
