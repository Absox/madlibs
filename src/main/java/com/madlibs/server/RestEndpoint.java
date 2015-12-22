package com.madlibs.server;

/**
 * Rest endpoint controller interface.
 * Created by Ran on 12/22/2015.
 */
public interface RestEndpoint {

    /**
     * Gets response code.
     * @return Response code.
     */
    public int getResponseCode();

    /**
     * Gets response body.
     * @return Response body.
     */
    public String getResponseBody();
}
