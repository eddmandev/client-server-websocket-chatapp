package wit.zalicz.pap.clients;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

public class StompClient {
    private StompSession session;
    private final String username;

    public StompClient(String username){
        this.username = username;

        // transport is a method/protocol to transfer data between the client and server
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient wsStompClient = new WebSocketStompClient(sockJsClient);
        wsStompClient.setMessageConverter(new MappingJackson2MessageConverter());

    }


}
