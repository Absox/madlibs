package com.madlibs.websocketcontroller.messages;

import com.google.gson.JsonObject;
import com.madlibs.model.MadLibsScript;

/**
 * Session complete message.
 * Created by Ran on 1/2/2016.
 */
public class SessionCompleteMessage extends WebsocketMessage {

    public SessionCompleteMessage(MadLibsScript script) {
        this.content = script.getScriptJson();
        this.content.addProperty("type", "sessionComplete");
    }
}
