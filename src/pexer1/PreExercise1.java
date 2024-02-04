/**
 AUTHOR: Lacanilao, Marvin Patrick D.
 ACITIVITY: Prelim Laboratory Exercise 1
 CLASS CODE: 9329 - CS 222L Computer Programming 3
 -----------------------------------------------------------------------------------------------------------------
 SAMPLE RUN:
 Host 1 - Type IP address/Hostname: yahoo.com
 Number of Hosts/IPs: 12

 Host Name           IP Address
 yahoo.com           74.6.143.25
 yahoo.com           98.137.11.164
 yahoo.com           98.137.11.163
 yahoo.com           74.6.231.21
 yahoo.com           74.6.231.20
 yahoo.com           74.6.143.26
 yahoo.com           2001:4998:124:1507:0:0:0:f001
 yahoo.com           2001:4998:24:120d:0:0:1:1
 yahoo.com           2001:4998:24:120d:0:0:1:0
 yahoo.com           2001:4998:44:3507:0:0:0:8000
 yahoo.com           2001:4998:44:3507:0:0:0:8001
 yahoo.com           2001:4998:124:1507:0:0:0:f000
 Search another? [Y/N]: Y

 Host 2 - Type IP address/Hostname: slu.edu.ph
 Number of Hosts/IPs: 1

 Host Name            IP Address
 slu.edu.ph          122.53.179.133
 Search another? [Y/N]: n
 Program exited.

 Process finished with exit code 0

 SAMPLE RUN (Unknown Host):

 Host 1 - Type IP address/Hostname: djashdjah.com
 Unknown Host: No such host is known (djashdjah.com)
 Program exited.

 Process finished with exit code 0

 */

package pexer1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * PreExercise1 class performs host/IP address lookups and displays the results.
 */
public class PreExercise1 {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * The main method to execute the PreExercise1 program.
     */
    public static void main(String[] args) {
        PreExercise1 preExercise1 = new PreExercise1();
        try {
            preExercise1.run();
        } catch (IOException e) {
            System.out.println("Error in main: " + e.getMessage());
        }
    }

    /**
     * Runs the PreExercise1 program, performing host/IP address lookups and displaying the results.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void run() throws IOException {
        int hostNum = 1;
        char searchCondition;

        do {
            System.out.print("\nHost " + hostNum + " - Type IP address/Hostname: ");
            String host = reader.readLine(); // Read user input for host/IP address

            try {
                // Perform lookup for the entered host/IP address
                InetAddress[] searchAddresses = InetAddress.getAllByName(host);
                int numberOfHostIP = searchAddresses.length;

                // Display the number of found hosts/IP addresses
                System.out.println("\tNumber of Hosts/IPs: " + numberOfHostIP);
                System.out.printf("\t%-20s%-15s%n", "Host Name", "IP Address");

                // Display each host - IP address pair
                for (InetAddress address : searchAddresses) {
                    System.out.printf("%-20s%-15s%n", address.getHostName(), address.getHostAddress());
                }

                // Ask user if they want to perform another search
                System.out.print("Search another? [Y/N]: ");
                String input = reader.readLine().trim();
                searchCondition = input.isEmpty() ? 'N' : input.charAt(0);
            } catch (UnknownHostException e) {
                // Handle UnknownHostException
                System.out.println("Unknown Host: " + e.getMessage());
                searchCondition = 'N';  // Exit in case of UnknownHostException
            } catch (IOException e) {
                // Handle IOException
                System.out.println("IO Exception: " + e.getMessage());
                searchCondition = 'N';  // Exit in case of IOException
            }

            hostNum++;
        } while (searchCondition == 'Y' || searchCondition == 'y');

        System.out.println("Program exited.");
    }
}