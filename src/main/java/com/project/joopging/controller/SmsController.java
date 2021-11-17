package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "Sms Controller Api V1")
public class SmsController {

    private SmsService smsService;

    @ApiOperation(value = "검색")
    @GetMapping("/send")
    public ResponseDto sendSms() {
        SmsService.sendSms();
        return new ResponseDto(200L,"문자보내기 성공","");
    }

}
