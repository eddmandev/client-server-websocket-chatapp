package wit.zalicz.pap.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import wit.zalicz.pap.model.Message;
import wit.zalicz.pap.templates.SimpleMessagingTemplate;

public class WebsocketController {
    private final SimpleMessagingTemplate msgTemplate;

    public WebsocketController(SimpleMessagingTemplate msgTemplate){
        this.msgTemplate = msgTemplate;
    }

    @MessageMapping("/message")
    public void handleMessage(Message message){
        System.out.println("Got message from user: " + message.getUser() +": " + message.getMessage());
        msgTemplate.convertAndSend("/topic/messages", message);
        System.out.println("Sent message to /topic/messages from user: " + message.getUser() +": " + message.getMessage());
    }
}
