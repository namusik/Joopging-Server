package com.project.joopging.util.coolsms;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MessageModel {

    private String groupId;
    private String messageId;
    private String statusCode;
    private String statusMessage;
    private String to;
    private String type;
    private String from;
    private String customFields;
    private String country;
    private String accountId;

}
