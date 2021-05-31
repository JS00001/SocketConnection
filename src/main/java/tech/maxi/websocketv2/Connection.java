package tech.maxi.websocketv2;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Connection extends WebSocketClient {

    public Connection(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("[WebSocket] Connection Opened");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("[WebSocket] Message Received: " + message);
        BukkitEvents.sendCommand(message);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("[WebSocket] Connection Closed. Reconnecting...");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("[WebSocket] Error Occurred");
        System.out.println(e);
    }
}
