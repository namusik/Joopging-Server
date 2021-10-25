package com.project.joopging.enums;

import com.project.joopging.exception.CustomErrorException;

public enum Location {
    INCHEON(0,"인천"),
    SEOUL(1,"서울"),
    GYEONGGI_DO(2,"경기도"),
    GANGWON_DO(3,"강원도"),
    GYEONGSANG_DO(4,"경상도"),
    CHUNGCHEONG_DO(5,"충청도"),
    DEAGU(6,"대구"),
    DEAJEON(7,"대전"),
    BUSAN(8,"부산"),
    JEOLLA_DO(9,"전라도"),
    GWANGJU(10,"광주"),
    JEJU_ISLAND(11,"제주도");


    public String getName() {
        return name;
    }

    public Integer getNum() {
        return num;
    }

    public static Location getLocationById(Integer id) {
        if (id.equals(0)) {
            return INCHEON;
        } else if (id.equals(1)) {
            return SEOUL;
        } else if (id.equals(2)) {
            return GYEONGGI_DO;
        } else if (id.equals(3)) {
            return GANGWON_DO;
        } else if (id.equals(4)) {
            return GYEONGSANG_DO;
        } else if (id.equals(5)) {
            return CHUNGCHEONG_DO;
        } else if (id.equals(6)) {
            return DEAGU;
        } else if (id.equals(7)) {
            return DEAJEON;
        } else if (id.equals(8)) {
            return BUSAN;
        } else if (id.equals(9)) {
            return JEOLLA_DO;
        } else if (id.equals(10)) {
            return GWANGJU;
        } else if (id.equals(11)) {
            return JEJU_ISLAND;
        } else {
            throw new CustomErrorException("지역을 설정해주세요.");
        }
    }

    private final Integer num;
    private final String name;

    Location(Integer num, String name) {
        this.num = num;
        this.name = name;
    }
}
