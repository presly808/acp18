package ClientServer;

import java.io.*;
import java.net.Socket;

public class ClientServ {
        public static void main(String[] args) throws IOException {
            Socket socket = new Socket("127.0.1.1", 8080);
            System.out.println("1");

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            System.out.println("2");

            OutputStream os = socket.getOutputStream();
            os.write("pwd".getBytes());
            System.out.println("3");

            StringBuilder sb = new StringBuilder();
            String line = null;
            System.out.println("4");

            System.out.println(br.readLine());
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            System.out.println("5");

            System.out.println(sb.toString());
            System.out.println("6");
        }
}
