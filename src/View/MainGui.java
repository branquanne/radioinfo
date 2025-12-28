package View;

import javax.swing.*;

public class MainGui {
    TableGui tg = new TableGui();

    public MainGui() {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGui::showGUI);

    }

    private static void showGUI() {
        JFrame frame = new JFrame("Radio Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
    }

}
