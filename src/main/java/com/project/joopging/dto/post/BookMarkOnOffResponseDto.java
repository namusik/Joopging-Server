package com.project.joopging.dto.post;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;



@Getter
@ApiModel(value = "북마크 On/Off 응답 DTO", description = "북마크 On/Off 응답 DTO")
public class BookMarkOnOffResponseDto {

    private final boolean bookMarkOnOff;

    private BookMarkOnOffResponseDto(boolean bookMarkOnOff) {
        this.bookMarkOnOff = bookMarkOnOff;
    }

    public static BookMarkOnOffResponseDto OnOff(boolean bookMarkOnOff) {
        return new BookMarkOnOffResponseDto(bookMarkOnOff);
    }

}
