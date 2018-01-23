package sockets;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientSocket {


    public static void main(String[] args) throws InterruptedException, IOException {

        Socket socket = new Socket("localhost", 8080);

        Scanner sc = new Scanner(System.in);
        String comand = "";

        while (!(comand.equalsIgnoreCase("s-s")
                || comand.equalsIgnoreCase("shutdown-server"))){

            comand = sc.nextLine();

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF(comand);
            out.flush();
            //out.close();

            Thread.sleep(1000);

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder sb = new StringBuilder();

            String line = null;

            while ((line = br.readLine()) != null) {

                sb.append(line).append("\n");
            }

        }

        socket.close();
        socket = null;

    }
}
