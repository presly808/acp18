package sockets.exclude;

import sockets.MyServerSocket;

public class ServerRun {
    public static void main(String[] args) {
        MyServerSocket server = new MyServerSocket(8080);
        server.start();
    }
}
