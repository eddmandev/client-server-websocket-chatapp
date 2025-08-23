package wit.zalicz.pap.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import wit.zalicz.pap.model.Message;
import wit.zalicz.pap.services.WebSocketSessionManager;

@Controller
public class WebsocketController {
    private final SimpMessagingTemplate msgTemplate;
    private final WebSocketSessionManager webSocketSessionManager;

    public WebsocketController(SimpMessagingTemplate msgTemplate, WebSocketSessionManager webSocketSessionManager){
        this.msgTemplate = msgTemplate;
        this.webSocketSessionManager = webSocketSessionManager;
    }

    @MessageMapping("/message")
    public void handleMessage(Message message){
        System.out.println("Got message from user: " + message.getUser() +": " + message.getMessage());
        msgTemplate.convertAndSend("/topic/messages", message);
        System.out.println("Sent message to /topic/messages from user: " + message.getUser() +": " + message.getMessage());
    }

    @MessageMapping("/connect")
    public void connectUser(String username){
        webSocketSessionManager.addUsername(username);
        webSocketSessionManager.broadcastActiveUsernames();
        System.out.println(username + " connected");
    }
    @MessageMapping("/disconnect")
    public void disconnectUser(String username){
        webSocketSessionManager.removeUsername(username);
        webSocketSessionManager.broadcastActiveUsernames();
        System.out.println(username + " disconnected");
    }

    @MessageMapping("/request-users")
    public void requestUsers(){
        webSocketSessionManager.broadcastActiveUsernames();
        System.out.println("requesting users");
    }
}
