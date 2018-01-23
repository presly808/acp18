package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdExecutor {

    Runtime runtime;

    public CmdExecutor() {
        this.runtime = Runtime.getRuntime();
    }

    public String exec(String cmd) throws IOException, InterruptedException {

        Process process = runtime.exec(cmd);
        //process.waitFor();
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line, result = "";

        while ((line = bufReader.readLine()) != null) {
            result+= line + "\n";
        }
        return result;

    }

}
