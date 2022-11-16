package com.sku.TravelF.domain;

import lombok.*;
@NoArgsConstructor
@Getter
@Setter
public class Region {

    private String region;
    private String areaCode;

    public Region(String region, String areaCode) {
        this.region = region;
        this.areaCode = areaCode;
    }
}

