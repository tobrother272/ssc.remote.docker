/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api;

import java.util.prefs.Preferences;
/**
 * @author simplesolution.co
 */
public class ToolSetting {

    private Preferences pre;
    private String firefoxPath;
    private String host;
    private int port;
    private String profile;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
    
    
    
    public static ToolSetting instance;
    private ToolSetting() {
        pre = Preferences.userRoot().node("SSCRemoteTool");
    }
    public static ToolSetting getInstance() {
        if (instance == null) {
            instance = new ToolSetting();
        }
        return instance;
    }

}
