package com.einkdisplay.einkdisplayserver;

public class ResourceNotFoundException extends RuntimeException {

    ResourceNotFoundException(String message) {
        super(message);
    }
}