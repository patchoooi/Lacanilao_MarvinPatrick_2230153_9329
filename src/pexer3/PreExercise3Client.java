/**
 AUTHOR: Lacanilao, Marvin Patrick D.
 ACITIVITY: Prelim Laboratory Exercise 3
 CLASS CODE: 9329 - CS 222L Computer Programming 3
 -----------------------------------------------------------------------------------------------------------------
 SAMPLE RUN:
 5 ^ 2 = 25.0
 12 % 5 = 2.0
 8.5 * 2 = 17.0
 20 / 4 = 5.0
 15 + 7 = 22.0
 5.2 + 2.x = Invalid expression: Invalid operand '5.2' or '2.x'
 23 $ 2.5 = Invalid expression: Invalid operator: $
 10 * three = Invalid expression: Invalid operand '10' or 'three'
 18 # 6 = Invalid expression: Invalid operator: #
 4.5 - 2.5 = 2.0
 100 * 2 = 200.0

 Process finished with exit code 0
 */

package pexer3;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * The PreExercise3Client class implements a simple client that reads mathematical
 * expressions from an input file, sends them to a server, and prints the responses
 * received from the server.
 */
public class PreExercise3Client {
    public static void main(String[] args) {
        try (
                // Create a socket connection to the server
                Socket socket = new Socket("localhost", 5420);
                // Create BufferedWriter to send data to the server
                BufferedWriter serverWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                // Create BufferedReader to receive data from the server
                BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // Read expressions from the input file
            List<String> expressions = Files.readAllLines(Paths.get("res/exer3.xml"));

            // Send expressions to the server
            for (String expression : expressions) {
                serverWriter.write(expression + "\n");
            }

            // Send "bye" to indicate the end of data
            serverWriter.write("bye\n");
            serverWriter.flush();

            // Receive and print responses from the server
            String response;
            while ((response = serverReader.readLine()) != null) {
                System.out.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}