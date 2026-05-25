/**
 * 
 * I declare that this code was written by me, 22028488. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: YU CHU YING
 */

package FYP.CREDITRISK;

/**
 * @author 22028488
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PythonVersionChecker {

    public static void main(String[] args) {
        try {
            // Path to the Python executable (adjust as needed)
            String pythonExePath = "python";  // Or specify the full path to python.exe if needed
            
            // Path to the Python script that checks the version
            String scriptPath = "C:\\Users\\ivanl\\AppData\\Local\\Microsoft\\WindowsApps\\python.exe";  // Replace with the actual path to your script
            
            // Command to run the Python script
            String[] command = {pythonExePath, scriptPath};

            // Create a process to execute the command
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);  // Combine standard error and standard output
            Process process = pb.start();

            // Read the output of the process
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Python Version Output: " + line);
            }
            in.close();

            // Wait for the process to complete
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
