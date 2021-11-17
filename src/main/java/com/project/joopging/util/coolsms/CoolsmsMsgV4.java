package com.project.joopging.util.coolsms;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.*;

// 일부 API는 Query Parameter를 추가로 사용할 수 있습니다.
public interface CoolsmsMsgV4 {

    // 심플 메시지
    @POST("messages/v4/send")
    Call<GroupModel> sendMessage(@Header("Authorization") String auth,
                                   @Body Message message);

    // 여러건 발송
    @POST("messages/v4/send-many")
    Call<GroupModel> sendMessages(@Header("Authorization") String auth,
                                  @Body JsonObject messages);

    // 그룹 메시지 - 그룹 생성
//    @POST("messages/v4/groups")
//    Call<GroupModel> createGroup(@Header("Authorization") String auth);

    // 그룹 메시지 - 그룹 목록
//    @GET("messages/v4/groups")
//    Call<GroupListModel> getGroups(@Header("Authorization") String auth);

    // 그룹 메시지 - 그룹 정보
//    @GET("messages/v4/groups/{groupId}")
//    Call<GroupModel> getGroupInfo(@Header("Authorization") String auth,
//                                  @Path("groupId") String groupId);

    // 그룹 메시지 - 그룹 삭제
//    @DELETE("messages/v4/groups/{groupId}")
//    Call<GroupModel> deleteGroupInfo(@Header("Authorization") String auth,
//                                     @Path("groupId") String groupId);

    // 그룹 메시지 - 그룹 메시지 추가
//    @PUT("messages/v4/groups/{groupId}/messages")
//    Call<AddMessageListModel> addGroupMessage(@Header("Authorization") String auth,
//                                              @Path("groupId") String groupId,
//                                              @Body MessageList messages);

//    // 그룹 메시지 - 그룹 메시지 발송
//    @POST("messages/v4/groups/{groupId}/send")
//    Call<ResponseBody> sendGroupMessage(@Header("Authorization") String auth,
//                                        @Path("groupId") String groupId);

    // 그룹 메시지 - 그룹 메시지 삭제
//    @HTTP(method = "DELETE", path = "messages/v4/groups/{groupId}/messages", hasBody = true)
//    Call<DeleteGroupModel> deleteGroupMessages(@Header("Authorization") String auth,
//                                               @Path("groupId") String groupId,
//                                               @Body MessageIds messageIds);

    // 메시지 조회
//    @GET("messages/v4/list")
//    Call<GetMessageListModel> getMessageList(@Header("Authorization") String auth);
}
