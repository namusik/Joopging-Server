package com.project.joopging.enums;

import com.project.joopging.exception.CustomErrorException;

public enum Type {
    MOUNTAIN(0,"등산하면서"),
    OFF_WORK(1,"퇴근하면서"),
    CITY_WORK(2,"도심 산책하면서"),
    WATERFRONT(3,"해안가에서"),
    TRAVEL(4,"여행하면서"),
    PARK(5,"공원에서");

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
            return OFF_WORK;
        } else if (id.equals(2)) {
            return CITY_WORK;
        } else if (id.equals(3)) {
            return WATERFRONT;
        } else if (id.equals(4)) {
            return TRAVEL;
        } else if (id.equals(5)) {
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
