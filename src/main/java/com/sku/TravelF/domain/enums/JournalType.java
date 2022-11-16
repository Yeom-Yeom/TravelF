package com.sku.TravelF.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JournalType {
    everyone("모두의 일지"),
    alone("나만의 일지");

    private String value;
}
