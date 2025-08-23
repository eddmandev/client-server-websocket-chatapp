package wit.zalicz.pap.clients;

import wit.zalicz.pap.ClientGUI;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args){
        // this approach makes GUI's more thread-safe
        SwingUtilities.invokeLater(() -> {
            ClientGUI gui = null;
            try {
                gui = new ClientGUI("mock");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gui.setVisible(true);
        });
    }
}
