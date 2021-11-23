package com.project.joopging.TestController;


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
}
