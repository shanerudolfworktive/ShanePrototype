package com.prototype.shane.shaneprototype;

import android.app.Application;

import com.prototype.shane.shaneprototype.util.Const;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by shane on 8/11/16.
 */
public class ShanePrototypeApplication extends Application {
    private Socket mSocket;{
        try {
            mSocket = IO.socket(Const.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
