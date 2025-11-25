package com.actor;

import java.util.HashMap;
import java.util.Map;

import org.json.*;

import netscape.javascript.JSObject;

public abstract class ActorClass {
    private int id;

    public ActorClass(int id) {
        this.id=id;
    }

    private JSONObject convertMapToJSON(Map<String, Object> message){
        return new JSObject(message);
    }

    private JSObject createPackage(int ActorID, Map<String, Object> message){
        Map<String, Object> pck = new HashMap<>();
        pck.put("ActorID", ActorID);
        pck.putAll(convertMapToJSON(message));
        return new JSObject();
    }

    public void send(String url, int ActorID, String message){
                
    }
}