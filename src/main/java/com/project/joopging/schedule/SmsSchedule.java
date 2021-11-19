package com.project.joopging.schedule;

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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class SmsSchedule {

    private final PostRepository postRepository;

    public void sendSms(JsonArray toList, String message) {
        JsonObject params = new JsonObject();
        JsonArray messages = new JsonArray();
        JsonObject msg = new JsonObject();
//        JsonArray toList = new JsonArray();
//        toList.add(userNumber);
        msg.add("to", toList);
        msg.addProperty("from", "01099403102");
        msg.addProperty("text", message);
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

    //스케쥴러 매일 9시
    //러닝데이트 1일 전에 알럿문자메세지
    @Scheduled(cron = "0 0 9 * * *")
    @Transactional(readOnly = true)
    public void sendRunningDateAlertToCrew() {
        List<Post> postList = postRepository.findAll();
        JsonArray toList = new JsonArray();
        for (Post post : postList) {
            String nowPlusOneDay = getLocalDateTimeNowToStringDay(LocalDateTime.now().plusDays(1));
            String runningDate = getRunningDateToStringDay(post);
          if (nowPlusOneDay.equals(runningDate)) {
              List<Crew> crewList = post.getCrew();
              String postTitle = post.getTitle();
              String message = "안녕하세요 줍깅입니다. " +
                      "신청하신"+ " [" + postTitle + "] " +"모임의 모임날짜가 하루전입니다.";
              for (Crew crew : crewList) {
                  User user = crew.getUserJoin();
                  String userNumber = user.getNumber();
                  toList.add(userNumber);
              }
              sendSms(toList, message);
          }
        }
    }

    //스케쥴러 1분마다 체크
    //CrewHead 에게 출석체크 url 알럿문자메세지
    @Scheduled(cron ="0 0/1 * * *")
    public void sendAttendanceCheckAlertToCrewHead() {
        List<Post> postList = postRepository.findAll();
        JsonArray toList = new JsonArray();
        for (Post post : postList) {
            String runningDate = getRunningDateToStringDay(post);
            String now = getLocalDateTimeNowToStringMinute(LocalDateTime.now());
            if (now.equals(runningDate)) {
                String number = post.getWriter().getNumber();
                String postTitle = post.getTitle();
                String message = "안녕하세요 줍깅입니다." +" ["+ postTitle +"] "+ "모임의 모임원들은" +
                        "다 모이셨나요? 출석체크를 해주세요!" +
                        "출석체크는 앞으로 유저간의 신뢰도를 측정하는데 도움이 됩니다!" +
                        "출석체크 url";
                toList.add(number);
                sendSms(toList,message);
            }

        }
    }
    //스케쥴러 1분마다 체크
    //Crew 전체에게 후기 쓰고 설문조사 유도 알럿문자메세지
    @Scheduled(cron = "0 0/1 * * *")
    public void sendInduceReviewAlertToCrew() {
        List<Post> postList = postRepository.findAll();
        JsonArray toList = new JsonArray();
        for (Post post : postList) {
            String runningDate = getRunningDateToStringMinute(post);
            String now = getLocalDateTimeNowToStringMinute(LocalDateTime.now());
            if (now.equals(runningDate)) {
                List<Crew> crewList = post.getCrew();
                String postTitle = post.getTitle();
                String message = "안녕하세요 줍깅입니다. 이번" +" ["+ postTitle + "] " + "모임은 어떠셨나요?" +
                        "후기를 작성하여 다른 사용자에게 플로깅이 얼마나 좋은지 알려주세요!" +
                        "[이벤트] 이벤트 기간 중 설문조사를 작성하시면 소정의 기프티콘을 드려요! " +
                        "설문조사 url";
                for (Crew crew : crewList) {
                    User user = crew.getUserJoin();
                    String userNumber = user.getNumber();
                    toList.add(userNumber);
                }
                sendSms(toList,message);
            }
        }
    }




    //요일까지만 비교 (runningDate 1일전 알럿용)
    private String getRunningDateToStringDay(Post post) {
        LocalDateTime runningDate = post.getRunningDate();
        String day = runningDate.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(runningDate);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        return ts[0] + " ("+ day +") ";
    }

    //요일까지만 비교 (runningDate 1일전 알럿용)
    private String getLocalDateTimeNowToStringDay(LocalDateTime localDateTime) {
        String day = localDateTime.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(localDateTime);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        return ts[0] + " (" + day + ") ";
    }
    // 분까지 비교 (출석 체크 알럿용)
    private String getRunningDateToStringMinute(Post post) {
        LocalDateTime runningDate = post.getRunningDate();
        String day = runningDate.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(runningDate);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        String[] ms = ts[1].split(":");
        return ts[0] + " ("+ day +") " + ms[0] + ms[1];
    }

    // 분까지 비교 (출석 체크 알럿용)
    private String getLocalDateTimeNowToStringMinute(LocalDateTime localDateTime) {
        String day = localDateTime.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(localDateTime);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        String[] ms = ts[1].split(":");
        return ts[0] + " (" + day + ") " + ms[0] + ms[1];
    }
}

