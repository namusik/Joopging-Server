package com.project.joopging.util.coolsms;

import com.google.gson.JsonObject;

public class Message {

    private JsonObject message = new JsonObject();


    public Message(String to, String from, String text) {
        this.message.addProperty("to",to);
        this.message.addProperty("from",from);
        this.message.addProperty("text",text);
    }

    public JsonObject toJson() {
        return this.message;
    }

}
