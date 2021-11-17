package com.project.joopging.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.joopging.model.Crew;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import com.project.joopging.repository.PostRepository;
import com.project.joopging.util.coolsms.APIInit;
import com.project.joopging.util.coolsms.GroupModel;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final PostRepository postRepository;

    public void sendSms(String userNumber, String postTile) {

        JsonObject params = new JsonObject();
        JsonArray messages = new JsonArray();

        JsonObject msg = new JsonObject();
        JsonArray toList = new JsonArray();

        toList.add(userNumber);
        msg.add("to", toList);
        msg.addProperty("from", "01099403102");
        msg.addProperty("text",
                "안녕하세요 줍깅입니다. 신청하신"+ "[ " + postTile + " ]" +"모임의 모임날짜가 하루전입니다.");
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
    //스케쥴러 새벽 3시
    //러닝데이트 1일 전에 알럿문자메세지
//    @Scheduled(cron = "0 0 3 * * *")
    @Transactional(readOnly = true)
    public void sendRunningDateAlertToCrew() {
        List<Post> postList = postRepository.findAll();
        for (Post post : postList) {
            String nowPlusOneDay = getLocalDateTimeNowToString(LocalDateTime.now().plusDays(1));
            String runningDate = getRunningDateToString(post);
          if (nowPlusOneDay.equals(runningDate)) {
              List<Crew> crewList = post.getCrew();
              String postTitle = post.getTitle();
              for (Crew crew : crewList) {
                  User user = crew.getUserJoin();
                  String userNumber = user.getNumber();
                  sendSms(userNumber, postTitle);
              }
          }
        }
    }

    private String getRunningDateToString(Post post) {
        LocalDateTime runningDate = post.getRunningDate();
        String day = runningDate.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(runningDate);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        return ts[0] + " ("+day+") ";
    }

    private String getLocalDateTimeNowToString(LocalDateTime localDateTime) {
        String day = localDateTime.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(localDateTime);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        return ts[0] + " ("+day+") ";
    }
}

