package com.madlibs.model;

/**
 * Abstract superclass; represents a single component within a mad libs script.
 * Created by Ran on 12/20/2015.
 */
public abstract class MadLibsComponent {

    /**
     * Gets the display value of this component within a template.
     * @return
     */
    public abstract String getTemplateDisplayValue();

    /**
     * Gets the display value of this component within a script.
     * @return
     */
    public abstract String getScriptDisplayValue();

}
