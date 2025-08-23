package wit.zalicz.pap.clients;

import wit.zalicz.pap.ClientGUI;

import javax.swing.*;

public class App {
    public static void main(String[] args){
        // this approach makes GUI's more thread-safe
        SwingUtilities.invokeLater(() -> {
            ClientGUI gui = new ClientGUI("mock");
            gui.setVisible(true);
        });
    }
}
