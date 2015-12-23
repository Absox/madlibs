package com.madlibs.config;

/**
 * Configurations object for server.
 * Created by Ran on 12/22/2015.
 */
public class ServerConfigs {

    private int templateId;
    private int scriptId;

    /**
     * Initializes a server configs object.
     * @param templateId Template id counter.
     * @param scriptId Script id counter.
     */
    public ServerConfigs(int templateId, int scriptId) {
        this.templateId = templateId;
        this.scriptId = scriptId;
    }

    /**
     * Accessor for template id counter.
     * @return Template id counter.
     */
    public int getTemplateId() {
        return this.templateId;
    }

    /**
     * Returns, then increments template id counter.
     * @return Template id counter.
     */
    public int getNextTemplateId() {
        return this.templateId++;
    }

    /**
     * Accessor for script id counter.
     * @return Script id counter.
     */
    public int getScriptId() {
        return this.scriptId;
    }

    /**
     * Returns, then increments script id counter.
     * @return Script id counter.
     */
    public int getNextScriptId() {
        return this.scriptId++;
    }

}
