package org.example.foodordersystem.service.exception;

public class ItemNameAlreadyExistsException extends RuntimeException {
    private static final String ITEM_WITH_NAME_ALREADY_EXISTS = "Item with name '%s' already exists";

    public ItemNameAlreadyExistsException(String name) {
        super(String.format(ITEM_WITH_NAME_ALREADY_EXISTS, name));
    }
}
