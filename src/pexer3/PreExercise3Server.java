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

public class PreExercise3Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5420);
            System.out.println("Server listening on port 5420...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                StringBuilder xmlData = new StringBuilder();
                String line;
                while (!(line = reader.readLine()).equals("bye")) {
                    xmlData.append(line).append("\n");
                }

                String responses = processXMLData(xmlData.toString());
                writer.write(responses);
                writer.flush();

                reader.close();
                writer.close();
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processXMLData(String xmlData) {
        StringBuilder result = new StringBuilder();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlData.getBytes()));

            NodeList expressionList = document.getElementsByTagName("expression");
            for (int i = 0; i < expressionList.getLength(); i++) {
                Element expressionElement = (Element) expressionList.item(i);

                String operand1 = expressionElement.getElementsByTagName("operand1").item(0).getTextContent();
                String operator = expressionElement.getElementsByTagName("operator").item(0).getTextContent();
                String operand2 = expressionElement.getElementsByTagName("operand2").item(0).getTextContent();

                try {
                    double resultValue = calculateResult(Double.parseDouble(operand1), operator, Double.parseDouble(operand2));
                    result.append(operand1).append(" ").append(operator).append(" ").append(operand2)
                            .append(" = ").append(resultValue).append("\n");
                } catch (NumberFormatException e) {
                    result.append(operand1).append(" ").append(operator).append(" ").append(operand2)
                            .append(" = Invalid expression: Invalid operand '").append(operand1)
                            .append("' or '").append(operand2).append("'\n");
                } catch (IllegalArgumentException e) {
                    result.append(operand1).append(" ").append(operator).append(" ").append(operand2)
                            .append(" = Invalid expression: ").append(e.getMessage()).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.append("Invalid expression: ").append(e.getMessage()).append("\n");
        }

        return result.toString();
    }

    private static double calculateResult(double operand1, String operator, double operand2) {
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