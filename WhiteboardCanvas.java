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
import java.awt.*;

public class WhiteboardCanvas extends JPanel {
    private boolean drawing;
    private Point startPoint;

    public WhiteboardCanvas() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
    }

    public void startDrawing(Point startPoint) {
        this.startPoint = startPoint;
        drawing = true;
    }

    public void stopDrawing() {
        drawing = false;
    }

    public void continueDrawing(Point endPoint) {
        if (drawing) {
            Graphics2D g2d = (Graphics2D) getGraphics();
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            startPoint = endPoint;
        }
    }
    
    // NEW CODE
    public void clearScreen() {
    	Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 800, 600);
    }
    // NEW CODE END
}
