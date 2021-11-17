package com.project.joopging.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.joopging.util.coolsms.APIInit;
import com.project.joopging.util.coolsms.GroupModel;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SmsService {

    public void sendSms() {
        JsonObject params = new JsonObject();
        JsonArray messages = new JsonArray();

        JsonObject msg = new JsonObject();
        JsonArray toList = new JsonArray();

        toList.add("01099403102");
        msg.add("to", toList);
        msg.addProperty("from", "01099403102");
        msg.addProperty("text",
                "안녕하세요 줍깅입니다. 오늘 참여하실[같이 줍깅해요!]모임의 모임날짜가 3일 남으셨습니다.");
        messages.add(msg);

        params.add("messages", messages);

        Call<GroupModel> api = APIInit.getAPI().sendMessages(APIInit.getHeaders(), params);
        api.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                // 성공 시 200이 출력됩니다.
                if (response.isSuccessful()) {
                    System.out.println("statusCode : " + response.code());
                    GroupModel body = response.body();
                    System.out.println("groupId : " + body.getGroupId());
                    System.out.println("status: " + body.getStatus());
                    System.out.println("count: " + body.getCount().toString());
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GroupModel> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

}

