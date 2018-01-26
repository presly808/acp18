package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdExecutor {

    Runtime runtime;

    public CmdExecutor() {
        this.runtime = Runtime.getRuntime();
    }

    public String exec(String cmd) {

        String result = ownCommandsHandler(cmd);
        if (!result.isEmpty()) {
            return result;
        }

        return osHandler(cmd);

    }

    private String osHandler(String cmd) {

        String result, line;
        result = "";

        Process process = null;
        BufferedReader bufReadResult, bufReadErrors;

        try {
            process = runtime.exec(cmd);
            bufReadResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = bufReadResult.readLine()) != null) {
                result += line + "\n";
            }
        } catch (IOException e) {
            result = "Something gone wrong! - " + e.toString();
        } catch (NullPointerException e){
            bufReadErrors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            try {
                while ((line = bufReadErrors.readLine()) != null) {
                    result += line + "\n";
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return result;
    }

    private static String ownCommandsHandler(String cmd) {

        if (cmd.isEmpty()) {
            return "enter command!";
        }

        if (cmd.equalsIgnoreCase("os")) {
            return getOSVerion();
        }

        if (cmd.equalsIgnoreCase("-help")) {
            return "help yourself!";
        }

        return "";
    }

    public static String getOSVerion() {

        String os = System.getProperty("os.name").toLowerCase();

        if (os.indexOf("win") >= 0) {
            os = "WIN";
        } else if (os.indexOf("lin") >= 0) {
            os = "LINUX";
        }

        return os;
    }


}
