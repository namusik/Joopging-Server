package com.project.joopging.service;

import com.project.joopging.dto.enumDto.EnumDto;
import com.project.joopging.dto.enumDto.EnumRequsetDto;
import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnumService {

    public EnumDto findDistance() {
        List<EnumRequsetDto> distancelist = new ArrayList<>();

        for (Distance distance : Distance.values()) {
            EnumRequsetDto dto = new EnumRequsetDto(distance.getNum(), distance.getName());
            distancelist.add(dto);
        }
        return new EnumDto(distancelist);
    }

    public EnumDto findLocation() {
        List<EnumRequsetDto> locationlist = new ArrayList<>();

        for (Location location : Location.values()) {
            EnumRequsetDto dto = new EnumRequsetDto(location.getNum(), location.getName());
            locationlist.add(dto);
        }
        return new EnumDto(locationlist);
    }

    public EnumDto findType() {
        List<EnumRequsetDto> typelist = new ArrayList<>();

        for (Type type : Type.values()) {
            EnumRequsetDto dto = new EnumRequsetDto(type.getNum(), type.getName());
            typelist.add(dto);
        }
        return new EnumDto(typelist);
    }


}
