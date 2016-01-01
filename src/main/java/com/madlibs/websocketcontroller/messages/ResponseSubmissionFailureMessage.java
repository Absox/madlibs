package com.madlibs.websocketcontroller.messages;

/**
 * Created by Ran on 12/31/2015.
 */
public class ResponseSubmissionFailureMessage extends WebsocketMessage {

    public ResponseSubmissionFailureMessage(String reason) {
        content.addProperty("type", "responseSubmitFailure");
        content.addProperty("why", reason);
    }
}
