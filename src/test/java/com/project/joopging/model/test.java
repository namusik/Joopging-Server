package com.project.joopging.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class test {
    @Test
    void test() {
        LocalDate now = LocalDate.now();
        System.out.println("now = " + now);
        LocalDate yesterday = LocalDate.of(2021, 10, 13);
        System.out.println("yesterday = " + yesterday);
        long between = ChronoUnit.DAYS.between(yesterday, now);
        System.out.println("between = " + between);

    }
}
