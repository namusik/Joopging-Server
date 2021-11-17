package com.project.joopging.util.coolsms;

import com.google.gson.JsonObject;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class GroupModel {
    private ArrayList<JsonObject> log;
    private JsonObject agent;
    private JsonObject count;
    private String accountId;
    private String apiVersion;
    private String groupId;
    private String dateCreated;
    private String dateUpdated;
    private String _id;
    private String status;
}
