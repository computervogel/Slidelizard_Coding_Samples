package ue7.common;

import com.google.gson.Gson;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class PosEncoder implements Encoder.Text<Pos> {
    private static final Gson GSON = new Gson();

    @Override
    public String encode(Pos pos) throws EncodeException {
        return GSON.toJson(pos);
    }
}
