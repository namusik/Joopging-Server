package com.project.joopging.enums;


import com.project.joopging.exception.CustomErrorException;

public enum Distance {
    THREE_KM(0,"3KM 이내"),
    FIVE_KM(1,"5KM"),
    TEN_KM(2,"10KM"),
    FIFTEEN_KM(3,"15KM"),
    TWENTY_KM(4,"20KM"),
    NO_LIMIT_KM(5,"거리는 상관 없어요");

    public String getName() {
        return name;
    }

    public Integer getNum() {
        return num;
    }

    public static Distance getDistanceById(Integer id) {
        if (id.equals(0)) {
            return THREE_KM;
        } else if (id.equals(1)) {
            return FIVE_KM;
        } else if (id.equals(2)) {
            return TEN_KM;
        } else if (id.equals(3)) {
            return FIFTEEN_KM;
        } else if (id.equals(4)) {
            return TWENTY_KM;
        } else if (id.equals(5)) {
            return NO_LIMIT_KM;
        } else {
            throw  new CustomErrorException("거리를 설정해주세요.");
        }
    }

    private final Integer num;
    private final String name;


    Distance(Integer num, String name) {
        this.num = num;
        this.name = name;

    }
}
