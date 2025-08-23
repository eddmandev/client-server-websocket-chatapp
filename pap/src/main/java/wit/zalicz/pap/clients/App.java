package wit.zalicz.pap.clients;

import io.micrometer.common.util.StringUtils;
import wit.zalicz.pap.ClientGUI;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args){
        // this approach makes GUI's more thread-safe
        SwingUtilities.invokeLater(() -> {
            String username = JOptionPane.showInputDialog(
                    null,
                    "Wpisz nazwe uzytkownika (max. 16 znaki): ",
                    JOptionPane.QUESTION_MESSAGE);
            if (StringUtils.isEmpty(username) || username.length()>16){
                JOptionPane.showMessageDialog(null,
                        "Niepoprawny format uzytkownika",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            ClientGUI gui = null;
            try {
                gui = new ClientGUI(username);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gui.setVisible(true);
        });
    }
}
