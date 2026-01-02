import javax.swing.SwingUtilities;

import Controller.Controller;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Controller());
    }
}
