package ue7.common;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

public class PosDecoder implements Decoder.Text<Pos> {
    private static final Gson GSON = new Gson();

    @Override
    public Pos decode(String s) throws DecodeException {
        return GSON.fromJson(s, Pos.class);
    }

    @Override
    public boolean willDecode(String s) {
        try {
            GSON.fromJson(s, Pos.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }    }
}
