package org.example;
import java.net.*;
import java.io.*;

public class HttpServer {
    public static  void main(String[] args) throws IOException, IllegalAccessException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 8080.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;

        BufferedOutputStream dataOut = new BufferedOutputStream();

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibí: " + inputLine);
            if (!in.ready()) {break; }
        }

        String requestLine = in.readLine().substring(0);
        String[] tokens = requestLine.split(" ");
        String method = tokens[0];
        String uri = tokens[1];
        handleRequest(method,uri,out,dataOut);


    }
    private static void handleRequest(String method, String URI, PrintWriter out, BufferedOutputStream dataOut) throws IOException{

        System.out.println("Handling request: " + method + " " + URI);
        System.out.println("");

        if (method.equals("GET") && !URI.startsWith("/app") && !URI.startsWith("/Spring")) {
            handleGetRequest(URI, out, dataOut);
        } else {
            try {
                String responseBody = null;
                Integer contentLength = null;

                if (URI.startsWith("/app")) {
                    System.out.println("");

                    contentLength = responseBody.getBytes().length;
                } else if (URI.startsWith("/Spring")) {
                    System.out.println("");

                    contentLength = responseBody.getBytes().length;
                }

                out.print("HTTP/1.1 200 OK\r\n");
                out.print("Content-Type: application/json\r\n");
                out.print("Content-Length: " + contentLength + "\r\n");
                out.print("\r\n");
                out.print(responseBody);
                out.flush();
            } catch (Exception e) {
                out.print("HTTP/1.1 500 Internal Server Error\r\n");
                out.print("Content-Type: text/html\r\n");
                out.print("\r\n");
                out.print("<html><body><h1>Internal Server Error</h1></body></html>");
                out.flush();
                e.printStackTrace();
            }
        }
    }

    private static void handleGetRequest(String fileRequested, PrintWriter out, BufferedOutputStream dataOut) throws IOException {

    }
}