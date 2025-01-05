import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PaintBrushApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PaintBrushApp::new);
    }

    public PaintBrushApp() {
        JFrame frame = new JFrame("Simple Paint Brush");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        // Create the drawing area
        DrawingPanel drawingPanel = new DrawingPanel();

        // Create a toolbar
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout());

        // Add color buttons
        JButton redButton = new JButton("Red");
        redButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setColor(Color.RED);
            }
        });
        JButton greenButton = new JButton("Green");
        greenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setColor(Color.GREEN);
            }
        });
        JButton blueButton = new JButton("Blue");
        blueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setColor(Color.BLUE);
            }
        });

        // Add shape buttons
        JButton rectangleButton = new JButton("Rectangle");
        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setTool("Rectangle");
            }
        });
        JButton ovalButton = new JButton("Oval");
        ovalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setTool("Oval");
            }
        });
        JButton lineButton = new JButton("Line");
        lineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setTool("Line");
            }
        });

        // Add other buttons
        JButton freeHandButton = new JButton("Freehand");
        freeHandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setTool("Freehand");
            }
        });
        JButton eraserButton = new JButton("Eraser");
        eraserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setTool("Eraser");
                drawingPanel.setColor(Color.WHITE);
            }
        });
        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.clear();
            }
        });
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.undo();
            }
        });

        // Add checkboxes
        JCheckBox dottedCheckBox = new JCheckBox("Dotted");
        dottedCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setDotted(dottedCheckBox.isSelected());
            }
        });
        JCheckBox filledCheckBox = new JCheckBox("Filled");
        filledCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setFilled(filledCheckBox.isSelected());
            }
        });

        // Add controls to the toolbar
        toolbar.add(redButton);
        toolbar.add(greenButton);
        toolbar.add(blueButton);
        toolbar.add(rectangleButton);
        toolbar.add(ovalButton);
        toolbar.add(lineButton);
        toolbar.add(freeHandButton);
        toolbar.add(eraserButton);
        toolbar.add(dottedCheckBox);
        toolbar.add(filledCheckBox);
        toolbar.add(clearButton);
        toolbar.add(undoButton);

        // Add components to the frame
        frame.add(toolbar, BorderLayout.NORTH);
        frame.add(drawingPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}