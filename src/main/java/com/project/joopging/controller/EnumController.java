package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.enumDto.EnumDto;
import com.project.joopging.service.EnumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnumController {
    private final EnumService enumService;

    @GetMapping("/enum/distance")
    public ResponseDto findAllDistance() {
        EnumDto result = enumService.findDistance();
        return new ResponseDto(200L, "Distance 정보 전달 성공", result);
    }

    @GetMapping("/enum/location")
    public ResponseDto findAllLocation() {
        EnumDto result = enumService.findLocation();
        return new ResponseDto(200L, "location 정보 전달 성공", result);
    }

    @GetMapping("/enum/type")
    public ResponseDto findAllType() {
        EnumDto result = enumService.findType();
        return new ResponseDto(200L, "type 정보 전달 성공", result);
    }

}
