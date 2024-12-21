package com.sockscontrol.warehouse.customexceptions;

public class InvalidInputException extends Throwable {
    public InvalidInputException(String comparison) {
        super("Введён недопустимый оператор сравнения: " + comparison);
    }
}
