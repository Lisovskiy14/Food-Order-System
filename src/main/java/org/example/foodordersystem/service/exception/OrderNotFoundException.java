package org.example.foodordersystem.service.exception;

public class OrderNotFoundException extends ResourceNotFoundException {
    private static final String ORDER_WITH_ID_NOT_FOUND = "Order with id '%s' not found";

    public OrderNotFoundException(String id) {
        super(String.format(ORDER_WITH_ID_NOT_FOUND, id));
    }
}
