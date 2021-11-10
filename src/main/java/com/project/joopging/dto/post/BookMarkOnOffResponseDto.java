package com.project.joopging.dto.post;


import lombok.Getter;



@Getter
public class BookMarkOnOffResponseDto {

    private final boolean bookMarkOnOff;

    private BookMarkOnOffResponseDto(boolean bookMarkOnOff) {
        this.bookMarkOnOff = bookMarkOnOff;
    }

    public static BookMarkOnOffResponseDto OnOff(boolean bookMarkOnOff) {
        return new BookMarkOnOffResponseDto(bookMarkOnOff);
    }

}
