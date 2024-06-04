/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Whiteboard;

/**
 *
 * @author SUBRATA LAHA
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class WhiteboardClient {
    private static final String HOST = "localhost";
    private static final int PORT = 8000;

    private JFrame frame;
    private JPanel contentPane;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private WhiteboardCanvas canvas;

    public WhiteboardClient() {
        try {
            socket = new Socket(HOST, PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        createGUI();
        receiveMessages();
    }

    private void createGUI() {
        frame = new JFrame("Whiteboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        frame.setContentPane(contentPane);

        canvas = new WhiteboardCanvas();
        contentPane.add(canvas, BorderLayout.CENTER);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        
        // NEW CODE
        frame.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_C) {
        			canvas.clearScreen();
                	output.println("clear");
        		}
        	}
        });
        // NEW CODE END

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvas.startDrawing(e.getPoint());
                output.println("start:" + e.getPoint().getX() + "," + e.getPoint().getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                canvas.stopDrawing();
                output.println("stop");
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                canvas.continueDrawing(e.getPoint());
                output.println("draw:" + e.getPoint().getX() + "," + e.getPoint().getY());
            }
        });
    }

    private void receiveMessages() {
        Thread t = new Thread(() -> {
            try {
                String message;
                while ((message = input.readLine()) != null) {
                    if (message.startsWith("start:")) {
                        String[] parts = message.substring(6).split(",");
                        double x = Double.parseDouble(parts[0]);
                        double y = Double.parseDouble(parts[1]);
                        canvas.startDrawing(new Point((int) x, (int) y));
                    } else if (message.equals("stop")) {
                        canvas.stopDrawing();
                    } else if (message.startsWith("draw:")) {
                        String[] parts = message.substring(5).split(",");
                        double x = Double.parseDouble(parts[0]);
                        double y = Double.parseDouble(parts[1]);
                        canvas.continueDrawing(new Point((int) x, (int) y));
                    }
                    // NEW CODE 
                    else if(message.equals("clear")) {
                    	canvas.clearScreen();
                    }
                    // NEW CODE END
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WhiteboardClient::new);
    }
}
