package com.example.rehabilitationandintegration.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum DayOfWeekEnum {
    MONDAY(1, "BAZAR_ERTESI"),
    TUESDAY(2, "CERSENBE_AXSAMI"),
    WEDNESDAY(3, "CERSENBE"),
    THURSDAY(4, "CUME_AXSAMI"),
    FRIDAY(5, "CUME");

    private final int number;
    private final String name;

    DayOfWeekEnum(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public static DayOfWeekEnum fromString(String name) {
        for (DayOfWeekEnum day : DayOfWeekEnum.values()) {
            if (day.getName().equalsIgnoreCase(name)) {
                return day;
            }
        }
        throw new IllegalArgumentException("Неизвестный день недели: " + name);
    }

    public static DayOfWeekEnum fromNumber(int number) {
        for (DayOfWeekEnum day : DayOfWeekEnum.values()) {
            if (day.getNumber() == number) {
                return day;
            }
        }
        throw new IllegalArgumentException("Неизвестный номер дня недели: " + number);
    }

    public static int toNumber(String name) {
        for (DayOfWeekEnum day : DayOfWeekEnum.values()) {
            if (day.getName().equalsIgnoreCase(name)) {
                return day.getNumber();  // Возвращает числовое значение дня
            }
        }
        throw new IllegalArgumentException("Неизвестный день недели: " + name);
    }

    public int getValue() {
        return ordinal() + 1;
    }
}

