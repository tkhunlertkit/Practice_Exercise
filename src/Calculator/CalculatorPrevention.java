package Calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import GUI.MyCanvas;
import GUI.STATE;

/**
 * Created by Tanawat Khunlertkit on 8/24/15.
 */
public class CalculatorPrevention implements ActionListener {

    private MyCanvas m;

    public CalculatorPrevention(MyCanvas myCanvas) {
        m = myCanvas;
    }


    private static void executeCommand(CalculatorOperation co) {
        String[] command = new String[3];
        String os = System.getProperty("os.name").toLowerCase();
        int calcPID = getCalcPID();
        if (os.contains("win")) {
            command[0] = "CMD";
            command[1] = "/C";
            command[2] = (co == CalculatorOperation.OPEN_CALC) ? "calc" :
                    (calcPID >= 0) ? "taskkill /F /pid " + calcPID : "";
        } else {
            command[0] = "bash";
            command[1] = "-c";
            command[2] = (co == CalculatorOperation.OPEN_CALC) ? "open /Applications/calculator.app" :
                    (calcPID >= 0) ? "kill -9 " + calcPID : "";
        }
        try {
            new ProcessBuilder(command).start();
        } catch (IOException e) {
            System.err.println("Error executing command to " + ((co == CalculatorOperation.CLOSE_CALC) ? "close " : "open ") + "Calculator");
        }
    }

    public static int getCalcPID() {
        // return -1 of does not exist or fail execution
        String os = System.getProperty("os.name").toLowerCase();
        int calcPID = -1;
        String[] command = new String[3];
        if (os.contains("win")) {
            command[0] = "CMD";
            command[1] = "/C";
            command[2] = System.getenv("windir") + "\\system32\\" + "tasklist.exe";
        } else {
            command[0] = "bash";
            command[1] = "-c";
            command[2] = "ps -ax";
        }
        ProcessBuilder probuilder = new ProcessBuilder(command);

        try {
            Process p = probuilder.start();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains("calc")) {
                    System.out.println(os);
                    System.out.println(line);
                    String[] s = line.trim().split(" ");
                    System.out.println(s[0] + " " + s[1]);
                    calcPID = os.contains("win") ? Integer.parseInt(s[1]) : Integer.parseInt(s[0]);
                    System.out.println("Search for PID found with PID = " + calcPID);
                }
            }
        } catch (IOException e1) {
            System.err.print("Process cannot be executed.");
            e1.printStackTrace();
        }
        return calcPID;
    }

    public static void closeCalculator() {
        executeCommand(CalculatorOperation.CLOSE_CALC);
    }

    public static void openCalculator() {
        if (getCalcPID() == -1) {
            executeCommand(CalculatorOperation.OPEN_CALC);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println(os);
        int calcPID = getCalcPID();

        if (calcPID >= 0) {
            if (m.getGameState() == STATE.CAL_DETECTED) {
                System.out.println("Calculator detected !!!");
                closeCalculator();
                m.setGameState(STATE.CAL_DETECTED);
            } else {
                // CAL_WARNING_FLAG
                closeCalculator();
                m.setGameState(STATE.CAL_DETECTED);
            }
        }


    }

    private enum CalculatorOperation {
        OPEN_CALC, CLOSE_CALC
    }

}
