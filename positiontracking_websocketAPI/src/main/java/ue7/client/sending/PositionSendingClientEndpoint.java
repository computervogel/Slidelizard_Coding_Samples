package ue7.client.sending;

import jakarta.websocket.*;
import ue7.common.Pos;
import ue7.common.PosDecoder;
import ue7.common.PosEncoder;

import java.io.IOException;

@ClientEndpoint(encoders = PosEncoder.class, decoders = PosDecoder.class)
public class PositionSendingClientEndpoint {
//TODO
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to server");
    }

    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) throws IOException {
        System.out.println("Session closed: " + closeReason.getReasonPhrase());
        session.close();
    }

    public void sendPosition(Pos pos) {
        session.getAsyncRemote().sendObject(pos);
    }

    public boolean isConnected() {
        return session != null && session.isOpen();
    }

    public Session getSession() {
        return session;
    }
}
