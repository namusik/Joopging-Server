package com.project.joopging.enums;

import com.project.joopging.exception.CustomErrorException;

public enum Location {
    ALL_LOCATION(0,"지역전체"),
    JUNG_GU(1,"중구"),
    YONGSAN_GU(2,"용산구"),
    GWANGJIN_GU(3,"광진구"),
    DONGDAEMUN_GU(4,"동대문구"),
    JUNGNANG_GU(5,"중랑구"),
    SEONGBUK_GU(6,"성북구"),
    GANGBUK_GU(7,"강북구"),
    DOBONG_GU(8,"도봉구"),
    NOWON_GU(9,"노원구"),
    EUNPYEONG_GU(10,"은평구"),
    SEODAEMUN_GU(11,"서대문구"),
    MAPO_GU(12,"마포구"),
    YANGCHEON_GU(13,"양천구"),
    GANGSEO_GU(14,"강서구"),
    GURO_GU(15,"구로구"),
    GEUMCHEON_GU(16,"금천구"),
    YEONGDEUNGPO_GU(17,"영등포구"),
    DONGJAK_GU(18,"동작구"),
    GWANAK_GU(19,"관악구"),
    SERCHO_GU(20,"서초구"),
    GANGNAM_GU(21,"강남구"),
    SONGPA_GU(22,"송파구"),
    GANGDONG_GU(23,"강동구"),
    JONGNO_GU(24,"종로구"),
    SEONGDONG_GU(25,"성동구");


    public String getName() {
        return name;
    }

    public Integer getNum() {
        return num;
    }

    public static Location getLocationById(Integer id) {
        if (id.equals(0)) {
            return ALL_LOCATION;
        } else if (id.equals(1)) {
            return JUNG_GU;
        } else if (id.equals(2)) {
            return YONGSAN_GU;
        } else if (id.equals(3)) {
            return GWANGJIN_GU;
        } else if (id.equals(4)) {
            return DONGDAEMUN_GU;
        } else if (id.equals(5)) {
            return JUNGNANG_GU;
        } else if (id.equals(6)) {
            return SEONGBUK_GU;
        } else if (id.equals(7)) {
            return GANGBUK_GU;
        } else if (id.equals(8)) {
            return DOBONG_GU;
        } else if (id.equals(9)) {
            return NOWON_GU;
        } else if (id.equals(10)) {
            return EUNPYEONG_GU;
        } else if (id.equals(11)) {
            return SEODAEMUN_GU;
        } else if (id.equals(12)) {
            return MAPO_GU;
        } else if (id.equals(13)) {
            return YANGCHEON_GU;
        } else if (id.equals(14)) {
            return GANGSEO_GU;
        } else if (id.equals(15)) {
            return GURO_GU;
        } else if (id.equals(16)) {
            return GEUMCHEON_GU;
        } else if (id.equals(17)) {
            return YEONGDEUNGPO_GU;
        } else if (id.equals(18)) {
            return DONGJAK_GU;
        } else if (id.equals(19)) {
            return GWANAK_GU;
        } else if (id.equals(20)) {
            return SERCHO_GU;
        } else if (id.equals(21)) {
            return GANGNAM_GU;
        } else if (id.equals(22)) {
            return SONGPA_GU;
        } else if (id.equals(23)) {
            return GANGDONG_GU;
        } else if (id.equals(24)) {
            return JONGNO_GU;
        } else if (id.equals(25)) {
            return SEONGDONG_GU;
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
