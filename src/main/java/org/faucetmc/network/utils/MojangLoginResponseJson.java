package org.faucetmc.network.utils;

import com.google.gson.JsonArray;

public class MojangLoginResponseJson {

    private String id;
    private String name;
    private JsonArray properties;

    public MojangLoginResponseJson(String id, String name, JsonArray properties) {
        this.id = id;
        this.name = name;
        this.properties = properties;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public JsonArray getProperties() {
        return properties;
    }
}
