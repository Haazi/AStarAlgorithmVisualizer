import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // An instance of a frame
        JFrame window = new JFrame();

        // To exit the frame
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("A* PathFinding Visualizer");
        window.add(new Panel());

        window.pack();
        window.setLocationRelativeTo(null);

        // To make frame visible
        window.setVisible(true);
    }
}

