/**
 AUTHOR: Lacanilao, Marvin Patrick D.
 ACITIVITY: Prelim Laboratory Exercise 3
 CLASS CODE: 9329 - CS 222L Computer Programming 3
 -----------------------------------------------------------------------------------------------------------------
 SAMPLE RUN:
 Server listening on port 5420...
 Client connected: /127.0.0.1

 Process finished with exit code 130
 */

package pexer3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The PreExercise3Server class implements a simple server that receives XML data
 * containing mathematical expressions, processes the expressions, and sends the
 * results back to the client.
 */
public class PreExercise3Server {

    /**
     * The main method of the server that listens for incoming client connections,
     * processes XML data from clients, and sends back the calculated results.
     *
     * @param args The command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Create a server socket listening on port 5420
            ServerSocket serverSocket = new ServerSocket(5420);
            System.out.println("Server listening on port 5420...");

            // Main server loop to accept client connections
            while (true) {
                // Accept incoming client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Create BufferedReader and BufferedWriter for communication with the client
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                // Read XML data from the client until "bye" is received
                StringBuilder xmlData = new StringBuilder();
                String line;
                while (!(line = reader.readLine()).equals("bye")) {
                    xmlData.append(line).append("\n");
                }

                // Process XML data and send responses back to the client
                String responses = processXMLData(xmlData.toString());
                writer.write(responses);
                writer.flush();

                // Close resources for the current client connection
                reader.close();
                writer.close();
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes XML data containing mathematical expressions and calculates the results.
     *
     * @param xmlData The XML data received from the client.
     * @return A string containing the calculated results or error messages.
     */
    private static String processXMLData(String xmlData) {
        StringBuilder result = new StringBuilder();

        try {
            // Parse XML data using DocumentBuilder and DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlData.getBytes()));

            // Extract expressions from the XML document and calculate results
            NodeList expressionList = document.getElementsByTagName("expression");
            for (int i = 0; i < expressionList.getLength(); i++) {
                Element expressionElement = (Element) expressionList.item(i);

                String operand1 = expressionElement.getElementsByTagName("operand1").item(0).getTextContent();
                String operator = expressionElement.getElementsByTagName("operator").item(0).getTextContent();
                String operand2 = expressionElement.getElementsByTagName("operand2").item(0).getTextContent();

                try {
                    // Attempt to calculate the result of the expression
                    double resultValue = calculateResult(Double.parseDouble(operand1), operator, Double.parseDouble(operand2));
                    result.append(operand1).append(" ").append(operator).append(" ").append(operand2)
                            .append(" = ").append(resultValue).append("\n");
                } catch (NumberFormatException e) {
                    // Handle invalid operand format
                    result.append(operand1).append(" ").append(operator).append(" ").append(operand2)
                            .append(" = Invalid expression: Invalid operand '").append(operand1)
                            .append("' or '").append(operand2).append("'\n");
                } catch (IllegalArgumentException e) {
                    // Handle invalid operator or other errors
                    result.append(operand1).append(" ").append(operator).append(" ").append(operand2)
                            .append(" = Invalid expression: ").append(e.getMessage()).append("\n");
                }
            }
        } catch (Exception e) {
            // Handle general exceptions
            e.printStackTrace();
            result.append("Invalid expression: ").append(e.getMessage()).append("\n");
        }

        return result.toString();
    }

    /**
     * Calculates the result of a mathematical expression based on the given operator.
     *
     * @param operand1 The first operand of the expression.
     * @param operator The operator of the expression.
     * @param operand2 The second operand of the expression.
     * @return The result of the expression.
     * @throws IllegalArgumentException If the operator is invalid.
     */
    private static double calculateResult(double operand1, String operator, double operand2) {
        // Implement calculation logic for different operators
        switch (operator) {
            case "^":
                return Math.pow(operand1, operand2);
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            case "%":
                return operand1 % operand2;
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}