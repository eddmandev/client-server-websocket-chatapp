package wit.zalicz.pap.clients;

import com.fasterxml.jackson.databind.type.ArrayType;
import wit.zalicz.pap.model.Message;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StompSessionHandler extends StompSessionHandlerAdapter {
    private final String username;
    private MessageListener listener;
    public StompSessionHandler(MessageListener listener, String username){
        this.username = username;
        this.listener = listener;
    }
    @Override
    public void afterConnected(StompSession session, StompHeaders headers) {
        // connecting to the websocket
        session.send("/app/connect", username);
        System.out.println("Connection started");
        session.subscribe("/topic/messages", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            //handling incoming data to the websocket server
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    if (payload instanceof Message message) {
                        listener.onMessageReceived(message);
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

        session.subscribe("/topic/users", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return new ArrayList<String>().getClass();
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    if (payload instanceof ArrayList){
                        ArrayList<String> activeUsers = (ArrayList<String>) payload;
                        listener.onActiveUsersUpdated(activeUsers);
                        System.out.println("updated active users");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        exception.printStackTrace();
    }
}
