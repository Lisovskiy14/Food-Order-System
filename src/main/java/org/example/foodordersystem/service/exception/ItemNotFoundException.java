package org.example.foodordersystem.service.exception;

public class ItemNotFoundException extends ResourceNotFoundException {
    private static final String ITEM_WITH_ID_NOT_FOUND = "Item with id '%s' not found";

    public ItemNotFoundException(String id) {
        super(String.format(ITEM_WITH_ID_NOT_FOUND, id));
    }
}
