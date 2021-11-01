package com.project.joopging.model;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class test {
    @Test
    void test() {
//        LocalDate now = LocalDate.now();
//        System.out.println("now = " + now);
//        LocalDate yesterday = LocalDate.of(2021, 10, 13);
//        System.out.println("yesterday = " + yesterday);
//        long between = ChronoUnit.DAYS.between(yesterday, now);
//        System.out.println("between = " + between);
        //
        LocalDateTime nowTime = LocalDateTime.now();
//        System.out.println("nowTime = " + nowTime);
        String displayName = nowTime.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
//        System.out.println("displayName = " + displayName);
        String date = String.valueOf(nowTime);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        System.out.println(ts[0] + " ("+displayName+") " + ts[1]);
//        LocalDate localDate = nowTime.toLocalDate();
//        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
//        System.out.println("localDate = " + localDate);
//        System.out.println("dayOfWeek = " + dayOfWeek);

    }
}
