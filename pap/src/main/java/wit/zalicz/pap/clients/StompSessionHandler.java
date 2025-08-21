package wit.zalicz.pap.clients;

import wit.zalicz.pap.model.Message;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class StompSessionHandler extends StompSessionHandlerAdapter {
    private String username;
    public StompSessionHandler(String username){
        this.username = username;
    }
    @Override
    public void afterConnected(StompSession session, StompHeaders headers) {
        System.out.println("Connection started");
        session.subscribe("/topic/messages", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    if (payload instanceof Message) {
                        Message message = (Message) payload;
                        System.out.println("Succesfully received messsage from: " + message.getUser() + ":  " + message);
                    } else {
                        System.out.println("Unexpected payload: " + payload.getClass());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Client subscribed to /topic/messages");
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        exception.printStackTrace();
    }
}
