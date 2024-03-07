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

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The RMI server class implementing the MidInterface1 remote interface.
 */
public class MidExercise1 extends UnicastRemoteObject implements MidInterface1 {

    /**
     * Default constructor for MidExercise1.
     *
     * @throws RemoteException If there is an issue with the remote communication.
     */
    public MidExercise1() throws RemoteException {
        super();
    }

    /**
     * The main method to start the RMI server.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Create and export the RMI server object
            MidExercise1 server = new MidExercise1();

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("MidExercise1", server);

            System.out.println("Server is running...");
        } catch (Exception e) {
            // Handle exceptions during server setup
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Implements the profileString method as described in the prompt.
     *
     * @param s The input string for which the profile is calculated.
     * @return A formatted string containing profile information.
     * @throws RemoteException If there is an issue with the remote communication.
     */
    @Override
    public String profileString(String s) throws RemoteException {

        // Count the number of letters, vowels, and consonants in the input string
        int lettersCount = s.replaceAll("[^a-zA-Z]", "").length();
        int vowelsCount = s.replaceAll("[^aeiouAEIOU]", "").length();
        int consonantsCount = lettersCount - vowelsCount;

        // StringBuilder to store the vowels and consonants in order of appearance
        StringBuilder vowels = new StringBuilder();
        StringBuilder consonants = new StringBuilder();

        // Iterate through each character in the input string
        for (char c : s.toCharArray()) {
            // Check if the character is a letter
            if (Character.isLetter(c)) {
                // Check if the letter is a vowel
                if ("aeiouAEIOU".contains(String.valueOf(c))) {
                    vowels.append(c); // Append vowel to the vowels StringBuilder
                } else {
                    consonants.append(c); // Append consonant to the consonants StringBuilder
                }
            }
        }

        // Format and return the result string containing the profile information
        return String.format("%d %s %d %s %d", lettersCount, vowels.toString(), vowelsCount, consonants.toString(), consonantsCount);
    }
}
