import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DiagramsFrame {
    JFrame frame;
    DiagramPanel diagram;
    private int startX = 300;
    private int startY = 400;
    private int startY2;
    ArrayList<Double> x, y, x1;

    private int ScaleX = 20;
    private int ScaleY = 10;

    public void drawFrame(ArrayList<Double> x, ArrayList<Double> y, ArrayList<Double> x1) {
        this.x = x;
        this.y = y;
        this.x1 = x1;

        frame = new JFrame();
        diagram = new DiagramPanel();

        frame.getContentPane().add(BorderLayout.CENTER, diagram);
        frame.setVisible(true);
        frame.setSize(1200, 1200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    class DiagramPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            startY2 = getHeight() - startY;

            grid(g);
            diagramAxes(g);
            graph_XY(g);
            graph_XY1(g);
            label(g);
        }

        private void diagramAxes(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawLine(startX, 0, startX, getHeight());
            g.drawLine(0, startY2, getWidth(), startY2);

        }

        private void graph_XY(Graphics g) {
            g.setColor(Color.BLACK);
            System.out.println(x.toString());
            System.out.println(y.toString());
            double aX, aY;
            double bX, bY;
            aX = x.get(0) * ScaleX + startX;
            aY = startY2 - y.get(0) * ScaleY;

            for (int i = 1; i < x.size(); i++) {

                bX = (x.get(i) * ScaleX + startX);
                bY = (startY2 - y.get(i) * ScaleY);
                g.drawLine((int) aX, (int) aY, (int) bX, (int) bY);
                aX = bX;
                aY = bY;
            }
        }

        private void graph_XY1(Graphics g) {
            g.setColor(Color.BLACK);
            System.out.println(x.toString());
            System.out.println(y.toString());
            double aX, aY;
            double bX, bY;
            aX = x1.get(0) * ScaleX + startX;
            aY = startY2 - y.get(0) * ScaleY;

            for (int i = 1; i < x.size(); i++) {

                bX = (x1.get(i) * ScaleX + startX);
                bY = (startY2 - y.get(i) * ScaleY);
                g.drawLine((int) aX, (int) aY, (int) bX, (int) bY);
                aX = bX;
                aY = bY;
            }
        }

        //field
        private void grid(Graphics g) {
            for (int i = 0; i < getHeight(); i += 20) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0, i, getWidth(), i);
            }
            for (int i = 0; i < getWidth(); i += 20) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(i, 0, i, getHeight());
            }
        }

        private void label(Graphics g) {
            int k = 0;
            for (int i = 0; i < getWidth(); i += 40) {
                g.drawString(String.valueOf(k), (int) (i + startX), (int) (startY2 + 10));
                k = k + 1;
            }
            for (int i = 0; i < getHeight(); i += 40) {
                double j = i;
                double tempScale = ScaleY;
                g.drawString(String.valueOf(j / tempScale), startX, startY2 - i);
            }
        }
    }
}
