package wit.zalicz.pap.clients;

import wit.zalicz.pap.model.Message;

import java.util.ArrayList;

public interface MessageListener {
    void onMessageReceived(Message message);
    void onActiveUsersUpdated(ArrayList<String> users);
}
