package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerRun {
    public static void main(String[] args) {

        /*try {
            Process p = Runtime.getRuntime().exec("pwd");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        MyServerSocket server = new MyServerSocket(8080);
        server.start();

        /*try {
            Process p = Runtime.getRuntime().exec("ls");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


}
