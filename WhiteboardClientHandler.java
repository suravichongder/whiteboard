/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Whiteboard;

/**
 *
 * @author SUBRATA LAHA
 */

import java.io.*;
import java.net.*;

public class WhiteboardClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private WhiteboardServer server;

    public WhiteboardClientHandler(Socket socket, WhiteboardServer server) {
        this.clientSocket = socket;
        this.server = server;
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = input.readLine()) != null) {
                server.broadcast(message);
            }
        } catch (IOException e) {
            server.removeClient(this);
            e.printStackTrace();
        }
    }
}

