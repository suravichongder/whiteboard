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
import java.util.*;

public class WhiteboardServer {
    private static final int PORT = 8000;
    private ArrayList<WhiteboardClientHandler> clients;

    public WhiteboardServer() {
        clients = new ArrayList<>();
    }

    public void start() {
        try { 
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Whiteboard server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                WhiteboardClientHandler clientHandler = new WhiteboardClientHandler(clientSocket, this);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for (WhiteboardClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void removeClient(WhiteboardClientHandler client) {
        clients.remove(client);
    }

    public static void main(String[] args) {
        WhiteboardServer server = new WhiteboardServer();
        server.start();
    }
}
