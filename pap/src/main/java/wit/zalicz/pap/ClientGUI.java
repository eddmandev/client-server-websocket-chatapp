package wit.zalicz.pap;

import wit.zalicz.pap.clients.StompClient;
import wit.zalicz.pap.model.Message;

import java.util.concurrent.ExecutionException;

public class ClientGUI {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var client = new StompClient("Edgar");
        client.sendMessage(new Message("meowmeow", "wassup"));;
    }
}
