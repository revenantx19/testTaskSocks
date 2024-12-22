package com.sockscontrol.warehouse.customexceptions;

public class InvalidInputException extends Throwable {
    public InvalidInputException(String comparison, Exception e) {
        super("Введён недопустимый оператор сравнения: " + comparison);
    }
}
