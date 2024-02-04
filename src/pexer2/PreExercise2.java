/**
 AUTHOR: Lacanilao, Marvin Patrick D.
 ACITIVITY: Prelim Laboratory Exercise 2
 CLASS CODE: 9329 - CS 222L Computer Programming 3
 -----------------------------------------------------------------------------------------------------------------
 SAMPLE RUN: [Tested in multiple command prompt windows simultaneously]
 What is your name?
 Patrick
 What is your age?
 20
 Patrick, you may exercise your right to vote!
 Thank you and good day.


 Connection to host lost.

 What is your name?
 Robin
 What is your age?
 13
 Robin, you are still too young to vote!
 Thank you and good day.


 Connection to host lost.

 What is your name?
 Jihyo
 What is your age?
 hello
 Please enter a valid age.
 What is your age?
 0
 Please enter a valid age.
 What is your age?
 +
 Please enter a valid age.
 What is your age?
 -324
 Please enter a valid age.
 What is your age?
 21
 Jihyo, you may exercise your right to vote!
 Thank you and good day.


 Connection to host lost.

 SAMPLE RUN in IntelliJ: (Program terminates when manually stopped)
 Server started. Waiting for clients...

 Process finished with exit code 130
 */

package pexer2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * PreExercise2 class represents a server program that accepts multiple clients
 * and processes their requests.
 */
public class PreExercise2 {
    // Default port for the server
    private static final int port = 2000;
    // Maximum number of clients the server can handle concurrently
    private static final int maxClients = 10;

    /**
     * The main method creates an ExecutorService with a fixed thread pool size
     * and listens for client connections continuously.
     *
     * @param args The command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a fixed-size thread pool for handling client connections
        ExecutorService executorService = Executors.newFixedThreadPool(maxClients);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for clients...");

            // Continuously accept client connections
            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Submit client connection to the thread pool for processing
                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shutdown the thread pool when the server terminates
            executorService.shutdown();
        }
    }

    /**
     * ClientHandler class represents a task for handling client connections.
     * It implements the Runnable interface to be executed by a thread.
     */
    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        /**
         * Constructs a new ClientHandler object with the given client socket.
         *
         * @param clientSocket The client socket for communication
         */
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        /**
         * The run method is executed when the thread starts.
         * It handles the communication with the client.
         */
        @Override
        public void run() {
            try (
                    BufferedReader streamRdr = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter streamWtr = new PrintWriter(
                            clientSocket.getOutputStream(), true);
            ) {
                // Server sends a message to the client
                streamWtr.println("What is your name? ");
                // Server accepts input from the client
                String name = streamRdr.readLine();
                int age;

                // Validate the age input from the client
                while (true) {
                    streamWtr.println("What is your age? ");
                    try {
                        age = Integer.parseInt(streamRdr.readLine());
                        if (age <= 0) {
                            throw new NumberFormatException();
                        } else {
                            break;
                        }
                    } catch (NumberFormatException nfe) {
                        // Prompt the client to enter a valid age
                        streamWtr.println("Please enter a valid age.");
                        continue;
                    }
                }

                // Send appropriate response based on the client's age
                if (age >= 18) {
                    streamWtr.println(name + ", you may exercise your right to vote!");
                } else {
                    streamWtr.println(name + ", you are still too young to vote!");
                }
                streamWtr.println("Thank you and good day.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}