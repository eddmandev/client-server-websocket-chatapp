package wit.zalicz.pap.clients;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import wit.zalicz.pap.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StompClient {
    private StompSession session;
    private final String username;

    public StompClient(String username) throws ExecutionException, InterruptedException {
        this.username = username;

        // transport is a method/protocol to transfer data between the client and server
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient wsStompClient = new WebSocketStompClient(sockJsClient);
        wsStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        var sessionHandler = new StompSessionHandler(username);
        String url = "ws://localhost:8080/ws"; //ws for websocket
        session = wsStompClient.connectAsync(url, sessionHandler).get();

    }

    public void sendMessage(Message message) {
        try {
            session.send("/app/messsage", message);
            System.out.println("Message sent: " + message.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
