package wit.zalicz.pap.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WebSocketSessionManager {
    private final ArrayList<String> activeUsernames = new ArrayList<>();
    private final SimpMessagingTemplate msgTemplate;

    public WebSocketSessionManager(SimpMessagingTemplate msgTemplate){
        this.msgTemplate = msgTemplate;
    }

    public void addUsername(String username){
        activeUsernames.add(username);
    }

    public void removeUsername(String username){
        activeUsernames.remove(username);
    }

    public void broadcastActiveUsernames(){
        msgTemplate.convertAndSend("/topic/users", activeUsernames);
    }
}
