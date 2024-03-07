/**
 AUTHOR: Lacanilao, Marvin Patrick D.
 ACITIVITY: Midterm Exercise 1
 CLASS CODE: 9329 - CS 222L Computer Programming 3
 -----------------------------------------------------------------------------------------------------------------
 SAMPLE RUN (MidExercise1 class):
 Server is running...

 SAMPLE RUN (Client class):

 Input: sa1nt & louis
 Result: 9 aoui 4 sntls 5

 Input: un1v3rsityyyyy
 Result: 12 ui 2 nvrstyyyyy 10

 Input: b@cK 1n ch1c@go
 Result: 9 o 1 bcKnchcg 8
 */

package mexer1;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * The Client class for testing the RMI server implementation (MidExercise1).
 */
public class Client {

    /**
     * The main method of the Client class.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Look up the RMI server
            MidInterface1 server = (MidInterface1) Naming.lookup("rmi://localhost/MidExercise1");

            // Test cases
            profileString(server, "sa1nt & louis");
            profileString(server, "un1v3rsityyyyy");
            profileString(server, "b@cK 1n ch1c@go");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Invokes the remote method on the RMI server and displays the result.
     *
     * @param server The RMI server object.
     * @param input  The input string for the profileString method.
     */
    private static void profileString(MidInterface1 server, String input) {
        try {
            String result = server.profileString(input);

            // Display the result
            System.out.println("Input: " + input);
            System.out.println("Result: " + result);
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
