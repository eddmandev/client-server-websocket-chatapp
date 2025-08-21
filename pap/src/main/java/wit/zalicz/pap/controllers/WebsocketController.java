package wit.zalicz.pap.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import wit.zalicz.pap.model.Message;
import wit.zalicz.pap.services.WebSocketSessionManager;
import wit.zalicz.pap.templates.SimpleMessagingTemplate;

public class WebsocketController {
    private final SimpleMessagingTemplate msgTemplate;
    private final WebSocketSessionManager webSocketSessionManager;

    public WebsocketController(SimpleMessagingTemplate msgTemplate, WebSocketSessionManager webSocketSessionManager){
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
}
