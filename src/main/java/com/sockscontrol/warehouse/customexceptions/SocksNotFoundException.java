package com.sockscontrol.warehouse.customexceptions;

import lombok.NonNull;

import javax.validation.constraints.Min;

public class SocksNotFoundException extends RuntimeException {
    public SocksNotFoundException(String color) {
        super("Не найдено носков запрашиваемого цвета: " + color);
    }

    public SocksNotFoundException(String color, Integer cottonPart) {
        super("Не найдено носков с такими параметрами:\n" +
                "Цвет: " + color +
                "\nСодержание хлопка " + cottonPart);
    }

    public SocksNotFoundException() {
        super("Запрашиваемая строка отсутствует в БД");
    }
}
