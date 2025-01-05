
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;
import javax.swing.JPanel;

class DrawingPanel extends JPanel {
    private Color currentColor = Color.BLACK;
    private String currentTool = "Freehand";
    private boolean isDotted = false;
    private boolean isFilled = false;
    private Point startPoint, endPoint;
    private BufferedImage canvasImage;
    private Graphics2D g2d;
    private Stack<BufferedImage> undoStack = new Stack<>();

    public DrawingPanel() {
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                saveUndoState();
            }

            public void mouseReleased(MouseEvent e) {
                endPoint = e.getPoint();
                drawShape();
                startPoint = null;
                endPoint = null;
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (currentTool.equals("Freehand")) {
                    g2d.setColor(currentColor);
                    g2d.drawLine(startPoint.x, startPoint.y, e.getX(), e.getY());
                    startPoint = e.getPoint();
                } else if (currentTool.equals("Eraser")) {
                    g2d.setStroke(new BasicStroke(20)); // Real-time eraser size
                    g2d.setColor(Color.WHITE);
                    g2d.drawLine(startPoint.x, startPoint.y, e.getX(), e.getY());
                    startPoint = e.getPoint();
                } else {
                    endPoint = e.getPoint();
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (canvasImage == null) {
            canvasImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            g2d = canvasImage.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        g.drawImage(canvasImage, 0, 0, null);

        if (startPoint != null && endPoint != null && !currentTool.equals("Freehand") && !currentTool.equals("Eraser")) {
            drawPreviewShape((Graphics2D) g);
        }
    }

    private void drawShape() {
        if (startPoint == null || endPoint == null) return;

        g2d.setColor(currentColor);
        if (isDotted) {
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        } else {
            g2d.setStroke(new BasicStroke(2));
        }

        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);

        switch (currentTool) {
            case "Rectangle":
                if (isFilled) {
                    g2d.fillRect(x, y, width, height);
                } else {
                    g2d.drawRect(x, y, width, height);
                }
                break;
            case "Oval":
                if (isFilled) {
                    g2d.fillOval(x, y, width, height);
                } else {
                    g2d.drawOval(x, y, width, height);
                }
                break;
            case "Line":
                g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                break;
        }
    }

    private void drawPreviewShape(Graphics2D g) {
        g.setColor(currentColor);
        if (isDotted) {
            g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        } else {
            g.setStroke(new BasicStroke(2));
        }

        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);

        switch (currentTool) {
            case "Rectangle":
                if (isFilled) {
                    g.fillRect(x, y, width, height);
                } else {
                    g.drawRect(x, y, width, height);
                }
                break;
            case "Oval":
                if (isFilled) {
                    g.fillOval(x, y, width, height);
                } else {
                    g.drawOval(x, y, width, height);
                }
                break;
            case "Line":
                g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                break;
        }
    }

    public void setColor(Color color) {
        currentColor = color;
    }

    public void setTool(String tool) {
        currentTool = tool;
    }

    public void setDotted(boolean dotted) {
        isDotted = dotted;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public void clear() {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        repaint();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            canvasImage = undoStack.pop();
            g2d = canvasImage.createGraphics();
            repaint();
        }
    }

    private void saveUndoState() {
        BufferedImage copy = new BufferedImage(canvasImage.getWidth(), canvasImage.getHeight(), canvasImage.getType());
        Graphics g = copy.getGraphics();
        g.drawImage(canvasImage, 0, 0, null);
        g.dispose();
        undoStack.push(copy);
    }
}