package com.project.joopging.TestController;


import com.google.gson.JsonArray;
import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.requestDto.PhoneNumberRequestDto;
import com.project.joopging.dto.responseDto.CertificateNumberResponseDto;
import com.project.joopging.schedule.SmsSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SmsTestController {

    private final SmsSchedule smsSchedule;

//    @GetMapping("/test3")
//    public ResponseDto send() {
//        JsonArray toList = new JsonArray();
//        String number = "01099403102";
//        toList.add(number);
//        String message = "안녕하세요 줍깅입니다. " +
//                "https://forms.gle/X3nQmmbHiRwmmWtZ8";
//        smsSchedule.sendSms(toList,message);
//        return new ResponseDto(200L,"테스트 성공","");
//    }

    @PostMapping("/check/number")
    public ResponseDto certificatePhoneNumber(
            @RequestBody PhoneNumberRequestDto requestDto
    ) {
        CertificateNumberResponseDto data = smsSchedule.certificatePhoneNumber(requestDto);
        return new ResponseDto(200L,"문자메세지 전송완료", data);
    } 
//
//    @GetMapping("/test4")
//    public ResponseDto test4() {
//        JsonArray toList = new JsonArray();
//        String number = "01099403102";
//        String message = "안녕하세요 줍깅입니다." +" ["+ "우쥬같이줍깅!" +"] "+ "모임의 모임원들은" +
//                "다 모이셨나요? 출석체크를 해주세요!" +
//                "\r\n" +
//                "\r\n" +
//                "출석체크는 앞으로 유저간의 신뢰도를 측정하는데 도움이 됩니다!" +
//                "\r\n" +
//                "\r\n" +
//                "☞ https://joopgging.link/meetingcheck/" + 71;
//        toList.add(number);
//        smsSchedule.sendSms(toList,message);
//
//        return new ResponseDto(200L,"문자메세지 전송완료","");
//    }
//    @GetMapping("/test5")
//    public ResponseDto test5() {
//        JsonArray toList = new JsonArray();
//        String number = "01095591070";
//        String message = "안녕하세요 줍깅입니다. 이번" +" ["+ "우쥬같이줍깅" + "] " + "모임은 어떠셨나요?" +
//                "\r\n" +
//                "\r\n" +
//                "후기를 작성하여 다른 사용자에게 플로깅이 얼마나 좋은지 알려주세요! " +
//                "\r\n" +
//                "\r\n" +
//                "★Event★ 이벤트 기간 중 설문조사를 작성하시면 소정의 기프티콘을 드려요! " +
//                "\r\n" +
//                "\r\n" +
//                "☞ https://forms.gle/X3nQmmbHiRwmmWtZ8";
//        toList.add(number);
//        smsSchedule.sendSms(toList,message);
//
//        return new ResponseDto(200L,"문자메세지 전송완료","");
//    }
//
//
//    @GetMapping("/test3")
//    public ResponseDto test3() {
//        smsSchedule.sendInduceReviewAlertToCrew();
//        return new ResponseDto(200L,"문자메세지 전송완료","");
//    }
}
