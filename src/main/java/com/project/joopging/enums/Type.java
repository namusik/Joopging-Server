package com.project.joopging.enums;

import com.project.joopging.exception.CustomErrorException;

public enum Type {
    MOUNTAIN(0,"산에서"),
    HAN_RIVER(1,"한강에서"),
    CITY_WORK(2,"도심에서"),
    PARK(3,"공원에서");

    public String getName() {
        return name;
    }

    public Integer getNum() {
        return num;
    }

    public static Type getTypeById(Integer id) {
        if (id.equals(0)) {
            return MOUNTAIN;
        } else if (id.equals(1)) {
            return HAN_RIVER;
        } else if (id.equals(2)) {
            return CITY_WORK;
        } else if (id.equals(3)) {
            return PARK;
        } else {
            throw new CustomErrorException("선호 환경을 설정해주세요.");
        }
    }


    private final Integer num;
    private final String name;


    Type(Integer num, String name) {
        this.num = num;
        this.name = name;
    }
}
