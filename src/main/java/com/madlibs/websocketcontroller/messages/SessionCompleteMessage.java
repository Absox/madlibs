package com.madlibs.websocketcontroller.messages;

import com.madlibs.model.MadLibsSession;

/**
 * Created by Ran on 1/2/2016.
 */
public class SessionCompleteMessage extends WebsocketMessage {

    public SessionCompleteMessage(MadLibsSession session) {
        content.addProperty("type", "sessionComplete");
    }
}
